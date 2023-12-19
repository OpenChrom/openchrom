/*******************************************************************************
 * Copyright (c) 2016, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.wsd.converter.supplier.abif.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.chemclipse.converter.exceptions.FileIsNotReadableException;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.wsd.converter.io.AbstractChromatogramWSDReader;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.wsd.converter.supplier.abif.internal.support.ChromatogramArrayReader;
import net.openchrom.wsd.converter.supplier.abif.internal.support.IChromatogramArrayReader;
import net.openchrom.wsd.converter.supplier.abif.model.IVendorChromatogram;
import net.openchrom.wsd.converter.supplier.abif.model.IVendorScan;
import net.openchrom.wsd.converter.supplier.abif.model.VendorChromatogram;
import net.openchrom.wsd.converter.supplier.abif.model.VendorScan;
import net.openchrom.wsd.converter.supplier.abif.model.VendorScanSignalDAD;

public class ChromatogramReader extends AbstractChromatogramWSDReader {

	private static final Logger logger = Logger.getLogger(ChromatogramReader.class);

	@Override
	public IChromatogramWSD read(File file, IProgressMonitor monitor) throws IOException {

		return readChromatogram(file, monitor);
	}

	@Override
	public IChromatogramOverview readOverview(File file, IProgressMonitor monitor) throws IOException {

		return readChromatogram(file, monitor);
	}

	// limit to 4 wavelenths (for each nucleobase) although format might have an optional 5th dye
	private static short[] backfallWaveLengths = {540, 568, 595, 615}; // model 310 doesn't seem to set those
	// Directory entry packed structure (no padding)
	private String tagName;
	private int tagNumber;
	@SuppressWarnings("unused")
	private short elementType;
	private short elementSize;
	private int elements;
	private int dataSize;
	private int dataOffset;

	// similar to the Tagged Image File Format (TIFF)
	private void readDirectory(IChromatogramArrayReader in) {

		tagName = in.readBytesAsString(4); // ASCII characters
		tagNumber = in.read4BIntegerBE(); // current element number (1-1000)
		elementType = in.read2BShortBE(); // which data type (only interesting for non-specified tags)
		elementSize = in.read2BShortBE(); // how much to read (actually redundant to specified data type)
		elements = in.read4BIntegerBE(); // number of elements in item
		dataSize = in.read4BIntegerBE(); // number of bytes
		if(dataSize > 4) { // may also be item's data (if 4 bytes or less)
			dataOffset = in.read4BIntegerBE(); // otherwise where to look for data
		}
	}

	private IChromatogramWSD readChromatogram(File file, IProgressMonitor monitor) throws IOException {

		IChromatogramArrayReader in = new ChromatogramArrayReader(file);
		/*
		 * Setup the chromatogram.
		 */
		IVendorChromatogram chromatogram = new VendorChromatogram();
		chromatogram.setConverterId(""); // If the chromatogram shall be exportable, set the id otherwise its null or "".
		chromatogram.setFile(file);
		in.resetPosition();
		/*
		 * Read and check the magic bytes.
		 */
		String magicNumber = in.readBytesAsString(4);
		// The byte order of these decides if this is little or big endian, but this was was never used.
		if(!magicNumber.equals("ABIF")) {
			throw new FileIsNotReadableException("Not an ABIF sequence trace file.");
		}
		/*
		 * Read and check the version
		 */
		short version = in.read2BShortBE();
		if(version / 100 != 1) {
			throw new FileIsNotReadableException("ABIF files other than major version 1 are not supported.");
		}
		chromatogram.setVersion(version);
		/*
		 * Read directory entry structure
		 */
		readDirectory(in);
		in.skipBytes(4); // unused zero byte data handle
		if(!tagName.equals("tdir")) {
			throw new FileIsNotReadableException("Can't find ABIF directory entry.");
		}
		int expectedDataSize = elements * elementSize; // legacy libraries may have reserved additional space in the directory
		if(dataSize != expectedDataSize) {
			logger.warn("Invalid data size " + dataSize + " in ABIF file detected. Expected " + expectedDataSize + ".");
		}
		/*
		 * Read data
		 */
		in.resetPosition();
		int directoryDataOffset = dataOffset;
		in.seek(directoryDataOffset);
		int position = in.getPosition();
		// temporary data storage as we need to reorder later
		int scans = 0;
		short downSamplingFactor = 0;
		ArrayList<short[]> channels = new ArrayList<>(4);
		short[] waveLengths = backfallWaveLengths;
		// Loop through all relevant data
		int directoryElements = elements;
		for(int n = 0; n < directoryElements; n++) {
			readDirectory(in);
			if(tagName.isEmpty()) {
				continue;
			}
			switch(tagName) {
				/*
				 * Read fluorescent dye wavelengths.
				 */
				case "DyeW":
					if(tagNumber > waveLengths.length) {
						in.skipBytes(2);
						continue;
					}
					short wavelength = in.read2BShortBE();
					waveLengths[tagNumber - 1] = wavelength;
					in.skipBytes(2);
					break;
				/*
				 * Injection time (s)
				 */
				case "InSc":
					long injectionTime = in.read4BLongBE();
					chromatogram.setScanInterval(injectionTime);
					break;
				/*
				 * Read Array of the pre-evaluated gene sequence characters.
				 */
				case "PBAS":
					// skip the user edited sequence
					if(tagNumber != 2) {
						in.skipBytes(4);
						continue;
					}
					position = in.getPosition();
					in.resetPosition();
					in.seek(dataOffset);
					// C-style string (null terminated).
					String uneditedSequenceCharacters = in.readBytesAsString(dataSize);
					chromatogram.setMiscInfo(uneditedSequenceCharacters);
					in.resetPosition();
					in.seek(position);
					break;
				/*
				 * Sample name
				 */
				case "SMPL":
					position = in.getPosition();
					in.resetPosition();
					in.seek(dataOffset);
					// Pascal style string (length is stored in first byte)
					int length = in.read1BShortBE();
					String sampleName = in.readBytesAsString(length);
					chromatogram.setSampleName(sampleName);
					in.resetPosition();
					in.seek(position);
					break;
				/*
				 * Container identifier
				 */
				case "CTID":
					position = in.getPosition();
					in.resetPosition();
					in.seek(dataOffset);
					// C-style string (null terminated).
					String containerId = in.readBytesAsString(dataSize);
					chromatogram.setSampleGroup(containerId);
					in.resetPosition();
					in.seek(position);
					break;
				/*
				 * raw data
				 */
				case "DSam":
					downSamplingFactor = in.read2BShortBE();
					in.skipBytes(2);
					break;
				case "DATA":
					// ignore voltage, current, power, temperature etc.
					if(tagNumber > waveLengths.length) {
						in.skipBytes(4);
						continue;
					}
					position = in.getPosition();
					in.resetPosition();
					in.seek(dataOffset);
					short[] data = new short[elements];
					scans = elements;
					for(int d = 0; d < elements; d++) {
						short raw = in.read2BShortBE();
						data[d] = raw;
					}
					in.resetPosition();
					in.seek(position);
					channels.add(data);
					break;
				default:
					if(dataSize <= 4) {
						in.skipBytes(4); // unused data
					}
					break;
			}
			in.skipBytes(4); // data handle
		}
		// tidy data
		channels.trimToSize();
		// convert into multidimensional format with signals per wavelength
		for(int s = 0; s < scans; s++) {
			IVendorScan scan = new VendorScan();
			for(int c = 0; c < waveLengths.length; c++) {
				int waveLength = waveLengths[c];
				VendorScanSignalDAD scanSignal = new VendorScanSignalDAD();
				scanSignal.setWavelength(waveLength);
				short[] channel = channels.get(c);
				short signal = channel[s];
				float abundance = signal;
				if(downSamplingFactor > 0) {
					abundance = signal / downSamplingFactor;
				}
				scanSignal.setAbundance(abundance);
				scan.addScanSignal(scanSignal);
			}
			chromatogram.addScan(scan);
		}
		chromatogram.recalculateRetentionTimes();
		return chromatogram;
	}
}

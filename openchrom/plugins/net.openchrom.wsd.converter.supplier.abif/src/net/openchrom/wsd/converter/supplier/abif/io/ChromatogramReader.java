/*******************************************************************************
 * Copyright (c) 2016, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.wsd.converter.supplier.abif.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import org.eclipse.chemclipse.converter.exceptions.FileIsNotReadableException;
import org.eclipse.chemclipse.dsd.converter.io.AbstractChromatogramDSDReader;
import org.eclipse.chemclipse.dsd.model.core.IChromatogramDSD;
import org.eclipse.chemclipse.dsd.model.core.Nucleobase;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.model.identifier.ComparisonResult;
import org.eclipse.chemclipse.model.identifier.IComparisonResult;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.model.identifier.LibraryInformation;
import org.eclipse.chemclipse.model.implementation.IdentificationTarget;
import org.eclipse.chemclipse.wsd.model.core.IScanWSD;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.wsd.converter.supplier.abif.internal.support.ChromatogramArrayReader;
import net.openchrom.wsd.converter.supplier.abif.internal.support.IChromatogramArrayReader;
import net.openchrom.wsd.converter.supplier.abif.metric.BaseCallerMetrics;
import net.openchrom.wsd.converter.supplier.abif.model.IVendorChromatogram;
import net.openchrom.wsd.converter.supplier.abif.model.IVendorScan;
import net.openchrom.wsd.converter.supplier.abif.model.VendorChromatogram;
import net.openchrom.wsd.converter.supplier.abif.model.VendorScan;
import net.openchrom.wsd.converter.supplier.abif.model.VendorScanSignalWSD;

public class ChromatogramReader extends AbstractChromatogramDSDReader {

	private static final Logger logger = Logger.getLogger(ChromatogramReader.class);

	@Override
	public IChromatogramDSD read(File file, IProgressMonitor monitor) throws IOException {

		return readChromatogram(file);
	}

	@Override
	public IChromatogramOverview readOverview(File file, IProgressMonitor monitor) throws IOException {

		return readChromatogram(file);
	}

	// limit to 4 wavelenths (for each nucleobase) although format might have an optional 5th dye
	private static short[] backfallWaveLengths = {540, 568, 595, 615}; // model 310 doesn't seem to set those
	// Directory entry packed structure (no padding)
	private String tagName;
	private int tagNumber;
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

	private IChromatogramDSD readChromatogram(File file) throws IOException {

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
		int expectedDataSize = elements * elementSize;
		if(dataSize < expectedDataSize) { // legacy libraries may have reserved additional space in the directory
			logger.warn("Invalid data size " + dataSize + " in ABIF file detected. Expected " + expectedDataSize + ".");
		}
		/*
		 * Read data
		 */
		in.resetPosition();
		int directoryDataOffset = dataOffset;
		in.seek(directoryDataOffset);
		// temporary data storage as we need to reorder later
		int scans = 0;
		short downSamplingFactor = 0;
		ArrayList<short[]> channels = new ArrayList<>(4);
		short[] waveLengths = backfallWaveLengths;
		// Loop through all relevant data
		int directoryElements = elements;
		char[] baseOrder = new char[4];
		byte[] quality = new byte[0];
		char[] nucleotides = new char[0];
		ArrayList<Integer> peakLocations = new ArrayList<>();
		for(int n = 0; n < directoryElements; n++) {
			readDirectory(in);
			if(tagName.isEmpty()) {
				logger.warn("Ignoring empty tag name of element type " + elementType);
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
				 * Read filter wheel order.
				 */
				case "FWO_":
					baseOrder = in.readString(4).toCharArray(); // GATC
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
					if(tagNumber != 1) {
						in.skipBytes(4);
						continue;
					}
					int position = in.getPosition();
					in.resetPosition();
					in.seek(dataOffset);
					// C-style string (null terminated).
					nucleotides = in.readBytesAsString(dataSize).toCharArray();
					in.resetPosition();
					in.seek(position);
					break;
				/*
				 * Read quality values.
				 */
				case "PCON":
					// skip the user edited sequence
					if(tagNumber != 1) {
						in.skipBytes(4);
						continue;
					}
					position = in.getPosition();
					in.resetPosition();
					in.seek(dataOffset);
					quality = in.readBytes(dataSize);
					in.resetPosition();
					in.seek(position);
					break;
				/*
				 * Read peak locations
				 */
				case "PLOC":
					// skip the user edited sequence
					if(tagNumber != 1) {
						in.skipBytes(4);
						continue;
					}
					position = in.getPosition();
					in.resetPosition();
					in.seek(dataOffset);
					for(int i = 0; i < dataSize / Short.BYTES; i++) {
						peakLocations.add(in.read2BUIntegerBE());
					}
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
				 * Machine
				 */
				case "MCHN":
					position = in.getPosition();
					in.resetPosition();
					in.seek(dataOffset);
					// Pascal style string (length is stored in first byte)
					length = in.read1BShortBE();
					String instrument = in.readBytesAsString(length);
					chromatogram.setInstrument(instrument);
					in.resetPosition();
					in.seek(position);
					break;
				/*
				 * Run Date
				 */
				case "RUND": // actually 2 (start and stop)
					short year = in.read2BShortBE();
					short month = in.read1BShortBE();
					short day = in.read1BShortBE();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(chromatogram.getDate());
					calendar.set(Calendar.YEAR, year);
					calendar.set(Calendar.MONTH, month - 1);
					calendar.set(Calendar.DAY_OF_MONTH, day);
					chromatogram.setDate(calendar.getTime());
					break;
				/*
				 * Run Time
				 */
				case "RUNT": // actually 4 (start and stop and data collection start stop)
					short hour = in.read1BShortBE();
					short minute = in.read1BShortBE();
					short second = in.read1BShortBE();
					short hsecond = in.read1BShortBE(); // hundredths of a second.
					calendar = Calendar.getInstance();
					calendar.setTime(chromatogram.getDate());
					calendar.set(Calendar.HOUR_OF_DAY, hour);
					calendar.set(Calendar.MINUTE, minute);
					calendar.set(java.util.Calendar.SECOND, second);
					calendar.set(java.util.Calendar.MILLISECOND, hsecond * 10);
					chromatogram.setDate(calendar.getTime());
					break;
				/*
				 * Operator
				 */
				case "User":
					if(dataSize < 4) {
						break;
					}
					position = in.getPosition();
					in.resetPosition();
					in.seek(dataOffset);
					// Pascal style string (length is stored in first byte)
					length = in.read1BShortBE();
					String user = in.readBytesAsString(length);
					chromatogram.setOperator(user);
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
					chromatogram.setSampleGroup(containerId.trim());
					in.resetPosition();
					in.seek(position);
					break;
				/*
				 * Well Position
				 */
				case "TUBE":
					// Pascal style string (length is stored in first byte)
					short size = in.read1BShortBE();
					chromatogram.setWell(in.readString(size).trim());
					in.skipBytes(4 - dataSize); // padding
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
					if(tagNumber < 9 || tagNumber > 12) {
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
				VendorScanSignalWSD scanSignal = new VendorScanSignalWSD();
				scanSignal.setWavelength(waveLength);
				short[] channel = channels.get(c);
				short signal = channel[s];
				float abundance = signal;
				if(downSamplingFactor > 0) {
					abundance = signal / (float)downSamplingFactor;
				}
				scanSignal.setAbsorbance(abundance);
				scan.addScanSignal(scanSignal);
			}
			chromatogram.addScan(scan);
		}
		chromatogram.recalculateRetentionTimes();

		for(int i = 0; i < waveLengths.length; i++) {
			if(baseOrder[i] == Nucleobase.ADENINE.letter()) {
				chromatogram.getWavelengthMapping().put((float)waveLengths[i], Nucleobase.ADENINE);
			} else if(baseOrder[i] == Nucleobase.CYTOSINE.letter()) {
				chromatogram.getWavelengthMapping().put((float)waveLengths[i], Nucleobase.CYTOSINE);
			} else if(baseOrder[i] == Nucleobase.GUANINE.letter()) {
				chromatogram.getWavelengthMapping().put((float)waveLengths[i], Nucleobase.GUANINE);
			} else if(baseOrder[i] == Nucleobase.THYMINE.letter()) {
				chromatogram.getWavelengthMapping().put((float)waveLengths[i], Nucleobase.THYMINE);
			}
		}

		int i = 0;
		for(int peakLocation : peakLocations) {
			IScanWSD scan = chromatogram.getScan(peakLocation + 1);

			ILibraryInformation libraryInformation = new LibraryInformation();
			libraryInformation.setName(String.valueOf(nucleotides[i]));

			IComparisonResult comparisonResult = new ComparisonResult(BaseCallerMetrics.ALGORITHM_ABIF);
			comparisonResult.setMetric(BaseCallerMetrics.PHRED_QUALITY_SCORE, quality[i]);

			IIdentificationTarget identificationTarget = new IdentificationTarget(libraryInformation, comparisonResult);
			scan.getTargets().add(identificationTarget); // TODO add to scan signal rather than total signal
			scan.setCycleNumber(i + 1); // TODO: move elsewhere

			i++;
		}

		return chromatogram;
	}
}

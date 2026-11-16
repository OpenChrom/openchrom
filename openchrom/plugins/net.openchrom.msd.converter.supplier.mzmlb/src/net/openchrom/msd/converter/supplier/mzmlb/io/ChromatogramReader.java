/*******************************************************************************
 * Copyright (c) 2021, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mzmlb.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Deque;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.eclipse.chemclipse.converter.io.AbstractChromatogramReader;
import org.eclipse.chemclipse.converter.l10n.ConverterMessages;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.msd.converter.io.IChromatogramMSDReader;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.Polarity;
import org.eclipse.chemclipse.support.xml.XmlParserFactory;
import org.eclipse.chemclipse.xxd.converter.supplier.mzml.io.XmlReader110;
import org.eclipse.chemclipse.xxd.converter.supplier.mzml.model.v110.CVParamType;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;

import net.openchrom.msd.converter.supplier.mzmlb.internal.io.MzMLbInputStream;
import net.openchrom.msd.converter.supplier.mzmlb.internal.io.ReaderProxy;
import net.openchrom.msd.converter.supplier.mzmlb.io.support.IScanMarker;
import net.openchrom.msd.converter.supplier.mzmlb.io.support.ScanMarker;
import net.openchrom.msd.converter.supplier.mzmlb.model.IVendorChromatogram;
import net.openchrom.msd.converter.supplier.mzmlb.model.IVendorScanProxy;
import net.openchrom.msd.converter.supplier.mzmlb.model.VendorChromatogram;
import net.openchrom.msd.converter.supplier.mzmlb.model.VendorScanProxy;

import hdf.hdf5lib.exceptions.HDF5Exception;

public class ChromatogramReader extends AbstractChromatogramReader implements IChromatogramMSDReader {

	private static final Logger logger = Logger.getLogger(ChromatogramReader.class);

	private static final String FILE_CONTENT = "fileContent";
	private static final String SPECTRUM_LIST = "spectrumList";
	private static final String SPECTRUM = "spectrum";
	private static final String SCAN = "scan";
	private static final String BINARY_DATA_ARRAY = "binaryDataArray";
	private static final String CV_PARAM = "cvParam";
	private static final String CHROMATOGRAM_LIST = "chromatogramList";

	@Override
	public IChromatogramOverview readOverview(File file, IProgressMonitor monitor) throws IOException {

		return null;
	}

	@Override
	public IChromatogramMSD read(File file, IProgressMonitor monitor) throws IOException {

		IVendorChromatogram chromatogram = null;
		/*
		 * The embedded mzML document can exceed the maximum Java array size,
		 * hence stream it from the HDF5 dataset and read spectrum by spectrum.
		 * Only a few attributes per spectrum are needed, so they are taken
		 * directly from the StAX events, which is much faster than JAXB.
		 */
		try (InputStream inputStream = new MzMLbInputStream(file, "mzML")) {
			XMLStreamReader reader = XmlParserFactory.createInputFactory().createXMLStreamReader(inputStream);
			try {
				chromatogram = new VendorChromatogram();
				chromatogram.setFile(file);
				IReaderProxy readerProxy = new ReaderProxy(file);
				SubMonitor subMonitor = null; // created once the number of spectra is known
				/*
				 * MSn spectra are grouped into cycles, each started by an MS1 scan.
				 * Scans before the first MS1 scan of a MSn measurement belong to cycle 1.
				 */
				int cycleNumber = 0;
				while(reader.hasNext()) {
					if(monitor.isCanceled()) {
						break;
					}
					if(reader.isStartElement()) {
						String localName = reader.getLocalName();
						if(FILE_CONTENT.equals(localName)) {
							if(isMultiStageMassSpectrum(reader)) {
								cycleNumber = 1;
							}
						} else if(SPECTRUM_LIST.equals(localName)) {
							subMonitor = SubMonitor.convert(monitor, ConverterMessages.readScans, getCount(reader));
						} else if(SPECTRUM.equals(localName)) {
							IVendorScanProxy scanProxy = readSpectrum(reader, readerProxy, monitor);
							if(scanProxy.getMassSpectrometer() < 2) {
								cycleNumber++;
							}
							if(cycleNumber >= 1) {
								scanProxy.setCycleNumber(cycleNumber);
							}
							chromatogram.addScan(scanProxy);
							if(subMonitor != null) {
								subMonitor.worked(1);
							}
						} else if(CHROMATOGRAM_LIST.equals(localName)) {
							break; // everything needed has been read
						}
					}
					reader.next();
				}
			} finally {
				reader.close();
			}
		} catch(HDF5Exception e) {
			logger.error(e);
		} catch(XMLStreamException e) {
			logger.error(e);
		}
		return chromatogram;
	}

	private int getCount(XMLStreamReader reader) {

		String count = reader.getAttributeValue(null, "count");
		if(count != null) {
			try {
				return Integer.parseInt(count);
			} catch(NumberFormatException e) {
				logger.warn(e);
			}
		}
		return 0;
	}

	/**
	 * Expects the reader on the start of the file content and leaves it on its end.
	 */
	private boolean isMultiStageMassSpectrum(XMLStreamReader reader) throws XMLStreamException {

		boolean isMultiStage = false;
		int depth = 1;
		while(depth > 0) {
			int event = reader.next();
			if(event == XMLStreamConstants.START_ELEMENT) {
				if(depth == 1 && CV_PARAM.equals(reader.getLocalName())) {
					if("MS:1000580".equals(reader.getAttributeValue(null, "accession"))) {
						isMultiStage = true;
					}
				}
				depth++;
			} else if(event == XMLStreamConstants.END_ELEMENT) {
				depth--;
			}
		}
		return isMultiStage;
	}

	/**
	 * Expects the reader on the start of a spectrum and leaves it on its end.
	 */
	private IVendorScanProxy readSpectrum(XMLStreamReader reader, IReaderProxy readerProxy, IProgressMonitor monitor) throws XMLStreamException {

		IVendorScanProxy scanProxy = new VendorScanProxy(readerProxy, monitor);
		scanProxy.setScanNumber(Integer.parseInt(reader.getAttributeValue(null, "index")));
		scanProxy.setIdentifier(reader.getAttributeValue(null, "id"));
		IScanMarker scanMarker = new ScanMarker();
		BinaryDataArray binaryDataArray = new BinaryDataArray();
		/*
		 * Only the cvParams that are direct children of
		 * spectrum, scan and binaryDataArray are of interest.
		 */
		Deque<String> elements = new ArrayDeque<>();
		elements.push(SPECTRUM);
		while(!elements.isEmpty()) {
			int event = reader.next();
			if(event == XMLStreamConstants.START_ELEMENT) {
				String localName = reader.getLocalName();
				String parent = elements.peek();
				elements.push(localName);
				if(CV_PARAM.equals(localName)) {
					if(SPECTRUM.equals(parent)) {
						readSpectrumParameter(reader, scanProxy);
					} else if(SCAN.equals(parent)) {
						readScanParameter(reader, scanProxy);
					} else if(BINARY_DATA_ARRAY.equals(parent)) {
						readBinaryDataParameter(reader, scanMarker, binaryDataArray);
					}
				} else if(BINARY_DATA_ARRAY.equals(localName)) {
					binaryDataArray = new BinaryDataArray();
				}
			} else if(event == XMLStreamConstants.END_ELEMENT) {
				if(BINARY_DATA_ARRAY.equals(elements.pop())) {
					if(binaryDataArray.isMz) {
						readerProxy.setMzDataset(binaryDataArray.dataSet);
					} else if(binaryDataArray.isIntensity) {
						readerProxy.setIntensityDataset(binaryDataArray.dataSet);
					}
				}
			}
		}
		scanProxy.setScanMarker(scanMarker);
		return scanProxy;
	}

	private void readSpectrumParameter(XMLStreamReader reader, IVendorScanProxy scanProxy) {

		String accession = reader.getAttributeValue(null, "accession");
		String name = reader.getAttributeValue(null, "name");
		if("MS:1000285".equals(accession) && "total ion current".equals(name)) {
			scanProxy.setTotalSignal(Float.parseFloat(reader.getAttributeValue(null, "value")));
		} else if("MS:1000511".equals(accession) && "ms level".equals(name)) {
			scanProxy.setMassSpectrometer(Short.parseShort(reader.getAttributeValue(null, "value")));
		} else if("MS:1000129".equals(accession) && "negative scan".equals(name)) {
			scanProxy.setPolarity(Polarity.NEGATIVE);
		} else if("MS:1000130".equals(accession) && "positive scan".equals(name)) {
			scanProxy.setPolarity(Polarity.POSITIVE);
		}
	}

	private void readScanParameter(XMLStreamReader reader, IVendorScanProxy scanProxy) {

		String accession = reader.getAttributeValue(null, "accession");
		String name = reader.getAttributeValue(null, "name");
		if("MS:1000016".equals(accession) && "scan start time".equals(name)) {
			CVParamType cvParam = new CVParamType();
			cvParam.setUnitAccession(reader.getAttributeValue(null, "unitAccession"));
			cvParam.setUnitName(reader.getAttributeValue(null, "unitName"));
			float multiplicator = XmlReader110.getTimeMultiplicator(cvParam);
			int retentionTime = Math.round(Float.parseFloat(reader.getAttributeValue(null, "value")) * multiplicator);
			scanProxy.setRetentionTime(retentionTime);
		}
	}

	private void readBinaryDataParameter(XMLStreamReader reader, IScanMarker scanMarker, BinaryDataArray binaryDataArray) {

		String accession = reader.getAttributeValue(null, "accession");
		String name = reader.getAttributeValue(null, "name");
		if("MS:1002841".equals(accession) && "external HDF5 dataset".equals(name)) {
			binaryDataArray.dataSet = reader.getAttributeValue(null, "value");
		} else if("MS:1002842".equals(accession) && "external offset".equals(name)) {
			scanMarker.setOffset(Integer.parseInt(reader.getAttributeValue(null, "value")));
		} else if("MS:1002843".equals(accession) && "external array length".equals(name)) {
			scanMarker.setLength(Integer.parseInt(reader.getAttributeValue(null, "value")));
		} else if("MS:1000514".equals(accession) && "m/z array".equals(name)) {
			binaryDataArray.isMz = true;
		} else if("MS:1000515".equals(accession) && "intensity array".equals(name)) {
			binaryDataArray.isIntensity = true;
		}
	}

	private static class BinaryDataArray {

		private String dataSet = "";
		private boolean isMz = false;
		private boolean isIntensity = false;
	}
}

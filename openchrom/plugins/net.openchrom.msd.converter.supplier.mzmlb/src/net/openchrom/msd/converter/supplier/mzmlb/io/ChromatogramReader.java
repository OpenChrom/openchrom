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
				CombinedScan combinedScan = null;
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
							Spectrum spectrum = readSpectrum(reader, readerProxy);
							if(combinedScan != null && combinedScan.accepts(spectrum)) {
								combinedScan.add(spectrum);
							} else {
								combinedScan = new CombinedScan(new VendorScanProxy(readerProxy, monitor), spectrum);
								IVendorScanProxy scanProxy = combinedScan.scanProxy;
								if(scanProxy.getMassSpectrometer() < 2) {
									cycleNumber++;
								}
								if(cycleNumber >= 1) {
									scanProxy.setCycleNumber(cycleNumber);
								}
								chromatogram.addScan(scanProxy);
							}
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
	private Spectrum readSpectrum(XMLStreamReader reader, IReaderProxy readerProxy) throws XMLStreamException {

		Spectrum spectrum = new Spectrum();
		spectrum.index = Integer.parseInt(reader.getAttributeValue(null, "index"));
		spectrum.id = reader.getAttributeValue(null, "id");
		String defaultArrayLength = reader.getAttributeValue(null, "defaultArrayLength");
		if(defaultArrayLength != null) {
			spectrum.numberOfIons = Integer.parseInt(defaultArrayLength);
		}
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
						readSpectrumParameter(reader, spectrum);
					} else if(SCAN.equals(parent)) {
						readScanParameter(reader, spectrum);
					} else if(BINARY_DATA_ARRAY.equals(parent)) {
						readBinaryDataParameter(reader, binaryDataArray);
					}
				} else if(BINARY_DATA_ARRAY.equals(localName)) {
					binaryDataArray = new BinaryDataArray();
				}
			} else if(event == XMLStreamConstants.END_ELEMENT) {
				if(BINARY_DATA_ARRAY.equals(elements.pop())) {
					/*
					 * The m/z and intensity arrays of a spectrum can start at
					 * different offsets, e.g. when spectra share their m/z values.
					 */
					if(binaryDataArray.isMz) {
						readerProxy.setMzDataset(binaryDataArray.dataSet);
						spectrum.scanMarker.setOffset(binaryDataArray.offset);
						spectrum.scanMarker.setLength(binaryDataArray.length);
					} else if(binaryDataArray.isIntensity) {
						readerProxy.setIntensityDataset(binaryDataArray.dataSet);
						spectrum.scanMarker.setIntensityOffset(binaryDataArray.offset);
						spectrum.scanMarker.setLength(binaryDataArray.length);
					}
				}
			}
		}
		return spectrum;
	}

	private void readSpectrumParameter(XMLStreamReader reader, Spectrum spectrum) {

		String accession = reader.getAttributeValue(null, "accession");
		String name = reader.getAttributeValue(null, "name");
		if("MS:1000285".equals(accession) && "total ion current".equals(name)) {
			spectrum.totalSignal = Float.valueOf(reader.getAttributeValue(null, "value"));
		} else if("MS:1000511".equals(accession) && "ms level".equals(name)) {
			spectrum.msLevel = Short.parseShort(reader.getAttributeValue(null, "value"));
		} else if("MS:1000129".equals(accession) && "negative scan".equals(name)) {
			spectrum.polarity = Polarity.NEGATIVE;
		} else if("MS:1000130".equals(accession) && "positive scan".equals(name)) {
			spectrum.polarity = Polarity.POSITIVE;
		}
	}

	private void readScanParameter(XMLStreamReader reader, Spectrum spectrum) {

		String accession = reader.getAttributeValue(null, "accession");
		String name = reader.getAttributeValue(null, "name");
		if("MS:1000016".equals(accession) && "scan start time".equals(name)) {
			String value = reader.getAttributeValue(null, "value");
			CVParamType cvParam = new CVParamType();
			cvParam.setUnitAccession(reader.getAttributeValue(null, "unitAccession"));
			cvParam.setUnitName(reader.getAttributeValue(null, "unitName"));
			float multiplicator = XmlReader110.getTimeMultiplicator(cvParam);
			spectrum.scanStartTime = value;
			spectrum.retentionTime = Math.round(Float.parseFloat(value) * multiplicator);
		} else if("MS:1002815".equals(accession) || "MS:1002476".equals(accession)) {
			/*
			 * inverse reduced ion mobility (e.g. Bruker timsTOF)
			 * ion mobility drift time (e.g. Waters, Agilent)
			 */
			spectrum.ionMobility = true;
		}
	}

	private void readBinaryDataParameter(XMLStreamReader reader, BinaryDataArray binaryDataArray) {

		String accession = reader.getAttributeValue(null, "accession");
		String name = reader.getAttributeValue(null, "name");
		if("MS:1002841".equals(accession) && "external HDF5 dataset".equals(name)) {
			binaryDataArray.dataSet = reader.getAttributeValue(null, "value");
		} else if("MS:1002842".equals(accession) && "external offset".equals(name)) {
			binaryDataArray.offset = Integer.parseInt(reader.getAttributeValue(null, "value"));
		} else if("MS:1002843".equals(accession) && "external array length".equals(name)) {
			binaryDataArray.length = Integer.parseInt(reader.getAttributeValue(null, "value"));
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
		private int offset;
		private int length;
	}

	/**
	 * The attributes of a single spectrum element.
	 */
	private static class Spectrum {

		private int index;
		private String id;
		private int numberOfIons;
		private short msLevel = 1;
		private Polarity polarity;
		private Float totalSignal;
		private String scanStartTime;
		private int retentionTime;
		private boolean ionMobility;
		private final IScanMarker scanMarker = new ScanMarker();
	}

	/**
	 * Ion mobility spectrometers acquire a frame of many mobility scans at the
	 * same retention time. Converted without combining them, each mobility scan
	 * becomes a separate spectrum. Consecutive mobility spectra of the same
	 * frame are merged into a single scan, which is what a chromatogram needs.
	 */
	private static class CombinedScan {

		private final IVendorScanProxy scanProxy;
		private final Spectrum first;
		private int numberOfIons;
		/*
		 * ProteoWizard annotates each mobility scan with the total ion current
		 * of the whole frame. Only if the values differ, they are per scan.
		 */
		private float totalSignalSum;
		private boolean totalSignalPerFrame = true;

		private CombinedScan(IVendorScanProxy scanProxy, Spectrum spectrum) {

			this.scanProxy = scanProxy;
			this.first = spectrum;
			scanProxy.setScanNumber(spectrum.index);
			scanProxy.setIdentifier(spectrum.id);
			scanProxy.setMassSpectrometer(spectrum.msLevel);
			if(spectrum.polarity != null) {
				scanProxy.setPolarity(spectrum.polarity);
			}
			scanProxy.setRetentionTime(spectrum.retentionTime);
			add(spectrum);
		}

		private boolean accepts(Spectrum spectrum) {

			return first.ionMobility && spectrum.ionMobility //
					&& first.scanStartTime != null && first.scanStartTime.equals(spectrum.scanStartTime) //
					&& first.msLevel == spectrum.msLevel //
					&& first.polarity == spectrum.polarity;
		}

		private void add(Spectrum spectrum) {

			scanProxy.addScanMarker(spectrum.scanMarker);
			numberOfIons += spectrum.numberOfIons;
			scanProxy.setNumberOfIons(numberOfIons);
			if(spectrum.totalSignal != null) {
				totalSignalSum += spectrum.totalSignal;
				if(first.totalSignal == null || !first.totalSignal.equals(spectrum.totalSignal)) {
					totalSignalPerFrame = false;
				}
			}
			if(totalSignalPerFrame && first.totalSignal != null) {
				scanProxy.setTotalSignal(first.totalSignal);
			} else {
				scanProxy.setTotalSignal(totalSignalSum);
			}
		}
	}
}

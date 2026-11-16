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
import org.eclipse.chemclipse.xxd.converter.supplier.mzml.model.v110.BinaryDataArrayType;
import org.eclipse.chemclipse.xxd.converter.supplier.mzml.model.v110.CVParamType;
import org.eclipse.chemclipse.xxd.converter.supplier.mzml.model.v110.MzMLType;
import org.eclipse.chemclipse.xxd.converter.supplier.mzml.model.v110.ScanType;
import org.eclipse.chemclipse.xxd.converter.supplier.mzml.model.v110.SpectrumType;
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
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

public class ChromatogramReader extends AbstractChromatogramReader implements IChromatogramMSDReader {

	private static final Logger logger = Logger.getLogger(ChromatogramReader.class);

	private static final String SPECTRUM_LIST = "spectrumList";
	private static final String SPECTRUM = "spectrum";
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
		 * hence stream it from the HDF5 dataset and unmarshal spectrum by spectrum.
		 */
		try (InputStream inputStream = new MzMLbInputStream(file, "mzML")) {
			XMLStreamReader reader = XmlParserFactory.createInputFactory().createXMLStreamReader(inputStream);
			try {
				Unmarshaller unmarshaller = JAXBContext.newInstance(MzMLType.class).createUnmarshaller();
				chromatogram = new VendorChromatogram();
				chromatogram.setFile(file);
				IReaderProxy readerProxy = new ReaderProxy(file);
				SubMonitor subMonitor = null; // created once the number of spectra is known
				while(reader.hasNext()) {
					if(monitor.isCanceled()) {
						break;
					}
					if(reader.isStartElement()) {
						String localName = reader.getLocalName();
						if(SPECTRUM_LIST.equals(localName)) {
							subMonitor = SubMonitor.convert(monitor, ConverterMessages.readScans, getCount(reader));
						} else if(SPECTRUM.equals(localName)) {
							// leaves the reader on the event following </spectrum>
							SpectrumType spectrum = unmarshaller.unmarshal(reader, SpectrumType.class).getValue();
							chromatogram.addScan(readSpectrum(spectrum, readerProxy, monitor));
							if(subMonitor != null) {
								subMonitor.worked(1);
							}
							continue;
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
		} catch(JAXBException e) {
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

	private IVendorScanProxy readSpectrum(SpectrumType spectrum, IReaderProxy readerProxy, IProgressMonitor monitor) {

		IVendorScanProxy scanProxy = new VendorScanProxy(readerProxy, monitor);
		scanProxy.setScanNumber(spectrum.getIndex().intValue());
		scanProxy.setIdentifier(spectrum.getId());
		readSpectrumParameters(spectrum, scanProxy);
		readScan(spectrum, scanProxy);
		IScanMarker scanMarker = readBinaryData(spectrum, readerProxy);
		scanProxy.setScanMarker(scanMarker);
		return scanProxy;
	}

	private void readSpectrumParameters(SpectrumType spectrum, IVendorScanProxy scanProxy) {

		for(CVParamType cvParamSpectrum : spectrum.getCvParam()) {
			if(cvParamSpectrum.getAccession().equals("MS:1000285") && cvParamSpectrum.getName().equals("total ion current")) {
				scanProxy.setTotalSignal(Float.parseFloat(cvParamSpectrum.getValue()));
			}
			if(cvParamSpectrum.getAccession().equals("MS:1000511") && cvParamSpectrum.getName().equals("ms level")) {
				scanProxy.setMassSpectrometer(Short.parseShort(cvParamSpectrum.getValue()));
			}
			if(cvParamSpectrum.getAccession().equals("MS:1000129") && cvParamSpectrum.getName().equals("negative scan")) {
				scanProxy.setPolarity(Polarity.NEGATIVE);
			} else if(cvParamSpectrum.getAccession().equals("MS:1000130") && cvParamSpectrum.getName().equals("positive scan")) {
				scanProxy.setPolarity(Polarity.POSITIVE);
			}
		}
	}

	private void readScan(SpectrumType spectrum, IVendorScanProxy scanProxy) {

		for(ScanType scan : spectrum.getScanList().getScan()) {
			for(CVParamType cvParamScan : scan.getCvParam()) {
				if(cvParamScan.getAccession().equals("MS:1000016") && cvParamScan.getName().equals("scan start time")) {
					float multiplicator = XmlReader110.getTimeMultiplicator(cvParamScan);
					int retentionTime = Math.round(Float.parseFloat(cvParamScan.getValue()) * multiplicator);
					scanProxy.setRetentionTime(retentionTime);
				}
			}
		}
	}

	private IScanMarker readBinaryData(SpectrumType spectrum, IReaderProxy scanReaderProxy) {

		IScanMarker scanMarker = new ScanMarker();
		for(BinaryDataArrayType binaryDataArray : spectrum.getBinaryDataArrayList().getBinaryDataArray()) {
			String dataSet = "";
			boolean isMz = false;
			boolean isIntensity = false;
			for(CVParamType cvParamBinary : binaryDataArray.getCvParam()) {
				if(cvParamBinary.getAccession().equals("MS:1002841") && cvParamBinary.getName().equals("external HDF5 dataset")) {
					dataSet = cvParamBinary.getValue();
				}
				if(cvParamBinary.getAccession().equals("MS:1002842") && cvParamBinary.getName().equals("external offset")) {
					scanMarker.setOffset(Integer.parseInt(cvParamBinary.getValue()));
				}
				if(cvParamBinary.getAccession().equals("MS:1002843") && cvParamBinary.getName().equals("external array length")) {
					scanMarker.setLength(Integer.parseInt(cvParamBinary.getValue()));
				}
				if(cvParamBinary.getAccession().equals("MS:1000514") && cvParamBinary.getName().equals("m/z array")) {
					isMz = true;
				}
				if(cvParamBinary.getAccession().equals("MS:1000515") && cvParamBinary.getName().equals("intensity array")) {
					isIntensity = true;
				}
			}
			if(isMz) {
				scanReaderProxy.setMzDataset(dataSet);
			} else if(isIntensity) {
				scanReaderProxy.setIntensityDataset(dataSet);
			}
		}
		return scanMarker;
	}
}

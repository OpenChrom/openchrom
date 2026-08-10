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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.chemclipse.converter.io.AbstractChromatogramReader;
import org.eclipse.chemclipse.converter.l10n.ConverterMessages;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.msd.converter.io.IChromatogramMSDReader;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.Polarity;
import org.eclipse.chemclipse.xxd.converter.supplier.mzml.io.XmlReader110;
import org.eclipse.chemclipse.xxd.converter.supplier.mzml.model.v110.BinaryDataArrayType;
import org.eclipse.chemclipse.xxd.converter.supplier.mzml.model.v110.CVParamType;
import org.eclipse.chemclipse.xxd.converter.supplier.mzml.model.v110.MzMLType;
import org.eclipse.chemclipse.xxd.converter.supplier.mzml.model.v110.RunType;
import org.eclipse.chemclipse.xxd.converter.supplier.mzml.model.v110.ScanType;
import org.eclipse.chemclipse.xxd.converter.supplier.mzml.model.v110.SpectrumListType;
import org.eclipse.chemclipse.xxd.converter.supplier.mzml.model.v110.SpectrumType;
import org.eclipse.core.runtime.IProgressMonitor;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.openchrom.msd.converter.supplier.mzmlb.internal.io.ReaderProxy;
import net.openchrom.msd.converter.supplier.mzmlb.io.support.IScanMarker;
import net.openchrom.msd.converter.supplier.mzmlb.io.support.ScanMarker;
import net.openchrom.msd.converter.supplier.mzmlb.model.IVendorChromatogram;
import net.openchrom.msd.converter.supplier.mzmlb.model.IVendorScanProxy;
import net.openchrom.msd.converter.supplier.mzmlb.model.VendorChromatogram;
import net.openchrom.msd.converter.supplier.mzmlb.model.VendorScanProxy;

import ch.systemsx.cisd.hdf5.HDF5Factory;
import ch.systemsx.cisd.hdf5.IHDF5SimpleReader;
import hdf.hdf5lib.exceptions.HDF5LibraryException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

public class ChromatogramReader extends AbstractChromatogramReader implements IChromatogramMSDReader {

	private static final Logger logger = Logger.getLogger(ChromatogramReader.class);

	@Override
	public IChromatogramOverview readOverview(File file, IProgressMonitor monitor) throws IOException {

		return null;
	}

	@Override
	public IChromatogramMSD read(File file, IProgressMonitor monitor) throws IOException {

		IVendorChromatogram chromatogram = null;

		try (IHDF5SimpleReader reader = HDF5Factory.openForReading(file)) {
			byte[] xml = reader.readAsByteArray("mzML");

			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
			documentBuilderFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
			documentBuilderFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
			documentBuilderFactory.setNamespaceAware(true);

			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

			InputStream inputStream = new ByteArrayInputStream(xml);
			Document document = documentBuilder.parse(inputStream);
			NodeList topNode = document.getElementsByTagName("mzML");

			JAXBContext jaxbContext = JAXBContext.newInstance(MzMLType.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			MzMLType mzML = (MzMLType)unmarshaller.unmarshal(topNode.item(0));

			chromatogram = new VendorChromatogram();
			chromatogram.setFile(file);

			RunType run = mzML.getRun();
			SpectrumListType spectrumList = run.getSpectrumList();
			IReaderProxy readerProxy = new ReaderProxy(file);

			monitor.beginTask(ConverterMessages.readScans, spectrumList.getCount().intValue());
			for(SpectrumType spectrum : spectrumList.getSpectrum()) {
				IVendorScanProxy scanProxy = new VendorScanProxy(readerProxy, monitor);
				scanProxy.setScanNumber(spectrum.getIndex().intValue());
				scanProxy.setIdentifier(spectrum.getId());

				readSpectrumParameters(spectrum, scanProxy);
				readScan(spectrum, scanProxy);

				IScanMarker scanMarker = readBinaryData(spectrum, readerProxy);
				scanProxy.setScanMarker(scanMarker);
				chromatogram.addScan(scanProxy);
				monitor.worked(1);
			}
		} catch(HDF5LibraryException e) {
			logger.error(e);
		} catch(ParserConfigurationException e) {
			logger.warn(e);
		} catch(SAXException e) {
			logger.warn(e);
		} catch(JAXBException e) {
			logger.warn(e);
		}
		monitor.done();
		return chromatogram;
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

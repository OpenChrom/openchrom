/*******************************************************************************
 * Copyright (c) 2021, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.csd.converter.supplier.gaml.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.chemclipse.converter.exceptions.FileIsEmptyException;
import org.eclipse.chemclipse.converter.exceptions.FileIsNotReadableException;
import org.eclipse.chemclipse.converter.io.AbstractChromatogramReader;
import org.eclipse.chemclipse.csd.converter.io.IChromatogramCSDReader;
import org.eclipse.chemclipse.csd.model.core.IChromatogramCSD;
import org.eclipse.chemclipse.csd.model.core.IChromatogramPeakCSD;
import org.eclipse.chemclipse.csd.model.core.support.PeakBuilderCSD;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.model.identifier.ComparisonResult;
import org.eclipse.chemclipse.model.identifier.IComparisonResult;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.model.identifier.LibraryInformation;
import org.eclipse.chemclipse.model.implementation.IdentificationTarget;
import org.eclipse.chemclipse.model.support.IScanRange;
import org.eclipse.chemclipse.model.support.ScanRange;
import org.eclipse.core.runtime.IProgressMonitor;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.openchrom.csd.converter.supplier.gaml.model.IVendorChromatogram;
import net.openchrom.csd.converter.supplier.gaml.model.VendorChromatogram;
import net.openchrom.csd.converter.supplier.gaml.model.VendorScan;
import net.openchrom.xxd.converter.supplier.gaml.internal.io.IConstants;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.Experiment;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.GAML;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.ObjectFactory;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.Parameter;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.Peaktable;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.Peaktable.Peak;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.Peaktable.Peak.Baseline;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.Technique;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.Trace;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.Units;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.Xdata;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.Ydata;
import net.openchrom.xxd.converter.supplier.gaml.io.Reader120;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

public class ChromatogramReaderVersion120 extends AbstractChromatogramReader implements IChromatogramCSDReader {

	private static final Logger logger = Logger.getLogger(ChromatogramReaderVersion100.class);

	public ChromatogramReaderVersion120() {

	}

	@Override
	public IChromatogramCSD read(File file, IProgressMonitor monitor) throws IOException {

		List<VendorChromatogram> chromatograms = getChromatograms(file);
		if(chromatograms.isEmpty()) {
			return null;
		}
		VendorChromatogram chromatogram = chromatograms.get(0);
		chromatograms.stream().skip(1).forEach(chromatogram::addReferencedChromatogram);
		return chromatogram;
	}

	List<VendorChromatogram> getChromatograms(File file) {

		List<VendorChromatogram> chromatograms = new ArrayList<>();
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			NodeList nodeList = document.getElementsByTagName(IConstants.NODE_GAML);
			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			GAML gaml = (GAML)unmarshaller.unmarshal(nodeList.item(0));
			for(Experiment experiment : gaml.getExperiment()) {
				VendorChromatogram chromatogram = new VendorChromatogram();
				chromatogram.setDataName(experiment.getName());
				XMLGregorianCalendar collectDate = experiment.getCollectdate();
				if(collectDate != null) {
					chromatogram.setDate(collectDate.toGregorianCalendar().getTime());
				}
				chromatogram.setConverterId("");
				chromatogram.setFile(file);
				for(Parameter parameter : experiment.getParameter()) {
					if(parameter.getName().equals("limsID")) {
						chromatogram.setBarcode(parameter.getValue());
					}
				}
				for(Trace trace : experiment.getTrace()) {
					double[] retentionTimes = null;
					double[] intensities = null;
					if(trace.getTechnique() == Technique.CHROM) {
						for(Xdata xdata : trace.getXdata()) {
							Units unit = xdata.getUnits();
							retentionTimes = Reader120.parseValues(xdata.getValues());
							Ydata ydata = xdata.getYdata().get(0);
							intensities = Reader120.parseValues(ydata.getValues());
							int scans = Math.min(retentionTimes.length, intensities.length);
							for(int i = 0; i < scans; i++) {
								VendorScan scan = new VendorScan((float)intensities[i]);
								scan.setRetentionTime(Reader120.convertToMiliSeconds(retentionTimes[i], unit));
								chromatogram.addScan(scan);
							}
							for(Peaktable peakTable : ydata.getPeaktable()) {
								for(Peak peak : peakTable.getPeak()) {
									ILibraryInformation libraryInformation = new LibraryInformation();
									String name = peak.getName() != null ? peak.getName() : peak.getNumber().toString();
									libraryInformation.setName(name);
									IComparisonResult comparisonResult = ComparisonResult.COMPARISON_RESULT_BEST_MATCH;
									IIdentificationTarget identificationTarget = new IdentificationTarget(libraryInformation, comparisonResult);
									Baseline baseline = peak.getBaseline();
									if(baseline != null) {
										int startScan = chromatogram.getScanNumber((float)baseline.getStartXvalue());
										int stopScan = chromatogram.getScanNumber((float)baseline.getEndXvalue());
										if(startScan != 0 && stopScan != 0) {
											IScanRange scanRange = new ScanRange(startScan, stopScan);
											try {
												IChromatogramPeakCSD chromatogramPeak = PeakBuilderCSD.createPeak(chromatogram, scanRange, true);
												chromatogramPeak.getTargets().add(identificationTarget);
												chromatogram.addPeak(chromatogramPeak);
											} catch(Exception e) {
												logger.warn("Peak " + peak.getNumber() + " could not be added.");
											}
										} else {
											float rt = Reader120.convertToMinutes((float)peak.getPeakXvalue(), unit);
											chromatogram.getScan(chromatogram.getScanNumber(rt)).getTargets().add(identificationTarget);
										}
									}
								}
							}
						}
					}
				}
				chromatograms.add(chromatogram);
			}
		} catch(SAXException e) {
			logger.warn(e);
		} catch(JAXBException e) {
			logger.warn(e);
		} catch(ParserConfigurationException e) {
			logger.warn(e);
		} catch(IOException e) {
			logger.warn(e);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return chromatograms;
	}

	@Override
	public IChromatogramOverview readOverview(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		IVendorChromatogram chromatogram = null;
		//
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			NodeList nodeList = document.getElementsByTagName(IConstants.NODE_GAML);
			//
			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			GAML gaml = (GAML)unmarshaller.unmarshal(nodeList.item(0));
			//
			chromatogram = new VendorChromatogram();
			//
			Experiment experiment = gaml.getExperiment().get(0);
			chromatogram.setDataName(experiment.getName());
			chromatogram.setDate(experiment.getCollectdate().toGregorianCalendar().getTime());
		} catch(SAXException e) {
			logger.warn(e);
		} catch(JAXBException e) {
			logger.warn(e);
		} catch(ParserConfigurationException e) {
			logger.warn(e);
		}
		return chromatogram;
	}
}
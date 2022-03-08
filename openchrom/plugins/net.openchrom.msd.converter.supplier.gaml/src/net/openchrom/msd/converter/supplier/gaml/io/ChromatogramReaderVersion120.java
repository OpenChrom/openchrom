/*******************************************************************************
 * Copyright (c) 2021, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.gaml.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.chemclipse.converter.io.AbstractChromatogramReader;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.model.exceptions.AbundanceLimitExceededException;
import org.eclipse.chemclipse.msd.converter.io.IChromatogramMSDReader;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.exceptions.IonLimitExceededException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import net.openchrom.msd.converter.supplier.gaml.model.IVendorChromatogram;
import net.openchrom.msd.converter.supplier.gaml.model.IVendorIon;
import net.openchrom.msd.converter.supplier.gaml.model.IVendorScan;
import net.openchrom.msd.converter.supplier.gaml.model.VendorChromatogram;
import net.openchrom.msd.converter.supplier.gaml.model.VendorIon;
import net.openchrom.msd.converter.supplier.gaml.model.VendorScan;
import net.openchrom.xxd.converter.supplier.gaml.internal.io.IConstants;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.Coordinates;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.Experiment;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.GAML;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.Link;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.ObjectFactory;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.Parameter;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.Technique;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.Trace;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.Units;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.Xdata;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.Ydata;
import net.openchrom.xxd.converter.supplier.gaml.io.Reader120;

public class ChromatogramReaderVersion120 extends AbstractChromatogramReader implements IChromatogramMSDReader {

	private static final Logger logger = Logger.getLogger(ChromatogramReaderVersion120.class);

	public ChromatogramReaderVersion120() {

	}

	@Override
	public IChromatogramMSD read(File file, IProgressMonitor monitor) throws IOException {

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
				Map<Object, List<double[]>> linkedMassUnits = new LinkedHashMap<>();
				Map<Object, List<double[]>> linkedIntensities = new LinkedHashMap<>();
				Map<Object, double[]> linkedRetentionTimes = new HashMap<>();
				Map<Object, Units> linkedUnits = new HashMap<>();
				for(Trace trace : experiment.getTrace()) {
					chromatogram.setShortInfo(trace.getName());
					List<double[]> listedMassUnits = new ArrayList<>();
					List<double[]> listedIntensities = new ArrayList<>();
					if(trace.getTechnique() == Technique.CHROM) {
						for(Xdata xdata : trace.getXdata()) {
							Units unit = xdata.getUnits();
							double[] retentionTimes = Reader120.parseValues(xdata.getValues());
							for(Link link : xdata.getLink()) {
								linkedRetentionTimes.put(link.getLinkref(), retentionTimes);
								linkedUnits.put(link.getLinkref(), unit);
							}
						}
					}
					if(trace.getTechnique() == Technique.MS) {
						for(Xdata xdata : trace.getXdata()) {
							Units unit = xdata.getUnits();
							if(unit == Units.ATOMICMASSUNITS || unit == Units.MASSCHARGERATIO) {
								double[] mzs = Reader120.parseValues(xdata.getValues());
								listedMassUnits.add(mzs);
								Ydata ydata = xdata.getYdata().get(0);
								double[] intensities = Reader120.parseValues(ydata.getValues());
								listedIntensities.add(intensities);
							}
						}
					}
					for(Coordinates coordinate : trace.getCoordinates()) {
						linkedMassUnits.put(coordinate, listedMassUnits);
						linkedIntensities.put(coordinate, listedIntensities);
					}
				}
				Object link = linkedMassUnits.keySet().iterator().next();
				double[] retentionTimes = linkedRetentionTimes.get(link);
				List<double[]> massUnits = linkedMassUnits.get(link);
				List<double[]> intensities = linkedIntensities.get(link);
				Units unit = linkedUnits.get(link);
				for(int l = 0; l < massUnits.size(); l++) {
					double[] mzs = massUnits.get(l);
					double[] abundances = intensities.get(l);
					int scans = Math.min(mzs.length, abundances.length);
					IVendorScan massSpectrum = new VendorScan();
					int retentionTime = Reader120.convertToMiliSeconds(retentionTimes[l], unit);
					massSpectrum.setRetentionTime(retentionTime);
					for(int s = 0; s < scans; s++) {
						try {
							float intensity = (float)abundances[s];
							if(intensity >= VendorIon.MIN_ABUNDANCE && intensity <= VendorIon.MAX_ABUNDANCE) {
								IVendorIon ion = new VendorIon(mzs[s], intensity);
								massSpectrum.addIon(ion);
							}
						} catch(AbundanceLimitExceededException e) {
							logger.warn(e);
						} catch(IonLimitExceededException e) {
							logger.warn(e);
						}
					}
					chromatogram.addScan(massSpectrum);
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
		}
		return chromatograms;
	}

	@Override
	public IChromatogramOverview readOverview(File file, IProgressMonitor monitor) throws IOException {

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
			for(Parameter parameter : experiment.getParameter()) {
				if(parameter.getName().equals("limsID")) {
					chromatogram.setBarcode(parameter.getValue());
				}
			}
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

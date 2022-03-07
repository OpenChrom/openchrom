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
package net.openchrom.nmr.converter.supplier.gaml.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IComplexSignalMeasurement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import net.openchrom.nmr.converter.supplier.gaml.model.VendorFIDMeasurement;
import net.openchrom.nmr.converter.supplier.gaml.model.VendorFIDSignal;
import net.openchrom.xxd.converter.supplier.gaml.internal.io.IConstants;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.Experiment;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.GAML;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.ObjectFactory;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.Parameter;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.Trace;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.Xdata;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.Ydata;
import net.openchrom.xxd.converter.supplier.gaml.io.Reader120;

public class ScanReaderVersion120 {

	private static final Logger logger = Logger.getLogger(ScanReaderVersion120.class);

	public Collection<IComplexSignalMeasurement<?>> read(File file, IProgressMonitor monitor) throws IOException {

		List<IComplexSignalMeasurement<?>> measurements = new ArrayList<>();
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			NodeList nodeList = document.getElementsByTagName(IConstants.NODE_GAML);
			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			GAML gaml = (GAML)unmarshaller.unmarshal(nodeList.item(0));
			for(Experiment experiment : gaml.getExperiment()) {
				double[] time = null;
				double[] real = null;
				double[] imaginary = null;
				for(Trace trace : experiment.getTrace()) {
					for(Xdata xdata : trace.getXdata()) {
						time = Reader120.parseValues(xdata.getValues());
						for(Ydata ydata : xdata.getYdata()) {
							if(trace.getName().equals("Real")) {
								real = Reader120.parseValues(ydata.getValues());
							}
							if(trace.getName().equals("Imaginary")) {
								imaginary = Reader120.parseValues(ydata.getValues());
							}
						}
					}
				}
				VendorFIDMeasurement measurement = new VendorFIDMeasurement();
				measurement.setDataName(experiment.getName());
				XMLGregorianCalendar collectDate = experiment.getCollectdate();
				for(Parameter parameter : gaml.getParameter()) {
					if(parameter.getName().equals("SW_h")) {
						measurement.setSpectralWidth(Double.parseDouble(parameter.getValue()));
					}
					if(parameter.getName().equals("BF1")) {
						measurement.setSpectrometerFrequency(Double.parseDouble(parameter.getValue()));
					}
					if(parameter.getName().equals("SFO1")) {
						measurement.setCarrierFrequency(Double.parseDouble(parameter.getValue()));
					}
				}
				if(collectDate != null) {
					measurement.setDate(collectDate.toGregorianCalendar().getTime());
				}
				for(int i = 0; i < time.length; i++) {
					VendorFIDSignal signal = new VendorFIDSignal(time[i], real[i], imaginary[i]);
					measurement.addSignal(signal);
				}
				measurements.add(measurement);
			}
		} catch(IOException e) {
			logger.warn(e);
		} catch(JAXBException e) {
			logger.warn(e);
		} catch(ParserConfigurationException e) {
			logger.warn(e);
		} catch(SAXException e) {
			logger.warn(e);
		}
		if(measurements.isEmpty()) {
			return null;
		}
		return measurements;
	}
}

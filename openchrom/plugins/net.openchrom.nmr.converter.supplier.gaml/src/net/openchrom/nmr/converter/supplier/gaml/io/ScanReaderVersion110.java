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
package net.openchrom.nmr.converter.supplier.gaml.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IComplexSignalMeasurement;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.openchrom.nmr.converter.supplier.gaml.model.VendorFIDMeasurement;
import net.openchrom.nmr.converter.supplier.gaml.model.VendorFIDSignal;
import net.openchrom.xxd.converter.supplier.gaml.io.Reader;
import net.openchrom.xxd.converter.supplier.gaml.io.Reader110;
import net.openchrom.xxd.converter.supplier.gaml.v110.model.Experiment;
import net.openchrom.xxd.converter.supplier.gaml.v110.model.GAML;
import net.openchrom.xxd.converter.supplier.gaml.v110.model.ObjectFactory;
import net.openchrom.xxd.converter.supplier.gaml.v110.model.Parameter;
import net.openchrom.xxd.converter.supplier.gaml.v110.model.Trace;
import net.openchrom.xxd.converter.supplier.gaml.v110.model.Xdata;
import net.openchrom.xxd.converter.supplier.gaml.v110.model.Ydata;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

public class ScanReaderVersion110 {

	private static final Logger logger = Logger.getLogger(ScanReaderVersion110.class);

	public Collection<IComplexSignalMeasurement<?>> read(File file) throws IOException {

		List<IComplexSignalMeasurement<?>> measurements = new ArrayList<>();
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
			documentBuilderFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
			documentBuilderFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			NodeList nodeList = document.getElementsByTagName(Reader.NODE_GAML);

			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			GAML gaml = (GAML)unmarshaller.unmarshal(nodeList.item(0));
			for(Experiment experiment : gaml.getExperiment()) {
				double[] time = null;
				double[] real = null;
				double[] imaginary = null;
				for(Trace trace : experiment.getTrace()) {
					for(Xdata xdata : trace.getXdata()) {
						time = Reader110.parseValues(xdata.getValues());
						for(Ydata ydata : xdata.getYdata()) {
							if(trace.getName().equals("Real")) {
								real = Reader110.parseValues(ydata.getValues());
							}
							if(trace.getName().equals("Imaginary")) {
								imaginary = Reader110.parseValues(ydata.getValues());
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

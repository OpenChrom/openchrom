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
package net.openchrom.fsd.converter.supplier.gaml.io;

import java.io.File;
import java.io.IOException;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.chemclipse.fsd.model.core.implementation.SignalFSD;
import org.eclipse.chemclipse.logging.core.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.openchrom.fsd.converter.supplier.gaml.model.IVendorSpectrumFSD;
import net.openchrom.fsd.converter.supplier.gaml.model.VendorSpectrumFSD;
import net.openchrom.xxd.converter.supplier.gaml.io.Reader;
import net.openchrom.xxd.converter.supplier.gaml.io.Reader110;
import net.openchrom.xxd.converter.supplier.gaml.v110.model.Coordinates;
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

	public IVendorSpectrumFSD read(File file) throws IOException {

		IVendorSpectrumFSD vendorScan = null;
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			NodeList nodeList = document.getElementsByTagName(Reader.NODE_GAML);
			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			GAML gaml = (GAML)unmarshaller.unmarshal(nodeList.item(0));
			for(Experiment experiment : gaml.getExperiment()) {
				vendorScan = new VendorSpectrumFSD();
				vendorScan.setDataName(experiment.getName());
				XMLGregorianCalendar collectDate = experiment.getCollectdate();
				if(collectDate != null) {
					vendorScan.setDate(collectDate.toGregorianCalendar().getTime());
				}
				for(Parameter parameter : experiment.getParameter()) {
					if(parameter.getName().equals("FileID")) {
						vendorScan.setShortInfo(parameter.getValue());
					}
				}
				for(Trace trace : experiment.getTrace()) {
					for(Coordinates coordinates : trace.getCoordinates()) {
						double[] values = Reader110.parseValues(coordinates.getValues());
						for(int i = 0; i < values.length; i++) {
							vendorScan.getExcitation().add(new SignalFSD((float)values[i], 0));
						}
					}
					double[] x = null;
					double[] y = null;
					for(Xdata xdata : trace.getXdata()) {
						x = Reader110.parseValues(xdata.getValues());
						for(Ydata ydata : xdata.getYdata()) {
							y = Reader110.parseValues(ydata.getValues());
						}
					}
					if(x != null && y != null) {
						int scans = Math.min(x.length, y.length);
						for(int i = 0; i < scans; i++) {
							vendorScan.getEmission().add(new SignalFSD((float)x[i], y[i]));
						}
					}
				}
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
		return vendorScan;
	}
}
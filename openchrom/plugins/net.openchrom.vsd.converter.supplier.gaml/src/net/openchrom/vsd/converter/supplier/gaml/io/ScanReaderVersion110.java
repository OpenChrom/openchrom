/*******************************************************************************
 * Copyright (c) 2021, 2025 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Philip Wenig - refactoring vibrational spectroscopy
 *******************************************************************************/
package net.openchrom.vsd.converter.supplier.gaml.io;

import java.io.File;
import java.io.IOException;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.vsd.model.implementation.SignalInfrared;
import org.eclipse.chemclipse.vsd.model.implementation.SignalRaman;
import org.eclipse.core.runtime.IProgressMonitor;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.openchrom.vsd.converter.supplier.gaml.model.IVendorSpectrumVSD;
import net.openchrom.vsd.converter.supplier.gaml.model.VendorSpectrumVSD;
import net.openchrom.xxd.converter.supplier.gaml.io.Reader;
import net.openchrom.xxd.converter.supplier.gaml.io.Reader110;
import net.openchrom.xxd.converter.supplier.gaml.v110.model.Experiment;
import net.openchrom.xxd.converter.supplier.gaml.v110.model.GAML;
import net.openchrom.xxd.converter.supplier.gaml.v110.model.ObjectFactory;
import net.openchrom.xxd.converter.supplier.gaml.v110.model.Parameter;
import net.openchrom.xxd.converter.supplier.gaml.v110.model.Technique;
import net.openchrom.xxd.converter.supplier.gaml.v110.model.Trace;
import net.openchrom.xxd.converter.supplier.gaml.v110.model.Xdata;
import net.openchrom.xxd.converter.supplier.gaml.v110.model.Ydata;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

public class ScanReaderVersion110 {

	private static final Logger logger = Logger.getLogger(ScanReaderVersion110.class);

	public IVendorSpectrumVSD read(File file, IProgressMonitor monitor) throws IOException {

		IVendorSpectrumVSD vendorScan = null;
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			NodeList nodeList = document.getElementsByTagName(Reader.NODE_GAML);
			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			GAML gaml = (GAML)unmarshaller.unmarshal(nodeList.item(0));
			for(Experiment experiment : gaml.getExperiment()) {
				vendorScan = new VendorSpectrumVSD();
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
					double[] x = null;
					double[] y = null;
					for(Xdata xdata : trace.getXdata()) {
						x = Reader110.parseValues(xdata.getValues());
						for(Ydata ydata : xdata.getYdata()) {
							y = Reader110.parseValues(ydata.getValues());
						}
					}
					int scans = Math.min(x.length, y.length);
					for(int i = 0; i < scans; i++) {
						if(trace.getTechnique() == Technique.IR || trace.getTechnique() == Technique.NIR) {
							vendorScan.getScanVSD().getProcessedSignals().add(new SignalInfrared(x[i], y[i], 0));
						} else if(trace.getTechnique() == Technique.RAMAN) {
							vendorScan.getScanVSD().getProcessedSignals().add(new SignalRaman(x[i], y[i]));
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
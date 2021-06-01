/*******************************************************************************
 * Copyright (c) 2021 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.xir.converter.supplier.gaml.io;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.xir.model.core.SignalXIR;
import org.eclipse.core.runtime.IProgressMonitor;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.openchrom.xir.converter.supplier.gaml.model.IVendorScanXIR;
import net.openchrom.xir.converter.supplier.gaml.model.VendorScanXIR;
import net.openchrom.xxd.converter.supplier.gaml.internal.io.IConstants;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.Experiment;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.GAML;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.Parameter;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.Trace;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.Xdata;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.Ydata;
import net.openchrom.xxd.converter.supplier.gaml.io.Reader120;

public class ScanReaderVersion120 {

	private static final Logger logger = Logger.getLogger(ScanReaderVersion120.class);

	public IVendorScanXIR read(File file, IProgressMonitor monitor) throws IOException {

		IVendorScanXIR vendorScan = null;
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			NodeList nodeList = document.getElementsByTagName(IConstants.NODE_GAML);
			JAXBContext jaxbContext = JAXBContext.newInstance(IConstants.CONTEXT_PATH_V_120);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			GAML gaml = (GAML)unmarshaller.unmarshal(nodeList.item(0));
			for(Experiment experiment : gaml.getExperiment()) {
				vendorScan = new VendorScanXIR();
				vendorScan.setDataName(experiment.getName());
				XMLGregorianCalendar collectDate = experiment.getCollectdate();
				if(collectDate != null)
					vendorScan.setDate(collectDate.toGregorianCalendar().getTime());
				for(Parameter parameter : experiment.getParameter()) {
					if(parameter.getName().equals("FileID"))
						vendorScan.setShortInfo(parameter.getValue());
				}
				for(Trace trace : experiment.getTrace()) {
					double[] waveNumbers = null;
					double[] absorbance = null;
					for(Xdata xdata : trace.getXdata()) {
						waveNumbers = Reader120.parseValues(xdata.getValues());
						for(Ydata ydata : xdata.getYdata()) {
							absorbance = Reader120.parseValues(ydata.getValues());
						}
					}
					int scans = Math.min(waveNumbers.length, absorbance.length);
					for(int i = 0; i < scans; i++) {
						vendorScan.getProcessedSignals().add(new SignalXIR(waveNumbers[i], absorbance[i]));
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

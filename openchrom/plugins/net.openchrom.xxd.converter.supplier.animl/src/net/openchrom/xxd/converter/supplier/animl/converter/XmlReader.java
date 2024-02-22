/*******************************************************************************
 * Copyright (c) 2021, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.converter.supplier.animl.converter;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.chemclipse.support.history.EditHistory;
import org.eclipse.chemclipse.support.history.EditInformation;
import org.eclipse.chemclipse.support.history.IEditHistory;
import org.eclipse.chemclipse.support.history.IEditInformation;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.openchrom.xxd.converter.supplier.animl.model.astm.core.AnIMLType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.AuditTrailEntryType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.ObjectFactory;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.UnitType;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

public class XmlReader {

	public static AnIMLType getAnIML(File file) throws SAXException, IOException, JAXBException, ParserConfigurationException {

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(file);
		NodeList topNode = document.getElementsByTagName(AnIMLType.NODE_NAME);
		//
		JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		return (AnIMLType)unmarshaller.unmarshal(topNode.item(0));
	}

	public static int getTimeMultiplicator(UnitType unit) {

		int multiplicator = 1;
		if(unit.getLabel().equals("ms")) {
			multiplicator = 1;
		}
		if(unit.getLabel().equals("s")) {
			multiplicator = 1000;
		}
		if(unit.getLabel().equals("min")) {
			multiplicator = 60 * 1000;
		}
		return multiplicator;
	}

	public static Collection<IEditInformation> readAuditTrail(AnIMLType animl) {

		IEditHistory editHistory = new EditHistory();
		for(AuditTrailEntryType entry : animl.getAuditTrailEntrySet().getAuditTrailEntry()) {
			Date date = entry.getTimestamp().toGregorianCalendar().getTime();
			String description = entry.getAction().value();
			if(entry.getReason() != null && !entry.getReason().isEmpty()) {
				description += ", " + entry.getReason();
			}
			editHistory.add(new EditInformation(date, description, entry.getAuthor().getName()));
		}
		return editHistory;
	}
}

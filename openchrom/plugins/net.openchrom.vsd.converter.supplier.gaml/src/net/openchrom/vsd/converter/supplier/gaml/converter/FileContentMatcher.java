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
 * Philip Wenig - refactoring vibrational spectroscopy
 *******************************************************************************/
package net.openchrom.vsd.converter.supplier.gaml.converter;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import org.eclipse.chemclipse.converter.core.AbstractFileContentMatcher;

public class FileContentMatcher extends AbstractFileContentMatcher {

	@Override
	public boolean checkFileFormat(File file) {

		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		xmlInputFactory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
		xmlInputFactory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
		xmlInputFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");

		try (InputStream fileInputStream = new FileInputStream(file)) {
			XMLStreamReader reader = xmlInputFactory.createXMLStreamReader(fileInputStream);

			int events = 0;

			while(reader.hasNext() && events < 1000) {
				int event = reader.next();
				if(event == XMLStreamConstants.START_ELEMENT) {
					String localName = reader.getLocalName();
					if("trace".equals(localName)) {
						String technique = reader.getAttributeValue(null, "technique");
						if(technique != null) {
							if("IR".equalsIgnoreCase(technique.trim())) {
								return true;
							} else if("NIR".equalsIgnoreCase(technique.trim())) {
								return true;
							} else if("RAMAN".equalsIgnoreCase(technique.trim())) {
								return true;
							}
						}
					}
				}
				events++;
			}
		} catch(Exception _) {
			// fail silently
		}
		return false;
	}
}

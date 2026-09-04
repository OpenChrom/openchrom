/*******************************************************************************
 * Copyright (c) 2016, 2026 Lablicate GmbH.
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
package net.openchrom.msd.converter.supplier.animl.converter;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.chemclipse.converter.core.AbstractFileContentMatcher;
import org.eclipse.chemclipse.support.xml.XmlParserFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import net.openchrom.xxd.converter.supplier.animl.model.astm.core.AnIMLType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.TechniqueType;

public class MassSpectrumFileContentMatcher extends AbstractFileContentMatcher {

	@Override
	public boolean checkFileFormat(File file) {

		boolean isValidFormat = false;
		try {
			DocumentBuilderFactory documentBuilderFactory = XmlParserFactory.createDocumentBuilderFactory();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			NodeList root = document.getElementsByTagName(AnIMLType.NODE_NAME);
			if(root.getLength() != 1) {
				return isValidFormat;
			}
			NodeList techniquesList = document.getElementsByTagName(TechniqueType.NODE_NAME);
			int techniques = techniquesList.getLength();
			for(int t = 0; t < techniques; t++) {
				Element element = (Element)techniquesList.item(t);
				if(element.getAttribute("name").equals("Chromatography")) {
					isValidFormat = false;
					return isValidFormat;
				}
				if(element.getAttribute("name").equals("Mass Spectrometry")) {
					isValidFormat = true;
				}
			}
		} catch(Exception e) {
			// fail silently
		}
		return isValidFormat;
	}
}

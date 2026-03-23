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
package net.openchrom.msd.converter.supplier.gaml.converter;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.chemclipse.converter.core.AbstractFileContentMatcher;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import net.openchrom.xxd.converter.supplier.gaml.io.Reader;

public class FileContentMatcher extends AbstractFileContentMatcher {

	@Override
	public boolean checkFileFormat(File file) {

		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);

			NodeList root = document.getElementsByTagName(Reader.NODE_GAML);
			if(root.getLength() != 1) {
				return false;
			}

			NodeList experimentsList = document.getElementsByTagName("experiment");
			for(int e = 0; e < experimentsList.getLength(); e++) {
				Element experimentElement = (Element)experimentsList.item(e);
				NodeList traceList = experimentElement.getElementsByTagName("trace");

				boolean chromatography = false;
				boolean msd = false;

				for(int t = 0; t < traceList.getLength(); t++) {
					Element traceElement = (Element)traceList.item(t);
					String technique = traceElement.getAttribute("technique");
					if(technique == null) {
						continue;
					}
					if("CHROM".equalsIgnoreCase(technique.trim())) {
						chromatography = true;
					} else if("MS".equalsIgnoreCase(technique.trim())) {
						msd = true;
					}
				}

				if(chromatography && msd) {
					return true;
				}
			}
		} catch(Exception ex) {
			// fail silently
		}
		return false;
	}
}

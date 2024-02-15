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
package net.openchrom.wsd.converter.supplier.animl.converter;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FilenameUtils;
import org.eclipse.chemclipse.converter.core.AbstractFileContentMatcher;
import org.eclipse.chemclipse.converter.core.IFileContentMatcher;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import net.openchrom.xxd.converter.supplier.animl.model.astm.core.AnIMLType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.TechniqueType;

public class FileContentMatcher extends AbstractFileContentMatcher implements IFileContentMatcher {

	@Override
	public boolean checkFileFormat(File file) {

		boolean isValidFormat = false;
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
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
				if(isMatchingTechnique(element.getAttribute("uri"))) {
					isValidFormat = true;
				}
			}
		} catch(Exception e) {
			// fail silently
		}
		return isValidFormat;
	}

	// NOTE: everything from name, id even the URI is volatile in this format
	// URI can be:
	// https://github.com/AnIML/techniques/blob/master/uv-vis.atdd (no tagged releases)
	// http://techniques.animl.org/uv-vis/current/uv-vis.atdd (offline)
	// so we match the file name instead.
	public static boolean isMatchingTechnique(String uri) {

		String filename = FilenameUtils.getName(uri);
		return filename.equals("uv-vis-trace.atdd");
	}
}

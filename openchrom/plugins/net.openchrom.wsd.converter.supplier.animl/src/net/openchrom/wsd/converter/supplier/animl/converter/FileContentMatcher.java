/*******************************************************************************
 * Copyright (c) 2021, 2023 Lablicate GmbH.
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

import org.eclipse.chemclipse.converter.core.AbstractFileContentMatcher;
import org.eclipse.chemclipse.converter.core.IFileContentMatcher;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import net.openchrom.wsd.converter.supplier.animl.io.ChromatogramReader;
import net.openchrom.xxd.converter.supplier.animl.internal.converter.IConstants;

public class FileContentMatcher extends AbstractFileContentMatcher implements IFileContentMatcher {

	@Override
	public boolean checkFileFormat(File file) {

		boolean isValidFormat = false;
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			NodeList root = document.getElementsByTagName(IConstants.NODE_ANIML);
			if(root.getLength() != 1) {
				return isValidFormat;
			}
			NodeList techniquesList = document.getElementsByTagName(IConstants.NODE_TECHNIQUE);
			int techniques = techniquesList.getLength();
			for(int t = 0; t < techniques; t++) {
				Element element = (Element)techniquesList.item(t);
				if(element.getAttribute("uri").equals(ChromatogramReader.URI)) {
					isValidFormat = true;
				}
			}
		} catch(Exception e) {
			// fail silently
		}
		return isValidFormat;
	}
}

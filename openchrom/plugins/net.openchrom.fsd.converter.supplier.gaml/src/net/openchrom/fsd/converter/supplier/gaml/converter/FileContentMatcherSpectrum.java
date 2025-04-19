/*******************************************************************************
 * Copyright (c) 2025 Lablicate GmbH.
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
package net.openchrom.fsd.converter.supplier.gaml.converter;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.chemclipse.converter.core.AbstractFileContentMatcher;
import org.eclipse.chemclipse.converter.core.IFileContentMatcher;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import net.openchrom.xxd.converter.supplier.gaml.io.Reader;
import net.openchrom.xxd.converter.supplier.gaml.v120.model.GAML;
import net.openchrom.xxd.converter.supplier.gaml.v120.model.ObjectFactory;
import net.openchrom.xxd.converter.supplier.gaml.v120.model.Technique;
import net.openchrom.xxd.converter.supplier.gaml.v120.model.Trace;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

public class FileContentMatcherSpectrum extends AbstractFileContentMatcher implements IFileContentMatcher {

	@Override
	public boolean checkFileFormat(File file) {

		boolean isValidFormat = false;
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			NodeList nodeList = document.getElementsByTagName(Reader.NODE_GAML);
			//
			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			GAML gaml = (GAML)unmarshaller.unmarshal(nodeList.item(0));
			List<Trace> traces = gaml.getExperiment().get(0).getTrace();
			Technique technique = traces.get(0).getTechnique();
			if(technique == Technique.FLUOR) {
				isValidFormat = true;
			}
		} catch(Exception e) {
			// fail silently
		}
		return isValidFormat;
	}
}

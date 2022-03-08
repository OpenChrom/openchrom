/*******************************************************************************
 * Copyright (c) 2021, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.xir.converter.supplier.gaml.converter;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.chemclipse.converter.core.AbstractMagicNumberMatcher;
import org.eclipse.chemclipse.converter.core.IMagicNumberMatcher;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import net.openchrom.xxd.converter.supplier.gaml.internal.io.IConstants;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.GAML;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.ObjectFactory;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.Technique;
import net.openchrom.xxd.converter.supplier.gaml.internal.v120.model.Trace;

public class MagicNumberMatcher extends AbstractMagicNumberMatcher implements IMagicNumberMatcher {

	@Override
	public boolean checkFileFormat(File file) {

		boolean isValidFormat = false;
		if(!file.exists()) {
			return isValidFormat;
		}
		if(!checkFileExtension(file, ".gaml")) {
			return isValidFormat;
		}
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			NodeList nodeList = document.getElementsByTagName(IConstants.NODE_GAML);
			//
			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			GAML gaml = (GAML)unmarshaller.unmarshal(nodeList.item(0));
			List<Trace> traces = gaml.getExperiment().get(0).getTrace();
			Technique technique = traces.get(0).getTechnique();
			if(technique == Technique.IR || technique == Technique.NIR) {
				isValidFormat = true;
			}
		} catch(Exception e) {
			// fail silently
		}
		return isValidFormat;
	}
}

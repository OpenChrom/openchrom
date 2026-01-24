/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.installer.ui;

import java.io.File;
import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PluginFileExtractor {

	public String extract(File file) {

		StringBuilder builder = new StringBuilder();
		if(file != null && file.exists()) {
			try {
				DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
				documentBuilderFactory.setNamespaceAware(false);
				DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
				try (FileInputStream fileInputStream = new FileInputStream(file)) {
					Document document = documentBuilder.parse(fileInputStream);
					NodeList nodeList = document.getElementsByTagName("pluginDescriptor");
					for(int i = 0; i < nodeList.getLength(); i++) {
						Node node = nodeList.item(i);
						if(node instanceof Element element) {
							String name = validate(element.getAttribute("name"));
							String description = validate(element.getAttribute("description"));
							String id = validate(element.getAttribute("id"));
							builder.append(name);
							builder.append("\t");
							builder.append(description);
							builder.append("\t");
							builder.append(id);
							builder.append("\n");
						}
					}
				}
			} catch(Exception e) {
			}
		}

		return builder.toString();
	}

	private String validate(String value) {

		if(value == null) {
			return "";
		} else {
			return value.replaceAll("[\\t\\r\\n]+", " ").trim();
		}
	}
}
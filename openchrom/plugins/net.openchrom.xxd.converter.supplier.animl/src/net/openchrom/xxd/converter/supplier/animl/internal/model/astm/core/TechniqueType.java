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
package net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Reference to Technique Definition used in this Experiment.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TechniqueType", propOrder = {"extension"})
public class TechniqueType {

	@XmlElement(name = "Extension")
	protected List<ExtensionType> extension;
	@XmlAttribute(name = "uri", required = true)
	@XmlSchemaType(name = "anyURI")
	protected String uri;
	@XmlAttribute(name = "sha256")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "token")
	protected String sha256;
	@XmlAttribute(name = "name", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String name;
	@XmlAttribute(name = "id")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String id;

	public List<ExtensionType> getExtension() {

		if(extension == null) {
			extension = new ArrayList<ExtensionType>();
		}
		return this.extension;
	}

	public String getUri() {

		return uri;
	}

	public void setUri(String value) {

		this.uri = value;
	}

	public String getSha256() {

		return sha256;
	}

	public void setSha256(String value) {

		this.sha256 = value;
	}

	public String getName() {

		return name;
	}

	public void setName(String value) {

		this.name = value;
	}

	public String getId() {

		return id;
	}

	public void setId(String value) {

		this.id = value;
	}
}

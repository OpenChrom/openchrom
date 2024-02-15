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
package net.openchrom.xxd.converter.supplier.animl.model.astm.technique;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Literature reference cited from within the Documentation element.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LiteratureReferenceType", propOrder = {"value"})
public class LiteratureReferenceType {

	@XmlValue
	protected String value;
	@XmlAttribute(name = "literatureReferenceID")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String literatureReferenceID;
	@XmlAttribute(name = "uri")
	@XmlSchemaType(name = "anyURI")
	protected String uri;

	public String getValue() {

		return value;
	}

	public void setValue(String value) {

		this.value = value;
	}

	public String getLiteratureReferenceID() {

		return literatureReferenceID;
	}

	public void setLiteratureReferenceID(String value) {

		this.literatureReferenceID = value;
	}

	public String getUri() {

		return uri;
	}

	public void setUri(String value) {

		this.uri = value;
	}
}

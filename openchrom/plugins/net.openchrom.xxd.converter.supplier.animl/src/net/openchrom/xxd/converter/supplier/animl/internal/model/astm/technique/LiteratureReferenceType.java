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
package net.openchrom.xxd.converter.supplier.animl.internal.model.astm.technique;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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

	/**
	 * Ruft den Wert der literatureReferenceID-Eigenschaft ab.
	 *
	 * @return
	 *         possible object is
	 *         {@link String }
	 *
	 */
	public String getLiteratureReferenceID() {

		return literatureReferenceID;
	}

	/**
	 * Legt den Wert der literatureReferenceID-Eigenschaft fest.
	 *
	 * @param value
	 *            allowed object is
	 *            {@link String }
	 *
	 */
	public void setLiteratureReferenceID(String value) {

		this.literatureReferenceID = value;
	}

	/**
	 * Ruft den Wert der uri-Eigenschaft ab.
	 *
	 * @return
	 *         possible object is
	 *         {@link String }
	 *
	 */
	public String getUri() {

		return uri;
	}

	/**
	 * Legt den Wert der uri-Eigenschaft fest.
	 *
	 * @param value
	 *            allowed object is
	 *            {@link String }
	 *
	 */
	public void setUri(String value) {

		this.uri = value;
	}
}

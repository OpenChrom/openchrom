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
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Description of the enclosing element.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentationType", propOrder = {"value"})
public class DocumentationType {

	@XmlValue
	protected String value;
	@XmlAttribute(name = "literatureReferenceID")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String literatureReferenceID;
	@XmlAttribute(name = "literatureAccession")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String literatureAccession;

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

	public String getLiteratureAccession() {

		return literatureAccession;
	}

	public void setLiteratureAccession(String value) {

		this.literatureAccession = value;
	}
}

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
package net.openchrom.xxd.converter.supplier.animl.internal.model.w3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReferenceType", propOrder = {"transforms", "digestMethod", "digestValue"})
public class ReferenceType {

	@XmlElement(name = "Transforms")
	protected TransformsType transforms;
	@XmlElement(name = "DigestMethod", required = true)
	protected DigestMethodType digestMethod;
	@XmlElement(name = "DigestValue", required = true)
	protected byte[] digestValue;
	@XmlAttribute(name = "Id")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String id;
	@XmlAttribute(name = "URI")
	@XmlSchemaType(name = "anyURI")
	protected String uri;
	@XmlAttribute(name = "Type")
	@XmlSchemaType(name = "anyURI")
	protected String type;

	public TransformsType getTransforms() {

		return transforms;
	}

	public void setTransforms(TransformsType value) {

		this.transforms = value;
	}

	public DigestMethodType getDigestMethod() {

		return digestMethod;
	}

	public void setDigestMethod(DigestMethodType value) {

		this.digestMethod = value;
	}

	public byte[] getDigestValue() {

		return digestValue;
	}

	public void setDigestValue(byte[] value) {

		this.digestValue = value;
	}

	public String getId() {

		return id;
	}

	public void setId(String value) {

		this.id = value;
	}

	public String getURI() {

		return uri;
	}

	public void setURI(String value) {

		this.uri = value;
	}

	public String getType() {

		return type;
	}

	public void setType(String value) {

		this.type = value;
	}
}

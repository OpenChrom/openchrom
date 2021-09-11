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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RetrievalMethodType", propOrder = {"transforms"})
public class RetrievalMethodType {

	@XmlElement(name = "Transforms")
	protected TransformsType transforms;
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

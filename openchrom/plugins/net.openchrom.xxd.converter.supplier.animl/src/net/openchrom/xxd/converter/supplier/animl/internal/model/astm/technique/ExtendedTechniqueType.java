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
package net.openchrom.xxd.converter.supplier.animl.internal.model.astm.technique;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Reference to a Technique Definition which can be extended using this Extension.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExtendedTechniqueType")
public class ExtendedTechniqueType {

	@XmlAttribute(name = "name", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String name;
	@XmlAttribute(name = "uri", required = true)
	@XmlSchemaType(name = "anyURI")
	protected String uri;
	@XmlAttribute(name = "sha256")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "token")
	protected String sha256;

	public String getName() {

		return name;
	}

	public void setName(String value) {

		this.name = value;
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
}

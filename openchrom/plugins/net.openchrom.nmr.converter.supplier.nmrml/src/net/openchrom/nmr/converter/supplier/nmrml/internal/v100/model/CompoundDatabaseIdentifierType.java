/*******************************************************************************
 *  Copyright (c) 2021, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.converter.supplier.nmrml.internal.v100.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CompoundDatabaseIdentifierType")
public class CompoundDatabaseIdentifierType {

	@XmlAttribute(name = "identifier", required = true)
	@XmlSchemaType(name = "anySimpleType")
	protected String identifier;
	@XmlAttribute(name = "URI", required = true)
	@XmlSchemaType(name = "anySimpleType")
	protected String uri;

	public String getIdentifier() {

		return identifier;
	}

	public void setIdentifier(String value) {

		this.identifier = value;
	}

	public String getURI() {

		return uri;
	}

	public void setURI(String value) {

		this.uri = value;
	}
}

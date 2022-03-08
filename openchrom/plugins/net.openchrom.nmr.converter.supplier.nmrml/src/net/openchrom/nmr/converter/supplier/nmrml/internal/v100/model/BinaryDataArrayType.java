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

import java.math.BigInteger;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BinaryDataArrayType", propOrder = {"value"})
public class BinaryDataArrayType {

	@XmlValue
	protected byte[] value;
	@XmlAttribute(name = "compressed", required = true)
	protected boolean compressed;
	@XmlAttribute(name = "encodedLength", required = true)
	@XmlSchemaType(name = "nonNegativeInteger")
	protected BigInteger encodedLength;
	@XmlAttribute(name = "byteFormat", required = true)
	protected String byteFormat;

	public byte[] getValue() {

		return value;
	}

	public void setValue(byte[] value) {

		this.value = value;
	}

	public boolean isCompressed() {

		return compressed;
	}

	public void setCompressed(boolean value) {

		this.compressed = value;
	}

	public BigInteger getEncodedLength() {

		return encodedLength;
	}

	public void setEncodedLength(BigInteger value) {

		this.encodedLength = value;
	}

	public String getByteFormat() {

		return byteFormat;
	}

	public void setByteFormat(String value) {

		this.byteFormat = value;
	}
}

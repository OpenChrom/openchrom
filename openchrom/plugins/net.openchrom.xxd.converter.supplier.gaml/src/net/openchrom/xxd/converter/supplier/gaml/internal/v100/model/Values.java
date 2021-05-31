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
package net.openchrom.xxd.converter.supplier.gaml.internal.v100.model;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"value"})
@XmlRootElement(name = "values")
public class Values {

	@XmlValue
	protected byte[] value;
	@XmlAttribute(name = "format", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String format;
	@XmlAttribute(name = "byteorder", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String byteorder;
	@XmlAttribute(name = "numvalues")
	protected BigInteger numvalues;

	public byte[] getValue() {

		return value;
	}

	public void setValue(byte[] value) {

		this.value = value;
	}

	public String getFormat() {

		return format;
	}

	public void setFormat(String value) {

		this.format = value;
	}

	public String getByteorder() {

		return byteorder;
	}

	public void setByteorder(String value) {

		this.byteorder = value;
	}

	public BigInteger getNumvalues() {

		return numvalues;
	}

	public void setNumvalues(BigInteger value) {

		this.numvalues = value;
	}
}

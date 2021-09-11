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

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "ParameterTypeType")
@XmlEnum
public enum ParameterTypeType {

	/**
	 * Represents an individual integer value (32 bits, signed).
	 *
	 */
	@XmlEnumValue("Int32")
	INT_32("Int32"),
	/**
	 * Represents an individual long integer value (64 bits, signed).
	 *
	 */
	@XmlEnumValue("Int64")
	INT_64("Int64"),
	/**
	 * Represents an individual 32-bit floating point value.
	 *
	 */
	@XmlEnumValue("Float32")
	FLOAT_32("Float32"),
	/**
	 * Represents an individual 64-bit floating point value.
	 *
	 */
	@XmlEnumValue("Float64")
	FLOAT_64("Float64"),
	/**
	 * Represents an individual string value.
	 *
	 */
	@XmlEnumValue("String")
	STRING("String"),
	/**
	 * Represents an individual Boolean value.
	 *
	 */
	@XmlEnumValue("Boolean")
	BOOLEAN("Boolean"),
	/**
	 * Represents an individual ISO date/time value.
	 *
	 */
	@XmlEnumValue("DateTime")
	DATE_TIME("DateTime"),
	/**
	 * Represents a Value governed by a different XML Schema..
	 *
	 */
	@XmlEnumValue("EmbeddedXML")
	EMBEDDED_XML("EmbeddedXML"),
	/**
	 * Base 64 encoded PNG image
	 *
	 */
	PNG("PNG"),
	/**
	 * Value governed by the SVG DTD. Used to represent vector graphic images.
	 *
	 */
	SVG("SVG");

	private final String value;

	ParameterTypeType(String v) {

		value = v;
	}

	public String value() {

		return value;
	}

	public static ParameterTypeType fromValue(String v) {

		for(ParameterTypeType c : ParameterTypeType.values()) {
			if(c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}

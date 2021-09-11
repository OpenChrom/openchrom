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

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "AllTypeNameList")
@XmlEnum
public enum AllTypeNameList {

	/**
	 * Single 32-bit or 64-bit signed integer value.
	 *
	 */
	@XmlEnumValue("Int")
	INT("Int"),
	/**
	 * Single 32-bit or 64-bit floating point value.
	 *
	 */
	@XmlEnumValue("Float")
	FLOAT("Float"),
	/**
	 * Single 32-bit or 64-bit integer or floating point value.
	 *
	 */
	@XmlEnumValue("Numeric")
	NUMERIC("Numeric"),
	/**
	 * Single string value.
	 *
	 */
	@XmlEnumValue("String")
	STRING("String"),
	/**
	 * Single Boolean value.
	 *
	 */
	@XmlEnumValue("Boolean")
	BOOLEAN("Boolean"),
	/**
	 * Single ISO date and time Value.
	 *
	 */
	@XmlEnumValue("DateTime")
	DATE_TIME("DateTime"),
	/**
	 * Single XML value governed by a non-AnIML XML schema.
	 *
	 */
	@XmlEnumValue("EmbeddedXML")
	EMBEDDED_XML("EmbeddedXML"),
	/**
	 * Single Base64 binary encoded PNG image.
	 *
	 */
	PNG("PNG"),
	/**
	 * Value governed by the SVG DTD. Used to represent vector graphic images.
	 *
	 */
	SVG("SVG");

	private final String value;

	AllTypeNameList(String v) {

		value = v;
	}

	public String value() {

		return value;
	}

	public static AllTypeNameList fromValue(String v) {

		for(AllTypeNameList c : AllTypeNameList.values()) {
			if(c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}

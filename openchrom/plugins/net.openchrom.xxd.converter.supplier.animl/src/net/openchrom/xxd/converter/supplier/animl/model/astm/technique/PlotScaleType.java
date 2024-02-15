/*******************************************************************************
 * Copyright (c) 2021, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.converter.supplier.animl.model.astm.technique;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "PlotScaleType")
@XmlEnum
public enum PlotScaleType {

	/**
	 * Specifies that the Series data is typically plotted on a linear scale.
	 *
	 */
	@XmlEnumValue("linear")
	LINEAR("linear"),
	/**
	 * Specifies that the Series data is typically plotted on a common logarithmic scale (base 10).
	 *
	 */
	@XmlEnumValue("log")
	LOG("log"),
	/**
	 * Specifies that the Series data is typically plotted on a natural logarithmic scale (base e).
	 *
	 */
	@XmlEnumValue("ln")
	LN("ln"),
	/**
	 * Specifies that the Series data is typically not plotted.
	 *
	 */
	@XmlEnumValue("none")
	NONE("none");

	private final String value;

	PlotScaleType(String v) {

		value = v;
	}

	public String value() {

		return value;
	}

	public static PlotScaleType fromValue(String v) {

		for(PlotScaleType c : PlotScaleType.values()) {
			if(c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}

/*******************************************************************************
 * Copyright (c) 2021, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.converter.supplier.animl.model.astm.core;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "PlotScaleType")
@XmlEnum
public enum PlotScaleType {

	/**
	 * Specifies that this Series is typically plotted on a linear scale.
	 *
	 */
	@XmlEnumValue("linear")
	LINEAR("linear"),
	/**
	 * Specifies that this Series is typically plotted on a common logarithmic scale (base 10).
	 *
	 */
	@XmlEnumValue("log")
	LOG("log"),
	/**
	 * Specifies that this Series is typically plotted on a natural logarithmic scale (base e).
	 *
	 */
	@XmlEnumValue("ln")
	LN("ln"),
	/**
	 * Specifies that this Series is not plottable.
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

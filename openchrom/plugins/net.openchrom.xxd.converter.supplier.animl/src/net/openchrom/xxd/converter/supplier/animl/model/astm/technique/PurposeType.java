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

@XmlType(name = "PurposeType")
@XmlEnum
public enum PurposeType {

	/**
	 * Indicates that a sample is produced in an experiment.
	 *
	 */
	@XmlEnumValue("produced")
	PRODUCED("produced"),
	/**
	 * Indicates that a sample is consumed in an experiment.
	 *
	 */
	@XmlEnumValue("consumed")
	CONSUMED("consumed");

	private final String value;

	PurposeType(String v) {

		value = v;
	}

	public String value() {

		return value;
	}

	public static PurposeType fromValue(String v) {

		for(PurposeType c : PurposeType.values()) {
			if(c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}

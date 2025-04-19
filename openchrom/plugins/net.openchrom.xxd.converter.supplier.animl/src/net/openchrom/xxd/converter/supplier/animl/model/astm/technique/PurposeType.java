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

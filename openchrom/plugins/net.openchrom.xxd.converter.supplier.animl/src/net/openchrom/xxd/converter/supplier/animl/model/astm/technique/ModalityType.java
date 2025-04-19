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

@XmlType(name = "ModalityType")
@XmlEnum
public enum ModalityType {

	/**
	 * Specifies that the corresponding entity is required.
	 *
	 */
	@XmlEnumValue("required")
	REQUIRED("required"),
	/**
	 * Specifies that the corresponding entity is optional.
	 *
	 */
	@XmlEnumValue("optional")
	OPTIONAL("optional");

	private final String value;

	ModalityType(String v) {

		value = v;
	}

	public String value() {

		return value;
	}

	public static ModalityType fromValue(String v) {

		for(ModalityType c : ModalityType.values()) {
			if(c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}

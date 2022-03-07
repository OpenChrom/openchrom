/*******************************************************************************
 * Copyright (c) 2021, 2022 Lablicate GmbH.
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

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "UserTypeType")
@XmlEnum
public enum UserTypeType {

	/**
	 * Specifies that the user is a real person.
	 *
	 */
	@XmlEnumValue("human")
	HUMAN("human"),
	/**
	 * Specifies that the user is a device.
	 *
	 */
	@XmlEnumValue("device")
	DEVICE("device"),
	/**
	 * Specifies that the user is a software system.
	 *
	 */
	@XmlEnumValue("software")
	SOFTWARE("software");

	private final String value;

	UserTypeType(String v) {

		value = v;
	}

	public String value() {

		return value;
	}

	public static UserTypeType fromValue(String v) {

		for(UserTypeType c : UserTypeType.values()) {
			if(c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}

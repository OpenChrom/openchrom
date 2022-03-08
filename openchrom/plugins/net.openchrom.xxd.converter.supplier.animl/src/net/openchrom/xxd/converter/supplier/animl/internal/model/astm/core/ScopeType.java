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

@XmlType(name = "ScopeType")
@XmlEnum
public enum ScopeType {

	/**
	 * This diff describes the whole element before and after the change.
	 *
	 */
	@XmlEnumValue("element")
	ELEMENT("element"),
	/**
	 * This diff only describes a change in attributes. The child elements remain unchanged (and are not reflected in the diff to save space).
	 *
	 */
	@XmlEnumValue("attributes")
	ATTRIBUTES("attributes");

	private final String value;

	ScopeType(String v) {

		value = v;
	}

	public String value() {

		return value;
	}

	public static ScopeType fromValue(String v) {

		for(ScopeType c : ScopeType.values()) {
			if(c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}

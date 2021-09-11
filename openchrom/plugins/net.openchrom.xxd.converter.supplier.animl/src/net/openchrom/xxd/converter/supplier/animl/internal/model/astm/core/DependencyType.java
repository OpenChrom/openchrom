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

@XmlType(name = "DependencyType")
@XmlEnum
public enum DependencyType {

	/**
	 * Specified that the Series is independent.
	 *
	 */
	@XmlEnumValue("independent")
	INDEPENDENT("independent"),
	/**
	 * Specified that the Series is dependent.
	 *
	 */
	@XmlEnumValue("dependent")
	DEPENDENT("dependent");

	private final String value;

	DependencyType(String v) {

		value = v;
	}

	public String value() {

		return value;
	}

	public static DependencyType fromValue(String v) {

		for(DependencyType c : DependencyType.values()) {
			if(c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}

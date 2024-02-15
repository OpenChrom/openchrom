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
 * Philip Wenig - refactorings
 *******************************************************************************/
package net.openchrom.xxd.converter.supplier.animl.model.astm.core;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "ActionType")
@XmlEnum
public enum ActionType {

	/**
	 * The given user has created the references items.
	 *
	 */
	@XmlEnumValue("created")
	CREATED("created"),
	/**
	 * Item already existed and has been modified. Modifications are explained in the Description element.
	 *
	 */
	@XmlEnumValue("modified")
	MODIFIED("modified"),
	/**
	 * Item has been converted into AnIML format.
	 *
	 */
	@XmlEnumValue("converted")
	CONVERTED("converted"),
	/**
	 * The given user has exercised read access on the referenced items.
	 *
	 */
	@XmlEnumValue("read")
	READ("read"),
	/**
	 * The given user has attached a digital signature.
	 *
	 */
	@XmlEnumValue("signed")
	SIGNED("signed"),
	/**
	 * The referenced items were deleted. No reference is specified. Description explains what was deleted.
	 *
	 */
	@XmlEnumValue("deleted")
	DELETED("deleted");

	private final String value;

	ActionType(String v) {

		value = v;
	}

	public String value() {

		return value;
	}

	public static ActionType fromValue(String v) {

		for(ActionType c : ActionType.values()) {
			if(c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}

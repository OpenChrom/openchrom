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
package net.openchrom.xxd.converter.supplier.gaml.internal.v120.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "valueorder")
@XmlEnum
public enum Valueorder {
	EVEN, ORDERED, UNSPECIFIED;

	public String value() {

		return name();
	}

	public static Valueorder fromValue(String v) {

		return valueOf(v);
	}
}

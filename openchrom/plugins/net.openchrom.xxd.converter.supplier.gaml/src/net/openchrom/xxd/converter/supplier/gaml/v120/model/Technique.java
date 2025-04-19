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
package net.openchrom.xxd.converter.supplier.gaml.v120.model;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "technique")
@XmlEnum
public enum Technique {
	ATOMIC, CHROM, FLUOR, IR, MS, NIR, NMR, PDA, PARTICLE, POLAR, RAMAN, THERMAL, UNKNOWN, UVVIS, XRAY;

	public String value() {

		return name();
	}

	public static Technique fromValue(String v) {

		return valueOf(v);
	}
}

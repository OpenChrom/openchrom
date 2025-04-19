/*******************************************************************************
 * Copyright (c) 2024, 2025 Lablicate GmbH.
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
package net.openchrom.msd.converter.supplier.mzdb.internal;

public enum Precision {

	FLOAT(32), DOUBLE(64);

	private final int bits;

	Precision(int bits) {

		this.bits = bits;
	}

	public int getBits() {

		return bits;
	}

	public static Precision fromBits(int bits) {

		for(Precision p : Precision.values()) {
			if(p.getBits() == bits) {
				return p;
			}
		}
		throw new IllegalArgumentException("No enum constant for bits: " + bits);
	}
}

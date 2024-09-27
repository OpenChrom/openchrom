/*******************************************************************************
 * Copyright (c) 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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

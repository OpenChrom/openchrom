/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jan Holy - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.settings.support;

public enum ZeroFillingFactor {
	AUTO(0, "Automatic zero filling to a size with the next power of 2"), //
	FACTOR_2(1, "Zero filling up to a size of 2 data points"), //
	FACTOR_4(2, "Zero filling up to a size of 4 data points"), //
	FACTOR_8(3, "Zero filling up to a size of 8 data points"), //
	FACTOR_16(4, "Zero filling up to a size of 16 data points"), //
	FACTOR_32(5, "Zero filling up to a size of 32 data points"), //
	FACTOR_64(6, "Zero filling up to a size of 64 data points"), //
	FACTOR_128(7, "Zero filling up to a size of 128 data points"), //
	FACTOR_256(8, "Zero filling up to a size of 256 data points"), //
	FACTOR_512(9, "Zero filling up to a size of 512 data points"), //
	FACTOR_1k(10, "Zero filling up to a size of 1k data points"), //
	FACTOR_2k(11, "Zero filling up to a size of 2k data points"), //
	FACTOR_4k(12, "Zero filling up to a size of 4k data points"), //
	FACTOR_8k(13, "Zero filling up to a size of 8k data points"), //
	FACTOR_16k(14, "Zero filling up to a size of 16k data points"), //
	FACTOR_32k(15, "Zero filling up to a size of 32k data points"), //
	FACTOR_64k(16, "Zero filling up to a size of 64k data points"), //
	FACTOR_128k(17, "Zero filling up to a size of 128k data points"), //
	FACTOR_256k(18, "Zero filling up to a size of 256k data points"), //
	FACTOR_512k(19, "Zero filling up to a size of 512k data points"), //
	FACTOR_1024k(20, "Zero filling up to a size of 1024k data points");

	private int exponent;
	private String name;

	private ZeroFillingFactor(int exponent, String name) {

		this.exponent = exponent;
		this.name = name;
	}

	public int getExponent() {

		return exponent;
	}

	public int getValue() {

		return (int)Math.pow(2, exponent);
	}

	@Override
	public String toString() {

		return name;
	}
}

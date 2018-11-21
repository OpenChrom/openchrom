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

public enum ZERO_FILLING_FACTOR {
	AUTO(0, "Auto zero filling"), //
	FACTOR_2(1, "2"), //
	FACTOR_4(2, "4"), //
	FACTOR_8(3, "8"), //
	FACTOR_16(4, "16"), //
	FACTOR_32(5, "32"), //
	FACTOR_64(6, "64"), //
	FACTOR_128(7, "128"), //
	FACTOR_512(8, "512"), //
	FACTOR_1k(9, "1k"), //
	FACTOR_2k(10, "2k"), //
	FACTOR_4k(11, "4k"), //
	FACTOR_8k(12, "8k"), //
	FACTOR_16k(13, "16k"), //
	FACTOR_32k(14, "32k"), //
	FACTOR_64k(15, "64k"), //
	FACTOR_128k(16, "128k"), //
	FACTOR_256k(17, "256k"), //
	FACTOR_512k(18, "512k"), //
	FACTOR_1024k(19, "1024k"), //
	FACTOR_2048k(20, "2048k");

	private int exponent;
	private String name;

	private ZERO_FILLING_FACTOR(int exponent, String name) {

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

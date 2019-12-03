/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexander Stark - initial API and implementation
 * Jan Holy - refactoring
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.settings.support;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ZeroFillingFactor {
	AUTO(0, "Automatic zero filling to a size with the next power of 2"), //
	EXPONENT_1(1, "Zero filling up to a size of 2 data points"), //
	EXPONENT_2(2, "Zero filling up to a size of 4 data points"), //
	EXPONENT_3(3, "Zero filling up to a size of 8 data points"), //
	EXPONENT_4(4, "Zero filling up to a size of 16 data points"), //
	EXPONENT_5(5, "Zero filling up to a size of 32 data points"), //
	EXPONENT_6(6, "Zero filling up to a size of 64 data points"), //
	EXPONENT_7(7, "Zero filling up to a size of 128 data points"), //
	EXPONENT_8(8, "Zero filling up to a size of 256 data points"), //
	EXPONENT_9(9, "Zero filling up to a size of 512 data points"), //
	EXPONENT_10(10, "Zero filling up to a size of 1k data points"), //
	EXPONENT_11(11, "Zero filling up to a size of 2k data points"), //
	EXPONENT_12(12, "Zero filling up to a size of 4k data points"), //
	EXPONENT_13(13, "Zero filling up to a size of 8k data points"), //
	EXPONENT_14(14, "Zero filling up to a size of 16k data points"), //
	EXPONENT_15(15, "Zero filling up to a size of 32k data points"), //
	EXPONENT_16(16, "Zero filling up to a size of 64k data points"), //
	EXPONENT_17(17, "Zero filling up to a size of 128k data points"), //
	EXPONENT_18(18, "Zero filling up to a size of 256k data points"), //
	EXPONENT_19(19, "Zero filling up to a size of 512k data points"), //
	EXPONENT_20(20, "Zero filling up to a size of 1024k data points");

	private int exponent;
	@JsonValue
	private String name;
	private static Map<Object, Object> zeroFillingMap = new HashMap<>();

	private ZeroFillingFactor(int exponent, String name){

		this.exponent = exponent;
		this.name = name;
	}

	public int getExponent() {

		return exponent;
	}

	public int getValue() {

		return (int) Math.pow(2, exponent);
	}

	@Override
	public String toString() {

		return name;
	}

	static {
		for(ZeroFillingFactor zeroFillingFactor : ZeroFillingFactor.values()) {
			zeroFillingMap.put(zeroFillingFactor.exponent, zeroFillingFactor);
		}
	}

	public static ZeroFillingFactor valueOf(int zeroFillingFactor) {

		return (ZeroFillingFactor) zeroFillingMap.get(zeroFillingFactor);
	}
}

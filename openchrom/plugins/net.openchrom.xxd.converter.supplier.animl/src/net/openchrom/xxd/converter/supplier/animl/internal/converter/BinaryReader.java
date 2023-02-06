/*******************************************************************************
 * Copyright (c) 2021, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.converter.supplier.animl.internal.converter;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BinaryReader {

	public static int[] decodeIntArray(byte[] binary) {

		ByteBuffer byteBuffer = ByteBuffer.wrap(binary);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		IntBuffer intBuffer = byteBuffer.asIntBuffer();
		int[] values = new int[intBuffer.capacity()];
		for(int index = 0; index < intBuffer.capacity(); index++) {
			values[index] = intBuffer.get(index);
		}
		return values;
	}

	public static Float[] decodeFloatArray(byte[] binary) {

		ByteBuffer byteBuffer = ByteBuffer.wrap(binary);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
		Float[] values = new Float[floatBuffer.capacity()];
		for(int index = 0; index < floatBuffer.capacity(); index++) {
			values[index] = floatBuffer.get(index);
		}
		return values;
	}

	public static Double[] decodeDoubleArray(byte[] binary) {

		ByteBuffer byteBuffer = ByteBuffer.wrap(binary);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		DoubleBuffer doubleBuffer = byteBuffer.asDoubleBuffer();
		Double[] values = new Double[doubleBuffer.capacity()];
		for(int index = 0; index < doubleBuffer.capacity(); index++) {
			values[index] = doubleBuffer.get(index);
		}
		return values;
	}

	public static byte[] encodeArray(float[] array) {

		FloatBuffer floatBuffer = FloatBuffer.wrap(array);
		ByteBuffer byteBuffer = ByteBuffer.allocate(floatBuffer.capacity() * Float.BYTES);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		byteBuffer.asFloatBuffer().put(floatBuffer);
		return byteBuffer.array();
	}

	public static byte[] encodeArray(double[] array) {

		DoubleBuffer doubleBuffer = DoubleBuffer.wrap(array);
		ByteBuffer byteBuffer = ByteBuffer.allocate(doubleBuffer.capacity() * Double.BYTES);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		byteBuffer.asDoubleBuffer().put(doubleBuffer);
		return byteBuffer.array();
	}

	public static byte[] encodeArray(int[] array) {

		IntBuffer intBuffer = IntBuffer.wrap(array);
		ByteBuffer byteBuffer = ByteBuffer.allocate(intBuffer.capacity() * Integer.BYTES);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		byteBuffer.asIntBuffer().put(intBuffer);
		return byteBuffer.array();
	}
}

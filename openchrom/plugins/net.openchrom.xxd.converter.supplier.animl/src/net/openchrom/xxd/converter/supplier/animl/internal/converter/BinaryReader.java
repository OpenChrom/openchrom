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
package net.openchrom.xxd.converter.supplier.animl.internal.converter;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class BinaryReader {

	public static float[] decodeArray(byte[] binary) {

		ByteBuffer byteBuffer = ByteBuffer.wrap(binary);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
		float[] values = new float[floatBuffer.capacity()];
		for(int index = 0; index < floatBuffer.capacity(); index++) {
			values[index] = new Float(floatBuffer.get(index));
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
}

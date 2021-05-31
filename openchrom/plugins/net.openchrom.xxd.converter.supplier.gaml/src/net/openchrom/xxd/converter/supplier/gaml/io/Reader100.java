package net.openchrom.xxd.converter.supplier.gaml.io;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

import net.openchrom.xxd.converter.supplier.gaml.internal.v100.model.Units;
import net.openchrom.xxd.converter.supplier.gaml.internal.v100.model.Values;

public class Reader100 {

	public static double[] parseValues(Values values) {

		double[] buffer = new double[0];
		ByteBuffer byteBuffer = ByteBuffer.wrap(values.getValue());
		/*
		 * Byte Order
		 */
		String byteOrder = values.getByteorder();
		if(byteOrder != null && byteOrder.equals("INTEL")) {
			byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		}
		/*
		 * Data Type
		 */
		String format = values.getFormat();
		if(format.equals("FLOAT64")) {
			DoubleBuffer doubleBuffer = byteBuffer.asDoubleBuffer();
			buffer = new double[doubleBuffer.capacity()];
			for(int index = 0; index < doubleBuffer.capacity(); index++) {
				buffer[index] = doubleBuffer.get(index);
			}
		} else if(format.equals("FLOAT32")) {
			FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
			buffer = new double[floatBuffer.capacity()];
			for(int index = 0; index < floatBuffer.capacity(); index++) {
				buffer[index] = floatBuffer.get(index);
			}
		}
		return buffer;
	}

	public static int convertToMiliSeconds(double rt, Units unit) {

		int multiplicator = 1;
		if(unit == Units.SECONDS) {
			multiplicator = 1000;
		}
		if(unit == Units.MINUTES) {
			multiplicator = 60 * 1000;
		}
		return (int)Math.round(rt * multiplicator);
	}
}

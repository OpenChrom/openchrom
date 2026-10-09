/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
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
package net.openchrom.msd.converter.supplier.cdf.io.support;

import java.io.IOException;

import ucar.ma2.Array;
import ucar.ma2.ArrayChar;
import ucar.ma2.DataType;
import ucar.nc2.Attribute;
import ucar.nc2.Variable;

/**
 * Reads netCDF variables.<br/>
 * The files are opened unenhanced, hence the packing attributes 'scale_factor'
 * and 'add_offset' are not applied by the netCDF library itself. Vendors like
 * Teknivent store the m/z values as scaled integers, so skipping them yields
 * masses which are off by orders of magnitude.
 */
public class VariableSupport {

	private VariableSupport() {

	}

	/**
	 * Reads the variable and applies 'scale_factor' and 'add_offset'.
	 */
	public static double[] readScaled(Variable variable) throws IOException {

		double[] values = (double[])variable.read().get1DJavaArray(DataType.DOUBLE);
		double scaleFactor = getNumericAttribute(variable, CDFConstants.ATTRIBUTE_SCALE_FACTOR, 1.0d);
		double addOffset = getNumericAttribute(variable, CDFConstants.ATTRIBUTE_ADD_OFFSET, 0.0d);
		if(scaleFactor != 1.0d || addOffset != 0.0d) {
			for(int i = 0; i < values.length; i++) {
				values[i] = values[i] * scaleFactor + addOffset;
			}
		}
		return values;
	}

	/**
	 * Reads the variable, applies the packing attributes and narrows the result.
	 */
	public static float[] readScaledFloats(Variable variable) throws IOException {

		double[] values = readScaled(variable);
		float[] result = new float[values.length];
		for(int i = 0; i < values.length; i++) {
			result[i] = (float)values[i];
		}
		return result;
	}

	public static int[] readIntegers(Variable variable) throws IOException {

		return (int[])variable.read().get1DJavaArray(DataType.INT);
	}

	/**
	 * Reads a two dimensional character variable as one string per entry.<br/>
	 * Returns an empty array if the variable doesn't hold characters.
	 */
	public static String[] readStrings(Variable variable) throws IOException {

		Array array = variable.read();
		if(!(array instanceof ArrayChar arrayChar) || arrayChar.getRank() != 2) {
			return new String[0];
		}
		int entries = arrayChar.getShape()[0];
		String[] values = new String[entries];
		for(int i = 0; i < entries; i++) {
			values[i] = arrayChar.getString(i).trim();
		}
		return values;
	}

	public static double getNumericAttribute(Variable variable, String name, double defaultValue) {

		Attribute attribute = variable.findAttribute(name);
		if(attribute == null) {
			return defaultValue;
		}
		Number value = attribute.getNumericValue();
		return value == null ? defaultValue : value.doubleValue();
	}

	/**
	 * The AIA format marks fields a vendor did not fill in with -9999.
	 */
	public static boolean isNullValue(double value) {

		return value == CDFConstants.NULL_VALUE;
	}
}

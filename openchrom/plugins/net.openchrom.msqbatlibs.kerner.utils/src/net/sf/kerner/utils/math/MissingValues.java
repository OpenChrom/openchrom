/*******************************************************************************
 * Copyright (c) 2015, 2016 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.kerner.utils.math;

public class MissingValues {

	public static double[] remove(final double[] values) {

		final double[] result = new double[values.length];
		int i = 0;
		for(final double d : values) {
			if(Double.isNaN(d) || Double.isInfinite(d)) {
				// ignore
			} else {
				result[i] = d;
				i++;
			}
		}
		return result;
	}

	public static double[] replace(final double[] values, final double replacement) {

		final double[] result = new double[values.length];
		int i = 0;
		for(final double d : values) {
			if(Double.isNaN(d) || Double.isInfinite(d)) {
				// replace
				result[i] = replacement;
				i++;
			} else {
				result[i] = d;
				i++;
			}
		}
		return result;
	}

	private MissingValues() {
	}
}

/*******************************************************************************
 * Copyright (c) 2015, 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.kerner.utils.collections;

public class RangeScaler {

	public double[] scale(final double[] arr, final double newMax) {

		final double[] result = new double[arr.length];
		final double scale = newMax / arr[arr.length - 1];
		for(int i = 0; i < arr.length; i++) {
			result[i] = arr[i] * scale;
		}
		return result;
	}
}

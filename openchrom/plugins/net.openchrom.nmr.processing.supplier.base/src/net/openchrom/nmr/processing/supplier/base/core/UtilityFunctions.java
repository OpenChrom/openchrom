/*******************************************************************************
 * Copyright (c) 2018 Alexander Stark.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexander Stark - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.core;

public class UtilityFunctions {

	public double[] generateLinearlySpacedVector(double minVal, double maxVal, int points) {

		double[] vector = new double[points];
		for(int i = 0; i < points; i++) {
			vector[i] = minVal + (double)i / (points - 1) * (maxVal - minVal);
		}
		return vector;
	}

	public double getMaxValueOfArray(double[] dataArray) {

		double maxValue = 0;
		for(int m = 1; m < dataArray.length; m++) {
			if(dataArray[m] > maxValue) {
				maxValue = dataArray[m];
			}
		}
		return maxValue;
	}

	public double getMinValueOfArray(double[] dataArray) {

		double minValue = 0;
		for(int m = 1; m < dataArray.length; m++) {
			if(dataArray[m] < minValue) {
				minValue = dataArray[m];
			}
		}
		return minValue;
	}
}

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
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.core;

import org.apache.commons.math3.complex.Complex;
import org.eclipse.chemclipse.nmr.model.core.IMeasurementNMR;

public class UtilityFunctions {

	public double[] generateChemicalShiftAxis(IMeasurementNMR scanNMR) {

		double doubleSize = scanNMR.getProcessingParameters("numberOfFourierPoints");
		int deltaAxisPoints = (int)doubleSize;
		double[] chemicalShiftAxis = new double[(int)doubleSize];
		double minValueDeltaAxis = scanNMR.getProcessingParameters("firstDataPointOffset");
		double maxValueDeltaAxis = scanNMR.getProcessingParameters("sweepWidth") + scanNMR.getProcessingParameters("firstDataPointOffset");
		chemicalShiftAxis = generateLinearlySpacedVector(minValueDeltaAxis, maxValueDeltaAxis, deltaAxisPoints);
		return chemicalShiftAxis;
	}

	public long[] generateTimeScale(IMeasurementNMR vendorScan) {

		double minValTimescale = 0;
		double maxValTimescaleFactor = (vendorScan.getProcessingParameters("numberOfFourierPoints") / vendorScan.getProcessingParameters("numberOfPoints"));
		double maxValTimescale = vendorScan.getProcessingParameters("acquisitionTime") * maxValTimescaleFactor; // linspace(0,NmrData.at*(NmrData.fn/NmrData.np),NmrData.fn);
		int timescalePoints = vendorScan.getProcessingParameters("numberOfFourierPoints").intValue();
		double[] timeScaleTemp = generateLinearlySpacedVector(minValTimescale, maxValTimescale, timescalePoints); // for ft-operation
		// else: double[] TimeScale = GenerateLinearlySpacedVector(minValTimescale, BrukerAT, TimescalePoints);
		long[] timeScale = new long[timeScaleTemp.length];
		for(int i = 0; i < timeScaleTemp.length; i++) {
			timeScale[i] = (long)(timeScaleTemp[i] * 1000000);
		}
		return timeScale;
	}

	public double[] generateLinearlySpacedVector(double minVal, double maxVal, int points) {

		double[] vector = new double[points];
		for(int i = 0; i < points; i++) {
			vector[i] = minVal + (double)i / (points - 1) * (maxVal - minVal);
		}
		return vector;
	}

	public double getMaxValueOfArray(double[] dataArray) {

		double maxValue = -Double.MAX_VALUE;
		for(int m = 0; m < dataArray.length; m++) {
			if(dataArray[m] > maxValue) {
				maxValue = dataArray[m];
			}
		}
		return maxValue;
	}

	public double getMinValueOfArray(double[] dataArray) {

		double minValue = Double.MAX_VALUE;
		for(int m = 0; m < dataArray.length; m++) {
			if(dataArray[m] < minValue) {
				minValue = dataArray[m];
			}
		}
		return minValue;
	}

	public int findIndexOfValue(double[] array, double value) {

		int index;
		for(index = 0; index < array.length; index++) {
			if(Math.abs(array[index] - value) < 0.001) {
				break;
			}
		}
		return index;
	}

	public void leftShiftNMRData(double[] dataArray, int pointsToShift) {

		pointsToShift = pointsToShift % dataArray.length;
		while(pointsToShift-- > 0) {
			double tempArray = dataArray[0];
			for(int i = 1; i < dataArray.length; i++) {
				dataArray[i - 1] = dataArray[i];
			}
			dataArray[dataArray.length - 1] = tempArray;
		}
	}

	public double[] rightShiftNMRData(double[] dataArray, int pointsToShift) {

		for(int i = 0; i < pointsToShift; i++) {
			double tempArray = dataArray[dataArray.length - 1];
			for(int g = dataArray.length - 2; g > -1; g--) {
				dataArray[g + 1] = dataArray[g];
			}
			dataArray[0] = tempArray;
		}
		return dataArray;
	}

	public void leftShiftNMRComplexData(Complex[] dataArray, int pointsToShift) {

		pointsToShift = pointsToShift % dataArray.length;
		while(pointsToShift-- > 0) {
			Complex tempArray = dataArray[0];
			for(int i = 1; i < dataArray.length; i++) {
				dataArray[i - 1] = dataArray[i];
			}
			dataArray[dataArray.length - 1] = tempArray;
		}
	}

	public Complex[] rightShiftNMRComplexData(Complex[] dataArray, int pointsToShift) {

		for(int i = 0; i < pointsToShift; i++) {
			Complex tempArray = dataArray[dataArray.length - 1];
			for(int g = dataArray.length - 2; g > -1; g--) {
				dataArray[g + 1] = dataArray[g];
			}
			dataArray[0] = tempArray;
		}
		return dataArray;
	}
}

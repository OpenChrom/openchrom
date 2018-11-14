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

import org.eclipse.chemclipse.nmr.model.core.IScanNMR;

public class UtilityFunctions {

	public double[] generateChemicalShiftAxis(IScanNMR scanNMR) {

		double doubleSize = scanNMR.getProcessingParameters("numberOfFourierPoints");
		int deltaAxisPoints = (int)doubleSize;
		double[] chemicalShiftAxis = new double[(int)doubleSize];
		double minValueDeltaAxis = scanNMR.getProcessingParameters("firstDataPointOffset");
		double maxValueDeltaAxis = scanNMR.getProcessingParameters("sweepWidth") + scanNMR.getProcessingParameters("firstDataPointOffset");
		chemicalShiftAxis = generateLinearlySpacedVector(minValueDeltaAxis, maxValueDeltaAxis, deltaAxisPoints);
		return chemicalShiftAxis;
	}

	public int[] generateTimeScale(IScanNMR vendorScan) {

		double minValTimescale = 0;
		double maxValTimescaleFactor = (vendorScan.getProcessingParameters("numberOfFourierPoints") / vendorScan.getProcessingParameters("numberOfPoints"));
		double maxValTimescale = vendorScan.getProcessingParameters("acquisitionTime") * maxValTimescaleFactor; // linspace(0,NmrData.at*(NmrData.fn/NmrData.np),NmrData.fn);
		int timescalePoints = vendorScan.getProcessingParameters("numberOfFourierPoints").intValue();
		double[] timeScaleTemp = generateLinearlySpacedVector(minValTimescale, maxValTimescale, timescalePoints); // for ft-operation
		// else: double[] TimeScale = GenerateLinearlySpacedVector(minValTimescale, BrukerAT, TimescalePoints);
		int[] timeScale = new int[timeScaleTemp.length];
		for(int i = 0; i < timeScaleTemp.length; i++) {
			timeScale[i] = (int)(timeScaleTemp[i] * 1000000);
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

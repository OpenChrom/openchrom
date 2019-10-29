/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexander Stark - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.processing.peakdetection;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.chemclipse.nmr.model.core.SpectrumSignal;
import org.ejml.simple.SimpleMatrix;

import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions;

public class WaveletPeakDetectorCWT {

	public static SimpleMatrix calculateWaveletCoefficients(List<? extends SpectrumSignal> signals, WaveletPeakDetectorSettings configuration) {

		double[] psiValues = UtilityFunctions.generateLinearlySpacedVector(-8, 8, 1024);
		double[] psi = WaveletPeakDetectorCWTUtils.calculatePsi(psiValues);
		double subtractPsi = psiValues[0];
		for(int i = 0; i < psiValues.length; i++) {
			psiValues[i] = psiValues[i] - subtractPsi;
		}
		double dxValue = psiValues[1];
		int maxPsiValue = (int) psiValues[psiValues.length - 1];

		// extract data
		double[] dataArray = extractDataFromList(signals, configuration);
		int workingLength = dataArray.length;

		/*
		 * calculate coefficients
		 */
		int[] psiScales = configuration.getPsiScales();
		SimpleMatrix coefficients = new SimpleMatrix(workingLength, psiScales.length);
		for(int i = 0; i < psiScales.length; i++) {
			int currentScale = psiScales[i];
			int[] waveValues = WaveletPeakDetectorCWTUtils.calculateWaveValues(currentScale, maxPsiValue, dxValue);
			double[] convolveFunction = WaveletPeakDetectorCWTUtils.calculateConvolveFunction(waveValues, psi, workingLength);
			double[] convolvedSignals = WaveletPeakDetectorCWTUtils.convolveSignals(dataArray, convolveFunction, currentScale, waveValues.length);
			coefficients.setColumn(i, 0, convolvedSignals);
		}
		if(isPaddedData()) {
			// cut coefficients back to original data size if padding was done
			return coefficients.extractMatrix(0, coefficients.numRows(), 0, signals.size());
		}
		return coefficients;
	}

	private static boolean paddedData = false;

	private static boolean isPaddedData() {
		return paddedData;
	}

	private static void setPaddedData(boolean paddedData) {
		WaveletPeakDetectorCWT.paddedData = paddedData;
	}

	private static double[] extractDataFromList(List<? extends SpectrumSignal> signals, WaveletPeakDetectorSettings configuration) {

		// check if length of data equals 2^n
		if(!WaveletPeakDetectorCWTUtils.lengthIsPowerOfTwo(signals)) {
			// pseudo zero filling, e.g. append part of data to satisfy FFT condition
			setPaddedData(true);
			int newLength = (int) (Math.ceil((Math.log(signals.size()) / Math.log(2))));
			int diffLength = newLength - signals.size();
			double[] tempDataArray = WaveletPeakDetectorCWTUtils.getAbsorptiveIntensity(signals);
			double[] dataToAppend = Arrays.copyOfRange(tempDataArray, 0, diffLength);
			return ArrayUtils.addAll(tempDataArray, dataToAppend);
		} else {
			// just copy data
			return WaveletPeakDetectorCWTUtils.getAbsorptiveIntensity(signals);
		}
	}

}

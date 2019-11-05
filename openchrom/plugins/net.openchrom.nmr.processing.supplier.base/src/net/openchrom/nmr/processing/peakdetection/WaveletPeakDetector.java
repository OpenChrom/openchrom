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

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.chemclipse.nmr.model.core.SpectrumSignal;
import org.ejml.simple.SimpleMatrix;

import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions;

public class WaveletPeakDetector {

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
		double[] dataArray = WaveletPeakDetectorCWTUtils.extractDataFromList(signals, configuration);
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
		if(WaveletPeakDetectorCWTUtils.isPaddedData()) {
			// cut coefficients back to original data size if padding was done
			return coefficients.extractMatrix(0, coefficients.numRows(), 0, signals.size());
		}
		return coefficients;
	}

	public static SimpleMatrix calculateLocalMaxima(List<? extends SpectrumSignal> signals, SimpleMatrix waveletCoefficients, WaveletPeakDetectorSettings configuration) {
	
		// prepare data for search
		SimpleMatrix waveletCoefficientsWithData = WaveletPeakDetectorMaximaUtils.prepareDataForMaximaSearch(signals, waveletCoefficients);
		int[] localPsiScales = ArrayUtils.addAll(new int[] { 0 }, configuration.getPsiScales());
	
		SimpleMatrix localMaximumMatrix = new SimpleMatrix(waveletCoefficientsWithData.numRows(), waveletCoefficientsWithData.numCols());
		double[] localMaximum = new double[signals.size()];
	
		// search column-wise for maximum within the specified window
		for(int s = 0; s < localPsiScales.length; s++) {
			int currentScale = localPsiScales[s];
			int windowSize = WaveletPeakDetectorMaximaUtils.calculateWindowSize(currentScale, configuration);
	
			// search using column and windowSize
			for(int c = 0; c < waveletCoefficientsWithData.numCols(); c++) {
				int dataLength = waveletCoefficientsWithData.numRows();
				//
				int shiftDataValue = Integer.MIN_VALUE;
				int rowNumber = (int) Math.ceil((double) dataLength / windowSize);
				localMaximum = WaveletPeakDetectorMaximaUtils.locateMaxima(c, rowNumber, windowSize, dataLength, shiftDataValue, waveletCoefficientsWithData);
				//
				shiftDataValue = (int) Math.floor(windowSize / 2);
				rowNumber = (int) Math.ceil((double) (dataLength + shiftDataValue) / windowSize);
				double[] tempLocalMaximum = WaveletPeakDetectorMaximaUtils.locateMaxima(c, rowNumber, windowSize, dataLength, shiftDataValue, waveletCoefficientsWithData);
				// combine results from both locateMaxima() calls
				WaveletPeakDetectorMaximaUtils.combineLocatedMaximaResults(localMaximum, tempLocalMaximum);
	
				WaveletPeakDetectorMaximaUtils.determineLocalMaxima(localMaximum, windowSize, signals);
				localMaximumMatrix.setColumn(c, 0, localMaximum);
			}
		}
	
		// for every coefficient < amplitudeThreshold set (possible) local maximum to 0
		WaveletPeakDetectorMaximaUtils.applyAmplitudeThreshold(localMaximumMatrix, waveletCoefficientsWithData, configuration);
		return localMaximumMatrix;
	}

}

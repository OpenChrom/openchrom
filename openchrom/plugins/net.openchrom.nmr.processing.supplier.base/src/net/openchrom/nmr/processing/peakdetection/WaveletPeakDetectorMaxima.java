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

public class WaveletPeakDetectorMaxima {

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

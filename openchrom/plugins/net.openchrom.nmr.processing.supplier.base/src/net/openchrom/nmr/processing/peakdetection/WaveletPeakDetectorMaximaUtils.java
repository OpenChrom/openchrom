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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.chemclipse.nmr.model.core.SpectrumSignal;
import org.ejml.data.DMatrixIterator;
import org.ejml.simple.SimpleMatrix;

import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions;

public class WaveletPeakDetectorMaximaUtils {

	public static double[] extractMatrixElements(SimpleMatrix paddedDataArrayMatrix, boolean extractRow, int element) {

		return UtilityFunctions.extractVectorFromMatrix(paddedDataArrayMatrix, extractRow, element);
	}

	public static int calculateWindowSize(int currentScale, WaveletPeakDetectorSettings configuration) {

		int windowSize = currentScale * 2 + 1;
		if(windowSize < configuration.getMinimumWindowSize()) {
			windowSize = configuration.getMinimumWindowSize();
		}
		return windowSize;
	}

	public static double calculateAmplitudeThreshold(SimpleMatrix waveletCoefficientsWithData, WaveletPeakDetectorSettings configuration) {

		double amplitudeThreshold = Double.MIN_VALUE;
		if(configuration.getAmplitudeThreshold() == 0) {
			amplitudeThreshold = 0;
		} else {
			amplitudeThreshold = WaveletPeakDetectorMaximaUtils.identifyMaxCoefficient(waveletCoefficientsWithData) * configuration.getAmplitudeThreshold();
		}
		return amplitudeThreshold;
	}

	private static double identifyMaxCoefficient(SimpleMatrix waveletCoefficients) {

		double result = Double.MIN_VALUE;
		for(int r = 0; r < waveletCoefficients.numRows(); r++) {
			double[] rowVector = extractMatrixElements(waveletCoefficients, true, r);
			double maxValue = UtilityFunctions.getMaxValueOfArray(rowVector);
			if(maxValue > result) {
				result = maxValue;
			}
		}
		return result;
	}

	public static SimpleMatrix prepareDataForMaximaSearch(List<? extends SpectrumSignal> signals, SimpleMatrix waveletCoefficients) {

		double[] dataArray = WaveletPeakDetectorCWTUtils.getAbsorptiveIntensity(signals);
		SimpleMatrix dataArrayMatrix = new SimpleMatrix(signals.size(), 1);
		dataArrayMatrix.setColumn(0, 0, dataArray);
		SimpleMatrix waveletCoefficientsWithData = new SimpleMatrix(waveletCoefficients.numRows(), waveletCoefficients.numCols() + 1);
		waveletCoefficientsWithData.insertIntoThis(0, 0, dataArrayMatrix);
		waveletCoefficientsWithData.insertIntoThis(0, 1, waveletCoefficients);
		return waveletCoefficientsWithData;
	}

	private static boolean[] checkIfMaximumIsBiggerThanColumnBounds(int length, int rowNumber, SimpleMatrix paddedDataArrayMatrix) {

		boolean[] maximumBiggerThanBounds = new boolean[length];
		for(int i = 0; i < rowNumber; i++) {
			double[] column = extractMatrixElements(paddedDataArrayMatrix, false, i);
			double max = UtilityFunctions.getMaxValueOfArray(column);
			double lowBound = column[0];
			double highBound = column[column.length - 1];
			maximumBiggerThanBounds[i] = ((max > lowBound && max > highBound) ? true : false);
		}
		return maximumBiggerThanBounds;
	}

	public static double[] getMaxValuePerColumn(int rowNumber, SimpleMatrix paddedDataArrayMatrix) {

		double[] paddedDataArrayMatrixMaxIndices = new double[paddedDataArrayMatrix.numCols()];
		for(int i = 0; i < rowNumber; i++) {
			double[] column = extractMatrixElements(paddedDataArrayMatrix, false, i);
			paddedDataArrayMatrixMaxIndices[i] = UtilityFunctions.findIndexOfValue(column, UtilityFunctions.getMaxValueOfArray(column));
		}
		return paddedDataArrayMatrixMaxIndices;
	}

	public static void applyAmplitudeThreshold(SimpleMatrix localMaximumMatrix, SimpleMatrix waveletCoefficientsWithData, WaveletPeakDetectorSettings configuration) {

		double amplitudeThreshold = calculateAmplitudeThreshold(waveletCoefficientsWithData, configuration);
		DMatrixIterator matrixIterator = localMaximumMatrix.iterator(false, 0, 0, localMaximumMatrix.numRows() - 1, localMaximumMatrix.numCols() - 1);
		while (matrixIterator.hasNext()) {
			matrixIterator.next();
			int index = matrixIterator.getIndex();
			if(waveletCoefficientsWithData.get(index) < amplitudeThreshold) {
				localMaximumMatrix.set(index, 0);
			}
		}
	}

	public static void determineLocalMaxima(double[] localMaximum, int windowSize, List<? extends SpectrumSignal> signals) {

		List<Integer> maxIndices = new ArrayList<Integer>();
		List<Integer> selectedIndices = new ArrayList<Integer>();
		for(int i = 0; i < localMaximum.length; i++) {
			if(localMaximum[i] > 0) {
				maxIndices.add(i);
			}
		}
		// calculate differences between pairs of consecutive elements and check for
		// maxima within windowSize
		for(int i = 0; i < maxIndices.size() - 1; i++) {
			if((maxIndices.get(i + 1) - maxIndices.get(i)) < windowSize) {
				selectedIndices.add(i);
			}
		}
		WaveletPeakDetectorMaximaUtils.checkForMaximaWithinWindowSize(maxIndices, selectedIndices, signals, localMaximum);
	}

	private static void checkForMaximaWithinWindowSize(List<Integer> maxIndices, List<Integer> selectedIndices, List<? extends SpectrumSignal> signals, double[] localMaximum) {

		List<Integer> selectedIndicesA = new ArrayList<Integer>();
		List<Integer> selectedIndicesB = new ArrayList<Integer>();
		double[] spectralData = WaveletPeakDetectorCWTUtils.getAbsorptiveIntensity(signals);
		double[] selectedIndicesDifference = new double[selectedIndices.size()];
		if(selectedIndices.size() > 0) {
			for(int i = 0; i < selectedIndices.size() - 1; i++) {
				selectedIndicesA.add(maxIndices.get(selectedIndices.get(i)));
				selectedIndicesB.add(maxIndices.get(selectedIndices.get(i) + 1));
			}
			for(int i = 0; i < selectedIndices.size() - 1; i++) {
				selectedIndicesDifference[i] = spectralData[selectedIndicesA.get(i)] - spectralData[selectedIndicesB.get(i)];
			}
			for(int i = 0; i < selectedIndicesDifference.length - 1; i++) {
				if(selectedIndicesDifference[i] <= 0) {
					localMaximum[selectedIndicesA.get(i)] = 0;
				}
				if(selectedIndicesDifference[i] > 0) {
					localMaximum[selectedIndicesB.get(i)] = 0;
				}
			}
		}
	}

	public static void combineLocatedMaximaResults(double[] localMaximum, double[] tempLocalMaximum) {
		for(int i = 0; i < tempLocalMaximum.length; i++) {
			if(tempLocalMaximum[i] == 1) {
				localMaximum[i] = 1;
			}
		}
	}

	public static double[] locateMaxima(int c, int rowNumber, int windowSize, int dataLength, int shiftDataValue, SimpleMatrix waveletCoefficientsWithData) {

		double[] localDataArray = extractMatrixElements(waveletCoefficientsWithData, false, c);
		double lastColumnValue = waveletCoefficientsWithData.get(dataLength - 1, c);

		double[] paddedDataArray = null;
		SimpleMatrix paddedDataArrayMatrix = null;
		if(shiftDataValue == Integer.MIN_VALUE) {
			// without shift
			int smallArraySize = rowNumber * windowSize - dataLength;
			double[] smallArray = new double[smallArraySize];
			for(int i = 0; i < smallArraySize; i++) {
				smallArray[i] = lastColumnValue;
			}
			paddedDataArray = ArrayUtils.addAll(localDataArray, smallArray);
			paddedDataArrayMatrix = new SimpleMatrix(windowSize, (paddedDataArray.length / windowSize));
		} else {
			// with shift
			double firstColumnValue = waveletCoefficientsWithData.get(0, c);
			double[] frontArray = new double[shiftDataValue];
			double[] backArray = new double[rowNumber * windowSize - dataLength - shiftDataValue];
			for(int i = 0; i < frontArray.length; i++) {
				frontArray[i] = firstColumnValue;
			}
			for(int i = 0; i < backArray.length; i++) {
				backArray[i] = lastColumnValue;
			}
			paddedDataArray = ArrayUtils.addAll(frontArray, localDataArray);
			paddedDataArray = ArrayUtils.addAll(paddedDataArray, backArray);
			paddedDataArrayMatrix = new SimpleMatrix(windowSize, (paddedDataArray.length / windowSize));
		}
		// transform array into matrix with columns[windowSize]
		WaveletPeakDetectorMaximaUtils.transformArrayIntoMatrix(paddedDataArrayMatrix, rowNumber, paddedDataArray);
		// get max per column
		double[] paddedDataArrayMatrixMaxIndices = getMaxValuePerColumn(rowNumber, paddedDataArrayMatrix);
		// check if maximum is bigger than column bounds
		boolean[] maximumBiggerThanBounds = checkIfMaximumIsBiggerThanColumnBounds(paddedDataArrayMatrixMaxIndices.length, rowNumber, paddedDataArrayMatrix);
		// and extract selected indices and return 'localMaximum'
		return WaveletPeakDetectorMaximaUtils.extractSelectedIndices(maximumBiggerThanBounds, windowSize, paddedDataArrayMatrixMaxIndices, shiftDataValue, waveletCoefficientsWithData.numRows());
	}

	private static double[] extractSelectedIndices(boolean[] maximumBiggerThanBounds, int windowSize, double[] paddedDataArrayMatrixMaxIndices, int shiftDataValue, int size) {

		double[] localMaximum = new double[size];
		List<Integer> selectedIndices = new ArrayList<Integer>();
		int index = 0;
		for(boolean b : maximumBiggerThanBounds) {
			if(b) {
				selectedIndices.add(index);
			}
			index++;
		}
		int i = 0;
		while (i < selectedIndices.size()) {
			if(selectedIndices.get(i).intValue() > 0) { // check difference here
				int product = (selectedIndices.get(i).intValue() - 1) * windowSize;
				int summand = 0;
				if(shiftDataValue == Integer.MIN_VALUE) {
					summand = (int) paddedDataArrayMatrixMaxIndices[selectedIndices.get(i).intValue()];
				} else {
					summand = (int) paddedDataArrayMatrixMaxIndices[selectedIndices.get(i).intValue()] - shiftDataValue;
				}
				int position = (product + summand);
				if(position < 0) {
					// check for negative position and restore index
					position = position + shiftDataValue;
				}
				localMaximum[Math.abs(position)] = 1;
			}
			i++;
		}
		return localMaximum;
	}

	private static void transformArrayIntoMatrix(SimpleMatrix paddedDataArrayMatrix, int rowNumber, double[] paddedDataArray) {

		int copyFrom = 0;
		int copyTo = 5;
		for(int i = 0; i < rowNumber; i++) {
			double[] copy = Arrays.copyOfRange(paddedDataArray, copyFrom, copyTo);
			if(i == rowNumber - 1) {
				double append = paddedDataArray[paddedDataArray.length - 1];
				ArrayUtils.addAll(copy, new double[] { append });
			}
			paddedDataArrayMatrix.setColumn(i, 0, copy);
			copyFrom = copyFrom + 5;
			copyTo = copyTo + 5;
		}
	}

}

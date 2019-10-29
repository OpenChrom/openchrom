package net.openchrom.nmr.processing.peakdetection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.chemclipse.nmr.model.core.SpectrumSignal;
import org.ejml.data.MatrixIterator64F;
import org.ejml.simple.SimpleMatrix;

import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions;

public class WaveletPeakDetectorMaxima {

	public static SimpleMatrix calculateLocalMaxima(List<? extends SpectrumSignal> signals, SimpleMatrix waveletCoefficients, WaveletPeakDetectorSettings configuration) {

		// prepare data for search
		SimpleMatrix waveletCoefficientsWithData = prepareDataForSearch(signals, waveletCoefficients);
		int[] localPsiScales = ArrayUtils.addAll(new int[] { 0 }, configuration.getPsiScales());

		// calculate threshold for later sorting purpose
		double amplitudeThreshold = calculateAmplitudeThreshold(waveletCoefficientsWithData, configuration);

		int minimumWindowSize = 5;
		SimpleMatrix localMaximumMatrix = new SimpleMatrix(waveletCoefficientsWithData.numRows(), waveletCoefficientsWithData.numCols());
		double[] localMaximum = new double[signals.size()];

		// search column-wise for maximum within the specified window
		for(int s = 0; s < localPsiScales.length; s++) {
			int currentScale = localPsiScales[s];
			int windowSize = calculateWindowSize(currentScale, minimumWindowSize);

			// search using column and windowSize
			for(int c = 0; c < waveletCoefficientsWithData.numCols(); c++) {
				int shiftDataValue = Integer.MIN_VALUE;
				int dataLength = waveletCoefficientsWithData.numRows();
				int rowNumber = (int) Math.ceil((double) dataLength / windowSize);

				localMaximum = locateMaxima(c, rowNumber, windowSize, dataLength, shiftDataValue, waveletCoefficientsWithData);

				shiftDataValue = (int) Math.floor(windowSize / 2);
				rowNumber = (int) Math.ceil((double) (dataLength + shiftDataValue) / windowSize);

				//
				double[] tempLocalMaximum = locateMaxima(c, rowNumber, windowSize, dataLength, shiftDataValue, waveletCoefficientsWithData);
				// combine results from both locateMaxima() calls
				for(int i = 0; i < tempLocalMaximum.length; i++) {
					if(tempLocalMaximum[i] == 1) {
						localMaximum[i] = 1;
					}
				}

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
				List<Integer> selectedIndicesA = new ArrayList<Integer>();
				List<Integer> selectedIndicesB = new ArrayList<Integer>();
				double[] spectralData = extractAbsorptiveSignalIntensity(signals);
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
				localMaximumMatrix.setColumn(c, 0, localMaximum);
			}
		}

		// localMaximumMatrix.print(); // before application of threshold
		// for every coefficient < amplitudeThreshold set (possible) local maximum to 0
		MatrixIterator64F matrixIterator = localMaximumMatrix.iterator(false, 0, 0, localMaximumMatrix.numRows() - 1, localMaximumMatrix.numCols() - 1);
		while (matrixIterator.hasNext()) {
			matrixIterator.next();
			int index = matrixIterator.getIndex();
			if(waveletCoefficientsWithData.get(index) < amplitudeThreshold) {
				localMaximumMatrix.set(index, 0);
			}
		}

		// localMaximumMatrix.print(); // after application of threshold => choosing
		// carefully to not sort out too many peaks
		return localMaximumMatrix;
	}

	private static double[] locateMaxima(int c, int rowNumber, int windowSize, int dataLength, int shiftDataValue, SimpleMatrix waveletCoefficientsWithData) {

		double[] localMaximum = new double[waveletCoefficientsWithData.numRows()];

		double[] localDataArray = waveletCoefficientsWithData.extractVector(false, c).getMatrix().getData();
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
		int copyFrom = 0;
		int copyTo = 5;
		for(int i = 0; i < rowNumber; i++) {
			// System.out.println(copyFrom + " - " + copyTo);
			double[] copy = Arrays.copyOfRange(paddedDataArray, copyFrom, copyTo);
			// System.out.println(Arrays.toString(copy) + " Anzahl " + copy.length);

			if(i == rowNumber - 1) {
				double append = paddedDataArray[paddedDataArray.length - 1];
				ArrayUtils.addAll(copy, new double[] { append });
			}
			paddedDataArrayMatrix.setColumn(i, 0, copy);
			copyFrom = copyFrom + 5;
			copyTo = copyTo + 5;
		}

		// get max per column
		double[] paddedDataArrayMatrixMaxIndices = new double[paddedDataArrayMatrix.numCols()];
		for(int i = 0; i < rowNumber; i++) {
			double[] column = paddedDataArrayMatrix.extractVector(false, i).getMatrix().getData();
			// System.out.println(Arrays.toString(column));
			paddedDataArrayMatrixMaxIndices[i] = UtilityFunctions.findIndexOfValue(column, UtilityFunctions.getMaxValueOfArray(column));
		}

		// check if maximum is bigger than column bounds
		boolean[] maximumBiggerThanBounds = new boolean[paddedDataArrayMatrixMaxIndices.length];
		for(int i = 0; i < rowNumber; i++) {
			double[] column = paddedDataArrayMatrix.extractVector(false, i).getMatrix().getData();
			double max = UtilityFunctions.getMaxValueOfArray(column);
			double lowBound = column[0];
			double highBound = column[column.length - 1];
			if(max > lowBound && max > highBound) {
				maximumBiggerThanBounds[i] = true;
			} else {
				maximumBiggerThanBounds[i] = false;
			}
		}

		// and extract selected indices
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
			if(selectedIndices.get(i).intValue() > 0) {

				// check difference here

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
				// selectedIndices.set(i, product);
			}
			i++;
		}
		return localMaximum;
	}

	private static int calculateWindowSize(int currentScale, int minimumWindowSize) {

		int windowSize = currentScale * 2 + 1;
		if(windowSize < minimumWindowSize) {
			windowSize = minimumWindowSize;
		}
		return windowSize;
	}

	private static SimpleMatrix prepareDataForSearch(List<? extends SpectrumSignal> signals, SimpleMatrix waveletCoefficients) {

		double[] dataArray = extractAbsorptiveSignalIntensity(signals);
		SimpleMatrix dataArrayMatrix = new SimpleMatrix(signals.size(), 1);
		dataArrayMatrix.setColumn(0, 0, dataArray);
		SimpleMatrix waveletCoefficientsWithData = new SimpleMatrix(waveletCoefficients.numRows(), waveletCoefficients.numCols() + 1);
		waveletCoefficientsWithData.insertIntoThis(0, 0, dataArrayMatrix);
		waveletCoefficientsWithData.insertIntoThis(0, 1, waveletCoefficients);
		return waveletCoefficientsWithData;
	}

	private static double[] extractAbsorptiveSignalIntensity(List<? extends SpectrumSignal> signals) {

		double[] dataArray = new double[signals.size()];
		int position = 0;
		for(SpectrumSignal signal : signals) {
			dataArray[position] = signal.getAbsorptiveIntensity().doubleValue();
			position++;
		}
		return dataArray;
	}

	private static double calculateAmplitudeThreshold(SimpleMatrix waveletCoefficientsWithData, WaveletPeakDetectorSettings configuration) {

		double amplitudeThreshold = Double.MIN_VALUE;
		if(configuration.getAmplitudeThreshold() == 0) {
			amplitudeThreshold = 0;
		} else {
			amplitudeThreshold = identifyMaxCoefficient(waveletCoefficientsWithData) * configuration.getAmplitudeThreshold();
		}
		return amplitudeThreshold;

	}

	private static double identifyMaxCoefficient(SimpleMatrix waveletCoefficients) {

		double result = Double.MIN_VALUE;
		for(int r = 0; r < waveletCoefficients.numRows(); r++) {
			double[] rowVector = waveletCoefficients.extractVector(true, r).getMatrix().getData();
			double maxValue = UtilityFunctions.getMaxValueOfArray(rowVector);
			if(maxValue > result) {
				result = maxValue;
			}
		}
		return result;
	}
}

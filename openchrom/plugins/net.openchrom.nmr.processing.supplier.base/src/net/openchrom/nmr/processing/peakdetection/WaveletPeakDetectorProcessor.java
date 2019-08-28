/*******************************************************************************
 * Copyright (c) 2019 Alexander Stark.
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
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.chemclipse.model.core.IMeasurement;
import org.eclipse.chemclipse.model.core.PeakList;
import org.eclipse.chemclipse.model.core.PeakPosition;
import org.eclipse.chemclipse.model.detector.IMeasurementPeakDetector;
import org.eclipse.chemclipse.nmr.model.core.SpectrumMeasurement;
import org.eclipse.chemclipse.nmr.model.core.SpectrumSignal;
import org.eclipse.chemclipse.processing.core.MessageConsumer;
import org.eclipse.chemclipse.processing.detector.Detector;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.ejml.simple.SimpleMatrix;
import org.osgi.service.component.annotations.Component;

import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions;

@Component(service = { Detector.class, IMeasurementPeakDetector.class })
public class WaveletPeakDetectorProcessor implements IMeasurementPeakDetector<WaveletPeakDetectorSettings> {

	/*
	 * Reference for the Wavelet Peak Detector:
	 *
	 * Du, Pan et al. Bioinformatics 22, Nr. 17 (1. September 2006): 2059–65.
	 */
	private static final String NAME = "Wavelet Peak Detector";
	private static final int[] psiScales = { 1, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, //
			36, 40, 44, 48, 52, 56, 60, 64 };

	@Override
	public String getName() {

		return NAME;
	}

	@Override
	public Class<WaveletPeakDetectorSettings> getConfigClass() {

		return WaveletPeakDetectorSettings.class;
	}

	@Override
	public <T extends IMeasurement> Map<T, PeakList> detectIMeasurementPeaks(Collection<T> detectorInputItems,
			WaveletPeakDetectorSettings configuration, MessageConsumer messageConsumer, IProgressMonitor monitor)
			throws IllegalArgumentException {

		if (configuration == null) {
			configuration = new WaveletPeakDetectorSettings();
		}
		SubMonitor convert = SubMonitor.convert(monitor, getName(), detectorInputItems.size() * 100);
		LinkedHashMap<T, PeakList> map = new LinkedHashMap<>();
		for (T measurement : detectorInputItems) {
			if (measurement instanceof SpectrumMeasurement) {
				map.put(measurement, detect(((SpectrumMeasurement) measurement).getSignals(), configuration,
						messageConsumer, convert.split(100)));
			}
		}
		return map;
	}

	private PeakList detect(List<? extends SpectrumSignal> signals, WaveletPeakDetectorSettings configuration,
			MessageConsumer messageConsumer, IProgressMonitor monitor) {

		/*
		 * TODO detect the peaks
		 */
		SimpleMatrix waveletCoefficients = WaveletPeakDetectorCWT.calculateWaveletCoefficients(signals, psiScales);
		//
		SimpleMatrix localMaxima = calculateLocalMaxima(signals, waveletCoefficients, psiScales, configuration);
		//
		SubMonitor subMonitor = SubMonitor.convert(monitor, signals.size());
		List<PeakPosition> peakPositions = new ArrayList<>();
		int index = 0;
		for (SpectrumSignal signal : signals) {
			@SuppressWarnings("unused")
			double x = signal.getX();
			@SuppressWarnings("unused")
			double y = signal.getY();
			subMonitor.worked(1);
			if (index == 100) {
				peakPositions.add(new WavletPeakPosition());
			}
		}
		return new PeakList(peakPositions, getID(), getName(), getDescription());
	}

	/*
	 * WIP maximum part
	 */

	private SimpleMatrix calculateLocalMaxima(List<? extends SpectrumSignal> signals, SimpleMatrix waveletCoefficients,
			int[] psiScales, WaveletPeakDetectorSettings configuration) {

		// prepare data for search
		SimpleMatrix waveletCoefficientsWithData = prepareDataForSearch(signals, waveletCoefficients);
		int[] localPsiScales = ArrayUtils.addAll(new int[] { 0 }, psiScales);

		// calculate threshold for later sorting purpose
		double amplitudeThreshold = calculateAmplitudeThreshold(waveletCoefficientsWithData, configuration);

		int minimumWindowSize = 5;
		double[] localMaximum = new double[signals.size()];

		// search column-wise for maximum within the specified window
		for (int s = 0; s < localPsiScales.length; s++) {
			int currentScale = localPsiScales[s];
			int windowSize = calculateWindowSize(currentScale, minimumWindowSize);

			// search using column and windowSize
			for (int c = 0; c < waveletCoefficientsWithData.numCols(); c++) {
				int shiftDataValue = Integer.MIN_VALUE;
				int dataLength = waveletCoefficientsWithData.numRows();
				int rowNumber = (int) Math.ceil((double) dataLength / windowSize);

				localMaximum = locateMaxima(c, rowNumber, windowSize, dataLength, shiftDataValue,
						waveletCoefficientsWithData);

				shiftDataValue = (int) Math.floor(windowSize / 2);
				rowNumber = (int) Math.ceil((double) (dataLength + shiftDataValue) / windowSize);

				//
				double[] temp = locateMaxima(c, rowNumber, windowSize, dataLength, shiftDataValue,
						waveletCoefficientsWithData);

				int breakPoint = 1;

			}

		}

		// alle Maxima für die Koeff < amplitudeThreshold gilt => auf 0 setzen
		if (1 < amplitudeThreshold) {
			// placeholder
		}
		return null;
	}

	private static double[] locateMaxima(int c, int rowNumber, int windowSize, int dataLength, int shiftDataValue,
			SimpleMatrix waveletCoefficientsWithData) {

		double[] localMaximum = new double[waveletCoefficientsWithData.numRows()];

		double[] localDataArray = waveletCoefficientsWithData.extractVector(false, c).getMatrix().getData();
		double lastColumnValue = waveletCoefficientsWithData.get(dataLength - 1, c);

		double[] paddedDataArray = null;
		SimpleMatrix paddedDataArrayMatrix = null;
		if (shiftDataValue == Integer.MIN_VALUE) {
			// without shift
			int smallArraySize = rowNumber * windowSize - dataLength;
			double[] smallArray = new double[smallArraySize];
			for (int i = 0; i < smallArraySize; i++) {
				smallArray[i] = lastColumnValue;
			}
			paddedDataArray = ArrayUtils.addAll(localDataArray, smallArray);
			paddedDataArrayMatrix = new SimpleMatrix(windowSize, (paddedDataArray.length / windowSize));
			System.out.println(paddedDataArray.length);
		} else {
			// with shift
			double firstColumnValue = waveletCoefficientsWithData.get(0, c);
			double[] frontArray = new double[shiftDataValue];
			double[] backArray = new double[rowNumber * windowSize - dataLength - shiftDataValue];
			for (int i = 0; i < frontArray.length; i++) {
				frontArray[i] = firstColumnValue;
			}
			for (int i = 0; i < backArray.length; i++) {
				backArray[i] = lastColumnValue;
			}
			paddedDataArray = ArrayUtils.addAll(frontArray, localDataArray);
			paddedDataArray = ArrayUtils.addAll(paddedDataArray, backArray);
			paddedDataArrayMatrix = new SimpleMatrix(windowSize, (paddedDataArray.length / windowSize));
			System.out.println(paddedDataArray.length);

		}

		// transform array into matrix with columns[windowSize]
		int copyFrom = 0;
		int copyTo = 5;
		for (int i = 0; i < rowNumber; i++) {
			// System.out.println(copyFrom + " - " + copyTo);
			double[] copy = Arrays.copyOfRange(paddedDataArray, copyFrom, copyTo);
			// System.out.println(Arrays.toString(copy) + " Anzahl " + copy.length);

			if (i == rowNumber - 1) {
				double append = paddedDataArray[paddedDataArray.length - 1];
				ArrayUtils.addAll(copy, new double[] { append });
			}
			paddedDataArrayMatrix.setColumn(i, 0, copy);
			copyFrom = copyFrom + 5;
			copyTo = copyTo + 5;
		}

		// get max per column
		double[] paddedDataArrayMatrixMaxIndices = new double[paddedDataArrayMatrix.numCols()];
		for (int i = 0; i < rowNumber; i++) {
			double[] column = paddedDataArrayMatrix.extractVector(false, i).getMatrix().getData();
			// System.out.println(Arrays.toString(column));
			paddedDataArrayMatrixMaxIndices[i] = UtilityFunctions.findIndexOfValue(column,
					UtilityFunctions.getMaxValueOfArray(column));
		}

		// check if maximum is bigger than column bounds
		boolean[] maximumBiggerThanBounds = new boolean[paddedDataArrayMatrixMaxIndices.length];
		for (int i = 0; i < rowNumber; i++) {
			double[] column = paddedDataArrayMatrix.extractVector(false, i).getMatrix().getData();
			double max = UtilityFunctions.getMaxValueOfArray(column);
			double lowBound = column[0];
			double highBound = column[column.length - 1];
			if (max > lowBound && max > highBound) {
				maximumBiggerThanBounds[i] = true;
			} else {
				maximumBiggerThanBounds[i] = false;
			}
		}
		// and extract selected indices
		List<Integer> selectedIndices = new ArrayList<Integer>();
		int index = 0;
		for (boolean b : maximumBiggerThanBounds) {
			if (b) {
				selectedIndices.add(index);
			}
			index++;
		}
		int i = 0;
		while (i < selectedIndices.size()) {
			if (selectedIndices.get(i).intValue() > 0) {
				int product = (selectedIndices.get(i).intValue() - 1) * windowSize;
				int position = (int) (product + paddedDataArrayMatrixMaxIndices[selectedIndices.get(i).intValue()]);
				localMaximum[position] = 1;
				// selectedIndices.set(i, product);
			}
			i++;
		}
		return localMaximum;
	}

	private static int calculateWindowSize(int currentScale, int minimumWindowSize) {

		int windowSize = currentScale * 2 + 1;
		if (windowSize < minimumWindowSize) {
			windowSize = minimumWindowSize;
		}
		return windowSize;
	}

	private static SimpleMatrix prepareDataForSearch(List<? extends SpectrumSignal> signals,
			SimpleMatrix waveletCoefficients) {

		double[] dataArray = new double[signals.size()];
		int position = 0;
		for (SpectrumSignal signal : signals) {
			dataArray[position] = signal.getAbsorptiveIntensity().doubleValue();
			position++;
		}
		SimpleMatrix dataArrayMatrix = new SimpleMatrix(signals.size(), 1);
		dataArrayMatrix.setColumn(0, 0, dataArray);
		SimpleMatrix waveletCoefficientsWithData = new SimpleMatrix(waveletCoefficients.numRows(),
				waveletCoefficients.numCols() + 1);
		waveletCoefficientsWithData.insertIntoThis(0, 0, dataArrayMatrix);
		waveletCoefficientsWithData.insertIntoThis(0, 1, waveletCoefficients);
		return waveletCoefficientsWithData;
	}

	private static double calculateAmplitudeThreshold(SimpleMatrix waveletCoefficientsWithData,
			WaveletPeakDetectorSettings configuration) {

		double amplitudeThreshold = Double.MIN_VALUE;
		if (configuration.getAmplitudeThreshold() == 0) {
			amplitudeThreshold = 0;
		} else {
			amplitudeThreshold = identifyMaxCoefficient(waveletCoefficientsWithData)
					* configuration.getAmplitudeThreshold();
		}
		return amplitudeThreshold;

	}

	private static double identifyMaxCoefficient(SimpleMatrix waveletCoefficients) {

		double result = Double.MIN_VALUE;
		for (int r = 0; r < waveletCoefficients.numRows(); r++) {
			double[] rowVector = waveletCoefficients.extractVector(true, r).getMatrix().getData();
			double maxValue = UtilityFunctions.getMaxValueOfArray(rowVector);
			if (maxValue > result) {
				result = maxValue;
			}
		}
		return result;
	}

	/*
	 * WIP maximum part end
	 */

	@Override
	public boolean acceptsIMeasurements(Collection<? extends IMeasurement> items) {

		for (IMeasurement measurement : items) {
			if (!(measurement instanceof SpectrumMeasurement)) {
				return false;
			}
		}
		return true;
	}

}

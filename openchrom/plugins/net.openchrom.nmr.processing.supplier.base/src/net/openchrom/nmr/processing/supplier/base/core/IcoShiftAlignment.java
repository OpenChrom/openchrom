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
 * Jan Holy - implementation
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.core;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.stat.descriptive.rank.Median;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import org.eclipse.chemclipse.nmr.model.core.IMeasurementNMR;
import org.eclipse.chemclipse.nmr.model.selection.DataNMRSelection;
import org.eclipse.chemclipse.nmr.model.selection.IDataNMRSelection;
import org.eclipse.chemclipse.nmr.model.support.ISignalExtractor;
import org.eclipse.chemclipse.nmr.model.support.SignalExtractor;
import org.ejml.simple.SimpleMatrix;

public class IcoShiftAlignment {

	/*
	 * settings
	 */
	private IcoShiftAlignmentSettings settings;
	/*
	 * icoshift algorithm (interval-correlation-shifting)
	 * ---
	 * based on: Savorani et al., J. Magn. Reson. 202, Nr. 2 (1. Februar 2010): 190–202.
	 */
	/*
	 * internals property
	 */
	private int[] referenceWindow;

	public IcoShiftAlignment() {

		super();
	}

	public SimpleMatrix process(List<IMeasurementNMR> experimentalDatasetsList, IcoShiftAlignmentSettings settings) {

		// imported real parts of spectra
		SimpleMatrix experimentalDatasetsMatrix = extractMultipleSpectra(experimentalDatasetsList);
		/*
		 * settings end
		 */
		// calculate intervals according to settings
		SortedMap<Integer, Interval> intervalRegionsMap = calculateIntervals(experimentalDatasetsList, settings);
		return process(experimentalDatasetsMatrix, intervalRegionsMap, settings);
	}

	public SimpleMatrix process(SimpleMatrix experimentalDatasetsMatrix, SortedMap<Integer, Interval> intervalRegionsMap, IcoShiftAlignmentSettings settings) {

		this.settings = settings;
		if(!settings.getAlignmentType().equals(AlignmentType.WHOLE_SPECTRUM) && settings.isPrelimiterCoShifting()) {
			experimentalDatasetsMatrix = executePreliminaryCoShifting(experimentalDatasetsMatrix);
		}
		// check after calculation of intervals
		ShiftCorrectionType shiftCorrectionType = settings.getShiftCorrectionType();
		if(shiftCorrectionType.equals(ShiftCorrectionType.USER_DEFINED)) {
			checkShiftCorrectionTypeValueSize(intervalRegionsMap, settings.getShiftCorrectionTypeValue());
		}
		//
		SimpleMatrix alignedDatasets = new SimpleMatrix(experimentalDatasetsMatrix.numRows(), experimentalDatasetsMatrix.numCols());
		//
		AlignmentType intervalSelection = settings.getAlignmentType();
		switch(intervalSelection) {
			case SINGLE_PEAK:
				//
				alignedDatasets = alignOneInterval(intervalRegionsMap, experimentalDatasetsMatrix);
				break;
			case NUMBER_OF_INTERVALS:
				//
				alignedDatasets = alignSeveralIntervals(intervalRegionsMap, experimentalDatasetsMatrix);
				break;
			case INTERVAL_LENGTH:
				//
				alignedDatasets = alignSeveralIntervals(intervalRegionsMap, experimentalDatasetsMatrix);
				break;
			case WHOLE_SPECTRUM:
				//
				alignedDatasets = alignOneInterval(intervalRegionsMap, experimentalDatasetsMatrix);
				break;
			case USER_DEFINED_INTERVALS:
				//
				alignedDatasets = alignSeveralIntervals(intervalRegionsMap, experimentalDatasetsMatrix);
				break;
		}
		return alignedDatasets;
	}

	private SimpleMatrix executePreliminaryCoShifting(SimpleMatrix experimentalDatasetsMatrix) {

		SimpleMatrix result = new SimpleMatrix(experimentalDatasetsMatrix.numRows(), experimentalDatasetsMatrix.numCols());
		// change settings for preliminary Co-Shifting
		IcoShiftAlignmentSettings settings = new IcoShiftAlignmentSettings();
		settings.setTargetCalculationSelection(TargetCalculationSelection.MEAN); // or MEDIAN
		settings.setAligmentType(AlignmentType.WHOLE_SPECTRUM);
		settings.setShiftCorrectionType(ShiftCorrectionType.FAST);
		settings.setGapFillingType(GapFillingType.MARGIN);
		SortedMap<Integer, Interval> intervalRegionsMap = new TreeMap<>();
		int lengthOfDataset = experimentalDatasetsMatrix.numCols();
		intervalRegionsMap.put(0, new Interval(0, lengthOfDataset - 1));
		result = alignSeveralIntervals(intervalRegionsMap, experimentalDatasetsMatrix);
		//
		return result;
	}

	private double[] calculateSelectedTarget(SimpleMatrix experimentalDatasetsMatrix) {

		double[] targetSpectrum = new double[experimentalDatasetsMatrix.numCols()];
		TargetCalculationSelection targetCalculationSelection = settings.getTargetCalculationSelection();
		switch(targetCalculationSelection) {
			case MEAN:
				//
				targetSpectrum = calculateMeanTarget(experimentalDatasetsMatrix);
				break;
			case MEDIAN:
				//
				targetSpectrum = calculateMedianTarget(experimentalDatasetsMatrix);
				break;
			case MAX:
				//
				targetSpectrum = calculateMaxTarget(experimentalDatasetsMatrix);
				break;
		}
		return targetSpectrum;
	}

	private double[] calculateMeanTarget(SimpleMatrix experimentalDatasetsMatrix) {

		/*
		 * mean array
		 */
		int numColsMax = experimentalDatasetsMatrix.numCols();
		int numRowsMax = experimentalDatasetsMatrix.numRows();
		double[] columnSumArray = new double[numColsMax];
		for(int c = 0; c < numColsMax; c++) {
			// step through each column and sum matrix column-wise up
			for(int r = 0; r < numRowsMax; r++) {
				columnSumArray[c] = columnSumArray[c] + experimentalDatasetsMatrix.get(r, c);
			}
			columnSumArray[c] = columnSumArray[c] / numRowsMax;
		}
		return columnSumArray;
	}

	private double[] calculateMedianTarget(SimpleMatrix experimentalDatasetsMatrix) {

		/*
		 * median array
		 */
		// create an object of Median class
		Median median = new Median();
		//
		int numColsMax = experimentalDatasetsMatrix.numCols();
		double[] columnArray = new double[numColsMax];
		for(int c = 0; c < numColsMax; c++) {
			// calculate median for each column
			SimpleMatrix matrixColumnVector = experimentalDatasetsMatrix.extractVector(false, c);
			double[] columnVector = matrixColumnVector.getMatrix().getData();
			// evaluation of median
			double evaluation = median.evaluate(columnVector);
			columnArray[c] = evaluation;
		}
		return columnArray;
	}

	private double[] calculateMaxTarget(SimpleMatrix experimentalDatasetsMatrix) {

		/*
		 * max array
		 */
		int numRowsMax = experimentalDatasetsMatrix.numRows();
		double[] rowArraySum = new double[numRowsMax];
		for(int r = 0; r < numRowsMax; r++) {
			// extract each row
			SimpleMatrix matrixRowVector = experimentalDatasetsMatrix.extractVector(true, r);
			double[] rowVector = matrixRowVector.getMatrix().getData();
			// sum of row
			rowArraySum[r] = Arrays.stream(rowVector).sum();
		}
		UtilityFunctions utilityFunction = new UtilityFunctions();
		double maxRowValue = utilityFunction.getMaxValueOfArray(rowArraySum);
		int maxTargetIndex = utilityFunction.findIndexOfValue(rowArraySum, maxRowValue);
		double[] maxTarget = experimentalDatasetsMatrix.extractVector(true, maxTargetIndex).getMatrix().getData();
		return maxTarget;
	}

	private SimpleMatrix extractMultipleSpectra(List<IMeasurementNMR> experimentalDatasetsList) {

		// List<Object> experimentalDatasetsList = new ArrayList<Object>();
		// experimentalDatasetsList = importMultipleDatasets(experimentalDatasets);
		//
		boolean firstDataset = true;
		SimpleMatrix experimentalDatasetsMatrix = null;
		int matrixRow = 0;
		//
		for(IMeasurementNMR measurementNMR : experimentalDatasetsList) {
			// prepare matrix for storage of spectra >once< => aiming for comparison each spectrum should have the same size
			if(firstDataset) {
				int numberOfDatasets = experimentalDatasetsList.size();
				int datapointsPerDataset = measurementNMR.getScanMNR().getNumberOfFourierPoints();
				experimentalDatasetsMatrix = new SimpleMatrix(numberOfDatasets, datapointsPerDataset);
				firstDataset = false;
			}
			ISignalExtractor signalExtractor = new SignalExtractor(measurementNMR);
			experimentalDatasetsMatrix.setRow(matrixRow, 0, signalExtractor.extractFourierTransformedDataRealPart());
			matrixRow++;
		}
		return experimentalDatasetsMatrix;
	}

	private SortedMap<Integer, Interval> calculateIntervals(List<IMeasurementNMR> experimentalDatasetsList, IcoShiftAlignmentSettings settings) {

		SortedMap<Integer, Interval> intervalRegionsMap = new TreeMap<>();
		/*
		 * For use with MS data, chromatograms, etc .:
		 * ~~~~~~~
		 * as long as the supplied object has an X-axis and a defined
		 * length, the algorithm should be able to work with it.
		 */
		// Object object = experimentalDatasetsList.get(0);
		IMeasurementNMR measureNMR = experimentalDatasetsList.get(0);
		IDataNMRSelection dataNMRSelect = new DataNMRSelection(measureNMR);
		ISignalExtractor signalExtract = new SignalExtractor(dataNMRSelect);
		double[] chemicalShiftAxis = signalExtract.extractChemicalShift();
		int lengthOfDataset = dataNMRSelect.getMeasurmentNMR().getScanMNR().getNumberOfFourierPoints();
		UtilityFunctions utilityFunction = new UtilityFunctions();
		//
		AlignmentType intervalSelection = settings.getAlignmentType();
		switch(intervalSelection) {
			case SINGLE_PEAK:
				/*
				 * get left and right boundaries in ppm
				 * find indices in data
				 */
				double lowerBorder = settings.getSinglePeakLowerBorder();
				double higherBorder = settings.getSinglePeakHigherBorder();
				int lowerBorderIndex = utilityFunction.findIndexOfValue(chemicalShiftAxis, lowerBorder);
				int higherBorderIndex = utilityFunction.findIndexOfValue(chemicalShiftAxis, higherBorder);
				intervalRegionsMap.put(lowerBorderIndex, new Interval(lowerBorderIndex, higherBorderIndex));
				break;
			case WHOLE_SPECTRUM:
				/*
				 * no user input required
				 * start at index=0, end at index=lengthOfDataset-1
				 */
				intervalRegionsMap.put(0, new Interval(0, lengthOfDataset - 1));
				break;
			case NUMBER_OF_INTERVALS:
				/*
				 * divide present data in number of equal intervals
				 * save every interval in map with left and right boundaries
				 */
				int numberOfIntervals = settings.getNumberOfIntervals();
				//
				int remainingInterval = lengthOfDataset % numberOfIntervals;
				int approxIntervalSpan = (int)Math.floor(lengthOfDataset / numberOfIntervals);
				// int maxValue = (remainingInterval - 1) * (approxIntervalSpan + 1) + 1;
				int intervalSpan = approxIntervalSpan + 1;
				//
				int[] intervalStartsPartA = new int[remainingInterval];
				for(int i = 1; i < remainingInterval; i++) {
					intervalStartsPartA[i] = intervalStartsPartA[i - 1] + intervalSpan;
				}
				//
				int[] intervalStartsPartB = new int[numberOfIntervals - remainingInterval];
				int startOfPartB = (remainingInterval - 1) * (approxIntervalSpan + 1) + 1 + 1 + approxIntervalSpan;
				intervalStartsPartB[0] = startOfPartB - 1;
				for(int i = 1; i < numberOfIntervals - remainingInterval; i++) {
					intervalStartsPartB[i] = intervalStartsPartB[i - 1] + approxIntervalSpan;
				}
				//
				int[] intervalStartValues = ArrayUtils.addAll(intervalStartsPartA, intervalStartsPartB);
				int[] intervalEndValues = new int[numberOfIntervals];
				System.arraycopy(intervalStartValues, 1, intervalEndValues, 0, intervalStartValues.length - 1);
				intervalEndValues[intervalEndValues.length - 1] = lengthOfDataset - 1;
				//
				for(int i = 0; i < numberOfIntervals; i++) {
					intervalRegionsMap.put(intervalStartValues[i], new Interval(intervalStartValues[i], intervalEndValues[i]));
				}
				break;
			case INTERVAL_LENGTH:
				/*
				 * divide present data by the amount of given datapoints (=length of interval) in equal intervals
				 * save every interval in map with left and right boundaries
				 */
				int lengthOfIntervals = (int)(settings.getIntervalLength() / (chemicalShiftAxis[1] - chemicalShiftAxis[0]));
				//
				int numberOfFullIntervals = (int)Math.floor(lengthOfDataset / lengthOfIntervals);
				int[] intervalStarts = new int[numberOfFullIntervals + 1];
				for(int i = 1; i < numberOfFullIntervals + 1; i++) {
					intervalStarts[i] = intervalStarts[i - 1] + lengthOfIntervals - 1;
				}
				int[] intervalEnds = new int[numberOfFullIntervals + 1];
				intervalEnds[0] = lengthOfIntervals - 1;
				for(int i = 1; i < numberOfFullIntervals + 1; i++) {
					intervalEnds[i] = intervalEnds[i - 1] + lengthOfIntervals - 1;
				}
				if(intervalEnds[intervalEnds.length - 1] != lengthOfDataset - 1) {
					intervalEnds[intervalEnds.length - 1] = lengthOfDataset - 1;
				}
				for(int i = 0; i < numberOfFullIntervals + 1; i++) {
					intervalRegionsMap.put(intervalStarts[i], new Interval(intervalStarts[i], intervalEnds[i]));
				}
				break;
			case USER_DEFINED_INTERVALS:
				/*
				 * take a map / import a file / read an integral list... with user defined intervals
				 * the boundaries will be in ppm (?) => double value!; find indices in data
				 * ***
				 * main difference to number/length methods: intervals may be discontiguous!
				 */
				List<ChemicalShiftInterval> userDefIntervalRegions = settings.getUserDefIntervalRegions();
				//
				// intervalSelection.setUserDefIntervalRegions(userDefIntervalRegions);
				// intervalSelection.setUserDefIntervalRegions(4.23, 3.21);
				//
				userDefIntervalRegions.forEach((entry) -> {
					// get indices for each user defined interval
					double higherUserBorder = entry.getStart();
					double lowerUserBorder = entry.getStop();
					// System.out.println(higherUserBorder + "-" + lowerUserBorder);
					int lowerUserBorderIndex = utilityFunction.findIndexOfValue(chemicalShiftAxis, lowerUserBorder);
					int higherUserBorderIndex = utilityFunction.findIndexOfValue(chemicalShiftAxis, higherUserBorder);
					intervalRegionsMap.put(lowerUserBorderIndex, new Interval(lowerUserBorderIndex, higherUserBorderIndex));
				});
				break;
		}
		return intervalRegionsMap;
	}

	public int[] coshiftSpectra(SimpleMatrix experimentalDatasetsMatrix, Interval interval) {

		//
		referenceWindow = new int[interval.getStop() - interval.getStart() + 1];
		for(int i = 0; i < referenceWindow.length; ++i) {
			referenceWindow[i] = referenceWindow[i] + interval.getStart() + i;
		}
		int referenceWindowLength = 0;
		if(Arrays.stream(referenceWindow).allMatch(i -> i >= 0)) {
			referenceWindowLength = referenceWindow.length;
		} else {
			referenceWindowLength = 1;
		}
		// int[] dimensionOfTarget = {1, targetSpectrum.length};
		int[] dimensionOfDataset = {experimentalDatasetsMatrix.numRows(), experimentalDatasetsMatrix.numCols()};
		int[] dimensionOfReferenceWindow = {1, referenceWindowLength};
		//
		int localDimension = 0;
		boolean fastAutomaticSearch = false;
		//
		int shiftCorrectionTypeValue = 0;
		ShiftCorrectionType shiftCorrectionType = settings.getShiftCorrectionType();
		if(shiftCorrectionType.equals(ShiftCorrectionType.USER_DEFINED)) {
			shiftCorrectionTypeValue = settings.getShiftCorrectionTypeValue();
		}
		int sourceStep = 0;
		if(shiftCorrectionType.equals(ShiftCorrectionType.FAST) || shiftCorrectionType.equals(ShiftCorrectionType.BEST)) {
			// switch for the best automatic search on
			if(referenceWindowLength != 1) {
				localDimension = dimensionOfReferenceWindow[1];
			} else {
				localDimension = dimensionOfDataset[1];
			}
			//
			if(shiftCorrectionType.equals(ShiftCorrectionType.FAST)) {
				fastAutomaticSearch = true;
			}
			if(fastAutomaticSearch) {
				shiftCorrectionTypeValue = localDimension - 1;
				sourceStep = Math.round(localDimension / 2) - 1;
			} else {
				// change here the first searching point for the best "n"
				shiftCorrectionTypeValue = (int)Math.max(Math.floor(0.05 * localDimension), 10);
				// change here to define the searching step
				sourceStep = (int)Math.floor(localDimension / 20);
			}
		}
		//
		double blockSize = Math.pow(2, 25);
		double sizeOfDouble = Double.SIZE / 8; // in bytes
		double byteSize = experimentalDatasetsMatrix.getNumElements() * sizeOfDouble;
		int numberOfBlocks = (int)Math.ceil(byteSize / blockSize); // *** of any use!?
		//
		/*
		 * calculate target here
		 * extract needed parts for co-shifting
		 */
		double[] targetForProcessing = null;
		SimpleMatrix experimentalDatasetsMatrixPartForProcessing = extractPartOfDataForProcessing(experimentalDatasetsMatrix);
		//
		TargetCalculationSelection targetCalculationSelection = settings.getTargetCalculationSelection();
		if(targetCalculationSelection.equals(TargetCalculationSelection.MAX)) {
			// calculate max target here for each interval
			targetForProcessing = calculateSelectedTarget(experimentalDatasetsMatrixPartForProcessing);
		} else {
			// "MEAN" and "MEDIAN"
			double[] targetSpectrum = calculateSelectedTarget(experimentalDatasetsMatrix);
			targetForProcessing = extractPartOfDataForProcessing(targetSpectrum);
		}
		/*
		 * Automatic search for the best "shiftCorrectionTypeValue" for each interval
		 */
		int[] shiftCorrectionTypeBorders = {-shiftCorrectionTypeValue, shiftCorrectionTypeValue};
		boolean bestShift = false;
		int bestShiftIteration = 0;
		int[] shiftingValues = new int[experimentalDatasetsMatrixPartForProcessing.numRows()];
		/*
		 * TODO line 122 >>> opt???
		 */
		int shiftCorrectionTypeValueInternal = shiftCorrectionTypeValue;
		while(!bestShift) {
			//
			bestShiftIteration++;
			System.out.println("Searching optimal max. shift: iteration #" + bestShiftIteration);
			for(int i = 0; i < numberOfBlocks; i++) {
				// FFT Co-Shifting
				shiftingValues = doFFTCoShifting(experimentalDatasetsMatrixPartForProcessing, targetForProcessing, shiftCorrectionTypeValueInternal);
			}
			//
			if(shiftCorrectionType.equals(ShiftCorrectionType.FAST) || shiftCorrectionType.equals(ShiftCorrectionType.BEST)) {
				//
				double[] absoluteShiftingValues = new double[shiftingValues.length];
				for(int s = 0; s < shiftingValues.length; s++) {
					absoluteShiftingValues[s] = Math.abs(shiftingValues[s]);
				}
				UtilityFunctions utilityFunction = new UtilityFunctions();
				double tempMax = utilityFunction.getMaxValueOfArray(absoluteShiftingValues);
				int tempNumber = shiftCorrectionTypeBorders[1] + sourceStep;
				//
				if(tempMax == shiftCorrectionTypeBorders[1] && !fastAutomaticSearch) {
					//
					fastAutomaticSearch = tempNumber >= dimensionOfReferenceWindow[1];
				} else if(tempMax < shiftCorrectionTypeBorders[1] && tempNumber < dimensionOfReferenceWindow[1] && !fastAutomaticSearch) {
					//
					fastAutomaticSearch = true;
				} else {
					//
					bestShift = true;
					shiftCorrectionTypeValue = shiftCorrectionTypeBorders[1];
					shiftCorrectionTypeValueInternal = shiftCorrectionTypeValue;
					continue;
				}
				//
				shiftCorrectionTypeBorders[1] = tempNumber;
				shiftCorrectionTypeBorders[0] = -shiftCorrectionTypeBorders[1];
				//
				shiftCorrectionTypeValueInternal = shiftCorrectionTypeBorders[1];
			} else {
				bestShift = true;
			}
			//
		}
		//
		return shiftingValues;
	}

	private SimpleMatrix extractPartOfDataForProcessing(SimpleMatrix data) {

		SimpleMatrix experimentalDatasetsMatrix = data;
		//
		SimpleMatrix experimentalDatasetsMatrixPartForProcessing = new SimpleMatrix(experimentalDatasetsMatrix.numRows(), referenceWindow.length);
		int numberOfRows = experimentalDatasetsMatrixPartForProcessing.numRows();
		int numberOfCols = experimentalDatasetsMatrixPartForProcessing.numCols();
		for(int rowIndex = 0; rowIndex < numberOfRows; rowIndex++) {
			// extract necessary part of data matrix
			for(int c = 0; c < numberOfCols; c++) {
				experimentalDatasetsMatrixPartForProcessing.set(rowIndex, c, experimentalDatasetsMatrix.get(rowIndex, referenceWindow[c]));
			}
		}
		return experimentalDatasetsMatrixPartForProcessing;
	}

	private double[] extractPartOfDataForProcessing(double[] targetSpectrum) {

		int idx = 0;
		double[] targetForProcessing = new double[referenceWindow.length];
		for(int i = referenceWindow[0]; i <= referenceWindow[referenceWindow.length - 1]; i++) {
			// extract necessary part of target vector
			targetForProcessing[idx] = targetSpectrum[i];
			idx++;
		}
		return targetForProcessing;
	}

	private int[] doFFTCoShifting(SimpleMatrix experimentalDatasetsMatrixPartForProcessing, double[] targetForProcessing, int shiftCorrectionTypeValue) {

		/*
		 * normalize data and prepare for calculations
		 */
		SimpleMatrix experimentalDatasetForFFT = normalizeDataBeforeCalculation(experimentalDatasetsMatrixPartForProcessing);
		double[] targetForFFT = normalizeDataBeforeCalculation(targetForProcessing);
		/*
		 * FFT shift Cross Correlation and determination of shifts
		 */
		int[] shiftValues = calculateFFTCrossCorrelation(targetForFFT, experimentalDatasetForFFT, shiftCorrectionTypeValue);
		//
		return shiftValues;
	}

	private double[] normalizeDataBeforeCalculation(double[] targetForProcessing) {

		Double targetNormalization = (double)calculateSquareRootOfSum(targetForProcessing);
		if(targetNormalization.compareTo(0.0) == 0) {//
			targetNormalization = 1.0;
		}
		double[] targetForFFT = new double[targetForProcessing.length];
		for(int i = 0; i < targetForProcessing.length; i++) {
			targetForFFT[i] = targetForProcessing[i] / targetNormalization;
		}
		return targetForFFT;
	}

	private SimpleMatrix normalizeDataBeforeCalculation(SimpleMatrix experimentalDatasetsMatrixPartForProcessing) {

		SimpleMatrix datasetNormalization = calculateSquareRootOfSum(experimentalDatasetsMatrixPartForProcessing);
		for(int r = 0; r < datasetNormalization.numRows(); r++) {
			Double compareValue = datasetNormalization.get(r, 0);
			if(compareValue.compareTo(0.0) == 0) {
				datasetNormalization.set(r, 0, 1);
			}
		}
		int rows = experimentalDatasetsMatrixPartForProcessing.numRows();
		int cols = experimentalDatasetsMatrixPartForProcessing.numCols();
		SimpleMatrix experimentalDatasetForFFT = new SimpleMatrix(rows, cols);
		for(int r = 0; r < rows; r++) {
			for(int c = 0; c < cols; c++) {
				double value = experimentalDatasetsMatrixPartForProcessing.get(r, c);
				value = value / datasetNormalization.get(r, 0);
				experimentalDatasetForFFT.setRow(r, c, value);
			}
		}
		return experimentalDatasetForFFT;
	}

	private SimpleMatrix alignAllDatasets(int[] shiftValues, SimpleMatrix fouriertransformedDatasetCrossCorrelated) {

		SimpleMatrix warpedDataset = new SimpleMatrix(fouriertransformedDatasetCrossCorrelated.numRows(), fouriertransformedDatasetCrossCorrelated.numCols());
		UtilityFunctions utilityFunction = new UtilityFunctions();
		//
		for(int r = 0; r < fouriertransformedDatasetCrossCorrelated.numRows(); r++) {
			//
			double[] shiftArray = fouriertransformedDatasetCrossCorrelated.extractVector(true, r).getMatrix().getData();
			double[] shiftedArray = new double[shiftArray.length]; // initialize with zero
			int end = fouriertransformedDatasetCrossCorrelated.numCols() - 1;
			double[] rowMarginValues = {fouriertransformedDatasetCrossCorrelated.get(r, 0), fouriertransformedDatasetCrossCorrelated.get(r, end)};
			//
			GapFillingType gapFillingType = settings.getGapFillingType();
			if(shiftValues[r] >= 0) {
				// left shift
				if(gapFillingType.equals(GapFillingType.ZERO)) {// fill array here - options: margin value OR 0
					utilityFunction.leftShiftNMRData(shiftArray, shiftValues[r]);
					System.arraycopy(shiftArray, 0, shiftedArray, 0, shiftArray.length - shiftValues[r]);
				} else {
					// margin value
					utilityFunction.leftShiftNMRData(shiftArray, shiftValues[r]);
					Arrays.fill(shiftedArray, rowMarginValues[1]); // fill with number
					System.arraycopy(shiftArray, 0, shiftedArray, 0, shiftArray.length - shiftValues[r]);
				}
			} else {
				// right shift
				if(gapFillingType.equals(GapFillingType.ZERO)) {// fill array here - options: margin value OR 0
					double[] temp = utilityFunction.rightShiftNMRData(shiftArray, Math.abs(shiftValues[r]));
					System.arraycopy(temp, Math.abs(shiftValues[r]), shiftedArray, Math.abs(shiftValues[r]), shiftArray.length - Math.abs(shiftValues[r]));
				} else {
					// margin value
					double[] temp = utilityFunction.rightShiftNMRData(shiftArray, Math.abs(shiftValues[r]));
					Arrays.fill(shiftedArray, rowMarginValues[0]); // fill with number
					System.arraycopy(temp, Math.abs(shiftValues[r]), shiftedArray, Math.abs(shiftValues[r]), shiftArray.length - Math.abs(shiftValues[r]));
				}
			}
			warpedDataset.setRow(r, 0, shiftedArray);
		}
		return warpedDataset;
	}

	public SimpleMatrix alignSeveralIntervals(SortedMap<Integer, Interval> intervalRegionsList, SimpleMatrix experimentalDatasetsMatrix) {

		//
		Iterator<Interval> intervalRegionsMapIterator = intervalRegionsList.values().iterator();
		int[] shiftingValues = new int[experimentalDatasetsMatrix.numRows()];
		SimpleMatrix alignedDatasets = new SimpleMatrix(experimentalDatasetsMatrix.numRows(), experimentalDatasetsMatrix.numCols());
		//
		AlignmentType intervalSelection = settings.getAlignmentType();
		if(intervalSelection.equals(AlignmentType.USER_DEFINED_INTERVALS)) {
			alignedDatasets = experimentalDatasetsMatrix.copy();
		}
		//
		while(intervalRegionsMapIterator.hasNext()) {
			Interval interval = intervalRegionsMapIterator.next();
			System.out.println("Aligning region from index " + interval.getStart() + " to " + interval.getStart());
			shiftingValues = coshiftSpectra(experimentalDatasetsMatrix, interval);
			// extract individual part for alignment
			SimpleMatrix matrixPart = extractPartOfDataForProcessing(experimentalDatasetsMatrix);
			// align individual part
			matrixPart = alignAllDatasets(shiftingValues, matrixPart);
			// combine all individual parts
			int insertCol = referenceWindow[0];
			alignedDatasets.insertIntoThis(0, insertCol, matrixPart);
		}
		return alignedDatasets;
	}

	private SimpleMatrix alignOneInterval(SortedMap<Integer, Interval> intervalRegionsMap, SimpleMatrix experimentalDatasetsMatrix) {

		//
		Iterator<Interval> intervalRegionsMapIterator = intervalRegionsMap.values().iterator();
		int[] shiftingValues = new int[experimentalDatasetsMatrix.numRows()];
		SimpleMatrix alignedDatasets = new SimpleMatrix(experimentalDatasetsMatrix.numRows(), experimentalDatasetsMatrix.numCols());
		//
		while(intervalRegionsMapIterator.hasNext()) {
			Interval interval = intervalRegionsMapIterator.next();
			shiftingValues = coshiftSpectra(experimentalDatasetsMatrix, interval);
		}
		// shifting datasets
		alignedDatasets = alignAllDatasets(shiftingValues, experimentalDatasetsMatrix);
		//
		return alignedDatasets;
	}

	private int[] calculateShiftValues(SimpleMatrix fouriertransformedDatasetCrossCorrelated, int shiftCorrectionTypeValue) {

		UtilityFunctions utilityFunction = new UtilityFunctions();
		//
		int[] maxPeakPositions = new int[fouriertransformedDatasetCrossCorrelated.numRows()];
		double[] searchArray = null;
		ShiftCorrectionType shiftCorrectionType = settings.getShiftCorrectionType();
		for(int r = 0; r < fouriertransformedDatasetCrossCorrelated.numRows(); r++) {
			double[] shiftArray = fouriertransformedDatasetCrossCorrelated.extractVector(true, r).getMatrix().getData();
			// circular shift
			fouriertransformedDatasetCrossCorrelated.setRow(r, 0, utilityFunction.rightShiftNMRData(shiftArray, shiftArray.length / 2));
			searchArray = shiftArray;
			//
			if(!shiftCorrectionType.equals(ShiftCorrectionType.FAST)) { // either USER_DEFINED or BEST
				// cut out central part of observed shiftArray
				searchArray = Arrays.copyOfRange(shiftArray, (shiftArray.length / 2) - shiftCorrectionTypeValue - 1, (shiftArray.length / 2) + shiftCorrectionTypeValue);
			}
			// find max. peak positions
			double maxValue = utilityFunction.getMaxValueOfArray(searchArray);
			int maxValueIndex = utilityFunction.findIndexOfValue(searchArray, maxValue);
			maxPeakPositions[r] = maxValueIndex;
		}
		// correct the range to fit newDataSize
		int newDataSize = searchArray.length;
		int shiftValuesRange = (newDataSize + 1);
		int[] shiftValuesArray = new int[shiftValuesRange];
		for(int i = 0; i < shiftValuesRange; i++) {
			shiftValuesArray[i] = -newDataSize / 2 + i;
		}
		int[] shiftValues = new int[maxPeakPositions.length];
		for(int i = 0; i < maxPeakPositions.length; i++) {
			shiftValues[i] = shiftValuesArray[maxPeakPositions[i]];
		}
		return shiftValues;
	}

	public int[] calculateFFTCrossCorrelation(double[] targetForFFT, SimpleMatrix experimentalDatasetForFFT, int shiftCorrectionTypeValue) {

		/*
		 * procedure: zero filling >> FFT >> CC calculations >> IFFT
		 * *******
		 * automatic zero filling! make sure datasize always == 2^n
		 */
		// zero filling
		int rows = experimentalDatasetForFFT.numRows();
		int cols = experimentalDatasetForFFT.numCols();
		int newDataSize = (int)Math.pow(2, (int)(Math.ceil((Math.log(cols) / Math.log(2)))));
		double[] targetForFFTzf = new double[newDataSize];
		System.arraycopy(targetForFFT, 0, targetForFFTzf, 0, targetForFFT.length);
		SimpleMatrix experimentalDatasetForFFTzf = new SimpleMatrix(rows, newDataSize);
		double[] tempDataDestination = new double[newDataSize];
		for(int r = 0; r < experimentalDatasetForFFT.numRows(); r++) {
			double[] tempDataSource = experimentalDatasetForFFT.extractVector(true, r).getMatrix().getData();
			System.arraycopy(tempDataSource, 0, tempDataDestination, 0, tempDataSource.length);
			experimentalDatasetForFFTzf.setRow(r, 0, tempDataDestination);
			// reset array content
			// tempDataDestination = Arrays.stream(tempDataDestination).map(i -> i > 0 ? 0 : i).toArray();
			Arrays.fill(tempDataDestination, 0);
		}
		// FFT
		// MATLAB: fft(X,n,2) returns the n-point Fourier transform of each row.
		FastFourierTransformer fFourierTransformer = new FastFourierTransformer(DftNormalization.STANDARD);
		Complex[] fouriertransformedTarget = fFourierTransformer.transform(targetForFFTzf, TransformType.FORWARD);
		for(int c = 0; c < fouriertransformedTarget.length; c++) {
			// complex conjugate of target
			fouriertransformedTarget[c] = fouriertransformedTarget[c].conjugate();
		}
		SimpleMatrix fouriertransformedDatasetCrossCorrelated = new SimpleMatrix(rows, newDataSize);
		for(int r = 0; r < experimentalDatasetForFFTzf.numRows(); r++) {
			// FFT and calculations
			double[] arrayForFFT = experimentalDatasetForFFTzf.extractVector(true, r).getMatrix().getData();
			Complex[] fouriertransformedArray = fFourierTransformer.transform(arrayForFFT, TransformType.FORWARD);
			for(int m = 0; m < fouriertransformedArray.length; m++) {
				fouriertransformedArray[m] = fouriertransformedArray[m].multiply(fouriertransformedTarget[m]);
			}
			// IFFT
			Complex[] arrayAfterFFT = fFourierTransformer.transform(fouriertransformedArray, TransformType.INVERSE);
			for(int x = 0; x < arrayAfterFFT.length; x++) {
				fouriertransformedDatasetCrossCorrelated.setRow(r, 0, getRealPartOfComplexArray(arrayAfterFFT));
			}
		}
		//
		int[] shiftValues = calculateShiftValues(fouriertransformedDatasetCrossCorrelated, shiftCorrectionTypeValue);
		return shiftValues;
	}

	private double[] getRealPartOfComplexArray(Complex[] array) {

		double[] result = new double[array.length];
		for(int a = 0; a < array.length; a++) {
			result[a] = array[a].getReal();
		}
		return result;
	}

	private double calculateSquareRootOfSum(double[] array) {

		// 1D array
		for(int i = 0; i < array.length; i++) {
			array[i] = Math.pow(array[i], 2);
		}
		double sum = Arrays.stream(array).sum();
		return Math.sqrt(sum);
	}

	private SimpleMatrix calculateSquareRootOfSum(SimpleMatrix matrix) {

		int rows = matrix.numRows();
		matrix = matrix.elementPower(2);
		SimpleMatrix sumMatrix = new SimpleMatrix(rows, 1);
		for(int r = 0; r < rows; r++) {
			double[] array = matrix.extractVector(true, r).getMatrix().getData();
			double sum = Arrays.stream(array).sum();
			sum = Math.sqrt(sum);
			sumMatrix.setRow(r, 0, sum);
		}
		return sumMatrix;
	}

	private void checkShiftCorrectionTypeValueSize(SortedMap<Integer, Interval> intervalRegionsMap, int shiftCorrectionTypeValue) {

		intervalRegionsMap.values().forEach((interval) -> {
			int intervalRange = interval.stop - interval.start + 1;
			if((shiftCorrectionTypeValue > intervalRange) || ((shiftCorrectionTypeValue * 2) > intervalRange)) {
				throw new IllegalArgumentException(">shiftCorrectionTypeValue< must be not larger than the size of the smallest interval");
			}
		});
	}

	/*
	 * enums
	 */
	public enum AlignmentType {
		SINGLE_PEAK("SINGLE_PEAK"), // align spectra referencing a single peak
		WHOLE_SPECTRUM("WHOLE_SPECTRUM"), // align the whole spectrum
		NUMBER_OF_INTERVALS("NUMBER_OF_INTERVALS"), // align the spectrum divided in the given no. of intervals
		INTERVAL_LENGTH("INTERVAL_LENGTH"), // align the spectrum divided in intervals of given length
		USER_DEFINED_INTERVALS("USER_DEFINED_INTERVALS");

		//
		private String intervalSelection;

		// align user defined regions, e.g. integrals of interest
		private AlignmentType(String intervalSelection) {

			this.intervalSelection = intervalSelection;
		}

		@Override
		public String toString() {

			return intervalSelection;
		}
	}

	public static class ChemicalShiftInterval {

		public ChemicalShiftInterval(double start, double stop) {

			this.start = start;
			this.stop = stop;
		}

		private double start;
		private double stop;

		public double getStart() {

			return start;
		}

		public double getStop() {

			return stop;
		}
	}

	public static class Interval {

		public Interval(int start, int stop) {

			this.start = start;
			this.stop = stop;
		}

		private int start;
		private int stop;

		public int getStart() {

			return start;
		}

		public int getStop() {

			return stop;
		}
	}

	public enum ShiftCorrectionType {
		FAST("Fast"), //
		BEST("Best"), //
		USER_DEFINED("User Defined");// requires user input @shiftCorrectionTypeValue

		private String shiftCorrectionType;

		private ShiftCorrectionType(String shiftCorrectionType) {

			this.shiftCorrectionType = shiftCorrectionType;
		}

		@Override
		public String toString() {

			return shiftCorrectionType;
		}
	}

	public enum GapFillingType {
		ZERO("Zero"), // ""
		MARGIN("Margin"); //

		private String gapFillingType;

		private GapFillingType(String gapFillingType) {

			this.gapFillingType = gapFillingType;
		}

		@Override
		public String toString() {

			return gapFillingType;
		}
	}

	public enum TargetCalculationSelection {
		MEAN("Mean"), //
		MEDIAN("Median"), //
		MAX("Max");//

		private String targetCalculationSelection;

		private TargetCalculationSelection(String targetCalculationSelection) {

			this.targetCalculationSelection = targetCalculationSelection;
		}

		@Override
		public String toString() {

			return targetCalculationSelection;
		}
	}
}
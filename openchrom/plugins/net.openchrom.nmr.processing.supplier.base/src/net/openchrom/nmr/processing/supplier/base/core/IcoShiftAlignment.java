/*******************************************************************************
 * Copyright (c) 2018, 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexander Stark - initial API and implementation
 * Jan Holy - refactoring
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.core;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.DoubleStream;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.stat.descriptive.rank.Median;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.nmr.model.core.IMeasurementNMR;
import org.eclipse.chemclipse.nmr.model.selection.DataNMRSelection;
import org.eclipse.chemclipse.nmr.model.selection.IDataNMRSelection;
import org.eclipse.chemclipse.nmr.model.support.ISignalExtractor;
import org.eclipse.chemclipse.nmr.model.support.SignalExtractor;
import org.ejml.simple.SimpleMatrix;

import net.openchrom.nmr.processing.supplier.base.settings.IcoShiftAlignmentSettings;
import net.openchrom.nmr.processing.supplier.base.settings.support.IcoShiftAlignmentGapFillingType;
import net.openchrom.nmr.processing.supplier.base.settings.support.IcoShiftAlignmentShiftCorrectionType;
import net.openchrom.nmr.processing.supplier.base.settings.support.IcoShiftAlignmentTargetCalculationSelection;
import net.openchrom.nmr.processing.supplier.base.settings.support.IcoShiftAlignmentType;
import net.openchrom.nmr.processing.supplier.base.settings.support.IcoShiftAlignmentUtilities;
import net.openchrom.nmr.processing.supplier.base.settings.support.IcoShiftAlignmentUtilities.Interval;

/*
 * icoshift algorithm (interval-correlation-shifting)
 * ---
 * based on: Savorani et al., J. Magn. Reson. 202, Nr. 2 (1. Februar 2010): 190–202.
 */
public class IcoShiftAlignment {

	private static final Logger icoShiftAlignmentLogger = Logger.getLogger(IcoShiftAlignment.class);

	public SimpleMatrix process(Collection<? extends IMeasurementNMR> experimentalDatasetsList, IcoShiftAlignmentSettings settings) {

		// safety check for length of each spectrum; they all must have equal length!
		checkLengthOfEachSpectrum(experimentalDatasetsList);
		// import real parts of spectra
		SimpleMatrix experimentalDatasetsMatrix = extractMultipleSpectra(experimentalDatasetsList);
		// calculate intervals according to settings
		SortedMap<Integer, Interval<Integer>> intervalRegionsMap = calculateIntervals(experimentalDatasetsList, settings);
		// do the alignment
		return performMainAlignment(experimentalDatasetsMatrix, intervalRegionsMap, settings);
	}

	private SimpleMatrix performMainAlignment(SimpleMatrix experimentalDatasetsMatrix, SortedMap<Integer, Interval<Integer>> intervalRegionsMap, IcoShiftAlignmentSettings settings) {

		if(!(settings.getAlignmentType() == IcoShiftAlignmentType.WHOLE_SPECTRUM) && settings.isPreliminaryCoShifting()) {
			experimentalDatasetsMatrix = executePreliminaryCoShifting(experimentalDatasetsMatrix);
		}
		// check after calculation of intervals
		IcoShiftAlignmentShiftCorrectionType shiftCorrectionType = settings.getShiftCorrectionType();
		if(shiftCorrectionType == IcoShiftAlignmentShiftCorrectionType.USER_DEFINED) {
			checkShiftCorrectionTypeValueSize(intervalRegionsMap, settings.getShiftCorrectionTypeValue());
		}
		//
		IcoShiftAlignmentType alignmentType = settings.getAlignmentType();
		switch(alignmentType) {
			case SINGLE_PEAK: // fall through for one interval
			case WHOLE_SPECTRUM: {
				SimpleMatrix alignedDatasets = alignOneInterval(intervalRegionsMap, experimentalDatasetsMatrix, settings);
				return alignedDatasets;
			}
			case NUMBER_OF_INTERVALS: // fall through for several intervals
			case INTERVAL_LENGTH:
			case USER_DEFINED_INTERVALS: {
				SimpleMatrix alignedDatasets = alignSeveralIntervals(intervalRegionsMap, experimentalDatasetsMatrix, settings);
				return alignedDatasets;
			}
			default:
				throw new IllegalArgumentException("unsupported AlignmentType: " + alignmentType);
		}
	}

	private SimpleMatrix executePreliminaryCoShifting(SimpleMatrix experimentalDatasetsMatrix) {

		// define settings for preliminary Co-Shifting
		IcoShiftAlignmentSettings settings = generatePreliminarySettings();
		//
		IcoShiftAlignmentUtilities shiftUtils = new IcoShiftAlignmentUtilities();
		int lengthOfDataset = experimentalDatasetsMatrix.numCols();
		SortedMap<Integer, Interval<Integer>> intervalRegionsMap = new TreeMap<>();
		// local map of a full range interval; same calculation as in calculateIntervals() with AlignmentType.WHOLE_SPECTRUM
		intervalRegionsMap.put(1, shiftUtils.new Interval<Integer>(0, lengthOfDataset - 1));
		//
		IcoShiftAlignment icoShiftAlignment = new IcoShiftAlignment();
		return icoShiftAlignment.performMainAlignment(experimentalDatasetsMatrix, intervalRegionsMap, settings);
	}

	private IcoShiftAlignmentSettings generatePreliminarySettings() {

		/*
		 * TODO add special comment that describes this
		 */
		IcoShiftAlignmentSettings settings = new IcoShiftAlignmentSettings();
		settings.setTargetCalculationSelection(IcoShiftAlignmentTargetCalculationSelection.MEAN); // or MEDIAN
		settings.setAlignmentType(IcoShiftAlignmentType.WHOLE_SPECTRUM);
		settings.setShiftCorrectionType(IcoShiftAlignmentShiftCorrectionType.FAST);
		settings.setGapFillingType(IcoShiftAlignmentGapFillingType.MARGIN);
		return settings;
	}

	private double[] calculateSelectedTarget(SimpleMatrix experimentalDatasetsMatrix, IcoShiftAlignmentSettings settings) {

		IcoShiftAlignmentTargetCalculationSelection targetCalculationSelection = settings.getTargetCalculationSelection();
		switch(targetCalculationSelection) {
			case MEAN: {
				return calculateMeanTarget(experimentalDatasetsMatrix);
			}
			case MEDIAN: {
				return calculateMedianTarget(experimentalDatasetsMatrix);
			}
			case MAX: {
				return calculateMaxTarget(experimentalDatasetsMatrix);
			}
			default:
				throw new IllegalArgumentException("unsupported TargetCalculationSelection: " + targetCalculationSelection);
		}
	}

	private double[] calculateMeanTarget(SimpleMatrix experimentalDatasetsMatrix) {

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

	private SimpleMatrix extractMultipleSpectra(Collection<? extends IMeasurementNMR> experimentalDatasetsList) {

		IDataNMRSelection dataNMRSelect = new DataNMRSelection(experimentalDatasetsList.iterator().next());
		int datapointsPerDataset = dataNMRSelect.getMeasurmentNMR().getScanMNR().getNumberOfFourierPoints();
		if(datapointsPerDataset == 0) {
			throw new IllegalArgumentException("No datapoints for dataset found!");
		}
		int numberOfDatasets = experimentalDatasetsList.size();
		SimpleMatrix experimentalDatasetsMatrix = new SimpleMatrix(numberOfDatasets, datapointsPerDataset);
		int matrixRow = 0;
		//
		for(IMeasurementNMR measurementNMR : experimentalDatasetsList) {
			ISignalExtractor signalExtractor = new SignalExtractor(measurementNMR);
			experimentalDatasetsMatrix.setRow(matrixRow, 0, signalExtractor.extractFourierTransformedDataRealPart());
			matrixRow++;
		}
		return experimentalDatasetsMatrix;
	}

	private SortedMap<Integer, Interval<Integer>> calculateIntervals(Collection<? extends IMeasurementNMR> experimentalDatasetsList, IcoShiftAlignmentSettings settings) {

		// map to store throughout numbered intervals
		SortedMap<Integer, Interval<Integer>> intervalRegionsMap = new TreeMap<>();
		/*
		 * For use with MS data, chromatograms, etc .:
		 * ~~~~~~~
		 * as long as the supplied object has an X-axis and a defined
		 * length, the algorithm should be able to work with it.
		 */
		//
		IMeasurementNMR measureNMR = experimentalDatasetsList.iterator().next();
		IDataNMRSelection dataNMRSelect = new DataNMRSelection(measureNMR);
		ISignalExtractor signalExtract = new SignalExtractor(dataNMRSelect);
		double[] chemicalShiftAxis = signalExtract.extractChemicalShift();
		int lengthOfDataset = dataNMRSelect.getMeasurmentNMR().getScanMNR().getNumberOfFourierPoints();
		UtilityFunctions utilityFunction = new UtilityFunctions();
		//
		IcoShiftAlignmentUtilities shiftUtils = new IcoShiftAlignmentUtilities();
		IcoShiftAlignmentType alignmentType = settings.getAlignmentType();
		switch(alignmentType) {
			case SINGLE_PEAK:
				/*
				 * get left and right boundaries in ppm
				 * find indices in data
				 */
				double lowerBorder = settings.getSinglePeakLowerBorder();
				double higherBorder = settings.getSinglePeakHigherBorder();
				int lowerBorderIndex = utilityFunction.findIndexOfValue(chemicalShiftAxis, lowerBorder);
				int higherBorderIndex = utilityFunction.findIndexOfValue(chemicalShiftAxis, higherBorder);
				intervalRegionsMap.put(1, shiftUtils.new Interval<Integer>(lowerBorderIndex, higherBorderIndex));
				break;
			case WHOLE_SPECTRUM:
				/*
				 * no user input required
				 * start at index=0, end at index=lengthOfDataset-1
				 */
				intervalRegionsMap.put(1, shiftUtils.new Interval<Integer>(0, lengthOfDataset - 1));
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
					intervalRegionsMap.put(i + 1, shiftUtils.new Interval<Integer>(intervalStartValues[i], intervalEndValues[i]));
				}
				break;
			case INTERVAL_LENGTH:
				/*
				 * divide present data by the amount of given datapoints (=length of interval) in equal intervals
				 * save every interval in map with left and right boundaries
				 */
				int lengthOfIntervals = settings.getIntervalLength();
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
					intervalRegionsMap.put(i + 1, shiftUtils.new Interval<Integer>(intervalStarts[i], intervalEnds[i]));
				}
				break;
			case USER_DEFINED_INTERVALS:
				/*
				 * take a map / import a file / read an integral list... with user defined intervals
				 * the boundaries will be in ppm (?) => double value!; find indices in data
				 * ***
				 * main difference to number/length methods: intervals may be discontiguous!
				 */
				List<Interval<Double>> userDefIntervalRegions = settings.getUserDefIntervalRegions();
				Iterator<Interval<Double>> userDefIntervalRegionsIterator = userDefIntervalRegions.iterator();
				int intervalNumber = 1;
				//
				while(userDefIntervalRegionsIterator.hasNext()) {
					Interval<Double> interval = userDefIntervalRegionsIterator.next();
					// get indices for each user defined interval
					double higherUserBorder = interval.getStart();
					double lowerUserBorder = interval.getStop();
					int lowerUserBorderIndex = utilityFunction.findIndexOfValue(chemicalShiftAxis, lowerUserBorder);
					int higherUserBorderIndex = utilityFunction.findIndexOfValue(chemicalShiftAxis, higherUserBorder);
					intervalRegionsMap.put(intervalNumber, shiftUtils.new Interval<Integer>(lowerUserBorderIndex, higherUserBorderIndex));
					intervalNumber++;
				}
				break;
			default:
				throw new IllegalArgumentException("unsupported AlignmentType: " + alignmentType);
		}
		return intervalRegionsMap;
	}

	public int[] coshiftSpectra(SimpleMatrix experimentalDatasetsMatrix, Interval<Integer> interval, IcoShiftAlignmentSettings settings) {

		//
		int[] referenceWindow = IcoShiftAlignmentUtilities.generateReferenceWindow(interval);
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
		IcoShiftAlignmentShiftCorrectionType shiftCorrectionType = settings.getShiftCorrectionType();
		if(shiftCorrectionType == IcoShiftAlignmentShiftCorrectionType.USER_DEFINED) {
			shiftCorrectionTypeValue = settings.getShiftCorrectionTypeValue();
		}
		int sourceStep = 0;
		if(shiftCorrectionType == IcoShiftAlignmentShiftCorrectionType.FAST || shiftCorrectionType == IcoShiftAlignmentShiftCorrectionType.BEST) {
			// switch for the best automatic search on
			if(referenceWindowLength != 1) {
				localDimension = dimensionOfReferenceWindow[1];
			} else {
				localDimension = dimensionOfDataset[1];
			}
			//
			if(shiftCorrectionType == IcoShiftAlignmentShiftCorrectionType.FAST) {
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
		SimpleMatrix experimentalDatasetsMatrixPartForProcessing = extractPartOfDataForProcessing(experimentalDatasetsMatrix, referenceWindow);
		//
		IcoShiftAlignmentTargetCalculationSelection targetCalculationSelection = settings.getTargetCalculationSelection();
		if(targetCalculationSelection == IcoShiftAlignmentTargetCalculationSelection.MAX) {
			// calculate max target here for each interval
			targetForProcessing = calculateSelectedTarget(experimentalDatasetsMatrixPartForProcessing, settings);
		} else {
			// "MEAN" and "MEDIAN"
			double[] targetSpectrum = calculateSelectedTarget(experimentalDatasetsMatrix, settings);
			targetForProcessing = extractPartOfDataForProcessing(targetSpectrum, referenceWindow);
		}
		/*
		 * Automatic search for the best "shiftCorrectionTypeValue" for each interval
		 */
		int[] shiftCorrectionTypeBorders = {-shiftCorrectionTypeValue, shiftCorrectionTypeValue};
		boolean bestShift = false;
		int bestShiftIteration = 0;
		int[] shiftingValues = new int[experimentalDatasetsMatrixPartForProcessing.numRows()];
		//
		int shiftCorrectionTypeValueInternal = shiftCorrectionTypeValue;
		while(!bestShift) {
			//
			bestShiftIteration++;
			icoShiftAlignmentLogger.info("Searching optimal max. shift: iteration #" + bestShiftIteration);
			for(int i = 0; i < numberOfBlocks; i++) {
				// FFT Co-Shifting
				shiftingValues = doFFTCoShifting(experimentalDatasetsMatrixPartForProcessing, targetForProcessing, shiftCorrectionTypeValueInternal, settings);
			}
			//
			if(shiftCorrectionType == IcoShiftAlignmentShiftCorrectionType.FAST || shiftCorrectionType == IcoShiftAlignmentShiftCorrectionType.BEST) {
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

	private SimpleMatrix extractPartOfDataForProcessing(SimpleMatrix data, int[] referenceWindow) {

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

	private double[] extractPartOfDataForProcessing(double[] targetSpectrum, int[] referenceWindow) {

		int idx = 0;
		double[] targetForProcessing = new double[referenceWindow.length];
		for(int i = referenceWindow[0]; i <= referenceWindow[referenceWindow.length - 1]; i++) {
			// extract necessary part of target vector
			targetForProcessing[idx] = targetSpectrum[i];
			idx++;
		}
		return targetForProcessing;
	}

	private int[] doFFTCoShifting(SimpleMatrix experimentalDatasetsMatrixPartForProcessing, double[] targetForProcessing, int shiftCorrectionTypeValue, IcoShiftAlignmentSettings settings) {

		/*
		 * normalize data and prepare for calculations
		 */
		SimpleMatrix experimentalDatasetForFFT = normalizeDataBeforeCalculation(experimentalDatasetsMatrixPartForProcessing);
		double[] targetForFFT = normalizeDataBeforeCalculation(targetForProcessing);
		/*
		 * FFT shift Cross Correlation and determination of shifts
		 */
		int[] shiftValues = calculateFFTCrossCorrelation(targetForFFT, experimentalDatasetForFFT, shiftCorrectionTypeValue, settings);
		//
		return shiftValues;
	}

	private double[] normalizeDataBeforeCalculation(double[] targetForProcessing) {

		Double targetNormalization = (double)IcoShiftAlignmentUtilities.calculateSquareRootOfSum(targetForProcessing);
		if(targetNormalization.compareTo(0.0) == 0) {
			targetNormalization = 1.0;
		}
		double[] targetForFFT = new double[targetForProcessing.length];
		for(int i = 0; i < targetForProcessing.length; i++) {
			targetForFFT[i] = targetForProcessing[i] / targetNormalization;
		}
		return targetForFFT;
	}

	private SimpleMatrix normalizeDataBeforeCalculation(SimpleMatrix experimentalDatasetsMatrixPartForProcessing) {

		SimpleMatrix datasetNormalization = IcoShiftAlignmentUtilities.calculateSquareRootOfSum(experimentalDatasetsMatrixPartForProcessing);
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

	private SimpleMatrix alignAllDatasets(int[] shiftValues, SimpleMatrix fouriertransformedDatasetCrossCorrelated, IcoShiftAlignmentSettings settings) {

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
			IcoShiftAlignmentGapFillingType gapFillingType = settings.getGapFillingType();
			if(shiftValues[r] >= 0) {
				// left shift
				if(gapFillingType == IcoShiftAlignmentGapFillingType.ZERO) {
					// fill array here with 0
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
				if(gapFillingType == IcoShiftAlignmentGapFillingType.ZERO) {
					// fill array here with 0
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

	public SimpleMatrix alignSeveralIntervals(SortedMap<Integer, Interval<Integer>> intervalRegionsList, SimpleMatrix experimentalDatasetsMatrix, IcoShiftAlignmentSettings settings) {

		//
		Iterator<Interval<Integer>> intervalRegionsMapIterator = intervalRegionsList.values().iterator();
		int[] shiftingValues = new int[experimentalDatasetsMatrix.numRows()];
		SimpleMatrix alignedDatasets = new SimpleMatrix(experimentalDatasetsMatrix.numRows(), experimentalDatasetsMatrix.numCols());
		//
		IcoShiftAlignmentType alignmentType = settings.getAlignmentType();
		if(alignmentType == IcoShiftAlignmentType.USER_DEFINED_INTERVALS) {
			alignedDatasets = experimentalDatasetsMatrix.copy();
		}
		//
		while(intervalRegionsMapIterator.hasNext()) {
			Interval<Integer> interval = intervalRegionsMapIterator.next();
			icoShiftAlignmentLogger.info("Aligning region from index " + interval.getStart() + " to " + interval.getStop());
			shiftingValues = coshiftSpectra(experimentalDatasetsMatrix, interval, settings);
			// extract individual part for alignment
			int[] referenceWindow = IcoShiftAlignmentUtilities.generateReferenceWindow(interval);
			SimpleMatrix matrixPart = extractPartOfDataForProcessing(experimentalDatasetsMatrix, referenceWindow);
			// align individual part
			matrixPart = alignAllDatasets(shiftingValues, matrixPart, settings);
			// combine all individual parts
			int insertCol = referenceWindow[0];
			alignedDatasets.insertIntoThis(0, insertCol, matrixPart);
		}
		return alignedDatasets;
	}

	private SimpleMatrix alignOneInterval(SortedMap<Integer, Interval<Integer>> intervalRegionsMap, SimpleMatrix experimentalDatasetsMatrix, IcoShiftAlignmentSettings settings) {

		//
		Iterator<Interval<Integer>> intervalRegionsMapIterator = intervalRegionsMap.values().iterator();
		int[] shiftingValues = new int[experimentalDatasetsMatrix.numRows()];
		SimpleMatrix alignedDatasets = new SimpleMatrix(experimentalDatasetsMatrix.numRows(), experimentalDatasetsMatrix.numCols());
		//
		while(intervalRegionsMapIterator.hasNext()) {
			Interval<Integer> interval = intervalRegionsMapIterator.next();
			icoShiftAlignmentLogger.info("Aligning region from index " + interval.getStart() + " to " + interval.getStop());
			shiftingValues = coshiftSpectra(experimentalDatasetsMatrix, interval, settings);
		}
		// shifting datasets
		alignedDatasets = alignAllDatasets(shiftingValues, experimentalDatasetsMatrix, settings);
		//
		return alignedDatasets;
	}

	private int[] calculateShiftValues(SimpleMatrix fouriertransformedDatasetCrossCorrelated, int shiftCorrectionTypeValue, IcoShiftAlignmentSettings settings) {

		UtilityFunctions utilityFunction = new UtilityFunctions();
		//
		int[] maxPeakPositions = new int[fouriertransformedDatasetCrossCorrelated.numRows()];
		double[] searchArray = null;
		IcoShiftAlignmentShiftCorrectionType shiftCorrectionType = settings.getShiftCorrectionType();
		for(int r = 0; r < fouriertransformedDatasetCrossCorrelated.numRows(); r++) {
			double[] shiftArray = fouriertransformedDatasetCrossCorrelated.extractVector(true, r).getMatrix().getData();
			// circular shift
			fouriertransformedDatasetCrossCorrelated.setRow(r, 0, utilityFunction.rightShiftNMRData(shiftArray, shiftArray.length / 2));
			searchArray = shiftArray;
			//
			if(!(shiftCorrectionType == IcoShiftAlignmentShiftCorrectionType.FAST)) { // either USER_DEFINED or BEST
				// cut out central part of observed shiftArray
				int copyOfRangeFrom = (shiftArray.length / 2) - shiftCorrectionTypeValue - 1;
				int copyOfRangeTo = (shiftArray.length / 2) + shiftCorrectionTypeValue;
				searchArray = Arrays.copyOfRange(shiftArray, copyOfRangeFrom, copyOfRangeTo);
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

	public int[] calculateFFTCrossCorrelation(double[] targetForFFT, SimpleMatrix experimentalDatasetForFFT, int shiftCorrectionTypeValue, IcoShiftAlignmentSettings settings) {

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
				fouriertransformedDatasetCrossCorrelated.setRow(r, 0, IcoShiftAlignmentUtilities.getRealPartOfComplexArray(arrayAfterFFT));
			}
		}
		//
		int[] shiftValues = calculateShiftValues(fouriertransformedDatasetCrossCorrelated, shiftCorrectionTypeValue, settings);
		return shiftValues;
	}

	private void checkShiftCorrectionTypeValueSize(SortedMap<Integer, Interval<Integer>> intervalRegionsMap, int shiftCorrectionTypeValue) {

		intervalRegionsMap.values().forEach((interval) -> {
			int stop = interval.getStop();
			int start = interval.getStart();
			int intervalRange = stop - start + 1;
			if((shiftCorrectionTypeValue > intervalRange) || ((shiftCorrectionTypeValue * 2) > intervalRange)) {
				throw new IllegalArgumentException(">shiftCorrectionTypeValue< must be not larger than the size of the smallest interval");
			}
		});
	}

	private void checkLengthOfEachSpectrum(Collection<? extends IMeasurementNMR> experimentalDatasetsList) {

		double[] collectNumberOfFourierPoints = new double[experimentalDatasetsList.size()];
		int i = 0;
		for(Object o : experimentalDatasetsList) {
			IMeasurementNMR measureNMR = (IMeasurementNMR)o;
			IDataNMRSelection dataNMRSelect = new DataNMRSelection(measureNMR);
			collectNumberOfFourierPoints[i] = dataNMRSelect.getMeasurmentNMR().getProcessingParameters("numberOfFourierPoints");
			i++;
		}
		//
		boolean verification = DoubleStream.of(collectNumberOfFourierPoints).anyMatch(x -> x != collectNumberOfFourierPoints[0]);
		if(verification) {
			throw new IllegalArgumentException("Size of all experiments is not equal!");
		}
	}
}
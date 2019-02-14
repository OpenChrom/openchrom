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

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.stream.DoubleStream;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.stat.descriptive.rank.Median;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import org.eclipse.chemclipse.nmr.converter.core.ScanConverterNMR;
import org.eclipse.chemclipse.nmr.model.core.IMeasurementNMR;
import org.eclipse.chemclipse.nmr.model.selection.DataNMRSelection;
import org.eclipse.chemclipse.nmr.model.selection.IDataNMRSelection;
import org.eclipse.chemclipse.nmr.model.support.ISignalExtractor;
import org.eclipse.chemclipse.nmr.model.support.SignalExtractor;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.ejml.simple.SimpleMatrix;

import net.openchrom.nmr.processing.supplier.base.settings.FourierTransformationSettings;

public class IcoShiftAlignment {

	/*
	 * icoshift algorithm (interval-correlation-shifting)
	 * ---
	 * based on: Savorani et al., J. Magn. Reson. 202, Nr. 2 (1. Februar 2010): 190–202.
	 */
	public SimpleMatrix process(File multipleDataDir, String searchPattern) {

		// virtually load the filelist
		String[] experimentalDatasets = readPathsToMultipleDatasets(multipleDataDir, searchPattern);
		// save every experiment/dataset as object in list
		List<Object> experimentalDatasetsList = importMultipleDatasets(experimentalDatasets);
		// imported real parts of spectra
		SimpleMatrix experimentalDatasetsMatrix = extractMultipleSpectra(experimentalDatasetsList);
		/*
		 * settings start
		 */
		/*
		 * target calculation => "MEAN", "MEDIAN", "MAX"
		 */
		targetCalculationSelection.setTargetCalculationSelection("MEAN");
		/*
		 * intervals to align => "SINGLE_PEAK", "WHOLE_SPECTRUM", "NUMBER_OF_INTERVALS", "INTERVAL_LENGTH", "USER_DEFINED_INTERVALS"
		 */
		intervalSelection.setIntervalSelection("SINGLE_PEAK");
		// SINGLE_PEAK
		intervalSelection.setSinglePeakHigherBorder(2.41);
		intervalSelection.setSinglePeakLowerBorder(2.21);
		// NUMBER_OF_INTERVALS
		intervalSelection.setNumberOfIntervals(100);
		// INTERVAL_LENGTH
		intervalSelection.setLengthOfIntervals(1000);
		// USER_DEFINED_INTERVALS
		// either put in values one by one or you can do it as a complete map
		intervalSelection.setUserDefIntervalRegions(5.5, 5.2);
		intervalSelection.setUserDefIntervalRegions(4.4, 4.1);
		intervalSelection.setUserDefIntervalRegions(2.4, 2.25);
		intervalSelection.setUserDefIntervalRegions(2.1, 1.9);
		intervalSelection.setUserDefIntervalRegions(1.4, 1.2);
		//
		/*
		 * shiftCorrection => "FAST", "BEST", "USER_DEFINED"
		 */
		shiftCorrectionType.setShiftCorrectionType("FAST");
		// if USER_DEFINED is selected define following:
		shiftCorrectionType.setShiftCorrectionTypeValue(45);
		/*
		 * gap filling type => "ZERO", "MARGIN"
		 */
		gapFillingType.setGapFillingType("MARGIN");
		/*
		 * optional preliminary Co-Shifting of whole dataset => "YES" or "NO"
		 */
		preliminaryCoShifting.setPreliminaryCoShifting("NO");
		/*
		 * settings end
		 */
		if(!intervalSelection.getIntervalSelection().contentEquals("WHOLE_SPECTRUM") && preliminaryCoShifting.getPreliminaryCoShifting().contentEquals("YES")) {
			experimentalDatasetsMatrix = executePreliminaryCoShifting(experimentalDatasetsList, experimentalDatasetsMatrix);
		}
		// calculate intervals according to settings
		LinkedHashMap<Integer, Integer> intervalRegionsMap = calculateIntervals(experimentalDatasetsList);
		// check after calculation of intervals
		if(shiftCorrectionType.getShiftCorrectionType().contentEquals("USER_DEFINED")) {
			checkShiftCorrectionTypeValueSize(intervalRegionsMap);
		}
		//
		SimpleMatrix alignedDatasets = new SimpleMatrix(experimentalDatasetsMatrix.numRows(), experimentalDatasetsMatrix.numCols());
		//
		switch(intervalSelection.getIntervalSelection()) {
			case "SINGLE_PEAK":
				//
				alignedDatasets = alignOneInterval(intervalRegionsMap, experimentalDatasetsMatrix);
				break;
			case "NUMBER_OF_INTERVALS":
				//
				alignedDatasets = alignSeveralIntervals(intervalRegionsMap, experimentalDatasetsMatrix);
				break;
			case "INTERVAL_LENGTH":
				//
				alignedDatasets = alignSeveralIntervals(intervalRegionsMap, experimentalDatasetsMatrix);
				break;
			case "WHOLE_SPECTRUM":
				//
				alignedDatasets = alignOneInterval(intervalRegionsMap, experimentalDatasetsMatrix);
				break;
			case "USER_DEFINED_INTERVALS":
				//
				alignedDatasets = alignSeveralIntervals(intervalRegionsMap, experimentalDatasetsMatrix);
				break;
		}
		return alignedDatasets;
	}

	private void checkLengthOfEachSpectrum(List<Object> experimentalDatasetsList) {

		double[] collectNumberOfFourierPoints = new double[experimentalDatasetsList.size()];
		//
		for(int i = 0; i < experimentalDatasetsList.size(); i++) {
			IMeasurementNMR measureNMR = (IMeasurementNMR)experimentalDatasetsList.get(i);
			IDataNMRSelection dataNMRSelect = new DataNMRSelection(measureNMR);
			collectNumberOfFourierPoints[i] = dataNMRSelect.getMeasurmentNMR().getProcessingParameters("numberOfFourierPoints");// getScanMNR().getNumberOfFourierPoints();
		}
		//
		boolean verification = DoubleStream.of(collectNumberOfFourierPoints).anyMatch(x -> x != collectNumberOfFourierPoints[0]);
		if(verification) {
			throw new IllegalArgumentException("Size of all experiments is not equal!");
		}
	}

	private SimpleMatrix executePreliminaryCoShifting(List<Object> experimentalDatasetsList, SimpleMatrix experimentalDatasetsMatrix) {

		SimpleMatrix result = new SimpleMatrix(experimentalDatasetsMatrix.numRows(), experimentalDatasetsMatrix.numCols());
		// backup settings made by user
		String backupTargetCalculationSelection = targetCalculationSelection.getTargetCalculationSelection();
		String backupIntervalSelection = intervalSelection.getIntervalSelection();
		String backupShiftCorrectionType = shiftCorrectionType.getShiftCorrectionType();
		String backupGapFillingType = gapFillingType.getGapFillingType();
		// change settings for preliminary Co-Shifting
		targetCalculationSelection.setTargetCalculationSelection("MEAN"); // or MEDIAN
		intervalSelection.setIntervalSelection("WHOLE_SPECTRUM");
		shiftCorrectionType.setShiftCorrectionType("FAST");
		gapFillingType.setGapFillingType("MARGIN");
		// co-shift the datasets
		LinkedHashMap<Integer, Integer> preliminaryIntervalRegionsMap = calculateIntervals(experimentalDatasetsList);
		result = alignSeveralIntervals(preliminaryIntervalRegionsMap, experimentalDatasetsMatrix);
		intervalSelection.IntervalRegionsMap.clear();
		// restore settings
		targetCalculationSelection.setTargetCalculationSelection(backupTargetCalculationSelection);
		intervalSelection.setIntervalSelection(backupIntervalSelection);
		shiftCorrectionType.setShiftCorrectionType(backupShiftCorrectionType);
		gapFillingType.setGapFillingType(backupGapFillingType);
		//
		return result;
	}

	private double[] calculateSelectedTarget(SimpleMatrix experimentalDatasetsMatrix) {

		double[] targetSpectrum = new double[experimentalDatasetsMatrix.numCols()];
		switch(targetCalculationSelection.getTargetCalculationSelection()) {
			case "MEAN":
				//
				targetSpectrum = calculateMeanTarget(experimentalDatasetsMatrix);
				break;
			case "MEDIAN":
				//
				targetSpectrum = calculateMedianTarget(experimentalDatasetsMatrix);
				break;
			case "MAX":
				//
				targetSpectrum = calculateMaxTarget(experimentalDatasetsMatrix);
				break;
		}
		targetCalculationSelection.setCalculatedTargetSelection(targetSpectrum);
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

	public String[] readPathsToMultipleDatasets(File multipleDataDir, String searchPattern) {

		int datasetCount = 0;
		for(File dataset : multipleDataDir.listFiles()) {
			if(dataset.isDirectory()) {
				// System.out.println(dataset.getName());
				datasetCount++;
			}
		}
		String[] datasetArray = new String[datasetCount];
		int idx = 0;
		for(File dataset : multipleDataDir.listFiles()) {
			if(dataset.isDirectory()) {
				datasetArray[idx] = dataset.getName().toString();
				idx++;
			}
		}
		// System.out.println("No. of datasets: " + datasetCount);
		int experimentCount = 0;
		try {
			Path startDir = multipleDataDir.toPath();
			// String searchPattern = "1r";
			boolean searchInFile = false;
			List<Path> result = FileVisitorScanner.searchFor(startDir, searchPattern, searchInFile);
			for(@SuppressWarnings("unused")
			Path path : result) {
				// System.out.println(path);
				experimentCount++;
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		String[] experimentArray = new String[experimentCount];
		idx = 0;
		try {
			Path startDir = multipleDataDir.toPath();
			// String searchPattern = "1r";
			boolean searchInFile = false;
			List<Path> result = FileVisitorScanner.searchFor(startDir, searchPattern, searchInFile);
			for(Path path : result) {
				experimentArray[idx] = path.toString();
				idx++;
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		// System.out.println("No. of experiments: " + experimentCount);
		String[] experimentalDataFinal = new String[datasetCount];
		if(datasetCount != experimentCount) {
			// System.out.println("No. of datasets != No. of experiments => clean up");
			String[] experimentArrayRemEnd = new String[experimentArray.length];
			idx = 0;
			for(String s : experimentArray) {
				experimentArrayRemEnd[idx] = StringUtils.removeEnd(s, "\\pdata\\1\\1r");
				idx++;
			}
			//
			String[] experimentalData = new String[datasetArray.length];
			String splittingPattern = Pattern.quote(System.getProperty("file.separator"));
			String experimentArraySplitOld = "9999999";
			String experimentArraySplitCurrent = "";
			idx = 0;
			for(String ds : datasetArray) {
				for(String ea : experimentArrayRemEnd) {
					//
					if(ea.contains(ds)) {
						// find lowest experiment number
						String[] eaSplit = ea.split(splittingPattern);
						experimentArraySplitCurrent = eaSplit[eaSplit.length - 1];
						if(Integer.parseInt(experimentArraySplitCurrent) < Integer.parseInt(experimentArraySplitOld)) {
							//
							experimentArraySplitOld = experimentArraySplitCurrent;
						}
					}
				}
				experimentalData[idx] = multipleDataDir.toString().concat(File.separator + ds + File.separator) + experimentArraySplitOld;
				idx++;
				experimentArraySplitOld = "9999999";
			}
			//
			idx = 0;
			for(String s : experimentalData) {
				experimentalDataFinal[idx] = s.concat("\\pdata\\1\\1r");
				idx++;
			}
		} else {
			// System.out.println("No. of datasets == No. of experiments => OK");
			experimentalDataFinal = experimentArray;
		}
		return experimentalDataFinal;
	}

	private static class FileVisitorScanner implements FileVisitor<Path> {

		/*
		 * https://docs.oracle.com/javase/tutorial/essential/io/walk.html
		 * https://docs.oracle.com/javase/tutorial/essential/io/find.html
		 */
		private String searchPattern = "";
		private List<Path> searchList;

		public static List<Path> searchFor(Path startDir, String searchPattern, boolean searchInFile) throws IOException {

			FileVisitorScanner fileVisitor = new FileVisitorScanner(searchPattern);
			Files.walkFileTree(startDir, fileVisitor);
			return fileVisitor.getResultList();
		}

		private FileVisitorScanner(String searchPattern) {

			this.searchPattern = searchPattern;
			this.searchList = new ArrayList<>();
		}

		public List<Path> getResultList() {

			return searchList;
		}

		@Override
		public FileVisitResult visitFile(Path path, BasicFileAttributes bfa) throws IOException {

			FileSystem fileSystem = FileSystems.getDefault();
			PathMatcher pathMatcher = fileSystem.getPathMatcher("glob:" + searchPattern);
			// for comparison strip name of file from path
			if(pathMatcher.matches(path.getFileName())) {
				searchList.add(path);
			}
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path path, IOException ex) throws IOException {

			System.out.println("visitFileFailed " + " Exception = " + ex);
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path path, IOException exc) throws IOException {

			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes attrs) throws IOException {

			return FileVisitResult.CONTINUE;
		}
	}

	public SimpleMatrix extractMultipleSpectra(List<Object> experimentalDatasetsList) {

		// List<Object> experimentalDatasetsList = new ArrayList<Object>();
		// experimentalDatasetsList = importMultipleDatasets(experimentalDatasets);
		//
		boolean firstDataset = true;
		SimpleMatrix experimentalDatasetsMatrix = null;
		int matrixRow = 0;
		//
		for(Object o : experimentalDatasetsList) {
			if(o instanceof IMeasurementNMR) {
				IMeasurementNMR measurementNMR = (IMeasurementNMR)o;
				IDataNMRSelection dataNMRSelection = new DataNMRSelection(measurementNMR);
				FourierTransformationProcessor fourierTransformation = new FourierTransformationProcessor();
				fourierTransformation.process(dataNMRSelection, new FourierTransformationSettings(), new NullProgressMonitor());
				// prepare matrix for storage of spectra >once< => aiming for comparison each spectrum should have the same size
				if(firstDataset) {
					int numberOfDatasets = experimentalDatasetsList.size();
					int datapointsPerDataset = dataNMRSelection.getMeasurmentNMR().getScanMNR().getNumberOfFourierPoints();
					experimentalDatasetsMatrix = new SimpleMatrix(numberOfDatasets, datapointsPerDataset);
					firstDataset = false;
				}
				ISignalExtractor signalExtractor = new SignalExtractor(dataNMRSelection);
				experimentalDatasetsMatrix.setRow(matrixRow, 0, signalExtractor.extractFourierTransformedDataRealPart());
				matrixRow++;
			}
		}
		return experimentalDatasetsMatrix;
	}

	public List<Object> importMultipleDatasets(String[] experimentalDatasets) {

		List<Object> experimentalDatasetsList = new ArrayList<Object>();
		//
		int counter = 0;
		for(String s : experimentalDatasets) {
			counter++;
			System.out.println("Importing dataset " + counter + " / " + experimentalDatasets.length);
			//
			File file = new File(s);
			IProcessingInfo processingInfo = ScanConverterNMR.convert(file, new NullProgressMonitor());
			Object object = processingInfo.getProcessingResult();
			if(object instanceof IMeasurementNMR) {
				// save each dataset object
				experimentalDatasetsList.add(object);
			}
		}
		//
		// safety check for length of each spectrum; has to be equal length!
		checkLengthOfEachSpectrum(experimentalDatasetsList);
		//
		return experimentalDatasetsList;
	}

	private LinkedHashMap<Integer, Integer> calculateIntervals(List<Object> experimentalDatasetsList) {

		LinkedHashMap<Integer, Integer> intervalRegionsMap = new LinkedHashMap<Integer, Integer>();
		/*
		 * For use with MS data, chromatograms, etc .:
		 * ~~~~~~~
		 * as long as the supplied object has an X-axis and a defined
		 * length, the algorithm should be able to work with it.
		 */
		// Object object = experimentalDatasetsList.get(0);
		IMeasurementNMR measureNMR = (IMeasurementNMR)experimentalDatasetsList.get(0);
		IDataNMRSelection dataNMRSelect = new DataNMRSelection(measureNMR);
		ISignalExtractor signalExtract = new SignalExtractor(dataNMRSelect);
		double[] chemicalShiftAxis = signalExtract.extractChemicalShift();
		int lengthOfDataset = dataNMRSelect.getMeasurmentNMR().getScanMNR().getNumberOfFourierPoints();
		//
		switch(intervalSelection.getIntervalSelection()) {
			case "SINGLE_PEAK":
				/*
				 * get left and right boundaries in ppm
				 * find indices in data
				 */
				double lowerBorder = intervalSelection.getSinglePeakLowerBorder();// 2.0d; // ppm, user input
				double higherBorder = intervalSelection.getSinglePeakHigherBorder(); // 2.2d; // ppm, user input
				UtilityFunctions utilityFunction = new UtilityFunctions();
				int lowerBorderIndex = utilityFunction.findIndexOfValue(chemicalShiftAxis, lowerBorder);
				int higherBorderIndex = utilityFunction.findIndexOfValue(chemicalShiftAxis, higherBorder);
				intervalRegionsMap.put(lowerBorderIndex, higherBorderIndex);
				break;
			case "WHOLE_SPECTRUM":
				/*
				 * no user input required
				 * start at index=0, end at index=lengthOfDataset-1
				 */
				intervalRegionsMap.put(0, lengthOfDataset - 1);
				break;
			case "NUMBER_OF_INTERVALS":
				/*
				 * divide present data in number of equal intervals
				 * save every interval in map with left and right boundaries
				 */
				int numberOfIntervals = intervalSelection.getNumberOfIntervals();// 100; // user input
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
					intervalRegionsMap.put(intervalStartValues[i], intervalEndValues[i]);
				}
				break;
			case "INTERVAL_LENGTH":
				/*
				 * divide present data by the amount of given datapoints (=length of interval) in equal intervals
				 * save every interval in map with left and right boundaries
				 */
				int lengthOfIntervals = intervalSelection.getLengthOfIntervals();// 1000; // user input
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
					intervalRegionsMap.put(intervalStarts[i], intervalEnds[i]);
				}
				break;
			case "USER_DEFINED_INTERVALS":
				/*
				 * take a map / import a file / read an integral list... with user defined intervals
				 * the boundaries will be in ppm (?) => double value!; find indices in data
				 * ***
				 * main difference to number/length methods: intervals may be discontiguous!
				 */
				LinkedHashMap<Double, Double> userDefIntervalRegions = new LinkedHashMap<Double, Double>();
				//
				// intervalSelection.setUserDefIntervalRegions(userDefIntervalRegions);
				// intervalSelection.setUserDefIntervalRegions(4.23, 3.21);
				userDefIntervalRegions = intervalSelection.getUserDefIntervalRegions();
				//
				UtilityFunctions utilityFunctionU = new UtilityFunctions();
				userDefIntervalRegions.entrySet().forEach((entry) -> {
					// get indices for each user defined interval
					double higherUserBorder = entry.getKey();
					double lowerUserBorder = entry.getValue();
					// System.out.println(higherUserBorder + "-" + lowerUserBorder);
					int lowerUserBorderIndex = utilityFunctionU.findIndexOfValue(chemicalShiftAxis, lowerUserBorder);
					int higherUserBorderIndex = utilityFunctionU.findIndexOfValue(chemicalShiftAxis, higherUserBorder);
					intervalRegionsMap.put(lowerUserBorderIndex, higherUserBorderIndex);
				});
				break;
		}
		intervalSelection.setIntervalRegionsMap(intervalRegionsMap);
		return intervalRegionsMap;
	}

	private int[] coshiftSpectra(SimpleMatrix experimentalDatasetsMatrix, Entry<Integer, Integer> interval) {

		//
		int[] referenceWindow = new int[interval.getValue() - interval.getKey() + 1];
		for(int i = 0; i < referenceWindow.length; ++i) {
			referenceWindow[i] = referenceWindow[i] + interval.getKey() + i;
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
		if(shiftCorrectionType.getShiftCorrectionType().contentEquals("USER_DEFINED")) {
			shiftCorrectionTypeValue = shiftCorrectionType.getShiftCorrectionTypeValue();
		}
		int sourceStep = 0;
		if(shiftCorrectionType.getShiftCorrectionType().contentEquals("FAST") || shiftCorrectionType.getShiftCorrectionType().contentEquals("BEST")) {
			// switch for the best automatic search on
			if(referenceWindowLength != 1) {
				localDimension = dimensionOfReferenceWindow[1];
			} else {
				localDimension = dimensionOfDataset[1];
			}
			//
			if(shiftCorrectionType.getShiftCorrectionType().contentEquals("FAST")) {
				fastAutomaticSearch = true;
			}
			if(fastAutomaticSearch) {
				shiftCorrectionTypeValue = localDimension - 1;
				shiftCorrectionType.setShiftCorrectionTypeValue(shiftCorrectionTypeValue);
				sourceStep = Math.round(localDimension / 2) - 1;
			} else {
				// change here the first searching point for the best "n"
				shiftCorrectionTypeValue = (int)Math.max(Math.floor(0.05 * localDimension), 10);
				shiftCorrectionType.setShiftCorrectionTypeValue(shiftCorrectionTypeValue);
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
		intervalSelection.setReferenceWindow(referenceWindow);
		/*
		 * calculate target here
		 * extract needed parts for co-shifting
		 */
		double[] targetSpectrum = null;
		SimpleMatrix experimentalDatasetsMatrixPartForProcessing = (SimpleMatrix)extractPartOfDataForProcessing(experimentalDatasetsMatrix);
		//
		if(targetCalculationSelection.getTargetCalculationSelection().contentEquals("MAX")) {
			// calculate max target here for each interval
			targetSpectrum = calculateSelectedTarget(experimentalDatasetsMatrixPartForProcessing);
			targetCalculationSelection.setCalculatedTargetSelection(targetSpectrum);
		} else {
			// "MEAN" and "MEDIAN"
			targetSpectrum = calculateSelectedTarget(experimentalDatasetsMatrix);
			double[] targetForProcessing = (double[])extractPartOfDataForProcessing(targetSpectrum);
			targetCalculationSelection.setCalculatedTargetSelection(targetForProcessing);
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
		while(!bestShift) {
			//
			bestShiftIteration++;
			System.out.println("Searching optimal max. shift: iteration #" + bestShiftIteration);
			for(int i = 0; i < numberOfBlocks; i++) {
				// FFT Co-Shifting
				shiftingValues = doFFTCoShifting(experimentalDatasetsMatrixPartForProcessing);
			}
			//
			if(shiftCorrectionType.getShiftCorrectionType().contentEquals("FAST") || shiftCorrectionType.getShiftCorrectionType().contentEquals("BEST")) {
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
					continue;
				}
				//
				shiftCorrectionTypeBorders[1] = tempNumber;
				shiftCorrectionTypeBorders[0] = -shiftCorrectionTypeBorders[1];
				//
				shiftCorrectionType.setShiftCorrectionTypeValue(shiftCorrectionTypeBorders[1]);
			} else {
				bestShift = true;
			}
			//
		}
		//
		return shiftingValues;
	}

	private Object extractPartOfDataForProcessing(Object data) {

		Object result = null;
		int[] referenceWindow = intervalSelection.getReferenceWindow();
		//
		if(data.getClass().isArray()) {
			// 1D array
			double[] targetSpectrum = (double[])data;
			//
			int idx = 0;
			double[] targetForProcessing = new double[referenceWindow.length];
			for(int i = referenceWindow[0]; i <= referenceWindow[referenceWindow.length - 1]; i++) {
				// extract necessary part of target vector
				targetForProcessing[idx] = targetSpectrum[i];
				idx++;
			}
			result = targetForProcessing;
		} else {
			// 2D matrix
			SimpleMatrix experimentalDatasetsMatrix = (SimpleMatrix)data;
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
			result = experimentalDatasetsMatrixPartForProcessing;
		}
		return result;
	}

	private int[] doFFTCoShifting(SimpleMatrix experimentalDatasetsMatrixPartForProcessing) {

		/*
		 * normalize data and prepare for calculations
		 */
		double[] targetForProcessing = targetCalculationSelection.getCalculatedTargetSelection();
		SimpleMatrix experimentalDatasetForFFT = (SimpleMatrix)normalizeDataBeforeCalculation(experimentalDatasetsMatrixPartForProcessing);
		double[] targetForFFT = (double[])normalizeDataBeforeCalculation(targetForProcessing);
		/*
		 * FFT shift Cross Correlation and determination of shifts
		 */
		int[] shiftValues = calculateFFTCrossCorrelation(targetForFFT, experimentalDatasetForFFT);
		//
		return shiftValues;
	}

	private Object normalizeDataBeforeCalculation(Object data) {

		Object result = null;
		if(data.getClass().isArray()) {
			// 1D array
			double[] targetForProcessing = (double[])data;
			Double targetNormalization = (double)calculateSquareRootOfSum(targetForProcessing);
			if(targetNormalization.compareTo(0.0) == 0) {//
				targetNormalization = 1.0;
			}
			double[] targetForFFT = new double[targetForProcessing.length];
			for(int i = 0; i < targetForProcessing.length; i++) {
				targetForFFT[i] = targetForProcessing[i] / targetNormalization;
			}
			result = targetForFFT;
		} else {
			// 2D matrix
			SimpleMatrix experimentalDatasetsMatrixPartForProcessing = (SimpleMatrix)data;
			SimpleMatrix datasetNormalization = (SimpleMatrix)calculateSquareRootOfSum(experimentalDatasetsMatrixPartForProcessing);
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
			result = experimentalDatasetForFFT;
		}
		return result;
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
			if(shiftValues[r] >= 0) {
				// left shift
				if(gapFillingType.getGapFillingType().contentEquals("ZERO")) {// fill array here - options: margin value OR 0
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
				if(gapFillingType.getGapFillingType().contentEquals("ZERO")) {// fill array here - options: margin value OR 0
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

	private SimpleMatrix alignSeveralIntervals(LinkedHashMap<Integer, Integer> intervalRegionsMap, SimpleMatrix experimentalDatasetsMatrix) {

		//
		Iterator<Entry<Integer, Integer>> intervalRegionsMapIterator = intervalRegionsMap.entrySet().iterator();
		int[] shiftingValues = new int[experimentalDatasetsMatrix.numRows()];
		SimpleMatrix alignedDatasets = new SimpleMatrix(experimentalDatasetsMatrix.numRows(), experimentalDatasetsMatrix.numCols());
		//
		if(intervalSelection.getIntervalSelection().contentEquals("USER_DEFINED_INTERVALS")) {
			alignedDatasets = experimentalDatasetsMatrix.copy();
		}
		//
		while(intervalRegionsMapIterator.hasNext()) {
			Entry<Integer, Integer> interval = intervalRegionsMapIterator.next();
			System.out.println("Aligning region from index " + interval.getKey().toString() + " to " + interval.getValue().toString());
			shiftingValues = coshiftSpectra(experimentalDatasetsMatrix, interval);
			// extract individual part for alignment
			SimpleMatrix matrixPart = (SimpleMatrix)extractPartOfDataForProcessing(experimentalDatasetsMatrix);
			// align individual part
			matrixPart = alignAllDatasets(shiftingValues, matrixPart);
			// combine all individual parts
			int insertCol = intervalSelection.getReferenceWindow()[0];
			alignedDatasets.insertIntoThis(0, insertCol, matrixPart);
		}
		return alignedDatasets;
	}

	private SimpleMatrix alignOneInterval(LinkedHashMap<Integer, Integer> intervalRegionsMap, SimpleMatrix experimentalDatasetsMatrix) {

		//
		Iterator<Entry<Integer, Integer>> intervalRegionsMapIterator = intervalRegionsMap.entrySet().iterator();
		int[] shiftingValues = new int[experimentalDatasetsMatrix.numRows()];
		SimpleMatrix alignedDatasets = new SimpleMatrix(experimentalDatasetsMatrix.numRows(), experimentalDatasetsMatrix.numCols());
		//
		while(intervalRegionsMapIterator.hasNext()) {
			Entry<Integer, Integer> interval = intervalRegionsMapIterator.next();
			shiftingValues = coshiftSpectra(experimentalDatasetsMatrix, interval);
		}
		// shifting datasets
		alignedDatasets = alignAllDatasets(shiftingValues, experimentalDatasetsMatrix);
		//
		return alignedDatasets;
	}

	private int[] calculateShiftValues(SimpleMatrix fouriertransformedDatasetCrossCorrelated) {

		UtilityFunctions utilityFunction = new UtilityFunctions();
		//
		int shiftCorrectionTypeValue = shiftCorrectionType.getShiftCorrectionTypeValue();
		int[] maxPeakPositions = new int[fouriertransformedDatasetCrossCorrelated.numRows()];
		double[] searchArray = null;
		for(int r = 0; r < fouriertransformedDatasetCrossCorrelated.numRows(); r++) {
			double[] shiftArray = fouriertransformedDatasetCrossCorrelated.extractVector(true, r).getMatrix().getData();
			// circular shift
			fouriertransformedDatasetCrossCorrelated.setRow(r, 0, utilityFunction.rightShiftNMRData(shiftArray, shiftArray.length / 2));
			searchArray = shiftArray;
			//
			if(!shiftCorrectionType.getShiftCorrectionType().contentEquals("FAST")) { // either USER_DEFINED or BEST
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

	private int[] calculateFFTCrossCorrelation(double[] targetForFFT, SimpleMatrix experimentalDatasetForFFT) {

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
		int[] shiftValues = calculateShiftValues(fouriertransformedDatasetCrossCorrelated);
		return shiftValues;
	}

	private double[] getRealPartOfComplexArray(Complex[] array) {

		double[] result = new double[array.length];
		for(int a = 0; a < array.length; a++) {
			result[a] = array[a].getReal();
		}
		return result;
	}

	private Object calculateSquareRootOfSum(Object data) {

		Object result = null;
		// compute sqrt(sum(element^2) of all elements in each row
		if(data.getClass().isArray()) {
			// 1D array
			double[] array = (double[])data;
			for(int i = 0; i < array.length; i++) {
				array[i] = Math.pow(array[i], 2);
			}
			double sum = Arrays.stream(array).sum();
			result = Math.sqrt(sum);
			return result;
		} else {
			// 2D matrix
			SimpleMatrix matrix = (SimpleMatrix)data;
			int rows = matrix.numRows();
			matrix = matrix.elementPower(2);
			SimpleMatrix sumMatrix = new SimpleMatrix(rows, 1);
			for(int r = 0; r < rows; r++) {
				double[] array = matrix.extractVector(true, r).getMatrix().getData();
				double sum = Arrays.stream(array).sum();
				sum = Math.sqrt(sum);
				sumMatrix.setRow(r, 0, sum);
			}
			return result = sumMatrix;
		}
	}

	private void checkShiftCorrectionTypeValueSize(LinkedHashMap<Integer, Integer> intervalRegionsMap) {

		int shiftCorrectionTypeValue = shiftCorrectionType.getShiftCorrectionTypeValue();
		intervalRegionsMap.entrySet().forEach((interval) -> {
			int intervalRange = interval.getValue() - interval.getKey() + 1;
			if((shiftCorrectionTypeValue > intervalRange) || ((shiftCorrectionTypeValue * 2) > intervalRange)) {
				throw new IllegalArgumentException(">shiftCorrectionTypeValue< must be not larger than the size of the smallest interval");
			}
		});
	}

	/*
	 * enums
	 */
	public enum INTERVAL_SELECTION {
		SINGLE_PEAK("SINGLE_PEAK"), // align spectra referencing a single peak
		WHOLE_SPECTRUM("WHOLE_SPECTRUM"), // align the whole spectrum
		NUMBER_OF_INTERVALS("NUMBER_OF_INTERVALS"), // align the spectrum divided in the given no. of intervals
		INTERVAL_LENGTH("INTERVAL_LENGTH"), // align the spectrum divided in intervals of given length
		USER_DEFINED_INTERVALS("USER_DEFINED_INTERVALS");// align user defined regions, e.g. integrals of interest

		private String intervalSelection;
		//
		double singlePeakLowerBorder = 2.21d; // ppm, user input
		double singlePeakHigherBorder = 2.41d; // ppm, user input
		//
		int numberOfIntervals = 100;
		//
		int lengthOfIntervals = 1000;
		//
		int[] referenceWindow;
		//
		LinkedHashMap<Double, Double> userDefIntervalRegions = new LinkedHashMap<Double, Double>();
		LinkedHashMap<Integer, Integer> IntervalRegionsMap = new LinkedHashMap<Integer, Integer>();

		public LinkedHashMap<Integer, Integer> getIntervalRegionsMap() {

			return IntervalRegionsMap;
		}

		public void setIntervalRegionsMap(LinkedHashMap<Integer, Integer> intervalRegionsMap) {

			IntervalRegionsMap = intervalRegionsMap;
		}

		public LinkedHashMap<Double, Double> getUserDefIntervalRegions() {

			return userDefIntervalRegions;
		}

		public void setUserDefIntervalRegions(LinkedHashMap<Double, Double> userDefIntervalRegions) {

			this.userDefIntervalRegions = userDefIntervalRegions;
		}

		public void setUserDefIntervalRegions(double lowerUserBorderIndex, double higherUserBorderIndex) {

			this.userDefIntervalRegions.put(lowerUserBorderIndex, higherUserBorderIndex);
		}

		public int getNumberOfIntervals() {

			return numberOfIntervals;
		}

		public void setNumberOfIntervals(int numberOfIntervals) {

			this.numberOfIntervals = numberOfIntervals;
		}

		public int getLengthOfIntervals() {

			return lengthOfIntervals;
		}

		public void setLengthOfIntervals(int lengthOfIntervals) {

			this.lengthOfIntervals = lengthOfIntervals;
		}

		public double getSinglePeakLowerBorder() {

			return singlePeakLowerBorder;
		}

		public void setSinglePeakLowerBorder(double singlePeakLowerBorder) {

			this.singlePeakLowerBorder = singlePeakLowerBorder;
		}

		public double getSinglePeakHigherBorder() {

			return singlePeakHigherBorder;
		}

		public void setSinglePeakHigherBorder(double singlePeakHigherBorder) {

			this.singlePeakHigherBorder = singlePeakHigherBorder;
		}

		public String getIntervalSelection() {

			return intervalSelection;
		}

		public void setIntervalSelection(String intervalSelection) {

			this.intervalSelection = intervalSelection;
		}

		private INTERVAL_SELECTION(String intervalSelection) {

			this.intervalSelection = intervalSelection;
		}

		@Override
		public String toString() {

			return intervalSelection;
		}

		public int[] getReferenceWindow() {

			return referenceWindow;
		}

		public void setReferenceWindow(int[] referenceWindow) {

			this.referenceWindow = referenceWindow;
		}
	}

	public INTERVAL_SELECTION intervalSelection = INTERVAL_SELECTION.SINGLE_PEAK;

	public enum SHIFT_CORRECTION_TYPE {
		FAST("FAST"), //
		BEST("BEST"), //
		USER_DEFINED("USER_DEFINED");// requires user input @shiftCorrectionTypeValue

		private String shiftCorrectionType;
		private int shiftCorrectionTypeValue = 50; // only positive values > 0 allowed

		public int getShiftCorrectionTypeValue() {

			return shiftCorrectionTypeValue;
		}

		public void setShiftCorrectionTypeValue(int shiftCorrectionTypeValue) {

			this.shiftCorrectionTypeValue = shiftCorrectionTypeValue;
		}

		public String getShiftCorrectionType() {

			return shiftCorrectionType;
		}

		public void setShiftCorrectionType(String shiftCorrectionType) {

			this.shiftCorrectionType = shiftCorrectionType;
		}

		private SHIFT_CORRECTION_TYPE(String shiftCorrectionType) {

			this.shiftCorrectionType = shiftCorrectionType;
		}

		@Override
		public String toString() {

			return shiftCorrectionType;
		}
	}

	public SHIFT_CORRECTION_TYPE shiftCorrectionType = SHIFT_CORRECTION_TYPE.FAST;

	public enum GAP_FILLING_TYPE {
		ZERO("ZERO"), // ""
		MARGIN("MARGIN"); //

		private String gapFillingType;

		public String getGapFillingType() {

			return gapFillingType;
		}

		public void setGapFillingType(String gapFillingType) {

			this.gapFillingType = gapFillingType;
		}

		private GAP_FILLING_TYPE(String gapFillingType) {

			this.gapFillingType = gapFillingType;
		}

		@Override
		public String toString() {

			return gapFillingType;
		}
	}

	public GAP_FILLING_TYPE gapFillingType = GAP_FILLING_TYPE.ZERO;

	public enum TARGET_CALCULATION_SELECTION {
		MEAN("MEAN"), //
		MEDIAN("MEDIAN"), //
		MAX("MAX");//

		private String targetCalculationSelection;
		private double[] calculatedTargetSelection;

		public String getTargetCalculationSelection() {

			return targetCalculationSelection;
		}

		public void setTargetCalculationSelection(String targetCalculationSelection) {

			this.targetCalculationSelection = targetCalculationSelection;
		}

		private TARGET_CALCULATION_SELECTION(String targetCalculationSelection) {

			this.targetCalculationSelection = targetCalculationSelection;
		}

		@Override
		public String toString() {

			return targetCalculationSelection;
		}

		public double[] getCalculatedTargetSelection() {

			return calculatedTargetSelection;
		}

		public void setCalculatedTargetSelection(double[] calculatedTargetSelection) {

			this.calculatedTargetSelection = calculatedTargetSelection;
		}
	}

	public TARGET_CALCULATION_SELECTION targetCalculationSelection = TARGET_CALCULATION_SELECTION.MEAN;

	public enum PRELIMINARY_CO_SHIFTING {
		YES("YES"), //
		NO("NO"); //

		private String preliminaryCoShifting;

		public String getPreliminaryCoShifting() {

			return preliminaryCoShifting;
		}

		public void setPreliminaryCoShifting(String preliminaryCoShifting) {

			this.preliminaryCoShifting = preliminaryCoShifting;
		}

		private PRELIMINARY_CO_SHIFTING(String preliminaryCoShifting) {

			this.preliminaryCoShifting = preliminaryCoShifting;
		}

		@Override
		public String toString() {

			return preliminaryCoShifting;
		}
	}

	public PRELIMINARY_CO_SHIFTING preliminaryCoShifting = PRELIMINARY_CO_SHIFTING.NO;
}
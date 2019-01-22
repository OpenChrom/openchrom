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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.stat.descriptive.rank.Median;
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
	 * Savorani et al., J. Magn. Reson. 202, Nr. 2 (1. Februar 2010): 190–202.
	 */

	public double[] CalculateMeanTarget(SimpleMatrix experimentalDatasetsMatrix) {

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

	public double[] CalculateMedianTarget(SimpleMatrix experimentalDatasetsMatrix) {

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

	public double[] CalculateMaxTarget(SimpleMatrix experimentalDatasetsMatrix) {

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
		/*
		 * TODO extract max interval only
		 */
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

	public static class FileVisitorScanner implements FileVisitor<Path> {

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
				/*
				 * signalExtractor.extractChemicalShift(); needed for alignment?
				 */
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
		return experimentalDatasetsList;
	}

	public LinkedHashMap<Integer, Integer> CalculateIntervals(List<Object> experimentalDatasetsList, String intervalsForAlignment) {

		/*
		 * refactor into ==> enum?
		 * NUMBER_OF_INTERVALS
		 * INTERVAL_LENGTH
		 * WHOLE_SPECTRUM
		 * SINGLE_PEAK
		 * USER_DEFINED_INTERVALS
		 */
		LinkedHashMap<Integer, Integer> intervalRegionsMap = new LinkedHashMap<Integer, Integer>();
		//
		// Object object = experimentalDatasetsList.get(0);
		IMeasurementNMR measureNMR = (IMeasurementNMR)experimentalDatasetsList.get(0);
		IDataNMRSelection dataNMRSelect = new DataNMRSelection(measureNMR);
		ISignalExtractor signalExtract = new SignalExtractor(dataNMRSelect);
		double[] chemicalShiftAxis = signalExtract.extractChemicalShift();
		int lengthOfDataset = dataNMRSelect.getMeasurmentNMR().getScanMNR().getNumberOfFourierPoints();
		//
		switch(intervalsForAlignment) {
			case "SINGLE_PEAK":
				/*
				 * get left and right boundaries in ppm
				 * find indices in data
				 */
				double lowerBorder = 2.0d; // ppm, user input
				double higherBorder = 2.2d; // ppm, user input
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
				int numberOfIntervals = 100; // user input
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
				int lengthOfIntervals = 1000; // user input
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
				 * the boundaries will be in ppm (?); find indices in data
				 * ***
				 * main difference to number/length methods: intervals may be discontiguous!
				 */
				LinkedHashMap<Double, Double> userDefIntervalRegions = new LinkedHashMap<Double, Double>();
				userDefIntervalRegions.put(9.765, 9.432);
				userDefIntervalRegions.put(5.864, 4.732);
				userDefIntervalRegions.put(4.284, 4.132);
				userDefIntervalRegions.put(2.724, 2.483);
				userDefIntervalRegions.put(1.999, 0.111);
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
		return intervalRegionsMap;
	}
}
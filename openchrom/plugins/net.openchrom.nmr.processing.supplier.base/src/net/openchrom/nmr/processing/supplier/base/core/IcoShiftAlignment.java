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
import java.util.List;
import java.util.regex.Pattern;

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

	public SimpleMatrix extractMultipleSpectra(String[] experimentalDatasets) {

		List<Object> experimentalDatasetsList = new ArrayList<Object>();
		experimentalDatasetsList = importMultipleDatasets(experimentalDatasets);
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
		for(String s : experimentalDatasets) {
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
}
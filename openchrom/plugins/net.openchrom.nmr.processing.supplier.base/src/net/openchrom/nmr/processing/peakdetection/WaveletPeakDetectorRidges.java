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
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.ejml.simple.SimpleMatrix;

public class WaveletPeakDetectorRidges {

	public static List<Integer> constructRidgeList(SimpleMatrix localMaxima, WaveletPeakDetectorSettings configuration, int gapThreshold, int skipValue) {

		int[] psiScales = ArrayUtils.addAll(new int[] { 0 }, configuration.getPsiScales());
		int numberOfColumns = localMaxima.numCols();
		int numberOfRows = localMaxima.numRows();

		List<Integer> maxIndexCurrent = WaveletPeakDetectorRidgesUtils.getMaxCurrentIndex(localMaxima, numberOfColumns);

		int[] columnIndex = WaveletPeakDetectorRidgesUtils.generateColumnIndex(numberOfColumns);

		LinkedHashMap<Integer, List<Integer>> ridgeList = new LinkedHashMap<Integer, List<Integer>>();
		LinkedHashMap<Integer, Integer> peakStatus = new LinkedHashMap<Integer, Integer>();
		for(Integer key : maxIndexCurrent) {
			List<Integer> values = new ArrayList<Integer>();
			values.add(key);
			ridgeList.put(key, values);
			peakStatus.put(key, 0);
		}

		List<Integer> orphanRidgeList = new ArrayList<Integer>();
		List<Integer> orphanRidgeName = new ArrayList<Integer>();

		int numberOfLevels = columnIndex.length;

		// main loop
		for(int i = 0; i < numberOfLevels; i++) {
			int localColumn = columnIndex[i];
			int localScale = psiScales[localColumn];
			if(columnIndex[i] == skipValue) {
				for(Integer key : ridgeList.keySet()) {
					ridgeList.get(key).add(key);
				}
				continue;
			}
			if(maxIndexCurrent.size() == 0) {
				maxIndexCurrent = WaveletPeakDetectorRidgesUtils.getMaxCurrentIndex(localMaxima, localColumn);
				continue;
			}
			// slide window size
			int localWindowSize = WaveletPeakDetectorMaximaUtils.calculateWindowSize(localScale, configuration);

			// prüfen ob richtiger Typ
			LinkedHashMap<Integer, Integer> selectedPeaks = new LinkedHashMap<Integer, Integer>();
			LinkedHashMap<Integer, Integer> removePeak = new LinkedHashMap<Integer, Integer>();

			// inner loop
			for(int k = 0; k < maxIndexCurrent.size(); k++) {
				int localIndex = maxIndexCurrent.get(k);
				int start = ((localIndex - localWindowSize < 1) ? 1 : localIndex - localWindowSize);
				int end = ((localIndex + localWindowSize > numberOfRows) ? numberOfRows : localIndex + localWindowSize);
				//
				List<Integer> currentIndex = new ArrayList<Integer>();
				double[] partOfMaxColumn = WaveletPeakDetectorMaximaUtils.extractMatrixElements(localMaxima, false, localColumn);
				for(int p = start; p < end; p++) {
					if(partOfMaxColumn[p] > 0) {
						currentIndex.add(p + start);
					}
				}

				if(currentIndex.size() == 0) {

				}
			}
		}
		return null;
	}
}

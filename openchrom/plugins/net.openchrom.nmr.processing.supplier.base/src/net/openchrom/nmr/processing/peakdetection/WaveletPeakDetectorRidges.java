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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.ejml.simple.SimpleMatrix;

import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions;

public class WaveletPeakDetectorRidges {

	public static List<Integer> constructRidgeList(SimpleMatrix localMaxima, WaveletPeakDetectorSettings configuration, int gapThreshold, int skipValue) {

		int[] psiScales = ArrayUtils.addAll(new int[] { 0 }, configuration.getPsiScales());
		int numberOfColumns = localMaxima.numCols();
		int numberOfRows = localMaxima.numRows();

		List<Integer> currentMaxIndex = WaveletPeakDetectorRidgesUtils.getMaxCurrentIndex(localMaxima, numberOfColumns);
		LinkedHashMap<Integer, List<Integer>> ridgeList = new LinkedHashMap<Integer, List<Integer>>();
		LinkedHashMap<Integer, Integer> peakStatus = new LinkedHashMap<Integer, Integer>();
		for(Integer key : currentMaxIndex) {
			List<Integer> values = new ArrayList<Integer>();
			values.add(key);
			ridgeList.put(key, values);
			peakStatus.put(key, 0);
		}
		LinkedHashMap<String, List<Integer>> orphanRidgeList = new LinkedHashMap<String, List<Integer>>();

		// main loop
		int[] columnIndex = WaveletPeakDetectorRidgesUtils.generateColumnIndex(numberOfColumns);
		int numberOfLevels = columnIndex.length;
		for(int i = 0; i < numberOfLevels; i++) {
			int localColumn = columnIndex[i];
			int localScale = psiScales[localColumn];
			if(columnIndex[i] == skipValue) {
				for(Integer key : ridgeList.keySet()) {
					ridgeList.get(key).add(key);
				}
				continue;
			}
			if(currentMaxIndex.size() == 0) {
				currentMaxIndex = WaveletPeakDetectorRidgesUtils.getMaxCurrentIndex(localMaxima, localColumn);
				continue;
			}
			// slide window size
			int localWindowSize = WaveletPeakDetectorMaximaUtils.calculateWindowSize(localScale, configuration);

			// prüfen ob richtiger Typ
			// LinkedHashMap<Integer, Integer> selectedPeaks = new LinkedHashMap<Integer,
			// Integer>();
			// LinkedHashMap<Integer, Integer> removePeak = new LinkedHashMap<Integer,
			// Integer>();
			List<Integer> localSelectedPeaks = new ArrayList<Integer>();
			List<Integer> localRemovePeaks = new ArrayList<Integer>();

			// inner loop
			for(int k = 0; k < currentMaxIndex.size(); k++) {
				int localIndex = currentMaxIndex.get(k);
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
					int localStatus = peakStatus.get(localIndex);
					if(localStatus == 0) {
						localStatus = gapThreshold + 1;
					}
					if(localStatus > gapThreshold & localScale >= 2) {
						List<Integer> tempList = ridgeList.get(localIndex);
						List<Integer> values = tempList.subList(0, tempList.size() - localStatus);
						String key = String.valueOf(localColumn + localStatus + 1) + "_" + String.valueOf(localIndex);
						orphanRidgeList.put(key, values);
						localRemovePeaks.add(localIndex);
						continue;
					} else {
						currentIndex.add(localIndex);
						peakStatus.put(localIndex, localStatus + 1);
					}
				} else {
					peakStatus.put(localIndex, 0);
					if(currentIndex.size() >= 2) {
						double[] tempIndex = new double[currentIndex.size()];
						for(int j = 0; j < currentIndex.size(); j++) {
							tempIndex[j] = Math.abs(currentIndex.get(j) - localIndex);
						}

						double minValue = UtilityFunctions.getMinValueOfArray(tempIndex);
						for(Integer element : currentIndex) {
							if(!element.equals(Double.valueOf(minValue).intValue())) {
								currentIndex.remove(element);
							}
						}
					}
				}
				ridgeList.put(localIndex, currentIndex);
				localSelectedPeaks.add(currentIndex.get(0));
			} // end inner loop

			// Remove disconnected lines
			if(localRemovePeaks.size() > 0) {
				for(Integer removePeak : localRemovePeaks) {
					if(ridgeList.containsKey(removePeak)) {
						ridgeList.remove(removePeak);
						peakStatus.remove(removePeak);
					}
				}
			}

			// Check for duplicated selected peaks;only keep the one with the longest path
			List<Integer> localUniquePeaks = localSelectedPeaks.stream().distinct().collect(Collectors.toList());
			if(localSelectedPeaks.size() > localUniquePeaks.size()) {
				//
				List<Integer> localDiplicatedPeaks = localSelectedPeaks.stream().filter(not(new HashSet<>(localUniquePeaks)::contains)).collect(Collectors.toList());

			}
		}
		return null;
	}

	private static <T> Predicate<T> not(Predicate<T> predicate) {

		return predicate.negate();
	}
}

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
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.ejml.simple.SimpleMatrix;

import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions;

public class WaveletPeakDetectorRidges {

	public static LinkedHashMap<String, List<Integer>> constructRidgeList(SimpleMatrix localMaxima, WaveletPeakDetectorSettings configuration, int gapThreshold, int skipValue) {

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
		LinkedHashMap<String, List<Integer>> finalRidgeList = new LinkedHashMap<String, List<Integer>>();
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
				List<Integer> duplicatedPeaksToRemoveIndices = new ArrayList<>();
				List<Integer> localDiplicatedPeaks = localSelectedPeaks.stream().filter(not(new HashSet<>(localUniquePeaks)::contains)).collect(Collectors.toList());
				Integer maxLengthOfMatchingIndices = null;
				for(Integer duplicatedPeak : localDiplicatedPeaks) {
					// get indices for duplicate peaks
					List<Integer> matchingIndicesForDuplicatePeaks = new ArrayList<>();
					for(int m = 0; m < localSelectedPeaks.size(); m++) {
						Integer localSelectedPeak = localSelectedPeaks.get(m);
						if(duplicatedPeak.equals(localSelectedPeak)) {
							matchingIndicesForDuplicatePeaks.add(m); // 99 346
						}
					}
					// get length of these duplicate peak ridges
					List<Integer> lengthOfMatchingIndices = new ArrayList<>();
					for(Integer matchingIndex : matchingIndicesForDuplicatePeaks) {
						int match = 0;
						Iterator<Entry<Integer, List<Integer>>> iterator = ridgeList.entrySet().iterator();
						while (iterator.hasNext()) {
							Entry<Integer, List<Integer>> peakRidgeEntry = iterator.next();
							if(duplicatedPeak.equals(peakRidgeEntry.getKey()) & matchingIndex.equals(match)) {
								// double check necessary above?
								lengthOfMatchingIndices.add(peakRidgeEntry.getValue().size()); // 9 3
							}
							match++;
						}
					}
					maxLengthOfMatchingIndices = Collections.max(lengthOfMatchingIndices);
					int positionOfBetterDuplicatePeak = lengthOfMatchingIndices.indexOf(maxLengthOfMatchingIndices);
					matchingIndicesForDuplicatePeaks.remove(positionOfBetterDuplicatePeak);
					// collect all indices of duplicate peaks with short ridges
					duplicatedPeaksToRemoveIndices.addAll(matchingIndicesForDuplicatePeaks);
					// fill in the orphanRidgeList
					String key = String.valueOf(localColumn + "_" + localSelectedPeaks.get(maxLengthOfMatchingIndices));
					List<Integer> values = ridgeList.get(positionOfBetterDuplicatePeak);
					orphanRidgeList.put(key, values);
				}
				// finally remove all "bad" peaks
				for(Integer removeIndex : duplicatedPeaksToRemoveIndices) {
					localSelectedPeaks.remove(removeIndex);
					ridgeList.keySet().stream().collect(Collectors.toList()).remove(removeIndex);
					peakStatus.keySet().stream().collect(Collectors.toList()).remove(removeIndex);
				}
			}
			//
			if(localScale >= 2) {
				List<Integer> nextCurrentMaxIndex = WaveletPeakDetectorRidgesUtils.getMaxCurrentIndex(localMaxima, localColumn);
				List<Integer> addPeaks = nextCurrentMaxIndex.stream().filter(not(new HashSet<>(localSelectedPeaks)::contains)).collect(Collectors.toList());
				if(addPeaks.size() > 1) {
					for(int p = 0; p < addPeaks.size(); p++) {
						ridgeList.put(addPeaks.get(p), new ArrayList<Integer>(addPeaks.get(p)));
					}
				} else {
					ridgeList.put(addPeaks.get(0), addPeaks);
				}
				// add to currentMaxIndex =>localSelectedPeaks + new peaks
//				List<Integer> tempList = new ArrayList<Integer>();
//				tempList.addAll(localSelectedPeaks);
//				tempList.addAll(addPeaks);
//				Collections.sort(tempList);
				addPeaks.stream().filter(not(new HashSet<>(currentMaxIndex)::contains)).forEachOrdered(currentMaxIndex::add);
				for(Integer status : addPeaks) {
					peakStatus.put(status, 0);
				}
			} else {
				currentMaxIndex.clear();
				currentMaxIndex.addAll(localSelectedPeaks);
			}
			// prepare final ridge list

			Iterator<Entry<Integer, List<Integer>>> iterator = ridgeList.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<Integer, List<Integer>> peakRidgeEntry = iterator.next();
				String newKey = "1_" + String.valueOf(peakRidgeEntry.getKey());
				finalRidgeList.put(newKey, peakRidgeEntry.getValue());
			}
			/*
			 * this call is equivalent to that of calling put(k, v) - check for duplicates?
			 * map1.keySet().equals(map2.keySet());
			 */
			finalRidgeList.putAll(orphanRidgeList);
			//
			Iterator<Entry<String, List<Integer>>> finalIterator = finalRidgeList.entrySet().iterator();
			while (finalIterator.hasNext()) {
				Entry<String, List<Integer>> finalEntry = finalIterator.next();
				List<Integer> reversedList = finalEntry.getValue();
				Collections.reverse(reversedList);
				finalRidgeList.put(finalEntry.getKey(), reversedList);
			}
		}
		return finalRidgeList;
	}

	private static <T> Predicate<T> not(Predicate<T> predicate) {

		return predicate.negate();
	}
}

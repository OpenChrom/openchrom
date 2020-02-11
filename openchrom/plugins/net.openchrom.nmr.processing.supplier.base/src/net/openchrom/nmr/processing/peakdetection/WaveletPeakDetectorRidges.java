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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import net.openchrom.nmr.processing.peakdetection.peakmodel.CwtPeakSupport;
import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions;

public class WaveletPeakDetectorRidges {

	public static void constructRidgeList(CwtPeakSupport cwtPeakSupport, WaveletPeakDetectorSettings configuration) {

		int[] extendedPsiScales = configuration.getExtendedPsiScales();
		List<Integer> currentMaxIndex = WaveletPeakDetectorRidgesUtils.getMaxCurrentIndex(cwtPeakSupport.getLocalMaxima(), cwtPeakSupport.getLocalMaxima().numCols());
		int skipValue = WaveletPeakDetectorRidgesUtils.checkSkipValue(configuration.getSkipValue(), cwtPeakSupport.getLocalMaxima().numCols());

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
		int[] columnIndex = WaveletPeakDetectorRidgesUtils.generateColumnIndex(cwtPeakSupport.getLocalMaxima().numCols());
		int numberOfLevels = columnIndex.length;
		LinkedHashMap<String, List<Integer>> finalRidgeList = new LinkedHashMap<String, List<Integer>>();
		for(int i = 0; i < numberOfLevels; i++) {
			int localColumn = columnIndex[i];
			int localScale = extendedPsiScales[localColumn];
			if(columnIndex[i] == skipValue) {
				for(Integer key : ridgeList.keySet()) {
					ridgeList.get(key).add(key);
				}
				continue;
			}
			if(currentMaxIndex.size() == 0) {
				currentMaxIndex = WaveletPeakDetectorRidgesUtils.getMaxCurrentIndex(cwtPeakSupport.getLocalMaxima(), localColumn);
				continue;
			}

			// inner loop
			List<Integer> localSelectedPeaks = new ArrayList<Integer>();
			List<Integer> localRemovePeaks = new ArrayList<Integer>();
			for(int k = 0; k < currentMaxIndex.size(); k++) {
				int localIndex = currentMaxIndex.get(k);
				System.out.println(localIndex);
				HashMap<String, Integer> currentIndexBounds = WaveletPeakDetectorRidgesUtils.getCurrentIndexBounds(localIndex, cwtPeakSupport.getLocalMaxima().numRows(), localScale, configuration);
				double[] partOfMaxColumn = WaveletPeakDetectorMaximaUtils.extractMatrixElements(cwtPeakSupport.getLocalMaxima(), false, localColumn);
				List<Integer> currentIndex = WaveletPeakDetectorRidgesUtils.calculateCurrentIndex(partOfMaxColumn, currentIndexBounds);

				if(currentIndex.size() == 0) {
					Integer localStatus = peakStatus.get(localIndex);
					if(localStatus == null /* || localStatus.SIZE == 0 */) {
						localStatus = configuration.getGapThreshold() + 1;
					}
					if(localStatus > configuration.getGapThreshold() && localScale >= 2) {
						List<Integer> tempList = ridgeList.get(localIndex);
						// FIXME NPE after accessing a localIndex successfully several times that index
						// seem to "disappear"?!
						List<Integer> values = tempList.subList(0, tempList.size() - localStatus);
						WaveletPeakDetectorRidgesUtils.fillRidgeListMaps(orphanRidgeList, localColumn + localStatus + 1, localIndex, values);
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

						int minValue = (int) UtilityFunctions.getMinValueOfArray(tempIndex);
						for(Integer element : currentIndex) {
							if(!element.equals(minValue)) {
								currentIndex.remove(element);
							}
						}
					}
				}
				ridgeList.get(localIndex).addAll(currentIndex);
				// ridgeList.put(localIndex, currentIndex);
				localSelectedPeaks.add(currentIndex.get(0));
			} // end inner loop

			// Remove disconnected ridge lines
			WaveletPeakDetectorRidgesUtils.removeDisconnectedLines(localRemovePeaks, ridgeList, peakStatus);

			// Check for duplicated selected peaks;only keep the one with the longest path
			List<Integer> localUniquePeaks = localSelectedPeaks.stream().distinct().collect(Collectors.toList());
			if(localSelectedPeaks.size() > localUniquePeaks.size()) {
				List<Integer> duplicatedPeaksToRemoveIndices = new ArrayList<>();
				List<Integer> localDiplicatedPeaks = localSelectedPeaks.stream().filter(WaveletPeakDetectorRidgesUtils.not(new HashSet<>(localUniquePeaks)::contains)).collect(Collectors.toList());

				for(Integer duplicatedPeak : localDiplicatedPeaks) {
					// get indices for duplicate peaks and the length of their ridges
					List<Integer> matchingIndicesForDuplicatePeaks = WaveletPeakDetectorRidgesUtils.getIndicesForDuplicatePeaks(localSelectedPeaks, duplicatedPeak);
					List<Integer> lengthOfMatchingIndices = WaveletPeakDetectorRidgesUtils.getLengthOfDuplicatePeakRidges(matchingIndicesForDuplicatePeaks, ridgeList, duplicatedPeak);
					// find the better peak e.g. with longer ridge and collect only indices of
					// duplicate peaks with short ridges
					matchingIndicesForDuplicatePeaks.remove(lengthOfMatchingIndices.indexOf(Collections.max(lengthOfMatchingIndices)));
					duplicatedPeaksToRemoveIndices.addAll(matchingIndicesForDuplicatePeaks);
					// fill peaks with short ridges in the orphanRidgeList
					List<Integer> values = ridgeList.get(lengthOfMatchingIndices.indexOf(Collections.max(lengthOfMatchingIndices)));
					WaveletPeakDetectorRidgesUtils.fillRidgeListMaps(orphanRidgeList, localColumn, localSelectedPeaks.get(Collections.max(lengthOfMatchingIndices)), values);
				}
				// finally remove all peaks with shorter ridges
				WaveletPeakDetectorRidgesUtils.removePeaksWithShortRidges(duplicatedPeaksToRemoveIndices, localSelectedPeaks, ridgeList, peakStatus);
			}
			//
			if(localScale >= 2) {
				List<Integer> nextCurrentMaxIndex = WaveletPeakDetectorRidgesUtils.getMaxCurrentIndex(cwtPeakSupport.getLocalMaxima(), localColumn);
				List<Integer> newPeaksToAdd = nextCurrentMaxIndex.stream().filter(WaveletPeakDetectorRidgesUtils.not(new HashSet<>(localSelectedPeaks)::contains)).collect(Collectors.toList());
				if(newPeaksToAdd.size() > 1) {
					for(int p = 0; p < newPeaksToAdd.size(); p++) {
						ridgeList.put(newPeaksToAdd.get(p), new ArrayList<Integer>(newPeaksToAdd.get(p)));
					}
				} else {
					ridgeList.put(newPeaksToAdd.get(0), newPeaksToAdd);
				}
				// add to currentMaxIndex =>localSelectedPeaks + new peaks
				newPeaksToAdd.stream().filter(WaveletPeakDetectorRidgesUtils.not(new HashSet<>(currentMaxIndex)::contains)).forEachOrdered(currentMaxIndex::add);
				for(Integer status : newPeaksToAdd) {
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
				WaveletPeakDetectorRidgesUtils.fillRidgeListMaps(finalRidgeList, 1, peakRidgeEntry.getKey(), peakRidgeEntry.getValue());
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
		cwtPeakSupport.setRidgeList(finalRidgeList);
	}
}

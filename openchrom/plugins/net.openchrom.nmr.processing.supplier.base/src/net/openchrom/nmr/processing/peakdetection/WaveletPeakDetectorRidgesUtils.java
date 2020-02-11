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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.ejml.simple.SimpleMatrix;

import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions;

public class WaveletPeakDetectorRidgesUtils {

	static List<Integer> getMaxCurrentIndex(SimpleMatrix localMaxima, int maxVector) {

		List<Integer> maxIndexCurrent = new ArrayList<Integer>();
		for(int i = 0; i < localMaxima.numRows(); i++) {
			double[] tempRow = UtilityFunctions.extractVectorFromMatrix(localMaxima, true, i);
			if(tempRow[maxVector - 1] > 0) {
				maxIndexCurrent.add(i);
			}
		}
		return maxIndexCurrent;
	}

	static int[] generateColumnIndex(int maxVector) {

		int[] columnIndex = new int[maxVector - 1];
		int position = 0;
		if(maxVector > 1) {
			for(int i = maxVector - 1; i > 0; i--) {
				columnIndex[position] = i;
				position++;
			}
		}
		return columnIndex;
	}

	static int checkSkipValue(int skipValue, int numberOfColumns) {

		if(skipValue == 0) {
			return skipValue = numberOfColumns + 1;
		} else {
			return skipValue;
		}
	}

	static <T> Predicate<T> not(Predicate<T> predicate) {

		return predicate.negate();
	}

	static HashMap<String, Integer> getCurrentIndexBounds(int localIndex, int numberOfRows, int localScale, WaveletPeakDetectorSettings configuration) {

		// slide window size
		int localWindowSize = WaveletPeakDetectorMaximaUtils.calculateWindowSize(localScale, configuration);
		// calculate start / end values
		HashMap<String, Integer> bounds = new HashMap<>();
		bounds.put("start", ((localIndex - localWindowSize < 1) ? 1 : localIndex - localWindowSize));
		bounds.put("end", ((localIndex + localWindowSize > numberOfRows) ? numberOfRows : localIndex + localWindowSize));
		return bounds;
	}

	static List<Integer> calculateCurrentIndex(double[] partOfMaxColumn, HashMap<String, Integer> currentIndexBounds) {

		List<Integer> currentIndex = new ArrayList<Integer>();
		for(int p = currentIndexBounds.get("start"); p < currentIndexBounds.get("end"); p++) {
			if(partOfMaxColumn[p] > 0) {
				// TODO check later on if index is still correct without addition
				currentIndex.add(p /* + currentIndexBounds.get("start") - 1 */);
			}
		}
		return currentIndex;
	}

	static void fillRidgeListMaps(LinkedHashMap<String, List<Integer>> ridgeListMap, int prefix, int postfix, List<Integer> values) {

		String key = String.valueOf(prefix + "_" + postfix);
		ridgeListMap.put(key, values);
	}

	static void removeDisconnectedLines(List<Integer> localRemovePeaks, LinkedHashMap<Integer, List<Integer>> ridgeList, LinkedHashMap<Integer, Integer> peakStatus) {

		if(localRemovePeaks.size() > 0) {
			for(Integer removePeak : localRemovePeaks) {
				if(ridgeList.containsKey(removePeak)) {
					ridgeList.remove(removePeak);
					peakStatus.remove(removePeak);
				}
			}
		}
	}

	static List<Integer> getIndicesForDuplicatePeaks(List<Integer> localSelectedPeaks, Integer duplicatedPeak) {

		List<Integer> matchingIndicesForDuplicatePeaks = new ArrayList<>();
		for(int m = 0; m < localSelectedPeaks.size(); m++) {
			Integer localSelectedPeak = localSelectedPeaks.get(m);
			if(duplicatedPeak.equals(localSelectedPeak)) {
				matchingIndicesForDuplicatePeaks.add(m); // 99 346
			}
		}
		return matchingIndicesForDuplicatePeaks;
	}

	static List<Integer> getLengthOfDuplicatePeakRidges(List<Integer> matchingIndicesForDuplicatePeaks, LinkedHashMap<Integer, List<Integer>> ridgeList, Integer duplicatedPeak) {

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
		return lengthOfMatchingIndices;
	}

	static void removePeaksWithShortRidges(List<Integer> duplicatedPeaksToRemoveIndices, List<Integer> localSelectedPeaks, LinkedHashMap<Integer, List<Integer>> ridgeList, LinkedHashMap<Integer, Integer> peakStatus) {

		for(Integer removeIndex : duplicatedPeaksToRemoveIndices) {
			localSelectedPeaks.remove(removeIndex);
			ridgeList.keySet().stream().collect(Collectors.toList()).remove(removeIndex);
			peakStatus.keySet().stream().collect(Collectors.toList()).remove(removeIndex);
		}
	}

}

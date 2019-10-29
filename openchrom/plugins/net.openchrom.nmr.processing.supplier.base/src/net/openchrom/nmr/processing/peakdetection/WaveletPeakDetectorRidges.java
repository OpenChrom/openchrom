package net.openchrom.nmr.processing.peakdetection;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.ejml.simple.SimpleMatrix;

public class WaveletPeakDetectorRidges {

	public static List<Integer> constructRidgeList(SimpleMatrix localMaxima, WaveletPeakDetectorSettings configuration, int gapThreshold, int skipValue) {

		int[] psiScales = configuration.getPsiScales();
		int maxVector = localMaxima.numCols();
		int numberOfRows = localMaxima.numRows();
		int minimumWindowSize = 5;

		List<Integer> maxIndexCurrent = getMaxCurrentIndex(localMaxima, maxVector);

		int[] columnIndex = new int[maxVector - 1];
		int position = 0;
		if(maxVector > 1) {
			for(int i = maxVector - 1; i > 0; i--) {
				columnIndex[position] = i;
				position++;
			}
		}

		LinkedHashMap<Integer, List<Integer>> ridgeList = new LinkedHashMap<Integer, List<Integer>>();
		LinkedHashMap<Integer, Integer> peakStatus = new LinkedHashMap<Integer, Integer>();
		for(Integer key : maxIndexCurrent) {
			List<Integer> keys = new ArrayList<Integer>();
			keys.add(key);
			ridgeList.put(key, keys);
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
				maxIndexCurrent = getMaxCurrentIndex(localMaxima, localColumn);
				continue;
			}
			// slide window size
			int localWindowSize = localScale * 2 + 1;
			if(localWindowSize < minimumWindowSize) {
				localWindowSize = minimumWindowSize;
			}

			// prüfen ob richtiger Typ
			LinkedHashMap<Integer, Integer> selectedPeaks = new LinkedHashMap<Integer, Integer>();
			LinkedHashMap<Integer, Integer> removePeak = new LinkedHashMap<Integer, Integer>();

			// inner loop
			for(int k = 0; i < maxIndexCurrent.size(); i++) {
				int localIndex = maxIndexCurrent.get(k);
				int start = ((localIndex - localWindowSize < 1) ? 1 : localIndex - localWindowSize);
				int end = ((localIndex + localWindowSize > numberOfRows) ? numberOfRows : localIndex + localWindowSize);
				//
				List<Integer> currentIndex = new ArrayList<Integer>();
				double[] partOfMaxColumn = localMaxima.extractVector(false, localColumn).getMatrix().getData();
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

	private static List<Integer> getMaxCurrentIndex(SimpleMatrix localMaxima, int maxVector) {

		List<Integer> maxIndexCurrent = new ArrayList<Integer>();
		for(int i = 0; i < localMaxima.numRows(); i++) {
			double[] tempRow = localMaxima.extractVector(true, i).getMatrix().getData();
			if(tempRow[maxVector - 1] > 0) {
				maxIndexCurrent.add(i);
			}
		}
		return maxIndexCurrent;
	}

}

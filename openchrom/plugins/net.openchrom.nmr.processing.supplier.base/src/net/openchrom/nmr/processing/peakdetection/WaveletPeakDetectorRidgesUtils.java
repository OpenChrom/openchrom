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
import java.util.List;

import org.ejml.simple.SimpleMatrix;

public class WaveletPeakDetectorRidgesUtils {

	static List<Integer> getMaxCurrentIndex(SimpleMatrix localMaxima, int maxVector) {
	
		List<Integer> maxIndexCurrent = new ArrayList<Integer>();
		for(int i = 0; i < localMaxima.numRows(); i++) {
			double[] tempRow = localMaxima.extractVector(true, i).getMatrix().getData();
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

}

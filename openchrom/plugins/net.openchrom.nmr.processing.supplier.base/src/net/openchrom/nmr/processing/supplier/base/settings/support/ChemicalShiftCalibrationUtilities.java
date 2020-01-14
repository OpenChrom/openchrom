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
package net.openchrom.nmr.processing.supplier.base.settings.support;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;

import org.eclipse.chemclipse.nmr.model.core.SpectrumMeasurement;
import org.ejml.simple.SimpleMatrix;

import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions;
import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions.SpectrumData;
import net.openchrom.nmr.processing.supplier.base.settings.IcoShiftAlignmentSettings;
import net.openchrom.nmr.processing.supplier.base.settings.support.IcoShiftAlignmentUtilities.Interval;

public class ChemicalShiftCalibrationUtilities {

	public static boolean isSamePeakPosition(int[] actualPositions, int intendedPosition) {

		for(int p : actualPositions) {
			if(p != intendedPosition) {
				return false;
			}
		}
		return true;
	}

	public static BigDecimal[] getChemicalShiftAxis(Collection<? extends SpectrumMeasurement> experimentalDatasetsList) {

		SpectrumMeasurement measurementNMR = experimentalDatasetsList.iterator().next();
		SpectrumData spectrumData = UtilityFunctions.toComplexSpectrumData(measurementNMR);
		return spectrumData.chemicalShift;
	}

	public static int[] getActualPeakPositions(Interval<Integer> intervalIndices, SimpleMatrix calibratedData) {

		int numRowsMax = calibratedData.numRows();
		int[] actualPositions = new int[numRowsMax];
		for(int r = 0; r < numRowsMax; r++) {
			double[] rowVector = IcoShiftAlignmentUtilities.extractVectorFromMatrix(calibratedData, true, r);
			double[] rowVectorPart = new double[intervalIndices.getStart() - intervalIndices.getStop()];
			//
			System.arraycopy(rowVector, intervalIndices.getStop(), rowVectorPart, 0, intervalIndices.getStart() - intervalIndices.getStop());
			double maxValue = UtilityFunctions.getMaxValueOfArray(rowVectorPart);
			actualPositions[r] = UtilityFunctions.findIndexOfValue(rowVectorPart, maxValue);
		}
		return actualPositions;
	}

	public static int getIntendedPeakPosition(Interval<Integer> intervalIndices, BigDecimal[] chemicalShiftAxis, IcoShiftAlignmentSettings alignmentSettings) {

		BigDecimal[] chemicalShiftAxisPart = new BigDecimal[intervalIndices.getStart() - intervalIndices.getStop()];
		System.arraycopy(chemicalShiftAxis, intervalIndices.getStop(), chemicalShiftAxisPart, 0, intervalIndices.getStart() - intervalIndices.getStop());
		double calibrationPeak = (alignmentSettings.getSinglePeakHigherBorder() + alignmentSettings.getSinglePeakLowerBorder()) / 2;
		BigDecimal calibrationPeakPosition = new BigDecimal(calibrationPeak).setScale(3, RoundingMode.HALF_UP);
		return UtilityFunctions.findIndexOfValue(chemicalShiftAxisPart, calibrationPeakPosition);
	}

	public static Interval<Integer> getCalibrationIntervalIndices(BigDecimal[] chemicalShiftAxis, IcoShiftAlignmentSettings alignmentSettings) {

		double leftMargin = alignmentSettings.getSinglePeakHigherBorder();
		double rigthMargin = alignmentSettings.getSinglePeakLowerBorder();
		int leftIndex = UtilityFunctions.findIndexOfValue(chemicalShiftAxis, BigDecimal.valueOf(leftMargin));
		int rightIndex = UtilityFunctions.findIndexOfValue(chemicalShiftAxis, BigDecimal.valueOf(rigthMargin));
		return new Interval<Integer>(leftIndex, rightIndex);
	}
}

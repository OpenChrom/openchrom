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

import java.util.Collection;

import org.eclipse.chemclipse.nmr.model.core.IMeasurementNMR;
import org.eclipse.chemclipse.nmr.model.selection.DataNMRSelection;
import org.eclipse.chemclipse.nmr.model.selection.IDataNMRSelection;
import org.eclipse.chemclipse.nmr.model.support.ISignalExtractor;
import org.eclipse.chemclipse.nmr.model.support.SignalExtractor;
import org.ejml.simple.SimpleMatrix;

import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions;
import net.openchrom.nmr.processing.supplier.base.settings.IcoShiftAlignmentSettings;

public class ChemicalShiftCalibrationUtilities {

	public static boolean isSamePeakPosition(int[] actualPositions, int intendedPosition) {

		for(int p : actualPositions) {
			if(p != intendedPosition) {
				return false;
			}
		}
		return true;
	}

	public static double[] getChemicalShiftAxis(Collection<? extends IMeasurementNMR> experimentalDatasetsList) {

		IMeasurementNMR measurementNMR = experimentalDatasetsList.iterator().next();
		IDataNMRSelection dataNMRSelection = new DataNMRSelection(measurementNMR);
		ISignalExtractor signalExtractor = new SignalExtractor(dataNMRSelection);
		return signalExtractor.extractChemicalShift();
	}

	public static int[] getActualPeakPositions(int[] intervalIndices, SimpleMatrix calibratedData) {

		UtilityFunctions utilityFunction = new UtilityFunctions();
		int numRowsMax = calibratedData.numRows();
		int[] actualPositions = new int[numRowsMax];
		for(int r = 0; r < numRowsMax; r++) {
			double[] rowVector = calibratedData.extractVector(true, r).getMatrix().getData();
			double[] rowVectorPart = new double[intervalIndices[0] - intervalIndices[1]];
			//
			System.arraycopy(rowVector, intervalIndices[1], rowVectorPart, 0, intervalIndices[0] - intervalIndices[1]);
			double maxValue = utilityFunction.getMaxValueOfArray(rowVectorPart);
			actualPositions[r] = utilityFunction.findIndexOfValue(rowVectorPart, maxValue);
		}
		return actualPositions;
	}

	public static int getIntendedPeakPosition(int[] intervalIndices, double[] chemicalShiftAxis) {

		UtilityFunctions utilityFunction = new UtilityFunctions();
		double[] chemicalShiftAxisPart = new double[intervalIndices[0] - intervalIndices[1]];
		System.arraycopy(chemicalShiftAxis, intervalIndices[1], chemicalShiftAxisPart, 0, intervalIndices[0] - intervalIndices[1]);
		return utilityFunction.findIndexOfValue(chemicalShiftAxisPart, 0);
	}

	public static int[] getCalibrationIntervalIndices(double[] chemicalShiftAxis, IcoShiftAlignmentSettings calibrationSettings) {

		UtilityFunctions utilityFunction = new UtilityFunctions();
		int[] intervalIndices = new int[2];
		double leftMargin = calibrationSettings.getSinglePeakHigherBorder();
		double rigthMargin = calibrationSettings.getSinglePeakLowerBorder();
		intervalIndices[0] = utilityFunction.findIndexOfValue(chemicalShiftAxis, leftMargin); // leftIndex
		intervalIndices[1] = utilityFunction.findIndexOfValue(chemicalShiftAxis, rigthMargin); // rightIndex
		return intervalIndices;
	}
}

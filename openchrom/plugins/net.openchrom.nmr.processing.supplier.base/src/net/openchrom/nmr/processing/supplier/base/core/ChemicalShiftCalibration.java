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
package net.openchrom.nmr.processing.supplier.base.core;

import java.util.Collection;

import org.apache.commons.math3.complex.Complex;
import org.eclipse.chemclipse.nmr.model.core.IMeasurementNMR;
import org.eclipse.chemclipse.nmr.model.support.ISignalExtractor;
import org.eclipse.chemclipse.nmr.model.support.SignalExtractor;
import org.ejml.simple.SimpleMatrix;

import net.openchrom.nmr.processing.supplier.base.settings.IcoShiftAlignmentSettings;
import net.openchrom.nmr.processing.supplier.base.settings.support.ChemicalShiftCalibrationTargetCalculation;
import net.openchrom.nmr.processing.supplier.base.settings.support.ChemicalShiftCalibrationUtilities;
import net.openchrom.nmr.processing.supplier.base.settings.support.IcoShiftAlignmentGapFillingType;
import net.openchrom.nmr.processing.supplier.base.settings.support.IcoShiftAlignmentShiftCorrectionType;
import net.openchrom.nmr.processing.supplier.base.settings.support.IcoShiftAlignmentType;
import net.openchrom.nmr.processing.supplier.base.settings.support.IcoShiftAlignmentUtilities.Interval;

public class ChemicalShiftCalibration {

	/**
	 * The method calibrate will define the necessary settings and calculate
	 * a target for calibration of the dataset and calibrate the data.
	 * <p>
	 * Commonly used internal standards for calibrating chemical shift:
	 * <ul>
	 * <li>TMS (Tetramethylsilane)</li>
	 *
	 * <li>DSS (4,4-dimethyl-4-silapentane-1-sulfonic acid)</li>
	 *
	 * <li>TSP (3-(trimethylsilyl)propionic acid, sodium salt)</li>
	 * <ul>
	 * <li>The chemical shift of the (main) singlet of each standard is
	 * assigned as 0 ppm.</li>
	 * </ul>
	 * </ul>
	 *
	 * @author Alexander Stark
	 */
	public SimpleMatrix calibrate(Collection<? extends IMeasurementNMR> experimentalDatasetsList) {

		IcoShiftAlignmentSettings calibrationSettings = generateCalibrationSettings();
		IcoShiftAlignment icoShiftAlignment = new IcoShiftAlignment();
		// set calibration target in IcoShift algorithm
		icoShiftAlignment.setCalculateCalibrationTargetFunction(new ChemicalShiftCalibrationTargetCalculation());
		SimpleMatrix calibratedData = icoShiftAlignment.process(experimentalDatasetsList, calibrationSettings);
		//
		double[] chemicalShiftAxis = ChemicalShiftCalibrationUtilities.getChemicalShiftAxis(experimentalDatasetsList);
		Collection<? extends IMeasurementNMR> newDatasetsList = copyPartlyCalibratedData(experimentalDatasetsList, calibratedData);
		int checkIterator = 0;
		while(!checkCalibration(calibratedData, chemicalShiftAxis, calibrationSettings)) { // check for quality of calibration
			newDatasetsList = copyPartlyCalibratedData(newDatasetsList, calibratedData);
			// try to calibrate datasets again
			calibratedData = icoShiftAlignment.process(newDatasetsList, calibrationSettings);
			checkIterator++;
			if(checkIterator == 5) {
				break;
			}
		}
		//
		if(checkIterator > 2) {
			calibratedData = finalPeakCalibration(calibratedData, chemicalShiftAxis, calibrationSettings);
		}
		//
		return calibratedData;
	}

	private static boolean checkCalibration(SimpleMatrix calibratedData, double[] chemicalShiftAxis, IcoShiftAlignmentSettings calibrationSettings) {

		Interval<Integer> intervalIndices = ChemicalShiftCalibrationUtilities.getCalibrationIntervalIndices(chemicalShiftAxis, calibrationSettings);
		int intendedPosition = ChemicalShiftCalibrationUtilities.getIntendedPeakPosition(intervalIndices, chemicalShiftAxis);
		int[] actualPositions = ChemicalShiftCalibrationUtilities.getActualPeakPositions(intervalIndices, calibratedData);
		return ChemicalShiftCalibrationUtilities.isSamePeakPosition(actualPositions, intendedPosition);
	}

	private static Collection<? extends IMeasurementNMR> copyPartlyCalibratedData(Collection<? extends IMeasurementNMR> datasetsList, SimpleMatrix calibratedData) {

		double[] chemicalShiftAxis = ChemicalShiftCalibrationUtilities.getChemicalShiftAxis(datasetsList);
		//
		int r = 0;
		for(IMeasurementNMR measurementNMR : datasetsList) {
			double[] rowVector = calibratedData.extractVector(true, r).getMatrix().getData();
			r++;
			Complex[] complexVector = new Complex[rowVector.length];
			for(int c = 0; c < complexVector.length; c++) {
				complexVector[c] = new Complex(rowVector[c], 0);
			}
			//
			ISignalExtractor signalExtractor = new SignalExtractor(measurementNMR);
			signalExtractor.storeFrequencyDomainSpectrum(complexVector, chemicalShiftAxis);
		}
		return datasetsList;
	}

	private static SimpleMatrix finalPeakCalibration(SimpleMatrix calibratedData, double[] chemicalShiftAxis, IcoShiftAlignmentSettings calibrationSettings) {

		Interval<Integer> intervalIndices = ChemicalShiftCalibrationUtilities.getCalibrationIntervalIndices(chemicalShiftAxis, calibrationSettings);
		int intendedPosition = ChemicalShiftCalibrationUtilities.getIntendedPeakPosition(intervalIndices, chemicalShiftAxis);
		int[] actualPositions = ChemicalShiftCalibrationUtilities.getActualPeakPositions(intervalIndices, calibratedData);
		//
		UtilityFunctions utilityFunction = new UtilityFunctions();
		// try to correct the remaining discrepancy
		for(int i = 0; i < actualPositions.length; i++) {
			double[] shiftVector = calibratedData.extractVector(true, i).getMatrix().getData();
			//
			if(actualPositions[i] > intendedPosition) {
				// leftShift
				utilityFunction.leftShiftNMRData(shiftVector, (actualPositions[i] - intendedPosition));
				calibratedData.setRow(i, 0, shiftVector);
			} else {
				// rightShift
				utilityFunction.rightShiftNMRData(shiftVector, (intendedPosition - actualPositions[i]));
				calibratedData.setRow(i, 0, shiftVector);
			}
		}
		return calibratedData;
	}

	/**
	 * This method will generate a separate instance of alignment settings
	 * used for a calibration of datasets.
	 * <p>
	 * <p>
	 * All settings are fixed for the calculation of the target. The peak of
	 * a commonly used internal standard for calibration (assigned as 0 ppm)
	 * is predefined.
	 * <p>
	 * If needed the range for a user defined peak can be defined by setting:
	 * <br>
	 * {@link calibrationSettings.setSinglePeakLowerBorder()}<br>
	 * and <br>
	 * {@link calibrationSettings.setSinglePeakHigherBorder()}.
	 * <p>
	 * The selected region should not be too wide to ensure that only the peak
	 * of interest is used for the calibration.
	 *
	 * @author Alexander Stark
	 *
	 */
	private static IcoShiftAlignmentSettings generateCalibrationSettings() {

		IcoShiftAlignmentSettings calibrationSettings = new IcoShiftAlignmentSettings();
		//
		calibrationSettings.setShiftCorrectionType(IcoShiftAlignmentShiftCorrectionType.BEST);
		//
		calibrationSettings.setAlignmentType(IcoShiftAlignmentType.SINGLE_PEAK);
		calibrationSettings.setSinglePeakLowerBorder(-0.05);
		calibrationSettings.setSinglePeakHigherBorder(0.05);
		//
		calibrationSettings.setGapFillingType(IcoShiftAlignmentGapFillingType.MARGIN);
		return calibrationSettings;
	}
}

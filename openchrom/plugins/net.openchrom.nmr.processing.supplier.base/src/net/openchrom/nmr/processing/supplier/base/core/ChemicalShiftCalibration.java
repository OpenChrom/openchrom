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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.math3.complex.Complex;
import org.eclipse.chemclipse.model.core.IMeasurement;
import org.eclipse.chemclipse.model.filter.IMeasurementFilter;
import org.eclipse.chemclipse.nmr.model.core.FilteredSpectrumMeasurement;
import org.eclipse.chemclipse.nmr.model.core.SpectrumMeasurement;
import org.eclipse.chemclipse.processing.DataCategory;
import org.eclipse.chemclipse.processing.core.MessageConsumer;
import org.eclipse.chemclipse.processing.filter.Filter;
import org.eclipse.chemclipse.processing.filter.FilterContext;
import org.eclipse.core.runtime.IProgressMonitor;
import org.ejml.simple.SimpleMatrix;
import org.osgi.service.component.annotations.Component;

import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions.SpectrumData;
import net.openchrom.nmr.processing.supplier.base.settings.ChemicalShiftCalibrationSettings;
import net.openchrom.nmr.processing.supplier.base.settings.IcoShiftAlignmentSettings;
import net.openchrom.nmr.processing.supplier.base.settings.support.ChemicalShiftCalibrationTargetCalculation;
import net.openchrom.nmr.processing.supplier.base.settings.support.ChemicalShiftCalibrationUtilities;
import net.openchrom.nmr.processing.supplier.base.settings.support.IcoShiftAlignmentGapFillingType;
import net.openchrom.nmr.processing.supplier.base.settings.support.IcoShiftAlignmentShiftCorrectionType;
import net.openchrom.nmr.processing.supplier.base.settings.support.IcoShiftAlignmentType;
import net.openchrom.nmr.processing.supplier.base.settings.support.IcoShiftAlignmentUtilities;
import net.openchrom.nmr.processing.supplier.base.settings.support.IcoShiftAlignmentUtilities.Interval;

@Component(service = { Filter.class, IMeasurementFilter.class })
public class ChemicalShiftCalibration implements IMeasurementFilter<ChemicalShiftCalibrationSettings> {

	@Override
	public String getName() {

		return "Chemical Shift Calibration";
	}

	@Override
	public Class<ChemicalShiftCalibrationSettings> getConfigClass() {

		return ChemicalShiftCalibrationSettings.class;
	}

	@Override
	public DataCategory[] getDataCategories() {

		return new DataCategory[] { DataCategory.NMR };
	}

	@Override
	public <ResultType> ResultType filterIMeasurements(Collection<? extends IMeasurement> filterItems, ChemicalShiftCalibrationSettings configuration, Function<? super Collection<? extends IMeasurement>, ResultType> chain, MessageConsumer messageConsumer, IProgressMonitor monitor) throws IllegalArgumentException {

		if(configuration == null) {
			configuration = createNewConfiguration();
		}
		Collection<SpectrumMeasurement> collection = new ArrayList<>();
		for(IMeasurement measurement : filterItems) {
			if(measurement instanceof SpectrumMeasurement) {
				collection.add((SpectrumMeasurement) measurement);
			} else {
				throw new IllegalArgumentException();
			}
		}
		SimpleMatrix calibrationResult = calibrate(collection, configuration);
		List<IMeasurement> results = IcoShiftAlignmentUtilities.processResultsForFilter(collection, calibrationResult, this.getName());
		return chain.apply(results);
	}

	@Override
	public boolean acceptsIMeasurements(Collection<? extends IMeasurement> items) {

		for(IMeasurement measurement : items) {
			if(!(measurement instanceof SpectrumMeasurement)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * The method calibrate will define the necessary settings and calculate a
	 * target for calibration of the dataset and calibrate the data.
	 * <p>
	 * Commonly used internal standards for calibrating chemical shift:
	 * <ul>
	 * <li>TMS (Tetramethylsilane)</li>
	 *
	 * <li>DSS (4,4-dimethyl-4-silapentane-1-sulfonic acid)</li>
	 *
	 * <li>TSP (3-(trimethylsilyl)propionic acid, sodium salt)</li>
	 * <ul>
	 * <li>The chemical shift of the (main) singlet of each standard is assigned as
	 * 0 ppm.</li>
	 * </ul>
	 * </ul>
	 *
	 * @author Alexander Stark
	 */
	public SimpleMatrix calibrate(Collection<? extends SpectrumMeasurement> experimentalDatasetsList, ChemicalShiftCalibrationSettings calibrationSettings) {

		IcoShiftAlignmentSettings alignmentSettings = generateAlignmentSettings(calibrationSettings);
		IcoShiftAlignment icoShiftAlignment = new IcoShiftAlignment();
		if(!checkSettingsForPeakPosition(alignmentSettings, calibrationSettings)) {
			throw new IllegalArgumentException("Peak Position in calibration settings and alignment settings does not match.");
		}
		// set calibration target in IcoShift algorithm
		icoShiftAlignment.setCalculateCalibrationTargetFunction(new ChemicalShiftCalibrationTargetCalculation());
		icoShiftAlignment.setCalibrationSettings(calibrationSettings);
		SimpleMatrix calibratedData = icoShiftAlignment.process(experimentalDatasetsList, alignmentSettings, null);
		//
		BigDecimal[] chemicalShiftAxis = ChemicalShiftCalibrationUtilities.getChemicalShiftAxis(experimentalDatasetsList);
		Collection<? extends SpectrumMeasurement> newDatasetsList = copyPartlyCalibratedData(experimentalDatasetsList, calibratedData);
		int checkIterator = 0;
		while (!checkCalibration(calibratedData, chemicalShiftAxis, alignmentSettings)) { // check for quality of calibration
			newDatasetsList = copyPartlyCalibratedData(newDatasetsList, calibratedData);
			// try to calibrate datasets again
			calibratedData = icoShiftAlignment.process(newDatasetsList, alignmentSettings, null);
			checkIterator++;
			if(checkIterator == calibrationSettings.getNumberOfQualitiyControlCycles()) {
				break;
			}
		}
		//
		if(checkIterator >= calibrationSettings.getNumberOfQualitiyControlCycles()) {
			calibratedData = finalPeakCalibration(calibratedData, chemicalShiftAxis, alignmentSettings);
		}
		//
		return calibratedData;
	}

	private static boolean checkSettingsForPeakPosition(IcoShiftAlignmentSettings alignmentSettings, ChemicalShiftCalibrationSettings calibrationSettings) {

		double highBorder = alignmentSettings.getSinglePeakHigherBorder();
		double lowBorder = alignmentSettings.getSinglePeakLowerBorder();
		double alignmentPeakPosition = (highBorder + lowBorder) / 2;
		double calibrationPeakPosition = calibrationSettings.getLocationOfCauchyDistribution();
		if(Double.compare(alignmentPeakPosition, calibrationPeakPosition) == 0) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean checkCalibration(SimpleMatrix calibratedData, BigDecimal[] chemicalShiftAxis, IcoShiftAlignmentSettings alignmentSettings) {

		Interval<Integer> intervalIndices = ChemicalShiftCalibrationUtilities.getCalibrationIntervalIndices(chemicalShiftAxis, alignmentSettings);
		int intendedPosition = ChemicalShiftCalibrationUtilities.getIntendedPeakPosition(intervalIndices, chemicalShiftAxis);
		int[] actualPositions = ChemicalShiftCalibrationUtilities.getActualPeakPositions(intervalIndices, calibratedData);
		return ChemicalShiftCalibrationUtilities.isSamePeakPosition(actualPositions, intendedPosition);
	}

	private static Collection<? extends SpectrumMeasurement> copyPartlyCalibratedData(Collection<? extends SpectrumMeasurement> experimentalDatasetsList, SimpleMatrix calibratedData) {

		Collection<SpectrumMeasurement> result = new ArrayList<>();
		int r = 0;
		for(SpectrumMeasurement measurementNMR : experimentalDatasetsList) {
			SpectrumData complexSpectrumData = UtilityFunctions.toComplexSpectrumData(measurementNMR);
			double[] rowVector = calibratedData.extractVector(true, r).getMatrix().getData();
			r++;
			List<ComplexSpectrumSignal> newSignals = new ArrayList<>();
			for(int c = 0; c < rowVector.length; c++) {
				newSignals.add(new ComplexSpectrumSignal(complexSpectrumData.frequency[c], new Complex(rowVector[c], 0)));
			}
			FilteredSpectrumMeasurement<Void> filtered = new FilteredSpectrumMeasurement<>(FilterContext.create(measurementNMR, null, null));
			filtered.setSignals(newSignals);
			result.add(filtered);
		}
		return result;
	}

	private static SimpleMatrix finalPeakCalibration(SimpleMatrix calibratedData, BigDecimal[] chemicalShiftAxis, IcoShiftAlignmentSettings alignmentSettings) {

		Interval<Integer> intervalIndices = ChemicalShiftCalibrationUtilities.getCalibrationIntervalIndices(chemicalShiftAxis, alignmentSettings);
		int intendedPosition = ChemicalShiftCalibrationUtilities.getIntendedPeakPosition(intervalIndices, chemicalShiftAxis);
		int[] actualPositions = ChemicalShiftCalibrationUtilities.getActualPeakPositions(intervalIndices, calibratedData);
		// try to correct the remaining discrepancy
		for(int i = 0; i < actualPositions.length; i++) {
			double[] shiftVector = calibratedData.extractVector(true, i).getMatrix().getData();
			//
			if(actualPositions[i] > intendedPosition) {
				// leftShift
				UtilityFunctions.leftShiftNMRData(shiftVector, (actualPositions[i] - intendedPosition));
				calibratedData.setRow(i, 0, shiftVector);
			} else {
				// rightShift
				UtilityFunctions.rightShiftNMRData(shiftVector, (intendedPosition - actualPositions[i]));
				calibratedData.setRow(i, 0, shiftVector);
			}
		}
		return calibratedData;
	}

	/**
	 * This method will generate a separate instance of alignment settings used for
	 * a calibration of datasets.
	 * <p>
	 * <p>
	 * All settings are fixed for the calculation of the target. The peak of a
	 * commonly used internal standard for calibration (assigned as 0 ppm) is
	 * predefined.
	 * <p>
	 * If needed the range for a user defined peak can be defined by setting: <br>
	 * {@link calibrationSettings.setSinglePeakLowerBorder()}<br>
	 * and <br>
	 * {@link calibrationSettings.setSinglePeakHigherBorder()}.
	 * <p>
	 * The selected region should not be too wide to ensure that only the peak of
	 * interest is used for the calibration.
	 *
	 * @author Alexander Stark
	 *
	 */
	private static IcoShiftAlignmentSettings generateAlignmentSettings(ChemicalShiftCalibrationSettings calibrationSettings) {

		IcoShiftAlignmentSettings alignmentSettings = new IcoShiftAlignmentSettings();
		//
		alignmentSettings.setShiftCorrectionType(IcoShiftAlignmentShiftCorrectionType.BEST);
		//
		alignmentSettings.setAlignmentType(IcoShiftAlignmentType.SINGLE_PEAK);
		/*
		 * It can be assumed that deviations (if any) are covered by this range from
		 * 0.05 to -0.05 ppm. A larger range means that impurities or other peaks could
		 * be misused for calibration.
		 */
		alignmentSettings.setSinglePeakLowerBorder(calibrationSettings.getRangeAroundCalibrationSignal() / 2);
		alignmentSettings.setSinglePeakHigherBorder(-calibrationSettings.getRangeAroundCalibrationSignal() / 2);
		//
		alignmentSettings.setGapFillingType(IcoShiftAlignmentGapFillingType.MARGIN);
		return alignmentSettings;
	}
}

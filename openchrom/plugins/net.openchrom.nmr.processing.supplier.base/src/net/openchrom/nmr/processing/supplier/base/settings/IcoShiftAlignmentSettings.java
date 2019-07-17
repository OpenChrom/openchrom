/*******************************************************************************
 * Copyright (c) 2018, 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexander Stark - initial API and implementation
 * Jan Holy - refactoring
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.settings;

import java.util.List;

import net.openchrom.nmr.processing.supplier.base.settings.support.IcoShiftAlignmentGapFillingType;
import net.openchrom.nmr.processing.supplier.base.settings.support.IcoShiftAlignmentShiftCorrectionType;
import net.openchrom.nmr.processing.supplier.base.settings.support.IcoShiftAlignmentTargetCalculationSelection;
import net.openchrom.nmr.processing.supplier.base.settings.support.IcoShiftAlignmentType;
import net.openchrom.nmr.processing.supplier.base.settings.support.IcoShiftAlignmentUtilities.Interval;

/**
 * IcoShiftAlignmentSettings will define all necessary settings for the
 * alignment algorithm.
 *
 * @author Alexander Stark
 */
public class IcoShiftAlignmentSettings {

	private IcoShiftAlignmentTargetCalculationSelection targetCalculationSelection = IcoShiftAlignmentTargetCalculationSelection.MEAN;
	private IcoShiftAlignmentShiftCorrectionType shiftCorrectionType = IcoShiftAlignmentShiftCorrectionType.FAST;
	private int shiftCorrectionTypeValue = 55;
	private IcoShiftAlignmentGapFillingType gapFillingType = IcoShiftAlignmentGapFillingType.MARGIN;
	private IcoShiftAlignmentType alignmentType = IcoShiftAlignmentType.WHOLE_SPECTRUM;
	private double singlePeakLowerBorder = 2.21;
	private double singlePeakHigherBorder = 2.41;
	private int numberOfIntervals = 120;
	private int intervalLength = 1000;
	private List<Interval<Double>> userDefIntervalRegions;
	private boolean preliminaryCoShifting;

	/**
	 * getTargetCalculationSelection returns a calculated target vector that
	 * can be of MEAN, MEDIAN, or MAX value type.
	 *
	 * @param getTargetCalculationSelection
	 */
	public IcoShiftAlignmentTargetCalculationSelection getTargetCalculationSelection() {

		return targetCalculationSelection;
	}

	/**
	 * setTargetCalculationSelection sets the selection to MEAN, MEDIAN, or MAX.
	 * According to the selection the target is calculated.
	 *
	 * @param setTargetCalculationSelection
	 */
	public void setTargetCalculationSelection(IcoShiftAlignmentTargetCalculationSelection targetCalculationSelection) {

		this.targetCalculationSelection = targetCalculationSelection;
	}

	/**
	 * getShiftCorrectionType returns the shift correction type that is used to
	 * define the way the shift correction value is calculated. Possible ways
	 * are FAST, BEST, or USER_DEFINED.
	 *
	 * @param getShiftCorrectionType
	 */
	public IcoShiftAlignmentShiftCorrectionType getShiftCorrectionType() {

		return shiftCorrectionType;
	}

	/**
	 * setShiftCorrectionType defines the way the shift correction value is
	 * calculated. Possible selections are FAST, BEST, or USER_DEFINED.
	 *
	 * @param setShiftCorrectionType
	 */
	public void setShiftCorrectionType(IcoShiftAlignmentShiftCorrectionType shiftCorrectionType) {

		this.shiftCorrectionType = shiftCorrectionType;
	}

	/**
	 * getGapFillingType returns how the resulting gaps after the alignment are
	 * closed again. Possible ways are ZERO or MARGIN (values from each interval).
	 *
	 * @param getGapFillingType
	 */
	public IcoShiftAlignmentGapFillingType getGapFillingType() {

		return gapFillingType;
	}

	/**
	 * setGapFillingType defines how the resulting gaps after the alignment are
	 * closed again. Possible ways are ZERO or MARGIN (values from each interval).
	 *
	 * @param setGapFillingType
	 */
	public void setGapFillingType(IcoShiftAlignmentGapFillingType gapFillingType) {

		this.gapFillingType = gapFillingType;
	}

	/**
	 * getAlignmentType returns the method how the alignment is executed, i.e.
	 * it reflects the type and amount of intervals for the alignment.
	 * Possible methods are SINGLE_PEAK, WHOLE_SPECTRUM, NUMBER_OF_INTERVALS,
	 * INTERVAL_LENGTH, or USER_DEFINED_INTERVALS
	 *
	 * @param getAlignmentType
	 */
	public IcoShiftAlignmentType getAlignmentType() {

		return alignmentType;
	}

	/**
	 * setAlignmentType defines the method how the alignment is executed, i.e.
	 * it defines the type and amount of intervals for the alignment.
	 * Possible selections are SINGLE_PEAK, WHOLE_SPECTRUM, NUMBER_OF_INTERVALS,
	 * INTERVAL_LENGTH, or USER_DEFINED_INTERVALS
	 *
	 * @param setAlignmentType
	 */
	public void setAlignmentType(IcoShiftAlignmentType alignmentType) {

		this.alignmentType = alignmentType;
	}

	/**
	 * getSinglePeakLowerBorder returns the lower double value (ppm) for the
	 * selection IcoShiftAlignmentType.SINGLE_PEAK needed for calculation.
	 *
	 * @param getSinglePeakLowerBorder
	 */
	public double getSinglePeakLowerBorder() {

		return singlePeakLowerBorder;
	}

	/**
	 * setSinglePeakLowerBorder defines the lower double value (ppm) for the
	 * selection IcoShiftAlignmentType.SINGLE_PEAK. The value can be positive
	 * or negative and should be in the range of the chemical shift axis.
	 * The value should not be greater than {@link singlePeakHigherBorder}.
	 *
	 * @param setSinglePeakLowerBorder
	 */
	public void setSinglePeakLowerBorder(double singlePeakLowerBorder) {

		this.singlePeakLowerBorder = singlePeakLowerBorder;
	}

	/**
	 * getSinglePeakHigherBorder returns the higher double value (ppm) for the
	 * selection IcoShiftAlignmentType.SINGLE_PEAK needed for calculation.
	 *
	 * @param getSinglePeakHigherBorder
	 */
	public double getSinglePeakHigherBorder() {

		return singlePeakHigherBorder;
	}

	/**
	 * setSinglePeakHigherBorder defines the higher double value (ppm) for the
	 * selection IcoShiftAlignmentType.SINGLE_PEAK. The value can be positive
	 * or negative and should be in the range of the chemical shift axis.
	 * The value should be greater than {@link singlePeakLowerBorder}.
	 *
	 * @param setSinglePeakHigherBorder
	 */
	public void setSinglePeakHigherBorder(double singlePeakHigherBorder) {

		this.singlePeakHigherBorder = singlePeakHigherBorder;
	}

	/**
	 * getIntervalLength returns the length of an interval for the selection
	 * IcoShiftAlignmentType.INTERVAL_LENGTH needed for calculation.
	 *
	 * @param getIntervalLength
	 */
	public int getIntervalLength() {

		return intervalLength;
	}

	/**
	 * setIntervalLength defines the length of an interval for the selection
	 * IcoShiftAlignmentType.INTERVAL_LENGTH needed for calculation. Input is
	 * an integer value between 100 and 10000 data points.
	 *
	 * @param setIntervalLength
	 */
	public void setIntervalLength(int intervalLength) {

		this.intervalLength = intervalLength;
	}

	/**
	 * getUserDefIntervalRegions returns a map with user defined regions for
	 * interval calculation.
	 *
	 * @param getUserDefIntervalRegions
	 */
	public List<Interval<Double>> getUserDefIntervalRegions() {

		return userDefIntervalRegions;
	}

	/**
	 * setUserDefIntervalRegions defines a map with user defined regions for
	 * interval calculation.
	 * Each value pair is inserted as new ChemicalShiftInterval(hV, lV),
	 * where hV is the higher value and lV is the lower value of the pair.
	 *
	 * @param setUserDefIntervalRegions
	 */
	public void setUserDefIntervalRegions(List<Interval<Double>> userDefIntervalRegions) {

		this.userDefIntervalRegions = userDefIntervalRegions;
	}

	/**
	 * getNumberOfIntervals returns the number of intervals for the selection
	 * IcoShiftAlignmentType.NUMBER_OF_INTERVALS needed for calculation.
	 *
	 * @param getNumberOfIntervals
	 */
	public int getNumberOfIntervals() {

		return numberOfIntervals;
	}

	/**
	 * setNumberOfIntervals defines the number of intervals for the selection
	 * IcoShiftAlignmentType.NUMBER_OF_INTERVALS needed for calculation. Input
	 * is an integer greater than 1.
	 *
	 * @param setNumberOfIntervals
	 */
	public void setNumberOfIntervals(int numberOfIntrvals) {

		this.numberOfIntervals = numberOfIntrvals;
	}

	/**
	 * getShiftCorrectionTypeValue returns the user defined value needed for
	 * IcoShiftAlignmentShiftCorrectionType.USER_DEFINED
	 *
	 * @param getShiftCorrectionTypeValue
	 */
	public int getShiftCorrectionTypeValue() {

		return shiftCorrectionTypeValue;
	}

	/**
	 * setShiftCorrectionTypeValue defines the value needed for calculation of
	 * the shift correction value for IcoShiftAlignmentShiftCorrectionType.USER_DEFINED
	 *
	 * @param setShiftCorrectionTypeValue
	 */
	public void setShiftCorrectionTypeValue(int shiftCorrectionTypeValue) {

		this.shiftCorrectionTypeValue = shiftCorrectionTypeValue;
	}

	/**
	 * isPreliminaryCoShifting returns if a preliminary Co-Shifting will be
	 * executed before the main alignment will be carried out.
	 *
	 * @param isPreliminaryCoShifting
	 */
	public boolean isPreliminaryCoShifting() {

		return preliminaryCoShifting;
	}

	/**
	 * setPreliminaryCoShifting defines if a preliminary Co-Shifting will be
	 * executed before the main alignment will be carried out.
	 *
	 * @param setPreliminaryCoShifting
	 */
	public void setPreliminaryCoShifting(boolean preliminaryCoShifting) {

		this.preliminaryCoShifting = preliminaryCoShifting;
	}
}

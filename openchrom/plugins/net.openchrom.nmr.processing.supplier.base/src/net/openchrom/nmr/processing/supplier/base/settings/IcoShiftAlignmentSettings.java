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

import org.eclipse.chemclipse.support.settings.StringSettingsProperty;
import org.eclipse.chemclipse.support.settings.SystemSettings;
import org.eclipse.chemclipse.support.settings.SystemSettingsStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.openchrom.nmr.processing.supplier.base.settings.support.IcoShiftAlignmentGapFillingType;
import net.openchrom.nmr.processing.supplier.base.settings.support.IcoShiftAlignmentShiftCorrectionType;
import net.openchrom.nmr.processing.supplier.base.settings.support.IcoShiftAlignmentTargetCalculationSelection;
import net.openchrom.nmr.processing.supplier.base.settings.support.IcoShiftAlignmentType;
import net.openchrom.nmr.processing.supplier.base.settings.support.IcoShiftAlignmentUtilities;
import net.openchrom.nmr.processing.supplier.base.settings.support.IcoShiftAlignmentUtilities.Interval;

/**
 * IcoShiftAlignmentSettings will define all necessary settings for the
 * alignment algorithm.
 *
 * @author Alexander Stark
 */
@SystemSettings(SystemSettingsStrategy.NEW_INSTANCE)
public class IcoShiftAlignmentSettings {
	@JsonProperty("Perform preliminary CoShifting")
	private boolean preliminaryCoShifting;
	@JsonProperty("Calculated target function")
	private IcoShiftAlignmentTargetCalculationSelection targetCalculationSelection = IcoShiftAlignmentTargetCalculationSelection.MEAN;
	@JsonProperty("Shift correction type")
	private IcoShiftAlignmentShiftCorrectionType shiftCorrectionType = IcoShiftAlignmentShiftCorrectionType.FAST;
	@JsonProperty("User defined shift correction type value")
	private int shiftCorrectionTypeValue = 55;
	@JsonProperty("Gap filling type")
	private IcoShiftAlignmentGapFillingType gapFillingType = IcoShiftAlignmentGapFillingType.MARGIN;
	@JsonProperty("Alignment type")
	private IcoShiftAlignmentType alignmentType = IcoShiftAlignmentType.WHOLE_SPECTRUM;
	@JsonProperty("Single peak border (rigth)")
	private double singlePeakLowerBorder = 2.21;
	@JsonProperty("Single peak border (left)")
	private double singlePeakHigherBorder = 2.41;
	@JsonProperty("No. of intervals")
	private int numberOfIntervals = 120;
	@JsonProperty("Length of intervals")
	private int intervalLength = 1000;
	@StringSettingsProperty(regExp = "(\\d*\\.\\d+)-(\\d*\\.\\d+)", isMultiLine = true)
	@JsonProperty("User defined regions [ppm] \ne.g.\n5.1-5.25\n2.985-3.5 ")
	private String userDefinedIntervalRegions = "5.2-5.5\n4.1-4.4\n2.25-2.4\n1.9-2.1\n1.2-1.4";

	/**
	 * getTargetCalculationSelection returns a calculated target vector that can be
	 * of MEAN, MEDIAN, or MAX value type.
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
	 * define the way the shift correction value is calculated. Possible ways are
	 * FAST, BEST, or USER_DEFINED.
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
	 * getAlignmentType returns the method how the alignment is executed, i.e. it
	 * reflects the type and amount of intervals for the alignment. Possible methods
	 * are SINGLE_PEAK, WHOLE_SPECTRUM, NUMBER_OF_INTERVALS, INTERVAL_LENGTH, or
	 * USER_DEFINED_INTERVALS
	 *
	 * @param getAlignmentType
	 */
	public IcoShiftAlignmentType getAlignmentType() {

		return alignmentType;
	}

	/**
	 * setAlignmentType defines the method how the alignment is executed, i.e. it
	 * defines the type and amount of intervals for the alignment. Possible
	 * selections are SINGLE_PEAK, WHOLE_SPECTRUM, NUMBER_OF_INTERVALS,
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
	 * selection IcoShiftAlignmentType.SINGLE_PEAK. The value can be positive or
	 * negative and should be in the range of the chemical shift axis. The value
	 * should not be greater than {@link singlePeakHigherBorder}.
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
	 * selection IcoShiftAlignmentType.SINGLE_PEAK. The value can be positive or
	 * negative and should be in the range of the chemical shift axis. The value
	 * should be greater than {@link singlePeakLowerBorder}.
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
	 * IcoShiftAlignmentType.INTERVAL_LENGTH needed for calculation. Input is an
	 * integer value between 100 and 10000 data points.
	 *
	 * @param setIntervalLength
	 */
	public void setIntervalLength(int intervalLength) {

		this.intervalLength = intervalLength;
	}

	/**
	 * getUserDefinedIntervalRegions returns a String with user defined regions for
	 * interval calculation.
	 *
	 * @param getUserDefinedIntervalRegions
	 */

	public String getUserDefinedIntervalRegions() {

		return userDefinedIntervalRegions;
	}

	/**
	 * setUserDefinedIntervalRegions defines a String with user defined regions for
	 * interval calculation. Each interval is inserted as new value pair hV-lV,
	 * where hV is the higher value and lV is the lower value of the pair.
	 *
	 * @param setUserDefinedIntervalRegions
	 */

	public void setUserDefinedIntervalRegions(String userDefinedIntervalRegions) {

		this.userDefinedIntervalRegions = userDefinedIntervalRegions;
	}

	/**
	 * getUserDefinedIntervalRegionsAsList is used to parse the input String
	 * {@link userDefinedIntervalRegions} which holds the interval values. It
	 * returns a List that contains multiple ChemicalShiftIntervals.<br>
	 * Each value pair per new line is inserted as new ChemicalShiftInterval(hV,
	 * lV), where hV is the higher value and lV is the lower value of the pair.
	 *
	 * @param getUserDefinedIntervalRegionsAsList
	 */
	@JsonIgnore
	public List<Interval<Double>> getUserDefinedIntervalRegionsAsList() {

		return IcoShiftAlignmentUtilities.parseUserDefinedIntervalRegionsToList(userDefinedIntervalRegions);
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
	 * IcoShiftAlignmentType.NUMBER_OF_INTERVALS needed for calculation. Input is an
	 * integer greater than 1.
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
	 * setShiftCorrectionTypeValue defines the value needed for calculation of the
	 * shift correction value for IcoShiftAlignmentShiftCorrectionType.USER_DEFINED
	 *
	 * @param setShiftCorrectionTypeValue
	 */
	public void setShiftCorrectionTypeValue(int shiftCorrectionTypeValue) {

		this.shiftCorrectionTypeValue = shiftCorrectionTypeValue;
	}

	/**
	 * isPreliminaryCoShifting returns if a preliminary Co-Shifting will be executed
	 * before the main alignment will be carried out.
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

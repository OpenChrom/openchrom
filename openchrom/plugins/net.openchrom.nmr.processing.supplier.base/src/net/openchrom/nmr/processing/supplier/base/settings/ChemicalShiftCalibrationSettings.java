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
package net.openchrom.nmr.processing.supplier.base.settings;

import org.eclipse.chemclipse.support.settings.SystemSettings;
import org.eclipse.chemclipse.support.settings.SystemSettingsStrategy;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ChemicalShiftCalibrationSettings will define all necessary settings for the
 * calculation of a lorentzian shaped line/peak which in turn will be used for
 * calibration of data using the icoshift alignment algorithm.
 * <p>
 * By default, the data is calibrated to an internal standard at 0 ppm.
 *
 * @author Alexander Stark
 *
 */
@SystemSettings(SystemSettingsStrategy.NEW_INSTANCE)
public class ChemicalShiftCalibrationSettings {

	@JsonProperty("Location of calibration peak [ppm]")
	private double locationOfCauchyDistribution = 0;
	@JsonProperty("Scale of calculated peak")
	private double scaleOfCauchyDistribution = 0.01;
	@JsonProperty("Total width of calculated peak [ppm]")
	private double rangeOfCauchyDistribution = 2;
	@JsonProperty("Observed range around the calibration peak [ppm]")
	private double rangeAroundCalibrationSignal = 0.1;
	@JsonProperty("No. of qualitiy control cycles")
	private int numberOfQualitiyControlCycles = 1;

	/**
	 * getLocationOfCauchyDistribution returns the location parameter of the used
	 * Cauchy Distribution. The location refers to the peak maximum of the internal
	 * standard and corresponds to the chemical shift value in ppm.
	 *
	 * @param getLocationOfCauchyDistribution
	 */
	public double getLocationOfCauchyDistribution() {

		return locationOfCauchyDistribution;
	}

	/**
	 * setLocationOfCauchyDistribution sets the location parameter of the used
	 * Cauchy Distribution.
	 * <p>
	 * By default, the value is set to 0.
	 *
	 * @param setLocationOfCauchyDistribution
	 */
	public void setLocationOfCauchyDistribution(double locationOfCauchyDistribution) {

		this.locationOfCauchyDistribution = locationOfCauchyDistribution;
	}

	/**
	 * getScaleOfCauchyDistribution returns the scale parameter of the used Cauchy
	 * Distribution. The parameter scales the intensity of the distribution to suit
	 * the intensity of observed peak of the internal standard.
	 *
	 * @param getScaleOfCauchyDistribution
	 */
	public double getScaleOfCauchyDistribution() {

		return scaleOfCauchyDistribution;
	}

	/**
	 * setScaleOfCauchyDistribution sets the scale parameter of the used Cauchy
	 * Distribution.<br>
	 * Only values greater than zero are allowed.
	 * <p>
	 * By default, the value is set to 0.01.
	 *
	 * @param setScaleOfCauchyDistribution
	 */
	public void setScaleOfCauchyDistribution(double scaleOfCauchyDistribution) {

		this.scaleOfCauchyDistribution = scaleOfCauchyDistribution;
	}

	/**
	 * getRangeOfCauchyDistribution returns the range parameter of the used Cauchy
	 * Distribution. This parameter describes the width of the considered section
	 * around the observed peak of the internal standard and is used to define the
	 * peak width.
	 *
	 * @param getRangeOfCauchyDistribution
	 */
	public double getRangeOfCauchyDistribution() {

		return rangeOfCauchyDistribution;
	}

	/**
	 * setRangeOfCauchyDistribution sets the range parameter of the used Cauchy
	 * Distribution.
	 * <p>
	 * By default, the value is set to 2.
	 *
	 * @param setRangeOfCauchyDistribution
	 */
	public void setRangeOfCauchyDistribution(double rangeOfCauchyDistribution) {

		this.rangeOfCauchyDistribution = rangeOfCauchyDistribution;
	}

	/**
	 * getRangeAroundCalibrationSignal returns the range parameter used for the
	 * selection of the calibration signal. This parameter describes the width of
	 * the considered section around the observed peak of the internal standard.
	 *
	 * @param getRangeAroundCalibrationSignal
	 */
	public double getRangeAroundCalibrationSignal() {

		return rangeAroundCalibrationSignal;
	}

	/**
	 * setRangeAroundCalibrationSignal sets the range parameter used for the
	 * selection of the calibration signal.
	 * <p>
	 * By default, the value is set to 0.1.
	 * <p>
	 * It can be assumed that deviations (if any) are covered by this range from
	 * 0.05 to -0.05 ppm. A larger range means that impurities or other peaks could
	 * be misused for calibration.
	 *
	 * @param setRangeAroundCalibrationSignal
	 */
	public void setRangeAroundCalibrationSignal(double rangeAroundCalibrationSignal) {

		this.rangeAroundCalibrationSignal = rangeAroundCalibrationSignal;
	}

	public int getNumberOfQualitiyControlCycles() {
		return numberOfQualitiyControlCycles;
	}

	public void setNumberOfQualitiyControlCycles(int numberOfQualitiyControlCycles) {
		this.numberOfQualitiyControlCycles = numberOfQualitiyControlCycles;
	}
}

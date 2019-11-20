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
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.settings;

import org.eclipse.chemclipse.support.settings.SystemSettings;
import org.eclipse.chemclipse.support.settings.SystemSettingsStrategy;

import com.fasterxml.jackson.annotation.JsonProperty;

@SystemSettings(SystemSettingsStrategy.NEW_INSTANCE)
public class AutoPhaseCorrectionSettings {

	static final double DEFAULT_PENALTY_FACTORY = 1E-9 / 5;
	static final int DEFAULT_NUMBER_OF_OPTIMIZATION_CYCLES = 1;
	static final int DEFAULT_OMIT_PERCENT_OF_SPECTRUM = 15;
	// user might provide better values
	@JsonProperty("Weighting penalty factor")
	private double penaltyFactor = DEFAULT_PENALTY_FACTORY;
	@JsonProperty("No. of optimization cycles")
	private int numberOfOptimizationCycles = DEFAULT_NUMBER_OF_OPTIMIZATION_CYCLES;
	@JsonProperty("Omit edge portions of spectrum [%]")
	private int omitPercentOfTheSpectrum = DEFAULT_OMIT_PERCENT_OF_SPECTRUM;
	//
	@JsonProperty("Apply only 0th order correction")
	private boolean correctOnlyZerothPhase;
	@JsonProperty("0th order correction [°]")
	private double zerothOrderValue = 0.0d;
	@JsonProperty("1st order correction [°]")
	private double firstOrderValue = 0.0d;

	public double getZerothOrderValue() {
		return zerothOrderValue;
	}

	public void setZerothOrderValue(double zerothOrderValue) {
		this.zerothOrderValue = zerothOrderValue;
	}

	public double getFirstOrderValue() {
		return firstOrderValue;
	}

	public void setFirstOrderValue(double firstOrderValue) {
		this.firstOrderValue = firstOrderValue;
	}

	public double getPenaltyFactor() {

		return penaltyFactor;
	}

	public void setPenaltyFactor(double penaltyFactor) {

		this.penaltyFactor = penaltyFactor;
	}

	public boolean isCorrectOnlyZerothPhase() {

		return correctOnlyZerothPhase;
	}

	public void setCorrectOnlyZerothPhase(boolean correctOnlyZerothPhase) {

		this.correctOnlyZerothPhase = correctOnlyZerothPhase;
	}

	public int getNumberOfOptimizationCycles() {

		return numberOfOptimizationCycles;
	}

	public void setNumberOfOptimizationCycles(int numberOfOptimizationCycles) {

		this.numberOfOptimizationCycles = numberOfOptimizationCycles;
	}

	public int getOmitPercentOfTheSpectrum() {

		return omitPercentOfTheSpectrum;
	}

	public void setOmitPercentOfTheSpectrum(int omitPercentOfTheSpectrum) {

		this.omitPercentOfTheSpectrum = omitPercentOfTheSpectrum;
	}
}

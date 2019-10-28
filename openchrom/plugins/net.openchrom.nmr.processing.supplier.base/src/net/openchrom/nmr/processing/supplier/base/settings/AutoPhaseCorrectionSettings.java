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

import net.openchrom.nmr.processing.phasecorrection.AutoPhaseCorrectionProcessor.PhaseCorrectionValue;

@SystemSettings(SystemSettingsStrategy.NEW_INSTANCE)
public class AutoPhaseCorrectionSettings {

	static final double DEFAULT_PENALTY_FACTORY = 1E-9 / 5;
	static final int DEFAULT_NUMBER_OF_OPTIMIZATION_CYCLES = 1;
	static final int DEFAULT_OMIT_PERCENT_OF_SPECTRUM = 15;
	// user might provide better values
	private double penaltyFactor = DEFAULT_PENALTY_FACTORY;
	private int numberOfOptimizationCycles = DEFAULT_NUMBER_OF_OPTIMIZATION_CYCLES;
	private int omitPercentOfTheSpectrum = DEFAULT_OMIT_PERCENT_OF_SPECTRUM;
	//
	private boolean correctOnlyZerothPhase;
	private PhaseCorrectionValue<Double> phaseCorrectionValues;

	public PhaseCorrectionValue<Double> getPhaseCorrectionValues() {

		return phaseCorrectionValues;
	}

	public void setPhaseCorrectionValues(PhaseCorrectionValue<Double> phaseCorrectionValues) {

		this.phaseCorrectionValues = phaseCorrectionValues;
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

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

import org.eclipse.chemclipse.nmr.processor.settings.IProcessorSettings;
import org.eclipse.chemclipse.support.settings.IntSettingsProperty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaselineCorrectionSettings implements IProcessorSettings {

	static final int DEFAULT_OMIT_PERCENT_OF_SPECTRUM = 5;
	static final int DEFUALT_NUMBER_OF_ITERATIONS = 1000;
	static final double DEFAULT_FACTOR_FOR_NEGLIGIBLE_BASELINE_CORRECTION = 0.125;
	static final int DEFAULT_FITTING_CONSTANT_U = 4;
	static final int DEFAULT_FITTING_CONSTANT_V = 2;
	//
	private int omitPercentOfTheSpectrum = DEFAULT_OMIT_PERCENT_OF_SPECTRUM;
	private int numberOfIterations = DEFUALT_NUMBER_OF_ITERATIONS;
	private int fittingConstantU = DEFAULT_FITTING_CONSTANT_U;
	private int fittingConstantV = DEFAULT_FITTING_CONSTANT_V;
	private double factorForNegligibleBaselineCorrection = DEFAULT_FACTOR_FOR_NEGLIGIBLE_BASELINE_CORRECTION;
	//
	@JsonProperty(value = "Polynomial Order", defaultValue = "2")
	@IntSettingsProperty(minValue = 1)
	private int polynomialOrder = 2;

	public BaselineCorrectionSettings() {

	}

	public int getPolynomialOrder() {

		return polynomialOrder;
	}

	public void setPolynomialOrder(int polynomialOrder) {

		this.polynomialOrder = polynomialOrder;
	}

	public int getOmitPercentOfTheSpectrum() {

		return omitPercentOfTheSpectrum;
	}

	public void setOmitPercentOfTheSpectrum(int omitPercentOfTheSpectrum) {

		this.omitPercentOfTheSpectrum = omitPercentOfTheSpectrum;
	}

	public int getNumberOfIterations() {

		return numberOfIterations;
	}

	public void setNumberOfIterations(int numberOfIterations) {

		this.numberOfIterations = numberOfIterations;
	}

	public double getFactorForNegligibleBaselineCorrection() {

		return factorForNegligibleBaselineCorrection;
	}

	public void setFactorForNegligibleBaselineCorrection(double factorForNegligibleBaselineCorrection) {

		this.factorForNegligibleBaselineCorrection = factorForNegligibleBaselineCorrection;
	}

	public int getFittingConstantU() {

		return fittingConstantU;
	}

	public void setFittingConstantU(int fittingConstantU) {

		this.fittingConstantU = fittingConstantU;
	}

	public int getFittingConstantV() {

		return fittingConstantV;
	}

	public void setFittingConstantV(int fittingConstantV) {

		this.fittingConstantV = fittingConstantV;
	}
}

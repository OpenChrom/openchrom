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
 * Christoph Läubrich - rework for new filter model
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.core;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.eclipse.chemclipse.filter.Filter;
import org.eclipse.chemclipse.model.core.FilteredMeasurement;
import org.eclipse.chemclipse.model.filter.IMeasurementFilter;
import org.eclipse.chemclipse.nmr.model.core.FilteredSpectrumMeasurement;
import org.eclipse.chemclipse.nmr.model.core.SpectrumMeasurement;
import org.eclipse.chemclipse.processing.core.MessageConsumer;
import org.eclipse.core.runtime.IProgressMonitor;
import org.osgi.service.component.annotations.Component;

import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions.SpectrumData;
import net.openchrom.nmr.processing.supplier.base.settings.BaselineCorrectionSettings;

@Component(service = {Filter.class, IMeasurementFilter.class})
public class BaselineCorrectionProcessor extends AbstractSpectrumSignalFilter<BaselineCorrectionSettings> {

	private static final String NAME = "Baseline Correction";

	public BaselineCorrectionProcessor() {

		super(BaselineCorrectionSettings.class);
	}

	@Override
	public String getFilterName() {

		return NAME;
	}

	@Override
	protected FilteredMeasurement<?> doFiltering(SpectrumMeasurement measurement, BaselineCorrectionSettings settings, MessageConsumer messageConsumer, IProgressMonitor monitor) {

		SpectrumData spectrumData = UtilityFunctions.toComplexSpectrumData(measurement.getSignals());
		FilteredSpectrumMeasurement filtered = new FilteredSpectrumMeasurement(measurement);
		perform(spectrumData, settings);
		filtered.setSignals(spectrumData.toSignal());
		return filtered;
	}

	private void perform(SpectrumData spectrumData, final BaselineCorrectionSettings settings) {

		int polynomialOrder = settings.getPolynomialOrder();
		/*
		 * Matlab:
		 * polyfit - Polynomial curve fitting
		 * p = polyfit(x,y,n)
		 * p = polyfit(x,y,n) returns the coefficients for a polynomial p(x) of degree n that
		 * is a best fit (in a least-squares sense) for the data in y. The coefficients in p
		 * are in descending powers, and the length of p is n+1
		 */
		// Class PolynomialCurveFitter
		// org.apache.commons.math4.fitting.PolynomialCurveFitter
		//
		/*
		 * Matlab:
		 * polyval - Polynomial evaluation
		 * y = polyval(p,x)
		 * y = polyval(p,x) returns the value of a polynomial of degree n evaluated at x.
		 * The input argument p is a vector of length n+1 whose elements are the coefficients
		 * in descending powers of the polynomial to be evaluated.
		 */
		// Class PolynomialFunction
		// org.apache.commons.math3.analysis.polynomials.PolynomialFunction
		//
		/*
		 * Literature: G. A. Pearson, Journal of Magnetic Resonance, 27, 265-272 (1977)
		 */
		// chemical shift axis used while fitting
		BigDecimal[] deltaAxisPPM = spectrumData.chemicalShift;
		// spectrum to be baseline corrected
		Complex[] phasedSignals = spectrumData.signals;
		//
		Complex[] nmrSpectrumFTProcessedPhasedBaseline = new Complex[phasedSignals.length];
		// change/select parameters for BC
		int fittingConstantU = 6; // 4 recommended from paper but probably from grotty data?
		int fittingConstantV = 6; // 2-3 recommended from paper
		double negligibleFactorMinimum = 0.125; // from paper
		double sigmaOne = Double.POSITIVE_INFINITY; // starting value: infinity
		double cutPercentage = 5.0; // ignore this % of spectrum each side
		//
		// preparation of real part of spectrum for fitting
		double[] nmrSpectrumForBaselineCorr = new double[phasedSignals.length]; // spec
		for(int i = 0; i < phasedSignals.length; i++) {
			nmrSpectrumForBaselineCorr[i] = phasedSignals[i].getReal();
		}
		//
		int cutPartOfSpectrum = (int)Math.round(phasedSignals.length * cutPercentage * 0.01);
		for(int i = 0; i <= cutPartOfSpectrum; i++) {
			nmrSpectrumForBaselineCorr[i] = 0;
		}
		//
		int forInitialization = phasedSignals.length - cutPartOfSpectrum + 1;
		for(int i = forInitialization; i < phasedSignals.length; i++) {
			nmrSpectrumForBaselineCorr[i] = 0;
		}
		//
		double[] nmrSpectrumBaselineCorrSquare = new double[phasedSignals.length]; // spec_sq
		double[] nmrSpectrumBaselineCorrAbsolute = new double[phasedSignals.length]; // spec_abs
		for(int i = 0; i < nmrSpectrumForBaselineCorr.length; i++) {
			nmrSpectrumBaselineCorrSquare[i] = Math.pow(nmrSpectrumForBaselineCorr[i], 2);
		}
		for(int i = 0; i < nmrSpectrumForBaselineCorr.length; i++) {
			nmrSpectrumBaselineCorrAbsolute[i] = Math.abs(nmrSpectrumForBaselineCorr[i]);
		}
		//
		// parts of the fitting routine
		int maximumIterations = 1000;
		int numberOfFourierPoints = spectrumData.signals.length;
		double[] heavisideFunctionality = new double[nmrSpectrumBaselineCorrAbsolute.length];
		for(int i = 0; i < heavisideFunctionality.length; i++) {
			heavisideFunctionality[i] = 0;
		}
		double[] baselineCorrectionReal = new double[numberOfFourierPoints];
		double[] baselineCorrectionImag = new double[numberOfFourierPoints];
		Complex[] baselineCorrection = new Complex[numberOfFourierPoints];
		//
		double[] fittingFunctionality = new double[nmrSpectrumBaselineCorrAbsolute.length]; // spec_v
		for(int i = 0; i < fittingFunctionality.length; i++) {
			fittingFunctionality[i] = 0;
		}
		//
		// iterative baseline correction
		boolean firstFit = true;
		for(int i = 1; i < maximumIterations; i++) {
			// System.out.println("Iteration " + i);
			// create heaviside functionality
			for(int k = 0; k < heavisideFunctionality.length; k++) {
				heavisideFunctionality[k] = (fittingConstantU * sigmaOne) - nmrSpectrumBaselineCorrAbsolute[k];
			}
			for(int m = 0; m < heavisideFunctionality.length; m++) {
				if(heavisideFunctionality[m] > 0) {
					heavisideFunctionality[m] = 1;
				}
				if(heavisideFunctionality[m] < 0) {
					heavisideFunctionality[m] = 0;
				}
			}
			// Tentative sigma
			double[] tempDividend = new double[heavisideFunctionality.length];
			for(int a = 0; a < heavisideFunctionality.length; a++) {
				tempDividend[a] = nmrSpectrumBaselineCorrSquare[a] * heavisideFunctionality[a];
			}
			double tempDividendSum = Arrays.stream(tempDividend).sum();
			double tempDivisor = 1 + Arrays.stream(heavisideFunctionality).sum();
			double sigmaNull = tempDividendSum / tempDivisor;
			sigmaNull = Math.sqrt(sigmaNull);
			// coefficients for PolynomialCurveFitter
			final double[] realCoeff;
			final double[] imagCoeff;
			//
			if(Math.abs(sigmaNull - sigmaOne) < 1E-18) {
				// fitting here
				for(int z = 0; z < baselineCorrection.length; z++) {
					baselineCorrection[z] = new Complex(0, 0);
				}
				// double[] fittingFunctionality = new double[nmrSpectrumBaselineCorrAbsolute.length]; //spec_v
				for(int k = 0; k < fittingFunctionality.length; k++) {
					fittingFunctionality[k] = (fittingConstantV * sigmaOne) - nmrSpectrumBaselineCorrAbsolute[k];
				}
				// the data has to be stored in ArrayList to be fitted; Complex[] not supported by Fitter etc. => split into real and imaginary parts
				ArrayList<WeightedObservedPoint> realFittingPoints = new ArrayList<WeightedObservedPoint>();
				ArrayList<WeightedObservedPoint> imagFittingPoints = new ArrayList<WeightedObservedPoint>();
				double fittingWeight = 1.0; // weight = 1.0 if none
				// add data to ArrayList
				for(int z = 0; z < phasedSignals.length; z++) {
					if(fittingFunctionality[z] > 0) {
						if(firstFit) {
							realFittingPoints.add(new WeightedObservedPoint(fittingWeight, deltaAxisPPM[z].doubleValue(), phasedSignals[z].getReal()));
							imagFittingPoints.add(new WeightedObservedPoint(fittingWeight, deltaAxisPPM[z].doubleValue(), phasedSignals[z].getImaginary()));
						} else {
							realFittingPoints.add(new WeightedObservedPoint(fittingWeight, deltaAxisPPM[z].doubleValue(), nmrSpectrumFTProcessedPhasedBaseline[z].getReal()));
							imagFittingPoints.add(new WeightedObservedPoint(fittingWeight, deltaAxisPPM[z].doubleValue(), nmrSpectrumFTProcessedPhasedBaseline[z].getImaginary()));
						}
					} // else?? = 0 ??
				}
				// TODO define polynomial order for fitting
				final PolynomialCurveFitter baselineFitter = PolynomialCurveFitter.create(polynomialOrder);
				realCoeff = baselineFitter.fit(realFittingPoints);
				imagCoeff = baselineFitter.fit(imagFittingPoints);
				// apply PolynomialFunction with calculated coefficients
				final PolynomialFunction polyFuncReal = new PolynomialFunction(realCoeff);
				final PolynomialFunction polyFuncImag = new PolynomialFunction(imagCoeff);
				for(int s = 0; s < deltaAxisPPM.length; s++) {
					baselineCorrectionReal[s] = polyFuncReal.value(deltaAxisPPM[s].doubleValue());
					baselineCorrectionImag[s] = polyFuncImag.value(deltaAxisPPM[s].doubleValue());
				}
				// apply baseline correction
				if(firstFit) {
					for(int w = 0; w < nmrSpectrumFTProcessedPhasedBaseline.length; w++) {
						baselineCorrection[w] = new Complex(baselineCorrectionReal[w], baselineCorrectionImag[w]);
						nmrSpectrumFTProcessedPhasedBaseline[w] = phasedSignals[w].subtract(baselineCorrection[w]);
					}
					firstFit = false;
				} else {
					for(int w = 0; w < nmrSpectrumFTProcessedPhasedBaseline.length; w++) {
						baselineCorrection[w] = new Complex(baselineCorrectionReal[w], baselineCorrectionImag[w]);
						nmrSpectrumFTProcessedPhasedBaseline[w] = nmrSpectrumFTProcessedPhasedBaseline[w].subtract(baselineCorrection[w]);
					}
				}
				//
				// preparation of real part of spectrum for further fitting
				for(int ia = 0; ia < nmrSpectrumFTProcessedPhasedBaseline.length; ia++) {
					nmrSpectrumForBaselineCorr[ia] = nmrSpectrumFTProcessedPhasedBaseline[ia].getReal();
				}
				for(int ib = 0; ib <= cutPartOfSpectrum; ib++) {
					nmrSpectrumForBaselineCorr[ib] = 0;
				}
				for(int ic = nmrSpectrumFTProcessedPhasedBaseline.length - cutPartOfSpectrum + 1; ic < nmrSpectrumFTProcessedPhasedBaseline.length; ic++) {
					nmrSpectrumForBaselineCorr[ic] = 0;
				}
				for(int id = 0; id < nmrSpectrumForBaselineCorr.length; id++) {
					nmrSpectrumBaselineCorrSquare[id] = Math.pow(nmrSpectrumForBaselineCorr[id], 2);
				}
				for(int ie = 0; ie < nmrSpectrumForBaselineCorr.length; ie++) {
					nmrSpectrumBaselineCorrAbsolute[ie] = Math.abs(nmrSpectrumForBaselineCorr[ie]);
				}
				//
				// check if baseline correction is good enough
				double baselineCorrectionBreakCheck = 0;
				for(int g = 0; g < baselineCorrection.length; g++) {
					baselineCorrectionBreakCheck = baselineCorrectionBreakCheck + baselineCorrection[g].abs();
				}
				double breakCondition = negligibleFactorMinimum * sigmaOne;
				// System.out.println("baselineCorrectionBreakCheck = " + baselineCorrectionBreakCheck + ", breakCondition = " + breakCondition + "; " + i);
				if(baselineCorrectionBreakCheck < breakCondition) {
					System.out.println("baseline correction iterations: " + i);
					break;
				}
			} else {
				sigmaOne = sigmaNull;
			}
			if(i == maximumIterations - 1) {
				System.out.println("maximum iterations reached.");
			}
		}
		for(int j = 0; j < spectrumData.signals.length; j++) {
			spectrumData.signals[j] = spectrumData.signals[j].subtract(baselineCorrection[j]);
		}
	}
}

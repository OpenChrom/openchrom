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

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.FilteredMeasurement;
import org.eclipse.chemclipse.model.filter.IMeasurementFilter;
import org.eclipse.chemclipse.nmr.model.core.FilteredSpectrumMeasurement;
import org.eclipse.chemclipse.nmr.model.core.SpectrumMeasurement;
import org.eclipse.chemclipse.processing.core.MessageConsumer;
import org.eclipse.chemclipse.processing.filter.Filter;
import org.eclipse.core.runtime.IProgressMonitor;
import org.osgi.service.component.annotations.Component;

import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions.SpectrumData;
import net.openchrom.nmr.processing.supplier.base.settings.BaselineCorrectionSettings;

@Component(service = {Filter.class, IMeasurementFilter.class})
public class BaselineCorrectionProcessor extends AbstractSpectrumSignalFilter<BaselineCorrectionSettings> {

	private static final long serialVersionUID = -3321578724822253648L;
	private static final String NAME = "Baseline Correction";
	private static final Logger baselineCorrectionLogger = Logger.getLogger(BaselineCorrectionProcessor.class);

	public BaselineCorrectionProcessor() {
		super(BaselineCorrectionSettings.class);
	}

	@Override
	public String getName() {

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
		Number[] deltaAxisPPM = spectrumData.chemicalShift;
		// spectrum to be baseline corrected
		Complex[] phasedSignals = spectrumData.signals;
		// change/select parameters for BC
		int fittingConstantU = settings.getFittingConstantU(); // 4 recommended from paper but probably from grotty data?
		int fittingConstantV = settings.getFittingConstantV(); // 2-3 recommended from paper
		double negligibleFactorMinimum = settings.getFactorForNegligibleBaselineCorrection();// 0.125 from paper
		double cutPercentage = settings.getOmitPercentOfTheSpectrum(); // ignore this % of spectrum each side
		double sigmaOne = Double.POSITIVE_INFINITY; // starting value: infinity
		//
		// preparation of real part of spectrum for fitting
		double[] nmrSpectrumForBaselineCorr = cutPartOfSpectrum(phasedSignals, cutPercentage);
		double[] nmrSpectrumBaselineCorrAbsolute = calculateAbsoluteSpectrum(nmrSpectrumForBaselineCorr); // spec_abs
		double[] nmrSpectrumBaselineCorrSquare = calculateSquaredSpectrum(nmrSpectrumForBaselineCorr); // spec_sq
		//
		// parts of the fitting routine
		int polynomialOrder = settings.getPolynomialOrder();
		int maximumIterations = settings.getNumberOfIterations();
		double[] heavisideFunctionality = new double[nmrSpectrumBaselineCorrAbsolute.length];
		double[] fittingFunctionality = new double[nmrSpectrumBaselineCorrAbsolute.length]; // spec_v
		Complex[] baselineCorrection = new Complex[nmrSpectrumBaselineCorrAbsolute.length];
		//
		// iterative baseline correction
		for(int i = 1; i < maximumIterations; i++) {
			// create heaviside functionality
			for(int k = 0; k < heavisideFunctionality.length; k++) {
				heavisideFunctionality[k] = (fittingConstantU * sigmaOne) - nmrSpectrumBaselineCorrAbsolute[k];
				if(heavisideFunctionality[k] > 0) {
					heavisideFunctionality[k] = 1;
				}
				if(heavisideFunctionality[k] < 0) {
					heavisideFunctionality[k] = 0;
				}
			}
			// Tentative sigma
			double sigmaNull = calculateTentativeSigma(heavisideFunctionality, nmrSpectrumBaselineCorrSquare);
			//
			if(Double.compare(sigmaNull, sigmaOne) == 0) {
				// fitting here
				for(int k = 0; k < fittingFunctionality.length; k++) {
					fittingFunctionality[k] = (fittingConstantV * sigmaOne) - nmrSpectrumBaselineCorrAbsolute[k];
				}
				// the data has to be stored in ArrayList to be fitted
				// Complex[] not supported by Fitter etc. => split into real and imaginary parts
				ArrayList<WeightedObservedPoint> realFittingPoints = new ArrayList<WeightedObservedPoint>();
				ArrayList<WeightedObservedPoint> imagFittingPoints = new ArrayList<WeightedObservedPoint>();
				double fittingWeight = 1.0; // weight = 1.0 if none
				// add data to ArrayList
				for(int z = 0; z < phasedSignals.length; z++) {
					if(fittingFunctionality[z] > 0) {
						realFittingPoints.add(new WeightedObservedPoint(fittingWeight, //
								deltaAxisPPM[z].doubleValue(), phasedSignals[z].getReal()));
						imagFittingPoints.add(new WeightedObservedPoint(fittingWeight, //
								deltaAxisPPM[z].doubleValue(), phasedSignals[z].getImaginary()));
					} // else?? = 0 ??
				}
				// define polynomial order for fitting
				final PolynomialCurveFitter baselineFitter = PolynomialCurveFitter.create(polynomialOrder);
				// coefficients for PolynomialCurveFitter
				final double[] realCoeff = baselineFitter.fit(realFittingPoints);
				final double[] imagCoeff = baselineFitter.fit(imagFittingPoints);
				// apply PolynomialFunction with calculated coefficients
				final PolynomialFunction polyFuncReal = new PolynomialFunction(realCoeff);
				final PolynomialFunction polyFuncImag = new PolynomialFunction(imagCoeff);
				baselineCorrection = calculateBaselineCorrection(deltaAxisPPM, polyFuncReal, polyFuncImag);
				// apply baseline correction
				for(int w = 0; w < phasedSignals.length; w++) {
					phasedSignals[w] = phasedSignals[w].subtract(baselineCorrection[w]);
				}
				// preparation of real part of spectrum for next iteration
				nmrSpectrumForBaselineCorr = cutPartOfSpectrum(phasedSignals, cutPercentage);
				nmrSpectrumBaselineCorrSquare = calculateSquaredSpectrum(nmrSpectrumForBaselineCorr);
				nmrSpectrumBaselineCorrAbsolute = calculateAbsoluteSpectrum(nmrSpectrumForBaselineCorr);
				//
				// check if baseline correction is good enough
				double baselineCorrectionBreakCheck = calculateBaselineCorrectionBreakCheck(baselineCorrection);
				double breakCondition = negligibleFactorMinimum * sigmaOne;
				if(baselineCorrectionBreakCheck < breakCondition) {
					baselineCorrectionLogger.info(i + " baseline correction iterations performed");
					break;
				}
			} else {
				sigmaOne = sigmaNull;
			}
			if(i == maximumIterations - 1) {
				baselineCorrectionLogger.info("maximum iterations reached!");
			}
		}
		for(int j = 0; j < spectrumData.signals.length; j++) {
			spectrumData.signals[j] = phasedSignals[j];
		}
	}

	private static Complex[] calculateBaselineCorrection(Number[] deltaAxisPPM, PolynomialFunction polyFuncReal, PolynomialFunction polyFuncImag) {

		double[] baselineCorrectionReal = new double[deltaAxisPPM.length];
		double[] baselineCorrectionImag = new double[deltaAxisPPM.length];
		Complex[] baselineCorrection = new Complex[deltaAxisPPM.length];
		for(int s = 0; s < deltaAxisPPM.length; s++) {
			baselineCorrectionReal[s] = polyFuncReal.value(deltaAxisPPM[s].doubleValue());
			baselineCorrectionImag[s] = polyFuncImag.value(deltaAxisPPM[s].doubleValue());
			baselineCorrection[s] = new Complex(baselineCorrectionReal[s], baselineCorrectionImag[s]);
		}
		return baselineCorrection;
	}

	private static double calculateBaselineCorrectionBreakCheck(Complex[] baselineCorrection) {

		double baselineCorrectionBreakCheck = 0;
		for(int g = 0; g < baselineCorrection.length; g++) {
			baselineCorrectionBreakCheck = baselineCorrectionBreakCheck + baselineCorrection[g].abs();
		}
		return baselineCorrectionBreakCheck;
	}

	private static double calculateTentativeSigma(double[] heavisideFunctionality, double[] nmrSpectrumBaselineCorrSquare) {

		double[] tempDividend = new double[heavisideFunctionality.length];
		for(int a = 0; a < heavisideFunctionality.length; a++) {
			tempDividend[a] = nmrSpectrumBaselineCorrSquare[a] * heavisideFunctionality[a];
		}
		double tempDividendSum = Arrays.stream(tempDividend).sum();
		double tempDivisor = 1 + Arrays.stream(heavisideFunctionality).sum();
		double sigmaNull = tempDividendSum / tempDivisor;
		return sigmaNull = Math.sqrt(sigmaNull);
	}

	private static double[] calculateAbsoluteSpectrum(double[] nmrSpectrumForBaselineCorr) {

		double[] nmrSpectrumBaselineCorrAbsolute = new double[nmrSpectrumForBaselineCorr.length];
		for(int i = 0; i < nmrSpectrumForBaselineCorr.length; i++) {
			nmrSpectrumBaselineCorrAbsolute[i] = Math.abs(nmrSpectrumForBaselineCorr[i]);
		}
		return nmrSpectrumBaselineCorrAbsolute;
	}

	private static double[] calculateSquaredSpectrum(double[] nmrSpectrumForBaselineCorr) {

		double[] nmrSpectrumBaselineCorrSquare = new double[nmrSpectrumForBaselineCorr.length];
		for(int i = 0; i < nmrSpectrumForBaselineCorr.length; i++) {
			nmrSpectrumBaselineCorrSquare[i] = Math.pow(nmrSpectrumForBaselineCorr[i], 2);
		}
		return nmrSpectrumBaselineCorrSquare;
	}

	private static double[] cutPartOfSpectrum(Complex[] phasedSignals, double cutPercentage) {

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
		return nmrSpectrumForBaselineCorr;
	}
}

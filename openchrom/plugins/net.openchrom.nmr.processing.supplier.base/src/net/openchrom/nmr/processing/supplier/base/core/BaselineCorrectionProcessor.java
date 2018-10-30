package net.openchrom.nmr.processing.supplier.base.core;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.eclipse.chemclipse.nmr.model.core.IScanNMR;
import org.eclipse.chemclipse.nmr.model.core.SignalNMR;
import org.eclipse.chemclipse.nmr.processor.core.AbstractScanProcessor;
import org.eclipse.chemclipse.nmr.processor.core.IScanProcessor;
import org.eclipse.chemclipse.nmr.processor.settings.IProcessorSettings;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.nmr.processing.supplier.base.settings.BaselineCorrectionSettings;

public class BaselineCorrectionProcessor extends AbstractScanProcessor implements IScanProcessor {

	public BaselineCorrectionProcessor() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public IProcessingInfo process(final IScanNMR scanNMR, final IProcessorSettings processorSettings, final IProgressMonitor monitor) {

		final IProcessingInfo processingInfo = validate(scanNMR, processorSettings);
		if(!processingInfo.hasErrorMessages()) {
			final BaselineCorrectionSettings settings = (BaselineCorrectionSettings)processorSettings;
			final Complex[] baselineCorrectedData = perform(scanNMR.getPhaseCorrectedData(), scanNMR, settings);
			scanNMR.setBaselineCorrectedData(baselineCorrectedData);
			processingInfo.setProcessingResult(scanNMR);
		}
		return processingInfo;
	}

	private Complex[] perform(final Complex[] phasedSignals, IScanNMR scanNMR, final BaselineCorrectionSettings settings) {

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
		int maximumIterations = 100;
		int numberOfFourierPoints = scanNMR.getProcessingParameters("numberOfFourierPoints").intValue();
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
		// chemical shift axis used while fitting
		double[] deltaAxisPPM = generateChemicalShiftAxis(scanNMR);
		//
		// iterative baseline correction
		for(int i = 1; i < maximumIterations; i++) {
			System.out.println("Iteration " + i);
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
			// int fitCompare = Double.compare(sigmaNull, sigmaOne);
			// if (fitCompare == 0) {
			if(Math.abs(sigmaNull - sigmaOne) < 1E-18) {
				// fitting here
				for(int z = 0; z < baselineCorrection.length; z++) {
					baselineCorrection[z] = new Complex(0, 0);
				}
				// double[] fittingFunctionality = new double[nmrSpectrumBaselineCorrAbsolute.length]; //spec_v
				for(int k = 0; k < fittingFunctionality.length; k++) {
					fittingFunctionality[k] = (fittingConstantV * sigmaOne) - nmrSpectrumBaselineCorrAbsolute[k];
				}
				// the data has to be stored in ArrayList to be fitted
				ArrayList<WeightedObservedPoint> realFittingPoints = new ArrayList<WeightedObservedPoint>();
				ArrayList<WeightedObservedPoint> imagFittingPoints = new ArrayList<WeightedObservedPoint>();
				double fittingWeight = 1.0; // weight = 1.0 if none
				// add data to ArrayList
				for(int z = 0; z < phasedSignals.length; z++) {
					if(fittingFunctionality[z] > 0) {
						realFittingPoints.add(new WeightedObservedPoint(fittingWeight, deltaAxisPPM[z], phasedSignals[z].getReal()));
						imagFittingPoints.add(new WeightedObservedPoint(fittingWeight, deltaAxisPPM[z], phasedSignals[z].getImaginary()));
					} // else?? = 0 ??
				}
				// TODO define polynomial order for fitting
				int polynomialOrder = 1; // 0,1,2,3,...
				final PolynomialCurveFitter baselineFitter = PolynomialCurveFitter.create(polynomialOrder);
				realCoeff = baselineFitter.fit(realFittingPoints);
				imagCoeff = baselineFitter.fit(imagFittingPoints);
				// apply PolynomialFunction with calculated coefficients
				final PolynomialFunction polyFuncReal = new PolynomialFunction(realCoeff);
				final PolynomialFunction polyFuncImag = new PolynomialFunction(imagCoeff);
				for(int s = 0; s < deltaAxisPPM.length; s++) {
					baselineCorrectionReal[s] = polyFuncReal.value(deltaAxisPPM[s]);
					baselineCorrectionImag[s] = polyFuncImag.value(deltaAxisPPM[s]);
				}
				// apply baseline correction
				for(int w = 0; w < nmrSpectrumFTProcessedPhasedBaseline.length; w++) {
					baselineCorrection[w] = new Complex(baselineCorrectionReal[w], baselineCorrectionImag[w]);
					nmrSpectrumFTProcessedPhasedBaseline[w] = phasedSignals[w].subtract(baselineCorrection[w]);
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
				// Complex baselineCorrectionBreakCheck = new Complex(0, 0);
				double baselineCorrectionBreakCheck = 0;
				for(int g = 0; g < baselineCorrection.length; g++) {
					// baselineCorrectionBreakCheck.add(baselineCorrection[g].abs());
					baselineCorrectionBreakCheck = baselineCorrectionBreakCheck + baselineCorrection[g].abs();
				}
				// if (baselineCorrectionBreakCheck.abs() < (negligibleFactorMinimum * sigmaOne)) {
				double breakCondition = negligibleFactorMinimum * sigmaOne;
				System.out.println("baselineCorrectionBreakCheck = " + baselineCorrectionBreakCheck + ", breakCondition = " + breakCondition + "; " + i);
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
		// save processed data for further displaying purposes in OpenNMR
		for(int i = 0; i < nmrSpectrumFTProcessedPhasedBaseline.length; i++) {
			double intensity = nmrSpectrumFTProcessedPhasedBaseline[i].getReal();
			double chemicalShift = deltaAxisPPM[i];
			scanNMR.getProcessedSignals().add(new SignalNMR(chemicalShift, intensity));
		}
		//
		return nmrSpectrumFTProcessedPhasedBaseline;
	}

	public static double[] generateChemicalShiftAxis(IScanNMR scanNMR) {

		double doubleSize = scanNMR.getProcessingParameters("numberOfFourierPoints");
		int deltaAxisPoints = (int)doubleSize;
		double[] chemicalShiftAxis = new double[(int)doubleSize];
		double minValueDeltaAxis = scanNMR.getProcessingParameters("firstDataPointOffset");
		double maxValueDeltaAxis = scanNMR.getProcessingParameters("sweepWidth") + scanNMR.getProcessingParameters("firstDataPointOffset");
		chemicalShiftAxis = generateLinearlySpacedVector(minValueDeltaAxis, maxValueDeltaAxis, deltaAxisPoints);
		return chemicalShiftAxis;
	}

	public static double[] generateLinearlySpacedVector(double minVal, double maxVal, int points) {

		double[] vector = new double[points];
		for(int i = 0; i < points; i++) {
			vector[i] = minVal + (double)i / (points - 1) * (maxVal - minVal);
		}
		return vector;
	}
}

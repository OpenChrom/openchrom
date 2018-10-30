package net.openchrom.nmr.processing.supplier.base.core;

import java.util.Arrays;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.optim.InitialGuess;
import org.apache.commons.math3.optim.MaxEval;
import org.apache.commons.math3.optim.MaxIter;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.nonlinear.scalar.MultiStartMultivariateOptimizer;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.NelderMeadSimplex;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.SimplexOptimizer;
import org.apache.commons.math3.random.GaussianRandomGenerator;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomVectorGenerator;
import org.apache.commons.math3.random.UncorrelatedRandomVectorGenerator;
import org.eclipse.chemclipse.nmr.model.core.IScanNMR;
import org.eclipse.chemclipse.nmr.processor.core.AbstractScanProcessor;
import org.eclipse.chemclipse.nmr.processor.core.IScanProcessor;
import org.eclipse.chemclipse.nmr.processor.settings.IProcessorSettings;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.nmr.processing.supplier.base.settings.PhaseCorrectionSettings;

public class PhaseCorrectionProcessor extends AbstractScanProcessor implements IScanProcessor {

	public PhaseCorrectionProcessor() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public IProcessingInfo process(final IScanNMR scanNMR, final IProcessorSettings processorSettings, final IProgressMonitor monitor) {

		final IProcessingInfo processingInfo = validate(scanNMR, processorSettings);
		if(!processingInfo.hasErrorMessages()) {
			final PhaseCorrectionSettings settings = (PhaseCorrectionSettings)processorSettings;
			final Complex[] phaseCorrectedData = perform(scanNMR.getFourierTransformedData(), scanNMR, settings);
			scanNMR.setPhaseCorrectedData(phaseCorrectedData);
			processingInfo.setProcessingResult(scanNMR);
		}
		return processingInfo;
	}

	private Complex[] perform(final Complex[] fourierTransformedSignals, IScanNMR scanNMR, final PhaseCorrectionSettings settings) {

		Complex[] nmrSpectrumFTProcessedPhased = new Complex[fourierTransformedSignals.length];
		double leftPhaseChange = 0;
		double rightPhaseChange = 0;
		// switch for automatic phasing routine
		boolean acmePhasing = true;
		//
		if(acmePhasing) {
			/*
			 * ACME algorithm - Chen et al., J Magn Res 2002, 158 (1), 164–168.
			 */
			StopWatch stopwatch = new StopWatch();
			stopwatch.start();
			// parts of optimizer
			CalculateACMEEntropy calculateACMEEntropyFcn = new CalculateACMEEntropy(scanNMR);
			SimplexOptimizer nestedSimplexOptimizer = new SimplexOptimizer(1e-10, 1e-30);
			double[] initialGuessMultiStart = new double[]{0, 0}; // {rightPhaseChange, leftPhaseChange};
			NelderMeadSimplex nelderMeadSimplex = new NelderMeadSimplex(initialGuessMultiStart.length);
			// NelderMeadSimplex nelderMeadSimplex = new NelderMeadSimplex(new double[] { 1, 5 }); // Steps along the canonical axes representing box edges
			//
			// generator for MultiStartMultivariateOptimizer
			JDKRandomGenerator randomGenerator = new JDKRandomGenerator();
			int seed = 1973582469;
			randomGenerator.setSeed(seed);
			int dimension = 2;
			RandomVectorGenerator vectorGenerator = new UncorrelatedRandomVectorGenerator(dimension, new GaussianRandomGenerator(randomGenerator));
			// optimizer
			int numberOfRestarts = 3; // if 1 optimizer behaves like a MultivariateOptimizer
			MultiStartMultivariateOptimizer multiStartPhaseOptimizer = new MultiStartMultivariateOptimizer(nestedSimplexOptimizer, numberOfRestarts, vectorGenerator);
			PointValuePair optimizedPhaseValues = multiStartPhaseOptimizer.optimize(new MaxEval(2000), new MaxIter(2000), new ObjectiveFunction(calculateACMEEntropyFcn), GoalType.MINIMIZE, nelderMeadSimplex, new InitialGuess(initialGuessMultiStart));
			// optimized values
			double[] phaseOpt = new double[initialGuessMultiStart.length];
			if(optimizedPhaseValues != null) {
				phaseOpt[0] = optimizedPhaseValues.getKey()[0];
				scanNMR.putProcessingParameters("zeroOrderPhaseCorrection", phaseOpt[0]);
				rightPhaseChange = phaseOpt[0];
				phaseOpt[1] = optimizedPhaseValues.getKey()[1];
				scanNMR.putProcessingParameters("firstOrderPhaseCorrection", phaseOpt[1]);
				leftPhaseChange = phaseOpt[1];
			}
			//
			stopwatch.stop();
			long timeElapsed = stopwatch.getTime();
			System.out.println("Automatic phase correction duration [ms]: " + timeElapsed);
			stopwatch.reset();
			System.out.println("No. of evaluations: " + calculateACMEEntropyFcn.getCount() + ", PH0: " + rightPhaseChange + ", PH1: " + leftPhaseChange);
			// phase spectrum - ps
			nmrSpectrumFTProcessedPhased = phaseCorrection(fourierTransformedSignals, phaseOpt);
		} else {
			double firstOrderPhaseCorrection = 0;
			double zeroOrderPhaseCorrection = 0;
			if(scanNMR.getProcessingParameters("firstOrderPhaseCorrection") != -1) {
				leftPhaseChange = scanNMR.getProcessingParameters("firstOrderPhaseCorrection"); // lp
			} else {
				leftPhaseChange = 0; // lp
			}
			if(scanNMR.getProcessingParameters("zeroOrderPhaseCorrection") != -1) {
				rightPhaseChange = scanNMR.getProcessingParameters("zeroOrderPhaseCorrection"); // rp
			} else {
				rightPhaseChange = 0; // rp
			}
			/*
			 * possible values for absolutePhase:
			 * 0 autophase or manual edit; commonly used
			 * ==> 1 Apply absolute values; commonly used
			 * 2 autophase or manual edit; commonly used
			 * 20 phasing individual spectra from a function
			 * (10 phasing individual spectra from a function)
			 */
			// select phasing mode
			// Scanner phaseReader = new Scanner(System.in);
			// System.out.println("Select phasing mode (0, 1, 2, 20): ");
			// int absolutePhase = inputReader.nextInt(); // = 0;
			int absolutePhase = 1;
			// phaseReader.close();
			// inputReader.close();
			scanNMR.putProcessingParameters("absolutePhase", (double)absolutePhase);
			Complex phaseCorrectionFactor = new Complex(0, (Math.PI / 180));
			Double complexSize = scanNMR.getProcessingParameters("numberOfFourierPoints");
			Complex[] totalPhaseCorrection = new Complex[complexSize.intValue()];
			Complex phaseCorrectionComplexFactor;
			double[] deltaAxisPPM = generateChemicalShiftAxis(scanNMR);
			double phasingPivotpoint = deltaAxisPPM[(complexSize.intValue() / 2) - 1];
			//
			double[] leftPhaseCorrection = generateLinearlySpacedVector(0, 1, complexSize.intValue());
			for(int i = 0; i < leftPhaseCorrection.length; i++) {
				leftPhaseCorrection[i] *= leftPhaseChange;
			}
			double[] leftPhaseCorrectionDSP = generateLinearlySpacedVector(0, 1, complexSize.intValue());
			for(int i = 0; i < leftPhaseCorrectionDSP.length; i++) {
				leftPhaseCorrectionDSP[i] *= 0;
			}
			//
			if(absolutePhase == 1) { // 1 Apply absolute values
				leftPhaseCorrectionDSP = generateLinearlySpacedVector(0, 1, complexSize.intValue());
				for(int i = 0; i < leftPhaseCorrectionDSP.length; i++) {
					leftPhaseCorrectionDSP[i] *= scanNMR.getProcessingParameters("dspPhaseFactor"); // dspPhase
				}
			} else if(absolutePhase == 20) { // 20 phasing individual spectra from a function (Matlab)
				leftPhaseCorrectionDSP = generateLinearlySpacedVector(0, 1, complexSize.intValue());
				for(int i = 0; i < leftPhaseCorrectionDSP.length; i++) {
					leftPhaseCorrectionDSP[i] *= scanNMR.getProcessingParameters("dspPhaseFactor");
				}
				leftPhaseCorrection = generateLinearlySpacedVector(0, 1, complexSize.intValue());
				for(int i = 0; i < leftPhaseCorrection.length; i++) {
					leftPhaseCorrection[i] *= (leftPhaseChange - scanNMR.getProcessingParameters("leftPhaseIndex"));
				}
				for(int i = 0; i < totalPhaseCorrection.length; i++) {
					double correctionFactorTerm = ((rightPhaseChange - scanNMR.getProcessingParameters("rightPhaseIndex")) + leftPhaseCorrection[i]);
					phaseCorrectionComplexFactor = phaseCorrectionFactor.multiply(correctionFactorTerm);
					totalPhaseCorrection[i] = phaseCorrectionComplexFactor.exp();
				}
				for(int i = 0; i < nmrSpectrumFTProcessedPhased.length; i++) {
					nmrSpectrumFTProcessedPhased[i] = fourierTransformedSignals[i].multiply(totalPhaseCorrection[i]); // phasing
				}
			} else {
				if(absolutePhase == 2) {
					// never mind the pivot
				} else {
					double phaseCorrectionTermA = (phasingPivotpoint - scanNMR.getProcessingParameters("firstDataPointOffset"));
					double phaseCorrectionTermB = phaseCorrectionTermA / scanNMR.getProcessingParameters("sweepWidth");
					double phaseCorrectionTermC = Math.round(complexSize * phaseCorrectionTermB);
					double rightPhaseCorrectionleftPhase = -(leftPhaseCorrection[(int)phaseCorrectionTermC]);
					rightPhaseChange += rightPhaseCorrectionleftPhase;
				}
				firstOrderPhaseCorrection += leftPhaseChange;
				zeroOrderPhaseCorrection += rightPhaseChange;
				scanNMR.putProcessingParameters("firstOrderPhaseCorrection", firstOrderPhaseCorrection);
				scanNMR.putProcessingParameters("zeroOrderPhaseCorrection", zeroOrderPhaseCorrection);
			}
			// phase spectra
			Complex[] phaseCorrection = new Complex[leftPhaseCorrectionDSP.length];
			for(int i = 0; i < leftPhaseCorrectionDSP.length; i++) {
				double phaseCorrectionFactorTerm = (rightPhaseChange + leftPhaseCorrection[i] + leftPhaseCorrectionDSP[i]);
				phaseCorrectionComplexFactor = phaseCorrectionFactor.multiply(phaseCorrectionFactorTerm);
				phaseCorrection[i] = phaseCorrectionComplexFactor.exp();
			}
			//
			if(absolutePhase == 20) {
				// phased already
			} else {
				for(int i = 0; i < nmrSpectrumFTProcessedPhased.length; i++) {
					nmrSpectrumFTProcessedPhased[i] = fourierTransformedSignals[i].multiply(phaseCorrection[i]); // phasing
				}
			}
		}
		return nmrSpectrumFTProcessedPhased;
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

	private static class CalculateACMEEntropy implements MultivariateFunction {

		private final Complex[] fourierTransformedData;
		private int iterCount;

		public CalculateACMEEntropy(final IScanNMR scanNMR) {
			this.fourierTransformedData = scanNMR.getFourierTransformedData();
			this.iterCount = 0;
		}

		@Override
		public double value(double[] parameters) {
			/*
			 * entropy minimization algorithm
			 */

			int stepsize = 1;
			++iterCount;
			// (de)phase spectrum
			int dataSize = fourierTransformedData.length;
			Complex[] nmrDataComplex = phaseCorrection(fourierTransformedData, parameters);
			double[] nmrDataReal = new double[dataSize];
			for(int i = 0; i < dataSize; i++) {
				nmrDataReal[i] = nmrDataComplex[i].getReal();
			}
			// calculation of first derivatives
			double[] derivativeArrayA = new double[dataSize - 2];
			System.arraycopy(nmrDataReal, 2, derivativeArrayA, 0, derivativeArrayA.length);
			double[] derivativeArrayB = new double[dataSize - 2];
			System.arraycopy(nmrDataReal, 0, derivativeArrayB, 0, derivativeArrayA.length - 2);
			double[] derivativeArrayC = new double[dataSize - 2];
			//
			for(int i = 0; i < dataSize - 2; i++) {
				derivativeArrayC[i] = Math.abs((derivativeArrayA[i] - derivativeArrayB[i]) / (stepsize * 2));
			}
			//
			double[] penaltyArray = new double[dataSize - 2];
			double derivativeArrayCSum = Arrays.stream(derivativeArrayC).sum();
			for(int i = 0; i < dataSize - 2; i++) {
				penaltyArray[i] = derivativeArrayC[i] / derivativeArrayCSum;
			}
			// calculation of entropy
			for(int p = 0; p < penaltyArray.length; p++) {
				if(new Double(penaltyArray[p]).equals(0.0)) {
					penaltyArray[p] = 1.0;
				}
			}
			//
			double[] probabilityDistribution = new double[dataSize - 2];
			for(int i = 0; i < dataSize - 2; i++) {
				probabilityDistribution[i] = -penaltyArray[i] * Math.log(penaltyArray[i]);
			}
			double probabilityDistributionSum = Arrays.stream(probabilityDistribution).sum();
			// calculation of negativity penalty
			double pFun = 0.0;
			double[] nmrDataRealAbsolute = new double[nmrDataReal.length];
			for(int i = 0; i < nmrDataReal.length; i++) {
				nmrDataRealAbsolute[i] = nmrDataReal[i] - Math.abs(nmrDataReal[i]);
			}
			double nmrDataRealAbsoluteSum = Arrays.stream(nmrDataRealAbsolute).sum();
			//
			if(nmrDataRealAbsoluteSum < 0) {
				double[] tempSquare = new double[nmrDataReal.length];
				for(int i = 0; i < nmrDataReal.length; i++) {
					tempSquare[i] = Math.pow(nmrDataRealAbsolute[i], 2);
				}
				double nmrDataRealAbsoluteSquare = Arrays.stream(tempSquare).sum();
				pFun = pFun + nmrDataRealAbsoluteSquare / 4 / dataSize / dataSize;
			}
			double penaltyFunction = 1000 * pFun;
			iterCount++;
			// value of objective function
			double result = probabilityDistributionSum + penaltyFunction;
			// System.out.format("%80.10f%n",result);
			return result;
		}

		public int getCount() {

			return iterCount;
		}
	}

	public static Complex[] phaseCorrection(Complex[] fourierTransformedData, double[] phaseParameters) {

		// convert to radians
		double zerothPhase = phaseParameters[0] * Math.PI / 180;
		double firstPhase = phaseParameters[1] * Math.PI / 180;
		int dataSize = fourierTransformedData.length;
		Complex[] nmrDataIn = fourierTransformedData;
		Complex[] nmrDataOut = new Complex[dataSize];
		Complex complexFactor = new Complex(0.0, 1.0); // sqrt(-1)
		double[] phasingArray = generateLinearlySpacedVector(0, dataSize, dataSize);
		Complex[] phaseCorrection = new Complex[dataSize];
		// generate phase correction array
		for(int i = 0; i < dataSize; i++) {
			phaseCorrection[i] = complexFactor.multiply(zerothPhase + (firstPhase * phasingArray[i] / dataSize));
		}
		for(int i = 0; i < dataSize; i++) {
			phaseCorrection[i] = phaseCorrection[i].exp();
		}
		// apply correction
		for(int i = 0; i < dataSize; i++) {
			nmrDataOut[i] = nmrDataIn[i].multiply(phaseCorrection[i]);
		}
		return nmrDataOut;
	}
}

package net.openchrom.nmr.processing.supplier.base.core;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.eclipse.chemclipse.nmr.model.support.ISignalExtractor;
import org.eclipse.chemclipse.nmr.model.support.SignalExtractor;
import org.eclipse.chemclipse.nmr.processor.core.AbstractScanProcessor;
import org.eclipse.chemclipse.nmr.processor.core.IScanProcessor;
import org.eclipse.chemclipse.nmr.processor.settings.IProcessorSettings;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.nmr.processing.supplier.base.settings.AutoPhaseCorrectionSettings;

public class AutoPhaseCorrectionProcessor extends AbstractScanProcessor implements IScanProcessor {

	public AutoPhaseCorrectionProcessor() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public IProcessingInfo process(final IScanNMR scanNMR, final IProcessorSettings processorSettings, final IProgressMonitor monitor) {

		final IProcessingInfo processingInfo = validate(scanNMR, processorSettings);
		if(!processingInfo.hasErrorMessages()) {
			final AutoPhaseCorrectionSettings settings = (AutoPhaseCorrectionSettings)processorSettings;
			ISignalExtractor signalExtractor = new SignalExtractor(scanNMR);
			final Complex[] phaseCorrection = perform(signalExtractor, scanNMR, settings);
			signalExtractor.setPhaseCorrection(phaseCorrection, true);
			processingInfo.setProcessingResult(scanNMR);
		}
		return processingInfo;
	}

	private Complex[] perform(ISignalExtractor signalExtractor, IScanNMR scanNMR, final AutoPhaseCorrectionSettings settings) {

		Complex[] fourierTransformedSignals = signalExtractor.extractFourierTransformedData();
		//
		double leftPhaseChange = 0;
		double rightPhaseChange = 0;
		//
		boolean tweakFlag = false;
		try {
			for(Map.Entry<String, String> e : scanNMR.getHeaderDataMap().entrySet()) {
				if(e.getKey().contains("JCAMP-DX")) {
					Pattern p = Pattern.compile("\\b" + "JCAMP-DX" + "\\b");
					Matcher m = p.matcher(e.getKey());
					if(m.find()) {
						tweakFlag = true;
					}
				}
			}
		} catch(Exception e) {
			throw new IllegalStateException("Variable not detected!");
		}
		//
		/*
		 * ACME algorithm - Chen et al., J Magn Res 2002, 158 (1), 164–168.
		 */
		// parts of optimizer
		CalculateACMEEntropy calculateACMEEntropyFcn = new CalculateACMEEntropy(signalExtractor.extractFourierTransformedData(), tweakFlag);
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
		phaseOpt[0] = optimizedPhaseValues.getKey()[0];
		scanNMR.putProcessingParameters("zeroOrderPhaseCorrection", phaseOpt[0]);
		rightPhaseChange = phaseOpt[0];
		phaseOpt[1] = optimizedPhaseValues.getKey()[1];
		scanNMR.putProcessingParameters("firstOrderPhaseCorrection", phaseOpt[1]);
		leftPhaseChange = phaseOpt[1];
		//
		System.out.println("No. of evaluations: " + calculateACMEEntropyFcn.getCount() + ", PH0: " + rightPhaseChange + ", PH1: " + leftPhaseChange);
		// phase spectrum - ps
		return generatePhaseCorrection(fourierTransformedSignals.length, phaseOpt);
	}

	private static class CalculateACMEEntropy implements MultivariateFunction {

		private final Complex[] fourierTransformedData;
		private int iterCount;
		private boolean tweakFlag;

		public CalculateACMEEntropy(Complex[] fourierTransformedData, boolean tweakFlag) {
			this.fourierTransformedData = fourierTransformedData;
			this.iterCount = 0;
			this.tweakFlag = tweakFlag;
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
			Complex[] nmrDataComplex = applyPhaseCorrection(fourierTransformedData, parameters);
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
			double finalTweaking = 5; // values in the range from 1.1 to 5 seem good
			// double finalTweaking = ThreadLocalRandom.current().nextDouble(1.1, 5.1); a way to automate the process?
			// =====> with a too big range the optimizer cannot find the minimum!
			//
			// Following it is conceivable to divide or multiply the penaltyFactor; Division currently preferred
			double penaltyFactor = 0;
			if(tweakFlag) {
				penaltyFactor = 1E9 / finalTweaking; // to balance the contributions of the entropy and penalty parts
			} else {
				penaltyFactor = 1E-9 / finalTweaking; // to balance the contributions of the entropy and penalty parts
			}
			double penaltyFunction = penaltyFactor * pFun;
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

	private static Complex[] applyPhaseCorrection(Complex[] fourierTransformedData, double[] phaseParameters) {

		int dataSize = fourierTransformedData.length;
		Complex[] correction = generatePhaseCorrection(dataSize, phaseParameters);
		Complex[] nmrDataIn = fourierTransformedData;
		Complex[] nmrDataOut = new Complex[dataSize];
		for(int i = 0; i < dataSize; i++) {
			nmrDataOut[i] = nmrDataIn[i].multiply(correction[i]);
		}
		return nmrDataOut;
	}

	private static Complex[] generatePhaseCorrection(int dataSize, double[] phaseParameters) {

		// convert to radians
		double zerothPhase = phaseParameters[0] * Math.PI / 180;
		double firstPhase = phaseParameters[1] * Math.PI / 180;
		Complex complexFactor = new Complex(0.0, 1.0); // sqrt(-1)
		UtilityFunctions utilityFunction = new UtilityFunctions();
		double[] phasingArray = utilityFunction.generateLinearlySpacedVector(0, dataSize, dataSize);
		Complex[] phaseCorrection = new Complex[dataSize];
		// generate phase correction array
		for(int i = 0; i < dataSize; i++) {
			phaseCorrection[i] = complexFactor.multiply(zerothPhase + (firstPhase * phasingArray[i] / dataSize));
		}
		for(int i = 0; i < dataSize; i++) {
			phaseCorrection[i] = phaseCorrection[i].exp();
		}
		// apply correction
		return phaseCorrection;
	}
}

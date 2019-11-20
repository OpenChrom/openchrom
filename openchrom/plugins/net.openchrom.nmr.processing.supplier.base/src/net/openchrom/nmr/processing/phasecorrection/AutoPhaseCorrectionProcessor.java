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
package net.openchrom.nmr.processing.phasecorrection;

import java.util.Arrays;
import java.util.Random;

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
import org.eclipse.chemclipse.model.core.IMeasurement;
import org.eclipse.chemclipse.model.filter.IMeasurementFilter;
import org.eclipse.chemclipse.nmr.model.core.FilteredSpectrumMeasurement;
import org.eclipse.chemclipse.nmr.model.core.SpectrumMeasurement;
import org.eclipse.chemclipse.processing.core.MessageConsumer;
import org.eclipse.chemclipse.processing.filter.Filter;
import org.eclipse.chemclipse.processing.filter.FilterContext;
import org.eclipse.core.runtime.IProgressMonitor;
import org.osgi.service.component.annotations.Component;

import net.openchrom.nmr.processing.supplier.base.core.AbstractSpectrumSignalFilter;
import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions;
import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions.SpectrumData;
import net.openchrom.nmr.processing.supplier.base.settings.AutoPhaseCorrectionSettings;

@Component(service = { Filter.class, IMeasurementFilter.class })
public class AutoPhaseCorrectionProcessor extends AbstractSpectrumSignalFilter<AutoPhaseCorrectionSettings> {

	private static final long serialVersionUID = 5088365386062088062L;
	private static final String NAME = "Auto Phase Correction";
	//
	private static final int DEFAULT_MAXIMUM_EVALUATIONS = 2000;
	private static int maximumEvaluations = DEFAULT_MAXIMUM_EVALUATIONS;
	private static final int DEFAULT_MAXIMUM_ITERATIONS = 2000;
	private static int maximumIterations = DEFAULT_MAXIMUM_ITERATIONS;
	//
	static AutoPhaseCorrectionProcessor aPCP = new AutoPhaseCorrectionProcessor();

	public static int getMaxEvaluations() {

		return maximumEvaluations;
	}

	public static void setMaxEvaluations(int maximumEvaluations) {

		AutoPhaseCorrectionProcessor.maximumEvaluations = maximumEvaluations;
	}

	public static int getMaxIterations() {

		return maximumIterations;
	}

	public static void setMaxIterations(int maximumIterations) {

		AutoPhaseCorrectionProcessor.maximumIterations = maximumIterations;
	}

	public AutoPhaseCorrectionProcessor(){
		super(AutoPhaseCorrectionSettings.class);
	}

	@Override
	public String getName() {

		return NAME;
	}

	public final class PhaseCorrectionValue<T extends Number> {

		private T zerothOrderValue;
		private T firstOrderValue;

		public PhaseCorrectionValue(T zerothOrderValue){
			this.zerothOrderValue = zerothOrderValue;
		}

		public PhaseCorrectionValue(T zerothOrderValue, T firstOrderValue){
			this.zerothOrderValue = zerothOrderValue;
			this.firstOrderValue = firstOrderValue;
		}

		public T getZerothOrderValue() {

			return zerothOrderValue;
		}

		public T getFirstOrderValue() {

			return firstOrderValue;
		}

		public int getLengthOfValues() {

			if(this.firstOrderValue == null) {
				return 1;
			} else {
				return 2;
			}
		}

		public double[] toArray() {

			if(this.firstOrderValue == null) {
				return new double[] { this.zerothOrderValue.doubleValue() };
			} else {
				return new double[] { this.zerothOrderValue.doubleValue(), this.firstOrderValue.doubleValue() };
			}
		}
	}

	@Override
	protected IMeasurement doFiltering(FilterContext<SpectrumMeasurement, AutoPhaseCorrectionSettings> context, MessageConsumer messageConsumer, IProgressMonitor monitor) {

		SpectrumData spectrumData = UtilityFunctions.toComplexSpectrumData(context.getFilteredObject());
		perform(spectrumData, context.getFilterConfig());
		FilteredSpectrumMeasurement<AutoPhaseCorrectionSettings> spectrumMeasurement = new FilteredSpectrumMeasurement<AutoPhaseCorrectionSettings>(context);
		spectrumMeasurement.setSignals(spectrumData.toSignal());
		return spectrumMeasurement;
	}

	private static void perform(SpectrumData spectrumData, final AutoPhaseCorrectionSettings settings) {

		Complex[] fourierTransformedSignals = spectrumData.signals;
		//
		/*
		 * ACME algorithm - Chen et al., J Magn Res 2002, 158 (1), 164–168.
		 */
		// parts of optimizer
		CalculateACMEEntropy calculateACMEEntropyFcn = new CalculateACMEEntropy(fourierTransformedSignals, settings);
		SimplexOptimizer nestedSimplexOptimizer = new SimplexOptimizer(1e-10, 1e-30);
		//
		PhaseCorrectionValue<Double> initialGuessValues;
		if(settings.isCorrectOnlyZerothPhase()) {
			// optimize only PHC0 value
			initialGuessValues = aPCP.new PhaseCorrectionValue<Double>(0.0);
		} else {
			// optimize PHC0 and PHC1 values
			initialGuessValues = aPCP.new PhaseCorrectionValue<Double>(0.0, 0.0);
		}
		NelderMeadSimplex nelderMeadSimplex = new NelderMeadSimplex(initialGuessValues.getLengthOfValues());
		// Steps along the canonical axes representing box edges
		// NelderMeadSimplex nelderMeadSimplex = new NelderMeadSimplex(new double[] { 1,
		// 5 });
		//
		// generator for MultiStartMultivariateOptimizer
		RandomVectorGenerator vectorGenerator = createRandomVectorGenerator(initialGuessValues.getLengthOfValues());
		//
		// optimizer
		int numberOfRestarts = settings.getNumberOfOptimizationCycles(); // if 1 optimizer behaves like a MultivariateOptimizer
		MultiStartMultivariateOptimizer multiStartPhaseOptimizer = //
				new MultiStartMultivariateOptimizer(nestedSimplexOptimizer, numberOfRestarts, vectorGenerator);
		PointValuePair optimizedPhaseValues = multiStartPhaseOptimizer.optimize(//
				new MaxEval(getMaxEvaluations()), //
				new MaxIter(getMaxIterations()), //
				new ObjectiveFunction(calculateACMEEntropyFcn), //
				GoalType.MINIMIZE, nelderMeadSimplex, //
				new InitialGuess(initialGuessValues.toArray()));
		// optimized values
		if(settings.isCorrectOnlyZerothPhase()) {
			// optimize only PHC0 value
			settings.setZerothOrderValue(optimizedPhaseValues.getKey()[0]);
		} else {
			// optimize PHC0 and PHC1 values
			settings.setZerothOrderValue(optimizedPhaseValues.getKey()[0]);
			settings.setFirstOrderValue(optimizedPhaseValues.getKey()[1]);
		}
		// phase spectrum - ps
		Complex[] phaseCorrection = generatePhaseCorrection(fourierTransformedSignals.length, settings);
		for(int i = 0; i < phaseCorrection.length; i++) {
			spectrumData.signals[i] = spectrumData.signals[i].multiply(phaseCorrection[i]);
		}
	}

	private static RandomVectorGenerator createRandomVectorGenerator(int dimension) {

		JDKRandomGenerator randomGenerator = new JDKRandomGenerator();
		Random seed = new Random();
		randomGenerator.setSeed(seed.nextInt());
		return new UncorrelatedRandomVectorGenerator(dimension, new GaussianRandomGenerator(randomGenerator));
	}

	private static class CalculateACMEEntropy implements MultivariateFunction {

		private final Complex[] fourierTransformedData;
		private final AutoPhaseCorrectionSettings settings;

		public CalculateACMEEntropy(Complex[] fourierTransformedData, AutoPhaseCorrectionSettings settings){
			this.fourierTransformedData = fourierTransformedData;
			this.settings = settings;
		}

		@Override
		public double value(double[] parameters) {
			/*
			 * entropy minimization algorithm
			 */

			int stepsize = 1;
			// (de)phase spectrum
			int dataSize = fourierTransformedData.length;
			if(parameters.length == 1) {
				settings.setZerothOrderValue(parameters[0]);
			} else {
				settings.setZerothOrderValue(parameters[0]);
				settings.setFirstOrderValue(parameters[1]);
			}
			Complex[] nmrDataComplex = applyPhaseCorrection(fourierTransformedData, settings);
			double[] nmrDataReal = new double[dataSize];
			for(int i = 0; i < dataSize; i++) {
				nmrDataReal[i] = nmrDataComplex[i].getReal();
			}
			/*
			 * "cut" left and right edges of spectrum (often distorted)
			 */
			double cutPercentage = settings.getOmitPercentOfTheSpectrum(); // ignore this % of spectrum each side
			int cutPartOfSpectrum = (int) Math.round(nmrDataReal.length * cutPercentage * 0.01);
			for(int i = 0; i <= cutPartOfSpectrum; i++) {
				nmrDataReal[i] = 0;
			}
			//
			int forInitialization = nmrDataReal.length - cutPartOfSpectrum + 1;
			for(int i = forInitialization; i < nmrDataReal.length; i++) {
				nmrDataReal[i] = 0;
			}
			//
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
			double penaltyFunction = settings.getPenaltyFactor() * pFun;
			// value of objective function
			double result = probabilityDistributionSum + penaltyFunction;
			// System.out.format("%80.10f%n",result);
			return result;
		}
	}

	private static Complex[] applyPhaseCorrection(Complex[] fourierTransformedData, AutoPhaseCorrectionSettings settings) {

		int dataSize = fourierTransformedData.length;
		Complex[] correction = generatePhaseCorrection(dataSize, settings);
		Complex[] nmrDataIn = fourierTransformedData;
		Complex[] nmrDataOut = new Complex[dataSize];
		for(int i = 0; i < dataSize; i++) {
			// apply correction
			nmrDataOut[i] = nmrDataIn[i].multiply(correction[i]);
		}
		return nmrDataOut;
	}

	private static Complex[] generatePhaseCorrection(int dataSize, AutoPhaseCorrectionSettings settings) {

		// convert to radians
		double zerothPhase = 0;
		double firstPhase = 0;
		if(settings.isCorrectOnlyZerothPhase()) {
			// optimize only PHC0 value
			zerothPhase = settings.getZerothOrderValue() * Math.PI / 180;
		} else {
			// optimize PHC0 and PHC1 values
			zerothPhase = settings.getZerothOrderValue() * Math.PI / 180;
			firstPhase = settings.getFirstOrderValue() * Math.PI / 180;
		}
		//
		Complex complexFactor = new Complex(0.0, 1.0); // sqrt(-1)
		double[] phasingArray = UtilityFunctions.generateLinearlySpacedVector(0, dataSize, dataSize);
		Complex[] phaseCorrection = new Complex[dataSize];
		// generate phase correction array
		for(int i = 0; i < dataSize; i++) {
			phaseCorrection[i] = complexFactor.multiply(zerothPhase + (firstPhase * phasingArray[i] / dataSize));
		}
		for(int i = 0; i < dataSize; i++) {
			phaseCorrection[i] = phaseCorrection[i].exp();
		}
		return phaseCorrection;
	}
}

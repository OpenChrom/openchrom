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
import org.eclipse.chemclipse.filter.Filter;
import org.eclipse.chemclipse.model.core.FilteredMeasurement;
import org.eclipse.chemclipse.model.filter.IMeasurementFilter;
import org.eclipse.chemclipse.nmr.model.core.FilteredSpectrumMeasurement;
import org.eclipse.chemclipse.nmr.model.core.SpectrumMeasurement;
import org.eclipse.chemclipse.processing.core.MessageConsumer;
import org.eclipse.core.runtime.IProgressMonitor;
import org.osgi.service.component.annotations.Component;

import net.openchrom.nmr.processing.supplier.base.core.AbstractSpectrumSignalFilter;
import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions;
import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions.SpectrumData;
import net.openchrom.nmr.processing.supplier.base.settings.AutoPhaseCorrectionSettings;

@Component(service = {Filter.class, IMeasurementFilter.class})
public class AutoPhaseCorrectionProcessor extends AbstractSpectrumSignalFilter<AutoPhaseCorrectionSettings> {

	private static final String NAME = "Auto Phase Correction";

	public AutoPhaseCorrectionProcessor() {
		super(AutoPhaseCorrectionSettings.class);
	}

	@Override
	public String getFilterName() {

		return NAME;
	}

	@Override
	protected FilteredMeasurement<?> doFiltering(SpectrumMeasurement measurement, AutoPhaseCorrectionSettings settings, MessageConsumer messageConsumer, IProgressMonitor monitor) {

		SpectrumData spectrumData = UtilityFunctions.toComplexSpectrumData(measurement.getSignals());
		perform(spectrumData, settings);
		FilteredSpectrumMeasurement spectrumMeasurement = new FilteredSpectrumMeasurement(measurement);
		spectrumMeasurement.setSignals(spectrumData.toSignal());
		return spectrumMeasurement;
	}

	private static void perform(SpectrumData spectrumData, final AutoPhaseCorrectionSettings settings) {

		Complex[] fourierTransformedSignals = spectrumData.signals;
		//
		//
		/*
		 * ACME algorithm - Chen et al., J Magn Res 2002, 158 (1), 164–168.
		 */
		// parts of optimizer
		CalculateACMEEntropy calculateACMEEntropyFcn = new CalculateACMEEntropy(fourierTransformedSignals, settings.getPenaltyFactor());
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
		phaseOpt[1] = optimizedPhaseValues.getKey()[1];
		//
		// phase spectrum - ps
		Complex[] phaseCorrection = generatePhaseCorrection(fourierTransformedSignals.length, phaseOpt);
		for(int i = 0; i < phaseCorrection.length; i++) {
			spectrumData.signals[i] = spectrumData.signals[i].multiply(phaseCorrection[i]);
		}
	}

	private static class CalculateACMEEntropy implements MultivariateFunction {

		private final Complex[] fourierTransformedData;
		private int iterCount;
		private double penaltyFactor;

		public CalculateACMEEntropy(Complex[] fourierTransformedData, double penaltyFactor) {
			this.fourierTransformedData = fourierTransformedData;
			this.penaltyFactor = penaltyFactor;
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

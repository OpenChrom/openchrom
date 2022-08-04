/*******************************************************************************
 * Copyright (c) 2018, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexander Stark - initial API and implementation
 * Christoph Läubrich - rework for new filter model
 * Philip Wenig - refactoring
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.core;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.eclipse.chemclipse.model.core.IMeasurement;
import org.eclipse.chemclipse.model.filter.IMeasurementFilter;
import org.eclipse.chemclipse.nmr.model.core.FilteredSpectrumMeasurement;
import org.eclipse.chemclipse.nmr.model.core.SpectrumMeasurement;
import org.eclipse.chemclipse.nmr.model.core.SpectrumSignal;
import org.eclipse.chemclipse.processing.core.IMessageConsumer;
import org.eclipse.chemclipse.processing.filter.Filter;
import org.eclipse.chemclipse.processing.filter.FilterContext;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.osgi.service.component.annotations.Component;

import net.openchrom.nmr.processing.supplier.base.settings.BaselineCorrectionSettings;

@Component(service = {Filter.class, IMeasurementFilter.class})
public class BaselineCorrectionProcessor extends AbstractSpectrumSignalFilter<BaselineCorrectionSettings> {

	private static final long serialVersionUID = -3321578724822253648L;
	private static final String NAME = "Baseline Correction";

	public BaselineCorrectionProcessor() {

		super(BaselineCorrectionSettings.class);
	}

	@Override
	public String getName() {

		return NAME;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	protected IMeasurement doFiltering(FilterContext<SpectrumMeasurement, BaselineCorrectionSettings> context, IMessageConsumer messageConsumer, IProgressMonitor monitor) {

		BaselineCorrectionSettings config = context.getFilterConfig();
		// read parameters
		int fittingConstantU = config.getFittingConstantU(); // 4 recommended from paper but probably from grotty data?
		int fittingConstantV = config.getFittingConstantV(); // 2-3 recommended from paper
		double negligibleFactorMinimum = config.getFactorForNegligibleBaselineCorrection();// 0.125 from paper
		double cutPercentage = config.getOmitPercentOfTheSpectrum(); // ignore this % of spectrum each side
		int maximumIterations = config.getNumberOfIterations();
		// init data
		List<WeightedObservedPointSpectrumSignal> signals = initSignalData(context.getFilteredObject(), cutPercentage);
		// start the fitting
		final PolynomialCurveFitter baselineFitter = PolynomialCurveFitter.create(config.getPolynomialOrder());
		double sigmaOne = Double.POSITIVE_INFINITY; // starting value: infinity
		// iterative baseline correction
		SubMonitor subMonitor = SubMonitor.convert(monitor, getName(), maximumIterations);
		int iteration = 0;
		for(; iteration < maximumIterations; iteration++) {
			if(subMonitor.isCanceled()) {
				return null;
			}
			// Tentative sigma
			double sigmaNull = calculateTentativeSigma(signals, fittingConstantU * sigmaOne);
			//
			if(Double.compare(sigmaNull, sigmaOne) == 0) {
				DoubleUnaryOperator[] polyFunction = getPolyFunction(signals, baselineFitter, fittingConstantV * sigmaOne);
				// apply baseline correction
				double baselineCorrectionBreakCheck = 0;
				for(WeightedObservedPointSpectrumSignal signal : signals) {
					double x = signal.getX();
					double realCorrection = polyFunction[0].applyAsDouble(x);
					double imagCorrection = polyFunction[1].applyAsDouble(x);
					signal.subtract(realCorrection, imagCorrection);
					baselineCorrectionBreakCheck += Math.sqrt(realCorrection * realCorrection + imagCorrection * imagCorrection);
				}
				//
				// check if baseline correction is good enough
				double breakCondition = negligibleFactorMinimum * sigmaOne;
				if(baselineCorrectionBreakCheck < breakCondition) {
					messageConsumer.addInfoMessage(getName(), iteration + " baseline correction iterations performed");
					break;
				}
			} else {
				sigmaOne = sigmaNull;
			}
			subMonitor.worked(1);
		}
		if(iteration == maximumIterations - 1) {
			messageConsumer.addWarnMessage(getName(), "maximum iterations reached!");
		}
		//
		FilteredSpectrumMeasurement filtered = new FilteredSpectrumMeasurement(context);
		filtered.setSignals(signals);
		//
		return filtered;
	}

	private static DoubleUnaryOperator[] getPolyFunction(List<WeightedObservedPointSpectrumSignal> signals, PolynomialCurveFitter baselineFitter, double fittingFunctionality) {

		List<WeightedObservedPoint> realFittingPoints = new ArrayList<>(signals.size());
		List<WeightedObservedPoint> imagFittingPoints = new ArrayList<>(signals.size());
		for(WeightedObservedPointSpectrumSignal signal : signals) {
			SignalWeightedObservedPoint realPoint = signal.real;
			double value = realPoint.cutof ? 0 : realPoint.abs;
			if(fittingFunctionality - value > 0) {
				realFittingPoints.add(realPoint);
				imagFittingPoints.add(signal.imag);
			}
		}
		// coefficients for PolynomialCurveFitter
		final PolynomialFunction polyFuncReal = new PolynomialFunction(baselineFitter.fit(realFittingPoints));
		final PolynomialFunction polyFuncImag = new PolynomialFunction(baselineFitter.fit(imagFittingPoints));
		return new DoubleUnaryOperator[]{polyFuncReal::value, polyFuncImag::value};
	}

	private static List<WeightedObservedPointSpectrumSignal> initSignalData(SpectrumMeasurement measurement, double cutPercentage) {

		List<? extends SpectrumSignal> signals = measurement.getSignals();
		int size = signals.size();
		List<WeightedObservedPointSpectrumSignal> convertedSignals = new ArrayList<>();
		double fittingWeight = 1.0; // weight = 1.0 if none
		int lowerCut = (int)Math.round(size * cutPercentage * 0.01);
		int upperCut = size - lowerCut + 1;
		for(int i = 0; i < size; i++) {
			SpectrumSignal signal = signals.get(i);
			double chemicalShift = signal.getFrequency().doubleValue();
			double realPart = signal.getY();
			boolean cut = i <= lowerCut || i >= upperCut;
			convertedSignals.add(new WeightedObservedPointSpectrumSignal(new SignalWeightedObservedPoint(fittingWeight, chemicalShift, realPart, cut), new SignalWeightedObservedPoint(fittingWeight, chemicalShift, signal.getImaginaryY(), cut)));
		}
		return convertedSignals;
	}

	private static double calculateTentativeSigma(List<WeightedObservedPointSpectrumSignal> signals, double heavySide) {

		double sum = 0;
		int count = 0;
		for(WeightedObservedPointSpectrumSignal signal : signals) {
			SignalWeightedObservedPoint point = signal.real;
			double value = point.cutof ? 0 : point.abs;
			if(heavySide - value > 0) {
				count++;
				sum += value * value;
			}
		}
		return Math.sqrt(sum / (1.0 + count));
	}

	private static final class WeightedObservedPointSpectrumSignal implements SpectrumSignal {

		private SignalWeightedObservedPoint real;
		private SignalWeightedObservedPoint imag;

		private WeightedObservedPointSpectrumSignal(SignalWeightedObservedPoint real, SignalWeightedObservedPoint imag) {

			this.real = real;
			this.imag = imag;
		}

		@Override
		public BigDecimal getFrequency() {

			return BigDecimal.valueOf(real.getX());
		}

		@Override
		public Number getAbsorptiveIntensity() {

			return real.getY();
		}

		@Override
		public Number getDispersiveIntensity() {

			return imag.getY();
		}

		private void subtract(double subtractReal, double substractImag) {

			this.real.setValue(this.real.value - subtractReal);
			this.imag.setValue(this.imag.value - substractImag);
		}

		@Override
		public double getX() {

			return real.getX();
		}
	}

	/**
	 * A modifiable extension of the unmodifiable WeightedObservedPoint
	 * 
	 * @author Christoph Läubrich
	 *
	 */
	private static final class SignalWeightedObservedPoint extends WeightedObservedPoint {

		private static final long serialVersionUID = 2565341389484393927L;
		private double value;
		private double abs;
		private boolean cutof;

		public SignalWeightedObservedPoint(double fittingWeight, double chemicalShift, double value, boolean cutof) {

			super(fittingWeight, chemicalShift, Double.NaN);
			this.cutof = cutof;
			setValue(value);
		}

		@Override
		public double getY() {

			return value;
		}

		public void setValue(double value) {

			this.value = value;
			this.abs = Math.abs(value);
		}
	}
}
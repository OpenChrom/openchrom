/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexander Stark - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.core;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.math3.complex.Complex;
import org.eclipse.chemclipse.nmr.model.core.FIDSignal;
import org.eclipse.chemclipse.nmr.model.core.IMeasurementNMR;
import org.eclipse.chemclipse.nmr.model.core.SpectrumSignal;

public class UtilityFunctions {

	public static ComplexFIDData toComplexFIDData(Collection<? extends FIDSignal> signals) {

		Complex[] array = new Complex[signals.size()];
		BigDecimal[] times = new BigDecimal[signals.size()];
		int i = 0;
		for(FIDSignal signal : signals) {
			array[i] = new Complex(signal.getY(), signal.getImaginaryY());
			times[i] = signal.getSignalTime();
		}
		return new ComplexFIDData(array, times);
	}

	public static SpectrumData toComplexSpectrumData(Collection<? extends SpectrumSignal> signals) {

		Complex[] array = new Complex[signals.size()];
		BigDecimal[] chemicalShift = new BigDecimal[signals.size()];
		int i = 0;
		for(SpectrumSignal signal : signals) {
			array[i] = new Complex(signal.getY(), signal.getImaginaryY());
			chemicalShift[i] = signal.getChemicalShift();
		}
		return new SpectrumData(array, chemicalShift);
	}

	public double[] generateChemicalShiftAxis(IMeasurementNMR scanNMR) {

		double doubleSize = scanNMR.getProcessingParameters("numberOfFourierPoints");
		int deltaAxisPoints = (int)doubleSize;
		double[] chemicalShiftAxis = new double[(int)doubleSize];
		double minValueDeltaAxis = scanNMR.getProcessingParameters("firstDataPointOffset");
		double maxValueDeltaAxis = scanNMR.getProcessingParameters("sweepWidth") + scanNMR.getProcessingParameters("firstDataPointOffset");
		chemicalShiftAxis = generateLinearlySpacedVector(minValueDeltaAxis, maxValueDeltaAxis, deltaAxisPoints);
		return chemicalShiftAxis;
	}

	public long[] generateTimeScale(IMeasurementNMR vendorScan) {

		double minValTimescale = 0;
		double maxValTimescaleFactor = (vendorScan.getProcessingParameters("numberOfFourierPoints") / vendorScan.getProcessingParameters("numberOfPoints"));
		double maxValTimescale = vendorScan.getProcessingParameters("acquisitionTime") * maxValTimescaleFactor; // linspace(0,NmrData.at*(NmrData.fn/NmrData.np),NmrData.fn);
		int timescalePoints = vendorScan.getProcessingParameters("numberOfFourierPoints").intValue();
		double[] timeScaleTemp = generateLinearlySpacedVector(minValTimescale, maxValTimescale, timescalePoints); // for ft-operation
		// else: double[] TimeScale = GenerateLinearlySpacedVector(minValTimescale, BrukerAT, TimescalePoints);
		long[] timeScale = new long[timeScaleTemp.length];
		for(int i = 0; i < timeScaleTemp.length; i++) {
			timeScale[i] = (long)(timeScaleTemp[i] * 1000000);
		}
		return timeScale;
	}

	public double[] generateLinearlySpacedVector(double minVal, double maxVal, int points) {

		double[] vector = new double[points];
		for(int i = 0; i < points; i++) {
			vector[i] = minVal + (double)i / (points - 1) * (maxVal - minVal);
		}
		return vector;
	}

	public double getMaxValueOfArray(double[] dataArray) {

		double maxValue = -Double.MAX_VALUE;
		for(int m = 0; m < dataArray.length; m++) {
			if(dataArray[m] > maxValue) {
				maxValue = dataArray[m];
			}
		}
		return maxValue;
	}

	public double getMinValueOfArray(double[] dataArray) {

		double minValue = Double.MAX_VALUE;
		for(int m = 0; m < dataArray.length; m++) {
			if(dataArray[m] < minValue) {
				minValue = dataArray[m];
			}
		}
		return minValue;
	}

	public int findIndexOfValue(double[] array, double value) {

		int index;
		for(index = 0; index < array.length; index++) {
			if(Math.abs(array[index] - value) < 0.001) {
				break;
			}
		}
		//
		int reverseIndex = array.length - 1;
		for(; reverseIndex > 0; reverseIndex--) {
			if(Math.abs(array[reverseIndex] - value) < 0.001) {
				break;
			}
		}
		//
		if(Double.compare(value, 0.0) < 0) {
			return (int)Math.floor((reverseIndex + index) / 2);
		} else {
			return (int)Math.ceil((reverseIndex + index) / 2);
		}
	}

	public void leftShiftNMRData(double[] dataArray, int pointsToShift) {

		pointsToShift = pointsToShift % dataArray.length;
		while(pointsToShift-- > 0) {
			double tempArray = dataArray[0];
			for(int i = 1; i < dataArray.length; i++) {
				dataArray[i - 1] = dataArray[i];
			}
			dataArray[dataArray.length - 1] = tempArray;
		}
	}

	public double[] rightShiftNMRData(double[] dataArray, int pointsToShift) {

		for(int i = 0; i < pointsToShift; i++) {
			double tempArray = dataArray[dataArray.length - 1];
			for(int g = dataArray.length - 2; g > -1; g--) {
				dataArray[g + 1] = dataArray[g];
			}
			dataArray[0] = tempArray;
		}
		return dataArray;
	}

	public void leftShiftNMRComplexData(Complex[] dataArray, int pointsToShift) {

		pointsToShift = pointsToShift % dataArray.length;
		while(pointsToShift-- > 0) {
			Complex tempArray = dataArray[0];
			for(int i = 1; i < dataArray.length; i++) {
				dataArray[i - 1] = dataArray[i];
			}
			dataArray[dataArray.length - 1] = tempArray;
		}
	}

	public static Complex[] rightShiftNMRComplexData(Complex[] dataArray, int pointsToShift) {

		for(int i = 0; i < pointsToShift; i++) {
			Complex tempArray = dataArray[dataArray.length - 1];
			for(int g = dataArray.length - 2; g > -1; g--) {
				dataArray[g + 1] = dataArray[g];
			}
			dataArray[0] = tempArray;
		}
		return dataArray;
	}

	public static final class SpectrumData {

		public Complex[] signals;
		public BigDecimal[] chemicalShift;

		public SpectrumData(Complex[] array, BigDecimal[] chemicalShift) {
			this.signals = array;
			this.chemicalShift = chemicalShift;
		}

		public Collection<SpectrumSignal> toSignal() {

			if(signals.length != chemicalShift.length) {
				throw new IllegalStateException("chemicalShift length differs from signals length");
			}
			List<SpectrumSignal> list = new ArrayList<>(chemicalShift.length);
			for(int i = 0; i < chemicalShift.length; i++) {
				list.add(new ComplexSpectrumSignal(chemicalShift[i], signals[i]));
			}
			return list;
		}
	}

	public static final class ComplexFIDData {

		public Complex[] signals;
		public BigDecimal[] times;

		public ComplexFIDData(Complex[] array, BigDecimal[] times) {
			this.signals = array;
			this.times = times;
		}

		public Collection<FIDSignal> toSignal() {

			if(signals.length != times.length) {
				throw new IllegalStateException("times length differs from signals length");
			}
			List<FIDSignal> list = new ArrayList<>(times.length);
			for(int i = 0; i < times.length; i++) {
				list.add(new ComplexFIDSignal(times[i], signals[i]));
			}
			return list;
		}
	}

	private static final class ComplexSpectrumSignal implements SpectrumSignal {

		private Complex complex;
		private BigDecimal shift;

		public ComplexSpectrumSignal(BigDecimal shift, Complex complex) {
			this.shift = shift;
			this.complex = complex;
		}

		@Override
		public BigDecimal getChemicalShift() {

			return shift;
		}

		@Override
		public Number getAbsorptiveIntensity() {

			return complex.getReal();
		}

		@Override
		public Number getDispersiveIntensity() {

			return complex.getImaginary();
		}
	}

	private static final class ComplexFIDSignal implements FIDSignal {

		private Complex complex;
		private BigDecimal time;

		public ComplexFIDSignal(BigDecimal time, Complex complex) {
			this.time = time;
			this.complex = complex;
		}

		@Override
		public BigDecimal getSignalTime() {

			return time;
		}

		@Override
		public Number getRealComponent() {

			return complex.getReal();
		}

		@Override
		public Number getImaginaryComponent() {

			return complex.getImaginary();
		}
	}
}

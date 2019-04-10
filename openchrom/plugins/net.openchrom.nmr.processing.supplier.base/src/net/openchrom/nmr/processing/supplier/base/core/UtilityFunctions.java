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
 * Alexander Kerner - implementation
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.core;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.math3.complex.Complex;
import org.eclipse.chemclipse.nmr.model.core.FIDSignal;
import org.eclipse.chemclipse.nmr.model.core.SpectrumSignal;

public class UtilityFunctions {

	public static ComplexFIDData toComplexFIDData(Collection<? extends FIDSignal> signals) {

		Complex[] array = new Complex[signals.size()];
		BigDecimal[] times = new BigDecimal[signals.size()];
		int i = 0;
		for(FIDSignal signal : signals) {
			array[i] = new Complex(signal.getY(), signal.getImaginaryY());
			times[i] = signal.getSignalTime();
			i++;
		}
		return new ComplexFIDData(array, times);
	}

	public static SpectrumData toComplexSpectrumData(Collection<? extends SpectrumSignal> signals) {

		Complex[] array = new Complex[signals.size()];
		BigDecimal[] chemicalShift = new BigDecimal[signals.size()];
		int i = 0;
		int maxIndex = 0;
		double maxValue = Double.MIN_NORMAL;
		for(SpectrumSignal signal : signals) {
			double real = signal.getY();
			array[i] = new Complex(real, signal.getImaginaryY());
			chemicalShift[i] = signal.getChemicalShift();
			if(real > maxValue) {
				maxValue = real;
				maxIndex = i;
			}
			i++;
		}
		return new SpectrumData(array, chemicalShift, maxIndex);
	}

	public double[] generateLinearlySpacedVector(double minVal, double maxVal, int points) {

		double[] vector = new double[points];
		for(int i = 0; i < points; i++) {
			vector[i] = minVal + (double)i / (points - 1) * (maxVal - minVal);
		}
		return vector;
	}

	public double getMaxValueOfArray(double[] dataArray) {

		return Arrays.stream(dataArray).max().orElseThrow(IllegalArgumentException::new);
	}

	public double getMinValueOfArray(double[] dataArray) {

		return Arrays.stream(dataArray).min().orElseThrow(IllegalArgumentException::new);
	}

	public int findIndexOfValue(double[] array, double value) {

		Arrays.sort(array);
		return Arrays.binarySearch(array, value);
	}

	public static int findIndexOfValue(BigDecimal[] array, double value) {

		Arrays.sort(array);
		return Arrays.binarySearch(array, value);
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
		public int maxIndex;

		public SpectrumData(Complex[] array, BigDecimal[] chemicalShift, int maxIndex) {
			this.signals = array;
			this.chemicalShift = chemicalShift;
			this.maxIndex = maxIndex;
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

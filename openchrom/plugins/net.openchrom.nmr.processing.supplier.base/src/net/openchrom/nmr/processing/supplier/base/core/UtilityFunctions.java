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

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.math3.complex.Complex;
import org.eclipse.chemclipse.nmr.model.core.AcquisitionParameter;
import org.eclipse.chemclipse.nmr.model.core.FIDSignal;
import org.eclipse.chemclipse.nmr.model.core.SpectrumMeasurement;
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

	public static Complex[] toComplexArray(List<? extends SpectrumSignal> signals) {

		Complex[] array = new Complex[signals.size()];
		for(int i = 0; i < array.length; i++) {
			SpectrumSignal signal = signals.get(i);
			array[i] = new Complex(signal.getY(), signal.getImaginaryY());
		}
		return array;
	}

	public static SpectrumData toComplexSpectrumData(SpectrumMeasurement measurement) {

		List<? extends SpectrumSignal> signals = measurement.getSignals();
		AcquisitionParameter parameter = measurement.getAcquisitionParameter();
		Complex[] array = new Complex[signals.size()];
		BigDecimal[] chemicalShift = new BigDecimal[signals.size()];
		BigDecimal[] frequency = new BigDecimal[signals.size()];
		int i = 0;
		int maxIndex = 0;
		double maxValue = Double.MIN_NORMAL;
		for(SpectrumSignal signal : signals) {
			double real = signal.getY();
			array[i] = new Complex(real, signal.getImaginaryY());
			frequency[i] = signal.getFrequency();
			chemicalShift[i] = parameter.toPPM(frequency[i]);
			if(real > maxValue) {
				maxValue = real;
				maxIndex = i;
			}
			i++;
		}
		return new SpectrumData(array, frequency, chemicalShift, maxIndex, parameter);
	}

	public static double[] generateLinearlySpacedVector(double minVal, double maxVal, int points) {

		double[] vector = new double[points];
		for(int i = 0; i < points; i++) {
			vector[i] = minVal + (double) i / (points - 1) * (maxVal - minVal);
		}
		return vector;
	}

	public static double getMaxValueOfArray(double[] dataArray) {

		return Arrays.stream(dataArray).max().orElseThrow(IllegalArgumentException::new);
	}

	public double getMinValueOfArray(double[] dataArray) {

		return Arrays.stream(dataArray).min().orElseThrow(IllegalArgumentException::new);
	}

	public static int findIndexOfValue(double[] array, double value) {

		int index;
		double threshold = 0.001;
		for(index = 0; index < array.length; index++) {
			if(Math.abs(array[index] - value) < threshold) {
				break;
			}
		}
		//
		int reverseIndex = array.length - 1;
		for(; reverseIndex > 0; reverseIndex--) {
			if(Math.abs(array[reverseIndex] - value) < threshold) {
				break;
			}
		}
		//
		if(Double.compare(value, 0.0) < 0) {
			return (int) Math.floor((reverseIndex + index) / 2);
		} else {
			return (int) Math.ceil((reverseIndex + index) / 2);
		}
	}

	public static int findIndexOfValue(Number[] array, double value) {

		int index;
		double threshold = 0.001;
		for(index = 0; index < array.length; index++) {
			if(Math.abs(array[index].doubleValue() - value) < threshold) {
				break;
			}
		}
		//
		int reverseIndex = array.length - 1;
		for(; reverseIndex > 0; reverseIndex--) {
			if(Math.abs(array[reverseIndex].doubleValue() - value) < threshold) {
				break;
			}
		}
		//
		if(Double.compare(value, 0.0) < 0) {
			return (int) Math.floor((reverseIndex + index) / 2);
		} else {
			return (int) Math.ceil((reverseIndex + index) / 2);
		}
	}

	public static int findIndexOfValue(Number[] array, BigDecimal value) {

		int index;
		for(index = 0; index < array.length; index++) {
			if(value.compareTo(BigDecimal.valueOf(array[index].doubleValue())) != -1) {
				break;
			}
		}
		//
		int reverseIndex = array.length - 1;
		for(; reverseIndex > 0; reverseIndex--) {
			if(value.compareTo(BigDecimal.valueOf(array[reverseIndex].doubleValue())) != 1) {
				break;
			}
		}
		//
		if(Double.compare(reverseIndex, index) == 0) {
			return reverseIndex;
		} else {
			if((index - reverseIndex) > 1) {
				Number[] partOfArray = Arrays.copyOfRange(array, reverseIndex, index);
				double[] searchForMax = new double[partOfArray.length];
				for(int n = 1; n < partOfArray.length; n++) {
					searchForMax[n] = partOfArray[n].doubleValue();
				}
				return findIndexOfValue(array, getMaxValueOfArray(searchForMax));
			} else {
				double indexValue = Math.abs(array[index].doubleValue() - value.doubleValue());
				double revIndexValue = Math.abs(array[reverseIndex].doubleValue() - value.doubleValue());
				if(Double.compare(value.doubleValue(), 0) < 0) {
					// values<0
					return ((Double.compare(indexValue, revIndexValue) > 0) ? index : reverseIndex);
				} else {
					// values>0
					return ((Double.compare(indexValue, revIndexValue) < 0) ? index : reverseIndex);
				}
			}
		}
	}

	public static void leftShiftNMRData(double[] dataArray, int pointsToShift) {

		pointsToShift = pointsToShift % dataArray.length;
		while (pointsToShift-- > 0) {
			double tempArray = dataArray[0];
			for(int i = 1; i < dataArray.length; i++) {
				dataArray[i - 1] = dataArray[i];
			}
			dataArray[dataArray.length - 1] = tempArray;
		}
	}

	public static double[] rightShiftNMRData(double[] dataArray, int pointsToShift) {

		for(int i = 0; i < pointsToShift; i++) {
			double tempArray = dataArray[dataArray.length - 1];
			for(int g = dataArray.length - 2; g > -1; g--) {
				dataArray[g + 1] = dataArray[g];
			}
			dataArray[0] = tempArray;
		}
		return dataArray;
	}

	public static void leftShiftNMRComplexData(Complex[] dataArray, int pointsToShift) {

		pointsToShift = pointsToShift % dataArray.length;
		while (pointsToShift-- > 0) {
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

	public static boolean lengthIsPowerOfTwo(List<? extends FIDSignal> signals) {

		if(signals.size() == 0) {
			throw new IllegalArgumentException("Signals length can't be 0");
		}
		double divisor = Math.log(2);
		double dividend = Math.log(signals.size());
		return (int) (Math.ceil((dividend / divisor))) == (int) (Math.floor(((dividend / divisor))));
	}

	public static final class SpectrumData {

		public Complex[] signals;
		public BigDecimal[] frequency;
		public final BigDecimal[] chemicalShift;
		public final int maxIndex;
		public final AcquisitionParameter parameter;

		public SpectrumData(Complex[] array, BigDecimal[] frequency, BigDecimal[] chemicalShift, int maxIndex, AcquisitionParameter parameter){
			this.signals = array;
			this.frequency = frequency;
			this.chemicalShift = chemicalShift;
			this.maxIndex = maxIndex;
			this.parameter = parameter;
		}

		public List<SpectrumSignal> toSignal() {

			if(signals.length != frequency.length) {
				throw new IllegalStateException("chemicalShift length differs from signals length");
			}
			List<SpectrumSignal> list = new ArrayList<>(frequency.length);
			for(int i = 0; i < frequency.length; i++) {
				list.add(new ComplexSpectrumSignal(frequency[i], signals[i]));
			}
			return list;
		}
	}

	public static final class ComplexFIDData implements Serializable {

		private static final long serialVersionUID = -3917469249085421088L;
		public Complex[] signals;
		public BigDecimal[] times;

		public ComplexFIDData(Complex[] array, BigDecimal[] times){
			this.signals = array;
			this.times = times;
		}

		public List<FIDSignal> toSignal() {

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
}

/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexander Stark - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.settings.support;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.math3.complex.Complex;
import org.eclipse.chemclipse.model.core.IMeasurement;
import org.eclipse.chemclipse.nmr.model.core.FilteredSpectrumMeasurement;
import org.eclipse.chemclipse.nmr.model.core.SpectrumMeasurement;
import org.eclipse.chemclipse.nmr.model.core.SpectrumSignal;
import org.eclipse.chemclipse.processing.filter.FilterContext;
import org.ejml.simple.SimpleMatrix;

public class IcoShiftAlignmentUtilities {

	public static final class Interval<T extends Number> {

		private T start;
		private T stop;

		public Interval(T start, T stop) {
			this.start = start;
			this.stop = stop;
		}

		public T getStart() {

			return start;
		}

		public T getStop() {

			return stop;
		}
	}

	public static int[] generateReferenceWindow(Interval<Integer> interval) {

		int stop = interval.getStop();
		int start = interval.getStart();
		int[] referenceWindow = new int[stop - start + 1];
		for(int i = 0; i < referenceWindow.length; ++i) {
			referenceWindow[i] = referenceWindow[i] + start + i;
		}
		return referenceWindow;
	}

	public static double[] getRealPartOfComplexArray(Complex[] array) {

		double[] result = new double[array.length];
		for(int a = 0; a < array.length; a++) {
			result[a] = array[a].getReal();
		}
		return result;
	}

	public static double calculateSquareRootOfSum(double[] array) {

		for(int i = 0; i < array.length; i++) {
			array[i] = Math.pow(array[i], 2);
		}
		double sum = Arrays.stream(array).sum();
		return Math.sqrt(sum);
	}

	public static SimpleMatrix calculateSquareRootOfSum(SimpleMatrix matrix) {

		int rows = matrix.numRows();
		matrix = matrix.elementPower(2);
		SimpleMatrix sumMatrix = new SimpleMatrix(rows, 1);
		for(int r = 0; r < rows; r++) {
			double sum = matrix.extractVector(true, r).elementSum();
			sumMatrix.setRow(r, 0, Math.sqrt(sum));
		}
		return sumMatrix;
	}

	public static List<IMeasurement> processResultsForFilter(Collection<SpectrumMeasurement> collection, SimpleMatrix result, String processorName) {

		int alignmentResultIndex = 0;
		double[] chemicalShiftAxis = ChemicalShiftCalibrationUtilities.getChemicalShiftAxis(collection);
		List<IMeasurement> results = new ArrayList<>();
		//
		for(SpectrumMeasurement measurement : collection) {
			FilteredSpectrumMeasurement<Void> filteredSpectrumMeasurement = new FilteredSpectrumMeasurement<>(FilterContext.create(measurement, null, null));
			if(processorName.contentEquals("Icoshift Alignment")) {
				filteredSpectrumMeasurement.setDataName("IcoShift");
			} else {
				filteredSpectrumMeasurement.setDataName("Calibration");
			}
			List<IcoShiftSignal> newSignals = new ArrayList<>();
			double[] dataArray = result.extractVector(true, alignmentResultIndex).getMatrix().getData();
			for(int i = 0; i < dataArray.length; i++) {
				newSignals.add(new IcoShiftSignal(chemicalShiftAxis[i], dataArray[i]));
			}
			alignmentResultIndex++;
			filteredSpectrumMeasurement.setSignals(newSignals);
			results.add(filteredSpectrumMeasurement);
		}
		return results;
	}

	private static final class IcoShiftSignal implements SpectrumSignal {

		private double ppm;
		private double real;

		public IcoShiftSignal(double ppm, double real) {
			this.ppm = ppm;
			this.real = real;
		}

		@Override
		public BigDecimal getFrequency() {

			return BigDecimal.valueOf(ppm);
		}

		@Override
		public Number getAbsorptiveIntensity() {

			return real;
		}

		@Override
		public Number getDispersiveIntensity() {

			// no imaginary part
			return 0.0d;
		}
	}
}

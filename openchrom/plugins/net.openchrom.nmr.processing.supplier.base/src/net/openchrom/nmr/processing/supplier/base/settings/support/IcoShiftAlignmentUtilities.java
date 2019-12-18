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
import org.eclipse.chemclipse.nmr.model.core.AcquisitionParameter;
import org.eclipse.chemclipse.nmr.model.core.FilteredSpectrumMeasurement;
import org.eclipse.chemclipse.nmr.model.core.SpectrumMeasurement;
import org.eclipse.chemclipse.nmr.model.core.SpectrumSignal;
import org.eclipse.chemclipse.processing.filter.FilterContext;
import org.ejml.data.DenseMatrix64F;
import org.ejml.simple.SimpleMatrix;

public class IcoShiftAlignmentUtilities {

	public static final class Interval<T extends Number> {

		private T start;
		private T stop;

		public Interval(T start, T stop){

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
		BigDecimal[] chemicalShiftAxis = ChemicalShiftCalibrationUtilities.getChemicalShiftAxis(collection);
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
			DenseMatrix64F matrix = result.extractVector(true, alignmentResultIndex).getMatrix();
			double[] dataArray = matrix.getData();
			AcquisitionParameter parameter = measurement.getAcquisitionParameter();
			for(int i = 0; i < dataArray.length; i++) {
				newSignals.add(new IcoShiftSignal(parameter.toHz(chemicalShiftAxis[i]), dataArray[i]));
			}
			alignmentResultIndex++;
			filteredSpectrumMeasurement.setSignals(newSignals);
			results.add(filteredSpectrumMeasurement);
		}
		return results;
	}

	private static final class IcoShiftSignal implements SpectrumSignal {

		private double real;
		private BigDecimal hz;

		public IcoShiftSignal(BigDecimal hz, double real){

			this.hz = hz;
			this.real = real;
		}

		@Override
		public BigDecimal getFrequency() {

			return hz;
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

	public static List<Interval<Double>> parseUserDefinedIntervalRegionsToList(String userDefinedIntervalRegions) {

		List<Interval<Double>> finalIntervalRegions = new ArrayList<Interval<Double>>();
		if(!userDefinedIntervalRegions.isEmpty()) {
			String[] tempIntervals = userDefinedIntervalRegions.split(System.getProperty("line.separator"));
			for(String interval : tempIntervals) {
				if(!interval.isEmpty()) {
					// parse
					String[] tempIntervalValues = interval.split("-");
					double[] intervalValues = new double[tempIntervalValues.length];
					int position = 0;
					for(String s : tempIntervalValues) {
						intervalValues[position] = Double.parseDouble(s);
						position++;
					}
					if(intervalValues[0] < intervalValues[1]) {
						finalIntervalRegions.add(new Interval<Double>(intervalValues[0], intervalValues[1]));
					} else {
						throw new IllegalArgumentException("Please note the order of the interval limits! Input high field value first followed by low field value.");
					}
				}
			}
		}
		return finalIntervalRegions;
	}
}

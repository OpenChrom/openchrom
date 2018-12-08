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

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.complex.Complex;
import org.eclipse.chemclipse.nmr.model.core.IMeasurementNMR;
import org.eclipse.chemclipse.nmr.model.selection.IDataNMRSelection;

public class DirectCurrentCorrection {
	/*
	 * TODO: insert DC correction for FID and spectrum here
	 */

	public Complex[] directCurrentCorrectionFID(Complex[] complexSignals, IMeasurementNMR measurementNMR) {

		/*
		 * to be used before FT
		 */
		// following as used in GNAT
		int numberOfFourierPoints = measurementNMR.getProcessingParameters("numberOfFourierPoints").intValue();
		int numberOfPoints = measurementNMR.getProcessingParameters("numberOfPoints").intValue();
		int directCurrentPointsTerm = 5 * numberOfFourierPoints / 20;
		int directCurrentPoints = Math.round(directCurrentPointsTerm);
		/*
		 * select direct current correction for FID
		 */
		int directCurrentFID = 1; // 0 = No, 1 = Yes
		//
		Complex[] freeInductionDecay = new Complex[complexSignals.length];
		Complex[] complexSignalsDCcopy = new Complex[complexSignals.length - directCurrentPoints];
		Complex complexSignalsDCcopyAverage = new Complex(0, 0);
		double[] complexSignalsReal = new double[complexSignals.length];
		double[] complexSignalsImag = new double[complexSignals.length];
		// for (int k = 1; k <= dataArrayDimension; k++) { // expand for nD dimensions
		if(numberOfFourierPoints >= numberOfPoints) {
			if(directCurrentFID > 0) {
				complexSignalsDCcopy = Arrays.copyOfRange(complexSignals, directCurrentPoints, complexSignals.length);
				for(int i = 0; i < complexSignalsDCcopy.length; i++) {
					complexSignalsReal[i] = (complexSignalsDCcopy[i].getReal());
					complexSignalsImag[i] = (complexSignalsDCcopy[i].getImaginary());
				}
				double realAverage = Arrays.stream(complexSignalsReal).average().getAsDouble();
				double imagAverage = Arrays.stream(complexSignalsImag).average().getAsDouble();
				complexSignalsDCcopyAverage = new Complex(realAverage, imagAverage);
				for(int i = 0; i < numberOfPoints; i++) {
					freeInductionDecay[i] = complexSignals[i].subtract(complexSignalsDCcopyAverage);
				}
			} else {
				freeInductionDecay = Arrays.copyOfRange(complexSignals, 0, numberOfPoints);
			}
		} else {
			if(directCurrentFID > 0) {
				complexSignalsDCcopy = Arrays.copyOfRange(complexSignals, directCurrentPoints, complexSignals.length);
				for(int i = 0; i < complexSignalsDCcopy.length; i++) {
					complexSignalsReal[i] = (complexSignalsDCcopy[i].getReal());
					complexSignalsImag[i] = (complexSignalsDCcopy[i].getImaginary());
				}
				double realAverage = Arrays.stream(complexSignalsReal).average().getAsDouble();
				double imagAverage = Arrays.stream(complexSignalsImag).average().getAsDouble();
				complexSignalsDCcopyAverage = new Complex(realAverage, imagAverage);
				for(int i = 0; i < numberOfFourierPoints; i++) {
					freeInductionDecay[i] = complexSignals[i].subtract(complexSignalsDCcopyAverage);
				}
			} else {
				freeInductionDecay = Arrays.copyOfRange(complexSignals, 0, numberOfFourierPoints);
			}
		}
		// }
		return freeInductionDecay;
	}

	public Complex[] directCurrentCorrectionSpectrum(Complex[] nmrSpectrumProcessedPhased, IDataNMRSelection dataNMRSelection) {

		/*
		 * to be used after phase correction
		 */
		// TODO select direct current correction for spectrum
		IMeasurementNMR measurementNMR = dataNMRSelection.getMeasurmentNMR();
		int directCurrentSpec = 1; // 0 = No, 1 = Yes
		Complex[] nmrSpectrumProcessedPhasedDC = new Complex[nmrSpectrumProcessedPhased.length];
		if(directCurrentSpec == 1) {
			double directCurrentPointsSpec = Math.ceil(2 * measurementNMR.getProcessingParameters("numberOfFourierPoints").intValue() / 20);
			double directCurrentPointsSpecPart = Math.ceil(directCurrentPointsSpec / 10);
			int directCurrentPointsRange = (int)(directCurrentPointsSpec - directCurrentPointsSpecPart) + 1;
			Complex[] directCurrentPointsSpecFront = new Complex[directCurrentPointsRange];
			Complex[] directCurrentPointsSpecBack = new Complex[directCurrentPointsRange];
			int z = 0;
			for(int i = (int)directCurrentPointsSpecPart; i <= (int)directCurrentPointsSpec; i++) {
				directCurrentPointsSpecFront[z] = nmrSpectrumProcessedPhased[i];
				z++;
			}
			z = 0;
			int forInitialization = (int)(nmrSpectrumProcessedPhased.length - directCurrentPointsSpec);
			int forTermination = (int)(nmrSpectrumProcessedPhased.length - directCurrentPointsSpecPart);
			for(int i = forInitialization; i <= forTermination; i++) {
				directCurrentPointsSpecBack[z] = nmrSpectrumProcessedPhased[i];
				z++;
			}
			Complex[] combinedDirectCurrentPointsSpec = new Complex[directCurrentPointsRange * 2];
			combinedDirectCurrentPointsSpec = ArrayUtils.addAll(directCurrentPointsSpecFront, directCurrentPointsSpecBack);
			double[] tempCombinedArrayReal = new double[combinedDirectCurrentPointsSpec.length];
			double[] tempCombinedArrayImag = new double[combinedDirectCurrentPointsSpec.length];
			for(int i = 0; i < combinedDirectCurrentPointsSpec.length; i++) {
				tempCombinedArrayReal[i] = (combinedDirectCurrentPointsSpec[i].getReal());
				tempCombinedArrayImag[i] = (combinedDirectCurrentPointsSpec[i].getImaginary());
			}
			double tempCombinedArrayRealAverage = Arrays.stream(tempCombinedArrayReal).average().getAsDouble();
			double tempCombinedArrayImagAverage = Arrays.stream(tempCombinedArrayImag).average().getAsDouble();
			Complex tempCombinedArrayAverage = new Complex(tempCombinedArrayRealAverage, tempCombinedArrayImagAverage);
			for(int i = 0; i < nmrSpectrumProcessedPhasedDC.length; i++) {
				nmrSpectrumProcessedPhasedDC[i] = nmrSpectrumProcessedPhased[i].subtract(tempCombinedArrayAverage);
			}
		} else {
			System.arraycopy(nmrSpectrumProcessedPhased, 0, nmrSpectrumProcessedPhasedDC, 0, nmrSpectrumProcessedPhased.length);
		}
		return nmrSpectrumProcessedPhasedDC;
	}
}

/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.core;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import org.eclipse.chemclipse.nmr.model.core.IScanNMR;
import org.eclipse.chemclipse.nmr.model.support.ISignalExtractor;
import org.eclipse.chemclipse.nmr.model.support.SignalExtractor;
import org.eclipse.chemclipse.nmr.processor.core.AbstractScanProcessor;
import org.eclipse.chemclipse.nmr.processor.core.IScanProcessor;
import org.eclipse.chemclipse.nmr.processor.settings.IProcessorSettings;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.nmr.processing.supplier.base.settings.FourierTransformationSettings;

public class FourierTransformationProcessor extends AbstractScanProcessor implements IScanProcessor {

	@Override
	public IProcessingInfo process(final IScanNMR scanNMR, final IProcessorSettings processorSettings, final IProgressMonitor monitor) {

		IProcessingInfo processingInfo = validate(scanNMR, processorSettings);
		if(!processingInfo.hasErrorMessages()) {
			FourierTransformationSettings settings = (FourierTransformationSettings)processorSettings;
			ISignalExtractor signalExtractor = new SignalExtractor(scanNMR);
			Complex[] fourierTransformedData = transform(scanNMR, settings);
			UtilityFunctions utilityFunction = new UtilityFunctions();
			double[] chemicalShift = utilityFunction.generateChemicalShiftAxis(scanNMR);
			signalExtractor.createScans(fourierTransformedData, chemicalShift);
			processingInfo.setProcessingResult(scanNMR);
		}
		return processingInfo;
	}

	private Complex[] transform(IScanNMR scanNMR, FourierTransformationSettings processorSettings) {

		/*
		 * Header Data
		 */
		// ==> done in each respective reader
		/*
		 * Raw Data
		 */
		ISignalExtractor signalExtractor = new SignalExtractor(scanNMR);
		/*
		 * according to J.Holy:
		 * ~~~~~~~
		 * extractRawIntesityFID(); will be used for data where/until the digital filter is removed
		 * and
		 * extractIntesityFID(); will hold the data after apodization (=> setScanFIDCorrection();)
		 */
		Complex[] complexSignals = signalExtractor.extractRawIntesityFID();
		//
		// leftshift the fid - complex
		// int dataArrayDimension = 0;
		// dataArrayDimension = brukerVariableImporter.importBrukerVariable(dataArrayDimension, scanNMR, "dataArrayDimension");;
		Complex[] complexSignalsShifted = new Complex[complexSignals.length];
		System.arraycopy(complexSignals, 0, complexSignalsShifted, 0, complexSignals.length);
		UtilityFunctions utilityFunction = new UtilityFunctions();
		// if (leftRotationFid > 0) {
		// //for (int k = 1; k <= dataArrayDimension; k++) {
		// dataShifter.leftShiftNMRComplexData(complexSignalsShifted, (int)leftRotationFid);
		// // expand for nD dimensions
		// //}
		// }
		//
		/*
		 * Direct Current correction; FID
		 */
		Complex[] freeInductionDecay = new Complex[complexSignals.length];
		DirectCurrentCorrection directCurrentCorrection = new DirectCurrentCorrection();
		freeInductionDecay = directCurrentCorrection.directCurrentCorrectionFID(complexSignalsShifted, scanNMR);
		/*
		 * 1. remove shifted points e.g digital filter
		 * 2. apply window multiplication = apodization is the technical term for changing the shape of e.g. an electrical signal
		 */
		double numberOfPoints = scanNMR.getProcessingParameters("numberOfPoints");
		double numberOfFourierPoints = scanNMR.getProcessingParameters("numberOfFourierPoints");
		Complex[] freeInductionDecayShiftedWindowMultiplication = new Complex[complexSignals.length];
		for(int i = 0; i < complexSignals.length; i++) {
			freeInductionDecayShiftedWindowMultiplication[i] = new Complex(0, 0);
		}
		if(numberOfFourierPoints >= numberOfPoints) {
			Complex[] tempFID = new Complex[freeInductionDecay.length];
			//
			DigitalFilterRemoval digitalFilterRemoval = new DigitalFilterRemoval();
			tempFID = digitalFilterRemoval.removeDigitalFilter(scanNMR, freeInductionDecay);
			/*
			 * applying apodization here
			 */
			WindowMultiplication apodize = new WindowMultiplication();
			double[] lineBroadeningWindwowFunction = apodize.generateApodizationFunction(scanNMR, signalExtractor);
			// signals to be modified
			tempFID = signalExtractor.extractRawIntesityFID();
			//
			if(!scanNMR.getProcessingParameters("ProcessedDataFlag").equals(1.0)) {
				/*
				 * data after removal of digital filter or without digital filter; at this point data is equal
				 */
				double[] tempRealArray = new double[freeInductionDecay.length];
				for(int i = 0; i < freeInductionDecay.length; i++) {
					tempRealArray[i] = tempFID[i].getReal();
				}
				double tempFIDmin = utilityFunction.getMinValueOfArray(tempRealArray);
				double tempFIDmax = utilityFunction.getMaxValueOfArray(tempRealArray);
				//
				if(Math.abs(tempFIDmax) > Math.abs(tempFIDmin)) {
					// System.out.println("neg, *-1");
					// introduced "-"lineBroadeningWindwowFunction after refactoring the removal of dig. filter to flip spectrum up-down
					for(int i = 0; i < lineBroadeningWindwowFunction.length; i++) {
						lineBroadeningWindwowFunction[i] *= -1;
					}
					signalExtractor.setScansFIDCorrection(lineBroadeningWindwowFunction, false);
				} else {
					// System.out.println("pos");
					signalExtractor.setScansFIDCorrection(lineBroadeningWindwowFunction, false);
				}
			} else {
				/*
				 * processed data; without removal of dig. filter
				 */
				for(int i = 0; i < lineBroadeningWindwowFunction.length; i++) {
					// do not apply apodization
					lineBroadeningWindwowFunction[i] = 1;
				}
				signalExtractor.setScansFIDCorrection(lineBroadeningWindwowFunction, false);
			}
		} // else {
			// signalExtractor.setScansFIDCorrection(lineBroadeningWindwowFunction, false);
			// }
		/*
		 * modified signals after apodization
		 */
		freeInductionDecayShiftedWindowMultiplication = signalExtractor.extractIntesityFID();
		//
		if(scanNMR.getHeaderDataMap().containsValue("Bruker BioSpin GmbH")) {
			// On A*X data, FCOR (from proc(s)) allows you to control the DC offset of the spectrum; value between 0.0 and 2.0
			double firstFIDDataPointMultiplicationFactor = scanNMR.getProcessingParameters("firstFIDDataPointMultiplicationFactor");
			// multiply first data point
			freeInductionDecayShiftedWindowMultiplication[0].multiply(firstFIDDataPointMultiplicationFactor);
		} else if(scanNMR.getHeaderDataMap().containsValue("Nanalysis Corp.")) {
			// no multiplication necessary?
		} else {
			// another approach
		}
		// zero filling // Automatic zero filling if size != 2^n
		Complex[] freeInductionDecayShiftedWindowMultiplicationZeroFill = new Complex[freeInductionDecayShiftedWindowMultiplication.length];
		//
		int n = freeInductionDecayShiftedWindowMultiplication.length;
		int nextPower = (int)(Math.ceil((Math.log(n) / Math.log(2))));
		int previousPower = (int)(Math.floor(((Math.log(n) / Math.log(2)))));
		if(nextPower != previousPower) {
			// zero filling
			double autoZeroFill = 1;
			scanNMR.putProcessingParameters("autoZeroFill", autoZeroFill);
			ZeroFilling zeroFiller = new ZeroFilling();
			freeInductionDecayShiftedWindowMultiplicationZeroFill = zeroFiller.zerofill(signalExtractor, scanNMR);
			autoZeroFill = 0;
			scanNMR.putProcessingParameters("autoZeroFill", autoZeroFill);
			// mark data as automatically zero filled
			scanNMR.putProcessingParameters("automaticallyZeroFilled", 1.0);
		} else {
			// no ZF!
		}
		//
		double zeroFillingFactor = 0.0; // 0 = no action, 16 = 16k, 32 = 32k, 64 = 64k
		if(zeroFillingFactor > 0) {
			scanNMR.putProcessingParameters("zeroFillingFactor", zeroFillingFactor);
			// user defined zero filling
			ZeroFilling zeroFiller = new ZeroFilling();
			freeInductionDecayShiftedWindowMultiplicationZeroFill = zeroFiller.zerofill(signalExtractor, scanNMR);
		} else {
			int copySize = freeInductionDecayShiftedWindowMultiplication.length;
			System.arraycopy(freeInductionDecayShiftedWindowMultiplication, 0, freeInductionDecayShiftedWindowMultiplicationZeroFill, 0, copySize);
		}
		// Fourier transform, shift and flip the data
		Complex[] nmrSpectrumProcessed = fourierTransformNmrData(freeInductionDecayShiftedWindowMultiplicationZeroFill, utilityFunction);
		if(scanNMR.getProcessingParameters("ProcessedDataFlag").equals(1.0)) {
			// shift processed data once more
			utilityFunction.leftShiftNMRComplexData(nmrSpectrumProcessed, nmrSpectrumProcessed.length / 2);
		}
		return nmrSpectrumProcessed;
	}

	public Complex[] fourierTransformNmrData(Complex[] fid, UtilityFunctions utilityFunction) {

		FastFourierTransformer fFourierTransformer = new FastFourierTransformer(DftNormalization.STANDARD);
		Complex[] nmrSpectrum = fFourierTransformer.transform(fid, TransformType.FORWARD);
		Complex[] nmrSpectrumProcessed = new Complex[nmrSpectrum.length];
		System.arraycopy(nmrSpectrum, 0, nmrSpectrumProcessed, 0, nmrSpectrum.length); // NmrData.SPECTRA
		utilityFunction.rightShiftNMRComplexData(nmrSpectrumProcessed, nmrSpectrumProcessed.length / 2);
		ArrayUtils.reverse(nmrSpectrumProcessed);
		return nmrSpectrumProcessed;
	}

	public Complex[] inverseFourierTransformData(Complex[] spectrum, UtilityFunctions utilityFunction) {

		ArrayUtils.reverse(spectrum);
		utilityFunction.rightShiftNMRComplexData(spectrum, spectrum.length / 2);
		FastFourierTransformer fFourierTransformer = new FastFourierTransformer(DftNormalization.STANDARD);
		Complex[] unfilteredFID = fFourierTransformer.transform(spectrum, TransformType.INVERSE);
		Complex[] analogFID = new Complex[unfilteredFID.length];
		System.arraycopy(unfilteredFID, 0, analogFID, 0, unfilteredFID.length);
		return analogFID;
	}
}

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

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
			double[] chemicalShift = generateChemicalShiftAxis(scanNMR);
			signalExtractor.createScans(fourierTransformedData, chemicalShift);
			processingInfo.setProcessingResult(scanNMR);
		}
		return processingInfo;
	}

	public static double[] generateChemicalShiftAxis(IScanNMR scanNMR) {

		double doubleSize = scanNMR.getProcessingParameters("numberOfFourierPoints");
		int deltaAxisPoints = (int)doubleSize;
		double[] chemicalShiftAxis = new double[(int)doubleSize];
		double minValueDeltaAxis = scanNMR.getProcessingParameters("firstDataPointOffset");
		double maxValueDeltaAxis = scanNMR.getProcessingParameters("sweepWidth") + scanNMR.getProcessingParameters("firstDataPointOffset");
		UtilityFunctions utilityFunction = new UtilityFunctions();
		chemicalShiftAxis = utilityFunction.generateLinearlySpacedVector(minValueDeltaAxis, maxValueDeltaAxis, deltaAxisPoints);
		return chemicalShiftAxis;
	}

	private Complex[] transform(IScanNMR scanNMR, FourierTransformationSettings processorSettings) {

		/*
		 * Raw Data
		 */
		double[] signals = scanNMR.getRawSignals();
		int sigsize = signals.length;
		Complex[] complexSignals = new Complex[sigsize / 2];
		int c = 0;
		for(int q = 0; q < signals.length; q += 2) {
			complexSignals[c] = new Complex(signals[q], -signals[q + 1]); // equals fid; prepared for processing
			c += 1;
		}
		/*
		 * Header Data
		 */
		BrukerVariableImporter brukerVariableImporter = new BrukerVariableImporter();
		NanalysisVariableImporter nanalysisVariableImporter = new NanalysisVariableImporter();
		if(scanNMR.getHeaderDataMap().containsValue("Bruker BioSpin GmbH")) {
			importBrukerVariables(scanNMR, brukerVariableImporter);
		} else if(scanNMR.getHeaderDataMap().containsValue("Nanalysis Corp.")) {
			importNanalysisVariables(scanNMR, nanalysisVariableImporter);
		} else {
			// another approach
		}
		/*
		 * Digital Filtering
		 */
		double groupDelayOfDigitallyFilteredData = determineDigitalFilter(scanNMR);
		// necessary parameters for processing
		double leftRotationFid = groupDelayOfDigitallyFilteredData;
		double leftRotationFidOriginal = 0;
		double dspPhaseFactor = 0;
		scanNMR.putProcessingParameters("dspPhaseFactor", dspPhaseFactor);
		scanNMR.putProcessingParameters("leftRotationFid", leftRotationFid);
		scanNMR.putProcessingParameters("leftRotationFidOriginal", leftRotationFidOriginal);
		/*
		 * data pre-processing
		 */
		// Generate Timescale
		double[] timeScale = generateTimeScale(scanNMR);
		// exponential apodization
		double[] exponentialLineBroadening = new double[timeScale.length];
		exponentialLineBroadening = exponentialApodizationFunction(timeScale, scanNMR);
		// gaussian apodization; is procs_GB the correct parameter?
		double[] gaussianLineBroadening = new double[timeScale.length];
		gaussianLineBroadening = gaussianApodizationFunction(timeScale, scanNMR);
		// apodization window function; to be applied to the fid
		double[] lineBroadeningWindwowFunction = new double[timeScale.length];
		for(int i = 0; i < timeScale.length; i++) {
			lineBroadeningWindwowFunction[i] = gaussianLineBroadening[i] * exponentialLineBroadening[i]; // for ft data
		}
		/*
		 * leftshift the fid - complex
		 */
		// int dataArrayDimension = 0;
		// dataArrayDimension = brukerVariableImporter.importBrukerVariable(dataArrayDimension, scanNMR, "dataArrayDimension");;
		Complex[] complexSignalsShifted = new Complex[complexSignals.length];
		System.arraycopy(complexSignals, 0, complexSignalsShifted, 0, complexSignals.length);
		ShiftNmrData dataShifter = new ShiftNmrData();
		// if (leftRotationFid > 0) {
		// //for (int k = 1; k <= dataArrayDimension; k++) {
		// dataShifter.leftShiftNMRComplexData(complexSignalsShifted, (int)leftRotationFid);
		// // expand for nD dimensions
		// //}
		// }
		// Direct Current correction; FID
		Complex[] freeInductionDecay = new Complex[complexSignals.length];
		freeInductionDecay = directCurrentCorrectionFID(complexSignalsShifted, scanNMR);
		// remove shifted points, apply window multiplication
		double numberOfPoints = scanNMR.getProcessingParameters("numberOfPoints");
		double numberOfFourierPoints = scanNMR.getProcessingParameters("numberOfFourierPoints");
		Complex[] freeInductionDecayShiftedWindowMultiplication = new Complex[complexSignals.length];
		for(int i = 0; i < complexSignals.length; i++) {
			freeInductionDecayShiftedWindowMultiplication[i] = new Complex(0, 0);
		}
		// int shiftedPointsToRetain = (int)(numberOfPoints-leftRotationFid+1);
		// Complex overwriteShiftedData = new Complex(0, 0);
		if(numberOfFourierPoints >= numberOfPoints) {
			Complex[] tempFID = new Complex[freeInductionDecay.length];
			if(Math.abs(leftRotationFid) > 0.0) {
				// for(int i = shiftedPointsToRetain; i < numberOfPoints; i++) {
				// freeInductionDecay[i] = overwriteShiftedData;
				// }
				// automatic zero filling just in case size != 2^n
				int checkPowerOfTwo = freeInductionDecay.length % 256;
				Complex[] freeInductionDecayZeroFill = new Complex[freeInductionDecay.length];
				if(checkPowerOfTwo > 0) {
					for(int i = 10; i < 17; i++) {
						int automaticSize = (int)Math.pow(2, i);
						if(automaticSize > freeInductionDecay.length) {
							freeInductionDecayZeroFill = new Complex[automaticSize];
							for(int j = 0; j < automaticSize; j++) {
								freeInductionDecayZeroFill[j] = new Complex(0, 0);
							}
							int copySize = freeInductionDecay.length;
							System.arraycopy(freeInductionDecay, 0, freeInductionDecayZeroFill, 0, copySize);
							break;
						}
					}
				}
				//
				Complex[] filteredNMRSpectrum = null;
				if(!Arrays.asList(freeInductionDecayZeroFill).contains(null)) {// (freeInductionDecayZeroFill != null) {
					filteredNMRSpectrum = fourierTransformNmrData(freeInductionDecayZeroFill, dataShifter);
				} else {
					filteredNMRSpectrum = fourierTransformNmrData(freeInductionDecay, dataShifter);
				}
				//
				Complex[] unfilteredNMRSpectrum = new Complex[filteredNMRSpectrum.length];
				double[] digitalFilterFactor = new double[filteredNMRSpectrum.length];
				int spectrumSize = filteredNMRSpectrum.length;
				int f = 0;
				Complex complexFactor = new Complex(-0.0, -1.0);
				// remove the filter!
				for(int i = 1; i <= spectrumSize; i++) {
					double filterTermA = (double)i / spectrumSize;
					double filterTermB = Math.floor(spectrumSize / 2);
					digitalFilterFactor[f] = filterTermA - filterTermB;
					Complex exponentialFactor = complexFactor.multiply(leftRotationFid * 2 * Math.PI * digitalFilterFactor[f]);
					unfilteredNMRSpectrum[f] = filteredNMRSpectrum[f].multiply(exponentialFactor.exp());
					f++;
				}
				//
				Complex[] tempUnfilteredSpectrum = inverseFourierTransformData(unfilteredNMRSpectrum, dataShifter);
				// remove temporary zero filling
				System.arraycopy(tempUnfilteredSpectrum, 0, tempFID, 0, tempFID.length);
			}
			if(!Arrays.asList(tempFID).contains(null)) {
				double[] tempRealArray = new double[freeInductionDecay.length];
				for(int i = 0; i < freeInductionDecay.length; i++) {
					tempRealArray[i] = tempFID[i].getReal();
				}
				UtilityFunctions utilityFunction = new UtilityFunctions();
				double tempFIDmin = utilityFunction.getMinValueOfArray(tempRealArray);
				double tempFIDmax = utilityFunction.getMaxValueOfArray(tempRealArray);
				//
				if(Math.abs(tempFIDmax) > Math.abs(tempFIDmin)) {
					// System.out.println("neg, *-1");
					for(int i = 0; i < numberOfFourierPoints; i++) {
						// introduced "-"lineBroadeningWindwowFunction after refactoring the removal of dig. filter to flip spectrum up-down
						freeInductionDecayShiftedWindowMultiplication[i] = tempFID[i].multiply(-lineBroadeningWindwowFunction[i]);
					}
				} else {
					// System.out.println("pos");
					for(int i = 0; i < numberOfFourierPoints; i++) {
						freeInductionDecayShiftedWindowMultiplication[i] = tempFID[i].multiply(lineBroadeningWindwowFunction[i]);
					}
				}
			} else {
				for(int i = 0; i < numberOfFourierPoints; i++) {
					// without removal of dig. filter
					freeInductionDecayShiftedWindowMultiplication[i] = freeInductionDecay[i].multiply(lineBroadeningWindwowFunction[i]);
				}
			}
		} else {
			for(int i = 0; i < numberOfPoints; i++) {
				freeInductionDecayShiftedWindowMultiplication[i] = freeInductionDecay[i].multiply(lineBroadeningWindwowFunction[i]);
			}
		}
		//
		if(scanNMR.getHeaderDataMap().containsValue("Bruker BioSpin GmbH")) {
			// On A*X data, FCOR (from proc(s)) allows you to control the DC offset of the spectrum; value between 0.0 and 2.0
			double firstFIDDataPointMultiplicationFactor = 0;
			firstFIDDataPointMultiplicationFactor = brukerVariableImporter.importBrukerVariable(firstFIDDataPointMultiplicationFactor, scanNMR, "procs_FCOR");
			scanNMR.putProcessingParameters("firstFIDDataPointMultiplicationFactor", firstFIDDataPointMultiplicationFactor);
			// multiply first data point
			freeInductionDecayShiftedWindowMultiplication[1].multiply(firstFIDDataPointMultiplicationFactor);
		} else if(scanNMR.getHeaderDataMap().containsValue("Nanalysis Corp.")) {
			// no multiplication necessary?
		} else {
			// another approach
		}
		// zero filling // Automatic zero filling if size != 2^n
		int zeroFillingFactor = 0;
		Complex[] freeInductionDecayShiftedWindowMultiplicationZeroFill = new Complex[freeInductionDecayShiftedWindowMultiplication.length];
		int checkPowerOfTwo = freeInductionDecayShiftedWindowMultiplication.length % 256;
		boolean automaticZeroFill = true;
		if(checkPowerOfTwo > 0) {
			for(int i = 10; i < 17; i++) {
				int automaticSize = (int)Math.pow(2, i);
				if(automaticSize > freeInductionDecayShiftedWindowMultiplication.length) {
					freeInductionDecayShiftedWindowMultiplicationZeroFill = new Complex[automaticSize];
					for(int j = 0; j < automaticSize; j++) {
						freeInductionDecayShiftedWindowMultiplicationZeroFill[j] = new Complex(0, 0);
					}
					int copySize = freeInductionDecayShiftedWindowMultiplication.length;
					System.arraycopy(freeInductionDecayShiftedWindowMultiplication, 0, freeInductionDecayShiftedWindowMultiplicationZeroFill, 0, copySize);
					numberOfFourierPoints = automaticSize;
					scanNMR.putProcessingParameters("numberOfFourierPoints", numberOfFourierPoints);
					automaticZeroFill = false;
					break;
				}
			}
		}
		if(zeroFillingFactor == 1) { // 16k
			int newDataSize = (int)Math.pow(2, 14);
			freeInductionDecayShiftedWindowMultiplicationZeroFill = new Complex[newDataSize];
			for(int i = 0; i < newDataSize; i++) {
				freeInductionDecayShiftedWindowMultiplicationZeroFill[i] = new Complex(0, 0);
			}
			int copySize = freeInductionDecayShiftedWindowMultiplication.length;
			System.arraycopy(freeInductionDecayShiftedWindowMultiplication, 0, freeInductionDecayShiftedWindowMultiplicationZeroFill, 0, copySize);
			numberOfFourierPoints = newDataSize;
			scanNMR.putProcessingParameters("numberOfFourierPoints", numberOfFourierPoints);
		} else if(zeroFillingFactor == 2) { // 32k
			int newDataSize = (int)Math.pow(2, 15);
			freeInductionDecayShiftedWindowMultiplicationZeroFill = new Complex[newDataSize];
			for(int i = 0; i < newDataSize; i++) {
				freeInductionDecayShiftedWindowMultiplicationZeroFill[i] = new Complex(0, 0);
			}
			int copySize = freeInductionDecayShiftedWindowMultiplication.length;
			System.arraycopy(freeInductionDecayShiftedWindowMultiplication, 0, freeInductionDecayShiftedWindowMultiplicationZeroFill, 0, copySize);
			numberOfFourierPoints = newDataSize;
			scanNMR.putProcessingParameters("numberOfFourierPoints", numberOfFourierPoints);
		} else if(zeroFillingFactor == 3) { // 64k
			int newDataSize = (int)Math.pow(2, 16);
			freeInductionDecayShiftedWindowMultiplicationZeroFill = new Complex[newDataSize];
			for(int i = 0; i < newDataSize; i++) {
				freeInductionDecayShiftedWindowMultiplicationZeroFill[i] = new Complex(0, 0);
			}
			int copySize = freeInductionDecayShiftedWindowMultiplication.length;
			System.arraycopy(freeInductionDecayShiftedWindowMultiplication, 0, freeInductionDecayShiftedWindowMultiplicationZeroFill, 0, copySize);
			numberOfFourierPoints = newDataSize;
			scanNMR.putProcessingParameters("numberOfFourierPoints", numberOfFourierPoints);
		} else {
			// do nothing
			if(automaticZeroFill) {
				int dataSize = freeInductionDecayShiftedWindowMultiplication.length;
				freeInductionDecayShiftedWindowMultiplicationZeroFill = new Complex[dataSize];
				System.arraycopy(freeInductionDecayShiftedWindowMultiplication, 0, freeInductionDecayShiftedWindowMultiplicationZeroFill, 0, dataSize);
				numberOfFourierPoints = dataSize;
				scanNMR.putProcessingParameters("numberOfFourierPoints", numberOfFourierPoints);
			}
		}
		// // generate x-axis (delta [ppm])
		// double[] deltaAxisPPM = generateChemicalShiftAxis(brukerParameterMap);
		// Fourier transform, shift and flip the data
		Complex[] nmrSpectrumProcessed = fourierTransformNmrData(freeInductionDecayShiftedWindowMultiplicationZeroFill, dataShifter);
		return nmrSpectrumProcessed;
	}

	private void importNanalysisVariables(IScanNMR scanNMR, NanalysisVariableImporter nanalysisVariableImporter) {

		double sweepWidth = 0;
		double firstDataPointOffset = 0;
		double firstOrderPhaseCorrection = 0;
		double zeroOrderPhaseCorrection = 0;
		double spectralReferenceFrequency = 0;
		double irradiationCarrierFrequency = 0;
		double numberOfPoints = 0;
		//
		if(scanNMR.getHeaderData("DataArrayDimension").contentEquals("1")) {
			sweepWidth = nanalysisVariableImporter.importVariable(sweepWidth, scanNMR, "SW");
			scanNMR.putProcessingParameters("sweepWidth", sweepWidth);
			//
			Matcher m = null;
			for(Map.Entry<String, String> e : scanNMR.getHeaderDataMap().entrySet()) {
				if(e.getKey().contains("LB")) {
					Pattern p = Pattern.compile("\\bLB\\b");
					m = p.matcher(e.getKey());
				}
			}
			//
			if(m.find()) {// (scanNMR.getHeaderDataMap().containsKey("LB")){
				firstDataPointOffset = nanalysisVariableImporter.importVariable(firstDataPointOffset, scanNMR, "OFFSET");
				firstDataPointOffset = firstDataPointOffset - sweepWidth;
				scanNMR.putProcessingParameters("firstDataPointOffset", firstDataPointOffset);
				firstOrderPhaseCorrection = nanalysisVariableImporter.importVariable(firstOrderPhaseCorrection, scanNMR, "PHC1");// "PHASE 1");
				scanNMR.putProcessingParameters("firstOrderPhaseCorrection", firstOrderPhaseCorrection);
				zeroOrderPhaseCorrection = nanalysisVariableImporter.importVariable(zeroOrderPhaseCorrection, scanNMR, "PHC0");// "PHASE 0");
				// zeroOrderPhaseCorrection = zeroOrderPhaseCorrection*-1 - firstOrderPhaseCorrection;
				scanNMR.putProcessingParameters("zeroOrderPhaseCorrection", zeroOrderPhaseCorrection);
			} else {
				// if not available estimate processing parameters from acquisition parameters
				spectralReferenceFrequency = nanalysisVariableImporter.importVariable(spectralReferenceFrequency, scanNMR, "BF1");
				scanNMR.putProcessingParameters("spectralReferenceFrequency", spectralReferenceFrequency);
				irradiationCarrierFrequency = nanalysisVariableImporter.importVariable(irradiationCarrierFrequency, scanNMR, "SFO1");
				scanNMR.putProcessingParameters("irradiationCarrierFrequency", irradiationCarrierFrequency);
				double offsetTermOne = (irradiationCarrierFrequency / spectralReferenceFrequency - 1) * 1E6;
				double offsetTermTwo = 0.5 * sweepWidth * (irradiationCarrierFrequency / spectralReferenceFrequency);
				firstDataPointOffset = offsetTermOne + offsetTermTwo - sweepWidth;
				scanNMR.putProcessingParameters("firstDataPointOffset", firstDataPointOffset);
				firstOrderPhaseCorrection = 0;
				scanNMR.putProcessingParameters("firstOrderPhaseCorrection", firstOrderPhaseCorrection);
				zeroOrderPhaseCorrection = 0;
				scanNMR.putProcessingParameters("zeroOrderPhaseCorrection", zeroOrderPhaseCorrection);
			}
		} else {
			// 2D and so forth to come
		}
		numberOfPoints = nanalysisVariableImporter.importVariable(numberOfPoints, scanNMR, "TD");
		numberOfPoints = numberOfPoints / 2;
		scanNMR.putProcessingParameters("numberOfPoints", numberOfPoints);
		double numberOfFourierPoints = numberOfPoints; // No of FP can be modified later on
		scanNMR.putProcessingParameters("numberOfFourierPoints", numberOfFourierPoints);
		irradiationCarrierFrequency = nanalysisVariableImporter.importVariable(irradiationCarrierFrequency, scanNMR, "SFO1");
		scanNMR.putProcessingParameters("irradiationCarrierFrequency", irradiationCarrierFrequency);
		double acquisitionTimeDivisor = (sweepWidth * irradiationCarrierFrequency);
		double acquisitionTime = numberOfPoints / acquisitionTimeDivisor;
		scanNMR.putProcessingParameters("acquisitionTime", acquisitionTime);
		double sizeofRealSpectrum = 0;
		if(scanNMR.getHeaderData("ProcessedDataFlag").contentEquals("true")) {
			sizeofRealSpectrum = nanalysisVariableImporter.importVariable(sizeofRealSpectrum, scanNMR, "SI");
			sizeofRealSpectrum = Math.round(sizeofRealSpectrum);
			scanNMR.putProcessingParameters("sizeofRealSpectrum", sizeofRealSpectrum);
			acquisitionTimeDivisor = (numberOfPoints / sizeofRealSpectrum);
			acquisitionTime = 0.5 * acquisitionTime / acquisitionTimeDivisor;
			scanNMR.putProcessingParameters("acquisitionTime", acquisitionTime);
		}
		//
		double leftPhaseIndex = firstOrderPhaseCorrection;
		double rightPhaseIndex = zeroOrderPhaseCorrection;
		scanNMR.putProcessingParameters("leftPhaseIndex", leftPhaseIndex);
		scanNMR.putProcessingParameters("rightPhaseIndex", rightPhaseIndex);
		//
		int decimationFactorOfDigitalFilter = 1; // DECIM
		decimationFactorOfDigitalFilter = nanalysisVariableImporter.importVariable(decimationFactorOfDigitalFilter, scanNMR, "DECIMATION");
		scanNMR.putProcessingParameters("decimationFactorOfDigitalFilter", (double)decimationFactorOfDigitalFilter);
		// int dspFirmwareVersion = 1; //DSPFVS
		// dspFirmwareVersion = nanalysisVariableImporter.importVariable(dspFirmwareVersion, scanNMR, "acqus_DSPFVS");
		// scanNMR.putProcessingParameters("dspFirmwareVersion", (double)dspFirmwareVersion);
		// double groupDelay = 0; // GRPDLY corresponds to digital shift
		// groupDelay = nanalysisVariableImporter.importVariable(groupDelay, scanNMR, "acqus_GRPDLY");
		// scanNMR.putProcessingParameters("groupDelay", groupDelay);
		//
		String processedDataFlag = null;
		processedDataFlag = nanalysisVariableImporter.importVariable(processedDataFlag, scanNMR, "ProcessedDataFlag");
		if(processedDataFlag.equals("true")) {
			scanNMR.putProcessingParameters("ProcessedDataFlag", 1.0);
		} else {
			scanNMR.putProcessingParameters("ProcessedDataFlag", 0.0);
		}
		//
		double exponentialLineBroadeningFactor = 0;
		exponentialLineBroadeningFactor = nanalysisVariableImporter.importVariable(exponentialLineBroadeningFactor, scanNMR, "LB");
		scanNMR.putProcessingParameters("exponentialLineBroadeningFactor", exponentialLineBroadeningFactor);
		// double gaussianLineBroadeningFactor = 0;
		// gaussianLineBroadeningFactor = nanalysisVariableImporter.importVariable(gaussianLineBroadeningFactor, scanNMR, "procs_GB");
		// scanNMR.putProcessingParameters("gaussianLineBroadeningFactor", gaussianLineBroadeningFactor);
	}

	class NanalysisVariableImporter {

		public double importVariable(double importedVariable, IScanNMR scanNMR, String variableName) {

			try {
				String tempValue = "";
				for(Map.Entry<String, String> e : scanNMR.getHeaderDataMap().entrySet()) {
					if(e.getKey().contains(variableName)) {
						Pattern p = Pattern.compile("\\b" + variableName + "\\b");
						Matcher m = p.matcher(e.getKey());
						if(m.find()) {
							tempValue = e.getValue();
							importedVariable = Double.parseDouble(tempValue);
						}
					}
				}
			} catch(Exception e) {
				throw new IllegalStateException("No Variable >>" + variableName + "<< detected!");
			}
			return importedVariable;
		}

		public String importVariable(String importedVariable, IScanNMR scanNMR, String variableName) {

			try {
				String tempValue = "";
				for(Map.Entry<String, String> e : scanNMR.getHeaderDataMap().entrySet()) {
					if(e.getKey().contains(variableName)) {
						Pattern p = Pattern.compile("\\b" + variableName + "\\b");
						Matcher m = p.matcher(e.getKey());
						if(m.find()) {
							tempValue = e.getValue();
							importedVariable = tempValue;
						}
					}
				}
			} catch(Exception e) {
				throw new IllegalStateException("No Variable >>" + variableName + "<< detected!");
			}
			return importedVariable;
		}

		public int importVariable(int importedVariable, IScanNMR scanNMR, String variableName) {

			try {
				String tempValue = "";
				for(Map.Entry<String, String> e : scanNMR.getHeaderDataMap().entrySet()) {
					if(e.getKey().contains(variableName)) {
						Pattern p = Pattern.compile("\\b" + variableName + "\\b");
						Matcher m = p.matcher(e.getKey());
						if(m.find()) {
							tempValue = e.getValue();
							importedVariable = Integer.parseInt(tempValue);
						}
					}
				}
			} catch(Exception e) {
				throw new IllegalStateException("No Variable >>" + variableName + "<< detected!");
			}
			return importedVariable;
		}
	}

	private void importBrukerVariables(IScanNMR scanNMR, BrukerVariableImporter brukerVariableImporter) {

		double sweepWidth = 0;
		double firstDataPointOffset = 0;
		double firstOrderPhaseCorrection = 0;
		double zeroOrderPhaseCorrection = 0;
		double spectralReferenceFrequency = 0;
		double irradiationCarrierFrequency = 0;
		double numberOfPoints = 0;
		//
		if(scanNMR.getHeaderData("DataArrayDimension").contentEquals("1")) {
			// The ngrad pulse program statement is mainly used on AMX/ARX spectrometers.
			// An exception is gradient shimming, where the ngrad statement is used.
			// int BrukernGrad = 1;
			//
			sweepWidth = brukerVariableImporter.importBrukerVariable(sweepWidth, scanNMR, "acqus_SW");
			scanNMR.putProcessingParameters("sweepWidth", sweepWidth);
			if(scanNMR.getHeaderDataMap().containsKey("procs_LB")) {
				firstDataPointOffset = brukerVariableImporter.importBrukerVariable(firstDataPointOffset, scanNMR, "procs_OFFSET");
				firstDataPointOffset = firstDataPointOffset - sweepWidth;
				scanNMR.putProcessingParameters("firstDataPointOffset", firstDataPointOffset);
				firstOrderPhaseCorrection = brukerVariableImporter.importBrukerVariable(firstOrderPhaseCorrection, scanNMR, "procs_PHC1");
				scanNMR.putProcessingParameters("firstOrderPhaseCorrection", firstOrderPhaseCorrection);
				zeroOrderPhaseCorrection = brukerVariableImporter.importBrukerVariable(zeroOrderPhaseCorrection, scanNMR, "procs_PHC0");
				zeroOrderPhaseCorrection = zeroOrderPhaseCorrection * -1 - firstOrderPhaseCorrection;
				scanNMR.putProcessingParameters("zeroOrderPhaseCorrection", zeroOrderPhaseCorrection);
			} else {
				// if not available estimate processing parameters from acquisition parameters
				spectralReferenceFrequency = brukerVariableImporter.importBrukerVariable(spectralReferenceFrequency, scanNMR, "acqus_BF1");
				scanNMR.putProcessingParameters("spectralReferenceFrequency", spectralReferenceFrequency);
				irradiationCarrierFrequency = brukerVariableImporter.importBrukerVariable(irradiationCarrierFrequency, scanNMR, "acqus_SFO1");
				scanNMR.putProcessingParameters("irradiationCarrierFrequency", irradiationCarrierFrequency);
				double offsetTermOne = (irradiationCarrierFrequency / spectralReferenceFrequency - 1) * 1E6;
				double offsetTermTwo = 0.5 * sweepWidth * (irradiationCarrierFrequency / spectralReferenceFrequency);
				firstDataPointOffset = offsetTermOne + offsetTermTwo - sweepWidth;
				scanNMR.putProcessingParameters("firstDataPointOffset", firstDataPointOffset);
				firstOrderPhaseCorrection = 0;
				scanNMR.putProcessingParameters("firstOrderPhaseCorrection", firstOrderPhaseCorrection);
				zeroOrderPhaseCorrection = 0;
				scanNMR.putProcessingParameters("zeroOrderPhaseCorrection", zeroOrderPhaseCorrection);
			}
		} else {
			// 2D and so forth to come
		}
		numberOfPoints = brukerVariableImporter.importBrukerVariable(numberOfPoints, scanNMR, "acqus_TD");
		numberOfPoints = numberOfPoints / 2;
		scanNMR.putProcessingParameters("numberOfPoints", numberOfPoints);
		double numberOfFourierPoints = numberOfPoints; // No of FP can be modified later on
		scanNMR.putProcessingParameters("numberOfFourierPoints", numberOfFourierPoints);
		irradiationCarrierFrequency = brukerVariableImporter.importBrukerVariable(irradiationCarrierFrequency, scanNMR, "acqus_SFO1");
		scanNMR.putProcessingParameters("irradiationCarrierFrequency", irradiationCarrierFrequency);
		double acquisitionTimeDivisor = (sweepWidth * irradiationCarrierFrequency);
		double acquisitionTime = numberOfPoints / acquisitionTimeDivisor;
		scanNMR.putProcessingParameters("acquisitionTime", acquisitionTime);
		double sizeofRealSpectrum = 0;
		if(scanNMR.getHeaderData("ProcessedDataFlag").contentEquals("true")) {
			sizeofRealSpectrum = brukerVariableImporter.importBrukerVariable(sizeofRealSpectrum, scanNMR, "procs_SI");
			sizeofRealSpectrum = Math.round(sizeofRealSpectrum);
			scanNMR.putProcessingParameters("sizeofRealSpectrum", sizeofRealSpectrum);
			acquisitionTimeDivisor = (numberOfPoints / sizeofRealSpectrum);
			acquisitionTime = 0.5 * acquisitionTime / acquisitionTimeDivisor;
			scanNMR.putProcessingParameters("acquisitionTime", acquisitionTime);
		}
		//
		double leftPhaseIndex = firstOrderPhaseCorrection;
		double rightPhaseIndex = zeroOrderPhaseCorrection;
		scanNMR.putProcessingParameters("leftPhaseIndex", leftPhaseIndex);
		scanNMR.putProcessingParameters("rightPhaseIndex", rightPhaseIndex);
		//
		int decimationFactorOfDigitalFilter = 1; // DECIM
		decimationFactorOfDigitalFilter = brukerVariableImporter.importBrukerVariable(decimationFactorOfDigitalFilter, scanNMR, "acqus_DECIM");
		scanNMR.putProcessingParameters("decimationFactorOfDigitalFilter", (double)decimationFactorOfDigitalFilter);
		//
		int dspFirmwareVersion = 1; // DSPFVS
		dspFirmwareVersion = brukerVariableImporter.importBrukerVariable(dspFirmwareVersion, scanNMR, "acqus_DSPFVS");
		scanNMR.putProcessingParameters("dspFirmwareVersion", (double)dspFirmwareVersion);
		//
		double groupDelay = 0; // GRPDLY corresponds to digital shift
		groupDelay = brukerVariableImporter.importBrukerVariable(groupDelay, scanNMR, "acqus_GRPDLY");
		scanNMR.putProcessingParameters("groupDelay", groupDelay);
		//
		String processedDataFlag = null;
		processedDataFlag = brukerVariableImporter.importBrukerVariable(processedDataFlag, scanNMR, "ProcessedDataFlag");
		if(processedDataFlag.equals("true")) {
			scanNMR.putProcessingParameters("ProcessedDataFlag", 1.0);
		} else {
			scanNMR.putProcessingParameters("ProcessedDataFlag", 0.0);
		}
		//
		double exponentialLineBroadeningFactor = 0;
		exponentialLineBroadeningFactor = brukerVariableImporter.importBrukerVariable(exponentialLineBroadeningFactor, scanNMR, "procs_LB");
		scanNMR.putProcessingParameters("exponentialLineBroadeningFactor", exponentialLineBroadeningFactor);
		//
		double gaussianLineBroadeningFactor = 0;
		gaussianLineBroadeningFactor = brukerVariableImporter.importBrukerVariable(gaussianLineBroadeningFactor, scanNMR, "procs_GB");
		scanNMR.putProcessingParameters("gaussianLineBroadeningFactor", gaussianLineBroadeningFactor);
	}

	class BrukerVariableImporter {

		public double importBrukerVariable(double importedVariable, IScanNMR scanNMR, String variableName) {

			if(scanNMR.getHeaderDataMap().containsKey(variableName)) {
				importedVariable = Double.parseDouble(scanNMR.getHeaderData(variableName));
			} else {
				throw new IllegalStateException("No Variable >>" + variableName + "<< detected!");
			}
			return importedVariable;
		}

		public String importBrukerVariable(String importedVariable, IScanNMR scanNMR, String variableName) {

			if(scanNMR.getHeaderDataMap().containsKey(variableName)) {
				importedVariable = scanNMR.getHeaderData(variableName);
			} else {
				throw new IllegalStateException("No Variable >>" + variableName + "<< detected!");
			}
			return importedVariable;
		}

		public int importBrukerVariable(int importedVariable, IScanNMR scanNMR, String variableName) {

			if(scanNMR.getHeaderDataMap().containsKey(variableName)) {
				importedVariable = Integer.parseInt(scanNMR.getHeaderData(variableName));
			} else {
				throw new IllegalStateException("No Variable >>" + variableName + "<< detected!");
			}
			return importedVariable;
		}
	}

	private static double determineDigitalFilter(IScanNMR scanNMR) {

		double[][] brukerDigitalFilter = {{2, 44.750, 46.000, 46.311}, {3, 33.500, 36.500, 36.530}, {4, 66.625, 48.000, 47.870}, {6, 59.083, 50.167, 50.229}, {8, 68.563, 53.250, 53.289}, {12, 60.375, 69.500, 69.551}, {16, 69.531, 72.250, 71.600}, {24, 61.021, 70.167, 70.184}, {32, 70.016, 72.750, 72.138}, {48, 61.344, 70.500, 70.528}, {64, 70.258, 73.000, 72.348}, {96, 61.505, 70.667, 70.700}, {128, 70.379, 72.500, 72.524}, {192, 61.586, 71.333, 0}, {256, 70.439, 72.250, 0}, {384, 61.626, 71.667, 0}, {512, 70.470, 72.125, 0}, {768, 61.647, 71.833, 0}, {1024, 70.485, 72.063, 0}, {1536, 61.657, 71.917, 0}, {2048, 70.492, 72.031, 0}};
		// System.out.println(BrukerDigitalFilter[11][1]);
		//
		int decimationFactorOfDigitalFilter = 1; // DECIM
		int decimationFactorOfDigitalFilterRow = 0;
		int dspFirmwareVersion = 1; // DSPFVS
		int dspFirmwareVersionRow = 0;
		double groupDelayOfDigitallyFilteredData = 0; // GRPDLY corresponds to digital shift
		// if (scanNMR.getHeaderDataMap().containsKey("acqus_GRPDLY")) {
		if(scanNMR.processingParametersContainsKey("groupDelay") && scanNMR.getProcessingParameters("groupDelay") != -1) {
			groupDelayOfDigitallyFilteredData = scanNMR.getProcessingParameters("groupDelay");
			// }
		} else {
			if(scanNMR.processingParametersContainsKey("decimationFactorOfDigitalFilter")) {
				decimationFactorOfDigitalFilter = scanNMR.getProcessingParameters("decimationFactorOfDigitalFilter").intValue();
				// brukerParameterMap.put("decimationFactorOfDigitalFilter", (double)decimationFactorOfDigitalFilter);
				//
				switch(decimationFactorOfDigitalFilter) {
					case 2:
						decimationFactorOfDigitalFilterRow = 0;
						break;
					case 3:
						decimationFactorOfDigitalFilterRow = 1;
						break;
					case 4:
						decimationFactorOfDigitalFilterRow = 2;
						break;
					case 6:
						decimationFactorOfDigitalFilterRow = 3;
						break;
					case 8:
						decimationFactorOfDigitalFilterRow = 4;
						break;
					case 12:
						decimationFactorOfDigitalFilterRow = 5;
						break;
					case 16:
						decimationFactorOfDigitalFilterRow = 6;
						break;
					case 24:
						decimationFactorOfDigitalFilterRow = 7;
						break;
					case 32:
						decimationFactorOfDigitalFilterRow = 8;
						break;
					case 48:
						decimationFactorOfDigitalFilterRow = 9;
						break;
					case 64:
						decimationFactorOfDigitalFilterRow = 10;
						break;
					case 96:
						decimationFactorOfDigitalFilterRow = 11;
						break;
					case 128:
						decimationFactorOfDigitalFilterRow = 12;
						break;
					case 192:
						decimationFactorOfDigitalFilterRow = 13;
						break;
					case 256:
						decimationFactorOfDigitalFilterRow = 14;
						break;
					case 384:
						decimationFactorOfDigitalFilterRow = 15;
						break;
					case 512:
						decimationFactorOfDigitalFilterRow = 16;
						break;
					case 768:
						decimationFactorOfDigitalFilterRow = 17;
						break;
					case 1024:
						decimationFactorOfDigitalFilterRow = 18;
						break;
					case 1536:
						decimationFactorOfDigitalFilterRow = 19;
						break;
					case 2048:
						decimationFactorOfDigitalFilterRow = 20;
						break;
					default:
						// unknown value
						decimationFactorOfDigitalFilter = 0;
						decimationFactorOfDigitalFilterRow = 666; // Matlab => Double.POSITIVE_INFINITY;
				}
			} else {
				// no DECIM parameter in acqus
				decimationFactorOfDigitalFilter = 0;
				decimationFactorOfDigitalFilterRow = 666; // Matlab => Double.POSITIVE_INFINITY;
			}
			if(scanNMR.processingParametersContainsKey("dspFirmwareVersion")) {
				dspFirmwareVersion = scanNMR.getProcessingParameters("dspFirmwareVersion").intValue();
				// brukerParameterMap.put("dspFirmwareVersion", (double)dspFirmwareVersion);
				//
				switch(dspFirmwareVersion) {
					case 10:
						dspFirmwareVersionRow = 1;
						break;
					case 11:
						dspFirmwareVersionRow = 2;
						break;
					case 12:
						dspFirmwareVersionRow = 3;
						break;
					default:
						// unknown value
						dspFirmwareVersion = 0;
						dspFirmwareVersionRow = 0;
				}
			} else {
				// no DSPFVS parameter in acqus
				dspFirmwareVersion = 0;
			}
			if(decimationFactorOfDigitalFilterRow > 13 && dspFirmwareVersionRow == 3) {
				// unknown combination of DSPVFS and DECIM parameters
				decimationFactorOfDigitalFilter = 0;
				dspFirmwareVersion = 0;
			}
		}
		//
		if(decimationFactorOfDigitalFilter == 0 && dspFirmwareVersion == 0) {
			// No digital filtering
			groupDelayOfDigitallyFilteredData = 0;
		} else if(decimationFactorOfDigitalFilter == 1 && dspFirmwareVersion == 1) {
			// digital filtering set by GRPDLY => do nothing
		} else {
			groupDelayOfDigitallyFilteredData = brukerDigitalFilter[decimationFactorOfDigitalFilterRow][dspFirmwareVersionRow];
		}
		//
		if(scanNMR.getProcessingParameters("ProcessedDataFlag").equals(1.0)) {
			// processed data only
			groupDelayOfDigitallyFilteredData = 0;
		}
		//
		// digital filter for further calculation
		groupDelayOfDigitallyFilteredData = Math.round(groupDelayOfDigitallyFilteredData);
		scanNMR.putProcessingParameters("groupDelayOfDigitallyFilteredData", groupDelayOfDigitallyFilteredData);
		return groupDelayOfDigitallyFilteredData;
	}

	private static double[] generateTimeScale(IScanNMR scanNMR) {

		double minValTimescale = 0;
		double maxValTimescaleFactor = (scanNMR.getProcessingParameters("numberOfFourierPoints") / scanNMR.getProcessingParameters("numberOfPoints"));
		double maxValTimescale = scanNMR.getProcessingParameters("acquisitionTime") * maxValTimescaleFactor; // linspace(0,NmrData.at*(NmrData.fn/NmrData.np),NmrData.fn);
		int timescalePoints = scanNMR.getProcessingParameters("numberOfFourierPoints").intValue();
		UtilityFunctions utilityFunction = new UtilityFunctions();
		double[] timeScale = utilityFunction.generateLinearlySpacedVector(minValTimescale, maxValTimescale, timescalePoints); // for ft-operation
		// else: double[] TimeScale = GenerateLinearlySpacedVector(minValTimescale, BrukerAT, TimescalePoints);
		return timeScale;
	}

	private static double[] exponentialApodizationFunction(double[] timeScale, IScanNMR scanNMR) {

		double exponentialLineBroadeningFactor = 0;
		if(scanNMR.processingParametersContainsKey("exponentialLineBroadeningFactor")) {
			exponentialLineBroadeningFactor = scanNMR.getProcessingParameters("exponentialLineBroadeningFactor");
		}
		double[] exponentialLineBroadening = new double[timeScale.length];
		double exponentialLineBroadenigTerm;
		if(exponentialLineBroadeningFactor > 0) {
			for(int i = 0; i < timeScale.length; i++) { // Lbfunc=exp(-Timescale'*pi*NmrData.lb);
				exponentialLineBroadenigTerm = (-timeScale[i] * Math.PI * exponentialLineBroadeningFactor);
				exponentialLineBroadening[i] = Math.exp(exponentialLineBroadenigTerm);
			}
		} else {
			for(int i = 0; i < timeScale.length; i++) {
				exponentialLineBroadening[i] = (timeScale[i] * 0 + 1);
			}
		}
		return exponentialLineBroadening;
	}

	private static double[] gaussianApodizationFunction(double[] timeScale, IScanNMR scanNMR) {

		double gaussianLineBroadeningFactor = 0;
		if(scanNMR.processingParametersContainsKey("gaussianLineBroadeningFactor")) {
			gaussianLineBroadeningFactor = scanNMR.getProcessingParameters("gaussianLineBroadeningFactor");
		}
		double[] gaussianLineBroadening = new double[timeScale.length];
		double gaussianLineBroadenigTermA;
		double gaussianLineBroadenigTermB;
		if(gaussianLineBroadeningFactor > 0) {
			// gf=2*sqrt(log(2))/(pi*NmrData.gw);
			// Gwfunc=exp(-(Timescale'/gf).^2);
			gaussianLineBroadenigTermA = (Math.PI * gaussianLineBroadeningFactor);
			double gaussFactor = 2 * Math.sqrt(Math.log(2)) / gaussianLineBroadenigTermA;
			for(int i = 0; i < timeScale.length; i++) {
				gaussianLineBroadenigTermB = -(timeScale[i] / gaussFactor);
				gaussianLineBroadening[i] = Math.exp(Math.pow(gaussianLineBroadenigTermB, 2));
			}
		} else {
			for(int i = 0; i < timeScale.length; i++) {
				gaussianLineBroadening[i] = (timeScale[i] * 0 + 1);
			}
		}
		return gaussianLineBroadening;
	}

	class ShiftNmrData {

		@SuppressWarnings("unused")
		private void leftShiftNMRData(int[] dataArray, int pointsToShift) {

			pointsToShift = pointsToShift % dataArray.length;
			while(pointsToShift-- > 0) {
				int tempArray = dataArray[0];
				for(int i = 1; i < dataArray.length; i++) {
					dataArray[i - 1] = dataArray[i];
				}
				dataArray[dataArray.length - 1] = tempArray;
			}
		}

		public int[] rightShiftNMRData(int[] dataArray, int pointsToShift) {

			for(int i = 0; i < pointsToShift; i++) {
				int tempArray = dataArray[dataArray.length - 1];
				for(int g = dataArray.length - 2; g > -1; g--) {
					dataArray[g + 1] = dataArray[g];
				}
				dataArray[0] = tempArray;
			}
			return dataArray;
		}

		@SuppressWarnings("unused")
		private void leftShiftNMRComplexData(Complex[] dataArray, int pointsToShift) {

			pointsToShift = pointsToShift % dataArray.length;
			while(pointsToShift-- > 0) {
				Complex tempArray = dataArray[0];
				for(int i = 1; i < dataArray.length; i++) {
					dataArray[i - 1] = dataArray[i];
				}
				dataArray[dataArray.length - 1] = tempArray;
			}
		}

		public Complex[] rightShiftNMRComplexData(Complex[] dataArray, int pointsToShift) {

			for(int i = 0; i < pointsToShift; i++) {
				Complex tempArray = dataArray[dataArray.length - 1];
				for(int g = dataArray.length - 2; g > -1; g--) {
					dataArray[g + 1] = dataArray[g];
				}
				dataArray[0] = tempArray;
			}
			return dataArray;
		}
	}

	private static Complex[] directCurrentCorrectionFID(Complex[] complexSignals, IScanNMR scanNMR) {

		// following as used in GNAT
		int numberOfFourierPoints = scanNMR.getProcessingParameters("numberOfFourierPoints").intValue();
		int numberOfPoints = scanNMR.getProcessingParameters("numberOfPoints").intValue();
		int directCurrentPointsTerm = 5 * numberOfFourierPoints / 20;
		int directCurrentPoints = Math.round(directCurrentPointsTerm);
		// select direct current correction for FID
		int directCurrentFID = 0; // 0 = No, 1 = Yes
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

	private static Complex[] fourierTransformNmrData(Complex[] fid, ShiftNmrData dataShifter) {

		FastFourierTransformer fFourierTransformer = new FastFourierTransformer(DftNormalization.STANDARD);
		Complex[] nmrSpectrum = fFourierTransformer.transform(fid, TransformType.FORWARD);
		Complex[] nmrSpectrumProcessed = new Complex[nmrSpectrum.length];
		System.arraycopy(nmrSpectrum, 0, nmrSpectrumProcessed, 0, nmrSpectrum.length); // NmrData.SPECTRA
		dataShifter.rightShiftNMRComplexData(nmrSpectrumProcessed, nmrSpectrumProcessed.length / 2);
		ArrayUtils.reverse(nmrSpectrumProcessed);
		return nmrSpectrumProcessed;
	}

	private static Complex[] inverseFourierTransformData(Complex[] spectrum, ShiftNmrData datashifter) {

		ArrayUtils.reverse(spectrum);
		datashifter.rightShiftNMRComplexData(spectrum, spectrum.length / 2);
		FastFourierTransformer fFourierTransformer = new FastFourierTransformer(DftNormalization.STANDARD);
		Complex[] unfilteredFID = fFourierTransformer.transform(spectrum, TransformType.INVERSE);
		Complex[] analogFID = new Complex[unfilteredFID.length];
		System.arraycopy(unfilteredFID, 0, analogFID, 0, unfilteredFID.length);
		return analogFID;
	}
}

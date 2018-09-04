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
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import org.eclipse.chemclipse.nmr.model.core.IScanNMR;
import org.eclipse.chemclipse.nmr.processor.core.AbstractScanProcessor;
import org.eclipse.chemclipse.nmr.processor.core.IScanProcessor;
import org.eclipse.chemclipse.nmr.processor.settings.IProcessorSettings;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.nmr.processing.supplier.base.settings.FourierTransformationSettings;

public class FourierTransformation extends AbstractScanProcessor implements IScanProcessor {

	@Override
	public IProcessingInfo process(final IScanNMR scanNMR, final IProcessorSettings processorSettings, final IProgressMonitor monitor) {

		IProcessingInfo processingInfo = validate(scanNMR, processorSettings, FourierTransformationSettings.class);
		if(!processingInfo.hasErrorMessages()) {
			FourierTransformationSettings settings = (FourierTransformationSettings)processorSettings;
			Complex[] fourierTransformedData = transform(scanNMR, settings);
			scanNMR.setModifiedSignals(fourierTransformedData);
			processingInfo.setProcessingResult(scanNMR);
		}
		return processingInfo;
	}

	private Complex[] transform(IScanNMR scanNMR, FourierTransformationSettings processorSettings) {

		/*
		 * Raw Data
		 */
		
		double[] signals = scanNMR.getRawSignals();
		int sigsize = signals.length;
		Complex[] complexSignals = new Complex[sigsize/2];
		int c = 0;
		for (int q=0; q<signals.length; q+=2) {
			complexSignals[c] = new Complex(signals[q], -signals[q+1]); // equals fid; prepared for processing
			c+=1;
		}
				
		/*
		 * Header Data
		 */

		BrukerVariableImporter brukerVariableImporter = new BrukerVariableImporter();

		Map<String, Double> brukerParameterMap = new HashMap<>();

		double sweepWidth = 0;
		double firstDataPointOffset = 0;
		double firstOrderPhaseCorrection = 0;
		double zeroOrderPhaseCorrection = 0;
		double spectralReferenceFrequency = 0;
		double irradiationCarrierFrequency = 0;
		double numberOfPoints = 0;
		
		if (scanNMR.getHeaderData("DataArrayDimension").contentEquals("1")) {
			// The ngrad pulse program statement is mainly used on AMX/ARX spectrometers.
			// An exception is gradient shimming, where the ngrad statement is used.
			// int BrukernGrad = 1;

			sweepWidth = brukerVariableImporter.importBrukerVariable(sweepWidth, scanNMR, "acqus_SW");
			brukerParameterMap.put("sweepWidth", sweepWidth);
			if (scanNMR.getHeaderDataMap().containsKey("procs_LB")){
				firstDataPointOffset = brukerVariableImporter.importBrukerVariable(firstDataPointOffset, scanNMR, "procs_OFFSET");
				firstDataPointOffset = firstDataPointOffset - sweepWidth;
				brukerParameterMap.put("firstDataPointOffset", firstDataPointOffset);
				firstOrderPhaseCorrection = brukerVariableImporter.importBrukerVariable(firstOrderPhaseCorrection, scanNMR, "procs_PHC1");
				brukerParameterMap.put("firstOrderPhaseCorrection", firstOrderPhaseCorrection);
				zeroOrderPhaseCorrection = brukerVariableImporter.importBrukerVariable(zeroOrderPhaseCorrection, scanNMR, "procs_PHC0");
				zeroOrderPhaseCorrection = zeroOrderPhaseCorrection*-1 - firstOrderPhaseCorrection;
				brukerParameterMap.put("zeroOrderPhaseCorrection", zeroOrderPhaseCorrection);
			} else {
				// if not available estimate processing parameters from acquisition parameters
				spectralReferenceFrequency = brukerVariableImporter.importBrukerVariable(spectralReferenceFrequency, scanNMR, "acqus_BF1"); 
				brukerParameterMap.put("spectralReferenceFrequency", spectralReferenceFrequency);
				irradiationCarrierFrequency = brukerVariableImporter.importBrukerVariable(irradiationCarrierFrequency, scanNMR, "acqus_SFO1");
				brukerParameterMap.put("irradiationCarrierFrequency", irradiationCarrierFrequency);
				double offsetTermOne = (irradiationCarrierFrequency/spectralReferenceFrequency-1)*1E6;
				double offsetTermTwo = 0.5*sweepWidth*(irradiationCarrierFrequency/spectralReferenceFrequency);
				firstDataPointOffset = offsetTermOne+offsetTermTwo-sweepWidth;
				brukerParameterMap.put("firstDataPointOffset", firstDataPointOffset);
				firstOrderPhaseCorrection = 0;
				brukerParameterMap.put("firstOrderPhaseCorrection", firstOrderPhaseCorrection);
				zeroOrderPhaseCorrection = 0;
				brukerParameterMap.put("zeroOrderPhaseCorrection", zeroOrderPhaseCorrection);
			}
		} else {
			// 2D and so forth to come
		}
		numberOfPoints = brukerVariableImporter.importBrukerVariable(numberOfPoints, scanNMR, "acqus_TD");
		numberOfPoints = numberOfPoints/2;
		brukerParameterMap.put("numberOfPoints", numberOfPoints);
		double numberOfFourierPoints = numberOfPoints; // No of FP can be modified later on	
		brukerParameterMap.put("numberOfFourierPoints", numberOfFourierPoints);
		irradiationCarrierFrequency = brukerVariableImporter.importBrukerVariable(irradiationCarrierFrequency, scanNMR, "acqus_SFO1");
		brukerParameterMap.put("irradiationCarrierFrequency", irradiationCarrierFrequency);
		double brukerATDivisor = (sweepWidth*irradiationCarrierFrequency);
		double brukerAT = numberOfPoints/brukerATDivisor;
		brukerParameterMap.put("brukerAT", brukerAT);
		double sizeofRealSpectrum = 0;
		if (scanNMR.getHeaderData("ProcessedDataFlag").contentEquals("true")) {
			sizeofRealSpectrum = brukerVariableImporter.importBrukerVariable(sizeofRealSpectrum, scanNMR, "procs_SI");
			sizeofRealSpectrum = Math.round(sizeofRealSpectrum);
			brukerParameterMap.put("sizeofRealSpectrum", sizeofRealSpectrum);
			brukerATDivisor = (numberOfPoints/sizeofRealSpectrum);
			brukerAT = 0.5*brukerAT/brukerATDivisor;
			brukerParameterMap.put("brukerAT", brukerAT);
		}

		double leftPhaseIndex = firstOrderPhaseCorrection;
		double rightPhaseIndex = zeroOrderPhaseCorrection;
		brukerParameterMap.put("leftPhaseIndex", leftPhaseIndex);
		brukerParameterMap.put("rightPhaseIndex", rightPhaseIndex);

		int decimationFactorOfDigitalFilter = 1; //DECIM
		decimationFactorOfDigitalFilter = brukerVariableImporter.importBrukerVariable(decimationFactorOfDigitalFilter, scanNMR, "acqus_DECIM"); 
		brukerParameterMap.put("decimationFactorOfDigitalFilter", (double)decimationFactorOfDigitalFilter);

		int dspFirmwareVersion = 1; //DSPFVS
		dspFirmwareVersion = brukerVariableImporter.importBrukerVariable(dspFirmwareVersion, scanNMR, "acqus_DSPFVS");
		brukerParameterMap.put("dspFirmwareVersion", (double)dspFirmwareVersion);

		double groupDelay = 0; // GRPDLY corresponds to digital shift
		groupDelay = brukerVariableImporter.importBrukerVariable(groupDelay, scanNMR, "acqus_GRPDLY");
		brukerParameterMap.put("groupDelay", groupDelay);

		String processedDataFlag = null;
		processedDataFlag = brukerVariableImporter.importBrukerVariable(processedDataFlag, scanNMR, "ProcessedDataFlag");
		if (processedDataFlag.equals("true")) {
			brukerParameterMap.put("ProcessedDataFlag", 1.0);
		} else {
			brukerParameterMap.put("ProcessedDataFlag", 0.0);
		}

		double exponentialLineBroadeningFactor = 0;
		exponentialLineBroadeningFactor = brukerVariableImporter.importBrukerVariable(exponentialLineBroadeningFactor, scanNMR, "procs_LB");
		brukerParameterMap.put("exponentialLineBroadeningFactor", exponentialLineBroadeningFactor);

		double gaussianLineBroadeningFactor = 0;
		gaussianLineBroadeningFactor = brukerVariableImporter.importBrukerVariable(gaussianLineBroadeningFactor, scanNMR, "procs_GB");
		brukerParameterMap.put("gaussianLineBroadeningFactor", gaussianLineBroadeningFactor);

		/*
		 * Digital Filtering
		 */

		double groupDelayOfDigitallyFilteredData = determineDigitalFilter(brukerParameterMap);

		// necessary parameters for processing
		double leftRotationFid = groupDelayOfDigitallyFilteredData;
		double leftRotationFidOriginal = 0;
		double dspPhaseFactor = 0;
		brukerParameterMap.put("dspPhaseFactor", dspPhaseFactor);
		brukerParameterMap.put("leftRotationFid", leftRotationFid);
		brukerParameterMap.put("leftRotationFidOriginal", leftRotationFidOriginal);

		/*
		 * data pre-processing
		 */
		
		// Generate Timescale
		double[] timeScale = generateTimeScale(brukerParameterMap);
		
		// exponential apodization
		double[] exponentialLineBroadening = new double[timeScale.length];
		exponentialLineBroadening = exponentialApodizationFunction(timeScale, brukerParameterMap);
		
		// gaussian apodization; is procs_GB the correct parameter?
		double[] gaussianLineBroadening = new double[timeScale.length];
		gaussianLineBroadening = gaussianApodizationFunction(timeScale, brukerParameterMap);
		
		// apodization window function; to be applied to the fid
		double[] lineBroadeningWindwowFunction = new double[timeScale.length];
		for (int i = 0; i < timeScale.length; i++) {
			lineBroadeningWindwowFunction[i] = gaussianLineBroadening[i] * exponentialLineBroadening[i]; // for ft data
		}
		
		// leftshift the fid - complex
		//int dataArrayDimension = 0;
		//dataArrayDimension = brukerVariableImporter.importBrukerVariable(dataArrayDimension, scanNMR, "dataArrayDimension");;
		Complex[] complexSignalsShifted = new Complex[complexSignals.length];
		System.arraycopy(complexSignals, 0, complexSignalsShifted, 0, complexSignals.length );
		ShiftNmrData dataShifter = new ShiftNmrData();
		if (leftRotationFid > 0) {		    	
			//for (int k = 1; k <= dataArrayDimension; k++) {
			dataShifter.leftShiftNMRComplexData(complexSignalsShifted, (int)leftRotationFid);
			// expand for nD dimensions
			//}			    
		}
		
		// Direct Current correction; FID
		Complex[] freeInductionDecay = new Complex[complexSignals.length];
		freeInductionDecay = directCurrentCorrectionFID(complexSignals, brukerParameterMap);

		// remove shifted points, apply window multiplication		    
		Complex[] freeInductionDecayShiftedWindowMultiplication = new Complex[complexSignals.length];
		for (int i = 0; i < complexSignals.length; i++) {
			freeInductionDecayShiftedWindowMultiplication[i] = new Complex(0, 0);
		}
		int shiftedPointsToRetain = (int)(numberOfPoints-leftRotationFid+1);
		Complex overwriteShiftedData = new Complex(0, 0);
		if (numberOfFourierPoints >= numberOfPoints) {
			if (Math.abs(leftRotationFid) > 0) {
				for(int i = shiftedPointsToRetain; i < numberOfPoints; i++) {
					freeInductionDecay[i] = overwriteShiftedData;
				}
			}
			for (int i = 0; i < numberOfFourierPoints; i++) {
				freeInductionDecayShiftedWindowMultiplication[i] = freeInductionDecay[i].multiply(lineBroadeningWindwowFunction[i]);
			}
		} else {
			for (int i = 0; i < numberOfPoints; i++) {
				freeInductionDecayShiftedWindowMultiplication[i] = freeInductionDecay[i].multiply(lineBroadeningWindwowFunction[i]);
			}
		}

		// On A*X data, FCOR (from proc(s)) allows you to control the DC offset of the spectrum; value between 0.0 and 2.0
		double firstFIDDataPointMultiplicationFactor = 0;
		firstFIDDataPointMultiplicationFactor = brukerVariableImporter.importBrukerVariable(firstFIDDataPointMultiplicationFactor, scanNMR, "procs_FCOR");
		// multiply first data point
		freeInductionDecayShiftedWindowMultiplication[1].multiply(firstFIDDataPointMultiplicationFactor);
		
		// zero filling // Automatic zero filling if size != 2^n
		int zeroFillingFactor = 0;		
		Complex[] freeInductionDecayShiftedWindowMultiplicationZeroFill = new Complex[freeInductionDecayShiftedWindowMultiplication.length];
		int checkPowerOfTwo = freeInductionDecayShiftedWindowMultiplication.length % 256;
		boolean automaticZeroFill = true;
		if (checkPowerOfTwo > 0) {
			for (int i = 10; i < 17; i++) {
				int automaticSize = (int)Math.pow(2, i);
				if (automaticSize > freeInductionDecayShiftedWindowMultiplication.length) {
					freeInductionDecayShiftedWindowMultiplicationZeroFill = new Complex[automaticSize];
					for (int j = 0; j < automaticSize; j++) {
						freeInductionDecayShiftedWindowMultiplicationZeroFill[j] = new Complex(0, 0);
					}
					int copySize = freeInductionDecayShiftedWindowMultiplication.length;
					System.arraycopy(freeInductionDecayShiftedWindowMultiplication, 0, freeInductionDecayShiftedWindowMultiplicationZeroFill, 0, copySize);
					numberOfFourierPoints = automaticSize;
					automaticZeroFill = false;
					break;
				}
			}		    	
		}

		if (zeroFillingFactor == 1) { // 16k
			int newDataSize = (int)Math.pow(2, 14);
			freeInductionDecayShiftedWindowMultiplicationZeroFill = new Complex[newDataSize];
			for (int i = 0; i < newDataSize; i++) {
				freeInductionDecayShiftedWindowMultiplicationZeroFill[i] = new Complex(0, 0);
			}
			int copySize = freeInductionDecayShiftedWindowMultiplication.length;
			System.arraycopy(freeInductionDecayShiftedWindowMultiplication, 0, freeInductionDecayShiftedWindowMultiplicationZeroFill, 0, copySize);
			numberOfFourierPoints = newDataSize;
		} else if (zeroFillingFactor == 2) { // 32k
			int newDataSize = (int)Math.pow(2, 15);
			freeInductionDecayShiftedWindowMultiplicationZeroFill = new Complex[newDataSize];
			for (int i = 0; i < newDataSize; i++) {
				freeInductionDecayShiftedWindowMultiplicationZeroFill[i] = new Complex(0, 0);
			}
			int copySize = freeInductionDecayShiftedWindowMultiplication.length;
			System.arraycopy(freeInductionDecayShiftedWindowMultiplication, 0, freeInductionDecayShiftedWindowMultiplicationZeroFill, 0, copySize);
			numberOfFourierPoints = newDataSize;
		} else if (zeroFillingFactor == 3){ // 64k
			int newDataSize = (int)Math.pow(2, 16);
			freeInductionDecayShiftedWindowMultiplicationZeroFill = new Complex[newDataSize];
			for (int i = 0; i < newDataSize; i++) {
				freeInductionDecayShiftedWindowMultiplicationZeroFill[i] = new Complex(0, 0);
			}
			int copySize = freeInductionDecayShiftedWindowMultiplication.length;
			System.arraycopy(freeInductionDecayShiftedWindowMultiplication, 0, freeInductionDecayShiftedWindowMultiplicationZeroFill, 0, copySize);
			numberOfFourierPoints = newDataSize;
		} else {
			//do nothing
			if (automaticZeroFill) {
				int dataSize = freeInductionDecayShiftedWindowMultiplication.length;
				freeInductionDecayShiftedWindowMultiplicationZeroFill = new Complex[dataSize];		    	
				System.arraycopy(freeInductionDecayShiftedWindowMultiplication, 0, freeInductionDecayShiftedWindowMultiplicationZeroFill, 0, dataSize);
				numberOfFourierPoints = dataSize;
			}
		}
		
//		// generate x-axis (delta [ppm])
//		double[] deltaAxisPPM = generateChemicalShiftAxis(brukerParameterMap);
		
		// Fourier transform, shift and flip the data
		Complex[] nmrSpectrumProcessed = fourierTransformNmrData(freeInductionDecayShiftedWindowMultiplicationZeroFill, dataShifter);

		return nmrSpectrumProcessed;
	}

	class BrukerVariableImporter {
		public double importBrukerVariable(double importedVariable, IScanNMR scanNMR, String variableName) {
			if (scanNMR.getHeaderDataMap().containsKey(variableName)) {
				importedVariable = Double.parseDouble(scanNMR.getHeaderData(variableName));
			} else {
				throw new IllegalStateException ("No Variable >>"+variableName+"<< detected!");
			}				
			return importedVariable;
		}

		public String importBrukerVariable(String importedVariable, IScanNMR scanNMR, String variableName) {
			if (scanNMR.getHeaderDataMap().containsKey(variableName)) {
				importedVariable = scanNMR.getHeaderData(variableName);
			} else {
				throw new IllegalStateException ("No Variable >>"+variableName+"<< detected!");
			}		
			return importedVariable;
		}

		public int importBrukerVariable(int importedVariable, IScanNMR scanNMR, String variableName) {
			if (scanNMR.getHeaderDataMap().containsKey(variableName)) {
				importedVariable = Integer.parseInt(scanNMR.getHeaderData(variableName));
			} else {
				throw new IllegalStateException ("No Variable >>"+variableName+"<< detected!");
			}				
			return importedVariable;
		}
	}
	
	private static double determineDigitalFilter(Map<String, Double> brukerParameterMap) {

		double[][] brukerDigitalFilter = {
				{2,44.750,46.000,46.311}, 
				{3,33.500,36.500,36.530}, 
				{4,66.625,48.000,47.870},
				{6,59.083,50.167,50.229},
				{8,68.563,53.250,53.289},
				{12,60.375,69.500,69.551},
				{16,69.531,72.250,71.600},
				{24,61.021,70.167,70.184},
				{32,70.016,72.750,72.138},
				{48,61.344,70.500,70.528},
				{64,70.258,73.000,72.348},
				{96,61.505,70.667,70.700},
				{128,70.379,72.500,72.524},
				{192,61.586,71.333,0},
				{256,70.439,72.250,0},
				{384,61.626,71.667,0},
				{512,70.470,72.125,0},
				{768,61.647,71.833,0},
				{1024,70.485,72.063,0},
				{1536,61.657,71.917,0},
				{2048,70.492,72.031,0}
		};
		//System.out.println(BrukerDigitalFilter[11][1]);

		int decimationFactorOfDigitalFilter = 1; //DECIM
		int decimationFactorOfDigitalFilterRow = 0;
		int dspFirmwareVersion = 1; //DSPFVS
		int dspFirmwareVersionRow = 0;
		double groupDelayOfDigitallyFilteredData = 0; // GRPDLY corresponds to digital shift
		//if (scanNMR.getHeaderDataMap().containsKey("acqus_GRPDLY")) {				
		if (brukerParameterMap.get("groupDelay") != -1){
			groupDelayOfDigitallyFilteredData = brukerParameterMap.get("groupDelay");
			//	}
		} else {
			if (brukerParameterMap.containsKey("decimationFactorOfDigitalFilter")){
				decimationFactorOfDigitalFilter = brukerParameterMap.get("decimationFactorOfDigitalFilter").intValue();
				//brukerParameterMap.put("decimationFactorOfDigitalFilter", (double)decimationFactorOfDigitalFilter);

				switch (decimationFactorOfDigitalFilter) {
					case 2:
						decimationFactorOfDigitalFilterRow = 0;
					case 3:
						decimationFactorOfDigitalFilterRow = 1;
					case 4:
						decimationFactorOfDigitalFilterRow = 2;
					case 6:
						decimationFactorOfDigitalFilterRow = 3;
					case 8:
						decimationFactorOfDigitalFilterRow = 4;
					case 12:
						decimationFactorOfDigitalFilterRow = 5;
					case 16:
						decimationFactorOfDigitalFilterRow = 6;
					case 24:
						decimationFactorOfDigitalFilterRow = 7;
					case 32:
						decimationFactorOfDigitalFilterRow = 8;
					case 48:
						decimationFactorOfDigitalFilterRow = 9;
					case 64:
						decimationFactorOfDigitalFilterRow = 10;
					case 96:
						decimationFactorOfDigitalFilterRow = 11;
					case 128:
						decimationFactorOfDigitalFilterRow = 12;
					case 192:
						decimationFactorOfDigitalFilterRow = 13;
					case 256:
						decimationFactorOfDigitalFilterRow = 14;
					case 384:
						decimationFactorOfDigitalFilterRow = 15;
					case 512:
						decimationFactorOfDigitalFilterRow = 16;
					case 768:
						decimationFactorOfDigitalFilterRow = 17;
					case 1024:
						decimationFactorOfDigitalFilterRow = 18;
					case 1536:
						decimationFactorOfDigitalFilterRow = 19;
					case 2048:
						decimationFactorOfDigitalFilterRow = 20;
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
			if (brukerParameterMap.containsKey("dspFirmwareVersion")){
				dspFirmwareVersion = brukerParameterMap.get("dspFirmwareVersion").intValue();
				//brukerParameterMap.put("dspFirmwareVersion", (double)dspFirmwareVersion);

				switch (dspFirmwareVersion) {
					case 10:
						dspFirmwareVersionRow = 1;
					case 11:
						dspFirmwareVersionRow = 2;
					case 12:
						dspFirmwareVersionRow = 3;
					default:
						// unknown value
						dspFirmwareVersion = 0;
						dspFirmwareVersionRow = 0;
				}
			} else {
				// no DSPFVS parameter in acqus
				dspFirmwareVersion = 0;						
			}
			if (decimationFactorOfDigitalFilterRow > 13 && dspFirmwareVersionRow == 3) {
				// unknown combination of DSPVFS and DECIM parameters
				decimationFactorOfDigitalFilter = 0;
				dspFirmwareVersion = 0;
			}
		}

		if (decimationFactorOfDigitalFilter == 0 && dspFirmwareVersion == 0) {
			// No digital filtering
			groupDelayOfDigitallyFilteredData = 0;
		} else if (decimationFactorOfDigitalFilter == 1 && dspFirmwareVersion == 1) {
			// digital filtering set by GRPDLY => do nothing
		} else {
			groupDelayOfDigitallyFilteredData = brukerDigitalFilter[decimationFactorOfDigitalFilterRow][dspFirmwareVersionRow];
		}

		if (brukerParameterMap.get("ProcessedDataFlag").equals(1.0)) {
			// processed data only
			groupDelayOfDigitallyFilteredData = 0;
		}

		// digital filter for further calculation
		groupDelayOfDigitallyFilteredData = Math.round(groupDelayOfDigitallyFilteredData);
		brukerParameterMap.put("groupDelayOfDigitallyFilteredData", groupDelayOfDigitallyFilteredData);

		return groupDelayOfDigitallyFilteredData;
	}
	
	private static double[] generateTimeScale(Map<String, Double> brukerParameterMap) {
		double minValTimescale = 0;
		double maxValTimescaleFactor=(brukerParameterMap.get("numberOfFourierPoints")/brukerParameterMap.get("numberOfPoints")); 
		double maxValTimescale = brukerParameterMap.get("brukerAT")*maxValTimescaleFactor; //linspace(0,NmrData.at*(NmrData.fn/NmrData.np),NmrData.fn);
		int timescalePoints = brukerParameterMap.get("numberOfFourierPoints").intValue();
		double[] timeScale = generateLinearlySpacedVector(minValTimescale, maxValTimescale, timescalePoints); // for ft-operation
		//else: double[] TimeScale = GenerateLinearlySpacedVector(minValTimescale, BrukerAT, TimescalePoints);
		return timeScale;
	}
	
	private static double[] generateLinearlySpacedVector(double minVal, double maxVal, int points) {
		double[] vector = new double[points];
		for (int i = 0; i < points; i++){
			vector[i] = minVal + (double)i/(points-1)*(maxVal-minVal);
		}
		return vector;  
	}  
	
	private static double[] exponentialApodizationFunction(double[] timeScale, Map<String, Double> brukerParameterMap) {

		double exponentialLineBroadeningFactor = 0;
		exponentialLineBroadeningFactor = brukerParameterMap.get("exponentialLineBroadeningFactor");
		double[] exponentialLineBroadening = new double[timeScale.length];
		double exponentialLineBroadenigTerm;
		if (exponentialLineBroadeningFactor > 0) {
			for (int i = 0; i < timeScale.length; i++) { //Lbfunc=exp(-Timescale'*pi*NmrData.lb);
				exponentialLineBroadenigTerm = (-timeScale[i]*Math.PI*exponentialLineBroadeningFactor);
				exponentialLineBroadening[i] = Math.exp(exponentialLineBroadenigTerm);
			}
		} else {
			for (int i = 0; i < timeScale.length; i++) {
				exponentialLineBroadening[i] = (timeScale[i]*0+1);
			}
		}
		return exponentialLineBroadening;
	}
	
	private static double[] gaussianApodizationFunction(double[] timeScale, Map<String, Double> brukerParameterMap) {

		double gaussianLineBroadeningFactor = 0;
		gaussianLineBroadeningFactor = brukerParameterMap.get("gaussianLineBroadeningFactor");
		double[] gaussianLineBroadening = new double[timeScale.length];
		double gaussianLineBroadenigTermA;
		double gaussianLineBroadenigTermB;
		if (gaussianLineBroadeningFactor > 0) {
			// gf=2*sqrt(log(2))/(pi*NmrData.gw);
			// Gwfunc=exp(-(Timescale'/gf).^2);
			gaussianLineBroadenigTermA = (Math.PI*gaussianLineBroadeningFactor);
			double gaussFactor = 2*Math.sqrt(Math.log(2))/gaussianLineBroadenigTermA;
			for (int i = 0; i < timeScale.length; i++) {
				gaussianLineBroadenigTermB = -(timeScale[i]/gaussFactor);
				gaussianLineBroadening[i] = Math.exp(Math.pow(gaussianLineBroadenigTermB, 2));
			}
		} else {
			for (int i = 0; i < timeScale.length; i++) {
				gaussianLineBroadening[i] = (timeScale[i]*0+1);
			}
		}
		return gaussianLineBroadening;
	}
	
	class ShiftNmrData {
		@SuppressWarnings("unused")
		private void leftShiftNMRData(int[] dataArray, int pointsToShift) {
			pointsToShift = pointsToShift % dataArray.length;
			while (pointsToShift-- > 0)
			{
				int tempArray = dataArray[0];
				for (int i = 1; i < dataArray.length; i++)
					dataArray[i - 1] = dataArray[i];
				dataArray[dataArray.length - 1] = tempArray;
			}
		}

		public int[] rightShiftNMRData(int[] dataArray, int pointsToShift) {
			for (int i = 0; i < pointsToShift; i++) {
				int tempArray = dataArray[dataArray.length - 1];
				for (int g = dataArray.length - 2; g > -1; g--) {
					dataArray[g + 1] = dataArray[g];
				}
				dataArray[0]=tempArray;				
			}
			return dataArray;
		}

		private void leftShiftNMRComplexData(Complex[] dataArray, int pointsToShift) {
			pointsToShift = pointsToShift % dataArray.length;
			while (pointsToShift-- > 0)
			{
				Complex tempArray = dataArray[0];
				for (int i = 1; i < dataArray.length; i++)
					dataArray[i - 1] = dataArray[i];
				dataArray[dataArray.length - 1] = tempArray;
			}
		}

		public Complex[] rightShiftNMRComplexData(Complex[] dataArray, int pointsToShift) {
			for (int i = 0; i < pointsToShift; i++) {
				Complex tempArray = dataArray[dataArray.length - 1];
				for (int g = dataArray.length - 2; g > -1; g--) {
					dataArray[g + 1] = dataArray[g];
				}
				dataArray[0]=tempArray;				
			}
			return dataArray;
		}
	}
	
	private static Complex[] directCurrentCorrectionFID(Complex[] complexSignals, Map<String, Double> brukerParameterMap) {

		// following as used in GNAT
		int numberOfFourierPoints = brukerParameterMap.get("numberOfFourierPoints").intValue();
		int numberOfPoints = brukerParameterMap.get("numberOfPoints").intValue();
		int directCurrentPointsTerm = 5*numberOfFourierPoints/20;
		int directCurrentPoints = Math.round(directCurrentPointsTerm);
		// select direct current correction for FID
		int directCurrentFID = 0; // 0 = No, 1 = Yes

		Complex[] freeInductionDecay = new Complex[complexSignals.length];
		Complex[] complexSignalsDCcopy = new Complex[complexSignals.length-directCurrentPoints];
		Complex complexSignalsDCcopyAverage = new Complex(0, 0);
		double[] complexSignalsReal = new double[complexSignals.length];
		double[] complexSignalsImag = new double[complexSignals.length];		    
		//for (int k = 1; k <= dataArrayDimension; k++) { // expand for nD dimensions
		if (numberOfFourierPoints >= numberOfPoints) {
			if (directCurrentFID > 0) {		    			
				complexSignalsDCcopy = Arrays.copyOfRange(complexSignals, directCurrentPoints, complexSignals.length);

				for (int i=0; i < complexSignalsDCcopy.length; i++) {
					complexSignalsReal[i] = (complexSignalsDCcopy[i].getReal());
					complexSignalsImag[i] = (complexSignalsDCcopy[i].getImaginary());
				}

				double realAverage = Arrays.stream(complexSignalsReal).average().getAsDouble();
				double imagAverage = Arrays.stream(complexSignalsImag).average().getAsDouble();

				complexSignalsDCcopyAverage = new Complex(realAverage, imagAverage);

				for (int i = 0; i < numberOfPoints; i++) {
					freeInductionDecay[i] = complexSignals[i].subtract(complexSignalsDCcopyAverage);
				}
			} else {		    			
				freeInductionDecay = Arrays.copyOfRange(complexSignals, 0, numberOfPoints);
			}
		} else {
			if (directCurrentFID > 0) {		    			
				complexSignalsDCcopy = Arrays.copyOfRange(complexSignals, directCurrentPoints, complexSignals.length);

				for (int i=0; i < complexSignalsDCcopy.length; i++) {
					complexSignalsReal[i] = (complexSignalsDCcopy[i].getReal());
					complexSignalsImag[i] = (complexSignalsDCcopy[i].getImaginary());
				}

				double realAverage = Arrays.stream(complexSignalsReal).average().getAsDouble();
				double imagAverage = Arrays.stream(complexSignalsImag).average().getAsDouble();

				complexSignalsDCcopyAverage = new Complex(realAverage, imagAverage);

				for (int i = 0; i < numberOfFourierPoints; i++) {
					freeInductionDecay[i] = complexSignals[i].subtract(complexSignalsDCcopyAverage);
				}
			} else {		    			
				freeInductionDecay = Arrays.copyOfRange(complexSignals, 0, numberOfFourierPoints);
			}
		}		    	
		//}
		return freeInductionDecay;
	}
	
//	public static double[] generateChemicalShiftAxis(Map<String, Double> brukerParameterMap) {
//		double doubleSize = brukerParameterMap.get("numberOfFourierPoints");
//		int deltaAxisPoints = (int) doubleSize;
//		double[] chemicalShiftAxis = new double[(int)doubleSize];			
//		double minValueDeltaAxis = brukerParameterMap.get("firstDataPointOffset");
//		double maxValueDeltaAxis = brukerParameterMap.get("sweepWidth")+brukerParameterMap.get("firstDataPointOffset");
//
//		chemicalShiftAxis = generateLinearlySpacedVector(minValueDeltaAxis, maxValueDeltaAxis, deltaAxisPoints);
//
//		return chemicalShiftAxis;
//	}
	
	private static Complex[] fourierTransformNmrData(Complex[] fid, ShiftNmrData dataShifter) {
		FastFourierTransformer fFourierTransformer = new FastFourierTransformer(DftNormalization.STANDARD);
		Complex[] nmrSpectrum = fFourierTransformer.transform(fid, TransformType.FORWARD);

		Complex[] nmrSpectrumProcessed = new Complex[nmrSpectrum.length];
		System.arraycopy(nmrSpectrum, 0, nmrSpectrumProcessed, 0, nmrSpectrum.length); //NmrData.SPECTRA
		dataShifter.rightShiftNMRComplexData(nmrSpectrumProcessed, nmrSpectrumProcessed.length/2);
		ArrayUtils.reverse(nmrSpectrumProcessed);
		return nmrSpectrumProcessed;			
	}
	
	
}

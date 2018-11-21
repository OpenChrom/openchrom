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

import org.apache.commons.math3.complex.Complex;
import org.eclipse.chemclipse.nmr.model.core.IScanNMR;
import org.eclipse.chemclipse.nmr.model.support.ISignalExtractor;
import org.eclipse.chemclipse.nmr.model.support.SignalExtractor;

import net.openchrom.nmr.processing.supplier.base.settings.support.ZERO_FILLING_FACTOR;

public class DigitalFilterRemoval {

	public Complex[] removeDigitalFilter(IScanNMR scanNMR, Complex[] FID) {

		FourierTransformationProcessor transform = new FourierTransformationProcessor();
		UtilityFunctions utilityFunction = new UtilityFunctions();
		ISignalExtractor signalExtractor = new SignalExtractor(scanNMR);
		//
		Complex[] freeInductionDecay = FID;
		Complex[] tempFID = new Complex[freeInductionDecay.length];
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
		//
		if(Math.abs(leftRotationFid) > 0.0) {
			// // save old size here
			// int originalFIDSize = freeInductionDecay.length;
			//
			Complex[] freeInductionDecayZeroFill = new Complex[freeInductionDecay.length];
			Complex[] filteredNMRSpectrum = null;
			//
			// automatic zero filling just in case size != 2^n
			int n = freeInductionDecay.length;
			int nextPower = (int)(Math.ceil((Math.log(n) / Math.log(2))));
			int previousPower = (int)(Math.floor(((Math.log(n) / Math.log(2)))));
			if(nextPower != previousPower) {
				// flag for used data
				double digitalFilterZeroFill = 1;
				scanNMR.putProcessingParameters("digitalFilterZeroFill", digitalFilterZeroFill);
				// zero filling
				double autoZeroFill = 1;
				scanNMR.putProcessingParameters("autoZeroFill", autoZeroFill);
				//
				double zeroFillingFactor = 0.0; // 0 = no action
				scanNMR.putProcessingParameters("zeroFillingFactor", zeroFillingFactor);
				//
				ZeroFilling zeroFiller = new ZeroFilling();
				freeInductionDecayZeroFill = zeroFiller.zerofill(signalExtractor, scanNMR, ZERO_FILLING_FACTOR.AUTO);
				// reset flags
				autoZeroFill = 0;
				scanNMR.putProcessingParameters("autoZeroFill", autoZeroFill);
				digitalFilterZeroFill = 0;
				scanNMR.putProcessingParameters("digitalFilterZeroFill", digitalFilterZeroFill);
				//
				filteredNMRSpectrum = transform.fourierTransformNmrData(freeInductionDecayZeroFill, utilityFunction);
			} else {
				// no ZF!
				double autoZeroFill = 0;
				scanNMR.putProcessingParameters("autoZeroFill", autoZeroFill);
				filteredNMRSpectrum = transform.fourierTransformNmrData(freeInductionDecay, utilityFunction);
			}
			// create filtered spectrum
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
			// ifft => revert to fid
			Complex[] tempUnfilteredSpectrum = transform.inverseFourierTransformData(unfilteredNMRSpectrum, utilityFunction);
			// remove temporary zero filling if necessary
			System.arraycopy(tempUnfilteredSpectrum, 0, tempFID, 0, tempFID.length);
			// save unfiltered fid
			int[] timeAxis = utilityFunction.generateTimeScale(scanNMR);
			signalExtractor.createScansFID(tempFID, timeAxis);
		} else {
			// no digital filter, no zero filling
			double autoZeroFill = 0;
			scanNMR.putProcessingParameters("autoZeroFill", autoZeroFill);
		}
		//
		return tempFID;
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
		//
		if(scanNMR.processingParametersContainsKey("groupDelay") && scanNMR.getProcessingParameters("groupDelay") != -1) {
			groupDelayOfDigitallyFilteredData = scanNMR.getProcessingParameters("groupDelay");
			// }
		} else {
			if(scanNMR.processingParametersContainsKey("decimationFactorOfDigitalFilter")) {
				decimationFactorOfDigitalFilter = scanNMR.getProcessingParameters("decimationFactorOfDigitalFilter").intValue();
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
						decimationFactorOfDigitalFilterRow = 97531; // Matlab => Double.POSITIVE_INFINITY;
				}
			} else {
				// no DECIM parameter in acqus
				decimationFactorOfDigitalFilter = 0;
				decimationFactorOfDigitalFilterRow = 666; // Matlab => Double.POSITIVE_INFINITY;
			}
			if(scanNMR.processingParametersContainsKey("dspFirmwareVersion")) {
				dspFirmwareVersion = scanNMR.getProcessingParameters("dspFirmwareVersion").intValue();
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
}

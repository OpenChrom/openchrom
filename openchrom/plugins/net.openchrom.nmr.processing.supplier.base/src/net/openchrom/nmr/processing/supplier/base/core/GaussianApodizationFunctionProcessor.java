/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jan Holy - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.core;

import org.apache.commons.math3.complex.Complex;
import org.eclipse.chemclipse.nmr.model.core.IScanNMR;
import org.eclipse.chemclipse.nmr.model.support.ISignalExtractor;
import org.eclipse.chemclipse.nmr.model.support.SignalExtractor;
import org.eclipse.chemclipse.nmr.processor.core.AbstractScanProcessor;
import org.eclipse.chemclipse.nmr.processor.core.IScanProcessor;
import org.eclipse.chemclipse.nmr.processor.settings.IProcessorSettings;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.nmr.processing.supplier.base.settings.GaussianApodizationSettings;

public class GaussianApodizationFunctionProcessor extends AbstractScanProcessor implements IScanProcessor {

	@Override
	public IProcessingInfo process(IScanNMR scanNMR, IProcessorSettings processorSettings, IProgressMonitor monitor) {

		IProcessingInfo processingInfo = validate(scanNMR, processorSettings);
		if(!processingInfo.hasErrorMessages()) {
			GaussianApodizationSettings settings = (GaussianApodizationSettings)processorSettings;
			ISignalExtractor signalExtractor = new SignalExtractor(scanNMR);
			double[] gaussianApodization = GaussianApodizationFunction(scanNMR, signalExtractor, settings);
			signalExtractor.setScansFIDCorrection(gaussianApodization, false);
			processingInfo.setProcessingResult(scanNMR);
		}
		return processingInfo;
	}

	private double[] GaussianApodizationFunction(IScanNMR scanNMR, ISignalExtractor signalExtractor, GaussianApodizationSettings gaussianApodizationSettings) {

		// Get Timescale
		double[] timeScale = signalExtractor.extractTimeFID();// generateTimeScale(scanNMR);
		for(int i = 0; i < timeScale.length; i++) {
			timeScale[i] = timeScale[i] / 1000000;
		}
		//
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
		/*
		 * check for raw / processed data
		 */
		UtilityFunctions utilityFunction = new UtilityFunctions();
		Complex[] tempFID = signalExtractor.extractIntesityFID();
		if(!scanNMR.getProcessingParameters("ProcessedDataFlag").equals(1.0)) {
			/*
			 * data after removal of digital filter or without digital filter; at this point data is equal
			 */
			double[] tempRealArray = new double[tempFID.length];
			for(int i = 0; i < tempFID.length; i++) {
				tempRealArray[i] = tempFID[i].getReal();
			}
			double tempFIDmin = utilityFunction.getMinValueOfArray(tempRealArray);
			double tempFIDmax = utilityFunction.getMaxValueOfArray(tempRealArray);
			//
			if(Math.abs(tempFIDmax) > Math.abs(tempFIDmin)) {
				// System.out.println("neg, *-1");
				// introduced "-"lineBroadeningWindwowFunction after refactoring the removal of dig. filter to flip spectrum up-down
				for(int i = 0; i < gaussianLineBroadening.length; i++) {
					gaussianLineBroadening[i] *= -1;
				}
			} else {
				// System.out.println("pos");
				// do nothing
			}
		} else {
			/*
			 * processed data; without removal of dig. filter
			 */
			for(int i = 0; i < gaussianLineBroadening.length; i++) {
				// do not apply apodization
				gaussianLineBroadening[i] = 1;
			}
		}
		//
		return gaussianLineBroadening;
	}
}

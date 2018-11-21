/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jan - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.core;

import org.eclipse.chemclipse.nmr.model.core.IScanNMR;
import org.eclipse.chemclipse.nmr.model.support.ISignalExtractor;
import org.eclipse.chemclipse.nmr.model.support.SignalExtractor;
import org.eclipse.chemclipse.nmr.processor.core.AbstractScanProcessor;
import org.eclipse.chemclipse.nmr.processor.core.IScanProcessor;
import org.eclipse.chemclipse.nmr.processor.settings.IProcessorSettings;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.nmr.processing.supplier.base.settings.ExponentialApodizationSettings;

public class ExponentialApodizationFunctionProcessor extends AbstractScanProcessor implements IScanProcessor {

	@Override
	public IProcessingInfo process(IScanNMR scanNMR, IProcessorSettings processorSettings, IProgressMonitor monitor) {

		IProcessingInfo processingInfo = validate(scanNMR, processorSettings);
		if(!processingInfo.hasErrorMessages()) {
			ExponentialApodizationSettings settings = (ExponentialApodizationSettings)processorSettings;
			ISignalExtractor signalExtractor = new SignalExtractor(scanNMR);
			double[] exponentialApodization = ExponentialApodizationFunction(scanNMR, signalExtractor, settings);
			signalExtractor.setScansFIDCorrection(exponentialApodization, false);
			processingInfo.setProcessingResult(scanNMR);
		}
		return processingInfo;
	}

	private double[] ExponentialApodizationFunction(IScanNMR scanNMR, ISignalExtractor signalExtractor, ExponentialApodizationSettings exponentialApodizationSettings) {

		// Get Timescale
		double[] timeScale = signalExtractor.extractTimeFID();// generateTimeScale(scanNMR);
		for(int i = 0; i < timeScale.length; i++) {
			timeScale[i] = timeScale[i] / 1000000;
		}
		//
		double exponentialLineBroadeningFactor = exponentialApodizationSettings.getExponentialLineBroadeningFactor();
		/*
		 * if(scanNMR.processingParametersContainsKey("exponentialLineBroadeningFactor")) {
		 * exponentialLineBroadeningFactor = scanNMR.getProcessingParameters("exponentialLineBroadeningFactor");
		 * }
		 */
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
		//
		return exponentialLineBroadening;
	}
}

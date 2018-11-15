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

import org.eclipse.chemclipse.nmr.model.core.IScanNMR;
import org.eclipse.chemclipse.nmr.model.support.ISignalExtractor;

public class WindowMultiplication {

	public double[] generateApodizationFunction(IScanNMR scanNMR, ISignalExtractor signalExtractor) {

		// Get Timescale
		double[] timeScale = signalExtractor.extractTimeFID();// generateTimeScale(scanNMR);
		for(int i = 0; i < timeScale.length; i++) {
			timeScale[i] = timeScale[i] / 1000000;
		}
		// exponential apodization; procs_LB is the correct parameter
		double[] exponentialLineBroadening = new double[timeScale.length];
		exponentialLineBroadening = exponentialApodizationFunction(timeScale, scanNMR);
		// gaussian apodization; procs_GB is the correct parameter
		double[] gaussianLineBroadening = new double[timeScale.length];
		gaussianLineBroadening = gaussianApodizationFunction(timeScale, scanNMR);
		// apodization window function; to be applied to the fid
		double[] lineBroadeningWindwowFunction = new double[timeScale.length];
		for(int i = 0; i < timeScale.length; i++) {
			lineBroadeningWindwowFunction[i] = gaussianLineBroadening[i] * exponentialLineBroadening[i]; // for ft data
		}
		return lineBroadeningWindwowFunction;
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
}

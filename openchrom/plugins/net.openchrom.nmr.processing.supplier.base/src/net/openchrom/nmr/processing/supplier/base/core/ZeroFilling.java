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

import org.apache.commons.math3.complex.Complex;
import org.eclipse.chemclipse.nmr.model.core.IScanNMR;
import org.eclipse.chemclipse.nmr.model.support.ISignalExtractor;
import org.eclipse.chemclipse.nmr.model.support.SignalExtractor;
import org.eclipse.chemclipse.nmr.processor.core.AbstractScanProcessor;
import org.eclipse.chemclipse.nmr.processor.core.IScanProcessor;
import org.eclipse.chemclipse.nmr.processor.settings.IProcessorSettings;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.nmr.processing.supplier.base.settings.ZeroFillingSettings;

public class ZeroFilling extends AbstractScanProcessor implements IScanProcessor {

	@Override
	public IProcessingInfo process(final IScanNMR scanNMR, final IProcessorSettings processorSettings, final IProgressMonitor monitor) {

		final IProcessingInfo processingInfo = validate(scanNMR, processorSettings);
		if(!processingInfo.hasErrorMessages()) {
			final ZeroFillingSettings settings = (ZeroFillingSettings)processorSettings;
			ISignalExtractor signalExtractor = new SignalExtractor(scanNMR);
			final Complex[] zerofilledSignals = zerofill(signalExtractor, scanNMR, settings);
			UtilityFunctions utilityFunction = new UtilityFunctions();
			int[] timeScale = utilityFunction.generateTimeScale(scanNMR);
			signalExtractor.createScansFID(zerofilledSignals, timeScale);
			processingInfo.setProcessingResult(scanNMR);
		}
		return processingInfo;
	}

	private Complex[] zerofill(ISignalExtractor signalExtractor, IScanNMR scanNMR, final ZeroFillingSettings settings) {

		Complex[] intesityFID = signalExtractor.extractIntesityFID();
		Complex[] zeroFilledFID = new Complex[signalExtractor.extractIntesityFID().length];
		//
		boolean autoZeroFill = false;
		if(scanNMR.getProcessingParameters("autoZeroFill").equals(1.0)) {
			autoZeroFill = true;
		} else {
			// no zero filling needed
		}
		//
		int newDataSize = 0;
		int zeroFillingFactor = 0;
		if(scanNMR.getProcessingParameters("zeroFillingFactor").equals(16.0)) {
			zeroFillingFactor = 16;
		} else if(scanNMR.getProcessingParameters("zeroFillingFactor").equals(32.0)) {
			zeroFillingFactor = 32;
		} else if(scanNMR.getProcessingParameters("zeroFillingFactor").equals(64.0)) {
			zeroFillingFactor = 64;
		} else {
			// default
		}
		//
		if(autoZeroFill) {
			// automatic zero filling
			//
			for(int i = 10; i < 17; i++) {
				int automaticSize = (int)Math.pow(2, i);
				if(automaticSize > intesityFID.length) {
					zeroFilledFID = new Complex[automaticSize];
					for(int j = 0; j < automaticSize; j++) {
						zeroFilledFID[j] = new Complex(0, 0);
					}
					int copySize = intesityFID.length;
					System.arraycopy(intesityFID, 0, zeroFilledFID, 0, copySize);
					double numberOfFourierPoints = automaticSize;
					scanNMR.putProcessingParameters("numberOfFourierPoints", numberOfFourierPoints);
					break;
				}
			}
		} else {
			// user defined zero filling
			//
			switch(zeroFillingFactor) {
				case 16:
					// 16k
					newDataSize = (int)Math.pow(2, 14);
					break;
				case 32:
					// 32k
					newDataSize = (int)Math.pow(2, 15);
					break;
				case 64:
					// 64k
					newDataSize = (int)Math.pow(2, 16);
					break;
				default:
					// do nothing
					newDataSize = intesityFID.length;
					break;
			}
			zeroFilledFID = new Complex[newDataSize];
			for(int i = 0; i < newDataSize; i++) {
				zeroFilledFID[i] = new Complex(0, 0);
			}
			int copySize = intesityFID.length;
			System.arraycopy(intesityFID, 0, zeroFilledFID, 0, copySize);
			double numberOfFourierPoints = newDataSize;
			scanNMR.putProcessingParameters("numberOfFourierPoints", numberOfFourierPoints);
		}
		return zeroFilledFID;
	}
}

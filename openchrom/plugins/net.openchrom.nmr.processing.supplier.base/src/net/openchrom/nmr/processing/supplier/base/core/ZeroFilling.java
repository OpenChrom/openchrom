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

public class ZeroFilling {

	public Complex[] zerofill(ISignalExtractor signalExtractor, IScanNMR scanNMR) {

		Complex[] intesityFID = null;
		if(scanNMR.getProcessingParameters("digitalFilterZeroFill").equals(1.0)) {
			intesityFID = signalExtractor.extractRawIntesityFID();
		} else {
			intesityFID = signalExtractor.extractIntesityFID();
		}
		Complex[] zeroFilledFID = new Complex[signalExtractor.extractRawIntesityFID().length];
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
			newDataSize = (int)Math.pow(2, (int)(Math.ceil((Math.log(intesityFID.length) / Math.log(2)))));
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
		}
		zeroFilledFID = new Complex[newDataSize];
		for(int i = 0; i < newDataSize; i++) {
			zeroFilledFID[i] = new Complex(0, 0);
		}
		int copySize = intesityFID.length;
		System.arraycopy(intesityFID, 0, zeroFilledFID, 0, copySize);
		double numberOfFourierPoints = newDataSize;
		scanNMR.putProcessingParameters("numberOfFourierPoints", numberOfFourierPoints);
		return zeroFilledFID;
	}
}

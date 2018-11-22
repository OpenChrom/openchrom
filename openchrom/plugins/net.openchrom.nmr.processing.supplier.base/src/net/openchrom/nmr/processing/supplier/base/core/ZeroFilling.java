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

import net.openchrom.nmr.processing.supplier.base.settings.support.ZERO_FILLING_FACTOR;

public class ZeroFilling {

	public Complex[] zerofill(ISignalExtractor signalExtractor, IScanNMR scanNMR, ZERO_FILLING_FACTOR zeroFillingFactor) {

		Complex[] intesityFID = null;
		// if(scanNMR.getProcessingParameters("digitalFilterZeroFill").equals(1.0)) {
		// intesityFID = signalExtractor.extractRawIntesityFID();
		// } else {
		intesityFID = signalExtractor.extractIntesityFID();
		// }
		Complex[] zeroFilledFID = new Complex[signalExtractor.extractRawIntesityFID().length];
		//
		int newDataSize = 0;
		int zeroFillingSize = zeroFillingFactor.getValue();
		if(zeroFillingFactor.equals(ZERO_FILLING_FACTOR.AUTO) || zeroFillingSize < zeroFilledFID.length) {
			newDataSize = (int)Math.pow(2, (int)(Math.ceil((Math.log(intesityFID.length) / Math.log(2)))));
		} else {
			newDataSize = zeroFillingSize;
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

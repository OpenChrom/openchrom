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
import org.eclipse.chemclipse.nmr.model.core.IMeasurementNMR;

import net.openchrom.nmr.processing.supplier.base.settings.support.ZeroFillingFactor;

public class ZeroFilling {

	public Complex[] zerofill(Complex[] intesityFID, IMeasurementNMR measurementNMR, ZeroFillingFactor zeroFillingFactor) {

		// }
		Complex[] zeroFilledFID;
		//
		int newDataSize = 0;
		int zeroFillingSize = zeroFillingFactor.getValue();
		if(measurementNMR.getHeaderDataMap().get("ProcessedDataFlag").equalsIgnoreCase("true")) {
			/*
			 * For processed data it must be ensured that the original data size is restored!
			 */
			newDataSize = Integer.parseInt(measurementNMR.getHeaderDataMap().get("proc_SI"));
		} else if(zeroFillingFactor.equals(ZeroFillingFactor.AUTO) || zeroFillingSize < intesityFID.length) {
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
		measurementNMR.putProcessingParameters("numberOfFourierPoints", numberOfFourierPoints);
		return zeroFilledFID;
	}
}

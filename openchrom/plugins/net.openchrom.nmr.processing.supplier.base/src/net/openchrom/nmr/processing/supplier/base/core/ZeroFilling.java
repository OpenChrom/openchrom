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
 * Christoph Läubrich - add general purpose static filling function
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.core;

import java.util.Arrays;

import org.apache.commons.math3.complex.Complex;
import org.eclipse.chemclipse.nmr.model.core.MeasurementNMR;

import net.openchrom.nmr.processing.supplier.base.settings.support.ZeroFillingFactor;

public class ZeroFilling {

	public Complex[] zerofill(Complex[] intesityFID, MeasurementNMR measurementNMR, ZeroFillingFactor zeroFillingFactor) {

		// }
		Complex[] zeroFilledFID;
		//
		int newDataSize = 0;
		int zeroFillingSize = zeroFillingFactor.getValue();
		if(Boolean.parseBoolean(measurementNMR.getHeaderDataMap().get("ProcessedDataFlag"))) {
			/*
			 * For processed data it must be ensured that the original data size is restored!
			 */
			newDataSize = measurementNMR.getProcessingParameters("sizeofRealSpectrum").intValue();
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

	public static Complex[] fill(Complex[] signals) {

		if(signals == null) {
			throw new IllegalArgumentException("Signals can't be null");
		}
		if(signals.length == 0) {
			throw new IllegalArgumentException("Signals can't be empty");
		}
		int newLength = Math.max(2, (int)Math.pow(2, (int)(Math.ceil((Math.log(signals.length) / Math.log(2))))));
		if(newLength == signals.length) {
			return signals;
		}
		Complex[] copyOf = Arrays.copyOf(signals, newLength);
		Arrays.fill(copyOf, signals.length, newLength, Complex.ZERO);
		return copyOf;
	}
}

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

import net.openchrom.nmr.processing.supplier.base.settings.support.ZeroFillingFactor;

public class ZeroFilling {

	public static Complex[] fill(Complex[] signals) {

		return fill(signals, ZeroFillingFactor.AUTO);
	}

	public static Complex[] fill(Complex[] signals, ZeroFillingFactor factor) {

		if(signals == null) {
			throw new IllegalArgumentException("Signals can't be null");
		}
		if(signals.length == 0) {
			throw new IllegalArgumentException("Signals can't be empty");
		}
		int lowerBound = factor == ZeroFillingFactor.AUTO ? 2 : factor.getValue();
		int newLength = Math.max(lowerBound, (int)Math.pow(lowerBound, (int)(Math.ceil((Math.log(signals.length) / Math.log(lowerBound))))));
		if(newLength == signals.length) {
			return signals;
		}
		Complex[] copyOf = Arrays.copyOf(signals, newLength);
		Arrays.fill(copyOf, signals.length, newLength, Complex.ZERO);
		return copyOf;
	}
}

/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christoph Läubrich - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.core;

import java.io.Serializable;

import org.apache.commons.math3.complex.Complex;
import org.eclipse.chemclipse.nmr.model.core.SpectrumSignal;

public final class ComplexSpectrumSignal implements SpectrumSignal, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -3361359307799587392L;
	private Complex complex;
	private Number shift;

	public ComplexSpectrumSignal(Number shift, Complex complex) {
		this.shift = shift;
		this.complex = complex;
	}

	@Override
	public Number getChemicalShift() {

		return shift;
	}

	@Override
	public Number getAbsorptiveIntensity() {

		return complex.getReal();
	}

	@Override
	public Number getDispersiveIntensity() {

		return complex.getImaginary();
	}
}
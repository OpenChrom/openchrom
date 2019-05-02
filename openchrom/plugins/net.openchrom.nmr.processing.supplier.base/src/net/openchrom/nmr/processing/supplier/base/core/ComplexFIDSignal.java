/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexander Stark - initial API and implementation
 * Alexander Kerner - serialization
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.core;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.math3.complex.Complex;
import org.eclipse.chemclipse.nmr.model.core.FIDSignal;

public class ComplexFIDSignal implements FIDSignal, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1324819102443373298L;
	private Complex complex;
	private BigDecimal time;

	public ComplexFIDSignal(BigDecimal time, Complex complex) {
		this.time = time;
		this.complex = complex;
	}

	@Override
	public BigDecimal getSignalTime() {

		return time;
	}

	@Override
	public Number getRealComponent() {

		return complex.getReal();
	}

	@Override
	public Number getImaginaryComponent() {

		return complex.getImaginary();
	}
}
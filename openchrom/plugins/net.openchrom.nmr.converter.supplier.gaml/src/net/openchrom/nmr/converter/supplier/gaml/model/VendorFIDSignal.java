/*******************************************************************************
 * Copyright (c) 2021 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.converter.supplier.gaml.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.eclipse.chemclipse.nmr.model.core.FIDSignal;

public class VendorFIDSignal implements FIDSignal, Serializable {

	private static final long serialVersionUID = 1L;
	private Double time;
	private Double real;
	private Double imaginary;

	public VendorFIDSignal(Double time, Double real, Double imaginary) {

		this.time = time;
		this.real = real;
		this.imaginary = imaginary;
	}

	@Override
	public BigDecimal getSignalTime() {

		return new BigDecimal(time);
	}

	@Override
	public Number getRealComponent() {

		return real;
	}

	@Override
	public Number getImaginaryComponent() {

		return imaginary;
	}
}
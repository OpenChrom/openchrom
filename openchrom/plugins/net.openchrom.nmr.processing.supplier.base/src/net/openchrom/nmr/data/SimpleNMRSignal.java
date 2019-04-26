/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 * Alexander Stark - initial API and implementation
 * Christoph Läubrich - changes for new processor api and process optimizations
 * Alexander Kerner - serialization
 *******************************************************************************/

package net.openchrom.nmr.data;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.math.BigDecimal;

import org.eclipse.chemclipse.nmr.model.core.SpectrumSignal;

final class SimpleNMRSignal implements SpectrumSignal, Externalizable {

	private static final long serialVersionUID = -8928811371157406560L;
	private Number chemicalShift;
	private Number real;
	private Number imaginary;

	public SimpleNMRSignal(Number chemicalShift, Number real, Number imaginary, BigDecimal scalingFactor) {
		this.chemicalShift = chemicalShift;
		if (scalingFactor == null) {
			this.real = real;
			this.imaginary = imaginary;
		} else {
			this.real = scaleData(real, scalingFactor);
			this.imaginary = scaleData(imaginary, scalingFactor);
		}
	}

	/**
	 * No-arg for serialization
	 */
	public SimpleNMRSignal() {

	}

	private static Number scaleData(Number raw, Number scalingFactor) {

		return BigDecimal.valueOf(raw.doubleValue()).multiply(BigDecimal.valueOf(scalingFactor.doubleValue()));
	}

	@Override
	public Number getChemicalShift() {

		return chemicalShift;
	}

	@Override
	public Number getAbsorptiveIntensity() {

		return real;
	}

	@Override
	public Number getDispersiveIntensity() {

		return imaginary;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(chemicalShift);
		out.writeObject(real);
		out.writeObject(imaginary);

	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		this.chemicalShift = (Number) in.readObject();
		this.real = (Number) in.readObject();
		this.imaginary = (Number) in.readObject();

	}
}
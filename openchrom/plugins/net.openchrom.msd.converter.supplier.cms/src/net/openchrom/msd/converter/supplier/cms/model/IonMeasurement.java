/*******************************************************************************
 * Copyright (c) 2016, 2017 Walter Whitlock.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Walter Whitlock - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cms.model;

public class IonMeasurement implements IIonMeasurement {

	private double mz;
	private float signal;

	public IonMeasurement(double mz, float signal) {
		this.mz = mz;
		this.signal = signal;
	}

	// -----------------------------Comparable<IonMeasurement>
	/**
	 * Compares the MZ of two ion measurements. Returns the
	 * following values: a.compareTo(b) 0 a == b : 28 == 28 -1 a < b : 18 < 28
	 * +1 a > b : 28 > 18
	 */
	@Override
	public int compareTo(IIonMeasurement other) {

		return (int)(this.mz - other.getMZ()); // overrides AbstractIon.compareTo(IIon)
	}

	@Override
	public double getMZ() {

		return mz;
	}

	@Override
	public float getSignal() {

		return signal;
	}

	@Override
	public void setSignal(float signal) {

		this.signal = signal;
	}
}

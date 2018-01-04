/*******************************************************************************
 * Copyright (c) 2016, 2018 Walter Whitlock.
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
	/**
	 * returns true if mass is within +/- tol of mz value
	 */
	public boolean massEqual(double mass, double tol) {

		double diff;
		diff = this.mz - mass;
		if(tol > java.lang.StrictMath.abs(diff)) {
			return true;
		}
		return false;
	}

	@Override
	/**
	 * returns true if mass is less than (mz-tol)
	 */
	public boolean massLess(double mass, double tol) {

		double diff;
		diff = this.mz - mass;
		if(-tol > diff) {
			return true;
		}
		return false;
	}

	@Override
	public void setSignal(float signal) {

		this.signal = signal;
	}
}

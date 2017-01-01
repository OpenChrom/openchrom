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

	public double getMZ() {

		return mz;
	}

	public float getSignal() {

		return signal;
	}

	public void setSignal(float signal) {

		this.signal = signal;
	}
}

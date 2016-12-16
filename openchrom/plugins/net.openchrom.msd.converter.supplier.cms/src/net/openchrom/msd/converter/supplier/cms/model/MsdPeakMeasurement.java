/*******************************************************************************
 * Copyright (c) 2016 whitlow.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * whitlow - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cms.model;

public class MsdPeakMeasurement implements IMsdPeakMeasurement {

	private double peakmz;
	private float peaksignal;

	public MsdPeakMeasurement(double mz, float signal) {
		peakmz = mz;
		peaksignal = signal;
	}

	public double getMZ() {

		return peakmz;
	}

	public float getSignal() {

		return peaksignal;
	}

	public void setSignal(float sig) {

		peaksignal = sig;
	}
}

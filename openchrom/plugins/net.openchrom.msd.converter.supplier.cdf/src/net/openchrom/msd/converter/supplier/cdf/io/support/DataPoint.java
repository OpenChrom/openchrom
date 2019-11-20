/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cdf.io.support;

public class DataPoint {

	private int mz;
	private double intensity;

	public DataPoint(int mz, double intensity) {
		this.mz = mz;
		this.intensity = intensity;
	}

	public int getMz() {

		return mz;
	}

	public void setMz(int mz) {

		this.mz = mz;
	}

	public double getIntensity() {

		return intensity;
	}

	public void setIntensity(double intensity) {

		this.intensity = intensity;
	}
}

/*******************************************************************************
 * Copyright (c) 2019, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
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

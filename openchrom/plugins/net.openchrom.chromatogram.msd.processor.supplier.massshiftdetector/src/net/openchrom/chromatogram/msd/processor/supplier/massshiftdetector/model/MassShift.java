/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model;

public class MassShift {

	private double mz;
	private int massShiftLevel;
	private double uncertainty;

	public MassShift(double mz, int massShiftLevel, double uncertainty) {
		this.mz = mz;
		this.massShiftLevel = massShiftLevel;
		this.uncertainty = uncertainty;
	}

	public double getMz() {

		return mz;
	}

	public int getMassShiftLevel() {

		return massShiftLevel;
	}

	public double getUncertainty() {

		return uncertainty;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(uncertainty);
		result = prime * result + (int)(temp ^ (temp >>> 32));
		result = prime * result + massShiftLevel;
		temp = Double.doubleToLongBits(mz);
		result = prime * result + (int)(temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		MassShift other = (MassShift)obj;
		if(Double.doubleToLongBits(uncertainty) != Double.doubleToLongBits(other.uncertainty))
			return false;
		if(massShiftLevel != other.massShiftLevel)
			return false;
		if(Double.doubleToLongBits(mz) != Double.doubleToLongBits(other.mz))
			return false;
		return true;
	}

	@Override
	public String toString() {

		return "MassShift [mz=" + mz + ", massShiftLevel=" + massShiftLevel + ", uncertainty=" + uncertainty + "]";
	}
}
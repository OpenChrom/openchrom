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
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.v1000;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.IMassShift;

public class MassShift_v1000 implements IMassShift {

	@XmlElement(name = "MZ")
	private double mz;
	@XmlElement(name = "RetentionTimeReference")
	private int retentionTimeReference;
	@XmlElement(name = "RetentionTimeIsotope")
	private int retentionTimeIsotope;
	@XmlElement(name = "MassShiftLevel")
	private int massShiftLevel;
	@XmlElement(name = "Certainty")
	private double certainty;

	public MassShift_v1000() {
	}

	public MassShift_v1000(double mz, int massShiftLevel, double certainty) {
		this.mz = mz;
		this.massShiftLevel = massShiftLevel;
		this.certainty = certainty;
	}

	@Override
	@XmlTransient
	public double getMz() {

		return mz;
	}

	@Override
	public void setMz(double mz) {

		this.mz = mz;
	}

	@Override
	@XmlTransient
	public int getRetentionTimeReference() {

		return retentionTimeReference;
	}

	@Override
	public void setRetentionTimeReference(int retentionTimeReference) {

		this.retentionTimeReference = retentionTimeReference;
	}

	@Override
	@XmlTransient
	public int getRetentionTimeIsotope() {

		return retentionTimeIsotope;
	}

	@Override
	public void setRetentionTimeIsotope(int retentionTimeIsotope) {

		this.retentionTimeIsotope = retentionTimeIsotope;
	}

	@Override
	@XmlTransient
	public int getMassShiftLevel() {

		return massShiftLevel;
	}

	@Override
	public void setMassShiftLevel(int massShiftLevel) {

		this.massShiftLevel = massShiftLevel;
	}

	@Override
	@XmlTransient
	public double getCertainty() {

		return certainty;
	}

	@Override
	public void setCertainty(double certainty) {

		this.certainty = certainty;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(certainty);
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
		MassShift_v1000 other = (MassShift_v1000)obj;
		if(Double.doubleToLongBits(certainty) != Double.doubleToLongBits(other.certainty))
			return false;
		if(massShiftLevel != other.massShiftLevel)
			return false;
		if(Double.doubleToLongBits(mz) != Double.doubleToLongBits(other.mz))
			return false;
		return true;
	}

	@Override
	public String toString() {

		return "MassShift [mz=" + mz + ", massShiftLevel=" + massShiftLevel + ", certainty=" + certainty + "]";
	}
}
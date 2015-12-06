/*******************************************************************************
 *  Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.jmgf.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.bioutils.proteomics.peak.Peak;
import net.sf.jmgf.MGFElement;
import net.sf.kerner.utils.collections.UtilCollection;

public class MGFElementBean implements MGFElement {

	private String charge;
	private List<Peak> peaks;
	private double pepMass;
	private int retentionTimeInSeconds;
	private String title;

	public MGFElementBean() {

	}

	public MGFElementBean(final String title, final String charge, final double pepMass, final int retTimeSecs, final List<? extends Peak> peaks) {

		this.title = title;
		this.charge = charge;
		this.peaks = new ArrayList<Peak>(peaks);
		retentionTimeInSeconds = retTimeSecs;
		this.pepMass = pepMass;
	}

	@Override
	public boolean equals(final Object obj) {

		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(!(obj instanceof MGFElementBean))
			return false;
		final MGFElementBean other = (MGFElementBean)obj;
		if(charge == null) {
			if(other.charge != null)
				return false;
		} else if(!charge.equals(other.charge))
			return false;
		if(peaks == null) {
			if(other.peaks != null)
				return false;
		} else if(!peaks.equals(other.peaks))
			return false;
		if(Double.doubleToLongBits(pepMass) != Double.doubleToLongBits(other.pepMass))
			return false;
		if(retentionTimeInSeconds != other.retentionTimeInSeconds)
			return false;
		return true;
	}

	public String getCharge() {

		return charge;
	}

	public List<Peak> getPeaks() {

		return peaks;
	}

	public double getPepMass() {

		return pepMass;
	}

	public int getRetentionTimeInSeconds() {

		return retentionTimeInSeconds;
	}

	public String getTitle() {

		return title;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((charge == null) ? 0 : charge.hashCode());
		result = prime * result + ((peaks == null) ? 0 : peaks.hashCode());
		long temp;
		temp = Double.doubleToLongBits(pepMass);
		result = prime * result + (int)(temp ^ (temp >>> 32));
		result = prime * result + retentionTimeInSeconds;
		return result;
	}

	public void setCharge(final String charge) {

		this.charge = charge;
	}

	public void setPeaks(final List<Peak> peaks) {

		this.peaks = peaks;
	}

	public void setPepMass(final double pepMass) {

		this.pepMass = pepMass;
	}

	public void setRetentionTimeInSeconds(final int retentionTimeInSeconds) {

		this.retentionTimeInSeconds = retentionTimeInSeconds;
	}

	public void setTitle(final String title) {

		this.title = title;
	}

	@Override
	public String toString() {

		return getTitle() + UtilCollection.toString(getPeaks());
	}
}

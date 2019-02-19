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
package net.openchrom.xxd.process.supplier.templates.model;

public class IntegratorSetting {

	public static final String INTEGRATOR_NAME_TRAPEZOID = "Trapezoid";
	public static final String INTEGRATOR_ID_TRAPEZOID = "org.eclipse.chemclipse.chromatogram.xxd.integrator.supplier.trapezoid.peakIntegrator";
	public static final String INTEGRATOR_NAME_MAX = "Max";
	public static final String INTEGRATOR_ID_MAX = "org.eclipse.chemclipse.chromatogram.msd.integrator.supplier.peakmax.peakIntegrator";
	//
	private double startRetentionTime = 0.0d; // Minutes
	private double stopRetentionTime = 0.0d; // Minutes
	private String identifier = "";
	private String integrator = "";

	public void copyFrom(IntegratorSetting setting) {

		if(setting != null) {
			setStartRetentionTime(setting.getStartRetentionTime());
			setStopRetentionTime(setting.getStopRetentionTime());
			setIdentifier(setting.getIdentifier());
			setIntegrator(setting.getIntegrator());
		}
	}

	public double getStartRetentionTime() {

		return startRetentionTime;
	}

	public void setStartRetentionTime(double startRetentionTime) {

		this.startRetentionTime = startRetentionTime;
	}

	public double getStopRetentionTime() {

		return stopRetentionTime;
	}

	public void setStopRetentionTime(double stopRetentionTime) {

		this.stopRetentionTime = stopRetentionTime;
	}

	public String getIdentifier() {

		return identifier;
	}

	public void setIdentifier(String identifier) {

		this.identifier = identifier;
	}

	public String getIntegrator() {

		return integrator;
	}

	public void setIntegrator(String integrator) {

		this.integrator = integrator;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
		long temp;
		temp = Double.doubleToLongBits(startRetentionTime);
		result = prime * result + (int)(temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(stopRetentionTime);
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
		IntegratorSetting other = (IntegratorSetting)obj;
		if(identifier == null) {
			if(other.identifier != null)
				return false;
		} else if(!identifier.equals(other.identifier))
			return false;
		if(Double.doubleToLongBits(startRetentionTime) != Double.doubleToLongBits(other.startRetentionTime))
			return false;
		if(Double.doubleToLongBits(stopRetentionTime) != Double.doubleToLongBits(other.stopRetentionTime))
			return false;
		return true;
	}

	@Override
	public String toString() {

		return "IntegratorSetting [startRetentionTime=" + startRetentionTime + ", stopRetentionTime=" + stopRetentionTime + ", identifier=" + identifier + ", integrator=" + integrator + "]";
	}
}

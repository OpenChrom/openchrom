/*******************************************************************************
 * Copyright (c) 2018, 2019 Lablicate GmbH.
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

public class AssignerStandard {

	private double startRetentionTime = 0.0d; // Minutes
	private double stopRetentionTime = 0.0d; // Minutes
	private String name = "";
	private double concentration = 0.0d;
	private String concentrationUnit = "";
	private double responseFactor = 1.0d;

	public void copyFrom(AssignerStandard setting) {

		if(setting != null) {
			setStartRetentionTime(setting.getStartRetentionTime());
			setStopRetentionTime(setting.getStopRetentionTime());
			setName(setting.getName());
			setConcentration(setting.getConcentration());
			setConcentrationUnit(setting.getConcentrationUnit());
			setResponseFactor(setting.getResponseFactor());
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

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public double getConcentration() {

		return concentration;
	}

	public void setConcentration(double concentration) {

		this.concentration = concentration;
	}

	public String getConcentrationUnit() {

		return concentrationUnit;
	}

	public void setConcentrationUnit(String concentrationUnit) {

		this.concentrationUnit = concentrationUnit;
	}

	public double getResponseFactor() {

		return responseFactor;
	}

	public void setResponseFactor(double responseFactor) {

		this.responseFactor = responseFactor;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		AssignerStandard other = (AssignerStandard)obj;
		if(name == null) {
			if(other.name != null)
				return false;
		} else if(!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {

		return "AssignerStandard [name=" + name + ", startRetentionTime=" + startRetentionTime + ", stopRetentionTime=" + stopRetentionTime + ", concentration=" + concentration + ", concentrationUnit=" + concentrationUnit + ", responseFactor=" + responseFactor + "]";
	}
}

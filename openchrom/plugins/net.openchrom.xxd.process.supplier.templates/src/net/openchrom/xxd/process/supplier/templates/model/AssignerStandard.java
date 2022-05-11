/*******************************************************************************
 * Copyright (c) 2018, 2022 Lablicate GmbH.
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

import java.util.Objects;

public class AssignerStandard extends AbstractSetting {

	private String name = "";
	private double concentration = 0.0d;
	private String concentrationUnit = "";
	private double responseFactor = 1.0d;
	private String tracesIdentification = "";

	public void copyFrom(AssignerStandard setting) {

		if(setting != null) {
			setPositionStart(setting.getPositionStart());
			setPositionStop(setting.getPositionStop());
			setPositionDirective(setting.getPositionDirective());
			setName(setting.getName());
			setConcentration(setting.getConcentration());
			setConcentrationUnit(setting.getConcentrationUnit());
			setResponseFactor(setting.getResponseFactor());
			setTracesIdentification(setting.getTracesIdentification());
		}
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

	public String getTracesIdentification() {

		return tracesIdentification;
	}

	public void setTracesIdentification(String tracesIdentification) {

		this.tracesIdentification = tracesIdentification;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(name);
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if(this == obj)
			return true;
		if(!super.equals(obj))
			return false;
		if(getClass() != obj.getClass())
			return false;
		AssignerStandard other = (AssignerStandard)obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {

		return "AssignerStandard [name=" + name + "]";
	}
}
/*******************************************************************************
 * Copyright (c) 2019, 2022 Lablicate GmbH.
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

public class CompensationSetting extends AbstractSetting {

	private String name = "";
	private String internalStandard = "";
	private double expectedConcentration = 0.0d;
	private String concentrationUnit = "";
	private String targetUnit = ""; // Could be also empty to match all units.
	private boolean adjustQuantitationEntry = false;

	public void copyFrom(CompensationSetting setting) {

		if(setting != null) {
			setName(setting.getName());
			setInternalStandard(setting.getInternalStandard());
			setExpectedConcentration(setting.getExpectedConcentration());
			setConcentrationUnit(setting.getConcentrationUnit());
			setTargetUnit(setting.getTargetUnit());
			setAdjustQuantitationEntry(adjustQuantitationEntry);
		}
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getInternalStandard() {

		return internalStandard;
	}

	public void setInternalStandard(String internalStandard) {

		this.internalStandard = internalStandard;
	}

	public double getExpectedConcentration() {

		return expectedConcentration;
	}

	public void setExpectedConcentration(double expectedConcentration) {

		this.expectedConcentration = expectedConcentration;
	}

	public String getConcentrationUnit() {

		return concentrationUnit;
	}

	public void setConcentrationUnit(String concentrationUnit) {

		this.concentrationUnit = concentrationUnit;
	}

	public String getTargetUnit() {

		return targetUnit;
	}

	public void setTargetUnit(String targetUnit) {

		this.targetUnit = targetUnit;
	}

	public boolean isAdjustQuantitationEntry() {

		return adjustQuantitationEntry;
	}

	public void setAdjustQuantitationEntry(boolean adjustQuantitationEntry) {

		this.adjustQuantitationEntry = adjustQuantitationEntry;
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

		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(getClass() != obj.getClass()) {
			return false;
		}
		CompensationSetting other = (CompensationSetting)obj;
		if(name == null) {
			if(other.name != null) {
				return false;
			}
		} else if(!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {

		return "CompensationSetting [name=" + name + ", internalStandard=" + internalStandard + ", expectedConcentration=" + expectedConcentration + ", concentrationUnit=" + concentrationUnit + ", targetUnit=" + targetUnit + ", adjustQuantitationEntry=" + adjustQuantitationEntry + "]";
	}
}
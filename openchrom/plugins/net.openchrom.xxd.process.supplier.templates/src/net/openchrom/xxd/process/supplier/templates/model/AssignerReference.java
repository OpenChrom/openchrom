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

public class AssignerReference extends AbstractSetting {

	private String internalStandard = ""; // Target ISTD
	private String identifier = ""; // Source Identifier

	public void copyFrom(AssignerReference setting) {

		if(setting != null) {
			setStartRetentionTime(setting.getStartRetentionTime());
			setStopRetentionTime(setting.getStopRetentionTime());
			setInternalStandard(setting.getInternalStandard());
			setIdentifier(setting.getIdentifier());
		}
	}

	public String getInternalStandard() {

		return internalStandard;
	}

	public void setInternalStandard(String internalStandard) {

		this.internalStandard = internalStandard;
	}

	public String getIdentifier() {

		return identifier;
	}

	public void setIdentifier(String identifier) {

		this.identifier = identifier;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
		result = prime * result + ((internalStandard == null) ? 0 : internalStandard.hashCode());
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
		AssignerReference other = (AssignerReference)obj;
		if(identifier == null) {
			if(other.identifier != null)
				return false;
		} else if(!identifier.equals(other.identifier))
			return false;
		if(internalStandard == null) {
			if(other.internalStandard != null)
				return false;
		} else if(!internalStandard.equals(other.internalStandard))
			return false;
		return true;
	}

	@Override
	public String toString() {

		return "AssignerReference [startRetentionTime=" + getStartRetentionTime() + ", stopRetentionTime=" + getStopRetentionTime() + ", internalStandard=" + internalStandard + ", identifier=" + identifier + "]";
	}
}

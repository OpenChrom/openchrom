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

public class AssignerReference extends AbstractSetting {

	private String internalStandard = ""; // Target ISTD
	private String identifier = ""; // Source Identifier

	public void copyFrom(AssignerReference setting) {

		if(setting != null) {
			setPositionStart(setting.getPositionStart());
			setPositionStop(setting.getPositionStop());
			setPositionDirective(setting.getPositionDirective());
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
		int result = super.hashCode();
		result = prime * result + Objects.hash(identifier, internalStandard);
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
		AssignerReference other = (AssignerReference)obj;
		return Objects.equals(identifier, other.identifier) && Objects.equals(internalStandard, other.internalStandard);
	}

	@Override
	public String toString() {

		return "AssignerReference [internalStandard=" + internalStandard + ", identifier=" + identifier + "]";
	}
}
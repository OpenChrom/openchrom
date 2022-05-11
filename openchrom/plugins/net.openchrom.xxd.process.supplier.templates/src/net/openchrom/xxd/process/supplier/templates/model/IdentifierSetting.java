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

public class IdentifierSetting extends AbstractSetting {

	private String name = "";
	private String casNumber = "";
	private String comments = "";
	private String contributor = "";
	private String reference = "";
	private String traces = "";
	private String referenceIdentifier = ""; // Used for relative retention time

	public void copyFrom(IdentifierSetting setting) {

		if(setting != null) {
			setPositionStart(setting.getPositionStart());
			setPositionStop(setting.getPositionStop());
			setPositionDirective(setting.getPositionDirective());
			setName(setting.getName());
			setCasNumber(setting.getCasNumber());
			setComments(setting.getComments());
			setContributor(setting.getContributor());
			setReference(setting.getReference());
			setTraces(setting.getTraces());
			setReferenceIdentifier(setting.getReferenceIdentifier());
		}
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getCasNumber() {

		return casNumber;
	}

	public void setCasNumber(String casNumber) {

		this.casNumber = casNumber;
	}

	public String getComments() {

		return comments;
	}

	public void setComments(String comments) {

		this.comments = comments;
	}

	public String getContributor() {

		return contributor;
	}

	public void setContributor(String contributor) {

		this.contributor = contributor;
	}

	public String getReference() {

		return reference;
	}

	public void setReference(String reference) {

		this.reference = reference;
	}

	public String getTraces() {

		return traces;
	}

	public void setTraces(String traces) {

		this.traces = traces;
	}

	public String getReferenceIdentifier() {

		return referenceIdentifier;
	}

	public void setReferenceIdentifier(String referenceIdentifier) {

		this.referenceIdentifier = referenceIdentifier;
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
		IdentifierSetting other = (IdentifierSetting)obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {

		return "IdentifierSetting [name=" + name + "]";
	}
}
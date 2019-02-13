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

public class IdentifierSetting {

	private double startRetentionTime = 0.0d; // Minutes
	private double stopRetentionTime = 0.0d; // Minutes
	private String name = "";
	private String casNumber = "";
	private String comments = "";
	private String contributor = "";
	private String referenceId = "";
	private String traces = "";

	public void copyFrom(IdentifierSetting setting) {

		if(setting != null) {
			setStartRetentionTime(setting.getStartRetentionTime());
			setStopRetentionTime(setting.getStopRetentionTime());
			setName(setting.getName());
			setCasNumber(setting.getCasNumber());
			setComments(setting.getComments());
			setContributor(setting.getContributor());
			setReferenceId(setting.getReferenceId());
			setTraces(setting.getTraces());
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

	public String getReferenceId() {

		return referenceId;
	}

	public void setReferenceId(String referenceId) {

		this.referenceId = referenceId;
	}

	public String getTraces() {

		return traces;
	}

	public void setTraces(String traces) {

		this.traces = traces;
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
		IdentifierSetting other = (IdentifierSetting)obj;
		if(name == null) {
			if(other.name != null)
				return false;
		} else if(!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {

		return "IdentifierSetting [startRetentionTime=" + startRetentionTime + ", stopRetentionTime=" + stopRetentionTime + ", name=" + name + ", casNumber=" + casNumber + ", comments=" + comments + ", contributor=" + contributor + ", referenceId=" + referenceId + "]";
	}
}

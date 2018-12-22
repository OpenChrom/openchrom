/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
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

public class AssignerReference {

	private String name = ""; // Target ISTD
	private double startRetentionTime = 0.0d; // Minutes
	private double stopRetentionTime = 0.0d; // Minutes
	private String identifier = ""; // Source Identifier

	public void copyFrom(AssignerReference setting) {

		if(setting != null) {
			setName(setting.getName());
			setStartRetentionTime(setting.getStartRetentionTime());
			setStopRetentionTime(setting.getStopRetentionTime());
			setIdentifier(setting.getIdentifier());
		}
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
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

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
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
		AssignerReference other = (AssignerReference)obj;
		if(identifier == null) {
			if(other.identifier != null)
				return false;
		} else if(!identifier.equals(other.identifier))
			return false;
		if(name == null) {
			if(other.name != null)
				return false;
		} else if(!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {

		return "AssignerReference [name=" + name + ", startRetentionTime=" + startRetentionTime + ", stopRetentionTime=" + stopRetentionTime + ", identifier=" + identifier + "]";
	}
}

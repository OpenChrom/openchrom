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
package net.openchrom.xxd.classifier.supplier.ratios.model;

public class QuantitationRatio extends AbstractRatio {

	private double expectedContent = 0.0d;
	private double content = 0.0d; // Calculated (transient)
	private String unit = "";

	public void copyFrom(QuantitationRatio setting) {

		if(setting != null) {
			setName(setting.getName());
			setExpectedContent(setting.getExpectedContent());
			setUnit(setting.getUnit());
			setDeviationWarn(setting.getDeviationWarn());
			setDeviationError(setting.getDeviationError());
		}
	}

	public double getExpectedContent() {

		return expectedContent;
	}

	public void setExpectedContent(double expectedContent) {

		this.expectedContent = expectedContent;
	}

	public double getContent() {

		return content;
	}

	public void setContent(double content) {

		this.content = content;
	}

	public String getUnit() {

		return unit;
	}

	public void setUnit(String unit) {

		this.unit = unit;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(expectedContent);
		result = prime * result + (int)(temp ^ (temp >>> 32));
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
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
		QuantitationRatio other = (QuantitationRatio)obj;
		if(Double.doubleToLongBits(expectedContent) != Double.doubleToLongBits(other.expectedContent))
			return false;
		if(unit == null) {
			if(other.unit != null)
				return false;
		} else if(!unit.equals(other.unit))
			return false;
		return true;
	}

	@Override
	public String toString() {

		return "QuantitationRatio [expectedContent=" + expectedContent + ", content=" + content + ", unit=" + unit + ", AbstractRatio=" + super.toString() + "]";
	}
}

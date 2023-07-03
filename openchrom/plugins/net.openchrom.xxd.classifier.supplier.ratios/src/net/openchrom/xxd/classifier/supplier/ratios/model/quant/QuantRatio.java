/*******************************************************************************
 * Copyright (c) 2019, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.model.quant;

import net.openchrom.xxd.classifier.supplier.ratios.model.AbstractPeakRatio;

public class QuantRatio extends AbstractPeakRatio {

	private String quantitationName = "";
	private double expectedConcentration = 0.0d;
	private double concentration = 0.0d; // Extracted (transient)
	private String concentrationUnit = "";

	public void copyFrom(QuantRatio setting) {

		if(setting != null) {
			setName(setting.getName());
			setQuantitationName(setting.getQuantitationName());
			setExpectedConcentration(setting.getExpectedConcentration());
			setConcentrationUnit(setting.getConcentrationUnit());
			setDeviationWarn(setting.getDeviationWarn());
			setDeviationError(setting.getDeviationError());
		}
	}

	public String getQuantitationName() {

		return quantitationName;
	}

	public void setQuantitationName(String quantitationName) {

		this.quantitationName = quantitationName;
	}

	public double getExpectedConcentration() {

		return expectedConcentration;
	}

	public void setExpectedConcentration(double expectedConcentration) {

		this.expectedConcentration = expectedConcentration;
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

		double offset = getDeviation() / 100.0d;
		if(concentration <= expectedConcentration) {
			offset *= -1;
		}
		//
		return 1.0d + offset;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(expectedConcentration);
		result = prime * result + (int)(temp ^ (temp >>> 32));
		result = prime * result + ((concentrationUnit == null) ? 0 : concentrationUnit.hashCode());
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
		QuantRatio other = (QuantRatio)obj;
		if(Double.doubleToLongBits(expectedConcentration) != Double.doubleToLongBits(other.expectedConcentration))
			return false;
		if(concentrationUnit == null) {
			if(other.concentrationUnit != null)
				return false;
		} else if(!concentrationUnit.equals(other.concentrationUnit))
			return false;
		return true;
	}

	@Override
	public String toString() {

		return "QuantitationRatio [quantitationName=" + quantitationName + ", expectedConcentration=" + expectedConcentration + ", concentrationUnit=" + concentrationUnit + ", AbstractRatio=" + super.toString() + "]";
	}
}
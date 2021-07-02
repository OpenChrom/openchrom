/*******************************************************************************
 * Copyright (c) 2021 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.converter.supplier.nmrml.internal.v100.model;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AcquisitionDimensionParameterSetType", propOrder = {"decouplingMethod", "acquisitionNucleus", "effectiveExcitationField", "sweepWidth", "pulseWidth", "irradiationFrequency", "decouplingNucleus", "samplingStrategy", "samplingTimePoints"})
public class AcquisitionDimensionParameterSetType {

	protected CVTermType decouplingMethod;
	@XmlElement(required = true)
	protected CVTermType acquisitionNucleus;
	@XmlElement(required = true)
	protected ValueWithUnitType effectiveExcitationField;
	@XmlElement(required = true)
	protected ValueWithUnitType sweepWidth;
	@XmlElement(required = true)
	protected ValueWithUnitType pulseWidth;
	@XmlElement(required = true)
	protected ValueWithUnitType irradiationFrequency;
	protected CVTermType decouplingNucleus;
	@XmlElement(required = true)
	protected CVTermType samplingStrategy;
	protected BinaryDataArrayType samplingTimePoints;
	@XmlAttribute(name = "decoupled", required = true)
	protected boolean decoupled;
	@XmlAttribute(name = "numberOfDataPoints", required = true)
	protected BigInteger numberOfDataPoints;

	public CVTermType getDecouplingMethod() {

		return decouplingMethod;
	}

	public void setDecouplingMethod(CVTermType value) {

		this.decouplingMethod = value;
	}

	public CVTermType getAcquisitionNucleus() {

		return acquisitionNucleus;
	}

	public void setAcquisitionNucleus(CVTermType value) {

		this.acquisitionNucleus = value;
	}

	public ValueWithUnitType getEffectiveExcitationField() {

		return effectiveExcitationField;
	}

	public void setEffectiveExcitationField(ValueWithUnitType value) {

		this.effectiveExcitationField = value;
	}

	public ValueWithUnitType getSweepWidth() {

		return sweepWidth;
	}

	public void setSweepWidth(ValueWithUnitType value) {

		this.sweepWidth = value;
	}

	public ValueWithUnitType getPulseWidth() {

		return pulseWidth;
	}

	public void setPulseWidth(ValueWithUnitType value) {

		this.pulseWidth = value;
	}

	public ValueWithUnitType getIrradiationFrequency() {

		return irradiationFrequency;
	}

	public void setIrradiationFrequency(ValueWithUnitType value) {

		this.irradiationFrequency = value;
	}

	public CVTermType getDecouplingNucleus() {

		return decouplingNucleus;
	}

	public void setDecouplingNucleus(CVTermType value) {

		this.decouplingNucleus = value;
	}

	public CVTermType getSamplingStrategy() {

		return samplingStrategy;
	}

	public void setSamplingStrategy(CVTermType value) {

		this.samplingStrategy = value;
	}

	public BinaryDataArrayType getSamplingTimePoints() {

		return samplingTimePoints;
	}

	public void setSamplingTimePoints(BinaryDataArrayType value) {

		this.samplingTimePoints = value;
	}

	public boolean isDecoupled() {

		return decoupled;
	}

	public void setDecoupled(boolean value) {

		this.decoupled = value;
	}

	public BigInteger getNumberOfDataPoints() {

		return numberOfDataPoints;
	}

	public void setNumberOfDataPoints(BigInteger value) {

		this.numberOfDataPoints = value;
	}
}

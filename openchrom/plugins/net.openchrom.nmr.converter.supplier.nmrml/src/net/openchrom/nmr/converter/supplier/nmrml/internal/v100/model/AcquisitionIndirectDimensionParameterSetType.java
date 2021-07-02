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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AcquisitionIndirectDimensionParameterSetType", propOrder = {"acquisitionNucleus", "effectiveExcitationField", "sweepWidth", "timeDomain", "encodingMethod", "irradiationFrequency"})
public class AcquisitionIndirectDimensionParameterSetType {

	@XmlElement(required = true)
	protected CVTermType acquisitionNucleus;
	@XmlElement(required = true)
	protected ValueWithUnitType effectiveExcitationField;
	@XmlElement(required = true)
	protected ValueWithUnitType sweepWidth;
	@XmlElement(required = true)
	protected BinaryDataArrayType timeDomain;
	@XmlElement(required = true)
	protected CVTermType encodingMethod;
	@XmlElement(required = true)
	protected ValueWithUnitType irradiationFrequency;
	@XmlAttribute(name = "decoupled", required = true)
	protected boolean decoupled;
	@XmlAttribute(name = "acquisitionParamsFileRef", required = true)
	@XmlSchemaType(name = "anyURI")
	protected String acquisitionParamsFileRef;
	@XmlAttribute(name = "numberOfDataPoints", required = true)
	protected BigInteger numberOfDataPoints;

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

	public BinaryDataArrayType getTimeDomain() {

		return timeDomain;
	}

	public void setTimeDomain(BinaryDataArrayType value) {

		this.timeDomain = value;
	}

	public CVTermType getEncodingMethod() {

		return encodingMethod;
	}

	public void setEncodingMethod(CVTermType value) {

		this.encodingMethod = value;
	}

	public ValueWithUnitType getIrradiationFrequency() {

		return irradiationFrequency;
	}

	public void setIrradiationFrequency(ValueWithUnitType value) {

		this.irradiationFrequency = value;
	}

	public boolean isDecoupled() {

		return decoupled;
	}

	public void setDecoupled(boolean value) {

		this.decoupled = value;
	}

	public String getAcquisitionParamsFileRef() {

		return acquisitionParamsFileRef;
	}

	public void setAcquisitionParamsFileRef(String value) {

		this.acquisitionParamsFileRef = value;
	}

	public BigInteger getNumberOfDataPoints() {

		return numberOfDataPoints;
	}

	public void setNumberOfDataPoints(BigInteger value) {

		this.numberOfDataPoints = value;
	}
}

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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AcquisitionParameterSetType", propOrder = {"contactRefList", "softwareRef", "sampleContainer", "sampleAcquisitionTemperature", "solventSuppressionMethod", "spinningRate", "relaxationDelay", "pulseSequence", "shapedPulseFile", "groupDelay", "acquisitionParameterRefList"})
@XmlSeeAlso({AcquisitionParameterSetMultiDType.class, AcquisitionParameterSet1DType.class})
public class AcquisitionParameterSetType {

	protected ContactRefListType contactRefList;
	protected SoftwareRefType softwareRef;
	@XmlElement(required = true)
	protected CVTermType sampleContainer;
	@XmlElement(required = true)
	protected ValueWithUnitType sampleAcquisitionTemperature;
	protected CVParamType solventSuppressionMethod;
	@XmlElement(required = true)
	protected ValueWithUnitType spinningRate;
	@XmlElement(required = true)
	protected ValueWithUnitType relaxationDelay;
	@XmlElement(required = true)
	protected PulseSequenceType pulseSequence;
	protected SourceFileRefType shapedPulseFile;
	protected ValueWithUnitType groupDelay;
	protected AcquisitionParameterFileRefListType acquisitionParameterRefList;
	@XmlAttribute(name = "numberOfSteadyStateScans", required = true)
	protected BigInteger numberOfSteadyStateScans;
	@XmlAttribute(name = "numberOfScans", required = true)
	protected BigInteger numberOfScans;

	public ContactRefListType getContactRefList() {

		return contactRefList;
	}

	public void setContactRefList(ContactRefListType value) {

		this.contactRefList = value;
	}

	public SoftwareRefType getSoftwareRef() {

		return softwareRef;
	}

	public void setSoftwareRef(SoftwareRefType value) {

		this.softwareRef = value;
	}

	public CVTermType getSampleContainer() {

		return sampleContainer;
	}

	public void setSampleContainer(CVTermType value) {

		this.sampleContainer = value;
	}

	public ValueWithUnitType getSampleAcquisitionTemperature() {

		return sampleAcquisitionTemperature;
	}

	public void setSampleAcquisitionTemperature(ValueWithUnitType value) {

		this.sampleAcquisitionTemperature = value;
	}

	public CVParamType getSolventSuppressionMethod() {

		return solventSuppressionMethod;
	}

	public void setSolventSuppressionMethod(CVParamType value) {

		this.solventSuppressionMethod = value;
	}

	public ValueWithUnitType getSpinningRate() {

		return spinningRate;
	}

	public void setSpinningRate(ValueWithUnitType value) {

		this.spinningRate = value;
	}

	public ValueWithUnitType getRelaxationDelay() {

		return relaxationDelay;
	}

	public void setRelaxationDelay(ValueWithUnitType value) {

		this.relaxationDelay = value;
	}

	public PulseSequenceType getPulseSequence() {

		return pulseSequence;
	}

	public void setPulseSequence(PulseSequenceType value) {

		this.pulseSequence = value;
	}

	public SourceFileRefType getShapedPulseFile() {

		return shapedPulseFile;
	}

	public void setShapedPulseFile(SourceFileRefType value) {

		this.shapedPulseFile = value;
	}

	public ValueWithUnitType getGroupDelay() {

		return groupDelay;
	}

	public void setGroupDelay(ValueWithUnitType value) {

		this.groupDelay = value;
	}

	public AcquisitionParameterFileRefListType getAcquisitionParameterRefList() {

		return acquisitionParameterRefList;
	}

	public void setAcquisitionParameterRefList(AcquisitionParameterFileRefListType value) {

		this.acquisitionParameterRefList = value;
	}

	public BigInteger getNumberOfSteadyStateScans() {

		return numberOfSteadyStateScans;
	}

	public void setNumberOfSteadyStateScans(BigInteger value) {

		this.numberOfSteadyStateScans = value;
	}

	public BigInteger getNumberOfScans() {

		return numberOfScans;
	}

	public void setNumberOfScans(BigInteger value) {

		this.numberOfScans = value;
	}
}

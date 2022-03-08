/*******************************************************************************
 * Copyright (c) 2021, 2022 Lablicate GmbH.
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

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SampleType", propOrder = {"originalBiologicalSamplepH", "postBufferpH", "buffer", "fieldFrequencyLock", "chemicalShiftStandard", "solventType", "additionalSoluteList", "concentrationStandard"})
public class SampleType {

	protected Double originalBiologicalSamplepH;
	protected Double postBufferpH;
	protected CVTermType buffer;
	@XmlElement(required = true)
	protected SampleType.FieldFrequencyLock fieldFrequencyLock;
	@XmlElement(required = true)
	protected CVParamType chemicalShiftStandard;
	@XmlElement(required = true)
	protected List<CVParamWithUnitType> solventType;
	@XmlElement(required = true)
	protected AdditionalSoluteListType additionalSoluteList;
	protected SampleType.ConcentrationStandard concentrationStandard;
	@XmlAttribute(name = "originalBiologicalSampleReference", required = true)
	@XmlSchemaType(name = "anyURI")
	protected String originalBiologicalSampleReference;

	public Double getOriginalBiologicalSamplepH() {

		return originalBiologicalSamplepH;
	}

	public void setOriginalBiologicalSamplepH(Double value) {

		this.originalBiologicalSamplepH = value;
	}

	public Double getPostBufferpH() {

		return postBufferpH;
	}

	public void setPostBufferpH(Double value) {

		this.postBufferpH = value;
	}

	public CVTermType getBuffer() {

		return buffer;
	}

	public void setBuffer(CVTermType value) {

		this.buffer = value;
	}

	public SampleType.FieldFrequencyLock getFieldFrequencyLock() {

		return fieldFrequencyLock;
	}

	public void setFieldFrequencyLock(SampleType.FieldFrequencyLock value) {

		this.fieldFrequencyLock = value;
	}

	public CVParamType getChemicalShiftStandard() {

		return chemicalShiftStandard;
	}

	public void setChemicalShiftStandard(CVParamType value) {

		this.chemicalShiftStandard = value;
	}

	public List<CVParamWithUnitType> getSolventType() {

		if(solventType == null) {
			solventType = new ArrayList<CVParamWithUnitType>();
		}
		return this.solventType;
	}

	public AdditionalSoluteListType getAdditionalSoluteList() {

		return additionalSoluteList;
	}

	public void setAdditionalSoluteList(AdditionalSoluteListType value) {

		this.additionalSoluteList = value;
	}

	public SampleType.ConcentrationStandard getConcentrationStandard() {

		return concentrationStandard;
	}

	public void setConcentrationStandard(SampleType.ConcentrationStandard value) {

		this.concentrationStandard = value;
	}

	public String getOriginalBiologicalSampleReference() {

		return originalBiologicalSampleReference;
	}

	public void setOriginalBiologicalSampleReference(String value) {

		this.originalBiologicalSampleReference = value;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {"type", "concentrationInSample", "name"})
	public static class ConcentrationStandard {

		@XmlElement(required = true)
		protected CVTermType type;
		@XmlElement(required = true)
		protected ValueWithUnitType concentrationInSample;
		@XmlElement(required = true)
		protected CVTermType name;

		public CVTermType getType() {

			return type;
		}

		public void setType(CVTermType value) {

			this.type = value;
		}

		public ValueWithUnitType getConcentrationInSample() {

			return concentrationInSample;
		}

		public void setConcentrationInSample(ValueWithUnitType value) {

			this.concentrationInSample = value;
		}

		public CVTermType getName() {

			return name;
		}

		public void setName(CVTermType value) {

			this.name = value;
		}
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "")
	public static class FieldFrequencyLock {

		@XmlAttribute(name = "fieldFrequencyLockName", required = true)
		protected String fieldFrequencyLockName;

		public String getFieldFrequencyLockName() {

			return fieldFrequencyLockName;
		}

		public void setFieldFrequencyLockName(String value) {

			this.fieldFrequencyLockName = value;
		}
	}
}

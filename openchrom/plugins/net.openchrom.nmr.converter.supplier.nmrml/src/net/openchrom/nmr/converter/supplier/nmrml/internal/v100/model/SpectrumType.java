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
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SpectrumType", propOrder = {"processingSoftwareRefList", "processingParameterFileRefList", "spectrumDataArray", "xAxis", "processingParameterSet"})
@XmlSeeAlso({Spectrum1DType.class, SpectrumMultiDType.class})
public class SpectrumType {

	protected SoftwareRefListType processingSoftwareRefList;
	protected ProcessingParameterFileRefListType processingParameterFileRefList;
	@XmlElement(required = true)
	protected BinaryDataArrayType spectrumDataArray;
	@XmlElement(required = true)
	protected AxisWithUnitType xAxis;
	protected SpectrumType.ProcessingParameterSet processingParameterSet;
	@XmlAttribute(name = "numberOfDataPoints", required = true)
	protected BigInteger numberOfDataPoints;
	@XmlAttribute(name = "id", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String id;
	@XmlAttribute(name = "name")
	protected String name;

	public SoftwareRefListType getProcessingSoftwareRefList() {

		return processingSoftwareRefList;
	}

	public void setProcessingSoftwareRefList(SoftwareRefListType value) {

		this.processingSoftwareRefList = value;
	}

	public ProcessingParameterFileRefListType getProcessingParameterFileRefList() {

		return processingParameterFileRefList;
	}

	public void setProcessingParameterFileRefList(ProcessingParameterFileRefListType value) {

		this.processingParameterFileRefList = value;
	}

	public BinaryDataArrayType getSpectrumDataArray() {

		return spectrumDataArray;
	}

	public void setSpectrumDataArray(BinaryDataArrayType value) {

		this.spectrumDataArray = value;
	}

	public AxisWithUnitType getXAxis() {

		return xAxis;
	}

	public void setXAxis(AxisWithUnitType value) {

		this.xAxis = value;
	}

	public SpectrumType.ProcessingParameterSet getProcessingParameterSet() {

		return processingParameterSet;
	}

	public void setProcessingParameterSet(SpectrumType.ProcessingParameterSet value) {

		this.processingParameterSet = value;
	}

	public BigInteger getNumberOfDataPoints() {

		return numberOfDataPoints;
	}

	public void setNumberOfDataPoints(BigInteger value) {

		this.numberOfDataPoints = value;
	}

	public String getId() {

		return id;
	}

	public void setId(String value) {

		this.id = value;
	}

	public String getName() {

		return name;
	}

	public void setName(String value) {

		this.name = value;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {"postAcquisitionSolventSuppressionMethod", "calibrationCompound", "dataTransformationMethod"})
	public static class ProcessingParameterSet {

		protected CVTermType postAcquisitionSolventSuppressionMethod;
		protected CVTermType calibrationCompound;
		protected CVTermType dataTransformationMethod;

		public CVTermType getPostAcquisitionSolventSuppressionMethod() {

			return postAcquisitionSolventSuppressionMethod;
		}

		public void setPostAcquisitionSolventSuppressionMethod(CVTermType value) {

			this.postAcquisitionSolventSuppressionMethod = value;
		}

		public CVTermType getCalibrationCompound() {

			return calibrationCompound;
		}

		public void setCalibrationCompound(CVTermType value) {

			this.calibrationCompound = value;
		}

		public CVTermType getDataTransformationMethod() {

			return dataTransformationMethod;
		}

		public void setDataTransformationMethod(CVTermType value) {

			this.dataTransformationMethod = value;
		}
	}
}

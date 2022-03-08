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
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SpectralProcessingParameterSetType", propOrder = {"processingSoftwareRefList", "postAcquisitionSolventSuppressionMethod", "dataTransformationMethod", "calibrationCompound"})
@XmlSeeAlso({SpectralProcessingParameterSet2DType.class})
public class SpectralProcessingParameterSetType {

	@XmlElement(required = true)
	protected List<SoftwareRefListType> processingSoftwareRefList;
	@XmlElement(required = true)
	protected CVTermType postAcquisitionSolventSuppressionMethod;
	@XmlElement(required = true)
	protected CVTermType dataTransformationMethod;
	@XmlElement(required = true)
	protected CVTermType calibrationCompound;

	public List<SoftwareRefListType> getProcessingSoftwareRefList() {

		if(processingSoftwareRefList == null) {
			processingSoftwareRefList = new ArrayList<SoftwareRefListType>();
		}
		return this.processingSoftwareRefList;
	}

	public CVTermType getPostAcquisitionSolventSuppressionMethod() {

		return postAcquisitionSolventSuppressionMethod;
	}

	public void setPostAcquisitionSolventSuppressionMethod(CVTermType value) {

		this.postAcquisitionSolventSuppressionMethod = value;
	}

	public CVTermType getDataTransformationMethod() {

		return dataTransformationMethod;
	}

	public void setDataTransformationMethod(CVTermType value) {

		this.dataTransformationMethod = value;
	}

	public CVTermType getCalibrationCompound() {

		return calibrationCompound;
	}

	public void setCalibrationCompound(CVTermType value) {

		this.calibrationCompound = value;
	}
}

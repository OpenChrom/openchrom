/*******************************************************************************
 * Copyright (c) 2021, 2023 Lablicate GmbH.
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
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AcquisitionParameterSetMultiDType", propOrder = {"hadamardParameterSet", "directDimensionParameterSet", "encodingScheme", "indirectDimensionParameterSet"})
public class AcquisitionParameterSetMultiDType extends AcquisitionParameterSetType {

	protected AcquisitionParameterSetMultiDType.HadamardParameterSet hadamardParameterSet;
	@XmlElement(required = true)
	protected AcquisitionDimensionParameterSetType directDimensionParameterSet;
	@XmlElement(required = true)
	protected CVParamType encodingScheme;
	@XmlElement(required = true)
	protected List<AcquisitionDimensionParameterSetType> indirectDimensionParameterSet;

	public AcquisitionParameterSetMultiDType.HadamardParameterSet getHadamardParameterSet() {

		return hadamardParameterSet;
	}

	public void setHadamardParameterSet(AcquisitionParameterSetMultiDType.HadamardParameterSet value) {

		this.hadamardParameterSet = value;
	}

	public AcquisitionDimensionParameterSetType getDirectDimensionParameterSet() {

		return directDimensionParameterSet;
	}

	public void setDirectDimensionParameterSet(AcquisitionDimensionParameterSetType value) {

		this.directDimensionParameterSet = value;
	}

	public CVParamType getEncodingScheme() {

		return encodingScheme;
	}

	public void setEncodingScheme(CVParamType value) {

		this.encodingScheme = value;
	}

	public List<AcquisitionDimensionParameterSetType> getIndirectDimensionParameterSet() {

		if(indirectDimensionParameterSet == null) {
			indirectDimensionParameterSet = new ArrayList<>();
		}
		return this.indirectDimensionParameterSet;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {"hadamardFrequency"})
	public static class HadamardParameterSet {

		protected List<ValueWithUnitType> hadamardFrequency;

		public List<ValueWithUnitType> getHadamardFrequency() {

			if(hadamardFrequency == null) {
				hadamardFrequency = new ArrayList<>();
			}
			return this.hadamardFrequency;
		}
	}
}

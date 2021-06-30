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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SpectrumMultiDType", propOrder = {"firstDimensionProcessingParameterSet", "higherDimensionProcessingParameterSet", "projected3DProcessingParamaterSet"})
public class SpectrumMultiDType extends SpectrumType {

	@XmlElement(required = true)
	protected FirstDimensionProcessingParameterSetType firstDimensionProcessingParameterSet;
	@XmlElement(required = true)
	protected List<HigherDimensionProcessingParameterSetType> higherDimensionProcessingParameterSet;
	protected Projected3DProcessingParamaterSetType projected3DProcessingParamaterSet;

	public FirstDimensionProcessingParameterSetType getFirstDimensionProcessingParameterSet() {

		return firstDimensionProcessingParameterSet;
	}

	public void setFirstDimensionProcessingParameterSet(FirstDimensionProcessingParameterSetType value) {

		this.firstDimensionProcessingParameterSet = value;
	}

	public List<HigherDimensionProcessingParameterSetType> getHigherDimensionProcessingParameterSet() {

		if(higherDimensionProcessingParameterSet == null) {
			higherDimensionProcessingParameterSet = new ArrayList<HigherDimensionProcessingParameterSetType>();
		}
		return this.higherDimensionProcessingParameterSet;
	}

	public Projected3DProcessingParamaterSetType getProjected3DProcessingParamaterSet() {

		return projected3DProcessingParamaterSet;
	}

	public void setProjected3DProcessingParamaterSet(Projected3DProcessingParamaterSetType value) {

		this.projected3DProcessingParamaterSet = value;
	}
}

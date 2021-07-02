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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AcquisitionMultiDType", propOrder = {"acquisitionParameterSet", "fidData"})
public class AcquisitionMultiDType {

	@XmlElement(required = true)
	protected AcquisitionParameterSetMultiDType acquisitionParameterSet;
	@XmlElement(required = true)
	protected BinaryDataArrayType fidData;

	public AcquisitionParameterSetMultiDType getAcquisitionParameterSet() {

		return acquisitionParameterSet;
	}

	public void setAcquisitionParameterSet(AcquisitionParameterSetMultiDType value) {

		this.acquisitionParameterSet = value;
	}

	public BinaryDataArrayType getFidData() {

		return fidData;
	}

	public void setFidData(BinaryDataArrayType value) {

		this.fidData = value;
	}
}

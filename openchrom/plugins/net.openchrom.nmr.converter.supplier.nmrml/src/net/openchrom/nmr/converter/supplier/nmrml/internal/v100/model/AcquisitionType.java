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
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AcquisitionType", propOrder = {"acquisition1D", "acquisitionMultiD"})
public class AcquisitionType {

	protected Acquisition1DType acquisition1D;
	protected AcquisitionMultiDType acquisitionMultiD;

	public Acquisition1DType getAcquisition1D() {

		return acquisition1D;
	}

	public void setAcquisition1D(Acquisition1DType value) {

		this.acquisition1D = value;
	}

	public AcquisitionMultiDType getAcquisitionMultiD() {

		return acquisitionMultiD;
	}

	public void setAcquisitionMultiD(AcquisitionMultiDType value) {

		this.acquisitionMultiD = value;
	}
}

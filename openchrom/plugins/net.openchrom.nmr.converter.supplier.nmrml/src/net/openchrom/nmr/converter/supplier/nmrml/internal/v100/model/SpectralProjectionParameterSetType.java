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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SpectralProjectionParameterSetType", propOrder = {"projectionMethod"})
public class SpectralProjectionParameterSetType {

	@XmlElement(required = true)
	protected CVTermType projectionMethod;
	@XmlAttribute(name = "projectionAxis")
	@XmlSchemaType(name = "anySimpleType")
	protected String projectionAxis;

	public CVTermType getProjectionMethod() {

		return projectionMethod;
	}

	public void setProjectionMethod(CVTermType value) {

		this.projectionMethod = value;
	}

	public String getProjectionAxis() {

		return projectionAxis;
	}

	public void setProjectionAxis(String value) {

		this.projectionAxis = value;
	}
}

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

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlIDREF;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QuantificationAnnotationType", propOrder = {"quantificationMethod", "quantifiedCompoundList"})
public class QuantificationAnnotationType {

	@XmlElement(required = true)
	protected CVTermType quantificationMethod;
	@XmlElement(required = true)
	protected QuantifiedCompoundListType quantifiedCompoundList;
	@XmlAttribute(name = "spectrumRef", required = true)
	@XmlIDREF
	@XmlSchemaType(name = "IDREF")
	protected Object spectrumRef;

	public CVTermType getQuantificationMethod() {

		return quantificationMethod;
	}

	public void setQuantificationMethod(CVTermType value) {

		this.quantificationMethod = value;
	}

	public QuantifiedCompoundListType getQuantifiedCompoundList() {

		return quantifiedCompoundList;
	}

	public void setQuantifiedCompoundList(QuantifiedCompoundListType value) {

		this.quantifiedCompoundList = value;
	}

	public Object getSpectrumRef() {

		return spectrumRef;
	}

	public void setSpectrumRef(Object value) {

		this.spectrumRef = value;
	}
}

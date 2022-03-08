/*******************************************************************************
 *  Copyright (c) 2021, 2022 Lablicate GmbH.
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
@XmlType(name = "AtomAssingmentAnnotationType", propOrder = {"chemicalCompound", "atomAssignmentList"})
public class AtomAssingmentAnnotationType {

	@XmlElement(required = true)
	protected ChemicalCompoundType chemicalCompound;
	protected AtomAssignmentListType atomAssignmentList;
	@XmlAttribute(name = "spectrumRef", required = true)
	@XmlIDREF
	@XmlSchemaType(name = "IDREF")
	protected Object spectrumRef;

	public ChemicalCompoundType getChemicalCompound() {

		return chemicalCompound;
	}

	public void setChemicalCompound(ChemicalCompoundType value) {

		this.chemicalCompound = value;
	}

	public AtomAssignmentListType getAtomAssignmentList() {

		return atomAssignmentList;
	}

	public void setAtomAssignmentList(AtomAssignmentListType value) {

		this.atomAssignmentList = value;
	}

	public Object getSpectrumRef() {

		return spectrumRef;
	}

	public void setSpectrumRef(Object value) {

		this.spectrumRef = value;
	}
}

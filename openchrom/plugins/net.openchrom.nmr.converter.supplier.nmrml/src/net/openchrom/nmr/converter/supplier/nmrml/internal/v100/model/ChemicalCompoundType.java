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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChemicalCompoundType", propOrder = {"identifierList", "structure"})
@XmlSeeAlso({QuantifiedCompoundType.class})
public class ChemicalCompoundType {

	protected CompoundIdentifierListType identifierList;
	protected CompoundStructureType structure;
	@XmlAttribute(name = "name")
	@XmlSchemaType(name = "anySimpleType")
	protected String name;

	public CompoundIdentifierListType getIdentifierList() {

		return identifierList;
	}

	public void setIdentifierList(CompoundIdentifierListType value) {

		this.identifierList = value;
	}

	public CompoundStructureType getStructure() {

		return structure;
	}

	public void setStructure(CompoundStructureType value) {

		this.structure = value;
	}

	public String getName() {

		return name;
	}

	public void setName(String value) {

		this.name = value;
	}
}

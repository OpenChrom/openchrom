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
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Acquisition1DType", propOrder = {"acquisitionParameterSet", "fidData"})
public class Acquisition1DType {

	@XmlElement(required = true)
	protected AcquisitionParameterSet1DType acquisitionParameterSet;
	@XmlElement(required = true)
	protected BinaryDataArrayType fidData;
	@XmlAttribute(name = "id")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String id;
	@XmlAttribute(name = "name")
	protected String name;

	public AcquisitionParameterSet1DType getAcquisitionParameterSet() {

		return acquisitionParameterSet;
	}

	public void setAcquisitionParameterSet(AcquisitionParameterSet1DType value) {

		this.acquisitionParameterSet = value;
	}

	public BinaryDataArrayType getFidData() {

		return fidData;
	}

	public void setFidData(BinaryDataArrayType value) {

		this.fidData = value;
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
}

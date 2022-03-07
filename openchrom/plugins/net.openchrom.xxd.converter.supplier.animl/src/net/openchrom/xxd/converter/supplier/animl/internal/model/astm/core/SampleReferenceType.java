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
package net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Reference to a Sample used in this Experiment.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SampleReferenceType")
public class SampleReferenceType {

	@XmlAttribute(name = "sampleID", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String sampleID;
	@XmlAttribute(name = "id")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String id;
	@XmlAttribute(name = "role", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String role;
	@XmlAttribute(name = "samplePurpose", required = true)
	protected PurposeType samplePurpose;

	public String getSampleID() {

		return sampleID;
	}

	public void setSampleID(String value) {

		this.sampleID = value;
	}

	public String getId() {

		return id;
	}

	public void setId(String value) {

		this.id = value;
	}

	public String getRole() {

		return role;
	}

	public void setRole(String value) {

		this.role = value;
	}

	public PurposeType getSamplePurpose() {

		return samplePurpose;
	}

	public void setSamplePurpose(PurposeType value) {

		this.samplePurpose = value;
	}
}

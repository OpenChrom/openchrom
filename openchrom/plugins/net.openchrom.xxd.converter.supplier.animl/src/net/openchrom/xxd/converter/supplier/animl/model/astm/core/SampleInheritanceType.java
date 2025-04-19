/*******************************************************************************
 * Copyright (c) 2021, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.converter.supplier.animl.model.astm.core;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Indicates that a Sample was inherited from the parent ExperimentStep.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SampleInheritanceType")
public class SampleInheritanceType {

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

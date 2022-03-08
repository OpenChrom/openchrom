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
 * Prefix-based reference to a set of Experiment Steps whose data are consumed.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExperimentDataBulkReferenceType")
public class ExperimentDataBulkReferenceType {

	@XmlAttribute(name = "experimentStepIDPrefix", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String experimentStepIDPrefix;
	@XmlAttribute(name = "id")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String id;
	@XmlAttribute(name = "role", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String role;
	@XmlAttribute(name = "dataPurpose", required = true)
	protected PurposeType dataPurpose;

	public String getExperimentStepIDPrefix() {

		return experimentStepIDPrefix;
	}

	public void setExperimentStepIDPrefix(String value) {

		this.experimentStepIDPrefix = value;
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

	public PurposeType getDataPurpose() {

		return dataPurpose;
	}

	public void setDataPurpose(PurposeType value) {

		this.dataPurpose = value;
	}
}

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

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Set of Experiment Steps consumed by this Experiment Step.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExperimentDataReferenceSetType", propOrder = {"experimentDataReference", "experimentDataBulkReference"})
public class ExperimentDataReferenceSetType {

	@XmlElement(name = "ExperimentDataReference")
	protected List<ExperimentDataReferenceType> experimentDataReference;
	@XmlElement(name = "ExperimentDataBulkReference")
	protected List<ExperimentDataBulkReferenceType> experimentDataBulkReference;
	@XmlAttribute(name = "id")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String id;

	public List<ExperimentDataReferenceType> getExperimentDataReference() {

		if(experimentDataReference == null) {
			experimentDataReference = new ArrayList<ExperimentDataReferenceType>();
		}
		return this.experimentDataReference;
	}

	public List<ExperimentDataBulkReferenceType> getExperimentDataBulkReference() {

		if(experimentDataBulkReference == null) {
			experimentDataBulkReference = new ArrayList<ExperimentDataBulkReferenceType>();
		}
		return this.experimentDataBulkReference;
	}

	public String getId() {

		return id;
	}

	public void setId(String value) {

		this.id = value;
	}
}

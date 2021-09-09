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
package net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Contains references to the context of this Experiment.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfrastructureType", propOrder = {"sampleReferenceSet", "parentDataPointReferenceSet", "experimentDataReferenceSet", "timestamp"})
public class InfrastructureType {

	@XmlElement(name = "SampleReferenceSet")
	protected SampleReferenceSetType sampleReferenceSet;
	@XmlElement(name = "ParentDataPointReferenceSet")
	protected ParentDataPointReferenceSetType parentDataPointReferenceSet;
	@XmlElement(name = "ExperimentDataReferenceSet")
	protected ExperimentDataReferenceSetType experimentDataReferenceSet;
	@XmlElement(name = "Timestamp")
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar timestamp;
	@XmlAttribute(name = "id")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String id;

	public SampleReferenceSetType getSampleReferenceSet() {

		return sampleReferenceSet;
	}

	public void setSampleReferenceSet(SampleReferenceSetType value) {

		this.sampleReferenceSet = value;
	}

	public ParentDataPointReferenceSetType getParentDataPointReferenceSet() {

		return parentDataPointReferenceSet;
	}

	public void setParentDataPointReferenceSet(ParentDataPointReferenceSetType value) {

		this.parentDataPointReferenceSet = value;
	}

	public ExperimentDataReferenceSetType getExperimentDataReferenceSet() {

		return experimentDataReferenceSet;
	}

	public void setExperimentDataReferenceSet(ExperimentDataReferenceSetType value) {

		this.experimentDataReferenceSet = value;
	}

	public XMLGregorianCalendar getTimestamp() {

		return timestamp;
	}

	public void setTimestamp(XMLGregorianCalendar value) {

		this.timestamp = value;
	}

	public String getId() {

		return id;
	}

	public void setId(String value) {

		this.id = value;
	}
}

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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Container that documents a step in an experiment. Use one ExperimentStep per application of a Technique.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExperimentStepType", propOrder = {"tagSet", "technique", "infrastructure", "method", "result"})
public class ExperimentStepType {

	@XmlElement(name = "TagSet")
	protected TagSetType tagSet;
	@XmlElement(name = "Technique")
	protected TechniqueType technique;
	@XmlElement(name = "Infrastructure")
	protected InfrastructureType infrastructure;
	@XmlElement(name = "Method")
	protected MethodType method;
	@XmlElement(name = "Result")
	protected List<ResultType> result;
	@XmlAttribute(name = "experimentStepID", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String experimentStepID;
	@XmlAttribute(name = "templateUsed")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String templateUsed;
	@XmlAttribute(name = "comment")
	protected String comment;
	@XmlAttribute(name = "sourceDataLocation")
	protected String sourceDataLocation;
	@XmlAttribute(name = "name", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String name;
	@XmlAttribute(name = "id")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String id;

	public TagSetType getTagSet() {

		return tagSet;
	}

	public void setTagSet(TagSetType value) {

		this.tagSet = value;
	}

	public TechniqueType getTechnique() {

		return technique;
	}

	public void setTechnique(TechniqueType value) {

		this.technique = value;
	}

	public InfrastructureType getInfrastructure() {

		return infrastructure;
	}

	public void setInfrastructure(InfrastructureType value) {

		this.infrastructure = value;
	}

	public MethodType getMethod() {

		return method;
	}

	public void setMethod(MethodType value) {

		this.method = value;
	}

	public List<ResultType> getResult() {

		if(result == null) {
			result = new ArrayList<ResultType>();
		}
		return this.result;
	}

	public String getExperimentStepID() {

		return experimentStepID;
	}

	public void setExperimentStepID(String value) {

		this.experimentStepID = value;
	}

	public String getTemplateUsed() {

		return templateUsed;
	}

	public void setTemplateUsed(String value) {

		this.templateUsed = value;
	}

	public String getComment() {

		return comment;
	}

	public void setComment(String value) {

		this.comment = value;
	}

	public String getSourceDataLocation() {

		return sourceDataLocation;
	}

	public void setSourceDataLocation(String value) {

		this.sourceDataLocation = value;
	}

	public String getName() {

		return name;
	}

	public void setName(String value) {

		this.name = value;
	}

	public String getId() {

		return id;
	}

	public void setId(String value) {

		this.id = value;
	}
}

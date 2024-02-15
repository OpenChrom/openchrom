/*******************************************************************************
 * Copyright (c) 2021, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.converter.supplier.animl.model.astm.core;

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
 * Represents a template for an ExperimentStep.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TemplateType", propOrder = {"tagSet", "technique", "infrastructure", "method", "result"})
public class TemplateType {

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
	@XmlAttribute(name = "templateID", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String templateID;
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
			result = new ArrayList<>();
		}
		return this.result;
	}

	public String getTemplateID() {

		return templateID;
	}

	public void setTemplateID(String value) {

		this.templateID = value;
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

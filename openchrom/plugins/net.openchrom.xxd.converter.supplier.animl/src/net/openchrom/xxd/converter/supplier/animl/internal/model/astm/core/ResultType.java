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
 * Container for Data derived from Experiment.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResultType", propOrder = {"seriesSet", "category", "experimentStepSet"})
public class ResultType {

	@XmlElement(name = "SeriesSet")
	protected SeriesSetType seriesSet;
	@XmlElement(name = "Category")
	protected List<CategoryType> category;
	@XmlElement(name = "ExperimentStepSet")
	protected ExperimentStepSetType experimentStepSet;
	@XmlAttribute(name = "name", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String name;
	@XmlAttribute(name = "id")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String id;

	public SeriesSetType getSeriesSet() {

		return seriesSet;
	}

	public void setSeriesSet(SeriesSetType value) {

		this.seriesSet = value;
	}

	public List<CategoryType> getCategory() {

		if(category == null) {
			category = new ArrayList<CategoryType>();
		}
		return this.category;
	}

	public ExperimentStepSetType getExperimentStepSet() {

		return experimentStepSet;
	}

	public void setExperimentStepSet(ExperimentStepSetType value) {

		this.experimentStepSet = value;
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

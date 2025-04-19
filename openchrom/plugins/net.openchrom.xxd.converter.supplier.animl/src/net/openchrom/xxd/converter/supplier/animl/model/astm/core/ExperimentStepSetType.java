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
 * Container for multiple ExperimentSteps that describe the process and results.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExperimentStepSetType", propOrder = {"template", "experimentStep"})
public class ExperimentStepSetType {

	@XmlElement(name = "Template")
	protected List<TemplateType> template;
	@XmlElement(name = "ExperimentStep", required = true)
	protected List<ExperimentStepType> experimentStep;
	@XmlAttribute(name = "id")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String id;

	public List<TemplateType> getTemplate() {

		if(template == null) {
			template = new ArrayList<>();
		}
		return this.template;
	}

	public List<ExperimentStepType> getExperimentStep() {

		if(experimentStep == null) {
			experimentStep = new ArrayList<>();
		}
		return this.experimentStep;
	}

	public String getId() {

		return id;
	}

	public void setId(String value) {

		this.id = value;
	}
}

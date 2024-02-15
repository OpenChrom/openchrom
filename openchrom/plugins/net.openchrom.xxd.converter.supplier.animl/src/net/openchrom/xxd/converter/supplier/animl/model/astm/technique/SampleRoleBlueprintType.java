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
 * Philip Wenig - refactorings
 *******************************************************************************/
package net.openchrom.xxd.converter.supplier.animl.model.astm.technique;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Definition of characteristics and role that the referenced Sample plays in the ExperimentStep.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SampleRoleBlueprintType", propOrder = {"documentation", "categoryBlueprint"})
public class SampleRoleBlueprintType {

	@XmlElement(name = "Documentation")
	protected DocumentationType documentation;
	@XmlElement(name = "CategoryBlueprint")
	protected List<CategoryBlueprintType> categoryBlueprint;
	@XmlAttribute(name = "name", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String name;
	@XmlAttribute(name = "samplePurpose", required = true)
	protected PurposeType samplePurpose;
	@XmlAttribute(name = "modality")
	protected ModalityType modality;
	@XmlAttribute(name = "maxOccurs")
	protected String maxOccurs;
	@XmlAttribute(name = "inheritable")
	protected Boolean inheritable;

	public DocumentationType getDocumentation() {

		return documentation;
	}

	public void setDocumentation(DocumentationType value) {

		this.documentation = value;
	}

	public List<CategoryBlueprintType> getCategoryBlueprint() {

		if(categoryBlueprint == null) {
			categoryBlueprint = new ArrayList<>();
		}
		return this.categoryBlueprint;
	}

	public String getName() {

		return name;
	}

	public void setName(String value) {

		this.name = value;
	}

	public PurposeType getSamplePurpose() {

		return samplePurpose;
	}

	public void setSamplePurpose(PurposeType value) {

		this.samplePurpose = value;
	}

	public ModalityType getModality() {

		if(modality == null) {
			return ModalityType.REQUIRED;
		} else {
			return modality;
		}
	}

	public void setModality(ModalityType value) {

		this.modality = value;
	}

	public String getMaxOccurs() {

		if(maxOccurs == null) {
			return "1";
		} else {
			return maxOccurs;
		}
	}

	public void setMaxOccurs(String value) {

		this.maxOccurs = value;
	}

	public boolean isInheritable() {

		if(inheritable == null) {
			return true;
		} else {
			return inheritable;
		}
	}

	public void setInheritable(Boolean value) {

		this.inheritable = value;
	}
}

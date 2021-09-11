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
package net.openchrom.xxd.converter.supplier.animl.internal.model.astm.technique;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Definition of characteristics and role that the referenced ExperimentStep plays in the ExperimentStep.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExperimentDataRoleBlueprintType", propOrder = {"documentation"})
public class ExperimentDataRoleBlueprintType {

	@XmlElement(name = "Documentation")
	protected DocumentationType documentation;
	@XmlAttribute(name = "name", required = true)
	protected String name;
	@XmlAttribute(name = "experimentStepPurpose", required = true)
	protected PurposeType experimentStepPurpose;
	@XmlAttribute(name = "modality")
	protected ModalityType modality;
	@XmlAttribute(name = "maxOccurs")
	protected String maxOccurs;

	public DocumentationType getDocumentation() {

		return documentation;
	}

	public void setDocumentation(DocumentationType value) {

		this.documentation = value;
	}

	public String getName() {

		return name;
	}

	public void setName(String value) {

		this.name = value;
	}

	public PurposeType getExperimentStepPurpose() {

		return experimentStepPurpose;
	}

	public void setExperimentStepPurpose(PurposeType value) {

		this.experimentStepPurpose = value;
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
}

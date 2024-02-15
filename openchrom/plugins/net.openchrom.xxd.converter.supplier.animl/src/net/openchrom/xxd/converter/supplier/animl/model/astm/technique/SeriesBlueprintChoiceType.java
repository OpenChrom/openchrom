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

/**
 * Set of alternative Series which need to be attached to this SeriesSet.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SeriesBlueprintChoiceType", propOrder = {"documentation", "seriesBlueprint"})
public class SeriesBlueprintChoiceType {

	@XmlElement(name = "Documentation")
	protected DocumentationType documentation;
	@XmlElement(name = "SeriesBlueprint", required = true)
	protected List<SeriesBlueprintType> seriesBlueprint;
	@XmlAttribute(name = "modality")
	protected ModalityType modality;

	public DocumentationType getDocumentation() {

		return documentation;
	}

	public void setDocumentation(DocumentationType value) {

		this.documentation = value;
	}

	public List<SeriesBlueprintType> getSeriesBlueprint() {

		if(seriesBlueprint == null) {
			seriesBlueprint = new ArrayList<>();
		}
		return this.seriesBlueprint;
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
}

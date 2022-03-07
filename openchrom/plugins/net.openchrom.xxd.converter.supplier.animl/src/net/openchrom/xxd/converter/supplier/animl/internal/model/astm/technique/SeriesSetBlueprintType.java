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
package net.openchrom.xxd.converter.supplier.animl.internal.model.astm.technique;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Definition of SeriesSet that needs to be attached at this point.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SeriesSetBlueprintType", propOrder = {"documentation", "seriesBlueprintOrSeriesBlueprintChoice"})
public class SeriesSetBlueprintType {

	@XmlElement(name = "Documentation")
	protected DocumentationType documentation;
	@XmlElements({@XmlElement(name = "SeriesBlueprint", type = SeriesBlueprintType.class), @XmlElement(name = "SeriesBlueprintChoice", type = SeriesBlueprintChoiceType.class)})
	protected List<Object> seriesBlueprintOrSeriesBlueprintChoice;
	@XmlAttribute(name = "name", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String name;
	@XmlAttribute(name = "modality")
	protected ModalityType modality;

	public DocumentationType getDocumentation() {

		return documentation;
	}

	public void setDocumentation(DocumentationType value) {

		this.documentation = value;
	}

	/**
	 * Gets the value of the seriesBlueprintOrSeriesBlueprintChoice property.
	 *
	 * <p>
	 * This accessor method returns a reference to the live list,
	 * not a snapshot. Therefore any modification you make to the
	 * returned list will be present inside the JAXB object.
	 * This is why there is not a <CODE>set</CODE> method for the seriesBlueprintOrSeriesBlueprintChoice property.
	 *
	 * <p>
	 * For example, to add a new item, do as follows:
	 * <pre>
	 *    getSeriesBlueprintOrSeriesBlueprintChoice().add(newItem);
	 * </pre>
	 *
	 *
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link SeriesBlueprintType }
	 * {@link SeriesBlueprintChoiceType }
	 *
	 *
	 */
	public List<Object> getSeriesBlueprintOrSeriesBlueprintChoice() {

		if(seriesBlueprintOrSeriesBlueprintChoice == null) {
			seriesBlueprintOrSeriesBlueprintChoice = new ArrayList<Object>();
		}
		return this.seriesBlueprintOrSeriesBlueprintChoice;
	}

	public String getName() {

		return name;
	}

	public void setName(String value) {

		this.name = value;
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

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
 * Name-value pair to be stored in current Category.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParameterBlueprintType", propOrder = {"documentation", "quantity", "allowedValue"})
public class ParameterBlueprintType {

	@XmlElement(name = "Documentation")
	protected DocumentationType documentation;
	@XmlElement(name = "Quantity")
	protected List<Quantity> quantity;
	@XmlElement(name = "AllowedValue")
	protected List<AllowedValueType> allowedValue;
	@XmlAttribute(name = "name", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String name;
	@XmlAttribute(name = "parameterType", required = true)
	protected AllTypeNameList parameterType;
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

	/**
	 * Gets the value of the quantity property.
	 *
	 * <p>
	 * This accessor method returns a reference to the live list,
	 * not a snapshot. Therefore any modification you make to the
	 * returned list will be present inside the JAXB object.
	 * This is why there is not a <CODE>set</CODE> method for the quantity property.
	 *
	 * <p>
	 * For example, to add a new item, do as follows:
	 * <pre>
	 *    getQuantity().add(newItem);
	 * </pre>
	 *
	 *
	 * <p>
	 * Objects of the following types are allowed in the list
	 * {@link Quantity }
	 *
	 *
	 */
	public List<Quantity> getQuantity() {

		if(quantity == null) {
			quantity = new ArrayList<>();
		}
		return this.quantity;
	}

	/**
	 * Gets the value of the allowedValue property.
	 *
	 * <p>
	 * This accessor method returns a reference to the live list,
	 * not a snapshot. Therefore any modification you make to the
	 * returned list will be present inside the JAXB object.
	 * This is why there is not a <CODE>set</CODE> method for the allowedValue property.
	 *
	 * <p>
	 * For example, to add a new item, do as follows:
	 * <pre>
	 *    getAllowedValue().add(newItem);
	 * </pre>
	 *
	 *
	 * <p>
	 * Objects of the following types are allowed in the list
	 * {@link AllowedValueType }
	 *
	 *
	 */
	public List<AllowedValueType> getAllowedValue() {

		if(allowedValue == null) {
			allowedValue = new ArrayList<>();
		}
		return this.allowedValue;
	}

	public String getName() {

		return name;
	}

	public void setName(String value) {

		this.name = value;
	}

	public AllTypeNameList getParameterType() {

		return parameterType;
	}

	public void setParameterType(AllTypeNameList value) {

		this.parameterType = value;
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

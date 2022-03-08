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
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Description of the experimental method used.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MethodBlueprintType", propOrder = {"categoryBlueprint"})
public class MethodBlueprintType {

	@XmlElement(name = "CategoryBlueprint")
	protected List<CategoryBlueprintType> categoryBlueprint;

	/**
	 * Gets the value of the categoryBlueprint property.
	 *
	 * <p>
	 * This accessor method returns a reference to the live list,
	 * not a snapshot. Therefore any modification you make to the
	 * returned list will be present inside the JAXB object.
	 * This is why there is not a <CODE>set</CODE> method for the categoryBlueprint property.
	 *
	 * <p>
	 * For example, to add a new item, do as follows:
	 * <pre>
	 *    getCategoryBlueprint().add(newItem);
	 * </pre>
	 *
	 *
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link CategoryBlueprintType }
	 *
	 *
	 */
	public List<CategoryBlueprintType> getCategoryBlueprint() {

		if(categoryBlueprint == null) {
			categoryBlueprint = new ArrayList<CategoryBlueprintType>();
		}
		return this.categoryBlueprint;
	}
}

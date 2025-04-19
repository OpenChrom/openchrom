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
 * Philip Wenig - refactorings
 *******************************************************************************/
package net.openchrom.xxd.converter.supplier.animl.model.astm.technique;

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

	public List<CategoryBlueprintType> getCategoryBlueprint() {

		if(categoryBlueprint == null) {
			categoryBlueprint = new ArrayList<>();
		}
		return this.categoryBlueprint;
	}
}

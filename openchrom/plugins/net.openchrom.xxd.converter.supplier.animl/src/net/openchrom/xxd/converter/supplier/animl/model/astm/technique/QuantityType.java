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
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Definition of an allowable Quantity and its associated Units.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QuantityType", propOrder = {"unit", "allowedRange"})
@XmlSeeAlso({Quantity.class})
public class QuantityType {

	@XmlElement(name = "Unit")
	protected List<UnitType> unit;
	@XmlElement(name = "AllowedRange")
	protected List<AllowedRangeType> allowedRange;
	@XmlAttribute(name = "name", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String name;

	public List<UnitType> getUnit() {

		if(unit == null) {
			unit = new ArrayList<>();
		}
		return this.unit;
	}

	public List<AllowedRangeType> getAllowedRange() {

		if(allowedRange == null) {
			allowedRange = new ArrayList<>();
		}
		return this.allowedRange;
	}

	public String getName() {

		return name;
	}

	public void setName(String value) {

		this.name = value;
	}
}

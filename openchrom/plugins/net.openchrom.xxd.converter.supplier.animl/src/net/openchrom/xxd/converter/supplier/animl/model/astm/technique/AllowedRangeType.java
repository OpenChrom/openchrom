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
package net.openchrom.xxd.converter.supplier.animl.model.astm.technique;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AllowedRangeType", propOrder = {"documentation", "min", "max"})
public class AllowedRangeType {

	@XmlElement(name = "Documentation")
	protected DocumentationType documentation;
	@XmlElement(name = "Min")
	protected MinType min;
	@XmlElement(name = "Max")
	protected MaxType max;
	@XmlAttribute(name = "unit")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String unit;

	public DocumentationType getDocumentation() {

		return documentation;
	}

	public void setDocumentation(DocumentationType value) {

		this.documentation = value;
	}

	public MinType getMin() {

		return min;
	}

	public void setMin(MinType value) {

		this.min = value;
	}

	public MaxType getMax() {

		return max;
	}

	public void setMax(MaxType value) {

		this.max = value;
	}

	public String getUnit() {

		return unit;
	}

	public void setUnit(String value) {

		this.unit = value;
	}
}

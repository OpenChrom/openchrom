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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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

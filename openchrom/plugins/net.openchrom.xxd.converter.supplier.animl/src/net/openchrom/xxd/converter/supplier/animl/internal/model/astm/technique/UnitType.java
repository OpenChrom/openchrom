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
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Definition of a supported Scientific Unit.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UnitType", propOrder = {"siUnit"})
public class UnitType {

	@XmlElement(name = "SIUnit")
	protected List<SIUnitType> siUnit;
	@XmlAttribute(name = "label", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String label;

	public List<SIUnitType> getSIUnit() {

		if(siUnit == null) {
			siUnit = new ArrayList<SIUnitType>();
		}
		return this.siUnit;
	}

	public String getLabel() {

		return label;
	}

	public void setLabel(String value) {

		this.label = value;
	}
}

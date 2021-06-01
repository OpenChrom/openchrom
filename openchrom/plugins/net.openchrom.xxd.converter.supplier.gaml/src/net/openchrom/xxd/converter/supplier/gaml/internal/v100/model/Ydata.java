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
package net.openchrom.xxd.converter.supplier.gaml.internal.v100.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"parameter", "values", "peaktable"})
@XmlRootElement(name = "Ydata")
public class Ydata {

	protected List<Parameter> parameter;
	@XmlElement(required = true)
	protected Values values;
	protected List<Peaktable> peaktable;
	@XmlAttribute(name = "units", required = true)
	protected Units units;
	@XmlAttribute(name = "label")
	protected String label;

	public List<Parameter> getParameter() {

		if(parameter == null) {
			parameter = new ArrayList<Parameter>();
		}
		return this.parameter;
	}

	public Values getValues() {

		return values;
	}

	public void setValues(Values value) {

		this.values = value;
	}

	public List<Peaktable> getPeaktable() {

		if(peaktable == null) {
			peaktable = new ArrayList<Peaktable>();
		}
		return this.peaktable;
	}

	public Units getUnits() {

		return units;
	}

	public void setUnits(Units value) {

		this.units = value;
	}

	public String getLabel() {

		return label;
	}

	public void setLabel(String value) {

		this.label = value;
	}
}

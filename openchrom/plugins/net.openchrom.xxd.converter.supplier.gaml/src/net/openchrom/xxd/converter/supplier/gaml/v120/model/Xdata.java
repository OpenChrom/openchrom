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
 *******************************************************************************/
package net.openchrom.xxd.converter.supplier.gaml.v120.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"link", "parameter", "values", "altXdata", "ydata"})
@XmlRootElement(name = "Xdata")
public class Xdata {

	protected List<Link> link;
	protected List<Parameter> parameter;
	@XmlElement(required = true)
	protected Values values;
	protected List<AltXdata> altXdata;
	@XmlElement(name = "Ydata", required = true)
	protected List<Ydata> ydata;
	@XmlAttribute(name = "units", required = true)
	protected Units units;
	@XmlAttribute(name = "label")
	protected String label;
	@XmlAttribute(name = "linkid")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	protected String linkid;
	@XmlAttribute(name = "valueorder")
	protected Valueorder valueorder;

	public List<Link> getLink() {

		if(link == null) {
			link = new ArrayList<Link>();
		}
		return this.link;
	}

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

	public List<AltXdata> getAltXdata() {

		if(altXdata == null) {
			altXdata = new ArrayList<AltXdata>();
		}
		return this.altXdata;
	}

	public List<Ydata> getYdata() {

		if(ydata == null) {
			ydata = new ArrayList<Ydata>();
		}
		return this.ydata;
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

	public String getLinkid() {

		return linkid;
	}

	public void setLinkid(String value) {

		this.linkid = value;
	}

	public Valueorder getValueorder() {

		return valueorder;
	}

	public void setValueorder(Valueorder value) {

		this.valueorder = value;
	}
}

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
package net.openchrom.xxd.converter.supplier.gaml.v100.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"parameter", "coordinates", "xdata"})
@XmlRootElement(name = "trace")
public class Trace {

	protected List<Parameter> parameter;
	protected List<Coordinates> coordinates;
	@XmlElement(name = "Xdata")
	protected List<Xdata> xdata;
	@XmlAttribute(name = "name")
	protected String name;
	@XmlAttribute(name = "technique", required = true)
	protected Technique technique;

	public List<Parameter> getParameter() {

		if(parameter == null) {
			parameter = new ArrayList<Parameter>();
		}
		return this.parameter;
	}

	public List<Coordinates> getCoordinates() {

		if(coordinates == null) {
			coordinates = new ArrayList<Coordinates>();
		}
		return this.coordinates;
	}

	public List<Xdata> getXdata() {

		if(xdata == null) {
			xdata = new ArrayList<Xdata>();
		}
		return this.xdata;
	}

	public String getName() {

		return name;
	}

	public void setName(String value) {

		this.name = value;
	}

	public Technique getTechnique() {

		return technique;
	}

	public void setTechnique(Technique value) {

		this.technique = value;
	}
}

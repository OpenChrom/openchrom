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
package net.openchrom.xxd.converter.supplier.gaml.v100.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"collectdate", "parameter", "trace"})
@XmlRootElement(name = "experiment")
public class Experiment {

	protected XMLGregorianCalendar collectdate;
	protected List<Parameter> parameter;
	@XmlElement(required = true)
	protected List<Trace> trace;
	@XmlAttribute(name = "name")
	protected String name;

	public XMLGregorianCalendar getCollectdate() {

		return collectdate;
	}

	public void setCollectdate(XMLGregorianCalendar value) {

		this.collectdate = value;
	}

	public List<Parameter> getParameter() {

		if(parameter == null) {
			parameter = new ArrayList<>();
		}
		return this.parameter;
	}

	public List<Trace> getTrace() {

		if(trace == null) {
			trace = new ArrayList<>();
		}
		return this.trace;
	}

	public String getName() {

		return name;
	}

	public void setName(String value) {

		this.name = value;
	}
}

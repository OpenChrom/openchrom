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
package net.openchrom.xxd.converter.supplier.gaml.internal.v110.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"collectdate", "parameter", "trace"})
@XmlRootElement(name = "experiment")
public class Experiment {

	protected XMLGregorianCalendar collectdate;
	protected List<Parameter> parameter;
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
			parameter = new ArrayList<Parameter>();
		}
		return this.parameter;
	}

	public List<Trace> getTrace() {

		if(trace == null) {
			trace = new ArrayList<Trace>();
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

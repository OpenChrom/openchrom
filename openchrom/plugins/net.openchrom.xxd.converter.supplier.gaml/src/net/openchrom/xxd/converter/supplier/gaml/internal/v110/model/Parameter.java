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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"value"})
@XmlRootElement(name = "parameter")
public class Parameter {

	@XmlValue
	protected String value;
	@XmlAttribute(name = "group")
	protected String group;
	@XmlAttribute(name = "name", required = true)
	protected String name;
	@XmlAttribute(name = "label")
	protected String label;

	public String getValue() {

		return value;
	}

	public void setValue(String value) {

		this.value = value;
	}

	public String getGroup() {

		return group;
	}

	public void setGroup(String value) {

		this.group = value;
	}

	public String getName() {

		return name;
	}

	public void setName(String value) {

		this.name = value;
	}

	public String getLabel() {

		return label;
	}

	public void setLabel(String value) {

		this.label = value;
	}
}

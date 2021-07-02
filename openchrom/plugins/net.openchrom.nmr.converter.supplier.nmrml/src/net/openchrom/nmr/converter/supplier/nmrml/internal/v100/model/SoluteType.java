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
package net.openchrom.nmr.converter.supplier.nmrml.internal.v100.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SoluteType", propOrder = {"concentrationInSample"})
public class SoluteType {

	@XmlElement(required = true)
	protected ValueWithUnitType concentrationInSample;
	@XmlAttribute(name = "name", required = true)
	protected String name;

	public ValueWithUnitType getConcentrationInSample() {

		return concentrationInSample;
	}

	public void setConcentrationInSample(ValueWithUnitType value) {

		this.concentrationInSample = value;
	}

	public String getName() {

		return name;
	}

	public void setName(String value) {

		this.name = value;
	}
}

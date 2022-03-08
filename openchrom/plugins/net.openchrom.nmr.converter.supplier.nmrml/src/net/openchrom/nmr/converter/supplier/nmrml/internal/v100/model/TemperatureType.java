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
package net.openchrom.nmr.converter.supplier.nmrml.internal.v100.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TemperatureType")
public class TemperatureType {

	@XmlAttribute(name = "temperature", required = true)
	protected float temperature;
	@XmlAttribute(name = "temperatureUnitName", required = true)
	protected String temperatureUnitName;
	@XmlAttribute(name = "temperatureUnitID")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String temperatureUnitID;

	public float getTemperature() {

		return temperature;
	}

	public void setTemperature(float value) {

		this.temperature = value;
	}

	public String getTemperatureUnitName() {

		return temperatureUnitName;
	}

	public void setTemperatureUnitName(String value) {

		this.temperatureUnitName = value;
	}

	public String getTemperatureUnitID() {

		return temperatureUnitID;
	}

	public void setTemperatureUnitID(String value) {

		this.temperatureUnitID = value;
	}
}

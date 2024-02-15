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
package net.openchrom.xxd.converter.supplier.animl.model.astm.core;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Software used to author this.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SoftwareType", propOrder = {"manufacturer", "name", "version", "operatingSystem"})
public class SoftwareType {

	@XmlElement(name = "Manufacturer")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "token")
	protected String manufacturer;
	@XmlElement(name = "Name", required = true)
	protected String name;
	@XmlElement(name = "Version")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "token")
	protected String version;
	@XmlElement(name = "OperatingSystem")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "token")
	protected String operatingSystem;

	public String getManufacturer() {

		return manufacturer;
	}

	public void setManufacturer(String value) {

		this.manufacturer = value;
	}

	public String getName() {

		return name;
	}

	public void setName(String value) {

		this.name = value;
	}

	public String getVersion() {

		return version;
	}

	public void setVersion(String value) {

		this.version = value;
	}

	public String getOperatingSystem() {

		return operatingSystem;
	}

	public void setOperatingSystem(String value) {

		this.operatingSystem = value;
	}
}

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
package net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Device used to perform experiment.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeviceType", propOrder = {"deviceIdentifier", "manufacturer", "name", "firmwareVersion", "serialNumber"})
public class DeviceType {

	@XmlElement(name = "DeviceIdentifier")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "token")
	protected String deviceIdentifier;
	@XmlElement(name = "Manufacturer")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "token")
	protected String manufacturer;
	@XmlElement(name = "Name", required = true)
	protected String name;
	@XmlElement(name = "FirmwareVersion")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "token")
	protected String firmwareVersion;
	@XmlElement(name = "SerialNumber")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "token")
	protected String serialNumber;

	public String getDeviceIdentifier() {

		return deviceIdentifier;
	}

	public void setDeviceIdentifier(String value) {

		this.deviceIdentifier = value;
	}

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

	public String getFirmwareVersion() {

		return firmwareVersion;
	}

	public void setFirmwareVersion(String value) {

		this.firmwareVersion = value;
	}

	public String getSerialNumber() {

		return serialNumber;
	}

	public void setSerialNumber(String value) {

		this.serialNumber = value;
	}
}

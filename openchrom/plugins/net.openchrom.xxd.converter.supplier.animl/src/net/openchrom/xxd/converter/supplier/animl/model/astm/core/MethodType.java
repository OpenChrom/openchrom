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

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Describes how this Experiment was performed.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MethodType", propOrder = {"author", "device", "software", "category"})
public class MethodType {

	@XmlElement(name = "Author")
	protected AuthorType author;
	@XmlElement(name = "Device")
	protected DeviceType device;
	@XmlElement(name = "Software")
	protected SoftwareType software;
	@XmlElement(name = "Category")
	protected List<CategoryType> category;
	@XmlAttribute(name = "name")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String name;
	@XmlAttribute(name = "id")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String id;

	public AuthorType getAuthor() {

		return author;
	}

	public void setAuthor(AuthorType value) {

		this.author = value;
	}

	public DeviceType getDevice() {

		return device;
	}

	public void setDevice(DeviceType value) {

		this.device = value;
	}

	public SoftwareType getSoftware() {

		return software;
	}

	public void setSoftware(SoftwareType value) {

		this.software = value;
	}

	public List<CategoryType> getCategory() {

		if(category == null) {
			category = new ArrayList<>();
		}
		return this.category;
	}

	public String getName() {

		return name;
	}

	public void setName(String value) {

		this.name = value;
	}

	public String getId() {

		return id;
	}

	public void setId(String value) {

		this.id = value;
	}
}

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
 * Individual Sample, referenced from other parts of this AnIML document.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SampleType", propOrder = {"tagSet", "category"})
public class SampleType {

	@XmlElement(name = "TagSet")
	protected TagSetType tagSet;
	@XmlElement(name = "Category")
	protected List<CategoryType> category;
	@XmlAttribute(name = "sampleID", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String sampleID;
	@XmlAttribute(name = "barcode")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String barcode;
	@XmlAttribute(name = "comment")
	protected String comment;
	@XmlAttribute(name = "derived")
	protected Boolean derived;
	@XmlAttribute(name = "containerType")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String containerType;
	@XmlAttribute(name = "containerID")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String containerID;
	@XmlAttribute(name = "locationInContainer")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String locationInContainer;
	@XmlAttribute(name = "name", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String name;
	@XmlAttribute(name = "id")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String id;
	@XmlAttribute(name = "sourceDataLocation")
	protected String sourceDataLocation;

	public TagSetType getTagSet() {

		return tagSet;
	}

	public void setTagSet(TagSetType value) {

		this.tagSet = value;
	}

	public List<CategoryType> getCategory() {

		if(category == null) {
			category = new ArrayList<>();
		}
		return this.category;
	}

	public String getSampleID() {

		return sampleID;
	}

	public void setSampleID(String value) {

		this.sampleID = value;
	}

	public String getBarcode() {

		return barcode;
	}

	public void setBarcode(String value) {

		this.barcode = value;
	}

	public String getComment() {

		return comment;
	}

	public void setComment(String value) {

		this.comment = value;
	}

	public boolean isDerived() {

		if(derived == null) {
			return false;
		} else {
			return derived;
		}
	}

	public void setDerived(Boolean value) {

		this.derived = value;
	}

	public String getContainerType() {

		if(containerType == null) {
			return "simple";
		} else {
			return containerType;
		}
	}

	public void setContainerType(String value) {

		this.containerType = value;
	}

	public String getContainerID() {

		return containerID;
	}

	public void setContainerID(String value) {

		this.containerID = value;
	}

	public String getLocationInContainer() {

		return locationInContainer;
	}

	public void setLocationInContainer(String value) {

		this.locationInContainer = value;
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

	public String getSourceDataLocation() {

		return sourceDataLocation;
	}

	public void setSourceDataLocation(String value) {

		this.sourceDataLocation = value;
	}
}

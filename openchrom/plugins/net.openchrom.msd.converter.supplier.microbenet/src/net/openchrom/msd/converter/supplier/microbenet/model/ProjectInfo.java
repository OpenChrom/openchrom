/*******************************************************************************
 * Copyright (c) 2023 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.microbenet.model;

import javax.xml.datatype.XMLGregorianCalendar;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class ProjectInfo {

	@XmlAttribute(name = "name")
	protected String name;
	@XmlAttribute(name = "timestamp")
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar timestamp;
	@XmlAttribute(name = "instrumentId")
	protected String instrumentId;
	@XmlAttribute(name = "externalTargetId")
	protected String externalTargetId;
	@XmlAttribute(name = "uuid", required = true)
	protected String uuid;
	@XmlAttribute(name = "validationPosition")
	protected String validationPosition;
	@XmlAttribute(name = "validationResult")
	protected Boolean validationResult;
	@XmlAttribute(name = "projectTypeName")
	protected String projectTypeName;
	@XmlAttribute(name = "creator")
	protected String creator;
	@XmlAttribute(name = "totalSampleNumber")
	protected Integer totalSampleNumber;
	@XmlAttribute(name = "comment")
	protected String comment;

	public String getName() {

		return name;
	}

	public void setName(String value) {

		this.name = value;
	}

	public XMLGregorianCalendar getTimestamp() {

		return timestamp;
	}

	public void setTimestamp(XMLGregorianCalendar value) {

		this.timestamp = value;
	}

	public String getInstrumentId() {

		return instrumentId;
	}

	public void setInstrumentId(String value) {

		this.instrumentId = value;
	}

	public String getExternalTargetId() {

		return externalTargetId;
	}

	public void setExternalTargetId(String value) {

		this.externalTargetId = value;
	}

	public String getUuid() {

		return uuid;
	}

	public void setUuid(String value) {

		this.uuid = value;
	}

	public String getValidationPosition() {

		return validationPosition;
	}

	public void setValidationPosition(String value) {

		this.validationPosition = value;
	}

	public Boolean isValidationResult() {

		return validationResult;
	}

	public void setValidationResult(Boolean value) {

		this.validationResult = value;
	}

	public String getProjectTypeName() {

		return projectTypeName;
	}

	public void setProjectTypeName(String value) {

		this.projectTypeName = value;
	}

	public String getCreator() {

		return creator;
	}

	public void setCreator(String value) {

		this.creator = value;
	}

	public Integer getTotalSampleNumber() {

		return totalSampleNumber;
	}

	public void setTotalSampleNumber(Integer value) {

		this.totalSampleNumber = value;
	}

	public String getComment() {

		return comment;
	}

	public void setComment(String value) {

		this.comment = value;
	}
}
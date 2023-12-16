/*******************************************************************************
 * Copyright (c) 2021, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Philip Wenig - refactorings
 *******************************************************************************/
package net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Describes a set of changes made to the particular AnIML document by one user at a given time.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuditTrailEntryType", propOrder = {"timestamp", "author", "software", "action", "reason", "comment", "diff", "reference"})
public class AuditTrailEntryType {

	@XmlElement(name = "Timestamp", required = true)
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar timestamp;
	@XmlElement(name = "Author", required = true)
	protected AuthorType author;
	@XmlElement(name = "Software")
	protected SoftwareType software;
	@XmlElement(name = "Action", required = true)
	@XmlSchemaType(name = "token")
	protected ActionType action;
	@XmlElement(name = "Reason")
	protected String reason;
	@XmlElement(name = "Comment")
	protected String comment;
	@XmlElement(name = "Diff")
	protected List<DiffType> diff;
	@XmlElementRef(name = "Reference", namespace = "urn:org:astm:animl:schema:core:draft:0.90", type = JAXBElement.class, required = false)
	protected List<JAXBElement<Object>> reference;
	@XmlAttribute(name = "id")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String id;

	public XMLGregorianCalendar getTimestamp() {

		return timestamp;
	}

	public void setTimestamp(XMLGregorianCalendar value) {

		this.timestamp = value;
	}

	public AuthorType getAuthor() {

		return author;
	}

	public void setAuthor(AuthorType value) {

		this.author = value;
	}

	public SoftwareType getSoftware() {

		return software;
	}

	public void setSoftware(SoftwareType value) {

		this.software = value;
	}

	public ActionType getAction() {

		return action;
	}

	public void setAction(ActionType value) {

		this.action = value;
	}

	public String getReason() {

		return reason;
	}

	public void setReason(String value) {

		this.reason = value;
	}

	public String getComment() {

		return comment;
	}

	public void setComment(String value) {

		this.comment = value;
	}

	public List<DiffType> getDiff() {

		if(diff == null) {
			diff = new ArrayList<>();
		}
		return this.diff;
	}

	public List<JAXBElement<Object>> getReference() {

		if(reference == null) {
			reference = new ArrayList<>();
		}
		return this.reference;
	}

	public String getId() {

		return id;
	}

	public void setId(String value) {

		this.id = value;
	}
}
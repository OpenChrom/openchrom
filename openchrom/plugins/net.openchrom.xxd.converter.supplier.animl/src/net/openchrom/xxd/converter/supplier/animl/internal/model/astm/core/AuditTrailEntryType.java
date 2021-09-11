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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;

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

	/**
	 * Ruft den Wert der timestamp-Eigenschaft ab.
	 *
	 * @return
	 *         possible object is
	 *         {@link XMLGregorianCalendar }
	 *
	 */
	public XMLGregorianCalendar getTimestamp() {

		return timestamp;
	}

	/**
	 * Legt den Wert der timestamp-Eigenschaft fest.
	 *
	 * @param value
	 *            allowed object is
	 *            {@link XMLGregorianCalendar }
	 *
	 */
	public void setTimestamp(XMLGregorianCalendar value) {

		this.timestamp = value;
	}

	/**
	 * Ruft den Wert der author-Eigenschaft ab.
	 *
	 * @return
	 *         possible object is
	 *         {@link AuthorType }
	 *
	 */
	public AuthorType getAuthor() {

		return author;
	}

	/**
	 * Legt den Wert der author-Eigenschaft fest.
	 *
	 * @param value
	 *            allowed object is
	 *            {@link AuthorType }
	 *
	 */
	public void setAuthor(AuthorType value) {

		this.author = value;
	}

	/**
	 * Ruft den Wert der software-Eigenschaft ab.
	 *
	 * @return
	 *         possible object is
	 *         {@link SoftwareType }
	 *
	 */
	public SoftwareType getSoftware() {

		return software;
	}

	/**
	 * Legt den Wert der software-Eigenschaft fest.
	 *
	 * @param value
	 *            allowed object is
	 *            {@link SoftwareType }
	 *
	 */
	public void setSoftware(SoftwareType value) {

		this.software = value;
	}

	/**
	 * Ruft den Wert der action-Eigenschaft ab.
	 *
	 * @return
	 *         possible object is
	 *         {@link ActionType }
	 *
	 */
	public ActionType getAction() {

		return action;
	}

	/**
	 * Legt den Wert der action-Eigenschaft fest.
	 *
	 * @param value
	 *            allowed object is
	 *            {@link ActionType }
	 *
	 */
	public void setAction(ActionType value) {

		this.action = value;
	}

	/**
	 * Ruft den Wert der reason-Eigenschaft ab.
	 *
	 * @return
	 *         possible object is
	 *         {@link String }
	 *
	 */
	public String getReason() {

		return reason;
	}

	/**
	 * Legt den Wert der reason-Eigenschaft fest.
	 *
	 * @param value
	 *            allowed object is
	 *            {@link String }
	 *
	 */
	public void setReason(String value) {

		this.reason = value;
	}

	/**
	 * Ruft den Wert der comment-Eigenschaft ab.
	 *
	 * @return
	 *         possible object is
	 *         {@link String }
	 *
	 */
	public String getComment() {

		return comment;
	}

	/**
	 * Legt den Wert der comment-Eigenschaft fest.
	 *
	 * @param value
	 *            allowed object is
	 *            {@link String }
	 *
	 */
	public void setComment(String value) {

		this.comment = value;
	}

	/**
	 * Gets the value of the diff property.
	 *
	 * <p>
	 * This accessor method returns a reference to the live list,
	 * not a snapshot. Therefore any modification you make to the
	 * returned list will be present inside the JAXB object.
	 * This is why there is not a <CODE>set</CODE> method for the diff property.
	 *
	 * <p>
	 * For example, to add a new item, do as follows:
	 * <pre>
	 *    getDiff().add(newItem);
	 * </pre>
	 *
	 *
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link DiffType }
	 *
	 *
	 */
	public List<DiffType> getDiff() {

		if(diff == null) {
			diff = new ArrayList<DiffType>();
		}
		return this.diff;
	}

	/**
	 * Gets the value of the reference property.
	 *
	 * <p>
	 * This accessor method returns a reference to the live list,
	 * not a snapshot. Therefore any modification you make to the
	 * returned list will be present inside the JAXB object.
	 * This is why there is not a <CODE>set</CODE> method for the reference property.
	 *
	 * <p>
	 * For example, to add a new item, do as follows:
	 * <pre>
	 *    getReference().add(newItem);
	 * </pre>
	 *
	 *
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link JAXBElement }{@code <}{@link Object }{@code >}
	 *
	 *
	 */
	public List<JAXBElement<Object>> getReference() {

		if(reference == null) {
			reference = new ArrayList<JAXBElement<Object>>();
		}
		return this.reference;
	}

	/**
	 * Ruft den Wert der id-Eigenschaft ab.
	 *
	 * @return
	 *         possible object is
	 *         {@link String }
	 *
	 */
	public String getId() {

		return id;
	}

	/**
	 * Legt den Wert der id-Eigenschaft fest.
	 *
	 * @param value
	 *            allowed object is
	 *            {@link String }
	 *
	 */
	public void setId(String value) {

		this.id = value;
	}
}

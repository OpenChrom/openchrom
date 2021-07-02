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
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContactType")
public class ContactType extends ParamGroupType {

	@XmlAttribute(name = "id", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String id;
	@XmlAttribute(name = "fullname", required = true)
	protected String fullname;
	@XmlAttribute(name = "url")
	@XmlSchemaType(name = "anyURI")
	protected String url;
	@XmlAttribute(name = "address")
	protected String address;
	@XmlAttribute(name = "organization")
	protected String organization;
	@XmlAttribute(name = "email", required = true)
	protected String email;

	public String getId() {

		return id;
	}

	public void setId(String value) {

		this.id = value;
	}

	public String getFullname() {

		return fullname;
	}

	public void setFullname(String value) {

		this.fullname = value;
	}

	public String getUrl() {

		return url;
	}

	public void setUrl(String value) {

		this.url = value;
	}

	public String getAddress() {

		return address;
	}

	public void setAddress(String value) {

		this.address = value;
	}

	public String getOrganization() {

		return organization;
	}

	public void setOrganization(String value) {

		this.organization = value;
	}

	public String getEmail() {

		return email;
	}

	public void setEmail(String value) {

		this.email = value;
	}
}

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
package net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Information about a person, a device or a piece of software authoring AnIML files.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthorType", propOrder = {"name", "affiliation", "role", "email", "phone", "location"})
public class AuthorType {

	@XmlElement(name = "Name", required = true)
	protected String name;
	@XmlElement(name = "Affiliation")
	protected String affiliation;
	@XmlElement(name = "Role")
	protected String role;
	@XmlElement(name = "Email")
	protected String email;
	@XmlElement(name = "Phone")
	protected String phone;
	@XmlElement(name = "Location")
	protected String location;
	@XmlAttribute(name = "userType", required = true)
	protected UserTypeType userType;

	public String getName() {

		return name;
	}

	public void setName(String value) {

		this.name = value;
	}

	public String getAffiliation() {

		return affiliation;
	}

	public void setAffiliation(String value) {

		this.affiliation = value;
	}

	public String getRole() {

		return role;
	}

	public void setRole(String value) {

		this.role = value;
	}

	public String getEmail() {

		return email;
	}

	public void setEmail(String value) {

		this.email = value;
	}

	public String getPhone() {

		return phone;
	}

	public void setPhone(String value) {

		this.phone = value;
	}

	public String getLocation() {

		return location;
	}

	public void setLocation(String value) {

		this.location = value;
	}

	public UserTypeType getUserType() {

		return userType;
	}

	public void setUserType(UserTypeType value) {

		this.userType = value;
	}
}

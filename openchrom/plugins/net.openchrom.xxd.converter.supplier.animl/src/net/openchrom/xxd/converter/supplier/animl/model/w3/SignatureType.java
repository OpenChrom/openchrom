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
package net.openchrom.xxd.converter.supplier.animl.model.w3;

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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignatureType", propOrder = {"signedInfo", "signatureValue", "keyInfo", "object"})
public class SignatureType {

	@XmlElement(name = "SignedInfo", required = true)
	protected SignedInfoType signedInfo;
	@XmlElement(name = "SignatureValue", required = true)
	protected SignatureValueType signatureValue;
	@XmlElement(name = "KeyInfo")
	protected KeyInfoType keyInfo;
	@XmlElement(name = "Object")
	protected List<ObjectType> object;
	@XmlAttribute(name = "Id")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String id;

	public SignedInfoType getSignedInfo() {

		return signedInfo;
	}

	public void setSignedInfo(SignedInfoType value) {

		this.signedInfo = value;
	}

	public SignatureValueType getSignatureValue() {

		return signatureValue;
	}

	public void setSignatureValue(SignatureValueType value) {

		this.signatureValue = value;
	}

	public KeyInfoType getKeyInfo() {

		return keyInfo;
	}

	public void setKeyInfo(KeyInfoType value) {

		this.keyInfo = value;
	}

	public List<ObjectType> getObject() {

		if(object == null) {
			object = new ArrayList<>();
		}
		return this.object;
	}

	public String getId() {

		return id;
	}

	public void setId(String value) {

		this.id = value;
	}
}

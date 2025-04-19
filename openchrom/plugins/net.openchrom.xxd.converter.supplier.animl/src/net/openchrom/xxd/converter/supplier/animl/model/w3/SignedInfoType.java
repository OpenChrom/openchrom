/*******************************************************************************
 * Copyright (c) 2021, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
@XmlType(name = "SignedInfoType", propOrder = {"canonicalizationMethod", "signatureMethod", "reference"})
public class SignedInfoType {

	@XmlElement(name = "CanonicalizationMethod", required = true)
	protected CanonicalizationMethodType canonicalizationMethod;
	@XmlElement(name = "SignatureMethod", required = true)
	protected SignatureMethodType signatureMethod;
	@XmlElement(name = "Reference", required = true)
	protected List<ReferenceType> reference;
	@XmlAttribute(name = "Id")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String id;

	public CanonicalizationMethodType getCanonicalizationMethod() {

		return canonicalizationMethod;
	}

	public void setCanonicalizationMethod(CanonicalizationMethodType value) {

		this.canonicalizationMethod = value;
	}

	public SignatureMethodType getSignatureMethod() {

		return signatureMethod;
	}

	public void setSignatureMethod(SignatureMethodType value) {

		this.signatureMethod = value;
	}

	public List<ReferenceType> getReference() {

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

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
package net.openchrom.xxd.converter.supplier.animl.internal.model.w3;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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
			reference = new ArrayList<ReferenceType>();
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

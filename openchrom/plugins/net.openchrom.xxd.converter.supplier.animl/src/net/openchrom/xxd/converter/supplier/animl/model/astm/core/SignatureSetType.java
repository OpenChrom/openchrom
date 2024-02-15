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

import net.openchrom.xxd.converter.supplier.animl.model.w3.SignatureType;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Container for digital signatures covering parts of this AnIML document.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignatureSetType", propOrder = {"signature"})
public class SignatureSetType {

	@XmlElement(name = "Signature", required = true)
	protected List<SignatureType> signature;

	public List<SignatureType> getSignature() {

		if(signature == null) {
			signature = new ArrayList<>();
		}
		return this.signature;
	}
}

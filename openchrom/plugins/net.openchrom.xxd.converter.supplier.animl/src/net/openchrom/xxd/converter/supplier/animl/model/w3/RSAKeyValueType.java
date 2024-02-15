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

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RSAKeyValueType", propOrder = {"modulus", "exponent"})
public class RSAKeyValueType {

	@XmlElement(name = "Modulus", required = true)
	protected byte[] modulus;
	@XmlElement(name = "Exponent", required = true)
	protected byte[] exponent;

	public byte[] getModulus() {

		return modulus;
	}

	public void setModulus(byte[] value) {

		this.modulus = value;
	}

	public byte[] getExponent() {

		return exponent;
	}

	public void setExponent(byte[] value) {

		this.exponent = value;
	}
}

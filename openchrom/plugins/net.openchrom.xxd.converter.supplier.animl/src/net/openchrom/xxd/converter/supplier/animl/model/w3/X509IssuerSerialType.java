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

import java.math.BigInteger;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "X509IssuerSerialType", propOrder = {"x509IssuerName", "x509SerialNumber"})
public class X509IssuerSerialType {

	@XmlElement(name = "X509IssuerName", required = true)
	protected String x509IssuerName;
	@XmlElement(name = "X509SerialNumber", required = true)
	protected BigInteger x509SerialNumber;

	public String getX509IssuerName() {

		return x509IssuerName;
	}

	public void setX509IssuerName(String value) {

		this.x509IssuerName = value;
	}

	public BigInteger getX509SerialNumber() {

		return x509SerialNumber;
	}

	public void setX509SerialNumber(BigInteger value) {

		this.x509SerialNumber = value;
	}
}

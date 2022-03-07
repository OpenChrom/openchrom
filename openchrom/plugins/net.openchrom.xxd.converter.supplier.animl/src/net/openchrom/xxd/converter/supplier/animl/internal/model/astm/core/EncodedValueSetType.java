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
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;

/**
 * Multiple numeric values encoded as a base64 binary string. Uses little-endian byte order.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EncodedValueSetType", propOrder = {"value"})
public class EncodedValueSetType {

	@XmlValue
	protected byte[] value;
	@XmlAttribute(name = "startIndex")
	protected Integer startIndex;
	@XmlAttribute(name = "endIndex")
	protected Integer endIndex;

	public byte[] getValue() {

		return value;
	}

	public void setValue(byte[] value) {

		this.value = value;
	}

	public Integer getStartIndex() {

		return startIndex;
	}

	public void setStartIndex(Integer value) {

		this.startIndex = value;
	}

	public Integer getEndIndex() {

		return endIndex;
	}

	public void setEndIndex(Integer value) {

		this.endIndex = value;
	}
}

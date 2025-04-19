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
package net.openchrom.xxd.converter.supplier.animl.model.astm.core;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Multiple Values explicitly specified.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IndividualValueSetType")
public class IndividualValueSetType extends UnboundedValueType {

	@XmlAttribute(name = "startIndex")
	protected Integer startIndex;
	@XmlAttribute(name = "endIndex")
	protected Integer endIndex;

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

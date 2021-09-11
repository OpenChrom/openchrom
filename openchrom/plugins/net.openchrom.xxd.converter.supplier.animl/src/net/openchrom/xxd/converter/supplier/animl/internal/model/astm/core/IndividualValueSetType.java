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
package net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

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

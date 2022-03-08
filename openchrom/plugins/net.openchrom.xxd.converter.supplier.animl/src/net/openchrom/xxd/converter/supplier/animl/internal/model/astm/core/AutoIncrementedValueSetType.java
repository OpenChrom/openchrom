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
 * Multiple values given in form of a start value and an increment.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AutoIncrementedValueSetType", propOrder = {"startValue", "increment"})
public class AutoIncrementedValueSetType {

	@XmlElement(name = "StartValue", required = true)
	protected StartValueType startValue;
	@XmlElement(name = "Increment", required = true)
	protected IncrementType increment;
	@XmlAttribute(name = "startIndex")
	protected Integer startIndex;
	@XmlAttribute(name = "endIndex")
	protected Integer endIndex;

	public StartValueType getStartValue() {

		return startValue;
	}

	public void setStartValue(StartValueType value) {

		this.startValue = value;
	}

	public IncrementType getIncrement() {

		return increment;
	}

	public void setIncrement(IncrementType value) {

		this.increment = value;
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

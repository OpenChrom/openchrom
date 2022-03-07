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
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Reference to a data point or value range in an independent Series in the parent Result.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParentDataPointReferenceType", propOrder = {"startValue", "endValue"})
public class ParentDataPointReferenceType {

	@XmlElement(name = "StartValue", required = true)
	protected StartValueType startValue;
	@XmlElement(name = "EndValue")
	protected EndValueType endValue;
	@XmlAttribute(name = "seriesID", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String seriesID;
	@XmlAttribute(name = "id")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String id;

	public StartValueType getStartValue() {

		return startValue;
	}

	public void setStartValue(StartValueType value) {

		this.startValue = value;
	}

	public EndValueType getEndValue() {

		return endValue;
	}

	public void setEndValue(EndValueType value) {

		this.endValue = value;
	}

	public String getSeriesID() {

		return seriesID;
	}

	public void setSeriesID(String value) {

		this.seriesID = value;
	}

	public String getId() {

		return id;
	}

	public void setId(String value) {

		this.id = value;
	}
}

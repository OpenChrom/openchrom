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
package net.openchrom.nmr.converter.supplier.nmrml.internal.v100.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClusterType", propOrder = {"peakList"})
public class ClusterType {

	@XmlElement(required = true)
	protected PeakListType peakList;
	@XmlAttribute(name = "center")
	@XmlSchemaType(name = "anySimpleType")
	protected String center;
	@XmlAttribute(name = "shift")
	@XmlSchemaType(name = "anySimpleType")
	protected String shift;

	public PeakListType getPeakList() {

		return peakList;
	}

	public void setPeakList(PeakListType value) {

		this.peakList = value;
	}

	public String getCenter() {

		return center;
	}

	public void setCenter(String value) {

		this.center = value;
	}

	public String getShift() {

		return shift;
	}

	public void setShift(String value) {

		this.shift = value;
	}
}

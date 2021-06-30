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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PeakType")
public class PeakType {

	@XmlAttribute(name = "center", required = true)
	@XmlSchemaType(name = "anySimpleType")
	protected String center;
	@XmlAttribute(name = "amplitude")
	@XmlSchemaType(name = "anySimpleType")
	protected String amplitude;
	@XmlAttribute(name = "width")
	@XmlSchemaType(name = "anySimpleType")
	protected String width;

	public String getCenter() {

		return center;
	}

	public void setCenter(String value) {

		this.center = value;
	}

	public String getAmplitude() {

		return amplitude;
	}

	public void setAmplitude(String value) {

		this.amplitude = value;
	}

	public String getWidth() {

		return width;
	}

	public void setWidth(String value) {

		this.width = value;
	}
}

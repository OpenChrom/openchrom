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
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AtomType")
public class AtomType {

	@XmlAttribute(name = "id", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String id;
	@XmlAttribute(name = "elementType", required = true)
	@XmlSchemaType(name = "anySimpleType")
	protected String elementType;
	@XmlAttribute(name = "x", required = true)
	@XmlSchemaType(name = "anySimpleType")
	protected String x;
	@XmlAttribute(name = "y", required = true)
	@XmlSchemaType(name = "anySimpleType")
	protected String y;

	public String getId() {

		return id;
	}

	public void setId(String value) {

		this.id = value;
	}

	public String getElementType() {

		return elementType;
	}

	public void setElementType(String value) {

		this.elementType = value;
	}

	public String getX() {

		return x;
	}

	public void setX(String value) {

		this.x = value;
	}

	public String getY() {

		return y;
	}

	public void setY(String value) {

		this.y = value;
	}
}

/*******************************************************************************
 *  Copyright (c) 2021, 2022 Lablicate GmbH.
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

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlIDREF;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AxisWithUnitType")
public class AxisWithUnitType {

	@XmlAttribute(name = "unitAccession")
	protected String unitAccession;
	@XmlAttribute(name = "unitName")
	protected String unitName;
	@XmlAttribute(name = "unitCvRef")
	@XmlIDREF
	@XmlSchemaType(name = "IDREF")
	protected Object unitCvRef;
	@XmlAttribute(name = "startValue")
	@XmlSchemaType(name = "anySimpleType")
	protected String startValue;
	@XmlAttribute(name = "endValue")
	@XmlSchemaType(name = "anySimpleType")
	protected String endValue;

	public String getUnitAccession() {

		return unitAccession;
	}

	public void setUnitAccession(String value) {

		this.unitAccession = value;
	}

	public String getUnitName() {

		return unitName;
	}

	public void setUnitName(String value) {

		this.unitName = value;
	}

	public Object getUnitCvRef() {

		return unitCvRef;
	}

	public void setUnitCvRef(Object value) {

		this.unitCvRef = value;
	}

	public String getStartValue() {

		return startValue;
	}

	public void setStartValue(String value) {

		this.startValue = value;
	}

	public String getEndValue() {

		return endValue;
	}

	public void setEndValue(String value) {

		this.endValue = value;
	}
}

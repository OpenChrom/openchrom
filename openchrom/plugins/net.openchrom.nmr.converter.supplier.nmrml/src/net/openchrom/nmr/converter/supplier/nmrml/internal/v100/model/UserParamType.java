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
package net.openchrom.nmr.converter.supplier.nmrml.internal.v100.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlIDREF;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Uncontrolled user parameters (essentially allowing free text). Before using
 * these, one should verify whether there is an appropriate CV term available, and if so, use
 * the CV term instead
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserParamType")
public class UserParamType {

	@XmlAttribute(name = "name", required = true)
	protected String name;
	@XmlAttribute(name = "valueType")
	protected String valueType;
	@XmlAttribute(name = "value")
	protected String value;
	@XmlAttribute(name = "unitAccession")
	protected String unitAccession;
	@XmlAttribute(name = "unitName")
	protected String unitName;
	@XmlAttribute(name = "unitCvRef")
	@XmlIDREF
	@XmlSchemaType(name = "IDREF")
	protected Object unitCvRef;

	public String getName() {

		return name;
	}

	public void setName(String value) {

		this.name = value;
	}

	public String getValueType() {

		return valueType;
	}

	public void setValueType(String value) {

		this.valueType = value;
	}

	public String getValue() {

		return value;
	}

	public void setValue(String value) {

		this.value = value;
	}

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
}

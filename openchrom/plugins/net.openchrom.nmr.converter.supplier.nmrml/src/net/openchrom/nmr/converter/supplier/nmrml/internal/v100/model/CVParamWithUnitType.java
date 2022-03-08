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
@XmlType(name = "CVParamWithUnitType")
public class CVParamWithUnitType {

	@XmlAttribute(name = "cvRef", required = true)
	@XmlIDREF
	@XmlSchemaType(name = "IDREF")
	protected Object cvRef;
	@XmlAttribute(name = "accession", required = true)
	protected String accession;
	@XmlAttribute(name = "name", required = true)
	protected String name;
	@XmlAttribute(name = "value")
	protected String value;
	@XmlAttribute(name = "unitCvRef")
	@XmlIDREF
	@XmlSchemaType(name = "IDREF")
	protected Object unitCvRef;
	@XmlAttribute(name = "unitAccession")
	protected String unitAccession;
	@XmlAttribute(name = "unitName")
	protected String unitName;

	public Object getCvRef() {

		return cvRef;
	}

	public void setCvRef(Object value) {

		this.cvRef = value;
	}

	public String getAccession() {

		return accession;
	}

	public void setAccession(String value) {

		this.accession = value;
	}

	public String getName() {

		return name;
	}

	public void setName(String value) {

		this.name = value;
	}

	public String getValue() {

		return value;
	}

	public void setValue(String value) {

		this.value = value;
	}

	public Object getUnitCvRef() {

		return unitCvRef;
	}

	public void setUnitCvRef(Object value) {

		this.unitCvRef = value;
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
}

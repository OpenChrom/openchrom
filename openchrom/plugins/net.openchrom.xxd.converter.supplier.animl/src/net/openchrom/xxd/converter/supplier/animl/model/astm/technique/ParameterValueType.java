/*******************************************************************************
 * Copyright (c) 2021, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.converter.supplier.animl.model.astm.technique;

import javax.xml.datatype.XMLGregorianCalendar;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Elements for allowed values in Parameters.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParameterValueType", propOrder = {"documentation", "i", "l", "f", "d", "s", "_boolean", "dateTime", "png", "embeddedXML", "svg"})
@XmlSeeAlso({AllowedValueType.class, NumericValueType.class})
public class ParameterValueType {

	@XmlElement(name = "Documentation")
	protected DocumentationType documentation;
	@XmlElement(name = "I")
	protected Integer i;
	@XmlElement(name = "L")
	protected Long l;
	@XmlElement(name = "F")
	protected Float f;
	@XmlElement(name = "D")
	protected Double d;
	@XmlElement(name = "S")
	protected String s;
	@XmlElement(name = "Boolean")
	protected Boolean bool;
	@XmlElement(name = "DateTime")
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar dateTime;
	@XmlElement(name = "PNG")
	protected byte[] png;
	@XmlElement(name = "EmbeddedXML")
	protected String embeddedXML;
	@XmlElement(name = "SVG")
	protected String svg;

	public DocumentationType getDocumentation() {

		return documentation;
	}

	public void setDocumentation(DocumentationType value) {

		this.documentation = value;
	}

	public Integer getI() {

		return i;
	}

	public void setI(Integer value) {

		this.i = value;
	}

	public Long getL() {

		return l;
	}

	public void setL(Long value) {

		this.l = value;
	}

	public Float getF() {

		return f;
	}

	public void setF(Float value) {

		this.f = value;
	}

	public Double getD() {

		return d;
	}

	public void setD(Double value) {

		this.d = value;
	}

	public String getS() {

		return s;
	}

	public void setS(String value) {

		this.s = value;
	}

	public Boolean isBoolean() {

		return bool;
	}

	public void setBoolean(Boolean value) {

		this.bool = value;
	}

	public XMLGregorianCalendar getDateTime() {

		return dateTime;
	}

	public void setDateTime(XMLGregorianCalendar value) {

		this.dateTime = value;
	}

	public byte[] getPNG() {

		return png;
	}

	public void setPNG(byte[] value) {

		this.png = value;
	}

	public String getEmbeddedXML() {

		return embeddedXML;
	}

	public void setEmbeddedXML(String value) {

		this.embeddedXML = value;
	}

	public String getSVG() {

		return svg;
	}

	public void setSVG(String value) {

		this.svg = value;
	}
}

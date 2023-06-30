/*******************************************************************************
 * Copyright (c) 2023 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.microbenet.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"analyte"})
public class Analytes {

	@XmlElement(name = "Analyte", required = true)
	protected List<Analytes.Analyte> analyte;

	public List<Analytes.Analyte> getAnalyte() {

		if(analyte == null) {
			analyte = new ArrayList<>();
		}
		return this.analyte;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {"peaklist"})
	public static class Analyte {

		@XmlElement(name = "Peaklist", required = true)
		protected Peaklist peaklist;
		@XmlAttribute(name = "name")
		protected String name;
		@XmlAttribute(name = "internId")
		protected String internId;
		@XmlAttribute(name = "externId")
		protected String externId;
		@XmlAttribute(name = "description")
		protected String description;
		@XmlAttribute(name = "typeName")
		protected String typeName;
		@XmlAttribute(name = "timestamp")
		@XmlSchemaType(name = "dateTime")
		protected XMLGregorianCalendar timestamp;
		@XmlAttribute(name = "targetChip")
		protected String targetChip;
		@XmlAttribute(name = "targetPosition")
		protected String targetPosition;

		public Peaklist getPeaklist() {

			return peaklist;
		}

		public void setPeaklist(Peaklist value) {

			this.peaklist = value;
		}

		public String getName() {

			return name;
		}

		public void setName(String value) {

			this.name = value;
		}

		public String getInternId() {

			return internId;
		}

		public void setInternId(String value) {

			this.internId = value;
		}

		public String getExternId() {

			return externId;
		}

		public void setExternId(String value) {

			this.externId = value;
		}

		public String getDescription() {

			return description;
		}

		public void setDescription(String value) {

			this.description = value;
		}

		public String getTypeName() {

			return typeName;
		}

		public void setTypeName(String value) {

			this.typeName = value;
		}

		public XMLGregorianCalendar getTimestamp() {

			return timestamp;
		}

		public void setTimestamp(XMLGregorianCalendar value) {

			this.timestamp = value;
		}

		public String getTargetChip() {

			return targetChip;
		}

		public void setTargetChip(String value) {

			this.targetChip = value;
		}

		public String getTargetPosition() {

			return targetPosition;
		}

		public void setTargetPosition(String value) {

			this.targetPosition = value;
		}
	}
}
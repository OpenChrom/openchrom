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
package net.openchrom.xxd.converter.supplier.gaml.internal.v120.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"integrity", "parameter", "experiment"})
@XmlRootElement(name = "GAML")
public class GAML {

	protected GAML.Integrity integrity;
	protected List<Parameter> parameter;
	protected List<Experiment> experiment;
	@XmlAttribute(name = "version", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String version;
	@XmlAttribute(name = "name")
	protected String name;

	public GAML.Integrity getIntegrity() {

		return integrity;
	}

	public void setIntegrity(GAML.Integrity value) {

		this.integrity = value;
	}

	public List<Parameter> getParameter() {

		if(parameter == null) {
			parameter = new ArrayList<Parameter>();
		}
		return this.parameter;
	}

	public List<Experiment> getExperiment() {

		if(experiment == null) {
			experiment = new ArrayList<Experiment>();
		}
		return this.experiment;
	}

	public String getVersion() {

		return version;
	}

	public void setVersion(String value) {

		this.version = value;
	}

	public String getName() {

		return name;
	}

	public void setName(String value) {

		this.name = value;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {"value"})
	public static class Integrity {

		@XmlValue
		@XmlJavaTypeAdapter(HexBinaryAdapter.class)
		@XmlSchemaType(name = "hexBinary")
		protected byte[] value;
		@XmlAttribute(name = "algorithm", required = true)
		@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
		protected String algorithm;

		public byte[] getValue() {

			return value;
		}

		public void setValue(byte[] value) {

			this.value = value;
		}

		public String getAlgorithm() {

			return algorithm;
		}

		public void setAlgorithm(String value) {

			this.algorithm = value;
		}
	}
}

/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.processor.supplier.tracecompare.model;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

public class ReferenceModel {

	@XmlElement(name = "ReferenceName")
	private String referenceName = "";
	@XmlElement(name = "ReferencePath")
	private String referencePath = "";
	@XmlElement(name = "TraceModels", type = TraceModel.class)
	private Map<Double, TraceModel> traceModels = new HashMap<Double, TraceModel>();

	@XmlTransient
	public String getReferenceName() {

		return referenceName;
	}

	public void setReferenceName(String referenceName) {

		this.referenceName = referenceName;
	}

	@XmlTransient
	public String getReferencePath() {

		return referencePath;
	}

	public void setReferencePath(String referencePath) {

		this.referencePath = referencePath;
	}

	@XmlTransient
	public Map<Double, TraceModel> getTraceModels() {

		return traceModels;
	}

	public void setTraceModels(Map<Double, TraceModel> traceModels) {

		this.traceModels = traceModels;
	}
}

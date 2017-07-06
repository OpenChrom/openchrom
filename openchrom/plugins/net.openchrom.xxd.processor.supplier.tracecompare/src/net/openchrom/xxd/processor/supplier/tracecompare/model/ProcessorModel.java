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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "TraceCompare")
public class ProcessorModel {

	@XmlElement(name = "Version")
	private String version = "1.0.0.0";
	//
	@XmlElement(name = "DetectorType")
	private String detectorType;
	@XmlElement(name = "SampleName")
	private String sampleName;
	@XmlElement(name = "SamplePath")
	private String samplePath;
	@XmlElement(name = "ReferencesPath")
	private String referencesPath;
	@XmlElement(name = "CalculatedResult")
	private String calculatedResult;
	@XmlElement(name = "GeneralNotes")
	private String generalNotes;
	@XmlElement(name = "ReferenceModels", type = ReferenceModel.class)
	private Map<String, ReferenceModel> referenceModels = new HashMap<String, ReferenceModel>();

	@XmlTransient
	public String getVersion() {

		return version;
	}

	public void setVersion(String version) {

		this.version = version;
	}

	@XmlTransient
	public String getDetectorType() {

		return detectorType;
	}

	public void setDetectorType(String detectorType) {

		this.detectorType = detectorType;
	}

	@XmlTransient
	public String getSampleName() {

		return sampleName;
	}

	public void setSampleName(String sampleName) {

		this.sampleName = sampleName;
	}

	@XmlTransient
	public String getSamplePath() {

		return samplePath;
	}

	public void setSamplePath(String samplePath) {

		this.samplePath = samplePath;
	}

	@XmlTransient
	public String getReferencesPath() {

		return referencesPath;
	}

	public void setReferencesPath(String referencesPath) {

		this.referencesPath = referencesPath;
	}

	@XmlTransient
	public String getCalculatedResult() {

		return calculatedResult;
	}

	public void setCalculatedResult(String calculatedResult) {

		this.calculatedResult = calculatedResult;
	}

	@XmlTransient
	public String getGeneralNotes() {

		return generalNotes;
	}

	public void setGeneralNotes(String generalNotes) {

		this.generalNotes = generalNotes;
	}

	@XmlTransient
	public Map<String, ReferenceModel> getReferenceModels() {

		return referenceModels;
	}

	public void setReferenceModels(Map<String, ReferenceModel> referenceModels) {

		this.referenceModels = referenceModels;
	}
}

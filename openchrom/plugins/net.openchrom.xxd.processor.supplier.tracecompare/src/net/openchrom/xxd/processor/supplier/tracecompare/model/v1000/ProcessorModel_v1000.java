/*******************************************************************************
 * Copyright (c) 2017, 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.processor.supplier.tracecompare.model.v1000;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.openchrom.xxd.processor.supplier.tracecompare.model.IProcessorModel;

@XmlRootElement(name = "TraceCompare")
public class ProcessorModel_v1000 implements IProcessorModel {

	@XmlElement(name = "Version")
	private String version = "1.0.0.0";
	//
	@XmlElement(name = "DetectorType")
	private String detectorType = "";
	@XmlElement(name = "ImageDirectory")
	private String imageDirectory = "";
	@XmlElement(name = "SampleDirectory")
	private String sampleDirectory = "";
	@XmlElement(name = "ReferenceDirectory")
	private String referenceDirectory = "";
	@XmlElement(name = "CalculatedResult")
	private String calculatedResult = "";
	@XmlElement(name = "GeneralNotes")
	private String generalNotes = "";
	@XmlElement(name = "ReferenceModels", type = ReferenceModel_v1000.class)
	private Map<String, ReferenceModel_v1000> referenceModels = new HashMap<String, ReferenceModel_v1000>();

	@Override
	@XmlTransient
	public String getVersion() {

		return version;
	}

	@Override
	public void setVersion(String version) {

		this.version = version;
	}

	@Override
	@XmlTransient
	public String getDetectorType() {

		return detectorType;
	}

	@Override
	public void setDetectorType(String detectorType) {

		this.detectorType = detectorType;
	}

	@Override
	@XmlTransient
	public String getImageDirectory() {

		return imageDirectory;
	}

	@Override
	public void setImageDirectory(String imageDirectory) {

		this.imageDirectory = imageDirectory;
	}

	@Override
	@XmlTransient
	public String getSampleDirectory() {

		return sampleDirectory;
	}

	@Override
	public void setSampleDirectory(String sampleDirectory) {

		this.sampleDirectory = sampleDirectory;
	}

	@Override
	@XmlTransient
	public String getReferenceDirectory() {

		return referenceDirectory;
	}

	@Override
	public void setReferenceDirectory(String referenceDirectory) {

		this.referenceDirectory = referenceDirectory;
	}

	@Override
	@XmlTransient
	public String getCalculatedResult() {

		return calculatedResult;
	}

	@Override
	public void setCalculatedResult(String calculatedResult) {

		this.calculatedResult = calculatedResult;
	}

	@Override
	@XmlTransient
	public String getGeneralNotes() {

		return generalNotes;
	}

	@Override
	public void setGeneralNotes(String generalNotes) {

		this.generalNotes = generalNotes;
	}

	@Override
	@XmlTransient
	public Map<String, ReferenceModel_v1000> getReferenceModels() {

		return referenceModels;
	}

	@Override
	public void setReferenceModels(Map<String, ReferenceModel_v1000> referenceModels) {

		this.referenceModels = referenceModels;
	}
}

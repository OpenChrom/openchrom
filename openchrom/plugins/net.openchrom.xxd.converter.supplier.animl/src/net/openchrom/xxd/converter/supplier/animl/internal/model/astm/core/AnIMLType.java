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
package net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AnIMLType", propOrder = {"sampleSet", "experimentStepSet", "auditTrailEntrySet", "signatureSet"})
@XmlRootElement(name = "AnIML")
public class AnIMLType {

	@XmlElement(name = "SampleSet")
	protected SampleSetType sampleSet;
	@XmlElement(name = "ExperimentStepSet")
	protected ExperimentStepSetType experimentStepSet;
	@XmlElement(name = "AuditTrailEntrySet")
	protected AuditTrailEntrySetType auditTrailEntrySet;
	@XmlElement(name = "SignatureSet")
	protected SignatureSetType signatureSet;
	@XmlAttribute(name = "version", required = true)
	protected String version;

	public SampleSetType getSampleSet() {

		return sampleSet;
	}

	public void setSampleSet(SampleSetType value) {

		this.sampleSet = value;
	}

	public ExperimentStepSetType getExperimentStepSet() {

		return experimentStepSet;
	}

	public void setExperimentStepSet(ExperimentStepSetType value) {

		this.experimentStepSet = value;
	}

	public AuditTrailEntrySetType getAuditTrailEntrySet() {

		return auditTrailEntrySet;
	}

	public void setAuditTrailEntrySet(AuditTrailEntrySetType value) {

		this.auditTrailEntrySet = value;
	}

	public SignatureSetType getSignatureSet() {

		return signatureSet;
	}

	public void setSignatureSet(SignatureSetType value) {

		this.signatureSet = value;
	}

	public String getVersion() {

		if(version == null) {
			return "0.90";
		} else {
			return version;
		}
	}

	public void setVersion(String value) {

		this.version = value;
	}
}

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
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"cvList", "fileDescription", "contactList", "referenceableParamGroupList", "sourceFileList", "softwareList", "instrumentConfigurationList", "sampleList", "acquisition", "spectrumList", "spectrumAnnotationList"})
@XmlRootElement(name = "nmrML")
public class NmrMLType {

	@XmlElement(required = true)
	protected CVListType cvList;
	@XmlElement(required = true)
	protected FileDescriptionType fileDescription;
	protected ContactListType contactList;
	protected ReferenceableParamGroupListType referenceableParamGroupList;
	protected SourceFileListType sourceFileList;
	protected SoftwareListType softwareList;
	@XmlElement(required = true)
	protected InstrumentConfigurationListType instrumentConfigurationList;
	protected SampleListType sampleList;
	@XmlElement(required = true)
	protected AcquisitionType acquisition;
	protected SpectrumListType spectrumList;
	protected SpectrumAnnotationListType spectrumAnnotationList;
	@XmlAttribute(name = "version", required = true)
	protected String version;
	@XmlAttribute(name = "accession")
	protected String accession;
	@XmlAttribute(name = "accession_url")
	@XmlSchemaType(name = "anyURI")
	protected String accessionUrl;
	@XmlAttribute(name = "id")
	protected String id;

	public CVListType getCvList() {

		return cvList;
	}

	public void setCvList(CVListType value) {

		this.cvList = value;
	}

	public FileDescriptionType getFileDescription() {

		return fileDescription;
	}

	public void setFileDescription(FileDescriptionType value) {

		this.fileDescription = value;
	}

	public ContactListType getContactList() {

		return contactList;
	}

	public void setContactList(ContactListType value) {

		this.contactList = value;
	}

	public ReferenceableParamGroupListType getReferenceableParamGroupList() {

		return referenceableParamGroupList;
	}

	public void setReferenceableParamGroupList(ReferenceableParamGroupListType value) {

		this.referenceableParamGroupList = value;
	}

	public SourceFileListType getSourceFileList() {

		return sourceFileList;
	}

	public void setSourceFileList(SourceFileListType value) {

		this.sourceFileList = value;
	}

	public SoftwareListType getSoftwareList() {

		return softwareList;
	}

	public void setSoftwareList(SoftwareListType value) {

		this.softwareList = value;
	}

	public InstrumentConfigurationListType getInstrumentConfigurationList() {

		return instrumentConfigurationList;
	}

	public void setInstrumentConfigurationList(InstrumentConfigurationListType value) {

		this.instrumentConfigurationList = value;
	}

	public SampleListType getSampleList() {

		return sampleList;
	}

	public void setSampleList(SampleListType value) {

		this.sampleList = value;
	}

	public AcquisitionType getAcquisition() {

		return acquisition;
	}

	public void setAcquisition(AcquisitionType value) {

		this.acquisition = value;
	}

	public SpectrumListType getSpectrumList() {

		return spectrumList;
	}

	public void setSpectrumList(SpectrumListType value) {

		this.spectrumList = value;
	}

	public SpectrumAnnotationListType getSpectrumAnnotationList() {

		return spectrumAnnotationList;
	}

	public void setSpectrumAnnotationList(SpectrumAnnotationListType value) {

		this.spectrumAnnotationList = value;
	}

	public String getVersion() {

		return version;
	}

	public void setVersion(String value) {

		this.version = value;
	}

	public String getAccession() {

		return accession;
	}

	public void setAccession(String value) {

		this.accession = value;
	}

	public String getAccessionUrl() {

		return accessionUrl;
	}

	public void setAccessionUrl(String value) {

		this.accessionUrl = value;
	}

	public String getId() {

		return id;
	}

	public void setId(String value) {

		this.id = value;
	}
}

/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "MassShiftDetector")
public class ProcessorModel_v1000 implements IProcessorModel {

	@XmlElement(name = "Version")
	private String version = "1.0.0.0";
	//
	@XmlElement(name = "ReferenceChromatogramPath")
	private String referenceChromatogramPath;
	@XmlElement(name = "IsotopeChromatogramPath")
	private String isotopeChromatogramPath;
	@XmlElement(name = "StartShiftLevel")
	private int startShiftLevel;
	@XmlElement(name = "StopShiftLevel")
	private int stopShiftLevel;
	@XmlElement(name = "Notes")
	private String notes;
	@XmlElement(name = "Description")
	private String description;
	@XmlElementWrapper(name = "ScanMarkers")
	@XmlElement(name = "ScanMarker", type = ScanMarker_v1000.class)
	private List<ScanMarker_v1000> scanMarker;

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
	public String getReferenceChromatogramPath() {

		return referenceChromatogramPath;
	}

	@Override
	public void setReferenceChromatogramPath(String referenceChromatogramPath) {

		this.referenceChromatogramPath = referenceChromatogramPath;
	}

	@Override
	@XmlTransient
	public String getIsotopeChromatogramPath() {

		return isotopeChromatogramPath;
	}

	@Override
	public void setIsotopeChromatogramPath(String isotopeChromatogramPath) {

		this.isotopeChromatogramPath = isotopeChromatogramPath;
	}

	@Override
	@XmlTransient
	public int getStartShiftLevel() {

		return startShiftLevel;
	}

	@Override
	public void setStartShiftLevel(int startShiftLevel) {

		this.startShiftLevel = startShiftLevel;
	}

	@Override
	@XmlTransient
	public int getStopShiftLevel() {

		return stopShiftLevel;
	}

	@Override
	public void setStopShiftLevel(int stopShiftLevel) {

		this.stopShiftLevel = stopShiftLevel;
	}

	@Override
	@XmlTransient
	public String getNotes() {

		return notes;
	}

	@Override
	public void setNotes(String notes) {

		this.notes = notes;
	}

	@Override
	@XmlTransient
	public String getDescription() {

		return description;
	}

	@Override
	public void setDescription(String description) {

		this.description = description;
	}

	@Override
	@XmlTransient
	public List<ScanMarker_v1000> getScanMarker() {

		return scanMarker;
	}

	@Override
	public void setScanMarker(List<ScanMarker_v1000> scanMarker) {

		this.scanMarker = scanMarker;
	}
}

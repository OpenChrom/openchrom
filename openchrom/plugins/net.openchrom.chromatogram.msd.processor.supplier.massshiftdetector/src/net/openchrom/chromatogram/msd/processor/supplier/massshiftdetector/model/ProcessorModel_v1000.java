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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "MassShiftDetector")
public class ProcessorModel_v1000 {

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

	@XmlTransient
	public String getVersion() {

		return version;
	}

	public void setVersion(String version) {

		this.version = version;
	}

	@XmlTransient
	public String getReferenceChromatogramPath() {

		return referenceChromatogramPath;
	}

	public void setReferenceChromatogramPath(String referenceChromatogramPath) {

		this.referenceChromatogramPath = referenceChromatogramPath;
	}

	@XmlTransient
	public String getIsotopeChromatogramPath() {

		return isotopeChromatogramPath;
	}

	public void setIsotopeChromatogramPath(String isotopeChromatogramPath) {

		this.isotopeChromatogramPath = isotopeChromatogramPath;
	}

	@XmlTransient
	public int getStartShiftLevel() {

		return startShiftLevel;
	}

	public void setStartShiftLevel(int startShiftLevel) {

		this.startShiftLevel = startShiftLevel;
	}

	@XmlTransient
	public int getStopShiftLevel() {

		return stopShiftLevel;
	}

	public void setStopShiftLevel(int stopShiftLevel) {

		this.stopShiftLevel = stopShiftLevel;
	}

	@XmlTransient
	public String getNotes() {

		return notes;
	}

	public void setNotes(String notes) {

		this.notes = notes;
	}

	@XmlTransient
	public String getDescription() {

		return description;
	}

	public void setDescription(String description) {

		this.description = description;
	}
}

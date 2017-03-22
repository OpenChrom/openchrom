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
public class ProcessorModel {

	@XmlElement(name = "C12ChromatogramPath")
	private String c12ChromatogramPath;
	@XmlElement(name = "C13ChromatogramPath")
	private String c13ChromatogramPath;
	@XmlElement(name = "Level")
	private int level;
	@XmlElement(name = "Notes")
	private String notes;
	@XmlElement(name = "Description")
	private String description;

	@XmlTransient
	public String getC12ChromatogramPath() {

		return c12ChromatogramPath;
	}

	public void setC12ChromatogramPath(String c12ChromatogramPath) {

		this.c12ChromatogramPath = c12ChromatogramPath;
	}

	@XmlTransient
	public String getC13ChromatogramPath() {

		return c13ChromatogramPath;
	}

	public void setC13ChromatogramPath(String c13ChromatogramPath) {

		this.c13ChromatogramPath = c13ChromatogramPath;
	}

	@XmlTransient
	public int getLevel() {

		return level;
	}

	public void setLevel(int level) {

		this.level = level;
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

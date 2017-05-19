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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "TraceCompare")
public class ProcessorModel {

	@XmlElement(name = "ChromatogramName")
	private String chromatogramName;
	@XmlElement(name = "ChromatogramPath")
	private String chromatogramPath;
	@XmlElement(name = "Notes")
	private String notes;

	@XmlTransient
	public String getChromatogramName() {

		return chromatogramName;
	}

	public void setChromatogramName(String chromatogramName) {

		this.chromatogramName = chromatogramName;
	}

	@XmlTransient
	public String getChromatogramPath() {

		return chromatogramPath;
	}

	public void setChromatogramPath(String chromatogramPath) {

		this.chromatogramPath = chromatogramPath;
	}

	@XmlTransient
	public String getNotes() {

		return notes;
	}

	public void setNotes(String notes) {

		this.notes = notes;
	}
}

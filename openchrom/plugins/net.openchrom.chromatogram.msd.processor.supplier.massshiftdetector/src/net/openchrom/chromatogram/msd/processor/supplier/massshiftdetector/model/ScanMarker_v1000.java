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

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;

public class ScanMarker_v1000 implements IScanMarker {

	@XmlElement(name = "ScanNumber")
	private int scanNumber;
	@XmlElementWrapper(name = "MassShifts")
	@XmlElement(name = "MassShift", type = MassShift_v1000.class)
	private Set<MassShift_v1000> massShifts;
	@XmlElement(name = "Validated")
	private boolean validated;

	public ScanMarker_v1000() {
		this.massShifts = new HashSet<MassShift_v1000>();
	}

	public ScanMarker_v1000(int scan) {
		this.scanNumber = scan;
		this.massShifts = new HashSet<MassShift_v1000>();
	}

	@Override
	@XmlTransient
	public int getScanNumber() {

		return scanNumber;
	}

	@Override
	public void setScanNumber(int scanNumber) {

		this.scanNumber = scanNumber;
	}

	@Override
	@XmlTransient
	public Set<MassShift_v1000> getMassShifts() {

		return massShifts;
	}

	@Override
	public void setMassShifts(Set<MassShift_v1000> massShifts) {

		this.massShifts = massShifts;
	}

	@Override
	@XmlTransient
	public boolean isValidated() {

		return validated;
	}

	@Override
	public void setValidated(boolean validated) {

		this.validated = validated;
	}
}

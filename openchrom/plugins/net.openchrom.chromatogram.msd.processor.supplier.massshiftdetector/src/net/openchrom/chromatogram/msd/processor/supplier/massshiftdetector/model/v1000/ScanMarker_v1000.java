/*******************************************************************************
 * Copyright (c) 2017, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.v1000;

import java.util.HashSet;
import java.util.Set;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.IMassShift;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.IScanMarker;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlTransient;

public class ScanMarker_v1000 implements IScanMarker {

	@XmlElement(name = "ScanNumber")
	private int scanNumber;
	@XmlElement(name = "RetentionTimeReference")
	private int retentionTimeReference;
	@XmlElement(name = "RetentionTimeIsotope")
	private int retentionTimeIsotope;
	@XmlElementWrapper(name = "MassShifts")
	@XmlElement(name = "MassShift", type = MassShift_v1000.class)
	private Set<IMassShift> massShifts;
	@XmlElement(name = "Validated")
	private boolean validated;

	public ScanMarker_v1000() {

		this.massShifts = new HashSet<>();
	}

	public ScanMarker_v1000(int scan) {

		this.scanNumber = scan;
		this.massShifts = new HashSet<>();
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
	public int getRetentionTimeReference() {

		return retentionTimeReference;
	}

	@Override
	public void setRetentionTimeReference(int retentionTimeReference) {

		this.retentionTimeReference = retentionTimeReference;
	}

	@Override
	@XmlTransient
	public int getRetentionTimeIsotope() {

		return retentionTimeIsotope;
	}

	@Override
	public void setRetentionTimeIsotope(int retentionTimeIsotope) {

		this.retentionTimeIsotope = retentionTimeIsotope;
	}

	@Override
	@XmlTransient
	public Set<IMassShift> getMassShifts() {

		return massShifts;
	}

	@Override
	public void setMassShifts(Set<IMassShift> massShifts) {

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

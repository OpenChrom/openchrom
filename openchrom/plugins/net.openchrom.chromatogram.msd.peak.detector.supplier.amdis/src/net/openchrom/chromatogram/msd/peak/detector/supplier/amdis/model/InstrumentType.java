/*******************************************************************************
 * Copyright (c) 2020, 2025 Lablicate GmbH.
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
package net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.model;

import org.eclipse.chemclipse.support.text.ILabel;

public enum InstrumentType implements ILabel {

	QUADRUPOLE("Quadrupole", "0"), //
	ION_TRAP("Ion Trap", "1"), //
	MAGNETIC_SECTOR("Magnetic Sector", "2"), //
	SIM("SIM", "3");

	private String label = "";
	private String value = "";

	private InstrumentType(String label, String value) {

		this.label = label;
		this.value = value;
	}

	@Override
	public String label() {

		return label;
	}

	public String value() {

		return value;
	}

	public static String[][] getItems() {

		return new String[][]{//
				{QUADRUPOLE.label(), QUADRUPOLE.value()}, //
				{ION_TRAP.label(), ION_TRAP.value()}, //
				{MAGNETIC_SECTOR.label(), MAGNETIC_SECTOR.value()}, //
				{SIM.label(), SIM.value()}//
		};
	}
}
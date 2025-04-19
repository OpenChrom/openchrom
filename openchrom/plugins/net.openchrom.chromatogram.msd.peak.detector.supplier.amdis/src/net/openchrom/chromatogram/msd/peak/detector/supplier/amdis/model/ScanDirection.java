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

public enum ScanDirection implements ILabel {

	HIGH_TO_LOW("High to Low", "-1"), //
	NONE("None", "0"), //
	LOW_TO_HIGH("Low to High", "1");

	private String label = "";
	private String value = "";

	private ScanDirection(String label, String value) {

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
				{HIGH_TO_LOW.label(), HIGH_TO_LOW.value()}, //
				{NONE.label(), NONE.value()}, //
				{LOW_TO_HIGH.label(), LOW_TO_HIGH.value()}//
		};
	}
}
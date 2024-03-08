/*******************************************************************************
 * Copyright (c) 2020, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.model;

import org.eclipse.chemclipse.support.text.ILabel;

public enum AdjacentPeakSubtraction implements ILabel {

	TWO("Two", "0"), //
	ONE("One", "1"), //
	NONE("None", "2");

	private String label = "";
	private String value = "";

	private AdjacentPeakSubtraction(String label, String value) {

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
				{TWO.label(), TWO.value()}, //
				{ONE.label(), ONE.value()}, //
				{NONE.label(), NONE.value()}//
		};
	}
}
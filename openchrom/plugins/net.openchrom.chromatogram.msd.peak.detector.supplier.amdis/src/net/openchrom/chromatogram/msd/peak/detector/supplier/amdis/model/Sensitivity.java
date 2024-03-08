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

public enum Sensitivity implements ILabel {

	VERY_HIGH("Very High", "60"), //
	HIGH("High", "30"), //
	MEDIUM("Medium", "10"), //
	LOW("Low", "3"), //
	VERY_LOW("Very Low", "1");

	private String label = "";
	private String value = "";

	private Sensitivity(String label, String value) {

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
				{VERY_HIGH.label(), VERY_HIGH.value()}, //
				{HIGH.label(), HIGH.value()}, //
				{MEDIUM.label(), MEDIUM.value()}, //
				{LOW.label(), LOW.value()}, //
				{VERY_LOW.label(), VERY_LOW.value()} //
		};
	}
}
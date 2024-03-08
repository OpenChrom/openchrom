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

public enum Threshold implements ILabel {

	HIGH("High", "3"), //
	MEDIUM("Medium", "2"), //
	LOW("Low", "1"), //
	OFF("Off", "0");

	private String label = "";
	private String value = "";

	private Threshold(String label, String value) {

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
				{HIGH.label(), HIGH.value()}, //
				{MEDIUM.label(), MEDIUM.value()}, //
				{LOW.label(), LOW.value()}, //
				{OFF.label(), OFF.value()}//
		};
	}
}

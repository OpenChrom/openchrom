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

public enum Option implements ILabel {

	YES("Yes", "1"), //
	NO("No", "0");

	private String label = "";
	private String value = "";

	private Option(String label, String value) {

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
				{YES.label(), YES.value()}, //
				{NO.label(), NO.value()}//
		};
	}
}
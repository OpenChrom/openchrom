/*******************************************************************************
 * Copyright (c) 2019, 2025 Lablicate GmbH.
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
package net.openchrom.xxd.classifier.supplier.ratios.model.qual;

import org.eclipse.chemclipse.support.text.ILabel;

public enum PeakQuality implements ILabel {
	VERY_GOOD("++"), //
	GOOD("+"), //
	ACCEPTABLE("~"), //
	BAD("-"), //
	VERY_BAD("--"), //
	NONE("");

	private String label = "";

	private PeakQuality(String label) {

		this.label = label;
	}

	public String label() {

		return label;
	}

	public static String[][] getOptions() {

		return ILabel.getOptions(values());
	}
}
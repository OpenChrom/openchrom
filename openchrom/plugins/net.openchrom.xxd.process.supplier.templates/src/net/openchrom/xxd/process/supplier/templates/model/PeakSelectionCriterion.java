/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
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
package net.openchrom.xxd.process.supplier.templates.model;

import org.eclipse.chemclipse.support.text.ILabel;

public enum PeakSelectionCriterion implements ILabel {

	AREA_LOWEST("Area (Lowest)"), //
	AREA_HIGHEST("Area (Highest)"), //
	HEIGHT_LOWEST("Height (Lowest)"), //
	HEIGHT_HIGHEST("Height (Highest)"), //
	RETENTION_TIME_START("Retention Time (Start)"), //
	RETENTION_TIME_STOP("Retention Time (Stop)"); //

	private String label = "";

	private PeakSelectionCriterion(String label) {

		this.label = label;
	}

	@Override
	public String label() {

		return label;
	}

	public static String[][] getOptions() {

		return ILabel.getOptions(values());
	}
}
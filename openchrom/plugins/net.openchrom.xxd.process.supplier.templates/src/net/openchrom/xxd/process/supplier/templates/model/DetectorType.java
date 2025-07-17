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
package net.openchrom.xxd.process.supplier.templates.model;

import org.eclipse.chemclipse.model.core.PeakType;
import org.eclipse.chemclipse.support.text.ILabel;

public enum DetectorType implements ILabel {

	VV("VV (Valley)"), //
	BB("BB (Baseline)"), //
	CB("CB (Chromatogram Baseline)"), //
	MM("MM (Manual)");

	private String label = "";

	private DetectorType(String label) {

		this.label = label;
	}

	public String label() {

		return label;
	}

	public static String[][] getOptions() {

		return ILabel.getOptions(values());
	}

	public static PeakType translate(DetectorType detectorType) {

		if(DetectorType.BB.equals(detectorType)) {
			return PeakType.BB;
		} else if(DetectorType.MM.equals(detectorType)) {
			return PeakType.MM;
		} else if(DetectorType.CB.equals(detectorType)) {
			return PeakType.CB;
		} else {
			return PeakType.VV;
		}
	}
}
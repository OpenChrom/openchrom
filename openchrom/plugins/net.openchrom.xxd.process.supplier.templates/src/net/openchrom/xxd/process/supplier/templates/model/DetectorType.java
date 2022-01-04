/*******************************************************************************
 * Copyright (c) 2020, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.model;

import org.eclipse.chemclipse.model.core.PeakType;
import org.eclipse.chemclipse.support.text.ILabel;

public enum DetectorType implements ILabel {
	VV("Valley-Valley"), //
	BB("Baseline-Baseline"), //
	MM("Manual-Manual");

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
		} else {
			return PeakType.VV;
		}
	}
}
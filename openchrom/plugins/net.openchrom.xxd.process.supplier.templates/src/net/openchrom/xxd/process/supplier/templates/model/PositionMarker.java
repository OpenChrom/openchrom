/*******************************************************************************
 * Copyright (c) 2022 Lablicate GmbH.
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

import org.eclipse.chemclipse.support.text.ILabel;

public enum PositionMarker implements ILabel {

	RT_MIN_START("Start Retention Time [min]"), //
	RT_MIN_STOP("Stop Retention Time [min]"), //
	RT_MS_START("Start Retention Time [ms]"), //
	RT_MS_STOP("Stop Retention Time [ms]"), //
	RI_START("Start Retention Index"), //
	RI_STOP("Stop Retention Index"); //

	private String label = "";

	private PositionMarker(String label) {

		this.label = label;
	}

	public String label() {

		return label;
	}

	public static String[][] getOptions() {

		return ILabel.getOptions(values());
	}
}
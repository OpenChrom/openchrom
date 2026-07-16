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

public enum PeakSelectionChoice implements ILabel {

	/*
	 * I assume that if more than 5 options
	 * are needed, that the initial template
	 * selection was set too wide.
	 */
	FIRST("1st", 0), //
	SECOND("2nd", 1), //
	THIRD("3rd", 2), //
	FOURTH("4th", 3), //
	FIFTH("5th", 4); //

	private String label = "";
	private int index = 0;

	private PeakSelectionChoice(String label, int index) {

		this.label = label;
		this.index = index;
	}

	@Override
	public String label() {

		return label;
	}

	public int index() {

		return index;
	}

	public static String[][] getOptions() {

		return ILabel.getOptions(values());
	}
}
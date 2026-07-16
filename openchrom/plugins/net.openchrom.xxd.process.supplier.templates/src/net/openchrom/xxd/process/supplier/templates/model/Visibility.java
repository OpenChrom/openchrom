/*******************************************************************************
 * Copyright (c) 2020, 2026 Lablicate GmbH.
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

public enum Visibility implements ILabel {

	TIC("Total Intensity"), //
	TRACE("Trace"), //
	BOTH("Total Intensity and Trace"); //

	private String label = "";

	private Visibility(String label) {

		this.label = label;
	}

	@Override
	public String label() {

		return label;
	}

	public static String[][] getOptions() {

		return ILabel.getOptions(values());
	}

	public static boolean isTIC(Visibility visibility) {

		return TIC.equals(visibility) || BOTH.equals(visibility);
	}

	public static boolean isTRACE(Visibility visibility) {

		return TRACE.equals(visibility) || BOTH.equals(visibility);
	}
}
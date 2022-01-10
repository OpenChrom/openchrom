/*******************************************************************************
 * Copyright (c) 2022 Lablicate GmbH.
 *
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.xxd.report.supplier.csv.model;

import org.eclipse.chemclipse.support.text.ILabel;

public enum Delimiter implements ILabel {

	COMMA(',', "Comma"), //
	SEMICOLON(';', "Semicolon"), //
	TAB('\t', "Tab");

	private char character;
	private String label;

	private Delimiter(char character, String label) {

		this.character = character;
		this.label = label;
	}

	public char getCharacter() {

		return character;
	}

	@Override
	public String label() {

		return label;
	}

	public static String[][] getOptions() {

		return ILabel.getOptions(values());
	}
}
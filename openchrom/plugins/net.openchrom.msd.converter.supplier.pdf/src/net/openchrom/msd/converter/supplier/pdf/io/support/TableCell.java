/*******************************************************************************
 * Copyright (c) 2016, 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.pdf.io.support;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class TableCell {

	public static int BORDER_NONE = 0;
	public static int BORDER_LEFT = 1 << 0;
	public static int BORDER_RIGHT = 1 << 1;
	public static int BORDER_TOP = 1 << 2;
	public static int BORDER_BOTTOM = 1 << 3;
	public static int BORDER_ALL = BORDER_LEFT | BORDER_RIGHT | BORDER_TOP | BORDER_BOTTOM;
	//
	private String text;
	private float width;
	//
	private PDFont font = PDType1Font.HELVETICA;
	private float fontSize = 12.0f;
	private int border = BORDER_NONE;

	public TableCell(String text, float width) {
		this(text, width, BORDER_NONE);
	}

	public TableCell(String text, float width, int border) {
		this.text = text;
		this.width = width;
		this.border = border;
	}

	public String getText() {

		return text;
	}

	public float getWidth() {

		return width;
	}

	public PDFont getFont() {

		return font;
	}

	public TableCell setFont(PDFont font) {

		this.font = font;
		return this;
	}

	public float getFontSize() {

		return fontSize;
	}

	public TableCell setFontSize(float fontSize) {

		this.fontSize = fontSize;
		return this;
	}

	public boolean isBorderSet() {

		return border > BORDER_NONE;
	}

	/**
	 * Check for BORDER_LEFT, ...
	 * 
	 * @param constant
	 * @return boolean
	 */
	public boolean isBorderSet(int constant) {

		return (border & constant) == constant;
	}

	public TableCell setBorder(int border) {

		this.border = border;
		return this;
	}
}

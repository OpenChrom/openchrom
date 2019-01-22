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

import java.awt.Color;

import org.apache.pdfbox.pdmodel.font.PDFont;

public class CellElement extends TextElement {

	public static int BORDER_NONE = 0;
	public static int BORDER_LEFT = 1 << 0;
	public static int BORDER_RIGHT = 1 << 1;
	public static int BORDER_TOP = 1 << 2;
	public static int BORDER_BOTTOM = 1 << 3;
	public static int BORDER_ALL = BORDER_LEFT | BORDER_RIGHT | BORDER_TOP | BORDER_BOTTOM;
	//
	private int border = BORDER_NONE;

	public CellElement(String text, float width) {
		this(text, width, BORDER_NONE);
	}

	public CellElement(String text, float width, int border) {
		super(-1, -1, width);
		setText(text);
		this.border = border;
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

	public CellElement setBorder(int border) {

		this.border = border;
		return this;
	}

	@Override
	public CellElement setFont(PDFont font) {

		super.setFont(font);
		return this;
	}

	@Override
	public CellElement setFontSize(float fontSize) {

		super.setFontSize(fontSize);
		return this;
	}

	@Override
	public CellElement setColor(Color color) {

		super.setColor(color);
		return this;
	}

	@Override
	public CellElement setMinHeight(float minHeight) {

		super.setMinHeight(minHeight);
		return this;
	}

	@Override
	public CellElement setMaxWidth(float maxWidth) {

		super.setMaxWidth(maxWidth);
		return this;
	}

	@Override
	public CellElement setText(String text) {

		super.setText(text);
		return this;
	}

	@Override
	public CellElement setTextOption(TextOption textOption) {

		super.setTextOption(textOption);
		return this;
	}

	@Override
	public CellElement setReferenceX(ReferenceX referenceX) {

		super.setReferenceX(referenceX);
		return this;
	}

	@Override
	public CellElement setReferenceY(ReferenceY referenceY) {

		super.setReferenceY(referenceY);
		return this;
	}

	@Override
	public CellElement setX(float x) {

		super.setX(x);
		return this;
	}

	@Override
	public CellElement setY(float y) {

		super.setY(y);
		return this;
	}
}

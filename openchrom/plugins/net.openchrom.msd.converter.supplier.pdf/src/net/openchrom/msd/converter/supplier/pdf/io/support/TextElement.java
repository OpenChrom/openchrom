/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
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

public class TextElement {

	private PDFont font = PDType1Font.HELVETICA;
	private float fontSize = 12;
	private float x = 0.0f;
	private float y = 0.0f;
	private float maxWidth = Float.MAX_VALUE;
	private String text = "";
	private AlignHorizontal alignHorizontal = AlignHorizontal.LEFT;
	private AlignVertical alignVertical = AlignVertical.TOP;

	public PDFont getFont() {

		return font;
	}

	public TextElement setFont(PDFont font) {

		this.font = font;
		return this;
	}

	public float getFontSize() {

		return fontSize;
	}

	public TextElement setFontSize(float fontSize) {

		this.fontSize = fontSize;
		return this;
	}

	public float getX() {

		return x;
	}

	public TextElement setX(float x) {

		this.x = x;
		return this;
	}

	public float getY() {

		return y;
	}

	public TextElement setY(float y) {

		this.y = y;
		return this;
	}

	public float getMaxWidth() {

		return maxWidth;
	}

	public TextElement setMaxWidth(float maxWidth) {

		this.maxWidth = maxWidth;
		return this;
	}

	public String getText() {

		return text;
	}

	public TextElement setText(String text) {

		this.text = text;
		return this;
	}

	public AlignHorizontal getAlignHorizontal() {

		return alignHorizontal;
	}

	public TextElement setAlignHorizontal(AlignHorizontal alignHorizontal) {

		this.alignHorizontal = alignHorizontal;
		return this;
	}

	public AlignVertical getAlignVertical() {

		return alignVertical;
	}

	public TextElement setAlignVertical(AlignVertical alignVertical) {

		this.alignVertical = alignVertical;
		return this;
	}
}

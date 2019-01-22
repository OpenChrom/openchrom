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

import java.awt.Color;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class TextElement extends AbstractReferenceElement<TextElement> {

	private PDFont font = PDType1Font.HELVETICA;
	private float fontSize = 12; // pt
	private Color color = Color.BLACK;
	private float minHeight = -1;
	private float maxWidth = Float.MAX_VALUE;
	private String text = "";
	private TextOption textOption = TextOption.NONE;

	public TextElement(float x, float y, float maxWidth) {
		setX(x);
		setY(y);
		this.maxWidth = maxWidth;
	}

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

	public Color getColor() {

		return color;
	}

	public TextElement setColor(Color color) {

		this.color = color;
		return this;
	}

	public float getMinHeight() {

		return minHeight;
	}

	public TextElement setMinHeight(float minHeight) {

		this.minHeight = minHeight;
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

	public TextOption getTextOption() {

		return textOption;
	}

	public TextElement setTextOption(TextOption textOption) {

		this.textOption = textOption;
		return this;
	}
}

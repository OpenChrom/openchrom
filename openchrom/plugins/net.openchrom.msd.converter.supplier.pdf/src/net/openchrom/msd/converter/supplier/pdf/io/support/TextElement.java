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

public class TextElement extends AbstractReferenceElement<TextElement> {

	private PDFont font = PDType1Font.HELVETICA;
	private float fontSize = 12;
	private float maxWidth = Float.MAX_VALUE;
	private String text = "";

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
}

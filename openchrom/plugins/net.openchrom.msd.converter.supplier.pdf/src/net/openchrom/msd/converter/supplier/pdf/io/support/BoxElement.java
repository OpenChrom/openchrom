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

public class BoxElement extends AbstractReferenceElement<BoxElement> {

	private Color color = Color.BLACK;
	private float width = 0.0f;
	private float height = 0.0f;

	public BoxElement(float x, float y, float width, float height) {
		setX(x);
		setY(y);
		this.width = width;
		this.height = height;
	}

	public Color getColor() {

		return color;
	}

	public BoxElement setColor(Color color) {

		this.color = color;
		return this;
	}

	public float getWidth() {

		return width;
	}

	public BoxElement setWidth(float width) {

		this.width = width;
		return this;
	}

	public float getHeight() {

		return height;
	}

	public BoxElement setHeight(float height) {

		this.height = height;
		return this;
	}
}

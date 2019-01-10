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

public class LineElement extends AbstractElement<LineElement> {

	private Color color = Color.BLACK;
	private float width = 1.0f;
	private float x1 = 0.0f;
	private float y1 = 0.0f;

	public LineElement(float x, float y, float x1, float y1) {
		setX(x);
		setY(y);
		setX1(x1);
		setY1(y1);
	}

	public Color getColor() {

		return color;
	}

	public LineElement setColor(Color color) {

		this.color = color;
		return this;
	}

	public float getWidth() {

		return width;
	}

	public LineElement setWidth(float width) {

		this.width = width;
		return this;
	}

	public float getX1() {

		return x1;
	}

	public LineElement setX1(float x1) {

		this.x1 = x1;
		return this;
	}

	public float getY1() {

		return y1;
	}

	public LineElement setY1(float y1) {

		this.y1 = y1;
		return this;
	}
}

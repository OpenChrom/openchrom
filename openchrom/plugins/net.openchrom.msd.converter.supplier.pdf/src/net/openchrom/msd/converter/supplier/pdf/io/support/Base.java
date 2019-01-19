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

/**
 * Defines the position of (0,0) and offers the conversion options.
 */
public enum Base {
	BOTTOM_LEFT(0.0f, 0.0f, false, false), // Default by PDFBox
	TOP_LEFT(0.0f, 1.0f, false, true); // Convenient for reports

	/*
	 * The factor of width and height.
	 */
	private float factorWidth;
	private float factorHeight;
	private boolean subtractWidth;
	private boolean subtractHeight;

	private Base(float factorWidth, float factorHeight, boolean subtractWidth, boolean subtractHeight) {
		this.factorWidth = factorWidth;
		this.factorHeight = factorHeight;
		this.subtractWidth = subtractWidth;
		this.subtractHeight = subtractHeight;
	}

	public float getFactorWidth() {

		return factorWidth;
	}

	public float getFactorHeight() {

		return factorHeight;
	}

	public boolean isSubtractWidth() {

		return subtractWidth;
	}

	public boolean isSubtractHeight() {

		return subtractHeight;
	}
}

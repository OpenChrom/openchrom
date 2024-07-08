/*******************************************************************************
 * Copyright (c) 2023, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.swtchart.extension.export.vectorgraphics.model;

import org.eclipse.chemclipse.support.text.ILabel;

import de.erichseifert.vectorgraphics2d.util.PageSize;

public enum PageSizeOption implements ILabel {

	/*
	 * The full landscape is used to compensate that int
	 * values can be plotted only.
	 */
	FULL_LANDSCAPE("Full Size (Landscape)", new PageSize(44930.0, 31780.0), 20.0f), //
	/*
	 * MM
	 */
	A0_LANDSCAPE("A0 (Landscape)", new PageSize(1189.0, 841.0), 1.0f), //
	A1_LANDSCAPE("A1 (Landscape)", new PageSize(841.0, 594.0), 0.75f), //
	A2_LANDSCAPE("A2 (Landscape)", new PageSize(594.0, 420.0), 0.5f), //
	A3_LANDSCAPE("A3 (Landscape)", new PageSize(420.0, 297.0), 0.375f), //
	A4_LANDSCAPE("A4 (Landscape)", new PageSize(297.0, 210.0), 0.25f), //
	A5_LANDSCAPE("A5 (Landscape)", new PageSize(210.0, 148.0), 0.125f), //
	US_LETTER("US Letter (Landscape)", new PageSize(279.4, 215.9), 0.25f), //
	US_LEGAL("US Legal (Landscape)", new PageSize(355.6, 215.9), 0.25f); //

	private String label = "";
	private PageSize pageSize;
	private float factorGraphics;
	private float factorFont;

	private PageSizeOption(String label, PageSize pageSize, float factor) {

		this(label, pageSize, factor, factor);
	}

	private PageSizeOption(String label, PageSize pageSize, float factorGraphics, float factorFont) {

		this.label = label;
		this.pageSize = pageSize;
		this.factorGraphics = factorGraphics;
		this.factorFont = factorFont;
	}

	public String label() {

		return label;
	}

	public PageSize pageSize() {

		return pageSize;
	}

	public float factorGraphics() {

		return factorGraphics;
	}

	public float factorFont() {

		return factorFont;
	}

	public static String[][] getOptions() {

		return ILabel.getOptions(values());
	}
}
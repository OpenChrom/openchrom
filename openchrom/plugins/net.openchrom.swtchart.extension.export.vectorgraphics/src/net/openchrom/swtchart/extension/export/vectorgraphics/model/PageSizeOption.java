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

	FULL_LANDSCAPE("Full Size (Landscape)", new PageSize(44930, 31780), 20.0f), // [px] -> 11887.73, 8408.46 [mm]
	A0_LANDSCAPE("A0 (Landscape)", new PageSize(4493.858, 3178.583), 1.0f), // [px] -> 1189.0, 841.0 [mm]
	A1_LANDSCAPE("A1 (Landscape)", new PageSize(3178.583, 2245.039), 0.75f), // [px] -> 841.0, 594.0 [mm]
	A2_LANDSCAPE("A2 (Landscape)", new PageSize(2245.039, 1587.402), 0.5f), // [px] -> 594.0, 420.0 [mm]
	A3_LANDSCAPE("A3 (Landscape)", new PageSize(1587.402, 1122.520), 0.375f), // [px] -> 420.0, 297.0 [mm]
	A4_LANDSCAPE("A4 (Landscape)", new PageSize(1122.520, 793.701), 0.25f), // [px] -> 297.0, 210.0 [mm]
	A5_LANDSCAPE("A5 (Landscape)", new PageSize(793.701, 559.370), 0.125f), // [px] -> 210.0, 148.0 [mm]
	US_LETTER("US Letter (Landscape)", new PageSize(1056.0, 816.0), 0.25f), // [px] -> 279.4, 215.9 [mm]
	US_LEGAL("US Legal (Landscape)", new PageSize(1344.0, 816.0), 0.25f); // [px] -> 355.6, 215.9 [in]

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
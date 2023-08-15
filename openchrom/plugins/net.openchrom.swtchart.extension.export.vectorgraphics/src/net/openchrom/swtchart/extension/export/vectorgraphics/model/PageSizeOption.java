/*******************************************************************************
 * Copyright (c) 2023 Lablicate GmbH.
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

import java.awt.BasicStroke;

import org.eclipse.chemclipse.support.text.ILabel;

import de.erichseifert.vectorgraphics2d.util.PageSize;

public enum PageSizeOption implements ILabel {

	FULL_LANDSCAPE("Full Size (Landscape)", new PageSize(44930, 31780), new BasicStroke(20.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND)), // [px] -> 11887.73, 8408.46 [mm]
	A0_LANDSCAPE("A0 (Landscape)", new PageSize(4493.858, 3178.583), new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND)), // [px] -> 1189.0, 841.0 [mm]
	A4_LANDSCAPE("A4 (Landscape)", new PageSize(1122.520, 793.701), new BasicStroke(0.25f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND)); // [px] -> 297.0, 210.0 [mm]

	private String label = "";
	private PageSize pageSize;
	private BasicStroke basicStroke;

	private PageSizeOption(String label, PageSize pageSize, BasicStroke basicStroke) {

		this.label = label;
		this.pageSize = pageSize;
		this.basicStroke = basicStroke;
	}

	public String label() {

		return label;
	}

	public PageSize pageSize() {

		return pageSize;
	}

	public BasicStroke basicStroke() {

		return basicStroke;
	}

	public static String[][] getOptions() {

		return ILabel.getOptions(values());
	}
}
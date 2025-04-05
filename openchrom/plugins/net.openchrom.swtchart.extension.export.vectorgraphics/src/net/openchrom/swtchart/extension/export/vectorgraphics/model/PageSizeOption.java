/*******************************************************************************
 * Copyright (c) 2023, 2025 Lablicate GmbH.
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
import org.eclipse.swtchart.vectorgraphics2d.util.PageSize;

public enum PageSizeOption implements ILabel {

	/*
	 * The full landscape is used to compensate that integer values can be plotted only.
	 */
	FULL_LANDSCAPE("Full Size (Landscape)", new PageSize(44930.0, 31780.0)), //
	/*
	 * MM
	 */
	A0_LANDSCAPE("A0 (Landscape)", new PageSize(1189.0, 841.0)), //
	A1_LANDSCAPE("A1 (Landscape)", new PageSize(841.0, 594.0)), //
	A2_LANDSCAPE("A2 (Landscape)", new PageSize(594.0, 420.0)), //
	A3_LANDSCAPE("A3 (Landscape)", new PageSize(420.0, 297.0)), //
	A4_LANDSCAPE("A4 (Landscape)", new PageSize(297.0, 210.0)), //
	A5_LANDSCAPE("A5 (Landscape)", new PageSize(210.0, 148.0)), //
	US_LETTER("US Letter (Landscape)", new PageSize(279.4, 215.9)), // 11.0 x 8.5 in
	US_LEGAL("US Legal (Landscape)", new PageSize(355.6, 215.9)), // 14.0 x 8.5 in
	TABLOID("Tabloid", new PageSize(11.0 * PageSize.MM_PER_INCH, 17.0 * PageSize.MM_PER_INCH)), // 11.0 x 17.0 in
	LEDGER("Ledger", new PageSize(17.0 * PageSize.MM_PER_INCH, 11.0 * PageSize.MM_PER_INCH)), // 17.0 x 11.0 in
	CUSTOM_SIZE("Custom Size", new PageSize(297.0, 210.0)); //

	private String label = "";
	private PageSize pageSize;
	private ChartSettings chartSettings;

	private PageSizeOption(String label, PageSize pageSize) {

		this.label = label;
		this.pageSize = pageSize;
		this.chartSettings = getChartSettings();
	}

	public String label() {

		return label;
	}

	public PageSize pageSize() {

		return pageSize;
	}

	public ChartSettings chartSettings() {

		return chartSettings;
	}

	public static String[][] getOptions() {

		return ILabel.getOptions(values());
	}

	private static ChartSettings getChartSettings() {

		ChartSettings chartSettings = new ChartSettings();
		chartSettings.setFactorFont(1.0f);
		chartSettings.setFactorGraphics(1.0f);
		chartSettings.setNumberTics(20);

		return chartSettings;
	}
}
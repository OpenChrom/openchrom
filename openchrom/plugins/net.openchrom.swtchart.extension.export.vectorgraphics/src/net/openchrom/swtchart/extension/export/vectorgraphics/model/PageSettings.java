/*******************************************************************************
 * Copyright (c) 2023, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.swtchart.extension.export.vectorgraphics.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtchart.LineStyle;
import org.eclipse.swtchart.vectorgraphics2d.util.PageSize;

import net.openchrom.swtchart.extension.export.vectorgraphics.support.AWTUtils;

public class PageSettings {

	public static final int STYLE_FONT_STANDARD = Font.PLAIN;
	//
	public static final PageSizeOption FULL_LANDSCAPE = PageSizeOption.FULL_LANDSCAPE;
	//
	private static final int SIZE_FONT_STANDARD = 14;
	private static final String TYPE_FONT_STANDARD = "Arial";
	private static final float FACTOR_GRAPHICS = 20.0f;
	private static final float FACTOR_FONT = 20.0f;
	//
	private ChartSettings chartSettings;
	//
	private double width = 0;
	private double height = 0;
	private double borderLeftX = 0;
	private double borderRightX = 0;
	private double borderTopY = 0;
	private double borderBottomY = 0;
	private double intentX = 0;
	private double intentY = 0;
	//
	private Color colorBlack = AWTUtils.convertColor(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
	private Color colorDarkGray = AWTUtils.convertColor(Display.getDefault().getSystemColor(SWT.COLOR_DARK_GRAY));
	private Color colorGray = AWTUtils.convertColor(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
	//
	private Font font;
	//
	private BasicStroke strokeSolid;
	private BasicStroke strokeDash;
	private BasicStroke strokeDot;
	private BasicStroke strokeDashDot;
	private BasicStroke strokeDashDotDot;

	public PageSettings(PageSizeOption pageSizeOption) {

		PageSize pageSize = pageSizeOption.pageSize();
		this.chartSettings = pageSizeOption.chartSettings();
		this.width = pageSize.getWidth();
		this.height = pageSize.getHeight();
		updateFactors();
	}

	public float getFactorGraphicsFullLandscape() {

		return chartSettings.getFactorGraphics() * FACTOR_GRAPHICS;
	}

	public float getFactorFontFullLandscape() {

		return chartSettings.getFactorFont() * FACTOR_FONT;
	}

	public BasicStroke getStroke(LineStyle lineStyle, int lineWidth) {

		BasicStroke stroke = null;
		float width = lineWidth * getFactorGraphicsFullLandscape();
		//
		switch(lineStyle) {
			case DASH:
				stroke = getStrokeDash(width);
				break;
			case DOT:
				stroke = getStrokeDot(width);
				break;
			case DASHDOT:
				stroke = getStrokeDashDot(width);
				break;
			case DASHDOTDOT:
				stroke = getStrokeDashDotDot(width);
				break;
			case SOLID:
			default:
				stroke = getStrokeSolid(width);
				break;
		}
		//
		return stroke;
	}

	public ChartSettings getChartSettings() {

		return chartSettings;
	}

	public void updateChartSettings(ChartSettings chartSettings) {

		this.chartSettings = chartSettings;
		updateFactors();
	}

	public double getWidth() {

		return width;
	}

	public double getHeight() {

		return height;
	}

	public double getBorderLeftX() {

		return borderLeftX;
	}

	public double getBorderRightX() {

		return borderRightX;
	}

	public double getBorderTopY() {

		return borderTopY;
	}

	public double getBorderBottomY() {

		return borderBottomY;
	}

	public double getIntentX() {

		return intentX;
	}

	public double getIntentY() {

		return intentY;
	}

	public Color getColorBlack() {

		return colorBlack;
	}

	public Color getColorDarkGray() {

		return colorDarkGray;
	}

	public Color getColorGray() {

		return colorGray;
	}

	public Font getFont() {

		return font;
	}

	public Font getFont(float size) {

		return new Font(TYPE_FONT_STANDARD, STYLE_FONT_STANDARD, (int)(SIZE_FONT_STANDARD * size));
	}

	public BasicStroke getStrokeSolid() {

		return strokeSolid;
	}

	public BasicStroke getStrokeDash() {

		return strokeDash;
	}

	public BasicStroke getStrokeDot() {

		return strokeDot;
	}

	public BasicStroke getStrokeDashDot() {

		return strokeDashDot;
	}

	public BasicStroke getStrokeDashDotDot() {

		return strokeDashDotDot;
	}

	private BasicStroke getStrokeSolid(float width) {

		return new BasicStroke(width, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND);
	}

	private BasicStroke getStrokeDash(float width) {

		return new BasicStroke(width, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0.0f, new float[]{width}, 0.0f);
	}

	private BasicStroke getStrokeDot(float width) {

		return new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL, 0.0f, new float[]{width}, 0.0f);
	}

	private BasicStroke getStrokeDashDot(float width) {

		float halfWidth = width / 2.0f;
		return new BasicStroke(width, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0.0f, new float[]{width, width, halfWidth, width}, 0.0f);
	}

	private BasicStroke getStrokeDashDotDot(float width) {

		float halfWidth = width / 2.0f;
		return new BasicStroke(width, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0.0f, new float[]{width, width, halfWidth, width, halfWidth, width}, 0.0f);
	}

	private void updateFactors() {

		float factorGraphics = getFactorGraphicsFullLandscape();
		float factorFont = getFactorFontFullLandscape();
		//
		this.borderLeftX = 150 * factorGraphics;
		this.borderRightX = 50 * factorGraphics;
		this.borderTopY = 50 * factorGraphics;
		this.borderBottomY = 100 * factorGraphics;
		this.intentX = 5 * factorGraphics;
		this.intentY = 5 * factorGraphics;
		//
		// BasicStroke.CAP_SQUARE;
		// BasicStroke.CAP_BUTT;
		// BasicStroke.CAP_ROUND;
		//
		// BasicStroke.JOIN_ROUND;
		// BasicStroke.JOIN_MITER;
		// BasicStroke.JOIN_BEVEL;
		//
		this.font = getFont(factorFont);
		//
		this.strokeSolid = getStrokeSolid(factorGraphics);
		this.strokeDash = getStrokeDash(factorGraphics);
		this.strokeDot = getStrokeDot(factorGraphics);
		this.strokeDashDot = getStrokeDashDot(factorGraphics);
		this.strokeDashDotDot = getStrokeDashDotDot(factorGraphics);
	}
}
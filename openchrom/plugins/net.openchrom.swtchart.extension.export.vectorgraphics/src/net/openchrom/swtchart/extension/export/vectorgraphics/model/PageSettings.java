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
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtchart.LineStyle;

import net.openchrom.swtchart.extension.export.vectorgraphics.support.AWTUtils;

import de.erichseifert.vectorgraphics2d.util.PageSize;

public class PageSettings {

	private static final int SIZE_FONT_STANDARD = 14;
	//
	private DecimalFormat decimalFormatX = new DecimalFormat(("0.00"), new DecimalFormatSymbols(Locale.ENGLISH));
	private DecimalFormat decimalFormatY = new DecimalFormat(("0.0#E0"), new DecimalFormatSymbols(Locale.ENGLISH));
	//
	private float factor = 1.0f;
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
	private Font font;
	private BasicStroke strokeDefault;
	private BasicStroke strokeDashed;
	//

	public PageSettings(PageSizeOption pageSizeOption) {

		PageSize pageSize = pageSizeOption.pageSize();
		//
		this.factor = pageSizeOption.factor();
		this.width = pageSize.getWidth();
		this.height = pageSize.getHeight();
		//
		this.borderLeftX = 100 * factor;
		this.borderRightX = 50 * factor;
		this.borderTopY = 50 * factor;
		this.borderBottomY = 100 * factor;
		this.intentX = 5 * factor;
		this.intentY = 5 * factor;
		//
		this.font = new Font("Arial", Font.PLAIN, (int)(SIZE_FONT_STANDARD * factor));
		this.strokeDefault = new BasicStroke(factor, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND);
		this.strokeDashed = new BasicStroke(factor, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
	}

	public BasicStroke getStroke(LineStyle lineStyle, int lineWidth) {

		BasicStroke stroke = null;
		float width = lineWidth * factor;
		//
		switch(lineStyle) {
			case DASH:
				stroke = new BasicStroke(width, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1.0f, new float[]{2.0f, 0.0f, 2.0f}, 2.0f);
				break;
			case DOT:
				stroke = new BasicStroke(width, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1.0f, new float[]{1.0f, 0.0f, 1.0f}, 2.0f);
				break;
			case DASHDOT:
				stroke = new BasicStroke(width, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1.0f, new float[]{2.0f, 0.0f, 1.0f}, 2.0f);
				break;
			case DASHDOTDOT:
				stroke = new BasicStroke(width, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1.0f, new float[]{2.0f, 1.0f, 1.0f}, 2.0f);
				break;
			case SOLID:
			default:
				stroke = new BasicStroke(width, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND);
				break;
		}
		//
		return stroke;
	}

	public DecimalFormat getDecimalFormatX() {

		return decimalFormatX;
	}

	public DecimalFormat getDecimalFormatY() {

		return decimalFormatY;
	}

	public float getFactor() {

		return factor;
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

	public BasicStroke getStrokeDefault() {

		return strokeDefault;
	}

	public BasicStroke getStrokeDashed() {

		return strokeDashed;
	}
}
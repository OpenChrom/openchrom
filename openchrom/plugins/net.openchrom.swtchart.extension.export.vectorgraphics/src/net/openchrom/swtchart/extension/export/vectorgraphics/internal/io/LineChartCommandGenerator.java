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
package net.openchrom.swtchart.extension.export.vectorgraphics.internal.io;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.numeric.core.IPoint;
import org.eclipse.chemclipse.numeric.core.Point;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtchart.IAxis;
import org.eclipse.swtchart.ILineSeries.PlotSymbolType;
import org.eclipse.swtchart.ISeries;
import org.eclipse.swtchart.Range;
import org.eclipse.swtchart.export.core.VectorExportSettingsDialog;
import org.eclipse.swtchart.extensions.core.BaseChart;
import org.eclipse.swtchart.extensions.core.IAxisScaleConverter;
import org.eclipse.swtchart.extensions.core.IAxisSettings;
import org.eclipse.swtchart.extensions.core.ISecondaryAxisSettings;
import org.eclipse.swtchart.extensions.core.ISeriesSettings;
import org.eclipse.swtchart.extensions.core.ScrollableChart;
import org.eclipse.swtchart.extensions.linecharts.ILineSeriesSettings;

import net.openchrom.swtchart.extension.export.vectorgraphics.model.PageSettings;
import net.openchrom.swtchart.extension.export.vectorgraphics.model.PageSizeOption;
import net.openchrom.swtchart.extension.export.vectorgraphics.support.AWTUtils;

import de.erichseifert.vectorgraphics2d.VectorGraphics2D;
import de.erichseifert.vectorgraphics2d.intermediate.CommandSequence;

public class LineChartCommandGenerator implements IChartCommandGenerator {

	private static final int NUMBER_TICS = 20;

	@Override
	public CommandSequence getCommandSequence(Shell shell, PageSizeOption pageSizeOption, ScrollableChart scrollableChart) {

		VectorGraphics2D graphics2D = new VectorGraphics2D();
		//
		PageSettings pageSettings = new PageSettings(pageSizeOption);
		graphics2D.setStroke(pageSettings.getStrokeDefault());
		graphics2D.setFont(pageSettings.getFont());
		//
		BaseChart baseChart = scrollableChart.getBaseChart();
		VectorExportSettingsDialog exportSettingsDialog = new VectorExportSettingsDialog(shell, baseChart);
		exportSettingsDialog.create();
		//
		if(exportSettingsDialog.open() == Window.OK) {
			//
			int indexAxisX = exportSettingsDialog.getIndexAxisSelectionX();
			int indexAxisY = exportSettingsDialog.getIndexAxisSelectionY();
			//
			if(indexAxisX >= 0 && indexAxisY >= 0) {
				/*
				 * Print
				 */
				drawData(graphics2D, baseChart, pageSettings);
				drawAxes(graphics2D, baseChart, indexAxisX, indexAxisY, pageSettings);
				drawBranding(graphics2D, pageSettings);
			}
		}
		//
		return graphics2D.getCommands();
	}

	private void drawAxes(Graphics2D graphics2D, BaseChart baseChart, int indexAxisX, int indexAxisY, PageSettings pageSettings) {

		drawAxisX(graphics2D, baseChart, indexAxisX, pageSettings);
		drawAxisY(graphics2D, baseChart, indexAxisY, pageSettings);
	}

	private void drawAxisX(Graphics2D graphics2D, BaseChart baseChart, int indexAxisX, PageSettings pageSettings) {

		DecimalFormat decimalFormatX = pageSettings.getDecimalFormatX();
		double width = pageSettings.getWidth();
		double height = pageSettings.getHeight();
		double xBorderLeft = pageSettings.getBorderLeftX();
		double xBorderRight = pageSettings.getBorderRightX();
		double yBorderTop = pageSettings.getBorderTopY();
		double yBorderBottom = pageSettings.getBorderBottomY();
		/*
		 * Font/Color
		 */
		Font font = pageSettings.getFont();
		graphics2D.setFont(font);
		graphics2D.setStroke(pageSettings.getStrokeDefault());
		graphics2D.setColor(pageSettings.getColorBlack());
		FontMetrics fontMetrics = graphics2D.getFontMetrics();
		/*
		 * X Axis Settings
		 */
		IAxis axisX = baseChart.getAxisSet().getXAxis(BaseChart.ID_PRIMARY_X_AXIS);
		IAxisSettings axisSettingsX = baseChart.getXAxisSettings(indexAxisX);
		IAxisScaleConverter axisScaleConverterX = null;
		String labelX = axisSettingsX.getLabel();
		if(axisSettingsX instanceof ISecondaryAxisSettings) {
			ISecondaryAxisSettings secondaryAxisSettings = (ISecondaryAxisSettings)axisSettingsX;
			axisScaleConverterX = secondaryAxisSettings.getAxisScaleConverter();
			labelX = secondaryAxisSettings.getLabel();
		}
		/*
		 * Settings
		 */
		Range rangeX = axisX.getRange();
		double deltaRange = (rangeX.upper + rangeX.lower) / NUMBER_TICS;
		double deltaWidth = (width - xBorderLeft - xBorderRight) / NUMBER_TICS;
		/*
		 * Scale
		 */
		if(!labelX.isEmpty()) {
			int widthText = fontMetrics.stringWidth(labelX);
			int heightText = fontMetrics.getHeight();
			int x = (int)(width / 2.0d - widthText / 2.0d);
			int y = (int)(height - (yBorderBottom / 2.0d) + heightText);
			graphics2D.drawString(labelX, x, y);
		}
		/*
		 * Grid
		 */
		graphics2D.setStroke(pageSettings.getStrokeDashed());
		graphics2D.setColor(pageSettings.getColorGray());
		for(int i = 1; i <= NUMBER_TICS; i++) {
			int x = (int)(xBorderLeft + i * deltaWidth);
			int y1 = (int)(yBorderTop);
			int y2 = (int)(height - yBorderBottom);
			graphics2D.drawLine(x, y1, x, y2);
		}
		/*
		 * Tics
		 */
		graphics2D.setStroke(pageSettings.getStrokeDefault());
		graphics2D.setColor(pageSettings.getColorBlack());
		for(int i = 1; i <= NUMBER_TICS; i++) {
			double xMin = rangeX.lower + i * deltaRange;
			String label = decimalFormatX.format(axisScaleConverterX != null ? axisScaleConverterX.convertToSecondaryUnit(xMin) : xMin);
			int widthText = fontMetrics.stringWidth(label);
			int heightText = fontMetrics.getHeight();
			int x = (int)(xBorderLeft + i * deltaWidth);
			int y1 = (int)(height - yBorderBottom);
			int y2 = (int)(y1 + (yBorderBottom / 4.0d));
			int x3 = (int)(x - (widthText / 2.0d));
			int y3 = (int)(y1 + (yBorderBottom / 3.0d) + (heightText / 2.0d));
			graphics2D.drawLine(x, y1, x, y2);
			graphics2D.drawString(label, x3, y3);
		}
		/*
		 * X Axis
		 */
		int x11 = (int)(xBorderLeft / 2.0d);
		int y11 = (int)(height - yBorderBottom);
		int x12 = (int)(width - xBorderRight);
		int y12 = (int)(height - yBorderBottom);
		graphics2D.setStroke(pageSettings.getStrokeDefault());
		graphics2D.drawLine(x11, y11, x12, y12);
	}

	private void drawAxisY(Graphics2D graphics2D, BaseChart baseChart, int indexAxisY, PageSettings pageSettings) {

		DecimalFormat decimalFormatY = pageSettings.getDecimalFormatY();
		double width = pageSettings.getWidth();
		double height = pageSettings.getHeight();
		double xBorderLeft = pageSettings.getBorderLeftX();
		double xBorderRight = pageSettings.getBorderRightX();
		double yBorderTop = pageSettings.getBorderTopY();
		double yBorderBottom = pageSettings.getBorderBottomY();
		/*
		 * Font/Color
		 */
		Font font = pageSettings.getFont();
		graphics2D.setFont(font);
		graphics2D.setStroke(pageSettings.getStrokeDefault());
		graphics2D.setColor(pageSettings.getColorBlack());
		FontMetrics fontMetrics = graphics2D.getFontMetrics();
		/*
		 * Y Axis Settings
		 */
		IAxis axisY = baseChart.getAxisSet().getYAxis(BaseChart.ID_PRIMARY_Y_AXIS);
		IAxisSettings axisSettingsY = baseChart.getYAxisSettings(indexAxisY);
		IAxisScaleConverter axisScaleConverterY = null;
		if(axisSettingsY instanceof ISecondaryAxisSettings) {
			ISecondaryAxisSettings secondaryAxisSettings = (ISecondaryAxisSettings)axisSettingsY;
			axisScaleConverterY = secondaryAxisSettings.getAxisScaleConverter();
		}
		/*
		 * Settings
		 */
		Range rangeY = axisY.getRange();
		double deltaRange = (rangeY.upper + rangeY.lower) / NUMBER_TICS;
		double deltaHeight = (height - yBorderTop - yBorderBottom) / NUMBER_TICS;
		/*
		 * Grid
		 */
		graphics2D.setStroke(pageSettings.getStrokeDashed());
		graphics2D.setColor(pageSettings.getColorGray());
		for(int i = 0; i < NUMBER_TICS; i++) {
			int x1 = (int)(xBorderLeft);
			int x2 = (int)(width - xBorderRight);
			int y = (int)(yBorderTop + i * deltaHeight);
			graphics2D.drawLine(x1, y, x2, y);
		}
		/*
		 * Tics
		 */
		graphics2D.setStroke(pageSettings.getStrokeDefault());
		graphics2D.setColor(pageSettings.getColorBlack());
		for(int i = 0; i < NUMBER_TICS; i++) {
			double yMin = rangeY.lower + (NUMBER_TICS - i) * deltaRange;
			String label = decimalFormatY.format((axisScaleConverterY != null) ? axisScaleConverterY.convertToSecondaryUnit(yMin) : yMin);
			int heightText = fontMetrics.getHeight();
			int x1 = (int)(xBorderLeft / 4.0d);
			int x2 = (int)(xBorderLeft / 1.3d);
			int x3 = (int)(xBorderLeft);
			int y1 = (int)(yBorderTop + i * deltaHeight);
			int y2 = (int)(y1 + heightText / 2.75d);
			graphics2D.drawLine(x2, y1, x3, y1);
			graphics2D.drawString(label, x1, y2);
		}
		/*
		 * Y Axis
		 */
		int x21 = (int)(xBorderLeft);
		int y21 = (int)(yBorderTop);
		int x22 = (int)(xBorderLeft);
		int y22 = (int)(height - yBorderTop);
		graphics2D.setStroke(pageSettings.getStrokeDefault());
		graphics2D.drawLine(x21, y21, x22, y22);
	}

	private void drawData(Graphics2D graphics2D, BaseChart baseChart, PageSettings pageSettings) {

		double width = pageSettings.getWidth();
		double height = pageSettings.getHeight();
		double xBorderLeft = pageSettings.getBorderLeftX();
		double xBorderRight = pageSettings.getBorderRightX();
		double yBorderTop = pageSettings.getBorderTopY();
		double yBorderBottom = pageSettings.getBorderBottomY();
		//
		double xMin = baseChart.getMinX();
		double xMax = baseChart.getMaxX();
		double yMin = baseChart.getMinY();
		double yMax = baseChart.getMaxY();
		//
		ISeries<?>[] seriesSet = baseChart.getSeriesSet().getSeries();
		for(ISeries<?> series : seriesSet) {
			/*
			 * Series
			 */
			if(series.isVisible()) {
				String id = series.getId();
				ISeriesSettings seriesSettings = baseChart.getSeriesSettings(id);
				if(seriesSettings instanceof ILineSeriesSettings lineSeriesSettings) {
					double[] xSeries = series.getXSeries();
					double[] ySeries = series.getYSeries();
					double xDenumerator = xMax - xMin;
					double yDenumerator = yMax - yMin;
					//
					if(xMax > 0 && yMax > 0) {
						/*
						 * Factors
						 */
						double factorX = (width - xBorderLeft - xBorderRight) / xDenumerator;
						double factorY = (height - yBorderTop - yBorderBottom) / yDenumerator;
						/*
						 * Collect
						 */
						double xb = 0;
						List<IPoint> points = new ArrayList<>();
						for(int i = 0; i < xSeries.length; i++) {
							double x = xSeries[i];
							if(x > xb) {
								if(x >= xMin && x <= xMax) {
									int x1 = (int)((factorX * (x - xMin)) + xBorderLeft);
									int y1 = (int)((height - factorY * (ySeries[i] - yMin)) - yBorderBottom);
									points.add(new Point(x1, y1));
									xb = x;
								}
							}
						}
						/*
						 * Print
						 */
						printLine(graphics2D, points, lineSeriesSettings, pageSettings);
						printSymbols(graphics2D, points, lineSeriesSettings, pageSettings);
					}
				}
			}
		}
	}

	private void printLine(Graphics2D graphics2D, List<IPoint> points, ILineSeriesSettings lineSeriesSettings, PageSettings pageSettings) {

		/*
		 * Line
		 */
		int lineWidth = lineSeriesSettings.getLineWidth();
		if(lineWidth > 0) {
			int size = points.size();
			if(size > 0) {
				/*
				 * Area
				 */
				int[] xvals = new int[size];
				int[] yvals = new int[size];
				for(int i = 0; i < size; i++) {
					IPoint point = points.get(i);
					xvals[i] = (int)point.getX();
					yvals[i] = (int)point.getY();
				}
				//
				graphics2D.setStroke(pageSettings.getStroke(lineSeriesSettings.getLineStyle(), lineWidth));
				graphics2D.setColor(AWTUtils.convertColor(lineSeriesSettings.getLineColor()));
				graphics2D.drawPolyline(xvals, yvals, size);
				if(lineSeriesSettings.isEnableArea()) {
					// graphics2D.fillPolygon(xvals, yvals, size);
				}
			}
		}
	}

	private void printSymbols(Graphics2D graphics2D, List<IPoint> points, ILineSeriesSettings lineSeriesSettings, PageSettings pageSettings) {

		/*
		 * Symbols
		 */
		int symbolSize = lineSeriesSettings.getSymbolSize();
		if(symbolSize > 0) {
			double size = (lineSeriesSettings.getSymbolSize() * pageSettings.getFactor());
			double radius = size / 2.0d;
			graphics2D.setColor(AWTUtils.convertColor(lineSeriesSettings.getSymbolColor()));
			PlotSymbolType symbolType = lineSeriesSettings.getSymbolType();
			switch(symbolType) {
				case CIRCLE:
					for(IPoint point : points) {
						int x = (int)(point.getX() - radius);
						int y = (int)(point.getY() - radius);
						int width = (int)size;
						int height = (int)size;
						graphics2D.fillOval(x, y, width, height);
					}
					break;
				default:
					break;
			}
		}
	}

	private void drawBranding(Graphics2D graphics2D, PageSettings pageSettings) {

		double width = pageSettings.getWidth();
		double xBorderRight = pageSettings.getBorderRightX();
		double yBorderTop = pageSettings.getBorderTopY();
		//
		graphics2D.setColor(pageSettings.getColorDarkGray());
		String label = "https://openchrom.net";
		FontMetrics fontMetrics = graphics2D.getFontMetrics();
		int widthText = fontMetrics.stringWidth(label);
		int heightText = fontMetrics.getHeight();
		int x = (int)(width - xBorderRight - widthText);
		int y = (int)((yBorderTop / 2.0d) + (heightText / 2.0d));
		graphics2D.drawString(label, x, y);
	}
}
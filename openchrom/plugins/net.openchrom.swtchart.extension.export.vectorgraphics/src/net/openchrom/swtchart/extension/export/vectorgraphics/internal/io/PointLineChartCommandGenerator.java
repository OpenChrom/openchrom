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
package net.openchrom.swtchart.extension.export.vectorgraphics.internal.io;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.numeric.core.IPoint;
import org.eclipse.chemclipse.numeric.core.Point;
import org.eclipse.swtchart.IAxis;
import org.eclipse.swtchart.ILineSeries.PlotSymbolType;
import org.eclipse.swtchart.ISeries;
import org.eclipse.swtchart.LineStyle;
import org.eclipse.swtchart.Range;
import org.eclipse.swtchart.extensions.core.BaseChart;
import org.eclipse.swtchart.extensions.core.IChartSettings;
import org.eclipse.swtchart.extensions.core.IPointSeriesSettings;
import org.eclipse.swtchart.extensions.core.ISeriesSettings;
import org.eclipse.swtchart.extensions.core.RangeRestriction;
import org.eclipse.swtchart.extensions.core.ScrollableChart;
import org.eclipse.swtchart.extensions.linecharts.ILineSeriesSettings;
import org.eclipse.swtchart.vectorgraphics2d.core.VectorGraphics2D;
import org.eclipse.swtchart.vectorgraphics2d.intermediate.CommandSequence;
import org.eclipse.swtchart.vectorgraphics2d.util.PageSize;

import net.openchrom.swtchart.extension.export.vectorgraphics.model.PageSettings;
import net.openchrom.swtchart.extension.export.vectorgraphics.model.PageSizeOption;
import net.openchrom.swtchart.extension.export.vectorgraphics.support.AWTUtils;

/*
 * Important to now:
 * Basically, x|y 0,0 is on the left top side.
 * ---
 * drawString("text", x, y)
 * The anchor is on the left side at the bottom.
 * ---
 * affineTransform.rotate(Math.toRadians(-90), anchorx, anchory);
 */
public class PointLineChartCommandGenerator extends AbstractCommandGenerator implements IChartCommandGenerator {

	@Override
	public CommandSequence getCommandSequence(PageSizeOption pageSizeOption, int indexAxisX, int indexAxisY, ScrollableChart scrollableChart) {

		VectorGraphics2D graphics2D = new VectorGraphics2D();
		PageSettings pageSettings = new PageSettings(PageSettings.FULL_LANDSCAPE);
		Point scale = calculateScale(graphics2D, pageSizeOption, pageSettings);
		/*
		 * Create using the given axes.
		 */
		if(indexAxisX >= 0 && indexAxisY >= 0) {
			/*
			 * Print
			 */
			BaseChart baseChart = scrollableChart.getBaseChart();
			drawAxes(graphics2D, scale, baseChart, indexAxisX, indexAxisY, pageSettings);
			drawStandardSeries(graphics2D, scale, baseChart, pageSettings);
			drawCustomSeries(graphics2D, scale, baseChart, pageSettings);
			drawTitle(graphics2D, pageSettings, scrollableChart);
			drawBranding(graphics2D, pageSettings);
		}

		PageSize pageSize = pageSizeOption.pageSize();
		graphics2D.setClip(0, 0, (int)Math.round(pageSize.getWidth()), (int)Math.round(pageSize.getHeight()));
		return graphics2D.getCommands();
	}

	private void drawStandardSeries(Graphics2D graphics2D, IPoint scale, BaseChart baseChart, PageSettings pageSettings) {

		double width = pageSettings.getWidth();
		double height = pageSettings.getHeight();
		double xBorderLeft = pageSettings.getBorderLeftX();
		double xBorderRight = pageSettings.getBorderRightX();
		double yBorderTop = pageSettings.getBorderTopY();
		double yBorderBottom = pageSettings.getBorderBottomY();

		IChartSettings chartSettings = baseChart.getChartSettings();
		RangeRestriction raneRangeRestriction = chartSettings.getRangeRestriction();
		double extendMaxY = raneRangeRestriction.getExtendMaxY();

		IAxis axisX = baseChart.getAxisSet().getXAxis(BaseChart.ID_PRIMARY_X_AXIS);
		IAxis axisY = baseChart.getAxisSet().getYAxis(BaseChart.ID_PRIMARY_Y_AXIS);
		Range rangeX = axisX.getRange();
		Range rangeY = axisY.getRange();

		double xMin = rangeX.lower; // baseChart.getMinX();
		double xMax = rangeX.upper; // baseChart.getMaxX();
		double yMin = baseChart.getMinY(); // Watch Out: Force to have no offset
		double yMax = rangeY.upper; // baseChart.getMaxY();

		ISeries<?>[] seriesSet = baseChart.getSeriesSet().getSeries();
		for(ISeries<?> series : seriesSet) {
			/*
			 * Series
			 */
			if(series.isVisible()) {
				String id = series.getId();
				ISeriesSettings seriesSettings = baseChart.getSeriesSettings(id);
				if(seriesSettings instanceof IPointSeriesSettings pointSeriesSettings) {
					double[] xSeries = series.getXSeries();
					double[] ySeries = series.getYSeries();
					double xDenumerator = xMax - xMin;
					double yDenumerator = (yMax + yMax * extendMaxY) - yMin;

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
							if(x >= xMin && x <= xMax) {
								/*
								 * x >= xb (start/end is at the same x position in case of background)
								 * boolean areaStrict = lineSeriesSettings.isAreaStrict();
								 */
								if(x >= xb) {
									if(x >= xMin && x <= xMax) {
										int x1 = (int)((factorX * (x - xMin)) + xBorderLeft);
										int y1 = (int)((height - factorY * (ySeries[i] - yMin)) - yBorderBottom);
										points.add(new Point(x1, y1));
										xb = x;
									}
								}
							}
						}
						/*
						 * Print
						 */
						if(pointSeriesSettings instanceof ILineSeriesSettings lineSeriesSettings) {
							int minValue = (int)(height - yBorderBottom);
							printLine(graphics2D, points, minValue, lineSeriesSettings, pageSettings);
						}
						printSymbols(graphics2D, scale, points, pointSeriesSettings, pageSettings);
					}
				}
			}
		}
	}

	private void printLine(Graphics2D graphics2D, List<IPoint> points, int minValue, ILineSeriesSettings lineSeriesSettings, PageSettings pageSettings) {

		/*
		 * Line
		 */
		int lineWidth = lineSeriesSettings.getLineWidth();
		LineStyle lineStyle = lineSeriesSettings.getLineStyle();

		if(lineWidth > 0 && !LineStyle.NONE.equals(lineStyle)) {
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

				Color color = AWTUtils.convertColor(lineSeriesSettings.getLineColor());
				graphics2D.setStroke(pageSettings.getStroke(lineStyle, lineWidth));
				graphics2D.setColor(color);
				graphics2D.drawPolyline(xvals, yvals, size);

				if(lineSeriesSettings.isEnableArea()) {
					int sizePolygon = size + 2;
					int[] xvalsPolygon = transformPolylineToPolygon(xvals, false, minValue);
					int[] yvalsPolygon = transformPolylineToPolygon(yvals, lineSeriesSettings.isAreaStrict() ? false : true, minValue);
					Color colorBrighter = new Color(color.getRed(), color.getGreen(), color.getBlue(), AWTUtils.getAlpha(20));
					graphics2D.setColor(colorBrighter);
					graphics2D.fillPolygon(xvalsPolygon, yvalsPolygon, sizePolygon);
				}
			}
		}
	}

	private int[] transformPolylineToPolygon(int[] vals, boolean zero, int minValue) {

		int length = vals.length;
		int size = length + 2;
		int[] valsTransformed = new int[size];

		if(length >= 2) {
			/*
			 * Edges
			 */
			int firstValue = 0;
			int lastValue = 0;

			if(zero) {
				/*
				 * Max, because the values are transposed to the chart
				 * coordinate (0,0) left top system already.
				 */
				firstValue = minValue;
				lastValue = minValue;
			} else {
				firstValue = vals[0];
				lastValue = vals[length - 1];
			}
			/*
			 * Transformed
			 */
			valsTransformed[0] = firstValue;
			for(int i = 0; i < length; i++) {
				valsTransformed[i + 1] = vals[i];
			}
			valsTransformed[size - 1] = lastValue;
		}

		return valsTransformed;
	}

	private void printSymbols(Graphics2D graphics2D, IPoint scale, List<IPoint> points, IPointSeriesSettings pointSeriesSettings, PageSettings pageSettings) {

		/*
		 * Symbols
		 */
		int symbolSize = pointSeriesSettings.getSymbolSize();
		PlotSymbolType symbolType = pointSeriesSettings.getSymbolType();

		if(symbolSize > 0 && !PlotSymbolType.NONE.equals(symbolType)) {
			double size = (symbolSize * pageSettings.getFactorGraphicsFullLandscape());
			graphics2D.setFont(pageSettings.getFont(symbolSize * 2.0f));
			graphics2D.setColor(AWTUtils.convertColor(pointSeriesSettings.getSymbolColor()));
			for(IPoint point : points) {
				drawSymbol(graphics2D, scale, point, size, symbolType);
			}
		}
	}

	private void drawSymbol(Graphics2D graphics2D, IPoint scale, IPoint point, double size, PlotSymbolType symbolType) {

		/*
		 * Determine X|Y
		 */
		double radius = size / 2.0d;
		int width = (int)size;
		int height = (int)size;
		int x;
		int y;
		String label = "";

		switch(symbolType) {
			case TRIANGLE:
			case INVERTED_TRIANGLE:
				x = (int)(point.getX());
				y = (int)(point.getY());
				break;
			case CROSS:
			case PLUS:
				label = PlotSymbolType.CROSS.equals(symbolType) ? "x" : "+";
				FontMetrics fontMetrics = graphics2D.getFontMetrics();
				int widthText = fontMetrics.stringWidth(label);
				int heightText = fontMetrics.getHeight();
				x = (int)(point.getX() - (widthText / 2.0d));
				y = (int)(point.getY() - (heightText / 2.0d));
				break;
			default:
				x = (int)(point.getX() - radius);
				y = (int)(point.getY() - radius);
				break;
		}
		/*
		 * Draw
		 */
		switch(symbolType) {
			case CIRCLE:
				graphics2D.fillOval(x, y, width, height);
				break;
			case TRIANGLE:
				drawTriangle(graphics2D, x, y, width, height);
				break;
			case INVERTED_TRIANGLE:
				drawInvertedTriangle(graphics2D, x, y, width, height);
				break;
			case CROSS:
				graphics2D.drawString(label, x, y);
				break;
			case DIAMOND:
				AffineTransform affineTransformDefault = graphics2D.getTransform();
				AffineTransform affineTransform = createAffineTransform(scale);
				affineTransform.rotate(Math.toRadians(45), x + size / 2.0d, y + size / 2.0d);
				graphics2D.setTransform(affineTransform);
				graphics2D.fillRect(x, y, width, height);
				graphics2D.setTransform(affineTransformDefault);
				break;
			case PLUS:
				graphics2D.drawString(label, x, y);
				break;
			case SQUARE:
				graphics2D.fillRect(x, y, width, height);
				break;
			default:
				break;
		}
	}

	private void drawTriangle(Graphics2D graphics2D, int x, int y, int width, int height) {

		int x1 = x;
		int y1 = y;
		int x2 = (int)(x + width / 2.0d);
		int y2 = y + height;
		int x3 = (int)(x - width / 2.0d);
		int y3 = y + height;

		drawTriangle(graphics2D, x1, y1, x2, y2, x3, y3);
	}

	private void drawInvertedTriangle(Graphics2D graphics2D, int x, int y, int width, int height) {

		int x1 = (int)(x + width / 2.0d);
		int y1 = y;
		int x2 = (int)(x - width / 2.0d);
		int y2 = y;
		int x3 = x;
		int y3 = y + height;

		drawTriangle(graphics2D, x1, y1, x2, y2, x3, y3);
	}

	private void drawTriangle(Graphics2D graphics2D, int x1, int y1, int x2, int y2, int x3, int y3) {

		int[] xPoints = new int[]{x1, x2, x3};
		int[] yPoints = new int[]{y1, y2, y3};
		graphics2D.drawPolygon(xPoints, yPoints, 3);
		graphics2D.fillPolygon(xPoints, yPoints, 3);
	}

}
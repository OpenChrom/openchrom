/*******************************************************************************
 * Copyright (c) 2025, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.swtchart.extension.export.vectorgraphics.internal.io;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.numeric.core.IPoint;
import org.eclipse.chemclipse.numeric.core.Point;
import org.eclipse.swtchart.IAxis;
import org.eclipse.swtchart.ISeries;
import org.eclipse.swtchart.LineStyle;
import org.eclipse.swtchart.Range;
import org.eclipse.swtchart.extensions.barcharts.IBarSeriesSettings;
import org.eclipse.swtchart.extensions.core.BaseChart;
import org.eclipse.swtchart.extensions.core.IChartSettings;
import org.eclipse.swtchart.extensions.core.ISeriesSettings;
import org.eclipse.swtchart.extensions.core.RangeRestriction;
import org.eclipse.swtchart.extensions.core.ScrollableChart;
import org.eclipse.swtchart.vectorgraphics2d.core.VectorGraphics2D;
import org.eclipse.swtchart.vectorgraphics2d.intermediate.CommandSequence;
import org.eclipse.swtchart.vectorgraphics2d.util.PageSize;

import net.openchrom.swtchart.extension.export.vectorgraphics.model.PageSettings;
import net.openchrom.swtchart.extension.export.vectorgraphics.model.PageSizeOption;
import net.openchrom.swtchart.extension.export.vectorgraphics.support.AWTUtils;

public class BarChartCommandGenerator extends AbstractCommandGenerator {

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
					ISeriesSettings seriesSettings = baseChart.getSeriesSettings(id);
					int minValue = (int)(height - yBorderBottom);
					if(seriesSettings instanceof IBarSeriesSettings barSeriesSettings) {
						printBars(graphics2D, points, scale, minValue, barSeriesSettings, pageSettings);
					}
				}
			}
		}
	}

	private void printBars(Graphics2D graphics2D, List<IPoint> points, IPoint scale, int minValue, IBarSeriesSettings barSeriesSettings, PageSettings pageSettings) {

		int barWidth = barSeriesSettings.getBarWidth();
		if(barWidth > 0 && !points.isEmpty()) {
			Color color = AWTUtils.convertColor(barSeriesSettings.getBarColor());
			graphics2D.setColor(color);

			for(IPoint point : points) {
				int x = (int)point.getX();
				int y = (int)point.getY();

				int barX = x - barWidth / 2;
				int barY = Math.min(y, minValue);
				int barHeight = Math.abs(minValue - y);

				int barThickness = (int)(barWidth * (1 / scale.getX()));
				graphics2D.setStroke(pageSettings.getStroke(LineStyle.SOLID, barThickness));
				graphics2D.fillRect(barX, barY, barThickness, barHeight);
			}
		}
	}
}

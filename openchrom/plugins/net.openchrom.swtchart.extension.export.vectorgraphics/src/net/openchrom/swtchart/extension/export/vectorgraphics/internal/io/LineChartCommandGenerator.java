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

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.chemclipse.numeric.core.IPoint;
import org.eclipse.chemclipse.numeric.core.Point;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtchart.IAxis;
import org.eclipse.swtchart.ISeries;
import org.eclipse.swtchart.Range;
import org.eclipse.swtchart.export.core.VectorExportSettingsDialog;
import org.eclipse.swtchart.extensions.core.BaseChart;
import org.eclipse.swtchart.extensions.core.IAxisScaleConverter;
import org.eclipse.swtchart.extensions.core.IAxisSettings;
import org.eclipse.swtchart.extensions.core.ISecondaryAxisSettings;
import org.eclipse.swtchart.extensions.core.ScrollableChart;

import net.openchrom.swtchart.extension.export.vectorgraphics.model.PageSizeOption;

import de.erichseifert.vectorgraphics2d.VectorGraphics2D;
import de.erichseifert.vectorgraphics2d.intermediate.CommandSequence;
import de.erichseifert.vectorgraphics2d.util.PageSize;

public class LineChartCommandGenerator implements IChartCommandGenerator {

	@Override
	public CommandSequence getCommandSequence(Shell shell, PageSizeOption pageSizeOption, ScrollableChart scrollableChart) {

		VectorGraphics2D graphics2D = new VectorGraphics2D();
		//
		float factor = pageSizeOption.factor();
		graphics2D.setStroke(new BasicStroke(factor, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
		Font font = new Font("Arial", Font.PLAIN, (int)(14 * factor));
		graphics2D.setFont(font);
		//
		DecimalFormat decimalFormatX = new DecimalFormat(("0.00"), new DecimalFormatSymbols(Locale.ENGLISH));
		DecimalFormat decimalFormatY = new DecimalFormat(("0.0#E0"), new DecimalFormatSymbols(Locale.ENGLISH));
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
				 * X Axis Settings
				 */
				IAxis axisX = baseChart.getAxisSet().getXAxis(BaseChart.ID_PRIMARY_X_AXIS);
				IAxisSettings axisSettingsX = baseChart.getXAxisSettings(indexAxisX);
				IAxisScaleConverter axisScaleConverterX = null;
				String labelX = "";
				if(axisSettingsX instanceof ISecondaryAxisSettings) {
					ISecondaryAxisSettings secondaryAxisSettings = (ISecondaryAxisSettings)axisSettingsX;
					axisScaleConverterX = secondaryAxisSettings.getAxisScaleConverter();
					labelX = secondaryAxisSettings.getLabel();
				}
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
				 * Size
				 */
				PageSize pageSize = pageSizeOption.pageSize();
				double width = pageSize.getWidth();
				double height = pageSize.getHeight();
				double xBorder = 50 * factor;
				double yBorder = 50 * factor;
				double xIntent = 5 * factor;
				double yIntent = 5 * factor;
				//
				ISeries<?>[] series = baseChart.getSeriesSet().getSeries();
				ISeries<?> dataSeries = series[0];
				if(dataSeries != null) {
					double[] xSeries = dataSeries.getXSeries();
					double[] ySeries = dataSeries.getYSeries();
					Range rangeX = axisX.getRange();
					Range rangeY = axisY.getRange();
					double xMin = rangeX.lower; // Arrays.stream(xSeries).min().getAsDouble();
					double xMax = rangeX.upper; // Arrays.stream(xSeries).max().getAsDouble();
					double yMin = rangeY.lower; // Arrays.stream(ySeries).min().getAsDouble();
					double yMax = rangeY.upper; // Arrays.stream(ySeries).max().getAsDouble();
					double xDenumerator = xMax - xMin;
					double yDenumerator = yMax - yMin;
					//
					if(xMax > 0 && yMax > 0) {
						/*
						 * Factors
						 */
						double factorX = (width - 2 * xBorder) / xDenumerator;
						double factorY = (height - 2 * yBorder) / yDenumerator;
						/*
						 * Chromatogram
						 */
						double xb = 0;
						List<IPoint> points = new ArrayList<>();
						for(int i = 0; i < xSeries.length; i++) {
							double x = xSeries[i];
							if(x > xb) {
								if(x >= xMin && x <= xMax) {
									int x1 = (int)((factorX * (x - xMin)) + xBorder);
									int y1 = (int)((height - factorY * (ySeries[i] - yMin)) - yBorder);
									points.add(new Point(x1, y1));
									xb = x;
								}
							}
						}
						//
						int size = points.size();
						if(size > 0) {
							int[] xvals = new int[size];
							int[] yvals = new int[size];
							for(int i = 0; i < size; i++) {
								IPoint point = points.get(i);
								xvals[i] = (int)point.getX();
								yvals[i] = (int)point.getY();
							}
							graphics2D.drawPolyline(xvals, yvals, size);
						}
						/*
						 * Font Metrics
						 */
						AffineTransform affineTransform = new AffineTransform();
						FontRenderContext fontRenderContext = new FontRenderContext(affineTransform, true, true);
						/*
						 * X Axis
						 */
						int x11 = (int)(xBorder);
						int y11 = (int)(height - yBorder);
						int x12 = (int)(width - xBorder);
						int y12 = (int)(height - yBorder);
						graphics2D.drawLine(x11, y11, x12, y12);
						/*
						 * Scale
						 */
						if(!"".equals(labelX)) {
							int textwidth = (int)(font.getStringBounds(labelX, fontRenderContext).getWidth());
							int textheight = (int)(font.getStringBounds(labelX, fontRenderContext).getHeight());
							int x1 = (int)(width / 2 - textwidth / 2);
							int y1 = (int)(height - yBorder + textheight + yIntent);
							graphics2D.drawString(labelX, x1, y1);
						}
						/*
						 * X Tick 1
						 */
						if(axisScaleConverterX != null) {
							String text = decimalFormatX.format(axisScaleConverterX.convertToSecondaryUnit(xMin));
							int textwidth = (int)(font.getStringBounds(text, fontRenderContext).getWidth());
							int textheight = (int)(font.getStringBounds(text, fontRenderContext).getHeight());
							int x1 = (int)(xBorder);
							int y1 = (int)(height - yBorder);
							int x2 = (int)(xBorder);
							int y2 = (int)(height - yBorder / 2);
							int x3 = (int)(xBorder - textwidth / 2);
							int y3 = (int)(height - yBorder / 2 + textheight);
							graphics2D.drawLine(x1, y1, x2, y2);
							graphics2D.drawString(text, x3, y3);
						}
						/*
						 * X Tick 5
						 */
						if(axisScaleConverterX != null) {
							String text = decimalFormatX.format(axisScaleConverterX.convertToSecondaryUnit(xMax));
							int textwidth = (int)(font.getStringBounds(text, fontRenderContext).getWidth());
							int textheight = (int)(font.getStringBounds(text, fontRenderContext).getHeight());
							int x1 = (int)(width - xBorder);
							int y1 = (int)(height - yBorder);
							int x2 = (int)(width - xBorder);
							int y2 = (int)(height - yBorder / 2);
							int x3 = (int)(width - xBorder - textwidth / 2);
							int y3 = (int)(height - yBorder / 2 + textheight);
							graphics2D.drawLine(x1, y1, x2, y2);
							graphics2D.drawString(text, x3, y3);
						}
						/*
						 * Y Axis
						 */
						int x21 = (int)(xBorder);
						int y21 = (int)(yBorder);
						int x22 = (int)(xBorder);
						int y22 = (int)(height - yBorder);
						graphics2D.drawLine(x21, y21, x22, y22);
						/*
						 * Y Tick 1
						 */
						if(axisScaleConverterY != null) {
							String text = decimalFormatY.format(axisScaleConverterY.convertToSecondaryUnit(yMin));
							int textheight = (int)(font.getStringBounds(text, fontRenderContext).getHeight());
							int x1 = (int)(xBorder - xBorder / 5);
							int y1 = (int)(height - yBorder);
							int x2 = (int)(xBorder);
							int y2 = (int)(height - yBorder);
							int x3 = (int)(xIntent);
							int y3 = (int)(height - yBorder + textheight / 2);
							graphics2D.drawLine(x1, y1, x2, y2);
							graphics2D.drawString(text, x3, y3);
						}
						/*
						 * Y Tick 5
						 */
						if(axisScaleConverterY != null) {
							String text = decimalFormatY.format(axisScaleConverterY.convertToSecondaryUnit(yMax));
							int textheight = (int)(font.getStringBounds(text, fontRenderContext).getHeight());
							int x1 = (int)(xBorder - xBorder / 5);
							int y1 = (int)(yBorder);
							int x2 = (int)(xBorder);
							int y2 = (int)(yBorder);
							int x3 = (int)(xIntent);
							int y3 = (int)(yBorder + textheight / 2);
							graphics2D.drawLine(x1, y1, x2, y2);
							graphics2D.drawString(text, x3, y3);
						}
						/*
						 * Branding
						 */
						String slogan = "https://openchrom.net";
						FontMetrics fontMetrics = graphics2D.getFontMetrics();
						int heightSlogan = fontMetrics.getHeight();
						int widthSlogan = fontMetrics.stringWidth(slogan);
						graphics2D.drawString(slogan, (int)(width - xBorder - widthSlogan), (int)(height - yBorder + heightSlogan));
					}
				}
			}
		}
		//
		return graphics2D.getCommands();
	}
}
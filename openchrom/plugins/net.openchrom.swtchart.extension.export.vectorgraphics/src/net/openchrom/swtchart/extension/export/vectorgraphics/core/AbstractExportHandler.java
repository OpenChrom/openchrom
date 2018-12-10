/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.swtchart.extension.export.vectorgraphics.core;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Locale;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtchart.extensions.core.BaseChart;
import org.eclipse.swtchart.extensions.core.IAxisScaleConverter;
import org.eclipse.swtchart.extensions.core.IAxisSettings;
import org.eclipse.swtchart.extensions.core.ISecondaryAxisSettings;
import org.eclipse.swtchart.extensions.core.ScrollableChart;
import org.eclipse.swtchart.extensions.menu.export.AbstractSeriesExportHandler;
import org.eclipse.swtchart.extensions.menu.export.ExportSettingsDialog;
import org.eclipse.swtchart.extensions.menu.export.ISeriesExportConverter;
import org.swtchart.ISeries;

import de.erichseifert.vectorgraphics2d.VectorGraphics2D;
import de.erichseifert.vectorgraphics2d.intermediate.CommandSequence;
import de.erichseifert.vectorgraphics2d.util.PageSize;

public abstract class AbstractExportHandler extends AbstractSeriesExportHandler implements ISeriesExportConverter {

	public static final PageSize A0_LANDSCAPE = new PageSize(1189.0, 841.0);
	public static final PageSize A4_LANDSCAPE = new PageSize(297.0, 210.0);
	public static final PageSize SELECTED_PAGE_SIZE = A0_LANDSCAPE;

	public CommandSequence getCommandSequence(Shell shell, ScrollableChart scrollableChart) {

		Graphics2D graphics2D = new VectorGraphics2D();
		//
		DecimalFormat decimalFormatX = new DecimalFormat(("0.00"), new DecimalFormatSymbols(Locale.ENGLISH));
		DecimalFormat decimalFormatY = new DecimalFormat(("0.0#E0"), new DecimalFormatSymbols(Locale.ENGLISH));
		//
		BaseChart baseChart = scrollableChart.getBaseChart();
		ExportSettingsDialog exportSettingsDialog = new ExportSettingsDialog(shell, baseChart);
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
				IAxisSettings axisSettingsY = baseChart.getYAxisSettings(indexAxisY);
				IAxisScaleConverter axisScaleConverterY = null;
				if(axisSettingsY instanceof ISecondaryAxisSettings) {
					ISecondaryAxisSettings secondaryAxisSettings = (ISecondaryAxisSettings)axisSettingsY;
					axisScaleConverterY = secondaryAxisSettings.getAxisScaleConverter();
				}
				/*
				 * OK
				 */
				double width = SELECTED_PAGE_SIZE.getWidth();
				double height = SELECTED_PAGE_SIZE.getHeight();
				double xBorder = 50;
				double yBorder = 50;
				double xIntent = 5;
				double yIntent = 5;
				//
				ISeries[] series = baseChart.getSeriesSet().getSeries();
				ISeries dataSeries = series[0];
				if(dataSeries != null) {
					double[] xSeries = dataSeries.getXSeries();
					double[] ySeries = dataSeries.getYSeries();
					double xMin = Arrays.stream(xSeries).min().getAsDouble();
					double xMax = Arrays.stream(xSeries).max().getAsDouble();
					double yMin = Arrays.stream(ySeries).min().getAsDouble();
					double yMax = Arrays.stream(ySeries).max().getAsDouble();
					//
					double xDenumerator = xMax - xMin;
					double yDenumerator = yMax - yMin;
					//
					if(xMax > 0 && yMax > 0) {
						double factorX = (width - 2 * xBorder) / xDenumerator;
						double factorY = (height - 2 * yBorder) / yDenumerator;
						/*
						 * Chromatogram
						 */
						int nPoints = xSeries.length - 1;
						for(int i = 0; i < nPoints; i++) {
							int x1 = (int)((factorX * (xSeries[i] - xMin)) + xBorder);
							int y1 = (int)((height - factorY * (ySeries[i] - yMin)) - yBorder);
							int x2 = (int)((factorX * (xSeries[i + 1] - xMin)) + xBorder);
							int y2 = (int)((height - factorY * (ySeries[i + 1] - yMin)) - yBorder);
							graphics2D.drawLine(x1, y1, x2, y2);
						}
						/*
						 * Font Metrics
						 */
						AffineTransform affineTransform = new AffineTransform();
						FontRenderContext fontRenderContext = new FontRenderContext(affineTransform, true, true);
						Font font = graphics2D.getFont();
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
					}
				}
			}
		}
		//
		return ((VectorGraphics2D)graphics2D).getCommands();
	}
}

/*******************************************************************************
 * Copyright (c) 2023, 2026 Lablicate GmbH.
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

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.numeric.core.IPoint;
import org.eclipse.chemclipse.numeric.core.Point;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtchart.IAxis;
import org.eclipse.swtchart.LineStyle;
import org.eclipse.swtchart.Range;
import org.eclipse.swtchart.export.core.VectorExportSettingsDialog;
import org.eclipse.swtchart.extensions.core.BaseChart;
import org.eclipse.swtchart.extensions.core.IAxisScaleConverter;
import org.eclipse.swtchart.extensions.core.IAxisSettings;
import org.eclipse.swtchart.extensions.core.IChartSettings;
import org.eclipse.swtchart.extensions.core.ISecondaryAxisSettings;
import org.eclipse.swtchart.extensions.core.RangeRestriction;
import org.eclipse.swtchart.extensions.core.ScrollableChart;
import org.eclipse.swtchart.extensions.model.ElementLine;
import org.eclipse.swtchart.extensions.model.ElementPolygon;
import org.eclipse.swtchart.extensions.model.ElementRectangle;
import org.eclipse.swtchart.extensions.model.ICustomSeries;
import org.eclipse.swtchart.extensions.model.IElement;
import org.eclipse.swtchart.extensions.model.IGraphicElement;
import org.eclipse.swtchart.extensions.model.ITextElement;
import org.eclipse.swtchart.vectorgraphics2d.core.VectorGraphics2D;
import org.eclipse.swtchart.vectorgraphics2d.intermediate.CommandSequence;
import org.eclipse.swtchart.vectorgraphics2d.util.PageSize;

import net.openchrom.swtchart.extension.export.vectorgraphics.model.PageSettings;
import net.openchrom.swtchart.extension.export.vectorgraphics.model.PageSizeOption;
import net.openchrom.swtchart.extension.export.vectorgraphics.support.AWTUtils;

public abstract class AbstractCommandGenerator implements IChartCommandGenerator {

	@Override
	public CommandSequence getCommandSequence(Shell shell, PageSizeOption pageSizeOption, ScrollableChart scrollableChart) {

		BaseChart baseChart = scrollableChart.getBaseChart();
		VectorExportSettingsDialog exportSettingsDialog = new VectorExportSettingsDialog(shell, baseChart);
		exportSettingsDialog.create();

		if(exportSettingsDialog.open() == Window.OK) {

			int indexAxisX = exportSettingsDialog.getIndexAxisSelectionX();
			int indexAxisY = exportSettingsDialog.getIndexAxisSelectionY();
			return getCommandSequence(pageSizeOption, indexAxisX, indexAxisY, scrollableChart);
		}

		return null;
	}

	public Point calculateScale(VectorGraphics2D graphics2D, PageSizeOption pageSizeOption, PageSettings pageSettings) {

		/*
		 * Use the full landscape and then scale the image.
		 */
		pageSettings.updateChartSettings(pageSizeOption.chartSettings());
		graphics2D.setStroke(pageSettings.getStrokeSolid());
		graphics2D.setFont(pageSettings.getFont());
		PageSize pageSize = pageSizeOption.pageSize();
		/*
		 * Scale to selected end format.
		 */
		Point scale;
		if(PageSettings.FULL_LANDSCAPE.equals(pageSizeOption)) {
			scale = new Point(1.0, 1.0);
		} else {
			PageSize pageSizeFull = PageSettings.FULL_LANDSCAPE.pageSize();
			double x = pageSize.getWidth() / pageSizeFull.getWidth();
			double y = pageSize.getHeight() / pageSizeFull.getHeight();
			scale = new Point(x, y);
		}
		graphics2D.scale(scale.getX(), scale.getY());

		return scale;
	}

	public void drawAxes(Graphics2D graphics2D, IPoint scale, BaseChart baseChart, int indexAxisX, int indexAxisY, PageSettings pageSettings) {

		drawAxisX(graphics2D, baseChart, indexAxisX, pageSettings);
		drawAxisY(graphics2D, scale, baseChart, indexAxisY, pageSettings);
	}

	private void drawAxisX(Graphics2D graphics2D, BaseChart baseChart, int indexAxisX, PageSettings pageSettings) {

		int numberTics = pageSettings.getChartSettings().getNumberTics();
		double width = pageSettings.getWidth();
		double height = pageSettings.getHeight();
		double xBorderLeft = pageSettings.getBorderLeftX();
		double xBorderRight = pageSettings.getBorderRightX();
		double yBorderTop = pageSettings.getBorderTopY();
		double yBorderBottom = pageSettings.getBorderBottomY();
		/*
		 * Font/Color
		 */
		graphics2D.setFont(pageSettings.getFont());
		graphics2D.setStroke(pageSettings.getStrokeSolid());
		graphics2D.setColor(pageSettings.getColorBlack());
		FontMetrics fontMetrics = graphics2D.getFontMetrics();
		/*
		 * X Axis
		 */
		IAxis axisX = baseChart.getAxisSet().getXAxis(BaseChart.ID_PRIMARY_X_AXIS);
		IAxisSettings axisSettingsX = baseChart.getXAxisSettings(indexAxisX);
		IAxisScaleConverter axisScaleConverterX = null;
		String labelX = axisSettingsX.getLabel();
		DecimalFormat decimalFormatX = axisSettingsX.getDecimalFormat();

		if(axisSettingsX instanceof ISecondaryAxisSettings secondaryAxisSettings) {
			axisScaleConverterX = secondaryAxisSettings.getAxisScaleConverter();
			labelX = secondaryAxisSettings.getLabel();
			decimalFormatX = secondaryAxisSettings.getDecimalFormat();
		}
		/*
		 * Settings
		 */
		Range rangeX = axisX.getRange();
		double deltaRange = (rangeX.upper - rangeX.lower) / numberTics;
		double deltaWidth = (width - xBorderLeft - xBorderRight) / numberTics;
		/*
		 * Scale
		 */
		if(!labelX.isEmpty()) {
			int widthText = fontMetrics.stringWidth(labelX);
			int heightText = fontMetrics.getHeight();
			int x = (int)(width / 2.0d - widthText / 2.0d);
			int y = (int)(height - (yBorderBottom / 3.0d) + heightText);
			graphics2D.drawString(labelX, x, y);
		}
		/*
		 * Grid
		 */
		if(isGridDisplayed(baseChart.getChartSettings())) {
			graphics2D.setStroke(pageSettings.getStrokeDash());
			graphics2D.setColor(pageSettings.getColorGray());
			for(int i = 1; i <= numberTics; i++) {
				int x = (int)(xBorderLeft + i * deltaWidth);
				int y1 = (int)(yBorderTop);
				int y2 = (int)(height - yBorderBottom);
				graphics2D.drawLine(x, y1, x, y2);
			}
		}
		/*
		 * Tics
		 */
		graphics2D.setStroke(pageSettings.getStrokeSolid());
		graphics2D.setColor(pageSettings.getColorBlack());
		for(int i = 1; i <= numberTics; i++) {
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
		graphics2D.setStroke(pageSettings.getStrokeSolid());
		graphics2D.drawLine(x11, y11, x12, y12);
	}

	private void drawAxisY(Graphics2D graphics2D, IPoint scale, BaseChart baseChart, int indexAxisY, PageSettings pageSettings) {

		int numberTics = pageSettings.getChartSettings().getNumberTics();
		double width = pageSettings.getWidth();
		double height = pageSettings.getHeight();
		double xBorderLeft = pageSettings.getBorderLeftX();
		double xBorderRight = pageSettings.getBorderRightX();
		double yBorderTop = pageSettings.getBorderTopY();
		double yBorderBottom = pageSettings.getBorderBottomY();
		/*
		 * Font/Color
		 */
		graphics2D.setFont(pageSettings.getFont());
		graphics2D.setStroke(pageSettings.getStrokeSolid());
		graphics2D.setColor(pageSettings.getColorBlack());
		FontMetrics fontMetrics = graphics2D.getFontMetrics();
		/*
		 * Y Axis
		 */
		IAxis axisY = baseChart.getAxisSet().getYAxis(BaseChart.ID_PRIMARY_Y_AXIS);
		IAxisSettings axisSettingsY = baseChart.getYAxisSettings(indexAxisY);
		IAxisScaleConverter axisScaleConverterY = null;
		String labelY = axisSettingsY.getLabel();
		DecimalFormat decimalFormatY = axisSettingsY.getDecimalFormat();

		if(axisSettingsY instanceof ISecondaryAxisSettings secondaryAxisSettings) {
			axisScaleConverterY = secondaryAxisSettings.getAxisScaleConverter();
			labelY = secondaryAxisSettings.getLabel();
			decimalFormatY = secondaryAxisSettings.getDecimalFormat();
		}
		/*
		 * Settings
		 */
		Range rangeY = axisY.getRange();
		double lower = baseChart.getMinY();
		double deltaRange = (rangeY.upper - lower) / numberTics; // Watch Out: Force to have no offset
		double deltaHeight = (height - yBorderTop - yBorderBottom) / numberTics;
		/*
		 * Scale
		 */
		if(!labelY.isEmpty()) {
			int widthText = fontMetrics.stringWidth(labelY);
			int heightText = fontMetrics.getHeight();
			int x = (int)(xBorderLeft / 8.0d);
			int y = (int)((height - yBorderBottom) / 2.0 - (heightText / 2.0d));
			drawStringCentered(graphics2D, scale, labelY, -90, x, y, widthText, heightText);
		}
		/*
		 * Grid
		 */
		if(isGridDisplayed(baseChart.getChartSettings())) {
			graphics2D.setStroke(pageSettings.getStrokeDash());
			graphics2D.setColor(pageSettings.getColorGray());
			for(int i = 0; i < numberTics; i++) {
				int x1 = (int)(xBorderLeft);
				int x2 = (int)(width - xBorderRight);
				int y = (int)(yBorderTop + i * deltaHeight);
				graphics2D.drawLine(x1, y, x2, y);
			}
		}
		/*
		 * Tics
		 */
		graphics2D.setStroke(pageSettings.getStrokeSolid());
		graphics2D.setColor(pageSettings.getColorBlack());
		for(int i = 0; i < numberTics; i++) {
			double yMin = lower + (numberTics - i) * deltaRange;
			String label = decimalFormatY.format((axisScaleConverterY != null) ? axisScaleConverterY.convertToSecondaryUnit(yMin) : yMin);
			int heightText = fontMetrics.getHeight();
			int x1 = (int)(xBorderLeft / 2.5d);
			int x2 = (int)(xBorderLeft / 1.2d);
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
		graphics2D.setStroke(pageSettings.getStrokeSolid());
		graphics2D.drawLine(x21, y21, x22, y22);
	}

	private void drawStringCentered(Graphics2D graphics2D, IPoint scale, String label, int rotation, int x, int y, int widthText, int heightText) {

		int x1 = (int)(x + heightText / 2.0d);
		int y1 = (int)(y + widthText / 2.0d);

		AffineTransform affineTransformDefault = graphics2D.getTransform();
		AffineTransform affineTransform = createAffineTransform(scale);
		affineTransform.rotate(Math.toRadians(rotation), x1, y1);
		graphics2D.setTransform(affineTransform);
		graphics2D.drawString(label, x1, y1);
		graphics2D.setTransform(affineTransformDefault);
	}

	public void drawStringNormal(Graphics2D graphics2D, IPoint scale, String label, int rotation, int x, int y, int widthText, int heightText) {

		int x1 = (int)(x + (heightText / 4.0d));
		int y1 = (int)(y - (heightText / 4.0d)); // Small distance

		AffineTransform affineTransformDefault = graphics2D.getTransform();
		AffineTransform affineTransform = createAffineTransform(scale);
		affineTransform.rotate(Math.toRadians(rotation), x1, y1);
		graphics2D.setTransform(affineTransform);
		graphics2D.drawString(label, x1, y1);
		graphics2D.setTransform(affineTransformDefault);
	}

	public boolean isGridDisplayed(IChartSettings chartSettings) {

		List<IAxisSettings> axisSettingsList = getAxisSettings(chartSettings);
		for(IAxisSettings axisSettings : axisSettingsList) {
			if(isGridDisplayed(axisSettings)) {
				return true;
			}
		}

		return false;
	}

	private List<IAxisSettings> getAxisSettings(IChartSettings chartSettings) {

		List<IAxisSettings> axisSettingsList = new ArrayList<>();
		/*
		 * Primary Axis X/Y
		 */
		axisSettingsList.add(chartSettings.getPrimaryAxisSettingsX());
		axisSettingsList.add(chartSettings.getPrimaryAxisSettingsY());
		/*
		 * Secondary Axes X/Y
		 */
		for(IAxisSettings axisSettings : chartSettings.getSecondaryAxisSettingsListX()) {
			axisSettingsList.add(axisSettings);
		}

		for(IAxisSettings axisSettings : chartSettings.getSecondaryAxisSettingsListY()) {
			axisSettingsList.add(axisSettings);
		}

		return axisSettingsList;
	}

	private boolean isGridDisplayed(IAxisSettings axisSettings) {

		return axisSettings.isVisible() && !LineStyle.NONE.equals(axisSettings.getGridLineStyle());
	}

	public AffineTransform createAffineTransform(IPoint scale) {

		AffineTransform affineTransform = new AffineTransform();
		affineTransform.scale(scale.getX(), scale.getY());

		return affineTransform;
	}

	public void drawBranding(Graphics2D graphics2D, PageSettings pageSettings) {

		double width = pageSettings.getWidth();
		double xBorderRight = pageSettings.getBorderRightX();
		double yBorderTop = pageSettings.getBorderTopY();

		graphics2D.setFont(pageSettings.getFont());
		graphics2D.setColor(pageSettings.getColorDarkGray());
		graphics2D.setStroke(pageSettings.getStrokeSolid());

		String label = "https://openchrom.net";
		FontMetrics fontMetrics = graphics2D.getFontMetrics();
		int widthText = fontMetrics.stringWidth(label);
		int heightText = fontMetrics.getHeight();
		int x = (int)(width - xBorderRight - widthText);
		int y = (int)((yBorderTop / 2.0d) + (heightText / 2.0d));
		graphics2D.drawString(label, x, y);
	}

	public void drawTitle(Graphics2D graphics2D, PageSettings pageSettings, ScrollableChart scrollableChart) {

		IChartSettings chartSettings = scrollableChart.getChartSettings();
		if(chartSettings.isTitleVisible()) {
			double xBorderLeft = pageSettings.getBorderLeftX();
			double yBorderTop = pageSettings.getBorderTopY();

			graphics2D.setFont(pageSettings.getFont());
			graphics2D.setColor(pageSettings.getColorDarkGray());
			graphics2D.setStroke(pageSettings.getStrokeSolid());

			String label = chartSettings.getTitle();
			FontMetrics fontMetrics = graphics2D.getFontMetrics();
			int heightText = fontMetrics.getHeight();
			int x = (int)(xBorderLeft);
			int y = (int)((yBorderTop / 2.0d) + (heightText / 2.0d));
			graphics2D.drawString(label, x, y);
		}
	}

	public void drawCustomSeries(Graphics2D graphics2D, IPoint scale, BaseChart baseChart, PageSettings pageSettings) {

		double width = pageSettings.getWidth();
		double height = pageSettings.getHeight();
		double xBorderLeft = pageSettings.getBorderLeftX();
		double xBorderRight = pageSettings.getBorderRightX();
		double yBorderTop = pageSettings.getBorderTopY();
		double yBorderBottom = pageSettings.getBorderBottomY();

		IChartSettings chartSettings = baseChart.getChartSettings();
		RangeRestriction raneRangeRestriction = chartSettings.getRangeRestriction();
		double extendMaxY = raneRangeRestriction.getExtendMaxY();

		graphics2D.setFont(pageSettings.getFont());
		graphics2D.setColor(pageSettings.getColorBlack());
		graphics2D.setStroke(pageSettings.getStrokeSolid());
		FontMetrics fontMetrics = graphics2D.getFontMetrics();

		IAxis axisX = baseChart.getAxisSet().getXAxis(BaseChart.ID_PRIMARY_X_AXIS);
		IAxis axisY = baseChart.getAxisSet().getYAxis(BaseChart.ID_PRIMARY_Y_AXIS);
		Range rangeX = axisX.getRange();
		Range rangeY = axisY.getRange();

		double xMin = rangeX.lower; // baseChart.getMinX();
		double xMax = rangeX.upper; // baseChart.getMaxX();
		double yMin = baseChart.getMinY(); // Force to have no offset
		double yMax = rangeY.upper; // baseChart.getMaxY();

		double xDenumerator = xMax - xMin;
		double yDenumerator = (yMax + yMax * extendMaxY) - yMin;

		if(xMax > 0 && yMax > 0) {
			/*
			 * Factors
			 */
			double factorX = (width - xBorderLeft - xBorderRight) / xDenumerator;
			double factorY = (height - yBorderTop - yBorderBottom) / yDenumerator;

			for(ICustomSeries customSeries : baseChart.getCustomSeries()) {
				if(customSeries.isDraw()) {
					/*
					 * Graphic Elements
					 */
					for(IGraphicElement graphicElement : customSeries.getGraphicElements()) {
						double x = graphicElement.getX();
						if(x >= xMin && x <= xMax) {

							graphics2D.setColor(AWTUtils.convertColor(graphicElement.getColor(), graphicElement.getAlpha()));
							graphics2D.setStroke(pageSettings.getStrokeSolid());

							double y = graphicElement.getY();
							int x1 = getX(factorX, x, xMin, xBorderLeft, xBorderRight);
							int y1 = getY(factorY, y, height, yMin, yBorderTop, yBorderBottom);

							if(graphicElement instanceof ElementRectangle elementRectangle) {
								/*
								 * Rectangle
								 */
								int width1 = getWidth(factorX, elementRectangle.getWidth(), width, xBorderLeft, xBorderRight);
								int height1 = getHeight(factorY, elementRectangle.getHeight(), height, yBorderTop, yBorderBottom);
								graphics2D.fillRect(x1, y1, width1, height1);
							} else if(graphicElement instanceof ElementPolygon elementPolygon) {
								/*
								 * Polygon
								 */
								if(elementPolygon.hasData()) {
									double[] polygon = elementPolygon.getPolygon();
									int size = polygon.length;
									int nPoints = size / 2;
									int[] xPoints = new int[nPoints];
									int[] yPoints = new int[nPoints];
									int index = 0;
									for(int i = 0; i < (size - 1); i++) {
										if(i % 2 == 0) {
											xPoints[index] = getX(factorX, polygon[i], xMin, xBorderLeft, xBorderRight);
											yPoints[index] = getY(factorY, polygon[i + 1], height, yMin, yBorderTop, yBorderBottom);
											index++;
										}
									}
									graphics2D.fillPolygon(xPoints, yPoints, nPoints);
								}
							} else if(graphicElement instanceof ElementLine elementLine) {
								/*
								 * Line
								 */
								int x2 = getX(factorX, elementLine.getX2(), xMin, xBorderLeft, xBorderRight);
								int y2 = getY(factorY, elementLine.getY2(), height, yMin, yBorderTop, yBorderBottom);
								graphics2D.setStroke(pageSettings.getStroke(elementLine.getLineStyle(), elementLine.getLineWidth()));
								graphics2D.drawLine(x1, y1, x2, y2);
							}
						}
					}
					/*
					 * Text Elements
					 */
					for(ITextElement textElement : customSeries.getTextElements()) {
						double x = textElement.getX();
						if(x >= xMin && x <= xMax) {

							graphics2D.setFont(pageSettings.getFont());
							graphics2D.setColor(AWTUtils.convertColor(textElement.getColor(), textElement.getAlpha()));
							graphics2D.setStroke(pageSettings.getStrokeSolid());

							double y = textElement.getY();
							String label = textElement.getLabel();
							int rotation = textElement.getRotation();
							int widthText = fontMetrics.stringWidth(label);
							int heightText = fontMetrics.getHeight();
							int x1 = (int)((factorX * (x - xMin)) + xBorderLeft);
							int y1 = (int)((height - factorY * (y - yMin)) - yBorderBottom);
							drawStringNormal(graphics2D, scale, label, rotation, x1, y1, widthText, heightText);
						}
					}
				}
			}
		}
	}

	private int getX(double factorX, double x, double xMin, double xBorderLeft, double xBorderRight) {

		if(x == IElement.POSITION_LEFT_X) {
			return (int)xBorderLeft;
		} else if(x == IElement.POSITION_RIGHT_X) {
			return (int)xBorderRight;
		} else {
			return (int)((factorX * (x - xMin)) + xBorderLeft);
		}
	}

	private int getY(double factorY, double y, double pageHeight, double yMin, double yBorderTop, double yBorderBottom) {

		if(y == IElement.POSITION_TOP_Y) {
			return (int)yBorderTop;
		} else if(y == IElement.POSITION_BOTTOM_Y) {
			return (int)(pageHeight - yBorderBottom);
		} else {
			return (int)((pageHeight - factorY * (y - yMin)) - yBorderBottom);
		}
	}

	private int getWidth(double factorX, double width, double pageWidth, double xBorderLeft, double xBorderRight) {

		if(width == IElement.MAX_WIDTH) {
			return (int)(pageWidth - xBorderLeft - xBorderRight);
		} else {
			return (int)(factorX * width);
		}
	}

	private int getHeight(double factorY, double height, double pageHeight, double yBorderTop, double yBorderBottom) {

		if(height == IElement.MAX_HEIGHT) {
			return (int)(pageHeight - yBorderTop - yBorderBottom);
		} else {
			return (int)(factorY * height);
		}
	}
}

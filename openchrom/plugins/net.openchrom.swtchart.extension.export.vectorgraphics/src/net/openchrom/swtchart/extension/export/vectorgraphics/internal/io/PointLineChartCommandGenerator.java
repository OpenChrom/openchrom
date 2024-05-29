/*******************************************************************************
 * Copyright (c) 2023, 2024 Lablicate GmbH.
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

import java.awt.Color;
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
import org.eclipse.swtchart.ILineSeries.PlotSymbolType;
import org.eclipse.swtchart.ISeries;
import org.eclipse.swtchart.LineStyle;
import org.eclipse.swtchart.Range;
import org.eclipse.swtchart.export.core.VectorExportSettingsDialog;
import org.eclipse.swtchart.extensions.core.BaseChart;
import org.eclipse.swtchart.extensions.core.IAxisScaleConverter;
import org.eclipse.swtchart.extensions.core.IAxisSettings;
import org.eclipse.swtchart.extensions.core.IChartSettings;
import org.eclipse.swtchart.extensions.core.IPointSeriesSettings;
import org.eclipse.swtchart.extensions.core.ISecondaryAxisSettings;
import org.eclipse.swtchart.extensions.core.ISeriesSettings;
import org.eclipse.swtchart.extensions.core.RangeRestriction;
import org.eclipse.swtchart.extensions.core.ScrollableChart;
import org.eclipse.swtchart.extensions.linecharts.ILineSeriesSettings;
import org.eclipse.swtchart.extensions.model.ElementLine;
import org.eclipse.swtchart.extensions.model.ElementRectangle;
import org.eclipse.swtchart.extensions.model.ICustomSeries;
import org.eclipse.swtchart.extensions.model.IElement;
import org.eclipse.swtchart.extensions.model.IGraphicElement;
import org.eclipse.swtchart.extensions.model.ITextElement;

import net.openchrom.swtchart.extension.export.vectorgraphics.model.PageSettings;
import net.openchrom.swtchart.extension.export.vectorgraphics.model.PageSizeOption;
import net.openchrom.swtchart.extension.export.vectorgraphics.support.AWTUtils;

import de.erichseifert.vectorgraphics2d.VectorGraphics2D;
import de.erichseifert.vectorgraphics2d.intermediate.CommandSequence;
import de.erichseifert.vectorgraphics2d.util.PageSize;

/*
 * Important to now:
 * Basically, x|y 0,0 is on the left top side.
 * ---
 * drawString("text", x, y)
 * The anchor is on the left side at the bottom.
 * ---
 * affineTransform.rotate(Math.toRadians(-90), anchorx, anchory);
 */
public class PointLineChartCommandGenerator implements IChartCommandGenerator {

	private static final PageSizeOption FULL_LANDSCAPE = PageSizeOption.FULL_LANDSCAPE;
	private static final int NUMBER_TICS = 20;

	@Override
	public CommandSequence getCommandSequence(Shell shell, PageSizeOption pageSizeOption, ScrollableChart scrollableChart) {

		/*
		 * Use the full landscape and the scale the image.
		 */
		VectorGraphics2D graphics2D = new VectorGraphics2D();
		PageSettings pageSettings = new PageSettings(FULL_LANDSCAPE);
		graphics2D.setStroke(pageSettings.getStrokeSolid());
		graphics2D.setFont(pageSettings.getFont());
		PageSize pageSize = pageSizeOption.pageSize();
		/*
		 * Calculate/Set Scale
		 */
		Point scale;
		if(FULL_LANDSCAPE.equals(pageSizeOption)) {
			scale = new Point(1.0, 1.0);
		} else {
			PageSize pageSizeFull = FULL_LANDSCAPE.pageSize();
			double x = pageSize.getWidth() / pageSizeFull.getWidth();
			double y = pageSize.getHeight() / pageSizeFull.getHeight();
			scale = new Point(x, y);
		}
		graphics2D.scale(scale.getX(), scale.getY());
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
				drawAxes(graphics2D, scale, baseChart, indexAxisX, indexAxisY, pageSettings);
				drawStandardSeries(graphics2D, scale, baseChart, pageSettings);
				drawCustomSeries(graphics2D, scale, baseChart, pageSettings);
				drawBranding(graphics2D, pageSettings);
			}
		}
		//
		graphics2D.setClip(0, 0, (int)Math.round(pageSize.getWidth()), (int)Math.round(pageSize.getHeight()));
		return graphics2D.getCommands();
	}

	private void drawAxes(Graphics2D graphics2D, IPoint scale, BaseChart baseChart, int indexAxisX, int indexAxisY, PageSettings pageSettings) {

		drawAxisX(graphics2D, scale, baseChart, indexAxisX, pageSettings);
		drawAxisY(graphics2D, scale, baseChart, indexAxisY, pageSettings);
	}

	private void drawAxisX(Graphics2D graphics2D, IPoint scale, BaseChart baseChart, int indexAxisX, PageSettings pageSettings) {

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
		//
		if(axisSettingsX instanceof ISecondaryAxisSettings secondaryAxisSettings) {
			axisScaleConverterX = secondaryAxisSettings.getAxisScaleConverter();
			labelX = secondaryAxisSettings.getLabel();
			decimalFormatX = secondaryAxisSettings.getDecimalFormat();
		}
		/*
		 * Settings
		 */
		Range rangeX = axisX.getRange();
		double deltaRange = (rangeX.upper - rangeX.lower) / NUMBER_TICS;
		double deltaWidth = (width - xBorderLeft - xBorderRight) / NUMBER_TICS;
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
			for(int i = 1; i <= NUMBER_TICS; i++) {
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
		graphics2D.setStroke(pageSettings.getStrokeSolid());
		graphics2D.drawLine(x11, y11, x12, y12);
	}

	private void drawAxisY(Graphics2D graphics2D, IPoint scale, BaseChart baseChart, int indexAxisY, PageSettings pageSettings) {

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
		//
		if(axisSettingsY instanceof ISecondaryAxisSettings secondaryAxisSettings) {
			axisScaleConverterY = secondaryAxisSettings.getAxisScaleConverter();
			labelY = secondaryAxisSettings.getLabel();
			decimalFormatY = secondaryAxisSettings.getDecimalFormat();
		}
		/*
		 * Settings
		 */
		Range rangeY = axisY.getRange();
		double deltaRange = (rangeY.upper - baseChart.getMinY()) / NUMBER_TICS; // Watch Out: Force to have no offset
		double deltaHeight = (height - yBorderTop - yBorderBottom) / NUMBER_TICS;
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
			for(int i = 0; i < NUMBER_TICS; i++) {
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
		for(int i = 0; i < NUMBER_TICS; i++) {
			double yMin = rangeY.lower + (NUMBER_TICS - i) * deltaRange;
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

	private void drawStandardSeries(Graphics2D graphics2D, IPoint scale, BaseChart baseChart, PageSettings pageSettings) {

		double width = pageSettings.getWidth();
		double height = pageSettings.getHeight();
		double xBorderLeft = pageSettings.getBorderLeftX();
		double xBorderRight = pageSettings.getBorderRightX();
		double yBorderTop = pageSettings.getBorderTopY();
		double yBorderBottom = pageSettings.getBorderBottomY();
		//
		IChartSettings chartSettings = baseChart.getChartSettings();
		RangeRestriction raneRangeRestriction = chartSettings.getRangeRestriction();
		double extendMaxY = raneRangeRestriction.getExtendMaxY();
		//
		IAxis axisX = baseChart.getAxisSet().getXAxis(BaseChart.ID_PRIMARY_X_AXIS);
		IAxis axisY = baseChart.getAxisSet().getYAxis(BaseChart.ID_PRIMARY_Y_AXIS);
		Range rangeX = axisX.getRange();
		Range rangeY = axisY.getRange();
		//
		double xMin = rangeX.lower; // baseChart.getMinX();
		double xMax = rangeX.upper; // baseChart.getMaxX();
		double yMin = baseChart.getMinY(); // Watch Out: Force to have no offset
		double yMax = rangeY.upper; // baseChart.getMaxY();
		//
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

	private void drawCustomSeries(Graphics2D graphics2D, IPoint scale, BaseChart baseChart, PageSettings pageSettings) {

		double width = pageSettings.getWidth();
		double height = pageSettings.getHeight();
		double xBorderLeft = pageSettings.getBorderLeftX();
		double xBorderRight = pageSettings.getBorderRightX();
		double yBorderTop = pageSettings.getBorderTopY();
		double yBorderBottom = pageSettings.getBorderBottomY();
		//
		IChartSettings chartSettings = baseChart.getChartSettings();
		RangeRestriction raneRangeRestriction = chartSettings.getRangeRestriction();
		double extendMaxY = raneRangeRestriction.getExtendMaxY();
		//
		graphics2D.setFont(pageSettings.getFont());
		graphics2D.setColor(pageSettings.getColorBlack());
		graphics2D.setStroke(pageSettings.getStrokeSolid());
		FontMetrics fontMetrics = graphics2D.getFontMetrics();
		//
		IAxis axisX = baseChart.getAxisSet().getXAxis(BaseChart.ID_PRIMARY_X_AXIS);
		IAxis axisY = baseChart.getAxisSet().getYAxis(BaseChart.ID_PRIMARY_Y_AXIS);
		Range rangeX = axisX.getRange();
		Range rangeY = axisY.getRange();
		//
		double xMin = rangeX.lower; // baseChart.getMinX();
		double xMax = rangeX.upper; // baseChart.getMaxX();
		double yMin = baseChart.getMinY(); // Force to have no offset
		double yMax = rangeY.upper; // baseChart.getMaxY();
		//
		double xDenumerator = xMax - xMin;
		double yDenumerator = (yMax + yMax * extendMaxY) - yMin;
		//
		if(xMax > 0 && yMax > 0) {
			/*
			 * Factors
			 */
			double factorX = (width - xBorderLeft - xBorderRight) / xDenumerator;
			double factorY = (height - yBorderTop - yBorderBottom) / yDenumerator;
			//
			for(ICustomSeries customSeries : baseChart.getCustomSeries()) {
				if(customSeries.isDraw()) {
					/*
					 * Text Elements
					 */
					for(ITextElement textElement : customSeries.getTextElements()) {
						double x = textElement.getX();
						if(x >= xMin && x <= xMax) {
							//
							graphics2D.setFont(pageSettings.getFont());
							graphics2D.setColor(AWTUtils.convertColor(textElement.getColor(), textElement.getAlpha()));
							graphics2D.setStroke(pageSettings.getStrokeSolid());
							//
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
					/*
					 * Graphic Elements
					 */
					for(IGraphicElement graphicElement : customSeries.getGraphicElements()) {
						double x = graphicElement.getX();
						if(x >= xMin && x <= xMax) {
							//
							graphics2D.setColor(AWTUtils.convertColor(graphicElement.getColor(), graphicElement.getAlpha()));
							graphics2D.setStroke(pageSettings.getStrokeSolid());
							//
							double y = graphicElement.getY();
							int x1 = getX(factorX, x, width, xMin, xBorderLeft, xBorderRight);
							int y1 = getY(factorY, y, height, yMin, yBorderTop, yBorderBottom);
							//
							if(graphicElement instanceof ElementRectangle elementRectangle) {
								/*
								 * Rectangle
								 */
								int width1 = getWidth(factorX, elementRectangle.getWidth(), width, xBorderLeft, xBorderRight);
								int height1 = getHeight(factorY, elementRectangle.getHeight(), height, yBorderTop, yBorderBottom);
								graphics2D.fillRect(x1, y1, width1, height1);
							} else if(graphicElement instanceof ElementLine elementLine) {
								/*
								 * Line
								 */
								int x2 = getX(factorX, elementLine.getX2(), width, xMin, xBorderLeft, xBorderRight);
								int y2 = getY(factorY, elementLine.getY2(), height, yMin, yBorderTop, yBorderBottom);
								graphics2D.setStroke(pageSettings.getStroke(elementLine.getLineStyle(), elementLine.getLineWidth()));
								graphics2D.drawLine(x1, y1, x2, y2);
							}
						}
					}
				}
			}
		}
	}

	private int getX(double factorX, double x, double pageWidth, double xMin, double xBorderLeft, double xBorderRight) {

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

	private void printLine(Graphics2D graphics2D, List<IPoint> points, int minValue, ILineSeriesSettings lineSeriesSettings, PageSettings pageSettings) {

		/*
		 * Line
		 */
		int lineWidth = lineSeriesSettings.getLineWidth();
		LineStyle lineStyle = lineSeriesSettings.getLineStyle();
		//
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
				//
				Color color = AWTUtils.convertColor(lineSeriesSettings.getLineColor());
				graphics2D.setStroke(pageSettings.getStroke(lineStyle, lineWidth));
				graphics2D.setColor(color);
				graphics2D.drawPolyline(xvals, yvals, size);
				//
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
		//
		if(length >= 2) {
			/*
			 * Edges
			 */
			int firstValue = 0;
			int lastValue = 0;
			//
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
		//
		return valsTransformed;
	}

	private void printSymbols(Graphics2D graphics2D, IPoint scale, List<IPoint> points, IPointSeriesSettings pointSeriesSettings, PageSettings pageSettings) {

		/*
		 * Symbols
		 */
		int symbolSize = pointSeriesSettings.getSymbolSize();
		PlotSymbolType symbolType = pointSeriesSettings.getSymbolType();
		//
		if(symbolSize > 0 && !PlotSymbolType.NONE.equals(symbolType)) {
			/*
			 * Factor 2, otherwise symbols font is too small.
			 */
			double size = (symbolSize * pageSettings.getFactorGraphics());
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
		//
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

	private void drawBranding(Graphics2D graphics2D, PageSettings pageSettings) {

		double width = pageSettings.getWidth();
		double xBorderRight = pageSettings.getBorderRightX();
		double yBorderTop = pageSettings.getBorderTopY();
		//
		graphics2D.setFont(pageSettings.getFont());
		graphics2D.setColor(pageSettings.getColorDarkGray());
		graphics2D.setStroke(pageSettings.getStrokeSolid());
		//
		String label = "https://openchrom.net";
		FontMetrics fontMetrics = graphics2D.getFontMetrics();
		int widthText = fontMetrics.stringWidth(label);
		int heightText = fontMetrics.getHeight();
		int x = (int)(width - xBorderRight - widthText);
		int y = (int)((yBorderTop / 2.0d) + (heightText / 2.0d));
		graphics2D.drawString(label, x, y);
	}

	private void drawTriangle(Graphics2D graphics2D, int x, int y, int width, int height) {

		int x1 = x;
		int y1 = y;
		int x2 = (int)(x + width / 2.0d);
		int y2 = y + height;
		int x3 = (int)(x - width / 2.0d);
		int y3 = y + height;
		//
		drawTriangle(graphics2D, x1, y1, x2, y2, x3, y3);
	}

	private void drawInvertedTriangle(Graphics2D graphics2D, int x, int y, int width, int height) {

		int x1 = (int)(x + width / 2.0d);
		int y1 = y;
		int x2 = (int)(x - width / 2.0d);
		int y2 = y;
		int x3 = x;
		int y3 = y + height;
		//
		drawTriangle(graphics2D, x1, y1, x2, y2, x3, y3);
	}

	private void drawTriangle(Graphics2D graphics2D, int x1, int y1, int x2, int y2, int x3, int y3) {

		int[] xPoints = new int[]{x1, x2, x3};
		int[] yPoints = new int[]{y1, y2, y3};
		graphics2D.drawPolygon(xPoints, yPoints, 3);
		graphics2D.fillPolygon(xPoints, yPoints, 3);
	}

	private void drawStringCentered(Graphics2D graphics2D, IPoint scale, String label, int rotation, int x, int y, int widthText, int heightText) {

		int x1 = (int)(x + heightText / 2.0d);
		int y1 = (int)(y + widthText / 2.0d);
		//
		AffineTransform affineTransformDefault = graphics2D.getTransform();
		AffineTransform affineTransform = createAffineTransform(scale);
		affineTransform.rotate(Math.toRadians(rotation), x1, y1);
		graphics2D.setTransform(affineTransform);
		graphics2D.drawString(label, x1, y1);
		graphics2D.setTransform(affineTransformDefault);
	}

	private void drawStringNormal(Graphics2D graphics2D, IPoint scale, String label, int rotation, int x, int y, int widthText, int heightText) {

		int x1 = (int)(x + (heightText / 4.0d));
		int y1 = (int)(y - (heightText / 4.0d)); // Small distance
		//
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
		//
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
		//
		for(IAxisSettings axisSettings : chartSettings.getSecondaryAxisSettingsListY()) {
			axisSettingsList.add(axisSettings);
		}
		//
		return axisSettingsList;
	}

	private boolean isGridDisplayed(IAxisSettings axisSettings) {

		return axisSettings.isVisible() && !LineStyle.NONE.equals(axisSettings.getGridLineStyle());
	}

	private AffineTransform createAffineTransform(IPoint scale) {

		AffineTransform affineTransform = new AffineTransform();
		affineTransform.scale(scale.getX(), scale.getY());
		//
		return affineTransform;
	}
}
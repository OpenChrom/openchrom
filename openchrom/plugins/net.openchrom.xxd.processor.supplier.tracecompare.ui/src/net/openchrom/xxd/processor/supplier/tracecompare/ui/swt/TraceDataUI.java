/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.processor.supplier.tracecompare.ui.swt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.eavp.service.swtchart.converter.MillisecondsToMinuteConverter;
import org.eclipse.eavp.service.swtchart.core.ColorAndFormatSupport;
import org.eclipse.eavp.service.swtchart.core.IChartSettings;
import org.eclipse.eavp.service.swtchart.core.IPrimaryAxisSettings;
import org.eclipse.eavp.service.swtchart.core.ISecondaryAxisSettings;
import org.eclipse.eavp.service.swtchart.core.ISeriesData;
import org.eclipse.eavp.service.swtchart.core.SecondaryAxisSettings;
import org.eclipse.eavp.service.swtchart.linecharts.ILineSeriesData;
import org.eclipse.eavp.service.swtchart.linecharts.ILineSeriesSettings;
import org.eclipse.eavp.service.swtchart.linecharts.LineChart;
import org.eclipse.eavp.service.swtchart.linecharts.LineSeriesData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.swtchart.IAxis.Position;

public class TraceDataUI extends LineChart {

	private static final int LENGTH_HINT_DATA_POINTS = 15000;
	//
	private boolean enableRangeInfo;
	private boolean showAxisTitle;
	private boolean enableHorizontalSlider;

	public TraceDataUI(Composite parent, int style, boolean enableRangeInfo, boolean showAxisTitle, boolean enableHorizontalSlider) {
		super(parent, style);
		this.enableRangeInfo = enableRangeInfo;
		this.showAxisTitle = showAxisTitle;
		this.enableHorizontalSlider = enableHorizontalSlider;
		createControl();
	}

	private void createControl() {

		configureChart();
		addDemoSeries();
	}

	private void configureChart() {

		try {
			IChartSettings chartSettings = getChartSettings();
			chartSettings.setOrientation(SWT.HORIZONTAL);
			chartSettings.setEnableRangeInfo(enableRangeInfo);
			chartSettings.setHorizontalSliderVisible(enableHorizontalSlider);
			chartSettings.setVerticalSliderVisible(false);
			chartSettings.setUseZeroX(true);
			chartSettings.setUseZeroY(true);
			//
			IPrimaryAxisSettings primaryAxisSettingsX = chartSettings.getPrimaryAxisSettingsX();
			primaryAxisSettingsX.setTitle("Retention Time (milliseconds)");
			primaryAxisSettingsX.setDecimalFormat(ColorAndFormatSupport.decimalFormatVariable);
			primaryAxisSettingsX.setColor(ColorAndFormatSupport.COLOR_BLACK);
			primaryAxisSettingsX.setPosition(Position.Secondary);
			primaryAxisSettingsX.setVisible(false);
			//
			IPrimaryAxisSettings primaryAxisSettingsY = chartSettings.getPrimaryAxisSettingsY();
			primaryAxisSettingsY.setTitle("Intensity");
			primaryAxisSettingsY.setDecimalFormat(ColorAndFormatSupport.decimalFormatScientific);
			primaryAxisSettingsY.setColor(ColorAndFormatSupport.COLOR_BLACK);
			primaryAxisSettingsY.setVisible(false);
			//
			String axisTitle = "";
			if(showAxisTitle) {
				axisTitle = "Minutes";
			}
			//
			ISecondaryAxisSettings secondaryAxisSettingsMinutes = new SecondaryAxisSettings(axisTitle, "Minutes", new MillisecondsToMinuteConverter());
			secondaryAxisSettingsMinutes.setPosition(Position.Primary);
			secondaryAxisSettingsMinutes.setDecimalFormat(ColorAndFormatSupport.decimalFormatFixed);
			secondaryAxisSettingsMinutes.setColor(ColorAndFormatSupport.COLOR_BLACK);
			chartSettings.getSecondaryAxisSettingsListX().add(secondaryAxisSettingsMinutes);
			//
			applySettings(chartSettings);
		} catch(Exception e) {
			System.out.println(e);
		}
	}

	private void addDemoSeries() {

		List<ILineSeriesData> lineSeriesDataList = new ArrayList<ILineSeriesData>();
		ISeriesData seriesData = SeriesConverter.getSeriesXY(SeriesConverter.LINE_SERIES_1);
		//
		ILineSeriesData lineSeriesData = new LineSeriesData(seriesData);
		ILineSeriesSettings lineSerieSettings = lineSeriesData.getLineSeriesSettings();
		lineSerieSettings.setEnableArea(true);
		lineSeriesDataList.add(lineSeriesData);
		/*
		 * Set series.
		 */
		addSeriesData(lineSeriesDataList, LENGTH_HINT_DATA_POINTS);
	}
}

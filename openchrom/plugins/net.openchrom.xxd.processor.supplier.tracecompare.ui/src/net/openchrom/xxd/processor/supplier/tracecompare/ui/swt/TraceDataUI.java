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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.eclipse.eavp.service.swtchart.core.IChartSettings;
import org.eclipse.eavp.service.swtchart.core.IPrimaryAxisSettings;
import org.eclipse.eavp.service.swtchart.core.ISecondaryAxisSettings;
import org.eclipse.eavp.service.swtchart.core.SecondaryAxisSettings;
import org.eclipse.eavp.service.swtchart.linecharts.LineChart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.swtchart.IAxis.Position;
import org.swtchart.LineStyle;

import net.openchrom.xxd.processor.supplier.tracecompare.ui.converter.MillisecondsToCentimeterConverter;

public class TraceDataUI extends LineChart {

	private TraceDataSettings traceDataSettings;

	public TraceDataUI(Composite parent, int style, TraceDataSettings traceDataSettings) {
		super(parent, style);
		this.traceDataSettings = traceDataSettings;
		createControl();
	}

	private void createControl() {

		configureChart();
	}

	private void configureChart() {

		try {
			IChartSettings chartSettings = getChartSettings();
			chartSettings.setOrientation(SWT.HORIZONTAL);
			chartSettings.setEnableRangeUI(traceDataSettings.isEnableRangeInfo());
			chartSettings.setHorizontalSliderVisible(traceDataSettings.isEnableHorizontalSlider());
			chartSettings.setVerticalSliderVisible(false);
			chartSettings.getRangeRestriction().setZeroX(true);
			chartSettings.getRangeRestriction().setZeroY(true);
			chartSettings.setCreateMenu(traceDataSettings.isCreateMenu());
			//
			IPrimaryAxisSettings primaryAxisSettingsX = chartSettings.getPrimaryAxisSettingsX();
			primaryAxisSettingsX.setTitle("Retention Time (milliseconds)");
			primaryAxisSettingsX.setDecimalFormat(new DecimalFormat(("0.0##"), new DecimalFormatSymbols(Locale.ENGLISH)));
			primaryAxisSettingsX.setColor(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
			primaryAxisSettingsX.setPosition(Position.Secondary);
			primaryAxisSettingsX.setVisible(false);
			primaryAxisSettingsX.setGridLineStyle(LineStyle.NONE);
			//
			IPrimaryAxisSettings primaryAxisSettingsY = chartSettings.getPrimaryAxisSettingsY();
			primaryAxisSettingsY.setTitle("Intensity");
			primaryAxisSettingsY.setDecimalFormat(new DecimalFormat(("0.0#E0"), new DecimalFormatSymbols(Locale.ENGLISH)));
			primaryAxisSettingsY.setColor(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
			primaryAxisSettingsY.setVisible(false);
			//
			String axisTitle = "";
			if(traceDataSettings.isShowAxisTitle()) {
				axisTitle = "Distance [cm]";
			}
			//
			ISecondaryAxisSettings secondaryAxisSettingsX = new SecondaryAxisSettings(axisTitle, "cm", new MillisecondsToCentimeterConverter());
			secondaryAxisSettingsX.setPosition(Position.Primary);
			secondaryAxisSettingsX.setDecimalFormat(new DecimalFormat(("0.00"), new DecimalFormatSymbols(Locale.ENGLISH)));
			secondaryAxisSettingsX.setColor(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
			chartSettings.getSecondaryAxisSettingsListX().add(secondaryAxisSettingsX);
			//
			applySettings(chartSettings);
		} catch(Exception e) {
			System.out.println(e);
		}
	}
}

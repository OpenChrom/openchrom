/*******************************************************************************
 * Copyright (c) 2017, 2018 Lablicate GmbH.
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

import org.eclipse.eavp.service.swtchart.axisconverter.RelativeIntensityConverter;
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

import net.openchrom.xxd.processor.supplier.tracecompare.ui.converter.MillisecondsToMillimeterConverter;

public class TraceDataUI extends LineChart {

	public TraceDataUI(Composite parent, int style) {
		super(parent, style);
		createControl();
	}

	private void createControl() {

		configureChart();
	}

	private void configureChart() {

		try {
			IChartSettings chartSettings = getChartSettings();
			//
			IPrimaryAxisSettings primaryAxisSettingsX = chartSettings.getPrimaryAxisSettingsX();
			primaryAxisSettingsX.setTitle("Retention Time (milliseconds)");
			primaryAxisSettingsX.setDecimalFormat(new DecimalFormat(("0.0##"), new DecimalFormatSymbols(Locale.ENGLISH)));
			primaryAxisSettingsX.setColor(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
			primaryAxisSettingsX.setPosition(Position.Secondary);
			primaryAxisSettingsX.setGridLineStyle(LineStyle.NONE);
			primaryAxisSettingsX.setVisible(false);
			//
			IPrimaryAxisSettings primaryAxisSettingsY = chartSettings.getPrimaryAxisSettingsY();
			primaryAxisSettingsY.setTitle("Intensity");
			primaryAxisSettingsY.setDecimalFormat(new DecimalFormat(("0.0#E0"), new DecimalFormatSymbols(Locale.ENGLISH)));
			primaryAxisSettingsY.setColor(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
			primaryAxisSettingsY.setGridLineStyle(LineStyle.NONE);
			primaryAxisSettingsY.setVisible(false);
			//
			ISecondaryAxisSettings secondaryAxisSettingsX = new SecondaryAxisSettings("Distance [mm]", "mm", new MillisecondsToMillimeterConverter());
			secondaryAxisSettingsX.setPosition(Position.Primary);
			secondaryAxisSettingsX.setDecimalFormat(new DecimalFormat(("0.00"), new DecimalFormatSymbols(Locale.ENGLISH)));
			secondaryAxisSettingsX.setColor(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
			secondaryAxisSettingsX.setVisible(true);
			chartSettings.getSecondaryAxisSettingsListX().add(secondaryAxisSettingsX);
			//
			ISecondaryAxisSettings secondaryAxisSettingsY = new SecondaryAxisSettings("Int [%]", "Relative Intensity [%]", new RelativeIntensityConverter(SWT.VERTICAL, true));
			secondaryAxisSettingsY.setPosition(Position.Secondary);
			secondaryAxisSettingsY.setDecimalFormat(new DecimalFormat(("0.00"), new DecimalFormatSymbols(Locale.ENGLISH)));
			secondaryAxisSettingsY.setColor(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
			secondaryAxisSettingsY.setVisible(false);
			chartSettings.getSecondaryAxisSettingsListY().add(secondaryAxisSettingsY);
			//
			applySettings(chartSettings);
		} catch(Exception e) {
			System.out.println(e);
		}
	}
}

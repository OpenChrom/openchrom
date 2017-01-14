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
package net.openchrom.msd.process.supplier.cms.ui.parts.swt;

import org.eclipse.chemclipse.msd.swt.ui.components.massspectrum.AbstractExtendedMassSpectrumUI;
import org.eclipse.chemclipse.msd.swt.ui.components.massspectrum.MassValueDisplayPrecision;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.chemclipse.swt.ui.series.ISeries;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.chemclipse.swt.ui.support.Sign;
import org.eclipse.swt.widgets.Composite;
import org.swtchart.IAxisSet;
import org.swtchart.IBarSeries;
import org.swtchart.IBarSeries.BarWidthStyle;
import org.swtchart.ISeries.SeriesType;

import net.openchrom.msd.process.supplier.cms.ui.internal.provider.SeriesConverterIonMeasurement;

public class IonMeasurementSpectrumUI extends AbstractExtendedMassSpectrumUI {

	public IonMeasurementSpectrumUI(Composite parent, int style) {
		super(parent, style, MassValueDisplayPrecision.NOMINAL);
	}

	@Override
	public void setViewSeries() {

		if(this.massSpectrum != null) {
			ISeries series = SeriesConverterIonMeasurement.convertNominalIonMeasurement(this.massSpectrum, Sign.POSITIVE);
			multipleLineSeries.add(series);
			barSeriesPositive = (IBarSeries)getSeriesSet().createSeries(SeriesType.BAR, series.getId());
			barSeriesPositive.setXSeries(series.getXSeries());
			barSeriesPositive.setYSeries(series.getYSeries());
			barSeriesPositive.setBarWidthStyle(BarWidthStyle.FIXED);
			barSeriesPositive.setBarWidth(1);
			barSeriesPositive.setBarColor(Colors.RED);
		}
	}

	/**
	 * Creates the primary axes abundance and milliseconds.
	 */
	private void createPrimaryAxes() {

		/*
		 * Main Axes.
		 */
		IAxisSet axisSet = getAxisSet();
		xAxisBottom = axisSet.getXAxis(0);
		yAxisLeft = axisSet.getYAxis(0);
		yAxisLeft.getTitle().setText("abundance");
		//yAxisLeft.getTick().setFormat(ValueFormat.getDecimalFormatEnglish("0.0###"));
		yAxisLeft.getTick().setFormat(ValueFormat.getDecimalFormatEnglish("0.0###E00"));
		//
		xAxisBottom.getTitle().setText("m/z");
		xAxisBottom.getTick().setFormat(ValueFormat.getDecimalFormatEnglish("0.0###"));
	}
}

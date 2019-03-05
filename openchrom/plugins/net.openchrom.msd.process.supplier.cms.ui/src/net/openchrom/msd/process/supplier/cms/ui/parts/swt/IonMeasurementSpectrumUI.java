/*******************************************************************************
 * Copyright (c) 2017, 2019 Lablicate GmbH.
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
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swtchart.IBarSeries;
import org.eclipse.swtchart.IBarSeries.BarWidthStyle;
import org.eclipse.swtchart.ISeries.SeriesType;

import net.openchrom.msd.converter.supplier.cms.model.CalibratedVendorLibraryMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.CalibratedVendorMassSpectrum;
import net.openchrom.msd.process.supplier.cms.ui.internal.provider.SeriesConverterIonMeasurement;

public class IonMeasurementSpectrumUI extends AbstractExtendedMassSpectrumUI {

	public IonMeasurementSpectrumUI(Composite parent, int style) {
		super(parent, style, MassValueDisplayPrecision.NOMINAL);
	}

	@Override
	public void setViewSeries() {

		if(this.massSpectrum != null) {
			String yAxisTitle;
			ISeries series = SeriesConverterIonMeasurement.convertNominalIonMeasurement(this.massSpectrum, Sign.POSITIVE);
			multipleLineSeries.add(series);
			barSeriesPositive = (IBarSeries)getSeriesSet().createSeries(SeriesType.BAR, series.getId());
			barSeriesPositive.setXSeries(series.getXSeries());
			barSeriesPositive.setYSeries(series.getYSeries());
			barSeriesPositive.setBarWidthStyle(BarWidthStyle.FIXED);
			barSeriesPositive.setBarWidth(1);
			barSeriesPositive.setBarColor(Colors.RED);
			if(this.massSpectrum instanceof CalibratedVendorMassSpectrum) {
				yAxisTitle = "signal";
			} else {
				yAxisTitle = "abundance";
			}
			if(this.massSpectrum instanceof CalibratedVendorLibraryMassSpectrum) {
				String signalUnits = ((CalibratedVendorLibraryMassSpectrum)this.massSpectrum).getSignalUnits();
				if((null != signalUnits) && (0 < signalUnits.length())) {
					yAxisTitle = yAxisTitle + ", " + signalUnits;
				}
			}
			this.getAxisSet().getYAxis(0).getTick().setFormat(ValueFormat.getDecimalFormatEnglish("0.0###E00"));
			this.setAxisTitle(SWT.LEFT, yAxisTitle);
		}
	}
}

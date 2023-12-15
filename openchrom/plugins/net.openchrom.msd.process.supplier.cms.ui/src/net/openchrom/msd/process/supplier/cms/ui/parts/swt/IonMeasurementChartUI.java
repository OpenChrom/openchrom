/*******************************************************************************
 * Copyright (c) 2017, 2023 Lablicate GmbH.
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swtchart.ICustomPaintListener;
import org.eclipse.swtchart.IPlotArea;
import org.eclipse.swtchart.ISeries;
import org.eclipse.swtchart.extensions.barcharts.BarChart;
import org.eclipse.swtchart.extensions.barcharts.BarSeriesData;
import org.eclipse.swtchart.extensions.barcharts.IBarSeriesData;
import org.eclipse.swtchart.extensions.core.IChartSettings;
import org.eclipse.swtchart.extensions.core.IPrimaryAxisSettings;
import org.eclipse.swtchart.extensions.core.RangeRestriction;
import org.eclipse.swtchart.extensions.core.SeriesData;

import net.openchrom.msd.converter.supplier.cms.model.CalibratedVendorLibraryMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.CalibratedVendorMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorLibraryMassSpectrum;
import net.openchrom.msd.process.supplier.cms.ui.internal.provider.BarSeriesIon;
import net.openchrom.msd.process.supplier.cms.ui.internal.provider.BarSeriesIonComparator;

public class IonMeasurementChartUI extends BarChart {

	private BarSeriesIonComparator barSeriesIonComparator = new BarSeriesIonComparator();

	public IonMeasurementChartUI() {

		initialize();
	}

	public IonMeasurementChartUI(Composite parent, int style) {

		super(parent, style);
		initialize();
	}

	public void update(ICalibratedVendorLibraryMassSpectrum calibratedVendorLibraryMassSpectrum) {

		deleteSeries();
		if(calibratedVendorLibraryMassSpectrum != null) {
			/*
			 * Modify the Y axis.
			 */
			IChartSettings chartSettings = getChartSettings();
			String yTitle = (calibratedVendorLibraryMassSpectrum instanceof CalibratedVendorMassSpectrum) ? "signal" : "abundance";
			if(calibratedVendorLibraryMassSpectrum instanceof CalibratedVendorLibraryMassSpectrum massSpectrum) {
				String signalUnits = massSpectrum.getSignalUnits();
				if((null != signalUnits) && (0 < signalUnits.length())) {
					yTitle += ", " + signalUnits;
				}
			}
			chartSettings.getPrimaryAxisSettingsY().setTitle(yTitle);
			applySettings(chartSettings);
			/*
			 * Series
			 */
			List<IBarSeriesData> barSeriesDataList = new ArrayList<>();
			IBarSeriesData barSeriesData = convert(calibratedVendorLibraryMassSpectrum);
			barSeriesData.getSettings().setBarColor(Colors.RED);
			barSeriesDataList.add(barSeriesData);
			addSeriesData(barSeriesDataList);
		}
	}

	private void initialize() {

		IChartSettings chartSettings = getChartSettings();
		chartSettings.setOrientation(SWT.HORIZONTAL);
		chartSettings.setHorizontalSliderVisible(true);
		chartSettings.setVerticalSliderVisible(true);
		RangeRestriction rangeRestriction = chartSettings.getRangeRestriction();
		rangeRestriction.setZeroX(false);
		rangeRestriction.setZeroY(false);
		rangeRestriction.setRestrictFrame(true);
		rangeRestriction.setExtendTypeX(RangeRestriction.ExtendType.ABSOLUTE);
		rangeRestriction.setExtendMinX(2.0d);
		rangeRestriction.setExtendMaxX(2.0d);
		rangeRestriction.setExtendTypeY(RangeRestriction.ExtendType.RELATIVE);
		rangeRestriction.setExtendMaxY(0.1d);
		//
		setPrimaryAxisSet(chartSettings);
		applySettings(chartSettings);
		//
		addSeriesLabelMarker();
	}

	private void setPrimaryAxisSet(IChartSettings chartSettings) {

		IPrimaryAxisSettings primaryAxisSettingsX = chartSettings.getPrimaryAxisSettingsX();
		primaryAxisSettingsX.setTitle("m/z");
		primaryAxisSettingsX.setDecimalFormat(ValueFormat.getDecimalFormatEnglish("0"));
		primaryAxisSettingsX.setColor(getDisplay().getSystemColor(SWT.COLOR_BLACK));
		//
		IPrimaryAxisSettings primaryAxisSettingsY = chartSettings.getPrimaryAxisSettingsY();
		primaryAxisSettingsY.setTitle("Intensity");
		primaryAxisSettingsY.setDecimalFormat(ValueFormat.getDecimalFormatEnglish("0.0###E00"));
		primaryAxisSettingsY.setColor(getDisplay().getSystemColor(SWT.COLOR_BLACK));
	}

	private BarSeriesData convert(ICalibratedVendorLibraryMassSpectrum calibratedVendorLibraryMassSpectrum) {

		SeriesData seriesData = null;
		if(calibratedVendorLibraryMassSpectrum.getTotalSignal() > 0.0f) {
			List<IIon> ions = calibratedVendorLibraryMassSpectrum.getIons();
			double[] xSeries = new double[ions.size()];
			double[] ySeries = new double[ions.size()];
			/*
			 * Get the abundance for each ion and check if the values should be
			 * negative
			 */
			int x = 0;
			int y = 0;
			for(IIon ion : ions) {
				xSeries[x++] = ion.getIon();
				ySeries[y++] = ion.getAbundance();
			}
			//
			seriesData = new SeriesData(xSeries, ySeries, "Ion Measurement");
		} else {
			seriesData = new SeriesData(new double[]{0, 0}, new double[]{0, 0}, "No Ion Measurement");
		}
		return new BarSeriesData(seriesData);
	}

	private void addSeriesLabelMarker() {

		/*
		 * Plot the series name above the entry.
		 */
		IPlotArea plotArea = getBaseChart().getPlotArea();
		plotArea.addCustomPaintListener(new ICustomPaintListener() {

			@Override
			public void paintControl(PaintEvent e) {

				List<BarSeriesIon> barSeriesIons = getBarSeriesIonList();
				Collections.sort(barSeriesIons, barSeriesIonComparator);
				int barSeriesSize = barSeriesIons.size();
				int limit = 5;
				//
				for(int i = 0; i < limit; i++) {
					if(i < barSeriesSize) {
						BarSeriesIon barSeriesIon = barSeriesIons.get(i);
						printLabel(barSeriesIon, e);
					}
				}
			}

			@Override
			public boolean drawBehindSeries() {

				return false;
			}
		});
	}

	private void printLabel(BarSeriesIon barSeriesIon, PaintEvent e) {

		Point point = barSeriesIon.getPoint();
		String label = Integer.toString((int)barSeriesIon.getMz());
		boolean negative = (barSeriesIon.getIntensity() < 0) ? true : false;
		Point labelSize = e.gc.textExtent(label);
		int x = (int)(point.x + 0.5d - labelSize.x / 2.0d);
		int y = point.y;
		if(!negative) {
			y = point.y - labelSize.y;
		}
		e.gc.drawText(label, x, y, true);
	}

	private List<BarSeriesIon> getBarSeriesIonList() {

		List<BarSeriesIon> barSeriesIons = new ArrayList<BarSeriesIon>();
		//
		int widthPlotArea = getBaseChart().getPlotArea().getSize().x;
		ISeries<?>[] series = getBaseChart().getSeriesSet().getSeries();
		for(ISeries<?> barSeries : series) {
			if(barSeries != null) {
				//
				double[] xSeries = barSeries.getXSeries();
				double[] ySeries = barSeries.getYSeries();
				int size = barSeries.getXSeries().length;
				//
				for(int i = 0; i < size; i++) {
					Point point = barSeries.getPixelCoordinates(i);
					if(point.x >= 0 && point.x <= widthPlotArea) {
						barSeriesIons.add(new BarSeriesIon(xSeries[i], ySeries[i], point));
					}
				}
			}
		}
		return barSeriesIons;
	}
}

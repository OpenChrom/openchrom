/*******************************************************************************
 * Copyright (c) 2017, 2025 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.xxd.report.supplier.pdf.ui.swt;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.comparator.PeakRetentionTimeComparator;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.support.comparator.SortOrder;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.chemclipse.ux.extension.xxd.ui.charts.ChromatogramChart;
import org.eclipse.chemclipse.ux.extension.xxd.ui.support.charts.ChromatogramChartSupport;
import org.eclipse.chemclipse.ux.extension.xxd.ui.support.charts.ChromatogramDataSupport;
import org.eclipse.chemclipse.ux.extension.xxd.ui.support.charts.PeakChartSupport;
import org.eclipse.chemclipse.ux.extension.xxd.ui.support.charts.ScanChartSupport;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtchart.ILineSeries.PlotSymbolType;
import org.eclipse.swtchart.IPlotArea;
import org.eclipse.swtchart.LineStyle;
import org.eclipse.swtchart.extensions.core.BaseChart;
import org.eclipse.swtchart.extensions.core.IChartSettings;
import org.eclipse.swtchart.extensions.core.IExtendedChart;
import org.eclipse.swtchart.extensions.core.RangeRestriction;
import org.eclipse.swtchart.extensions.linecharts.ILineSeriesData;
import org.eclipse.swtchart.extensions.linecharts.ILineSeriesSettings;

import net.openchrom.swtchart.extension.export.vectorgraphics.core.PDFExportHandler;
import net.openchrom.swtchart.extension.export.vectorgraphics.model.PageSizeOption;

public class ChartScreenshotRunnable implements Runnable {

	private static final Logger logger = Logger.getLogger(ChartScreenshotRunnable.class);

	private ChromatogramChartSupport chromatogramChartSupport = new ChromatogramChartSupport();
	private PeakChartSupport peakChartSupport = new PeakChartSupport();
	private ScanChartSupport scanChartSupport = new ScanChartSupport();
	private PeakRetentionTimeComparator peakRetentionTimeComparator = new PeakRetentionTimeComparator(SortOrder.ASC);

	private IChromatogram chromatogram;
	private int numberOfPages;
	private int width;
	private int height;

	private List<? extends IPeak> peaks = new ArrayList<>();
	private List<IScan> scans = new ArrayList<>();

	private List<File> chromatogramFiles = new ArrayList<>();

	public ChartScreenshotRunnable(IChromatogram chromatogram, int width, int height, int numberOfPages) {

		this.chromatogram = chromatogram;
		this.width = width;
		this.height = height;
		this.numberOfPages = numberOfPages;
	}

	public List<? extends IPeak> getPeaks() {

		return peaks;
	}

	public List<IScan> getScans() {

		return scans;
	}

	@Override
	public void run() {

		ChromatogramChart chromatogramChart = new ChromatogramChart();

		Shell imageShell = chromatogramChart.getShell();
		Rectangle imageBounds = imageShell.computeTrim(0, 0, width, height);
		imageShell.setSize(imageBounds.width, imageBounds.height);
		imageShell.setLocation(0, 0);
		imageShell.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		imageShell.setLayout(new FillLayout());

		try {
			IChartSettings chartSettings = chromatogramChart.getChartSettings();
			chartSettings.setBackground(Colors.WHITE);
			chartSettings.setBackgroundChart(Colors.WHITE);
			chartSettings.setBackgroundPlotArea(Colors.WHITE);

			RangeRestriction rangeRestriction = chartSettings.getRangeRestriction();
			rangeRestriction.setExtendMaxY(0.1d);
			rangeRestriction.setForceZeroMinY(false);
			rangeRestriction.setZeroY(false);
			chromatogramChart.applySettings(chartSettings);

			List<ILineSeriesData> lineSeriesDataList = new ArrayList<>();
			lineSeriesDataList.add(chromatogramChartSupport.getLineSeriesDataChromatogram(chromatogram, chromatogram.getName(), Colors.RED));
			BaseChart baseChart = chromatogramChart.getBaseChart();
			peaks = addPeaks(baseChart, lineSeriesDataList);
			scans = addScans(baseChart, lineSeriesDataList);
			chromatogramChart.addSeriesData(lineSeriesDataList);

			while(!imageShell.isDisposed()) {
				if(!imageShell.getDisplay().readAndDispatch()) {
					imageShell.getDisplay().sleep();
					takeScreenshots(imageShell, chromatogramChart);
					imageShell.close();
				}
			}
		} catch(IOException e) {
			logger.warn(e);
		}
	}

	private void takeScreenshots(Shell imageShell, ChromatogramChart chromatogramChart) throws IOException {

		int startRetentionTime = chromatogram.getStartRetentionTime();
		int stopRetentionTime = chromatogram.getStopRetentionTime();
		int delta = (stopRetentionTime - startRetentionTime) / numberOfPages;
		int extra = delta / 16;

		for(int i = 0; i < numberOfPages; i++) {
			int offset = i * delta;
			int start = startRetentionTime + offset - extra;
			int stop = start + delta + extra;
			chromatogramChart.setRange(IExtendedChart.X_AXIS, start, stop);
			chromatogramChart.update();
			takeScreenshot(imageShell, chromatogramChart);
		}
	}

	private void takeScreenshot(Shell imageShell, ChromatogramChart chromatogramChart) throws IOException {

		File file = Files.createTempFile("", ".pdf").toFile();
		PDFExportHandler exportHandler = new PDFExportHandler();
		if(exportHandler.execute(file, imageShell, PageSizeOption.A4_LANDSCAPE, 1, 1, chromatogramChart)) {
			chromatogramFiles.add(file);
		}
	}

	public List<File> getChromatogramFiles() {

		return chromatogramFiles;
	}

	private List<? extends IPeak> addPeaks(BaseChart baseChart, List<ILineSeriesData> lineSeriesDataList) {

		List<? extends IPeak> peaks = chromatogram.getPeaks();
		if(!peaks.isEmpty()) {
			Collections.sort(peaks, peakRetentionTimeComparator);
			ILineSeriesData lineSeriesData = peakChartSupport.getPeaks(peaks, true, false, Colors.GRAY, "Peaks");
			ILineSeriesSettings lineSeriesSettings = lineSeriesData.getSettings();
			lineSeriesSettings.setEnableArea(false);
			lineSeriesSettings.setLineStyle(LineStyle.NONE);
			lineSeriesSettings.setSymbolType(PlotSymbolType.INVERTED_TRIANGLE);
			lineSeriesSettings.setSymbolSize(5);
			lineSeriesSettings.setSymbolColor(Colors.DARK_GRAY);
			lineSeriesDataList.add(lineSeriesData);

			IPlotArea plotArea = baseChart.getPlotArea();
			int indexSeries = lineSeriesDataList.size() - 1;
			PeakLabelMarker peakLabelMarker = new PeakLabelMarker(baseChart, indexSeries, peaks);
			plotArea.addCustomPaintListener(peakLabelMarker);
		}

		return peaks;
	}

	private List<IScan> addScans(BaseChart baseChart, List<ILineSeriesData> lineSeriesDataList) {

		List<IScan> scans = ChromatogramDataSupport.getIdentifiedScans(chromatogram);
		if(!scans.isEmpty()) {
			ILineSeriesData lineSeriesData = scanChartSupport.getLineSeriesDataPoint(scans, false, "Scans");
			ILineSeriesSettings lineSeriesSettings = lineSeriesData.getSettings();
			lineSeriesSettings.setLineStyle(LineStyle.NONE);
			lineSeriesSettings.setSymbolType(PlotSymbolType.CIRCLE);
			lineSeriesSettings.setSymbolSize(5);
			lineSeriesSettings.setSymbolColor(Colors.DARK_GRAY);
			lineSeriesDataList.add(lineSeriesData);

			IPlotArea plotArea = baseChart.getPlotArea();
			int indexSeries = lineSeriesDataList.size() - 1;
			ScanLabelMarker scanLabelMarker = new ScanLabelMarker(baseChart, indexSeries, scans);
			plotArea.addCustomPaintListener(scanLabelMarker);
		}

		return scans;
	}
}

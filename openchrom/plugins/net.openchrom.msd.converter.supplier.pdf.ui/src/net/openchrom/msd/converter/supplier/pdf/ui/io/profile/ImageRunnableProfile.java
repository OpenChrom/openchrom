/*******************************************************************************
 * Copyright (c) 2018, 2021 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.pdf.ui.io.profile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.comparator.PeakRetentionTimeComparator;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.support.comparator.SortOrder;
import org.eclipse.chemclipse.support.settings.ApplicationSettings;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.chemclipse.ux.extension.xxd.ui.charts.ChromatogramChart;
import org.eclipse.chemclipse.ux.extension.xxd.ui.support.charts.ChromatogramChartSupport;
import org.eclipse.chemclipse.ux.extension.xxd.ui.support.charts.PeakChartSupport;
import org.eclipse.swt.SWT;
import org.eclipse.swtchart.ILineSeries.PlotSymbolType;
import org.eclipse.swtchart.IPlotArea;
import org.eclipse.swtchart.LineStyle;
import org.eclipse.swtchart.export.images.ImageFactory;
import org.eclipse.swtchart.extensions.core.BaseChart;
import org.eclipse.swtchart.extensions.core.IChartSettings;
import org.eclipse.swtchart.extensions.core.IExtendedChart;
import org.eclipse.swtchart.extensions.core.RangeRestriction;
import org.eclipse.swtchart.extensions.linecharts.ILineSeriesData;
import org.eclipse.swtchart.extensions.linecharts.ILineSeriesSettings;

import net.openchrom.msd.converter.supplier.pdf.ui.Activator;

public class ImageRunnableProfile implements Runnable {

	private static final Logger logger = Logger.getLogger(ImageRunnableProfile.class);
	private static final String PLUGIN_NAME = Activator.getDefault().getBundle().getSymbolicName();
	//
	private ChromatogramChartSupport chromatogramChartSupport = new ChromatogramChartSupport();
	private PeakChartSupport peakChartSupport = new PeakChartSupport();
	private PeakRetentionTimeComparator peakRetentionTimeComparator = new PeakRetentionTimeComparator(SortOrder.ASC);
	//
	private IChromatogram<? extends IPeak> chromatogram;
	private String chromatogramName = "";
	//
	private PDDocument document;
	private int numberOfPages;
	private int numberOfPeaks;
	private int width;
	private int height;
	//
	private List<PDImageXObject> chromatogramImages = new ArrayList<>();
	private List<? extends IPeak> peaks = new ArrayList<>();
	private List<IScan> scans = new ArrayList<>();

	public ImageRunnableProfile(IChromatogram<? extends IPeak> chromatogram, String chromatogramName, PDDocument document, int numberOfPages, int numberOfPeaks, int width, int height) {

		this.chromatogram = chromatogram;
		this.chromatogramName = chromatogramName;
		this.document = document;
		this.numberOfPages = numberOfPages;
		this.numberOfPeaks = numberOfPeaks;
		this.width = width;
		this.height = height;
	}

	public List<PDImageXObject> getChromatogramImages() {

		return chromatogramImages;
	}

	public List<? extends IPeak> getPeaks() {

		return peaks;
	}

	public List<IScan> getScans() {

		return scans;
	}

	@Override
	public void run() {

		if(chromatogram != null && document != null) {
			try {
				/*
				 * Create the chart
				 */
				ImageFactory<ChromatogramChart> imageFactory = new ImageFactory<ChromatogramChart>(ChromatogramChart.class, width, height);
				ChromatogramChart chromatogramChart = imageFactory.getChart();
				IChartSettings chartSettings = chromatogramChart.getChartSettings();
				chartSettings.setTitle(chromatogramName);
				chartSettings.setTitleVisible(true);
				chartSettings.setTitleColor(Colors.BLACK);
				RangeRestriction rangeRestriction = chartSettings.getRangeRestriction();
				rangeRestriction.setExtendMaxY(0.7d);
				chromatogramChart.applySettings(chartSettings);
				List<ILineSeriesData> lineSeriesDataList = new ArrayList<ILineSeriesData>();
				//
				addChromatogram(lineSeriesDataList);
				BaseChart baseChart = chromatogramChart.getBaseChart();
				peaks = addPeaks(baseChart, lineSeriesDataList);
				chromatogramChart.addSeriesData(lineSeriesDataList);
				chromatogramImages.addAll(createImages(chromatogramChart, imageFactory, chromatogram, numberOfPages, document));
			} catch(InstantiationException e) {
				logger.warn(e);
			} catch(IllegalAccessException e) {
				logger.warn(e);
			} catch(IOException e) {
				logger.warn(e);
			}
		}
	}

	private void addChromatogram(List<ILineSeriesData> lineSeriesDataList) {

		ILineSeriesData lineSeriesData = chromatogramChartSupport.getLineSeriesDataChromatogram(chromatogram, chromatogram.getName(), Colors.BLACK);
		lineSeriesData.getSettings().setEnableArea(true);
		lineSeriesDataList.add(lineSeriesData);
	}

	private List<? extends IPeak> addPeaks(BaseChart baseChart, List<ILineSeriesData> lineSeriesDataList) {

		List<? extends IPeak> peaks = chromatogram.getPeaks();
		if(peaks.size() > 0) {
			Collections.sort(peaks, peakRetentionTimeComparator);
			ILineSeriesData lineSeriesData = peakChartSupport.getPeaks(peaks, true, false, Colors.GRAY, "Peaks");
			ILineSeriesSettings lineSeriesSettings = lineSeriesData.getSettings();
			lineSeriesSettings.setEnableArea(false);
			lineSeriesSettings.setLineStyle(LineStyle.NONE);
			lineSeriesSettings.setSymbolType(PlotSymbolType.INVERTED_TRIANGLE);
			lineSeriesSettings.setSymbolSize(5);
			lineSeriesSettings.setSymbolColor(Colors.DARK_GRAY);
			lineSeriesDataList.add(lineSeriesData);
			//
			IPlotArea plotArea = baseChart.getPlotArea();
			int indexSeries = lineSeriesDataList.size() - 1;
			PeakLabelMarker peakNameMarker = new PeakLabelMarker(baseChart, indexSeries, peaks, numberOfPeaks);
			plotArea.addCustomPaintListener(peakNameMarker);
		}
		//
		return peaks;
	}

	private List<PDImageXObject> createImages(ChromatogramChart chromatogramChart, ImageFactory<ChromatogramChart> imageFactory, IChromatogram<? extends IPeak> chromatogram, int numberOfPages, PDDocument document) throws IOException {

		List<PDImageXObject> chromatogramImages = new ArrayList<>();
		//
		int startRetentionTime = chromatogram.getStartRetentionTime();
		int stopRetentionTime = chromatogram.getStopRetentionTime();
		int delta = (stopRetentionTime - startRetentionTime) / numberOfPages;
		int extra = delta / 16;
		//
		for(int i = 0; i < numberOfPages; i++) {
			//
			int offset = i * delta;
			int start = startRetentionTime + offset - extra;
			int stop = start + delta + extra;
			chromatogramChart.setRange(IExtendedChart.X_AXIS, start, stop);
			chromatogramChart.update();
			//
			File file = getTemporaryFile(new Date().getTime() + ".png");
			imageFactory.saveImage(file.getAbsolutePath(), SWT.IMAGE_PNG);
			PDImageXObject chromatogramImage = PDImageXObject.createFromFile(file.getAbsolutePath(), document);
			chromatogramImages.add(chromatogramImage);
			file.delete();
		}
		//
		imageFactory.closeShell();
		return chromatogramImages;
	}

	private File getTemporaryFile(String fileName) {

		File dir = new File(ApplicationSettings.getSettingsDirectory().getAbsolutePath() + File.separator + PLUGIN_NAME);
		/*
		 * Create the directory if it not exists.
		 */
		if(!dir.exists()) {
			if(!dir.mkdirs()) {
				logger.warn("The temporarily file directory could not be created: " + dir.getAbsolutePath());
			}
		}
		/*
		 * Create the file.
		 */
		return new File(dir.getAbsolutePath() + File.separator + fileName);
	}
}

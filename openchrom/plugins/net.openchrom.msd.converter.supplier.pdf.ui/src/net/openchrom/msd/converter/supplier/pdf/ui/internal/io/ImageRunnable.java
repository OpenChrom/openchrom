/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.pdf.ui.internal.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.support.settings.ApplicationSettings;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.chemclipse.ux.extension.xxd.ui.charts.ChromatogramChart;
import org.eclipse.chemclipse.ux.extension.xxd.ui.support.charts.ChromatogramChartSupport;
import org.eclipse.eavp.service.swtchart.core.IExtendedChart;
import org.eclipse.eavp.service.swtchart.images.ImageFactory;
import org.eclipse.eavp.service.swtchart.linecharts.ILineSeriesData;
import org.eclipse.swt.SWT;

public class ImageRunnable implements Runnable {

	private static final Logger logger = Logger.getLogger(ImageRunnable.class);
	private static final String PLUGIN_NAME = "net.openchrom.msd.converter.supplier.pdf.ui";
	//
	private ChromatogramChartSupport chromatogramChartSupport = new ChromatogramChartSupport();
	//
	@SuppressWarnings("rawtypes")
	private IChromatogram chromatogram;
	private PDDocument document;
	private int numberOfPages;
	private int width;
	private int height;
	//
	private List<PDImageXObject> chromatogramImages = new ArrayList<>();

	@SuppressWarnings("rawtypes")
	public ImageRunnable(IChromatogram chromatogram, PDDocument document, int numberOfPages, int width, int height) {
		this.chromatogram = chromatogram;
		this.document = document;
		this.numberOfPages = numberOfPages;
		this.width = width;
		this.height = height;
	}

	public List<PDImageXObject> getChromatogramImages() {

		return chromatogramImages;
	}

	@Override
	public void run() {

		if(chromatogram != null && document != null) {
			try {
				ImageFactory<ChromatogramChart> imageFactory = new ImageFactory<ChromatogramChart>(ChromatogramChart.class, width, height);
				ChromatogramChart chromatogramChart = imageFactory.getChart();
				//
				List<ILineSeriesData> lineSeriesDataList = new ArrayList<ILineSeriesData>();
				ILineSeriesData lineSeriesData = chromatogramChartSupport.getLineSeriesDataChromatogram(chromatogram, chromatogram.getName(), Colors.RED);
				lineSeriesDataList.add(lineSeriesData);
				chromatogramChart.addSeriesData(lineSeriesDataList);
				//
				int startRetentionTime = chromatogram.getStartRetentionTime();
				int stopRetentionTime = chromatogram.getStopRetentionTime();
				int delta = (stopRetentionTime - startRetentionTime) / numberOfPages;
				//
				for(int i = 0; i < numberOfPages; i++) {
					//
					int offset = i * delta;
					int start = startRetentionTime + offset;
					int stop = start + delta;
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
				//
			} catch(InstantiationException e) {
				logger.warn(e);
			} catch(IllegalAccessException e) {
				logger.warn(e);
			} catch(IOException e) {
				logger.warn(e);
			}
		}
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

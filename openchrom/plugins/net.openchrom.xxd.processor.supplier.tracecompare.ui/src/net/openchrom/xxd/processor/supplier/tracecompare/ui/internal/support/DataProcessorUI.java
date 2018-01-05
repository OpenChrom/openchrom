/*******************************************************************************
 * Copyright (c) 2017, 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.processor.supplier.tracecompare.ui.internal.support;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.chemclipse.wsd.model.xwc.ExtractedWavelengthSignalExtractor;
import org.eclipse.chemclipse.wsd.model.xwc.IExtractedWavelengthSignal;
import org.eclipse.chemclipse.wsd.model.xwc.IExtractedWavelengthSignals;
import org.eclipse.eavp.service.swtchart.core.ISeriesData;
import org.eclipse.eavp.service.swtchart.core.SeriesData;
import org.eclipse.eavp.service.swtchart.linecharts.ILineSeriesData;
import org.eclipse.eavp.service.swtchart.linecharts.ILineSeriesSettings;
import org.eclipse.eavp.service.swtchart.linecharts.LineSeriesData;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.swtchart.LineStyle;

import net.openchrom.xxd.processor.supplier.tracecompare.core.DataProcessor;
import net.openchrom.xxd.processor.supplier.tracecompare.model.IProcessorModel;
import net.openchrom.xxd.processor.supplier.tracecompare.preferences.PreferenceSupplier;
import net.openchrom.xxd.processor.supplier.tracecompare.ui.internal.runnables.MeasurementImportRunnable;

public class DataProcessorUI extends DataProcessor {

	private static final Logger logger = Logger.getLogger(DataProcessorUI.class);
	//
	public static final String MEASUREMENT_SAMPLE = "Sample";
	public static final String MEASUREMENT_REFERENCE = "Reference";
	//
	public static final String ANALYSIS_TYPE_QUALIFICATION = "Qualification";
	public static final String ANALYSIS_TYPE_VALIDATION = "Validation";
	//
	public static final String SHOW_ALL_WAVELENGTHS = "Show All";
	//
	private Map<String, Color> colorMap;
	private Color colorDefault;

	public DataProcessorUI() {
		/*
		 * Colors
		 */
		colorMap = new HashMap<String, Color>();
		reloadColors();
	}

	public void reloadColors() {

		colorMap.clear();
		colorMap.put("190", Colors.getColor(PreferenceSupplier.getColorData190()));
		colorMap.put("200", Colors.getColor(PreferenceSupplier.getColorData200()));
		colorMap.put("220", Colors.getColor(PreferenceSupplier.getColorData220()));
		colorMap.put("240", Colors.getColor(PreferenceSupplier.getColorData240()));
		colorMap.put("260", Colors.getColor(PreferenceSupplier.getColorData260()));
		colorMap.put("280", Colors.getColor(PreferenceSupplier.getColorData280()));
		colorMap.put("300", Colors.getColor(PreferenceSupplier.getColorData300()));
		colorDefault = Colors.getColor(PreferenceSupplier.getColorDataDefault());
	}

	public String[] getWavelengthItems() {

		List<String> wavelenghts = new ArrayList<String>();
		wavelenghts.add(DataProcessorUI.SHOW_ALL_WAVELENGTHS);
		wavelenghts.add("190");
		wavelenghts.add("200");
		wavelenghts.add("220");
		wavelenghts.add("240");
		wavelenghts.add("260");
		wavelenghts.add("280");
		wavelenghts.add("300");
		//
		return wavelenghts.toArray(new String[wavelenghts.size()]);
	}

	public Map<Integer, Map<String, ISeriesData>> extractMeasurementsData(List<File> measurementFiles, String type) {

		Map<Integer, Map<String, ISeriesData>> measurementsData = new HashMap<Integer, Map<String, ISeriesData>>();
		//
		MeasurementImportRunnable runnable = new MeasurementImportRunnable(measurementFiles);
		ProgressMonitorDialog monitor = new ProgressMonitorDialog(Display.getDefault().getActiveShell());
		try {
			monitor.run(true, true, runnable);
		} catch(InterruptedException e1) {
			logger.warn(e1);
		} catch(InvocationTargetException e1) {
			logger.warn(e1);
		}
		//
		ISeriesData seriesData;
		String wavelength;
		//
		List<IChromatogramWSD> measurements = runnable.getMeasurements();
		for(IChromatogram measurement : measurements) {
			/*
			 * Track 1
			 */
			int index = 1;
			seriesData = extractMeasurement(measurement, type);
			wavelength = Integer.toString(getWavelength(measurement));
			addMeasurementData(measurementsData, wavelength, seriesData, index++);
			/*
			 * Track 2 ... n
			 */
			for(IChromatogram additionalMeasurement : measurement.getReferencedChromatograms()) {
				seriesData = extractMeasurement(additionalMeasurement, type);
				wavelength = Integer.toString(getWavelength(additionalMeasurement));
				addMeasurementData(measurementsData, wavelength, seriesData, index++);
			}
		}
		//
		return measurementsData;
	}

	public void addMeasurementData(Map<Integer, Map<String, ISeriesData>> measurementsData, String wavelength, ISeriesData seriesData, int index) {

		Map<String, ISeriesData> wavelengthData = measurementsData.get(index);
		if(wavelengthData == null) {
			wavelengthData = new HashMap<String, ISeriesData>();
			measurementsData.put(index, wavelengthData);
		}
		wavelengthData.put(wavelength, seriesData);
	}

	public ISeriesData extractMeasurement(IChromatogram measurement, String type) {

		boolean isMirrorReferenceData = PreferenceSupplier.isMirrorReferenceData();
		//
		List<IScan> scans = measurement.getScans();
		double[] xSeries = new double[scans.size()];
		double[] ySeries = new double[scans.size()];
		int wavelength = getWavelength(measurement);
		boolean isReference = type.startsWith(MEASUREMENT_REFERENCE);
		//
		int index = 0;
		ExtractedWavelengthSignalExtractor signalExtractor = new ExtractedWavelengthSignalExtractor((IChromatogramWSD)measurement);
		IExtractedWavelengthSignals extractedWavelengthSignals = signalExtractor.getExtractedWavelengthSignals();
		for(IExtractedWavelengthSignal extractedWavelengthSignal : extractedWavelengthSignals.getExtractedWavelengthSignals()) {
			/*
			 * X,Y array
			 */
			xSeries[index] = extractedWavelengthSignal.getRetentionTime();
			double signal = extractedWavelengthSignal.getAbundance(wavelength);
			if(isReference) {
				ySeries[index] = (isMirrorReferenceData) ? -signal : signal;
			} else {
				ySeries[index] = signal;
			}
			index++;
		}
		/*
		 * Sample 210 nm
		 * ...
		 */
		return new SeriesData(xSeries, ySeries, type + " " + Integer.toString(wavelength) + " nm");
	}

	public ILineSeriesData getEmptyLineSeriesData() {

		double[] xSeries = new double[]{0, 1000};
		double[] ySeries = new double[]{0, 1000};
		ISeriesData seriesData = new SeriesData(xSeries, ySeries, "0");
		ILineSeriesData lineSeriesData = new LineSeriesData(seriesData);
		ILineSeriesSettings lineSerieSettings = lineSeriesData.getLineSeriesSettings();
		lineSerieSettings.setDescription("0 nm");
		lineSerieSettings.setEnableArea(false);
		lineSerieSettings.setLineColor(colorDefault);
		return lineSeriesData;
	}

	public ILineSeriesData getWavelengthData(Map<String, ISeriesData> wavelengthData, String wavelength) {

		ISeriesData seriesData = wavelengthData.get(wavelength);
		boolean isReference = seriesData.getId().startsWith(MEASUREMENT_REFERENCE);
		//
		ILineSeriesData lineSeriesData = new LineSeriesData(seriesData);
		ILineSeriesSettings lineSeriesSettings = lineSeriesData.getLineSeriesSettings();
		lineSeriesSettings.setEnableArea(false);
		if(isReference) {
			/*
			 * Reference
			 */
			lineSeriesSettings.setLineStyle(LineStyle.valueOf(PreferenceSupplier.getLineStyleReference()));
			lineSeriesSettings.setLineWidth(PreferenceSupplier.getLineWidthReference());
		} else {
			/*
			 * Sample
			 */
			lineSeriesSettings.setLineStyle(LineStyle.valueOf(PreferenceSupplier.getLineStyleSample()));
			lineSeriesSettings.setLineWidth(PreferenceSupplier.getLineWidthSample());
		}
		lineSeriesSettings.setLineColor((colorMap.containsKey(wavelength)) ? colorMap.get(wavelength) : colorDefault);
		/*
		 * Highlight
		 */
		ILineSeriesSettings lineSeriesSettingsHighlight = (ILineSeriesSettings)lineSeriesSettings.getSeriesSettingsHighlight();
		lineSeriesSettingsHighlight.setLineWidth(PreferenceSupplier.getLineWidthHighlight());
		//
		return lineSeriesData;
	}

	public List<ILineSeriesData> getLineSeriesDataList(Map<Integer, Map<String, ISeriesData>> measurementsData, String wavelengthSelection, int track) {

		List<ILineSeriesData> lineSeriesDataList = new ArrayList<ILineSeriesData>();
		boolean addEmptySeriesData = true;
		//
		if(measurementsData != null) {
			if(measurementsData.containsKey(track)) {
				/*
				 * Select the track.
				 */
				addEmptySeriesData = false;
				Map<String, ISeriesData> wavelengthData = measurementsData.get(track);
				addLineSeriesData(lineSeriesDataList, wavelengthData, wavelengthSelection);
			}
		}
		//
		if(addEmptySeriesData) {
			lineSeriesDataList.add(getEmptyLineSeriesData());
		}
		//
		return lineSeriesDataList;
	}

	public void addLineSeriesData(List<ILineSeriesData> lineSeriesDataList, Map<String, ISeriesData> wavelengthData, String wavelengthSelection) {

		if(wavelengthSelection.equals(SHOW_ALL_WAVELENGTHS)) {
			for(String wavelength : wavelengthData.keySet()) {
				lineSeriesDataList.add(getWavelengthData(wavelengthData, wavelength));
			}
		} else {
			lineSeriesDataList.add(getWavelengthData(wavelengthData, wavelengthSelection));
		}
	}

	public List<File> getMeasurementFileList(IProcessorModel processorModel, String meausurementType, String groupSelection) {

		List<File> referenceFiles = new ArrayList<File>();
		if(processorModel != null) {
			/*
			 * Get the file list
			 */
			String fileExtension = PreferenceSupplier.getFileExtension();
			String directory = (meausurementType.equals(MEASUREMENT_SAMPLE)) ? processorModel.getSampleDirectory() : processorModel.getReferenceDirectory();
			//
			if(processorModel != null) {
				if(!"".equals(groupSelection)) {
					referenceFiles = getMeasurementFiles(directory, fileExtension, groupSelection);
				}
			}
		}
		//
		return referenceFiles;
	}
}

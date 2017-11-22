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
import org.eclipse.chemclipse.wsd.model.core.IScanSignalWSD;
import org.eclipse.chemclipse.wsd.model.core.IScanWSD;
import org.eclipse.chemclipse.wsd.model.xwc.ExtractedWavelengthSignalExtractor;
import org.eclipse.chemclipse.wsd.model.xwc.IExtractedWavelengthSignal;
import org.eclipse.chemclipse.wsd.model.xwc.IExtractedWavelengthSignals;
import org.eclipse.eavp.service.swtchart.core.ISeriesData;
import org.eclipse.eavp.service.swtchart.core.SeriesData;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import net.openchrom.xxd.processor.supplier.tracecompare.core.DataProcessor;
import net.openchrom.xxd.processor.supplier.tracecompare.model.IProcessorModel;
import net.openchrom.xxd.processor.supplier.tracecompare.model.v1000.ReferenceModel_v1000;
import net.openchrom.xxd.processor.supplier.tracecompare.model.v1000.TrackModel_v1000;
import net.openchrom.xxd.processor.supplier.tracecompare.preferences.PreferenceSupplier;
import net.openchrom.xxd.processor.supplier.tracecompare.ui.editors.EditorProcessor;
import net.openchrom.xxd.processor.supplier.tracecompare.ui.internal.runnables.MeasurementImportRunnable;

public class TraceCompareEditorUI extends Composite {

	private static final Logger logger = Logger.getLogger(TraceCompareEditorUI.class);
	//
	private EditorProcessor editorProcessor;
	private TraceDataComparisonUI traceComparatorSample;
	private TraceDataComparisonUI traceComparatorValidation;
	//
	private IProcessorModel processorModel;
	private Map<String, Map<Integer, Map<String, ISeriesData>>> modelData = new HashMap<String, Map<Integer, Map<String, ISeriesData>>>();
	//
	private String sampleGroup = "0196"; // TODO
	private String referenceGroup = "0236"; // TODO

	public TraceCompareEditorUI(Composite parent, int style) {
		super(parent, style);
		modelData = new HashMap<String, Map<Integer, Map<String, ISeriesData>>>();
		initialize(parent);
	}

	public void update(Object object) {

		if(object instanceof EditorProcessor) {
			//
			editorProcessor = (EditorProcessor)object;
			processorModel = editorProcessor.getProcessorModel();
			initializeData();
		}
	}

	private void initialize(Composite parent) {

		setLayout(new FillLayout());
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new GridLayout(2, true));
		/*
		 * Elements
		 */
		createTraceComparatorSample(composite);
		createTraceComparatorValidation(composite);
	}

	private void createTraceComparatorSample(Composite parent) {

		traceComparatorSample = new TraceDataComparisonUI(parent, SWT.BORDER);
		traceComparatorSample.setLayoutData(new GridData(GridData.FILL_BOTH));
		traceComparatorSample.setBackground(Colors.WHITE);
	}

	private void createTraceComparatorValidation(Composite parent) {

		traceComparatorValidation = new TraceDataComparisonUI(parent, SWT.BORDER);
		traceComparatorValidation.setLayoutData(new GridData(GridData.FILL_BOTH));
		traceComparatorValidation.setBackground(Colors.WHITE);
	}

	private void initializeData() {

		String fileExtension = PreferenceSupplier.getFileExtension();
		/*
		 * Get the sample and reference.
		 */
		List<File> sampleFiles = new ArrayList<File>();
		List<File> referenceFiles = new ArrayList<File>();
		//
		if(processorModel != null) {
			/*
			 * Sample and reference files.
			 */
			String sampleDirectory = PreferenceSupplier.getSampleDirectory();
			sampleFiles = DataProcessor.getMeasurementFiles(sampleDirectory, fileExtension, sampleGroup);
			//
			String referenceDirectory = PreferenceSupplier.getReferenceDirectory();
			referenceFiles = DataProcessor.getMeasurementFiles(referenceDirectory, fileExtension, referenceGroup);
		}
		/*
		 * Get the model.
		 */
		ReferenceModel_v1000 referenceModel = processorModel.getReferenceModels().get(referenceGroup);
		if(referenceModel == null) {
			referenceModel = new ReferenceModel_v1000();
			referenceModel.setReferenceGroup(referenceGroup);
			referenceModel.setReferencePath(PreferenceSupplier.getReferenceDirectory());
			processorModel.getReferenceModels().put(referenceGroup, referenceModel);
		}
		/*
		 * Extract the data.
		 * 0196 [Group]
		 * -> 1 [Track] -> 190 [nm], ISeriesData
		 * -> 1 [Track] -> 200 [nm], ISeriesData
		 * ...
		 * -> 18 [Track] -> 190 [nm], ISeriesData
		 * -> 18 [Track] -> 200 [nm], ISeriesData
		 * 0197 [Group]
		 * -> 1 [Track] -> 190 [nm], ISeriesData
		 * -> 1 [Track] -> 200 [nm], ISeriesData
		 * ...
		 * -> 18 [Track] -> 190 [nm], ISeriesData
		 * -> 18 [Track] -> 200 [nm], ISeriesData
		 */
		Map<Integer, Map<String, ISeriesData>> sampleMeasurementsData = modelData.get(sampleGroup);
		if(sampleMeasurementsData == null) {
			sampleMeasurementsData = extractMeasurementsData(sampleFiles, TraceDataComparisonUI.SAMPLE);
			modelData.put(sampleGroup, sampleMeasurementsData);
		}
		//
		Map<Integer, Map<String, ISeriesData>> referenceMeasurementsData = modelData.get(referenceGroup);
		if(referenceMeasurementsData == null) {
			referenceMeasurementsData = extractMeasurementsData(referenceFiles, TraceDataComparisonUI.REFERENCE);
			modelData.put(referenceGroup, referenceMeasurementsData);
		}
		/*
		 * Track #
		 */
		int track = 1;
		TrackModel_v1000 trackModel = referenceModel.getTrackModels().get(track);
		if(trackModel == null) {
			trackModel = new TrackModel_v1000();
			trackModel.setSampleTrack(track);
			referenceModel.getTrackModels().put(track, trackModel);
		}
		/*
		 * Set the current velocity and the reference track.
		 * Set the reference track only if it is 0.
		 * The user may have selected another reference track.
		 */
		trackModel.setScanVelocity(PreferenceSupplier.getScanVelocity());
		if(trackModel.getReferenceTrack() == 0) {
			trackModel.setReferenceTrack(track);
		}
		//
		int previousTrack = (track > 1) ? track - 1 : track;
		int nextTrack = (track < 2) ? track + 1 : track;
		//
		traceComparatorSample.setData(editorProcessor, processorModel, trackModel, referenceGroup, sampleMeasurementsData, referenceMeasurementsData);
		traceComparatorSample.setTrackInformation(previousTrack, nextTrack);
		traceComparatorSample.loadData("Sample", sampleGroup);
		//
		traceComparatorValidation.setData(editorProcessor, processorModel, trackModel, referenceGroup, sampleMeasurementsData, referenceMeasurementsData);
		traceComparatorValidation.setTrackInformation(previousTrack, nextTrack);
		traceComparatorValidation.loadData("Validation", sampleGroup);
	}

	private Map<Integer, Map<String, ISeriesData>> extractMeasurementsData(List<File> measurementFiles, String type) {

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

	private void addMeasurementData(Map<Integer, Map<String, ISeriesData>> measurementsData, String wavelength, ISeriesData seriesData, int index) {

		Map<String, ISeriesData> wavelengthData = measurementsData.get(index);
		if(wavelengthData == null) {
			wavelengthData = new HashMap<String, ISeriesData>();
			measurementsData.put(index, wavelengthData);
		}
		wavelengthData.put(wavelength, seriesData);
	}

	private ISeriesData extractMeasurement(IChromatogram measurement, String type) {

		List<IScan> scans = measurement.getScans();
		double[] xSeries = new double[scans.size()];
		double[] ySeries = new double[scans.size()];
		int wavelength = getWavelength(measurement);
		//
		int index = 0;
		ExtractedWavelengthSignalExtractor signalExtractor = new ExtractedWavelengthSignalExtractor((IChromatogramWSD)measurement);
		IExtractedWavelengthSignals extractedWavelengthSignals = signalExtractor.getExtractedWavelengthSignals();
		for(IExtractedWavelengthSignal extractedWavelengthSignal : extractedWavelengthSignals.getExtractedWavelengthSignals()) {
			xSeries[index] = extractedWavelengthSignal.getRetentionTime();
			ySeries[index] = extractedWavelengthSignal.getAbundance(wavelength);
			index++;
		}
		/*
		 * Sample 210 nm
		 * ...
		 */
		return new SeriesData(xSeries, ySeries, type + " " + Integer.toString(wavelength) + " nm");
	}

	private int getWavelength(IChromatogram measurement) {

		for(IScan scan : measurement.getScans()) {
			if(scan instanceof IScanWSD) {
				IScanWSD scanWSD = (IScanWSD)scan;
				for(IScanSignalWSD signal : scanWSD.getScanSignals()) {
					int wavelength = (int)signal.getWavelength();
					return wavelength;
				}
			}
		}
		//
		return 0;
	}
}

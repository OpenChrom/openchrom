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
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.chemclipse.processing.ui.support.ProcessingInfoViewSupport;
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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import net.openchrom.xxd.processor.supplier.tracecompare.core.Processor;
import net.openchrom.xxd.processor.supplier.tracecompare.model.ProcessorModel;
import net.openchrom.xxd.processor.supplier.tracecompare.model.ReferenceModel;
import net.openchrom.xxd.processor.supplier.tracecompare.model.SampleLaneModel;
import net.openchrom.xxd.processor.supplier.tracecompare.preferences.PreferenceSupplier;
import net.openchrom.xxd.processor.supplier.tracecompare.ui.editors.EditorProcessor;
import net.openchrom.xxd.processor.supplier.tracecompare.ui.internal.runnables.MeasurementImportRunnable;

public class TraceCompareEditorUI extends Composite {

	private static final Logger logger = Logger.getLogger(TraceCompareEditorUI.class);
	//
	private static final String DESCRIPTION = "Trace Compare";
	//
	private Label labelSampleGroup;
	private Combo comboReferenceGroups;
	private TabFolder tabFolder;
	private Text textGeneralNotes;
	//
	private ProcessorModel processorModel;
	Map<String, Map<Integer, Map<String, ISeriesData>>> modelData = new HashMap<String, Map<Integer, Map<String, ISeriesData>>>();

	public TraceCompareEditorUI(Composite parent, int style) {
		super(parent, style);
		modelData = new HashMap<String, Map<Integer, Map<String, ISeriesData>>>();
		initialize(parent);
	}

	public void update(Object object) {

		if(object instanceof EditorProcessor) {
			EditorProcessor editorProcessor = (EditorProcessor)object;
			boolean initialize = false;
			if(processorModel == null) {
				initialize = true;
				processorModel = editorProcessor.getProcessorModel();
			} else {
				if(!processorModel.getSampleGroup().equals(editorProcessor.getProcessorModel().getSampleGroup())) {
					initialize = true;
					processorModel = editorProcessor.getProcessorModel();
				}
			}
			/*
			 * Get the sample group.
			 */
			labelSampleGroup.setText(processorModel.getSampleGroup());
			textGeneralNotes.setText(processorModel.getGeneralNotes());
			if(initialize) {
				initializeReferencesComboItems();
				initializeTraceComparators(false);
			}
		}
	}

	private void initialize(Composite parent) {

		setLayout(new FillLayout());
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		/*
		 * Elements
		 */
		createLabelSampleGroup(composite);
		createReferenceGroupsCombo(composite);
		createTraceComparators(composite);
		createGeneralNotes(composite);
	}

	private void createLabelSampleGroup(Composite parent) {

		Label label = new Label(parent, SWT.NONE);
		label.setText("Sample Group:");
		//
		labelSampleGroup = new Label(parent, SWT.NONE);
		labelSampleGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}

	private void createReferenceGroupsCombo(Composite parent) {

		Label label = new Label(parent, SWT.NONE);
		label.setText("Reference Group:");
		//
		comboReferenceGroups = new Combo(parent, SWT.READ_ONLY);
		initializeReferencesComboItems();
		comboReferenceGroups.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		comboReferenceGroups.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				initializeTraceComparators(true);
			}
		});
	}

	private void createTraceComparators(Composite parent) {

		tabFolder = new TabFolder(parent, SWT.BOTTOM);
		tabFolder.setLayoutData(getGridData(GridData.FILL_BOTH));
		//
		initializeTraceComparators(false);
	}

	private void initializeReferencesComboItems() {

		comboReferenceGroups.removeAll();
		String pathDirectory = PreferenceSupplier.getFilterPathReferences();
		String fileExtension = PreferenceSupplier.getFileExtension();
		List<String> references = Processor.getMeasurementPatterns(pathDirectory, fileExtension);
		comboReferenceGroups.setItems(references.toArray(new String[references.size()]));
		//
		if(comboReferenceGroups.getItemCount() > 0) {
			if(processorModel != null) {
				String referenceGroup = processorModel.getReferenceGroup();
				int index = comboReferenceGroups.indexOf(referenceGroup);
				if(index >= 0) {
					comboReferenceGroups.select(index);
				} else {
					comboReferenceGroups.select(0);
				}
			} else {
				comboReferenceGroups.select(0);
			}
		}
	}

	private void initializeTraceComparators(boolean validate) {

		if(validate) {
			IProcessingInfo processingInfo = validateSettings();
			Display.getDefault().asyncExec(new Runnable() {

				@Override
				public void run() {

					ProcessingInfoViewSupport.updateProcessingInfo(processingInfo, true);
				}
			});
		}
		/*
		 * Get the sample and reference.
		 */
		List<File> sampleFiles = new ArrayList<File>();
		List<File> referenceFiles = new ArrayList<File>();
		//
		if(processorModel != null) {
			//
			String fileExtension = PreferenceSupplier.getFileExtension();
			//
			String samplePathDirectory = PreferenceSupplier.getFilterPathSamples();
			String sampleGroup = processorModel.getSampleGroup();
			sampleFiles = Processor.getMeasurementFiles(samplePathDirectory, fileExtension, sampleGroup);
			//
			String referencePathDirectory = PreferenceSupplier.getFilterPathReferences();
			String referenceGroup = comboReferenceGroups.getText().trim();
			referenceFiles = Processor.getMeasurementFiles(referencePathDirectory, fileExtension, referenceGroup);
		}
		/*
		 * Clear the tab folder.
		 */
		for(TabItem tabItem : tabFolder.getItems()) {
			tabItem.dispose();
		}
		/*
		 * Validate that files have been selected.
		 */
		if(sampleFiles.size() > 0 && referenceFiles.size() > 0) {
			createSampleLaneTabItems(sampleFiles, referenceFiles);
		} else {
			createEmptyTabItem();
		}
	}

	private void createSampleLaneTabItems(List<File> sampleFiles, List<File> referenceFiles) {

		TabItem tabItem;
		Composite composite;
		TraceDataComparisonUI traceDataSelectedSignal;
		SampleLaneModel sampleLaneModel;
		/*
		 * Get the model.
		 */
		String sampleGroup = processorModel.getSampleGroup();
		String referenceGroup = comboReferenceGroups.getText().trim();
		//
		ReferenceModel referenceModel = processorModel.getReferenceModels().get(referenceGroup);
		if(referenceModel == null) {
			referenceModel = new ReferenceModel();
			referenceModel.setReferenceGroup(referenceGroup);
			referenceModel.setReferencePath(PreferenceSupplier.getFilterPathReferences());
			processorModel.getReferenceModels().put(referenceGroup, referenceModel);
		}
		/*
		 * Extract the data.
		 * 0196 [Group]
		 * -> 1 [Sample Lane] -> 190 [nm], ISeriesData
		 * -> 1 [Sample Lane] -> 200 [nm], ISeriesData
		 * ...
		 * -> 18 [Sample Lane] -> 190 [nm], ISeriesData
		 * -> 18 [Sample Lane] -> 200 [nm], ISeriesData
		 * 0197 [Group]
		 * -> 1 [Sample Lane] -> 190 [nm], ISeriesData
		 * -> 1 [Sample Lane] -> 200 [nm], ISeriesData
		 * ...
		 * -> 18 [Sample Lane] -> 190 [nm], ISeriesData
		 * -> 18 [Sample Lane] -> 200 [nm], ISeriesData
		 */
		Map<Integer, Map<String, ISeriesData>> sampleMeasurementsData = modelData.get(sampleGroup);
		if(sampleMeasurementsData == null) {
			sampleMeasurementsData = extractMeasurementsData(sampleFiles);
			modelData.put(sampleGroup, sampleMeasurementsData);
		}
		//
		Map<Integer, Map<String, ISeriesData>> referenceMeasurementsData = modelData.get(referenceGroup);
		if(referenceMeasurementsData == null) {
			referenceMeasurementsData = extractMeasurementsData(referenceFiles);
			modelData.put(referenceGroup, referenceMeasurementsData);
		}
		/*
		 * Sample Lanes
		 */
		int sampleLanes = sampleMeasurementsData.keySet().size();
		for(int sampleLane = 1; sampleLane <= sampleLanes; sampleLane++) {
			/*
			 * Sample Lane
			 */
			sampleLaneModel = referenceModel.getSampleLaneModels().get(sampleLane);
			if(sampleLaneModel == null) {
				sampleLaneModel = new SampleLaneModel();
				sampleLaneModel.setSampleLane(sampleLane);
				referenceModel.getSampleLaneModels().put(sampleLane, sampleLaneModel);
			}
			//
			tabItem = new TabItem(tabFolder, SWT.NONE);
			tabItem.setText("SL " + sampleLane);
			composite = new Composite(tabFolder, SWT.NONE);
			composite.setLayout(new FillLayout());
			traceDataSelectedSignal = new TraceDataComparisonUI(composite, SWT.BORDER);
			traceDataSelectedSignal.setBackground(Colors.WHITE);
			traceDataSelectedSignal.setData(processorModel, sampleLaneModel, referenceGroup, sampleMeasurementsData, referenceMeasurementsData);
			tabItem.setControl(composite);
		}
	}

	private Map<Integer, Map<String, ISeriesData>> extractMeasurementsData(List<File> measurementFiles) {

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
		//
		List<IChromatogramWSD> measurements = runnable.getMeasurements();
		for(IChromatogram measurement : measurements) {
			/*
			 * Sample Lane 1
			 */
			int index = 1;
			seriesData = extractMeasurement(measurement);
			addMeasurementData(measurementsData, seriesData, index++);
			/*
			 * Sample Lane 2 ... n
			 */
			for(IChromatogram additionalMeasurement : measurement.getReferencedChromatograms()) {
				seriesData = extractMeasurement(additionalMeasurement);
				addMeasurementData(measurementsData, seriesData, index++);
			}
		}
		//
		return measurementsData;
	}

	private void addMeasurementData(Map<Integer, Map<String, ISeriesData>> measurementsData, ISeriesData seriesData, int index) {

		Map<String, ISeriesData> wavelengthData = measurementsData.get(index);
		if(wavelengthData == null) {
			wavelengthData = new HashMap<String, ISeriesData>();
			measurementsData.put(index, wavelengthData);
		}
		wavelengthData.put(seriesData.getId(), seriesData);
	}

	private ISeriesData extractMeasurement(IChromatogram measurement) {

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
		//
		return new SeriesData(xSeries, ySeries, Integer.toString(wavelength));
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

	private void createEmptyTabItem() {

		/*
		 * No Data
		 */
		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText("Message");
		//
		Composite composite = new Composite(tabFolder, SWT.NONE);
		composite.setLayout(new GridLayout(1, true));
		composite.setBackground(Colors.GRAY);
		//
		Label label = new Label(composite, SWT.NONE);
		Display display = Display.getDefault();
		Font font = new Font(display, "Arial", 14, SWT.BOLD);
		label.setFont(font);
		label.setBackground(Colors.GRAY);
		label.setText("No data has been selected yet.");
		//
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalAlignment = SWT.CENTER;
		gridData.verticalAlignment = SWT.CENTER;
		label.setLayoutData(gridData);
		font.dispose();
		//
		tabItem.setControl(composite);
	}

	private void createGeneralNotes(Composite parent) {

		Label label = new Label(parent, SWT.NONE);
		label.setText("General notes:");
		label.setLayoutData(getGridData(GridData.FILL_HORIZONTAL));
		//
		textGeneralNotes = new Text(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		textGeneralNotes.setText("");
		GridData gridData = getGridData(GridData.FILL_HORIZONTAL);
		gridData.heightHint = 100;
		textGeneralNotes.setLayoutData(gridData);
	}

	private GridData getGridData(int style) {

		GridData gridData = new GridData(style);
		gridData.horizontalSpan = 2;
		return gridData;
	}

	private IProcessingInfo validateSettings() {

		IProcessingInfo processingInfo = new ProcessingInfo();
		//
		File fileReference = new File(PreferenceSupplier.getFilterPathReferences() + File.separator + comboReferenceGroups.getText());
		if(fileReference.exists()) {
			processingInfo.addInfoMessage(DESCRIPTION, "The reference file exists.");
		} else {
			processingInfo.addErrorMessage(DESCRIPTION, "The reference file doesn't exist.");
		}
		//
		return processingInfo;
	}
}

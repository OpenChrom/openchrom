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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.chemclipse.processing.ui.support.ProcessingInfoViewSupport;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.chemclipse.wsd.model.core.IScanSignalWSD;
import org.eclipse.chemclipse.wsd.model.core.selection.IChromatogramSelectionWSD;
import org.eclipse.chemclipse.wsd.model.xwc.ExtractedWavelengthSignalExtractor;
import org.eclipse.chemclipse.wsd.model.xwc.IExtractedWavelengthSignals;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
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

import net.openchrom.xxd.processor.supplier.tracecompare.model.ProcessorModel;
import net.openchrom.xxd.processor.supplier.tracecompare.model.ReferenceModel;
import net.openchrom.xxd.processor.supplier.tracecompare.model.TraceModel;
import net.openchrom.xxd.processor.supplier.tracecompare.preferences.PreferenceSupplier;
import net.openchrom.xxd.processor.supplier.tracecompare.ui.editors.EditorProcessor;
import net.openchrom.xxd.processor.supplier.tracecompare.ui.internal.runnables.ChromatogramImportRunnable;

public class TraceCompareEditorUI extends Composite {

	private static final Logger logger = Logger.getLogger(TraceCompareEditorUI.class);
	//
	private static final String DESCRIPTION = "Trace Compare";
	private static final Pattern SAMPLE_PATTERN = Pattern.compile("(.*)(A)(\\d+)(\\.)(DFM)");
	private static final String FILE_EXTENSION = ".DFM";
	//
	private Label labelSample;
	private Combo comboReferences;
	private Combo comboSampleLanes;
	private TabFolder tabFolder;
	private Text textGeneralNotes;
	//
	private ProcessorModel processorModel;
	//
	private Map<Integer, Color> colorMap;

	public TraceCompareEditorUI(Composite parent, int style) {
		super(parent, style);
		//
		colorMap = new HashMap<Integer, Color>();
		colorMap.put(190, Colors.YELLOW);
		colorMap.put(200, Colors.BLUE);
		colorMap.put(220, Colors.CYAN);
		colorMap.put(240, Colors.GREEN);
		colorMap.put(260, Colors.BLACK);
		colorMap.put(280, Colors.RED);
		colorMap.put(300, Colors.DARK_RED);
		//
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
				if(!processorModel.getSampleName().equals(editorProcessor.getProcessorModel().getSampleName())) {
					initialize = true;
					processorModel = editorProcessor.getProcessorModel();
				}
			}
			/*
			 * Get the sample group.
			 */
			String fileName = new File(processorModel.getSamplePath()).getName();
			Matcher matcher = SAMPLE_PATTERN.matcher(fileName);
			String sampleGroup;
			if(matcher.find()) {
				sampleGroup = "Unknown Sample: " + matcher.group(1) + " (Wavelengths and Measurements)";
			} else {
				sampleGroup = "No sample group selected.";
			}
			//
			labelSample.setText(sampleGroup);
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
		createLabelSample(composite);
		createReferenceCombo(composite);
		createSampleLanesCombo(composite);
		createTraceComparators(composite);
		createGeneralNotes(composite);
	}

	private void createLabelSample(Composite parent) {

		Display display = Display.getDefault();
		Font font = new Font(display, "Arial", 14, SWT.BOLD);
		//
		labelSample = new Label(parent, SWT.NONE);
		labelSample.setFont(font);
		labelSample.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//
		font.dispose();
	}

	private void createReferenceCombo(Composite parent) {

		comboReferences = new Combo(parent, SWT.READ_ONLY);
		initializeReferencesComboItems();
		comboReferences.setLayoutData(getGridData(GridData.FILL_HORIZONTAL));
		comboReferences.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				initializeTraceComparators(true);
			}
		});
	}

	private void createSampleLanesCombo(Composite parent) {

		comboSampleLanes = new Combo(parent, SWT.READ_ONLY);
		initializeSampleLaneComboItems(0);
		comboSampleLanes.setLayoutData(getGridData(GridData.FILL_HORIZONTAL));
		comboSampleLanes.addSelectionListener(new SelectionAdapter() {

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

		comboReferences.removeAll();
		//
		File directory = new File(PreferenceSupplier.getFilterPathReferences());
		if(directory.exists() && directory.isDirectory()) {
			List<String> references = new ArrayList<String>();
			for(File file : directory.listFiles()) {
				if(file.isFile()) {
					String name = file.getName();
					if(name.endsWith(FILE_EXTENSION)) {
						references.add(name);
					}
				}
			}
			comboReferences.setItems(references.toArray(new String[references.size()]));
		} else {
			comboReferences.setItems(new String[]{});
		}
		//
		if(comboReferences.getItemCount() > 0) {
			comboReferences.select(0);
		}
	}

	private void initializeSampleLaneComboItems(int numberOfSampleLanes) {

		comboSampleLanes.removeAll();
		//
		List<String> sampleLanes = new ArrayList<String>();
		for(int i = 1; i <= numberOfSampleLanes; i++) {
			sampleLanes.add("Sample Lane " + i);
		}
		comboSampleLanes.setItems(sampleLanes.toArray(new String[sampleLanes.size()]));
		//
		if(comboSampleLanes.getItemCount() > 0) {
			comboSampleLanes.select(0);
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
		File fileSample;
		if(processorModel != null) {
			fileSample = new File(processorModel.getSamplePath().trim());
		} else {
			fileSample = new File("");
		}
		File fileReference = new File(PreferenceSupplier.getFilterPathReferences() + File.separator + comboReferences.getText());
		/*
		 * Clear the tab folder.
		 */
		for(TabItem tabItem : tabFolder.getItems()) {
			tabItem.dispose();
		}
		//
		if(fileSample.exists() && fileReference.exists()) {
			/*
			 * Get the chromatograms.
			 */
			ChromatogramImportRunnable runnable = new ChromatogramImportRunnable(fileSample.getAbsolutePath(), fileReference.getAbsolutePath());
			ProgressMonitorDialog monitor = new ProgressMonitorDialog(Display.getDefault().getActiveShell());
			try {
				monitor.run(true, true, runnable);
			} catch(InterruptedException e1) {
				logger.warn(e1);
			} catch(InvocationTargetException e1) {
				logger.warn(e1);
			}
			//
			List<IChromatogramSelectionWSD> chromatogramSelections = runnable.getChromatogramSelections();
			IChromatogramSelectionWSD sampleChromatogramSelection = chromatogramSelections.get(0);
			IChromatogramSelectionWSD referenceChromatogramSelection = chromatogramSelections.get(1);
			initializeSampleLaneComboItems(sampleChromatogramSelection.getChromatogramWSD().getReferencedChromatograms().size());
			IExtractedWavelengthSignals extractedWavelengthSignalsSample = getExtractedWavelengthSignals(sampleChromatogramSelection);
			IExtractedWavelengthSignals extractedWavelengthSignalsReference = getExtractedWavelengthSignals(referenceChromatogramSelection);
			//
			createWavelengthTabItems(extractedWavelengthSignalsSample, extractedWavelengthSignalsReference);
		} else {
			createEmptyTabItem();
		}
	}

	private IExtractedWavelengthSignals getExtractedWavelengthSignals(IChromatogramSelectionWSD chromatogramSelectionWSD) {

		IChromatogramWSD chromatogramWSD = chromatogramSelectionWSD.getChromatogramWSD();
		ExtractedWavelengthSignalExtractor extractedWavelengthSignalExtractor = new ExtractedWavelengthSignalExtractor(chromatogramWSD);
		return extractedWavelengthSignalExtractor.getExtractedWavelengthSignals(chromatogramSelectionWSD);
	}

	private void createWavelengthTabItems(IExtractedWavelengthSignals extractedWavelengthSignalsSample, IExtractedWavelengthSignals extractedWavelengthSignalsReference) {

		TabItem tabItem;
		Composite composite;
		TraceDataComparisonUI traceDataSelectedSignal;
		TraceModel traceModel;
		/*
		 * Get the model.
		 */
		String reference = comboReferences.getText().trim();
		ReferenceModel referenceModel = processorModel.getReferenceModels().get(reference);
		if(referenceModel == null) {
			referenceModel = new ReferenceModel();
			referenceModel.setReferenceName(reference);
			referenceModel.setReferencePath(PreferenceSupplier.getFilterPathReferences() + File.separator + reference);
			processorModel.getReferenceModels().put(reference, referenceModel);
		}
		//
		// extractedWavelengthSignalsSample.getUsedWavelenghts();
		List<Integer> usedWavelenghts = new ArrayList<Integer>();
		usedWavelenghts.add(190);
		usedWavelenghts.add(200);
		usedWavelenghts.add(220);
		usedWavelenghts.add(240);
		usedWavelenghts.add(260);
		usedWavelenghts.add(280);
		usedWavelenghts.add(300);
		//
		for(int wavelength : usedWavelenghts) {
			/*
			 * Item XWC
			 */
			traceModel = referenceModel.getTraceModels().get(wavelength);
			if(traceModel == null) {
				traceModel = new TraceModel();
				traceModel.setTrace(wavelength);
				referenceModel.getTraceModels().put((double)wavelength, traceModel);
			}
			//
			tabItem = new TabItem(tabFolder, SWT.NONE);
			tabItem.setText(wavelength + " nm");
			composite = new Composite(tabFolder, SWT.NONE);
			composite.setLayout(new FillLayout());
			traceDataSelectedSignal = new TraceDataComparisonUI(composite, SWT.BORDER);
			traceDataSelectedSignal.setBackground(Colors.WHITE);
			traceDataSelectedSignal.setData((int)IScanSignalWSD.TIC_SIGNAL, extractedWavelengthSignalsSample, extractedWavelengthSignalsReference, processorModel, traceModel);
			tabItem.setControl(composite);
		}
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
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
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
		File fileReference = new File(PreferenceSupplier.getFilterPathReferences() + File.separator + comboReferences.getText());
		if(fileReference.exists()) {
			processingInfo.addInfoMessage(DESCRIPTION, "The reference file exists.");
		} else {
			processingInfo.addErrorMessage(DESCRIPTION, "The reference file doesn't exist.");
		}
		//
		return processingInfo;
	}
}

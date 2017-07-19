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
import java.util.List;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.chemclipse.processing.ui.support.ProcessingInfoViewSupport;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.chemclipse.wsd.model.core.selection.IChromatogramSelectionWSD;
import org.eclipse.chemclipse.wsd.model.xwc.ExtractedWavelengthSignalExtractor;
import org.eclipse.chemclipse.wsd.model.xwc.IExtractedWavelengthSignals;
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
import net.openchrom.xxd.processor.supplier.tracecompare.ui.internal.runnables.ChromatogramImportRunnable;

public class TraceCompareEditorUI extends Composite {

	private static final Logger logger = Logger.getLogger(TraceCompareEditorUI.class);
	//
	private static final String DESCRIPTION = "Trace Compare";
	//
	private Label labelSample;
	private Combo comboReferences;
	private TabFolder tabFolder;
	private Text textGeneralNotes;
	//
	private ProcessorModel processorModel;

	public TraceCompareEditorUI(Composite parent, int style) {
		super(parent, style);
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
				if(!processorModel.getSamplePattern().equals(editorProcessor.getProcessorModel().getSamplePattern())) {
					initialize = true;
					processorModel = editorProcessor.getProcessorModel();
				}
			}
			/*
			 * Get the sample group.
			 */
			labelSample.setText(processorModel.getSamplePattern());
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

	private void createTraceComparators(Composite parent) {

		tabFolder = new TabFolder(parent, SWT.BOTTOM);
		tabFolder.setLayoutData(getGridData(GridData.FILL_BOTH));
		//
		initializeTraceComparators(false);
	}

	private void initializeReferencesComboItems() {

		comboReferences.removeAll();
		String pathDirectory = PreferenceSupplier.getFilterPathReferences();
		String fileExtension = PreferenceSupplier.getFileExtension();
		List<String> references = Processor.getMeasurementPatterns(pathDirectory, fileExtension);
		comboReferences.setItems(references.toArray(new String[references.size()]));
		//
		if(comboReferences.getItemCount() > 0) {
			comboReferences.select(0);
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
		File fileSample = new File("");
		File fileReference = new File("");
		//
		if(processorModel != null) {
			//
			String fileExtension = PreferenceSupplier.getFileExtension();
			//
			String samplePathDirectory = PreferenceSupplier.getFilterPathSamples();
			String samplePattern = processorModel.getSamplePattern();
			List<File> sampleFiles = Processor.getMeasurementFiles(samplePathDirectory, fileExtension, samplePattern);
			if(sampleFiles.size() > 0) {
				fileSample = sampleFiles.get(0);
			}
			//
			String referencePathDirectory = PreferenceSupplier.getFilterPathReferences();
			String referencePattern = comboReferences.getText().trim();
			List<File> referenceFiles = Processor.getMeasurementFiles(referencePathDirectory, fileExtension, referencePattern);
			if(referenceFiles.size() > 0) {
				fileReference = referenceFiles.get(0);
			}
		}
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
			createSampleLaneTabItems(chromatogramSelections);
		} else {
			createEmptyTabItem();
		}
	}

	private IExtractedWavelengthSignals getExtractedWavelengthSignals(IChromatogramSelectionWSD chromatogramSelectionWSD) {

		IChromatogramWSD chromatogramWSD = chromatogramSelectionWSD.getChromatogramWSD();
		ExtractedWavelengthSignalExtractor extractedWavelengthSignalExtractor = new ExtractedWavelengthSignalExtractor(chromatogramWSD);
		return extractedWavelengthSignalExtractor.getExtractedWavelengthSignals(chromatogramSelectionWSD);
	}

	private void createSampleLaneTabItems(List<IChromatogramSelectionWSD> chromatogramSelections) {

		TabItem tabItem;
		Composite composite;
		TraceDataComparisonUI traceDataSelectedSignal;
		SampleLaneModel sampleLaneModel;
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
		IChromatogramSelectionWSD sampleChromatogramSelection = chromatogramSelections.get(0);
		IChromatogramSelectionWSD referenceChromatogramSelection = chromatogramSelections.get(1);
		IExtractedWavelengthSignals extractedWavelengthSignalsSample = getExtractedWavelengthSignals(sampleChromatogramSelection);
		IExtractedWavelengthSignals extractedWavelengthSignalsReference = getExtractedWavelengthSignals(referenceChromatogramSelection);
		/*
		 * Chromatogram + references chromatograms.
		 */
		int sampleLanes = sampleChromatogramSelection.getChromatogramWSD().getReferencedChromatograms().size() + 1;
		for(int sampleLane = 1; sampleLane <= sampleLanes; sampleLane++) {
			/*
			 * Item XWC
			 */
			sampleLaneModel = referenceModel.getSampleLaneModels().get(sampleLane);
			if(sampleLaneModel == null) {
				sampleLaneModel = new SampleLaneModel();
				sampleLaneModel.setSampleLane(sampleLane);
				referenceModel.getSampleLaneModels().put(sampleLane, sampleLaneModel);
			}
			//
			tabItem = new TabItem(tabFolder, SWT.NONE);
			tabItem.setText("Lane " + sampleLane);
			composite = new Composite(tabFolder, SWT.NONE);
			composite.setLayout(new FillLayout());
			traceDataSelectedSignal = new TraceDataComparisonUI(composite, SWT.BORDER);
			traceDataSelectedSignal.setBackground(Colors.WHITE);
			traceDataSelectedSignal.setData(processorModel, sampleLaneModel, extractedWavelengthSignalsSample, extractedWavelengthSignalsReference);
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

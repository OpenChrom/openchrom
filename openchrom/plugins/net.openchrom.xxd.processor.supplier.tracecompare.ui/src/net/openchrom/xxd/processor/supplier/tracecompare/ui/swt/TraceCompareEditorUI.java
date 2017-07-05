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
import java.util.List;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.chemclipse.processing.ui.support.ProcessingInfoViewSupport;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.ui.wizards.ChromatogramWizardElements;
import org.eclipse.chemclipse.support.ui.wizards.IChromatogramWizardElements;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.chemclipse.ux.extension.wsd.ui.wizards.ChromatogramInputEntriesWizard;
import org.eclipse.chemclipse.wsd.model.core.selection.IChromatogramSelectionWSD;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import net.openchrom.xxd.processor.supplier.tracecompare.preferences.PreferenceSupplier;
import net.openchrom.xxd.processor.supplier.tracecompare.ui.internal.runnables.ChromatogramImportRunnable;

public class TraceCompareEditorUI extends Composite {

	private static final Logger logger = Logger.getLogger(TraceCompareEditorUI.class);
	//
	private static final String DESCRIPTION = "Trace Compare";
	//
	private Text textSamplePath;
	private Combo comboReferences;
	private TabFolder tabFolder;
	private Text textGeneralNotes;

	public TraceCompareEditorUI(Composite parent, int style) {
		super(parent, style);
		initialize(parent);
	}

	public void update(Object object) {

		// TODO
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

		textSamplePath = new Text(parent, SWT.BORDER);
		textSamplePath.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//
		Button buttonSample = new Button(parent, SWT.PUSH);
		buttonSample.setText("Select");
		buttonSample.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_CHROMATOGRAM, IApplicationImage.SIZE_16x16));
		buttonSample.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				IChromatogramWizardElements chromatogramWizardElements = new ChromatogramWizardElements();
				System.out.println(PreferenceSupplier.getFilterPathSamples());
				ChromatogramInputEntriesWizard inputWizard = new ChromatogramInputEntriesWizard(chromatogramWizardElements, "Sample", "Select the sample.", PreferenceSupplier.getFilterPathSamples());
				WizardDialog wizardDialog = new WizardDialog(Display.getDefault().getActiveShell(), inputWizard);
				wizardDialog.create();
				//
				if(wizardDialog.open() == WizardDialog.OK) {
					List<String> selectedChromatograms = chromatogramWizardElements.getSelectedChromatograms();
					if(selectedChromatograms.size() > 0) {
						String selectedChromatogram = chromatogramWizardElements.getSelectedChromatograms().get(0);
						textSamplePath.setText(selectedChromatogram);
						//
						File file = new File(selectedChromatogram);
						if(file.exists()) {
							PreferenceSupplier.setFilterPathSamples(file.getParent());
							initializeTraceComparators();
						}
					}
				}
			}
		});
	}

	private void createReferenceCombo(Composite parent) {

		comboReferences = new Combo(parent, SWT.READ_ONLY);
		initializeReferencesComboItems();
		comboReferences.setLayoutData(getGridData(GridData.FILL_HORIZONTAL));
		comboReferences.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				initializeTraceComparators();
			}
		});
	}

	private void createTraceComparators(Composite parent) {

		tabFolder = new TabFolder(parent, SWT.BOTTOM);
		tabFolder.setLayoutData(getGridData(GridData.FILL_BOTH));
		//
		initializeTraceComparators();
	}

	private void initializeReferencesComboItems() {

		File directory = new File(PreferenceSupplier.getFilterPathReferences());
		if(directory.exists() && directory.isDirectory()) {
			List<String> references = new ArrayList<String>();
			for(File file : directory.listFiles()) {
				if(file.isFile()) {
					String name = file.getName();
					if(name.endsWith(".DFM")) {
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

	private void initializeTraceComparators() {

		/*
		 * Get the sample and reference.
		 */
		File fileSample = new File(textSamplePath.getText().trim());
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
			List<IChromatogramSelectionWSD> chromatogramSelections = runnable.getChromatogramSelections();
			IChromatogramSelectionWSD sampleChromatogramSelectionWSD = chromatogramSelections.get(0);
			IChromatogramSelectionWSD referenceChromatogramSelectionWSD = chromatogramSelections.get(1);
			//
			/*
			 * Item TIC
			 */
			String trace = "TIC";
			TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
			tabItem.setText(trace);
			Composite composite = new Composite(tabFolder, SWT.NONE);
			composite.setLayout(new FillLayout());
			TraceDataComparisonUI traceDataSelectedSignal = new TraceDataComparisonUI(composite, SWT.BORDER);
			traceDataSelectedSignal.setBackground(Colors.WHITE);
			traceDataSelectedSignal.setData(trace, sampleChromatogramSelectionWSD, referenceChromatogramSelectionWSD);
			//
			tabItem.setControl(composite);
			// for(int i = 203; i <= 210; i++) {
			// /*
			// * Item XIC
			// */
			// TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
			// tabItem.setText(i + " nm");
			// Composite composite = new Composite(tabFolder, SWT.NONE);
			// composite.setLayout(new FillLayout());
			// //
			// TraceDataComparisonUI traceDataSelectedSignal = new TraceDataComparisonUI(composite, SWT.BORDER);
			// traceDataSelectedSignal.setBackground(Colors.WHITE);
			// traceDataSelectedSignal.setTrace(i + " nm", sample, reference);
			// //
			// tabItem.setControl(composite);
			// }
		} else {
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
			//
			IProcessingInfo processingInfo = validateSettings();
			ProcessingInfoViewSupport.updateProcessingInfo(processingInfo, true);
		}
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
		File fileSample = new File(textSamplePath.getText().trim());
		if(fileSample.exists()) {
			processingInfo.addInfoMessage(DESCRIPTION, "The sample file exists.");
		} else {
			processingInfo.addErrorMessage(DESCRIPTION, "The sample file doesn't exist.");
		}
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

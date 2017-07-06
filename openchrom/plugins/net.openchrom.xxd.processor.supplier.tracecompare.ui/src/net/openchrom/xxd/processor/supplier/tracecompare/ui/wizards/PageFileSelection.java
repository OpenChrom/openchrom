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
package net.openchrom.xxd.processor.supplier.tracecompare.ui.wizards;

import java.io.File;
import java.util.List;

import org.eclipse.chemclipse.support.ui.wizards.AbstractExtendedWizardPage;
import org.eclipse.chemclipse.support.ui.wizards.ChromatogramWizardElements;
import org.eclipse.chemclipse.support.ui.wizards.IChromatogramWizardElements;
import org.eclipse.chemclipse.ux.extension.wsd.ui.wizards.ChromatogramInputEntriesWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import net.openchrom.xxd.processor.supplier.tracecompare.model.ProcessorModel;
import net.openchrom.xxd.processor.supplier.tracecompare.preferences.PreferenceSupplier;

public class PageFileSelection extends AbstractExtendedWizardPage {

	private IProcessorWizardElements wizardElements;
	//
	private Text samplePathText;
	private Text referencePathText;
	private Text generalNotesText;

	public PageFileSelection(IProcessorWizardElements wizardElements) {
		//
		super(PageFileSelection.class.getName());
		setTitle("TraceCompare Setup");
		setDescription("Select the sample and reference chromatogram.");
		this.wizardElements = wizardElements;
	}

	@Override
	public boolean canFinish() {

		ProcessorModel processorModel = wizardElements.getProcessorModel();
		//
		if(getErrorMessage() != null) {
			return false;
		}
		//
		if(processorModel.getSamplePath() == null || "".equals(processorModel.getSamplePath())) {
			return false;
		}
		//
		if(processorModel.getReferencesPath() == null || "".equals(processorModel.getReferencesPath())) {
			return false;
		}
		//
		if(processorModel.getGeneralNotes() == null) {
			return false;
		}
		//
		return true;
	}

	@Override
	public void setDefaultValues() {

	}

	@Override
	public void setVisible(boolean visible) {

		super.setVisible(visible);
		if(visible) {
			ProcessorModel processorModel = wizardElements.getProcessorModel();
			samplePathText.setText((processorModel.getSamplePath() != null) ? processorModel.getSamplePath() : "");
			referencePathText.setText((processorModel.getReferencesPath() != null) ? processorModel.getReferencesPath() : "");
			generalNotesText.setText((processorModel.getGeneralNotes() != null) ? processorModel.getGeneralNotes() : "");
			validateData();
		}
	}

	@Override
	public void createControl(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		//
		createSamplePathSection(composite);
		createReferencePathSection(composite);
		createGeneralNotesSection(composite);
		//
		validateData();
		setControl(composite);
	}

	private void createSamplePathSection(Composite parent) {

		Label label = new Label(parent, SWT.NONE);
		label.setText("Sample Chromatogram");
		GridData gridDataLabel = new GridData(GridData.FILL_HORIZONTAL);
		gridDataLabel.horizontalSpan = 2;
		label.setLayoutData(gridDataLabel);
		//
		samplePathText = new Text(parent, SWT.BORDER);
		samplePathText.setText("");
		samplePathText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//
		Button button = new Button(parent, SWT.PUSH);
		button.setText("Select");
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				IChromatogramWizardElements chromatogramWizardElements = new ChromatogramWizardElements();
				ChromatogramInputEntriesWizard inputWizard = new ChromatogramInputEntriesWizard(chromatogramWizardElements, "Sample", "Select the sample.", PreferenceSupplier.getFilterPathSamples());
				WizardDialog wizardDialog = new WizardDialog(Display.getDefault().getActiveShell(), inputWizard);
				wizardDialog.create();
				//
				if(wizardDialog.open() == WizardDialog.OK) {
					List<String> selectedChromatograms = chromatogramWizardElements.getSelectedChromatograms();
					if(selectedChromatograms.size() > 0) {
						String selectedChromatogram = chromatogramWizardElements.getSelectedChromatograms().get(0);
						samplePathText.setText(selectedChromatogram);
						//
						File file = new File(selectedChromatogram);
						if(file.exists()) {
							PreferenceSupplier.setFilterPathSamples(file.getParent());
						}
					}
				}
			}
		});
	}

	private void createReferencePathSection(Composite parent) {

		Label label = new Label(parent, SWT.NONE);
		label.setText("Reference Chromatogram");
		GridData gridDataLabel = new GridData(GridData.FILL_HORIZONTAL);
		gridDataLabel.horizontalSpan = 2;
		label.setLayoutData(gridDataLabel);
		//
		referencePathText = new Text(parent, SWT.BORDER);
		referencePathText.setText("");
		referencePathText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//
		Button button = new Button(parent, SWT.PUSH);
		button.setText("Select");
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				IChromatogramWizardElements chromatogramWizardElements = new ChromatogramWizardElements();
				ChromatogramInputEntriesWizard inputWizard = new ChromatogramInputEntriesWizard(chromatogramWizardElements, "Reference", "Select the reference.", PreferenceSupplier.getFilterPathReferences());
				WizardDialog wizardDialog = new WizardDialog(Display.getDefault().getActiveShell(), inputWizard);
				wizardDialog.create();
				//
				if(wizardDialog.open() == WizardDialog.OK) {
					List<String> selectedChromatograms = chromatogramWizardElements.getSelectedChromatograms();
					if(selectedChromatograms.size() > 0) {
						String selectedChromatogram = chromatogramWizardElements.getSelectedChromatograms().get(0);
						referencePathText.setText(selectedChromatogram);
						//
						File file = new File(selectedChromatogram);
						if(file.exists()) {
							PreferenceSupplier.setFilterPathReferences(file.getParent());
						}
					}
				}
			}
		});
	}

	private void createGeneralNotesSection(Composite parent) {

		Label label = new Label(parent, SWT.NONE);
		label.setText("General Notes");
		GridData gridDataLabel = new GridData(GridData.FILL_HORIZONTAL);
		gridDataLabel.horizontalSpan = 2;
		label.setLayoutData(gridDataLabel);
		//
		generalNotesText = new Text(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2;
		generalNotesText.setLayoutData(gridData);
		generalNotesText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {

				validateData();
			}
		});
	}

	private void validateData() {

		String message = null;
		ProcessorModel processorModel = wizardElements.getProcessorModel();
		//
		String samplePath = samplePathText.getText().trim();
		File sample = new File(samplePath);
		if(!sample.exists()) {
			message = "Please select the sample chromatogram.";
		} else {
			processorModel.setSampleName(sample.getName());
			processorModel.setSamplePath(samplePath);
		}
		//
		String referencePath = referencePathText.getText().trim();
		File reference = new File(referencePath);
		if(!reference.exists()) {
			message = "Please select the reference chromatogram.";
		} else {
			processorModel.setReferencesPath(reference.getParentFile().getAbsolutePath());
		}
		//
		processorModel.setGeneralNotes(generalNotesText.getText().trim());
		/*
		 * Updates the status
		 */
		updateStatus(message);
	}
}

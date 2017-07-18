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

import org.eclipse.chemclipse.support.ui.wizards.AbstractExtendedWizardPage;
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
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import net.openchrom.xxd.processor.supplier.tracecompare.core.Processor;
import net.openchrom.xxd.processor.supplier.tracecompare.model.ProcessorModel;
import net.openchrom.xxd.processor.supplier.tracecompare.preferences.PreferenceSupplier;

public class PageFileSelection extends AbstractExtendedWizardPage {

	private IProcessorWizardElements wizardElements;
	//
	private Text samplePatternText;
	private Text referencePatternText;
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
		if(processorModel.getReferencePath() == null || "".equals(processorModel.getReferencePath())) {
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
			samplePatternText.setText((processorModel.getSamplePath() != null) ? processorModel.getSamplePath() : "");
			referencePatternText.setText((processorModel.getReferencePath() != null) ? processorModel.getReferencePath() : "");
			generalNotesText.setText((processorModel.getGeneralNotes() != null) ? processorModel.getGeneralNotes() : "");
			validateData();
		}
	}

	@Override
	public void createControl(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		//
		createSamplePatternSection(composite);
		createReferencePatternSection(composite);
		createGeneralNotesSection(composite);
		//
		validateData();
		setControl(composite);
	}

	private void createSamplePatternSection(Composite parent) {

		Shell shell = Display.getDefault().getActiveShell();
		String fileExtension = PreferenceSupplier.getFileExtension();
		//
		Label label = new Label(parent, SWT.NONE);
		label.setText("Sample Measurement(s)");
		GridData gridDataLabel = new GridData(GridData.FILL_HORIZONTAL);
		gridDataLabel.horizontalSpan = 2;
		label.setLayoutData(gridDataLabel);
		//
		samplePatternText = new Text(parent, SWT.BORDER);
		samplePatternText.setText("");
		samplePatternText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//
		Button button = new Button(parent, SWT.PUSH);
		button.setText("Select");
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				FileDialog fileDialog = new FileDialog(shell, SWT.READ_ONLY | SWT.MULTI);
				fileDialog.setText("Measurement (*" + fileExtension + ")");
				fileDialog.setFilterExtensions(new String[]{"*" + fileExtension});
				fileDialog.setFilterNames(new String[]{"Data *" + fileExtension});
				fileDialog.setFilterPath(PreferenceSupplier.getFilterPathSamples());
				String pathname = fileDialog.open();
				if(pathname != null) {
					File file = new File(pathname);
					if(file.exists()) {
						samplePatternText.setText(Processor.getSamplePattern(file.getName()));
						PreferenceSupplier.setFilterPathSamples(file.getParent());
					} else {
						samplePatternText.setText("File doesn't exist.");
					}
				}
			}
		});
	}

	private void createReferencePatternSection(Composite parent) {

		Shell shell = Display.getDefault().getActiveShell();
		String fileExtension = PreferenceSupplier.getFileExtension();
		//
		Label label = new Label(parent, SWT.NONE);
		label.setText("Reference Measurement(s)");
		GridData gridDataLabel = new GridData(GridData.FILL_HORIZONTAL);
		gridDataLabel.horizontalSpan = 2;
		label.setLayoutData(gridDataLabel);
		//
		referencePatternText = new Text(parent, SWT.BORDER);
		referencePatternText.setText("");
		referencePatternText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//
		Button button = new Button(parent, SWT.PUSH);
		button.setText("Select");
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				FileDialog fileDialog = new FileDialog(shell, SWT.READ_ONLY | SWT.MULTI);
				fileDialog.setText("Measurement (*" + fileExtension + ")");
				fileDialog.setFilterExtensions(new String[]{"*" + fileExtension});
				fileDialog.setFilterNames(new String[]{"Data *" + fileExtension});
				fileDialog.setFilterPath(PreferenceSupplier.getFilterPathReferences());
				String pathname = fileDialog.open();
				if(pathname != null) {
					File file = new File(pathname);
					if(file.exists()) {
						referencePatternText.setText(Processor.getSamplePattern(file.getName()));
						PreferenceSupplier.setFilterPathReferences(file.getParent());
					} else {
						referencePatternText.setText("File doesn't exist.");
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
		String samplePath = samplePatternText.getText().trim();
		File sample = new File(samplePath);
		if(!sample.exists()) {
			message = "Please select the sample chromatogram.";
		} else {
			processorModel.setSamplePattern(sample.getName());
			processorModel.setSamplePath(samplePath);
		}
		//
		String referencePath = referencePatternText.getText().trim();
		File reference = new File(referencePath);
		if(!reference.exists()) {
			message = "Please select the reference chromatogram.";
		} else {
			processorModel.setReferencePath(reference.getParentFile().getAbsolutePath());
		}
		//
		processorModel.setGeneralNotes(generalNotesText.getText().trim());
		/*
		 * Updates the status
		 */
		updateStatus(message);
	}
}

/*******************************************************************************
 * Copyright (c) 2017, 2018 Lablicate GmbH.
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
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import net.openchrom.xxd.processor.supplier.tracecompare.model.IProcessorModel;
import net.openchrom.xxd.processor.supplier.tracecompare.preferences.PreferenceSupplier;

public class PageSettingsSelection extends AbstractExtendedWizardPage {

	private static final int VERTIAL_INDENT = 10;
	//
	private IProcessorWizardElements wizardElements;
	//
	private Text textSampleDirectory;
	private Text textReferenceDirectory;
	private Button buttonUseValidation;
	private Text textGeneralNotes;

	public PageSettingsSelection(IProcessorWizardElements wizardElements) {
		//
		super(PageSettingsSelection.class.getName());
		setTitle("Trace Compare Setup");
		setDescription("Select the sample and reference directories and set the preferences.");
		this.wizardElements = wizardElements;
	}

	@Override
	public boolean canFinish() {

		IProcessorModel processorModel = wizardElements.getProcessorModel();
		//
		if(getErrorMessage() != null) {
			return false;
		}
		//
		if(processorModel.getSampleDirectory() == null || "".equals(processorModel.getSampleDirectory())) {
			return false;
		}
		//
		if(processorModel.getReferenceDirectory() == null || "".equals(processorModel.getReferenceDirectory())) {
			return false;
		}
		//
		if(processorModel.getReferenceDirectory() == null || "".equals(processorModel.getReferenceDirectory())) {
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
			IProcessorModel processorModel = wizardElements.getProcessorModel();
			if(processorModel != null) {
				/*
				 * Set the sample and references directories.
				 */
				if(textSampleDirectory.getText().trim().equals("")) {
					textSampleDirectory.setText(PreferenceSupplier.getSampleDirectory());
				}
				//
				if(textReferenceDirectory.getText().trim().equals("")) {
					textReferenceDirectory.setText(PreferenceSupplier.getReferenceDirectory());
				}
			}
			//
			textGeneralNotes.setText((processorModel.getGeneralNotes() != null) ? processorModel.getGeneralNotes() : "");
			validateData();
		}
	}

	@Override
	public void createControl(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		//
		createLabelSampleSection(composite);
		createSampleGroupSection(composite);
		createLabelReferenceSection(composite);
		createReferenceGroupSection(composite);
		createUseValidationSection(composite);
		createGeneralNotesSection(composite);
		//
		validateData();
		setControl(composite);
	}

	private void createLabelSampleSection(Composite parent) {

		Label label = new Label(parent, SWT.NONE);
		label.setText("Sample Directory");
		GridData gridDataLabel = new GridData(GridData.FILL_HORIZONTAL);
		gridDataLabel.horizontalSpan = 2;
		label.setLayoutData(gridDataLabel);
	}

	private void createSampleGroupSection(Composite parent) {

		Shell shell = Display.getDefault().getActiveShell();
		//
		textSampleDirectory = new Text(parent, SWT.BORDER);
		textSampleDirectory.setText("");
		textSampleDirectory.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textSampleDirectory.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {

				validateData();
			}
		});
		//
		Button button = new Button(parent, SWT.PUSH);
		button.setText("Select");
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				DirectoryDialog directoryDialog = new DirectoryDialog(shell, SWT.READ_ONLY);
				directoryDialog.setText("Samples");
				directoryDialog.setFilterPath(PreferenceSupplier.getSampleDirectory());
				String pathname = directoryDialog.open();
				if(pathname != null) {
					File directory = new File(pathname);
					if(directory.exists()) {
						String path = directory.getAbsolutePath();
						textSampleDirectory.setText(path);
						PreferenceSupplier.setSampleDirectory(path);
					} else {
						textSampleDirectory.setText("Directory doesn't exist.");
					}
				}
				//
				validateData();
			}
		});
	}

	private void createLabelReferenceSection(Composite parent) {

		Label label = new Label(parent, SWT.NONE);
		label.setText("Reference Directory");
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		gridData.verticalIndent = VERTIAL_INDENT;
		label.setLayoutData(gridData);
	}

	private void createReferenceGroupSection(Composite parent) {

		Shell shell = Display.getDefault().getActiveShell();
		//
		textReferenceDirectory = new Text(parent, SWT.BORDER);
		textReferenceDirectory.setText("");
		textReferenceDirectory.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textReferenceDirectory.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {

				validateData();
			}
		});
		//
		Button button = new Button(parent, SWT.PUSH);
		button.setText("Select");
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				DirectoryDialog directoryDialog = new DirectoryDialog(shell, SWT.READ_ONLY);
				directoryDialog.setText("References");
				directoryDialog.setFilterPath(PreferenceSupplier.getReferenceDirectory());
				String pathname = directoryDialog.open();
				if(pathname != null) {
					File directory = new File(pathname);
					if(directory.exists()) {
						String path = directory.getAbsolutePath();
						textReferenceDirectory.setText(path);
						PreferenceSupplier.setReferenceDirectory(path);
					} else {
						textReferenceDirectory.setText("Directory doesn't exist.");
					}
				}
				//
				validateData();
			}
		});
	}

	private void createUseValidationSection(Composite parent) {

		buttonUseValidation = new Button(parent, SWT.CHECK);
		buttonUseValidation.setText("Perform an additional data validation");
		buttonUseValidation.setToolTipText("A separate validation comparison element will be displayed.");
		buttonUseValidation.setSelection(PreferenceSupplier.isUseDataValidation());
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2;
		buttonUseValidation.setLayoutData(gridData);
		buttonUseValidation.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				validateData();
			}
		});
	}

	private void createGeneralNotesSection(Composite parent) {

		Label label = new Label(parent, SWT.NONE);
		label.setText("General Notes");
		GridData gridDataLabel = new GridData(GridData.FILL_HORIZONTAL);
		gridDataLabel.horizontalSpan = 2;
		gridDataLabel.verticalIndent = VERTIAL_INDENT;
		label.setLayoutData(gridDataLabel);
		//
		textGeneralNotes = new Text(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2;
		textGeneralNotes.setLayoutData(gridData);
		textGeneralNotes.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {

				validateData();
			}
		});
	}

	private void validateData() {

		String message = null;
		IProcessorModel processorModel = wizardElements.getProcessorModel();
		//
		boolean useDataValidation = buttonUseValidation.getSelection();
		PreferenceSupplier.setUseDataValidation(useDataValidation);
		//
		String sampleDirectory = PreferenceSupplier.getSampleDirectory();
		if(!new File(sampleDirectory).exists()) {
			message = "Please select the sample directory.";
		} else {
			processorModel.setSampleDirectory(sampleDirectory);
		}
		//
		if(message == null) {
			String referenceDirectory = PreferenceSupplier.getReferenceDirectory();
			if(!new File(referenceDirectory).exists()) {
				message = "Please select the reference directory.";
			} else {
				processorModel.setReferenceDirectory(referenceDirectory);
			}
		}
		//
		processorModel.setGeneralNotes(textGeneralNotes.getText().trim());
		/*
		 * Updates the status
		 */
		updateStatus(message);
	}
}

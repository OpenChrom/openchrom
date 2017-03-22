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
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.wizards;

import java.io.File;
import java.util.List;

import org.eclipse.chemclipse.support.ui.wizards.AbstractExtendedWizardPage;
import org.eclipse.chemclipse.support.ui.wizards.ChromatogramWizardElements;
import org.eclipse.chemclipse.support.ui.wizards.IChromatogramWizardElements;
import org.eclipse.chemclipse.ux.extension.msd.ui.wizards.ChromatogramInputEntriesWizard;
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
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.core.MassShiftDetector;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.preferences.PreferenceSupplier;

public class PageSettings extends AbstractExtendedWizardPage {

	private IProcessorWizardElements wizardElements;
	//
	private Text c12ChromatogramText;
	private Text c13ChromatogramText;
	private Spinner levelSpinner;
	private Text notesText;
	private Text descriptionText;

	public PageSettings(IProcessorWizardElements wizardElements) {
		//
		super(PageSettings.class.getName());
		setTitle("Mass Shift Detector");
		setDescription("This processor helps to detect mass shifts.");
		this.wizardElements = wizardElements;
	}

	@Override
	public boolean canFinish() {

		String projectDescription = wizardElements.getDescription();
		if(projectDescription == null) {
			return false;
		}
		return true;
	}

	@Override
	public void setDefaultValues() {

	}

	@Override
	public void setVisible(boolean visible) {

		super.setVisible(visible);
		if(visible) {
			String projectDescription = wizardElements.getDescription();
			if(projectDescription != null) {
				descriptionText.setText(projectDescription);
			}
			validateData();
		}
	}

	@Override
	public void createControl(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		//
		createC12ChromatogramSection(composite);
		createC13ChromatogramSection(composite);
		createLevelSection(composite);
		createNoteSection(composite);
		createDescriptionSection(composite);
		//
		validateData();
		//
		setControl(composite);
	}

	private void createC12ChromatogramSection(Composite parent) {

		Label label = new Label(parent, SWT.NONE);
		label.setText("C12 - Chromatogram");
		GridData gridDataLabel = new GridData(GridData.FILL_HORIZONTAL);
		gridDataLabel.horizontalSpan = 2;
		label.setLayoutData(gridDataLabel);
		//
		c12ChromatogramText = new Text(parent, SWT.BORDER);
		c12ChromatogramText.setText("");
		c12ChromatogramText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//
		Button button = new Button(parent, SWT.PUSH);
		button.setText("Select");
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				IChromatogramWizardElements chromatogramWizardElements = new ChromatogramWizardElements();
				ChromatogramInputEntriesWizard inputWizard = new ChromatogramInputEntriesWizard(chromatogramWizardElements, "C12 - Chromatogram", "Select the C12 chromatogram.", PreferenceSupplier.getFilterPathC12Chromatogram());
				WizardDialog wizardDialog = new WizardDialog(Display.getCurrent().getActiveShell(), inputWizard);
				wizardDialog.create();
				//
				if(wizardDialog.open() == WizardDialog.OK) {
					List<String> selectedChromatograms = chromatogramWizardElements.getSelectedChromatograms();
					if(selectedChromatograms.size() > 0) {
						String selectedChromatogram = chromatogramWizardElements.getSelectedChromatograms().get(0);
						c12ChromatogramText.setText(selectedChromatogram);
						validateData();
						//
						File file = new File(selectedChromatogram);
						if(file.exists()) {
							PreferenceSupplier.setFilterPathC12Chromatogram(file.getParentFile().toString());
						}
					}
				}
			}
		});
	}

	private void createC13ChromatogramSection(Composite parent) {

		Label label = new Label(parent, SWT.NONE);
		label.setText("C13 - Chromatogram");
		GridData gridDataLabel = new GridData(GridData.FILL_HORIZONTAL);
		gridDataLabel.horizontalSpan = 2;
		label.setLayoutData(gridDataLabel);
		//
		c13ChromatogramText = new Text(parent, SWT.BORDER);
		c13ChromatogramText.setText("");
		c13ChromatogramText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//
		Button button = new Button(parent, SWT.PUSH);
		button.setText("Select");
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				IChromatogramWizardElements chromatogramWizardElements = new ChromatogramWizardElements();
				ChromatogramInputEntriesWizard inputWizard = new ChromatogramInputEntriesWizard(chromatogramWizardElements, "C13 - Chromatogram", "Select the C13 chromatogram.", PreferenceSupplier.getFilterPathC13Chromatogram());
				WizardDialog wizardDialog = new WizardDialog(Display.getCurrent().getActiveShell(), inputWizard);
				wizardDialog.create();
				//
				if(wizardDialog.open() == WizardDialog.OK) {
					List<String> selectedChromatograms = chromatogramWizardElements.getSelectedChromatograms();
					if(selectedChromatograms.size() > 0) {
						String selectedChromatogram = chromatogramWizardElements.getSelectedChromatograms().get(0);
						c13ChromatogramText.setText(selectedChromatogram);
						validateData();
						//
						File file = new File(selectedChromatogram);
						if(file.exists()) {
							PreferenceSupplier.setFilterPathC13Chromatogram(file.getParentFile().toString());
						}
					}
				}
			}
		});
	}

	private void createLevelSection(Composite parent) {

		Label label = new Label(parent, SWT.NONE);
		label.setText("Number of shifts:");
		GridData gridDataLabel = new GridData(GridData.FILL_HORIZONTAL);
		gridDataLabel.horizontalSpan = 2;
		label.setLayoutData(gridDataLabel);
		//
		levelSpinner = new Spinner(parent, SWT.BORDER);
		GridData gridDataSpinner = new GridData(GridData.FILL_HORIZONTAL);
		gridDataSpinner.horizontalSpan = 2;
		levelSpinner.setLayoutData(gridDataSpinner);
		levelSpinner.setMinimum(MassShiftDetector.MIN_LEVEL);
		levelSpinner.setMaximum(MassShiftDetector.MAX_LEVEL);
		levelSpinner.setIncrement(MassShiftDetector.INCREMENT_LEVEL);
		levelSpinner.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {

				validateData();
			}
		});
	}

	private void createNoteSection(Composite parent) {

		notesText = new Text(parent, SWT.BORDER);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		notesText.setLayoutData(gridData);
		notesText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {

				validateData();
			}
		});
	}

	private void createDescriptionSection(Composite parent) {

		descriptionText = new Text(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.WRAP);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2;
		descriptionText.setLayoutData(gridData);
		descriptionText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {

				validateData();
			}
		});
	}

	private void validateData() {

		String message = null;
		//
		String c12ChromatogramPath = c12ChromatogramText.getText().trim();
		if(!new File(c12ChromatogramPath).exists()) {
			message = "Please select the C12 chromatogram.";
		} else {
			wizardElements.setC12ChromatogramPath(c12ChromatogramPath);
		}
		//
		String c13ChromatogramPath = c13ChromatogramText.getText().trim();
		if(!new File(c13ChromatogramPath).exists()) {
			message = "Please select the C13 chromatogram.";
		} else {
			wizardElements.setC13ChromatogramPath(c13ChromatogramPath);
		}
		//
		wizardElements.setLevel(levelSpinner.getSelection());
		wizardElements.setNotes(notesText.getText().trim());
		wizardElements.setDescription(descriptionText.getText().trim());
		/*
		 * Updates the status
		 */
		updateStatus(message);
	}
}

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
	private Text referenceChromatogramText;
	private Text isotopeChromatogramText;
	private Spinner startShiftLevelSpinner;
	private Spinner stopShiftLevelSpinner;
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
		createReferenceChromatogramSection(composite);
		createIstopeChromatogramSection(composite);
		createStartShiftLevelSection(composite);
		createStopShiftLevelSection(composite);
		createNoteSection(composite);
		createDescriptionSection(composite);
		//
		validateData();
		//
		setControl(composite);
	}

	private void createReferenceChromatogramSection(Composite parent) {

		Label label = new Label(parent, SWT.NONE);
		label.setText("Reference - Chromatogram");
		GridData gridDataLabel = new GridData(GridData.FILL_HORIZONTAL);
		gridDataLabel.horizontalSpan = 2;
		label.setLayoutData(gridDataLabel);
		//
		referenceChromatogramText = new Text(parent, SWT.BORDER);
		referenceChromatogramText.setText("");
		referenceChromatogramText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//
		Button button = new Button(parent, SWT.PUSH);
		button.setText("Select");
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				IChromatogramWizardElements chromatogramWizardElements = new ChromatogramWizardElements();
				ChromatogramInputEntriesWizard inputWizard = new ChromatogramInputEntriesWizard(chromatogramWizardElements, "Reference - Chromatogram", "Select the reference chromatogram.", PreferenceSupplier.getFilterPathReferenceChromatogram());
				WizardDialog wizardDialog = new WizardDialog(Display.getCurrent().getActiveShell(), inputWizard);
				wizardDialog.create();
				//
				if(wizardDialog.open() == WizardDialog.OK) {
					List<String> selectedChromatograms = chromatogramWizardElements.getSelectedChromatograms();
					if(selectedChromatograms.size() > 0) {
						String selectedChromatogram = chromatogramWizardElements.getSelectedChromatograms().get(0);
						referenceChromatogramText.setText(selectedChromatogram);
						validateData();
						//
						File file = new File(selectedChromatogram);
						if(file.exists()) {
							PreferenceSupplier.setFilterPathReferenceChromatogram(file.getParentFile().toString());
						}
					}
				}
			}
		});
	}

	private void createIstopeChromatogramSection(Composite parent) {

		Label label = new Label(parent, SWT.NONE);
		label.setText("Isotope - Chromatogram");
		GridData gridDataLabel = new GridData(GridData.FILL_HORIZONTAL);
		gridDataLabel.horizontalSpan = 2;
		label.setLayoutData(gridDataLabel);
		//
		isotopeChromatogramText = new Text(parent, SWT.BORDER);
		isotopeChromatogramText.setText("");
		isotopeChromatogramText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//
		Button button = new Button(parent, SWT.PUSH);
		button.setText("Select");
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				IChromatogramWizardElements chromatogramWizardElements = new ChromatogramWizardElements();
				ChromatogramInputEntriesWizard inputWizard = new ChromatogramInputEntriesWizard(chromatogramWizardElements, "Isotope - Chromatogram", "Select the isotope chromatogram.", PreferenceSupplier.getFilterPathIsotopeChromatogram());
				WizardDialog wizardDialog = new WizardDialog(Display.getCurrent().getActiveShell(), inputWizard);
				wizardDialog.create();
				//
				if(wizardDialog.open() == WizardDialog.OK) {
					List<String> selectedChromatograms = chromatogramWizardElements.getSelectedChromatograms();
					if(selectedChromatograms.size() > 0) {
						String selectedChromatogram = chromatogramWizardElements.getSelectedChromatograms().get(0);
						isotopeChromatogramText.setText(selectedChromatogram);
						validateData();
						//
						File file = new File(selectedChromatogram);
						if(file.exists()) {
							PreferenceSupplier.setFilterPathIsotopeChromatogram(file.getParentFile().toString());
						}
					}
				}
			}
		});
	}

	private void createStartShiftLevelSection(Composite parent) {

		Label label = new Label(parent, SWT.NONE);
		label.setText("Start Shift Level:");
		GridData gridDataLabel = new GridData(GridData.FILL_HORIZONTAL);
		gridDataLabel.horizontalSpan = 2;
		label.setLayoutData(gridDataLabel);
		//
		startShiftLevelSpinner = new Spinner(parent, SWT.BORDER);
		GridData gridDataSpinner = new GridData(GridData.FILL_HORIZONTAL);
		gridDataSpinner.horizontalSpan = 2;
		startShiftLevelSpinner.setLayoutData(gridDataSpinner);
		startShiftLevelSpinner.setMinimum(MassShiftDetector.MIN_ISOTOPE_LEVEL);
		startShiftLevelSpinner.setMaximum(MassShiftDetector.MAX_ISOTOPE_LEVEL);
		startShiftLevelSpinner.setIncrement(MassShiftDetector.INCREMENT_ISOTOPE_LEVEL);
		startShiftLevelSpinner.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {

				validateData();
			}
		});
	}

	private void createStopShiftLevelSection(Composite parent) {

		Label label = new Label(parent, SWT.NONE);
		label.setText("Stop Shift Level:");
		GridData gridDataLabel = new GridData(GridData.FILL_HORIZONTAL);
		gridDataLabel.horizontalSpan = 2;
		label.setLayoutData(gridDataLabel);
		//
		stopShiftLevelSpinner = new Spinner(parent, SWT.BORDER);
		GridData gridDataSpinner = new GridData(GridData.FILL_HORIZONTAL);
		gridDataSpinner.horizontalSpan = 2;
		stopShiftLevelSpinner.setLayoutData(gridDataSpinner);
		stopShiftLevelSpinner.setMinimum(MassShiftDetector.MIN_ISOTOPE_LEVEL);
		stopShiftLevelSpinner.setMaximum(MassShiftDetector.MAX_ISOTOPE_LEVEL);
		stopShiftLevelSpinner.setIncrement(MassShiftDetector.INCREMENT_ISOTOPE_LEVEL);
		stopShiftLevelSpinner.addModifyListener(new ModifyListener() {

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
		String referenceChromatogramPath = referenceChromatogramText.getText().trim();
		if(!new File(referenceChromatogramPath).exists()) {
			message = "Please select the reference chromatogram.";
		} else {
			wizardElements.setReferenceChromatogramPath(referenceChromatogramPath);
		}
		//
		String isotopeChromatogramPath = isotopeChromatogramText.getText().trim();
		if(!new File(isotopeChromatogramPath).exists()) {
			message = "Please select the isotope chromatogram.";
		} else {
			wizardElements.setIsotopeChromatogramPath(isotopeChromatogramPath);
		}
		//
		if(startShiftLevelSpinner.getSelection() > stopShiftLevelSpinner.getSelection()) {
			message = "The start shift level must be <= stop shift level.";
		} else {
			wizardElements.setStartShiftLevel(startShiftLevelSpinner.getSelection());
			wizardElements.setStopShiftLevel(stopShiftLevelSpinner.getSelection());
		}
		//
		wizardElements.setNotes(notesText.getText().trim());
		wizardElements.setDescription(descriptionText.getText().trim());
		/*
		 * Updates the status
		 */
		updateStatus(message);
	}
}

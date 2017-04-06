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

import org.eclipse.chemclipse.support.ui.wizards.AbstractExtendedWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.core.MassShiftDetector;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.IProcessorModel;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.IProcessorSettings;

public class PageSettings extends AbstractExtendedWizardPage {

	private IProcessorWizardElements wizardElements;
	//
	private Spinner startShiftLevelSpinner;
	private Spinner stopShiftLevelSpinner;
	private Button normalizeDataCheckBox;
	private Combo ionSelectionStrategyCombo;
	private Text numberHighestIntensityMZText;
	private Button usePeaksCheckBox;
	//
	private String[] ionSelectionStrategies = new String[]{IProcessorSettings.STRATEGY_SELECT_ALL, IProcessorSettings.STRATEGY_ABOVE_MEAN, IProcessorSettings.STRATEGY_ABOVE_MEDIAN, IProcessorSettings.STRATEGY_N_HIGHEST_INTENSITY};

	public PageSettings(IProcessorWizardElements wizardElements) {
		//
		super(PageSettings.class.getName());
		setTitle("Settings");
		setDescription("Select the settings which are used to identify shifts.");
		this.wizardElements = wizardElements;
	}

	@Override
	public boolean canFinish() {

		IProcessorModel processorModel = wizardElements.getProcessorModel();
		IProcessorSettings processorSettings = processorModel.getProcessorSettings();
		//
		if(processorSettings.getStartShiftLevel() < MassShiftDetector.MIN_ISOTOPE_LEVEL || processorSettings.getStartShiftLevel() > MassShiftDetector.MAX_ISOTOPE_LEVEL) {
			return false;
		}
		//
		if(processorSettings.getStopShiftLevel() < MassShiftDetector.MIN_ISOTOPE_LEVEL || processorSettings.getStopShiftLevel() > MassShiftDetector.MAX_ISOTOPE_LEVEL) {
			return false;
		}
		//
		if(processorSettings.getStartShiftLevel() > processorSettings.getStopShiftLevel()) {
			return false;
		}
		//
		if(!isValidStrategy(processorSettings.getIonSelectionStrategy())) {
			return false;
		}
		//
		if(processorSettings.getIonSelectionStrategy().equals(IProcessorSettings.STRATEGY_N_HIGHEST_INTENSITY)) {
			if(processorSettings.getNumberHighestIntensityMZ() < IProcessorSettings.MIN_N_HIGHEST_INTENSITY || processorSettings.getNumberHighestIntensityMZ() > IProcessorSettings.MAX_N_HIGHEST_INTENSITY) {
				return false;
			}
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
			IProcessorSettings processorSettings = processorModel.getProcessorSettings();
			startShiftLevelSpinner.setSelection(processorSettings.getStartShiftLevel());
			stopShiftLevelSpinner.setSelection(processorSettings.getStopShiftLevel());
			normalizeDataCheckBox.setSelection(processorSettings.isNormalizeData());
			ionSelectionStrategyCombo.setText(processorSettings.getIonSelectionStrategy());
			numberHighestIntensityMZText.setText(Integer.toString(processorSettings.getNumberHighestIntensityMZ()));
			usePeaksCheckBox.setSelection(processorSettings.isUsePeaks());
			//
			if(processorSettings.getIonSelectionStrategy().equals(IProcessorSettings.STRATEGY_N_HIGHEST_INTENSITY)) {
				numberHighestIntensityMZText.setEnabled(true);
			} else {
				numberHighestIntensityMZText.setEnabled(false);
			}
			//
			validateData();
		}
	}

	@Override
	public void createControl(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		//
		createStartShiftLevelSection(composite);
		createStopShiftLevelSection(composite);
		createNormalizeDataSection(composite);
		createIonSelectionStrategySection(composite);
		createNumberHighestIntensitySection(composite);
		createUsePeaksSection(composite);
		//
		validateData();
		//
		setControl(composite);
	}

	private void createStartShiftLevelSection(Composite parent) {

		Label label = new Label(parent, SWT.NONE);
		label.setText("Start Shift Level:");
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//
		startShiftLevelSpinner = new Spinner(parent, SWT.BORDER);
		startShiftLevelSpinner.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
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
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//
		stopShiftLevelSpinner = new Spinner(parent, SWT.BORDER);
		stopShiftLevelSpinner.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		stopShiftLevelSpinner.setMinimum(MassShiftDetector.MIN_ISOTOPE_LEVEL);
		stopShiftLevelSpinner.setMaximum(MassShiftDetector.MAX_ISOTOPE_LEVEL);
		stopShiftLevelSpinner.setIncrement(MassShiftDetector.INCREMENT_ISOTOPE_LEVEL);
		stopShiftLevelSpinner.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {

				validateData();
			}
		});
	}

	private void createNormalizeDataSection(Composite parent) {

		normalizeDataCheckBox = new Button(parent, SWT.CHECK);
		normalizeDataCheckBox.setText("Normalize Data");
		normalizeDataCheckBox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		normalizeDataCheckBox.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				IProcessorModel processorModel = wizardElements.getProcessorModel();
				IProcessorSettings processorSettings = processorModel.getProcessorSettings();
				processorSettings.setNormalizeData(normalizeDataCheckBox.getSelection());
				validateData();
			}
		});
	}

	private void createIonSelectionStrategySection(Composite parent) {

		Label label = new Label(parent, SWT.NONE);
		label.setText("Ion Selection Strategy:");
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//
		ionSelectionStrategyCombo = new Combo(parent, SWT.READ_ONLY);
		ionSelectionStrategyCombo.setItems(ionSelectionStrategies);
		ionSelectionStrategyCombo.select(0);
		ionSelectionStrategyCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		ionSelectionStrategyCombo.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				if(ionSelectionStrategyCombo.getText().trim().equals(IProcessorSettings.STRATEGY_N_HIGHEST_INTENSITY)) {
					numberHighestIntensityMZText.setEnabled(true);
				} else {
					numberHighestIntensityMZText.setEnabled(false);
				}
				validateData();
			}
		});
	}

	private void createNumberHighestIntensitySection(Composite parent) {

		Label label = new Label(parent, SWT.NONE);
		label.setText("Number n Highest Intesity m/z:");
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//
		numberHighestIntensityMZText = new Text(parent, SWT.BORDER);
		numberHighestIntensityMZText.setText("");
		numberHighestIntensityMZText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		numberHighestIntensityMZText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {

				validateData();
			}
		});
	}

	private void createUsePeaksSection(Composite parent) {

		usePeaksCheckBox = new Button(parent, SWT.CHECK);
		usePeaksCheckBox.setText("Use Peaks");
		usePeaksCheckBox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		usePeaksCheckBox.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				IProcessorModel processorModel = wizardElements.getProcessorModel();
				IProcessorSettings processorSettings = processorModel.getProcessorSettings();
				processorSettings.setUsePeaks(usePeaksCheckBox.getSelection());
				validateData();
			}
		});
	}

	private void validateData() {

		String message = null;
		IProcessorModel processorModel = wizardElements.getProcessorModel();
		IProcessorSettings processorSettings = processorModel.getProcessorSettings();
		//
		if(startShiftLevelSpinner.getSelection() > stopShiftLevelSpinner.getSelection()) {
			message = "The start shift level must be <= stop shift level.";
		} else {
			processorSettings.setStartShiftLevel(startShiftLevelSpinner.getSelection());
			processorSettings.setStopShiftLevel(stopShiftLevelSpinner.getSelection());
		}
		//
		String ionSelectionStrategy = ionSelectionStrategyCombo.getText().trim();
		if(!isValidStrategy(ionSelectionStrategy)) {
			message = "Please select a valid ion selection strategy.";
		} else {
			processorSettings.setIonSelectionStrategy(ionSelectionStrategy);
		}
		//
		if(ionSelectionStrategy.equals(IProcessorSettings.STRATEGY_N_HIGHEST_INTENSITY)) {
			try {
				int numberHighestIntensityMZ = Integer.parseInt(numberHighestIntensityMZText.getText().trim());
				if(numberHighestIntensityMZ < IProcessorSettings.MIN_N_HIGHEST_INTENSITY || numberHighestIntensityMZ > IProcessorSettings.MAX_N_HIGHEST_INTENSITY) {
					message = "Please select a valid number of n highest intensity m/z values (" + IProcessorSettings.MIN_N_HIGHEST_INTENSITY + " - " + IProcessorSettings.MAX_N_HIGHEST_INTENSITY + ").";
				} else {
					processorSettings.setNumberHighestIntensityMZ(numberHighestIntensityMZ);
				}
			} catch(NumberFormatException e) {
				message = "Please select an integer value for the number of n highest intensity m/z values.";
			}
		}
		/*
		 * Updates the status
		 */
		updateStatus(message);
	}

	private boolean isValidStrategy(String ionSelectionStrategy) {

		for(String strategy : ionSelectionStrategies) {
			if(strategy.equals(ionSelectionStrategy)) {
				return true;
			}
		}
		return false;
	}
}

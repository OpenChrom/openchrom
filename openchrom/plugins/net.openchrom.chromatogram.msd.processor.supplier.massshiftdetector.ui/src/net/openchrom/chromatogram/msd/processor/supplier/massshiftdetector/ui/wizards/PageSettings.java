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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.core.MassShiftDetector;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.IProcessorModel;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.IProcessorSettings;

public class PageSettings extends AbstractExtendedWizardPage {

	private IProcessorWizardElements wizardElements;
	//
	private Spinner startShiftLevelSpinner;
	private Spinner stopShiftLevelSpinner;

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
		if(processorSettings.getStartShiftLevel() < MassShiftDetector.MIN_ISOTOPE_LEVEL || processorSettings.getStartShiftLevel() > MassShiftDetector.MAX_ISOTOPE_LEVEL) {
			return false;
		}
		//
		if(processorSettings.getStartShiftLevel() < MassShiftDetector.MIN_ISOTOPE_LEVEL || processorSettings.getStartShiftLevel() > MassShiftDetector.MAX_ISOTOPE_LEVEL) {
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
			IProcessorSettings processorSettings = processorModel.getProcessorSettings();
			startShiftLevelSpinner.setSelection(processorSettings.getStartShiftLevel());
			stopShiftLevelSpinner.setSelection(processorSettings.getStopShiftLevel());
			validateData();
		}
	}

	@Override
	public void createControl(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		//
		createStartShiftLevelSection(composite);
		createStopShiftLevelSection(composite);
		//
		validateData();
		//
		setControl(composite);
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
		/*
		 * Updates the status
		 */
		updateStatus(message);
	}
}

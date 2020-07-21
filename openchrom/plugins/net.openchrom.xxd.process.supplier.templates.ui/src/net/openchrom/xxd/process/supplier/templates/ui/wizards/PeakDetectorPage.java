/*******************************************************************************
 * Copyright (c) 2019, 2020 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.wizards;

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import net.openchrom.xxd.process.supplier.templates.ui.swt.peaks.TemplatePeakDetectorUI;

public class PeakDetectorPage extends WizardPage {

	private ProcessDetectorSettings peakProcessSettings;

	public PeakDetectorPage(String pageName, ProcessDetectorSettings processSettings) {

		super(pageName);
		setTitle("Modify/Add Peaks");
		setDescription(getDescription(processSettings));
		setErrorMessage(null);
		this.peakProcessSettings = processSettings;
	}

	@Override
	public void setVisible(boolean visible) {

		super.setVisible(visible);
		/*
		 * Remove the focus from the "Finish" button. It prevents that the user
		 * accidentally press "Enter" and thus closes the dialog.
		 */
		Shell shell = getShell();
		shell.getDisplay().asyncExec(() -> shell.setDefaultButton(null));
	}

	@Override
	public void createControl(Composite parent) {

		TemplatePeakDetectorUI control = new TemplatePeakDetectorUI(parent, SWT.NONE);
		control.setInput(peakProcessSettings);
		setControl(control);
	}

	private String getDescription(ProcessDetectorSettings processSettings) {

		if(processSettings != null) {
			IChromatogram<?> chromatogram = processSettings.getChromatogram();
			if(chromatogram != null) {
				return chromatogram.getName() + " / " + chromatogram.getDataName();
			}
		}
		return "Template Peak Detector";
	}
}

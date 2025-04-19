/*******************************************************************************
 * Copyright (c) 2019, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 * Christoph Läubrich - maximize shell
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.wizards;

import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class PeakDetectorSupport {

	public static final String DESCRIPTION = "Template Peak Detector UI";

	@SuppressWarnings("rawtypes")
	public void addPeaks(Shell shell, ProcessDetectorSettings processSettings) {

		PeakDetectorWizard wizard = new PeakDetectorWizard(processSettings);
		WizardDialog wizardDialog = new WizardDialog(shell, wizard) {

			@Override
			protected void constrainShellSize() {

				super.constrainShellSize();
				getShell().setMaximized(true);
			}

			@Override
			protected void createButtonsForButtonBar(Composite parent) {

				super.createButtonsForButtonBar(parent);
				getButton(CANCEL).setEnabled(false);
				getButton(IDialogConstants.FINISH_ID).setText(IDialogConstants.OK_LABEL);
			}
		};
		/*
		 * Processing
		 */
		try {
			wizardDialog.setMinimumPageSize(PeakDetectorWizard.DEFAULT_WIDTH, PeakDetectorWizard.DEFAULT_HEIGHT);
			wizardDialog.create();
			wizardDialog.getShell().setBackgroundMode(SWT.INHERIT_DEFAULT);
			//
			IProcessingInfo processingInfo = processSettings.getProcessingInfo();
			if(Window.OK == wizardDialog.open()) {
				processingInfo.addInfoMessage(DESCRIPTION, "Successfully modified/added the peaks.");
			} else {
				processingInfo.addWarnMessage(DESCRIPTION, "Cancel has been pressed. No peaks added.");
			}
		} finally {
			wizard.dispose();
		}
	}
}
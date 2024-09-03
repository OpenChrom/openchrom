/*******************************************************************************
 * Copyright (c) 2020, 2024 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.wizards;

import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class PeakReviewSupport {

	public static final String DESCRIPTION = "Template Review UI";

	public void addSettings(Shell shell, ProcessReviewSettings processSettings) {

		PeakReviewWizard wizard = new PeakReviewWizard(processSettings);
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
			wizardDialog.setMinimumPageSize(PeakReviewWizard.DEFAULT_WIDTH, PeakReviewWizard.DEFAULT_HEIGHT);
			wizardDialog.create();
			wizardDialog.getShell().setBackgroundMode(SWT.INHERIT_DEFAULT);
			//
			IProcessingInfo<?> processingInfo = processSettings.getProcessingInfo();
			if(Window.OK == wizardDialog.open()) {
				processingInfo.addInfoMessage(DESCRIPTION, "Successfully reviewed the peaks.");
			} else {
				processingInfo.addWarnMessage(DESCRIPTION, "Cancel has been pressed. No peaks reviewed.");
			}
		} finally {
			wizard.dispose();
		}
	}
}
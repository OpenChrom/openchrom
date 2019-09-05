/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
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

import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

public class PeakDetectorSupport {

	public static final String DESCRIPTION = "Template Peak Detector UI";

	@SuppressWarnings("rawtypes")
	public void addPeaks(Shell shell, PeakProcessSettings processSettings) {

		PeakDetectorWizard wizard = new PeakDetectorWizard(processSettings);
		WizardDialog wizardDialog = new WizardDialog(shell, wizard);
		wizardDialog.setMinimumPageSize(PeakDetectorWizard.DEFAULT_WIDTH, PeakDetectorWizard.DEFAULT_HEIGHT);
		//
		IProcessingInfo processingInfo = processSettings.getProcessingInfo();
		int status = wizardDialog.open();
		if(status == Dialog.OK) {
			processingInfo.addInfoMessage(DESCRIPTION, "Successfully modified/added the peak(s).");
		} else if(status == Dialog.CANCEL) {
			processingInfo.addWarnMessage(DESCRIPTION, "Cancel has been pressed. No peak(s) added.");
		} else {
			processingInfo.addErrorMessage(DESCRIPTION, "Something went wrong to add the peak(s).");
		}
	}
}

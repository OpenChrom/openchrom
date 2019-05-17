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

import java.util.List;

import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

public class PeakDetectorSupport {

	private static final String DESCRIPTION = "Manual Peak Detector UI";

	public void addPeaks(List<IPeak> peaks, Shell shell, IProcessingInfo processingInfo) {

		PeaksWizard wizard = new PeaksWizard(peaks);
		WizardDialog dialog = new WizardDialog(shell, wizard);
		if(dialog.open() == Dialog.OK) {
			processingInfo.addErrorMessage(DESCRIPTION, "Successfully modified/added the peaks.");
		} else {
			processingInfo.addErrorMessage(DESCRIPTION, "Something went wrong to add the peaks.");
		}
	}
}

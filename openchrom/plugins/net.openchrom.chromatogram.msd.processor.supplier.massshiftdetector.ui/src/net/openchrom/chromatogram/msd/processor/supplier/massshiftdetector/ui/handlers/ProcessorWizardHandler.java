/*******************************************************************************
 * Copyright (c) 2017, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.wizards.WizardProcessor;

public class ProcessorWizardHandler {

	@Execute
	public void execute() {

		Display display = Display.getDefault();
		Shell shell = display.getActiveShell();
		//
		WizardDialog wizardDialog = new WizardDialog(shell, new WizardProcessor());
		wizardDialog.open();
	}
}

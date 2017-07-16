/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.processor.supplier.tracecompare.ui.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;

import net.openchrom.xxd.processor.supplier.tracecompare.ui.wizards.WizardProcessor;

public class ProcessorWizardHandler {

	@Execute
	public void execute() {

		WizardDialog wizardDialog = new WizardDialog(Display.getDefault().getActiveShell(), new WizardProcessor());
		wizardDialog.open();
	}
}

/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
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

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.process.supplier.templates.ui.swt.peaks.TemplatePeakReviewUI;

public class PeakReviewPage extends WizardPage {

	private ProcessReviewSettings processSettings;

	public PeakReviewPage(String pageName, ProcessReviewSettings processSettings) {
		super(pageName);
		setTitle("Review Peaks");
		setDescription("Template Peak Review");
		setErrorMessage(null);
		this.processSettings = processSettings;
	}

	@Override
	public void createControl(Composite parent) {

		TemplatePeakReviewUI control = new TemplatePeakReviewUI(parent, SWT.NONE);
		control.setInput(processSettings);
		setControl(control);
	}
}

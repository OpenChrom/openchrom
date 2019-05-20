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

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.process.supplier.templates.ui.swt.TemplatePeakDetectorUI;

public class PeakDetectorPage extends WizardPage {

	private PeakProcessSettings peakProcessSettings;

	public PeakDetectorPage(String pageName, PeakProcessSettings processSettings) {
		super(pageName);
		setTitle("Modify/Add Peaks");
		setDescription("Template peak modifier/detector");
		setErrorMessage(null);
		this.peakProcessSettings = processSettings;
	}

	@Override
	public void createControl(Composite parent) {

		TemplatePeakDetectorUI control = new TemplatePeakDetectorUI(parent, SWT.NONE);
		control.setPeakProcessSettings(peakProcessSettings);
		setControl(control);
	}
}

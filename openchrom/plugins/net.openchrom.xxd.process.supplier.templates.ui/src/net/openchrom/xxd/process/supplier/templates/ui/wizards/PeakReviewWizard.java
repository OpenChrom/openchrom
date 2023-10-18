/*******************************************************************************
 * Copyright (c) 2020, 2023 Lablicate GmbH.
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

import org.eclipse.jface.wizard.Wizard;

public class PeakReviewWizard extends Wizard {

	public static final int DEFAULT_WIDTH = 500;
	public static final int DEFAULT_HEIGHT = 650;
	//
	private ProcessReviewSettings processSettings;

	public PeakReviewWizard(ProcessReviewSettings processSettings) {

		setNeedsProgressMonitor(false);
		setWindowTitle(PeakReviewSupport.DESCRIPTION);
		//
		this.processSettings = processSettings;
	}

	@Override
	public void addPages() {

		addPage(new PeakReviewPage("Peak Review", processSettings));
	}

	@Override
	public boolean performFinish() {

		return true;
	}
}

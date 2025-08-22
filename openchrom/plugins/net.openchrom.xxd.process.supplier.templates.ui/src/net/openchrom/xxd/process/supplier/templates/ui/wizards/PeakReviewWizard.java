/*******************************************************************************
 * Copyright (c) 2020, 2025 Lablicate GmbH.
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
package net.openchrom.xxd.process.supplier.templates.ui.wizards;

import org.eclipse.jface.wizard.Wizard;

public class PeakReviewWizard extends Wizard {

	public static final int DEFAULT_WIDTH = 500;
	public static final int DEFAULT_HEIGHT = 650;

	private ProcessReviewSettings processSettings;

	public PeakReviewWizard(ProcessReviewSettings processSettings) {

		setNeedsProgressMonitor(false);
		setWindowTitle(PeakReviewSupport.DESCRIPTION);

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
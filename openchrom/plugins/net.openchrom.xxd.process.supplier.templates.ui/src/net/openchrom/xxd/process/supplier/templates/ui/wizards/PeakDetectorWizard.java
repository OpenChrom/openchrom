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
 * Christoph Läubrich - no need for progressmonitor
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.wizards;

import org.eclipse.jface.wizard.Wizard;

public class PeakDetectorWizard extends Wizard {

	public static final int DEFAULT_WIDTH = 500;
	public static final int DEFAULT_HEIGHT = 650;

	private ProcessDetectorSettings processSettings;

	public PeakDetectorWizard(ProcessDetectorSettings processSettings) {

		setNeedsProgressMonitor(false);
		setWindowTitle(PeakDetectorSupport.DESCRIPTION);

		this.processSettings = processSettings;
	}

	@Override
	public void addPages() {

		addPage(new PeakDetectorPage("Peak Detector", processSettings));
	}

	@Override
	public boolean performFinish() {

		return true;
	}
}
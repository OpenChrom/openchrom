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
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.wizards;

import org.eclipse.swt.widgets.Shell;

public class WizardRunnable implements Runnable {

	private ProcessDetectorSettings processSettings;

	public WizardRunnable(ProcessDetectorSettings processSettings) {
		this.processSettings = processSettings;
	}

	@Override
	public void run() {

		/*
		 * Create a new shell and set
		 * the size to 0 cause only the wizard
		 * will be shown.
		 */
		Shell shell = new Shell();
		shell.setSize(0, 0);
		shell.open();
		//
		PeakDetectorSupport peakDetectorSupport = new PeakDetectorSupport();
		peakDetectorSupport.addPeaks(shell, processSettings);
		shell.close();
	}
}

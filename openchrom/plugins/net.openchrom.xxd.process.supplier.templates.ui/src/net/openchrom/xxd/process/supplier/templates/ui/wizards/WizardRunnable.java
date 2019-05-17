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
import org.eclipse.swt.widgets.Shell;

public class WizardRunnable implements Runnable {

	private IProcessingInfo processingInfo;
	private List<IPeak> peaks;

	public WizardRunnable(IProcessingInfo processingInfo, List<IPeak> peaks) {
		this.processingInfo = processingInfo;
		this.peaks = peaks;
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
		PeakDetectorSupport peakDetectorSupport = new PeakDetectorSupport();
		peakDetectorSupport.addPeaks(peaks, shell, processingInfo);
		shell.close();
	}
}

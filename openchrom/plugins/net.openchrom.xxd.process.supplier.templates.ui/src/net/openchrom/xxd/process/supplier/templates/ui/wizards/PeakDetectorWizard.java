/*******************************************************************************
 * Copyright (c) 2019, 2020 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Christoph Läubrich - no need for progressmonitor
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.wizards;

import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.jface.wizard.Wizard;

@SuppressWarnings("rawtypes")
public class PeakDetectorWizard extends Wizard {

	public static final int DEFAULT_WIDTH = 500;
	public static final int DEFAULT_HEIGHT = 650;
	//
	private PeakDetectorPage page;
	private ProcessDetectorSettings processSettings;
	//
	private int startRetentionTime;
	private int stopRetentionTime;

	public PeakDetectorWizard(ProcessDetectorSettings processSettings) {
		setNeedsProgressMonitor(false);
		setWindowTitle(PeakDetectorSupport.DESCRIPTION);
		//
		this.processSettings = processSettings;
		IChromatogramSelection chromatogramSelection = processSettings.getChromatogramSelection();
		startRetentionTime = chromatogramSelection.getStartRetentionTime();
		stopRetentionTime = chromatogramSelection.getStopRetentionTime();
	}

	@Override
	public void addPages() {

		page = new PeakDetectorPage("Peak Detector", processSettings);
		addPage(page);
	}

	@Override
	public boolean performCancel() {

		restoreChromatogramSelection();
		return super.performCancel();
	}

	@Override
	public boolean performFinish() {

		restoreChromatogramSelection();
		return true;
	}

	private void restoreChromatogramSelection() {

		IChromatogramSelection chromatogramSelection = processSettings.getChromatogramSelection();
		chromatogramSelection.setRangeRetentionTime(startRetentionTime, stopRetentionTime);
	}
}

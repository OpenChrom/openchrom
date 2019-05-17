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

import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.jface.wizard.Wizard;

@SuppressWarnings("rawtypes")
public class PeaksWizard extends Wizard {

	public static final int DEFAULT_WIDTH = 500;
	public static final int DEFAULT_HEIGHT = 650;
	//
	private PeaksPage page;
	private ProcessSettings processSettings;
	//
	private int startRetentionTime;
	private int stopRetentionTime;

	public PeaksWizard(ProcessSettings processSettings) {
		setNeedsProgressMonitor(true);
		setWindowTitle(PeakDetectorSupport.DESCRIPTION);
		//
		this.processSettings = processSettings;
		IChromatogramSelection chromatogramSelection = processSettings.getChromatogramSelection();
		startRetentionTime = chromatogramSelection.getStartRetentionTime();
		stopRetentionTime = chromatogramSelection.getStopRetentionTime();
	}

	@Override
	public void addPages() {

		page = new PeaksPage("Peaks", processSettings);
		addPage(page);
	}

	@Override
	public boolean performFinish() {

		IChromatogramSelection chromatogramSelection = processSettings.getChromatogramSelection();
		chromatogramSelection.setRangeRetentionTime(startRetentionTime, stopRetentionTime);
		return true;
	}
}

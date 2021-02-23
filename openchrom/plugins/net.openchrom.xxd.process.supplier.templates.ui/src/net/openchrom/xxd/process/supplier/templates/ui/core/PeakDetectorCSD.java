/*******************************************************************************
 * Copyright (c) 2019, 2021 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.core;

import org.eclipse.chemclipse.chromatogram.csd.peak.detector.core.AbstractPeakDetectorCSD;
import org.eclipse.chemclipse.chromatogram.csd.peak.detector.settings.IPeakDetectorSettingsCSD;
import org.eclipse.chemclipse.csd.model.core.selection.IChromatogramSelectionCSD;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.chemclipse.support.ui.workbench.DisplayUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Shell;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.settings.PeakDetectorSettings;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.PeakDetectorSupport;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.ProcessDetectorSettings;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.WizardRunnable;

public class PeakDetectorCSD<P extends IPeak, C extends IChromatogram<P>, R> extends AbstractPeakDetectorCSD<P, C, R> {

	@Override
	public IProcessingInfo<R> detect(IChromatogramSelectionCSD chromatogramSelection, IPeakDetectorSettingsCSD peakDetectorSettings, IProgressMonitor monitor) {

		IProcessingInfo<R> processingInfo = new ProcessingInfo<R>();
		if(peakDetectorSettings instanceof PeakDetectorSettings) {
			PeakDetectorSettings settings = (PeakDetectorSettings)peakDetectorSettings;
			ProcessDetectorSettings processSettings = new ProcessDetectorSettings(processingInfo, chromatogramSelection.getChromatogram(), settings);
			Shell shell = DisplayUtils.getShell();
			if(shell != null) {
				PeakDetectorSupport peakDetectorSupport = new PeakDetectorSupport();
				peakDetectorSupport.addPeaks(shell, processSettings);
			} else {
				WizardRunnable wizardRunnable = new WizardRunnable(processSettings);
				DisplayUtils.getDisplay().syncExec(wizardRunnable);
			}
		}
		return processingInfo;
	}

	@Override
	public IProcessingInfo<R> detect(IChromatogramSelectionCSD chromatogramSelection, IProgressMonitor monitor) {

		PeakDetectorSettings settings = PreferenceSupplier.getPeakDetectorSettingsCSD();
		return detect(chromatogramSelection, settings, monitor);
	}
}

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
package net.openchrom.xxd.process.supplier.templates.ui.core;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.chromatogram.csd.peak.detector.core.AbstractPeakDetectorCSD;
import org.eclipse.chemclipse.chromatogram.csd.peak.detector.settings.IPeakDetectorSettingsCSD;
import org.eclipse.chemclipse.csd.model.core.selection.IChromatogramSelectionCSD;
import org.eclipse.chemclipse.model.core.PeakType;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.chemclipse.support.ui.workbench.DisplayUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Shell;

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.settings.PeakDetectorDirectSettings;
import net.openchrom.xxd.process.supplier.templates.settings.PeakDetectorSettings;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.PeakDetectorSupport;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.ProcessDetectorSettings;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.WizardRunnable;

@SuppressWarnings("rawtypes")
public class PeakDetectorDirectCSD extends AbstractPeakDetectorCSD implements IPeakDetectorDirect {

	@Override
	public IProcessingInfo detect(IChromatogramSelectionCSD chromatogramSelection, IPeakDetectorSettingsCSD peakDetectorSettings, IProgressMonitor monitor) {

		IProcessingInfo processingInfo = new ProcessingInfo();
		if(peakDetectorSettings instanceof PeakDetectorDirectSettings) {
			PeakDetectorDirectSettings settingsDirect = (PeakDetectorDirectSettings)peakDetectorSettings;
			/*
			 * Create the template from the current selection.
			 * Work with an offset, if the start or stop of the chromatogram is selected.
			 */
			int offset = 100;
			int startRetentionTime = getStartRetentionTime(chromatogramSelection, offset);
			int stopRetentionTime = getStopRetentionTime(chromatogramSelection, offset);
			//
			List<DetectorSetting> detectorSettings = new ArrayList<>();
			DetectorSetting detectorSetting = new DetectorSetting();
			detectorSetting.setStartRetentionTime(startRetentionTime);
			detectorSetting.setStopRetentionTime(stopRetentionTime);
			detectorSetting.setDetectorType(settingsDirect.isDetectorTypeVV() ? PeakType.VV : PeakType.BB);
			detectorSetting.setTraces("");
			detectorSetting.setOptimizeRange(settingsDirect.isOptimizeRange());
			detectorSettings.add(detectorSetting);
			//
			PeakDetectorSettings settings = new PeakDetectorSettings();
			settings.setDetectorSettings(detectorSettings);
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
	public IProcessingInfo detect(IChromatogramSelectionCSD chromatogramSelection, IProgressMonitor monitor) {

		PeakDetectorSettings settings = PreferenceSupplier.getPeakDetectorSettingsCSD();
		return detect(chromatogramSelection, settings, monitor);
	}
}

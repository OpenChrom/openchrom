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
import java.util.concurrent.ExecutionException;

import org.eclipse.chemclipse.chromatogram.msd.peak.detector.core.AbstractPeakDetectorMSD;
import org.eclipse.chemclipse.chromatogram.msd.peak.detector.settings.IPeakDetectorSettingsMSD;
import org.eclipse.chemclipse.model.core.PeakType;
import org.eclipse.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;
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

@SuppressWarnings("rawtypes")
public class PeakDetectorDirectMSD extends AbstractPeakDetectorMSD {

	@Override
	public IProcessingInfo detect(IChromatogramSelectionMSD chromatogramSelection, IPeakDetectorSettingsMSD peakDetectorSettings, IProgressMonitor monitor) {

		IProcessingInfo processingInfo = new ProcessingInfo();
		if(peakDetectorSettings instanceof PeakDetectorDirectSettings) {
			PeakDetectorDirectSettings settingsDirect = (PeakDetectorDirectSettings)peakDetectorSettings;
			/*
			 * Create the template from the current selection.
			 */
			List<DetectorSetting> detectorSettings = new ArrayList<>();
			DetectorSetting detectorSetting = new DetectorSetting();
			detectorSetting.setStartRetentionTime(chromatogramSelection.getStartRetentionTime());
			detectorSetting.setStopRetentionTime(chromatogramSelection.getStopRetentionTime());
			detectorSetting.setDetectorType(PeakType.VV);
			detectorSetting.setTraces(settingsDirect.getTraces());
			detectorSetting.setOptimizeRange(true);
			detectorSettings.add(detectorSetting);
			//
			PeakDetectorSettings settings = new PeakDetectorSettings();
			settings.setDetectorSettings(detectorSettings);
			ProcessDetectorSettings processSettings = new ProcessDetectorSettings(processingInfo, chromatogramSelection.getChromatogram(), settings);
			try {
				DisplayUtils.executeInUserInterfaceThread(new Runnable() {

					@Override
					public void run() {

						Shell shell = DisplayUtils.getShell();
						PeakDetectorSupport peakDetectorSupport = new PeakDetectorSupport();
						peakDetectorSupport.addPeaks(shell, processSettings);
					}
				});
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			} catch(ExecutionException e) {
				processingInfo.addErrorMessage("PeakDetectorDirectMSD", "Execution failed", e);
			}
		}
		return processingInfo;
	}

	@Override
	public IProcessingInfo detect(IChromatogramSelectionMSD chromatogramSelection, IProgressMonitor monitor) {

		PeakDetectorDirectSettings settings = PreferenceSupplier.getPeakDetectorSettingsDirectMSD();
		return detect(chromatogramSelection, settings, monitor);
	}
}

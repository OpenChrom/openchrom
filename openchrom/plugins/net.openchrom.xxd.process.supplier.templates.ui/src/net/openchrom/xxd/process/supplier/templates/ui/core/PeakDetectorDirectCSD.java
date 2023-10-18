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
package net.openchrom.xxd.process.supplier.templates.ui.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.eclipse.chemclipse.chromatogram.csd.peak.detector.core.AbstractPeakDetectorCSD;
import org.eclipse.chemclipse.chromatogram.csd.peak.detector.settings.IPeakDetectorSettingsCSD;
import org.eclipse.chemclipse.csd.model.core.selection.IChromatogramSelectionCSD;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.chemclipse.support.ui.workbench.DisplayUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Shell;

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;
import net.openchrom.xxd.process.supplier.templates.model.DetectorType;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.settings.PeakDetectorDirectSettings;
import net.openchrom.xxd.process.supplier.templates.settings.PeakDetectorSettings;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.PeakDetectorSupport;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.ProcessDetectorSettings;

public class PeakDetectorDirectCSD<P extends IPeak, C extends IChromatogram<P>, R> extends AbstractPeakDetectorCSD<P, C, R> implements IPeakDetectorDirect {

	@Override
	public IProcessingInfo<R> detect(IChromatogramSelectionCSD chromatogramSelection, IPeakDetectorSettingsCSD peakDetectorSettings, IProgressMonitor monitor) {

		IProcessingInfo<R> processingInfo = new ProcessingInfo<R>();
		if(peakDetectorSettings instanceof PeakDetectorDirectSettings settingsDirect) {
			/*
			 * Create the template from the current selection.
			 * Work with an offset, if the start or stop of the chromatogram is selected.
			 */
			int offset = 100;
			IChromatogram<? extends IPeak> chromatogram = chromatogramSelection.getChromatogram();
			List<DetectorSetting> detectorSettings = new ArrayList<>();
			/*
			 * Peaks
			 */
			if(settingsDirect.isUseExistingPeaks()) {
				for(IPeak peak : chromatogram.getPeaks(chromatogramSelection)) {
					/*
					 * Retention Time
					 */
					IPeakModel peakModel = peak.getPeakModel();
					int startRetentionTime = peakModel.getStartRetentionTime() - offset;
					int stopRetentionTime = peakModel.getStopRetentionTime() + offset;
					//
					DetectorSetting detectorSetting = new DetectorSetting();
					detectorSetting.setStartRetentionTime(startRetentionTime);
					detectorSetting.setStopRetentionTime(stopRetentionTime);
					detectorSetting.setPeakType(DetectorType.translate(settingsDirect.getDetectorType()));
					detectorSetting.setTraces("");
					detectorSetting.setOptimizeRange(settingsDirect.isOptimizeRange());
					detectorSettings.add(detectorSetting);
				}
			}
			/*
			 * Detector Selected Range
			 */
			if(settingsDirect.isUseSelectedRange()) {
				/*
				 * Retention Time
				 */
				int startRetentionTime = getStartRetentionTime(chromatogramSelection, offset);
				int stopRetentionTime = getStopRetentionTime(chromatogramSelection, offset);
				//
				DetectorSetting detectorSetting = new DetectorSetting();
				detectorSetting.setStartRetentionTime(startRetentionTime);
				detectorSetting.setStopRetentionTime(stopRetentionTime);
				detectorSetting.setPeakType(DetectorType.translate(settingsDirect.getDetectorType()));
				detectorSetting.setTraces("");
				detectorSetting.setOptimizeRange(settingsDirect.isOptimizeRange());
				detectorSettings.add(detectorSetting);
			}
			/*
			 * Default: Complete Range
			 */
			if(detectorSettings.isEmpty()) {
				/*
				 * Retention Time
				 */
				int startRetentionTime = chromatogram.getStartRetentionTime() + 2000;
				int stopRetentionTime = chromatogram.getStopRetentionTime() - 2000;
				//
				DetectorSetting detectorSetting = new DetectorSetting();
				detectorSetting.setStartRetentionTime(startRetentionTime);
				detectorSetting.setStopRetentionTime(stopRetentionTime);
				detectorSetting.setPeakType(DetectorType.translate(settingsDirect.getDetectorType()));
				detectorSetting.setTraces("");
				detectorSetting.setOptimizeRange(settingsDirect.isOptimizeRange());
				detectorSettings.add(detectorSetting);
			}
			//
			PeakDetectorSettings settings = new PeakDetectorSettings();
			settings.setDetectorSettings(detectorSettings);
			ProcessDetectorSettings processSettings = new ProcessDetectorSettings(processingInfo, chromatogram, settings);
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
	public IProcessingInfo<R> detect(IChromatogramSelectionCSD chromatogramSelection, IProgressMonitor monitor) {

		PeakDetectorSettings settings = PreferenceSupplier.getPeakDetectorSettingsCSD();
		return detect(chromatogramSelection, settings, monitor);
	}
}

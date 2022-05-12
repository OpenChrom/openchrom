/*******************************************************************************
 * Copyright (c) 2019, 2022 Lablicate GmbH.
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

import java.util.List;
import java.util.concurrent.ExecutionException;

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

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.settings.PeakDetectorSettings;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.PeakDetectorSupport;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.ProcessDetectorSettings;
import net.openchrom.xxd.process.supplier.templates.util.ChromatogramValidator;

public class PeakDetectorCSD<P extends IPeak, C extends IChromatogram<P>, R> extends AbstractPeakDetectorCSD<P, C, R> {

	private static final String DESCRIPTION = "PeakDetectorCSD";

	@Override
	public IProcessingInfo<R> detect(IChromatogramSelectionCSD chromatogramSelection, IPeakDetectorSettingsCSD peakDetectorSettings, IProgressMonitor monitor) {

		IProcessingInfo<R> processingInfo = new ProcessingInfo<R>();
		if(peakDetectorSettings instanceof PeakDetectorSettings) {
			/*
			 * RI will be adjusted to retention time (minutes).
			 */
			PeakDetectorSettings settings = (PeakDetectorSettings)peakDetectorSettings;
			IChromatogram<? extends IPeak> chromatogram = chromatogramSelection.getChromatogram();
			List<DetectorSetting> detectorSettings = ChromatogramValidator.filterValidDetectorSettings(chromatogram, settings);
			//
			if(detectorSettings.isEmpty()) {
				processingInfo.addWarnMessage(DESCRIPTION, "The chromatogram doesn't contain any of the given peak traces.");
			} else {
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
					processingInfo.addErrorMessage(DESCRIPTION, "Sorry, somehow the execution failed.", e);
				}
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
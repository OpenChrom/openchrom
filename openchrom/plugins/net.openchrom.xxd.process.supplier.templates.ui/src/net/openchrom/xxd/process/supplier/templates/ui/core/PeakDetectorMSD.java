/*******************************************************************************
 * Copyright (c) 2019, 2023 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Christoph Läubrich - execute in EDT
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.core;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.eclipse.chemclipse.chromatogram.msd.peak.detector.core.AbstractPeakDetectorMSD;
import org.eclipse.chemclipse.chromatogram.msd.peak.detector.settings.IPeakDetectorSettingsMSD;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;
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

public class PeakDetectorMSD<P extends IPeak, C extends IChromatogram<P>, R> extends AbstractPeakDetectorMSD<P, C, R> {

	private static final String DESCRIPTION = "PeakDetectorMSD";

	@Override
	public IProcessingInfo<R> detect(IChromatogramSelectionMSD chromatogramSelection, IPeakDetectorSettingsMSD peakDetectorSettings, IProgressMonitor monitor) {

		IProcessingInfo<R> processingInfo = new ProcessingInfo<>();
		if(peakDetectorSettings instanceof PeakDetectorSettings settings) {
			/*
			 * RI will be adjusted to retention time (minutes).
			 */
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
	public IProcessingInfo<R> detect(IChromatogramSelectionMSD chromatogramSelection, IProgressMonitor monitor) {

		PeakDetectorSettings settings = PreferenceSupplier.getPeakDetectorSettingsMSD();
		return detect(chromatogramSelection, settings, monitor);
	}
}

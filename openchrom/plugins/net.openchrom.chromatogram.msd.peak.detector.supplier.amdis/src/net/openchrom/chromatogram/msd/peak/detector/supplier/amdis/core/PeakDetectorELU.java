/*******************************************************************************
 * Copyright (c) 2020, 2024 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.core;

import java.io.File;

import org.eclipse.chemclipse.chromatogram.msd.peak.detector.core.AbstractPeakDetectorMSD;
import org.eclipse.chemclipse.chromatogram.msd.peak.detector.settings.IPeakDetectorSettingsMSD;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.preferences.PreferenceSupplier;
import net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.settings.SettingsELU;
import net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.support.PeakProcessorSupport;

public class PeakDetectorELU<P extends IPeak, C extends IChromatogram<P>, R> extends AbstractPeakDetectorMSD<P, C, R> {

	private static final Logger logger = Logger.getLogger(PeakDetectorELU.class);

	@Override
	public IProcessingInfo<R> detect(IChromatogramSelectionMSD chromatogramSelection, IPeakDetectorSettingsMSD peakDetectorSettings, IProgressMonitor monitor) {

		/*
		 * Validate
		 */
		IProcessingInfo<R> processingInfo = validate(chromatogramSelection, peakDetectorSettings, monitor);
		if(!processingInfo.hasErrorMessages()) {
			if(peakDetectorSettings instanceof SettingsELU settingsELU) {
				PeakProcessorSupport peakProcessorSupport = new PeakProcessorSupport();
				File file = settingsELU.getResultFile();
				if(file != null && file.exists()) {
					peakProcessorSupport.extractEluFileAndSetPeaks(chromatogramSelection, file, settingsELU, monitor);
				} else {
					logger.warn("The file doesn't exist: " + file.getAbsolutePath());
				}
				chromatogramSelection.getChromatogram().setDirty(true);
			} else {
				logger.warn("The settings is not of type: " + SettingsELU.class);
			}
		}
		return processingInfo;
	}

	@Override
	public IProcessingInfo<R> detect(IChromatogramSelectionMSD chromatogramSelection, IProgressMonitor monitor) {

		SettingsELU settingsELU = PreferenceSupplier.getSettingsELU();
		return detect(chromatogramSelection, settingsELU, monitor);
	}
}

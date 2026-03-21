/*******************************************************************************
 * Copyright (c) 2020, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.core;

import java.io.File;

import org.eclipse.chemclipse.chromatogram.msd.peak.detector.core.AbstractPeakDetectorMSD;
import org.eclipse.chemclipse.chromatogram.msd.peak.detector.settings.IPeakDetectorSettingsMSD;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.settings.SettingsELU;
import net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.support.PeakProcessorSupport;

public class PeakDetectorELU extends AbstractPeakDetectorMSD {

	private static final Logger logger = Logger.getLogger(PeakDetectorELU.class);

	@Override
	public IProcessingInfo<?> detect(IChromatogramSelectionMSD chromatogramSelection, IPeakDetectorSettingsMSD peakDetectorSettings, IProgressMonitor monitor) {

		/*
		 * Validate
		 */
		IProcessingInfo<?> processingInfo = validate(chromatogramSelection, peakDetectorSettings, monitor);
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
}

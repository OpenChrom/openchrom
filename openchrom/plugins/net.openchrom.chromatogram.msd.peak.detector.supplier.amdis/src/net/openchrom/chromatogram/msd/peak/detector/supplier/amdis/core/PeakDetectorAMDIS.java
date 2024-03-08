/*******************************************************************************
 * Copyright (c) 2014, 2024 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.core;

import org.eclipse.chemclipse.chromatogram.msd.peak.detector.core.AbstractPeakDetectorMSD;
import org.eclipse.chemclipse.chromatogram.msd.peak.detector.settings.IPeakDetectorSettingsMSD;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.IProcessingMessage;
import org.eclipse.chemclipse.processing.core.IProcessingResult;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.internal.identifier.AmdisIdentifier;
import net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.preferences.PreferenceSupplier;
import net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.settings.SettingsAMDIS;

public class PeakDetectorAMDIS<P extends IPeak, C extends IChromatogram<P>, R> extends AbstractPeakDetectorMSD<P, C, R> {

	private static final Logger logger = Logger.getLogger(PeakDetectorAMDIS.class);

	@Override
	public IProcessingInfo<R> detect(IChromatogramSelectionMSD chromatogramSelection, IPeakDetectorSettingsMSD peakDetectorSettings, IProgressMonitor monitor) {

		/*
		 * Validate
		 */
		IProcessingInfo<R> processingInfo = validate(chromatogramSelection, peakDetectorSettings, monitor);
		if(!processingInfo.hasErrorMessages()) {
			if(peakDetectorSettings instanceof SettingsAMDIS settingsAMDIS) {
				AmdisIdentifier identifier = new AmdisIdentifier();
				try {
					IProcessingResult<Void> result = identifier.calulateAndSetDeconvolutedPeaks(chromatogramSelection, settingsAMDIS, monitor);
					for(IProcessingMessage message : result.getMessages()) {
						processingInfo.addMessage(message);
					}
				} catch(InterruptedException e) {
					Thread.currentThread().interrupt();
					return null;
				}
				chromatogramSelection.getChromatogram().setDirty(true);
			} else {
				logger.warn("The settings is not of type: " + SettingsAMDIS.class);
			}
		}
		return processingInfo;
	}

	@Override
	public IProcessingInfo<R> detect(IChromatogramSelectionMSD chromatogramSelection, IProgressMonitor monitor) {

		SettingsAMDIS settingsAMDIS = PreferenceSupplier.getSettingsAMDIS();
		return detect(chromatogramSelection, settingsAMDIS, monitor);
	}
}

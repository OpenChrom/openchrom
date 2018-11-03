/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.peaks;

import org.eclipse.chemclipse.chromatogram.csd.peak.detector.core.IPeakDetectorCSD;
import org.eclipse.chemclipse.chromatogram.csd.peak.detector.settings.IPeakDetectorSettingsCSD;
import org.eclipse.chemclipse.chromatogram.msd.peak.detector.core.IPeakDetectorMSD;
import org.eclipse.chemclipse.chromatogram.msd.peak.detector.settings.IPeakDetectorSettingsMSD;
import org.eclipse.chemclipse.chromatogram.peak.detector.core.AbstractPeakDetector;
import org.eclipse.chemclipse.chromatogram.peak.detector.settings.IPeakDetectorSettings;
import org.eclipse.chemclipse.csd.model.core.IChromatogramCSD;
import org.eclipse.chemclipse.csd.model.core.IChromatogramPeakCSD;
import org.eclipse.chemclipse.csd.model.core.selection.IChromatogramSelectionCSD;
import org.eclipse.chemclipse.csd.model.core.support.PeakBuilderCSD;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.AbstractChromatogram;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.exceptions.PeakException;
import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.model.support.IScanRange;
import org.eclipse.chemclipse.model.support.ScanRange;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IChromatogramPeakMSD;
import org.eclipse.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;
import org.eclipse.chemclipse.msd.model.core.support.PeakBuilderMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.settings.PeakDetectorSettings;

public class PeakDetector extends AbstractPeakDetector implements IPeakDetectorMSD, IPeakDetectorCSD {

	private static final Logger logger = Logger.getLogger(PeakDetector.class);

	@SuppressWarnings("unchecked")
	@Override
	public IProcessingInfo detect(IChromatogramSelectionMSD chromatogramSelection, IPeakDetectorSettingsMSD settings, IProgressMonitor monitor) {

		return applyDetector(chromatogramSelection, settings, monitor);
	}

	@Override
	public IProcessingInfo detect(IChromatogramSelectionMSD chromatogramSelection, IProgressMonitor monitor) {

		PeakDetectorSettings settings = getSettings(PreferenceSupplier.P_PEAK_DETECTOR_LIST_MSD);
		return detect(chromatogramSelection, settings, monitor);
	}

	@SuppressWarnings("unchecked")
	@Override
	public IProcessingInfo detect(IChromatogramSelectionCSD chromatogramSelection, IPeakDetectorSettingsCSD settings, IProgressMonitor monitor) {

		return applyDetector(chromatogramSelection, settings, monitor);
	}

	@Override
	public IProcessingInfo detect(IChromatogramSelectionCSD chromatogramSelection, IProgressMonitor monitor) {

		PeakDetectorSettings settings = getSettings(PreferenceSupplier.P_PEAK_DETECTOR_LIST_CSD);
		return detect(chromatogramSelection, settings, monitor);
	}

	private PeakDetectorSettings getSettings(String preferenceKey) {

		PeakDetectorSettings settings = new PeakDetectorSettings();
		settings.setDetectorSettings(PreferenceSupplier.getSettings(preferenceKey, ""));
		return settings;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private IProcessingInfo applyDetector(IChromatogramSelection<? extends IPeak> chromatogramSelection, IPeakDetectorSettings settings, IProgressMonitor monitor) {

		IProcessingInfo processingInfo = super.validate(chromatogramSelection, settings, monitor);
		if(!processingInfo.hasErrorMessages()) {
			if(settings instanceof PeakDetectorSettings) {
				IChromatogram chromatogram = chromatogramSelection.getChromatogram();
				PeakDetectorSettings peakDetectorSettings = (PeakDetectorSettings)settings;
				for(DetectorSettings detectorSettings : peakDetectorSettings.getDetectorSettings()) {
					setPeakBySettings(chromatogram, detectorSettings);
				}
			} else {
				processingInfo.addErrorMessage(PeakDetectorSettings.DESCRIPTION, "The settings instance is wrong.");
			}
		}
		return processingInfo;
	}

	private void setPeakBySettings(IChromatogram<? extends IPeak> chromatogram, DetectorSettings detectorSettings) {

		int start = (int)(detectorSettings.getStartRetentionTime() * AbstractChromatogram.MINUTE_CORRELATION_FACTOR);
		int stop = (int)(detectorSettings.getStopRetentionTime() * AbstractChromatogram.MINUTE_CORRELATION_FACTOR);
		setPeakByRetentionTimeRange(chromatogram, start, stop, detectorSettings.isIncludeBackground());
	}

	private void setPeakByRetentionTimeRange(IChromatogram<? extends IPeak> chromatogram, int startRetentionTime, int stopRetentionTime, boolean includeBackground) {

		int startScan = chromatogram.getScanNumber(startRetentionTime);
		int stopScan = chromatogram.getScanNumber(stopRetentionTime);
		setPeakByScanRange(chromatogram, startScan, stopScan, includeBackground);
	}

	private void setPeakByScanRange(IChromatogram<? extends IPeak> chromatogram, int startScan, int stopScan, boolean includeBackground) {

		try {
			if(startScan > 0 && startScan < stopScan) {
				IScanRange scanRange = new ScanRange(startScan, stopScan);
				if(chromatogram instanceof IChromatogramMSD) {
					IChromatogramMSD chromatogramMSD = (IChromatogramMSD)chromatogram;
					IChromatogramPeakMSD peak = PeakBuilderMSD.createPeak(chromatogramMSD, scanRange, includeBackground);
					peak.setDetectorDescription(PeakDetectorSettings.DESCRIPTION);
					chromatogramMSD.addPeak(peak);
				} else if(chromatogram instanceof IChromatogramCSD) {
					IChromatogramCSD chromatogramCSD = (IChromatogramCSD)chromatogram;
					IChromatogramPeakCSD peak = PeakBuilderCSD.createPeak(chromatogramCSD, scanRange, includeBackground);
					peak.setDetectorDescription(PeakDetectorSettings.DESCRIPTION);
					chromatogramCSD.addPeak(peak);
				}
			}
		} catch(PeakException e) {
			logger.warn(e);
		}
	}
}

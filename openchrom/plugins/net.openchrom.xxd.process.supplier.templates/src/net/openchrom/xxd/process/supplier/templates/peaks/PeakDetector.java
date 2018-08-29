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

import java.util.List;

import org.eclipse.chemclipse.chromatogram.csd.peak.detector.core.IPeakDetectorCSD;
import org.eclipse.chemclipse.chromatogram.csd.peak.detector.settings.IPeakDetectorCSDSettings;
import org.eclipse.chemclipse.chromatogram.msd.peak.detector.core.IPeakDetectorMSD;
import org.eclipse.chemclipse.chromatogram.msd.peak.detector.settings.IPeakDetectorMSDSettings;
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
import org.eclipse.core.runtime.IStatus;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.settings.PeakDetectorSettings;
import net.openchrom.xxd.process.supplier.templates.util.PeakDetectorListUtil;
import net.openchrom.xxd.process.supplier.templates.util.PeakDetectorValidator;

public class PeakDetector extends AbstractPeakDetector implements IPeakDetectorMSD, IPeakDetectorCSD {

	private static final Logger logger = Logger.getLogger(PeakDetector.class);
	//
	private static final String DETECTOR_DESCRIPTION = "Template Peak Detector";

	@SuppressWarnings("unchecked")
	@Override
	public IProcessingInfo detect(IChromatogramSelectionMSD chromatogramSelection, IPeakDetectorMSDSettings settings, IProgressMonitor monitor) {

		return applyDetector(chromatogramSelection, settings, monitor);
	}

	@Override
	public IProcessingInfo detect(IChromatogramSelectionMSD chromatogramSelection, IProgressMonitor monitor) {

		PeakDetectorSettings settings = getPeakDetectorSettings(PreferenceSupplier.P_PEAK_DETECTOR_LIST_MSD);
		return detect(chromatogramSelection, settings, monitor);
	}

	@SuppressWarnings("unchecked")
	@Override
	public IProcessingInfo detect(IChromatogramSelectionCSD chromatogramSelection, IPeakDetectorCSDSettings settings, IProgressMonitor monitor) {

		return applyDetector(chromatogramSelection, settings, monitor);
	}

	@Override
	public IProcessingInfo detect(IChromatogramSelectionCSD chromatogramSelection, IProgressMonitor monitor) {

		PeakDetectorSettings settings = getPeakDetectorSettings(PreferenceSupplier.P_PEAK_DETECTOR_LIST_CSD);
		return detect(chromatogramSelection, settings, monitor);
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
				processingInfo.addErrorMessage(DETECTOR_DESCRIPTION, "The settings instance is wrong.");
			}
		}
		return processingInfo;
	}

	private PeakDetectorSettings getPeakDetectorSettings(String preferenceKey) {

		PeakDetectorSettings settings = new PeakDetectorSettings();
		PeakDetectorListUtil util = new PeakDetectorListUtil();
		PeakDetectorValidator validator = new PeakDetectorValidator();
		//
		List<String> ranges = util.getList(PreferenceSupplier.INSTANCE().getPreferences().get(preferenceKey, ""));
		for(String range : ranges) {
			IStatus status = validator.validate(range);
			if(status.isOK()) {
				settings.getDetectorSettings().add(validator.getDetectorSettings());
			}
		}
		//
		return settings;
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
					peak.setDetectorDescription(DETECTOR_DESCRIPTION);
				} else if(chromatogram instanceof IChromatogramCSD) {
					IChromatogramCSD chromatogramCSD = (IChromatogramCSD)chromatogram;
					IChromatogramPeakCSD peak = PeakBuilderCSD.createPeak(chromatogramCSD, scanRange, includeBackground);
					peak.setDetectorDescription(DETECTOR_DESCRIPTION);
				}
			}
		} catch(PeakException e) {
			logger.warn(e);
		}
	}
}

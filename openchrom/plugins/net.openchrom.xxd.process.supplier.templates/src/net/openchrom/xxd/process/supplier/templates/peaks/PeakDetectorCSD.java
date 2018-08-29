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
import org.eclipse.chemclipse.chromatogram.peak.detector.core.AbstractPeakDetector;
import org.eclipse.chemclipse.csd.model.core.IChromatogramCSD;
import org.eclipse.chemclipse.csd.model.core.IChromatogramPeakCSD;
import org.eclipse.chemclipse.csd.model.core.selection.IChromatogramSelectionCSD;
import org.eclipse.chemclipse.csd.model.core.support.PeakBuilderCSD;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.AbstractChromatogram;
import org.eclipse.chemclipse.model.exceptions.PeakException;
import org.eclipse.chemclipse.model.support.IScanRange;
import org.eclipse.chemclipse.model.support.ScanRange;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.settings.PeakDetectorSettingsCSD;
import net.openchrom.xxd.process.supplier.templates.util.PeakDetectorListUtil;
import net.openchrom.xxd.process.supplier.templates.util.PeakDetectorValidator;

public class PeakDetectorCSD extends AbstractPeakDetector implements IPeakDetectorCSD {

	private static final Logger logger = Logger.getLogger(PeakDetectorCSD.class);
	//
	private static final String DETECTOR_DESCRIPTION = "Template Peak Detector";

	@Override
	public IProcessingInfo detect(IChromatogramSelectionCSD chromatogramSelection, IPeakDetectorCSDSettings settings, IProgressMonitor monitor) {

		IProcessingInfo processingInfo = super.validate(chromatogramSelection, settings, monitor);
		if(!processingInfo.hasErrorMessages()) {
			if(settings instanceof PeakDetectorSettingsCSD) {
				IChromatogramCSD chromatogram = chromatogramSelection.getChromatogramCSD();
				PeakDetectorSettingsCSD settingsCSD = (PeakDetectorSettingsCSD)settings;
				for(DetectorSettings detectorSettings : settingsCSD.getDetectorSettings()) {
					setPeakBySettings(chromatogram, detectorSettings);
				}
			} else {
				processingInfo.addErrorMessage(DETECTOR_DESCRIPTION, "The settings instance is wrong.");
			}
		}
		return processingInfo;
	}

	@Override
	public IProcessingInfo detect(IChromatogramSelectionCSD chromatogramSelection, IProgressMonitor monitor) {

		PeakDetectorSettingsCSD settings = new PeakDetectorSettingsCSD();
		PeakDetectorListUtil util = new PeakDetectorListUtil();
		PeakDetectorValidator validator = new PeakDetectorValidator();
		//
		List<String> ranges = util.getList(PreferenceSupplier.INSTANCE().getPreferences().get(PreferenceSupplier.P_PEAK_DETECTOR_LIST_MSD, ""));
		for(String range : ranges) {
			IStatus status = validator.validate(range);
			if(status.isOK()) {
				settings.getDetectorSettings().add(validator.getDetectorSettings());
			}
		}
		//
		return detect(chromatogramSelection, settings, monitor);
	}

	private void setPeakBySettings(IChromatogramCSD chromatogram, DetectorSettings detectorSettings) {

		int start = (int)(detectorSettings.getStartRetentionTime() * AbstractChromatogram.MINUTE_CORRELATION_FACTOR);
		int stop = (int)(detectorSettings.getStopRetentionTime() * AbstractChromatogram.MINUTE_CORRELATION_FACTOR);
		setPeakByRetentionTimeRange(chromatogram, start, stop, detectorSettings.isIncludeBackground());
	}

	private void setPeakByRetentionTimeRange(IChromatogramCSD chromatogram, int startRetentionTime, int stopRetentionTime, boolean includeBackground) {

		int startScan = chromatogram.getScanNumber(startRetentionTime);
		int stopScan = chromatogram.getScanNumber(stopRetentionTime);
		setPeakByScanRange(chromatogram, startScan, stopScan, includeBackground);
	}

	private void setPeakByScanRange(IChromatogramCSD chromatogram, int startScan, int stopScan, boolean includeBackground) {

		try {
			if(startScan > 0 && startScan < stopScan) {
				IScanRange scanRange = new ScanRange(startScan, stopScan);
				IChromatogramPeakCSD peak = PeakBuilderCSD.createPeak(chromatogram, scanRange, includeBackground);
				peak.setDetectorDescription(DETECTOR_DESCRIPTION);
				chromatogram.addPeak(peak);
			}
		} catch(PeakException e) {
			logger.warn(e);
		}
	}
}

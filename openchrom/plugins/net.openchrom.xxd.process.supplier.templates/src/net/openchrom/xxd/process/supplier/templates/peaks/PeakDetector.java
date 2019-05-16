/*******************************************************************************
 * Copyright (c) 2018, 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Alexander Kerner - Generics
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.peaks;

import java.util.Set;

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
import org.eclipse.chemclipse.model.comparator.TargetExtendedComparator;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.model.exceptions.PeakException;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.model.support.IScanRange;
import org.eclipse.chemclipse.model.support.ScanRange;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IChromatogramPeakMSD;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;
import org.eclipse.chemclipse.msd.model.core.support.PeakBuilderMSD;
import org.eclipse.chemclipse.msd.model.xic.IExtractedIonSignal;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.support.comparator.SortOrder;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.settings.PeakDetectorSettings;
import net.openchrom.xxd.process.supplier.templates.util.PeakDetectorListUtil;

@SuppressWarnings("rawtypes")
public class PeakDetector extends AbstractPeakDetector implements IPeakDetectorMSD, IPeakDetectorCSD {

	private static final Logger logger = Logger.getLogger(PeakDetector.class);
	//
	private TargetExtendedComparator comparator = new TargetExtendedComparator(SortOrder.DESC);
	private PeakDetectorListUtil listUtil = new PeakDetectorListUtil();

	@Override
	public IProcessingInfo detect(IChromatogramSelectionMSD chromatogramSelection, IPeakDetectorSettingsMSD settings, IProgressMonitor monitor) {

		return applyDetector(chromatogramSelection, settings, monitor);
	}

	@Override
	public IProcessingInfo detect(IChromatogramSelectionMSD chromatogramSelection, IProgressMonitor monitor) {

		PeakDetectorSettings settings = getSettings(PreferenceSupplier.P_PEAK_DETECTOR_LIST_MSD);
		return detect(chromatogramSelection, settings, monitor);
	}

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

	@SuppressWarnings({"unchecked"})
	private IProcessingInfo applyDetector(IChromatogramSelection<? extends IPeak, ?> chromatogramSelection, IPeakDetectorSettings settings, IProgressMonitor monitor) {

		IProcessingInfo processingInfo = super.validate(chromatogramSelection, settings, monitor);
		if(!processingInfo.hasErrorMessages()) {
			if(settings instanceof PeakDetectorSettings) {
				IChromatogram chromatogram = chromatogramSelection.getChromatogram();
				PeakDetectorSettings peakDetectorSettings = (PeakDetectorSettings)settings;
				for(DetectorSetting detectorSetting : peakDetectorSettings.getDetectorSettings()) {
					setPeakBySettings(chromatogram, detectorSetting);
				}
			} else {
				processingInfo.addErrorMessage(PeakDetectorSettings.DESCRIPTION, "The settings instance is wrong.");
			}
		}
		return processingInfo;
	}

	private void setPeakBySettings(IChromatogram<? extends IPeak> chromatogram, DetectorSetting detectorSetting) {

		int startRetentionTime = detectorSetting.getStartRetentionTime();
		int stopRetentionTime = detectorSetting.getStopRetentionTime();
		/*
		 * If a reference identifier is set, the retention time range
		 * is adjusted dynamically by the position of the given peak.
		 */
		String referenceIdentifier = detectorSetting.getReferenceIdentifier();
		if(!"".equals(referenceIdentifier)) {
			IPeak peak = getReferencePeak(chromatogram, referenceIdentifier);
			if(peak != null) {
				int retentionTime = peak.getPeakModel().getRetentionTimeAtPeakMaximum();
				startRetentionTime += retentionTime;
				stopRetentionTime += retentionTime;
			}
		}
		//
		setPeakByRetentionTimeRange(chromatogram, startRetentionTime, stopRetentionTime, detectorSetting);
	}

	private void setPeakByRetentionTimeRange(IChromatogram<? extends IPeak> chromatogram, int startRetentionTime, int stopRetentionTime, DetectorSetting detectorSetting) {

		int startScan = chromatogram.getScanNumber(startRetentionTime);
		int stopScan = chromatogram.getScanNumber(stopRetentionTime);
		setPeakByScanRange(chromatogram, startScan, stopScan, detectorSetting);
	}

	private void setPeakByScanRange(IChromatogram<? extends IPeak> chromatogram, int startScan, int stopScan, DetectorSetting detectorSetting) {

		boolean includeBackground = detectorSetting.isIncludeBackground();
		//
		try {
			if(startScan > 0 && startScan < stopScan) {
				/*
				 * Get the scan range.
				 */
				Set<Integer> traces = listUtil.extractTraces(detectorSetting.getTraces());
				IScanRange scanRange;
				if(detectorSetting.isOptimizeRange()) {
					scanRange = optimizeRange(chromatogram, startScan, stopScan, traces);
				} else {
					scanRange = new ScanRange(startScan, stopScan);
				}
				/*
				 * Try to create a peak.
				 */
				if(chromatogram instanceof IChromatogramMSD) {
					IChromatogramMSD chromatogramMSD = (IChromatogramMSD)chromatogram;
					IChromatogramPeakMSD peak;
					if(traces.size() > 0) {
						peak = PeakBuilderMSD.createPeak(chromatogramMSD, scanRange, includeBackground, traces);
					} else {
						peak = PeakBuilderMSD.createPeak(chromatogramMSD, scanRange, includeBackground);
					}
					peak.setDetectorDescription(PeakDetectorSettings.DESCRIPTION);
					chromatogramMSD.addPeak(peak);
				} else if(chromatogram instanceof IChromatogramCSD) {
					IChromatogramCSD chromatogramCSD = (IChromatogramCSD)chromatogram;
					IChromatogramPeakCSD peak = PeakBuilderCSD.createPeak(chromatogramCSD, scanRange, includeBackground);
					peak.setDetectorDescription(PeakDetectorSettings.DESCRIPTION);
					chromatogramCSD.addPeak(peak);
				} else if(chromatogram instanceof IChromatogramWSD) {
					logger.info("Handling WSD data is not supported yet");
				}
			}
		} catch(PeakException e) {
			logger.warn(e);
		}
	}

	private IScanRange optimizeRange(IChromatogram chromatogram, int startScan, int stopScan, Set<Integer> traces) {

		int scanWidth = stopScan - startScan + 1;
		int partLength = scanWidth / 4;
		/*
		 * Assume max value in ~ in the middle.
		 */
		float maxSignalCenter = Float.MIN_VALUE;
		int centerScan = startScan;
		for(int i = startScan + partLength; i <= stopScan - partLength; i++) {
			float signal = getScanSignal(chromatogram, i, traces);
			if(signal > maxSignalCenter) {
				maxSignalCenter = signal;
				centerScan = i;
			}
		}
		/*
		 * Left border optimization
		 */
		float minSignalLeft = Float.MAX_VALUE;
		int startScanOptimized = startScan;
		for(int i = startScan; i < centerScan; i++) {
			float signal = getScanSignal(chromatogram, i, traces);
			if(signal < minSignalLeft) {
				minSignalLeft = signal;
				startScanOptimized = i;
			}
		}
		/*
		 * Right border optimization
		 */
		float minSignalRight = Float.MAX_VALUE;
		int stopScanOptimized = stopScan;
		for(int i = stopScan; i > centerScan; i--) {
			float signal = getScanSignal(chromatogram, i, traces);
			if(signal < minSignalRight) {
				minSignalRight = signal;
				stopScanOptimized = i;
			}
		}
		//
		return new ScanRange(startScanOptimized, stopScanOptimized);
	}

	private float getScanSignal(IChromatogram chromatogram, int scanNumber, Set<Integer> traces) {

		float scanSignal = 0.0f;
		IScan scan = chromatogram.getScan(scanNumber);
		if(scan instanceof IScanMSD) {
			IScanMSD scanMSD = (IScanMSD)scan;
			IExtractedIonSignal extractedIonSignal = scanMSD.getExtractedIonSignal();
			for(int trace : traces) {
				scanSignal += extractedIonSignal.getAbundance(trace);
			}
		} else {
			scanSignal = scan.getTotalSignal();
		}
		return scanSignal;
	}

	private IPeak getReferencePeak(IChromatogram<? extends IPeak> chromatogram, String referenceIdentifier) {

		for(IPeak peak : chromatogram.getPeaks()) {
			ILibraryInformation libraryInformation = IIdentificationTarget.getBestLibraryInformation(peak.getTargets(), comparator);
			if(libraryInformation != null) {
				if(referenceIdentifier.equals(libraryInformation.getName())) {
					return peak;
				}
			}
		}
		return null;
	}
}
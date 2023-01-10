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
 * Christoph Läubrich - use retention time range interface
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.support;

import java.util.List;
import java.util.Set;

import org.eclipse.chemclipse.csd.model.core.IChromatogramCSD;
import org.eclipse.chemclipse.csd.model.core.IPeakCSD;
import org.eclipse.chemclipse.csd.model.core.support.PeakBuilderCSD;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.comparator.IdentificationTargetComparator;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.model.core.MarkedTraceModus;
import org.eclipse.chemclipse.model.exceptions.PeakException;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.model.support.IScanRange;
import org.eclipse.chemclipse.model.support.RetentionIndexMap;
import org.eclipse.chemclipse.model.support.RetentionIndexMath;
import org.eclipse.chemclipse.model.support.ScanRange;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IPeakMSD;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.core.support.PeakBuilderMSD;
import org.eclipse.chemclipse.msd.model.xic.IExtractedIonSignal;
import org.eclipse.chemclipse.support.comparator.SortOrder;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.chemclipse.wsd.model.core.IPeakWSD;
import org.eclipse.chemclipse.wsd.model.core.IScanWSD;
import org.eclipse.chemclipse.wsd.model.core.support.PeakBuilderWSD;
import org.eclipse.chemclipse.wsd.model.xwc.IExtractedWavelengthSignal;

import net.openchrom.xxd.process.supplier.templates.model.AbstractSetting;
import net.openchrom.xxd.process.supplier.templates.model.DefaultSetting;
import net.openchrom.xxd.process.supplier.templates.model.PositionDirective;
import net.openchrom.xxd.process.supplier.templates.model.RetentionTimeRange;
import net.openchrom.xxd.process.supplier.templates.settings.PeakDetectorSettings;

public class PeakSupport {

	private static final Logger logger = Logger.getLogger(PeakSupport.class);

	public static boolean isPeakRelevant(IPeak peak, Set<Integer> traces) {

		boolean isPeakRelevant = false;
		if(traces != null && peak != null) {
			/*
			 * In case if CSD or if the traces are empty, TIC is assumed.
			 */
			if(peak instanceof IPeakCSD || traces.isEmpty()) {
				isPeakRelevant = true;
			} else {
				if(peak instanceof IPeakMSD peakMSD) {
					/*
					 * MSD
					 */
					isPeakRelevant = true;
					IScanMSD scanMSD = peakMSD.getPeakModel().getPeakMassSpectrum();
					IExtractedIonSignal extractedIonSignal = scanMSD.getExtractedIonSignal();
					exitloop:
					for(int trace : traces) {
						float abundance = extractedIonSignal.getAbundance(trace);
						if(abundance == 0) {
							isPeakRelevant = false;
							break exitloop;
						}
					}
				} else if(peak instanceof IPeakWSD peakWSD) {
					/*
					 * WSD
					 */
					isPeakRelevant = true;
					IScan scan = peakWSD.getPeakModel().getPeakMaximum();
					if(scan instanceof IScanWSD scanWSD) {
						IExtractedWavelengthSignal extractedWavelengthSignal = scanWSD.getExtractedWavelengthSignal();
						exitloop:
						for(int trace : traces) {
							float abundance = extractedWavelengthSignal.getAbundance(trace);
							if(abundance == 0) {
								isPeakRelevant = false;
								break exitloop;
							}
						}
					}
				}
			}
		}
		//
		return isPeakRelevant;
	}

	public static int getStartScan(IChromatogram<?> chromatogram, int retentionTime) {

		int startScan = chromatogram.getScanNumber(retentionTime);
		if(startScan <= 0) {
			startScan = 1;
		}
		return startScan;
	}

	public static int getStopScan(IChromatogram<?> chromatogram, int retentionTime) {

		int stopScan = chromatogram.getScanNumber(retentionTime);
		if(stopScan > chromatogram.getNumberOfScans()) {
			stopScan = chromatogram.getNumberOfScans();
		}
		return stopScan;
	}

	public RetentionTimeRange getRetentionTimeRange(List<? extends IPeak> peaks, AbstractSetting setting, String referenceIdentifier, RetentionIndexMap retentionIndexMap) {

		/*
		 * Retention Time (milliseconds)
		 */
		int startRetentionTime = 0;
		int stopRetentionTime = 0;
		//
		if(!referenceIdentifier.isEmpty()) {
			/*
			 * Position via Reference
			 */
			IPeak peak = getReferencePeak(peaks, referenceIdentifier);
			if(peak != null) {
				/*
				 * If a reference identifier is set, the retention time range
				 * is adjusted dynamically by the position of the given peak.
				 * The start / stop retention could be also negative.
				 * This allows to address peaks that are in time before the marker peak.
				 * ---
				 * Positive
				 * REF ... (start ~ stop)
				 * ---
				 * Negative
				 * (start ~ stop) ... REF
				 */
				IPeakModel peakModel = peak.getPeakModel();
				int startRetentionTimePeak = peakModel.getStartRetentionTime();
				int stopRetentionTimePeak = peakModel.getStopRetentionTime();
				//
				if(isUseRetentionIndex(setting)) {
					/*
					 * The position is based on retention index.
					 * Hence, the retention index of the reference peak must be retrieved first.
					 * Then the RI based correction must be applied and converted back
					 * to the retention time in milliseconds
					 */
					float retentionIndexStart = retentionIndexMap.getRetentionIndex(startRetentionTimePeak);
					if(retentionIndexStart != RetentionIndexMath.RETENTION_INDEX_MISSING) {
						float retentionIndexStop = retentionIndexMap.getRetentionIndex(stopRetentionTimePeak);
						if(retentionIndexStop != RetentionIndexMath.RETENTION_INDEX_MISSING) {
							retentionIndexStart += setting.getPositionStart();
							retentionIndexStop += setting.getPositionStop();
							//
							DefaultSetting defaultSetting = new DefaultSetting();
							defaultSetting.setPositionDirective(PositionDirective.RETENTION_INDEX);
							defaultSetting.setPositionStart(retentionIndexStart);
							defaultSetting.setPositionStop(retentionIndexStop);
							startRetentionTime = defaultSetting.getRetentionTimeStart(retentionIndexMap);
							stopRetentionTime = defaultSetting.getRetentionTimeStop(retentionIndexMap);
						}
					}
				} else {
					/*
					 * The position is based on retention time (minutes or milliseconds).
					 * Hence, the correction can be applied directly.
					 */
					startRetentionTime = setting.getRetentionTimeStart(retentionIndexMap) + startRetentionTimePeak;
					stopRetentionTime = setting.getRetentionTimeStop(retentionIndexMap) + stopRetentionTimePeak;
				}
			}
		} else {
			/*
			 * Position Direct
			 */
			startRetentionTime = setting.getRetentionTimeStart(retentionIndexMap);
			stopRetentionTime = setting.getRetentionTimeStop(retentionIndexMap);
		}
		//
		return new RetentionTimeRange(startRetentionTime, stopRetentionTime);
	}

	public IPeak extractPeakByRetentionTime(IChromatogram<? extends IPeak> chromatogram, int startRetentionTime, int stopRetentionTime, boolean includeBackground, boolean optimizeRange, Set<Integer> traces) {

		int startScan = getStartScan(chromatogram, startRetentionTime);
		int stopScan = getStopScan(chromatogram, stopRetentionTime);
		return extractPeakByScanRange(chromatogram, startScan, stopScan, includeBackground, optimizeRange, traces);
	}

	public IPeak extractPeakByRetentionTime(IChromatogram<? extends IPeak> chromatogram, int startRetentionTime, int stopRetentionTime, float startIntensity, float stopIntensity, Set<Integer> traces) {

		int startScan = getStartScan(chromatogram, startRetentionTime);
		int stopScan = getStopScan(chromatogram, stopRetentionTime);
		return extractPeakByScanRange(chromatogram, startScan, stopScan, startIntensity, stopIntensity, traces);
	}

	public IPeak extractPeakByScanRange(IChromatogram<? extends IPeak> chromatogram, int startScan, int stopScan, boolean includeBackground, boolean optimizeRange, Set<Integer> traces) {

		IPeak peak = null;
		//
		try {
			if(startScan > 0 && startScan < stopScan) {
				/*
				 * Get the scan range.
				 */
				IScanRange scanRange;
				if(optimizeRange) {
					scanRange = optimizeRange(chromatogram, startScan, stopScan, traces);
				} else {
					scanRange = new ScanRange(startScan, stopScan);
				}
				/*
				 * Try to create a peak.
				 */
				if(chromatogram instanceof IChromatogramMSD) {
					/*
					 * Must be called with 'exclude' mode, so given ions will be 'excluded' from AbstractScan#removeIons.
					 */
					IChromatogramMSD chromatogramMSD = (IChromatogramMSD)chromatogram;
					if(traces.size() > 0) {
						peak = PeakBuilderMSD.createPeak(chromatogramMSD, scanRange, includeBackground, traces, MarkedTraceModus.EXCLUDE);
					} else {
						peak = PeakBuilderMSD.createPeak(chromatogramMSD, scanRange, includeBackground);
					}
					peak.setDetectorDescription(PeakDetectorSettings.DETECTOR_DESCRIPTION);
					/*
					 * Only one trace.
					 */
				} else if(chromatogram instanceof IChromatogramCSD) {
					IChromatogramCSD chromatogramCSD = (IChromatogramCSD)chromatogram;
					peak = PeakBuilderCSD.createPeak(chromatogramCSD, scanRange, includeBackground);
					peak.setDetectorDescription(PeakDetectorSettings.DETECTOR_DESCRIPTION);
				} else if(chromatogram instanceof IChromatogramWSD) {
					IChromatogramWSD chromatogramWSD = (IChromatogramWSD)chromatogram;
					if(traces.size() > 0) {
						peak = PeakBuilderWSD.createPeak(chromatogramWSD, scanRange, includeBackground, traces, MarkedTraceModus.INCLUDE);
					} else {
						peak = PeakBuilderWSD.createPeak(chromatogramWSD, scanRange, includeBackground);
					}
					peak.setDetectorDescription(PeakDetectorSettings.DETECTOR_DESCRIPTION);
				}
			}
		} catch(PeakException e) {
			logger.warn(e);
		}
		//
		return peak;
	}

	public IPeak extractPeakByScanRange(IChromatogram<? extends IPeak> chromatogram, int startScan, int stopScan, float startIntensity, float stopIntensity, Set<Integer> traces) {

		IPeak peak = null;
		//
		try {
			if(startScan > 0 && startScan < stopScan) {
				/*
				 * Get the scan range.
				 */
				IScanRange scanRange = new ScanRange(startScan, stopScan);
				/*
				 * Try to create a peak.
				 */
				if(chromatogram instanceof IChromatogramMSD) {
					IChromatogramMSD chromatogramMSD = (IChromatogramMSD)chromatogram;
					if(traces.size() > 0) {
						/**
						 * Must be called with 'exclude' mode, so given ions will be 'excluded' from AbstractScan#removeIons.
						 */
						peak = PeakBuilderMSD.createPeak(chromatogramMSD, scanRange, startIntensity, stopIntensity, traces, MarkedTraceModus.EXCLUDE);
					} else {
						peak = PeakBuilderMSD.createPeak(chromatogramMSD, scanRange, startIntensity, stopIntensity);
					}
					peak.setDetectorDescription(PeakDetectorSettings.DETECTOR_DESCRIPTION);
				} else if(chromatogram instanceof IChromatogramCSD) {
					IChromatogramCSD chromatogramCSD = (IChromatogramCSD)chromatogram;
					peak = PeakBuilderCSD.createPeak(chromatogramCSD, scanRange, startIntensity, stopIntensity);
					peak.setDetectorDescription(PeakDetectorSettings.DETECTOR_DESCRIPTION);
				} else if(chromatogram instanceof IChromatogramWSD) {
					IChromatogramWSD chromatogramWSD = (IChromatogramWSD)chromatogram;
					peak = PeakBuilderWSD.createPeak(chromatogramWSD, scanRange, startIntensity, stopIntensity);
					peak.setDetectorDescription(PeakDetectorSettings.DETECTOR_DESCRIPTION);
					if(traces.size() > 0) {
						/**
						 * Must be called with 'exclude' mode, so given ions will be 'excluded' from AbstractScan#removeIons.
						 */
						peak = PeakBuilderWSD.createPeak(chromatogramWSD, scanRange, startIntensity, stopIntensity, traces);
					} else {
						peak = PeakBuilderWSD.createPeak(chromatogramWSD, scanRange, startIntensity, stopIntensity);
					}
				}
			}
		} catch(PeakException e) {
			logger.warn(e);
		}
		//
		return peak;
	}

	private IScanRange optimizeRange(IChromatogram<? extends IPeak> chromatogram, int startScan, int stopScan, Set<Integer> traces) {

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

	private float getScanSignal(IChromatogram<? extends IPeak> chromatogram, int scanNumber, Set<Integer> traces) {

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

	private IPeak getReferencePeak(List<? extends IPeak> peaks, String referenceIdentifier) {

		for(IPeak peak : peaks) {
			float retentionIndex = peak.getPeakModel().getPeakMaximum().getRetentionIndex();
			IdentificationTargetComparator identificationTargetComparator = new IdentificationTargetComparator(SortOrder.DESC, retentionIndex);
			ILibraryInformation libraryInformation = IIdentificationTarget.getBestLibraryInformation(peak.getTargets(), identificationTargetComparator);
			if(libraryInformation != null) {
				if(referenceIdentifier.equals(libraryInformation.getName())) {
					return peak;
				}
			}
		}
		return null;
	}

	private boolean isUseRetentionIndex(AbstractSetting setting) {

		return PositionDirective.RETENTION_INDEX.equals(setting.getPositionDirective());
	}
}
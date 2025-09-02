/*******************************************************************************
 * Copyright (c) 2019, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 * Christoph Läubrich - use retention time range interface
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.support;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.chemclipse.csd.model.core.IChromatogramCSD;
import org.eclipse.chemclipse.csd.model.core.IChromatogramPeakCSD;
import org.eclipse.chemclipse.csd.model.core.IPeakCSD;
import org.eclipse.chemclipse.csd.model.core.support.PeakBuilderCSD;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IChromatogramPeak;
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
import org.eclipse.chemclipse.msd.model.core.IChromatogramPeakMSD;
import org.eclipse.chemclipse.msd.model.core.IPeakMSD;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.core.support.PeakBuilderMSD;
import org.eclipse.chemclipse.msd.model.xic.IExtractedIonSignal;
import org.eclipse.chemclipse.support.traces.DetectorType;
import org.eclipse.chemclipse.support.traces.ITrace;
import org.eclipse.chemclipse.support.traces.TraceFactory;
import org.eclipse.chemclipse.support.traces.TraceGeneric;
import org.eclipse.chemclipse.support.traces.TraceHighResMSD;
import org.eclipse.chemclipse.vsd.model.core.IChromatogramVSD;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramPeakWSD;
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

	public boolean isPeakRelevant(IPeak peak, String traces) {

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
					Set<Integer> traceSet = getTraceSet(null, traces);
					exitloop:
					for(int trace : traceSet) {
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
						Set<Integer> traceSet = getTraceSet(null, traces);
						exitloop:
						for(int trace : traceSet) {
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

		return isPeakRelevant;
	}

	public static int getStartScan(IChromatogram chromatogram, int retentionTime) {

		int startScan = chromatogram.getScanNumber(retentionTime);
		if(startScan <= 0) {
			startScan = 1;
		}
		return startScan;
	}

	public static int getStopScan(IChromatogram chromatogram, int retentionTime) {

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

		return new RetentionTimeRange(startRetentionTime, stopRetentionTime);
	}

	public IChromatogramPeak extractPeakByRetentionTime(IChromatogram chromatogram, int startRetentionTime, int stopRetentionTime, boolean includeBackground, boolean optimizeRange, String traces) {

		int startScan = getStartScan(chromatogram, startRetentionTime);
		int stopScan = getStopScan(chromatogram, stopRetentionTime);
		return extractPeakByScanRange(chromatogram, startScan, stopScan, includeBackground, optimizeRange, traces);
	}

	public IPeak extractPeakByRetentionTime(IChromatogram chromatogram, int startRetentionTime, int stopRetentionTime, float startIntensity, float stopIntensity, String traces) {

		int startScan = getStartScan(chromatogram, startRetentionTime);
		int stopScan = getStopScan(chromatogram, stopRetentionTime);
		return extractPeakByScanRange(chromatogram, startScan, stopScan, startIntensity, stopIntensity, traces);
	}

	public IChromatogramPeak extractPeakByScanRange(IChromatogram chromatogram, int startScan, int stopScan, boolean includeBackground, boolean optimizeRange, String traces) {

		IChromatogramPeak peak = null;

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
				if(chromatogram instanceof IChromatogramMSD chromatogramMSD) {
					/*
					 * Must be called with 'exclude' mode, so given ions will be 'excluded' from AbstractScan#removeIons.
					 */
					if(!traces.isEmpty()) {
						Set<Integer> traceSet = getTraceSet(chromatogram, traces);
						peak = PeakBuilderMSD.createPeak(chromatogramMSD, scanRange, includeBackground, traceSet, MarkedTraceModus.EXCLUDE);
					} else {
						peak = PeakBuilderMSD.createPeak(chromatogramMSD, scanRange, includeBackground);
					}
					peak.setDetectorDescription(PeakDetectorSettings.DETECTOR_DESCRIPTION);
					/*
					 * Only one trace.
					 */
				} else if(chromatogram instanceof IChromatogramCSD chromatogramCSD) {
					peak = PeakBuilderCSD.createPeak(chromatogramCSD, scanRange, includeBackground);
					peak.setDetectorDescription(PeakDetectorSettings.DETECTOR_DESCRIPTION);
				} else if(chromatogram instanceof IChromatogramWSD chromatogramWSD) {
					if(!traces.isEmpty()) {
						Set<Integer> traceSet = getTraceSet(chromatogram, traces);
						peak = PeakBuilderWSD.createPeak(chromatogramWSD, scanRange, includeBackground, traceSet, MarkedTraceModus.INCLUDE);
					} else {
						peak = PeakBuilderWSD.createPeak(chromatogramWSD, scanRange, includeBackground);
					}
					peak.setDetectorDescription(PeakDetectorSettings.DETECTOR_DESCRIPTION);
				} else if(chromatogram instanceof IChromatogramVSD) {
					/*
					 * VSD is not supported yet.
					 */
				}
			}
		} catch(PeakException e) {
			logger.warn(e);
		}
		return peak;
	}

	public IPeak extractPeakByScanRange(IChromatogram chromatogram, int startScan, int stopScan, float startIntensity, float stopIntensity, String traces) {

		IPeak peak = null;

		try {
			if(startScan > 0 && startScan < stopScan) {
				/*
				 * Get the scan range.
				 */
				IScanRange scanRange = new ScanRange(startScan, stopScan);
				/*
				 * Try to create a peak.
				 */
				if(chromatogram instanceof IChromatogramMSD chromatogramMSD) {
					if(!traces.isEmpty()) {
						/**
						 * Must be called with 'exclude' mode, so given ions will be 'excluded' from AbstractScan#removeIons.
						 */
						Set<Integer> traceSet = getTraceSet(chromatogram, traces);
						peak = PeakBuilderMSD.createPeak(chromatogramMSD, scanRange, startIntensity, stopIntensity, traceSet, MarkedTraceModus.EXCLUDE);
					} else {
						peak = PeakBuilderMSD.createPeak(chromatogramMSD, scanRange, startIntensity, stopIntensity);
					}
					peak.setDetectorDescription(PeakDetectorSettings.DETECTOR_DESCRIPTION);
				} else if(chromatogram instanceof IChromatogramCSD chromatogramCSD) {
					peak = PeakBuilderCSD.createPeak(chromatogramCSD, scanRange, startIntensity, stopIntensity);
					peak.setDetectorDescription(PeakDetectorSettings.DETECTOR_DESCRIPTION);
				} else if(chromatogram instanceof IChromatogramWSD chromatogramWSD) {
					peak = PeakBuilderWSD.createPeak(chromatogramWSD, scanRange, startIntensity, stopIntensity);
					peak.setDetectorDescription(PeakDetectorSettings.DETECTOR_DESCRIPTION);
					if(!traces.isEmpty()) {
						/**
						 * Must be called with 'exclude' mode, so given ions will be 'excluded' from AbstractScan#removeIons.
						 */
						Set<Integer> traceSet = getTraceSet(chromatogram, traces);
						peak = PeakBuilderWSD.createPeak(chromatogramWSD, scanRange, startIntensity, stopIntensity, traceSet);
					} else {
						peak = PeakBuilderWSD.createPeak(chromatogramWSD, scanRange, startIntensity, stopIntensity);
					}
				} else if(chromatogram instanceof IChromatogramVSD) {
					/*
					 * VSD is not supported yet.
					 */
				}
			}
		} catch(PeakException e) {
			logger.warn(e);
		}

		return peak;
	}

	public static void addPeak(IChromatogram chromatogram, IPeak peak) {

		if(chromatogram instanceof IChromatogramMSD chromatogramMSD && peak instanceof IChromatogramPeakMSD peakMSD) {
			chromatogramMSD.getPeaks().add(peakMSD);
		} else if(chromatogram instanceof IChromatogramCSD chromatogramCSD && peak instanceof IChromatogramPeakCSD peakCSD) {
			chromatogramCSD.getPeaks().add(peakCSD);
		} else if(chromatogram instanceof IChromatogramWSD chromatogramWSD && peak instanceof IChromatogramPeakWSD peakWSD) {
			chromatogramWSD.getPeaks().add(peakWSD);
		}
	}

	private IScanRange optimizeRange(IChromatogram chromatogram, int startScan, int stopScan, String traces) {

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

		return new ScanRange(startScanOptimized, stopScanOptimized);
	}

	private float getScanSignal(IChromatogram chromatogram, int scanNumber, String traces) {

		float scanSignal = 0.0f;
		IScan scan = chromatogram.getScan(scanNumber);
		if(scan instanceof IScanMSD scanMSD) {
			IExtractedIonSignal extractedIonSignal = scanMSD.getExtractedIonSignal();
			Set<Integer> traceSet = getTraceSet(chromatogram, traces);
			for(int trace : traceSet) {
				scanSignal += extractedIonSignal.getAbundance(trace);
			}
		} else {
			scanSignal = scan.getTotalSignal();
		}

		return scanSignal;
	}

	private IPeak getReferencePeak(List<? extends IPeak> peaks, String referenceIdentifier) {

		for(IPeak peak : peaks) {
			ILibraryInformation libraryInformation = IIdentificationTarget.getLibraryInformation(peak);
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

	private Set<Integer> getTraceSet(IChromatogram chromatogram, String content) {

		/*
		 * Traces (TODO)
		 */
		if(chromatogram != null) {
			DetectorType detectorType = getDetectorType(chromatogram);
			Class<? extends ITrace> clazz = TraceFactory.getTraceType(content, detectorType);
			if(clazz.equals(TraceHighResMSD.class)) {
				/*
				 * Make a decision to use high resolution traces.
				 */
			}
		}

		Set<Integer> traces = new HashSet<>();
		for(ITrace trace : TraceFactory.parseTraces(content, TraceGeneric.class)) {
			traces.add((int)Math.round(trace.getValue()));
		}

		return traces;
	}

	private DetectorType getDetectorType(IChromatogram chromatogram) {

		DetectorType detectorType;
		if(chromatogram instanceof IChromatogramMSD) {
			detectorType = DetectorType.MSD;
		} else if(chromatogram instanceof IChromatogramVSD) {
			detectorType = DetectorType.VSD;
		} else if(chromatogram instanceof IChromatogramWSD) {
			detectorType = DetectorType.WSD;
		} else {
			detectorType = DetectorType.AUTO;
		}

		return detectorType;
	}
}
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

import org.eclipse.chemclipse.chromatogram.msd.filter.supplier.xpass.filter.XPassFilter;
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
import org.eclipse.chemclipse.model.core.support.HeaderField;
import org.eclipse.chemclipse.model.exceptions.PeakException;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.model.support.IScanRange;
import org.eclipse.chemclipse.model.support.RetentionIndexMap;
import org.eclipse.chemclipse.model.support.RetentionIndexMath;
import org.eclipse.chemclipse.model.support.ScanRange;
import org.eclipse.chemclipse.msd.model.core.AbstractIon;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IChromatogramPeakMSD;
import org.eclipse.chemclipse.msd.model.core.IPeakMSD;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.core.support.PeakBuilderMSD;
import org.eclipse.chemclipse.msd.model.support.HighResolutionSupport;
import org.eclipse.chemclipse.msd.model.xic.IExtractedIonSignal;
import org.eclipse.chemclipse.support.traces.DetectorType;
import org.eclipse.chemclipse.support.traces.ITrace;
import org.eclipse.chemclipse.support.traces.TraceFactory;
import org.eclipse.chemclipse.support.traces.TraceGeneric;
import org.eclipse.chemclipse.support.traces.TraceHighResMSD;
import org.eclipse.chemclipse.support.traces.TraceTandemMSD;
import org.eclipse.chemclipse.vsd.model.core.IChromatogramVSD;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramPeakWSD;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.chemclipse.wsd.model.core.IPeakWSD;
import org.eclipse.chemclipse.wsd.model.core.IScanWSD;
import org.eclipse.chemclipse.wsd.model.core.support.PeakBuilderWSD;
import org.eclipse.chemclipse.wsd.model.xwc.IExtractedWavelengthSignal;

import net.openchrom.xxd.process.supplier.templates.model.AbstractSetting;
import net.openchrom.xxd.process.supplier.templates.model.DefaultSetting;
import net.openchrom.xxd.process.supplier.templates.model.IntensityRange;
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
					Set<Integer> traceSet = getTraceSet(traces);
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
						Set<Integer> traceSet = getTraceSet(traces);
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

	public RetentionTimeRange getRetentionTimeRange(List<? extends IPeak> peaks, AbstractSetting setting, String positionRelativePeakName, RetentionIndexMap retentionIndexMap) {

		/*
		 * Retention Time (milliseconds)
		 */
		int startRetentionTime = 0;
		int stopRetentionTime = 0;

		if(!positionRelativePeakName.isEmpty()) {
			/*
			 * Position via Reference
			 */
			IPeak peak = getReferencePeak(peaks, positionRelativePeakName);
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
							/*
							 * RI
							 */
							retentionIndexStart += setting.getPositionStart();
							retentionIndexStop += setting.getPositionStop();
							/*
							 * Position
							 */
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

		IScanRange scanRange = optimizeRange ? optimizeRange(chromatogram, startScan, stopScan, traces) : new ScanRange(startScan, stopScan);
		IntensityRange intensityRange = null; // On purpose: no MM or CB modus

		return extractPeakByScanRange(chromatogram, scanRange, intensityRange, includeBackground, traces);
	}

	public IChromatogramPeak extractPeakByScanRange(IChromatogram chromatogram, int startScan, int stopScan, float startIntensity, float stopIntensity, String traces) {

		IScanRange scanRange = new ScanRange(startScan, stopScan); // On purpose: no optimization of the peak range
		IntensityRange intensityRange = new IntensityRange(startIntensity, stopIntensity);

		return extractPeakByScanRange(chromatogram, scanRange, intensityRange, false, traces);
	}

	private IChromatogramPeak extractPeakByScanRange(IChromatogram chromatogram, IScanRange scanRange, IntensityRange intensityRange, boolean includeBackground, String traces) {

		IChromatogramPeak peak = null;

		int startScan = scanRange.getStartScan();
		int stopScan = scanRange.getStopScan();
		if(startScan > 0 && startScan < stopScan) {
			try {
				/*
				 * Try to create a peak.
				 */
				if(chromatogram instanceof IChromatogramMSD chromatogramMSD) {
					peak = extractChromatogramPeakMSD(chromatogramMSD, scanRange, traces, intensityRange, includeBackground);
				} else if(chromatogram instanceof IChromatogramCSD chromatogramCSD) {
					peak = extractChromatogramPeakCSD(chromatogramCSD, scanRange, intensityRange, includeBackground);
				} else if(chromatogram instanceof IChromatogramWSD chromatogramWSD) {
					peak = extractChromatogramPeakWSD(chromatogramWSD, scanRange, traces, intensityRange, includeBackground);
				} else if(chromatogram instanceof IChromatogramVSD) {
				}
			} catch(PeakException e) {
				logger.warn(e);
			}
		}
		/*
		 * Detector Description
		 */
		if(peak != null) {
			peak.setDetectorDescription(PeakDetectorSettings.DETECTOR_DESCRIPTION);
		}
		return peak;
	}

	private IChromatogramPeak extractChromatogramPeakCSD(IChromatogramCSD chromatogram, IScanRange scanRange, IntensityRange intensityRange, boolean includeBackground) {

		IChromatogramPeak peak = null;
		if(intensityRange != null) {
			peak = PeakBuilderCSD.createPeak(chromatogram, scanRange, intensityRange.getStartIntensity(), intensityRange.getStopIntensity());
		} else {
			peak = PeakBuilderCSD.createPeak(chromatogram, scanRange, includeBackground);
		}

		return peak;
	}

	private IChromatogramPeak extractChromatogramPeakMSD(IChromatogramMSD chromatogram, IScanRange scanRange, String traces, IntensityRange intensityRange, boolean includeBackground) {

		IChromatogramPeak peak = null;
		if(!traces.isEmpty()) {
			DetectorType detectorType = DetectorType.MSD;
			Class<? extends ITrace> clazz = TraceFactory.getTraceType(traces, detectorType);
			if(clazz.equals(TraceHighResMSD.class)) {
				/*
				 * HighResMS
				 */
				peak = extractPeakByScanRangeHighResolutionMS(chromatogram, scanRange, traces, intensityRange, includeBackground);
			} else if(clazz.equals(TraceTandemMSD.class)) {
				/*
				 * TODO TandemMS
				 */
			} else {
				/*
				 * NominalMS
				 * Must be called with 'exclude' mode, so given ions will be 'excluded' from AbstractScan#removeIons.
				 */
				if(intensityRange != null) {
					peak = PeakBuilderMSD.createPeak(chromatogram, scanRange, intensityRange.getStartIntensity(), intensityRange.getStopIntensity(), getTraceSet(traces), MarkedTraceModus.EXCLUDE);
				} else {
					peak = PeakBuilderMSD.createPeak(chromatogram, scanRange, includeBackground, getTraceSet(traces), MarkedTraceModus.EXCLUDE);
				}
			}
		} else {
			if(intensityRange != null) {
				peak = PeakBuilderMSD.createPeak(chromatogram, scanRange, intensityRange.getStartIntensity(), intensityRange.getStopIntensity());
			} else {
				peak = PeakBuilderMSD.createPeak(chromatogram, scanRange, includeBackground);
			}
		}

		return peak;
	}

	private IChromatogramPeak extractChromatogramPeakWSD(IChromatogramWSD chromatogram, IScanRange scanRange, String traces, IntensityRange intensityRange, boolean includeBackground) {

		IChromatogramPeak peak = null;
		if(!traces.isEmpty()) {
			if(intensityRange != null) {
				Set<Integer> traceSet = getTraceSet(traces);
				peak = PeakBuilderWSD.createPeak(chromatogram, scanRange, intensityRange.getStartIntensity(), intensityRange.getStopIntensity(), traceSet);
			} else {
				peak = PeakBuilderWSD.createPeak(chromatogram, scanRange, includeBackground, getTraceSet(traces), MarkedTraceModus.INCLUDE);
			}
		} else {
			if(intensityRange != null) {
				peak = PeakBuilderWSD.createPeak(chromatogram, scanRange, intensityRange.getStartIntensity(), intensityRange.getStopIntensity());
			} else {
				peak = PeakBuilderWSD.createPeak(chromatogram, scanRange, includeBackground);
			}
		}

		return peak;
	}

	private IChromatogramPeak extractPeakByScanRangeHighResolutionMS(IChromatogramMSD chromatogram, IScanRange scanRange, String traces, IntensityRange intensityRange, boolean includeBackground) {

		IChromatogramPeak peak = null;
		Set<TraceHighResMSD> tracesHighResolution = new HashSet<>(TraceFactory.parseTraces(traces, TraceHighResMSD.class));
		boolean enforceFullTimeRange = true;
		boolean separateTraces = false;
		List<IChromatogramMSD> chromatograms = HighResolutionSupport.extractHighResolutionData(chromatogram, HeaderField.DATA_NAME, enforceFullTimeRange, tracesHighResolution, separateTraces);
		if(chromatograms.size() == 1) {
			IChromatogramMSD chromatogramMSD = chromatograms.get(0);
			for(IScan scan : chromatogramMSD.getScans()) {
				if(scan instanceof IScanMSD massSpectrum) {
					XPassFilter.nominalize(massSpectrum);
				}
			}
			Set<Integer> traceSet = new HashSet<>();
			for(TraceHighResMSD traceHighResMSD : tracesHighResolution) {
				traceSet.add(AbstractIon.getIon(traceHighResMSD.getValue()));
			}
			/*
			 * Must be called with 'exclude' mode, so given ions will be 'excluded' from AbstractScan#removeIons.
			 */
			if(intensityRange != null) {
				peak = PeakBuilderMSD.createPeak(chromatogramMSD, scanRange, intensityRange.getStartIntensity(), intensityRange.getStopIntensity(), traceSet, MarkedTraceModus.EXCLUDE);
			} else {
				peak = PeakBuilderMSD.createPeak(chromatogramMSD, scanRange, includeBackground, traceSet, MarkedTraceModus.EXCLUDE);
			}
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
			Set<Integer> traceSet = getTraceSet(traces);
			for(int trace : traceSet) {
				scanSignal += extractedIonSignal.getAbundance(trace);
			}
		} else {
			scanSignal = scan.getTotalSignal();
		}

		return scanSignal;
	}

	private IPeak getReferencePeak(List<? extends IPeak> peaks, String name) {

		for(IPeak peak : peaks) {
			ILibraryInformation libraryInformation = IIdentificationTarget.getLibraryInformation(peak);
			if(libraryInformation != null) {
				if(name.equals(libraryInformation.getName())) {
					return peak;
				}
			}
		}
		return null;
	}

	private boolean isUseRetentionIndex(AbstractSetting setting) {

		return PositionDirective.RETENTION_INDEX.equals(setting.getPositionDirective());
	}

	private Set<Integer> getTraceSet(String content) {

		Set<Integer> traces = new HashSet<>();
		for(ITrace trace : TraceFactory.parseTraces(content, TraceGeneric.class)) {
			traces.add((int)Math.round(trace.getValue()));
		}

		return traces;
	}
}
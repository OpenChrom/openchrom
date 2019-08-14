/*******************************************************************************
 * Copyright (c) 2016, 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.model.exceptions.ChromatogramIsNullException;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IChromatogramPeakMSD;
import org.eclipse.chemclipse.msd.model.exceptions.NoExtractedIonSignalStoredException;
import org.eclipse.chemclipse.msd.model.xic.ExtractedIonSignalExtractor;
import org.eclipse.chemclipse.msd.model.xic.IExtractedIonSignal;
import org.eclipse.chemclipse.msd.model.xic.IExtractedIonSignals;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.comparators.ScanMarkerComparator;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.CalculatedIonCertainties;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.IMassShift;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.IProcessorSettings;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.IScanMarker;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.ProcessorData;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.v1000.MassShift_v1000;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.v1000.ScanMarker_v1000;

public class MassShiftDetector {

	public static final String PROCESSOR_FILE_EXTENSION = ".mdp";
	//
	public static final int MIN_ISOTOPE_LEVEL = 0;
	public static final int MAX_ISOTOPE_LEVEL = 3;
	public static final int INCREMENT_ISOTOPE_LEVEL = 1;
	//
	public static final int SCALE_CERTAINTY_MIN = 0; // 0%
	public static final int SCALE_CERTAINTY_MAX = 100; // 100%
	public static final int SCALE_CERTAINTY_INCREMENT = 1; // 1%
	public static final int SCALE_CERTAINTY_SELECTION = SCALE_CERTAINTY_MIN;
	//
	private static final float NORMALIZATION_BASE = SCALE_CERTAINTY_MAX;

	/**
	 * Detect the mass shift. The level defines which shifts shall be detected.
	 * 0 = m/z
	 * 1 = m/z +1
	 * 2 = m/z +2
	 * 3 = m/z +3
	 * 
	 * Map<Integer, Map<Integer, Map<Integer, Double>>>: ShiftLevel, Scan, m/z, Intensity
	 * 
	 * @param referenceChromatogram
	 * @param isotopeChromatogram
	 * @param useAbsDifference
	 * @param isotopeLevel
	 * @return CalculateIonCertainties
	 * @throws IllegalArgumentException
	 */
	public CalculatedIonCertainties calculateIonCertainties(IChromatogramMSD referenceChromatogram, IChromatogramMSD isotopeChromatogram, IProcessorSettings processorSettings, IProgressMonitor monitor) throws ChromatogramIsNullException, IllegalArgumentException {

		validateArguments(referenceChromatogram, isotopeChromatogram, processorSettings);
		/*
		 * Extract the m/z values.
		 */
		int startShiftLevel = processorSettings.getStartShiftLevel();
		int stopShiftLevel = processorSettings.getStopShiftLevel();
		int startScan = 1;
		int stopScan = Math.min(referenceChromatogram.getNumberOfScans(), isotopeChromatogram.getNumberOfScans());
		//
		CalculatedIonCertainties calculatedIonCertainties = new CalculatedIonCertainties();
		calculatedIonCertainties.setStartScan(startScan);
		calculatedIonCertainties.setStopScan(stopScan);
		calculatedIonCertainties.setStartRetentionTime(getRetentionTime(referenceChromatogram, startScan));
		calculatedIonCertainties.setStopRetentionTime(getRetentionTime(referenceChromatogram, stopScan));
		//
		IExtractedIonSignals referenceIonSignals = extractIonSignals(referenceChromatogram, processorSettings, startScan, stopScan);
		IExtractedIonSignals isotopeIonSignals = extractIonSignals(isotopeChromatogram, processorSettings, startScan, stopScan);
		List<Integer> peakScanNumbers = extractPeakScanNumbers(referenceChromatogram, isotopeChromatogram, processorSettings);
		/*
		 * Do the calculation.
		 */
		for(int shiftLevel = startShiftLevel; shiftLevel <= stopShiftLevel; shiftLevel++) {
			/*
			 * Get the m/z range.
			 * The last m/z values are skipped, dependent on the selected level.
			 * Create a new difference signals instance.
			 */
			int startIon = referenceIonSignals.getStartIon();
			int stopIon = referenceIonSignals.getStopIon() - shiftLevel;
			//
			calculatedIonCertainties.getShiftLevelStartIonMap().put(shiftLevel, startIon);
			calculatedIonCertainties.getShiftLevelStopIonMap().put(shiftLevel, stopIon);
			//
			Map<Integer, Map<Integer, Double>> ionSignalCertainties = new HashMap<Integer, Map<Integer, Double>>();
			calculatedIonCertainties.getMap().put(shiftLevel, ionSignalCertainties);
			/*
			 * Make the difference calculation.
			 */
			for(int scan = startScan; scan <= stopScan; scan++) {
				try {
					/*
					 * Get the signals and normalize the data on demand.
					 */
					IExtractedIonSignal referenceIonSignal = referenceIonSignals.getExtractedIonSignal(scan);
					IExtractedIonSignal isotopeIonSignal = isotopeIonSignals.getExtractedIonSignal(scan);
					/*
					 * Only extract certain scans, when using peaks.
					 */
					boolean extractScan = true;
					if(processorSettings.isUsePeaks()) {
						if(!peakScanNumbers.contains(scan)) {
							extractScan = false;
						}
					}
					//
					if(extractScan) {
						Map<Integer, Double> extractedIonSignalCertainties = calculateIonSignalCertainty(processorSettings, referenceIonSignal, isotopeIonSignal, scan, shiftLevel, startIon, stopIon, monitor);
						ionSignalCertainties.put(scan, extractedIonSignalCertainties);
					}
					//
				} catch(NoExtractedIonSignalStoredException e) {
					//
				}
			}
		}
		return calculatedIonCertainties;
	}

	/**
	 * Extract possible shift marker.
	 * 
	 * @param processorData
	 * @param monitor
	 * @return List<IScanMarker>
	 */
	public List<IScanMarker> extractMassShiftMarker(ProcessorData processorData, IProgressMonitor monitor) {

		CalculatedIonCertainties calculatedIonCertainties = processorData.getCalculatedIonCertainties();
		Map<Integer, Integer> levelCertainty = processorData.getLevelCertainty();
		IChromatogramMSD referenceChromatogram = processorData.getReferenceChromatogram();
		IChromatogramMSD isotopeChromatogram = processorData.getIsotopeChromatogram();
		//
		Map<Integer, IScanMarker> scanMarkerMap = new HashMap<Integer, IScanMarker>();
		//
		for(Map.Entry<Integer, Map<Integer, Map<Integer, Double>>> massShiftEntry : calculatedIonCertainties.getMap().entrySet()) {
			//
			int isotopeLevel = massShiftEntry.getKey();
			double certaintyThreshold = (levelCertainty.get(isotopeLevel) != null) ? levelCertainty.get(isotopeLevel) : SCALE_CERTAINTY_SELECTION;
			//
			monitor.subTask("Calculate Marker: " + isotopeLevel + " -> Certainty: " + certaintyThreshold);
			//
			for(Map.Entry<Integer, Map<Integer, Double>> scanElement : massShiftEntry.getValue().entrySet()) {
				/*
				 * Scan
				 */
				int scanNumber = scanElement.getKey();
				int retentionTimeReference = getRetentionTime(referenceChromatogram, scanNumber);
				int retentionTimeIsotope = getRetentionTime(isotopeChromatogram, scanNumber);
				//
				for(Map.Entry<Integer, Double> ionSignalCertainty : scanElement.getValue().entrySet()) {
					double mz = ionSignalCertainty.getKey();
					double certainty = ionSignalCertainty.getValue();
					if(certainty >= certaintyThreshold) {
						/*
						 * Create a mass shift marker if not already available.
						 */
						IScanMarker scanMarker = scanMarkerMap.get(scanNumber);
						if(scanMarker == null) {
							scanMarker = new ScanMarker_v1000(scanNumber);
							scanMarker.setRetentionTimeReference(retentionTimeReference);
							scanMarker.setRetentionTimeIsotope(retentionTimeIsotope);
							scanMarkerMap.put(scanNumber, scanMarker);
						}
						/*
						 * Add the mass shift information.
						 */
						IMassShift massShift = new MassShift_v1000(mz, isotopeLevel, certainty);
						massShift.setRetentionTimeReference(retentionTimeReference);
						massShift.setRetentionTimeIsotope(retentionTimeIsotope);
						scanMarker.getMassShifts().add(massShift);
					}
				}
			}
		}
		/*
		 * Extract and sort the list.
		 */
		List<IScanMarker> scanMarkerList = new ArrayList<IScanMarker>(scanMarkerMap.values());
		Collections.sort(scanMarkerList, new ScanMarkerComparator());
		return scanMarkerList;
	}

	private int getRetentionTime(IChromatogramMSD chromatogram, int scanNumber) {

		if(chromatogram != null) {
			IScan scan = chromatogram.getScan(scanNumber);
			if(scan != null) {
				return scan.getRetentionTime();
			}
		}
		return 0;
	}

	private Map<Integer, Double> calculateIonSignalCertainty(IProcessorSettings processorSettings, IExtractedIonSignal referenceIonSignal, IExtractedIonSignal isotopeIonSignal, int scan, int shiftLevel, int startIon, int stopIon, IProgressMonitor monitor) {

		Map<Integer, Double> ionSignalCertainties = new HashMap<Integer, Double>();
		/*
		 * Select the threshold strategy.
		 */
		float thresholdIntensity = getThresholdIntensity(processorSettings, referenceIonSignal);
		/*
		 * Calculate the certainty.
		 */
		for(int mz = startIon; mz <= stopIon; mz++) {
			//
			double certainty = NORMALIZATION_BASE; // 100 == certain -> best match
			double intensityReference = referenceIonSignal.getAbundance(mz);
			/*
			 * Filter ions.
			 */
			if(intensityReference >= thresholdIntensity) {
				/*
				 * Calculate the m/z difference.
				 */
				monitor.subTask("Calculate Level: " + shiftLevel + " -> Scan: " + scan + " -> m/z: " + mz);
				//
				double intensityIsotope = isotopeIonSignal.getAbundance(mz + shiftLevel);
				double intensityMax = Math.max(intensityReference, intensityIsotope);
				double intensityDelta = Math.abs(intensityReference - intensityIsotope);
				//
				if(intensityMax != 0) {
					/*
					 * Note that the certainty scale is switched when using the
					 * isotopeLevel > 0, hence adjust it.
					 */
					certainty = NORMALIZATION_BASE / intensityMax * intensityDelta;
					if(shiftLevel > 0) {
						certainty = NORMALIZATION_BASE - certainty;
					}
					ionSignalCertainties.put(mz, certainty);
				}
			}
		}
		//
		return ionSignalCertainties;
	}

	private List<Integer> extractPeakScanNumbers(IChromatogramMSD referenceChromatogram, IChromatogramMSD isotopeChromatogram, IProcessorSettings processorSettings) {

		/*
		 * Extract the peaks on demand.
		 */
		SortedSet<Integer> sortedScanNumbers = new TreeSet<Integer>();
		if(processorSettings.isUsePeaks()) {
			/*
			 * Reference
			 */
			if(referenceChromatogram.getNumberOfPeaks() > 0) {
				sortedScanNumbers.addAll(extractPeakScanNumbers(referenceChromatogram));
			}
			/*
			 * Isotope
			 */
			if(isotopeChromatogram.getNumberOfPeaks() > 0) {
				sortedScanNumbers.addAll(extractPeakScanNumbers(isotopeChromatogram));
			}
		}
		//
		return new ArrayList<Integer>(sortedScanNumbers);
	}

	private List<Integer> extractPeakScanNumbers(IChromatogramMSD chromatogram) {

		List<Integer> peakScanNumbers = new ArrayList<Integer>();
		//
		for(IChromatogramPeakMSD peakMSD : chromatogram.getPeaks()) {
			peakScanNumbers.add(peakMSD.getScanMax());
		}
		//
		return peakScanNumbers;
	}

	private float getThresholdIntensity(IProcessorSettings processorSettings, IExtractedIonSignal extractedIonSignal) {

		float thresholdIntensity;
		switch(processorSettings.getIonSelectionStrategy()) {
			case IProcessorSettings.STRATEGY_SELECT_ALL:
				thresholdIntensity = 0.0f;
				break;
			case IProcessorSettings.STRATEGY_ABOVE_MEAN:
				thresholdIntensity = extractedIonSignal.getMeanIntensity();
				break;
			case IProcessorSettings.STRATEGY_ABOVE_MEDIAN:
				thresholdIntensity = extractedIonSignal.getMedianIntensity();
				break;
			case IProcessorSettings.STRATEGY_N_HIGHEST_INTENSITY:
				thresholdIntensity = extractedIonSignal.getNthHighestIntensity(processorSettings.getNumberHighestIntensityMZ());
				break;
			default:
				thresholdIntensity = 0.0f;
		}
		return thresholdIntensity;
	}

	private void validateArguments(IChromatogramMSD referenceChromatogram, IChromatogramMSD isotopeChromatogram, IProcessorSettings processorSettings) throws IllegalArgumentException {

		if(referenceChromatogram == null) {
			throw new IllegalArgumentException("The reference chromatogram must be not null.");
		}
		//
		if(isotopeChromatogram == null) {
			throw new IllegalArgumentException("The isotope chromatogram must be not null.");
		}
		//
		if(processorSettings == null) {
			throw new IllegalArgumentException("The processor settings must be not null.");
		}
		//
		int startShiftLevel = processorSettings.getStartShiftLevel();
		int stopShiftLevel = processorSettings.getStopShiftLevel();
		//
		if(startShiftLevel < MIN_ISOTOPE_LEVEL || startShiftLevel > MAX_ISOTOPE_LEVEL) {
			throw new IllegalArgumentException("The start shift level is not valid.");
		}
		//
		if(stopShiftLevel < MIN_ISOTOPE_LEVEL || stopShiftLevel > MAX_ISOTOPE_LEVEL) {
			throw new IllegalArgumentException("The stop shift level is not valid.");
		}
		//
		if(stopShiftLevel < startShiftLevel) {
			throw new IllegalArgumentException("The stop shift level must be >= start shift level.");
		}
	}

	private IExtractedIonSignals extractIonSignals(IChromatogramMSD chromatogram, IProcessorSettings processorSettings, int startScan, int stopScan) throws ChromatogramIsNullException {

		ExtractedIonSignalExtractor extractedIonSignalExtractor = new ExtractedIonSignalExtractor(chromatogram);
		IExtractedIonSignals extractedIonSignals = extractedIonSignalExtractor.getExtractedIonSignals(startScan, stopScan);
		if(processorSettings.isNormalizeData()) {
			for(IExtractedIonSignal extractedIonSignal : extractedIonSignals.getExtractedIonSignals()) {
				extractedIonSignal.normalize(NORMALIZATION_BASE);
			}
		}
		return extractedIonSignals;
	}
}

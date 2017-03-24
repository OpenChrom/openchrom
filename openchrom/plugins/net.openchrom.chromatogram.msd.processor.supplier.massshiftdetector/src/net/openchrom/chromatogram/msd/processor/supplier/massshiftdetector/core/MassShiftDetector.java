/*******************************************************************************
 * Copyright (c) 2016, 2017 Lablicate GmbH.
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

import org.eclipse.chemclipse.model.exceptions.ChromatogramIsNullException;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.exceptions.NoExtractedIonSignalStoredException;
import org.eclipse.chemclipse.msd.model.xic.ExtractedIonSignalExtractor;
import org.eclipse.chemclipse.msd.model.xic.IExtractedIonSignal;
import org.eclipse.chemclipse.msd.model.xic.IExtractedIonSignals;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.comparators.ScanMarkerComparator;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.MassShift;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.ScanMarker;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.ValueRange;

public class MassShiftDetector {

	public static final String PROCESSOR_FILE_EXTENSION = ".mdp";
	//
	public static final int MIN_ISOTOPE_LEVEL = 0;
	public static final int MAX_ISOTOPE_LEVEL = 3;
	public static final int INCREMENT_ISOTOPE_LEVEL = 1;
	//
	public static final int SCALE_MIN = 0; // 0%
	public static final int SCALE_MAX = 1000; // 100%
	public static final int SCALE_INCREMENT = 1; // 0.001
	public static final int SCALE_SELECTION = SCALE_MAX;
	/*
	 * MIN and MAX are used to display the range in the heatmap.
	 * SCALE_MAX = 100% in range but 60% in the heatmap.
	 */
	private static final double MAX_PERCENTAGE_HIT = 60.0d;
	//
	public static final int SCALE_OFFSET = (int)(SCALE_MAX / MAX_PERCENTAGE_HIT * 100.0d) - SCALE_MAX;
	public static final int MIN_VALUE = -SCALE_MAX - SCALE_OFFSET;
	public static final int MAX_VALUE = SCALE_MAX + SCALE_OFFSET;
	//
	private static final float NORMALIZATION_BASE = SCALE_MAX;

	/**
	 * Detect the mass shift. The level defines which shifts shall be detected.
	 * 0 = m/z
	 * 1 = m/z +1
	 * 2 = m/z +2
	 * 3 = m/z +3
	 * 
	 * Map<Integer, Map<Integer, Map<Integer, Double>>>: ShiftLevel, Scan, m/z, Intensity
	 * 
	 * @param chromatogramReference
	 * @param chromatogramShifted
	 * @param useAbsDifference
	 * @param isotopeLevel
	 * @return Map<Integer, Map<Integer, Map<Integer, Double>>>
	 * @throws IllegalArgumentException
	 */
	public Map<Integer, Map<Integer, Map<Integer, Double>>> detectMassShifts(IChromatogramMSD chromatogramReference, IChromatogramMSD chromatogramShifted, int isotopeLevel, IProgressMonitor monitor) throws ChromatogramIsNullException, IllegalArgumentException {

		if(isotopeLevel < MIN_ISOTOPE_LEVEL || isotopeLevel > MAX_ISOTOPE_LEVEL) {
			throw new IllegalArgumentException("The level is not valid.");
		}
		/*
		 * Extract the m/z values.
		 */
		Map<Integer, Map<Integer, Map<Integer, Double>>> differenceIonSignalsMap = new HashMap<Integer, Map<Integer, Map<Integer, Double>>>();
		int startScan = 1;
		int stopScan = Math.min(chromatogramReference.getNumberOfScans(), chromatogramShifted.getNumberOfScans());
		IExtractedIonSignals extractedIonSignalsReference = extractAndNormalizeIonSignals(chromatogramReference, startScan, stopScan, NORMALIZATION_BASE);
		IExtractedIonSignals extractedIonSignalsShifted = extractAndNormalizeIonSignals(chromatogramShifted, startScan, stopScan, NORMALIZATION_BASE);
		/*
		 * Do the calculation.
		 */
		for(int shiftLevel = 0; shiftLevel <= isotopeLevel; shiftLevel++) {
			/*
			 * Get the m/z range.
			 * The last m/z values are skipped, dependent on the selected level.
			 * Create a new difference signals instance.
			 */
			int startIon = extractedIonSignalsReference.getStartIon();
			int stopIon = extractedIonSignalsReference.getStopIon() - shiftLevel;
			Map<Integer, Map<Integer, Double>> extractedIonSignalsDifference = new HashMap<Integer, Map<Integer, Double>>();
			differenceIonSignalsMap.put(shiftLevel, extractedIonSignalsDifference);
			/*
			 * Make the difference calculation.
			 */
			for(int scan = startScan; scan <= stopScan; scan++) {
				try {
					/*
					 * Get the scan.
					 */
					monitor.subTask("Calculate Level: " + shiftLevel + " -> Scan: " + scan);
					//
					IExtractedIonSignal extractedIonSignalReference = extractedIonSignalsReference.getExtractedIonSignal(scan);
					IExtractedIonSignal extractedIonSignalShifted = extractedIonSignalsShifted.getExtractedIonSignal(scan);
					Map<Integer, Double> extractedIonSignalDifference = new HashMap<Integer, Double>();
					extractedIonSignalsDifference.put(scan, extractedIonSignalDifference);
					//
					for(int ion = startIon; ion <= stopIon; ion++) {
						/*
						 * Calculate the m/z difference.
						 */
						double intensityReference = extractedIonSignalReference.getAbundance(ion);
						double intensityShifted = extractedIonSignalShifted.getAbundance(ion + shiftLevel);
						double intensityDifference = intensityReference - intensityShifted;
						/*
						 * 0 is the most likely value.
						 */
						if(isotopeLevel == 0) {
							if(intensityDifference < 0) {
								intensityDifference = -NORMALIZATION_BASE + intensityDifference;
							} else {
								intensityDifference = NORMALIZATION_BASE - intensityDifference;
							}
						}
						//
						extractedIonSignalDifference.put(ion, intensityDifference);
					}
				} catch(NoExtractedIonSignalStoredException e) {
					//
				}
			}
		}
		return differenceIonSignalsMap;
	}

	public List<ScanMarker> extractMassShiftMarker(Map<Integer, Map<Integer, Map<Integer, Double>>> massShifts, Map<Integer, ValueRange> levelGranularity) {

		Map<Integer, ScanMarker> scanMarkerMap = new HashMap<Integer, ScanMarker>();
		/*
		 * Default min/max ranges.
		 */
		double defaultMax = SCALE_MAX * MAX_PERCENTAGE_HIT / 100.0d;
		double defaultMin = -defaultMax;
		//
		for(Map.Entry<Integer, Map<Integer, Map<Integer, Double>>> massShift : massShifts.entrySet()) {
			//
			int isotopeLevel = massShift.getKey();
			if(isotopeLevel == 0) {
				continue;
			}
			double min;
			double max;
			//
			ValueRange valueRange = levelGranularity.get(isotopeLevel);
			if(valueRange != null) {
				min = valueRange.getMin();
				max = valueRange.getMax();
			} else {
				min = defaultMin;
				max = defaultMax;
			}
			// TODO improve
			min = -3.0d;
			max = 3.0d;
			//
			for(Map.Entry<Integer, Map<Integer, Double>> scanElement : massShift.getValue().entrySet()) {
				int scan = scanElement.getKey();
				for(Map.Entry<Integer, Double> mzElement : scanElement.getValue().entrySet()) {
					double mz = mzElement.getKey();
					double deltaIntensity = mzElement.getValue();
					if(deltaIntensity >= min && deltaIntensity <= max) {
						/*
						 * Create a mass shift marker if not already available.
						 */
						ScanMarker scanMarker = scanMarkerMap.get(scan);
						if(scanMarker == null) {
							scanMarker = new ScanMarker(scan);
							scanMarkerMap.put(scan, scanMarker);
						}
						/*
						 * Add the mass shift information.
						 */
						scanMarker.getMassShifts().add(new MassShift(mz, isotopeLevel, deltaIntensity));
					}
				}
			}
		}
		/*
		 * Extract and sort the list.
		 */
		List<ScanMarker> scanMarkerList = new ArrayList<ScanMarker>(scanMarkerMap.values());
		Collections.sort(scanMarkerList, new ScanMarkerComparator());
		return scanMarkerList;
	}

	private IExtractedIonSignals extractAndNormalizeIonSignals(IChromatogramMSD chromatogram, int startScan, int stopScan, float normalizationBase) throws ChromatogramIsNullException {

		ExtractedIonSignalExtractor extractedIonSignalExtractor = new ExtractedIonSignalExtractor(chromatogram);
		IExtractedIonSignals extractedIonSignals = extractedIonSignalExtractor.getExtractedIonSignals(startScan, stopScan);
		for(int i = startScan; i <= stopScan; i++) {
			try {
				IExtractedIonSignal extractedIonSignal = extractedIonSignals.getExtractedIonSignal(i);
				extractedIonSignal.normalize(normalizationBase);
			} catch(NoExtractedIonSignalStoredException e) {
				//
			}
		}
		return extractedIonSignals;
	}
}

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

public class MassShiftDetector {

	public static final String PROCESSOR_FILE_EXTENSION = ".mdp";
	//
	public static final int MIN_ISOTOPE_LEVEL = 0;
	public static final int MAX_ISOTOPE_LEVEL = 3;
	public static final int INCREMENT_ISOTOPE_LEVEL = 1;
	//
	public static final int SCALE_UNCERTAINTY_MIN = 0; // 0%
	public static final int SCALE_UNCERTAINTY_MAX = 100; // 100%
	public static final int SCALE_UNCERTAINTY_INCREMENT = 1; // 1%
	public static final int SCALE_UNCERTAINTY_SELECTION = SCALE_UNCERTAINTY_MAX;
	//
	private static final float NORMALIZATION_BASE = SCALE_UNCERTAINTY_MAX;

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
	 * @return Map<Integer, Map<Integer, Map<Integer, Double>>>
	 * @throws IllegalArgumentException
	 */
	public Map<Integer, Map<Integer, Map<Integer, Double>>> detectMassShifts(IChromatogramMSD referenceChromatogram, IChromatogramMSD isotopeChromatogram, int startShiftLevel, int stopShiftLevel, IProgressMonitor monitor) throws ChromatogramIsNullException, IllegalArgumentException {

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
		/*
		 * Extract the m/z values.
		 */
		Map<Integer, Map<Integer, Map<Integer, Double>>> uncertaintyIonSignalsMap = new HashMap<Integer, Map<Integer, Map<Integer, Double>>>();
		int startScan = 1;
		int stopScan = Math.min(referenceChromatogram.getNumberOfScans(), isotopeChromatogram.getNumberOfScans());
		IExtractedIonSignals referenceIonSignals = extractAndNormalizeIonSignals(referenceChromatogram, startScan, stopScan, NORMALIZATION_BASE);
		IExtractedIonSignals isotopeIonSignals = extractAndNormalizeIonSignals(isotopeChromatogram, startScan, stopScan, NORMALIZATION_BASE);
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
			Map<Integer, Map<Integer, Double>> extractedIonSignalsDifference = new HashMap<Integer, Map<Integer, Double>>();
			uncertaintyIonSignalsMap.put(shiftLevel, extractedIonSignalsDifference);
			/*
			 * Make the difference calculation.
			 */
			for(int scan = startScan; scan <= stopScan; scan++) {
				try {
					/*
					 * Get the scan.
					 */
					IExtractedIonSignal referenceIonSignal = referenceIonSignals.getExtractedIonSignal(scan);
					IExtractedIonSignal isotopeIonSignal = isotopeIonSignals.getExtractedIonSignal(scan);
					Map<Integer, Double> extractedIonSignalUncertainty = new HashMap<Integer, Double>();
					extractedIonSignalsDifference.put(scan, extractedIonSignalUncertainty);
					//
					for(int ion = startIon; ion <= stopIon; ion++) {
						/*
						 * Calculate the m/z difference.
						 */
						monitor.subTask("Calculate Level: " + shiftLevel + " -> Scan: " + scan + " -> m/z: " + ion);
						//
						double intensityReference = referenceIonSignal.getAbundance(ion);
						double uncertainty = NORMALIZATION_BASE; // 100 == uncertain -> no match
						/*
						 * Only calculate the value if it's above the median intensity.
						 */
						double intensityIsotope = isotopeIonSignal.getAbundance(ion + shiftLevel);
						double intensityMax = Math.max(intensityReference, intensityIsotope);
						double intensityDelta = Math.abs(intensityReference - intensityIsotope);
						/*
						 * Calculate the uncertainty.
						 */
						if(intensityMax != 0) {
							uncertainty = NORMALIZATION_BASE / intensityMax * intensityDelta;
							/*
							 * Note that the uncertainty scale is switched when using the
							 * isotopeLevel 0, hence adjust it.
							 */
							if(shiftLevel == 0) {
								uncertainty = NORMALIZATION_BASE - uncertainty;
							}
						}
						/*
						 * Set the uncertainty.
						 */
						extractedIonSignalUncertainty.put(ion, uncertainty);
					}
				} catch(NoExtractedIonSignalStoredException e) {
					//
				}
			}
		}
		return uncertaintyIonSignalsMap;
	}

	public List<ScanMarker> extractMassShiftMarker(Map<Integer, Map<Integer, Map<Integer, Double>>> massShifts, Map<Integer, Integer> levelUncertainty, IProgressMonitor monitor) {

		Map<Integer, ScanMarker> scanMarkerMap = new HashMap<Integer, ScanMarker>();
		//
		for(Map.Entry<Integer, Map<Integer, Map<Integer, Double>>> massShift : massShifts.entrySet()) {
			//
			int isotopeLevel = massShift.getKey();
			double threshold = (levelUncertainty.get(isotopeLevel) != null) ? levelUncertainty.get(isotopeLevel) : SCALE_UNCERTAINTY_SELECTION;
			//
			monitor.subTask("Calculate Marker: " + isotopeLevel + " -> Threshold: " + threshold);
			//
			for(Map.Entry<Integer, Map<Integer, Double>> scanElement : massShift.getValue().entrySet()) {
				int scan = scanElement.getKey();
				for(Map.Entry<Integer, Double> mzElement : scanElement.getValue().entrySet()) {
					double mz = mzElement.getKey();
					double uncertainty = mzElement.getValue();
					if(uncertainty <= threshold) {
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
						scanMarker.getMassShifts().add(new MassShift(mz, isotopeLevel, uncertainty));
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

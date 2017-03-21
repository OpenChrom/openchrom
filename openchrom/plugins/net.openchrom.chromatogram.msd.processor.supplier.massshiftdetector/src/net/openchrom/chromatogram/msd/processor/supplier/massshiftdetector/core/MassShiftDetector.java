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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.chemclipse.model.exceptions.ChromatogramIsNullException;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.exceptions.NoExtractedIonSignalStoredException;
import org.eclipse.chemclipse.msd.model.xic.ExtractedIonSignalExtractor;
import org.eclipse.chemclipse.msd.model.xic.IExtractedIonSignal;
import org.eclipse.chemclipse.msd.model.xic.IExtractedIonSignals;
import org.eclipse.core.runtime.IProgressMonitor;

public class MassShiftDetector {

	public static final int MIN_LEVEL = 0;
	public static final int MAX_LEVEL = 3;
	public static final int INCREMENT_LEVEL = 1;
	private static final float NORMALIZATION_BASE = 100.0f;

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
	 * @param level
	 * @return Map<Integer, Map<Integer, Map<Integer, Double>>>
	 * @throws IllegalArgumentException
	 */
	public Map<Integer, Map<Integer, Map<Integer, Double>>> detectMassShifts(IChromatogramMSD chromatogramReference, IChromatogramMSD chromatogramShifted, int level, boolean useAbsoluteValues, IProgressMonitor monitor) throws ChromatogramIsNullException, IllegalArgumentException {

		if(level < MIN_LEVEL || level > MAX_LEVEL) {
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
		for(int shiftLevel = 0; shiftLevel <= level; shiftLevel++) {
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
						double intensityDifference;
						if(useAbsoluteValues) {
							intensityDifference = Math.abs(intensityReference - intensityShifted);
						} else {
							intensityDifference = intensityReference - intensityShifted;
						}
						extractedIonSignalDifference.put(ion, intensityDifference);
					}
				} catch(NoExtractedIonSignalStoredException e) {
					//
				}
			}
		}
		return differenceIonSignalsMap;
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

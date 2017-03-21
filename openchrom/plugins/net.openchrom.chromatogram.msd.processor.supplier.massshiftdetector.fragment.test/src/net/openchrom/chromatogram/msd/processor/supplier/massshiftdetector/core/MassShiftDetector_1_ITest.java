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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.chemclipse.model.exceptions.ChromatogramIsNullException;
import org.eclipse.chemclipse.msd.converter.chromatogram.ChromatogramConverterMSD;
import org.eclipse.chemclipse.msd.converter.processing.chromatogram.IChromatogramMSDImportConverterProcessingInfo;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.exceptions.NoExtractedIonSignalStoredException;
import org.eclipse.core.runtime.NullProgressMonitor;

import junit.framework.TestCase;

public class MassShiftDetector_1_ITest extends TestCase {

	private MassShiftDetector massShiftDetector;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		massShiftDetector = new MassShiftDetector();
	}

	@Override
	protected void tearDown() throws Exception {

		massShiftDetector = null;
		super.tearDown();
	}

	public void test1() throws IllegalArgumentException, ChromatogramIsNullException, NoExtractedIonSignalStoredException, FileNotFoundException {

		String exportPath = "";
		File fileReference = new File("");
		File fileShifted = new File("");
		//
		extractAndPrintResults(fileReference, fileShifted, exportPath + "Abs", true);
		extractAndPrintResults(fileReference, fileShifted, exportPath + "Tot", false);
		//
		assertTrue(true);
	}

	private void extractAndPrintResults(File fileReference, File fileShifted, String exportPath, boolean useAbsValues) throws IllegalArgumentException, ChromatogramIsNullException, NoExtractedIonSignalStoredException, FileNotFoundException {

		int maxShiftLevel = 3;
		IChromatogramMSD chromatogramReference = getChromatogram(fileReference);
		IChromatogramMSD chromatogramShifted = getChromatogram(fileShifted);
		Map<Integer, Map<Integer, Map<Integer, Double>>> differenceIonSignalsMap = massShiftDetector.detectMassShifts(chromatogramReference, chromatogramShifted, maxShiftLevel, useAbsValues, new NullProgressMonitor());
		//
		for(int shiftLevel = 0; shiftLevel <= maxShiftLevel; shiftLevel++) {
			PrintWriter printWriter = new PrintWriter(new File(exportPath + shiftLevel + ".txt"));
			Map<Integer, Map<Integer, Double>> extractedIonSignalsDifference = differenceIonSignalsMap.get(shiftLevel);
			int startScan = Collections.min(extractedIonSignalsDifference.keySet());
			int stopScan = Collections.max(extractedIonSignalsDifference.keySet());
			int startIon = getStartIon(extractedIonSignalsDifference);
			int stopIon = getStopIon(extractedIonSignalsDifference);
			/*
			 * Print Header
			 */
			printWriter.print("SCAN");
			for(int ion = startIon; ion <= stopIon; ion++) {
				printWriter.print("\t");
				printWriter.print(ion);
			}
			printWriter.println("");
			/*
			 * Print data.
			 */
			for(int scan = startScan; scan <= stopScan; scan++) {
				printWriter.print(scan);
				Map<Integer, Double> extractedIonSignalDifference = extractedIonSignalsDifference.get(scan);
				for(int ion = startIon; ion <= stopIon; ion++) {
					printWriter.print("\t");
					printWriter.print(extractedIonSignalDifference.get(ion));
				}
				printWriter.println("");
			}
			//
			printWriter.flush();
			printWriter.close();
		}
	}

	private int getStartIon(Map<Integer, Map<Integer, Double>> extractedIonSignalsDifference) {

		Set<Integer> ions = new HashSet<Integer>();
		for(Map<Integer, Double> scan : extractedIonSignalsDifference.values()) {
			ions.add(Collections.min(scan.keySet()));
		}
		return Collections.min(ions);
	}

	private int getStopIon(Map<Integer, Map<Integer, Double>> extractedIonSignalsDifference) {

		Set<Integer> ions = new HashSet<Integer>();
		for(Map<Integer, Double> scan : extractedIonSignalsDifference.values()) {
			ions.add(Collections.max(scan.keySet()));
		}
		return Collections.max(ions);
	}

	private IChromatogramMSD getChromatogram(File file) {

		IChromatogramMSDImportConverterProcessingInfo processingInfo = ChromatogramConverterMSD.convert(file, new NullProgressMonitor());
		return processingInfo.getChromatogram();
	}
}

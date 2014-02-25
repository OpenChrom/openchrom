/*******************************************************************************
 * Copyright (c) 2013, 2014 Dr. Philip Wenig.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.chemclipse.chromatogram.msd.converter.supplier.cdf.io;

import net.chemclipse.chromatogram.model.signals.ITotalScanSignals;
import net.chemclipse.chromatogram.msd.converter.supplier.cdf.TestPathHelper;
import net.chemclipse.chromatogram.msd.model.core.IMassSpectrum;
import net.chemclipse.chromatogram.msd.model.xic.IExtractedIonSignals;

/**
 * IChromatogram OP8777<br/>
 * 
 * @author eselmeister
 */
public class CDFChromatogramReader_OP8777_1_ITest extends CDFChromatogramReaderTestCase {

	@Override
	protected void setUp() throws Exception {

		pathImport = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_OP8777);
		super.setUp();
	}

	private void testCDFChromatogramReader_1() {

		IMassSpectrum massSpectrum;
		ITotalScanSignals tic;
		IExtractedIonSignals xic;
		assertEquals("scanDelay", 5148, chromatogram.getScanDelay());
		assertEquals("scanInterval", 268, chromatogram.getScanInterval());
		assertEquals("operator", "Hamann", chromatogram.getOperator());
		assertEquals("file", fileImport, chromatogram.getFile());
		assertEquals("name", "OP8777", chromatogram.getName());
		assertTrue("date", chromatogram.getDate() != null);
		// assertEquals("date", 1127977020000l,
		// chromatogram.getDate().getTime());
		assertEquals("numberOfScans", 22435, chromatogram.getNumberOfScans());
		assertEquals("numberOfScanIons", 3403429, chromatogram.getNumberOfScanIons());
		assertEquals("startRetentionTime", 5148, chromatogram.getStartRetentionTime());
		assertEquals("stopRetentionTime", 5840076, chromatogram.getStopRetentionTime());
		assertEquals("minSignal", 18426.0f, chromatogram.getMinSignal());
		assertEquals("maxSignal", 10811341.0f, chromatogram.getMaxSignal());
		assertEquals("miscInfo", "199-7  57", chromatogram.getMiscInfo());
		tic = totalIonSignalExtractor.getTotalScanSignals();
		assertEquals("ITotalIonSignals size", 22435, tic.size());
		assertEquals("totalIonSignal", 8894604300.0f, chromatogram.getTotalSignal());
		xic = extractedIonSignalExtractor.getExtractedIonSignals();
		assertEquals("IExtractedIonSignals size", 22435, xic.size());
		xic = extractedIonSignalExtractor.getExtractedIonSignals(1.0f, 600.5f);
		assertEquals("IExtractedIonSignals size", 22435, xic.size());
		// --------------------test mass spectra
		massSpectrum = chromatogram.getSupplierScan(22436);
		assertTrue("massSpectrum", null == massSpectrum);
		massSpectrum = chromatogram.getSupplierScan(340);
		assertEquals("TotalSignal", 24893.0f, massSpectrum.getTotalSignal());
		massSpectrum = chromatogram.getSupplierScan(628);
		assertEquals("TotalSignal", 80763.0f, massSpectrum.getTotalSignal());
		massSpectrum = chromatogram.getSupplierScan(5726);
		assertEquals("TotalSignal", 79286.0f, massSpectrum.getTotalSignal());
		// --------------------test mass spectra
	}
}

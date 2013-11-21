/*******************************************************************************
 * Copyright (c) 2013 Dr. Philip Wenig.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.cdf.io;

import net.openchrom.chromatogram.model.signals.ITotalScanSignals;
import net.openchrom.chromatogram.msd.converter.supplier.cdf.TestPathHelper;
import net.openchrom.chromatogram.msd.model.core.IMassSpectrum;
import net.openchrom.chromatogram.msd.model.xic.IExtractedIonSignals;

/**
 * IChromatogram OP8786<br/>
 * 
 * @author eselmeister
 */
public class CDFChromatogramReader_OP8786_1_ITest extends CDFChromatogramReaderTestCase {

	@Override
	protected void setUp() throws Exception {

		pathImport = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_OP8786);
		super.setUp();
	}

	private void testCDFChromatogramReader_1() {

		IMassSpectrum massSpectrum;
		ITotalScanSignals tic;
		IExtractedIonSignals xic;
		assertEquals("scanDelay", 5149, chromatogram.getScanDelay());
		assertEquals("scanInterval", 268, chromatogram.getScanInterval());
		assertEquals("operator", "Hamann", chromatogram.getOperator());
		assertEquals("file", fileImport, chromatogram.getFile());
		assertEquals("name", "OP8786", chromatogram.getName());
		assertTrue("date", chromatogram.getDate() != null);
		// assertEquals("date", 1128043560000l,
		// chromatogram.getDate().getTime());
		assertEquals("numberOfScans", 22435, chromatogram.getNumberOfScans());
		assertEquals("numberOfScanIons", 3468104, chromatogram.getNumberOfScanIons());
		assertEquals("startRetentionTime", 5149, chromatogram.getStartRetentionTime());
		assertEquals("stopRetentionTime", 5840076, chromatogram.getStopRetentionTime());
		assertEquals("minSignal", 20653.0f, chromatogram.getMinSignal());
		assertEquals("maxSignal", 5475655.0f, chromatogram.getMaxSignal());
		assertEquals("miscInfo", "199-8  59", chromatogram.getMiscInfo());
		tic = totalIonSignalExtractor.getTotalScanSignals();
		assertEquals("ITotalIonSignals size", 22435, tic.size());
		assertEquals("totalIonSignal", 9039252500.0f, chromatogram.getTotalSignal());
		xic = extractedIonSignalExtractor.getExtractedIonSignals();
		assertEquals("IExtractedIonSignals size", 22435, xic.size());
		xic = extractedIonSignalExtractor.getExtractedIonSignals(1.0f, 600.5f);
		assertEquals("IExtractedIonSignals size", 22435, xic.size());
		// --------------------test mass spectra
		massSpectrum = chromatogram.getSupplierScan(22436);
		assertTrue("massSpectrum", null == massSpectrum);
		massSpectrum = chromatogram.getSupplierScan(340);
		assertEquals("TotalSignal", 28056.0f, massSpectrum.getTotalSignal());
		massSpectrum = chromatogram.getSupplierScan(628);
		assertEquals("TotalSignal", 87398.0f, massSpectrum.getTotalSignal());
		massSpectrum = chromatogram.getSupplierScan(5726);
		assertEquals("TotalSignal", 53362.0f, massSpectrum.getTotalSignal());
		// --------------------test mass spectra
	}
}

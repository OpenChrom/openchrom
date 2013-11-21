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
 * IChromatogram 080903_006<br/>
 * 
 * @author eselmeister
 */
public class CDFChromatogramReader_080903_006_1_ITest extends CDFChromatogramReaderTestCase {

	@Override
	protected void setUp() throws Exception {

		pathImport = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_080903_006);
		super.setUp();
	}

	private void testCDFChromatogramReader_1() {

		IMassSpectrum massSpectrum;
		ITotalScanSignals tic;
		IExtractedIonSignals xic;
		assertEquals("scanDelay", 5627, chromatogram.getScanDelay());
		assertEquals("scanInterval", 351, chromatogram.getScanInterval());
		assertEquals("operator", "", chromatogram.getOperator());
		assertEquals("file", fileImport, chromatogram.getFile());
		assertEquals("name", "080903_006", chromatogram.getName());
		assertTrue("date", chromatogram.getDate() != null);
		// assertEquals("date", 1220401800000l,
		// chromatogram.getDate().getTime());
		assertEquals("numberOfScans", 2755, chromatogram.getNumberOfScans());
		assertEquals("numberOfScanIons", 345710, chromatogram.getNumberOfScanIons());
		assertEquals("startRetentionTime", 5627, chromatogram.getStartRetentionTime());
		assertEquals("stopRetentionTime", 986888, chromatogram.getStopRetentionTime());
		assertEquals("minSignal", 15092.0f, chromatogram.getMinSignal());
		assertEquals("maxSignal", 62240624.0f, chromatogram.getMaxSignal());
		assertEquals("miscInfo", "MP1", chromatogram.getMiscInfo());
		tic = totalIonSignalExtractor.getTotalScanSignals();
		assertEquals("List<ITotalIonSignal> size", 2755, tic.size());
		assertEquals("totalIonSignal", 3988111100.0f, chromatogram.getTotalSignal());
		xic = extractedIonSignalExtractor.getExtractedIonSignals();
		assertEquals("IExtractedIonSignals size", 2755, xic.size());
		xic = extractedIonSignalExtractor.getExtractedIonSignals(1.0f, 600.5f);
		assertEquals("IExtractedIonSignals size", 2755, xic.size());
		// --------------------test mass spectra
		massSpectrum = chromatogram.getSupplierScan(5727);
		assertTrue("massSpectrum", null == massSpectrum);
		massSpectrum = chromatogram.getSupplierScan(340);
		assertEquals("TotalSignal", 659545.0f, massSpectrum.getTotalSignal());
		massSpectrum = chromatogram.getSupplierScan(628);
		assertEquals("TotalSignal", 406094.0f, massSpectrum.getTotalSignal());
		massSpectrum = chromatogram.getSupplierScan(5726);
		assertTrue("massSpectrum", null == massSpectrum);
		// --------------------test mass spectra
	}
}

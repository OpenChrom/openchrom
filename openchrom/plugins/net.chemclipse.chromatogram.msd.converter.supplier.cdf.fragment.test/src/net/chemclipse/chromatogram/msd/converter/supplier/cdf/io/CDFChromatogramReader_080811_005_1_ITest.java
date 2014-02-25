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
 * IChromatogram 080811_005<br/>
 * 
 * @author eselmeister
 */
public class CDFChromatogramReader_080811_005_1_ITest extends CDFChromatogramReaderTestCase {

	@Override
	protected void setUp() throws Exception {

		pathImport = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_080811_005);
		super.setUp();
	}

	private void testCDFChromatogramReader_1() {

		IMassSpectrum massSpectrum;
		ITotalScanSignals tic;
		IExtractedIonSignals xic;
		assertEquals("scanDelay", 5613, chromatogram.getScanDelay());
		assertEquals("scanInterval", 354, chromatogram.getScanInterval());
		assertEquals("operator", "", chromatogram.getOperator());
		assertEquals("file", fileImport, chromatogram.getFile());
		assertEquals("name", "080811_005", chromatogram.getName());
		assertTrue("date", chromatogram.getDate() != null);
		// assertEquals("date", 1241673253320L,
		// chromatogram.getDate().getTime());
		assertEquals("numberOfScans", 6215, chromatogram.getNumberOfScans());
		assertEquals("numberOfScanIons", 643926, chromatogram.getNumberOfScanIons());
		assertEquals("startRetentionTime", 5613, chromatogram.getStartRetentionTime());
		assertEquals("stopRetentionTime", 2220083, chromatogram.getStopRetentionTime());
		assertEquals("minSignal", 3001.0f, chromatogram.getMinSignal());
		assertEquals("maxSignal", 34367016.0f, chromatogram.getMaxSignal());
		assertEquals("miscInfo", "A4", chromatogram.getMiscInfo());
		tic = totalIonSignalExtractor.getTotalScanSignals();
		assertEquals("List<ITotalIonSignal> size", 6215, tic.size());
		assertEquals("totalIonSignal", 1372029950.0f, chromatogram.getTotalSignal());
		xic = extractedIonSignalExtractor.getExtractedIonSignals();
		assertEquals("IExtractedIonSignals size", 6215, xic.size());
		xic = extractedIonSignalExtractor.getExtractedIonSignals(1.0f, 600.5f);
		assertEquals("IExtractedIonSignals size", 6215, xic.size());
		// --------------------test mass spectra
		massSpectrum = chromatogram.getSupplierScan(5727);
		assertTrue("massSpectrum", null != massSpectrum);
		massSpectrum = chromatogram.getSupplierScan(340);
		assertEquals("TotalSignal", 59278.0f, massSpectrum.getTotalSignal());
		massSpectrum = chromatogram.getSupplierScan(628);
		assertEquals("TotalSignal", 43297.0f, massSpectrum.getTotalSignal());
		massSpectrum = chromatogram.getSupplierScan(5726);
		assertEquals("TotalSignal", 58638.0f, massSpectrum.getTotalSignal());
		// --------------------test mass spectra
	}
}

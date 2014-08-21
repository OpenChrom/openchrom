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
package net.chemclipse.msd.converter.supplier.cdf.io;

import net.chemclipse.chromatogram.model.signals.ITotalScanSignals;
import net.chemclipse.msd.converter.supplier.cdf.TestPathHelper;
import net.chemclipse.msd.model.core.IMassSpectrum;
import net.chemclipse.msd.model.xic.IExtractedIonSignals;

/**
 * IChromatogram VOCJ3205<br/>
 * 
 * @author eselmeister
 */
public class CDFChromatogramReader_VOCJ3205_1_ITest extends CDFChromatogramReaderTestCase {

	@Override
	protected void setUp() throws Exception {

		pathImport = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_VOCJ3205);
		super.setUp();
	}

	private void testCDFChromatogramReader_1() {

		IMassSpectrum massSpectrum;
		ITotalScanSignals tic;
		IExtractedIonSignals xic;
		assertEquals("scanDelay", 5117, chromatogram.getScanDelay());
		assertEquals("scanInterval", 502, chromatogram.getScanInterval());
		assertEquals("operator", "ib", chromatogram.getOperator());
		assertEquals("file", fileImport, chromatogram.getFile());
		assertEquals("name", "VOCJ3205", chromatogram.getName());
		assertTrue("date", chromatogram.getDate() != null);
		// assertEquals("date", 1206777060000l,
		// chromatogram.getDate().getTime());
		assertEquals("numberOfScans", 5439, chromatogram.getNumberOfScans());
		assertEquals("numberOfScanIons", 340183, chromatogram.getNumberOfScanIons());
		assertEquals("startRetentionTime", 5117, chromatogram.getStartRetentionTime());
		assertEquals("stopRetentionTime", 2759911, chromatogram.getStopRetentionTime());
		assertEquals("minSignal", 20872.0f, chromatogram.getMinSignal());
		assertEquals("maxSignal", 13932367.0f, chromatogram.getMaxSignal());
		assertEquals("miscInfo", "CH 336-3/2  13,3", chromatogram.getMiscInfo());
		tic = totalIonSignalExtractor.getTotalScanSignals();
		assertEquals("ITotalIonSignals size", 5439, tic.size());
		assertEquals("totalIonSignal", 718805700.0f, chromatogram.getTotalSignal());
		xic = extractedIonSignalExtractor.getExtractedIonSignals();
		assertEquals("IExtractedIonSignals size", 5439, xic.size());
		xic = extractedIonSignalExtractor.getExtractedIonSignals(1.0f, 600.5f);
		assertEquals("IExtractedIonSignals size", 5439, xic.size());
		// --------------------test mass spectra
		massSpectrum = chromatogram.getSupplierScan(22435);
		assertTrue("massSpectrum", null == massSpectrum);
		massSpectrum = chromatogram.getSupplierScan(340);
		assertEquals("TotalSignal", 49078.0f, massSpectrum.getTotalSignal());
		massSpectrum = chromatogram.getSupplierScan(628);
		assertEquals("TotalSignal", 55781.0f, massSpectrum.getTotalSignal());
		massSpectrum = chromatogram.getSupplierScan(5726);
		assertTrue("massSpectrum", null == massSpectrum);
		// --------------------test mass spectra
	}
}

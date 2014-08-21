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

public class CDFChromatogramWriter_1_ITest extends CDFChromatogramWriterTestCase {

	private final static String EXTENSION_POINT_ID_IMPORT = "net.chemclipse.msd.converter.supplier.cdf";
	private final static String EXTENSION_POINT_ID_EXPORT_REIMPORT = "net.chemclipse.msd.converter.supplier.cdf";

	@Override
	protected void setUp() throws Exception {

		pathImport = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_OP17760);
		extensionPointImport = EXTENSION_POINT_ID_IMPORT;
		pathExport = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_EXPORT_TEST);
		extensionPointExportReimport = EXTENSION_POINT_ID_EXPORT_REIMPORT;
		super.setUp();
	}

	private void testCDFChromatogramReader_1() {

		IMassSpectrum massSpectrum;
		ITotalScanSignals tic;
		IExtractedIonSignals xic;
		assertEquals("scanDelay", 5189, chromatogram.getScanDelay());
		assertEquals("scanInterval", 769, chromatogram.getScanInterval());
		assertEquals("operator", "Hamann", chromatogram.getOperator());
		assertEquals("file", fileExport, chromatogram.getFile());
		assertEquals("name", "TEST", chromatogram.getName());
		assertTrue("date", chromatogram.getDate() != null);
		// assertEquals("date", 1205321160000l,
		// chromatogram.getDate().getTime());
		assertEquals("numberOfScans", 5726, chromatogram.getNumberOfScans());
		assertEquals("numberOfScanIons", 1031366, chromatogram.getNumberOfScanIons());
		assertEquals("startRetentionTime", 5189, chromatogram.getStartRetentionTime());
		assertEquals("stopRetentionTime", 4439858, chromatogram.getStopRetentionTime());
		assertEquals("minSignal", 17475.0f, chromatogram.getMinSignal());
		assertEquals("maxSignal", 9571087.0f, chromatogram.getMaxSignal());
		assertEquals("miscInfo", "439-2   142�g", chromatogram.getMiscInfo());
		tic = totalIonSignalExtractor.getTotalScanSignals();
		assertEquals("ITotalIonSignals size", 5726, tic.size());
		assertEquals("totalIonSignal", 1024242300.0f, chromatogram.getTotalSignal());
		xic = extractedIonSignalExtractor.getExtractedIonSignals();
		assertEquals("IExtractedIonSignals size", 5726, xic.size());
		xic = extractedIonSignalExtractor.getExtractedIonSignals(1.0f, 600.5f);
		assertEquals("IExtractedIonSignals size", 5726, xic.size());
		// --------------------test mass spectra
		massSpectrum = chromatogram.getSupplierScan(5727);
		assertTrue("massSpectrum", null == massSpectrum);
		massSpectrum = chromatogram.getSupplierScan(340);
		assertEquals("TotalSignal", 150393.0f, massSpectrum.getTotalSignal());
		massSpectrum = chromatogram.getSupplierScan(628);
		assertEquals("TotalSignal", 2747568.0f, massSpectrum.getTotalSignal());
		massSpectrum = chromatogram.getSupplierScan(5726);
		assertEquals("TotalSignal", 153220.0f, massSpectrum.getTotalSignal());
		// --------------------test mass spectra
	}
}

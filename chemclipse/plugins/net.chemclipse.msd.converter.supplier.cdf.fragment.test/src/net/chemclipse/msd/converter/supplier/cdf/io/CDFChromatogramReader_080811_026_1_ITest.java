/*******************************************************************************
 * Copyright (c) 2013, 2015 Dr. Philip Wenig.
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

import net.chemclipse.model.signals.ITotalScanSignals;
import net.chemclipse.msd.converter.supplier.cdf.TestPathHelper;
import net.chemclipse.msd.model.core.IMassSpectrum;
import net.chemclipse.msd.model.xic.IExtractedIonSignals;

/**
 * IChromatogram 080811_026<br/>
 * 
 * @author eselmeister
 */
public class CDFChromatogramReader_080811_026_1_ITest extends CDFChromatogramReaderTestCase {

	@Override
	protected void setUp() throws Exception {

		pathImport = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_080811_026);
		super.setUp();
	}

	private void testCDFChromatogramReader_1() {

		IMassSpectrum massSpectrum;
		ITotalScanSignals tic;
		IExtractedIonSignals xic;
		assertEquals("scanDelay", 5627, chromatogram.getScanDelay());
		assertEquals("scanInterval", 354, chromatogram.getScanInterval());
		assertEquals("operator", "", chromatogram.getOperator());
		assertEquals("file", fileImport, chromatogram.getFile());
		assertEquals("name", "080811_026", chromatogram.getName());
		assertTrue("date", chromatogram.getDate() != null);
		// assertEquals("date", 1218523140000l,
		// chromatogram.getDate().getTime());
		assertEquals("numberOfScans", 6215, chromatogram.getNumberOfScans());
		assertEquals("numberOfScanIons", 389148, chromatogram.getNumberOfScanIons());
		assertEquals("startRetentionTime", 5627, chromatogram.getStartRetentionTime());
		assertEquals("stopRetentionTime", 2220098, chromatogram.getStopRetentionTime());
		assertEquals("minSignal", 696.0f, chromatogram.getMinSignal());
		assertEquals("maxSignal", 1454835.0f, chromatogram.getMaxSignal());
		assertEquals("miscInfo", "P2", chromatogram.getMiscInfo());
		tic = totalIonSignalExtractor.getTotalScanSignals();
		assertEquals("List<ITotalIonSignal> size", 6215, tic.size());
		assertEquals("totalIonSignal", 276475808.0f, chromatogram.getTotalSignal());
		xic = extractedIonSignalExtractor.getExtractedIonSignals();
		assertEquals("IExtractedIonSignals size", 6215, xic.size());
		xic = extractedIonSignalExtractor.getExtractedIonSignals(1.0f, 600.5f);
		assertEquals("IExtractedIonSignals size", 6215, xic.size());
		// --------------------test mass spectra
		massSpectrum = chromatogram.getSupplierScan(5727);
		assertTrue("massSpectrum", null != massSpectrum);
		massSpectrum = chromatogram.getSupplierScan(340);
		assertEquals("TotalSignal", 8248.0f, massSpectrum.getTotalSignal());
		massSpectrum = chromatogram.getSupplierScan(628);
		assertEquals("TotalSignal", 10023.0f, massSpectrum.getTotalSignal());
		massSpectrum = chromatogram.getSupplierScan(5726);
		assertEquals("TotalSignal", 29423.0f, massSpectrum.getTotalSignal());
		// --------------------test mass spectra
	}
}

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

import net.chemclipse.model.signals.ITotalScanSignals;
import net.chemclipse.msd.converter.supplier.cdf.TestPathHelper;
import net.chemclipse.msd.model.core.IMassSpectrum;
import net.chemclipse.msd.model.xic.IExtractedIonSignals;

/**
 * IChromatogram OP17796<br/>
 * 
 * @author eselmeister
 */
public class CDFChromatogramReader_OP17796_1_ITest extends CDFChromatogramReaderTestCase {

	@Override
	protected void setUp() throws Exception {

		pathImport = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_OP17796);
		super.setUp();
	}

	private void testCDFChromatogramReader_1() {

		IMassSpectrum massSpectrum;
		ITotalScanSignals tic;
		IExtractedIonSignals xic;
		assertEquals("scanDelay", 5196, chromatogram.getScanDelay());
		assertEquals("scanInterval", 769, chromatogram.getScanInterval());
		assertEquals("operator", "Wenig", chromatogram.getOperator());
		assertEquals("file", fileImport, chromatogram.getFile());
		assertEquals("name", "OP17796", chromatogram.getName());
		assertTrue("date", chromatogram.getDate() != null);
		// assertEquals("date", 1205490660000l,
		// chromatogram.getDate().getTime());
		assertEquals("numberOfScans", 5726, chromatogram.getNumberOfScans());
		assertEquals("numberOfScanIons", 1012653, chromatogram.getNumberOfScanIons());
		assertEquals("startRetentionTime", 5196, chromatogram.getStartRetentionTime());
		assertEquals("stopRetentionTime", 4439865, chromatogram.getStopRetentionTime());
		assertEquals("minSignal", 14349.0f, chromatogram.getMinSignal());
		assertEquals("maxSignal", 7652739.0f, chromatogram.getMaxSignal());
		assertEquals("miscInfo", "NH435-1 156�g", chromatogram.getMiscInfo());
		tic = totalIonSignalExtractor.getTotalScanSignals();
		assertEquals("ITotalIonSignals size", 5726, tic.size());
		assertEquals("totalIonSignal", 818578430.0f, chromatogram.getTotalSignal());
		xic = extractedIonSignalExtractor.getExtractedIonSignals();
		assertEquals("IExtractedIonSignals size", 5726, xic.size());
		xic = extractedIonSignalExtractor.getExtractedIonSignals(1.0f, 600.5f);
		assertEquals("IExtractedIonSignals size", 5726, xic.size());
		// --------------------test mass spectra
		massSpectrum = chromatogram.getSupplierScan(5727);
		assertTrue("massSpectrum", null == massSpectrum);
		massSpectrum = chromatogram.getSupplierScan(340);
		assertEquals("TotalSignal", 44155.0f, massSpectrum.getTotalSignal());
		massSpectrum = chromatogram.getSupplierScan(628);
		assertEquals("TotalSignal", 42270.0f, massSpectrum.getTotalSignal());
		massSpectrum = chromatogram.getSupplierScan(5726);
		assertEquals("TotalSignal", 110390.0f, massSpectrum.getTotalSignal());
		// --------------------test mass spectra
	}
}

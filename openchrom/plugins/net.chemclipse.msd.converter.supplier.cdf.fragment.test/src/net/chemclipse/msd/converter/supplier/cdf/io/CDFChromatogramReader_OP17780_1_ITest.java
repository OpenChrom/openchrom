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
 * IChromatogram OP17780<br/>
 * 
 * @author eselmeister
 */
public class CDFChromatogramReader_OP17780_1_ITest extends CDFChromatogramReaderTestCase {

	@Override
	protected void setUp() throws Exception {

		pathImport = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_OP17780);
		super.setUp();
	}

	private void testCDFChromatogramReader_1() {

		IMassSpectrum massSpectrum;
		ITotalScanSignals tic;
		IExtractedIonSignals xic;
		assertEquals("scanDelay", 5181, chromatogram.getScanDelay());
		assertEquals("scanInterval", 769, chromatogram.getScanInterval());
		assertEquals("operator", "Wenig", chromatogram.getOperator());
		assertEquals("file", fileImport, chromatogram.getFile());
		assertEquals("name", "OP17780", chromatogram.getName());
		assertTrue("date", chromatogram.getDate() != null);
		// assertEquals("date", 1205402400000l,
		// chromatogram.getDate().getTime());
		assertEquals("numberOfScans", 5726, chromatogram.getNumberOfScans());
		assertEquals("numberOfScanIons", 874611, chromatogram.getNumberOfScanIons());
		assertEquals("startRetentionTime", 5181, chromatogram.getStartRetentionTime());
		assertEquals("stopRetentionTime", 4439850, chromatogram.getStopRetentionTime());
		assertEquals("minSignal", 15947.0f, chromatogram.getMinSignal());
		assertEquals("maxSignal", 16350498.0f, chromatogram.getMaxSignal());
		assertEquals("miscInfo", "NH431-1 126�g", chromatogram.getMiscInfo());
		tic = totalIonSignalExtractor.getTotalScanSignals();
		assertEquals("ITotalIonSignals size", 5726, tic.size());
		assertEquals("totalIonSignal", 797918080.0f, chromatogram.getTotalSignal());
		xic = extractedIonSignalExtractor.getExtractedIonSignals();
		assertEquals("IExtractedIonSignals size", 5726, xic.size());
		xic = extractedIonSignalExtractor.getExtractedIonSignals(1.0f, 600.5f);
		assertEquals("IExtractedIonSignals size", 5726, xic.size());
		// --------------------test mass spectra
		massSpectrum = chromatogram.getSupplierScan(5727);
		assertTrue("massSpectrum", null == massSpectrum);
		massSpectrum = chromatogram.getSupplierScan(340);
		assertEquals("TotalSignal", 150741.0f, massSpectrum.getTotalSignal());
		massSpectrum = chromatogram.getSupplierScan(628);
		assertEquals("TotalSignal", 16350498.0f, massSpectrum.getTotalSignal());
		massSpectrum = chromatogram.getSupplierScan(5726);
		assertEquals("TotalSignal", 95393.0f, massSpectrum.getTotalSignal());
		// --------------------test mass spectra
	}
}

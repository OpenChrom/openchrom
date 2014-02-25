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
 * IChromatogram EI-EI_8_01<br/>
 * 
 * @author eselmeister
 */
public class CDFChromatogramReader_EIEI_8_01_1_ITest extends CDFChromatogramReaderTestCase {

	@Override
	protected void setUp() throws Exception {

		pathImport = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_EIEI_8_01);
		super.setUp();
	}

	private void testCDFChromatogramReader_1() {

		IMassSpectrum massSpectrum;
		ITotalScanSignals tic;
		IExtractedIonSignals xic;
		assertEquals("scanDelay", 5244, chromatogram.getScanDelay());
		assertEquals("scanInterval", 434, chromatogram.getScanInterval());
		assertEquals("operator", "Wilmschen", chromatogram.getOperator());
		assertEquals("file", fileImport, chromatogram.getFile());
		assertEquals("name", "EI-EI_8_01", chromatogram.getName());
		assertTrue("date", chromatogram.getDate() != null);
		// assertEquals("date", 1156414380000l,
		// chromatogram.getDate().getTime());
		assertEquals("numberOfScans", 1036, chromatogram.getNumberOfScans());
		assertEquals("numberOfScanIons", 464627, chromatogram.getNumberOfScanIons());
		assertEquals("startRetentionTime", 5244, chromatogram.getStartRetentionTime());
		assertEquals("stopRetentionTime", 473849, chromatogram.getStopRetentionTime());
		assertEquals("minSignal", 277447.0f, chromatogram.getMinSignal());
		assertEquals("maxSignal", 122215592.0f, chromatogram.getMaxSignal());
		assertEquals("miscInfo", "8", chromatogram.getMiscInfo());
		tic = totalIonSignalExtractor.getTotalScanSignals();
		assertEquals("ITotalIonSignals size", 1036, tic.size());
		assertEquals("totalIonSignal", 13211817000.0f, chromatogram.getTotalSignal());
		xic = extractedIonSignalExtractor.getExtractedIonSignals();
		assertEquals("IExtractedIonSignals size", 1036, xic.size());
		xic = extractedIonSignalExtractor.getExtractedIonSignals(1.0f, 600.5f);
		assertEquals("IExtractedIonSignals size", 1036, xic.size());
		// --------------------test mass spectra
		massSpectrum = chromatogram.getSupplierScan(5727);
		assertTrue("massSpectrum", null == massSpectrum);
		massSpectrum = chromatogram.getSupplierScan(340);
		assertEquals("TotalSignal", 1046394.0f, massSpectrum.getTotalSignal());
		massSpectrum = chromatogram.getSupplierScan(628);
		assertEquals("TotalSignal", 4281139.0f, massSpectrum.getTotalSignal());
		massSpectrum = chromatogram.getSupplierScan(5726);
		assertTrue("massSpectrum", null == massSpectrum);
		// --------------------test mass spectra
	}
}

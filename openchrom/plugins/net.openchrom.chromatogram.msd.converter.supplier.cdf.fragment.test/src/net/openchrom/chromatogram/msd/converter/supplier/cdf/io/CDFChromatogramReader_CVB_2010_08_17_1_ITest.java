/*******************************************************************************
 * Copyright (c) 2010, 2013 Philip (eselmeister) Wenig.
 * 
 * This library is free software;
 * you can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston MA 02111-1307, USA
 * 
 * 
 * Contributors: Philip (eselmeister) Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.cdf.io;

import net.openchrom.chromatogram.model.signals.ITotalScanSignals;
import net.openchrom.chromatogram.msd.converter.supplier.cdf.TestPathHelper;
import net.openchrom.chromatogram.msd.model.core.IMassSpectrum;
import net.openchrom.chromatogram.msd.model.xic.IExtractedIonSignals;

/**
 * TESTFILE_IMPORT_CVB_2010_08_17<br/>
 * 
 * @author Philip (eselmeister) Wenig
 */
public class CDFChromatogramReader_CVB_2010_08_17_1_ITest extends CDFChromatogramReaderTestCase {

	@Override
	protected void setUp() throws Exception {

		pathImport = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_CVB_2010_08_17);
		super.setUp();
	}

	private void testCDFChromatogramReader_1() {

		IMassSpectrum massSpectrum;
		ITotalScanSignals tic;
		IExtractedIonSignals xic;
		/*
		 * System.out.println(chromatogram.getScanDelay());
		 * System.out.println(chromatogram.getScanInterval());
		 * System.out.println(chromatogram.getOperator());
		 * System.out.println(chromatogram.getFile());
		 * System.out.println(chromatogram.getName());
		 * System.out.println("DATE");
		 * System.out.println(chromatogram.getNumberOfScans());
		 * System.out.println(chromatogram.getNumberOfScanIons());
		 * System.out.println(chromatogram.getStartRetentionTime());
		 * System.out.println(chromatogram.getStopRetentionTime());
		 * System.out.println(chromatogram.getMinSignal());
		 * System.out.println(chromatogram.getMaxSignal());
		 * System.out.println(chromatogram.getMiscInfo());
		 * System.out.println("");
		 * System.out.println(chromatogram.getTotalIonSignals());
		 * System.out.println("");
		 * System.out.println(chromatogram.getTotalSignal());
		 * System.out.println("");
		 * System.out.println(chromatogram.getExtractedIonSignals());
		 * System.out.println("");
		 * System.out.println(chromatogram.getExtractedIonSignals(1.0f,
		 * 600.5f)); System.out.println(""); System.out.println(massSpectrum =
		 * chromatogram.getSupplierScan(340));
		 * System.out.println(massSpectrum.getTotalSignal());
		 * System.out.println(""); System.out.println(massSpectrum =
		 * chromatogram.getSupplierScan(628));
		 * System.out.println(massSpectrum.getTotalSignal());
		 */
		assertEquals("scanDelay", 240, chromatogram.getScanDelay());
		assertEquals("scanInterval", 495, chromatogram.getScanInterval());
		assertEquals("operator", "System Administrator", chromatogram.getOperator());
		assertEquals("file", fileImport, chromatogram.getFile());
		assertEquals("name", "cvb-2010-08-17_1_17", chromatogram.getName());
		assertTrue("date", chromatogram.getDate() != null);
		// assertEquals("date", 1156320960000l,
		// chromatogram.getDate().getTime());
		assertEquals("numberOfScans", 4441, chromatogram.getNumberOfScans());
		assertEquals("numberOfScanIons", 57733, chromatogram.getNumberOfScanIons());
		assertEquals("startRetentionTime", 240, chromatogram.getStartRetentionTime());
		assertEquals("stopRetentionTime", 2220240, chromatogram.getStopRetentionTime());
		assertEquals("minSignal", 47620.0f, chromatogram.getMinSignal());
		assertEquals("maxSignal", 2414979.0f, chromatogram.getMaxSignal());
		assertEquals("miscInfo", "", chromatogram.getMiscInfo());
		tic = totalIonSignalExtractor.getTotalScanSignals();
		assertEquals("ITotalIonSignals size", 4441, tic.size());
		assertEquals("totalIonSignal", 1232325500.0f, chromatogram.getTotalSignal());
		xic = extractedIonSignalExtractor.getExtractedIonSignals();
		assertEquals("IExtractedIonSignals size", 4441, xic.size());
		xic = extractedIonSignalExtractor.getExtractedIonSignals(1.0f, 600.5f);
		assertEquals("IExtractedIonSignals size", 4441, xic.size());
		// --------------------test mass spectra
		massSpectrum = chromatogram.getSupplierScan(5727);
		assertTrue("massSpectrum", null == massSpectrum);
		massSpectrum = chromatogram.getSupplierScan(340);
		assertEquals("TotalSignal", 1395205.0f, massSpectrum.getTotalSignal());
		massSpectrum = chromatogram.getSupplierScan(628);
		assertEquals("TotalSignal", 369288.0f, massSpectrum.getTotalSignal());
		massSpectrum = chromatogram.getSupplierScan(5726);
		assertTrue("massSpectrum", null == massSpectrum);
		// --------------------test mass spectra
	}
}

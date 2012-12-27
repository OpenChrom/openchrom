/*******************************************************************************
 * Copyright (c) 2008, 2012 Philip (eselmeister) Wenig.
 * 
 * This library is free
 * software; you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston MA 02111-1307, USA
 * 
 * 
 * Contributors: Philip (eselmeister) Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.cdf.io;

import net.openchrom.chromatogram.msd.converter.supplier.cdf.TestPathHelper;
import net.openchrom.chromatogram.msd.model.core.IMassSpectrum;
import net.openchrom.chromatogram.msd.model.xic.IExtractedIonSignals;
import net.openchrom.chromatogram.msd.model.xic.ITotalIonSignals;

public class CDFChromatogramWriter_2_ITest extends CDFChromatogramWriterTestCase {

	private final static String EXTENSION_POINT_ID_IMPORT = "net.openchrom.chromatogram.msd.converter.supplier.agilent";
	private final static String EXTENSION_POINT_ID_EXPORT_REIMPORT = "net.openchrom.chromatogram.msd.converter.supplier.cdf";

	@Override
	protected void setUp() throws Exception {

		pathImport = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_OP17760_AGILENT);
		extensionPointImport = EXTENSION_POINT_ID_IMPORT;
		pathExport = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_EXPORT_TEST);
		extensionPointExportReimport = EXTENSION_POINT_ID_EXPORT_REIMPORT;
		super.setUp();
	}

	public void testCDFChromatogramReader_1() {

		IMassSpectrum massSpectrum;
		ITotalIonSignals tic;
		IExtractedIonSignals xic;
		assertEquals("scanDelay", 5190, chromatogram.getScanDelay());
		assertEquals("scanInterval", 769, chromatogram.getScanInterval());
		assertEquals("operator", "Hamann", chromatogram.getOperator());
		assertEquals("file", fileExport, chromatogram.getFile());
		assertEquals("name", "TEST", chromatogram.getName());
		assertTrue("date", chromatogram.getDate() != null);
		// assertEquals("date", 1205321160000l,
		// chromatogram.getDate().getTime());
		assertEquals("numberOfScans", 5726, chromatogram.getNumberOfScans());
		assertEquals("numberOfScanIons", 1031366, chromatogram.getNumberOfScanIons());
		assertEquals("startRetentionTime", 5190, chromatogram.getStartRetentionTime());
		assertEquals("stopRetentionTime", 4439858, chromatogram.getStopRetentionTime());
		assertEquals("minSignal", 17475.0f, chromatogram.getMinSignal());
		assertEquals("maxSignal", 9571087.0f, chromatogram.getMaxSignal());
		assertEquals("miscInfo", "", chromatogram.getMiscInfo());
		tic = totalIonSignalExtractor.getTotalIonSignals();
		assertEquals("ITotalIonSignals size", 5726, tic.size());
		assertEquals("totalIonSignal", 1024242300.0f, chromatogram.getTotalSignal());
		xic = chromatogram.getExtractedIonSignals();
		assertEquals("IExtractedIonSignals size", 5726, xic.size());
		xic = chromatogram.getExtractedIonSignals(1.0f, 600.5f);
		assertEquals("IExtractedIonSignals size", 5726, xic.size());
		// --------------------test mass spectra
		massSpectrum = chromatogram.getScan(5727);
		assertTrue("massSpectrum", null == massSpectrum);
		massSpectrum = chromatogram.getScan(340);
		assertEquals("TotalSignal", 150393.0f, massSpectrum.getTotalSignal());
		massSpectrum = chromatogram.getScan(628);
		assertEquals("TotalSignal", 2747568.0f, massSpectrum.getTotalSignal());
		massSpectrum = chromatogram.getScan(5726);
		assertEquals("TotalSignal", 153220.0f, massSpectrum.getTotalSignal());
		// --------------------test mass spectra
	}
}

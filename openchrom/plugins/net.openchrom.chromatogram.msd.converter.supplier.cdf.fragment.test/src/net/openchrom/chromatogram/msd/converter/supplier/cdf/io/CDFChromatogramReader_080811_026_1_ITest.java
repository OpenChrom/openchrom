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

	public void testCDFChromatogramReader_1() {

		IMassSpectrum massSpectrum;
		ITotalIonSignals tic;
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
		tic = totalIonSignalExtractor.getTotalIonSignals();
		assertEquals("List<ITotalIonSignal> size", 6215, tic.size());
		assertEquals("totalIonSignal", 276475808.0f, chromatogram.getTotalSignal());
		xic = extractedIonSignalExtractor.getExtractedIonSignals();
		assertEquals("IExtractedIonSignals size", 6215, xic.size());
		xic = extractedIonSignalExtractor.getExtractedIonSignals(1.0f, 600.5f);
		assertEquals("IExtractedIonSignals size", 6215, xic.size());
		// --------------------test mass spectra
		massSpectrum = chromatogram.getScan(5727);
		assertTrue("massSpectrum", null != massSpectrum);
		massSpectrum = chromatogram.getScan(340);
		assertEquals("TotalSignal", 8248.0f, massSpectrum.getTotalSignal());
		massSpectrum = chromatogram.getScan(628);
		assertEquals("TotalSignal", 10023.0f, massSpectrum.getTotalSignal());
		massSpectrum = chromatogram.getScan(5726);
		assertEquals("TotalSignal", 29423.0f, massSpectrum.getTotalSignal());
		// --------------------test mass spectra
	}
}

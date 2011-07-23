/*******************************************************************************
 * Copyright (c) 2008, 2011 Philip (eselmeister) Wenig.
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

	public void testCDFChromatogramReader_1() {

		IMassSpectrum massSpectrum;
		ITotalIonSignals tic;
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
		tic = chromatogram.getTotalIonSignals();
		assertEquals("List<ITotalIonSignal> size", 6215, tic.size());
		assertEquals("totalIonSignal", 1372029950.0f, chromatogram.getTotalSignal());
		xic = chromatogram.getExtractedIonSignals();
		assertEquals("IExtractedIonSignals size", 6215, xic.size());
		xic = chromatogram.getExtractedIonSignals(1.0f, 600.5f);
		assertEquals("IExtractedIonSignals size", 6215, xic.size());
		// --------------------test mass spectra
		massSpectrum = chromatogram.getScan(5727);
		assertTrue("massSpectrum", null != massSpectrum);
		massSpectrum = chromatogram.getScan(340);
		assertEquals("TotalSignal", 59278.0f, massSpectrum.getTotalSignal());
		massSpectrum = chromatogram.getScan(628);
		assertEquals("TotalSignal", 43297.0f, massSpectrum.getTotalSignal());
		massSpectrum = chromatogram.getScan(5726);
		assertEquals("TotalSignal", 58638.0f, massSpectrum.getTotalSignal());
		// --------------------test mass spectra
	}
}

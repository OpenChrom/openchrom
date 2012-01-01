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
 * IChromatogram OP8777<br/>
 * 
 * @author eselmeister
 */
public class CDFChromatogramReader_OP8777_1_ITest extends CDFChromatogramReaderTestCase {

	@Override
	protected void setUp() throws Exception {

		pathImport = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_OP8777);
		super.setUp();
	}

	public void testCDFChromatogramReader_1() {

		IMassSpectrum massSpectrum;
		ITotalIonSignals tic;
		IExtractedIonSignals xic;
		assertEquals("scanDelay", 5148, chromatogram.getScanDelay());
		assertEquals("scanInterval", 268, chromatogram.getScanInterval());
		assertEquals("operator", "Hamann", chromatogram.getOperator());
		assertEquals("file", fileImport, chromatogram.getFile());
		assertEquals("name", "OP8777", chromatogram.getName());
		assertTrue("date", chromatogram.getDate() != null);
		// assertEquals("date", 1127977020000l,
		// chromatogram.getDate().getTime());
		assertEquals("numberOfScans", 22435, chromatogram.getNumberOfScans());
		assertEquals("numberOfScanIons", 3403429, chromatogram.getNumberOfScanIons());
		assertEquals("startRetentionTime", 5148, chromatogram.getStartRetentionTime());
		assertEquals("stopRetentionTime", 5840076, chromatogram.getStopRetentionTime());
		assertEquals("minSignal", 18426.0f, chromatogram.getMinSignal());
		assertEquals("maxSignal", 10811341.0f, chromatogram.getMaxSignal());
		assertEquals("miscInfo", "199-7  57", chromatogram.getMiscInfo());
		tic = chromatogram.getTotalIonSignals();
		assertEquals("ITotalIonSignals size", 22435, tic.size());
		assertEquals("totalIonSignal", 8894604300.0f, chromatogram.getTotalSignal());
		xic = chromatogram.getExtractedIonSignals();
		assertEquals("IExtractedIonSignals size", 22435, xic.size());
		xic = chromatogram.getExtractedIonSignals(1.0f, 600.5f);
		assertEquals("IExtractedIonSignals size", 22435, xic.size());
		// --------------------test mass spectra
		massSpectrum = chromatogram.getScan(22436);
		assertTrue("massSpectrum", null == massSpectrum);
		massSpectrum = chromatogram.getScan(340);
		assertEquals("TotalSignal", 24893.0f, massSpectrum.getTotalSignal());
		massSpectrum = chromatogram.getScan(628);
		assertEquals("TotalSignal", 80763.0f, massSpectrum.getTotalSignal());
		massSpectrum = chromatogram.getScan(5726);
		assertEquals("TotalSignal", 79286.0f, massSpectrum.getTotalSignal());
		// --------------------test mass spectra
	}
}

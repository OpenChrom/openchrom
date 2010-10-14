/*******************************************************************************
 * Copyright (c) 2008, 2010 Philip (eselmeister) Wenig.
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
package net.openchrom.chromatogram.ms.converter.supplier.cdf.io;

import net.openchrom.chromatogram.ms.converter.supplier.cdf.TestPathHelper;
import net.openchrom.chromatogram.ms.model.core.IMassSpectrum;
import net.openchrom.chromatogram.ms.model.xic.IExtractedIonSignals;
import net.openchrom.chromatogram.ms.model.xic.ITotalIonSignals;

/**
 * IChromatogram OP17796<br/>
 * 
 * @author eselmeister
 */
public class CDFChromatogramReader_OP17796_1_Test extends CDFChromatogramReaderTestCase {

	@Override
	protected void setUp() throws Exception {

		pathImport = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_OP17796);
		super.setUp();
	}

	public void testCDFChromatogramReader_1() {

		IMassSpectrum massSpectrum;
		ITotalIonSignals tic;
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
		assertEquals("numberOfScanMassFragments", 1012653, chromatogram.getNumberOfScanMassFragments());
		assertEquals("startRetentionTime", 5196, chromatogram.getStartRetentionTime());
		assertEquals("stopRetentionTime", 4439865, chromatogram.getStopRetentionTime());
		assertEquals("minSignal", 14349.0f, chromatogram.getMinSignal());
		assertEquals("maxSignal", 7652739.0f, chromatogram.getMaxSignal());
		assertEquals("miscInfo", "NH435-1 156�g", chromatogram.getMiscInfo());
		tic = chromatogram.getTotalIonSignals();
		assertEquals("ITotalIonSignals size", 5726, tic.size());
		assertEquals("totalIonSignal", 818578430.0f, chromatogram.getTotalSignal());
		xic = chromatogram.getExtractedIonSignals();
		assertEquals("IExtractedIonSignals size", 5726, xic.size());
		xic = chromatogram.getExtractedIonSignals(1.0f, 600.5f);
		assertEquals("IExtractedIonSignals size", 5726, xic.size());
		// --------------------test mass spectra
		massSpectrum = chromatogram.getScan(5727);
		assertTrue("massSpectrum", null == massSpectrum);
		massSpectrum = chromatogram.getScan(340);
		assertEquals("TotalSignal", 44155.0f, massSpectrum.getTotalSignal());
		massSpectrum = chromatogram.getScan(628);
		assertEquals("TotalSignal", 42270.0f, massSpectrum.getTotalSignal());
		massSpectrum = chromatogram.getScan(5726);
		assertEquals("TotalSignal", 110390.0f, massSpectrum.getTotalSignal());
		// --------------------test mass spectra
	}
}

/*******************************************************************************
 * Copyright (c) 2008, 2013 Philip (eselmeister) Wenig.
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

/**
 * Will the memory be cleaned up, or does memory leaks appear while import the
 * chromatograms?
 * 
 * @author eselmeister
 */
public class CDFChromatogramReader_MemoryTest_1_ITest extends CDFChromatogramReaderTestCase {

	@Override
	protected void setUp() throws Exception {

		pathImport = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_OP17760);
		super.setUp();
	}

	private void testRead_1() {

		assertTrue("1", chromatogram != null);
	}

	private void testRead_2() {

		assertTrue("2", chromatogram != null);
	}

	private void testRead_3() {

		assertTrue("3", chromatogram != null);
	}

	private void testRead_4() {

		assertTrue("4", chromatogram != null);
	}

	private void testRead_5() {

		assertTrue("5", chromatogram != null);
	}

	private void testRead_6() {

		assertTrue("6", chromatogram != null);
	}

	private void testRead_7() {

		assertTrue("7", chromatogram != null);
	}

	private void testRead_8() {

		assertTrue("8", chromatogram != null);
	}

	private void testRead_9() {

		assertTrue("9", chromatogram != null);
	}

	private void testRead_10() {

		assertTrue("10", chromatogram != null);
	}

	private void testRead_11() {

		assertTrue("11", chromatogram != null);
	}

	private void testRead_12() {

		assertTrue("12", chromatogram != null);
	}

	private void testRead_13() {

		assertTrue("13", chromatogram != null);
	}

	private void testRead_14() {

		assertTrue("14", chromatogram != null);
	}

	private void testRead_15() {

		assertTrue("15", chromatogram != null);
	}

	private void testRead_16() {

		assertTrue("16", chromatogram != null);
	}

	private void testRead_17() {

		assertTrue("17", chromatogram != null);
	}

	private void testRead_18() {

		assertTrue("18", chromatogram != null);
	}

	private void testRead_19() {

		assertTrue("19", chromatogram != null);
	}

	private void testRead_20() {

		assertTrue("20", chromatogram != null);
	}
}

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

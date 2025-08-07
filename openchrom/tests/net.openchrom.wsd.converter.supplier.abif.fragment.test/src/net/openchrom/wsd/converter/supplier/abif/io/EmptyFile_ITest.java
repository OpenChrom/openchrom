/*******************************************************************************
 * Copyright (c) 2016, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.wsd.converter.supplier.abif.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import net.openchrom.wsd.converter.supplier.abif.ABIF;
import net.openchrom.wsd.converter.supplier.abif.ChromatogramReaderTestCase;

public class EmptyFile_ITest extends ChromatogramReaderTestCase {

	@Override
	@Before
	public void setUp() throws Exception {

		extensionPointId = ABIF.EXTENSION_POINT_ID;
		pathImport = ABIF.getAbsolutePath(ABIF.TESTFILE_IMPORT_EMPTY_AB1);
		super.setUp();
	}

	@Test
	public void testValidFile() {

		assertNotNull(chromatogram);
	}

	@Test
	public void testEmptySequence() {

		assertEquals("NNNNN", chromatogram.getMiscInfo());
	}

	@Test
	public void testSampleName() {

		assertEquals("226041_C-ME-19_pCAGseqF", chromatogram.getSampleName());
	}
}

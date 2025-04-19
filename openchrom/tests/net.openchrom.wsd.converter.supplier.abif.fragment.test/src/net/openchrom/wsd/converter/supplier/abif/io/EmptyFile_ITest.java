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

import net.openchrom.wsd.converter.supplier.abif.ABIF;
import net.openchrom.wsd.converter.supplier.abif.ChromatogramReaderTestCase;

public class EmptyFile_ITest extends ChromatogramReaderTestCase {

	@Override
	protected void setUp() throws Exception {

		extensionPointId = ABIF.EXTENSION_POINT_ID;
		pathImport = ABIF.getAbsolutePath(ABIF.TESTFILE_IMPORT_EMPTY_AB1);
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	public void testValidFile() {

		assertNotNull(chromatogram);
	}

	public void testEmptySequence() {

		assertEquals("NNNNN", chromatogram.getMiscInfo());
	}

	public void testSampleName() {

		assertEquals("226041_C-ME-19_pCAGseqF", chromatogram.getSampleName());
	}
}

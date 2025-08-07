/*******************************************************************************
 * Copyright (c) 2016, 2025 Matthias Mailänder, Philip Wenig.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.wsd.converter.supplier.abif.io;

import static org.junit.Assert.assertEquals;

import org.eclipse.chemclipse.wsd.model.core.IScanWSD;
import org.junit.Before;
import org.junit.Test;

import net.openchrom.wsd.converter.supplier.abif.ABIF;
import net.openchrom.wsd.converter.supplier.abif.ChromatogramReaderTestCase;

public class AbiPrism3100GeneticAnalyser_ITest extends ChromatogramReaderTestCase {

	@Override
	@Before
	public void setUp() throws Exception {

		extensionPointId = ABIF.EXTENSION_POINT_ID;
		pathImport = ABIF.getAbsolutePath(ABIF.TESTFILE_IMPORT_3100_AB1);
		super.setUp();
	}

	@Test
	public void testWavelengths() {

		IScanWSD scan = (IScanWSD)chromatogram.getScan(1);
		assertEquals(540.0f, scan.getScanSignal(0).getWavelength(), 0);
	}
}

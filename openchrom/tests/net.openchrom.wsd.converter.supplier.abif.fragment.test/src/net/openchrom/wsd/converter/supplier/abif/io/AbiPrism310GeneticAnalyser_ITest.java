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

import net.openchrom.wsd.converter.supplier.abif.ABIF;
import net.openchrom.wsd.converter.supplier.abif.ChromatogramReaderTestCase;
import net.openchrom.wsd.converter.supplier.abif.model.IVendorChromatogram;

public class AbiPrism310GeneticAnalyser_ITest extends ChromatogramReaderTestCase {

	@Override
	protected void setUp() throws Exception {

		extensionPointId = ABIF.EXTENSION_POINT_ID;
		pathImport = ABIF.getAbsolutePath(ABIF.TESTFILE_IMPORT_310_AB1);
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	public void testVersion() {

		assertEquals(101, ((IVendorChromatogram)chromatogram).getVersion());
	}
}

/*******************************************************************************
 * Copyright (c) 2016, 2020 Matthias Mailänder, Dr. Philip Wenig.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.wsd.converter.supplier.abif.io;

import org.eclipse.chemclipse.wsd.model.core.IScanWSD;

import net.openchrom.wsd.converter.supplier.abif.ABIF;
import net.openchrom.wsd.converter.supplier.abif.ChromatogramReaderTestCase;

public class AbiPrism3100GeneticAnalyser_ITest extends ChromatogramReaderTestCase {

	@Override
	protected void setUp() throws Exception {

		extensionPointId = ABIF.EXTENSION_POINT_ID;
		pathImport = ABIF.getAbsolutePath(ABIF.TESTFILE_IMPORT_3100_AB1);
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	public void testWavelengths() {

		IScanWSD scan = (IScanWSD)chromatogram.getScan(1);
		assertEquals(540.0d, scan.getScanSignal(0).getWavelength());
	}
}

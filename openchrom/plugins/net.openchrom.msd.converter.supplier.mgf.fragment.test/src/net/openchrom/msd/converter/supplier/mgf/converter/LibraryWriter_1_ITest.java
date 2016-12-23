/*******************************************************************************
 * Copyright (c) 2016 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mgf.converter;

import net.openchrom.msd.converter.supplier.mgf.LibraryWriterTestCase;
import net.openchrom.msd.converter.supplier.mgf.TestPathHelper;

public class LibraryWriter_1_ITest extends LibraryWriterTestCase {

	@Override
	protected void setUp() throws Exception {

		extensionPointIdImport = TestPathHelper.CONVERTER_ID;
		pathImport = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_MS_1);
		//
		extensionPointIdExport = TestPathHelper.CONVERTER_ID;
		pathExport = TestPathHelper.getAbsolutePath(TestPathHelper.TESTDIR_EXPORT) + TestPathHelper.TESTFILE_EXPORT_MS_1;
		//
		super.setUp();
	}

	public void testAgilentChromatogramWriter_1() {

		// TODO: implement
	}
}

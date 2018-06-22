/*******************************************************************************
 * Copyright (c) 2016, 2018 Matthias Mailänder.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.wsd.converter.supplier.abif.io;

import java.io.File;

import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.wsd.converter.chromatogram.ChromatogramConverterWSD;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.core.runtime.NullProgressMonitor;

import net.openchrom.wsd.converter.supplier.abif.ABIF;

import junit.framework.TestCase;

public class InvalidData_ITest extends TestCase {

	protected IChromatogramWSD chromatogram;
	protected String extensionPointId;
	protected String pathImport;
	protected File fileImport;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		extensionPointId = ABIF.EXTENSION_POINT_ID;
		pathImport = ABIF.getAbsolutePath(ABIF.TESTFILE_IMPORT_FAKE_AB1);
		fileImport = new File(this.pathImport);
		IProcessingInfo processingInfo = ChromatogramConverterWSD.convert(fileImport, extensionPointId, new NullProgressMonitor());
		try {
			chromatogram = processingInfo.getProcessingResult(IChromatogramWSD.class);
		} catch(Exception e) {
			// Test succeeded. Invalid data should throw an exception.
		}
	}

	@Override
	protected void tearDown() throws Exception {

		pathImport = null;
		fileImport = null;
		chromatogram = null;
		//
		System.gc();
		//
		super.tearDown();
	}

	public void testInvalidFile() {

	}
}

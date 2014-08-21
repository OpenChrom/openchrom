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
package net.chemclipse.msd.converter.supplier.cdf.io;

import java.io.File;

import org.eclipse.core.runtime.NullProgressMonitor;

import junit.framework.TestCase;
import net.chemclipse.msd.converter.chromatogram.ChromatogramConverterMSD;
import net.chemclipse.msd.converter.processing.chromatogram.IChromatogramMSDImportConverterProcessingInfo;
import net.chemclipse.msd.converter.supplier.cdf.TestPathHelper;
import net.chemclipse.msd.model.core.IChromatogramMSD;
import net.chemclipse.processing.core.exceptions.TypeCastException;

/**
 * Tests if the right exception will be thrown if the file is not readable.
 * 
 * @author eselmeister
 */
public class CDFChromatogramReader_NOT_READABLE_1_ITest extends TestCase {

	private IChromatogramMSD chromatogram;
	private String pathImport;
	private File fileImport;
	private final static String EXTENSION_POINT_ID = "net.chemclipse.msd.converter.supplier.cdf";

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		this.pathImport = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_NOT_READABLE);
		fileImport = new File(this.pathImport);
		fileImport.setReadable(false);
	}

	@Override
	protected void tearDown() throws Exception {

		fileImport.setReadable(true);
		fileImport = null;
		pathImport = null;
		super.tearDown();
	}

	public void testNotReadable_1() {

		IChromatogramMSDImportConverterProcessingInfo processingInfo = ChromatogramConverterMSD.convert(fileImport, EXTENSION_POINT_ID, new NullProgressMonitor());
		try {
			chromatogram = processingInfo.getChromatogram();
			assertNotNull(chromatogram);
		} catch(TypeCastException e) {
			assertTrue("TypeCastException", false);
		}
	}

	public void testNotReadable_2() {

		IChromatogramMSDImportConverterProcessingInfo processingInfo = ChromatogramConverterMSD.convert(fileImport, new NullProgressMonitor());
		try {
			chromatogram = processingInfo.getChromatogram();
			assertNotNull(chromatogram);
		} catch(TypeCastException e) {
			assertTrue("TypeCastException", false);
		}
	}
}

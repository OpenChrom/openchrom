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
package net.chemclipse.chromatogram.msd.converter.supplier.cdf.io;

import java.io.File;

import org.eclipse.core.runtime.NullProgressMonitor;

import junit.framework.TestCase;

import net.chemclipse.chromatogram.msd.converter.chromatogram.ChromatogramConverterMSD;
import net.chemclipse.chromatogram.msd.converter.processing.chromatogram.IChromatogramMSDImportConverterProcessingInfo;
import net.chemclipse.chromatogram.msd.converter.supplier.cdf.TestPathHelper;
import net.chemclipse.chromatogram.msd.model.core.IChromatogramMSD;

/**
 * This class tries to read the chromatogram OP17760 without specifying the
 * chromatogram reader.<br/>
 * 
 * @author eselmeister
 */
public class CDFChromatogramReader_OP17760_3_ITest extends TestCase {

	protected IChromatogramMSD chromatogram;
	protected String pathImport;
	protected File fileImport;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		this.pathImport = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_OP17760);
		fileImport = new File(this.pathImport);
		IChromatogramMSDImportConverterProcessingInfo processingInfo = ChromatogramConverterMSD.convert(fileImport, new NullProgressMonitor());
		chromatogram = processingInfo.getChromatogram();
	}

	@Override
	protected void tearDown() throws Exception {

		pathImport = null;
		fileImport = null;
		chromatogram = null;
		super.tearDown();
	}

	private void testCDFChromatogramReader_1() {

		assertEquals("startRetentionTime", 5189, chromatogram.getStartRetentionTime());
		assertEquals("stopRetentionTime", 4439858, chromatogram.getStopRetentionTime());
		assertEquals("minSignal", 17475.0f, chromatogram.getMinSignal());
		assertEquals("maxSignal", 9571087.0f, chromatogram.getMaxSignal());
	}
}

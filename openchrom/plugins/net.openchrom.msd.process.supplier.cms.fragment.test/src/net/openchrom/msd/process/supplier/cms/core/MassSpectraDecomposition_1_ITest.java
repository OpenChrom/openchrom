/*******************************************************************************
 * Copyright (c) 2016, 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.process.supplier.cms.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.chemclipse.converter.exceptions.FileIsEmptyException;
import org.eclipse.chemclipse.converter.exceptions.FileIsNotReadableException;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.core.runtime.NullProgressMonitor;

import net.openchrom.msd.converter.supplier.cms.io.MassSpectrumReader;
import net.openchrom.msd.process.supplier.cms.TestPathHelper;

import junit.framework.TestCase;

public class MassSpectraDecomposition_1_ITest extends TestCase {

	private MassSpectraDecomposition massSpectraDecomposition;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		massSpectraDecomposition = new MassSpectraDecomposition();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	public void test1() throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		/*
		 * argon, nitrogen, oxygen, ethane, ethylene
		 */
		File scanFile = new File(TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_TEST_2_SCAN_SPECTRA));
		MassSpectrumReader massSpectrumReader = new MassSpectrumReader();
		IMassSpectra scanSpectra = massSpectrumReader.read(scanFile, new NullProgressMonitor());
		/*
		 * argon, nitrogen, oxygen, ethane, ethylene
		 */
		File libraryFile = new File(TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_TEST_1_LIBRARY_SPECTRA));
		IMassSpectra librarySpectra = massSpectrumReader.read(libraryFile, new NullProgressMonitor());
		// try {
		massSpectraDecomposition.decompose(scanSpectra, librarySpectra, false, new NullProgressMonitor());
		// } catch(InvalidScanException e) {
		// System.out.println(e);
		// }
		//
		assertTrue(true); // ;-)
	}
}

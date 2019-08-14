/*******************************************************************************
 * Copyright (c) 2017 Walter Whitlock.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Walter Whitlock - initial API and implementation
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

import junit.framework.TestCase;

public class MassSpectraCorrelation_1_ITest extends TestCase {

	private MassSpectraCorrelation massSpectraCorrelation;
	private CorrelationResults correlationResults;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		massSpectraCorrelation = new MassSpectraCorrelation();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	public void test1() throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		/*
		 * argon, nitrogen, oxygen, ethane, ethylene
		 */
		// File scanFile = new File(TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_TEST_2_SCAN_SPECTRA));
		File scanFile = new File("G:/_CDS/svn/rivisc2h2/data/rga1_Mar_11_2017_10-27-04_AM/_mar_11_2017__10-27-22_am_1186.cms");
		MassSpectrumReader massSpectrumReader = new MassSpectrumReader();
		IMassSpectra scanSpectra = massSpectrumReader.read(scanFile, new NullProgressMonitor());
		/*
		 * argon, nitrogen, oxygen, ethane, ethylene
		 */
		// File libraryFile = new File(TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_TEST_1_LIBRARY_SPECTRA));
		File libraryFile = new File("G:/_CDS/svn/OPENChrom/library files/11-MAR-17 CH4+H2+Air+C2H2_rescaled.cms");
		IMassSpectra librarySpectra = massSpectrumReader.read(libraryFile, new NullProgressMonitor());
		correlationResults = massSpectraCorrelation.correlate(scanSpectra, librarySpectra, new NullProgressMonitor());
		//
		assertTrue(true); // ;-)
	}
}

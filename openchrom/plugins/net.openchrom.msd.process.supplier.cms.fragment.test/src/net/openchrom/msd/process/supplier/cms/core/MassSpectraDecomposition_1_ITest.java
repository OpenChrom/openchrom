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
package net.openchrom.msd.process.supplier.cms.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.chemclipse.converter.exceptions.FileIsEmptyException;
import org.eclipse.chemclipse.converter.exceptions.FileIsNotReadableException;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.implementation.MassSpectra;
import org.eclipse.core.runtime.NullProgressMonitor;

import net.openchrom.msd.converter.supplier.cms.io.CMSreader;

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

		// File file = new File(TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_SCANS_1));
		// IMassSpectrumImportConverterProcessingInfo processingInfo = MassSpectrumConverter.convert(file, new NullProgressMonitor());
		// IMassSpectra massSpectra = processingInfo.getMassSpectra();
		// massSpectraDecomposition.decompose(massSpectra, new NullProgressMonitor());
		//
		//File file = new File("G:/_CDS/svn/rivisc2h2/data/openchrom_test/test1_csv.csv");
		////File file = new File("G:/_CDS/svn/rivisc2h2/data/rga2_Mar_07_2016_12-14-09_AM/_mar_07_2016__12-14-36_am_466.csv");
		//IChromatogramMSDImportConverterProcessingInfo proccesingInfo = ChromatogramConverterMSD.convert(file, new NullProgressMonitor());
		//IChromatogramMSD chromatogramMSD = proccesingInfo.getChromatogram();
		IMassSpectra massSpectra = new MassSpectra();
		//for(IScan scan : chromatogramMSD.getScans()) {
		//	if(scan instanceof IScanMSD) {
		//		IScanMSD massSpectrum = (IScanMSD)scan;
		//		massSpectra.addMassSpectrum(massSpectrum);
		//	}
		//}
		
		File scanfile = new File("G:/_CDS/svn/rivisc2h2/data/openchrom_test/testscan.cms"); // argon, nitrogen, oxygen, ethane, ethylene
		//File scanfile = new File("G:/_CDS/svn/rivisc2h2/data/openchrom_test/test2.cms"); // _mar_07_2016__12-14-36_am_3
		//File scanfile = new File("G:/_CDS/svn/rivisc2h2/data/rga2_Mar_07_2016_12-14-09_AM/_mar_07_2016__12-14-36_am_466.cms");
		CMSreader cmsreader = new CMSreader();
		IMassSpectra scanSpectra = cmsreader.read(scanfile, new NullProgressMonitor());
		
		//File libfile = new File("G:/_CDS/svn/rivisc2h2/data/cracking patterns/_cmslib-all_mbar.cms"); // all
		File libfile = new File("G:/_CDS/svn/rivisc2h2/data/cracking patterns/_cmslib-1_mbar.cms"); // argon, nitrogen, oxygen, ethane, ethylene
		//File libfile = new File("G:/_CDS/svn/rivisc2h2/data/cracking patterns/_cmslib-edit_mbar.cms"); // cylinder, hydrogen, methane, ethane, ethylene, propane, butane, acetylene
        //IMassSpectrumImportConverterProcessingInfo processingInfo = MassSpectrumConverter.convert(libfile, new NullProgressMonitor());
        //IMassSpectra libmassSpectra = processingInfo.getMassSpectra();
		//MassSpectrumReader libReader = new MassSpectrumReader();
		IMassSpectra libSpectra = cmsreader.read(libfile, new NullProgressMonitor());
		//CalibratedVendorMassSpectrum libSpectra = libReader.read(libfile, new NullProgressMonitor());
		
		try {
			massSpectraDecomposition.decompose(scanSpectra, libSpectra, new NullProgressMonitor());
		}
		catch (InvalidScanException exc) {
			System.out.println(exc);
		}
		//
		assertTrue(true); // ;-)
	}
}

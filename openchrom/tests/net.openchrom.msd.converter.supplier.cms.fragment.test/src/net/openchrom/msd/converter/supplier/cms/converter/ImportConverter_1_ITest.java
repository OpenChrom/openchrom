/*******************************************************************************
 * Copyright (c) 2016, 2019 Walter Whitlock, Philip Wenig.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Walter Whitlock - initial API and implementation
 * Philip Wenig - initial API and implementation
 * Alexander Kerner - Generics
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cms.converter;

import java.io.File;

import org.eclipse.chemclipse.model.exceptions.AbundanceLimitExceededException;
import org.eclipse.chemclipse.msd.converter.database.IDatabaseImportConverter;
import org.eclipse.chemclipse.msd.model.core.ILibraryMassSpectrum;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.exceptions.IonLimitExceededException;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;

import junit.framework.TestCase;
import net.openchrom.msd.converter.supplier.cms.TestPathHelper;

public class ImportConverter_1_ITest extends TestCase {

	private IMassSpectra massSpectra;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		File importFile = new File(TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_MASS_SPECTRA_1));
		IDatabaseImportConverter importConverter = new DatabaseImportConverter();
		IProcessingInfo<IMassSpectra> processingInfo = importConverter.convert(importFile, new NullProgressMonitor());
		massSpectra = processingInfo.getProcessingResult();
	}

	@Override
	protected void tearDown() throws Exception {

		massSpectra = null;
		super.tearDown();
	}

	public void test_1() {

		assertEquals(5, massSpectra.size());
	}

	public void test_2() throws AbundanceLimitExceededException, IonLimitExceededException {

		IScanMSD massSpectrum = massSpectra.getMassSpectrum(1);
		ILibraryMassSpectrum libraryMassSpectrum = (ILibraryMassSpectrum)massSpectrum;
		assertEquals("Argon", libraryMassSpectrum.getLibraryInformation().getName());
		assertEquals("7440-37-1", libraryMassSpectrum.getLibraryInformation().getCasNumber());
		assertEquals(4, massSpectrum.getNumberOfIons());
		assertEquals(1.46215e-01f, massSpectrum.getIon(20).getAbundance());
		assertEquals(3.00030e-03f, massSpectrum.getIon(36).getAbundance());
		assertEquals(5.00050e-04f, massSpectrum.getIon(38).getAbundance());
		assertEquals(1.00000e+00f, massSpectrum.getIon(40).getAbundance());
	}

	public void test_3() throws AbundanceLimitExceededException, IonLimitExceededException {

		IScanMSD massSpectrum = massSpectra.getMassSpectrum(2);
		ILibraryMassSpectrum libraryMassSpectrum = (ILibraryMassSpectrum)massSpectrum;
		assertEquals("Nitrogen", libraryMassSpectrum.getLibraryInformation().getName());
		assertEquals("7727-37-9", libraryMassSpectrum.getLibraryInformation().getCasNumber());
		assertEquals(3, massSpectrum.getNumberOfIons());
		assertEquals(1.37914e-01f, massSpectrum.getIon(14).getAbundance());
		assertEquals(1.00000e+00f, massSpectrum.getIon(28).getAbundance());
		assertEquals(7.40074e-03f, massSpectrum.getIon(29).getAbundance());
	}

	public void test_4() throws AbundanceLimitExceededException, IonLimitExceededException {

		IScanMSD massSpectrum = massSpectra.getMassSpectrum(3);
		ILibraryMassSpectrum libraryMassSpectrum = (ILibraryMassSpectrum)massSpectrum;
		assertEquals("Oxygen", libraryMassSpectrum.getLibraryInformation().getName());
		assertEquals("7782-44-7", libraryMassSpectrum.getLibraryInformation().getCasNumber());
		assertEquals(2, massSpectrum.getNumberOfIons());
		assertEquals(2.18022e-01f, massSpectrum.getIon(16).getAbundance());
		assertEquals(1.00000e+00f, massSpectrum.getIon(32).getAbundance());
	}

	public void test_5() throws AbundanceLimitExceededException, IonLimitExceededException {

		IScanMSD massSpectrum = massSpectra.getMassSpectrum(4);
		ILibraryMassSpectrum libraryMassSpectrum = (ILibraryMassSpectrum)massSpectrum;
		assertEquals("Ethane", libraryMassSpectrum.getLibraryInformation().getName());
		assertEquals("74-84-0", libraryMassSpectrum.getLibraryInformation().getCasNumber());
		assertEquals(14, massSpectrum.getNumberOfIons());
		assertEquals(2.00020e-03f, massSpectrum.getIon(2).getAbundance());
		assertEquals(4.00040e-03f, massSpectrum.getIon(12).getAbundance());
		assertEquals(1.00010e-02f, massSpectrum.getIon(13).getAbundance());
		assertEquals(3.00030e-02f, massSpectrum.getIon(14).getAbundance());
		assertEquals(4.40044e-02f, massSpectrum.getIon(15).getAbundance());
		assertEquals(1.00010e-03f, massSpectrum.getIon(16).getAbundance());
		assertEquals(5.00050e-03f, massSpectrum.getIon(24).getAbundance());
		assertEquals(3.50035e-02f, massSpectrum.getIon(25).getAbundance());
		assertEquals(2.32223e-01f, massSpectrum.getIon(26).getAbundance());
		assertEquals(3.32333e-01f, massSpectrum.getIon(27).getAbundance());
		assertEquals(1.00000e+00f, massSpectrum.getIon(28).getAbundance());
		assertEquals(2.15222e-01f, massSpectrum.getIon(29).getAbundance());
		assertEquals(2.62226e-01f, massSpectrum.getIon(30).getAbundance());
		assertEquals(5.00050e-03f, massSpectrum.getIon(31).getAbundance());
	}

	public void test_6() throws AbundanceLimitExceededException, IonLimitExceededException {

		IScanMSD massSpectrum = massSpectra.getMassSpectrum(5);
		ILibraryMassSpectrum libraryMassSpectrum = (ILibraryMassSpectrum)massSpectrum;
		assertEquals("Ethylene", libraryMassSpectrum.getLibraryInformation().getName());
		assertEquals("74-85-1", libraryMassSpectrum.getLibraryInformation().getCasNumber());
		assertEquals(12, massSpectrum.getNumberOfIons());
		assertEquals(1.00010e-03f, massSpectrum.getIon(2).getAbundance());
		assertEquals(5.00050e-03f, massSpectrum.getIon(12).getAbundance());
		assertEquals(9.00090e-03f, massSpectrum.getIon(13).getAbundance());
		assertEquals(2.10021e-02f, massSpectrum.getIon(14).getAbundance());
		assertEquals(3.00030e-03f, massSpectrum.getIon(15).getAbundance());
		assertEquals(2.30023e-02f, massSpectrum.getIon(24).getAbundance());
		assertEquals(7.81078e-02f, massSpectrum.getIon(25).getAbundance());
		assertEquals(5.29553e-01f, massSpectrum.getIon(26).getAbundance());
		assertEquals(6.23662e-01f, massSpectrum.getIon(27).getAbundance());
		assertEquals(1.00000e+00f, massSpectrum.getIon(28).getAbundance());
		assertEquals(2.30023e-02f, massSpectrum.getIon(29).getAbundance());
		assertEquals(1.00010e-03f, massSpectrum.getIon(30).getAbundance());
	}
}

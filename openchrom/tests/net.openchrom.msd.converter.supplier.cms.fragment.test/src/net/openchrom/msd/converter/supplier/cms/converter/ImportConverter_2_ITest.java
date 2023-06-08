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
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorLibraryMassSpectrum;

public class ImportConverter_2_ITest extends TestCase {

	private IMassSpectra massSpectra;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		File importFile = new File(TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_MASS_SPECTRA_2));
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
		ICalibratedVendorLibraryMassSpectrum calibratedVendorLibraryMassSpectrum = (ICalibratedVendorLibraryMassSpectrum)massSpectrum;
		assertEquals("Argon", libraryMassSpectrum.getLibraryInformation().getName());
		assertEquals("7440-37-1", libraryMassSpectrum.getLibraryInformation().getCasNumber());
		assertEquals(4, massSpectrum.getNumberOfIons());
		assertEquals(2.9242924e-06f, massSpectrum.getIon(20).getAbundance());
		assertEquals(6.0006e-08f, massSpectrum.getIon(36).getAbundance());
		assertEquals(1.0001e-08f, massSpectrum.getIon(38).getAbundance());
		assertEquals(2e-05f, massSpectrum.getIon(40).getAbundance());
		assertEquals("amp", calibratedVendorLibraryMassSpectrum.getSignalUnits());
		assertEquals(1.0d, calibratedVendorLibraryMassSpectrum.getSourcePressure());
		assertEquals("mbar", calibratedVendorLibraryMassSpectrum.getSourcePressureUnits());
		assertEquals("2016-12-12_14:22:00_EDT", calibratedVendorLibraryMassSpectrum.getTimeStamp());
		assertEquals(0d, calibratedVendorLibraryMassSpectrum.getEtimes());
		assertEquals(70d, calibratedVendorLibraryMassSpectrum.getEenergy());
		assertEquals(3000d, calibratedVendorLibraryMassSpectrum.getIenergy());
		assertEquals("VG 14-80", calibratedVendorLibraryMassSpectrum.getInstrumentName());
		// ...
	}

	public void test_3() throws AbundanceLimitExceededException, IonLimitExceededException {

		IScanMSD massSpectrum = massSpectra.getMassSpectrum(2);
		ILibraryMassSpectrum libraryMassSpectrum = (ILibraryMassSpectrum)massSpectrum;
		ICalibratedVendorLibraryMassSpectrum calibratedVendorLibraryMassSpectrum = (ICalibratedVendorLibraryMassSpectrum)massSpectrum;
		assertEquals("Nitrogen", libraryMassSpectrum.getLibraryInformation().getName());
		assertEquals("7727-37-9", libraryMassSpectrum.getLibraryInformation().getCasNumber());
		assertEquals(3, massSpectrum.getNumberOfIons());
		assertEquals(2.75828e-06f, massSpectrum.getIon(14).getAbundance());
		assertEquals(2e-05f, massSpectrum.getIon(28).getAbundance());
		assertEquals(1.48015e-07f, massSpectrum.getIon(29).getAbundance());
		assertEquals("amp", calibratedVendorLibraryMassSpectrum.getSignalUnits());
		// ...
	}

	public void test_4() throws AbundanceLimitExceededException, IonLimitExceededException {

		IScanMSD massSpectrum = massSpectra.getMassSpectrum(3);
		ILibraryMassSpectrum libraryMassSpectrum = (ILibraryMassSpectrum)massSpectrum;
		ICalibratedVendorLibraryMassSpectrum calibratedVendorLibraryMassSpectrum = (ICalibratedVendorLibraryMassSpectrum)massSpectrum;
		assertEquals("Oxygen", libraryMassSpectrum.getLibraryInformation().getName());
		assertEquals("7782-44-7", libraryMassSpectrum.getLibraryInformation().getCasNumber());
		assertEquals(2, massSpectrum.getNumberOfIons());
		assertEquals(4.36044e-06f, massSpectrum.getIon(16).getAbundance());
		assertEquals(2e-05f, massSpectrum.getIon(32).getAbundance());
		assertEquals("amp", calibratedVendorLibraryMassSpectrum.getSignalUnits());
		// ...
	}

	public void test_5() throws AbundanceLimitExceededException, IonLimitExceededException {

		IScanMSD massSpectrum = massSpectra.getMassSpectrum(4);
		ILibraryMassSpectrum libraryMassSpectrum = (ILibraryMassSpectrum)massSpectrum;
		ICalibratedVendorLibraryMassSpectrum calibratedVendorLibraryMassSpectrum = (ICalibratedVendorLibraryMassSpectrum)massSpectrum;
		assertEquals("Ethane", libraryMassSpectrum.getLibraryInformation().getName());
		assertEquals("74-84-0", libraryMassSpectrum.getLibraryInformation().getCasNumber());
		assertEquals(14, massSpectrum.getNumberOfIons());
		assertEquals(4.0004e-08f, massSpectrum.getIon(2).getAbundance());
		assertEquals(8.0008e-08f, massSpectrum.getIon(12).getAbundance());
		assertEquals(2.0002e-07f, massSpectrum.getIon(13).getAbundance());
		assertEquals(6.0006e-07f, massSpectrum.getIon(14).getAbundance());
		assertEquals(8.80088e-07f, massSpectrum.getIon(15).getAbundance());
		assertEquals(2.0002e-08f, massSpectrum.getIon(16).getAbundance());
		assertEquals(1.0001e-07f, massSpectrum.getIon(24).getAbundance());
		assertEquals(7.0007e-07f, massSpectrum.getIon(25).getAbundance());
		assertEquals(4.64446e-06f, massSpectrum.getIon(26).getAbundance());
		assertEquals(6.64666e-06f, massSpectrum.getIon(27).getAbundance());
		assertEquals(2e-05f, massSpectrum.getIon(28).getAbundance());
		assertEquals(4.30443e-06f, massSpectrum.getIon(29).getAbundance());
		assertEquals(5.24452e-06f, massSpectrum.getIon(30).getAbundance());
		assertEquals(1.0001e-07f, massSpectrum.getIon(31).getAbundance());
		assertEquals("amp", calibratedVendorLibraryMassSpectrum.getSignalUnits());
		// ...
	}

	public void test_6() throws AbundanceLimitExceededException, IonLimitExceededException {

		IScanMSD massSpectrum = massSpectra.getMassSpectrum(5);
		ILibraryMassSpectrum libraryMassSpectrum = (ILibraryMassSpectrum)massSpectrum;
		ICalibratedVendorLibraryMassSpectrum calibratedVendorLibraryMassSpectrum = (ICalibratedVendorLibraryMassSpectrum)massSpectrum;
		assertEquals("Ethylene", libraryMassSpectrum.getLibraryInformation().getName());
		assertEquals("74-85-1", libraryMassSpectrum.getLibraryInformation().getCasNumber());
		assertEquals(12, massSpectrum.getNumberOfIons());
		assertEquals(2.0002e-08f, massSpectrum.getIon(2).getAbundance());
		assertEquals(1.0001e-07f, massSpectrum.getIon(12).getAbundance());
		assertEquals(1.80018e-07f, massSpectrum.getIon(13).getAbundance());
		assertEquals(4.20042e-07f, massSpectrum.getIon(14).getAbundance());
		assertEquals(6.0006e-08f, massSpectrum.getIon(15).getAbundance());
		assertEquals(4.60046e-07f, massSpectrum.getIon(24).getAbundance());
		assertEquals(1.56216e-06f, massSpectrum.getIon(25).getAbundance());
		assertEquals(1.05911e-05f, massSpectrum.getIon(26).getAbundance());
		assertEquals(1.24732e-05f, massSpectrum.getIon(27).getAbundance());
		assertEquals(2e-05f, massSpectrum.getIon(28).getAbundance());
		assertEquals(4.60046e-07f, massSpectrum.getIon(29).getAbundance());
		assertEquals(2.0002e-08f, massSpectrum.getIon(30).getAbundance());
		assertEquals("amp", calibratedVendorLibraryMassSpectrum.getSignalUnits());
		// ...
	}
}

/*******************************************************************************
 * Copyright (c) 2016, 2026 Walter Whitlock, Philip Wenig.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Walter Whitlock - initial API and implementation
 * Philip Wenig - initial API and implementation
 * Alexander Kerner - Generics
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cms.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;

import org.eclipse.chemclipse.msd.converter.database.IDatabaseImportConverter;
import org.eclipse.chemclipse.msd.model.core.ILibraryMassSpectrum;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorLibraryMassSpectrum;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ImportConverter_2_ITest {

	private IMassSpectra massSpectra;

	@Test
	@Order(1)
	public void testImport() {

		File importFile = new File("testData/files/import/MassSpectra2.cms");
		IDatabaseImportConverter importConverter = new DatabaseImportConverter();
		IProcessingInfo<IMassSpectra> processingInfo = importConverter.convert(importFile, new NullProgressMonitor());
		massSpectra = processingInfo.getProcessingResult();
		assertNotNull(massSpectra);
	}

	@Test
	public void test1() {

		assertEquals(5, massSpectra.size());
	}

	@Test
	public void test2() {

		IScanMSD massSpectrum = massSpectra.getMassSpectrum(1);
		ILibraryMassSpectrum libraryMassSpectrum = (ILibraryMassSpectrum)massSpectrum;
		ICalibratedVendorLibraryMassSpectrum calibratedVendorLibraryMassSpectrum = (ICalibratedVendorLibraryMassSpectrum)massSpectrum;
		assertEquals("Argon", libraryMassSpectrum.getLibraryInformation().getName());
		assertEquals("7440-37-1", libraryMassSpectrum.getLibraryInformation().getCasNumber());
		assertEquals(4, massSpectrum.getNumberOfIons());
		assertEquals(2.9242924e-06f, massSpectrum.getIon(20).getAbundance(), 0);
		assertEquals(6.0006e-08f, massSpectrum.getIon(36).getAbundance(), 0);
		assertEquals(1.0001e-08f, massSpectrum.getIon(38).getAbundance(), 0);
		assertEquals(2e-05f, massSpectrum.getIon(40).getAbundance(), 0);
		assertEquals("amp", calibratedVendorLibraryMassSpectrum.getSignalUnits());
		assertEquals(1.0d, calibratedVendorLibraryMassSpectrum.getSourcePressure(), 0);
		assertEquals("mbar", calibratedVendorLibraryMassSpectrum.getSourcePressureUnits());
		assertEquals("2016-12-12_14:22:00_EDT", calibratedVendorLibraryMassSpectrum.getTimeStamp());
		assertEquals(0d, calibratedVendorLibraryMassSpectrum.getEtimes(), 0);
		assertEquals(70d, calibratedVendorLibraryMassSpectrum.getEenergy(), 0);
		assertEquals(3000d, calibratedVendorLibraryMassSpectrum.getIenergy(), 0);
		assertEquals("VG 14-80", calibratedVendorLibraryMassSpectrum.getInstrumentName());
		// ...
	}

	@Test
	public void test3() {

		IScanMSD massSpectrum = massSpectra.getMassSpectrum(2);
		ILibraryMassSpectrum libraryMassSpectrum = (ILibraryMassSpectrum)massSpectrum;
		ICalibratedVendorLibraryMassSpectrum calibratedVendorLibraryMassSpectrum = (ICalibratedVendorLibraryMassSpectrum)massSpectrum;
		assertEquals("Nitrogen", libraryMassSpectrum.getLibraryInformation().getName());
		assertEquals("7727-37-9", libraryMassSpectrum.getLibraryInformation().getCasNumber());
		assertEquals(3, massSpectrum.getNumberOfIons());
		assertEquals(2.75828e-06f, massSpectrum.getIon(14).getAbundance(), 0);
		assertEquals(2e-05f, massSpectrum.getIon(28).getAbundance(), 0);
		assertEquals(1.48015e-07f, massSpectrum.getIon(29).getAbundance(), 0);
		assertEquals("amp", calibratedVendorLibraryMassSpectrum.getSignalUnits());
		// ...
	}

	@Test
	public void test4() {

		IScanMSD massSpectrum = massSpectra.getMassSpectrum(3);
		ILibraryMassSpectrum libraryMassSpectrum = (ILibraryMassSpectrum)massSpectrum;
		ICalibratedVendorLibraryMassSpectrum calibratedVendorLibraryMassSpectrum = (ICalibratedVendorLibraryMassSpectrum)massSpectrum;
		assertEquals("Oxygen", libraryMassSpectrum.getLibraryInformation().getName());
		assertEquals("7782-44-7", libraryMassSpectrum.getLibraryInformation().getCasNumber());
		assertEquals(2, massSpectrum.getNumberOfIons());
		assertEquals(4.36044e-06f, massSpectrum.getIon(16).getAbundance(), 0);
		assertEquals(2e-05f, massSpectrum.getIon(32).getAbundance(), 0);
		assertEquals("amp", calibratedVendorLibraryMassSpectrum.getSignalUnits());
		// ...
	}

	@Test
	public void test5() {

		IScanMSD massSpectrum = massSpectra.getMassSpectrum(4);
		ILibraryMassSpectrum libraryMassSpectrum = (ILibraryMassSpectrum)massSpectrum;
		ICalibratedVendorLibraryMassSpectrum calibratedVendorLibraryMassSpectrum = (ICalibratedVendorLibraryMassSpectrum)massSpectrum;
		assertEquals("Ethane", libraryMassSpectrum.getLibraryInformation().getName());
		assertEquals("74-84-0", libraryMassSpectrum.getLibraryInformation().getCasNumber());
		assertEquals(14, massSpectrum.getNumberOfIons());
		assertEquals(4.0004e-08f, massSpectrum.getIon(2).getAbundance(), 0);
		assertEquals(8.0008e-08f, massSpectrum.getIon(12).getAbundance(), 0);
		assertEquals(2.0002e-07f, massSpectrum.getIon(13).getAbundance(), 0);
		assertEquals(6.0006e-07f, massSpectrum.getIon(14).getAbundance(), 0);
		assertEquals(8.80088e-07f, massSpectrum.getIon(15).getAbundance(), 0);
		assertEquals(2.0002e-08f, massSpectrum.getIon(16).getAbundance(), 0);
		assertEquals(1.0001e-07f, massSpectrum.getIon(24).getAbundance(), 0);
		assertEquals(7.0007e-07f, massSpectrum.getIon(25).getAbundance(), 0);
		assertEquals(4.64446e-06f, massSpectrum.getIon(26).getAbundance(), 0);
		assertEquals(6.64666e-06f, massSpectrum.getIon(27).getAbundance(), 0);
		assertEquals(2e-05f, massSpectrum.getIon(28).getAbundance(), 0);
		assertEquals(4.30443e-06f, massSpectrum.getIon(29).getAbundance(), 0);
		assertEquals(5.24452e-06f, massSpectrum.getIon(30).getAbundance(), 0);
		assertEquals(1.0001e-07f, massSpectrum.getIon(31).getAbundance(), 0);
		assertEquals("amp", calibratedVendorLibraryMassSpectrum.getSignalUnits());
		// ...
	}

	@Test
	public void test6() {

		IScanMSD massSpectrum = massSpectra.getMassSpectrum(5);
		ILibraryMassSpectrum libraryMassSpectrum = (ILibraryMassSpectrum)massSpectrum;
		ICalibratedVendorLibraryMassSpectrum calibratedVendorLibraryMassSpectrum = (ICalibratedVendorLibraryMassSpectrum)massSpectrum;
		assertEquals("Ethylene", libraryMassSpectrum.getLibraryInformation().getName());
		assertEquals("74-85-1", libraryMassSpectrum.getLibraryInformation().getCasNumber());
		assertEquals(12, massSpectrum.getNumberOfIons());
		assertEquals(2.0002e-08f, massSpectrum.getIon(2).getAbundance(), 0);
		assertEquals(1.0001e-07f, massSpectrum.getIon(12).getAbundance(), 0);
		assertEquals(1.80018e-07f, massSpectrum.getIon(13).getAbundance(), 0);
		assertEquals(4.20042e-07f, massSpectrum.getIon(14).getAbundance(), 0);
		assertEquals(6.0006e-08f, massSpectrum.getIon(15).getAbundance(), 0);
		assertEquals(4.60046e-07f, massSpectrum.getIon(24).getAbundance(), 0);
		assertEquals(1.56216e-06f, massSpectrum.getIon(25).getAbundance(), 0);
		assertEquals(1.05911e-05f, massSpectrum.getIon(26).getAbundance(), 0);
		assertEquals(1.24732e-05f, massSpectrum.getIon(27).getAbundance(), 0);
		assertEquals(2e-05f, massSpectrum.getIon(28).getAbundance(), 0);
		assertEquals(4.60046e-07f, massSpectrum.getIon(29).getAbundance(), 0);
		assertEquals(2.0002e-08f, massSpectrum.getIon(30).getAbundance(), 0);
		assertEquals("amp", calibratedVendorLibraryMassSpectrum.getSignalUnits());
		// ...
	}
}

/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Matthia Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cms.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.List;

import org.eclipse.chemclipse.msd.converter.database.IDatabaseImportConverter;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.IIonMeasurement;

@TestInstance(Lifecycle.PER_CLASS)
public class ImportConverter_4_ITest {

	private IMassSpectra massSpectra;

	@BeforeAll
	public void setUp() {

		File importFile = new File("testData/files/import/MassSpectra4.cms");
		IDatabaseImportConverter importConverter = new DatabaseImportConverter();
		IProcessingInfo<IMassSpectra> processingInfo = importConverter.convert(importFile, new NullProgressMonitor());
		massSpectra = processingInfo.getProcessingResult();
	}

	@Test
	public void test_1() {

		assertEquals(4, massSpectra.size());
		IScanMSD massSpectrum = massSpectra.getMassSpectrum(1);
		ICalibratedVendorMassSpectrum cvmSpectrum = (ICalibratedVendorMassSpectrum)massSpectrum;
		assertEquals("1mbar, Argon=0.4, Nitrogen=0.2, Oxygen=0.2, Ethane=0.1, Ethylene=0.1", cvmSpectrum.getScanName());
		assertEquals("", cvmSpectrum.getLibraryInformation().getCasNumber());

		assertEquals(15, cvmSpectrum.getNumberOfIons());
		List<IIonMeasurement> ionMeasurements = cvmSpectrum.getIonMeasurements();
		assertEquals(15, ionMeasurements.size());

		assertEquals(6.53666E-7f, ionMeasurements.get(0).getSignal(), 0);
		assertEquals(8.74088E-7f, ionMeasurements.get(1).getSignal(), 0);
		assertEquals(1.16972E-6f, ionMeasurements.get(2).getSignal(), 0);
		assertEquals(5.60056E-8f, ionMeasurements.get(3).getSignal(), 0);
		assertEquals(1.0001E-8f, ionMeasurements.get(10).getSignal(), 0);
		assertEquals(8.0E-6f, ionMeasurements.get(14).getSignal(), 0);

		assertEquals(14.0d, ionMeasurements.get(0).getMZ(), 0);
		assertEquals(16.0d, ionMeasurements.get(1).getMZ(), 0);
		assertEquals(20.0d, ionMeasurements.get(2).getMZ(), 0);
		assertEquals(24.0d, ionMeasurements.get(3).getMZ(), 0);
		assertEquals(31.0d, ionMeasurements.get(10).getMZ(), 0);
		assertEquals(40.0d, ionMeasurements.get(14).getMZ(), 0);

		assertEquals("amp", cvmSpectrum.getSignalUnits());
		// ...
	}
}

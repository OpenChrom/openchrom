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
import java.util.List;

import org.eclipse.chemclipse.msd.converter.database.IDatabaseImportConverter;
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

import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.IIonMeasurement;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ImportConverter_3_ITest {

	private IMassSpectra massSpectra;

	@Test
	@Order(1)
	public void testImport() {

		File importFile = new File("testData/files/import/MassSpectra3.cms");
		IDatabaseImportConverter importConverter = new DatabaseImportConverter();
		IProcessingInfo<IMassSpectra> processingInfo = importConverter.convert(importFile, new NullProgressMonitor());
		massSpectra = processingInfo.getProcessingResult();
		assertNotNull(massSpectra);
	}

	@Test
	public void test1() {

		assertEquals(1, massSpectra.size());
		IScanMSD massSpectrum = massSpectra.getMassSpectrum(1);
		ICalibratedVendorMassSpectrum cvmSpectrum = (ICalibratedVendorMassSpectrum)massSpectrum;
		assertEquals("Argon=0.5, Nitrogen=0.3, Oxygen=0.2, Ethane=0.2, Ethylene=0.2", cvmSpectrum.getScanName());
		assertEquals("", cvmSpectrum.getLibraryInformation().getCasNumber());

		assertEquals(15, cvmSpectrum.getNumberOfIons());
		List<IIonMeasurement> ionMeasurements = cvmSpectrum.getIonMeasurements();
		assertEquals(15, ionMeasurements.size());

		assertEquals(1.0315e-06f, ionMeasurements.get(0).getSignal(), 0);
		assertEquals(8.76088e-07f, ionMeasurements.get(1).getSignal(), 0);
		assertEquals(1.46214e-06f, ionMeasurements.get(2).getSignal(), 0);
		assertEquals(1.12011e-07f, ionMeasurements.get(3).getSignal(), 0);
		assertEquals(2.0002e-08f, ionMeasurements.get(10).getSignal(), 0);
		assertEquals(1e-05f, ionMeasurements.get(14).getSignal(), 0);

		assertEquals(14.0d, ionMeasurements.get(0).getMZ(), 0);
		assertEquals(16.0d, ionMeasurements.get(1).getMZ(), 0);
		assertEquals(20.0d, ionMeasurements.get(2).getMZ(), 0);
		assertEquals(24.0d, ionMeasurements.get(3).getMZ(), 0);
		assertEquals(31.0d, ionMeasurements.get(10).getMZ(), 0);
		assertEquals(40.0d, ionMeasurements.get(14).getMZ(), 0);

		assertEquals("Amp", cvmSpectrum.getSignalUnits());
		// ...
	}
}

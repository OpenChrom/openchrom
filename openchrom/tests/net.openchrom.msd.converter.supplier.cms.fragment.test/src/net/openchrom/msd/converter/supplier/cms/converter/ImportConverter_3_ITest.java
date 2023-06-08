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
import java.util.List;

import org.eclipse.chemclipse.model.exceptions.AbundanceLimitExceededException;
import org.eclipse.chemclipse.msd.converter.database.IDatabaseImportConverter;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.exceptions.IonLimitExceededException;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;

import junit.framework.TestCase;
import net.openchrom.msd.converter.supplier.cms.PathResolver;
import net.openchrom.msd.converter.supplier.cms.TestPathHelper;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.IIonMeasurement;

public class ImportConverter_3_ITest extends TestCase {

	private IMassSpectra massSpectra;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		File importFile = new File(PathResolver.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_MASS_SPECTRA_3));
		IDatabaseImportConverter importConverter = new DatabaseImportConverter();
		IProcessingInfo<IMassSpectra> processingInfo = importConverter.convert(importFile, new NullProgressMonitor());
		massSpectra = processingInfo.getProcessingResult();
	}

	@Override
	protected void tearDown() throws Exception {

		massSpectra = null;
		super.tearDown();
	}

	public void test_1() throws AbundanceLimitExceededException, IonLimitExceededException {

		assertEquals(1, massSpectra.size());
		IScanMSD massSpectrum = massSpectra.getMassSpectrum(1);
		ICalibratedVendorMassSpectrum cvmSpectrum = (ICalibratedVendorMassSpectrum)massSpectrum;
		assertEquals("Argon=0.5, Nitrogen=0.3, Oxygen=0.2, Ethane=0.2, Ethylene=0.2", cvmSpectrum.getScanName());
		assertEquals("", cvmSpectrum.getLibraryInformation().getCasNumber());
		//
		assertEquals(15, cvmSpectrum.getNumberOfIons());
		List<IIonMeasurement> ionMeasurements = cvmSpectrum.getIonMeasurements();
		assertEquals(15, ionMeasurements.size());
		//
		assertEquals(1.0315e-06f, ionMeasurements.get(0).getSignal());
		assertEquals(8.76088e-07f, ionMeasurements.get(1).getSignal());
		assertEquals(1.46214e-06f, ionMeasurements.get(2).getSignal());
		assertEquals(1.12011e-07f, ionMeasurements.get(3).getSignal());
		assertEquals(2.0002e-08f, ionMeasurements.get(10).getSignal());
		assertEquals(1e-05f, ionMeasurements.get(14).getSignal());
		//
		assertEquals(14.0d, ionMeasurements.get(0).getMZ());
		assertEquals(16.0d, ionMeasurements.get(1).getMZ());
		assertEquals(20.0d, ionMeasurements.get(2).getMZ());
		assertEquals(24.0d, ionMeasurements.get(3).getMZ());
		assertEquals(31.0d, ionMeasurements.get(10).getMZ());
		assertEquals(40.0d, ionMeasurements.get(14).getMZ());
		//
		assertEquals("Amp", cvmSpectrum.getSignalUnits());
		// ...
	}
}

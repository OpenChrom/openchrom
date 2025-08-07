/*******************************************************************************
 * Copyright (c) 2015, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mgf.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Before;
import org.junit.Test;

import net.openchrom.msd.converter.supplier.mgf.TestPathHelper;

public class MassSpectrumImportConverter_MS1_ITest {

	private IMassSpectra massSpectra;

	@Before
	public void setUp() {

		File file = new File(TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_MS_1));
		DatabaseImportConverter importConverter = new DatabaseImportConverter();
		IProcessingInfo<IMassSpectra> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		massSpectra = processingInfo.getProcessingResult();
	}

	@Test
	public void testMassSpectra() {

		assertNotNull(massSpectra);
		assertEquals(5, massSpectra.getList().size());
	}

	@Test
	public void testMassSpectrum() {

		assertEquals(551, massSpectra.getMassSpectrum(1).getNumberOfIons());
		assertEquals(9535f, massSpectra.getMassSpectrum(2).getBasePeakAbundance(), 0);
		assertEquals(297916, massSpectra.getMassSpectrum(3).getRetentionTime());
		assertEquals(184.0712109, massSpectra.getMassSpectrum(4).getBasePeak(), 0);
	}
}

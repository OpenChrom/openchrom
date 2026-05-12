/*******************************************************************************
 * Copyright (c) 2015, 2026 Lablicate GmbH.
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;

import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class MassSpectrumImportConverter_MS1_ITest {

	private IMassSpectra massSpectra;

	@BeforeAll
	public void setUp() {

		File file = new File("testData/files/import/MS1.mgf");
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

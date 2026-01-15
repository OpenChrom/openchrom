/*******************************************************************************
 * Copyright (c) 2023, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mgf.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import net.openchrom.msd.converter.supplier.mgf.TestPathHelper;

@TestInstance(Lifecycle.PER_CLASS)
public class MassSpectrumImportConverter_ProteinPilot_Many_ITest {

	private IMassSpectra massSpectra;

	@BeforeAll
	public void setUp() throws IOException {

		File file = new File(TestPathHelper.TESTFILE_IMPORT_PROTEINPILOT_MANY_ELEMENTS);
		DatabaseImportConverter importConverter = new DatabaseImportConverter();
		IProcessingInfo<IMassSpectra> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		massSpectra = processingInfo.getProcessingResult();
	}

	@Test
	public void testMassSpectra() {

		assertNotNull(massSpectra);
		assertEquals(7532, massSpectra.getList().size());
	}

	@Test
	public void testMassSpectrum() {

		assertEquals(130, massSpectra.getMassSpectrum(5).getNumberOfIons());
		assertEquals(4446.87f, massSpectra.getMassSpectrum(2000).getBasePeakAbundance(), 0);
		assertEquals(2788000, massSpectra.getMassSpectrum(4000).getRetentionTime());
		assertEquals(516.3066, massSpectra.getMassSpectrum(6000).getBasePeak(), 0);
	}
}

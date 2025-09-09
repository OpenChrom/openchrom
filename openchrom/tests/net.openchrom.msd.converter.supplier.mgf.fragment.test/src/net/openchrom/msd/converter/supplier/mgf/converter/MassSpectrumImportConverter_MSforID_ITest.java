/*******************************************************************************
 * Copyright (c) 2023, 2025 Lablicate GmbH.
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

import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import net.openchrom.msd.converter.supplier.mgf.TestPathHelper;

@TestInstance(Lifecycle.PER_CLASS)
public class MassSpectrumImportConverter_MSforID_ITest {

	private IMassSpectra massSpectra;

	@BeforeAll
	public void setUp() {

		File file = new File(TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_MSFORID_TESTMIX));
		DatabaseImportConverter importConverter = new DatabaseImportConverter();
		IProcessingInfo<IMassSpectra> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		massSpectra = processingInfo.getProcessingResult();
	}

	@Test
	public void testMassSpectra() {

		assertNotNull(massSpectra);
		assertEquals(2259, massSpectra.getList().size());
	}

	@Test
	public void testMassSpectrum() {

		assertEquals(2, massSpectra.getMassSpectrum(1).getNumberOfIons());
		assertEquals(11.47201f, massSpectra.getMassSpectrum(200).getBasePeakAbundance(), 0);
		assertEquals(208989, massSpectra.getMassSpectrum(350).getRetentionTime());
		assertEquals(133.2336884, massSpectra.getMassSpectrum(725).getBasePeak(), 0);
	}
}

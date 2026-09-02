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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

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

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MassSpectrumImportConverter_ProteinPilot_Single_ITest {

	private IMassSpectra massSpectra;
	private File file;

	@Test
	@Order(1)
	public void testImport() {

		file = new File("testData/files/import/proteinpilot/singleElement.mgf");
		DatabaseImportConverter importConverter = new DatabaseImportConverter();
		IProcessingInfo<IMassSpectra> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		massSpectra = processingInfo.getProcessingResult();
	}

	@Test
	public void testMatch() {

		MagicNumberMatcher magicNumberMatcher = new MagicNumberMatcher();
		assertTrue(magicNumberMatcher.checkFileFormat(file));
	}

	@Test
	public void testMassSpectra() {

		assertNotNull(massSpectra);
		assertEquals(1, massSpectra.getList().size());
	}

	@Test
	public void testMassSpectrum() {

		IScanMSD massSpectrum = massSpectra.getMassSpectrum(1);
		assertEquals(45, massSpectrum.getNumberOfIons());
		assertEquals(6268.59f, massSpectrum.getBasePeakAbundance(), 0);
		assertEquals(380000, massSpectrum.getRetentionTime());
		assertEquals(1505.7377, massSpectrum.getBasePeak(), 0);
	}
}

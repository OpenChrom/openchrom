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
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.btmsp.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;

import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import net.openchrom.msd.converter.supplier.btmsp.converter.model.IMainSpectraProjection;
import net.openchrom.msd.converter.supplier.btmsp.converter.model.MainSpectraProjection;

@TestInstance(Lifecycle.PER_CLASS)
public class MassSpectrumImportConverter_Cattle_ITest {

	private IMassSpectra massSpectra;

	@BeforeAll
	public void setUp() {

		File file = new File("data/cattle.btmsp");
		DatabaseImportConverter importConverter = new DatabaseImportConverter();
		IProcessingInfo<IMassSpectra> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		massSpectra = processingInfo.getProcessingResult();
	}

	@Test
	public void testBasicValidation() {

		assertNotNull(massSpectra);
		assertFalse(massSpectra.getList().isEmpty());
		IScanMSD massSpectrum = massSpectra.getList().get(0);
		assertEquals(70, massSpectrum.getNumberOfIons());
		IMainSpectraProjection btmsp = (MainSpectraProjection)massSpectrum;
		assertEquals("Rind 1. Versuch", btmsp.getLibraryInformation().getName());
	}
}

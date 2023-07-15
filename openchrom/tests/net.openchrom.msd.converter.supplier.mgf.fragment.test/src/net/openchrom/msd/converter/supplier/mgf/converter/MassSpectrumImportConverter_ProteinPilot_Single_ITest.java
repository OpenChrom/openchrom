/*******************************************************************************
 * Copyright (c) 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mgf.converter;

import java.io.File;

import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Test;

import junit.framework.TestCase;
import net.openchrom.msd.converter.supplier.mgf.TestPathHelper;

public class MassSpectrumImportConverter_ProteinPilot_Single_ITest extends TestCase {

	private IMassSpectra massSpectra;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		File file = new File(TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_PROTEINPILOT_SINGLE_ELEMENT));
		DatabaseImportConverter importConverter = new DatabaseImportConverter();
		IProcessingInfo<IMassSpectra> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		massSpectra = processingInfo.getProcessingResult();
	}

	@Override
	protected void tearDown() throws Exception {

		massSpectra = null;
		super.tearDown();
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
		assertEquals(6268.59f, massSpectrum.getBasePeakAbundance());
		assertEquals(380000, massSpectrum.getRetentionTime());
		assertEquals(1505.7377, massSpectrum.getBasePeak());
	}
}

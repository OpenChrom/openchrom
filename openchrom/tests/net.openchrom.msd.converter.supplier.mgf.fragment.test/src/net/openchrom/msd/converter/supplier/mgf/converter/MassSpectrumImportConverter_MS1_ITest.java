/*******************************************************************************
 * Copyright (c) 2015, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mgf.converter;

import java.io.File;

import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Test;

import junit.framework.TestCase;
import net.openchrom.msd.converter.supplier.mgf.TestPathHelper;

public class MassSpectrumImportConverter_MS1_ITest extends TestCase {

	private IMassSpectra massSpectra;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		File file = new File(TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_MS_1));
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
		assertEquals(5, massSpectra.getList().size());
	}

	@Test
	public void testMassSpectrum() {

		assertEquals(551, massSpectra.getMassSpectrum(1).getNumberOfIons());
		assertEquals(9535f, massSpectra.getMassSpectrum(2).getBasePeakAbundance());
		assertEquals(297916, massSpectra.getMassSpectrum(3).getRetentionTime());
		assertEquals(184.0712109, massSpectra.getMassSpectrum(4).getBasePeak());
	}
}

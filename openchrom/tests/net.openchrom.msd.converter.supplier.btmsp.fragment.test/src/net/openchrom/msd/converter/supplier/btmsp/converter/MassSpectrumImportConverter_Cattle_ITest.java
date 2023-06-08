/*******************************************************************************
 * Copyright (c) 2015, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Dr. Alexander Kerner - initial API and implementation
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.btmsp.converter;

import java.io.File;

import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Test;

import net.openchrom.msd.converter.supplier.btmsp.TestPathHelper;
import net.openchrom.msd.converter.supplier.btmsp.converter.model.IMainSpectraProjection;
import net.openchrom.msd.converter.supplier.btmsp.converter.model.MainSpectraProjection;

import junit.framework.TestCase;

public class MassSpectrumImportConverter_Cattle_ITest extends TestCase {

	private IMassSpectra massSpectra;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		File file = new File(TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_CATTLE));
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
	public void testBasicValidation() {

		assertNotNull(massSpectra);
		assertFalse(massSpectra.getList().isEmpty());
		IScanMSD massSpectrum = massSpectra.getList().get(0);
		assertEquals(70, massSpectrum.getNumberOfIons());
		IMainSpectraProjection btmsp = (MainSpectraProjection)massSpectrum;
		assertEquals("Rind 1. Versuch", btmsp.getLibraryInformation().getName());
	}
}

/*******************************************************************************
 * Copyright (c) 2015, 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.pkf.converter;

import java.io.File;

import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;

import net.openchrom.msd.converter.supplier.pkf.TestPathHelper;

import junit.framework.TestCase;

public class MassSpectrumImportConverter_Ecoli_ITest extends TestCase {

	private IMassSpectra massSpectra = null;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		File file = new File(TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_ECOLI));
		DatabaseImportConverter importConverter = new DatabaseImportConverter();
		IProcessingInfo<IMassSpectra> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		massSpectra = processingInfo.getProcessingResult();
	}

	@Override
	protected void tearDown() throws Exception {

		massSpectra = null;
		super.tearDown();
	}

	public void testBasicValidation() {

		assertNotNull(massSpectra);
		assertEquals("ecoli-peaklist-oct16", massSpectra.getName());
		assertFalse(massSpectra.getList().isEmpty());
		IScanMSD massSpectrum = massSpectra.getMassSpectrum(1);
		assertEquals(30, massSpectrum.getNumberOfIons());
		assertEquals(18, massSpectra.getList().size()); // should be 16, but contains more
	}
}

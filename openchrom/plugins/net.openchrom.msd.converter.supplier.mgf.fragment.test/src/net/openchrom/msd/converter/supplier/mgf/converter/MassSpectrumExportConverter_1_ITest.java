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
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mgf.converter;

import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.implementation.Ion;
import org.eclipse.chemclipse.msd.model.implementation.MassSpectra;
import org.eclipse.chemclipse.msd.model.implementation.ScanMSD;

import junit.framework.TestCase;

public class MassSpectrumExportConverter_1_ITest extends TestCase {

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		// File file = new File(TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_MS_1));
		// DatabaseExportConverter exportConverter = new DatabaseExportConverter();
		//
		IMassSpectra massSpectra = new MassSpectra();
		IScanMSD massSpectrum = new ScanMSD();
		massSpectrum.addIon(new Ion(56.3f, 7382.3f));
		massSpectrum.addIon(new Ion(26.3f, 73382.3f));
		massSpectrum.addIon(new Ion(89.3f, 382.3f));
		massSpectra = new MassSpectra();
		massSpectra.addMassSpectrum(massSpectrum);
		/*
		 * TODO Export Test
		 */
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	public void test() {

		assertTrue("At least one test case is required.", true);
	}
}

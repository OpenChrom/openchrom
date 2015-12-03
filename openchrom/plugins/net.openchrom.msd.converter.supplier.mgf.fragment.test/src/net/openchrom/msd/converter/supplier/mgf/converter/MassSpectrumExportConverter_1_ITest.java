/*******************************************************************************
 * Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
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

import junit.framework.TestCase;

import org.eclipse.chemclipse.msd.converter.processing.massspectrum.IMassSpectrumExportConverterProcessingInfo;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.implementation.Ion;
import org.eclipse.chemclipse.msd.model.implementation.MassSpectra;
import org.eclipse.chemclipse.msd.model.implementation.ScanMSD;
import org.eclipse.core.runtime.NullProgressMonitor;

import net.openchrom.msd.converter.supplier.mgf.TestPathHelper;

public class MassSpectrumExportConverter_1_ITest extends TestCase {

	private File file;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		File file = new File(TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_MS_1));
		MassSpectrumExportConverter exportConverter = new MassSpectrumExportConverter();
		//
		IMassSpectra massSpectra = new MassSpectra();
		IScanMSD massSpectrum = new ScanMSD();
		massSpectrum.addIon(new Ion(56.3f, 7382.3f));
		massSpectrum.addIon(new Ion(26.3f, 73382.3f));
		massSpectrum.addIon(new Ion(89.3f, 382.3f));
		massSpectra = new MassSpectra();
		massSpectra.addMassSpectrum(massSpectrum);
		//
		IMassSpectrumExportConverterProcessingInfo processingInfo = exportConverter.convert(file, massSpectra, true, new NullProgressMonitor());
		file = processingInfo.getFile();
	}

	@Override
	protected void tearDown() throws Exception {

		file.delete();
		file = null;
		super.tearDown();
	}

	public void testExceptions_1() {

		assertTrue(file.exists());
	}
}

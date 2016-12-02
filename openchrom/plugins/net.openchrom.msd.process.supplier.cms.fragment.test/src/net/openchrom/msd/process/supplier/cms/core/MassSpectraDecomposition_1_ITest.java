/*******************************************************************************
 * Copyright (c) 2016 Walter Whitlock, Philip Wenig.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Walter Whitlock - initial API and implementation
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.process.supplier.cms.core;

import java.io.File;

import org.eclipse.chemclipse.msd.converter.massspectrum.MassSpectrumConverter;
import org.eclipse.chemclipse.msd.converter.processing.massspectrum.IMassSpectrumImportConverterProcessingInfo;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.core.runtime.NullProgressMonitor;

import net.openchrom.msd.process.supplier.cms.TestPathHelper;

import junit.framework.TestCase;

public class MassSpectraDecomposition_1_ITest extends TestCase {

	private MassSpectraDecomposition massSpectraDecomposition;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		massSpectraDecomposition = new MassSpectraDecomposition();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	public void test1() {

		File file = new File(TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_SCANS_1));
		IMassSpectrumImportConverterProcessingInfo processingInfo = MassSpectrumConverter.convert(file, new NullProgressMonitor());
		IMassSpectra massSpectra = processingInfo.getMassSpectra();
		massSpectraDecomposition.decompose(massSpectra, new NullProgressMonitor());
		//
		// File file = new File("to your *.csv");
		// IChromatogramMSDImportConverterProcessingInfo proccesingInfo = ChromatogramConverterMSD.convert(file, new NullProgressMonitor());
		// IChromatogramMSD chromatogramMSD = proccesingInfo.getChromatogram();
		// IMassSpectra massSpectra = new MassSpectra();
		// for(IScan scan : chromatogramMSD.getScans()) {
		// if(scan instanceof IScanMSD) {
		// IScanMSD massSpectrum = (IScanMSD)scan;
		// massSpectra.addMassSpectrum(massSpectrum);
		// }
		// }
		massSpectraDecomposition.decompose(massSpectra, new NullProgressMonitor());
		//
		assertTrue(true); // ;-)
	}
}

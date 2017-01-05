/*******************************************************************************
 * Copyright (c) 2016, 2017 Walter Whitlock, Philip Wenig.
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
package net.openchrom.msd.converter.supplier.cms.converter;

import java.io.File;

import org.eclipse.chemclipse.model.exceptions.AbundanceLimitExceededException;
import org.eclipse.chemclipse.msd.converter.massspectrum.IMassSpectrumExportConverter;
import org.eclipse.chemclipse.msd.converter.massspectrum.IMassSpectrumImportConverter;
import org.eclipse.chemclipse.msd.converter.processing.massspectrum.IMassSpectrumImportConverterProcessingInfo;
import org.eclipse.chemclipse.msd.model.core.ILibraryMassSpectrum;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.exceptions.IonLimitExceededException;
import org.eclipse.core.runtime.NullProgressMonitor;

import net.openchrom.msd.converter.supplier.cms.TestPathHelper;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorLibraryMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorMassSpectrum;

import junit.framework.TestCase;

public class ReImportConverter_1_ITest extends TestCase {

	private IMassSpectra massSpectra1, massSpectra2;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		IMassSpectrumImportConverter importConverter = new MassSpectrumImportConverter();
		IMassSpectrumExportConverter exportConverter = new MassSpectrumExportConverter();
		/*
		 * Import
		 */
		File importFile = new File(TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_MASS_SPECTRA_2));
		IMassSpectrumImportConverterProcessingInfo processingInfoImport = importConverter.convert(importFile, new NullProgressMonitor());
		massSpectra1 = processingInfoImport.getMassSpectra();
		/*
		 * Export
		 */
		File exportFile = new File(TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_EXPORT_MASS_SPECTRA_1));
		exportConverter.convert(exportFile, massSpectra1, false, new NullProgressMonitor());
		/*
		 * Re-Import
		 */
		File reImportFile = new File(TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_EXPORT_MASS_SPECTRA_1));
		IMassSpectrumImportConverterProcessingInfo processingInfoReImport = importConverter.convert(reImportFile, new NullProgressMonitor());
		massSpectra2 = processingInfoReImport.getMassSpectra();
		/*
		 * Delete the export file.
		 */
		// exportFile.delete();
	}

	@Override
	protected void tearDown() throws Exception {

		massSpectra1 = null;
		massSpectra2 = null;
		super.tearDown();
	}

	public void test_1() {

		assertEquals(massSpectra1.size(), massSpectra2.size());
	}

	public void test_2() throws AbundanceLimitExceededException, IonLimitExceededException {

		for(int i = 1; i <= massSpectra1.size(); i++) {
			IScanMSD massSpectrum1 = massSpectra1.getMassSpectrum(i);
			IScanMSD massSpectrum2 = massSpectra2.getMassSpectrum(i);
			if(massSpectrum1 instanceof ICalibratedVendorMassSpectrum) {
				assertEquals(0, ((ICalibratedVendorMassSpectrum)massSpectrum1).compareTo((ICalibratedVendorMassSpectrum)massSpectrum2));
			} else {
				assertEquals(0, ((ICalibratedVendorLibraryMassSpectrum)massSpectrum1).compareTo((ICalibratedVendorLibraryMassSpectrum)massSpectrum2));
			}
		}
	}
}

/*******************************************************************************
 * Copyright (c) 2016, 2019 Walter Whitlock, Philip Wenig.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Walter Whitlock - initial API and implementation
 * Philip Wenig - initial API and implementation
 * Alexander Kerner - Generics
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cms.converter;

import java.io.File;

import org.eclipse.chemclipse.model.exceptions.AbundanceLimitExceededException;
import org.eclipse.chemclipse.msd.converter.database.IDatabaseExportConverter;
import org.eclipse.chemclipse.msd.converter.database.IDatabaseImportConverter;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.exceptions.IonLimitExceededException;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;

import junit.framework.TestCase;
import net.openchrom.msd.converter.supplier.cms.PathResolver;
import net.openchrom.msd.converter.supplier.cms.TestPathHelper;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorLibraryMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorMassSpectrum;

public class ReImportConverter_1_ITest extends TestCase {

	private IMassSpectra massSpectra1, massSpectra2;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		IDatabaseImportConverter importConverter = new DatabaseImportConverter();
		IDatabaseExportConverter exportConverter = new DatabaseExportConverter();
		/*
		 * Import
		 */
		File importFile = new File(PathResolver.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_MASS_SPECTRA_2));
		IProcessingInfo<IMassSpectra> processingInfoImport = importConverter.convert(importFile, new NullProgressMonitor());
		massSpectra1 = processingInfoImport.getProcessingResult();
		/*
		 * Export
		 */
		File exportFile = new File(PathResolver.getAbsolutePath(TestPathHelper.TESTFILE_DIR_EXPORT) + File.separator + TestPathHelper.TESTFILE_MASS_SPECTRA_1);
		exportConverter.convert(exportFile, massSpectra1, false, new NullProgressMonitor());
		/*
		 * Re-Import
		 */
		File reImportFile = new File(PathResolver.getAbsolutePath(TestPathHelper.TESTFILE_DIR_EXPORT) + File.separator + TestPathHelper.TESTFILE_MASS_SPECTRA_1);
		IProcessingInfo<IMassSpectra> processingInfoReImport = importConverter.convert(reImportFile, new NullProgressMonitor());
		massSpectra2 = processingInfoReImport.getProcessingResult();
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

	public void test_1() throws AbundanceLimitExceededException, IonLimitExceededException {

		assertEquals(massSpectra1.size(), massSpectra2.size());
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

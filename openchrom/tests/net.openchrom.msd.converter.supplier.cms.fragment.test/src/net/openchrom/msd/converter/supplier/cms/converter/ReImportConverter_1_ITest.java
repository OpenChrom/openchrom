/*******************************************************************************
 * Copyright (c) 2016, 2026 Walter Whitlock, Philip Wenig.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Walter Whitlock - initial API and implementation
 * Philip Wenig - initial API and implementation
 * Alexander Kerner - Generics
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cms.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.eclipse.chemclipse.msd.converter.database.IDatabaseExportConverter;
import org.eclipse.chemclipse.msd.converter.database.IDatabaseImportConverter;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import net.openchrom.msd.converter.supplier.cms.TestPathHelper;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorLibraryMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorMassSpectrum;

@TestInstance(Lifecycle.PER_CLASS)
public class ReImportConverter_1_ITest {

	private IMassSpectra massSpectra1, massSpectra2;
	private File exportFile;

	@BeforeAll
	public void setUp() {

		IDatabaseImportConverter importConverter = new DatabaseImportConverter();
		IDatabaseExportConverter exportConverter = new DatabaseExportConverter();
		/*
		 * Import
		 */
		File importFile = new File(TestPathHelper.TESTFILE_IMPORT_MASS_SPECTRA_2);
		IProcessingInfo<IMassSpectra> processingInfoImport = importConverter.convert(importFile, new NullProgressMonitor());
		massSpectra1 = processingInfoImport.getProcessingResult();
		/*
		 * Export
		 */
		File exportFolder = new File(TestPathHelper.TESTFILE_DIR_EXPORT);
		exportFile = new File(exportFolder, File.separator + TestPathHelper.TESTFILE_MASS_SPECTRA_1);
		exportConverter.convert(exportFile, massSpectra1, false, new NullProgressMonitor());
		/*
		 * Re-Import
		 */
		File reImportFile = new File(exportFolder, File.separator + TestPathHelper.TESTFILE_MASS_SPECTRA_1);
		IProcessingInfo<IMassSpectra> processingInfoReImport = importConverter.convert(reImportFile, new NullProgressMonitor());
		massSpectra2 = processingInfoReImport.getProcessingResult();

	}

	@AfterAll
	public void tearDown() {

		/*
		 * Delete the export file.
		 */
		exportFile.delete();
	}

	@Test
	public void test_1() {

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

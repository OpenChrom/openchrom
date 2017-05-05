/*******************************************************************************
 * Copyright (c) 2017 whitlow.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * whitlow - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cms.converter;

import java.io.File;

import org.eclipse.chemclipse.model.exceptions.AbundanceLimitExceededException;
import org.eclipse.chemclipse.msd.converter.massspectrum.IMassSpectrumExportConverter;
import org.eclipse.chemclipse.msd.converter.massspectrum.IMassSpectrumImportConverter;
import org.eclipse.chemclipse.msd.converter.processing.massspectrum.IMassSpectrumImportConverterProcessingInfo;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.exceptions.IonLimitExceededException;
import org.eclipse.core.runtime.NullProgressMonitor;

import net.openchrom.msd.converter.supplier.cms.PathResolver;
import net.openchrom.msd.converter.supplier.cms.TestPathHelper;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorLibraryMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorMassSpectrum;

import junit.framework.TestCase;

public class ReImportConverter_2_ITest extends TestCase {

	private IMassSpectra massSpectra1, massSpectra2;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		IMassSpectrumImportConverter importConverter = new MassSpectrumImportConverter();
		IMassSpectrumExportConverter exportConverter = new MassSpectrumExportConverter();
		/*
		 * Import
		 */
		File importFile = new File(PathResolver.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_MASS_SPECTRA_5));
		IMassSpectrumImportConverterProcessingInfo processingInfoImport = importConverter.convert(importFile, new NullProgressMonitor());
		massSpectra1 = processingInfoImport.getMassSpectra();
		// calculate and subtract signal zero offset
		for(IScanMSD spectrum : massSpectra1.getList()) {
			if(spectrum instanceof ICalibratedVendorMassSpectrum) {
				if(((ICalibratedVendorMassSpectrum)spectrum).calculateSignalOffset()) {
					// float offsetValue = ((ICalibratedVendorMassSpectrum)spectrum).getSignalOffset();
					((ICalibratedVendorMassSpectrum)spectrum).subtractSignalOffset();
				}
				;
			}
		}
		/*
		 * Export
		 */
		File exportFile = new File(PathResolver.getAbsolutePath(TestPathHelper.TESTFILE_DIR_EXPORT) + File.separator + TestPathHelper.TESTFILE_MASS_SPECTRA_2);
		exportConverter.convert(exportFile, massSpectra1, false, new NullProgressMonitor());
		/*
		 * Re-Import
		 */
		File reImportFile = new File(PathResolver.getAbsolutePath(TestPathHelper.TESTFILE_DIR_EXPORT) + File.separator + TestPathHelper.TESTFILE_MASS_SPECTRA_2);
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
				// test if signal offset was removed from massSpectrum2 when it was written
				((ICalibratedVendorMassSpectrum)massSpectrum1).resetSignalOffset();
				assertEquals(0, ((ICalibratedVendorMassSpectrum)massSpectrum1).compareTo((ICalibratedVendorMassSpectrum)massSpectrum2));
			} else {
				assertEquals(0, ((ICalibratedVendorLibraryMassSpectrum)massSpectrum1).compareTo((ICalibratedVendorLibraryMassSpectrum)massSpectrum2));
			}
		}
	}
}

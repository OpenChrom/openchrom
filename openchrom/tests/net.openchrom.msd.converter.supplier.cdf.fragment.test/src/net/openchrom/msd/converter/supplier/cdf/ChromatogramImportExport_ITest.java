/*******************************************************************************
 * Copyright (c) 2011, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cdf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.eclipse.chemclipse.converter.PathResolver;
import org.eclipse.chemclipse.msd.converter.chromatogram.ChromatogramConverterMSD;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.IProcessingMessage;
import org.eclipse.chemclipse.xxd.converter.supplier.ocx.versions.VersionConstants;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.osgi.framework.FrameworkUtil;

@TestInstance(Lifecycle.PER_CLASS)
public class ChromatogramImportExport_ITest {

	private IChromatogramMSD chromatogram;
	private IChromatogramMSD chromatogramImport;
	private File fileExport;

	@BeforeAll
	public void setUp() throws IOException {

		/*
		 * Import
		 */
		String extensionPointImport = VersionConstants.CONVERTER_ID_CHROMATOGRAM;
		/*
		 * Export/Reimport
		 */
		File directory = new File(TestPathHelper.DIRECTORY_EXPORT_TEST);
		directory.mkdir();
		String pathExport = directory.getAbsolutePath() + File.separator + "Test.cdf";
		String extensionPointExportReimport = "net.openchrom.msd.converter.supplier.cdf";
		/*
		 * Import the chromatogram.
		 */
		File fileImport = PathResolver.getFile(FrameworkUtil.getBundle(getClass()), TestPathHelper.TESTFILE_IMPORT_CHROMATOGRAM_2);
		IProcessingInfo<IChromatogramMSD> processingInfoImport = ChromatogramConverterMSD.getInstance().convert(fileImport, extensionPointImport, new NullProgressMonitor());
		chromatogramImport = processingInfoImport.getProcessingResult();
		for(IProcessingMessage message : processingInfoImport.getMessages()) {
			System.out.println(message.getMessage());
		}
		/*
		 * Export the chromatogram.
		 */
		fileExport = new File(pathExport);
		IProcessingInfo<File> processingInfoExport = ChromatogramConverterMSD.getInstance().convert(fileExport, chromatogramImport, extensionPointExportReimport, new NullProgressMonitor());
		fileExport = processingInfoExport.getProcessingResult();
		for(IProcessingMessage message : processingInfoExport.getMessages()) {
			System.out.println(message.getMessage());
		}
		/*
		 * Reimport the exported chromatogram.
		 */
		IProcessingInfo<IChromatogramMSD> processingInfo = ChromatogramConverterMSD.getInstance().convert(fileExport, extensionPointExportReimport, new NullProgressMonitor());
		chromatogram = processingInfo.getProcessingResult();
		for(IProcessingMessage message : processingInfo.getMessages()) {
			System.out.println(message.getMessage());
		}
	}

	@AfterAll
	public void tearDown() {

		fileExport.delete();
	}

	@Test
	public void testImport() {

		assertNotNull(chromatogramImport);
	}

	@Test
	public void testExport() {

		assertTrue(fileExport.exists());
	}

	@Test
	public void testReimport() {

		assertNotNull(chromatogram);
		assertEquals(5726, chromatogram.getNumberOfScans());
		assertEquals(930401, chromatogram.getNumberOfScanIons());
	}
}
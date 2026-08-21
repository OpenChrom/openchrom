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
package net.openchrom.wsd.converter.supplier.animl.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;

import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.IProcessingMessage;
import org.eclipse.chemclipse.wsd.converter.chromatogram.ChromatogramConverterWSD;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.chemclipse.xxd.converter.supplier.ocx.versions.VersionConstants;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ChromatogramImportExport_ITest {

	private IChromatogramWSD chromatogramImport;
	private IChromatogramWSD chromatogram;
	private File fileExport;

	@Test
	@Order(1)
	public void testReImport() {

		/*
		 * Import
		 */
		String extensionPointImport = VersionConstants.CONVERTER_ID_CHROMATOGRAM;
		/*
		 * Export/Reimport
		 */
		File directory = new File("testData/files/export");
		directory.mkdir();
		String pathExport = directory.getAbsolutePath() + File.separator + "Test.animl";
		String extensionPointExportReimport = "net.openchrom.wsd.converter.supplier.animl.chromatogram";
		/*
		 * Import the chromatogram.
		 */
		File fileImport = new File("testData/files/import/Demo.ocb");
		IProcessingInfo<IChromatogramWSD> processingInfoImport = ChromatogramConverterWSD.getInstance().convert(fileImport, extensionPointImport, new NullProgressMonitor());
		chromatogramImport = processingInfoImport.getProcessingResult();
		for(IProcessingMessage message : processingInfoImport.getMessages()) {
			System.out.println(message.getMessage());
		}
		/*
		 * Export the chromatogram.
		 */
		fileExport = new File(pathExport);
		IProcessingInfo<File> processingInfoExport = ChromatogramConverterWSD.getInstance().convert(fileExport, chromatogramImport, extensionPointExportReimport, new NullProgressMonitor());
		fileExport = processingInfoExport.getProcessingResult();
		for(IProcessingMessage message : processingInfoExport.getMessages()) {
			System.out.println(message.getMessage());
		}
		/*
		 * Reimport the exported chromatogram.
		 */
		chromatogramImport = null;
		IProcessingInfo<IChromatogramWSD> processingInfo = ChromatogramConverterWSD.getInstance().convert(fileExport, extensionPointExportReimport, new NullProgressMonitor());
		chromatogram = processingInfo.getProcessingResult();
		for(IProcessingMessage message : processingInfo.getMessages()) {
			System.out.println(message.getMessage());
		}
		assertNotNull(chromatogram);
		assertEquals(4950, chromatogram.getNumberOfScans());
		assertEquals(280f, chromatogram.getWavelengths().iterator().next(), 0);
	}

	@AfterAll
	public void tearDown() {

		fileExport.delete();
	}
}
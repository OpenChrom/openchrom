/*******************************************************************************
 * Copyright (c) 2011, 2024 Lablicate GmbH.
 *
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.wsd.converter.supplier.animl.converter;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.IProcessingMessage;
import org.eclipse.chemclipse.wsd.converter.chromatogram.ChromatogramConverterWSD;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Test;

import net.openchrom.wsd.converter.supplier.animl.TestPathHelper;

import junit.framework.TestCase;

public class ChromatogramImportExport_ITest extends TestCase {

	protected IChromatogramWSD chromatogramImport;
	protected IChromatogramWSD chromatogram;
	protected String pathImport;
	protected String pathExport;
	protected File fileImport;
	protected File fileExport;
	protected String extensionPointImport;
	protected String extensionPointExportReimport;

	@Override
	protected void setUp() {

		/*
		 * Import
		 */
		pathImport = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_DEMO);
		extensionPointImport = "org.eclipse.chemclipse.xxd.converter.supplier.chemclipse";
		/*
		 * Export/Reimport
		 */
		File directory = new File(TestPathHelper.DIRECTORY_EXPORT_TEST);
		directory.mkdir();
		pathExport = TestPathHelper.getAbsolutePath(TestPathHelper.DIRECTORY_EXPORT_TEST) + File.separator + "Test.animl";
		extensionPointExportReimport = "net.openchrom.wsd.converter.supplier.animl.chromatogram";
		/*
		 * Import the chromatogram.
		 */
		fileImport = new File(this.pathImport);
		IProcessingInfo<IChromatogramWSD> processingInfoImport = ChromatogramConverterWSD.getInstance().convert(fileImport, this.extensionPointImport, new NullProgressMonitor());
		chromatogramImport = processingInfoImport.getProcessingResult();
		for(IProcessingMessage message : processingInfoImport.getMessages()) {
			System.out.println(message.getMessage());
		}
		/*
		 * Export the chromatogram.
		 */
		fileExport = new File(this.pathExport);
		IProcessingInfo<File> processingInfoExport = ChromatogramConverterWSD.getInstance().convert(fileExport, chromatogramImport, this.extensionPointExportReimport, new NullProgressMonitor());
		fileExport = processingInfoExport.getProcessingResult();
		for(IProcessingMessage message : processingInfoExport.getMessages()) {
			System.out.println(message.getMessage());
		}
		/*
		 * Reimport the exported chromatogram.
		 */
		chromatogramImport = null;
		IProcessingInfo<IChromatogramWSD> processingInfo = ChromatogramConverterWSD.getInstance().convert(fileExport, this.extensionPointExportReimport, new NullProgressMonitor());
		chromatogram = processingInfo.getProcessingResult();
		for(IProcessingMessage message : processingInfo.getMessages()) {
			System.out.println(message.getMessage());
		}
	}

	@Override
	protected void tearDown() throws Exception {

		pathImport = null;
		pathExport = null;
		fileImport = null;
		fileExport.delete();
		fileExport = null;
		chromatogramImport = null;
		chromatogram = null;
		super.tearDown();
	}

	@Test
	public void testReimport() {

		assertNotNull(chromatogram);
		assertEquals(4950, chromatogram.getNumberOfScans());
		assertEquals(280f, new ArrayList<>(chromatogram.getWavelengths()).get(0));
	}
}
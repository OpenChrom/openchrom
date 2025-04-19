/*******************************************************************************
 * Copyright (c) 2016, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Philip Wenig - initial API and implementation
 * Alexander Kerner - Generics
 *******************************************************************************/
package net.openchrom.wsd.converter.supplier.abif;

import java.io.File;

import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.wsd.converter.chromatogram.ChromatogramConverterWSD;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Ignore;

import junit.framework.TestCase;

@Ignore("Not a test case. Just a template to avoid boiler plate code.")
public class ChromatogramWriterTestCase extends TestCase {

	protected IChromatogramWSD chromatogram;
	//
	protected String extensionPointIdImport;
	protected String pathImport;
	protected File fileImport;
	//
	protected String extensionPointIdExport;
	protected String pathExport;
	protected File fileExport;

	@Override
	protected void setUp() throws Exception {

		super.setUp();

		fileImport = new File(this.pathImport);
		IProcessingInfo<IChromatogramWSD> importProcessingInfo = ChromatogramConverterWSD.getInstance().convert(fileImport, extensionPointIdImport, new NullProgressMonitor());
		chromatogram = importProcessingInfo.getProcessingResult();
		//
		fileExport = new File(this.pathExport);
		IProcessingInfo<File> exportProcessingInfo = ChromatogramConverterWSD.getInstance().convert(fileExport, chromatogram, extensionPointIdExport, new NullProgressMonitor());
		File fileReImport = exportProcessingInfo.getProcessingResult();
		//
		IProcessingInfo<IChromatogramWSD> reImportProcessingInfo = ChromatogramConverterWSD.getInstance().convert(fileReImport, new NullProgressMonitor());
		chromatogram = reImportProcessingInfo.getProcessingResult();
	}

	@Override
	protected void tearDown() throws Exception {

		pathImport = null;
		fileImport = null;
		chromatogram = null;

		super.tearDown();
	}
}

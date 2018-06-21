/*******************************************************************************
 * Copyright (c) 2016, 2018 Matthias Mailänder, Dr. Philip Wenig.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.wsd.converter.supplier.abif;

import java.io.File;

import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.exceptions.TypeCastException;
import org.eclipse.chemclipse.wsd.converter.chromatogram.ChromatogramConverterWSD;
import org.eclipse.chemclipse.wsd.converter.processing.chromatogram.IChromatogramWSDImportConverterProcessingInfo;
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
		try {
			fileImport = new File(this.pathImport);
			IChromatogramWSDImportConverterProcessingInfo importProcessingInfo = ChromatogramConverterWSD.convert(fileImport, extensionPointIdImport, new NullProgressMonitor());
			chromatogram = importProcessingInfo.getChromatogram();
			//
			fileExport = new File(this.pathExport);
			IProcessingInfo exportProcessingInfo = ChromatogramConverterWSD.convert(fileExport, chromatogram, extensionPointIdExport, new NullProgressMonitor());
			File fileReImport = exportProcessingInfo.getProcessingResult(File.class);
			//
			IChromatogramWSDImportConverterProcessingInfo reImportProcessingInfo = ChromatogramConverterWSD.convert(fileReImport, new NullProgressMonitor());
			chromatogram = reImportProcessingInfo.getChromatogram();
		} catch(TypeCastException e) {
			chromatogram = null;
		}
	}

	@Override
	protected void tearDown() throws Exception {

		pathImport = null;
		fileImport = null;
		chromatogram = null;
		//
		System.gc();
		//
		super.tearDown();
	}
}

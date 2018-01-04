/*******************************************************************************
 * Copyright (c) 2016, 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mgf;

import java.io.File;

import org.eclipse.chemclipse.msd.converter.massspectrum.MassSpectrumConverter;
import org.eclipse.chemclipse.msd.converter.processing.massspectrum.IMassSpectrumExportConverterProcessingInfo;
import org.eclipse.chemclipse.msd.converter.processing.massspectrum.IMassSpectrumImportConverterProcessingInfo;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.processing.core.exceptions.TypeCastException;
import org.eclipse.core.runtime.NullProgressMonitor;

import junit.framework.TestCase;

public class LibraryWriterTestCase extends TestCase {

	protected IMassSpectra massSpectra;
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
			IMassSpectrumImportConverterProcessingInfo importProcessingInfo = MassSpectrumConverter.convert(fileImport, extensionPointIdImport, new NullProgressMonitor());
			massSpectra = importProcessingInfo.getMassSpectra();
			//
			fileExport = new File(this.pathExport);
			IMassSpectrumExportConverterProcessingInfo exportProcessingInfo = MassSpectrumConverter.convert(fileExport, massSpectra, false, extensionPointIdExport, new NullProgressMonitor());
			File fileReImport = exportProcessingInfo.getFile();
			//
			IMassSpectrumImportConverterProcessingInfo reImportProcessingInfo = MassSpectrumConverter.convert(fileReImport, new NullProgressMonitor());
			massSpectra = reImportProcessingInfo.getMassSpectra();
		} catch(TypeCastException e) {
			massSpectra = null;
		}
	}

	@Override
	protected void tearDown() throws Exception {

		fileExport.delete();
		pathImport = null;
		pathExport = null;
		fileImport = null;
		fileExport = null;
		massSpectra = null;
		//
		System.gc();
		//
		super.tearDown();
	}
}

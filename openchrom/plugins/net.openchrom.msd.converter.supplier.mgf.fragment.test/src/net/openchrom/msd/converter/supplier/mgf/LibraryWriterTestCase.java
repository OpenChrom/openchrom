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

import org.eclipse.chemclipse.msd.converter.database.DatabaseConverter;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
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
			IProcessingInfo importProcessingInfo = DatabaseConverter.convert(fileImport, extensionPointIdImport, new NullProgressMonitor());
			massSpectra = importProcessingInfo.getProcessingResult(IMassSpectra.class);
			//
			fileExport = new File(this.pathExport);
			IProcessingInfo exportProcessingInfo = DatabaseConverter.convert(fileExport, massSpectra, false, extensionPointIdExport, new NullProgressMonitor());
			File fileReImport = exportProcessingInfo.getProcessingResult(File.class);
			//
			IProcessingInfo reImportProcessingInfo = DatabaseConverter.convert(fileReImport, new NullProgressMonitor());
			massSpectra = reImportProcessingInfo.getProcessingResult(IMassSpectra.class);
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

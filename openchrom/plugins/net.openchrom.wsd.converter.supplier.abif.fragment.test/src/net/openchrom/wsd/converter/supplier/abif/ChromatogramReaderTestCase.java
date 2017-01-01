/*******************************************************************************
 * Copyright (c) 2016, 2017 Matthias Mailänder, Dr. Philip Wenig.
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

import org.eclipse.chemclipse.processing.core.exceptions.TypeCastException;
import org.eclipse.chemclipse.wsd.converter.chromatogram.ChromatogramConverterWSD;
import org.eclipse.chemclipse.wsd.converter.processing.chromatogram.IChromatogramWSDImportConverterProcessingInfo;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Ignore;

import junit.framework.TestCase;

@Ignore("Not a test case. Just a template to avoid boiler plate code.")
public class ChromatogramReaderTestCase extends TestCase {

	protected IChromatogramWSD chromatogram;
	protected String extensionPointId;
	protected String pathImport;
	protected File fileImport;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		fileImport = new File(this.pathImport);
		IChromatogramWSDImportConverterProcessingInfo processingInfo = ChromatogramConverterWSD.convert(fileImport, extensionPointId, new NullProgressMonitor());
		try {
			chromatogram = processingInfo.getChromatogram();
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

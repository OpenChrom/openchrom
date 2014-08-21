/*******************************************************************************
 * Copyright (c) 2013, 2014 Dr. Philip Wenig.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.chemclipse.msd.converter.supplier.cdf.io;

import java.io.File;

import org.eclipse.core.runtime.NullProgressMonitor;

import net.chemclipse.chromatogram.model.signals.ITotalScanSignalExtractor;
import net.chemclipse.chromatogram.model.signals.TotalScanSignalExtractor;
import net.chemclipse.msd.converter.chromatogram.ChromatogramConverterMSD;
import net.chemclipse.msd.converter.processing.chromatogram.IChromatogramMSDImportConverterProcessingInfo;
import net.chemclipse.msd.model.core.IChromatogramMSD;
import net.chemclipse.msd.model.xic.ExtractedIonSignalExtractor;
import net.chemclipse.msd.model.xic.IExtractedIonSignalExtractor;

import junit.framework.TestCase;

/**
 * This class initializes an AgilentChromatogramReaderTest.
 * 
 * @author eselmeister
 */
public class CDFChromatogramReaderTestCase extends TestCase {

	protected IChromatogramMSD chromatogram;
	protected String pathImport;
	protected File fileImport;
	protected ITotalScanSignalExtractor totalIonSignalExtractor;
	protected IExtractedIonSignalExtractor extractedIonSignalExtractor;
	private final static String EXTENSION_POINT_ID = "net.chemclipse.msd.converter.supplier.cdf";

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		fileImport = new File(this.pathImport);
		IChromatogramMSDImportConverterProcessingInfo processingInfo = ChromatogramConverterMSD.convert(fileImport, EXTENSION_POINT_ID, new NullProgressMonitor());
		chromatogram = processingInfo.getChromatogram();
		totalIonSignalExtractor = new TotalScanSignalExtractor(chromatogram);
		extractedIonSignalExtractor = new ExtractedIonSignalExtractor(chromatogram);
	}

	@Override
	protected void tearDown() throws Exception {

		pathImport = null;
		fileImport = null;
		chromatogram = null;
		totalIonSignalExtractor = null;
		extractedIonSignalExtractor = null;
		//
		System.gc();
		//
		super.tearDown();
	}
}

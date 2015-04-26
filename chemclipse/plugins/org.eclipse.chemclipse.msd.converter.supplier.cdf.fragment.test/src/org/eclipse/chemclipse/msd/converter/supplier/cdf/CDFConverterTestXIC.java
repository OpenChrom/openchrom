/*******************************************************************************
 * Copyright (c) 2013, 2015 Dr. Philip Wenig.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.chemclipse.msd.converter.supplier.cdf;

import java.io.File;
import java.util.Date;

import org.eclipse.core.runtime.NullProgressMonitor;

import org.eclipse.chemclipse.model.exceptions.ChromatogramIsNullException;
import org.eclipse.chemclipse.msd.converter.chromatogram.ChromatogramConverterMSD;
import org.eclipse.chemclipse.msd.converter.processing.chromatogram.IChromatogramMSDImportConverterProcessingInfo;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.xic.ExtractedIonSignalExtractor;
import org.eclipse.chemclipse.msd.model.xic.IExtractedIonSignalExtractor;
import org.eclipse.chemclipse.msd.model.xic.IExtractedIonSignals;
import org.eclipse.chemclipse.processing.core.exceptions.TypeCastException;

import junit.framework.TestCase;

public class CDFConverterTestXIC extends TestCase {

	private final static String EXTENSION_POINT_ID = "org.eclipse.chemclipse.msd.converter.supplier.cdf";
	private IExtractedIonSignalExtractor extractedIonSignalExtractor;

	// private final static String EXTENSION_POINT_EXPORT_ID =
	// "org.eclipse.chemclipse.chromatogram.msd.supplier.agilent";
	@Override
	protected void setUp() throws Exception {

		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	public void testConvert_1() {

		Date start;
		Date stop;
		String path = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_OP17760);
		File chromatogram = new File(path);
		start = new Date();
		IChromatogramMSDImportConverterProcessingInfo processingInfo = ChromatogramConverterMSD.convert(chromatogram, EXTENSION_POINT_ID, new NullProgressMonitor());
		IChromatogramMSD chrom;
		try {
			chrom = processingInfo.getChromatogram();
			stop = new Date();
			System.out.println("Milliseconds Lesen Overview: " + (stop.getTime() - start.getTime()));
			assertEquals("Scans", 5726, chrom.getNumberOfScans());
			// List<ITotalIonSignal> signals = chrom.getTotalIonSignals();
			/*
			 * for(ITotalIonSignal signal : signals) {
			 * System.out.println(signal.getRetentionTime() + " " +
			 * signal.getTotalSignal()); }
			 */
			IExtractedIonSignals signals;
			start = new Date();
			extractedIonSignalExtractor = new ExtractedIonSignalExtractor(chrom);
			signals = extractedIonSignalExtractor.getExtractedIonSignals();
			System.out.println(signals.size());
			stop = new Date();
			System.out.println("XIC: " + (stop.getTime() - start.getTime()));
			start = new Date();
			signals = extractedIonSignalExtractor.getExtractedIonSignals(28.0f, 600.0f);
			stop = new Date();
			System.out.println("XIC 28-600: " + (stop.getTime() - start.getTime()));
		} catch(TypeCastException e) {
			e.printStackTrace();
		} catch(ChromatogramIsNullException e) {
			e.printStackTrace();
		}
	}
}

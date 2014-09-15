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
package net.chemclipse.msd.converter.supplier.cdf;

import java.io.File;
import java.util.Date;

import org.eclipse.core.runtime.NullProgressMonitor;

import net.chemclipse.converter.processing.chromatogram.IChromatogramExportConverterProcessingInfo;
import net.chemclipse.msd.converter.chromatogram.ChromatogramConverterMSD;
import net.chemclipse.msd.converter.processing.chromatogram.IChromatogramMSDImportConverterProcessingInfo;
import net.chemclipse.msd.model.core.IChromatogramMSD;
import net.chemclipse.processing.core.exceptions.TypeCastException;

import junit.framework.TestCase;

public class CDFConverterTestX extends TestCase {

	private final static String EXTENSION_POINT_CDF_ID = "net.chemclipse.msd.converter.supplier.cdf";
	private final static String EXTENSION_POINT_AGILENT_ID = "net.chemclipse.msd.converter.supplier.agilent";

	@Override
	protected void setUp() throws Exception {

		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	// TODO Test anpassen wenn IChromatogram implementiert ist
	public void testConvert_1() {

		Date start;
		Date stop;
		// String path =
		// TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_OP17760);
		String path = "E:\\tmp\\netCDF\\OP17763.D\\DATA.MS";
		File chromatogram = new File(path);
		// String pathExport = "E:\\tmp\\OPCDF.D\\DATA.MS";
		String pathExport = "E:\\tmp\\netCDF\\OP17763.CDF";
		// String pathExport = "/home/eselmeister/tmp/OPCDF.CDF";
		// String pathExport = "/home/eselmeister/tmp/OPCDF.D/DATA.MS";
		File chromatogramExport = new File(pathExport);
		start = new Date();
		IChromatogramMSD chrom;
		IChromatogramMSDImportConverterProcessingInfo processingInfo = ChromatogramConverterMSD.convert(chromatogram, EXTENSION_POINT_AGILENT_ID, new NullProgressMonitor());
		try {
			chrom = processingInfo.getChromatogram();
			stop = new Date();
			System.out.println("Milliseconds Lesen: " + (stop.getTime() - start.getTime()));
			assertEquals("Scans", 5726, chrom.getNumberOfScans());
			// assertEquals("TS", 55822.0d, chrom.getScan(3).getTotalSignal());
			// chrom.removeScans(3398, 3585);
			start = new Date();
			IChromatogramExportConverterProcessingInfo processingInfoExport = ChromatogramConverterMSD.convert(chromatogramExport, chrom, EXTENSION_POINT_CDF_ID, new NullProgressMonitor());
			File test;
			try {
				test = processingInfoExport.getFile();
				stop = new Date();
				System.out.println("Milliseconds Schreiben: " + (stop.getTime() - start.getTime()));
				assertEquals("File path", pathExport, test.getAbsolutePath());
			} catch(TypeCastException e) {
				e.printStackTrace();
			}
		} catch(TypeCastException e1) {
			e1.printStackTrace();
		}
	}
}

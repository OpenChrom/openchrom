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
import net.chemclipse.converter.processing.chromatogram.IChromatogramOverviewImportConverterProcessingInfo;
import net.chemclipse.chromatogram.model.core.IChromatogramOverview;
import net.chemclipse.msd.converter.chromatogram.ChromatogramConverterMSD;
import net.chemclipse.msd.converter.processing.chromatogram.IChromatogramMSDImportConverterProcessingInfo;
import net.chemclipse.msd.model.core.IChromatogramMSD;
import net.chemclipse.processing.core.exceptions.TypeCastException;

import junit.framework.TestCase;

public class CDFConverterTestXP extends TestCase {

	private final static String EXTENSION_POINT_ID = "net.chemclipse.msd.converter.supplier.cdf";

	// private final static String EXTENSION_POINT_EXPORT_ID =
	// "net.chemclipse.chromatogram.msd.supplier.agilent";
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
		String path = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_OP17760);
		File chromatogram = new File(path);
		// String pathExport = "E:\\tmp\\OPCDF.D\\DATA.MS";
		String pathExport = "E:\\tmp\\netCDF\\131108.CDF";
		// String pathExport = "/home/eselmeister/tmp/OPCDF.CDF";
		// String pathExport = "/home/eselmeister/tmp/OPCDF.D/DATA.MS";
		File chromatogramExport = new File(pathExport);
		start = new Date();
		IChromatogramMSDImportConverterProcessingInfo processingInfo = ChromatogramConverterMSD.convert(chromatogram, EXTENSION_POINT_ID, new NullProgressMonitor());
		IChromatogramMSD chrom;
		try {
			chrom = processingInfo.getChromatogram();
			stop = new Date();
			System.out.println("Milliseconds Lesen: " + (stop.getTime() - start.getTime()));
			assertEquals("Scans", 5726, chrom.getNumberOfScans());
			assertEquals("TS", 55822.0f, chrom.getScan(3).getTotalSignal());
			// chrom.removeScans(3398, 3585);
			start = new Date();
			IChromatogramExportConverterProcessingInfo processingInfoExport = ChromatogramConverterMSD.convert(chromatogramExport, chrom, EXTENSION_POINT_ID, new NullProgressMonitor());
			File test = processingInfoExport.getFile();
			stop = new Date();
			System.out.println("Milliseconds Schreiben: " + (stop.getTime() - start.getTime()));
			assertEquals("File path", pathExport, test.getAbsolutePath());
		} catch(TypeCastException e) {
			e.printStackTrace();
		}
	}

	public void testConvert_2() {

		Date start;
		Date stop;
		String path = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_OP17760);
		File chromatogram = new File(path);
		start = new Date();
		IChromatogramOverviewImportConverterProcessingInfo processingInfo = ChromatogramConverterMSD.convertOverview(chromatogram, EXTENSION_POINT_ID, new NullProgressMonitor());
		IChromatogramOverview chrom;
		try {
			chrom = processingInfo.getChromatogramOverview();
			stop = new Date();
			System.out.println("Milliseconds Lesen Overview: " + (stop.getTime() - start.getTime()));
			assertEquals("Scans", 5726, chrom.getNumberOfScans());
			// List<ITotalIonSignal> signals = chrom.getTotalIonSignals();
			/*
			 * for(ITotalIonSignal signal : signals) {
			 * System.out.println(signal.getRetentionTime() + " " +
			 * signal.getTotalSignal()); }
			 */
		} catch(TypeCastException e) {
			e.printStackTrace();
		}
	}
}

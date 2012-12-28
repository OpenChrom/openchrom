/*******************************************************************************
 * Copyright (c) 2008, 2012 Philip (eselmeister) Wenig.
 * 
 * This library is free
 * software; you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston MA 02111-1307, USA
 * 
 * 
 * Contributors: Philip (eselmeister) Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.cdf;

import java.io.File;
import java.util.Date;

import org.eclipse.core.runtime.NullProgressMonitor;

import net.openchrom.chromatogram.converter.processing.chromatogram.IChromatogramExportConverterProcessingInfo;
import net.openchrom.chromatogram.msd.converter.chromatogram.ChromatogramConverterMSD;
import net.openchrom.chromatogram.msd.converter.processing.chromatogram.IChromatogramMSDImportConverterProcessingInfo;
import net.openchrom.chromatogram.msd.model.core.IChromatogramMSD;
import net.openchrom.processing.core.exceptions.TypeCastException;

import junit.framework.TestCase;

public class CDFConverterTestX extends TestCase {

	private final static String EXTENSION_POINT_CDF_ID = "net.openchrom.chromatogram.msd.converter.supplier.cdf";
	private final static String EXTENSION_POINT_AGILENT_ID = "net.openchrom.chromatogram.msd.converter.supplier.agilent";

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

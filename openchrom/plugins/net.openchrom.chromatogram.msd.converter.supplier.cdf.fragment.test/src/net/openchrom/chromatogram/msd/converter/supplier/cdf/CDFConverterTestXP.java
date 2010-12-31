/*******************************************************************************
 * Copyright (c) 2008, 2011 Philip (eselmeister) Wenig.
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import org.eclipse.core.runtime.NullProgressMonitor;

import net.openchrom.chromatogram.msd.converter.chromatogram.ChromatogramConverter;
import net.openchrom.chromatogram.msd.converter.exceptions.FileIsEmptyException;
import net.openchrom.chromatogram.msd.converter.exceptions.FileIsNotReadableException;
import net.openchrom.chromatogram.msd.converter.exceptions.FileIsNotWriteableException;
import net.openchrom.chromatogram.msd.converter.exceptions.NoChromatogramConverterAvailableException;
import net.openchrom.chromatogram.msd.model.core.IChromatogram;
import net.openchrom.chromatogram.msd.model.core.IChromatogramOverview;

import junit.framework.TestCase;

public class CDFConverterTestXP extends TestCase {

	private final static String EXTENSION_POINT_ID = "net.openchrom.chromatogram.msd.converter.supplier.cdf";

	// private final static String EXTENSION_POINT_EXPORT_ID =
	// "net.openchrom.chromatogram.msd.supplier.agilent";
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
		try {
			start = new Date();
			IChromatogram chrom = ChromatogramConverter.convert(chromatogram, EXTENSION_POINT_ID, new NullProgressMonitor());
			stop = new Date();
			System.out.println("Milliseconds Lesen: " + (stop.getTime() - start.getTime()));
			assertEquals("Scans", 5726, chrom.getNumberOfScans());
			assertEquals("TS", 55822.0f, chrom.getScan(3).getTotalSignal());
			// chrom.removeScans(3398, 3585);
			start = new Date();
			File test = ChromatogramConverter.convert(chromatogramExport, chrom, EXTENSION_POINT_ID, new NullProgressMonitor());
			stop = new Date();
			System.out.println("Milliseconds Schreiben: " + (stop.getTime() - start.getTime()));
			assertEquals("File path", pathExport, test.getAbsolutePath());
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(FileIsNotReadableException e) {
			e.printStackTrace();
		} catch(FileIsEmptyException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} catch(FileIsNotWriteableException e) {
			e.printStackTrace();
		} catch(NoChromatogramConverterAvailableException e) {
			e.printStackTrace();
		}
	}

	public void testConvert_2() {

		Date start;
		Date stop;
		String path = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_OP17760);
		File chromatogram = new File(path);
		try {
			start = new Date();
			IChromatogramOverview chrom = ChromatogramConverter.convertOverview(chromatogram, EXTENSION_POINT_ID, new NullProgressMonitor());
			stop = new Date();
			System.out.println("Milliseconds Lesen Overview: " + (stop.getTime() - start.getTime()));
			assertEquals("Scans", 5726, chrom.getNumberOfScans());
			// List<ITotalIonSignal> signals = chrom.getTotalIonSignals();
			/*
			 * for(ITotalIonSignal signal : signals) {
			 * System.out.println(signal.getRetentionTime() + " " +
			 * signal.getTotalSignal()); }
			 */
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(FileIsNotReadableException e) {
			e.printStackTrace();
		} catch(FileIsEmptyException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} catch(NoChromatogramConverterAvailableException e) {
			e.printStackTrace();
		}
	}
}

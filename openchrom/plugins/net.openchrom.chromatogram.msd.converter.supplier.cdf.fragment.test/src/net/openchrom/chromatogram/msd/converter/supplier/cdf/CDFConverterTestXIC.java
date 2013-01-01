/*******************************************************************************
 * Copyright (c) 2008, 2013 Philip (eselmeister) Wenig.
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

import net.openchrom.chromatogram.model.exceptions.ChromatogramIsNullException;
import net.openchrom.chromatogram.msd.converter.chromatogram.ChromatogramConverterMSD;
import net.openchrom.chromatogram.msd.converter.processing.chromatogram.IChromatogramMSDImportConverterProcessingInfo;
import net.openchrom.chromatogram.msd.model.core.IChromatogramMSD;
import net.openchrom.chromatogram.msd.model.xic.ExtractedIonSignalExtractor;
import net.openchrom.chromatogram.msd.model.xic.IExtractedIonSignalExtractor;
import net.openchrom.chromatogram.msd.model.xic.IExtractedIonSignals;
import net.openchrom.processing.core.exceptions.TypeCastException;

import junit.framework.TestCase;

public class CDFConverterTestXIC extends TestCase {

	private final static String EXTENSION_POINT_ID = "net.openchrom.chromatogram.msd.converter.supplier.cdf";
	private IExtractedIonSignalExtractor extractedIonSignalExtractor;

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
		} catch(TypeCastException | ChromatogramIsNullException e) {
			e.printStackTrace();
		}
	}
}

/*******************************************************************************
 * Copyright (c) 2008, 2010 Philip (eselmeister) Wenig.
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
package net.openchrom.chromatogram.msd.converter.supplier.cdf.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.core.runtime.NullProgressMonitor;

import junit.framework.TestCase;
import net.openchrom.chromatogram.msd.converter.chromatogram.ChromatogramConverter;
import net.openchrom.chromatogram.msd.converter.exceptions.FileIsEmptyException;
import net.openchrom.chromatogram.msd.converter.exceptions.FileIsNotReadableException;
import net.openchrom.chromatogram.msd.converter.exceptions.NoChromatogramConverterAvailableException;
import net.openchrom.chromatogram.msd.converter.supplier.cdf.TestPathHelper;
import net.openchrom.chromatogram.msd.model.core.IChromatogram;

/**
 * Tests if the right exception will be thrown if the file is not readable.
 * 
 * @author eselmeister
 */
public class CDFChromatogramReader_NOT_READABLE_1_Test extends TestCase {

	private IChromatogram chromatogram;
	private String pathImport;
	private File fileImport;
	private final static String EXTENSION_POINT_ID = "net.openchrom.chromatogram.msd.converter.supplier.cdf";

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		this.pathImport = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_NOT_READABLE);
		fileImport = new File(this.pathImport);
		fileImport.setReadable(false);
	}

	@Override
	protected void tearDown() throws Exception {

		fileImport.setReadable(true);
		fileImport = null;
		pathImport = null;
		super.tearDown();
	}

	public void testNotReadable_1() {

		try {
			chromatogram = ChromatogramConverter.convert(fileImport, EXTENSION_POINT_ID, new NullProgressMonitor());
		} catch(FileNotFoundException e) {
			assertTrue("FileNotFoundException", false);
		} catch(FileIsNotReadableException e) {
			assertTrue("FileIsNotReadableException", true);
		} catch(FileIsEmptyException e) {
			assertTrue("FileIsEmptyException", false);
		} catch(IOException e) {
			assertTrue("IOException", false);
		} catch(NoChromatogramConverterAvailableException e) {
			assertTrue("NoChromatogramConverterAvailableException should not be thrown.", false);
		} finally {
			if(chromatogram != null) {
				chromatogram = null;
			}
		}
	}

	public void testNotReadable_2() {

		try {
			chromatogram = ChromatogramConverter.convert(fileImport, new NullProgressMonitor());
		} catch(FileNotFoundException e) {
			assertTrue("FileNotFoundException", false);
		} catch(FileIsNotReadableException e) {
			assertTrue("FileIsNotReadableException", true);
		} catch(FileIsEmptyException e) {
			assertTrue("FileIsEmptyException", false);
		} catch(NoChromatogramConverterAvailableException e) {
			assertTrue("NoChromatogramConverterAvailableException should not be thrown.", false);
		}
	}
}

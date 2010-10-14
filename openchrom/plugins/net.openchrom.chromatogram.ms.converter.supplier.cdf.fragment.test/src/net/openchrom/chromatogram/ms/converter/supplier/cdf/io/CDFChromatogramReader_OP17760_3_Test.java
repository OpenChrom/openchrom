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
package net.openchrom.chromatogram.ms.converter.supplier.cdf.io;

import java.io.File;

import org.eclipse.core.runtime.NullProgressMonitor;

import junit.framework.TestCase;

import net.openchrom.chromatogram.ms.converter.chromatogram.ChromatogramConverter;
import net.openchrom.chromatogram.ms.converter.supplier.cdf.TestPathHelper;
import net.openchrom.chromatogram.ms.model.core.IChromatogram;

/**
 * This class tries to read the chromatogram OP17760 without specifying the
 * chromatogram reader.<br/>
 * 
 * @author eselmeister
 */
public class CDFChromatogramReader_OP17760_3_Test extends TestCase {

	protected IChromatogram chromatogram;
	protected String pathImport;
	protected File fileImport;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		this.pathImport = TestPathHelper.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_OP17760);
		fileImport = new File(this.pathImport);
		chromatogram = ChromatogramConverter.convert(fileImport, new NullProgressMonitor());
	}

	@Override
	protected void tearDown() throws Exception {

		pathImport = null;
		fileImport = null;
		chromatogram = null;
		super.tearDown();
	}

	public void testCDFChromatogramReader_1() {

		assertEquals("startRetentionTime", 5189, chromatogram.getStartRetentionTime());
		assertEquals("stopRetentionTime", 4439858, chromatogram.getStopRetentionTime());
		assertEquals("minSignal", 17475.0f, chromatogram.getMinSignal());
		assertEquals("maxSignal", 9571087.0f, chromatogram.getMaxSignal());
		assertEquals("noiseFactor", 1.9135344f, chromatogram.getNoiseFactor());
		/*
		 * Test it twice to ensure that the buffer mechanism works.
		 */
		assertEquals("noiseFactor", 1.9135344f, chromatogram.getNoiseFactor());
	}
}

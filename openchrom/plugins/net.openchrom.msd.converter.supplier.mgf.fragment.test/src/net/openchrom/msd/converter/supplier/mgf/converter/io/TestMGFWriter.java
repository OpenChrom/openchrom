/*******************************************************************************
 * Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mgf.converter.io;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.eclipse.chemclipse.converter.exceptions.FileIsNotWriteableException;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.openchrom.msd.converter.supplier.mgf.converter.model.TransformerMGFElementIMGFVendorLibraryMassSpectrum;
import net.sf.jmgf.MGFElement;
import net.sf.jmgf.MGFFile;
import net.sf.jmgf.MGFFileReader;
import net.sf.jmgf.impl.MGFFileReaderImpl;
import net.sf.kerner.utils.io.CloserProperly;

public class TestMGFWriter {

	private final static Logger logger = Logger.getLogger(TestMGFWriter.class);
	private final static TransformerMGFElementIMGFVendorLibraryMassSpectrum TRANSFORMER_MGFELEMENT_IMGFVENDORLIBRARYMASSSPECTRUM = new TransformerMGFElementIMGFVendorLibraryMassSpectrum();

	private static MGFFile getMGFFile(File file) throws IOException {

		MGFFileReader reader = null;
		try {
			reader = new MGFFileReaderImpl(file);
			MGFFile result = reader.read();
			return result;
		} finally {
			new CloserProperly().closeProperly(reader);
		}
	}

	private static MGFFile getMGFFileSingleElement() throws IOException {

		return getMGFFile(new File("testData/files/export/singleElement.mgf"));
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	private File outSingleElement = new File("testData/files/export/outSingleElement.mgf");
	private MGFWriter writer;

	@Before
	public void setUp() throws Exception {

		writer = new MGFWriter();
	}

	@After
	public void tearDown() throws Exception {

		writer = null;
		// if(outSingleElement.delete()) {
		// } else {
		// logger.error("Failed to delete out file");
		// }
	}

	@Test
	public final void testWriteFileIMassSpectraBoolean01() {

	}

	@Test
	public final void testWriteFileIScanMSDBoolean01() throws IOException, FileIsNotWriteableException {

		MGFElement element = getMGFFileSingleElement().getElements().get(0);
		IScanMSD scan = TRANSFORMER_MGFELEMENT_IMGFVENDORLIBRARYMASSSPECTRUM.transform(element);
		writer.write(outSingleElement, scan, false, new NullProgressMonitor());
		MGFFile writtenFile = getMGFFile(outSingleElement);
		assertEquals(1, writtenFile.getElements().size());
		// elements differ, e.g.: CHARGE or PEPMASS
		assertEquals(element.getPeaks(), writtenFile.getElements().get(0).getPeaks());
	}
}

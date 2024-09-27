/*******************************************************************************
 * Copyright (c) 2022, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mzdb.fragment.test;

import java.io.File;

import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.support.history.IEditInformation;
import org.eclipse.core.runtime.NullProgressMonitor;

import net.openchrom.msd.converter.supplier.mzdb.PathResolver;
import net.openchrom.msd.converter.supplier.mzdb.converter.ChromatogramImportConverter;

import junit.framework.TestCase;

public class ChromatogramImportConverter_ITest extends TestCase {

	private IChromatogramMSD chromatogram;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		File file = new File(PathResolver.getAbsolutePath(TestPathHelper.TESTFILE));
		ChromatogramImportConverter importConverter = new ChromatogramImportConverter();
		IProcessingInfo<IChromatogramMSD> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		chromatogram = processingInfo.getProcessingResult();
	}

	@Override
	protected void tearDown() throws Exception {

		chromatogram = null;
		super.tearDown();
	}

	public void testLoading() {

		assertNotNull(chromatogram);
		assertEquals("1", chromatogram.getSampleName());
	}

	public void testHistory() {

		assertEquals(2, chromatogram.getEditHistory().size());
		IEditInformation info = chromatogram.getEditHistory().get(1);
		assertEquals("mzML to mzDB conversion", info.getDescription());
		assertEquals("Thermo2mzDB", info.getEditor());
	}

	public void testScans() {

		assertEquals(48, chromatogram.getNumberOfScans());
		assertEquals(1.6795854E7f, chromatogram.getScan(1).getTotalSignal());
		assertEquals(474, chromatogram.getScan(2).getRetentionTime());
	}

	public void testFirstSpectrum() {

		IScanMSD scanMSD = (IScanMSD)chromatogram.getScan(1);
		assertEquals(1750, scanMSD.getNumberOfIons());
	}
}

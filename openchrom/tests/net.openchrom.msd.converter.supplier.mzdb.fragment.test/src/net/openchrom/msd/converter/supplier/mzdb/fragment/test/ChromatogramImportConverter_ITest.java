/*******************************************************************************
 * Copyright (c) 2022, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mzdb.fragment.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;

import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.support.history.IEditInformation;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import net.openchrom.msd.converter.supplier.mzdb.converter.ChromatogramImportConverter;

@TestInstance(Lifecycle.PER_CLASS)
public class ChromatogramImportConverter_ITest {

	private IChromatogramMSD chromatogram;

	@BeforeAll
	public void setUp() {

		File file = new File(TestPathHelper.TESTFILE);
		ChromatogramImportConverter importConverter = new ChromatogramImportConverter();
		IProcessingInfo<IChromatogramMSD> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		chromatogram = processingInfo.getProcessingResult();
	}

	@Test
	public void testLoading() {

		assertNotNull(chromatogram);
		assertEquals("1", chromatogram.getSampleName());
	}

	@Test
	public void testHistory() {

		assertEquals(2, chromatogram.getEditHistory().size());
		IEditInformation info = chromatogram.getEditHistory().get(1);
		assertEquals("mzML to mzDB conversion", info.getDescription());
		assertEquals("Thermo2mzDB", info.getEditor());
	}

	@Test
	public void testScans() {

		assertEquals(48, chromatogram.getNumberOfScans());
		assertEquals(1.6795854E7f, chromatogram.getScan(1).getTotalSignal(), 0);
		assertEquals(474, chromatogram.getScan(2).getRetentionTime());
	}

	@Test
	public void testFirstSpectrum() {

		IScanMSD scanMSD = chromatogram.getScan(1);
		assertEquals(1750, scanMSD.getNumberOfIons());
	}
}

/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package net.openchrom.wsd.converter.supplier.axr.fragment.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import net.openchrom.wsd.converter.supplier.axr.converter.ChromatogramImportConverter;

@TestInstance(Lifecycle.PER_CLASS)
public class AXRRealFileSmoke_ITest {

	private IChromatogramWSD chromatogram;

	@BeforeAll
	public void setUp() {

		File file = new File("testdata/files/import/Moses_Lake_Low_Focus9_006.axr");
		assertTrue(file.isFile(), "Real AXR smoke file not found: " + file.getAbsolutePath());
		ChromatogramImportConverter importConverter = new ChromatogramImportConverter();
		IProcessingInfo<IChromatogramWSD> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		chromatogram = processingInfo.getProcessingResult();
	}

	@Test
	public void testLoadRealChromatogram() {

		assertNotNull(chromatogram);
		assertEquals(30150, chromatogram.getNumberOfScans());
	}

	@Test
	public void testRealChromatogramBoundaryValues() {

		assertEquals(14123406f, chromatogram.getScan(1).getTotalSignal(), 0);
		assertEquals(1, chromatogram.getScan(1).getRetentionTime());
		assertEquals(14115660f, chromatogram.getScan(30150).getTotalSignal(), 0);
		assertEquals(602945, chromatogram.getScan(30150).getRetentionTime());
	}
}

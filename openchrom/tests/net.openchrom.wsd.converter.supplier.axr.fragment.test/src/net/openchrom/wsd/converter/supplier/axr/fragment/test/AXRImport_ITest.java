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
public class AXRImport_ITest {

	private IChromatogramWSD chromatogram;

	@BeforeAll
	public void setUp() {

		File file = new File("testdata/files/import/valid.axr");
		ChromatogramImportConverter importConverter = new ChromatogramImportConverter();
		IProcessingInfo<IChromatogramWSD> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		chromatogram = processingInfo.getProcessingResult();
	}

	@Test
	public void testLoadChromatogram() {

		assertNotNull(chromatogram);
		assertEquals(5, chromatogram.getNumberOfScans());
	}

	@Test
	public void testSignalAndRetentionTime() {

		assertEquals(14123406f, chromatogram.getScan(1).getTotalSignal(), 0);
		assertEquals(33, chromatogram.getScan(2).getRetentionTime());
		assertEquals(14123142f, chromatogram.getScan(5).getTotalSignal(), 0);
	}
}

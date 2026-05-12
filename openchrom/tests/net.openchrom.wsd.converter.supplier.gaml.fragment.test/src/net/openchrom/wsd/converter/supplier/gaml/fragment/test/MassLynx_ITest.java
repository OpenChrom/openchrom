/*******************************************************************************
 * Copyright (c) 2023, 2026 Lablicate GmbH.
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
package net.openchrom.wsd.converter.supplier.gaml.fragment.test;

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

import net.openchrom.wsd.converter.supplier.gaml.converter.ChromatogramImportConverter;

@TestInstance(Lifecycle.PER_CLASS)
public class MassLynx_ITest {

	private IChromatogramWSD chromatogram;

	@BeforeAll
	public void setUp() {

		File file = new File("testData/files/import/Micromass_Masslynx.gaml");
		ChromatogramImportConverter importConverter = new ChromatogramImportConverter();
		IProcessingInfo<IChromatogramWSD> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		chromatogram = processingInfo.getProcessingResult();
	}

	@Test
	public void testLoading() {

		assertNotNull(chromatogram);
		assertEquals(562, chromatogram.getScans().size());
	}

	@Test
	public void testDAD() {

		assertEquals(1, chromatogram.getReferencedChromatograms().size());
		IChromatogramWSD referencedChromatogram = (IChromatogramWSD)chromatogram.getReferencedChromatograms().getFirst();
		assertEquals(562, referencedChromatogram.getNumberOfScans());
		assertEquals(205, referencedChromatogram.getWavelengths().size());
	}
}

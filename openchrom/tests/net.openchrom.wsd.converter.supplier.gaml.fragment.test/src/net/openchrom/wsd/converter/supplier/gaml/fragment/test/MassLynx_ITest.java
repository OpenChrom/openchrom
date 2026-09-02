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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

import net.openchrom.wsd.converter.supplier.gaml.converter.ChromatogramImportConverter;
import net.openchrom.wsd.converter.supplier.gaml.converter.FileContentMatcherChromatogram;
import net.openchrom.xxd.converter.supplier.gaml.converter.MagicNumberMatcher;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MassLynx_ITest {

	private IChromatogramWSD chromatogram;
	private File file;

	@Test
	@Order(1)
	public void testImport() {

		file = new File("testData/files/import/Micromass_Masslynx.gaml");
		ChromatogramImportConverter importConverter = new ChromatogramImportConverter();
		IProcessingInfo<IChromatogramWSD> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		chromatogram = processingInfo.getProcessingResult();
		assertNotNull(chromatogram);
	}

	@Test
	public void testMatch() {

		MagicNumberMatcher magicNumberMatcher = new MagicNumberMatcher();
		assertTrue(magicNumberMatcher.checkFileFormat(file));

		FileContentMatcherChromatogram fileContentMatcher = new FileContentMatcherChromatogram();
		assertTrue(fileContentMatcher.checkFileFormat(file));
	}

	@Test
	public void testLoading() {

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

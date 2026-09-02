/*******************************************************************************
 * Copyright (c) 2024, 2026 Lablicate GmbH.
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
package net.openchrom.csd.converter.supplier.cdf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.eclipse.chemclipse.csd.model.core.IChromatogramCSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

import net.openchrom.csd.converter.supplier.cdf.converter.ChromatogramImportConverterCSD;
import net.openchrom.csd.converter.supplier.cdf.converter.FileContentMatcher;
import net.openchrom.csd.converter.supplier.cdf.converter.MagicNumberMatcher;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WATERS4_ITest {

	private IChromatogramCSD chromatogram;
	private File file;

	@Test
	@Order(1)
	public void testImport() {

		file = new File("testData/Waters/WATERS4.CDF");
		ChromatogramImportConverterCSD importConverter = new ChromatogramImportConverterCSD();
		IProcessingInfo<IChromatogramCSD> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		chromatogram = processingInfo.getProcessingResult();
		assertNotNull(chromatogram);
	}

	@Test
	public void testMatch() {

		MagicNumberMatcher magicNumberMatcher = new MagicNumberMatcher();
		assertTrue(magicNumberMatcher.checkFileFormat(file));

		FileContentMatcher fileContentMatcher = new FileContentMatcher();
		assertTrue(fileContentMatcher.checkFileFormat(file));
	}

	@Test
	public void testScans() {

		assertEquals(1200, chromatogram.getNumberOfScans());
		assertEquals(0.03205f, chromatogram.getScan(600).getTotalSignal(), 0);
		assertEquals(599500, chromatogram.getScan(1200).getRetentionTime());
	}
}

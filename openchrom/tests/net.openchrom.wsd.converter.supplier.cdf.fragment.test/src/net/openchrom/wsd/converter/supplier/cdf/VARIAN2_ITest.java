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
package net.openchrom.wsd.converter.supplier.cdf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.time.Instant;

import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

import net.openchrom.wsd.converter.supplier.cdf.converter.ChromatogramImportConverterWSD;
import net.openchrom.wsd.converter.supplier.cdf.converter.FileContentMatcher;
import net.openchrom.wsd.converter.supplier.cdf.converter.MagicNumberMatcher;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class VARIAN2_ITest {

	private IChromatogramWSD chromatogram;
	private File file;

	@Test
	@Order(1)
	public void testImport() {

		file = new File("testData/Varian/VARIAN2.CDF");
		ChromatogramImportConverterWSD importConverter = new ChromatogramImportConverterWSD();
		IProcessingInfo<IChromatogramWSD> processingInfo = importConverter.convert(file, new NullProgressMonitor());
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
	public void testDate() {

		assertEquals(Instant.parse("1988-08-26T01:41:18Z"), chromatogram.getDate().toInstant());
	}

	@Test
	public void testOperator() {

		assertEquals("MEA", chromatogram.getOperator());
	}

	@Test
	public void testScans() {

		assertEquals(1794, chromatogram.getNumberOfScans());
		assertEquals(4.348755E-4f, chromatogram.getScan(900).getTotalSignal(), 0);
		assertEquals(358600, chromatogram.getScan(1794).getRetentionTime());
	}
}

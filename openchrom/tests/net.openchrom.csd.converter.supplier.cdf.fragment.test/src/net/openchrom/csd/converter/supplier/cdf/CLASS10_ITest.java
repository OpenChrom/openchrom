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
import java.time.Instant;

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
public class CLASS10_ITest {

	private IChromatogramCSD chromatogram;
	private File file;

	@Test
	@Order(1)
	public void testImport() {

		file = new File("testData/Shimadzu/CLASS10.CDF");// actually LC
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
	public void testDate() {

		assertEquals(Instant.parse("1994-06-07T00:35:56Z"), chromatogram.getDate().toInstant());
	}

	@Test
	public void testOperator() {

		assertEquals("Shimadzu", chromatogram.getOperator());
	}

	@Test
	public void testScans() {

		assertEquals(1997, chromatogram.getNumberOfScans());
		assertEquals(-0.002914f, chromatogram.getScan(1000).getTotalSignal(), 0);
		assertEquals(1277440, chromatogram.getScan(1997).getRetentionTime());
	}
}

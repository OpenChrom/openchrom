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
package net.openchrom.msd.converter.supplier.cdf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.time.Instant;

import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

import net.openchrom.msd.converter.supplier.cdf.converter.ChromatogramImportConverter;
import net.openchrom.msd.converter.supplier.cdf.converter.FileContentMatcher;
import net.openchrom.msd.converter.supplier.cdf.converter.MagicNumberMatcher;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TEKNIVNT_CONTINUM_ITest {

	private IChromatogramMSD chromatogram;
	private File file;

	@Test
	@Order(1)
	public void testImport() {

		file = new File("testData/TEKNIVNT/CONTINUM.CDF");
		ChromatogramImportConverter importConverter = new ChromatogramImportConverter();
		IProcessingInfo<IChromatogramMSD> processingInfo = importConverter.convert(file, new NullProgressMonitor());
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

		assertEquals(Instant.parse("1993-09-14T19:08:36Z"), chromatogram.getDate().toInstant());
	}

	@Test
	public void testScans() {

		assertEquals(5, chromatogram.getNumberOfScans());
		assertEquals(2376, chromatogram.getNumberOfScanIons());
		assertEquals(3, chromatogram.getScan(1).getRetentionTime());
		assertEquals(4000, chromatogram.getScan(5).getRetentionTime());
	}

	@Test
	public void testIons() {

		assertEquals(247, chromatogram.getScan(1).getNumberOfIons());
		assertEquals(10.505d, chromatogram.getScan(1).getLowestIon().getIon(), 0.0001d);
		assertEquals(362.614d, chromatogram.getScan(1).getHighestIon().getIon(), 0.0001d);
	}
}

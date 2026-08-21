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

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TGNTHPGC_ITest {

	private IChromatogramCSD chromatogram;

	@Test
	@Order(1)
	public void testImport() {

		File file = new File("testData/Thru-Put Systems/tgnthpgc.cdf");
		ChromatogramImportConverterCSD importConverter = new ChromatogramImportConverterCSD();
		IProcessingInfo<IChromatogramCSD> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		chromatogram = processingInfo.getProcessingResult();
	}

	@Test
	public void testLoading() {

		assertNotNull(chromatogram);
	}

	@Test
	public void testDate() {

		assertEquals(Instant.parse("1997-01-28T19:56:42Z"), chromatogram.getDate().toInstant());
	}

	@Test
	public void testOperator() {

		assertEquals("bph", chromatogram.getOperator());
	}

	@Test
	public void testScans() {

		assertEquals(93003, chromatogram.getNumberOfScans());
		assertEquals(51102.0f, chromatogram.getScan(45000).getTotalSignal(), 0);
		assertEquals(1860027, chromatogram.getScan(93003).getRetentionTime());
	}
}

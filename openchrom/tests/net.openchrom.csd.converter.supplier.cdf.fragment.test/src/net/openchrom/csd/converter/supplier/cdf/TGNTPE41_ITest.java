/*******************************************************************************
 * Copyright (c) 2024, 2025 Lablicate GmbH.
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

import org.eclipse.chemclipse.csd.model.core.IChromatogramCSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import net.openchrom.csd.converter.supplier.cdf.converter.ChromatogramImportConverterCSD;

@TestInstance(Lifecycle.PER_CLASS)
public class TGNTPE41_ITest {

	private IChromatogramCSD chromatogram;

	@BeforeAll
	public void setUp() {

		File file = new File(PathResolver.getAbsolutePath(TestPathHelper.TGNTPE41));
		ChromatogramImportConverterCSD importConverter = new ChromatogramImportConverterCSD();
		IProcessingInfo<IChromatogramCSD> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		chromatogram = processingInfo.getProcessingResult();
	}

	@Test
	public void testLoading() {

		assertNotNull(chromatogram);
	}

	@Test
	public void testScans() {

		assertEquals(2912, chromatogram.getNumberOfScans());
		assertEquals(10632.0f, chromatogram.getScan(1500).getTotalSignal(), 0);
		assertEquals(7275000, chromatogram.getScan(2912).getRetentionTime());
	}
}

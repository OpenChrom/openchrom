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

import java.io.File;

import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import net.openchrom.wsd.converter.supplier.cdf.converter.ChromatogramImportConverterWSD;

@TestInstance(Lifecycle.PER_CLASS)
public class VARIAN4_ITest {

	private IChromatogramWSD chromatogram;

	@BeforeAll
	public void setUp() {

		File file = new File("testData/Varian/VARIAN4.CDF");
		ChromatogramImportConverterWSD importConverter = new ChromatogramImportConverterWSD();
		IProcessingInfo<IChromatogramWSD> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		chromatogram = processingInfo.getProcessingResult();
	}

	@Test
	public void testLoading() {

		assertNotNull(chromatogram);
	}

	@Test
	public void testScans() {

		assertEquals(1794, chromatogram.getNumberOfScans());
		assertEquals(4.348755E-4f, chromatogram.getScan(900).getTotalSignal(), 0);
		assertEquals(358600, chromatogram.getScan(1794).getRetentionTime());
	}
}

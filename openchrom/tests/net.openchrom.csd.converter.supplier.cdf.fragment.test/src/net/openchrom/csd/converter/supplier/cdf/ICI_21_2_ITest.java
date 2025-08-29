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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.openchrom.csd.converter.supplier.cdf.converter.ChromatogramImportConverterCSD;

public class ICI_21_2_ITest {

	private IChromatogramCSD chromatogram;

	@BeforeEach
	public void setUp() throws Exception {

		File file = new File(PathResolver.getAbsolutePath(TestPathHelper.ICI_21_2));
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

		assertEquals(152, chromatogram.getNumberOfScans());
		assertEquals(46848.0f, chromatogram.getScan(76).getTotalSignal(), 0);
		assertEquals(302, chromatogram.getScan(152).getRetentionTime());
	}
}

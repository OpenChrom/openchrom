/*******************************************************************************
 * Copyright (c) 2023, 2025 Lablicate GmbH.
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
package net.openchrom.csd.converter.supplier.gaml.fragment.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.eclipse.chemclipse.csd.model.core.IChromatogramCSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Before;
import org.junit.Test;

import net.openchrom.csd.converter.supplier.gaml.PathResolver;
import net.openchrom.csd.converter.supplier.gaml.converter.ChromatogramImportConverter;

public class Multichrom_ITest {

	private IChromatogramCSD chromatogram;

	@Before
	public void setUp() throws Exception {

		File file = new File(PathResolver.getAbsolutePath(TestPathHelper.MULTICHROM));
		ChromatogramImportConverter importConverter = new ChromatogramImportConverter();
		IProcessingInfo<IChromatogramCSD> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		chromatogram = processingInfo.getProcessingResult();
	}

	@Test
	public void testLoading() {

		assertNotNull(chromatogram);
	}

	@Test
	public void testScans() {

		assertEquals(3800, chromatogram.getNumberOfScans());
		assertEquals(232.95596f, chromatogram.getScan(207).getTotalSignal(), 0);
		assertEquals(255840, chromatogram.getScan(1600).getRetentionTime());
	}

	@Test
	public void testPeaks() {

		assertEquals(16, chromatogram.getPeaks().size());
		assertEquals("Propanoic acid", chromatogram.getPeaks().get(3).getTargets().iterator().next().getLibraryInformation().getName());
	}
}

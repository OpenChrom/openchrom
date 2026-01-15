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
package net.openchrom.csd.converter.supplier.gaml.fragment.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.eclipse.chemclipse.converter.PathResolver;
import org.eclipse.chemclipse.csd.model.core.IChromatogramCSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.osgi.framework.FrameworkUtil;

import net.openchrom.csd.converter.supplier.gaml.converter.ChromatogramImportConverter;

@TestInstance(Lifecycle.PER_CLASS)
public class Multichrom_ITest {

	private IChromatogramCSD chromatogram;

	@BeforeAll
	public void setUp() throws IOException {

		File file = PathResolver.getFile(FrameworkUtil.getBundle(getClass()), TestPathHelper.MULTICHROM);
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

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

import java.io.File;

import org.eclipse.chemclipse.csd.model.core.IChromatogramCSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;

import net.openchrom.csd.converter.supplier.gaml.PathResolver;
import net.openchrom.csd.converter.supplier.gaml.converter.ChromatogramImportConverter;

import junit.framework.TestCase;

public class TurbochromGC_ITest extends TestCase {

	private IChromatogramCSD chromatogram;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		File file = new File(PathResolver.getAbsolutePath(TestPathHelper.TURBOCHROM_GC));
		ChromatogramImportConverter importConverter = new ChromatogramImportConverter();
		IProcessingInfo<IChromatogramCSD> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		chromatogram = processingInfo.getProcessingResult();
	}

	@Override
	protected void tearDown() throws Exception {

		chromatogram = null;
		super.tearDown();
	}

	public void testLoading() {

		assertNotNull(chromatogram);
	}

	public void testScans() {

		assertEquals(2912, chromatogram.getNumberOfScans());
		assertEquals(60941f, chromatogram.getScan(986).getTotalSignal());
		assertEquals(7050938, chromatogram.getScan(2805).getRetentionTime());
	}

	public void testPeaks() {

		assertEquals(82, chromatogram.getPeaks().size());
		assertEquals("METHANE", chromatogram.getPeaks().get(5).getTargets().iterator().next().getLibraryInformation().getName());
	}
}

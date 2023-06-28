/*******************************************************************************
 * Copyright (c) 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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

public class Multichrom_ITest extends TestCase {

	private IChromatogramCSD chromatogram;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		File file = new File(PathResolver.getAbsolutePath(TestPathHelper.MULTICHROM));
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

		assertEquals(3800, chromatogram.getNumberOfScans());
		assertEquals(232.95596f, chromatogram.getScan(207).getTotalSignal());
		assertEquals(255840, chromatogram.getScan(1600).getRetentionTime());
	}

	public void testPeaks() {

		assertEquals(16, chromatogram.getNumberOfPeaks());
		assertEquals("Propanoic acid", chromatogram.getPeaks().get(3).getTargets().iterator().next().getLibraryInformation().getName());
	}
}

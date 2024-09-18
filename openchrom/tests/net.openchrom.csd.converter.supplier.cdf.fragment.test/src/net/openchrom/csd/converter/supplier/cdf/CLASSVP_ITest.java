/*******************************************************************************
 * Copyright (c) 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.csd.converter.supplier.cdf;

import java.io.File;

import org.eclipse.chemclipse.csd.model.core.IChromatogramCSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;

import net.openchrom.csd.converter.supplier.cdf.converter.ChromatogramImportConverterCSD;

import junit.framework.TestCase;

public class CLASSVP_ITest extends TestCase {

	private IChromatogramCSD chromatogram;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		File file = new File(PathResolver.getAbsolutePath(TestPathHelper.CLASSVP));
		ChromatogramImportConverterCSD importConverter = new ChromatogramImportConverterCSD();
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

		assertEquals(1440, chromatogram.getNumberOfScans());
		assertEquals(0.005792f, chromatogram.getScan(720).getTotalSignal());
		assertEquals(359750, chromatogram.getScan(1440).getRetentionTime());
	}

	public void testPeaks() {

		assertEquals("Peak 1", chromatogram.getScan(402).getTargets().iterator().next().getLibraryInformation().getName());
		assertEquals("Peak 2", chromatogram.getScan(785).getTargets().iterator().next().getLibraryInformation().getName());
		assertEquals("Peak 3", chromatogram.getScan(909).getTargets().iterator().next().getLibraryInformation().getName());
		assertEquals("Peak 4", chromatogram.getScan(986).getTargets().iterator().next().getLibraryInformation().getName());
		assertEquals("Peak 5", chromatogram.getScan(1253).getTargets().iterator().next().getLibraryInformation().getName());
	}
}

/*******************************************************************************
 * Copyright (c) 2022, 2025 Lablicate GmbH.
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
package net.openchrom.msd.converter.supplier.mzmlb.fragment.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Before;
import org.junit.Test;

import net.openchrom.msd.converter.supplier.mzmlb.PathResolver;
import net.openchrom.msd.converter.supplier.mzmlb.converter.ChromatogramImportConverter;

public class ChromatogramImportConverter_ITest {

	private IChromatogramMSD chromatogram;

	@Before
	public void setUp() throws Exception {

		File file = new File(PathResolver.getAbsolutePath(TestPathHelper.TESTFILE));
		ChromatogramImportConverter importConverter = new ChromatogramImportConverter();
		IProcessingInfo<IChromatogramMSD> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		chromatogram = processingInfo.getProcessingResult();
	}

	@Test
	public void testLoading() {

		assertNotNull(chromatogram);
	}

	@Test
	public void testScans() {

		assertEquals(chromatogram.getNumberOfScans(), 2);
		assertEquals(chromatogram.getScan(1).getTotalSignal(), 1.5245068E7f, 0);
		assertEquals(chromatogram.getScan(2).getRetentionTime(), 356);
	}
}

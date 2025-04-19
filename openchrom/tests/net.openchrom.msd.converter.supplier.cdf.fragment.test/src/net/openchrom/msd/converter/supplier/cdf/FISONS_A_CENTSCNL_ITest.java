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
package net.openchrom.msd.converter.supplier.cdf;

import java.io.File;

import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;

import net.openchrom.msd.converter.supplier.cdf.converter.ChromatogramImportConverter;

import junit.framework.TestCase;

public class FISONS_A_CENTSCNL_ITest extends TestCase {

	private IChromatogramMSD chromatogram;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		File file = new File(PathResolver.getAbsolutePath(TestPathHelper.FISONS_A_CENTSCNL));
		ChromatogramImportConverter importConverter = new ChromatogramImportConverter();
		IProcessingInfo<IChromatogramMSD> processingInfo = importConverter.convert(file, new NullProgressMonitor());
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

		assertEquals(21, chromatogram.getNumberOfScans());
		assertEquals(538, chromatogram.getNumberOfScanIons());
		assertEquals(477000, chromatogram.getScan(21).getRetentionTime());
	}
}

/*******************************************************************************
 * Copyright (c) 2023, 2025 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.fsd.converter.supplier.gaml.fragment.test;

import java.io.File;

import org.eclipse.chemclipse.fsd.model.core.ISpectrumFSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;

import net.openchrom.fsd.converter.supplier.gaml.PathResolver;
import net.openchrom.fsd.converter.supplier.gaml.converter.ScanImportConverter;

import junit.framework.TestCase;

public class TS_AB2_FLSCAN_ITest extends TestCase {

	private ISpectrumFSD spectrumFSD;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		File file = new File(PathResolver.getAbsolutePath(TestPathHelper.TS_AB2_FLSCAN_HELIOS));
		ScanImportConverter importConverter = new ScanImportConverter();
		IProcessingInfo<ISpectrumFSD> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		spectrumFSD = processingInfo.getProcessingResult();
	}

	@Override
	protected void tearDown() throws Exception {

		spectrumFSD = null;
		super.tearDown();
	}

	public void testLoading() {

		assertNotNull(spectrumFSD);
	}

	public void testSignals() {

		assertEquals(1, spectrumFSD.getExcitation().size());
		assertEquals(301, spectrumFSD.getEmission().size());
	}
}

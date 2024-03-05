/*******************************************************************************
 * Copyright (c) 2023, 2024 Lablicate GmbH.
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

import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;

import net.openchrom.xir.converter.supplier.gaml.PathResolver;
import net.openchrom.xir.converter.supplier.gaml.converter.ScanImportConverter;
import net.openchrom.xir.converter.supplier.gaml.model.IVendorSpectrumXIR;

import junit.framework.TestCase;

public class Helios_ITest extends TestCase {

	private IVendorSpectrumXIR vendorSpectrum;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		File file = new File(PathResolver.getAbsolutePath(TestPathHelper.TS_HELIOS));
		ScanImportConverter importConverter = new ScanImportConverter();
		IProcessingInfo<IVendorSpectrumXIR> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		vendorSpectrum = processingInfo.getProcessingResult();
	}

	@Override
	protected void tearDown() throws Exception {

		vendorSpectrum = null;
		super.tearDown();
	}

	public void testLoading() {

		assertNotNull(vendorSpectrum);
	}

	public void testSignals() {

		assertEquals(201, vendorSpectrum.getScanISD().getProcessedSignals().size());
	}
}

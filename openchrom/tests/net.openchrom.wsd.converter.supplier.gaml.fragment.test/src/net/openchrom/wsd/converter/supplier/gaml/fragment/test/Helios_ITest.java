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
package net.openchrom.wsd.converter.supplier.gaml.fragment.test;

import java.io.File;

import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.wsd.model.core.ISpectrumWSD;
import org.eclipse.core.runtime.NullProgressMonitor;

import net.openchrom.wsd.converter.supplier.gaml.PathResolver;
import net.openchrom.wsd.converter.supplier.gaml.converter.ScanImportConverter;

import junit.framework.TestCase;

public class Helios_ITest extends TestCase {

	private ISpectrumWSD spectrumWSD;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		File file = new File(PathResolver.getAbsolutePath(TestPathHelper.TS_HELIOS));
		ScanImportConverter importConverter = new ScanImportConverter();
		IProcessingInfo<ISpectrumWSD> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		spectrumWSD = processingInfo.getProcessingResult();
	}

	@Override
	protected void tearDown() throws Exception {

		spectrumWSD = null;
		super.tearDown();
	}

	public void testLoading() {

		assertNotNull(spectrumWSD);
	}

	public void testSignals() {

		assertEquals(201, spectrumWSD.getSignals().size());
	}
}

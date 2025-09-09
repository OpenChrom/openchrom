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
package net.openchrom.fsd.converter.supplier.gaml.fragment.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;

import org.eclipse.chemclipse.fsd.model.core.ISpectrumFSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import net.openchrom.fsd.converter.supplier.gaml.PathResolver;
import net.openchrom.fsd.converter.supplier.gaml.converter.ScanImportConverter;

@TestInstance(Lifecycle.PER_CLASS)
public class TS_AB2_FLSCAN_ITest {

	private ISpectrumFSD spectrumFSD;

	@BeforeAll
	public void setUp() {

		File file = new File(PathResolver.getAbsolutePath(TestPathHelper.TS_AB2_FLSCAN_HELIOS));
		ScanImportConverter importConverter = new ScanImportConverter();
		IProcessingInfo<ISpectrumFSD> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		spectrumFSD = processingInfo.getProcessingResult();
	}

	@Test
	public void testLoading() {

		assertNotNull(spectrumFSD);
	}

	@Test
	public void testSignals() {

		assertEquals(1, spectrumFSD.getExcitation().size());
		assertEquals(301, spectrumFSD.getEmission().size());
	}
}

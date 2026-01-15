/*******************************************************************************
 * Copyright (c) 2025, 2026 Lablicate GmbH.
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
package net.openchrom.vsd.converter.supplier.gaml.fragment.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.eclipse.chemclipse.converter.PathResolver;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.vsd.model.core.ISpectrumVSD;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.osgi.framework.FrameworkUtil;

import net.openchrom.vsd.converter.supplier.gaml.converter.ScanImportConverter;

@TestInstance(Lifecycle.PER_CLASS)
public class Omnic_Raman_ITest {

	private ISpectrumVSD spectrumVSD;

	@BeforeAll
	public void setUp() throws IOException {

		File file = PathResolver.getFile(FrameworkUtil.getBundle(getClass()), TestPathHelper.TN_OMNIC_RAMAN);
		ScanImportConverter importConverter = new ScanImportConverter();
		IProcessingInfo<ISpectrumVSD> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		spectrumVSD = processingInfo.getProcessingResult();
	}

	@Test
	public void testLoading() {

		assertNotNull(spectrumVSD);
	}

	@Test
	public void testSignals() {

		assertEquals(3632, spectrumVSD.getScanVSD().getProcessedSignals().size());
	}
}

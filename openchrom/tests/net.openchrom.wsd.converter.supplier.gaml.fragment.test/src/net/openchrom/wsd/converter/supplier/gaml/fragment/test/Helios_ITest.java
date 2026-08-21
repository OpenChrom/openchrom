/*******************************************************************************
 * Copyright (c) 2023, 2026 Lablicate GmbH.
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
package net.openchrom.wsd.converter.supplier.gaml.fragment.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;

import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.wsd.model.core.ISpectrumWSD;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

import net.openchrom.wsd.converter.supplier.gaml.converter.ScanImportConverter;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Helios_ITest {

	private ISpectrumWSD spectrumWSD;

	@BeforeAll
	public void setUp() {

		File file = new File("testData/files/import/TS_Helios.gaml");
		ScanImportConverter importConverter = new ScanImportConverter();
		IProcessingInfo<ISpectrumWSD> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		spectrumWSD = processingInfo.getProcessingResult();
		assertNotNull(spectrumWSD);
	}

	@Test
	public void testSignals() {

		assertEquals(201, spectrumWSD.getSignals().size());
	}
}

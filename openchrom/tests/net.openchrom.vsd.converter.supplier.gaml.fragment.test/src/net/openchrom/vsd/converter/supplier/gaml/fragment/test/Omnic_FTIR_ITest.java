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
 * Philip Wenig - refactoring vibrational spectroscopy
 *******************************************************************************/
package net.openchrom.vsd.converter.supplier.gaml.fragment.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.vsd.model.core.ISpectrumVSD;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

import net.openchrom.vsd.converter.supplier.gaml.converter.FileContentMatcher;
import net.openchrom.vsd.converter.supplier.gaml.converter.ScanImportConverter;
import net.openchrom.xxd.converter.supplier.gaml.converter.MagicNumberMatcher;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Omnic_FTIR_ITest {

	private ISpectrumVSD spectrumVSD;
	private File file;

	@Test
	@Order(1)
	public void testImport() {

		file = new File("testData/files/import/TN_OMNIC_FTIR.gaml");
		ScanImportConverter importConverter = new ScanImportConverter();
		IProcessingInfo<ISpectrumVSD> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		spectrumVSD = processingInfo.getProcessingResult();
		assertNotNull(spectrumVSD);
	}

	@Test
	public void testMatch() {

		FileContentMatcher fileContentMatcher = new FileContentMatcher();
		assertTrue(fileContentMatcher.checkFileFormat(file));

		MagicNumberMatcher magicNumberMatcher = new MagicNumberMatcher();
		assertTrue(magicNumberMatcher.checkFileFormat(file));
	}

	@Test
	public void testSignals() {

		assertEquals(1868, spectrumVSD.getScanVSD().getProcessedSignals().size());
	}
}

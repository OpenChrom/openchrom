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
package net.openchrom.csd.converter.supplier.gaml.fragment.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;

import org.eclipse.chemclipse.csd.model.core.IChromatogramCSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

import net.openchrom.csd.converter.supplier.gaml.converter.ChromatogramImportConverter;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TurbochromGC_ITest {

	private IChromatogramCSD chromatogram;

	@Test
	@Order(1)
	public void testImport() {

		File file = new File("testData/files/import/PE_Turbochrom_GC.gaml");
		ChromatogramImportConverter importConverter = new ChromatogramImportConverter();
		IProcessingInfo<IChromatogramCSD> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		chromatogram = processingInfo.getProcessingResult();
		assertNotNull(chromatogram);
	}

	@Test
	public void testScans() {

		assertEquals(2912, chromatogram.getNumberOfScans());
		assertEquals(60941f, chromatogram.getScan(986).getTotalSignal(), 0);
		assertEquals(7050938, chromatogram.getScan(2805).getRetentionTime());
	}

	@Test
	public void testPeaks() {

		assertEquals(82, chromatogram.getPeaks().size());
		assertEquals("METHANE", chromatogram.getPeaks().get(5).getTargets().iterator().next().getLibraryInformation().getName());
	}
}

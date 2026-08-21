/*******************************************************************************
 * Copyright (c) 2024, 2026 Lablicate GmbH.
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
package net.openchrom.csd.converter.supplier.cdf;

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

import net.openchrom.csd.converter.supplier.cdf.converter.ChromatogramImportConverterCSD;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CLASSVP_ITest {

	private IChromatogramCSD chromatogram;

	@Test
	@Order(1)
	public void testImport() {

		File file = new File("testData/Shimadzu/CLASSVP.CDF"); // actually LC
		ChromatogramImportConverterCSD importConverter = new ChromatogramImportConverterCSD();
		IProcessingInfo<IChromatogramCSD> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		chromatogram = processingInfo.getProcessingResult();
	}

	@Test
	public void testLoading() {

		assertNotNull(chromatogram);
	}

	@Test
	public void testOperator() {

		assertEquals("DF", chromatogram.getOperator());
	}

	@Test
	public void testScans() {

		assertEquals(1440, chromatogram.getNumberOfScans());
		assertEquals(0.005792f, chromatogram.getScan(720).getTotalSignal(), 0);
		assertEquals(359750, chromatogram.getScan(1440).getRetentionTime());
	}

	@Test
	public void testPeaks() {

		assertEquals("Peak 1", chromatogram.getScan(402).getTargets().iterator().next().getLibraryInformation().getName());
		assertEquals("Peak 2", chromatogram.getScan(785).getTargets().iterator().next().getLibraryInformation().getName());
		assertEquals("Peak 3", chromatogram.getScan(909).getTargets().iterator().next().getLibraryInformation().getName());
		assertEquals("Peak 4", chromatogram.getScan(986).getTargets().iterator().next().getLibraryInformation().getName());
		assertEquals("Peak 5", chromatogram.getScan(1253).getTargets().iterator().next().getLibraryInformation().getName());
	}
}

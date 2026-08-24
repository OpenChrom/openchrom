/*******************************************************************************
 * Copyright (c) 2016, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.dsd.converter.supplier.abif.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;

import org.eclipse.chemclipse.dsd.model.core.IChromatogramDSD;
import org.eclipse.chemclipse.dsd.model.core.Nucleobase;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

import net.openchrom.wsd.converter.supplier.abif.core.ChromatogramImportConverter;
import net.openchrom.wsd.converter.supplier.abif.model.IVendorChromatogram;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AbiPrism310GeneticAnalyser_ITest {

	private IChromatogramDSD chromatogram;

	@Test
	@Order(1)
	public void testImport() {

		File fileImport = new File("testdata/files/import/310.ab1");
		ChromatogramImportConverter importConverter = new ChromatogramImportConverter();
		IProcessingInfo<IChromatogramDSD> processingInfo = importConverter.convert(fileImport, new NullProgressMonitor());
		chromatogram = processingInfo.getProcessingResult();
		assertNotNull(chromatogram);
	}

	@Test
	public void testVersion() {

		assertEquals(101, ((IVendorChromatogram)chromatogram).getVersion());
	}

	@Test
	public void testWavelengths() {

		assertEquals(4, chromatogram.getWavelengthMapping().size());
		assertEquals(540f, chromatogram.getScan(1).getScanSignal(0).getWavelength());
		assertEquals(Nucleobase.GUANINE, chromatogram.getWavelengthMapping().get(540f));

		assertEquals(568f, chromatogram.getScan(1).getScanSignal(1).getWavelength());
		assertEquals(Nucleobase.ADENINE, chromatogram.getWavelengthMapping().get(568f));

		assertEquals(595f, chromatogram.getScan(1).getScanSignal(2).getWavelength());
		assertEquals(Nucleobase.THYMINE, chromatogram.getWavelengthMapping().get(595f));

		assertEquals(615f, chromatogram.getScan(1).getScanSignal(3).getWavelength());
		assertEquals(Nucleobase.CYTOSINE, chromatogram.getWavelengthMapping().get(615f));
	}
}

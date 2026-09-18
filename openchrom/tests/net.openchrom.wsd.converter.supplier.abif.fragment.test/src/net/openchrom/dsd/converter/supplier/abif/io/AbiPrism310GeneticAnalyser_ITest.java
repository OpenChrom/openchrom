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
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.junit.jupiter.api.util.DefaultTimeZone;

import net.openchrom.wsd.converter.supplier.abif.core.ChromatogramImportConverter;
import net.openchrom.wsd.converter.supplier.abif.core.MagicNumberMatcher;
import net.openchrom.wsd.converter.supplier.abif.model.IVendorChromatogram;

@DefaultTimeZone("CET")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AbiPrism310GeneticAnalyser_ITest {

	private IVendorChromatogram chromatogram;
	private File file;

	@Test
	@Order(1)
	public void testImport() {

		file = new File("testdata/files/import/310.ab1");
		ChromatogramImportConverter importConverter = new ChromatogramImportConverter();
		IProcessingInfo<IChromatogramDSD> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		chromatogram = (IVendorChromatogram)processingInfo.getProcessingResult();
		assertNotNull(chromatogram);
	}

	@Test
	public void testMatch() {

		MagicNumberMatcher magicNumberMatcher = new MagicNumberMatcher();
		assertTrue(magicNumberMatcher.checkFileFormat(file));
	}

	@Test
	public void testInstrument() {

		assertEquals("ABI PRISM 310", chromatogram.getInstrument());
	}

	@Test
	public void testDate() {

		assertEquals("Thu Feb 19 04:04:15 CET 2009", chromatogram.getDate().toString());
	}

	@Test
	public void testWell() {

		assertEquals("C5", chromatogram.getWell());
	}

	@Test
	public void testUser() {

		assertEquals("", chromatogram.getOperator());
	}

	@Test
	public void testVersion() {

		assertEquals(101, chromatogram.getVersion());
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

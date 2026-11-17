/*******************************************************************************
 * Copyright (c) 2022, 2026 Lablicate GmbH.
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
package net.openchrom.msd.converter.supplier.mzmlb.fragment.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;

import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

import net.openchrom.msd.converter.supplier.mzmlb.converter.ChromatogramImportConverter;
import net.openchrom.msd.converter.supplier.mzmlb.converter.MagicNumberMatcher;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ChromatogramImportConverter_ITest {

	private IChromatogramMSD chromatogram;
	private File file;

	@Test
	@Order(1)
	public void testImport() {

		file = new File("testData/files/import/test.mzMLb");
		ChromatogramImportConverter importConverter = new ChromatogramImportConverter();
		IProcessingInfo<IChromatogramMSD> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		chromatogram = processingInfo.getProcessingResult();
		assertNotNull(chromatogram);
	}

	@Test
	public void testMatch() {

		MagicNumberMatcher magicNumberMatcher = new MagicNumberMatcher();
		assertTrue(magicNumberMatcher.checkFileFormat(file));
	}

	@Test
	public void testScans() {

		assertEquals(chromatogram.getNumberOfScans(), 2);
		assertEquals(chromatogram.getScan(1).getTotalSignal(), 1.5245068E7f, 0);
		assertEquals(chromatogram.getScan(2).getRetentionTime(), 356);
	}

	@Test
	public void testIons() {

		/*
		 * Both spectra share their m/z values but have their own intensities.
		 */
		for(int scan = 1; scan <= chromatogram.getNumberOfScans(); scan++) {
			List<IIon> ions = chromatogram.getScan(scan).getIons();
			assertEquals(19914, ions.size());
			assertEquals(200.00018310546875d, ions.getFirst().getIon(), 0);
			assertEquals(2000.0098876953125d, ions.getLast().getIon(), 0);
		}
	}

	@Test
	public void testCycles() {

		int cycleNumber = chromatogram.getScan(1).getCycleNumber();
		assertTrue(cycleNumber >= 1);
		assertEquals(cycleNumber + 1, chromatogram.getScan(2).getCycleNumber());
	}
}

/*******************************************************************************
 * Copyright (c) 2022, 2025 Lablicate GmbH.
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
package net.openchrom.msd.converter.supplier.mz5.fragment.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;

import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IRegularMassSpectrum;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import net.openchrom.msd.converter.supplier.mz5.PathResolver;
import net.openchrom.msd.converter.supplier.mz5.converter.ChromatogramImportConverter;

@TestInstance(Lifecycle.PER_CLASS)
public class ChromatogramImportConverter_ITest {

	private IChromatogramMSD chromatogram;

	@BeforeAll
	public void setUp() {

		File file = new File(PathResolver.getAbsolutePath(TestPathHelper.TESTFILE));
		ChromatogramImportConverter importConverter = new ChromatogramImportConverter();
		IProcessingInfo<IChromatogramMSD> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		chromatogram = processingInfo.getProcessingResult();
	}

	@Test
	public void testLoading() {

		assertNotNull(chromatogram);
	}

	@Test
	public void testScans() {

		assertEquals(26, chromatogram.getNumberOfScans());
		assertEquals(105525.87f, chromatogram.getScan(10).getTotalSignal(), 0);
		assertEquals(16639, chromatogram.getScan(26).getRetentionTime());
	}

	@Test
	public void testIonTransitions() {

		IRegularMassSpectrum massSpectrum = (IRegularMassSpectrum)chromatogram.getScan(3);
		assertEquals(367.201873779297, massSpectrum.getPrecursorIon(), 0);
		IIon ion = massSpectrum.getIons().get(0);
		assertEquals(367, ion.getIonTransition().getQ1Ion());
		assertEquals(112.1d, ion.getIonTransition().getQ3Ion(), 0);
	}
}

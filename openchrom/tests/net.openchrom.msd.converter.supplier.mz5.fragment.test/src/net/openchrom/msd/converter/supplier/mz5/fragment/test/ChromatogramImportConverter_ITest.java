/*******************************************************************************
 * Copyright (c) 2022, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mz5.fragment.test;

import java.io.File;

import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IRegularMassSpectrum;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;

import net.openchrom.msd.converter.supplier.mz5.PathResolver;
import net.openchrom.msd.converter.supplier.mz5.converter.ChromatogramImportConverter;

import junit.framework.TestCase;

public class ChromatogramImportConverter_ITest extends TestCase {

	private IChromatogramMSD chromatogram;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		File file = new File(PathResolver.getAbsolutePath(TestPathHelper.TESTFILE));
		ChromatogramImportConverter importConverter = new ChromatogramImportConverter();
		IProcessingInfo<IChromatogramMSD> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		chromatogram = processingInfo.getProcessingResult();
	}

	@Override
	protected void tearDown() throws Exception {

		chromatogram = null;
		super.tearDown();
	}

	public void testLoading() {

		assertNotNull(chromatogram);
	}

	public void testScans() {

		assertEquals(26, chromatogram.getNumberOfScans());
		assertEquals(105525.87f, chromatogram.getScan(10).getTotalSignal());
		assertEquals(16639, chromatogram.getScan(26).getRetentionTime());
	}

	public void testIonTransitions() {

		IRegularMassSpectrum massSpectrum = (IRegularMassSpectrum)chromatogram.getScan(3);
		assertEquals(367.201873779297, massSpectrum.getPrecursorIon());
		IIon ion = massSpectrum.getIons().get(0);
		assertEquals(367, ion.getIonTransition().getQ1Ion());
		assertEquals(112.1d, ion.getIonTransition().getQ3Ion());
	}
}

/*******************************************************************************
 * Copyright (c) 2015, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mgf.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.implementation.Ion;
import org.eclipse.chemclipse.msd.model.implementation.MassSpectra;
import org.eclipse.chemclipse.msd.model.implementation.ScanMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Test;

import net.openchrom.msd.converter.supplier.mgf.PathResolver;
import net.openchrom.msd.converter.supplier.mgf.TestPathHelper;

public class MassSpectrumExportConverter_1_ITest {

	@Test
	public void test() {

		File directory = new File(TestPathHelper.TESTDIR_EXPORT);
		directory.mkdir();
		File exportFile = new File(PathResolver.getAbsolutePath(TestPathHelper.TESTDIR_EXPORT) + File.separator + TestPathHelper.TESTFILE_EXPORT_MS_1);
		DatabaseExportConverter exportConverter = new DatabaseExportConverter();

		IMassSpectra massSpectra = new MassSpectra();
		IScanMSD massSpectrum = new ScanMSD();
		massSpectrum.addIon(new Ion(56.3f, 7382.3f));
		massSpectrum.addIon(new Ion(26.3f, 73382.3f));
		massSpectrum.addIon(new Ion(89.3f, 382.3f));
		massSpectra = new MassSpectra();
		massSpectra.addMassSpectrum(massSpectrum);

		IProcessingInfo<File> processingInfo = exportConverter.convert(exportFile, massSpectra, false, new NullProgressMonitor());
		assertTrue(processingInfo.getProcessingResult().exists());
		assertEquals(125, processingInfo.getProcessingResult().length());
	}
}

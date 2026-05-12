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
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.process.supplier.cms.core;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.Test;

import net.openchrom.msd.converter.supplier.cms.io.MassSpectrumReader;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorLibraryMassSpectrum;

public class MassSpectraDecomposition_1_ITest {

	private MassSpectraDecomposition massSpectraDecomposition = new MassSpectraDecomposition();

	@Test
	public void test1() throws IOException {

		/*
		 * argon, nitrogen, oxygen, ethane, ethylene
		 */
		File scanFile = new File("testData/files/import/test1/scan_42211.cms");
		MassSpectrumReader massSpectrumReader = new MassSpectrumReader();
		IMassSpectra scanSpectra = massSpectrumReader.read(scanFile, new NullProgressMonitor());
		/*
		 * argon, nitrogen, oxygen, ethane, ethylene
		 */
		File libraryFile = new File("testData/files/import/test1/LibrarySpectra.cms");
		IMassSpectra librarySpectra = massSpectrumReader.read(libraryFile, new NullProgressMonitor());
		for(IScanMSD libSpectrum : librarySpectra.getList()) {
			if(libSpectrum instanceof ICalibratedVendorLibraryMassSpectrum calibratedVendorLibraryMassSpectrum) {
				calibratedVendorLibraryMassSpectrum.setSelected(true);
			}
		}
		DecompositionResults results = massSpectraDecomposition.decompose(scanSpectra, librarySpectra, true, System.out);
		assertNotNull(results);
	}
}

/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Aleksandar Kurtakov - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mzmlb.fragment.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.implementation.ChromatogramMSD;
import org.eclipse.chemclipse.msd.model.implementation.Ion;
import org.eclipse.chemclipse.msd.model.implementation.ScanMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import net.openchrom.msd.converter.supplier.mzmlb.converter.ChromatogramExportConverter;
import net.openchrom.msd.converter.supplier.mzmlb.converter.ChromatogramImportConverter;

@TestInstance(Lifecycle.PER_CLASS)
public class ChromatogramExportConverter_ITest {

	private IChromatogramMSD chromatogram;
	private IChromatogramMSD reimported;
	private File exportFile;

	@BeforeAll
	public void exportAndReimport() throws IOException {

		exportFile = File.createTempFile("export", ".mzMLb");
		chromatogram = new ChromatogramMSD();
		chromatogram.setFile(exportFile);
		for(int s = 1; s <= 3; s++) {
			IScanMSD scan = new ScanMSD();
			scan.setRetentionTime(s * 1000);
			for(int i = 0; i < 4; i++) {
				scan.addIon(new Ion(50 + i * 10 + s * 0.5d, 100f * s + i));
			}
			chromatogram.addScan(scan);
		}
		IProcessingInfo<File> exportInfo = new ChromatogramExportConverter().convert(exportFile, chromatogram, new NullProgressMonitor());
		assertFalse(exportInfo.hasErrorMessages());
		reimported = new ChromatogramImportConverter().convert(exportFile, new NullProgressMonitor()).getProcessingResult();
	}

	@AfterAll
	public void deleteExport() {

		if(exportFile != null) {
			exportFile.delete();
		}
	}

	@Test
	public void testReimport() {

		assertNotNull(reimported);
		assertEquals(chromatogram.getNumberOfScans(), reimported.getNumberOfScans());
	}

	@Test
	public void testScans() {

		for(int s = 1; s <= chromatogram.getNumberOfScans(); s++) {
			IScanMSD expected = chromatogram.getScan(s);
			IScanMSD actual = reimported.getScan(s);
			assertEquals(expected.getRetentionTime(), actual.getRetentionTime());
			List<IIon> expectedIons = expected.getIons();
			List<IIon> actualIons = actual.getIons();
			assertEquals(expectedIons.size(), actualIons.size());
			for(int i = 0; i < expectedIons.size(); i++) {
				assertEquals(expectedIons.get(i).getIon(), actualIons.get(i).getIon(), 0);
				assertEquals(expectedIons.get(i).getAbundance(), actualIons.get(i).getAbundance(), 0);
			}
		}
	}
}

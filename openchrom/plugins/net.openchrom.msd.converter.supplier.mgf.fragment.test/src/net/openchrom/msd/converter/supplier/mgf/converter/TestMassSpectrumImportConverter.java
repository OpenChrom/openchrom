/*******************************************************************************
 * Copyright (c) 2016 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mgf.converter;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.eclipse.chemclipse.msd.converter.processing.massspectrum.IMassSpectrumImportConverterProcessingInfo;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.processing.core.exceptions.TypeCastException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.openchrom.msd.converter.supplier.mgf.converter.model.MGFMassSpectrum;

public class TestMassSpectrumImportConverter {

	private MassSpectrumImportConverter importConverter;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {

		importConverter = new MassSpectrumImportConverter();
	}

	@After
	public void tearDown() throws Exception {

		importConverter = null;
	}

	@Test
	public void testConvert01() throws TypeCastException {

		IMassSpectrumImportConverterProcessingInfo processingInfo = importConverter.convert(new File("testData/files/import/janko/ppw_L3_142651862521.txt"), new NullProgressMonitor());
		String[] expectedPepMasses = new String[]{"MS1dummyElement", "1022.5123", "1030.3408", "1048.3794", "1083.3975", "1177.6327", "1225.314", "1327.7755", "1389.6481", "1514.8406", "1534.8009", "1576.7075", "1631.7775", "1668.7076", "1719.7902", "1807.8884"};
		IMassSpectra massSpectra = processingInfo.getMassSpectra();
		// i == 0: MS1 Scan
		assertEquals(expectedPepMasses.length, massSpectra.getList().size());
		for(int i = 1; i < massSpectra.getList().size(); i++) {
			IScanMSD nextScan = massSpectra.getList().get(i);
			MGFMassSpectrum nextMGFMassSpectrum = (MGFMassSpectrum)nextScan;
			assertEquals(Double.parseDouble(expectedPepMasses[i]), nextMGFMassSpectrum.getPrecursorIon(), 0.0001);
		}
	}
}

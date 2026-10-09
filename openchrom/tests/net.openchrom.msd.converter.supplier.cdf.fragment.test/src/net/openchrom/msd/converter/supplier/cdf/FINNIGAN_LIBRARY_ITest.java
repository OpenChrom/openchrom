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
package net.openchrom.msd.converter.supplier.cdf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IRegularLibraryMassSpectrum;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

import net.openchrom.msd.converter.supplier.cdf.converter.DatabaseImportConverter;
import net.openchrom.msd.converter.supplier.cdf.converter.FileContentMatcher;
import net.openchrom.msd.converter.supplier.cdf.converter.FileContentMatcherDatabase;
import net.openchrom.msd.converter.supplier.cdf.converter.MagicNumberMatcher;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FINNIGAN_LIBRARY_ITest {

	private IMassSpectra massSpectra;
	private File file;

	@Test
	@Order(1)
	public void testImport() {

		file = new File("testData/FINNIGAN/LIBRARY.CDF");
		DatabaseImportConverter importConverter = new DatabaseImportConverter();
		IProcessingInfo<IMassSpectra> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		massSpectra = processingInfo.getProcessingResult();
		assertNotNull(massSpectra);
	}

	@Test
	public void testMatch() {

		MagicNumberMatcher magicNumberMatcher = new MagicNumberMatcher();
		assertTrue(magicNumberMatcher.checkFileFormat(file));

		FileContentMatcherDatabase fileContentMatcherDatabase = new FileContentMatcherDatabase();
		assertTrue(fileContentMatcherDatabase.checkFileFormat(file));

		FileContentMatcher fileContentMatcher = new FileContentMatcher();
		assertFalse(fileContentMatcher.checkFileFormat(file));
	}

	@Test
	public void testEntries() {

		assertEquals(100, massSpectra.size());
	}

	@Test
	public void testFirstEntry() {

		IRegularLibraryMassSpectrum massSpectrum = (IRegularLibraryMassSpectrum)massSpectra.getMassSpectrum(1);
		assertEquals("AA-DR.J.ZAMECNIK,H&W CANADA,BUREAU OF DRUG RES.,OTTAWA,957-1068", massSpectrum.getLibraryInformation().getName());
		assertEquals(5, massSpectrum.getNumberOfIons());
	}

	@Test
	public void testSecondEntry() {

		IRegularLibraryMassSpectrum massSpectrum = (IRegularLibraryMassSpectrum)massSpectra.getMassSpectrum(2);
		assertEquals("AMOBARBITAL", massSpectrum.getLibraryInformation().getName());
		assertEquals("C11H18O3N2", massSpectrum.getLibraryInformation().getFormula());
		assertEquals(34, massSpectrum.getNumberOfIons());
	}
}

/*******************************************************************************
 * Copyright (c) 2015, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.pkf.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.eclipse.chemclipse.converter.PathResolver;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.osgi.framework.FrameworkUtil;

import net.openchrom.msd.converter.supplier.pkf.TestPathHelper;

@TestInstance(Lifecycle.PER_CLASS)
public class MassSpectrumImportConverter_Ecoli_ITest {

	private IMassSpectra massSpectra;

	@BeforeAll
	public void setUp() throws IOException {

		File file = PathResolver.getFile(FrameworkUtil.getBundle(getClass()), TestPathHelper.TESTFILE_IMPORT_ECOLI);
		DatabaseImportConverter importConverter = new DatabaseImportConverter();
		IProcessingInfo<IMassSpectra> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		massSpectra = processingInfo.getProcessingResult();
	}

	@Test
	public void testBasicValidation() {

		assertNotNull(massSpectra);
		assertEquals("ecoli-peaklist-oct16", massSpectra.getName());
		assertFalse(massSpectra.getList().isEmpty());
		IScanMSD massSpectrum = massSpectra.getMassSpectrum(1);
		assertEquals(30, massSpectrum.getNumberOfIons());
		assertEquals(18, massSpectra.getList().size()); // should be 16, but contains more
	}
}

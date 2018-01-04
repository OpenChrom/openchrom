/*******************************************************************************
 * Copyright (c) 2015, 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mgf.converter;

import java.io.File;

import org.eclipse.chemclipse.msd.converter.processing.massspectrum.IMassSpectrumImportConverterProcessingInfo;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.core.runtime.NullProgressMonitor;

import junit.framework.TestCase;
import net.openchrom.msd.converter.supplier.mgf.PathResolver;
import net.openchrom.msd.converter.supplier.mgf.TestPathHelper;

public class MassSpectrumImportConverter_1_ITest extends TestCase {

	private IMassSpectra massSpectra;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		File file = new File(PathResolver.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_MS_1));
		MassSpectrumImportConverter importConverter = new MassSpectrumImportConverter();
		IMassSpectrumImportConverterProcessingInfo processingInfo = importConverter.convert(file, new NullProgressMonitor());
		massSpectra = processingInfo.getMassSpectra();
	}

	@Override
	protected void tearDown() throws Exception {

		massSpectra = null;
		super.tearDown();
	}

	public void testExceptions_1() {

		assertNotNull(massSpectra);
	}
}

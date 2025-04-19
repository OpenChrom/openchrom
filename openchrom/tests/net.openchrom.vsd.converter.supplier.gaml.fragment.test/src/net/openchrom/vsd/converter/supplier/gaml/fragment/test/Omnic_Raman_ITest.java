/*******************************************************************************
 * Copyright (c) 2025 Lablicate GmbH.
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
package net.openchrom.vsd.converter.supplier.gaml.fragment.test;

import java.io.File;

import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.vsd.model.core.ISpectrumVSD;
import org.eclipse.core.runtime.NullProgressMonitor;

import junit.framework.TestCase;
import net.openchrom.vsd.converter.supplier.gaml.PathResolver;
import net.openchrom.vsd.converter.supplier.gaml.converter.ScanImportConverter;

public class Omnic_Raman_ITest extends TestCase {

	private ISpectrumVSD spectrumVSD;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		File file = new File(PathResolver.getAbsolutePath(TestPathHelper.TN_OMNIC_RAMAN));
		ScanImportConverter importConverter = new ScanImportConverter();
		IProcessingInfo<ISpectrumVSD> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		spectrumVSD = processingInfo.getProcessingResult();
	}

	@Override
	protected void tearDown() throws Exception {

		spectrumVSD = null;
		super.tearDown();
	}

	public void testLoading() {

		assertNotNull(spectrumVSD);
	}

	public void testSignals() {

		assertEquals(3632, spectrumVSD.getScanVSD().getProcessedSignals().size());
	}
}

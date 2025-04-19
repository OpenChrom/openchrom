/*******************************************************************************
 * Copyright (c) 2023, 2025 Lablicate GmbH.
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
package net.openchrom.csd.converter.supplier.gaml.fragment.test;

import java.io.File;
import java.util.Collection;

import org.eclipse.chemclipse.model.core.IComplexSignalMeasurement;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;

import net.openchrom.nmr.converter.supplier.gaml.PathResolver;
import net.openchrom.nmr.converter.supplier.gaml.converter.ScanImportConverter;

import junit.framework.TestCase;

public class BrukerXWinNMR1D_ITest extends TestCase {

	private Collection<IComplexSignalMeasurement<?>> complexSignals;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		File file = new File(PathResolver.getAbsolutePath(TestPathHelper.BRUKER_XWINNMR_1D));
		ScanImportConverter importConverter = new ScanImportConverter();
		IProcessingInfo<Collection<IComplexSignalMeasurement<?>>> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		complexSignals = processingInfo.getProcessingResult();
	}

	@Override
	protected void tearDown() throws Exception {

		complexSignals = null;
		super.tearDown();
	}

	public void testLoading() {

		assertNotNull(complexSignals);
		assertFalse(complexSignals.isEmpty());
	}

	public void testSignals() {

		assertEquals(8192, complexSignals.iterator().next().getSignals().size());
	}
}

/*******************************************************************************
 * Copyright (c) 2023, 2026 Lablicate GmbH.
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.util.Collection;

import org.eclipse.chemclipse.model.core.IComplexSignalMeasurement;
import org.eclipse.chemclipse.nmr.model.core.ISpectrumNMR;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

import net.openchrom.nmr.converter.supplier.gaml.converter.ScanImportConverter;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BrukerXWinNMR1D_ITest {

	private Collection<IComplexSignalMeasurement<?>> complexSignals;

	@Test
	@Order(1)
	public void testImport() {

		File file = new File("testData/files/import/Bruker_XWINNMR_1D.gaml");
		ScanImportConverter importConverter = new ScanImportConverter();
		IProcessingInfo<ISpectrumNMR> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		complexSignals = processingInfo.getProcessingResult().getComplexSignalMeasurements();
		assertNotNull(complexSignals);
	}

	@Test
	public void testLoading() {

		assertFalse(complexSignals.isEmpty());
	}

	@Test
	public void testSignals() {

		assertEquals(8192, complexSignals.iterator().next().getSignals().size());
	}
}

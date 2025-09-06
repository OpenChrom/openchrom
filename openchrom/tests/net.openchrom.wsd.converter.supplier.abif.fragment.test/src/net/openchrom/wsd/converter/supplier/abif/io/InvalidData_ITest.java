/*******************************************************************************
 * Copyright (c) 2016, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Alexander Kerner - Generics
 *******************************************************************************/
package net.openchrom.wsd.converter.supplier.abif.io;

import static org.junit.Assert.assertNull;

import java.io.File;

import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.wsd.converter.chromatogram.ChromatogramConverterWSD;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.BeforeClass;
import org.junit.Test;

import net.openchrom.wsd.converter.supplier.abif.ABIF;

public class InvalidData_ITest {

	private static IChromatogramWSD chromatogram;

	@BeforeClass
	public static void setUp() {

		File fileImport = new File(ABIF.getAbsolutePath(ABIF.TESTFILE_IMPORT_FAKE_AB1));
		IProcessingInfo<IChromatogramWSD> processingInfo = ChromatogramConverterWSD.getInstance().convert(fileImport, ABIF.EXTENSION_POINT_ID, new NullProgressMonitor());
		chromatogram = processingInfo.getProcessingResult();
	}

	@Test
	public void testInvalidFile() {

		assertNull(chromatogram);
	}
}

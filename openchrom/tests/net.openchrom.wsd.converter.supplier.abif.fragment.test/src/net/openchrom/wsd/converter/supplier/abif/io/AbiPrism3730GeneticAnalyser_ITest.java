/*******************************************************************************
 * Copyright (c) 2016, 2025 Matthias Mailänder, Philip Wenig.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.wsd.converter.supplier.abif.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.wsd.converter.chromatogram.ChromatogramConverterWSD;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import net.openchrom.wsd.converter.supplier.abif.ABIF;

@TestInstance(Lifecycle.PER_CLASS)
public class AbiPrism3730GeneticAnalyser_ITest {

	private IChromatogramWSD chromatogram;

	@BeforeAll
	public void setUp() {

		File fileImport = new File(ABIF.getAbsolutePath(ABIF.TESTFILE_IMPORT_3730_AB1));
		IProcessingInfo<IChromatogramWSD> processingInfo = ChromatogramConverterWSD.getInstance().convert(fileImport, ABIF.EXTENSION_POINT_ID, new NullProgressMonitor());
		chromatogram = processingInfo.getProcessingResult();
	}

	@Test
	public void testScans() {

		assertEquals(16961, chromatogram.getNumberOfScans());
	}
}

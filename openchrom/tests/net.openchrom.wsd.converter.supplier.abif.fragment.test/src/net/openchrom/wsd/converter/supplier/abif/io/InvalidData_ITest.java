/*******************************************************************************
 * Copyright (c) 2016, 2026 Lablicate GmbH.
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

import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.File;
import java.io.IOException;

import org.eclipse.chemclipse.converter.PathResolver;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.wsd.converter.chromatogram.ChromatogramConverterWSD;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.osgi.framework.FrameworkUtil;

import net.openchrom.wsd.converter.supplier.abif.ABIF;

@TestInstance(Lifecycle.PER_CLASS)
public class InvalidData_ITest {

	private IChromatogramWSD chromatogram;

	@BeforeAll
	public void setUp() throws IOException {

		File fileImport = PathResolver.getFile(FrameworkUtil.getBundle(getClass()), ABIF.TESTFILE_IMPORT_FAKE_AB1);
		IProcessingInfo<IChromatogramWSD> processingInfo = ChromatogramConverterWSD.getInstance().convert(fileImport, ABIF.EXTENSION_POINT_ID, new NullProgressMonitor());
		chromatogram = processingInfo.getProcessingResult();
	}

	@Test
	public void testInvalidFile() {

		assertNull(chromatogram);
	}
}

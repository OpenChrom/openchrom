/*******************************************************************************
 * Copyright (c) 2024, 2025 Lablicate GmbH.
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
package net.openchrom.msd.converter.supplier.animl.converter;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.support.history.IEditInformation;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Test;

import net.openchrom.msd.converter.supplier.animl.PathResolver;
import net.openchrom.msd.converter.supplier.animl.TestPathHelper;

import junit.framework.TestCase;

public class StrictImport_ITest extends TestCase {

	private IChromatogramMSD chromatogram;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		File file = new File(PathResolver.getAbsolutePath(TestPathHelper.TESTFILE_IMPORT_MS_STRICT));
		ChromatogramImportConverter importConverter = new ChromatogramImportConverter();
		IProcessingInfo<IChromatogramMSD> processingInfo = importConverter.convert(file, new NullProgressMonitor());
		chromatogram = processingInfo.getProcessingResult();
	}

	@Override
	protected void tearDown() throws Exception {

		chromatogram = null;
		super.tearDown();
	}

	@Test
	public void testChromatogram() {

		assertNotNull(chromatogram);
		assertEquals("Test Sample", chromatogram.getSampleName());
		assertEquals("Gaston Bradford", chromatogram.getOperator());
	}

	@Test
	public void testEditHistory() {

		IEditInformation editInformation = chromatogram.getEditHistory().get(0);
		GregorianCalendar calendar = new GregorianCalendar(2023, Calendar.JANUARY, 19, 13, 25, 36);
		calendar.set(Calendar.MILLISECOND, 277);
		assertEquals(calendar.getTime(), editInformation.getDate());
		assertEquals("converted, unify data", editInformation.getDescription());
		assertEquals("AnIML Converter", editInformation.getEditor());
	}

	@Test
	public void testScans() {

		assertEquals(306, chromatogram.getNumberOfScans());
		assertEquals(79834.98f, chromatogram.getScan(1).getTotalSignal());
		assertEquals(1000, chromatogram.getScan(2).getRetentionTime());
	}
}

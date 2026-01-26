/*******************************************************************************
 * Copyright (c) 2025, 2026 Lablicate GmbH.
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
package net.openchrom.chromatogram.xxd.report.supplier.excel.template.io;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class TemplateIntegrationTest {

	private ExcelTemplateReportWriter excelTemplateReportWriter = new ExcelTemplateReportWriter();

	private File file;
	private File exportPath;

	@BeforeAll
	public void setUp() {

		exportPath = new File("testData/files/export");
		exportPath.mkdirs();
		file = new File(exportPath, "Template.xlsx");
	}

	@AfterAll
	public void tearDown() {

		exportPath.delete();
	}

	@Test
	public void testGenerateTemplate() throws IOException {

		excelTemplateReportWriter.generateTemplate(file);
		assertTrue(file.length() > 0);
	}
}

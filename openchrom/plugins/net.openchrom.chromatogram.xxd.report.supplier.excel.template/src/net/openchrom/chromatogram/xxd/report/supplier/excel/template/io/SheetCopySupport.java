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
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.xxd.report.supplier.excel.template.io;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class SheetCopySupport {

	public static void copy(XSSFSheet sheetSource, XSSFSheet sheetSink) {

		/*
		 * Column Width
		 */
		for(int column = 0; column < sheetSource.getRow(0).getLastCellNum(); column++) {
			sheetSink.setColumnWidth(column, sheetSource.getColumnWidth(column));
		}
		/*
		 * Copy Data
		 */
		for(Row rowSource : sheetSource) {
			Row rowSink = sheetSink.createRow(rowSource.getRowNum());
			for(Cell cellSource : rowSource) {
				Cell cellSink = rowSink.createCell(cellSource.getColumnIndex());
				copyCell(cellSource, cellSink);
			}
		}
	}

	private static void copyCell(Cell cellSource, Cell cellSink) {

		/*
		 * Content
		 */
		switch(cellSource.getCellType()) {
			case STRING:
				cellSink.setCellValue(cellSource.getStringCellValue());
				break;
			case NUMERIC:
				cellSink.setCellValue(cellSource.getNumericCellValue());
				break;
			case BOOLEAN:
				cellSink.setCellValue(cellSource.getBooleanCellValue());
				break;
			case FORMULA:
				cellSink.setCellFormula(cellSource.getCellFormula());
				break;
			case BLANK:
				cellSink.setBlank();
				break;
			default:
				break;
		}
		/*
		 * Copy the style
		 */
		CellStyle cellStyleSink = cellSink.getSheet().getWorkbook().createCellStyle();
		cellStyleSink.cloneStyleFrom(cellSource.getCellStyle());
		cellSink.setCellStyle(cellStyleSink);
	}
}
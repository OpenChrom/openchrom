/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.pdf.io.support;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.logging.core.Logger;

public class PDFTable {

	private static final Logger logger = Logger.getLogger(PDFTable.class);
	//
	private int rowStart = 1;
	private int rowStop = 0;
	//
	private int index = -1;
	private List<List<TableCell>> titleRows = new ArrayList<>();
	private List<List<String>> dataRows = new ArrayList<>();

	public PDFTable() {
		nextTitleRow();
	}

	public void nextTitleRow() {

		titleRows.add(new ArrayList<>());
		index = titleRows.size() - 1;
	}

	public int getRowStart() {

		return rowStart;
	}

	public void setRowStart(int rowStart) {

		this.rowStart = rowStart;
	}

	public int getRowStop() {

		return rowStop;
	}

	public void setRowStop(int rowStop) {

		this.rowStop = rowStop;
	}

	public int getNumberRows() {

		return dataRows.size();
	}

	public void addColumn(String title, float columnWidth) {

		addColumn(new TableCell(title, columnWidth, TableCell.BORDER_ALL));
	}

	public void addColumn(TableCell tableCell) {

		List<TableCell> titleRow = titleRows.get(index);
		titleRow.add(tableCell);
	}

	public void addRow(List<String> row) {

		dataRows.add(row);
		rowStop = getNumberRows();
	}

	public List<List<TableCell>> getTitles() {

		return titleRows;
	}

	public List<Float> getBounds() {

		List<Float> bounds = new ArrayList<>();
		for(TableCell tableCell : titleRows.get(index)) {
			bounds.add(tableCell.getWidth());
		}
		return bounds;
	}

	public List<List<String>> getRows() {

		return dataRows;
	}

	public float getWidth() {

		float width = 0.0f;
		for(float bound : getBounds()) {
			width += bound;
		}
		return width;
	}

	public boolean isValid() {

		List<Float> bounds = getBounds();
		for(List<String> dataRow : dataRows) {
			if(dataRow.size() != bounds.size()) {
				logger.warn("Row size not equals bounds size: " + dataRow.size() + " - " + bounds.size());
				return false;
			}
		}
		//
		return true;
	}
}

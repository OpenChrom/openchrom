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
	private int startIndex = 0;
	private int stopIndex = 0;
	private int defaultBorder; // initialized in constructor
	//
	private int indexHeader = -1;
	private List<List<TableCell>> headerRows = new ArrayList<>();
	private List<List<String>> dataRows = new ArrayList<>();

	public PDFTable() {
		this(TableCell.BORDER_ALL);
	}

	public PDFTable(int defaultBorder) {
		/*
		 * Important to add at least one header row.
		 */
		nextHeaderRow();
		this.defaultBorder = defaultBorder;
	}

	public void nextHeaderRow() {

		headerRows.add(new ArrayList<>());
		indexHeader = headerRows.size() - 1;
	}

	/**
	 * Get the data row start index - 0 based.
	 * 
	 * @return int
	 */
	public int getStartIndex() {

		return startIndex;
	}

	/**
	 * Data row start index - 0 based.
	 * 
	 * @param startIndex
	 */
	public void setStartIndex(int startIndex) {

		this.startIndex = startIndex;
	}

	/**
	 * Get the data row stop index - 0 based.
	 * 
	 * @return int
	 */
	public int getStopIndex() {

		return stopIndex;
	}

	/**
	 * Data row stop index - 0 based.
	 * 
	 * @param startIndex
	 */
	public void setStopIndex(int stopIndex) {

		this.stopIndex = stopIndex;
	}

	public int getNumberHeaderRows() {

		return headerRows.size();
	}

	public int getNumberDataRows() {

		return dataRows.size();
	}

	public void addColumn(String title, float columnWidth) {

		addColumn(new TableCell(title, columnWidth, defaultBorder));
	}

	public void addColumn(TableCell tableCell) {

		List<TableCell> titleRow = headerRows.get(indexHeader);
		titleRow.add(tableCell);
	}

	public List<List<TableCell>> getHeader() {

		return headerRows;
	}

	public void addDataRow(List<String> row) {

		dataRows.add(row);
		stopIndex = getNumberDataRows();
	}

	public List<TableCell> getDataRow(int i) {

		List<TableCell> rowCells = new ArrayList<TableCell>();
		if(i >= 0 && i < dataRows.size()) {
			List<String> row = dataRows.get(i);
			for(int j = 0; j < row.size(); j++) {
				float width = headerRows.get(indexHeader).get(j).getWidth();
				rowCells.add(new TableCell(row.get(j), width, defaultBorder));
			}
		}
		return rowCells;
	}

	public float getWidth() {

		float width = 0.0f;
		for(TableCell tableCell : headerRows.get(indexHeader)) {
			width += tableCell.getWidth();
		}
		return width;
	}

	public boolean isValid() {

		int columns = headerRows.get(indexHeader).size();
		for(List<String> dataRow : dataRows) {
			if(dataRow.size() != columns) {
				logger.warn("Row size not equals columns size: " + dataRow.size() + " - " + columns);
				return false;
			}
		}
		//
		return true;
	}
}

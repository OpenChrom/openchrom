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
import java.util.Collections;
import java.util.List;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.eclipse.chemclipse.logging.core.Logger;

public class PDFTable {

	private static final Logger logger = Logger.getLogger(PDFTable.class);
	//
	private static final float A4_PORTRAIT_HEIGHT = 297;
	private static final float A4_PORTRAIT_WIDTH = 210;
	/*
	 * 0,0 is left top.
	 */
	//
	private PDFont font = PDType1Font.HELVETICA;
	private PDFont fontBold = PDType1Font.HELVETICA;
	private float fontSize = 12;
	//
	private float positionX = 0.0f; // mm
	private float positionY = 0.0f; // mm
	private float columnHeight = 5.5f; // mm
	private int rowStart = 1;
	private int rowStop = 0;
	//
	private List<String> titles = new ArrayList<>();
	private List<Float> bounds = new ArrayList<>();
	private List<List<String>> rows = new ArrayList<>();

	public PDFont getFont() {

		return font;
	}

	public void setFont(PDFont font) {

		this.font = font;
	}

	public PDFont getFontBold() {

		return fontBold;
	}

	public void setFontBold(PDFont fontBold) {

		this.fontBold = fontBold;
	}

	public float getFontSize() {

		return fontSize;
	}

	public void setFontSize(float fontSize) {

		this.fontSize = fontSize;
	}

	public float getPositionX() {

		return positionX;
	}

	public void setPositionX(float positionX) {

		this.positionX = positionX;
	}

	public float getPositionY() {

		return positionY;
	}

	public void setPositionY(float positionY) {

		this.positionY = positionY;
	}

	public float getColumnHeight() {

		return columnHeight;
	}

	public void setColumnHeight(float columnHeight) {

		this.columnHeight = columnHeight;
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

		return rows.size();
	}

	public void addColumn(String title, float columnWidth) {

		titles.add(title);
		bounds.add(columnWidth);
	}

	public void addRow(List<String> row) {

		rows.add(row);
		rowStop = getNumberRows();
	}

	public List<String> getTitles() {

		return Collections.unmodifiableList(titles);
	}

	public List<Float> getBounds() {

		return Collections.unmodifiableList(bounds);
	}

	public List<List<String>> getRows() {

		return rows;
	}

	public float getWidth() {

		float width = 0.0f;
		for(float bound : bounds) {
			width += bound;
		}
		return width;
	}

	public boolean isValid() {

		if(titles.size() != bounds.size()) {
			logger.warn("Titles size not equals bounds size: " + titles.size() + " - " + bounds.size());
			return false;
		}
		//
		for(List<String> row : rows) {
			if(row.size() != bounds.size()) {
				logger.warn("Row size not equals bounds size: " + row.size() + " - " + bounds.size());
				return false;
			}
		}
		//
		float width = positionX + getWidth();
		if(width > A4_PORTRAIT_WIDTH) {
			logger.warn("The table width is larger then the paper width: " + width + " - " + A4_PORTRAIT_WIDTH);
			return false;
		}
		/*
		 * +2 = Header + Offset
		 */
		float height = (rowStop - rowStart + 2) * columnHeight;
		if(height > A4_PORTRAIT_HEIGHT) {
			logger.warn("The table contains more elements than there is available space: " + height + " - " + A4_PORTRAIT_HEIGHT);
			// No valid false
		}
		//
		return true;
	}
}

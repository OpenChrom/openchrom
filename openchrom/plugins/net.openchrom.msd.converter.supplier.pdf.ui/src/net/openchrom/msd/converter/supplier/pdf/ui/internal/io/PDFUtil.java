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
package net.openchrom.msd.converter.supplier.pdf.ui.internal.io;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.eclipse.chemclipse.logging.core.Logger;

/*
 * Only PORTRAIT IS SUPPORTED CURRENTLY.
 * A4 PORTRAIT = 595.0 x 842.0 pt
 * A4 LANDSCAPE = 842.0 x 595.0 pt
 * 0,0 is lower left!
 */
public class PDFUtil {

	private static final Logger logger = Logger.getLogger(PDFUtil.class);
	//
	private static final float FACTOR_MM_TO_PT = 2.8346f;
	//
	private static final float A4_PORTRAIT_HEIGHT = 297 * FACTOR_MM_TO_PT;
	// private static final float A4_PORTRAIT_WIDTH = 210 * FACTOR_MM_TO_PT;
	//
	private static final PDFont FONT_NORMAL = PDType1Font.HELVETICA;
	private static final PDFont FONT_BOLD = PDType1Font.HELVETICA_BOLD;

	public float convertMillimeterToPoint(float value) {

		return value * FACTOR_MM_TO_PT;
	}

	public float getPositionLeft(float x) {

		return x * FACTOR_MM_TO_PT;
	}

	public float getPositionTop(float y) {

		return A4_PORTRAIT_HEIGHT - y * FACTOR_MM_TO_PT;
	}

	public void printTable(PDPageContentStream contentStream, PDFTable pdfTable) throws IOException {

		if(pdfTable.isValid()) {
			//
			float xPosition = getPositionLeft(pdfTable.getPositionX());
			float yPosition = getPositionTop(pdfTable.getPositionY());
			float width = convertMillimeterToPoint(pdfTable.getWidth());
			float height = convertMillimeterToPoint(pdfTable.getColumnHeight());
			int startIndex = pdfTable.getRowStart() - 1;
			int stopIndex = pdfTable.getRowStop() - 1;
			//
			List<String> titles = pdfTable.getTitles();
			List<Float> bounds = pdfTable.getBounds();
			List<List<String>> rows = pdfTable.getRows();
			/*
			 * Header
			 */
			List<TableCell> titleCells = new ArrayList<TableCell>();
			for(int i = 0; i < titles.size(); i++) {
				titleCells.add(new TableCell(titles.get(i), convertMillimeterToPoint(bounds.get(i))));
			}
			yPosition -= printTableLine(contentStream, xPosition, yPosition, width, height, titleCells, Color.GRAY, true, true);
			/*
			 * Data
			 */
			if(pdfTable.getNumberRows() > 0) {
				for(int i = startIndex; i <= stopIndex; i++) {
					List<String> row = rows.get(i);
					List<TableCell> rowCells = new ArrayList<TableCell>();
					for(int j = 0; j < row.size(); j++) {
						String cell = row.get(j);
						rowCells.add(new TableCell(cell, convertMillimeterToPoint(bounds.get(j))));
					}
					Color backgroundColor = (i % 2 == 0) ? null : Color.LIGHT_GRAY;
					yPosition -= printTableLine(contentStream, xPosition, yPosition, width, height, rowCells, backgroundColor, false, true);
				}
			}
			/*
			 * Print last line.
			 */
			printTableLines(contentStream, xPosition, yPosition, width, height, getPositionTop(pdfTable.getPositionY()), titleCells);
		} else {
			logger.warn("The PDFTable is invalid.");
		}
	}

	private float printTableLine(PDPageContentStream contentStream, float xPosition, float yPosition, float width, float height, List<TableCell> cells, Color color, boolean bold, boolean drawBottomLine) throws IOException {

		contentStream.setFont((bold) ? FONT_BOLD : FONT_NORMAL, 12);
		/*
		 * Background
		 */
		if(color != null) {
			contentStream.setStrokingColor(color);
			contentStream.setNonStrokingColor(color);
			contentStream.addRect(xPosition, yPosition - height, width, height);
			contentStream.fill();
		}
		/*
		 * Print the text
		 */
		contentStream.setStrokingColor(Color.WHITE); // Background/Border
		contentStream.setNonStrokingColor(Color.BLACK); // Text
		float xLeft = xPosition;
		float yText = yPosition - height + convertMillimeterToPoint(1.0f);
		for(TableCell cell : cells) {
			printText(contentStream, xLeft, yText, " " + cell.getText());
			xLeft += cell.getWidth();
		}
		/*
		 * Bottom Line
		 */
		if(drawBottomLine) {
			contentStream.setStrokingColor(Color.BLACK); // Background/Border
			contentStream.setNonStrokingColor(Color.BLACK); // Text
			float yBottom = yPosition - height;
			drawLine(contentStream, xPosition, yBottom, xPosition + width, yBottom);
		}
		//
		return height;
	}

	private void printTableLines(PDPageContentStream contentStream, float xPosition, float yPosition, float width, float height, float yStartPosition, List<TableCell> cells) throws IOException {

		contentStream.setStrokingColor(Color.BLACK); // Background/Border
		contentStream.setNonStrokingColor(Color.BLACK); // Text
		/*
		 * Top Line
		 */
		drawLine(contentStream, xPosition, yStartPosition, xPosition + width, yStartPosition);
		/*
		 * Print vertical lines.
		 */
		//
		float yStart = yStartPosition;
		float yStartExtraSpace = (yStartPosition + convertMillimeterToPoint(1.9f));
		float yStop = yPosition;
		float xLeft = xPosition;
		for(TableCell cell : cells) {
			if(cell.isPrintLeftLine()) {
				drawLine(contentStream, xLeft, yStart, xLeft, yStop);
			} else {
				drawLine(contentStream, xLeft, yStartExtraSpace, xLeft, yStop);
			}
			xLeft += cell.getWidth();
		}
		//
		drawLine(contentStream, xPosition + width, yStart, xPosition + width, yStop);
	}

	/**
	 * X and Y in pt.
	 * 
	 * @param contentStream
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @throws IOException
	 */
	public void drawLine(PDPageContentStream contentStream, float x1, float y1, float x2, float y2) throws IOException {

		contentStream.moveTo(x1, y1);
		contentStream.lineTo(x2, y2);
		contentStream.stroke();
	}

	/**
	 * X and Y in pt.
	 * 
	 * @param contentStream
	 * @param x
	 * @param y
	 * @param text
	 * @throws IOException
	 */
	public void printText(PDPageContentStream contentStream, float x, float y, String text) throws IOException {

		contentStream.beginText();
		contentStream.newLineAtOffset(x, y);
		contentStream.showText(text);
		contentStream.endText();
	}
}

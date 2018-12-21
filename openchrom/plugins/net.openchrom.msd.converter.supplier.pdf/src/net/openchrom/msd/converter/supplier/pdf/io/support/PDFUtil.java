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

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;
import org.eclipse.chemclipse.logging.core.Logger;

/*
 * Default A4 portrait
 * 0,0 is upper left
 */
public class PDFUtil {

	private static final Logger logger = Logger.getLogger(PDFUtil.class);
	/*
	 * Default A4 portrait
	 */
	private PDPage pdPage;
	private PDPageContentStream contentStream;
	private PDRectangle pdRectangle = PDRectangle.A4;
	private IUnitConverter unitConverter = UnitConverterFactory.getInstance(Unit.PT);

	public PDFUtil(PDRectangle pdRectangle, IUnitConverter unitConverter) {
		// pdPage = new PDPage(pdRectangle);
		// contentStream = new PDPageContentStream(document, pdPage);
		this.pdRectangle = pdRectangle;
		this.unitConverter = unitConverter;
	}
	
	public PDPage getPage() {
		
		return pdPage;
	}

	public void printImage(PDPageContentStream contentStream, PDImageXObject image, float x, float y, float width, float height) throws IOException {

		contentStream.drawImage(image, getPositionLeft(x), getPositionTop(y + height), convert(width), convert(height));
	}

	public void printLine(PDPageContentStream contentStream, float x1, float y1, float x2, float y2) throws IOException {

		contentStream.moveTo(getPositionLeft(x1), getPositionTop(y1));
		contentStream.lineTo(getPositionLeft(x2), getPositionTop(y2));
		contentStream.stroke();
	}

	public void printTextTopEdge(PDPageContentStream contentStream, PDFont font, float fontSize, float x, float y, float maxWidth, String text) throws IOException {

		float height = calculateTextHeight(font, fontSize);
		printText(contentStream, font, fontSize, getPositionLeft(x), getPositionTop(y) - height, convert(maxWidth), text);
	}

	public void printTextBottomEdge(PDPageContentStream contentStream, PDFont font, float fontSize, float x, float y, float maxWidth, String text) throws IOException {

		printText(contentStream, font, fontSize, getPositionLeft(x), getPositionTop(y), convert(maxWidth), text);
	}

	public void printBackground(PDPageContentStream contentStream, Color strokingColor, Color nonStrokingColor, float x, float y, float width, float height) throws IOException {

		contentStream.setStrokingColor(strokingColor);
		contentStream.setNonStrokingColor(nonStrokingColor);
		contentStream.addRect(getPositionLeft(x), getPositionTop(y + height), convert(width), convert(height));
		contentStream.fill();
	}

	public void printTable(PDPageContentStream contentStream, PDFTable pdfTable) throws IOException {

		if(pdfTable.isValid()) {
			//
			float x = pdfTable.getPositionX();
			float y = pdfTable.getPositionY();
			float width = pdfTable.getWidth();
			float height = pdfTable.getColumnHeight();
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
				titleCells.add(new TableCell(titles.get(i), bounds.get(i)));
			}
			y += printTableLine(contentStream, pdfTable.getFontBold(), pdfTable.getFont(), pdfTable.getFontSize(), x, y, width, height, titleCells, Color.GRAY, true, true);
			/*
			 * Data
			 */
			if(pdfTable.getNumberRows() > 0) {
				for(int i = startIndex; i <= stopIndex; i++) {
					List<String> row = rows.get(i);
					List<TableCell> rowCells = new ArrayList<TableCell>();
					for(int j = 0; j < row.size(); j++) {
						String cell = row.get(j);
						rowCells.add(new TableCell(cell, bounds.get(j)));
					}
					Color backgroundColor = (i % 2 == 0) ? null : Color.LIGHT_GRAY;
					y += printTableLine(contentStream, pdfTable.getFontBold(), pdfTable.getFont(), pdfTable.getFontSize(), x, y, width, height, rowCells, backgroundColor, false, true);
				}
			}
			/*
			 * Print last line.
			 */
			printTableLines(contentStream, x, y, width, height, pdfTable.getPositionY(), titleCells);
		} else {
			logger.warn("The PDFTable is invalid.");
		}
	}

	private float getPageHeight() {

		return pdRectangle.getHeight();
	}

	private float getPageWidth() {

		return pdRectangle.getWidth();
	}

	private float calculateTextHeight(PDFont font, float fontSize) {

		PDRectangle rectangle = font.getFontDescriptor().getFontBoundingBox();
		return (rectangle.getHeight() / 1000.0f * fontSize * 0.68f);
	}

	private float calculateTextWidth(PDFont font, float fontSize, String text) throws IOException {

		return (font.getStringWidth(text) / 1000.0f * fontSize);
	}

	private float getPositionLeft(float x) {

		return convert(x);
	}

	private float getPositionTop(float y) {

		return getPageHeight() - convert(y);
	}

	private float convert(float value) {

		return unitConverter.convert(value);
	}

	private void printText(PDPageContentStream contentStream, PDFont font, float fontSize, float x, float y, float maxWidth, String text) throws IOException {

		int textLength = text.length();
		if(textLength > 0) {
			float textWidth = calculateTextWidth(font, fontSize, text);
			float pageWidth = getPageWidth();
			float availableWidth = (maxWidth > pageWidth) ? pageWidth : maxWidth;
			/*
			 * TODO
			 * Shorten, MultiLine?
			 */
			String printText;
			int endIndex = (int)(text.length() / textWidth * availableWidth) - 4;
			if(textWidth > availableWidth && endIndex > 0) {
				printText = text.substring(0, endIndex) + "...";
			} else {
				printText = text;
			}
			//
			contentStream.setFont(font, fontSize);
			printText(contentStream, x, y, printText);
		}
	}

	private void printText(PDPageContentStream contentStream, float x, float y, String text) throws IOException {

		contentStream.beginText();
		contentStream.setTextMatrix(Matrix.getRotateInstance(Math.toRadians(-90), 0, 0));
		contentStream.newLineAtOffset(x, y);
		contentStream.showText(text);
		contentStream.endText();
	}

	private float printTableLine(PDPageContentStream contentStream, PDFont fontBold, PDFont font, float fontSize, float x, float y, float width, float height, List<TableCell> cells, Color color, boolean bold, boolean drawBottomLine) throws IOException {

		/*
		 * Background
		 */
		if(color != null) {
			printBackground(contentStream, color, color, x, y, width, height);
		}
		/*
		 * Print the text
		 */
		contentStream.setStrokingColor(Color.WHITE); // Background/Border
		contentStream.setNonStrokingColor(Color.BLACK); // Text
		float xLeft = x; // + 1mm? instead of whitespace " " + cell.getText()
		float yText = y + 1; // TODO + 1mm
		for(TableCell cell : cells) {
			printTextTopEdge(contentStream, (bold) ? fontBold : font, fontSize, xLeft, yText, cell.getWidth(), " " + cell.getText());
			xLeft += cell.getWidth();
		}
		/*
		 * Bottom Line
		 */
		if(drawBottomLine) {
			contentStream.setStrokingColor(Color.BLACK); // Background/Border
			contentStream.setNonStrokingColor(Color.BLACK); // Text
			float yBottom = y + height;
			printLine(contentStream, x, yBottom, x + width, yBottom);
		}
		//
		return height;
	}

	private void printTableLines(PDPageContentStream contentStream, float x, float y, float width, float height, float yStartPosition, List<TableCell> cells) throws IOException {

		contentStream.setStrokingColor(Color.BLACK); // Background/Border
		contentStream.setNonStrokingColor(Color.BLACK); // Text
		/*
		 * Top Line
		 */
		printLine(contentStream, x, yStartPosition, x + width, yStartPosition);
		/*
		 * Print vertical lines.
		 */
		//
		float yStart = yStartPosition;
		float yStartExtraSpace = yStartPosition; // + 5.0f; // 5.385835pt -> 1.9 mm
		float yStop = y;
		float xLeft = x;
		for(TableCell cell : cells) {
			if(cell.isPrintLeftLine()) {
				printLine(contentStream, xLeft, yStart, xLeft, yStop);
			} else {
				printLine(contentStream, xLeft, yStartExtraSpace, xLeft, yStop);
			}
			xLeft += cell.getWidth();
		}
		//
		printLine(contentStream, x + width, yStart, x + width, yStop);
	}
}

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

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;
import org.eclipse.chemclipse.logging.core.Logger;
/*
 * Defaults
 * Page Center: top left (0,0)
 * Unit: mm
 * ***
 * All public method x,y values are handled in the given unit.In this case:mm and page center top left(0,0).*All private method x,y values are using the default PDF values pt and and page center bottom left(0,0).
 */

public class PageUtil {

	private static final Logger logger = Logger.getLogger(PageUtil.class);
	//
	private PDPage page = null; // Initialized in constructor
	private PDPageContentStream contentStream = null; // Initialized in constructor
	private IUnitConverter unitConverter = UnitFactory.getInstance(Unit.MM);
	private boolean landscape = false;

	/**
	 * Portrait
	 * 
	 * @param document
	 * @param pdRectangle
	 * @throws IOException
	 */
	public PageUtil(PDDocument document, PDRectangle pdRectangle) throws IOException {
		this(document, pdRectangle, false);
	}

	public PageUtil(PDDocument document, PDRectangle pdRectangle, boolean landscape) throws IOException {
		page = new PDPage(pdRectangle);
		document.addPage(page);
		contentStream = new PDPageContentStream(document, page);
		this.landscape = landscape;
		if(this.landscape) {
			page.setRotation(-90);
			float x = 0;
			float y = pdRectangle.getHeight();
			contentStream.transform(Matrix.getTranslateInstance(x, y));
			contentStream.transform(Matrix.getRotateInstance(Math.toRadians(-90), 0, 0));
		}
	}

	@Override
	protected void finalize() throws Throwable {

		close();
		super.finalize();
	}

	public PDPage getPage() {

		return page;
	}

	/**
	 * Call close when page creation is finished.
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {

		if(contentStream != null) {
			contentStream.close();
		}
	}

	public void printText(TextElement textElement) throws IOException {

		PDFont font = textElement.getFont();
		float fontSize = textElement.getFontSize();
		float maxWidth = convert(textElement.getMaxWidth());
		String text = textElement.getText();
		//
		float x;
		switch(textElement.getAlignHorizontal()) {
			case LEFT:
				x = getPositionLeft(textElement.getX());
				break;
			default:
				x = getPositionLeft(textElement.getX()); // Default LEFT
				logger.warn("Horizontal alignemt: Only LEFT is supported at the moment. Default LEFT");
				break;
		}
		//
		float y;
		switch(textElement.getAlignVertical()) {
			case TOP:
				float height = calculateTextHeight(font, fontSize);
				y = getPositionTop(textElement.getY()) - height;
				break;
			case BOTTOM:
				y = getPositionTop(textElement.getY());
				break;
			default:
				logger.warn("Vertical alignemt: Only TOP and BOTTOM are supported at the moment. Default BOTTOM");
				y = getPositionTop(textElement.getY());
				break;
		}
		//
		printText(font, fontSize, x, y, maxWidth, text);
	}

	/**
	 * Prints the text at the given position. The top line of the text is aligned at y.
	 * 
	 * @param font
	 * @param fontSize
	 * @param x
	 * @param y
	 * @param maxWidth
	 * @param text
	 * @throws IOException
	 */
	public void printTextTopEdge(PDFont font, float fontSize, float x, float y, float maxWidth, String text) throws IOException {

		float height = calculateTextHeight(font, fontSize);
		printText(font, fontSize, getPositionLeft(x), getPositionTop(y) - height, convert(maxWidth), text);
	}

	/**
	 * Prints the text at the given position. The bottom line of the text is aligned at y.
	 * 
	 * @param font
	 * @param fontSize
	 * @param x
	 * @param y
	 * @param maxWidth
	 * @param text
	 * @throws IOException
	 */
	public void printTextBottomEdge(PDFont font, float fontSize, float x, float y, float maxWidth, String text) throws IOException {

		printText(font, fontSize, getPositionLeft(x), getPositionTop(y), convert(maxWidth), text);
	}

	/**
	 * Prints the image at the given position. The top line of the image is aligned at y.
	 * 
	 * @param image
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	public void printImageTopEdge(PDImageXObject image, float x, float y, float width, float height) throws IOException {

		printImage(image, getPositionLeft(x), getPositionTop(y + height), convert(width), convert(height));
	}

	/**
	 * Prints the image at the given position. The bottom line of the image is aligned at y.
	 * 
	 * @param image
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	public void printImageBottomEdge(PDImageXObject image, float x, float y, float width, float height) throws IOException {

		contentStream.drawImage(image, getPositionLeft(x), getPositionTop(y), convert(width), convert(height));
	}

	/**
	 * Prints a line.
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @throws IOException
	 */
	public void printLine(float x1, float y1, float x2, float y2) throws IOException {

		contentStream.moveTo(getPositionLeft(x1), getPositionTop(y1));
		contentStream.lineTo(getPositionLeft(x2), getPositionTop(y2));
		contentStream.stroke();
	}

	/**
	 * Prints a rectangle.
	 * 
	 * @param color
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	public void printBackground(Color color, float x, float y, float width, float height) throws IOException {

		contentStream.setNonStrokingColor(color);
		contentStream.addRect(getPositionLeft(x), getPositionTop(y + height), convert(width), convert(height));
		contentStream.fill();
	}

	public void printTable(PDFTable pdfTable) throws IOException {

		if(isTableValid(pdfTable)) {
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
			y += printTableLine(pdfTable.getFontBold(), pdfTable.getFont(), pdfTable.getFontSize(), x, y, width, height, titleCells, Color.GRAY, true, true);
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
					y += printTableLine(pdfTable.getFontBold(), pdfTable.getFont(), pdfTable.getFontSize(), x, y, width, height, rowCells, backgroundColor, false, true);
				}
			}
			/*
			 * Print last line.
			 */
			printTableLines(x, y, width, height, pdfTable.getPositionY(), titleCells);
		} else {
			logger.warn("The PDFTable is invalid.");
		}
	}

	private boolean isTableValid(PDFTable pdfTable) {

		boolean isValid = pdfTable.isValid();
		if(isValid) {
			float widthTable = convert(pdfTable.getPositionX() + pdfTable.getWidth());
			float widthPage = getPageWidth();
			if(widthTable > widthPage) {
				logger.warn("The table width (" + widthTable + ")is larger then the page width (" + widthPage + ").");
			}
			/*
			 * +2 = Header + Offset
			 */
			float heightTable = convert((pdfTable.getRowStop() - pdfTable.getRowStart() + 2) * pdfTable.getColumnHeight() + pdfTable.getPositionY());
			float heightPage = getPageHeight();
			if(heightTable > heightPage) {
				logger.warn("The table height (" + heightTable + ")is larger then the page height (" + heightPage + ").");
			}
		}
		//
		return isValid;
	}

	/*
	 * Page Height (pt)
	 */
	private float getPageHeight() {

		PDRectangle rectangle = page.getMediaBox();
		return (landscape) ? rectangle.getWidth() : rectangle.getHeight();
	}

	/*
	 * Page Width (pt)
	 */
	private float getPageWidth() {

		PDRectangle rectangle = page.getMediaBox();
		return (landscape) ? rectangle.getHeight() : rectangle.getWidth();
	}

	/*
	 * Text height (pt)
	 */
	private float calculateTextHeight(PDFont font, float fontSize) {

		PDRectangle rectangle = font.getFontDescriptor().getFontBoundingBox();
		return (rectangle.getHeight() / 1000.0f * fontSize * 0.68f);
	}

	/*
	 * Text width (pt)
	 */
	private float calculateTextWidth(PDFont font, float fontSize, String text) throws IOException {

		return (font.getStringWidth(text) / 1000.0f * fontSize);
	}

	/*
	 * Left (pt) - 0,0 left bottom
	 */
	private float getPositionLeft(float x) {

		return convert(x);
	}

	/*
	 * Top (pt) - 0,0 left bottom
	 */
	private float getPositionTop(float y) {

		return getPageHeight() - convert(y);
	}

	/*
	 * Convert value to pt
	 */
	private float convert(float value) {

		return unitConverter.convert(value);
	}

	/*
	 * fontSize, x, y, maxWidth (pt)
	 * @param font
	 * @param fontSize
	 * @param x
	 * @param y
	 * @param maxWidth
	 * @param text
	 * @throws IOException
	 */
	private void printText(PDFont font, float fontSize, float x, float y, float maxWidth, String text) throws IOException {

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
			printText(x, y, printText);
		}
	}

	/*
	 * x, y (pt)
	 * @param x
	 * @param y
	 * @param text
	 * @throws IOException
	 */
	private void printText(float x, float y, String text) throws IOException {

		contentStream.beginText();
		contentStream.newLineAtOffset(x, y);
		contentStream.showText(text);
		contentStream.endText();
	}

	/*
	 * x, y, width, height (pt)
	 * @param image
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	private void printImage(PDImageXObject image, float x, float y, float width, float height) throws IOException {

		contentStream.drawImage(image, x, y, width, height);
	}

	private float printTableLine(PDFont fontBold, PDFont font, float fontSize, float x, float y, float width, float height, List<TableCell> cells, Color color, boolean bold, boolean drawBottomLine) throws IOException {

		/*
		 * Background
		 */
		if(color != null) {
			printBackground(color, x, y, width, height);
		}
		/*
		 * Print the text
		 */
		contentStream.setStrokingColor(Color.WHITE); // Background/Border
		contentStream.setNonStrokingColor(Color.BLACK); // Text
		float xLeft = x; // + 1mm? instead of whitespace " " + cell.getText()
		float yText = y + 1; // TODO + 1mm
		for(TableCell cell : cells) {
			printTextTopEdge((bold) ? fontBold : font, fontSize, xLeft, yText, cell.getWidth(), " " + cell.getText());
			xLeft += cell.getWidth();
		}
		/*
		 * Bottom Line
		 */
		if(drawBottomLine) {
			contentStream.setStrokingColor(Color.BLACK); // Background/Border
			contentStream.setNonStrokingColor(Color.BLACK); // Text
			float yBottom = y + height;
			printLine(x, yBottom, x + width, yBottom);
		}
		//
		return height;
	}

	private void printTableLines(float x, float y, float width, float height, float yStartPosition, List<TableCell> cells) throws IOException {

		contentStream.setStrokingColor(Color.BLACK); // Background/Border
		contentStream.setNonStrokingColor(Color.BLACK); // Text
		/*
		 * Top Line
		 */
		printLine(x, yStartPosition, x + width, yStartPosition);
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
				printLine(xLeft, yStart, xLeft, yStop);
			} else {
				printLine(xLeft, yStartExtraSpace, xLeft, yStop);
			}
			xLeft += cell.getWidth();
		}
		//
		printLine(x + width, yStart, x + width, yStop);
	}
}

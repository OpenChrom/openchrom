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

public class PageUtil {

	/*
	 * All public method values (x,y) are handled in the given unit
	 * of the page settings. Internally, pt is used and the page base bottom left (0,0).
	 */
	private static final Logger logger = Logger.getLogger(PageUtil.class);
	//
	private static final String TEXT_SHORTEN_MARKER = "...";
	/*
	 * Initialized in constructor
	 */
	private PDDocument document = null;
	private PDPage page = null;
	private PDPageContentStream contentStream = null;
	private IBaseConverter baseConverter = null;
	private IUnitConverter unitConverter = null;
	private boolean landscape;

	public PageUtil(PDDocument document, PageSettings pageSettings) throws IOException {
		this.document = document;
		page = new PDPage(pageSettings.getPDRectangle());
		this.document.addPage(page);
		contentStream = new PDPageContentStream(document, page);
		//
		baseConverter = ConverterFactory.getInstance(pageSettings.getBase());
		unitConverter = ConverterFactory.getInstance(pageSettings.getUnit());
		this.landscape = pageSettings.isLandscape();
		//
		if(this.landscape) {
			page.setRotation(-90);
			float x = 0;
			float y = pageSettings.getPDRectangle().getHeight();
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

	/**
	 * Print the text and returns the height in the given unit.
	 * 
	 * @param textElement
	 * @return float
	 * @throws IOException
	 */
	public float printText(TextElement textElement) throws IOException {

		float height = 0.0f;
		String text = textElement.getText();
		if(text.length() > 0) {
			switch(textElement.getTextOption()) {
				case NONE:
					height = printTextDefault(textElement);
					break;
				case SHORTEN:
					height = printTextShorten(textElement);
					break;
				case MULTI_LINE:
					height = printTextMultiLine(textElement);
					break;
				default:
					logger.warn("Option not supported: " + textElement.getTextOption());
					logger.warn("Option selected instead: " + TextOption.NONE);
					height = printTextDefault(textElement);
					break;
			}
		}
		//
		return unitConverter.convertFromPt(height);
	}

	/**
	 * Print the image.
	 * 
	 * @param imageElement
	 * @throws IOException
	 */
	public void printImage(ImageElement imageElement) throws IOException {

		PDImageXObject image = imageElement.getImage();
		float width = unitConverter.convertToPt(imageElement.getWidth());
		float height = unitConverter.convertToPt(imageElement.getHeight());
		float x = calculateX(imageElement, width, width);
		float y = calculateY(imageElement, height);
		//
		printImage(image, x, y, width, height);
	}

	/**
	 * Print a line.
	 * 
	 * @param lineElement
	 * @throws IOException
	 */
	public void printLine(LineElement lineElement) throws IOException {

		float width = unitConverter.convertToPt(lineElement.getWidth());
		float x0 = getPositionX(unitConverter.convertToPt(lineElement.getX()));
		float y0 = getPositionY(unitConverter.convertToPt(lineElement.getY()));
		float x1 = getPositionX(unitConverter.convertToPt(lineElement.getX1()));
		float y1 = getPositionY(unitConverter.convertToPt(lineElement.getY1()));
		//
		contentStream.setStrokingColor(lineElement.getColor());
		contentStream.setLineWidth(width);
		contentStream.moveTo(x0, y0);
		contentStream.lineTo(x1, y1);
		contentStream.stroke();
	}

	/**
	 * Print the box, e.g. rectangle of a certain color.
	 * 
	 * @param boxElement
	 * @throws IOException
	 */
	public void printBox(BoxElement boxElement) throws IOException {

		Color color = boxElement.getColor();
		float x = getPositionX(unitConverter.convertToPt(boxElement.getX()));
		float y = getPositionY(unitConverter.convertToPt(boxElement.getY() + boxElement.getHeight()));
		float width = unitConverter.convertToPt(boxElement.getWidth());
		float height = unitConverter.convertToPt(boxElement.getHeight());
		//
		contentStream.setNonStrokingColor(color);
		contentStream.addRect(x, y, width, height);
		contentStream.fill();
	}

	/**
	 * Print the table.
	 * 
	 * @param tableElement
	 * @throws IOException
	 */
	public void printTable(TableElement tableElement) throws IOException {

		if(isTableValid(tableElement)) {
			/*
			 * Settings
			 */
			PDFTable pdfTable = tableElement.getPdfTable();
			float y = unitConverter.convertToPt(tableElement.getY());
			/*
			 * Header
			 */
			for(List<TableCell> titleRow : pdfTable.getHeader()) {
				y += printTableLine(y, tableElement, titleRow, tableElement.getColorTitle());
			}
			/*
			 * Data
			 */
			if(pdfTable.getNumberDataRows() > 0) {
				for(int i = pdfTable.getStartIndex(); i <= pdfTable.getStopIndex(); i++) {
					List<TableCell> rowCells = pdfTable.getDataRow(i);
					Color color = (i % 2 == 0) ? null : tableElement.getColorData();
					y += printTableLine(y, tableElement, rowCells, color);
				}
			}
		} else {
			logger.warn("The PDFTable is invalid.");
			float x = tableElement.getX();
			float y = tableElement.getY();
			printText(new TextElement(x, y, Float.MAX_VALUE).setText("The table is invalid."));
		}
	}

	/**
	 * Returns false if the table contains no valid data and prints validation messages.
	 * 
	 * @param tableElement
	 * @return boolean
	 */
	private boolean isTableValid(TableElement tableElement) {

		PDFTable pdfTable = tableElement.getPdfTable();
		boolean isValid = pdfTable.isValid();
		//
		if(isValid) {
			/*
			 * Width
			 */
			float widthTable = unitConverter.convertToPt(tableElement.getX() + pdfTable.getWidth());
			float widthPage = getPageWidth();
			if(widthTable > widthPage) {
				logger.warn("The table width (" + widthTable + ")is larger then the page width (" + widthPage + ").");
			}
			/*
			 * Height
			 */
			float heightTable = unitConverter.convertToPt((pdfTable.getStopIndex() - pdfTable.getStartIndex() + 1 + pdfTable.getNumberHeaderRows()) * tableElement.getColumnHeight() + tableElement.getY());
			float heightPage = getPageHeight();
			if(heightTable > heightPage) {
				logger.warn("The table height (" + heightTable + ")is larger then the page height (" + heightPage + ").");
			}
		}
		//
		return isValid;
	}

	/**
	 * Page Height (pt)
	 * 
	 * @return float
	 */
	private float getPageHeight() {

		PDRectangle rectangle = page.getMediaBox();
		return (landscape) ? rectangle.getWidth() : rectangle.getHeight();
	}

	/**
	 * Page Width (pt)
	 * 
	 * @return float
	 */
	private float getPageWidth() {

		PDRectangle rectangle = page.getMediaBox();
		return (landscape) ? rectangle.getHeight() : rectangle.getWidth();
	}

	/**
	 * Text height (pt)
	 * 
	 * @param font
	 * @param fontSize
	 * @return float
	 */
	private float calculateTextHeight(PDFont font, float fontSize) {

		PDRectangle rectangle = font.getFontDescriptor().getFontBoundingBox();
		return (rectangle.getHeight() / 1000.0f * fontSize * 0.68f);
	}

	/**
	 * Text width (pt)
	 * 
	 * @param font
	 * @param fontSize
	 * @param text
	 * @return float
	 * @throws IOException
	 */
	private float calculateTextWidth(PDFont font, float fontSize, String text) throws IOException {

		return (font.getStringWidth(text) / 1000.0f * fontSize);
	}

	/**
	 * X (pt) - 0,0 left bottom
	 * 
	 * @param x
	 * @return float
	 */
	private float getPositionX(float x) {

		return baseConverter.getPositionX(getPageWidth(), x);
	}

	/**
	 * Y (pt) - 0,0 left bottom
	 * 
	 * @param y
	 * @return float
	 */
	private float getPositionY(float y) {

		return baseConverter.getPositionY(getPageHeight(), y);
	}

	/**
	 * x, y, width, height (pt)
	 * 
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

	/**
	 * Returns the height of the column (pt).
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param tableCells
	 * @param color
	 * @param textOffsetX
	 * @param textOffsetY
	 * @param lineWidth
	 * @return float
	 * @throws IOException
	 */
	private float printTableLine(float y, TableElement tableElement, List<TableCell> tableCells, Color color) throws IOException {

		/*
		 * _x = (_) means original unit.
		 */
		PDFTable pdfTable = tableElement.getPdfTable();
		float _x = tableElement.getX();
		float _y = unitConverter.convertFromPt(y);
		float _height = tableElement.getColumnHeight();
		float _lineWidth = tableElement.getLineWidth();
		/*
		 * Background
		 */
		if(color != null) {
			printBox(new BoxElement(_x, _y, pdfTable.getWidth(), _height).setColor(color));
		}
		//
		for(TableCell tableCell : tableCells) {
			/*
			 * Text
			 */
			TextElement textElement = new TextElement(_x + tableElement.getTextOffsetX(), _y + tableElement.getTextOffsetY(), tableCell.getWidth());
			textElement.setFont(tableCell.getFont());
			textElement.setFontSize(tableCell.getFontSize());
			textElement.setColor(tableCell.getColor());
			textElement.setText(tableCell.getText());
			//
			printText(textElement);
			/*
			 * Border(s)
			 */
			if(tableCell.isBorderSet()) {
				//
				float _xRight = _x + tableCell.getWidth();
				float _yHeight = _y + _height;
				//
				if(tableCell.isBorderSet(TableCell.BORDER_LEFT)) {
					printLine(new LineElement(_x, _y, _x, _yHeight).setWidth(_lineWidth));
				}
				//
				if(tableCell.isBorderSet(TableCell.BORDER_RIGHT)) {
					printLine(new LineElement(_xRight, _y, _xRight, _yHeight).setWidth(_lineWidth));
				}
				//
				if(tableCell.isBorderSet(TableCell.BORDER_TOP)) {
					printLine(new LineElement(_x, _y, _xRight, _y).setWidth(_lineWidth));
				}
				//
				if(tableCell.isBorderSet(TableCell.BORDER_BOTTOM)) {
					printLine(new LineElement(_x, _yHeight, _xRight, _yHeight).setWidth(_lineWidth));
				}
			}
			//
			_x += tableCell.getWidth();
		}
		//
		return unitConverter.convertToPt(_height);
	}

	/**
	 * X position (pt)
	 * 
	 * @param referenceElement
	 * @param elementWidth
	 * @param maxWidth
	 * @return float
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	private float calculateX(IReferenceElement referenceElement, float elementWidth, float maxWidth) throws IOException {

		float x;
		ReferenceX referenceX = referenceElement.getReferenceX();
		switch(referenceX) {
			case LEFT:
				x = getPositionX(unitConverter.convertToPt(referenceElement.getX()));
				break;
			case RIGHT:
				x = getPositionX(unitConverter.convertToPt(referenceElement.getX())) + maxWidth - elementWidth;
				break;
			default:
				logger.warn("Option not supported: " + referenceX);
				logger.warn("Option selected instead: " + ReferenceX.LEFT);
				x = getPositionX(unitConverter.convertToPt(referenceElement.getX()));
				break;
		}
		return x;
	}

	/**
	 * Y position (pt)
	 * 
	 * @param referenceElement
	 * @param elementHeight
	 * @return float
	 */
	@SuppressWarnings("rawtypes")
	private float calculateY(IReferenceElement referenceElement, float elementHeight) {

		float y;
		ReferenceY referenceY = referenceElement.getReferenceY();
		switch(referenceY) {
			case TOP:
				y = getPositionY(unitConverter.convertToPt(referenceElement.getY())) - elementHeight;
				break;
			case CENTER:
				y = getPositionY(unitConverter.convertToPt(referenceElement.getY())) - elementHeight / 2.0f;
				break;
			case BOTTOM:
				y = getPositionY(unitConverter.convertToPt(referenceElement.getY()));
				break;
			default:
				logger.warn("Option not supported: " + referenceY);
				logger.warn("Option selected instead: " + ReferenceY.BOTTOM);
				y = getPositionY(unitConverter.convertToPt(referenceElement.getY()));
				break;
		}
		return y;
	}

	/**
	 * Returns the height of the printed text (pt).
	 * 
	 * @param textElement
	 * @return
	 * @throws IOException
	 */
	private float printTextDefault(TextElement textElement) throws IOException {

		PDFont font = textElement.getFont();
		float fontSize = textElement.getFontSize();
		Color color = textElement.getColor();
		String text = textElement.getText();
		float maxWidth = unitConverter.convertToPt(textElement.getMaxWidth());
		float textWidth = calculateTextWidth(font, fontSize, text);
		float textHeight = calculateTextHeight(font, fontSize);
		float x = calculateX(textElement, textWidth, maxWidth);
		float y = calculateY(textElement, textHeight);
		//
		return printText(font, fontSize, color, x, y, text);
	}

	/**
	 * Returns the height of the printed text in (pt).
	 * 
	 * @param textElement
	 * @return float
	 * @throws IOException
	 */
	private float printTextShorten(TextElement textElement) throws IOException {

		PDFont font = textElement.getFont();
		float fontSize = textElement.getFontSize();
		Color color = textElement.getColor();
		String text = textElement.getText();
		float maxWidth = unitConverter.convertToPt(textElement.getMaxWidth());
		float textWidth = calculateTextWidth(font, fontSize, text);
		float textHeight = calculateTextHeight(font, fontSize);
		float x = unitConverter.convertToPt(textElement.getX());
		float y = calculateY(textElement, textHeight);
		//
		String shortenedText = text; // default
		ReferenceX referenceX = textElement.getReferenceX();
		switch(referenceX) {
			case LEFT:
				shortenedText = shortenStringLeft(text, textWidth, maxWidth);
				break;
			case RIGHT:
				shortenedText = shortenStringRight(text, textWidth, maxWidth);
				break;
			default:
				logger.warn("Option not supported: " + referenceX);
				logger.warn("Option selected instead: " + ReferenceX.LEFT);
				shortenedText = shortenStringLeft(text, textWidth, maxWidth);
				break;
		}
		//
		return printText(font, fontSize, color, x, y, shortenedText);
	}

	/**
	 * Returns the height of the printed text in (pt).
	 * 
	 * @param textElement
	 * @return float
	 * @throws IOException
	 */
	private float printTextMultiLine(TextElement textElement) throws IOException {

		PDFont font = textElement.getFont();
		float fontSize = textElement.getFontSize();
		Color color = textElement.getColor();
		String text = textElement.getText();
		float maxWidth = unitConverter.convertToPt(textElement.getMaxWidth());
		float textWidth = calculateTextWidth(font, fontSize, text);
		float minHeight = unitConverter.convertToPt(textElement.getMinHeight());
		float textHeight = calculateTextHeight(font, fontSize);
		float x = unitConverter.convertToPt(textElement.getX());
		float y = calculateY(textElement, textHeight);
		//
		List<String> textElements = cutStringMultiLine(text, textWidth, maxWidth);
		float offset = 0.0f;
		for(String part : textElements) {
			printText(font, fontSize, color, x, y - offset, part);
			offset += (textHeight > minHeight) ? textHeight : minHeight;
		}
		//
		return offset;
	}

	/**
	 * Returns the height of the printed text in (pt).
	 * 
	 * @param font
	 * @param fontSize
	 * @param color
	 * @param x
	 * @param y
	 * @param text
	 * @return
	 * @throws IOException
	 */
	private float printText(PDFont font, float fontSize, Color color, float x, float y, String text) throws IOException {

		contentStream.setFont(font, fontSize);
		contentStream.setNonStrokingColor(color);
		contentStream.beginText();
		contentStream.newLineAtOffset(x, y);
		contentStream.showText(text);
		contentStream.endText();
		//
		return calculateTextHeight(font, fontSize);
	}

	/**
	 * Shorten the text.
	 * textWidth, availableWidth (pt)
	 * Lorem ipsum dolor sit amet, consetetur sadipscing...
	 * 
	 * @param text
	 * @param textWidth
	 * @param maxWidth
	 * @return String
	 */
	private String shortenStringLeft(String text, float textWidth, float maxWidth) {

		int endIndex = (int)(text.length() / textWidth * maxWidth) - 3; // -3 (TEXT_CUT)
		if(textWidth > maxWidth && endIndex > 0) {
			return text.substring(0, endIndex) + TEXT_SHORTEN_MARKER;
		} else {
			return text;
		}
	}

	/**
	 * Shorten the text.
	 * textWidth, availableWidth (pt)
	 * ...kasd gubergren, no sea takimata sanctus est.
	 * 
	 * @param text
	 * @param textWidth
	 * @param maxWidth
	 * @return String
	 */
	private String shortenStringRight(String text, float textWidth, float maxWidth) {

		int length = text.length();
		int startIndex = length - (int)((length / textWidth * maxWidth));
		if(textWidth > maxWidth && startIndex > 0) {
			return TEXT_SHORTEN_MARKER + text.substring(startIndex, length);
		} else {
			return text;
		}
	}

	/**
	 * Cut the text.
	 * 
	 * @param text
	 * @param textWidth
	 * @param maxWidth
	 * @return List<String>
	 */
	private List<String> cutStringMultiLine(String text, float textWidth, float maxWidth) {

		List<String> textElements = new ArrayList<>();
		/*
		 * This could be more dynamically.
		 */
		int textLength = text.length();
		int partLength = (int)(textLength / textWidth * maxWidth);
		int parts = textLength / partLength + 1;
		for(int i = 0; i < parts; i++) {
			int startIndex = i * partLength;
			int stopIndex = startIndex + partLength;
			textElements.add(text.substring(startIndex, (stopIndex > textLength) ? textLength : stopIndex));
		}
		//
		return textElements;
	}
}

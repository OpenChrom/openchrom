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

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import junit.framework.TestCase;

public class PDFTest extends TestCase {

	private static final String TEXT = "the open source alternative for chromatography/spectrometry";
	private static final String LINE_FIRST = "First Line";
	private static final String LINE_CONTENT = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est.";
	private static final String LINE_LAST = "Last Line";
	private static final String FOOTER = "Page 1/20";
	//
	private DecimalFormat decimalFormat = new DecimalFormat("0.0000");

	public void test1() throws IOException {

		PDDocument document = null;
		try {
			document = new PDDocument();
			printHeaderData(document);
			document.save("/home/pwenig/Schreibtisch/Test.pdf");
		} catch(IOException e) {
			System.out.println(e);
		} finally {
			if(document != null) {
				document.close();
			}
		}
	}

	private void printHeaderData(PDDocument document) throws IOException {

		PDFont font = PDType1Font.HELVETICA;
		printPageLeft(document, font);
		printPageLeftShorten(document, font);
		printPageRight(document, font);
		printPageRightShorten(document, font);
		printPageMultiLine(document, font);
		printPageRotate0(document, font);
		printPageRotate180(document, font);
		printPageLogoPortrait(document, font);
		printPageLogoLandscape(document, font);
		printPageTablePortrait(document, font);
		printPageTableLandscape(document, font);
		printPageTableExtendedText(document, font);
		printPageDefault(document, font);
	}

	private PDPage printPageLeft(PDDocument document, PDFont font) throws IOException {

		PageUtil pageUtil = new PageUtil(document, new PageSettings(PDRectangle.A4, Base.TOP_LEFT, Unit.MM, false));
		//
		pageUtil.printText(new TextElement(10, 0, 190).setText(LINE_FIRST).setReferenceX(ReferenceX.RIGHT));
		for(int i = 1; i <= 28; i++) {
			pageUtil.printText(new TextElement(10, i * 10, 190).setText(LINE_CONTENT));
		}
		pageUtil.printText(new TextElement(10, 297, 190).setText(LINE_LAST).setReferenceY(ReferenceY.BOTTOM));
		//
		pageUtil.printLine(new LineElement(10, 10, 10, 287).setWidth(0.2f)); // left
		pageUtil.printLine(new LineElement(10, 10, 200, 10).setWidth(0.2f)); // top
		pageUtil.printLine(new LineElement(200, 10, 200, 287).setWidth(0.2f)); // right
		pageUtil.printLine(new LineElement(10, 287, 200, 287).setWidth(0.2f)); // bottom
		//
		pageUtil.close();
		return pageUtil.getPage();
	}

	private PDPage printPageLeftShorten(PDDocument document, PDFont font) throws IOException {

		PageUtil pageUtil = new PageUtil(document, new PageSettings(PDRectangle.A4, Base.TOP_LEFT, Unit.MM, false));
		//
		pageUtil.printText(new TextElement(10, 0, 190).setText(LINE_FIRST).setReferenceX(ReferenceX.RIGHT));
		for(int i = 1; i <= 28; i++) {
			pageUtil.printText(new TextElement(10, i * 10, 190).setText(LINE_CONTENT).setTextOption(TextOption.SHORTEN));
		}
		pageUtil.printText(new TextElement(10, 297, 190).setText(LINE_LAST).setReferenceY(ReferenceY.BOTTOM));
		//
		pageUtil.printLine(new LineElement(10, 10, 10, 287).setWidth(0.2f)); // left
		pageUtil.printLine(new LineElement(10, 10, 200, 10).setWidth(0.2f)); // top
		pageUtil.printLine(new LineElement(200, 10, 200, 287).setWidth(0.2f)); // right
		pageUtil.printLine(new LineElement(10, 287, 200, 287).setWidth(0.2f)); // bottom
		//
		pageUtil.close();
		return pageUtil.getPage();
	}

	private PDPage printPageRight(PDDocument document, PDFont font) throws IOException {

		PageUtil pageUtil = new PageUtil(document, new PageSettings(PDRectangle.A4, Base.TOP_LEFT, Unit.MM, true));
		//
		pageUtil.printText(new TextElement(10, 0, 277).setText(LINE_FIRST));
		for(int i = 1; i <= 17; i++) {
			pageUtil.printText(new TextElement(10, i * 10, 277).setText(LINE_CONTENT).setReferenceX(ReferenceX.RIGHT));
		}
		pageUtil.printText(new TextElement(10, 210, 277).setText(LINE_LAST).setReferenceY(ReferenceY.BOTTOM).setReferenceX(ReferenceX.RIGHT));
		//
		pageUtil.printLine(new LineElement(10, 10, 10, 200).setWidth(0.2f)); // left
		pageUtil.printLine(new LineElement(10, 10, 287, 10).setWidth(0.2f)); // top
		pageUtil.printLine(new LineElement(287, 10, 287, 200).setWidth(0.2f)); // right
		pageUtil.printLine(new LineElement(10, 200, 287, 200).setWidth(0.2f)); // bottom
		//
		pageUtil.close();
		return pageUtil.getPage();
	}

	private PDPage printPageRightShorten(PDDocument document, PDFont font) throws IOException {

		PageUtil pageUtil = new PageUtil(document, new PageSettings(PDRectangle.A4, Base.TOP_LEFT, Unit.MM, true));
		//
		pageUtil.printText(new TextElement(10, 0, 277).setText(LINE_FIRST));
		for(int i = 1; i <= 17; i++) {
			pageUtil.printText(new TextElement(10, i * 10, 277).setText(LINE_CONTENT).setReferenceX(ReferenceX.RIGHT).setTextOption(TextOption.SHORTEN));
		}
		pageUtil.printText(new TextElement(10, 210, 277).setText(LINE_LAST).setReferenceY(ReferenceY.BOTTOM).setReferenceX(ReferenceX.RIGHT));
		//
		pageUtil.printLine(new LineElement(10, 10, 10, 200).setWidth(0.2f)); // left
		pageUtil.printLine(new LineElement(10, 10, 287, 10).setWidth(0.2f)); // top
		pageUtil.printLine(new LineElement(287, 10, 287, 200).setWidth(0.2f)); // right
		pageUtil.printLine(new LineElement(10, 200, 287, 200).setWidth(0.2f)); // bottom
		//
		pageUtil.close();
		return pageUtil.getPage();
	}

	private PDPage printPageMultiLine(PDDocument document, PDFont font) throws IOException {

		PageUtil pageUtil = new PageUtil(document, new PageSettings(PDRectangle.A4, Base.TOP_LEFT, Unit.MM, false));
		//
		pageUtil.printText(new TextElement(10, 0, 190).setText(LINE_FIRST).setReferenceX(ReferenceX.RIGHT));
		pageUtil.printText(new TextElement(10, 10, 190).setText(LINE_CONTENT).setTextOption(TextOption.MULTI_LINE));
		pageUtil.printText(new TextElement(10, 100, 190).setText(LINE_CONTENT).setTextOption(TextOption.MULTI_LINE).setMinHeight(5));
		pageUtil.printText(new TextElement(10, 150, 190).setText(LINE_CONTENT).setTextOption(TextOption.MULTI_LINE).setMinHeight(15));
		pageUtil.printText(new TextElement(10, 297, 190).setText(LINE_LAST).setReferenceY(ReferenceY.BOTTOM));
		//
		pageUtil.printLine(new LineElement(10, 10, 10, 287).setWidth(0.2f)); // left
		pageUtil.printLine(new LineElement(10, 10, 200, 10).setWidth(0.2f)); // top
		pageUtil.printLine(new LineElement(200, 10, 200, 287).setWidth(0.2f)); // right
		pageUtil.printLine(new LineElement(10, 287, 200, 287).setWidth(0.2f)); // bottom
		//
		pageUtil.close();
		return pageUtil.getPage();
	}

	private PDPage printPageRotate0(PDDocument document, PDFont font) throws IOException {

		PDPage page = printPageRight(document, font);
		page.setRotation(0);
		return page;
	}

	private PDPage printPageRotate180(PDDocument document, PDFont font) throws IOException {

		PDPage page = printPageRight(document, font);
		page.setRotation(-180);
		return page;
	}

	private PDPage printPageLogoPortrait(PDDocument document, PDFont font) throws IOException {

		PageUtil pageUtil = new PageUtil(document, new PageSettings(PDRectangle.A4, Base.TOP_LEFT, Unit.MM, false));
		//
		pageUtil.printImage(new ImageElement(10, 10).setImage(getImage(document)).setWidth(63.5f).setHeight(8.05f));
		pageUtil.printText(new TextElement(10, 20, 190).setText(TEXT));
		//
		pageUtil.printImage(new ImageElement(10, 148.5f).setImage(getImage(document)).setWidth(63.5f).setHeight(8.05f).setReferenceY(ReferenceY.CENTER));
		pageUtil.printText(new TextElement(83.5f, 148.5f, 116.5f).setText(TEXT).setReferenceY(ReferenceY.CENTER));
		//
		pageUtil.printText(new TextElement(10, 277, 190).setText(TEXT).setReferenceY(ReferenceY.BOTTOM));
		pageUtil.printImage(new ImageElement(10, 287).setImage(getImage(document)).setWidth(63.5f).setHeight(8.05f).setReferenceY(ReferenceY.BOTTOM));
		pageUtil.printText(new TextElement(74, 287, 190).setText(FOOTER).setReferenceY(ReferenceY.BOTTOM));
		//
		pageUtil.close();
		return pageUtil.getPage();
	}

	private PDPage printPageLogoLandscape(PDDocument document, PDFont font) throws IOException {

		PageUtil pageUtil = new PageUtil(document, new PageSettings(PDRectangle.A4, Base.TOP_LEFT, Unit.MM, true));
		//
		pageUtil.printImage(new ImageElement(10, 10).setImage(getImage(document)).setWidth(63.5f).setHeight(8.05f));
		pageUtil.printText(new TextElement(10, 20, 277).setText(TEXT));
		//
		pageUtil.printImage(new ImageElement(10, 105).setImage(getImage(document)).setWidth(63.5f).setHeight(8.05f).setReferenceY(ReferenceY.CENTER));
		pageUtil.printText(new TextElement(83.5f, 105, 203.5f).setText(TEXT).setReferenceY(ReferenceY.CENTER));
		//
		pageUtil.printText(new TextElement(10, 190, 277).setText(TEXT).setReferenceY(ReferenceY.BOTTOM));
		pageUtil.printImage(new ImageElement(10, 200).setImage(getImage(document)).setWidth(63.5f).setHeight(8.05f).setReferenceY(ReferenceY.BOTTOM));
		pageUtil.printText(new TextElement(74, 200, 277).setText(FOOTER).setReferenceY(ReferenceY.BOTTOM));
		//
		pageUtil.close();
		return pageUtil.getPage();
	}

	private PDPage printPageTablePortrait(PDDocument document, PDFont font) throws IOException {

		PageUtil pageUtil = new PageUtil(document, new PageSettings(PDRectangle.A4, Base.TOP_LEFT, Unit.MM, false));
		//
		TableElement tableElement = new TableElement(10, 10, 5.5f);
		tableElement.setTextOffsetX(1.0f);
		tableElement.setTextOffsetY(1.0f);
		tableElement.setLineWidth(0.2f);
		PDTable pdTable = new PDTable();
		tableElement.setpdTable(pdTable);
		/*
		 * Header
		 */
		pdTable.addColumn(new CellElement("", 50.0f, CellElement.BORDER_LEFT | CellElement.BORDER_TOP));
		pdTable.addColumn(new CellElement("Test", 45.0f, CellElement.BORDER_LEFT | CellElement.BORDER_TOP));
		pdTable.addColumn(new CellElement("STD", 25.0f, CellElement.BORDER_LEFT | CellElement.BORDER_TOP));
		pdTable.addColumn(new CellElement("Content", 45.0f, CellElement.BORDER_LEFT | CellElement.BORDER_TOP));
		pdTable.addColumn(new CellElement("Result", 25.0f, CellElement.BORDER_LEFT | CellElement.BORDER_RIGHT | CellElement.BORDER_TOP));
		pdTable.nextHeaderRow();
		pdTable.addColumn(new CellElement("Analyte", 50.0f, CellElement.BORDER_LEFT | CellElement.BORDER_BOTTOM));
		pdTable.addColumn(new CellElement("Total", 22.5f, CellElement.BORDER_LEFT | CellElement.BORDER_BOTTOM));
		pdTable.addColumn(new CellElement("Top", 22.5f, CellElement.BORDER_LEFT | CellElement.BORDER_BOTTOM));
		pdTable.addColumn(new CellElement("AA", 25.0f, CellElement.BORDER_LEFT | CellElement.BORDER_BOTTOM));
		pdTable.addColumn(new CellElement("-B", 22.5f, CellElement.BORDER_LEFT | CellElement.BORDER_BOTTOM));
		pdTable.addColumn(new CellElement("+B", 22.5f, CellElement.BORDER_LEFT | CellElement.BORDER_BOTTOM));
		pdTable.addColumn(new CellElement("[mg/L]", 25, CellElement.BORDER_LEFT | CellElement.BORDER_RIGHT | CellElement.BORDER_BOTTOM));
		/*
		 * Data
		 */
		for(int i = 0; i < 30; i++) {
			List<String> row = new ArrayList<>();
			row.add("A");
			row.add(decimalFormat.format(Math.random()));
			row.add(decimalFormat.format(Math.random()));
			row.add(decimalFormat.format(Math.random()));
			row.add(decimalFormat.format(Math.random()));
			row.add(decimalFormat.format(Math.random()));
			row.add(decimalFormat.format(Math.random()));
			pdTable.addDataRow(row);
		}
		//
		pageUtil.printTable(tableElement);
		pageUtil.close();
		return pageUtil.getPage();
	}

	private PDPage printPageTableLandscape(PDDocument document, PDFont font) throws IOException {

		PageUtil pageUtil = new PageUtil(document, new PageSettings(PDRectangle.A4, Base.TOP_LEFT, Unit.MM, true));
		//
		TableElement tableElement = new TableElement(10, 10, 5.5f);
		tableElement.setTextOffsetX(1.0f);
		tableElement.setTextOffsetY(1.0f);
		tableElement.setLineWidth(0.2f);
		PDTable pdTable = new PDTable();
		tableElement.setpdTable(pdTable);
		/*
		 * Header
		 */
		pdTable.addColumn("A", 77);
		pdTable.addColumn("B", 150);
		pdTable.addColumn("C", 50);
		/*
		 * Data
		 */
		for(int i = 0; i < 30; i++) {
			List<String> row = new ArrayList<>();
			row.add(decimalFormat.format(Math.random()));
			row.add(decimalFormat.format(Math.random()));
			row.add(decimalFormat.format(Math.random()));
			pdTable.addDataRow(row);
		}
		//
		pageUtil.printTable(tableElement);
		pageUtil.close();
		return pageUtil.getPage();
	}

	private PDPage printPageTableExtendedText(PDDocument document, PDFont font) throws IOException {

		PageUtil pageUtil = new PageUtil(document, new PageSettings(PDRectangle.A4, Base.TOP_LEFT, Unit.MM, true));
		//
		TableElement tableElement = new TableElement(10, 10, 5.5f);
		tableElement.setTextOffsetX(1.0f);
		tableElement.setTextOffsetY(1.0f);
		tableElement.setLineWidth(0.2f);
		PDTable pdTable = new PDTable();
		tableElement.setpdTable(pdTable);
		/*
		 * Header
		 */
		pdTable.addColumn(new CellElement("A - Lorem ipsum dolor sit amet,", 77, CellElement.BORDER_ALL).setTextOption(TextOption.NONE));
		pdTable.addColumn(new CellElement("B - Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.", 150, CellElement.BORDER_ALL).setTextOption(TextOption.MULTI_LINE));
		pdTable.addColumn(new CellElement("C - Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.", 50, CellElement.BORDER_ALL).setTextOption(TextOption.SHORTEN));
		/*
		 * Data
		 */
		for(int i = 0; i < 30; i++) {
			List<String> row = new ArrayList<>();
			row.add(decimalFormat.format(Math.random()));
			row.add(decimalFormat.format(Math.random()));
			row.add(decimalFormat.format(Math.random()));
			pdTable.addDataRow(row);
		}
		//
		pageUtil.printTable(tableElement);
		pageUtil.close();
		return pageUtil.getPage();
	}

	private PDPage printPageDefault(PDDocument document, PDFont font) throws IOException {

		PageUtil pageUtil = new PageUtil(document, new PageSettings(PDRectangle.A4, Base.BOTTOM_LEFT, Unit.PT, false));
		//
		pageUtil.printText(new TextElement(28.3465f, 28.3465f, 538.5835f).setText(LINE_CONTENT).setReferenceY(ReferenceY.BOTTOM).setTextOption(TextOption.SHORTEN));
		//
		pageUtil.close();
		return pageUtil.getPage();
	}

	private PDImageXObject getImage(PDDocument document) throws IOException {

		return JPEGFactory.createFromStream(document, PDFTest.class.getResourceAsStream("openchromlogo.jpg"));
	}
}

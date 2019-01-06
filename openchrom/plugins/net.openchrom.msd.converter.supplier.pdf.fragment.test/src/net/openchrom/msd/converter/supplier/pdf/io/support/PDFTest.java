/*******************************************************************************
 * Copyright (c) 2018 pwenig.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * pwenig - initial API and implementation
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

		// printPage(document, PDType1Font.COURIER);
		// printPage(document, PDType1Font.COURIER_BOLD);
		// printPage(document, PDType1Font.COURIER_BOLD_OBLIQUE);
		// printPage(document, PDType1Font.COURIER_OBLIQUE);
		// printPage(document, PDType1Font.HELVETICA_BOLD);
		// printPage(document, PDType1Font.HELVETICA_BOLD_OBLIQUE);
		// printPage(document, PDType1Font.HELVETICA_OBLIQUE);
		// printPage(document, PDType1Font.TIMES_BOLD);
		// printPage(document, PDType1Font.TIMES_BOLD_ITALIC);
		// printPage(document, PDType1Font.TIMES_ITALIC);
		// printPage(document, PDType1Font.TIMES_ROMAN);
		//
		printPage1(document, PDType1Font.HELVETICA);
		printPage2(document, PDType1Font.HELVETICA);
		printPage3(document, PDType1Font.HELVETICA);
		printPage4(document, PDType1Font.HELVETICA);
		printPage5(document, PDType1Font.HELVETICA);
		printPage6(document, PDType1Font.HELVETICA);
		printPage7(document, PDType1Font.HELVETICA);
		printPage8(document, PDType1Font.HELVETICA);
	}

	private PDPage printPage1(PDDocument document, PDFont font) throws IOException {

		PageUtil pageUtil = new PageUtil(document, PDRectangle.A4);
		//
		String text0 = "Erste Zeile";
		String text1 = "Chromatogram Text und so und so viel text wie da auch nur stehen kann und ich weiß nicht doch das könnte etwas anders sein, wenn man bedenkt.";
		String text2 = "Letzte Zeile";
		//
		TextElement textElement = new TextElement().setX(10).setMaxWidth(190);
		//
		pageUtil.printText(textElement.setY(0).setText(text0));
		for(int i = 1; i <= 28; i++) {
			pageUtil.printText(textElement.setY(i * 10).setText(text1));
		}
		pageUtil.printText(textElement.setY(297).setText(text2).setAlignVertical(AlignVertical.BOTTOM));
		//
		pageUtil.printLine(10, 10, 10, 287); // left
		pageUtil.printLine(10, 10, 200, 10); // top
		pageUtil.printLine(200, 10, 200, 287); // right
		pageUtil.printLine(10, 287, 200, 287); // bottom
		//
		pageUtil.close();
		return pageUtil.getPage();
	}

	private PDPage printPage2(PDDocument document, PDFont font) throws IOException {

		PageUtil pageUtil = new PageUtil(document, PDRectangle.A4, true);
		//
		String text0 = "Erste Zeile";
		String text1 = "Chromatogram Text und so und so viel text wie da auch nur stehen kann und ich weiß nicht doch das könnte etwas anders sein, wenn man bedenkt.";
		String text2 = "Letzte Zeile";
		int left = 10;
		float fontSize = 12;
		float maxWidth = 277;
		pageUtil.printTextTopEdge(font, fontSize, left, 0, maxWidth, text0);
		for(int i = 1; i <= 17; i++) {
			pageUtil.printTextTopEdge(font, fontSize, left, i * 10, maxWidth, text1);
		}
		pageUtil.printTextBottomEdge(font, fontSize, left, 210, maxWidth, text2);
		//
		pageUtil.printLine(10, 10, 10, 200); // left
		pageUtil.printLine(10, 10, 287, 10); // top
		pageUtil.printLine(287, 10, 287, 200); // right
		pageUtil.printLine(10, 200, 287, 200); // bottom
		//
		pageUtil.close();
		return pageUtil.getPage();
	}

	private PDPage printPage3(PDDocument document, PDFont font) throws IOException {

		PDPage page = printPage2(document, font);
		page.setRotation(0);
		return page;
	}

	private PDPage printPage4(PDDocument document, PDFont font) throws IOException {

		PDPage page = printPage2(document, font);
		page.setRotation(-180);
		return page;
	}

	private PDPage printPage5(PDDocument document, PDFont font) throws IOException {

		PageUtil pageUtil = new PageUtil(document, PDRectangle.A4);
		//
		PDImageXObject image = JPEGFactory.createFromStream(document, PDFTest.class.getResourceAsStream("openchromlogo.jpg"));
		//
		pageUtil.printImageTopEdge(image, 10, 10, 63.5f, 8.05f);
		pageUtil.printTextTopEdge(font, 12, 10, 20, 190, "OpenChrom - the open source alternative for chromatography/spectrometry");
		//
		pageUtil.printTextBottomEdge(font, 12, 10, 277, 190, "OpenChrom - the open source alternative for chromatography/spectrometry");
		pageUtil.printImageBottomEdge(image, 10, 287, 63.5f, 8.05f);
		//
		pageUtil.printTextBottomEdge(font, 12, 74, 287, 190, "Page 1/20");
		//
		pageUtil.close();
		return pageUtil.getPage();
	}

	private PDPage printPage6(PDDocument document, PDFont font) throws IOException {

		PageUtil pageUtil = new PageUtil(document, PDRectangle.A4, true);
		//
		PDImageXObject image = JPEGFactory.createFromStream(document, PDFTest.class.getResourceAsStream("openchromlogo.jpg"));
		//
		pageUtil.printImageTopEdge(image, 10, 10, 63.5f, 8.05f);
		pageUtil.printTextTopEdge(font, 12, 10, 20, 277, "OpenChrom - the open source alternative for chromatography/spectrometry");
		//
		pageUtil.printTextBottomEdge(font, 12, 10, 190, 277, "OpenChrom - the open source alternative for chromatography/spectrometry");
		pageUtil.printImageBottomEdge(image, 10, 200, 63.5f, 8.05f);
		//
		pageUtil.printTextBottomEdge(font, 12, 74, 200, 277, "Page 1/20");
		//
		pageUtil.close();
		return pageUtil.getPage();
	}

	private PDPage printPage7(PDDocument document, PDFont font) throws IOException {

		PageUtil pageUtil = new PageUtil(document, PDRectangle.A4);
		//
		PDFTable pdfTable = new PDFTable();
		pdfTable.setPositionX(10);
		pdfTable.setPositionY(10);
		/*
		 * Header
		 */
		pdfTable.addColumn("A", 50);
		pdfTable.addColumn("B", 100);
		pdfTable.addColumn("C", 40);
		/*
		 * Data
		 */
		for(int i = 0; i < 30; i++) {
			List<String> row = new ArrayList<>();
			row.add(decimalFormat.format(Math.random()));
			row.add(decimalFormat.format(Math.random()));
			row.add(decimalFormat.format(Math.random()));
			pdfTable.addRow(row);
		}
		pageUtil.printTable(pdfTable);
		//
		pageUtil.close();
		return pageUtil.getPage();
	}

	private PDPage printPage8(PDDocument document, PDFont font) throws IOException {

		PageUtil pageUtil = new PageUtil(document, PDRectangle.A4, true);
		//
		PDFTable pdfTable = new PDFTable();
		pdfTable.setPositionX(10);
		pdfTable.setPositionY(10);
		/*
		 * Header
		 */
		pdfTable.addColumn("A", 77);
		pdfTable.addColumn("B", 150);
		pdfTable.addColumn("C", 50);
		/*
		 * Data
		 */
		for(int i = 0; i < 30; i++) {
			List<String> row = new ArrayList<>();
			row.add(decimalFormat.format(Math.random()));
			row.add(decimalFormat.format(Math.random()));
			row.add(decimalFormat.format(Math.random()));
			pdfTable.addRow(row);
		}
		pageUtil.printTable(pdfTable);
		//
		pageUtil.close();
		return pageUtil.getPage();
	}
}

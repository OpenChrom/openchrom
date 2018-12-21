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
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;

import junit.framework.TestCase;

public class PDFTest extends TestCase {

	private DecimalFormat decimalFormat = new DecimalFormat("0.0000");
	private PDFUtil pdfUtil = new PDFUtil(PDRectangle.A4, UnitConverterFactory.getInstance(Unit.MM));

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
		printPage0(document, PDType1Font.HELVETICA);
		// printPage1(document, PDType1Font.HELVETICA);
		// printPage2(document, PDType1Font.HELVETICA);
		// printPage3(document, PDType1Font.HELVETICA);
		// printPage4(document, PDType1Font.HELVETICA);
	}

	private void printPage0(PDDocument document, PDFont font) throws IOException {

		PDPage pdPage = new PDPage(PDRectangle.A4);
		document.addPage(pdPage);
		PDPageContentStream contentStream = new PDPageContentStream(document, pdPage);
		//
		pdPage.setRotation(-90);
		contentStream.transform(Matrix.getTranslateInstance(-87 * 2.83465f, 297 * 2.83465f));// 297 - 210, 297
		//
		String text0 = "Erste Zeile";
		String text1 = "Chromatogram Text und so und so viel text wie da auch nur stehen kann und ich weiß nicht doch das könnte etwas anders sein, wenn man bedenkt.";
		String text2 = "Letzte Zeile";
		//
		pdfUtil.printTextTopEdge(contentStream, font, 12, 0, 0, 297, text0);
		for(int i = 1; i <= 17; i++) {
			pdfUtil.printTextTopEdge(contentStream, font, 12, 0, i * 10, 297, text1);
		}
		pdfUtil.printTextTopEdge(contentStream, font, 12, 0, 180, 297, text2);
		//
		contentStream.close();
	}

	private void printPage1(PDDocument document, PDFont font) throws IOException {

		PDPage pdPage = new PDPage(PDRectangle.A4);
		document.addPage(pdPage);
		PDPageContentStream contentStream = new PDPageContentStream(document, pdPage);
		//
		String text = "Chromatogram Text und so und so viel text wie da auch nur stehen kann und ich weiß nicht doch das könnte etwas anders sein, wenn man bedenkt.";
		pdfUtil.printTextTopEdge(contentStream, font, 12, 10, 10, 190, text);
		pdfUtil.printTextTopEdge(contentStream, font, 12, 10, 20, 190, text);
		pdfUtil.printLine(contentStream, 10, 10, 10, 287);
		pdfUtil.printLine(contentStream, 10, 10, 200, 10);
		//
		contentStream.close();
	}

	private void printPage2(PDDocument document, PDFont font) throws IOException {

		PDPage pdPage = new PDPage(PDRectangle.A4);
		document.addPage(pdPage);
		pdPage.setRotation(90);
		PDPageContentStream contentStream = new PDPageContentStream(document, pdPage);
		//
		String text = "Chromatogram Text und so und so viel text wie da auch nur stehen kann und ich weiß nicht doch das könnte etwas anders sein, wenn man bedenkt.";
		pdfUtil.printTextTopEdge(contentStream, font, 12, 10, 10, 190, text);
		pdfUtil.printTextTopEdge(contentStream, font, 12, 10, 20, 190, text);
		pdfUtil.printLine(contentStream, 10, 10, 10, 287);
		pdfUtil.printLine(contentStream, 10, 10, 200, 10);
		//
		contentStream.close();
	}

	private void printPage3(PDDocument document, PDFont font) throws IOException {

		PDPage pdPage = new PDPage(PDRectangle.A4);
		document.addPage(pdPage);
		PDPageContentStream contentStream = new PDPageContentStream(document, pdPage);
		//
		PDFTable pdfTable = new PDFTable();
		pdfTable.setPositionX(10);
		pdfTable.setPositionY(10);
		pdfTable.addColumn("A", 50);
		pdfTable.addColumn("B", 100);
		pdfTable.addColumn("C", 40);
		for(int i = 0; i < 30; i++) {
			List<String> row = new ArrayList<>();
			row.add(decimalFormat.format(Math.random()));
			row.add(decimalFormat.format(Math.random()));
			row.add(decimalFormat.format(Math.random()));
			pdfTable.addRow(row);
		}
		pdfUtil.printTable(contentStream, pdfTable);
		//
		contentStream.close();
	}

	private void printPage4(PDDocument document, PDFont font) throws IOException {

		PDPage pdPage = new PDPage(PDRectangle.A4);
		document.addPage(pdPage);
		PDPageContentStream contentStream = new PDPageContentStream(document, pdPage);
		/*
		 * Logo
		 */
		PDImageXObject image = JPEGFactory.createFromStream(document, PDFTest.class.getResourceAsStream("openchromlogo.jpg"));
		pdfUtil.printImage(contentStream, image, 10, 10, 63.5f, 12.5f);
		pdfUtil.printTextTopEdge(contentStream, font, 12, 10, 23, 190, "OpenChrom - the open source alternative for chromatography/spectrometry");
		/*
		 * Footer
		 */
		StringBuilder builder = new StringBuilder();
		builder.append("Page");
		builder.append(" ");
		builder.append(1);
		builder.append(" / ");
		builder.append(20);
		//
		pdfUtil.printTextBottomEdge(contentStream, font, 12, 10, 287, 190, builder.toString());
		//
		contentStream.close();
	}
}

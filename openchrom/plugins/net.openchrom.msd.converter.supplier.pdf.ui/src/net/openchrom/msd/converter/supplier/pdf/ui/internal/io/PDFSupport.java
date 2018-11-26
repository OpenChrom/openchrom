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
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.chemclipse.support.ui.workbench.DisplayUtils;
import org.eclipse.core.runtime.IProgressMonitor;

public class PDFSupport {

	private static final Logger logger = Logger.getLogger(PDFSupport.class);
	/*
	 * A4 PORTRAIT = 595.0 x 842.0 pt
	 * A4 LANDSCAPE = 842.0 x 595.0 pt
	 * 0,0 is lower left!
	 */
	//
	private static final float L_1_MM = 2.8346f;
	//
	private static final float A4_WIDTH = 210 * L_1_MM;
	private static final float BORDER = 10 * L_1_MM;
	//
	private static final float IMAGE_WIDTH = A4_WIDTH - 2 * BORDER;
	private static final float IMAGE_HEIGHT = 270 * L_1_MM; // 297 - 17 (12+5) - 10
	//
	private static final float L_0_5_MM = 0.5f * L_1_MM;
	private static final float L_1_4_MM = 1.4f * L_1_MM;
	private static final float L_15_MM = 15 * L_1_MM;
	private static final float L_20_MM = 20 * L_1_MM;
	private static final float L_200_MM = 200 * L_1_MM;
	private static final float L_297_MM = 297 * L_1_MM;
	//
	private PDFont font = PDType1Font.HELVETICA;
	private PDFont fontBold = PDType1Font.HELVETICA_BOLD;
	//
	private DecimalFormat decimalFormat = ValueFormat.getDecimalFormatEnglish();

	@SuppressWarnings("rawtypes")
	public void createPDF(File file, IChromatogram chromatogram, IProgressMonitor monitor) throws IOException {

		PDDocument document = null;
		try {
			document = new PDDocument();
			createHeaderTable(document, chromatogram, 1, 1, monitor);
			List<PDImageXObject> chromatogramImages = createImages(chromatogram, document, 8);
			int pages = chromatogramImages.size();
			int page = 1;
			for(int i = pages - 1; i >= 0; i--) {
				PDImageXObject chromatogramImage = chromatogramImages.get(i);
				createImagePage(document, chromatogramImage, page, pages, monitor);
			}
			createTablePages(document, chromatogram, page, pages, monitor);
			document.save(file);
		} catch(IOException e) {
			logger.warn(e);
		} finally {
			if(document != null) {
				document.close();
			}
		}
	}

	private PDPage createHeaderTable(PDDocument document, IChromatogram chromatogram, int page, int pages, IProgressMonitor monitor) throws IOException {

		PDPage pdPage = new PDPage(PDRectangle.A4);
		document.addPage(pdPage);
		//
		PDPageContentStream contentStream = new PDPageContentStream(document, pdPage);
		contentStream.setFont(font, 12);
		float xPosition = BORDER;
		float yPosition = 0;
		//
		yPosition = printHeaderTable(contentStream, chromatogram, xPosition, yPosition);
		printPageFooter(document, contentStream, page, pages);
		//
		contentStream.close();
		return pdPage;
	}

	@SuppressWarnings("rawtypes")
	private float printHeaderTable(PDPageContentStream contentStream, IChromatogram chromatogram, float xPosition, float yPosition) throws IOException {

		/*
		 * L_200_MM - L_20_MM
		 * ~520
		 */
		yPosition += L_15_MM;
		float yStartPosition = yPosition;
		/*
		 * Headline
		 */
		List<TableCell> cellsMaster = new ArrayList<TableCell>();
		cellsMaster.add(new TableCell("Name", 260.0f));
		cellsMaster.add(new TableCell("Value", 260.0f));
		yPosition = printTableLine(contentStream, xPosition, yPosition, cellsMaster, Color.GRAY, true, true);
		/*
		 * Data
		 */
		Map<String, String> headerDataMap = chromatogram.getHeaderDataMap();
		//
		int i = 0;
		List<TableCell> cells;
		for(Map.Entry<String, String> entry : headerDataMap.entrySet()) {
			cells = new ArrayList<TableCell>();
			cells.add(new TableCell(entry.getKey(), 260.0f));
			cells.add(new TableCell(entry.getValue(), 260.0f));
			//
			if(i % 2 == 0) {
				yPosition = printTableLine(contentStream, xPosition, yPosition, cells, Color.LIGHT_GRAY, false, true);
			} else {
				yPosition = printTableLine(contentStream, xPosition, yPosition, cells, null, false, true);
			}
			i++;
		}
		/*
		 * Print last line.
		 */
		printTableLastLine(contentStream, xPosition, yPosition, yStartPosition, cellsMaster);
		return yPosition;
	}

	private PDPage createImagePage(PDDocument document, PDImageXObject chromatogramImage, int page, int pages, IProgressMonitor monitor) throws IOException {

		PDPage pdPage = new PDPage(PDRectangle.A4);
		document.addPage(pdPage);
		//
		PDPageContentStream contentStream = new PDPageContentStream(document, pdPage);
		contentStream.setFont(font, 12);
		//
		if(chromatogramImage != null) {
			AffineTransform transform = new AffineTransform(IMAGE_WIDTH, 0, 0, IMAGE_HEIGHT, 200 * L_1_MM, 17 * L_1_MM);
			transform.rotate(Math.toRadians(90));
			Matrix matrix = new Matrix(transform);
			contentStream.drawImage(chromatogramImage, matrix);
		}
		//
		printPageFooter(document, contentStream, page, pages);
		//
		contentStream.close();
		return pdPage;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private PDPage createTablePages(PDDocument document, IChromatogram chromatogram, int page, int pages, IProgressMonitor monitor) throws IOException {

		PDPage pdPage = new PDPage(PDRectangle.A4);
		document.addPage(pdPage);
		//
		PDPageContentStream contentStream = new PDPageContentStream(document, pdPage);
		contentStream.setFont(font, 12);
		float xPosition = L_20_MM;
		float yPosition = 0;
		//
		List<IPeak> peaks = chromatogram.getPeaks();
		yPosition = printTableResults(contentStream, peaks, xPosition, yPosition);
		printPageFooter(document, contentStream, page, pages);
		//
		contentStream.close();
		return pdPage;
	}

	@SuppressWarnings("deprecation")
	private float printTableResults(PDPageContentStream contentStream, List<IPeak> peaks, float xPosition, float yPosition) throws IOException {

		/*
		 * L_200_MM - L_20_MM
		 * ~520
		 */
		yPosition += L_15_MM;
		float yStartPosition = yPosition;
		/*
		 * Headline
		 */
		List<TableCell> cellsMaster = new ArrayList<TableCell>();
		cellsMaster.add(new TableCell("Name", 190.0f));
		cellsMaster.add(new TableCell("CAS#", 90.0f));
		cellsMaster.add(new TableCell("Conc.", 55.0f));
		cellsMaster.add(new TableCell("Unit", 55.0f));
		cellsMaster.add(new TableCell("MQ", 55.0f));
		cellsMaster.add(new TableCell("OK", 75.0f));
		yPosition = printTableLine(contentStream, xPosition, yPosition, cellsMaster, Color.GRAY, true, true);
		/*
		 * Data
		 */
		int i = 0;
		List<TableCell> cells;
		for(IPeak peak : peaks) {
			cells = new ArrayList<TableCell>();
			cells.add(new TableCell(peak.getDetectorDescription(), 190.0f));
			cells.add(new TableCell(peak.getModelDescription(), 90.0f));
			cells.add(new TableCell(decimalFormat.format(peak.getIntegratedArea()), 55.0f));
			cells.add(new TableCell(peak.getClassifier(), 55.0f));
			cells.add(new TableCell(decimalFormat.format(peak.getSuggestedNumberOfComponents()), 55.0f));
			cells.add(new TableCell("+", 75.0f));
			//
			if(i % 2 == 0) {
				yPosition = printTableLine(contentStream, xPosition, yPosition, cells, Color.LIGHT_GRAY, false, true);
			} else {
				yPosition = printTableLine(contentStream, xPosition, yPosition, cells, null, false, true);
			}
			i++;
		}
		/*
		 * Print last line.
		 */
		float top = getPositionFromTop(yPosition * L_1_MM);
		contentStream.drawLine(xPosition, top, L_200_MM, top);
		/*
		 * Print vertical lines.
		 */
		float yStart = getPositionFromTop(yStartPosition * L_1_MM);
		float yStartExtraSpace = getPositionFromTop((yStartPosition + L_1_4_MM + L_0_5_MM) * L_1_MM);
		float yStop = getPositionFromTop(yPosition * L_1_MM);
		float left = xPosition;
		for(TableCell cell : cellsMaster) {
			if(cell.isPrintLeftLine()) {
				contentStream.drawLine(left, yStart, left, yStop);
			} else {
				contentStream.drawLine(left, yStartExtraSpace, left, yStop);
			}
			left += cell.getWidth();
		}
		contentStream.drawLine(L_200_MM, yStart, L_200_MM, yStop);
		return yPosition;
	}

	private float printTableLine(PDPageContentStream contentStream, float xPosition, float yPosition, List<TableCell> cells, Color color, boolean bold, boolean drawTopLine) throws IOException {

		float left = xPosition;
		float top = getPositionFromTop(yPosition * L_1_MM);
		/*
		 * Draw a colored background.
		 */
		if(color != null) {
			contentStream.setNonStrokingColor(color);
			float heightx = L_1_4_MM + L_0_5_MM;
			float topx = getPositionFromTop((yPosition + heightx) * L_1_MM);
			float widthx = L_200_MM - xPosition;
			contentStream.addRect(xPosition, topx, widthx, heightx * L_1_MM);
			contentStream.fill();
		}
		contentStream.setNonStrokingColor(Color.BLACK);
		/*
		 * Draw line at bottom?
		 */
		if(drawTopLine) {
			contentStream.setStrokingColor(Color.BLACK);
		} else {
			contentStream.setStrokingColor(color);
		}
		drawLine(contentStream, xPosition, top, L_200_MM, top);
		yPosition += L_1_4_MM;
		/*
		 * Print the text
		 */
		top = getPositionFromTop(yPosition * L_1_MM);
		for(TableCell cell : cells) {
			if(bold) {
				contentStream.setFont(fontBold, 12);
			}
			printText(contentStream, left, top, " " + cell.getText()); // $NON-NLS-1$
			left += cell.getWidth();
		}
		/*
		 * Add space if there is a top line.
		 */
		yPosition += L_0_5_MM;
		/*
		 * Set normal font
		 */
		contentStream.setFont(font, 12);
		return yPosition;
	}

	private void printTableLastLine(PDPageContentStream contentStream, float xPosition, float yPosition, float yStartPosition, List<TableCell> cells) throws IOException {

		float top = getPositionFromTop(yPosition * L_1_MM);
		drawLine(contentStream, xPosition, top, L_200_MM, top);
		/*
		 * Print vertical lines.
		 */
		float yStart = getPositionFromTop(yStartPosition * L_1_MM);
		float yStartExtraSpace = getPositionFromTop((yStartPosition + L_1_4_MM + L_0_5_MM) * L_1_MM);
		float yStop = getPositionFromTop(yPosition * L_1_MM);
		float left = xPosition;
		for(TableCell cell : cells) {
			if(cell.isPrintLeftLine()) {
				drawLine(contentStream, left, yStart, left, yStop);
			} else {
				drawLine(contentStream, left, yStartExtraSpace, left, yStop);
			}
			left += cell.getWidth();
		}
		drawLine(contentStream, L_200_MM, yStart, L_200_MM, yStop);
	}

	private void drawLine(PDPageContentStream contentStream, float x1, float y1, float x2, float y2) throws IOException {

		contentStream.moveTo(x1, y1);
		contentStream.lineTo(x2, y2);
		contentStream.stroke();
	}

	private void printPageFooter(PDDocument document, PDPageContentStream contentStream, int page, int pages) throws IOException {

		printText(contentStream, BORDER, getPositionFromTop(285 * L_1_MM), "Page " + page + "/" + pages); // $NON-NLS-1$
	}

	private void printText(PDPageContentStream contentStream, float xPosition, float yPosition, String text) throws IOException {

		contentStream.beginText();
		contentStream.newLineAtOffset(xPosition, yPosition);
		contentStream.showText(text);
		contentStream.endText();
	}

	private float getPositionFromTop(float x) {

		return L_297_MM - x;
	}

	@SuppressWarnings("rawtypes")
	private List<PDImageXObject> createImages(IChromatogram chromatogram, PDDocument document, int numberOfPages) {

		int width = 1080;
		int height = (int)(width * (IMAGE_WIDTH / IMAGE_HEIGHT));
		ImageRunnable imageRunnable = new ImageRunnable(chromatogram, document, numberOfPages, width, height);
		DisplayUtils.getDisplay().syncExec(imageRunnable);
		return imageRunnable.getChromatogramImages();
	}
}

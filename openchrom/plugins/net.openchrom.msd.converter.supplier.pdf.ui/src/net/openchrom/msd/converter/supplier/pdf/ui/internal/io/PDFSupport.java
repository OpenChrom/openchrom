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
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.AbstractChromatogram;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.core.IScan;
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
	private static final float LEFT_BORDER = 10.0f; // mm
	private static final float IMAGE_WIDTH = 190; // mm
	private static final float IMAGE_HEIGHT = 270; // mm
	private static final PDFont FONT_NORMAL = PDType1Font.HELVETICA;
	//
	private DecimalFormat decimalFormat = ValueFormat.getDecimalFormatEnglish();
	private PDFUtil pdfUtil = new PDFUtil();

	public void createPDF(File file, IChromatogram<? extends IPeak> chromatogram, IProgressMonitor monitor) throws IOException {

		PDDocument document = null;
		try {
			document = new PDDocument();
			//
			ImageRunnable imageRunnable = createImages(chromatogram, document, 8);
			PDFTable headerDataTable = getHeaderDataTable(chromatogram.getHeaderDataMap());
			List<PDImageXObject> chromatogramImages = imageRunnable.getChromatogramImages();
			List<? extends IPeak> peaks = imageRunnable.getPeaks();
			List<IScan> scans = imageRunnable.getScans();
			//
			int pages = chromatogramImages.size();
			int page = 1;
			//
			printHeaderData(document, chromatogram.getName(), headerDataTable, 1, 1, monitor);
			printImages(document, chromatogramImages, page, pages, monitor);
			printPeakTable(document, peaks, page, pages, monitor);
			//
			document.save(file);
		} catch(IOException e) {
			logger.warn(e);
		} finally {
			if(document != null) {
				document.close();
			}
		}
	}

	private PDPage printHeaderData(PDDocument document, String name, PDFTable pdfTable, int page, int pages, IProgressMonitor monitor) throws IOException {

		PDPage pdPage = new PDPage(PDRectangle.A4);
		document.addPage(pdPage);
		PDPageContentStream contentStream = new PDPageContentStream(document, pdPage);
		/*
		 * Logo
		 */
		contentStream.setFont(FONT_NORMAL, 12);
		PDImageXObject image = JPEGFactory.createFromStream(document, PDFSupport.class.getResourceAsStream("openchromlogo.jpg"));
		float factor = 0.15f;
		float width = image.getWidth() * factor;
		float height = image.getHeight() * factor;
		contentStream.drawImage(image, pdfUtil.getPositionLeft(LEFT_BORDER), pdfUtil.getPositionTop(10.0f) - height, width, height);
		pdfUtil.printText(contentStream, pdfUtil.getPositionLeft(LEFT_BORDER), pdfUtil.getPositionTop(13.0f) - height, "OpenChrom - the open source alternative for chromatography/spectrometry");
		/*
		 * Chromatogram Name
		 */
		contentStream.setFont(FONT_NORMAL, 16);
		pdfUtil.printText(contentStream, pdfUtil.getPositionLeft(LEFT_BORDER), pdfUtil.getPositionTop(45.0f), "Chromatogram: " + name);
		/*
		 * Header Data
		 */
		pdfTable.setPositionX(10.0f);
		pdfTable.setPositionY(50.0f);
		pdfTable.setColumnHeight(5.5f);
		pdfUtil.printTable(contentStream, pdfTable);
		/*
		 * Footer
		 */
		printPageFooter(document, contentStream, page, pages);
		//
		contentStream.close();
		return pdPage;
	}

	private void printImages(PDDocument document, List<PDImageXObject> chromatogramImages, int page, int pages, IProgressMonitor monitor) throws IOException {

		for(int i = pages - 1; i >= 0; i--) {
			PDImageXObject chromatogramImage = chromatogramImages.get(i);
			createImagePage(document, chromatogramImage, page, pages, monitor);
		}
	}

	private PDPage createImagePage(PDDocument document, PDImageXObject chromatogramImage, int page, int pages, IProgressMonitor monitor) throws IOException {

		PDPage pdPage = new PDPage(PDRectangle.A4);
		document.addPage(pdPage);
		PDPageContentStream contentStream = new PDPageContentStream(document, pdPage);
		//
		if(chromatogramImage != null) {
			float width = pdfUtil.convertMillimeterToPoint(IMAGE_WIDTH);
			float height = pdfUtil.convertMillimeterToPoint(IMAGE_HEIGHT);
			float x = pdfUtil.convertMillimeterToPoint(200.0f);
			float y = pdfUtil.convertMillimeterToPoint(17.0f);
			AffineTransform transform = new AffineTransform(width, 0, 0, height, x, y);
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

	private PDPage printPeakTable(PDDocument document, List<? extends IPeak> peaks, int page, int pages, IProgressMonitor monitor) throws IOException {

		PDPage pdPage = new PDPage(PDRectangle.A4);
		document.addPage(pdPage);
		PDPageContentStream contentStream = new PDPageContentStream(document, pdPage);
		/*
		 * Info
		 */
		PDFTable pdfTable = getPeakDataTable(peaks);
		//
		contentStream.setFont(FONT_NORMAL, 16);
		int start = 1;
		int stop = 46;
		int size = pdfTable.getNumberRows();
		StringBuilder builder = new StringBuilder();
		builder.append("Peak Table:");
		builder.append(" ");
		builder.append(start);
		builder.append(" - ");
		builder.append(stop);
		builder.append(" / ");
		builder.append(size);
		//
		pdfUtil.printText(contentStream, pdfUtil.getPositionLeft(LEFT_BORDER), pdfUtil.getPositionTop(15.0f), builder.toString());
		/*
		 * Peak Data
		 */
		contentStream.setFont(FONT_NORMAL, 12);
		pdfTable.setPositionX(10.0f);
		pdfTable.setPositionY(20.0f);
		pdfTable.setColumnHeight(5.5f);
		pdfTable.setRowStart(1);
		pdfTable.setRowStop(45);
		pdfUtil.printTable(contentStream, pdfTable);
		/*
		 * Footer
		 */
		printPageFooter(document, contentStream, page, pages);
		//
		contentStream.close();
		return pdPage;
	}

	private void printPageFooter(PDDocument document, PDPageContentStream contentStream, int page, int pages) throws IOException {

		StringBuilder builder = new StringBuilder();
		builder.append("Page");
		builder.append(" ");
		builder.append(page);
		builder.append(" / ");
		builder.append(pages);
		//
		contentStream.setFont(FONT_NORMAL, 12);
		pdfUtil.printText(contentStream, pdfUtil.getPositionLeft(LEFT_BORDER), pdfUtil.getPositionTop(285.0f), builder.toString());
	}

	private PDFTable getHeaderDataTable(Map<String, String> headerDataMap) {

		PDFTable pdfTable = new PDFTable();
		pdfTable.addColumn("Name", 95.0f);
		pdfTable.addColumn("Value", 95.0f);
		//
		for(Map.Entry<String, String> entry : headerDataMap.entrySet()) {
			List<String> row = new ArrayList<>();
			row.add(entry.getKey());
			row.add(entry.getValue());
			pdfTable.addRow(row);
		}
		//
		return pdfTable;
	}

	private PDFTable getPeakDataTable(List<? extends IPeak> peaks) {

		PDFTable pdfTable = new PDFTable();
		pdfTable.addColumn("ID", 20.0f);
		pdfTable.addColumn("RT", 30.0f);
		pdfTable.addColumn("Area", 40.0f);
		pdfTable.addColumn("Leading", 50.0f);
		pdfTable.addColumn("Tailing", 50.0f);
		//
		int i = 1;
		for(IPeak peak : peaks) {
			IPeakModel peakModel = peak.getPeakModel();
			List<String> row = new ArrayList<>();
			row.add("P" + i++);
			row.add(decimalFormat.format(peakModel.getRetentionTimeAtPeakMaximum() / AbstractChromatogram.MINUTE_CORRELATION_FACTOR));
			row.add(decimalFormat.format(peak.getIntegratedArea()));
			row.add(decimalFormat.format(peakModel.getLeading()));
			row.add(decimalFormat.format(peakModel.getTailing()));
			pdfTable.addRow(row);
		}
		//
		return pdfTable;
	}

	private ImageRunnable createImages(IChromatogram<? extends IPeak> chromatogram, PDDocument document, int numberOfPages) {

		int width = 1080;
		int height = (int)(width * (IMAGE_WIDTH / IMAGE_HEIGHT));
		ImageRunnable imageRunnable = new ImageRunnable(chromatogram, document, numberOfPages, width, height);
		DisplayUtils.getDisplay().syncExec(imageRunnable);
		//
		return imageRunnable;
	}
}

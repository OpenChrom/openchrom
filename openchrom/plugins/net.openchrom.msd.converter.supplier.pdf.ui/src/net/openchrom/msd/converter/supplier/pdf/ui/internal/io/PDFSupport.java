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
import java.util.Set;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;
import org.eclipse.chemclipse.csd.model.core.IChromatogramCSD;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.comparator.TargetExtendedComparator;
import org.eclipse.chemclipse.model.core.AbstractChromatogram;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.support.comparator.SortOrder;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.chemclipse.support.ui.workbench.DisplayUtils;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.pdf.preferences.PreferenceSupplier;

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
	private static final int NUMBER_ROWS_HEADER = 40;
	private static final int NUMBER_ROWS_DATA = 46;
	private static final PDFont FONT_NORMAL = PDType1Font.HELVETICA;
	//
	private DecimalFormat decimalFormatRT = ValueFormat.getDecimalFormatEnglish("0.00");
	private DecimalFormat decimalFormatArea = ValueFormat.getDecimalFormatEnglish("0.000");
	private PDFUtil pdfUtil = new PDFUtil();
	private TargetExtendedComparator targetComparator = new TargetExtendedComparator(SortOrder.DESC);

	@SuppressWarnings({"unchecked", "rawtypes"})
	public void createPDF(File file, IChromatogram<? extends IPeak> chromatogram, IProgressMonitor monitor) throws IOException {

		PDDocument document = null;
		try {
			document = new PDDocument();
			createChromatogramPDF(chromatogram, document, monitor);
			for(IChromatogram chromatogramReference : chromatogram.getReferencedChromatograms()) {
				createChromatogramPDF(chromatogramReference, document, monitor);
			}
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

	private void createChromatogramPDF(IChromatogram<? extends IPeak> chromatogram, PDDocument document, IProgressMonitor monitor) throws IOException {

		int numberImagePages = PreferenceSupplier.getNumberImagePages();
		ImageRunnable imageRunnable = createImages(chromatogram, document, numberImagePages);
		//
		String chromatogramName = getChromatogramName(chromatogram);
		PDFTable headerDataTable = getHeaderDataTable(chromatogram.getHeaderDataMap());
		List<PDImageXObject> chromatogramImages = imageRunnable.getChromatogramImages();
		PDFTable peakDataTable = getPeakDataTable(imageRunnable.getPeaks());
		PDFTable scanDataTable = getScanDataTable(imageRunnable.getScans());
		/*
		 * Calculate the number of pages.
		 */
		int pages = headerDataTable.getNumberRows() / NUMBER_ROWS_HEADER + 1;
		pages += chromatogramImages.size();
		pages += peakDataTable.getNumberRows() / NUMBER_ROWS_DATA + 1;
		pages += peakDataTable.getNumberRows() / NUMBER_ROWS_DATA + 1;
		//
		int page = printHeaderDataPages(document, chromatogramName, headerDataTable, 1, pages, monitor);
		page = printImagePages(document, chromatogramImages, page, pages, monitor);
		page = printPeakTablePages(document, peakDataTable, page, pages, monitor);
		page = printScanTablePages(document, scanDataTable, page, pages, monitor);
	}

	private String getChromatogramName(IChromatogram<? extends IPeak> chromatogram) {

		String name = chromatogram.getName();
		if(chromatogram instanceof IChromatogramMSD) {
			name += " (MSD)";
		} else if(chromatogram instanceof IChromatogramCSD) {
			name += " (CSD)";
		} else if(chromatogram instanceof IChromatogramWSD) {
			name += " (WSD)";
		}
		//
		return name;
	}

	private int printHeaderDataPages(PDDocument document, String name, PDFTable pdfTable, int page, int pages, IProgressMonitor monitor) throws IOException {

		int parts = pdfTable.getNumberRows() / NUMBER_ROWS_HEADER + 1;
		for(int part = 0; part < parts; part++) {
			int range = part * NUMBER_ROWS_HEADER;
			int rowStart = range + 1;
			int rowStop = range + NUMBER_ROWS_HEADER;
			rowStop = (rowStop > pdfTable.getNumberRows()) ? pdfTable.getNumberRows() : rowStop;
			pdfTable.setRowStart(rowStart);
			pdfTable.setRowStop(rowStop);
			page = printHeaderData(document, name, pdfTable, page, pages, monitor);
		}
		return page;
	}

	private int printHeaderData(PDDocument document, String name, PDFTable pdfTable, int page, int pages, IProgressMonitor monitor) throws IOException {

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
		return ++page;
	}

	private int printImagePages(PDDocument document, List<PDImageXObject> chromatogramImages, int page, int pages, IProgressMonitor monitor) throws IOException {

		for(int i = chromatogramImages.size() - 1; i >= 0; i--) {
			PDImageXObject chromatogramImage = chromatogramImages.get(i);
			page = printImagePage(document, chromatogramImage, page, pages, monitor);
		}
		return page;
	}

	private int printImagePage(PDDocument document, PDImageXObject chromatogramImage, int page, int pages, IProgressMonitor monitor) throws IOException {

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
		return ++page;
	}

	private int printPeakTablePages(PDDocument document, PDFTable pdfTable, int page, int pages, IProgressMonitor monitor) throws IOException {

		int parts = pdfTable.getNumberRows() / NUMBER_ROWS_DATA + 1;
		for(int part = 0; part < parts; part++) {
			int range = part * NUMBER_ROWS_DATA;
			int rowStart = range + 1;
			int rowStop = range + NUMBER_ROWS_DATA;
			rowStop = (rowStop > pdfTable.getNumberRows()) ? pdfTable.getNumberRows() : rowStop;
			pdfTable.setRowStart(rowStart);
			pdfTable.setRowStop(rowStop);
			page = printPeakTablePage(document, pdfTable, page, pages, monitor);
		}
		return page;
	}

	private int printPeakTablePage(PDDocument document, PDFTable pdfTable, int page, int pages, IProgressMonitor monitor) throws IOException {

		PDPage pdPage = new PDPage(PDRectangle.A4);
		document.addPage(pdPage);
		PDPageContentStream contentStream = new PDPageContentStream(document, pdPage);
		/*
		 * Info
		 */
		contentStream.setFont(FONT_NORMAL, 16);
		int size = pdfTable.getNumberRows();
		StringBuilder builder = new StringBuilder();
		builder.append("Peak Table:");
		builder.append(" ");
		builder.append(pdfTable.getRowStart());
		builder.append(" - ");
		builder.append(pdfTable.getRowStop());
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
		pdfUtil.printTable(contentStream, pdfTable);
		/*
		 * Footer
		 */
		printPageFooter(document, contentStream, page, pages);
		//
		contentStream.close();
		return ++page;
	}

	private int printScanTablePages(PDDocument document, PDFTable pdfTable, int page, int pages, IProgressMonitor monitor) throws IOException {

		int parts = pdfTable.getNumberRows() / NUMBER_ROWS_DATA + 1;
		for(int part = 0; part < parts; part++) {
			int range = part * NUMBER_ROWS_DATA;
			int rowStart = range + 1;
			int rowStop = range + NUMBER_ROWS_DATA;
			rowStop = (rowStop > pdfTable.getNumberRows()) ? pdfTable.getNumberRows() : rowStop;
			pdfTable.setRowStart(rowStart);
			pdfTable.setRowStop(rowStop);
			page = printScanTablePage(document, pdfTable, page, pages, monitor);
		}
		return page;
	}

	private int printScanTablePage(PDDocument document, PDFTable pdfTable, int page, int pages, IProgressMonitor monitor) throws IOException {

		PDPage pdPage = new PDPage(PDRectangle.A4);
		document.addPage(pdPage);
		PDPageContentStream contentStream = new PDPageContentStream(document, pdPage);
		/*
		 * Info
		 */
		contentStream.setFont(FONT_NORMAL, 16);
		int size = pdfTable.getNumberRows();
		StringBuilder builder = new StringBuilder();
		builder.append("Scan Table:");
		builder.append(" ");
		builder.append(pdfTable.getRowStart());
		builder.append(" - ");
		builder.append(pdfTable.getRowStop());
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
		pdfUtil.printTable(contentStream, pdfTable);
		/*
		 * Footer
		 */
		printPageFooter(document, contentStream, page, pages);
		//
		contentStream.close();
		return ++page;
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
		pdfTable.addColumn("ID", 15.0f);
		pdfTable.addColumn("RT", 20.0f);
		pdfTable.addColumn("Area%", 20.0f);
		pdfTable.addColumn("Identification", 135.0f);
		//
		double totalPeakArea = getTotalPeakArea(peaks);
		//
		int i = 1;
		for(IPeak peak : peaks) {
			IPeakModel peakModel = peak.getPeakModel();
			List<String> row = new ArrayList<>();
			row.add("P" + i++);
			row.add(decimalFormatRT.format(peakModel.getRetentionTimeAtPeakMaximum() / AbstractChromatogram.MINUTE_CORRELATION_FACTOR));
			row.add(decimalFormatArea.format(getPercentagePeakArea(totalPeakArea, peak.getIntegratedArea())));
			row.add(getBestIdentification(peak.getTargets()));
			pdfTable.addRow(row);
		}
		//
		return pdfTable;
	}

	private double getPercentagePeakArea(double totalPeakArea, double peakArea) {

		if(totalPeakArea > 0) {
			return (100.0d / totalPeakArea) * peakArea;
		} else {
			return 0.0d;
		}
	}

	private double getTotalPeakArea(List<? extends IPeak> peaks) {

		double totalPeakArea = 0.0d;
		for(IPeak peak : peaks) {
			totalPeakArea += peak.getIntegratedArea();
		}
		return totalPeakArea;
	}

	private PDFTable getScanDataTable(List<IScan> scans) {

		PDFTable pdfTable = new PDFTable();
		pdfTable.addColumn("ID", 15.0f);
		pdfTable.addColumn("RT", 20.0f);
		pdfTable.addColumn("Scan#", 20.0f);
		pdfTable.addColumn("Identification", 135.0f);
		//
		int i = 1;
		for(IScan scan : scans) {
			List<String> row = new ArrayList<>();
			row.add("S" + i++);
			row.add(decimalFormatRT.format(scan.getRetentionTime() / AbstractChromatogram.MINUTE_CORRELATION_FACTOR));
			row.add(Integer.toString(scan.getScanNumber()));
			row.add(getBestIdentification(scan.getTargets()));
			pdfTable.addRow(row);
		}
		//
		return pdfTable;
	}

	private String getBestIdentification(Set<IIdentificationTarget> targets) {

		ILibraryInformation libraryInformation = IIdentificationTarget.getBestLibraryInformation(targets, targetComparator);
		if(libraryInformation != null) {
			return libraryInformation.getName();
		} else {
			return "";
		}
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

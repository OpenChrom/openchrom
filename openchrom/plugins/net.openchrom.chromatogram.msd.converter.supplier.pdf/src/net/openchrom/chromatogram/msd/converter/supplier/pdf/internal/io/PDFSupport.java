/*******************************************************************************
 * Copyright (c) 2012 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.pdf.internal.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;

import org.eclipse.core.runtime.IProgressMonitor;

import com.pdfjet.Box;
import com.pdfjet.Font;
import com.pdfjet.Image;
import com.pdfjet.ImageType;
import com.pdfjet.Line;
import com.pdfjet.A4;
import com.pdfjet.PDF;
import com.pdfjet.Page;
import com.pdfjet.Path;
import com.pdfjet.Point;
import com.pdfjet.RGB;
import com.pdfjet.TextLine;

import net.openchrom.chromatogram.msd.model.core.AbstractChromatogram;
import net.openchrom.chromatogram.msd.model.core.IChromatogram;
import net.openchrom.chromatogram.msd.model.xic.ITotalIonSignal;
import net.openchrom.chromatogram.msd.model.xic.ITotalIonSignalExtractor;
import net.openchrom.chromatogram.msd.model.xic.ITotalIonSignals;
import net.openchrom.chromatogram.msd.model.xic.TotalIonSignalExtractor;

public class PDFSupport {

	private static final double L_1_MM = 2.8346;
	private static final double L_2_MM = 2 * L_1_MM;
	private static final double L_5_MM = 5 * L_1_MM;
	private static final double L_10_MM = 10 * L_1_MM;
	private static final double L_11_MM = 11 * L_1_MM;
	private static final double L_15_MM = 15 * L_1_MM;
	private static final double L_17_MM = 17 * L_1_MM;
	private static final double L_23_MM = 23 * L_1_MM;
	private static final double L_25_MM = 25 * L_1_MM;
	private static final double L_110_MM = 110 * L_1_MM;
	private static final double L_125_MM = 125 * L_1_MM;
	private static final double L_128_MM = 128 * L_1_MM;
	private static final double L_135_MM = 135 * L_1_MM;
	private static final double L_140_MM = 140 * L_1_MM;
	private static final double L_152_MM = 152 * L_1_MM;
	private static final double L_170_MM = 170 * L_1_MM;
	private static final double L_200_MM = 200 * L_1_MM;
	private Font font;
	private Font font10pt;
	private Font font14pt;
	private DecimalFormat decimalFormat;
	private DecimalFormat abundanceFormat;
	//
	private double minRetentionTime;
	private double maxRetentionTime;
	private double minAbundance;
	private double maxAbundance;
	private IChromatogram chromatogram;

	public PDFSupport() {

		decimalFormat = new DecimalFormat("0.00");
		abundanceFormat = new DecimalFormat("0");
	}

	public void exportChromatogram(File file, IChromatogram chromatogram, IProgressMonitor monitor) throws Exception {

		/*
		 * Get basic chromatogram values.
		 */
		this.minRetentionTime = chromatogram.getStartRetentionTime();
		this.maxRetentionTime = chromatogram.getStopRetentionTime();
		this.minAbundance = chromatogram.getMinSignal();
		this.maxAbundance = chromatogram.getMaxSignal();
		this.chromatogram = chromatogram;
		/*
		 * PDF
		 */
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		PDF pdf = new PDF(fileOutputStream);
		/*
		 * PDF Pages
		 */
		font = new Font(pdf, "Helvetica");
		font10pt = new Font(pdf, "Helvetica");
		font10pt.setSize(10.0);
		font14pt = new Font(pdf, "Helvetica");
		font14pt.setSize(14.0);
		//
		createFirstPage(pdf);
		/*
		 * Close the streams
		 */
		pdf.flush();
		fileOutputStream.close();
	}

	private void createFirstPage(PDF pdf) throws Exception {

		/*
		 * A4 PORTRAIT = 595.0 x 842.0 pt
		 * A4 LANDSCAPE = 842.0 x 595.0 pt
		 */
		Page page = new Page(pdf, A4.PORTRAIT);
		createUpperBox(page, pdf);
		createLowerBox(page);
	}

	private void createUpperBox(Page page, PDF pdf) throws Exception {

		/*
		 * Box upper part with 5 mm border space
		 * 5 mm == 14.173 pthromatogram
		 * 200 x 140 mm == 566.929 x 396.850 pt
		 */
		Box box = new Box(L_5_MM, L_5_MM, L_200_MM, L_140_MM);
		box.setColor(RGB.WHITE);
		box.setFillShape(true);
		box.setLineWidth(0);
		box.drawOn(page);
		/*
		 * Content
		 */
		double yPosition = L_10_MM;
		createHeadlineText(page, box, chromatogram.getName(), yPosition);
		yPosition += 7 * L_1_MM;
		createUpperBoxText(page, box, "-------------------------------------------------------------------------------", yPosition);
		yPosition += L_5_MM;
		createUpperBoxText(page, box, "Min Abundance: " + minAbundance, yPosition);
		yPosition += L_5_MM;
		createUpperBoxText(page, box, "Max Abundance: " + maxAbundance, yPosition);
		yPosition += L_5_MM;
		createUpperBoxText(page, box, "Min Retention Time: " + decimalFormat.format(minRetentionTime / AbstractChromatogram.MINUTE_CORRELATION_FACTOR) + " Minutes = " + (int)minRetentionTime + " Milliseconds", yPosition);
		yPosition += L_5_MM;
		createUpperBoxText(page, box, "Max Retention Time: " + decimalFormat.format(maxRetentionTime / AbstractChromatogram.MINUTE_CORRELATION_FACTOR) + " Minutes = " + (int)maxRetentionTime + " Milliseconds", yPosition);
		yPosition += L_5_MM;
		createUpperBoxText(page, box, "Scans: " + chromatogram.getNumberOfScans(), yPosition);
		yPosition += L_5_MM;
		createUpperBoxText(page, box, "Ions: " + chromatogram.getNumberOfScanIons(), yPosition);
		yPosition += L_5_MM;
		createUpperBoxText(page, box, "Noise Factor: " + chromatogram.getNoiseFactor(), yPosition);
		yPosition += L_5_MM;
		createUpperBoxText(page, box, "Scan Delay: " + decimalFormat.format(chromatogram.getScanDelay() / AbstractChromatogram.MINUTE_CORRELATION_FACTOR) + " Minutes = " + chromatogram.getScanDelay() + " Milliseconds", yPosition);
		yPosition += L_5_MM;
		createUpperBoxText(page, box, "Scan Interval: " + decimalFormat.format(chromatogram.getScanInterval() / AbstractChromatogram.MINUTE_CORRELATION_FACTOR) + " Minutes = " + chromatogram.getScanInterval() + " Milliseconds", yPosition);
		/*
		 * Logo
		 */
		createLogoBox(page, box, pdf);
	}

	private void createLogoBox(Page page, Box box, PDF pdf) throws Exception {

		createUpperBoxText(page, box, "This chromatogram has been brought to you by OpenChrom (http://www.openchrom.net).", 133.5 * L_1_MM);
		/*
		 * Image
		 */
		Box boxImage = new Box(0, 0, 20 * L_1_MM, 20 * L_1_MM);
		boxImage.setColor(RGB.WHITE);
		boxImage.setFillShape(true);
		boxImage.setLineWidth(0);
		boxImage.placeIn(box, 180 * L_1_MM, 120 * L_1_MM);
		boxImage.drawOn(page);
		/*
		 * Place OpenChroms Logo
		 */
		BufferedInputStream bufferedInputStream = new BufferedInputStream(PDFSupport.class.getResourceAsStream("openchrom.png"));
		Image image = new Image(pdf, bufferedInputStream, ImageType.PNG);
		image.scaleBy(0.24d);
		image.placeIn(boxImage);
		image.drawOn(page);
	}

	private void createHeadlineText(Page page, Box box, String text, double yPosition) throws Exception {

		TextLine textLine = new TextLine(font14pt, text);
		textLine.placeIn(box);
		textLine.setPosition(L_5_MM, yPosition);
		textLine.drawOn(page);
	}

	private void createUpperBoxText(Page page, Box box, String text, double yPosition) throws Exception {

		TextLine textLine = new TextLine(font, text);
		textLine.placeIn(box);
		textLine.setPosition(L_5_MM, yPosition);
		textLine.drawOn(page);
	}

	private void createLowerBox(Page page) throws Exception {

		/*
		 * Box upper part with 5 mm border space
		 * 430.866 = L_152_MM
		 */
		Box box = new Box(L_5_MM, L_152_MM, L_200_MM, L_140_MM);
		box.setColor(RGB.WHITE);
		box.setFillShape(true);
		box.setLineWidth(2);
		box.drawOn(page);
		/*
		 * Chromatogram Box
		 */
		createChromatogramBox(page, box);
		/*
		 * Header
		 */
		StringBuilder builder = new StringBuilder();
		builder.append(chromatogram.getName());
		builder.append(" - ");
		builder.append(chromatogram.getNumberOfScans());
		builder.append(" ");
		builder.append("Scans");
		builder.append(" - ");
		builder.append("Max Abundance:");
		builder.append(" ");
		builder.append(maxAbundance);
		builder.append(" - ");
		builder.append("Max Retention Time:");
		builder.append(" ");
		builder.append(decimalFormat.format(maxRetentionTime / AbstractChromatogram.MINUTE_CORRELATION_FACTOR));
		builder.append(" ");
		builder.append("min");
		//
		createChromatogramHeader(page, box, builder.toString());
		/*
		 * Scale X Y
		 */
		createChromatogramBoxScaleY(page, box);
		createChromatogramBoxScaleX(page, box);
	}

	private void createChromatogramHeader(Page page, Box box, String text) throws Exception {

		TextLine textLine = new TextLine(font10pt, text);
		textLine.placeIn(box);
		textLine.setPosition(L_25_MM, L_10_MM);
		textLine.drawOn(page);
	}

	private void createChromatogramBox(Page page, Box box) throws Exception {

		Box chromatogramBox = new Box(0, 0, L_170_MM, L_110_MM);
		chromatogramBox.setColor(RGB.BLACK);
		chromatogramBox.setFillShape(false);
		chromatogramBox.setLineWidth(1);
		chromatogramBox.placeIn(box, L_25_MM, L_15_MM);
		chromatogramBox.drawOn(page);
		drawChromatogram(page, chromatogramBox);
	}

	private void drawChromatogram(Page page, Box box) throws Exception {

		double width = L_170_MM;
		double height = L_110_MM;
		Path path = new Path();
		/*
		 * Parse each scan
		 */
		ITotalIonSignalExtractor totalIonSignalExtractor = new TotalIonSignalExtractor(chromatogram);
		ITotalIonSignals scans = totalIonSignalExtractor.getTotalIonSignals();
		for(ITotalIonSignal scan : scans.getTotalIonSignals()) {
			int rt = scan.getRetentionTime();
			float abundance = scan.getTotalSignal();
			double x = calculateRetentionTime(rt, width);
			double y = calculateAbundance(abundance, height);
			path.add(new Point(x, y));
		}
		/*
		 * Path
		 */
		path.setClosePath(false);
		path.setColor(RGB.RED);
		path.setFillShape(false);
		path.placeIn(box, 0, 0);
		path.drawOn(page);
	}

	private double calculateRetentionTime(int retentionTime, double width) {

		double percentage = ((100.0 / maxRetentionTime) * retentionTime) / 100.0;
		double position = (width / 100.0) * (100 * percentage);
		return position;
	}

	private double calculateAbundance(float abundance, double height) {

		double percentage = ((100.0 / maxAbundance) * abundance) / 100.0;
		double position = (height / 100.0) * (100 * percentage);
		position = height - position;
		if(position < 0) {
			position = 0;
		}
		return position;
	}

	private void createChromatogramBoxScaleY(Page page, Box box) throws Exception {

		// float delta = (float)(maxAbundance - minAbundance);
		float delta = (float)(maxAbundance - 0);
		float part = (delta / 10.0f);
		float scaleIndex = (float)maxAbundance;
		Line line;
		TextLine textLine;
		double yPosition = L_15_MM;
		for(int i = 10; i >= 0; i--) {
			/*
			 * Line
			 */
			line = new Line(L_23_MM, yPosition, L_25_MM, yPosition);
			line.placeIn(box, 0, 0);
			line.drawOn(page);
			/*
			 * Scale
			 */
			// double scaleIndex = i / 10.0d;
			textLine = new TextLine(font10pt, abundanceFormat.format(scaleIndex));
			scaleIndex -= part;
			textLine.placeIn(box);
			textLine.setPosition(L_2_MM, yPosition + L_1_MM);
			textLine.drawOn(page);
			/*
			 * Increment the y position.
			 */
			yPosition += L_11_MM;
		}
	}

	private void createChromatogramBoxScaleX(Page page, Box box) throws Exception {

		int delta = (int)(maxRetentionTime - minRetentionTime);
		float part = (float)((delta / 10.0f) / AbstractChromatogram.MINUTE_CORRELATION_FACTOR);
		float start = (float)(minRetentionTime / AbstractChromatogram.MINUTE_CORRELATION_FACTOR);
		float scaleIndex = start;
		Line line;
		TextLine textLine;
		double xPosition = L_25_MM;
		for(int i = 0; i <= 10; i++) {
			/*
			 * Line
			 */
			line = new Line(xPosition, L_125_MM, xPosition, L_128_MM);
			line.placeIn(box, 0, 0);
			line.drawOn(page);
			/*
			 * Scale
			 */
			// double scaleIndex = i / 10.0d;
			textLine = new TextLine(font10pt, decimalFormat.format(scaleIndex));
			scaleIndex += part;
			textLine.placeIn(box);
			textLine.setPosition(xPosition - L_5_MM, L_135_MM);
			textLine.drawOn(page);
			/*
			 * Increment the x position.
			 */
			xPosition += L_17_MM;
		}
	}
}

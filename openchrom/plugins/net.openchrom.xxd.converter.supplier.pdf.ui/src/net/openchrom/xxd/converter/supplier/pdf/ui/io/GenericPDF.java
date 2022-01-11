/*******************************************************************************
 * Copyright (c) 2019, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Christoph Läubrich - Settings support
 *******************************************************************************/
package net.openchrom.xxd.converter.supplier.pdf.ui.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.eclipse.chemclipse.csd.model.core.IChromatogramCSD;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.comparator.IdentificationTargetComparator;
import org.eclipse.chemclipse.model.core.AbstractChromatogram;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.model.quantitation.IQuantitationEntry;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.pdfbox.extensions.core.PDTable;
import org.eclipse.chemclipse.pdfbox.extensions.core.PageUtil;
import org.eclipse.chemclipse.pdfbox.extensions.elements.ImageElement;
import org.eclipse.chemclipse.pdfbox.extensions.elements.TableElement;
import org.eclipse.chemclipse.pdfbox.extensions.elements.TextElement;
import org.eclipse.chemclipse.pdfbox.extensions.settings.PageBase;
import org.eclipse.chemclipse.pdfbox.extensions.settings.PageSettings;
import org.eclipse.chemclipse.pdfbox.extensions.settings.ReferenceX;
import org.eclipse.chemclipse.pdfbox.extensions.settings.ReferenceY;
import org.eclipse.chemclipse.pdfbox.extensions.settings.TextOption;
import org.eclipse.chemclipse.pdfbox.extensions.settings.Unit;
import org.eclipse.chemclipse.support.comparator.SortOrder;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.chemclipse.support.ui.workbench.DisplayUtils;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.converter.supplier.pdf.ui.io.generic.ImageRunnableGeneric;
import net.openchrom.xxd.converter.supplier.pdf.ui.settings.ReportSettingsGeneric;

public class GenericPDF {

	private static final Logger logger = Logger.getLogger(GenericPDF.class);
	//
	private static final float LEFT_BORDER = 10.0f;
	private static final float TOP_BORDER = 10.0f;
	private static final float MAX_WIDTH_PORTRAIT = 190.0f;
	private static final float MAX_WIDTH_LANDSCAPE = 277.0f;
	private static final float LINE_HEIGHT = 5.5f;
	private static final float LINE_WIDTH = 0.2f;
	private static final float TEXT_OFFSET_X = 1.0f;
	private static final float TEXT_OFFSET_Y = 1.0f;
	private static final int MAX_ROWS_PORTRAIT = 36;
	private static final int MAX_ROWS_LANDSCAPE = 20;
	//
	private static final float IMAGE_WIDTH = 270.0f;
	private static final float IMAGE_HEIGHT = 190.0f;
	//
	private PDImageXObject banner = null;
	private String slogan = null;
	//
	private DecimalFormat dfRetentionTime = ValueFormat.getDecimalFormatEnglish("0.00");
	private DecimalFormat dfAreaPercent = ValueFormat.getDecimalFormatEnglish("0.000");
	private DecimalFormat dfAreaNormal = ValueFormat.getDecimalFormatEnglish("0.0#E0");
	private DecimalFormat dfConcentration = ValueFormat.getDecimalFormatEnglish("0.000");
	private ReportSettingsGeneric settings;

	public GenericPDF() {

		this(new ReportSettingsGeneric());
	}

	public GenericPDF(ReportSettingsGeneric settings) {

		this.settings = settings;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public void createPDF(File file, IChromatogram<? extends IPeak> chromatogram, IProgressMonitor monitor) throws IOException {

		try (PDDocument document = new PDDocument()) {
			/*
			 * Report Name
			 */
			createChromatogramPDF(chromatogram, document, monitor);
			for(IChromatogram chromatogramReference : chromatogram.getReferencedChromatograms()) {
				createChromatogramPDF(chromatogramReference, document, monitor);
			}
			document.save(file);
		} catch(Exception e) {
			logger.warn(e);
		}
	}

	private void createChromatogramPDF(IChromatogram<? extends IPeak> chromatogram, PDDocument document, IProgressMonitor monitor) throws IOException {

		if(chromatogram != null) {
			String chromatogramName = getChromatogramName(chromatogram);
			PDTable headerDataTable = getHeaderDataTable(chromatogram.getHeaderDataMap());
			ImageRunnableGeneric imageRunnable = createImages(chromatogram, document, settings.getNumberOfImagesPerPage());
			List<PDImageXObject> chromatogramImages = imageRunnable.getChromatogramImages();
			PDTable peakDataTable = getPeakDataTable(imageRunnable.getPeaks());
			PDTable scanDataTable = getScanDataTable(imageRunnable.getScans());
			PDTable quantitationDataTable = getQuantitationDataTable(imageRunnable.getPeaks());
			/*
			 * Calculate the number of pages.
			 */
			int pages = headerDataTable.getNumberDataRows() / MAX_ROWS_PORTRAIT + 1;
			pages += chromatogramImages.size();
			if(peakDataTable.getNumberDataRows() > 0) {
				pages += peakDataTable.getNumberDataRows() / MAX_ROWS_PORTRAIT + 1;
			}
			if(scanDataTable.getNumberDataRows() > 0) {
				pages += scanDataTable.getNumberDataRows() / MAX_ROWS_PORTRAIT + 1;
			}
			if(quantitationDataTable.getNumberDataRows() > 0) {
				pages += quantitationDataTable.getNumberDataRows() / MAX_ROWS_LANDSCAPE + 1;
			}
			//
			int page = printTablePages(document, headerDataTable, "Header Table:", chromatogramName, 1, pages, false, monitor);
			page = printImagePages(document, chromatogramImages, page, pages, monitor);
			if(peakDataTable.getNumberDataRows() > 0) {
				page = printTablePages(document, peakDataTable, "Peak Table:", chromatogramName, page, pages, false, monitor);
			}
			if(scanDataTable.getNumberDataRows() > 0) {
				page = printTablePages(document, scanDataTable, "Scan Table:", chromatogramName, page, pages, false, monitor);
			}
			if(quantitationDataTable.getNumberDataRows() > 0) {
				printTablePages(document, quantitationDataTable, "Quantitation Table:", chromatogramName, page, pages, true, monitor);
			}
		}
	}

	private int printImagePages(PDDocument document, List<PDImageXObject> chromatogramImages, int page, int pages, IProgressMonitor monitor) throws IOException {

		for(int i = 0; i < chromatogramImages.size(); i++) {
			PDImageXObject chromatogramImage = chromatogramImages.get(i);
			page = printImagePage(document, chromatogramImage, page, pages, monitor);
		}
		return page;
	}

	private int printImagePage(PDDocument document, PDImageXObject chromatogramImage, int page, int pages, IProgressMonitor monitor) throws IOException {

		PageUtil pageUtil = new PageUtil(document, new PageSettings(PDRectangle.A4, PageBase.TOP_LEFT, Unit.MM, true));
		//
		if(chromatogramImage != null) {
			ImageElement imageElement = new ImageElement(LEFT_BORDER, TOP_BORDER).setWidth(IMAGE_WIDTH).setHeight(IMAGE_HEIGHT).setImage(chromatogramImage);
			pageUtil.printImage(imageElement);
		}
		//
		printPageFooter(pageUtil, page, pages, true);
		pageUtil.close();
		return ++page;
	}

	private int printTablePages(PDDocument document, PDTable pdTable, String title, String chromatogramName, int page, int pages, boolean landscape, IProgressMonitor monitor) throws IOException {

		int maxRows = (landscape) ? MAX_ROWS_LANDSCAPE : MAX_ROWS_PORTRAIT;
		//
		int parts = pdTable.getNumberDataRows() / maxRows + 1;
		for(int part = 0; part < parts; part++) {
			int range = part * maxRows;
			int startIndex = range;
			int stopIndex = range + maxRows;
			stopIndex = (stopIndex > pdTable.getNumberDataRows()) ? pdTable.getNumberDataRows() : stopIndex;
			pdTable.setStartIndex(startIndex);
			pdTable.setStopIndex(stopIndex);
			page = printTablePage(document, pdTable, title, chromatogramName, page, pages, landscape, monitor);
		}
		return page;
	}

	private int printTablePage(PDDocument document, PDTable pdTable, String title, String chromatogramName, int page, int pages, boolean landscape, IProgressMonitor monitor) throws IOException {

		PageUtil pageUtil = new PageUtil(document, new PageSettings(PDRectangle.A4, PageBase.TOP_LEFT, Unit.MM, landscape));
		printPageHeader(pageUtil);
		//
		pageUtil.printText(new TextElement(LEFT_BORDER, 45.0f, MAX_WIDTH_PORTRAIT).setText("Chromatogram: " + chromatogramName));
		pageUtil.printText(new TextElement(LEFT_BORDER, 50.0f, MAX_WIDTH_PORTRAIT).setText(getTableHeaderText(pdTable, title)));
		//
		TableElement tableElement = new TableElement(LEFT_BORDER, 60.0f, LINE_HEIGHT);
		tableElement.setTextOffsetX(TEXT_OFFSET_X);
		tableElement.setTextOffsetY(TEXT_OFFSET_Y);
		tableElement.setLineWidth(LINE_WIDTH);
		tableElement.setPDTable(pdTable);
		pageUtil.printTable(tableElement);
		//
		printPageFooter(pageUtil, page, pages, landscape);
		pageUtil.close();
		//
		return ++page;
	}

	private String getTableHeaderText(PDTable pdTable, String title) {

		int size = pdTable.getNumberDataRows();
		StringBuilder builder = new StringBuilder();
		builder.append(title);
		builder.append(" ");
		builder.append((pdTable.getNumberDataRows() == 0) ? 0 : pdTable.getStartIndex() + 1);
		builder.append(" - ");
		builder.append(pdTable.getStopIndex());
		builder.append(" / ");
		builder.append(size);
		return builder.toString();
	}

	private void printPageHeader(PageUtil pageUtil) throws IOException {

		banner = (banner == null) ? getBanner(pageUtil) : banner;
		slogan = (slogan == null) ? settings.getReportSlogan() : slogan;
		pageUtil.printImage(new ImageElement(LEFT_BORDER, TOP_BORDER).setWidth(100.0f).setHeight(13.89f).setImage(banner));
		pageUtil.printText(new TextElement(LEFT_BORDER, 28.0f, MAX_WIDTH_PORTRAIT).setText(slogan));
	}

	private void printPageFooter(PageUtil pageUtil, int page, int pages, boolean landscape) throws IOException {

		float y;
		float maxWidth;
		//
		if(landscape) {
			y = 200.0f;
			maxWidth = MAX_WIDTH_LANDSCAPE;
		} else {
			y = 287.0f;
			maxWidth = MAX_WIDTH_PORTRAIT;
		}
		//
		pageUtil.printText(new TextElement(LEFT_BORDER, y, maxWidth).setReferenceX(ReferenceX.RIGHT).setReferenceY(ReferenceY.BOTTOM).setText("Page " + page + "/" + pages));
	}

	private PDImageXObject getBanner(PageUtil pageUtil) throws IOException {

		PDImageXObject banner = null;
		InputStream inputStream = null;
		//
		try {
			File file = settings.getReportBanner();
			if(file != null && file.exists() && (file.getName().toLowerCase().endsWith(".jpg") || file.getName().toLowerCase().endsWith(".jepg"))) { // $NON-NLS-1$
				inputStream = new FileInputStream(file);
			} else {
				inputStream = GenericPDF.class.getResourceAsStream("openchromlogo.jpg"); // $NON-NLS-1$
			}
			banner = JPEGFactory.createFromStream(pageUtil.getDocument(), inputStream);
		} catch(Exception e) {
			logger.warn(e);
		} finally {
			if(inputStream != null) {
				inputStream.close();
			}
		}
		//
		return banner;
	}

	private ImageRunnableGeneric createImages(IChromatogram<? extends IPeak> chromatogram, PDDocument document, int numberOfPages) {

		int width = 1080;
		int height = (int)(width * (IMAGE_HEIGHT / IMAGE_WIDTH));
		ImageRunnableGeneric imageRunnable = new ImageRunnableGeneric(chromatogram, document, numberOfPages, width, height);
		DisplayUtils.getDisplay().syncExec(imageRunnable);
		//
		return imageRunnable;
	}

	private PDTable getHeaderDataTable(Map<String, String> headerDataMap) {

		PDTable pdTable = new PDTable();
		pdTable.setTextOption(TextOption.SHORTEN);
		//
		pdTable.addColumn("Name", 95.0f);
		pdTable.addColumn("Value", 95.0f);
		//
		for(Map.Entry<String, String> entry : headerDataMap.entrySet()) {
			List<String> row = new ArrayList<>();
			row.add(normalizeText(entry.getKey()));
			row.add(normalizeText(entry.getValue()));
			pdTable.addDataRow(row);
		}
		//
		return pdTable;
	}

	private PDTable getPeakDataTable(List<? extends IPeak> peaks) {

		PDTable pdTable = new PDTable();
		pdTable.setTextOption(TextOption.SHORTEN);
		//
		pdTable.addColumn("ID", 15.0f);
		pdTable.addColumn("RT", 20.0f);
		pdTable.addColumn("Area%", 20.0f);
		pdTable.addColumn("Identification", 135.0f);
		//
		double totalPeakArea = getTotalPeakArea(peaks);
		//
		int i = 1;
		for(IPeak peak : peaks) {
			IPeakModel peakModel = peak.getPeakModel();
			List<String> row = new ArrayList<>();
			row.add("P" + i++);
			row.add(dfRetentionTime.format(peakModel.getRetentionTimeAtPeakMaximum() / AbstractChromatogram.MINUTE_CORRELATION_FACTOR));
			row.add(dfAreaPercent.format(getPercentagePeakArea(totalPeakArea, peak.getIntegratedArea())));
			row.add(getBestIdentification(peak.getTargets(), peakModel.getPeakMaximum().getRetentionIndex()));
			pdTable.addDataRow(row);
		}
		//
		return pdTable;
	}

	private PDTable getScanDataTable(List<IScan> scans) {

		PDTable pdTable = new PDTable();
		pdTable.setTextOption(TextOption.SHORTEN);
		//
		pdTable.addColumn("ID", 15.0f);
		pdTable.addColumn("RT", 20.0f);
		pdTable.addColumn("Scan#", 20.0f);
		pdTable.addColumn("Identification", 135.0f);
		//
		int i = 1;
		for(IScan scan : scans) {
			List<String> row = new ArrayList<>();
			row.add("S" + i++);
			row.add(dfRetentionTime.format(scan.getRetentionTime() / AbstractChromatogram.MINUTE_CORRELATION_FACTOR));
			row.add(Integer.toString(scan.getScanNumber()));
			row.add(getBestIdentification(scan.getTargets(), scan.getRetentionIndex()));
			pdTable.addDataRow(row);
		}
		//
		return pdTable;
	}

	private PDTable getQuantitationDataTable(List<? extends IPeak> peaks) {

		PDTable pdTable = new PDTable();
		pdTable.setTextOption(TextOption.SHORTEN);
		//
		pdTable.addColumn("#", 15.0f);
		pdTable.addColumn("Identification", 91.0f);
		pdTable.addColumn("Substance", 91.0f);
		pdTable.addColumn("RT", 20.0f);
		pdTable.addColumn("Area", 20.0f);
		pdTable.addColumn("Conc.", 20.0f);
		pdTable.addColumn("Unit", 20.0f);
		//
		int i = 1;
		for(IPeak peak : peaks) {
			IPeakModel peakModel = peak.getPeakModel();
			String identification = getBestIdentification(peak.getTargets(), peakModel.getPeakMaximum().getRetentionIndex());
			String retentionTime = dfRetentionTime.format(peakModel.getRetentionTimeAtPeakMaximum() / AbstractChromatogram.MINUTE_CORRELATION_FACTOR);
			for(IQuantitationEntry quantitationEntry : peak.getQuantitationEntries()) {
				List<String> row = new ArrayList<>();
				row.add("P" + i);
				row.add(identification);
				row.add(normalizeText(quantitationEntry.getName()));
				row.add(retentionTime);
				row.add(dfAreaNormal.format(quantitationEntry.getArea()));
				row.add(dfConcentration.format(quantitationEntry.getConcentration()));
				row.add(normalizeText(replaceText(quantitationEntry.getConcentrationUnit())));
				pdTable.addDataRow(row);
			}
			i++;
		}
		//
		return pdTable;
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

	private String getBestIdentification(Set<IIdentificationTarget> targets, float retentionIndex) {

		IdentificationTargetComparator identificationTargetComparator = new IdentificationTargetComparator(SortOrder.DESC, retentionIndex);
		ILibraryInformation libraryInformation = IIdentificationTarget.getBestLibraryInformation(targets, identificationTargetComparator);
		if(libraryInformation != null) {
			return normalizeText(libraryInformation.getName());
		} else {
			return "";
		}
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

	private String replaceText(String text) {

		if(text.contains("μ")) {
			return text.replace("μ", "u");
		} else {
			return text;
		}
	}

	private String normalizeText(String text) {

		return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("\\P{InBasic_Latin}", "?");
	}
}

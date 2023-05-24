/*******************************************************************************
 * Copyright (c) 2020, 2023 Lablicate GmbH.
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
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.eclipse.chemclipse.csd.model.core.IChromatogramCSD;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.comparator.IdentificationTargetComparator;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
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

import net.openchrom.xxd.converter.supplier.pdf.ui.io.profile.ImageRunnableProfile;
import net.openchrom.xxd.converter.supplier.pdf.ui.settings.ReportSettingsProfile;

public class ProfilePDF {

	private static final Logger logger = Logger.getLogger(ProfilePDF.class);
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
	private DateFormat dateFormat = ValueFormat.getDateFormatEnglish("yyyy/MM/dd");
	private DecimalFormat formatAreaPercent = ValueFormat.getDecimalFormatEnglish("0.00");
	//
	private ReportSettingsProfile settings;

	public ProfilePDF() {

		this(new ReportSettingsProfile());
	}

	public ProfilePDF(ReportSettingsProfile settings) {

		this.settings = settings;
	}

	public void createPDF(File file, IChromatogram<? extends IPeak> chromatogram, IProgressMonitor monitor) throws IOException {

		try (PDDocument document = new PDDocument()) {
			String name = chromatogram.getDataName();
			createChromatogramPDF(chromatogram, name, document, monitor);
			for(IChromatogram<?> chromatogramReference : chromatogram.getReferencedChromatograms()) {
				createChromatogramPDF(chromatogramReference, name, document, monitor);
			}
			document.save(file);
		} catch(Exception e) {
			logger.warn(e);
		}
	}

	private void createChromatogramPDF(IChromatogram<? extends IPeak> chromatogram, String name, PDDocument document, IProgressMonitor monitor) throws IOException {

		if(chromatogram != null) {
			String chromatogramName = getChromatogramName(chromatogram, name);
			ImageRunnableProfile imageRunnable = createImages(chromatogram, chromatogramName, document, 1);
			List<PDImageXObject> chromatogramImages = imageRunnable.getChromatogramImages();
			PDTable peakDataTable = getPeakDataTable(imageRunnable.getPeaks());
			/*
			 * Calculate the number of pages.
			 */
			int pages = 0;
			pages += 1;
			pages += peakDataTable.getNumberDataRows() / MAX_ROWS_PORTRAIT + 1;
			pages += chromatogramImages.size();
			//
			int page = printHeaderPages(document, chromatogramName, 1, pages);
			page = printTablePages(document, peakDataTable, chromatogramName, page, pages, false, monitor);
			printImagePages(document, chromatogramImages, page, pages, monitor);
		}
	}

	private String getChromatogramName(IChromatogram<? extends IPeak> chromatogram, String name) {

		if(chromatogram instanceof IChromatogramMSD) {
			name += " (MSD)";
		} else if(chromatogram instanceof IChromatogramCSD) {
			name += " (FID)";
		} else if(chromatogram instanceof IChromatogramWSD) {
			name += " (UV/Vis)";
		}
		//
		return name;
	}

	private int printHeaderPages(PDDocument document, String chromatogramName, int page, int pages) throws IOException {

		boolean landscape = false;
		PageUtil pageUtil = new PageUtil(document, new PageSettings(PDRectangle.A4, PageBase.TOP_LEFT, Unit.MM, landscape));
		printPageHeader(pageUtil);
		//
		pageUtil.printText(new TextElement(LEFT_BORDER, 45.0f, MAX_WIDTH_PORTRAIT).setText(dateFormat.format(new Date())).setReferenceX(ReferenceX.RIGHT));
		pageUtil.printText(new TextElement(LEFT_BORDER, 45.0f, MAX_WIDTH_PORTRAIT).setText("Certificate of Analysis - GC Profiling"));
		//
		pageUtil.printText(new TextElement(LEFT_BORDER, 60.0f, MAX_WIDTH_PORTRAIT).setText("Sample Identification"));
		pageUtil.printText(new TextElement(LEFT_BORDER, 65.0f, MAX_WIDTH_PORTRAIT).setText("Data Name: " + chromatogramName));
		//
		pageUtil.printText(new TextElement(LEFT_BORDER, 80.0f, MAX_WIDTH_PORTRAIT).setText("Analysis"));
		pageUtil.printText(new TextElement(LEFT_BORDER, 85.0f, MAX_WIDTH_PORTRAIT).setText("Method: " + settings.getReportMethod()));
		//
		printPageFooter(pageUtil, page, pages, landscape);
		pageUtil.close();
		//
		return ++page;
	}

	private int printImagePages(PDDocument document, List<PDImageXObject> chromatogramImages, int page, int pages, IProgressMonitor monitor) throws IOException {

		for(int i = 0; i < chromatogramImages.size(); i++) {
			PDImageXObject chromatogramImage = chromatogramImages.get(i);
			page = printImagePage(document, chromatogramImage, page, pages);
		}
		return page;
	}

	private int printImagePage(PDDocument document, PDImageXObject chromatogramImage, int page, int pages) throws IOException {

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

	private int printTablePages(PDDocument document, PDTable pdTable, String chromatogramName, int page, int pages, boolean landscape, IProgressMonitor monitor) throws IOException {

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
			page = printTablePage(document, pdTable, chromatogramName, page, pages, landscape);
		}
		return page;
	}

	private int printTablePage(PDDocument document, PDTable pdTable, String chromatogramName, int page, int pages, boolean landscape) throws IOException {

		PageUtil pageUtil = new PageUtil(document, new PageSettings(PDRectangle.A4, PageBase.TOP_LEFT, Unit.MM, landscape));
		printPageHeader(pageUtil);
		//
		pageUtil.printText(new TextElement(LEFT_BORDER, 45.0f, MAX_WIDTH_PORTRAIT).setText(chromatogramName));
		pageUtil.printText(new TextElement(LEFT_BORDER, 55.0f, MAX_WIDTH_PORTRAIT).setText(getTableHeaderText(pdTable)));
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

	private String getTableHeaderText(PDTable pdTable) {

		int size = pdTable.getNumberDataRows();
		StringBuilder builder = new StringBuilder();
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
				inputStream = ProfilePDF.class.getResourceAsStream("openchromlogo.jpg"); // $NON-NLS-1$
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

	private ImageRunnableProfile createImages(IChromatogram<? extends IPeak> chromatogram, String chromatogramName, PDDocument document, int numberOfPages) {

		int width = 1080;
		int height = (int)(width * (IMAGE_HEIGHT / IMAGE_WIDTH));
		int numberLargestPeaks = settings.getNumberLargestPeaks();
		ImageRunnableProfile imageRunnable = new ImageRunnableProfile(chromatogram, chromatogramName, document, numberOfPages, numberLargestPeaks, width, height);
		DisplayUtils.getDisplay().syncExec(imageRunnable);
		//
		return imageRunnable;
	}

	private PDTable getPeakDataTable(List<? extends IPeak> peaks) {

		boolean printAll = settings.isPrintAllTargets();
		//
		PDTable pdTable = new PDTable();
		pdTable.setTextOption(TextOption.SHORTEN);
		//
		pdTable.addColumn("Identification", 155.0f);
		pdTable.addColumn("Area%", 35.0f);
		//
		double totalPeakArea = getTotalPeakArea(peaks);
		double percents = 0;
		//
		for(IPeak peak : peaks) {
			if(!peak.getTargets().isEmpty()) {
				double areaPercent = getPercentagePeakArea(totalPeakArea, peak.getIntegratedArea());
				percents += areaPercent;
				List<IIdentificationTarget> sortedTargets = getSortedTargets(peak.getTargets(), peak.getPeakModel().getPeakMaximum().getRetentionIndex());
				String percentageArea = (areaPercent >= 0.005d) ? formatAreaPercent.format(areaPercent) : "tr"; // tr = trace
				/*
				 * Best Hit
				 */
				List<String> rowBest = new ArrayList<>();
				rowBest.add(getTargetName(sortedTargets.get(0)));
				rowBest.add(percentageArea);
				pdTable.addDataRow(rowBest);
				/*
				 * Other
				 */
				if(printAll) {
					for(int i = 1; i < sortedTargets.size(); i++) {
						List<String> rowOther = new ArrayList<>();
						rowOther.add("*" + getTargetName(sortedTargets.get(i)));
						rowOther.add("[" + percentageArea + "]");
						pdTable.addDataRow(rowOther);
					}
				}
			}
		}
		//
		List<String> row = new ArrayList<>();
		row.add("Total");
		row.add(formatAreaPercent.format(percents));
		pdTable.addDataRow(row);
		//
		return pdTable;
	}

	private String getTargetName(IIdentificationTarget target) {

		return normalizeText(target.getLibraryInformation().getName());
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

	private List<IIdentificationTarget> getSortedTargets(Set<IIdentificationTarget> targets, float retentionIndex) {

		List<IIdentificationTarget> sortedTargets = new ArrayList<>(targets);
		IdentificationTargetComparator identificationTargetComparator = new IdentificationTargetComparator(SortOrder.DESC, retentionIndex);
		Collections.sort(sortedTargets, identificationTargetComparator);
		return sortedTargets;
	}

	private String normalizeText(String text) {

		return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("\\P{InBasic_Latin}", "?");
	}
}

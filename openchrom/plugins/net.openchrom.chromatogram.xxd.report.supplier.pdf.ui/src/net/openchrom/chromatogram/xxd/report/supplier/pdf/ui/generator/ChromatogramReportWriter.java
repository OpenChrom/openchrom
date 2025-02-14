/*******************************************************************************
 * Copyright (c) 2019, 2025 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Philip Wenig - initial API and implementation
 * Matthias Mailänder - vector graphics port
 *******************************************************************************/
package net.openchrom.chromatogram.xxd.report.supplier.pdf.ui.generator;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.multipdf.LayerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.util.Matrix;
import org.eclipse.chemclipse.csd.model.core.IChromatogramCSD;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.AbstractChromatogram;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.model.quantitation.IQuantitationEntry;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.pdfbox.extensions.core.PDTable;
import org.eclipse.chemclipse.pdfbox.extensions.core.PageUtil;
import org.eclipse.chemclipse.pdfbox.extensions.elements.TableElement;
import org.eclipse.chemclipse.pdfbox.extensions.elements.TextElement;
import org.eclipse.chemclipse.pdfbox.extensions.settings.PageBase;
import org.eclipse.chemclipse.pdfbox.extensions.settings.PageSettings;
import org.eclipse.chemclipse.pdfbox.extensions.settings.ReferenceX;
import org.eclipse.chemclipse.pdfbox.extensions.settings.ReferenceY;
import org.eclipse.chemclipse.pdfbox.extensions.settings.TextOption;
import org.eclipse.chemclipse.pdfbox.extensions.settings.Unit;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.chemclipse.support.ui.workbench.DisplayUtils;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import net.openchrom.chromatogram.xxd.report.supplier.pdf.ui.Activator;
import net.openchrom.chromatogram.xxd.report.supplier.pdf.ui.settings.ChromatogramReportSettings;
import net.openchrom.chromatogram.xxd.report.supplier.pdf.ui.swt.ChartScreenshotRunnable;

public class ChromatogramReportWriter {

	private static final Logger logger = Logger.getLogger(ChromatogramReportWriter.class);

	private static Color OPENCHROM_RED = Color.decode("#980001");

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

	private static final float CHART_WIDTH = 270.0f;
	private static final float CHART_HEIGHT = 190.0f;

	private DecimalFormat decimalFormatRetentionTime = ValueFormat.getDecimalFormatEnglish("0.00");
	private DecimalFormat decimalFormatAreaPercent = ValueFormat.getDecimalFormatEnglish("0.000");
	private DecimalFormat decimalFormatArea = ValueFormat.getDecimalFormatEnglish("0.0#E0");
	private DecimalFormat decimalFormatConcentration = ValueFormat.getDecimalFormatEnglish("0.000");

	public void generate(File file, boolean append, List<IChromatogram> chromatograms, ChromatogramReportSettings reportSettings) throws IOException {

		try (PDDocument document = new PDDocument()) {
			for(IChromatogram chromatogram : chromatograms) {
				print(document, chromatogram, reportSettings);
				for(IChromatogram chromatogramReference : chromatogram.getReferencedChromatograms()) {
					print(document, chromatogramReference, reportSettings);
				}
			}
			document.save(file);
		} catch(IOException e) {
			logger.warn(e);
		}
	}

	private void printText(PageUtil pageUtil, float y, String text) throws IOException {

		pageUtil.printText(new TextElement(LEFT_BORDER, y, MAX_WIDTH_PORTRAIT).setText(text));
	}

	private void printPageBrandingHeader(PageUtil pageUtil) throws IOException {

		URL logoURL = FileLocator.find(Activator.getDefault().getBundle(), new Path("icons/logo.pdf"));
		PDDocument logoDocument = PDDocument.load(logoURL.openStream());
		LayerUtility layerUtility = new LayerUtility(pageUtil.getDocument());
		PDFormXObject logo = layerUtility.importPageAsForm(logoDocument, 0);
		try (PDPageContentStream contentStream = new PDPageContentStream(pageUtil.getDocument(), pageUtil.getPage(), AppendMode.APPEND, false)) {
			contentStream.transform(Matrix.getTranslateInstance(pageUtil.getPositionBaseX(LEFT_BORDER), pageUtil.getPositionBaseY(TOP_BORDER + 10)));
			contentStream.transform(Matrix.getScaleInstance(0.6f, 0.6f));
			contentStream.drawForm(logo);
		}
		/*
		 * Wordmark and Slogan
		 */
		pageUtil.printText(new TextElement(LEFT_BORDER + 18, TOP_BORDER, MAX_WIDTH_PORTRAIT).setText("OPEN") //
				.setFontSize(32f).setColor(OPENCHROM_RED).setFont(PDType1Font.HELVETICA_BOLD));
		pageUtil.printText(new TextElement(LEFT_BORDER + 50, TOP_BORDER, MAX_WIDTH_PORTRAIT).setText("Chrom").setFontSize(32));
		pageUtil.printText(new TextElement(LEFT_BORDER + 84, TOP_BORDER - 4, MAX_WIDTH_PORTRAIT).setText("®").setFontSize(20).setColor(Color.GRAY));
		printText(pageUtil, 24f, "The Open Source Alternative for Chromatography and Spectrometry");
	}

	private void print(PDDocument document, IChromatogram chromatogram, ChromatogramReportSettings reportSettings) throws IOException {

		ChartScreenshotRunnable chartScreenshotRunnable = createChartScreenshotRunnable(chromatogram, reportSettings);

		PDTable peakDataTable = getPeakDataTable(chartScreenshotRunnable.getPeaks());
		PDTable scanDataTable = getScanDataTable(chartScreenshotRunnable.getScans());
		PDTable quantitationDataTable = getQuantitationDataTable(chromatogram.getPeaks());
		PDTable headerDataTable = getHeaderDataTable(chromatogram.getHeaderDataMap());

		int pages = headerDataTable.getNumberDataRows() / MAX_ROWS_PORTRAIT + 1;

		pages += reportSettings.getNumberOfImagesPerPage();

		if(peakDataTable.getNumberDataRows() > 0) {
			pages += peakDataTable.getNumberDataRows() / MAX_ROWS_PORTRAIT + 1;
		}
		if(scanDataTable.getNumberDataRows() > 0) {
			pages += scanDataTable.getNumberDataRows() / MAX_ROWS_PORTRAIT + 1;
		}
		if(quantitationDataTable.getNumberDataRows() > 0) {
			pages += quantitationDataTable.getNumberDataRows() / MAX_ROWS_LANDSCAPE + 1;
		}

		String chromatogramName = getChromatogramName(chromatogram);
		int page = printTablePages(document, headerDataTable, "Header Table:", chromatogramName, 1, pages, false);

		page = printCharts(chartScreenshotRunnable, document, page);

		if(peakDataTable.getNumberDataRows() > 0) {
			page = printTablePages(document, peakDataTable, "Peak Table:", chromatogramName, page, pages, false);
		}
		if(scanDataTable.getNumberDataRows() > 0) {
			page = printTablePages(document, scanDataTable, "Scan Table:", chromatogramName, page, pages, false);
		}
		if(quantitationDataTable.getNumberDataRows() > 0) {
			printTablePages(document, quantitationDataTable, "Quantitation Table:", chromatogramName, page, pages, true);
		}
	}

	private int printCharts(ChartScreenshotRunnable chartScreenshotRunnable, PDDocument document, int page) throws IOException {

		for(File chromatogramFile : chartScreenshotRunnable.getChromatogramFiles()) {
			PDDocument documentChart = PDDocument.load(chromatogramFile);
			for(PDPage pageChart : documentChart.getPages()) {
				document.addPage(pageChart);
				page++;
			}
		}
		return page;
	}

	private ChartScreenshotRunnable createChartScreenshotRunnable(IChromatogram chromatogram, ChromatogramReportSettings reportSettings) {

		int width = 1080;
		int height = (int)(width * (CHART_HEIGHT / CHART_WIDTH));
		ChartScreenshotRunnable screenshotRunnable = new ChartScreenshotRunnable(chromatogram, width, height, reportSettings.getNumberOfImagesPerPage());
		DisplayUtils.getDisplay().syncExec(screenshotRunnable);
		return screenshotRunnable;
	}

	private String getChromatogramName(IChromatogram chromatogram) {

		String name = chromatogram.getName();
		if(chromatogram instanceof IChromatogramMSD) {
			name += " (MSD)";
		} else if(chromatogram instanceof IChromatogramCSD) {
			name += " (CSD)";
		} else if(chromatogram instanceof IChromatogramWSD) {
			name += " (WSD)";
		}

		return name;
	}

	private PDTable getPeakDataTable(List<? extends IPeak> peaks) {

		PDTable pdTable = new PDTable();
		pdTable.setTextOption(TextOption.SHORTEN);

		pdTable.addColumn("ID", 15.0f);
		pdTable.addColumn("RT", 20.0f);
		pdTable.addColumn("Area%", 20.0f);
		pdTable.addColumn("Identification", 135.0f);

		double totalPeakArea = getTotalPeakArea(peaks);

		int i = 1;
		for(IPeak peak : peaks) {
			IPeakModel peakModel = peak.getPeakModel();
			List<String> row = new ArrayList<>();
			row.add("P" + i++);
			row.add(decimalFormatRetentionTime.format(peakModel.getRetentionTimeAtPeakMaximum() / IChromatogramOverview.MINUTE_CORRELATION_FACTOR));
			row.add(decimalFormatAreaPercent.format(getPercentagePeakArea(totalPeakArea, peak.getIntegratedArea())));
			row.add(getBestIdentification(peak.getTargets(), peakModel.getPeakMaximum().getRetentionIndex()));
			pdTable.addDataRow(row);
		}

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

	private PDTable getScanDataTable(List<IScan> scans) {

		PDTable pdTable = new PDTable();
		pdTable.setTextOption(TextOption.SHORTEN);

		pdTable.addColumn("ID", 15.0f);
		pdTable.addColumn("RT", 20.0f);
		pdTable.addColumn("Scan#", 20.0f);
		pdTable.addColumn("Identification", 135.0f);

		int i = 1;
		for(IScan scan : scans) {
			List<String> row = new ArrayList<>();
			row.add("S" + i++);
			row.add(decimalFormatRetentionTime.format(scan.getRetentionTime() / IChromatogramOverview.MINUTE_CORRELATION_FACTOR));
			row.add(Integer.toString(scan.getScanNumber()));
			row.add(getBestIdentification(scan.getTargets(), scan.getRetentionIndex()));
			pdTable.addDataRow(row);
		}

		return pdTable;
	}

	private String getBestIdentification(Set<IIdentificationTarget> targets, float retentionIndex) {

		ILibraryInformation libraryInformation = IIdentificationTarget.getLibraryInformation(targets, retentionIndex);
		if(libraryInformation != null) {
			return normalizeText(libraryInformation.getName());
		} else {
			return "";
		}
	}

	private PDTable getQuantitationDataTable(List<? extends IPeak> peaks) {

		PDTable pdTable = new PDTable();
		pdTable.setTextOption(TextOption.SHORTEN);

		pdTable.addColumn("#", 15.0f);
		pdTable.addColumn("Identification", 91.0f);
		pdTable.addColumn("Substance", 91.0f);
		pdTable.addColumn("RT", 20.0f);
		pdTable.addColumn("Area", 20.0f);
		pdTable.addColumn("Conc.", 20.0f);
		pdTable.addColumn("Unit", 20.0f);

		int i = 1;
		for(IPeak peak : peaks) {
			IPeakModel peakModel = peak.getPeakModel();
			String identification = getBestIdentification(peak.getTargets(), peakModel.getPeakMaximum().getRetentionIndex());
			String retentionTime = decimalFormatRetentionTime.format(peakModel.getRetentionTimeAtPeakMaximum() / AbstractChromatogram.MINUTE_CORRELATION_FACTOR);
			for(IQuantitationEntry quantitationEntry : peak.getQuantitationEntries()) {
				List<String> row = new ArrayList<>();
				row.add("P" + i);
				row.add(identification);
				row.add(normalizeText(quantitationEntry.getName()));
				row.add(retentionTime);
				row.add(decimalFormatArea.format(quantitationEntry.getArea()));
				row.add(decimalFormatConcentration.format(quantitationEntry.getConcentration()));
				row.add(normalizeText(replaceText(quantitationEntry.getConcentrationUnit())));
				pdTable.addDataRow(row);
			}
			i++;
		}

		return pdTable;
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

	private void printPageFooter(PageUtil pageUtil, int page, int pages, boolean landscape) throws IOException {

		float y;
		float maxWidth;

		if(landscape) {
			y = 200.0f;
			maxWidth = MAX_WIDTH_LANDSCAPE;
		} else {
			y = 287.0f;
			maxWidth = MAX_WIDTH_PORTRAIT;
		}

		pageUtil.printText(new TextElement(LEFT_BORDER, y, maxWidth).setReferenceX(ReferenceX.RIGHT).setReferenceY(ReferenceY.BOTTOM).setText("Page " + page + "/" + pages));
	}

	private int printTablePages(PDDocument document, PDTable pdTable, String title, String chromatogramName, int page, int pages, boolean landscape) throws IOException {

		int maxRows = (landscape) ? MAX_ROWS_LANDSCAPE : MAX_ROWS_PORTRAIT;

		int parts = pdTable.getNumberDataRows() / maxRows + 1;
		for(int part = 0; part < parts; part++) {
			int range = part * maxRows;
			int startIndex = range;
			int stopIndex = range + maxRows;
			stopIndex = (stopIndex > pdTable.getNumberDataRows()) ? pdTable.getNumberDataRows() : stopIndex;
			pdTable.setStartIndex(startIndex);
			pdTable.setStopIndex(stopIndex);
			page = printTablePage(document, pdTable, title, chromatogramName, page, pages, landscape);
		}
		return page;
	}

	private int printTablePage(PDDocument document, PDTable pdTable, String title, String chromatogramName, int page, int pages, boolean landscape) throws IOException {

		PageUtil pageUtil = new PageUtil(document, new PageSettings(PDRectangle.A4, PageBase.TOP_LEFT, Unit.MM, landscape));
		printPageBrandingHeader(pageUtil);

		pageUtil.printText(new TextElement(LEFT_BORDER, 45.0f, MAX_WIDTH_PORTRAIT).setText("Chromatogram: " + chromatogramName));
		pageUtil.printText(new TextElement(LEFT_BORDER, 50.0f, MAX_WIDTH_PORTRAIT).setText(getTableHeaderText(pdTable, title)));

		TableElement tableElement = new TableElement(LEFT_BORDER, 60.0f, LINE_HEIGHT);
		tableElement.setTextOffsetX(TEXT_OFFSET_X);
		tableElement.setTextOffsetY(TEXT_OFFSET_Y);
		tableElement.setLineWidth(LINE_WIDTH);
		tableElement.setPDTable(pdTable);
		pageUtil.printTable(tableElement);

		printPageFooter(pageUtil, page, pages, landscape);
		pageUtil.close();

		return ++page;
	}

	private PDTable getHeaderDataTable(Map<String, String> headerDataMap) {

		PDTable pdTable = new PDTable();
		pdTable.setTextOption(TextOption.SHORTEN);

		pdTable.addColumn("Name", 95.0f);
		pdTable.addColumn("Value", 95.0f);

		for(Map.Entry<String, String> entry : headerDataMap.entrySet()) {
			String name = entry.getKey();
			String value = entry.getValue();
			if(isJSON(value)) {
				final ObjectMapper mapper = new ObjectMapper();
				try {
					MethodSettings methodSettings = mapper.readValue(value, MethodSettings.class);
					for(String key : methodSettings.getSettings().keySet()) {
						name = StringUtils.capitalize(StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(key), ' '));
						value = methodSettings.getSettings().get(key).toString();
						List<String> row = new ArrayList<>();
						row.add(normalizeText(name));
						row.add(normalizeText(value));
						pdTable.addDataRow(row);
					}
				} catch(MismatchedInputException e) {
					// ignore
				} catch(IOException e) {
					logger.warn(e);
				}
			} else {
				List<String> row = new ArrayList<>();
				row.add(normalizeText(name));
				row.add(normalizeText(value));
				pdTable.addDataRow(row);
			}
		}

		return pdTable;
	}

	private static boolean isJSON(String tree) {

		try {
			final ObjectMapper mapper = new ObjectMapper();
			mapper.readTree(tree);
			return true;
		} catch(Exception e) {
			return false;
		}
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
}
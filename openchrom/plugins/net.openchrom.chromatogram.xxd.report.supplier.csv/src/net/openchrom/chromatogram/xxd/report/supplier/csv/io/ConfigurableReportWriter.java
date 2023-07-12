/*******************************************************************************
 * Copyright (c) 2022, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Philip Wenig - header options have been added
 *******************************************************************************/
package net.openchrom.chromatogram.xxd.report.supplier.csv.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.csv.CSVPrinter;
import org.eclipse.chemclipse.model.comparator.IdentificationTargetComparator;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.model.core.IChromatogramPeak;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.identifier.IComparisonResult;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.model.implementation.PeakResolution;
import org.eclipse.chemclipse.model.quantitation.IInternalStandard;
import org.eclipse.chemclipse.model.quantitation.IQuantitationEntry;
import org.eclipse.chemclipse.support.comparator.SortOrder;

import net.openchrom.chromatogram.xxd.report.supplier.csv.model.ReportColumns;
import net.openchrom.chromatogram.xxd.report.supplier.csv.settings.ChromatogramReportSettings;

public class ConfigurableReportWriter {

	public void generate(File file, boolean append, List<IChromatogram<? extends IPeak>> chromatograms, ChromatogramReportSettings reportSettings) throws IOException {

		boolean fileExists = file.exists() && file.length() > 0;
		try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(file, append), reportSettings.getFormat())) {
			/*
			 * Header
			 */
			ReportColumns reportColumns = reportSettings.getReportColumns();
			printHeader(csvPrinter, reportColumns, reportSettings, fileExists);
			boolean printSectionSeparator = reportSettings.isPrintSectionSeparator();
			/*
			 * Data
			 */
			for(IChromatogram<? extends IPeak> chromatogram : chromatograms) {
				printChromatogramData(csvPrinter, reportColumns, chromatogram, printSectionSeparator);
				if(reportSettings.reportReferencedChromatograms()) {
					for(IChromatogram<?> referencedChromatograms : chromatogram.getReferencedChromatograms()) {
						printChromatogramData(csvPrinter, reportColumns, referencedChromatograms, printSectionSeparator);
					}
				}
			}
		}
	}

	private void printHeader(CSVPrinter csvPrinter, ReportColumns reportColumns, ChromatogramReportSettings reportSettings, boolean fileExists) throws IOException {

		boolean printHeader = reportSettings.isPrintResultsHeader();
		if(printHeader && fileExists) {
			printHeader = reportSettings.isAppendResultsHeader();
		}
		//
		if(printHeader) {
			printHeader(csvPrinter, reportColumns);
		}
	}

	private void printHeader(CSVPrinter csvPrinter, ReportColumns reportColumns) throws IOException {

		for(String reportColumn : reportColumns) {
			csvPrinter.print(reportColumn);
		}
		csvPrinter.println();
	}

	private void printChromatogramData(CSVPrinter csvPrinter, ReportColumns reportColumns, IChromatogram<? extends IPeak> chromatogram, boolean printSectionSeparator) throws IOException {

		int peakNumber = 1;
		List<Object> records = new ArrayList<>();
		for(IPeak peak : chromatogram.getPeaks()) {
			for(String reportColumn : reportColumns) {
				if(reportColumn.equals(ReportColumns.CHROMATOGRAM_NAME)) {
					records.add(chromatogram.getName());
				}
				Map<String, String> headerMap = chromatogram.getHeaderDataMap();
				for(Entry<String, String> header : headerMap.entrySet()) {
					if(reportColumn.equals(header.getKey())) {
						records.add(header.getValue());
					}
				}
				if(reportColumn.equals(ReportColumns.NUMBER_PEAKS)) {
					records.add(chromatogram.getNumberOfPeaks());
				}
				if(reportColumn.equals(ReportColumns.PEAK_NUMBER)) {
					records.add(peakNumber);
				}
				if(reportColumn.equals(ReportColumns.PURITY)) {
					if(peak instanceof IChromatogramPeak chromatogramPeak) {
						records.add(chromatogramPeak.getPurity());
					} else {
						records.add("");
					}
				}
				if(reportColumn.equals(ReportColumns.SIGNAL_TO_NOISE)) {
					if(peak instanceof IChromatogramPeak chromatogramPeak) {
						records.add(chromatogramPeak.getSignalToNoiseRatio());
					} else {
						records.add("");
					}
				}
				if(reportColumn.equals(ReportColumns.COMPONENTS)) {
					records.add(peak.getSuggestedNumberOfComponents());
				}
				if(reportColumn.equals(ReportColumns.PEAK_AREA)) {
					records.add(peak.getIntegratedArea());
				}
				if(reportColumn.equals(ReportColumns.INTEGRATOR)) {
					records.add(peak.getIntegratorDescription());
				}
				if(reportColumn.equals(ReportColumns.MODEL)) {
					records.add(peak.getModelDescription());
				}
				if(reportColumn.equals(ReportColumns.DETECTOR)) {
					records.add(peak.getDetectorDescription());
				}
				if(reportColumn.equals(ReportColumns.QUANTIFIER)) {
					records.add(peak.getQuantifierDescription());
				}
				if(reportColumn.equals(ReportColumns.CLASSIFIER)) {
					records.add(peak.getClassifier());
				}
				IPeakModel peakModel = peak.getPeakModel();
				if(reportColumn.equals(ReportColumns.RETENTION_TIME)) {
					int retentionTimePeakMax = peakModel.getRetentionTimeAtPeakMaximum();
					records.add(retentionTimePeakMax / IChromatogramOverview.MINUTE_CORRELATION_FACTOR);
				}
				if(reportColumn.equals(ReportColumns.PEAK_HEIGHT)) {
					records.add(peakModel.getPeakAbundanceByInflectionPoints());
				}
				if(reportColumn.equals(ReportColumns.PEAK_WIDTH_BASELINE_FROM_INFLECTION_POINTS)) {
					records.add(peakModel.getWidthBaselineByInflectionPoints() / IChromatogramOverview.MINUTE_CORRELATION_FACTOR);
				}
				if(reportColumn.equals(ReportColumns.PEAK_WIDTH_BASELINE_TOTAL)) {
					records.add(peakModel.getWidthBaselineTotal() / IChromatogramOverview.MINUTE_CORRELATION_FACTOR);
				}
				if(reportColumn.equals(ReportColumns.PEAK_WIDTH_BY_INFLECTION_POINTS)) {
					records.add(peakModel.getWidthByInflectionPoints() / IChromatogramOverview.MINUTE_CORRELATION_FACTOR);
				}
				if(reportColumn.equals(ReportColumns.PEAK_WIDTH_0)) {
					records.add(peakModel.getWidthByInflectionPoints(0.0f) / IChromatogramOverview.MINUTE_CORRELATION_FACTOR);
				}
				if(reportColumn.equals(ReportColumns.PEAK_WIDTH_10)) {
					records.add(peakModel.getWidthByInflectionPoints(0.10f) / IChromatogramOverview.MINUTE_CORRELATION_FACTOR);
				}
				if(reportColumn.equals(ReportColumns.PEAK_WIDTH_15)) {
					records.add(peakModel.getWidthByInflectionPoints(0.15f) / IChromatogramOverview.MINUTE_CORRELATION_FACTOR);
				}
				if(reportColumn.equals(ReportColumns.PEAK_WIDTH_50)) {
					records.add(peakModel.getWidthByInflectionPoints(0.50f) / IChromatogramOverview.MINUTE_CORRELATION_FACTOR);
				}
				if(reportColumn.equals(ReportColumns.PEAK_WIDTH_85)) {
					records.add(peakModel.getWidthByInflectionPoints(0.85f) / IChromatogramOverview.MINUTE_CORRELATION_FACTOR);
				}
				if(reportColumn.equals(ReportColumns.LEADING)) {
					records.add(peakModel.getLeading());
				}
				if(reportColumn.equals(ReportColumns.TAILING)) {
					records.add(peakModel.getTailing());
				}
				if(reportColumn.equals(ReportColumns.START_RT)) {
					records.add(peakModel.getStartRetentionTime());
				}
				if(reportColumn.equals(ReportColumns.STOP_RT)) {
					records.add(peakModel.getStopRetentionTime());
				}
				if(reportColumn.equals(ReportColumns.RETENTION_INDEX)) {
					records.add(peakModel.getPeakMaximum().getRetentionIndex());
				}
				if(reportColumn.equals(ReportColumns.SCANS)) {
					records.add(peakModel.getNumberOfScans());
				}
				if(reportColumn.equals(ReportColumns.PEAK_RESOLUTION)) {
					int peakIndex = chromatogram.getPeaks().indexOf(peak);
					if(peakIndex != -1) {
						PeakResolution resolution = null;
						if(peakIndex < chromatogram.getNumberOfPeaks() - 1) {
							IPeak nextPeak = chromatogram.getPeaks().get(peakIndex + 1);
							resolution = new PeakResolution(peak, nextPeak);
						} else if(peakIndex > 0) {
							IPeak previousPeak = chromatogram.getPeaks().get(peakIndex - 1);
							resolution = new PeakResolution(previousPeak, peak);
						}
						if(resolution != null) {
							records.add(resolution.calculate());
						} else {
							records.add("");
						}
					}
				}
				Set<IIdentificationTarget> peakTargets = peak.getTargets();
				ILibraryInformation libraryInformation = null;
				IComparisonResult comparisonResult = null;
				if(!peakTargets.isEmpty()) {
					IdentificationTargetComparator identificationTargetComparator = new IdentificationTargetComparator(SortOrder.DESC);
					List<IIdentificationTarget> peakTargetList = new ArrayList<>(peakTargets);
					Collections.sort(peakTargetList, identificationTargetComparator);
					IIdentificationTarget peakTarget = peakTargetList.get(0);
					libraryInformation = peakTarget.getLibraryInformation();
					comparisonResult = peakTarget.getComparisonResult();
				}
				if(reportColumn.equals(ReportColumns.TARGET)) {
					if(libraryInformation != null) {
						records.add(libraryInformation.getName());
					} else {
						records.add("");
					}
				}
				if(reportColumn.equals(ReportColumns.CAS)) {
					if(libraryInformation != null) {
						records.add(libraryInformation.getCasNumber());
					} else {
						records.add("");
					}
				}
				if(reportColumn.equals(ReportColumns.MATCH_FACTOR)) {
					if(comparisonResult != null) {
						records.add(comparisonResult.getMatchFactor());
					} else {
						records.add("");
					}
				}
				if(reportColumn.equals(ReportColumns.MATCH_FACTOR_REVERSED)) {
					if(comparisonResult != null) {
						records.add(comparisonResult.getReverseMatchFactor());
					} else {
						records.add("");
					}
				}
				if(reportColumn.equals(ReportColumns.PROBABILITY)) {
					if(comparisonResult != null) {
						records.add(comparisonResult.getProbability());
					} else {
						records.add("");
					}
				}
				if(reportColumn.equals(ReportColumns.MOL_WEIGHT)) {
					if(libraryInformation != null) {
						records.add(libraryInformation.getMolWeight());
					} else {
						records.add("");
					}
				}
				if(reportColumn.equals(ReportColumns.IDENTIFIER)) {
					if(libraryInformation != null) {
						records.add(libraryInformation.getReferenceIdentifier());
					} else {
						records.add("");
					}
				}
				if(reportColumn.equals(ReportColumns.DATABASE)) {
					if(libraryInformation != null) {
						records.add(libraryInformation.getDatabase());
					} else {
						records.add("");
					}
				}
				List<IInternalStandard> internalStandards = peak.getInternalStandards();
				IInternalStandard internalStandard = null;
				if(!internalStandards.isEmpty()) {
					internalStandard = internalStandards.get(0);
				}
				if(reportColumn.equals(ReportColumns.INTERNAL_STANDARD_CHEMICAL_CLASS)) {
					if(internalStandard != null) {
						records.add(internalStandard.getChemicalClass());
					} else {
						records.add("");
					}
				}
				if(reportColumn.equals(ReportColumns.INTERNAL_STANDARD_CONCENTRATION)) {
					if(internalStandard != null) {
						records.add(internalStandard.getConcentration());
					} else {
						records.add("");
					}
				}
				if(reportColumn.equals(ReportColumns.INTERNAL_STANDARD_CONCENTRATION_UNIT)) {
					if(internalStandard != null) {
						records.add(internalStandard.getConcentrationUnit());
					} else {
						records.add("");
					}
				}
				if(reportColumn.equals(ReportColumns.INTERNAL_STANDARD_NAME)) {
					if(internalStandard != null) {
						records.add(internalStandard.getName());
					} else {
						records.add("");
					}
				}
				if(reportColumn.equals(ReportColumns.INTERNAL_STANDARD_COMPENSATION_FACTOR)) {
					if(internalStandard != null) {
						records.add(internalStandard.getCompensationFactor());
					} else {
						records.add("");
					}
				}
				List<IQuantitationEntry> quantitationEntries = peak.getQuantitationEntries();
				IQuantitationEntry quantitationEntry = null;
				if(!quantitationEntries.isEmpty()) {
					quantitationEntry = quantitationEntries.get(0);
				}
				if(reportColumn.equals(ReportColumns.QUANTITATION_ENTRY_AREA)) {
					if(quantitationEntry != null) {
						records.add(quantitationEntry.getArea());
					} else {
						records.add("");
					}
				}
				if(reportColumn.equals(ReportColumns.QUANTITATION_CALIBRATION_METHOD)) {
					if(quantitationEntry != null) {
						records.add(quantitationEntry.getCalibrationMethod());
					} else {
						records.add("");
					}
				}
				if(reportColumn.equals(ReportColumns.QUANTITATION_CHEMICAL_CLASS)) {
					if(quantitationEntry != null) {
						records.add(quantitationEntry.getChemicalClass());
					} else {
						records.add("");
					}
				}
				if(reportColumn.equals(ReportColumns.QUANTITATION_CONCENTRATION)) {
					if(quantitationEntry != null) {
						records.add(quantitationEntry.getConcentration());
					} else {
						records.add("");
					}
				}
				if(reportColumn.equals(ReportColumns.QUANTITATION_CONCENTRATION_UNIT)) {
					if(quantitationEntry != null) {
						records.add(quantitationEntry.getConcentrationUnit());
					} else {
						records.add("");
					}
				}
				if(reportColumn.equals(ReportColumns.QUANTITATION_DESCRIPTION)) {
					if(quantitationEntry != null) {
						records.add(quantitationEntry.getDescription());
					} else {
						records.add("");
					}
				}
				if(reportColumn.equals(ReportColumns.QUANTITATION_NAME)) {
					if(quantitationEntry != null) {
						records.add(quantitationEntry.getName());
					} else {
						records.add("");
					}
				}
				if(reportColumn.equals(ReportColumns.QUANTITATION_FLAG)) {
					if(quantitationEntry != null) {
						records.add(quantitationEntry.getQuantitationFlag().label());
					} else {
						records.add("");
					}
				}
				if(reportColumn.equals(ReportColumns.QUANTITATION_SIGNAL)) {
					if(quantitationEntry != null) {
						records.add(quantitationEntry.getSignal());
					} else {
						records.add("");
					}
				}
				if(reportColumn.equals(ReportColumns.QUANTITATION_CROSS_ZERO)) {
					if(quantitationEntry != null) {
						records.add(quantitationEntry.getUsedCrossZero());
					} else {
						records.add("");
					}
				}
				if(reportColumn.equals(ReportColumns.QUANTITATION_REFERENCE)) {
					List<String> quantitationReferences = peak.getQuantitationReferences();
					if(!quantitationReferences.isEmpty()) {
						records.add(quantitationReferences.get(0));
					} else {
						records.add("");
					}
				}
			}
			peakNumber++;
			csvPrinter.printRecord(records);
			records.clear();
		}
		//
		if(printSectionSeparator) {
			csvPrinter.println();
		}
	}
}

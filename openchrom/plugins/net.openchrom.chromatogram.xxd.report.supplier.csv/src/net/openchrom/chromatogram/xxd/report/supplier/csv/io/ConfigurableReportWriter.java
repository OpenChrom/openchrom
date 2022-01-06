/*******************************************************************************
 * Copyright (c) 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.xxd.report.supplier.csv.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
import org.eclipse.chemclipse.support.comparator.SortOrder;

import net.openchrom.chromatogram.xxd.report.supplier.csv.model.ReportColumns;
import net.openchrom.chromatogram.xxd.report.supplier.csv.settings.ChromatogramReportSettings;

public class ConfigurableReportWriter {

	public void generate(File file, boolean append, List<IChromatogram<? extends IPeak>> chromatograms, ChromatogramReportSettings reportSettings) throws IOException {

		try (FileWriter fileWriter = new FileWriter(file, append)) {
			try (CSVPrinter csvPrinter = new CSVPrinter(fileWriter, reportSettings.getFormat())) {
				ReportColumns reportColumns = reportSettings.getReportColumns();
				printHeader(csvPrinter, reportColumns);
				for(IChromatogram<? extends IPeak> chromatogram : chromatograms) {
					printChromatogramData(csvPrinter, reportColumns, chromatogram);
					if(reportSettings.reportReferencedChromatograms()) {
						for(IChromatogram<?> referencedChromatograms : chromatogram.getReferencedChromatograms()) {
							printChromatogramData(csvPrinter, reportColumns, referencedChromatograms);
						}
					}
				}
			}
		}
	}

	void printHeader(CSVPrinter csvPrinter, ReportColumns reportColumns) throws IOException {

		for(String reportColumn : reportColumns) {
			csvPrinter.print(reportColumn);
		}
		csvPrinter.println();
	}

	void printChromatogramData(CSVPrinter csvPrinter, ReportColumns reportColumns, IChromatogram<? extends IPeak> chromatogram) throws IOException {

		int peakNumber = 1;
		for(IPeak peak : chromatogram.getPeaks()) {
			for(String reportColumn : reportColumns) {
				if(reportColumn.equals(ReportColumns.CHROMATOGRAM_NAME)) {
					csvPrinter.print(chromatogram.getName());
				}
				if(reportColumn.equals(ReportColumns.BARCODE)) {
					csvPrinter.print(chromatogram.getBarcode());
				}
				if(reportColumn.equals(ReportColumns.BARCODE_TYPE)) {
					csvPrinter.print(chromatogram.getBarcodeType());
				}
				if(reportColumn.equals(ReportColumns.DATA_NAME)) {
					csvPrinter.print(chromatogram.getDataName());
				}
				if(reportColumn.equals(ReportColumns.DATE)) {
					csvPrinter.print(chromatogram.getDate());
				}
				if(reportColumn.equals(ReportColumns.DETAILED_INFO)) {
					csvPrinter.print(chromatogram.getDetailedInfo());
				}
				if(reportColumn.equals(ReportColumns.MISC_INFO)) {
					csvPrinter.print(chromatogram.getMiscInfo());
				}
				if(reportColumn.equals(ReportColumns.MISC_INFO_SEPARATED)) {
					csvPrinter.print(chromatogram.getMiscInfoSeparated());
				}
				if(reportColumn.equals(ReportColumns.OPERATOR)) {
					csvPrinter.print(chromatogram.getOperator());
				}
				if(reportColumn.equals(ReportColumns.SAMPLE_GROUP)) {
					csvPrinter.print(chromatogram.getSampleGroup());
				}
				if(reportColumn.equals(ReportColumns.SAMPLE_WEIGHT)) {
					csvPrinter.print(chromatogram.getSampleWeight());
				}
				if(reportColumn.equals(ReportColumns.SAMPLE_WEIGHT_UNIT)) {
					csvPrinter.print(chromatogram.getSampleWeightUnit());
				}
				if(reportColumn.equals(ReportColumns.SHORT_INFO)) {
					csvPrinter.print(chromatogram.getShortInfo());
				}
				if(reportColumn.equals(ReportColumns.NUMBER_PEAKS)) {
					csvPrinter.print(chromatogram.getNumberOfPeaks());
				}
				if(reportColumn.equals(ReportColumns.PEAK_NUMBER)) {
					csvPrinter.print(peakNumber);
				}
				if(reportColumn.equals(ReportColumns.RETENTION_TIME)) {
					IPeakModel peakModel = peak.getPeakModel();
					int retentionTimePeakMax = peakModel.getRetentionTimeAtPeakMaximum();
					csvPrinter.print(retentionTimePeakMax / IChromatogramOverview.MINUTE_CORRELATION_FACTOR);
				}
				if(reportColumn.equals(ReportColumns.PURITY)) {
					if(peak instanceof IChromatogramPeak) {
						IChromatogramPeak chromatogramPeak = (IChromatogramPeak)peak;
						csvPrinter.print(chromatogramPeak.getPurity());
					}
				}
				if(reportColumn.equals(ReportColumns.PEAK_AREA)) {
					csvPrinter.print(peak.getIntegratedArea());
				}
				if(reportColumn.equals(ReportColumns.INTEGRATOR)) {
					csvPrinter.print(peak.getIntegratorDescription());
				}
				Set<IIdentificationTarget> peakTargets = peak.getTargets();
				if(!peakTargets.isEmpty()) {
					IdentificationTargetComparator identificationTargetComparator = new IdentificationTargetComparator(SortOrder.DESC);
					List<IIdentificationTarget> peakTargetList = new ArrayList<>(peakTargets);
					Collections.sort(peakTargetList, identificationTargetComparator);
					IIdentificationTarget peakTarget = peakTargetList.get(0);
					ILibraryInformation libraryInformation = peakTarget.getLibraryInformation();
					IComparisonResult comparisonResult = peakTarget.getComparisonResult();
					if(reportColumn.equals(ReportColumns.TARGET)) {
						csvPrinter.print(libraryInformation.getName());
					}
					if(reportColumn.equals(ReportColumns.CAS)) {
						csvPrinter.print(libraryInformation.getCasNumber());
					}
					if(reportColumn.equals(ReportColumns.MATCH_FACTOR)) {
						csvPrinter.print(comparisonResult.getMatchFactor());
					}
					if(reportColumn.equals(ReportColumns.MATCH_FACTOR_REVERSED)) {
						csvPrinter.print(comparisonResult.getReverseMatchFactor());
					}
					if(reportColumn.equals(ReportColumns.PROBABILITY)) {
						csvPrinter.print(comparisonResult.getProbability());
					}
					if(reportColumn.equals(ReportColumns.MOL_WEIGHT)) {
						csvPrinter.print(libraryInformation.getMolWeight());
					}
					if(reportColumn.equals(ReportColumns.IDENTIFIER)) {
						csvPrinter.print(libraryInformation.getReferenceIdentifier());
					}
					if(reportColumn.equals(ReportColumns.DATABASE)) {
						csvPrinter.print(libraryInformation.getDatabase());
					}
				}
			}
			peakNumber++;
			csvPrinter.println();
		}
	}
}

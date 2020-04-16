/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.chromatogram;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.numeric.statistics.Calculations;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.comparator.ReportComparator;
import net.openchrom.xxd.process.supplier.templates.model.ReportSetting;
import net.openchrom.xxd.process.supplier.templates.settings.ChromatogramReportSettings;

public class ReportWriter {

	private static final String DELIMITER = "\t";
	//
	private static final DecimalFormat timeFormat = ValueFormat.getDecimalFormatEnglish("0.000");
	private static final DecimalFormat areaFormat = ValueFormat.getDecimalFormatEnglish("0.0000");

	public void generate(File file, boolean append, List<IChromatogram<? extends IPeak>> chromatograms, ChromatogramReportSettings reportSettings, IProgressMonitor monitor) throws IOException {

		try (PrintWriter printWriter = new PrintWriter(new FileWriter(file, append))) {
			printResults(chromatograms, reportSettings, printWriter, monitor);
			printWriter.flush();
		}
	}

	private void printResults(List<IChromatogram<? extends IPeak>> chromatograms, ChromatogramReportSettings reportSettings, PrintWriter printWriter, IProgressMonitor monitor) {

		Map<ReportSetting, List<IPeak>> sumResults = new HashMap<>();
		int reports = 0;
		//
		for(IChromatogram<? extends IPeak> chromatogram : chromatograms) {
			/*
			 * Master
			 */
			Map<ReportSetting, List<IPeak>> mappedResults = mapChromatogram(chromatogram, reportSettings);
			printChromatogramHeader(chromatogram, printWriter);
			printResults(mappedResults, printWriter);
			merge(mappedResults, sumResults);
			reports++;
			/*
			 * Reference(s)
			 */
			if(reportSettings.isReportReferencedChromatograms()) {
				for(IChromatogram<? extends IPeak> referenceChromatogram : chromatogram.getReferencedChromatograms()) {
					Map<ReportSetting, List<IPeak>> mappedResultsReference = mapChromatogram(referenceChromatogram, reportSettings);
					printChromatogramHeader(referenceChromatogram, printWriter);
					printResults(mappedResultsReference, printWriter);
					merge(mappedResultsReference, sumResults);
					reports++;
				}
			}
		}
		//
		if(reports > 1) {
			printWriter.println("Summary");
			printWriter.println("");
			printResults(sumResults, printWriter);
		}
	}

	private void merge(Map<ReportSetting, List<IPeak>> resultsSource, Map<ReportSetting, List<IPeak>> resultsSink) {

		for(ReportSetting reportSetting : resultsSource.keySet()) {
			/*
			 * Source
			 */
			List<IPeak> peaksSource = resultsSource.get(reportSetting);
			if(peaksSource == null) {
				peaksSource = new ArrayList<>();
			}
			/*
			 * Sink
			 */
			List<IPeak> peaksSink = resultsSink.get(reportSetting);
			if(peaksSink == null) {
				resultsSink.put(reportSetting, peaksSource);
			} else {
				peaksSink.addAll(peaksSource);
				resultsSink.put(reportSetting, peaksSink);
			}
		}
	}

	private void printResults(Map<ReportSetting, List<IPeak>> mappedResults, PrintWriter printWriter) {

		printResultHeader(printWriter);
		printResultData(mappedResults, printWriter);
		printWriter.println("");
	}

	private void printChromatogramHeader(IChromatogram<? extends IPeak> chromatogram, PrintWriter printWriter) {

		Map<String, String> headerData = chromatogram.getHeaderDataMap();
		List<String> keys = new ArrayList<>(headerData.keySet());
		Collections.sort(keys);
		//
		for(String key : keys) {
			printWriter.print(key);
			printWriter.print(": ");
			printWriter.println(headerData.get(key));
		}
		printWriter.println("");
	}

	private void printResultHeader(PrintWriter printWriter) {

		/*
		 * Header
		 */
		printWriter.print("Name");
		printWriter.print(DELIMITER);
		printWriter.print("CAS#");
		printWriter.print(DELIMITER);
		printWriter.print("Start Time [min]");
		printWriter.print(DELIMITER);
		printWriter.print("Stop Time [min]");
		printWriter.print(DELIMITER);
		printWriter.print("Number Peak(s)");
		printWriter.print(DELIMITER);
		printWriter.print("Area");
		printWriter.print(DELIMITER);
		printWriter.print("Min Area");
		printWriter.print(DELIMITER);
		printWriter.print("Max Area");
		printWriter.print(DELIMITER);
		printWriter.print("Mean Area");
		printWriter.print(DELIMITER);
		printWriter.print("Median Area");
		printWriter.print(DELIMITER);
		printWriter.print("Standard Deviation Area");
		printWriter.println("");
	}

	private void printResultData(Map<ReportSetting, List<IPeak>> mappedResults, PrintWriter printWriter) {

		List<ReportSetting> reportSettings = new ArrayList<>(mappedResults.keySet());
		Collections.sort(reportSettings, new ReportComparator());
		//
		for(ReportSetting reportSetting : reportSettings) {
			List<IPeak> peaks = mappedResults.get(reportSetting);
			double[] areas = extractPeakArea(peaks);
			/*
			 * Data
			 */
			printWriter.print(reportSetting.getName());
			printWriter.print(DELIMITER);
			printWriter.print(reportSetting.getCasNumber());
			printWriter.print(DELIMITER);
			printWriter.print(getRetentionTimeMinutes(reportSetting.getStartRetentionTime()));
			printWriter.print(DELIMITER);
			printWriter.print(getRetentionTimeMinutes(reportSetting.getStopRetentionTime()));
			printWriter.print(DELIMITER);
			printWriter.print(peaks.size());
			printWriter.print(DELIMITER);
			printWriter.print(areaFormat.format(Calculations.getSum(areas)));
			printWriter.print(DELIMITER);
			printWriter.print(areaFormat.format(Calculations.getMin(areas)));
			printWriter.print(DELIMITER);
			printWriter.print(areaFormat.format(Calculations.getMax(areas)));
			printWriter.print(DELIMITER);
			printWriter.print(areaFormat.format(Calculations.getMean(areas)));
			printWriter.print(DELIMITER);
			printWriter.print(areaFormat.format(Calculations.getMedian(areas)));
			printWriter.print(DELIMITER);
			printWriter.print(areaFormat.format(Calculations.getStandardDeviation(areas)));
			printWriter.println("");
		}
	}

	private Map<ReportSetting, List<IPeak>> mapChromatogram(IChromatogram<? extends IPeak> chromatogram, ChromatogramReportSettings reportSettings) {

		Map<ReportSetting, List<IPeak>> mappedResults = new HashMap<>();
		//
		for(ReportSetting reportSetting : reportSettings.getReportSettingsList()) {
			List<IPeak> matchedPeaks = extractPeaks(chromatogram, reportSetting);
			List<IPeak> storedPeaks = mappedResults.get(reportSetting);
			if(storedPeaks == null) {
				mappedResults.put(reportSetting, matchedPeaks);
			} else {
				storedPeaks.addAll(matchedPeaks);
				mappedResults.put(reportSetting, storedPeaks);
			}
		}
		//
		return mappedResults;
	}

	private List<IPeak> extractPeaks(IChromatogram<? extends IPeak> chromatogram, ReportSetting reportSetting) {

		int startRetentionTime = reportSetting.getStartRetentionTime();
		int stopRetentionTime = reportSetting.getStopRetentionTime();
		List<? extends IPeak> peaks = chromatogram.getPeaks(startRetentionTime, stopRetentionTime);
		List<IPeak> matchedPeaks = new ArrayList<>();
		//
		if(peaks != null && peaks.size() > 0) {
			switch(reportSetting.getReportStrategy()) {
				case ALL:
					/*
					 * Match conditions:
					 * All peaks, where the name or CAS# matches.
					 */
					for(IPeak peak : peaks) {
						exitloop:
						for(IIdentificationTarget identificationTarget : peak.getTargets()) {
							ILibraryInformation libraryInformation = identificationTarget.getLibraryInformation();
							if(libraryInformation.getName().equals(reportSetting.getName()) || libraryInformation.getCasNumber().equals(reportSetting.getCasNumber())) {
								matchedPeaks.add(peak);
								break exitloop;
							}
						}
					}
					break;
			}
		}
		//
		return matchedPeaks;
	}

	private String getRetentionTimeMinutes(int retentionTime) {

		return timeFormat.format(retentionTime / IChromatogram.MINUTE_CORRELATION_FACTOR);
	}

	private double[] extractPeakArea(List<IPeak> peaks) {

		int size = peaks.size();
		double[] areas = new double[size];
		for(int i = 0; i < size; i++) {
			areas[i] = peaks.get(i).getIntegratedArea();
		}
		return areas;
	}
}

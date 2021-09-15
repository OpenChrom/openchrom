/*******************************************************************************
 * Copyright (c) 2020, 2021 Lablicate GmbH.
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.chemclipse.model.comparator.IdentificationTargetComparator;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.numeric.statistics.Calculations;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.model.ReportColumns;
import net.openchrom.xxd.process.supplier.templates.model.ReportSetting;
import net.openchrom.xxd.process.supplier.templates.settings.ChromatogramReportSettings;

public class ReportWriter {

	private static final String DELIMITER = "\t";
	//
	private static final DecimalFormat timeFormat = ValueFormat.getDecimalFormatEnglish("0.000");
	private static final DecimalFormat areaFormat = ValueFormat.getDecimalFormatEnglish("0.0000");

	public void generate(File file, boolean append, List<IChromatogram<? extends IPeak>> chromatograms, ChromatogramReportSettings reportSettings, IProgressMonitor monitor) throws IOException {

		boolean fileExists = file.exists() && file.length() > 0;
		try (PrintWriter printWriter = new PrintWriter(new FileWriter(file, append))) {
			printResults(chromatograms, reportSettings, printWriter, fileExists, monitor);
			printWriter.flush();
		}
	}

	private void printResults(List<IChromatogram<? extends IPeak>> chromatograms, ChromatogramReportSettings reportSettings, PrintWriter printWriter, boolean fileExists, IProgressMonitor monitor) {

		Map<ReportSetting, List<IPeak>> sumResults = new HashMap<>();
		int reports = 0;
		List<String> columnsToPrint = extractColumnsToPrint(reportSettings);
		String chromatogramNameMaster = "";
		//
		for(IChromatogram<? extends IPeak> chromatogram : chromatograms) {
			/*
			 * Master
			 */
			chromatogramNameMaster = chromatogram.getName();
			Map<ReportSetting, List<IPeak>> mappedResults = printChromatogram(chromatogram, reportSettings, columnsToPrint, fileExists, chromatogramNameMaster, printWriter);
			merge(mappedResults, sumResults);
			reports++;
			/*
			 * Reference(s)
			 */
			if(reportSettings.isReportReferencedChromatograms()) {
				int reference = 1;
				for(IChromatogram<? extends IPeak> referenceChromatogram : chromatogram.getReferencedChromatograms()) {
					String chromatogramNameReference = chromatogramNameMaster + "_" + reference;
					Map<ReportSetting, List<IPeak>> mappedResultsReference = printChromatogram(referenceChromatogram, reportSettings, columnsToPrint, fileExists, chromatogramNameReference, printWriter);
					merge(mappedResultsReference, sumResults);
					reports++;
					reference++;
				}
			}
		}
		/*
		 * Summary
		 */
		if(reportSettings.isPrintSummary()) {
			if(reports > 1) {
				printWriter.println("Summary");
				printWriter.println("");
				printResults(reportSettings, sumResults, columnsToPrint, fileExists, chromatogramNameMaster, printWriter);
			}
		}
	}

	private List<String> extractColumnsToPrint(ChromatogramReportSettings reportSettings) {

		List<String> columnsToPrint = new ArrayList<>();
		ReportColumns reportColumns = reportSettings.getReportColumns();
		ReportColumns defaultColumns = ReportColumns.getDefault();
		//
		if(reportColumns.isEmpty()) {
			/*
			 * Default
			 */
			columnsToPrint.addAll(defaultColumns);
		} else {
			/*
			 * Selected Columns
			 */
			for(String value : reportColumns) {
				if(defaultColumns.contains(value)) {
					columnsToPrint.add(value);
				}
			}
		}
		return columnsToPrint;
	}

	private Map<ReportSetting, List<IPeak>> printChromatogram(IChromatogram<? extends IPeak> chromatogram, ChromatogramReportSettings reportSettings, List<String> columnsToPrint, boolean fileExists, String chromatogramName, PrintWriter printWriter) {

		Map<ReportSetting, List<IPeak>> mappedResults = mapChromatogram(chromatogram, reportSettings);
		//
		printChromatogramHeader(chromatogram, reportSettings, printWriter);
		printResults(reportSettings, mappedResults, columnsToPrint, fileExists, chromatogramName, printWriter);
		//
		return mappedResults;
	}

	private void printChromatogramHeader(IChromatogram<? extends IPeak> chromatogram, ChromatogramReportSettings reportSettings, PrintWriter printWriter) {

		if(reportSettings.isPrintHeader()) {
			Map<String, String> headerData = chromatogram.getHeaderDataMap();
			List<String> keys = new ArrayList<>(headerData.keySet());
			Collections.sort(keys); // SORT OK
			//
			for(String key : keys) {
				printWriter.print(key);
				printWriter.print(": ");
				printWriter.println(headerData.get(key));
			}
			printWriter.println("");
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

	private void printResults(ChromatogramReportSettings reportSettings, Map<ReportSetting, List<IPeak>> mappedResults, List<String> columnsToPrint, boolean fileExists, String chromatogramName, PrintWriter printWriter) {

		List<ReportSetting> reportSettingsList = reportSettings.getReportSettings();
		printResultHeader(reportSettings, columnsToPrint, fileExists, printWriter);
		printResultData(reportSettingsList, mappedResults, columnsToPrint, chromatogramName, printWriter);
		printWriter.println("");
	}

	private void printResultHeader(ChromatogramReportSettings reportSettings, List<String> columnsToPrint, boolean fileExists, PrintWriter printWriter) {

		boolean printHeader = reportSettings.isPrintResultsHeader();
		if(printHeader) {
			if(fileExists) {
				printHeader = reportSettings.isAppendResultsHeader();
			}
		}
		//
		if(printHeader) {
			printList(columnsToPrint, printWriter);
		}
	}

	private void printResultData(List<ReportSetting> reportSettings, Map<ReportSetting, List<IPeak>> mappedResults, List<String> columnsToPrint, String chromatogramName, PrintWriter printWriter) {

		/*
		 * The sort order of the report setting list is important.
		 * That's why the list is not extracted as a set from the map.
		 */
		for(ReportSetting reportSetting : reportSettings) {
			List<IPeak> peaks = mappedResults.get(reportSetting);
			if(peaks != null) {
				/*
				 * Peak Time(s)
				 */
				double[] areas = extractPeakAreas(peaks);
				int[] startTimes = extractPeakStartTimes(peaks);
				String startTime = timeFormat.format(Calculations.getMin(startTimes) / IChromatogram.MINUTE_CORRELATION_FACTOR);
				int[] centerTimes = extractPeakCenterTimes(peaks);
				String centerTime = timeFormat.format(Calculations.getMean(centerTimes) / IChromatogram.MINUTE_CORRELATION_FACTOR);
				int[] stopTimes = extractPeakStopTimes(peaks);
				String stopTime = timeFormat.format(Calculations.getMax(stopTimes) / IChromatogram.MINUTE_CORRELATION_FACTOR);
				/*
				 * Data
				 */
				Map<String, String> dataMap = new HashMap<>();
				dataMap.put(ReportColumns.CHROMATOGRAM_NAME, chromatogramName);
				dataMap.put(ReportColumns.PEAK_NAME, reportSetting.getName());
				dataMap.put(ReportColumns.CAS_NUMBER, reportSetting.getCasNumber());
				dataMap.put(ReportColumns.START_TIME_SETTING, getRetentionTimeMinutes(reportSetting.getStartRetentionTime()));
				dataMap.put(ReportColumns.STOP_TIME_SETTING, getRetentionTimeMinutes(reportSetting.getStopRetentionTime()));
				dataMap.put(ReportColumns.NUM_PEAKS, Integer.toString(peaks.size()));
				dataMap.put(ReportColumns.START_TIME_PEAKS, startTime);
				dataMap.put(ReportColumns.CENTER_TIME_PEAKS, centerTime);
				dataMap.put(ReportColumns.STOP_TIME_PEAKS, stopTime);
				dataMap.put(ReportColumns.SUM_AREA, areaFormat.format(Calculations.getSum(areas)));
				dataMap.put(ReportColumns.MIN_AREA, areaFormat.format(Calculations.getMin(areas)));
				dataMap.put(ReportColumns.MAX_AREA, areaFormat.format(Calculations.getMax(areas)));
				dataMap.put(ReportColumns.MEAN_AREA, areaFormat.format(Calculations.getMean(areas)));
				dataMap.put(ReportColumns.MEDIAN_AREA, areaFormat.format(Calculations.getMedian(areas)));
				dataMap.put(ReportColumns.STDEV_AREA, areaFormat.format(Calculations.getStandardDeviation(areas)));
				//
				List<String> items = new ArrayList<>();
				for(String columnToPrint : columnsToPrint) {
					String data = dataMap.get(columnToPrint);
					items.add((data != null) ? data : "");
				}
				//
				printList(items, printWriter);
			}
		}
	}

	private void printList(List<String> items, PrintWriter printWriter) {

		Iterator<String> iterator = items.iterator();
		while(iterator.hasNext()) {
			String item = iterator.next();
			printWriter.print(item);
			if(iterator.hasNext()) {
				printWriter.print(DELIMITER);
			}
		}
		printWriter.println("");
	}

	private Map<ReportSetting, List<IPeak>> mapChromatogram(IChromatogram<? extends IPeak> chromatogram, ChromatogramReportSettings reportSettings) {

		Map<ReportSetting, List<IPeak>> mappedResults = new HashMap<>();
		//
		for(ReportSetting reportSetting : reportSettings.getReportSettings()) {
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
							if(isTargetMatch(identificationTarget, reportSetting)) {
								matchedPeaks.add(peak);
								break exitloop;
							}
						}
					}
					break;
				case BEST_MATCH:
					for(IPeak peak : peaks) {
						float retentionIndex = peak.getPeakModel().getPeakMaximum().getRetentionIndex();
						IdentificationTargetComparator identificationTargetComparator = new IdentificationTargetComparator(retentionIndex);
						IIdentificationTarget identificationTarget = IIdentificationTarget.getBestIdentificationTarget(peak.getTargets(), identificationTargetComparator);
						if(identificationTarget != null) {
							if(isTargetMatch(identificationTarget, reportSetting)) {
								matchedPeaks.add(peak);
							}
						}
					}
					break;
				case LARGEST_AREA:
					double largestArea = Double.MIN_VALUE;
					IPeak largestPeak = null;
					for(IPeak peak : peaks) {
						double peakArea = peak.getIntegratedArea();
						if(peakArea > largestArea) {
							largestArea = peakArea;
							largestPeak = peak;
						}
					}
					//
					if(largestPeak != null && largestArea > 0) {
						matchedPeaks.add(largestPeak);
					}
					break;
				case SMALLEST_AREA:
					double smallestArea = Double.MAX_VALUE;
					IPeak smallestPeak = null;
					for(IPeak peak : peaks) {
						double peakArea = peak.getIntegratedArea();
						if(peakArea < smallestArea) {
							smallestArea = peakArea;
							smallestPeak = peak;
						}
					}
					//
					if(smallestPeak != null && smallestArea > 0) {
						matchedPeaks.add(smallestPeak);
					}
					break;
			}
		}
		//
		return matchedPeaks;
	}

	private boolean isTargetMatch(IIdentificationTarget identificationTarget, ReportSetting reportSetting) {

		/*
		 * Library Information
		 */
		ILibraryInformation libraryInformation = identificationTarget.getLibraryInformation();
		//
		String name = reportSetting.getName();
		if(!name.isEmpty() && libraryInformation.getName().equals(name)) {
			return true;
		}
		//
		String casNumber = reportSetting.getCasNumber();
		if(!casNumber.isEmpty() && libraryInformation.getCasNumber().equals(casNumber)) {
			return true;
		}
		//
		return false;
	}

	private String getRetentionTimeMinutes(int retentionTime) {

		return timeFormat.format(retentionTime / IChromatogram.MINUTE_CORRELATION_FACTOR);
	}

	private double[] extractPeakAreas(List<IPeak> peaks) {

		int size = peaks.size();
		double[] areas = new double[size];
		for(int i = 0; i < size; i++) {
			areas[i] = peaks.get(i).getIntegratedArea();
		}
		return areas;
	}

	private int[] extractPeakStartTimes(List<IPeak> peaks) {

		int size = peaks.size();
		int[] times = new int[size];
		for(int i = 0; i < size; i++) {
			IPeak peak = peaks.get(i);
			IPeakModel peakModel = peak.getPeakModel();
			times[i] = peakModel.getStartRetentionTime();
		}
		return times;
	}

	private int[] extractPeakCenterTimes(List<IPeak> peaks) {

		int size = peaks.size();
		int[] times = new int[size];
		for(int i = 0; i < size; i++) {
			IPeak peak = peaks.get(i);
			IPeakModel peakModel = peak.getPeakModel();
			times[i] = peakModel.getRetentionTimeAtPeakMaximum();
		}
		return times;
	}

	private int[] extractPeakStopTimes(List<IPeak> peaks) {

		int size = peaks.size();
		int[] times = new int[size];
		for(int i = 0; i < size; i++) {
			IPeak peak = peaks.get(i);
			IPeakModel peakModel = peak.getPeakModel();
			times[i] = peakModel.getStopRetentionTime();
		}
		return times;
	}
}

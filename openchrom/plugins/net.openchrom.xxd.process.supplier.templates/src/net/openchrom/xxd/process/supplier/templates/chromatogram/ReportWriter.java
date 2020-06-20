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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
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
	private static final String SEPARATOR = ",";
	private static final String SINGLE_TICK = "'";
	private static final String DOUBLE_TICK = "\"";
	//
	private static final DecimalFormat timeFormat = ValueFormat.getDecimalFormatEnglish("0.000");
	private static final DecimalFormat areaFormat = ValueFormat.getDecimalFormatEnglish("0.0000");
	//
	private static final String NAME = "Name";
	private static final String CAS_NUMBER = "CAS#";
	private static final String START_TIME_SETTING = "Start Time Setting [min]";
	private static final String STOP_TIME_SETTING = "Stop Time Setting [min]";
	private static final String NUM_PEAKS = "Number Peak(s)";
	private static final String START_TIME_PEAKS = "Start Time Peak(s) [min]";
	private static final String CENTER_TIME_PEAKS = "Center Time Peak(s) [min]";
	private static final String STOP_TIME_PEAKS = "Stop Time Peak(s) [min]";
	private static final String SUM_AREA = "Sum Area";
	private static final String MIN_AREA = "Min Area";
	private static final String MAX_AREA = "Max Area";
	private static final String MEAN_AREA = "Mean Area";
	private static final String MEDIAN_AREA = "Median Area";
	private static final String STDEV_AREA = "Standard Deviation Area";

	public void generate(File file, boolean append, List<IChromatogram<? extends IPeak>> chromatograms, ChromatogramReportSettings reportSettings, IProgressMonitor monitor) throws IOException {

		try (PrintWriter printWriter = new PrintWriter(new FileWriter(file, append))) {
			printResults(chromatograms, reportSettings, printWriter, monitor);
			printWriter.flush();
		}
	}

	private void printResults(List<IChromatogram<? extends IPeak>> chromatograms, ChromatogramReportSettings reportSettings, PrintWriter printWriter, IProgressMonitor monitor) {

		Map<ReportSetting, List<IPeak>> sumResults = new HashMap<>();
		int reports = 0;
		Set<String> columnsToPrint = extractColumnsToPrint(reportSettings);
		//
		for(IChromatogram<? extends IPeak> chromatogram : chromatograms) {
			/*
			 * Master
			 */
			Map<ReportSetting, List<IPeak>> mappedResults = printChromatogram(chromatogram, reportSettings, columnsToPrint, printWriter);
			merge(mappedResults, sumResults);
			reports++;
			/*
			 * Reference(s)
			 */
			if(reportSettings.isReportReferencedChromatograms()) {
				for(IChromatogram<? extends IPeak> referenceChromatogram : chromatogram.getReferencedChromatograms()) {
					Map<ReportSetting, List<IPeak>> mappedResultsReference = printChromatogram(referenceChromatogram, reportSettings, columnsToPrint, printWriter);
					merge(mappedResultsReference, sumResults);
					reports++;
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
				printResults(sumResults, columnsToPrint, printWriter);
			}
		}
	}

	private Set<String> extractColumnsToPrint(ChromatogramReportSettings reportSettings) {

		Set<String> columnsToPrint = new HashSet<>();
		String printColumns = reportSettings.getPrintColumns();
		if(!printColumns.isEmpty()) {
			/*
			 * Selected Columns
			 */
			String[] values = printColumns.split(SEPARATOR);
			for(String value : values) {
				value = value.replaceAll(SINGLE_TICK, "").replaceAll(DOUBLE_TICK, "");
				columnsToPrint.add(value.trim());
			}
		} else {
			/*
			 * All Columns
			 */
			columnsToPrint.add(NAME);
			columnsToPrint.add(CAS_NUMBER);
			columnsToPrint.add(START_TIME_SETTING);
			columnsToPrint.add(STOP_TIME_SETTING);
			columnsToPrint.add(NUM_PEAKS);
			columnsToPrint.add(START_TIME_PEAKS);
			columnsToPrint.add(CENTER_TIME_PEAKS);
			columnsToPrint.add(STOP_TIME_PEAKS);
			columnsToPrint.add(SUM_AREA);
			columnsToPrint.add(MIN_AREA);
			columnsToPrint.add(MAX_AREA);
			columnsToPrint.add(MEAN_AREA);
			columnsToPrint.add(MEDIAN_AREA);
			columnsToPrint.add(STDEV_AREA);
		}
		return columnsToPrint;
	}

	private Map<ReportSetting, List<IPeak>> printChromatogram(IChromatogram<? extends IPeak> chromatogram, ChromatogramReportSettings reportSettings, Set<String> columnsToPrint, PrintWriter printWriter) {

		Map<ReportSetting, List<IPeak>> mappedResults = mapChromatogram(chromatogram, reportSettings);
		if(reportSettings.isPrintHeader()) {
			printChromatogramHeader(chromatogram, printWriter);
		}
		printResults(mappedResults, columnsToPrint, printWriter);
		//
		return mappedResults;
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

	private void printResults(Map<ReportSetting, List<IPeak>> mappedResults, Set<String> columnsToPrint, PrintWriter printWriter) {

		printResultHeader(columnsToPrint, printWriter);
		printResultData(mappedResults, columnsToPrint, printWriter);
		printWriter.println("");
	}

	private void printResultHeader(Set<String> columnsToPrint, PrintWriter printWriter) {

		List<String> items = new ArrayList<>();
		addItemHeader(items, NAME, columnsToPrint);
		addItemHeader(items, CAS_NUMBER, columnsToPrint);
		addItemHeader(items, START_TIME_SETTING, columnsToPrint);
		addItemHeader(items, STOP_TIME_SETTING, columnsToPrint);
		addItemHeader(items, NUM_PEAKS, columnsToPrint);
		addItemHeader(items, START_TIME_PEAKS, columnsToPrint);
		addItemHeader(items, CENTER_TIME_PEAKS, columnsToPrint);
		addItemHeader(items, STOP_TIME_PEAKS, columnsToPrint);
		addItemHeader(items, SUM_AREA, columnsToPrint);
		addItemHeader(items, MIN_AREA, columnsToPrint);
		addItemHeader(items, MAX_AREA, columnsToPrint);
		addItemHeader(items, MEAN_AREA, columnsToPrint);
		addItemHeader(items, MEDIAN_AREA, columnsToPrint);
		addItemHeader(items, STDEV_AREA, columnsToPrint);
		printList(items, printWriter);
	}

	private void printResultData(Map<ReportSetting, List<IPeak>> mappedResults, Set<String> columnsToPrint, PrintWriter printWriter) {

		List<ReportSetting> reportSettings = new ArrayList<>(mappedResults.keySet());
		Collections.sort(reportSettings, new ReportComparator());
		//
		for(ReportSetting reportSetting : reportSettings) {
			List<IPeak> peaks = mappedResults.get(reportSetting);
			double[] areas = extractPeakAreas(peaks);
			int[] startTimes = extractPeakStartTimes(peaks);
			String startTime = timeFormat.format(getMin(startTimes) / IChromatogram.MINUTE_CORRELATION_FACTOR);
			int[] centerTimes = extractPeakCenterTimes(peaks);
			String centerTime = timeFormat.format(Calculations.getMean(centerTimes) / IChromatogram.MINUTE_CORRELATION_FACTOR);
			int[] stopTimes = extractPeakStopTimes(peaks);
			String stopTime = timeFormat.format(getMax(stopTimes) / IChromatogram.MINUTE_CORRELATION_FACTOR);
			/*
			 * Data
			 */
			List<String> items = new ArrayList<>();
			addItem(items, reportSetting.getName(), NAME, columnsToPrint);
			addItem(items, reportSetting.getCasNumber(), CAS_NUMBER, columnsToPrint);
			addItem(items, getRetentionTimeMinutes(reportSetting.getStartRetentionTime()), START_TIME_SETTING, columnsToPrint);
			addItem(items, getRetentionTimeMinutes(reportSetting.getStopRetentionTime()), STOP_TIME_SETTING, columnsToPrint);
			addItem(items, Integer.toString(peaks.size()), NUM_PEAKS, columnsToPrint);
			addItem(items, startTime, START_TIME_PEAKS, columnsToPrint);
			addItem(items, centerTime, CENTER_TIME_PEAKS, columnsToPrint);
			addItem(items, stopTime, STOP_TIME_PEAKS, columnsToPrint);
			addItem(items, areaFormat.format(Calculations.getSum(areas)), SUM_AREA, columnsToPrint);
			addItem(items, areaFormat.format(Calculations.getMin(areas)), MIN_AREA, columnsToPrint);
			addItem(items, areaFormat.format(Calculations.getMax(areas)), MAX_AREA, columnsToPrint);
			addItem(items, areaFormat.format(Calculations.getMean(areas)), MEAN_AREA, columnsToPrint);
			addItem(items, areaFormat.format(Calculations.getMedian(areas)), MEDIAN_AREA, columnsToPrint);
			addItem(items, areaFormat.format(Calculations.getStandardDeviation(areas)), STDEV_AREA, columnsToPrint);
			printList(items, printWriter);
		}
	}

	private void addItemHeader(List<String> items, String column, Set<String> columnsToPrint) {

		addItem(items, column, column, columnsToPrint);
	}

	private void addItem(List<String> items, String value, String column, Set<String> columnsToPrint) {

		if(columnsToPrint.contains(column)) {
			items.add(value);
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
							if(isTargetMatch(identificationTarget, reportSetting)) {
								matchedPeaks.add(peak);
								break exitloop;
							}
						}
					}
					break;
				case BEST_MATCH:
					for(IPeak peak : peaks) {
						IIdentificationTarget identificationTarget = IIdentificationTarget.getBestIdentificationTarget(peak.getTargets());
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

	// TODO - move to calculations
	private int getMin(int[] values) {

		int min = values.length > 0 ? Integer.MAX_VALUE : 0;
		for(int value : values) {
			min = Math.min(min, value);
		}
		return min;
	}

	// TODO - move to calculations
	private int getMax(int[] values) {

		int max = values.length > 0 ? Integer.MIN_VALUE : 0;
		for(int value : values) {
			max = Math.max(max, value);
		}
		return max;
	}
}

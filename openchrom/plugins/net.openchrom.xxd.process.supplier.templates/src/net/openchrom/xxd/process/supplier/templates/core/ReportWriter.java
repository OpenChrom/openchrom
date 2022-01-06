/*******************************************************************************
 * Copyright (c) 2020, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.core;

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

import org.eclipse.chemclipse.model.comparator.IdentificationTargetComparator;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IChromatogramPeak;
import org.eclipse.chemclipse.model.core.IIntegrationEntry;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.model.core.support.HeaderField;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.model.support.HeaderUtil;
import org.eclipse.chemclipse.msd.model.core.AbstractIon;
import org.eclipse.chemclipse.msd.model.core.IPeakMSD;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.xic.IExtractedIonSignal;
import org.eclipse.chemclipse.numeric.statistics.Calculations;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.chemclipse.wsd.model.core.IPeakWSD;
import org.eclipse.chemclipse.wsd.model.core.IScanWSD;
import org.eclipse.chemclipse.wsd.model.xwc.IExtractedWavelengthSignal;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

import net.openchrom.xxd.process.supplier.templates.model.ReportColumns;
import net.openchrom.xxd.process.supplier.templates.model.ReportSetting;
import net.openchrom.xxd.process.supplier.templates.settings.ChromatogramReportSettings;
import net.openchrom.xxd.process.supplier.templates.util.TracesValidator;

public class ReportWriter {

	private static final String DELIMITER = "\t";
	//
	private static final DecimalFormat RETENTION_TIME_FORMAT = ValueFormat.getDecimalFormatEnglish("0.000");
	private static final DecimalFormat RETENTION_INDEX_FORMAT = ValueFormat.getDecimalFormatEnglish("0");
	private static final DecimalFormat SIGNAL_TO_NOISE_FORMAT = ValueFormat.getDecimalFormatEnglish("0.00");
	private static final DecimalFormat AREA_FORMAT = ValueFormat.getDecimalFormatEnglish("0.0000");

	public void generate(File file, boolean append, List<IChromatogram<? extends IPeak>> chromatograms, ChromatogramReportSettings chromatogramReportSettings, IProgressMonitor monitor) throws IOException {

		boolean fileExists = file.exists() && file.length() > 0;
		TracesValidator tracesValidator = new TracesValidator();
		IStatus status = tracesValidator.validate(chromatogramReportSettings.getTraces());
		Set<Integer> traceSet = status.isOK() ? tracesValidator.getTraces() : new HashSet<>();
		traceSet.remove(0); // TIC doesn't make sense here.
		List<Integer> traces = new ArrayList<>(traceSet);
		Collections.sort(traces);
		//
		try (PrintWriter printWriter = new PrintWriter(new FileWriter(file, append))) {
			printResults(chromatograms, chromatogramReportSettings, printWriter, fileExists, traces, monitor);
			printWriter.flush();
		}
	}

	private void printResults(List<IChromatogram<? extends IPeak>> chromatograms, ChromatogramReportSettings chromatogramReportSettings, PrintWriter printWriter, boolean fileExists, List<Integer> traces, IProgressMonitor monitor) {

		Map<ReportSetting, List<IPeak>> sumResults = new HashMap<>();
		int reports = 0;
		List<String> columnsToPrint = extractColumnsToPrint(chromatogramReportSettings, traces);
		HeaderField headerField = chromatogramReportSettings.getHeaderField();
		String chromatogramNameMaster = "";
		//
		for(IChromatogram<? extends IPeak> chromatogram : chromatograms) {
			/*
			 * Master
			 */
			chromatogramNameMaster = HeaderUtil.getChromatogramName(chromatogram, headerField, chromatogram.getName());
			Map<ReportSetting, List<IPeak>> mappedResults = printChromatogram(chromatogram, chromatogramReportSettings, columnsToPrint, fileExists, chromatogramNameMaster, traces, printWriter);
			merge(mappedResults, sumResults);
			reports++;
			fileExists = true; // The first results are written, set to exist true. This is important to skip printing the column header.
			/*
			 * Reference(s)
			 */
			if(chromatogramReportSettings.isReportReferencedChromatograms()) {
				int reference = 1;
				for(IChromatogram<? extends IPeak> referenceChromatogram : chromatogram.getReferencedChromatograms()) {
					String defaultName = chromatogramNameMaster + "_" + reference;
					String chromatogramNameReference = HeaderUtil.getChromatogramName(referenceChromatogram, headerField, defaultName);
					Map<ReportSetting, List<IPeak>> mappedResultsReference = printChromatogram(referenceChromatogram, chromatogramReportSettings, columnsToPrint, fileExists, chromatogramNameReference, traces, printWriter);
					merge(mappedResultsReference, sumResults);
					reports++;
					reference++;
				}
			}
		}
		/*
		 * Summary
		 */
		if(chromatogramReportSettings.isPrintSummary()) {
			if(reports > 1) {
				if(chromatogramReportSettings.isPrintSectionSeparator()) {
					printWriter.println("");
				}
				printWriter.println("Summary");
				printResults(chromatogramReportSettings, sumResults, columnsToPrint, fileExists, chromatogramNameMaster, traces, printWriter);
			}
		}
	}

	private List<String> extractColumnsToPrint(ChromatogramReportSettings chromatogramReportSettings, List<Integer> traces) {

		List<String> columnsToPrint = new ArrayList<>();
		ReportColumns reportColumns = chromatogramReportSettings.getReportColumns();
		ReportColumns defaultColumns = ReportColumns.getDefault();
		/*
		 * Add default or selected columns.
		 */
		if(reportColumns.isEmpty()) {
			columnsToPrint.addAll(defaultColumns);
		} else {
			for(String value : reportColumns) {
				if(defaultColumns.contains(value)) {
					columnsToPrint.add(value);
				}
			}
		}
		/*
		 * Set the selected traces on demand.
		 */
		return adjustTracesColumns(columnsToPrint, traces);
	}

	private List<String> adjustTracesColumns(List<String> columnsToPrint, List<Integer> traces) {

		List<String> columnsToPrintAdjusted = new ArrayList<>();
		//
		for(String column : columnsToPrint) {
			if(isTraceColumnSelectedAndActive(column, traces)) {
				for(int trace : traces) {
					if(column.equals(ReportColumns.TRACES_MIN_AREA_PEAKS)) {
						columnsToPrintAdjusted.add(ReportColumns.createTraceColumnMin(trace));
					} else if(column.equals(ReportColumns.TRACES_MEAN_AREA_PEAKS)) {
						columnsToPrintAdjusted.add(ReportColumns.createTraceColumnMean(trace));
					} else if(column.equals(ReportColumns.TRACES_MEDIAN_AREA_PEAKS)) {
						columnsToPrintAdjusted.add(ReportColumns.createTraceColumnMedian(trace));
					} else if(column.equals(ReportColumns.TRACES_MAX_AREA_PEAKS)) {
						columnsToPrintAdjusted.add(ReportColumns.createTraceColumnMax(trace));
					}
				}
			} else {
				columnsToPrintAdjusted.add(column);
			}
		}
		//
		return columnsToPrintAdjusted;
	}

	private boolean isTraceColumnSelectedAndActive(String column, List<Integer> traces) {

		if(traces.size() > 0) {
			if(column.equals(ReportColumns.TRACES_MIN_AREA_PEAKS)) {
				return true;
			} else if(column.equals(ReportColumns.TRACES_MEAN_AREA_PEAKS)) {
				return true;
			} else if(column.equals(ReportColumns.TRACES_MEDIAN_AREA_PEAKS)) {
				return true;
			} else if(column.equals(ReportColumns.TRACES_MAX_AREA_PEAKS)) {
				return true;
			}
		}
		//
		return false;
	}

	private Map<ReportSetting, List<IPeak>> printChromatogram(IChromatogram<? extends IPeak> chromatogram, ChromatogramReportSettings chromatogramReportSettings, List<String> columnsToPrint, boolean fileExists, String chromatogramName, List<Integer> traces, PrintWriter printWriter) {

		Map<ReportSetting, List<IPeak>> mappedResults = mapChromatogram(chromatogram, chromatogramReportSettings);
		//
		printChromatogramHeader(chromatogram, chromatogramReportSettings, printWriter);
		printResults(chromatogramReportSettings, mappedResults, columnsToPrint, fileExists, chromatogramName, traces, printWriter);
		//
		return mappedResults;
	}

	private void printChromatogramHeader(IChromatogram<? extends IPeak> chromatogram, ChromatogramReportSettings chromatogramReportSettings, PrintWriter printWriter) {

		if(chromatogramReportSettings.isPrintHeader()) {
			/*
			 * Header Data
			 */
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

	private void printResults(ChromatogramReportSettings chromatogramReportSettings, Map<ReportSetting, List<IPeak>> mappedResults, List<String> columnsToPrint, boolean fileExists, String chromatogramName, List<Integer> traces, PrintWriter printWriter) {

		List<ReportSetting> reportSettings = chromatogramReportSettings.getReportSettings();
		printResultHeader(chromatogramReportSettings, columnsToPrint, fileExists, printWriter);
		printResultData(reportSettings, mappedResults, columnsToPrint, chromatogramName, traces, printWriter);
		//
		if(chromatogramReportSettings.isPrintSectionSeparator()) {
			printWriter.println("");
		}
	}

	private void printResultHeader(ChromatogramReportSettings chromatogramReportSettings, List<String> columnsToPrint, boolean fileExists, PrintWriter printWriter) {

		boolean printHeader = chromatogramReportSettings.isPrintResultsHeader();
		if(printHeader) {
			if(fileExists) {
				printHeader = chromatogramReportSettings.isAppendResultsHeader();
			}
		}
		//
		if(printHeader) {
			printList(columnsToPrint, printWriter);
		}
	}

	private void printResultData(List<ReportSetting> reportSettings, Map<ReportSetting, List<IPeak>> mappedResults, List<String> columnsToPrint, String chromatogramName, List<Integer> traces, PrintWriter printWriter) {

		/*
		 * The sort order of the report setting list is important.
		 * That's why the list is not extracted as a set from the map.
		 */
		for(ReportSetting reportSetting : reportSettings) {
			List<IPeak> peaks = mappedResults.get(reportSetting);
			if(peaks != null) {
				/*
				 * Peak Key Values
				 */
				double[] areas = extractPeakAreas(peaks);
				int[] startTimes = extractPeakStartTimes(peaks);
				String startRetentionTime = RETENTION_TIME_FORMAT.format(Calculations.getMin(startTimes) / IChromatogram.MINUTE_CORRELATION_FACTOR);
				int[] centerTimes = extractPeakCenterTimes(peaks);
				String meanRetentionTime = RETENTION_TIME_FORMAT.format(Calculations.getMean(centerTimes) / IChromatogram.MINUTE_CORRELATION_FACTOR);
				String medianRetentionTime = RETENTION_TIME_FORMAT.format(Calculations.getMedian(centerTimes) / IChromatogram.MINUTE_CORRELATION_FACTOR);
				int[] stopTimes = extractPeakStopTimes(peaks);
				String stopRetentionTime = RETENTION_TIME_FORMAT.format(Calculations.getMax(stopTimes) / IChromatogram.MINUTE_CORRELATION_FACTOR);
				float[] retentionIndices = extractPeakRetentionIndices(peaks);
				String startRetentionIndices = RETENTION_INDEX_FORMAT.format(Calculations.getMin(retentionIndices));
				String meanRetentionIndices = RETENTION_INDEX_FORMAT.format(Calculations.getMean(retentionIndices));
				String medianRetentionIndices = RETENTION_INDEX_FORMAT.format(Calculations.getMedian(retentionIndices));
				String stopRetentionIndices = RETENTION_INDEX_FORMAT.format(Calculations.getMax(retentionIndices));
				float[] signalToNoiseRatios = extractPeakSignalToNoiseRatios(peaks);
				String startSignalToNoiseRatios = SIGNAL_TO_NOISE_FORMAT.format(Calculations.getMin(signalToNoiseRatios));
				String meanSignalToNoiseRatios = SIGNAL_TO_NOISE_FORMAT.format(Calculations.getMean(signalToNoiseRatios));
				String medianSignalToNoiseRatios = SIGNAL_TO_NOISE_FORMAT.format(Calculations.getMedian(signalToNoiseRatios));
				String stopSignalToNoiseRatios = SIGNAL_TO_NOISE_FORMAT.format(Calculations.getMax(signalToNoiseRatios));
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
				dataMap.put(ReportColumns.MIN_RETENTION_TIME_PEAKS, startRetentionTime);
				dataMap.put(ReportColumns.MEAN_RETENTION_TIME_PEAKS, meanRetentionTime);
				dataMap.put(ReportColumns.MEDIAN_RETENTION_TIME_PEAKS, medianRetentionTime);
				dataMap.put(ReportColumns.MAX_RETENTION_TIME_PEAKS, stopRetentionTime);
				dataMap.put(ReportColumns.MIN_RETENTION_INDEX_PEAKS, startRetentionIndices);
				dataMap.put(ReportColumns.MEAN_RETENTION_INDEX_PEAKS, meanRetentionIndices);
				dataMap.put(ReportColumns.MEDIAN_RETENTION_INDEX_PEAKS, medianRetentionIndices);
				dataMap.put(ReportColumns.MAX_RETENTION_INDEX_PEAKS, stopRetentionIndices);
				dataMap.put(ReportColumns.MIN_SIGNAL_TO_NOISE_RATIOS_PEAKS, startSignalToNoiseRatios);
				dataMap.put(ReportColumns.MEAN_SIGNAL_TO_NOISE_RATIOS_PEAKS, meanSignalToNoiseRatios);
				dataMap.put(ReportColumns.MEDIAN_SIGNAL_TO_NOISE_RATIOS_PEAKS, medianSignalToNoiseRatios);
				dataMap.put(ReportColumns.MAX_SIGNAL_TO_NOISE_RATIOS_PEAKS, stopSignalToNoiseRatios);
				//
				for(int trace : traces) {
					double[] areasByTrace = extractPeakAreasByTrace(peaks, trace);
					dataMap.put(ReportColumns.createTraceColumnMin(trace), AREA_FORMAT.format(Calculations.getMin(areasByTrace)));
					dataMap.put(ReportColumns.createTraceColumnMean(trace), AREA_FORMAT.format(Calculations.getMean(areasByTrace)));
					dataMap.put(ReportColumns.createTraceColumnMedian(trace), AREA_FORMAT.format(Calculations.getMedian(areasByTrace)));
					dataMap.put(ReportColumns.createTraceColumnMax(trace), AREA_FORMAT.format(Calculations.getMax(areasByTrace)));
				}
				//
				dataMap.put(ReportColumns.SUM_AREA, AREA_FORMAT.format(Calculations.getSum(areas)));
				dataMap.put(ReportColumns.MIN_AREA, AREA_FORMAT.format(Calculations.getMin(areas)));
				dataMap.put(ReportColumns.MAX_AREA, AREA_FORMAT.format(Calculations.getMax(areas)));
				dataMap.put(ReportColumns.MEAN_AREA, AREA_FORMAT.format(Calculations.getMean(areas)));
				dataMap.put(ReportColumns.MEDIAN_AREA, AREA_FORMAT.format(Calculations.getMedian(areas)));
				dataMap.put(ReportColumns.STDEV_AREA, AREA_FORMAT.format(Calculations.getStandardDeviation(areas)));
				/*
				 * Print all selected columns.
				 */
				List<String> items = new ArrayList<>();
				for(String columnToPrint : columnsToPrint) {
					items.add(dataMap.getOrDefault(columnToPrint, "--"));
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

	private Map<ReportSetting, List<IPeak>> mapChromatogram(IChromatogram<? extends IPeak> chromatogram, ChromatogramReportSettings chromatogramReportSettings) {

		Map<ReportSetting, List<IPeak>> mappedResults = new HashMap<>();
		//
		for(ReportSetting reportSetting : chromatogramReportSettings.getReportSettings()) {
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
		/*
		 * Expand the retention time to max range if no selection has been made.
		 */
		if(startRetentionTime == 0 && stopRetentionTime == 0) {
			startRetentionTime = chromatogram.getStartRetentionTime();
			stopRetentionTime = chromatogram.getStopRetentionTime();
		}
		//
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

		return RETENTION_TIME_FORMAT.format(retentionTime / IChromatogram.MINUTE_CORRELATION_FACTOR);
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

	private float[] extractPeakRetentionIndices(List<IPeak> peaks) {

		int size = peaks.size();
		float[] indices = new float[size];
		for(int i = 0; i < size; i++) {
			IPeak peak = peaks.get(i);
			IPeakModel peakModel = peak.getPeakModel();
			indices[i] = peakModel.getPeakMaximum().getRetentionIndex();
		}
		return indices;
	}

	private float[] extractPeakSignalToNoiseRatios(List<IPeak> peaks) {

		int size = peaks.size();
		float[] signalToNoiseRatios = new float[size];
		for(int i = 0; i < size; i++) {
			IPeak peak = peaks.get(i);
			if(peak instanceof IChromatogramPeak) {
				IChromatogramPeak chromatogramPeak = (IChromatogramPeak)peak;
				signalToNoiseRatios[i] = chromatogramPeak.getSignalToNoiseRatio();
			}
		}
		return signalToNoiseRatios;
	}

	private double[] extractPeakAreasByTrace(List<IPeak> peaks, int trace) {

		int size = peaks.size();
		double[] areas = new double[size];
		for(int i = 0; i < size; i++) {
			IPeak peak = peaks.get(i);
			if(peak.getIntegratedArea() > 0) {
				if(peak instanceof IPeakMSD) {
					areas[i] = getIntegratedAreaMSD((IPeakMSD)peak, trace);
				} else if(peak instanceof IPeakWSD) {
					areas[i] = getIntegratedAreaWSD((IPeakWSD)peak, trace);
				}
			}
		}
		return areas;
	}

	private static double getIntegratedAreaMSD(IPeakMSD peakMSD, int trace) {

		double area = 0.0d;
		//
		List<IIntegrationEntry> integrationEntries = peakMSD.getIntegrationEntries();
		if(integrationEntries.size() > 1) {
			/*
			 * Traces Integrated
			 */
			for(IIntegrationEntry integrationEntry : integrationEntries) {
				int ion = AbstractIon.getIon(integrationEntry.getSignal());
				if(ion == trace) {
					area += integrationEntry.getIntegratedArea();
				}
			}
		} else {
			/*
			 * TIC Integrated
			 */
			double integratedArea = peakMSD.getIntegratedArea();
			if(integratedArea > 0) {
				IScan scan = peakMSD.getPeakModel().getPeakMaximum();
				if(scan instanceof IScanMSD) {
					IScanMSD scanMSD = (IScanMSD)scan;
					double totalIntensity = scanMSD.getTotalSignal();
					double tracesIntensity = 0.0d;
					IExtractedIonSignal extractedIonSignal = scanMSD.getExtractedIonSignal();
					for(int ion = extractedIonSignal.getStartIon(); ion <= extractedIonSignal.getStopIon(); ion++) {
						float intensity = extractedIonSignal.getAbundance(ion);
						if(intensity > 0 && ion == trace) {
							tracesIntensity += intensity;
						}
					}
					//
					if(totalIntensity > 0) {
						double percentage = 1.0d / totalIntensity * tracesIntensity;
						area = integratedArea * percentage;
					}
				}
			}
		}
		//
		return area;
	}

	private static double getIntegratedAreaWSD(IPeakWSD peakWSD, int trace) {

		double area = 0.0d;
		//
		List<IIntegrationEntry> integrationEntries = peakWSD.getIntegrationEntries();
		if(integrationEntries.size() > 1) {
			/*
			 * Traces Integrated
			 */
			for(IIntegrationEntry integrationEntry : integrationEntries) {
				int wavelength = (int)Math.round(integrationEntry.getSignal());
				if(wavelength == trace) {
					area += integrationEntry.getIntegratedArea();
				}
			}
		} else {
			/*
			 * TIC Integrated
			 */
			double integratedArea = peakWSD.getIntegratedArea();
			if(integratedArea > 0) {
				IScan scan = peakWSD.getPeakModel().getPeakMaximum();
				if(scan instanceof IScanWSD) {
					IScanWSD scanWSD = (IScanWSD)scan;
					double totalIntensity = scanWSD.getTotalSignal();
					double tracesIntensity = 0.0d;
					IExtractedWavelengthSignal extractedWavelengthSignal = scanWSD.getExtractedWavelengthSignal();
					for(int wavelength = extractedWavelengthSignal.getStartWavelength(); wavelength <= extractedWavelengthSignal.getStopWavelength(); wavelength++) {
						float intensity = extractedWavelengthSignal.getAbundance(wavelength);
						if(intensity > 0 && wavelength == trace) {
							tracesIntensity += intensity;
						}
					}
					//
					if(totalIntensity > 0) {
						double percentage = 1.0d / totalIntensity * tracesIntensity;
						area = integratedArea * percentage;
					}
				}
			}
		}
		//
		return area;
	}
}
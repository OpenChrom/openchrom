/*******************************************************************************
 * Copyright (c) 2021, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.model;

import java.util.ArrayList;
import java.util.Iterator;

public class ReportColumns extends ArrayList<String> {

	private static final long serialVersionUID = 727322817550120355L;
	//
	public static final String CHROMATOGRAM_NAME = "Chromatogram Name";
	public static final String PEAK_NAME = "Peak Name";
	public static final String CAS_NUMBER = "CAS#";
	public static final String START_TIME_SETTING = "Start Time Setting [min]";
	public static final String STOP_TIME_SETTING = "Stop Time Setting [min]";
	public static final String NUM_PEAKS = "Number Peaks";
	public static final String MIN_RETENTION_TIME_PEAKS = "Min Time Peaks [min]";
	public static final String MEAN_RETENTION_TIME_PEAKS = "Mean Time Peaks [min]";
	public static final String MEDIAN_RETENTION_TIME_PEAKS = "Median Time Peaks [min]";
	public static final String MAX_RETENTION_TIME_PEAKS = "Max Time Peaks [min]";
	public static final String MIN_RETENTION_INDEX_PEAKS = "Min Retention Index Peaks";
	public static final String MEAN_RETENTION_INDEX_PEAKS = "Mean Retention Index Peaks";
	public static final String MEDIAN_RETENTION_INDEX_PEAKS = "Median Retention Index Peaks";
	public static final String MAX_RETENTION_INDEX_PEAKS = "Max Retention Index Peaks";
	public static final String MIN_SIGNAL_TO_NOISE_RATIOS_PEAKS = "Min S/N Peaks";
	public static final String MEAN_SIGNAL_TO_NOISE_RATIOS_PEAKS = "Mean S/N Peaks";
	public static final String MEDIAN_SIGNAL_TO_NOISE_RATIOS_PEAKS = "Median S/N Peaks";
	public static final String MAX_SIGNAL_TO_NOISE_RATIOS_PEAKS = "Max S/N Peaks";
	public static final String TRACES_MIN_AREA_PEAKS = "Traces Min Area Peaks";
	public static final String TRACES_MEAN_AREA_PEAKS = "Traces Mean Area Peaks";
	public static final String TRACES_MEDIAN_AREA_PEAKS = "Traces Median Area Peaks";
	public static final String TRACES_MAX_AREA_PEAKS = "Traces Max Area Peaks";
	public static final String SUM_AREA = "Sum Area";
	public static final String MIN_AREA = "Min Area";
	public static final String MAX_AREA = "Max Area";
	public static final String MEAN_AREA = "Mean Area";
	public static final String MEDIAN_AREA = "Median Area";
	public static final String STDEV_AREA = "Standard Deviation Area";
	/*
	 * Internal markers
	 */
	private static final String MARKER_MIN = "Min";
	private static final String MARKER_MEDIAN = "Median";
	private static final String MARKER_MEAN = "Mean";
	private static final String MARKER_MAX = "Max";
	private static final String MARKER_TRACE = "Trace";
	private static final String MARKER_AREA = "Area";
	private static final String MARKER_SPACE = " ";
	private static final String MARKER_BRACKET_OPEN = "(";
	private static final String MARKER_BRACKET_CLOSE = ")";
	//
	private static final String SEPARATOR = ",";
	private static final String SINGLE_TICK = "'";
	private static final String DOUBLE_TICK = "\"";

	public static ReportColumns getDefault() {

		ReportColumns reportColumns = new ReportColumns();
		//
		reportColumns.add(CHROMATOGRAM_NAME);
		reportColumns.add(PEAK_NAME);
		reportColumns.add(CAS_NUMBER);
		reportColumns.add(START_TIME_SETTING);
		reportColumns.add(STOP_TIME_SETTING);
		reportColumns.add(NUM_PEAKS);
		reportColumns.add(MIN_RETENTION_TIME_PEAKS);
		reportColumns.add(MEAN_RETENTION_TIME_PEAKS);
		reportColumns.add(MEDIAN_RETENTION_TIME_PEAKS);
		reportColumns.add(MAX_RETENTION_TIME_PEAKS);
		reportColumns.add(MIN_RETENTION_INDEX_PEAKS);
		reportColumns.add(MEAN_RETENTION_INDEX_PEAKS);
		reportColumns.add(MEDIAN_RETENTION_INDEX_PEAKS);
		reportColumns.add(MAX_RETENTION_INDEX_PEAKS);
		reportColumns.add(MIN_SIGNAL_TO_NOISE_RATIOS_PEAKS);
		reportColumns.add(MEAN_SIGNAL_TO_NOISE_RATIOS_PEAKS);
		reportColumns.add(MEDIAN_SIGNAL_TO_NOISE_RATIOS_PEAKS);
		reportColumns.add(MAX_SIGNAL_TO_NOISE_RATIOS_PEAKS);
		reportColumns.add(TRACES_MIN_AREA_PEAKS);
		reportColumns.add(TRACES_MEAN_AREA_PEAKS);
		reportColumns.add(TRACES_MEDIAN_AREA_PEAKS);
		reportColumns.add(TRACES_MAX_AREA_PEAKS);
		reportColumns.add(SUM_AREA);
		reportColumns.add(MIN_AREA);
		reportColumns.add(MAX_AREA);
		reportColumns.add(MEAN_AREA);
		reportColumns.add(MEDIAN_AREA);
		reportColumns.add(STDEV_AREA);
		//
		return reportColumns;
	}

	public static String createTraceColumnMin(int trace) {

		return createTraceColumnId(MARKER_MIN, trace);
	}

	public static String createTraceColumnMean(int trace) {

		return createTraceColumnId(MARKER_MEAN, trace);
	}

	public static String createTraceColumnMedian(int trace) {

		return createTraceColumnId(MARKER_MEDIAN, trace);
	}

	public static String createTraceColumnMax(int trace) {

		return createTraceColumnId(MARKER_MAX, trace);
	}

	private static String createTraceColumnId(String marker, int trace) {

		StringBuilder builder = new StringBuilder();
		/*
		 * Min Area Trace (32)
		 * Mean Area Trace (32)
		 * Median Area Trace (32)
		 * Max Area Trace (32)
		 */
		builder.append(marker);
		builder.append(MARKER_SPACE);
		builder.append(MARKER_TRACE);
		builder.append(MARKER_SPACE);
		builder.append(MARKER_AREA);
		builder.append(MARKER_SPACE);
		builder.append(MARKER_BRACKET_OPEN);
		builder.append(Integer.toString(trace));
		builder.append(MARKER_BRACKET_CLOSE);
		//
		return builder.toString();
	}

	public void load(String items) {

		loadSettings(items);
	}

	public void loadDefault(String items) {

		loadSettings(items);
	}

	public String save() {

		return extract();
	}

	private void loadSettings(String items) {

		if(!"".equals(items)) {
			String[] values = items.split(SEPARATOR);
			for(String value : values) {
				value = value.replace(SINGLE_TICK, "").replace(DOUBLE_TICK, "").trim();
				if(!value.isEmpty()) {
					add(value);
				}
			}
		}
	}

	private String extract() {

		StringBuilder builder = new StringBuilder();
		Iterator<String> iterator = iterator();
		while(iterator.hasNext()) {
			String reportColumn = iterator.next();
			builder.append(reportColumn);
			if(iterator.hasNext()) {
				builder.append(SEPARATOR);
			}
		}
		return builder.toString().trim();
	}
}
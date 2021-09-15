/*******************************************************************************
 * Copyright (c) 2021 Lablicate GmbH.
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
	public static final String NUM_PEAKS = "Number Peak(s)";
	public static final String START_TIME_PEAKS = "Start Time Peak(s) [min]";
	public static final String CENTER_TIME_PEAKS = "Center Time Peak(s) [min]";
	public static final String STOP_TIME_PEAKS = "Stop Time Peak(s) [min]";
	public static final String SUM_AREA = "Sum Area";
	public static final String MIN_AREA = "Min Area";
	public static final String MAX_AREA = "Max Area";
	public static final String MEAN_AREA = "Mean Area";
	public static final String MEDIAN_AREA = "Median Area";
	public static final String STDEV_AREA = "Standard Deviation Area";
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
		reportColumns.add(START_TIME_PEAKS);
		reportColumns.add(CENTER_TIME_PEAKS);
		reportColumns.add(STOP_TIME_PEAKS);
		reportColumns.add(SUM_AREA);
		reportColumns.add(MIN_AREA);
		reportColumns.add(MAX_AREA);
		reportColumns.add(MEAN_AREA);
		reportColumns.add(MEDIAN_AREA);
		reportColumns.add(STDEV_AREA);
		//
		return reportColumns;
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
				value = value.replaceAll(SINGLE_TICK, "").replaceAll(DOUBLE_TICK, "").trim();
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
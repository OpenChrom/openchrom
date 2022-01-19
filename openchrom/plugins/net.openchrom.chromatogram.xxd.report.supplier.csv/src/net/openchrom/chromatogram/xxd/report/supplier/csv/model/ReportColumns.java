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
package net.openchrom.chromatogram.xxd.report.supplier.csv.model;

import java.util.ArrayList;
import java.util.Iterator;

public class ReportColumns extends ArrayList<String> {

	private static final long serialVersionUID = 727322817550120355L;
	//
	public static final String CHROMATOGRAM_NAME = "Chromatogram Name";
	public static final String BARCODE = "Barcode";
	public static final String BARCODE_TYPE = "Barcode Type";
	public static final String DATA_NAME = "Data Name";
	public static final String DATE = "Date";
	public static final String DETAILED_INFO = "Detailed Info";
	public static final String MISC_INFO = "Misc Info";
	public static final String MISC_INFO_SEPARATED = "Misc Info Separated";
	public static final String OPERATOR = "Operator";
	public static final String SAMPLE_GROUP = "Sample Group";
	public static final String SAMPLE_WEIGHT = "Sample Weight";
	public static final String SAMPLE_WEIGHT_UNIT = "Sample Weight Unit";
	public static final String SHORT_INFO = "Short Info";
	public static final String NUMBER_PEAKS = "Number Peak(s)";
	public static final String PEAK_NUMBER = "Peak Number";
	public static final String RETENTION_TIME = "Retention Time";
	public static final String RETENTION_INDEX = "Retention Index";
	public static final String SCANS = "Scans";
	public static final String PURITY = "Purity";
	public static final String COMPONENTS = "Components";
	public static final String SIGNAL_TO_NOISE = "S/N";
	public static final String LEADING = "Leading";
	public static final String TAILING = "Tailing";
	public static final String START_RT = "Start";
	public static final String STOP_RT = "Stop";
	public static final String PEAK_AREA = "Peak Area";
	public static final String PEAK_HEIGHT = "Peak Height";
	public static final String INTEGRATOR = "Integrator";
	public static final String TARGET = "Target";
	public static final String CAS = "CAS";
	public static final String MATCH_FACTOR = "Match Factor";
	public static final String MATCH_FACTOR_REVERSED = "Reverse Match Factor";
	public static final String PROBABILITY = "Probability";
	public static final String MOL_WEIGHT = "Mol Weight";
	public static final String IDENTIFIER = "Identifier";
	public static final String DATABASE = "Database";
	public static final String MODEL = "Model";
	public static final String DETECTOR = "Detector";
	public static final String QUANTIFIER = "Quantifier";
	public static final String CLASSIFIER = "Classifier";
	//
	private static final String SEPARATOR = ",";
	private static final String SINGLE_TICK = "'";
	private static final String DOUBLE_TICK = "\"";

	public static ReportColumns getDefault() {

		ReportColumns reportColumns = new ReportColumns();
		//
		reportColumns.add(CHROMATOGRAM_NAME);
		reportColumns.add(BARCODE);
		reportColumns.add(BARCODE_TYPE);
		reportColumns.add(DATA_NAME);
		reportColumns.add(DATE);
		reportColumns.add(DETAILED_INFO);
		reportColumns.add(MISC_INFO);
		reportColumns.add(MISC_INFO_SEPARATED);
		reportColumns.add(OPERATOR);
		reportColumns.add(SAMPLE_GROUP);
		reportColumns.add(SAMPLE_WEIGHT);
		reportColumns.add(SAMPLE_WEIGHT_UNIT);
		reportColumns.add(SHORT_INFO);
		reportColumns.add(NUMBER_PEAKS);
		reportColumns.add(PEAK_NUMBER);
		reportColumns.add(RETENTION_TIME);
		reportColumns.add(RETENTION_INDEX);
		reportColumns.add(SCANS);
		reportColumns.add(PURITY);
		reportColumns.add(COMPONENTS);
		reportColumns.add(SIGNAL_TO_NOISE);
		reportColumns.add(LEADING);
		reportColumns.add(TAILING);
		reportColumns.add(START_RT);
		reportColumns.add(STOP_RT);
		reportColumns.add(PEAK_AREA);
		reportColumns.add(PEAK_HEIGHT);
		reportColumns.add(INTEGRATOR);
		reportColumns.add(TARGET);
		reportColumns.add(CAS);
		reportColumns.add(MATCH_FACTOR);
		reportColumns.add(MATCH_FACTOR_REVERSED);
		reportColumns.add(PROBABILITY);
		reportColumns.add(MOL_WEIGHT);
		reportColumns.add(IDENTIFIER);
		reportColumns.add(DATABASE);
		reportColumns.add(MODEL);
		reportColumns.add(DETECTOR);
		reportColumns.add(QUANTIFIER);
		reportColumns.add(CLASSIFIER);
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

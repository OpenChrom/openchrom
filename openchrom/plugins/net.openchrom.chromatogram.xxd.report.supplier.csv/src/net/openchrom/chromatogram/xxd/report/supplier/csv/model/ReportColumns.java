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
 *******************************************************************************/
package net.openchrom.chromatogram.xxd.report.supplier.csv.model;

import java.util.ArrayList;
import java.util.Iterator;

public class ReportColumns extends ArrayList<String> {

	private static final long serialVersionUID = 727322817550120355L;
	//
	public static final String CHROMATOGRAM_NAME = "Chromatogram Name";
	public static final String NUMBER_PEAKS = "Number Peaks";
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
	public static final String INTERNAL_STANDARD_CHEMICAL_CLASS = "Internal Standard Chemical Class";
	public static final String INTERNAL_STANDARD_CONCENTRATION = "Internal Standard Concentration";
	public static final String INTERNAL_STANDARD_CONCENTRATION_UNIT = "Internal Standard Concentration Unit";
	public static final String INTERNAL_STANDARD_NAME = "Internal Standard Name";
	public static final String INTERNAL_STANDARD_RESPONSE_FACTOR = "Internal Standard Response Factor";
	public static final String QUANTITATION_ENTRY_AREA = "Quantitation Area";
	public static final String QUANTITATION_CALIBRATION_METHOD = "Quantitation Calibration Method";
	public static final String QUANTITATION_CHEMICAL_CLASS = "Quantitation Chemical Class";
	public static final String QUANTITATION_CONCENTRATION = "Quantitation Concentration";
	public static final String QUANTITATION_CONCENTRATION_UNIT = "Quantitation Concentration Unit";
	public static final String QUANTITATION_DESCRIPTION = "Quantitation Area Description";
	public static final String QUANTITATION_NAME = "Quantitation Name";
	public static final String QUANTITATION_FLAG = "Quantitation Flag";
	public static final String QUANTITATION_SIGNAL = "Quantitation Signal";
	public static final String QUANTITATION_CROSS_ZERO = "Quantitation Cross 0";
	public static final String QUANTITATION_REFERENCE = "Quantitation Reference";
	public static final String PEAK_WIDTH_BASELINE_FROM_INFLECTION_POINTS = "Peak Width Baseline from Inflection Points";
	public static final String PEAK_WIDTH_BASELINE_TOTAL = "Peak Width Baseline Total";
	public static final String PEAK_WIDTH_BY_INFLECTION_POINTS = "Peak Width at Half Peak Height";
	public static final String PEAK_WIDTH_0 = "Peak Width at 0% Height";
	public static final String PEAK_WIDTH_10 = "Peak Width at 10% Height";
	public static final String PEAK_WIDTH_15 = "Peak Width at 15% Height";
	public static final String PEAK_WIDTH_50 = "Peak Width at 50% Height";
	public static final String PEAK_WIDTH_85 = "Peak Width at 85% Height";
	public static final String PEAK_RESOLUTION = "Peak Resolution";
	//
	private static final String SEPARATOR = ",";
	private static final String SINGLE_TICK = "'";
	private static final String DOUBLE_TICK = "\"";

	public static ReportColumns getDefault() {

		ReportColumns reportColumns = new ReportColumns();
		//
		reportColumns.add(CHROMATOGRAM_NAME);
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
		reportColumns.add(INTERNAL_STANDARD_CHEMICAL_CLASS);
		reportColumns.add(INTERNAL_STANDARD_CONCENTRATION);
		reportColumns.add(INTERNAL_STANDARD_CONCENTRATION_UNIT);
		reportColumns.add(INTERNAL_STANDARD_NAME);
		reportColumns.add(INTERNAL_STANDARD_RESPONSE_FACTOR);
		reportColumns.add(QUANTITATION_ENTRY_AREA);
		reportColumns.add(QUANTITATION_CALIBRATION_METHOD);
		reportColumns.add(QUANTITATION_CHEMICAL_CLASS);
		reportColumns.add(QUANTITATION_CONCENTRATION);
		reportColumns.add(QUANTITATION_CONCENTRATION_UNIT);
		reportColumns.add(QUANTITATION_DESCRIPTION);
		reportColumns.add(QUANTITATION_NAME);
		reportColumns.add(QUANTITATION_FLAG);
		reportColumns.add(QUANTITATION_SIGNAL);
		reportColumns.add(QUANTITATION_CROSS_ZERO);
		reportColumns.add(QUANTITATION_REFERENCE);
		reportColumns.add(PEAK_WIDTH_BASELINE_FROM_INFLECTION_POINTS);
		reportColumns.add(PEAK_WIDTH_BASELINE_TOTAL);
		reportColumns.add(PEAK_WIDTH_BY_INFLECTION_POINTS);
		reportColumns.add(PEAK_WIDTH_0);
		reportColumns.add(PEAK_WIDTH_10);
		reportColumns.add(PEAK_WIDTH_15);
		reportColumns.add(PEAK_WIDTH_50);
		reportColumns.add(PEAK_WIDTH_85);
		reportColumns.add(PEAK_RESOLUTION);
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

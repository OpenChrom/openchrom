/*******************************************************************************
 * Copyright (c) 2019, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.preferences;

import org.eclipse.chemclipse.support.preferences.AbstractPreferenceSupplier;
import org.eclipse.chemclipse.support.preferences.IPreferenceSupplier;

import net.openchrom.xxd.classifier.supplier.ratios.Activator;
import net.openchrom.xxd.classifier.supplier.ratios.settings.QualRatioSettings;
import net.openchrom.xxd.classifier.supplier.ratios.settings.QuantRatioSettings;
import net.openchrom.xxd.classifier.supplier.ratios.settings.TimeRatioSettings;
import net.openchrom.xxd.classifier.supplier.ratios.settings.TraceRatioSettings;

public class PreferenceSupplier extends AbstractPreferenceSupplier implements IPreferenceSupplier {

	public static final float MIN_DEVIATION = 0.0f;
	public static final float MAX_DEVIATION = 100.0f;
	//
	public static final String P_ALLOWED_DEVIATION_OK = "allowedDeviationOK";
	public static final float DEF_ALLOWED_DEVIATION_OK = 20.0f;
	public static final String P_ALLOWED_DEVIATION_WARN = "allowedDeviationWarn";
	public static final float DEF_ALLOWED_DEVIATION_WARN = 40.0f;
	//
	public static final String P_TRACE_RATIO_LIST = "traceRatioList";
	public static final String DEF_TRACE_RATIO_LIST = "";
	public static final String P_TIME_RATIO_LIST = "timeRatioList";
	public static final String DEF_TIME_RATIO_LIST = "";
	public static final String P_QUANT_RATIO_LIST = "quantRatioList";
	public static final String DEF_QUANT_RATIO_LIST = "";
	//
	public static final String P_LIST_PATH_IMPORT = "listPathImport";
	public static final String DEF_LIST_PATH_IMPORT = "";
	public static final String P_LIST_PATH_EXPORT = "listPathExport";
	public static final String DEF_LIST_PATH_EXPORT = "";
	//
	public static final int MIN_NUMBER_TRACES = 1;
	public static final int MAX_NUMBER_TRACES = 10;
	public static final String P_EXPORT_NUMBER_TRACES = "exportNumberTraces";
	public static final int DEF_EXPORT_NUMBER_TRACES = 5;
	//
	private static IPreferenceSupplier preferenceSupplier = null;

	public static IPreferenceSupplier INSTANCE() {

		if(preferenceSupplier == null) {
			preferenceSupplier = new PreferenceSupplier();
		}
		return preferenceSupplier;
	}

	@Override
	public String getPreferenceNode() {

		return Activator.getContext().getBundle().getSymbolicName();
	}

	@Override
	public void initializeDefaults() {

		putDefault(P_ALLOWED_DEVIATION_OK, Float.toString(DEF_ALLOWED_DEVIATION_OK));
		putDefault(P_ALLOWED_DEVIATION_WARN, Float.toString(DEF_ALLOWED_DEVIATION_WARN));
		//
		putDefault(P_TRACE_RATIO_LIST, DEF_TRACE_RATIO_LIST);
		putDefault(P_TIME_RATIO_LIST, DEF_TIME_RATIO_LIST);
		putDefault(P_QUANT_RATIO_LIST, DEF_QUANT_RATIO_LIST);
		//
		putDefault(P_LIST_PATH_IMPORT, DEF_LIST_PATH_IMPORT);
		putDefault(P_LIST_PATH_EXPORT, DEF_LIST_PATH_EXPORT);
		//
		putDefault(P_EXPORT_NUMBER_TRACES, Integer.toString(DEF_EXPORT_NUMBER_TRACES));
	}

	public static TraceRatioSettings getSettingsTrace() {

		TraceRatioSettings settings = new TraceRatioSettings();
		settings.setRatioSettings(INSTANCE().get(P_TRACE_RATIO_LIST, DEF_TRACE_RATIO_LIST));
		return settings;
	}

	public static TimeRatioSettings getSettingsTime() {

		TimeRatioSettings settings = new TimeRatioSettings();
		settings.setRatioSettings(INSTANCE().get(P_TIME_RATIO_LIST, DEF_TIME_RATIO_LIST));
		return settings;
	}

	public static QuantRatioSettings getSettingsQuant() {

		QuantRatioSettings settings = new QuantRatioSettings();
		settings.setRatioSettings(INSTANCE().get(P_QUANT_RATIO_LIST, DEF_QUANT_RATIO_LIST));
		return settings;
	}

	public static QualRatioSettings getSettingsQual() {

		return new QualRatioSettings();
	}

	public static void setAllowedDeviationOk(float allowedDeviationOk) {

		INSTANCE().putFloat(P_ALLOWED_DEVIATION_OK, allowedDeviationOk);
	}

	public static float getAllowedDeviationOk() {

		return INSTANCE().getFloat(P_ALLOWED_DEVIATION_OK, DEF_ALLOWED_DEVIATION_OK);
	}

	public static void setAllowedDeviationWarn(float allowedDeviationWarn) {

		INSTANCE().putFloat(P_ALLOWED_DEVIATION_WARN, allowedDeviationWarn);
	}

	public static float getAllowedDeviationWarn() {

		return INSTANCE().getFloat(P_ALLOWED_DEVIATION_WARN, DEF_ALLOWED_DEVIATION_WARN);
	}

	public static String getListPathImport() {

		return INSTANCE().get(P_LIST_PATH_IMPORT, DEF_LIST_PATH_IMPORT);
	}

	public static void setListPathImport(String filterPath) {

		INSTANCE().put(P_LIST_PATH_IMPORT, filterPath);
	}

	public static String getListPathExport() {

		return INSTANCE().get(P_LIST_PATH_EXPORT, DEF_LIST_PATH_EXPORT);
	}

	public static void setListPathExport(String filterPath) {

		INSTANCE().put(P_LIST_PATH_EXPORT, filterPath);
	}

	public static void setNumberTraces(int numberTraces) {

		INSTANCE().putInteger(P_EXPORT_NUMBER_TRACES, numberTraces);
	}

	public static int getNumberTraces() {

		return INSTANCE().getInteger(P_EXPORT_NUMBER_TRACES, DEF_EXPORT_NUMBER_TRACES);
	}
}
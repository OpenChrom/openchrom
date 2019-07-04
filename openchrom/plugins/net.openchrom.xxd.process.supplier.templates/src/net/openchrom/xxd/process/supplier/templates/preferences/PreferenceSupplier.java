/*******************************************************************************
 * Copyright (c) 2018, 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.preferences;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.support.preferences.IPreferenceSupplier;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.BackingStoreException;

import net.openchrom.xxd.process.supplier.templates.Activator;
import net.openchrom.xxd.process.supplier.templates.settings.PeakDetectorSettings;

public class PreferenceSupplier implements IPreferenceSupplier {

	private static final Logger logger = Logger.getLogger(PreferenceSupplier.class);
	//
	public static final double MIN_DELTA_MINUTES = 0.0d; // 0 Minutes
	public static final double MAX_DELTA_MINUTES = 1.0d; // 1 Minute
	public static final int MIN_NUMBER_TRACES = 1;
	public static final int MAX_NUMBER_TRACES = 10;
	//
	public static final String P_PEAK_DETECTOR_LIST_MSD = "peakDetectorListMSD";
	public static final String DEF_PEAK_DETECTOR_LIST_MSD = "";
	public static final String P_PEAK_DETECTOR_LIST_CSD = "peakDetectorListCSD";
	public static final String DEF_PEAK_DETECTOR_LIST_CSD = "";
	public static final String P_PEAK_IDENTIFIER_LIST_MSD = "peakIdentifierListMSD";
	public static final String DEF_PEAK_IDENTIFIER_LIST_MSD = "";
	public static final String P_PEAK_IDENTIFIER_LIST_CSD = "peakIdentifierListCSD";
	public static final String DEF_PEAK_IDENTIFIER_LIST_CSD = "";
	public static final String P_STANDARDS_ASSIGNER_LIST = "standardsAssignerList";
	public static final String DEF_STANDARDS_ASSIGNER_LIST = "";
	public static final String P_STANDARDS_REFERENCER_LIST = "standardsReferencerList";
	public static final String DEF_STANDARDS_REFERENCER_LIST = "";
	public static final String P_PEAK_INTEGRATOR_LIST = "peakIntegratorList";
	public static final String DEF_PEAK_INTEGRATOR_LIST = "";
	public static final String P_COMPENSATION_QUANTIFIER_LIST = "compensationQuantifierList";
	public static final String DEF_COMPENSATION_QUANTIFIER_LIST = "";
	//
	public static final String P_LIST_PATH_IMPORT = "listPathImport";
	public static final String DEF_LIST_PATH_IMPORT = "";
	public static final String P_LIST_PATH_EXPORT = "listPathExport";
	public static final String DEF_LIST_PATH_EXPORT = "";
	//
	public static final String P_EXPORT_OPTIMIZE_RANGE = "exportOptimizeRange";
	public static final boolean DEF_EXPORT_OPTIMIZE_RANGE = true;
	public static final String P_EXPORT_DELTA_LEFT_MINUTES = "exportDeltaLeftMinutes";
	public static final double DEF_EXPORT_DELTA_LEFT_MINUTES = 0.1d;
	public static final String P_EXPORT_DELTA_RIGHT_MINUTES = "exportDeltaRightMinutes";
	public static final double DEF_EXPORT_DELTA_RIGHT_MINUTES = 0.1d;
	public static final String P_EXPORT_USE_TRACES = "exportUseTraces";
	public static final boolean DEF_EXPORT_USE_TRACES = false;
	public static final String P_EXPORT_NUMBER_TRACES = "exportNumberTraces";
	public static final int DEF_EXPORT_NUMBER_TRACES = 5;
	//
	public static final String P_STANDARDS_EXTRACTOR_CONCENTRATION_UNIT = "standardsExtractorConcentrationUnit";
	public static final String DEF_STANDARDS_EXTRACTOR_CONCENTRATION_UNIT = "ppm";
	//
	public static final String P_UI_DETECTOR_DELTA_LEFT_MINUTES = "uiDetectorDeltaLeftMinutes";
	public static final double DEF_UI_DETECTOR_DELTA_LEFT_MINUTES = 0.5d;
	public static final String P_UI_DETECTOR_DELTA_RIGHT_MINUTES = "uiDetectorDeltaRightMinutes";
	public static final double DEF_UI_DETECTOR_DELTA_RIGHT_MINUTES = 0.5d;
	public static final String P_UI_DETECTOR_REPLACE_PEAK = "uiDetectorReplacePeak";
	public static final boolean DEF_UI_DETECTOR_REPLACE_PEAK = true;
	//
	public static final String P_TRANSFER_USE_BEST_TARGET_ONLY = "transferUseBestTargetOnly";
	public static final boolean DEF_TRANSFER_USE_BEST_TARGET_ONLY = true;
	public static final String P_TRANSFER_RETENTION_TIME_MINUTES_LEFT = "transferRetentionTimeMinutesLeft";
	public static final double DEF_TRANSFER_RETENTION_TIME_MINUTES_LEFT = 0.0d;
	public static final String P_TRANSFER_RETENTION_TIME_MINUTES_RIGHT = "transferRetentionTimeMinutesRight";
	public static final double DEF_TRANSFER_RETENTION_TIME_MINUTES_RIGHT = 0.0d;
	public static final String P_TRANSFER_NUMBER_TRACES = "transferNumberTraces";
	public static final int DEF_TRANSFER_NUMBER_TRACES = 15;
	//
	private static IPreferenceSupplier preferenceSupplier;

	public static IPreferenceSupplier INSTANCE() {

		if(preferenceSupplier == null) {
			preferenceSupplier = new PreferenceSupplier();
		}
		return preferenceSupplier;
	}

	@Override
	public IScopeContext getScopeContext() {

		return InstanceScope.INSTANCE;
	}

	@Override
	public String getPreferenceNode() {

		return Activator.getContext().getBundle().getSymbolicName();
	}

	@Override
	public Map<String, String> getDefaultValues() {

		Map<String, String> defaultValues = new HashMap<String, String>();
		defaultValues.put(P_PEAK_DETECTOR_LIST_MSD, DEF_PEAK_DETECTOR_LIST_MSD);
		defaultValues.put(P_PEAK_DETECTOR_LIST_CSD, DEF_PEAK_DETECTOR_LIST_CSD);
		defaultValues.put(P_PEAK_IDENTIFIER_LIST_MSD, DEF_PEAK_IDENTIFIER_LIST_MSD);
		defaultValues.put(P_PEAK_IDENTIFIER_LIST_CSD, DEF_PEAK_IDENTIFIER_LIST_CSD);
		defaultValues.put(P_STANDARDS_ASSIGNER_LIST, DEF_STANDARDS_ASSIGNER_LIST);
		defaultValues.put(P_STANDARDS_REFERENCER_LIST, DEF_STANDARDS_REFERENCER_LIST);
		defaultValues.put(P_PEAK_INTEGRATOR_LIST, DEF_PEAK_INTEGRATOR_LIST);
		defaultValues.put(P_COMPENSATION_QUANTIFIER_LIST, DEF_COMPENSATION_QUANTIFIER_LIST);
		//
		defaultValues.put(P_LIST_PATH_IMPORT, DEF_LIST_PATH_IMPORT);
		defaultValues.put(P_LIST_PATH_EXPORT, DEF_LIST_PATH_EXPORT);
		//
		defaultValues.put(P_EXPORT_OPTIMIZE_RANGE, Boolean.toString(DEF_EXPORT_OPTIMIZE_RANGE));
		defaultValues.put(P_EXPORT_DELTA_LEFT_MINUTES, Double.toString(DEF_EXPORT_DELTA_LEFT_MINUTES));
		defaultValues.put(P_EXPORT_DELTA_RIGHT_MINUTES, Double.toString(DEF_EXPORT_DELTA_RIGHT_MINUTES));
		defaultValues.put(P_EXPORT_USE_TRACES, Boolean.toString(DEF_EXPORT_USE_TRACES));
		defaultValues.put(P_EXPORT_NUMBER_TRACES, Integer.toString(DEF_EXPORT_NUMBER_TRACES));
		//
		defaultValues.put(P_STANDARDS_EXTRACTOR_CONCENTRATION_UNIT, DEF_STANDARDS_EXTRACTOR_CONCENTRATION_UNIT);
		//
		defaultValues.put(P_UI_DETECTOR_DELTA_LEFT_MINUTES, Double.toString(DEF_UI_DETECTOR_DELTA_LEFT_MINUTES));
		defaultValues.put(P_UI_DETECTOR_DELTA_RIGHT_MINUTES, Double.toString(DEF_UI_DETECTOR_DELTA_RIGHT_MINUTES));
		defaultValues.put(P_UI_DETECTOR_REPLACE_PEAK, Boolean.toString(DEF_UI_DETECTOR_REPLACE_PEAK));
		//
		defaultValues.put(P_TRANSFER_USE_BEST_TARGET_ONLY, Boolean.toString(DEF_TRANSFER_USE_BEST_TARGET_ONLY));
		defaultValues.put(P_TRANSFER_RETENTION_TIME_MINUTES_LEFT, Double.toString(DEF_TRANSFER_RETENTION_TIME_MINUTES_LEFT));
		defaultValues.put(P_TRANSFER_RETENTION_TIME_MINUTES_RIGHT, Double.toString(DEF_TRANSFER_RETENTION_TIME_MINUTES_RIGHT));
		defaultValues.put(P_TRANSFER_NUMBER_TRACES, Integer.toString(DEF_TRANSFER_NUMBER_TRACES));
		//
		return defaultValues;
	}

	@Override
	public IEclipsePreferences getPreferences() {

		return getScopeContext().getNode(getPreferenceNode());
	}

	public static String getSettings(String key, String def) {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.get(key, def);
	}

	public static String getListPathImport() {

		return getFilterPath(P_LIST_PATH_IMPORT, DEF_LIST_PATH_IMPORT);
	}

	public static void setListPathImport(String filterPath) {

		setFilterPath(P_LIST_PATH_IMPORT, filterPath);
	}

	public static String getListPathExport() {

		return getFilterPath(P_LIST_PATH_EXPORT, DEF_LIST_PATH_EXPORT);
	}

	public static void setListPathExport(String filterPath) {

		setFilterPath(P_LIST_PATH_EXPORT, filterPath);
	}

	public static boolean isExportOptimizeRange() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_EXPORT_OPTIMIZE_RANGE, DEF_EXPORT_OPTIMIZE_RANGE);
	}

	public static double getExportDeltaLeftMinutes() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getDouble(P_EXPORT_DELTA_LEFT_MINUTES, DEF_EXPORT_DELTA_LEFT_MINUTES);
	}

	public static double getExportDeltaRightMinutes() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getDouble(P_EXPORT_DELTA_RIGHT_MINUTES, DEF_EXPORT_DELTA_RIGHT_MINUTES);
	}

	public static boolean isUseTraces() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_EXPORT_USE_TRACES, DEF_EXPORT_USE_TRACES);
	}

	public static int getExportNumberTraces() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getInt(P_EXPORT_NUMBER_TRACES, DEF_EXPORT_NUMBER_TRACES);
	}

	public static double getUiDetectorDeltaLeftMinutes() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getDouble(P_UI_DETECTOR_DELTA_LEFT_MINUTES, DEF_UI_DETECTOR_DELTA_LEFT_MINUTES);
	}

	public static double getUiDetectorDeltaRightMinutes() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getDouble(P_UI_DETECTOR_DELTA_RIGHT_MINUTES, DEF_UI_DETECTOR_DELTA_RIGHT_MINUTES);
	}

	public static boolean isDetectorReplacePeak() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_UI_DETECTOR_REPLACE_PEAK, DEF_UI_DETECTOR_REPLACE_PEAK);
	}

	public static void toggleDetectorReplacePeak() {

		boolean replacePeak = isDetectorReplacePeak();
		setDetectorReplacePeak(!replacePeak);
	}

	public static void setDetectorReplacePeak(boolean replacePeak) {

		try {
			IEclipsePreferences preferences = INSTANCE().getPreferences();
			preferences.putBoolean(P_UI_DETECTOR_REPLACE_PEAK, replacePeak);
			preferences.flush();
		} catch(BackingStoreException e) {
			logger.warn(e);
		}
	}

	public static PeakDetectorSettings getPeakDetectorSettingsCSD() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		PeakDetectorSettings settings = new PeakDetectorSettings();
		settings.setDetectorSettings(preferences.get(P_PEAK_DETECTOR_LIST_CSD, DEF_PEAK_DETECTOR_LIST_CSD));
		return settings;
	}

	public static PeakDetectorSettings getPeakDetectorSettingsMSD() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		PeakDetectorSettings settings = new PeakDetectorSettings();
		settings.setDetectorSettings(preferences.get(P_PEAK_DETECTOR_LIST_MSD, DEF_PEAK_DETECTOR_LIST_MSD));
		return settings;
	}

	private static String getFilterPath(String key, String def) {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.get(key, def);
	}

	public static String getStandardsExtractorConcentrationUnit() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.get(P_STANDARDS_EXTRACTOR_CONCENTRATION_UNIT, DEF_STANDARDS_EXTRACTOR_CONCENTRATION_UNIT);
	}

	public static boolean isTransferUseBestTargetOnly() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_TRANSFER_USE_BEST_TARGET_ONLY, DEF_TRANSFER_USE_BEST_TARGET_ONLY);
	}

	public static double getTransferRetentionTimeMinutesLeft() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getDouble(P_TRANSFER_RETENTION_TIME_MINUTES_LEFT, DEF_TRANSFER_RETENTION_TIME_MINUTES_LEFT);
	}

	public static double getTransferRetentionTimeMinutesRight() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getDouble(P_TRANSFER_RETENTION_TIME_MINUTES_RIGHT, DEF_TRANSFER_RETENTION_TIME_MINUTES_RIGHT);
	}

	public static int getTransferNumberTraces() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getInt(P_TRANSFER_NUMBER_TRACES, DEF_TRANSFER_NUMBER_TRACES);
	}

	private static void setFilterPath(String key, String filterPath) {

		try {
			IEclipsePreferences preferences = INSTANCE().getPreferences();
			preferences.put(key, filterPath);
			preferences.flush();
		} catch(BackingStoreException e) {
			logger.warn(e);
		}
	}
}

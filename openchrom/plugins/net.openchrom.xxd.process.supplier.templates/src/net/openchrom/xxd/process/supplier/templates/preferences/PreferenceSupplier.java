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
	public static final double MIN_DELTA_MINUTES = 0.0d; // 0 Minutes
	public static final double MAX_DELTA_MINUTES = 1.0d; // 1 Minute
	public static final String P_EXPORT_DELTA_LEFT_MINUTES = "exportDeltaLeftMinutes";
	public static final double DEF_EXPORT_DELTA_LEFT_MINUTES = 0.1d;
	public static final String P_EXPORT_DELTA_RIGHT_MINUTES = "exportDeltaRightMinutes";
	public static final double DEF_EXPORT_DELTA_RIGHT_MINUTES = 0.1d;
	public static final String P_EXPORT_USE_TRACES = "exportUseTraces";
	public static final boolean DEF_EXPORT_USE_TRACES = false;
	public static final int MIN_NUMBER_TRACES = 1;
	public static final int MAX_NUMBER_TRACES = 10;
	public static final String P_EXPORT_NUMBER_TRACES = "exportNumberTraces";
	public static final int DEF_EXPORT_NUMBER_TRACES = 5;
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

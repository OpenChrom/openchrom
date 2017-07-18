/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.processor.supplier.tracecompare.preferences;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.support.preferences.IPreferenceSupplier;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.BackingStoreException;

import net.openchrom.xxd.processor.supplier.tracecompare.Activator;

public class PreferenceSupplier implements IPreferenceSupplier {

	private static final Logger logger = Logger.getLogger(PreferenceSupplier.class);
	//
	public static final String DETECTOR_MSD = "MSD";
	public static final String DETECTOR_CSD = "CSD";
	public static final String DETECTOR_WSD = "WSD";
	public static final String P_DETECTOR_TYPE = "detectorType";
	public static final String DEF_DETECTOR_TYPE = DETECTOR_WSD;
	//
	public static final String P_FILTER_PATH_SAMPLES = "filterPathSamples";
	public static final String DEF_FILTER_PATH_SAMPLES = "";
	public static final String P_FILTER_PATH_REFERNCES = "filterPathReferences";
	public static final String DEF_FILTER_PATH_REFERNCES = "";
	//
	public static final String P_SEARCH_CASE_SENSITIVE = "searchCaseSensitive"; // $NON-NLS-1$
	public static final boolean DEF_SEARCH_CASE_SENSITIVE = false; // $NON-NLS-1$
	//
	public static final int MIN_SCAN_VELOCITY = 0;
	public static final int MAX_SCAN_VELOCITY = Integer.MAX_VALUE;
	public static final String P_SCAN_VELOCITY = "scanVelocity"; // mm/s
	public static final int DEF_SCAN_VELOCITY = 20;
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
		defaultValues.put(P_DETECTOR_TYPE, DEF_DETECTOR_TYPE);
		defaultValues.put(P_FILTER_PATH_SAMPLES, DEF_FILTER_PATH_SAMPLES);
		defaultValues.put(P_FILTER_PATH_REFERNCES, DEF_FILTER_PATH_REFERNCES);
		defaultValues.put(P_SEARCH_CASE_SENSITIVE, Boolean.toString(DEF_SEARCH_CASE_SENSITIVE));
		defaultValues.put(P_SCAN_VELOCITY, Integer.toString(DEF_SCAN_VELOCITY));
		return defaultValues;
	}

	@Override
	public IEclipsePreferences getPreferences() {

		return getScopeContext().getNode(getPreferenceNode());
	}

	public static String[][] getDetectorTypes() {

		int versions = 1; // Only VWD at the moment
		String[][] elements = new String[versions][2];
		//
		elements[0][0] = DETECTOR_WSD + " (VWD, DAD, ...)";
		elements[0][1] = DETECTOR_WSD;
		//
		// elements[0][0] = DETECTOR_MSD + " (NominalMS, TandemMS, HighResMS)";
		// elements[0][1] = DETECTOR_MSD;
		// elements[1][0] = DETECTOR_CSD + " (FID, NPD, ...)";
		// elements[1][1] = DETECTOR_CSD;
		// elements[2][0] = DETECTOR_WSD + " (VWD, DAD, ...)";
		// elements[2][1] = DETECTOR_WSD;
		//
		return elements;
	}

	public static String getFilterPathSamples() {

		return getFilterPath(P_FILTER_PATH_SAMPLES, DEF_FILTER_PATH_SAMPLES);
	}

	public static void setFilterPathSamples(String filterPath) {

		setFilterPath(P_FILTER_PATH_SAMPLES, filterPath);
	}

	public static String getFilterPathReferences() {

		return getFilterPath(P_FILTER_PATH_REFERNCES, DEF_FILTER_PATH_REFERNCES);
	}

	public static void setFilterPathReferences(String filterPath) {

		setFilterPath(P_FILTER_PATH_REFERNCES, filterPath);
	}

	public static boolean isSearchCaseSensitive() {

		IEclipsePreferences eclipsePreferences = INSTANCE().getPreferences();
		return eclipsePreferences.getBoolean(P_SEARCH_CASE_SENSITIVE, DEF_SEARCH_CASE_SENSITIVE);
	}

	public static void setSearchCaseSensitive(boolean searchCaseSensitive) {

		try {
			IEclipsePreferences eclipsePreferences = INSTANCE().getPreferences();
			eclipsePreferences.putBoolean(P_SEARCH_CASE_SENSITIVE, searchCaseSensitive);
			eclipsePreferences.flush();
		} catch(BackingStoreException e) {
			logger.warn(e);
		}
	}

	public static int getScanVelocity() {

		IEclipsePreferences eclipsePreferences = INSTANCE().getPreferences();
		return eclipsePreferences.getInt(P_SCAN_VELOCITY, DEF_SCAN_VELOCITY);
	}

	private static String getFilterPath(String key, String def) {

		IEclipsePreferences eclipsePreferences = INSTANCE().getPreferences();
		return eclipsePreferences.get(key, def);
	}

	private static void setFilterPath(String key, String filterPath) {

		try {
			IEclipsePreferences eclipsePreferences = INSTANCE().getPreferences();
			eclipsePreferences.put(key, filterPath);
			eclipsePreferences.flush();
		} catch(BackingStoreException e) {
			logger.warn(e);
		}
	}
}

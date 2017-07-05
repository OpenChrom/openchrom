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
	public static final String P_FILTER_PATH_SAMPLE = "filterPathSample";
	public static final String DEF_FILTER_PATH_SAMPLE = "";
	public static final String P_FILTER_PATH_REFERNCES = "filterPathReferences";
	public static final String DEF_FILTER_PATH_REFERNCES = "";
	//
	public static final String P_SEARCH_CASE_SENSITIVE = "searchCaseSensitive"; // $NON-NLS-1$
	public static final boolean DEF_SEARCH_CASE_SENSITIVE = false; // $NON-NLS-1$
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
		defaultValues.put(P_FILTER_PATH_SAMPLE, DEF_FILTER_PATH_SAMPLE);
		defaultValues.put(P_FILTER_PATH_REFERNCES, DEF_FILTER_PATH_REFERNCES);
		defaultValues.put(P_SEARCH_CASE_SENSITIVE, Boolean.toString(DEF_SEARCH_CASE_SENSITIVE));
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

	public static String getFilterPathSample() {

		return getFilterPath(P_FILTER_PATH_SAMPLE, DEF_FILTER_PATH_SAMPLE);
	}

	public static void setFilterPathSample(String filterPath) {

		setFilterPath(P_FILTER_PATH_SAMPLE, filterPath);
	}

	public static String getFilterPathReferences() {

		return getFilterPath(P_FILTER_PATH_REFERNCES, DEF_FILTER_PATH_REFERNCES);
	}

	public static void setFilterPathReferences(String filterPath) {

		setFilterPath(P_FILTER_PATH_REFERNCES, filterPath);
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

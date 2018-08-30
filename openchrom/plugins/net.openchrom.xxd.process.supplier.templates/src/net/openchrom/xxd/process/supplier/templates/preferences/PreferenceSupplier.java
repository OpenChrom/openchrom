/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
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
	public static final String P_PEAK_DETECTOR_LIST_PATH_IMPORT = "peakDetectorListPathImport";
	public static final String DEF_PEAK_DETECTOR_LIST_PATH_IMPORT = "";
	public static final String P_PEAK_DETECTOR_LIST_PATH_EXPORT = "peakDetectorListPathExport";
	public static final String DEF_PEAK_DETECTOR_LIST_PATH_EXPORT = "";
	public static final String P_PEAK_IDENTIFIER_LIST_PATH_IMPORT = "peakIdentifierListPathImport";
	public static final String DEF_PEAK_IDENTIFIER_LIST_PATH_IMPORT = "";
	public static final String P_PEAK_IDENTIFIER_LIST_PATH_EXPORT = "peakIdentifierListPathExport";
	public static final String DEF_PEAK_IDENTIFIER_LIST_PATH_EXPORT = "";
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
		defaultValues.put(P_PEAK_DETECTOR_LIST_PATH_IMPORT, DEF_PEAK_DETECTOR_LIST_PATH_IMPORT);
		defaultValues.put(P_PEAK_DETECTOR_LIST_PATH_EXPORT, DEF_PEAK_DETECTOR_LIST_PATH_EXPORT);
		defaultValues.put(P_PEAK_IDENTIFIER_LIST_PATH_IMPORT, DEF_PEAK_IDENTIFIER_LIST_PATH_IMPORT);
		defaultValues.put(P_PEAK_IDENTIFIER_LIST_PATH_EXPORT, DEF_PEAK_IDENTIFIER_LIST_PATH_EXPORT);
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

	public static String getPeakDetectorListPathImport() {

		return getFilterPath(P_PEAK_DETECTOR_LIST_PATH_IMPORT, DEF_PEAK_DETECTOR_LIST_PATH_IMPORT);
	}

	public static void setPeakDetectorListPathImport(String filterPath) {

		setFilterPath(P_PEAK_DETECTOR_LIST_PATH_IMPORT, filterPath);
	}

	public static String getPeakDetectorListPathExport() {

		return getFilterPath(P_PEAK_DETECTOR_LIST_PATH_EXPORT, DEF_PEAK_DETECTOR_LIST_PATH_EXPORT);
	}

	public static void setPeakDetectorListPathExport(String filterPath) {

		setFilterPath(P_PEAK_DETECTOR_LIST_PATH_EXPORT, filterPath);
	}

	public static String getPeakIdentifierListPathImport() {

		return getFilterPath(P_PEAK_IDENTIFIER_LIST_PATH_IMPORT, DEF_PEAK_IDENTIFIER_LIST_PATH_IMPORT);
	}

	public static void setPeakIdentifierListPathImport(String filterPath) {

		setFilterPath(P_PEAK_IDENTIFIER_LIST_PATH_IMPORT, filterPath);
	}

	public static String getPeakIdentifierListPathExport() {

		return getFilterPath(P_PEAK_IDENTIFIER_LIST_PATH_EXPORT, DEF_PEAK_IDENTIFIER_LIST_PATH_EXPORT);
	}

	public static void setPeakIdentifierListPathExport(String filterPath) {

		setFilterPath(P_PEAK_IDENTIFIER_LIST_PATH_EXPORT, filterPath);
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

/*******************************************************************************
 * Copyright (c) 2017, 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.preferences;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.support.preferences.IPreferenceSupplier;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.BackingStoreException;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.Activator;

public class PreferenceSupplier implements IPreferenceSupplier {

	private static final Logger logger = Logger.getLogger(PreferenceSupplier.class);
	//
	public static final String P_FILTER_PATH_REFERENCE_CHROMATOGRAM = "filterPathReferenceChromatogram";
	public static final String DEF_FILTER_PATH_REFERENCE_CHROMATOGRAM = "";
	public static final String P_FILTER_PATH_ISOTOPE_CHROMATOGRAM = "filterPathIsotopeChromatogram";
	public static final String DEF_FILTER_PATH_ISOTOPE_CHROMATOGRAM = "";
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
		defaultValues.put(P_FILTER_PATH_REFERENCE_CHROMATOGRAM, DEF_FILTER_PATH_REFERENCE_CHROMATOGRAM);
		defaultValues.put(P_FILTER_PATH_ISOTOPE_CHROMATOGRAM, DEF_FILTER_PATH_ISOTOPE_CHROMATOGRAM);
		return defaultValues;
	}

	@Override
	public IEclipsePreferences getPreferences() {

		return getScopeContext().getNode(getPreferenceNode());
	}

	public static String getFilterPathReferenceChromatogram() {

		return getFilterPath(P_FILTER_PATH_REFERENCE_CHROMATOGRAM, DEF_FILTER_PATH_REFERENCE_CHROMATOGRAM);
	}

	public static void setFilterPathReferenceChromatogram(String filterPath) {

		setFilterPath(P_FILTER_PATH_REFERENCE_CHROMATOGRAM, filterPath);
	}

	public static String getFilterPathIsotopeChromatogram() {

		return getFilterPath(P_FILTER_PATH_ISOTOPE_CHROMATOGRAM, DEF_FILTER_PATH_ISOTOPE_CHROMATOGRAM);
	}

	public static void setFilterPathIsotopeChromatogram(String filterPath) {

		setFilterPath(P_FILTER_PATH_ISOTOPE_CHROMATOGRAM, filterPath);
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

/*******************************************************************************
 * Copyright (c) 2014, 2020 Lablicate GmbH.
 * 
 * All rights reserved.
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.converter.supplier.pdf.preferences;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.chemclipse.support.preferences.IPreferenceSupplier;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;

import net.openchrom.xxd.converter.supplier.pdf.Activator;

public class PreferenceSupplier implements IPreferenceSupplier {

	public static final int MIN_NUMBER_IMAGE_PAGES = 1;
	public static final int MAX_NUMBER_IMAGE_PAGES = 30;
	public static final int MIN_NUMBER_LARGEST_PEAKS = 0;
	public static final int MAX_NUMBER_LARGEST_PEAKS = 30;
	//
	public static final String P_REPORT_BANNER = "reportBanner";
	public static final String DEF_REPORT_BANNER = ""; // default openchromlogo.jpg
	public static final String P_REPORT_SLOGAN = "reportSlogan";
	public static final String DEF_REPORT_SLOGAN = "OpenChrom - the open source alternative for chromatography/mass spectrometry";
	/*
	 * Generic
	 */
	public static final String P_NUMBER_IMAGE_PAGES = "numberImagePages";
	public static final int DEF_NUMBER_IMAGE_PAGES = 5;
	/*
	 * Profile
	 */
	public static final String P_REPORT_METHOD = "reportMethod";
	public static final String DEF_REPORT_METHOD = "GC Analysis";
	public static final String P_NUMBER_LARGEST_PEAKS = "numberLargestPeaks";
	public static final int DEF_NUMBER_LARGEST_PEAKS = 10;
	public static final String P_PRINT_ALL_TARGETS = "printAllTargets";
	public static final boolean DEF_PRINT_ALL_TARGETS = false;
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
		defaultValues.put(P_NUMBER_IMAGE_PAGES, Integer.toString(DEF_NUMBER_IMAGE_PAGES));
		defaultValues.put(P_REPORT_BANNER, DEF_REPORT_BANNER);
		defaultValues.put(P_REPORT_SLOGAN, DEF_REPORT_SLOGAN);
		defaultValues.put(P_REPORT_METHOD, DEF_REPORT_METHOD);
		defaultValues.put(P_NUMBER_LARGEST_PEAKS, Integer.toString(DEF_NUMBER_LARGEST_PEAKS));
		defaultValues.put(P_PRINT_ALL_TARGETS, Boolean.toString(DEF_PRINT_ALL_TARGETS));
		return defaultValues;
	}

	@Override
	public IEclipsePreferences getPreferences() {

		return getScopeContext().getNode(getPreferenceNode());
	}

	public static int getNumberImagePages() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getInt(P_NUMBER_IMAGE_PAGES, DEF_NUMBER_IMAGE_PAGES);
	}

	public static String getReportBanner() {

		IEclipsePreferences eclipsePreferences = INSTANCE().getPreferences();
		return eclipsePreferences.get(P_REPORT_BANNER, DEF_REPORT_BANNER);
	}

	public static String getReportSlogan() {

		IEclipsePreferences eclipsePreferences = INSTANCE().getPreferences();
		return eclipsePreferences.get(P_REPORT_SLOGAN, DEF_REPORT_SLOGAN);
	}

	public static String getReportMethod() {

		IEclipsePreferences eclipsePreferences = INSTANCE().getPreferences();
		return eclipsePreferences.get(P_REPORT_METHOD, DEF_REPORT_METHOD);
	}

	public static int getNumberLargestPeaks() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getInt(P_NUMBER_LARGEST_PEAKS, DEF_NUMBER_LARGEST_PEAKS);
	}

	public static boolean printAllTargets() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_PRINT_ALL_TARGETS, DEF_PRINT_ALL_TARGETS);
	}
}

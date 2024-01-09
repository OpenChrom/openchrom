/*******************************************************************************
 * Copyright (c) 2014, 2024 Lablicate GmbH.
 * 
 * All rights reserved.
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.converter.supplier.pdf.preferences;

import org.eclipse.chemclipse.support.preferences.AbstractPreferenceSupplier;
import org.eclipse.chemclipse.support.preferences.IPreferenceSupplier;

import net.openchrom.xxd.converter.supplier.pdf.Activator;

public class PreferenceSupplier extends AbstractPreferenceSupplier implements IPreferenceSupplier {

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

		putDefault(P_NUMBER_IMAGE_PAGES, Integer.toString(DEF_NUMBER_IMAGE_PAGES));
		putDefault(P_REPORT_BANNER, DEF_REPORT_BANNER);
		putDefault(P_REPORT_SLOGAN, DEF_REPORT_SLOGAN);
		putDefault(P_REPORT_METHOD, DEF_REPORT_METHOD);
		putDefault(P_NUMBER_LARGEST_PEAKS, Integer.toString(DEF_NUMBER_LARGEST_PEAKS));
		putDefault(P_PRINT_ALL_TARGETS, Boolean.toString(DEF_PRINT_ALL_TARGETS));
	}

	public static int getNumberImagePages() {

		return INSTANCE().getInteger(P_NUMBER_IMAGE_PAGES, DEF_NUMBER_IMAGE_PAGES);
	}

	public static String getReportBanner() {

		return INSTANCE().get(P_REPORT_BANNER, DEF_REPORT_BANNER);
	}

	public static String getReportSlogan() {

		return INSTANCE().get(P_REPORT_SLOGAN, DEF_REPORT_SLOGAN);
	}

	public static String getReportMethod() {

		return INSTANCE().get(P_REPORT_METHOD, DEF_REPORT_METHOD);
	}

	public static int getNumberLargestPeaks() {

		return INSTANCE().getInteger(P_NUMBER_LARGEST_PEAKS, DEF_NUMBER_LARGEST_PEAKS);
	}

	public static boolean printAllTargets() {

		return INSTANCE().getBoolean(P_PRINT_ALL_TARGETS, DEF_PRINT_ALL_TARGETS);
	}
}
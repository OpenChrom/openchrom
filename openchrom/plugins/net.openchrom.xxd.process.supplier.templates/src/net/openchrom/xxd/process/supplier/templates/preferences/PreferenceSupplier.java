/*******************************************************************************
 * Copyright (c) 2018, 2020 Lablicate GmbH.
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
import net.openchrom.xxd.process.supplier.templates.settings.ChromatogramReportSettings;
import net.openchrom.xxd.process.supplier.templates.settings.PeakDetectorDirectSettings;
import net.openchrom.xxd.process.supplier.templates.settings.PeakDetectorSettings;
import net.openchrom.xxd.process.supplier.templates.settings.PeakReviewSettings;

public class PreferenceSupplier implements IPreferenceSupplier {

	private static final Logger logger = Logger.getLogger(PreferenceSupplier.class);
	//
	public static final int MIN_DELTA_MILLISECONDS = 0; // 0 Minutes
	public static final int MAX_DELTA_MILLISECONDS = 120000; // 2 Minutes
	public static final int MIN_NUMBER_TRACES = 0; // 0 = TIC
	public static final int MAX_NUMBER_TRACES = Integer.MAX_VALUE;
	public static final int MIN_OFFSET_Y = 0; // %
	public static final int MAX_OFFSET_Y = 100; // %
	public static final int MIN_PEAK_OVERLAP_COVERAGE = 1; // %
	public static final int MAX_PEAK_OVERLAP_COVERAGE = 100; // %
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
	public static final String P_CHROMATOGRAM_REPORT_LIST = "chromatogramReportList";
	public static final String DEF_CHROMATOGRAM_REPORT_LIST = "";
	public static final String P_CHROMATOGRAM_REVIEW_LIST_MSD = "chromatogramReviewListMSD";
	public static final String DEF_CHROMATOGRAM_REVIEW_LIST_MSD = "";
	public static final String P_CHROMATOGRAM_REVIEW_LIST_CSD = "chromatogramReviewListCSD";
	public static final String DEF_CHROMATOGRAM_REVIEW_LIST_CSD = "";
	//
	public static final String P_LIST_PATH_IMPORT = "listPathImport";
	public static final String DEF_LIST_PATH_IMPORT = "";
	public static final String P_LIST_PATH_EXPORT = "listPathExport";
	public static final String DEF_LIST_PATH_EXPORT = "";
	//
	public static final String P_CHART_BUFFERED_SELECTION = "chartBufferedSelection";
	public static final boolean DEF_CHART_BUFFERED_SELECTION = false;
	public static final String P_OFFSET_MIN_Y = "offsetMinY";
	public static final int DEF_OFFSET_MIN_Y = 50;
	public static final String P_OFFSET_MAX_Y = "offsetMaxY";
	public static final int DEF_OFFSET_MAX_Y = 30;
	//
	public static final String P_SORT_IMPORT_TEMPLATE = "sortImportTemplate";
	public static final boolean DEF_SORT_IMPORT_TEMPLATE = false;
	public static final String P_SORT_EXPORT_TEMPLATE = "sortExportTemplate";
	public static final boolean DEF_SORT_EXPORT_TEMPLATE = false;
	public static final String P_DETECTOR_SETTINGS_SORT = "detectorSettingsSort";
	public static final boolean DEF_DETECTOR_SETTINGS_SORT = false;
	public static final String P_REVIEW_SETTINGS_SORT = "reviewSettingsSort";
	public static final boolean DEF_REVIEW_SETTINGS_SORT = false;
	public static final String P_REPORT_SETTINGS_SORT = "reportSettingsSort";
	public static final boolean DEF_REPORT_SETTINGS_SORT = false;
	/*
	 * Detector
	 */
	public static final String P_EXPORT_NUMBER_TRACES_DETECTOR = "exportNumberTracesDetector";
	public static final int DEF_EXPORT_NUMBER_TRACES_DETECTOR = 10;
	public static final String P_EXPORT_OPTIMIZE_RANGE_DETECTOR = "exportOptimizeRangeDetector";
	public static final boolean DEF_EXPORT_OPTIMIZE_RANGE_DETECTOR = true;
	public static final String P_EXPORT_DELTA_LEFT_MILLISECONDS_DETECTOR = "exportDeltaLeftMillisecondsDetector";
	public static final int DEF_EXPORT_DELTA_LEFT_MILLISECONDS_DETECTOR = 1000; // 1 [s]
	public static final String P_EXPORT_DELTA_RIGHT_MILLISECONDS_DETECTOR = "exportDeltaRightMillisecondsDetector";
	public static final int DEF_EXPORT_DELTA_RIGHT_MILLISECONDS_DETECTOR = 1000; // 1 [s]
	/*
	 * Identifier
	 */
	public static final String P_EXPORT_NUMBER_TRACES_IDENTIFIER = "exportNumberTracesIdentifier";
	public static final int DEF_EXPORT_NUMBER_TRACES_IDENTIFIER = 2;
	public static final String P_EXPORT_DELTA_LEFT_MILLISECONDS_IDENTIFIER = "exportDeltaLeftMillisecondsIdentifier";
	public static final int DEF_EXPORT_DELTA_LEFT_MILLISECONDS_IDENTIFIER = 0;
	public static final String P_EXPORT_DELTA_RIGHT_MILLISECONDS_IDENTIFIER = "exportDeltaRightMillisecondsIdentifier";
	public static final int DEF_EXPORT_DELTA_RIGHT_MILLISECONDS_IDENTIFIER = 0;
	/*
	 * Review
	 */
	public static final String P_EXPORT_NUMBER_TRACES_REVIEW = "exportNumberTracesReview";
	public static final int DEF_EXPORT_NUMBER_TRACES_REVIEW = 5;
	public static final String P_EXPORT_DELTA_LEFT_MILLISECONDS_REVIEW = "exportDeltaLeftMillisecondsReview";
	public static final int DEF_EXPORT_DELTA_LEFT_MILLISECONDS_REVIEW = 0;
	public static final String P_EXPORT_DELTA_RIGHT_MILLISECONDS_REVIEW = "exportDeltaRightMillisecondsReview";
	public static final int DEF_EXPORT_DELTA_RIGHT_MILLISECONDS_REVIEW = 0;
	/*
	 * Assigner
	 */
	public static final String P_EXPORT_DELTA_LEFT_MILLISECONDS_ASSIGNER = "exportDeltaLeftMillisecondsAssigner";
	public static final int DEF_EXPORT_DELTA_LEFT_MILLISECONDS_ASSIGNER = 0;
	public static final String P_EXPORT_DELTA_RIGHT_MILLISECONDS_ASSIGNER = "exportDeltaRightMillisecondsAssigner";
	public static final int DEF_EXPORT_DELTA_RIGHT_MILLISECONDS_ASSIGNER = 0;
	/*
	 * Standards
	 */
	public static final String P_EXPORT_DELTA_LEFT_MILLISECONDS_STANDARDS = "exportDeltaLeftMillisecondsStandards";
	public static final int DEF_EXPORT_DELTA_LEFT_MILLISECONDS_STANDARDS = 0;
	public static final String P_EXPORT_DELTA_RIGHT_MILLISECONDS_STANDARDS = "exportDeltaRightMillisecondsStandards";
	public static final int DEF_EXPORT_DELTA_RIGHT_MILLISECONDS_STANDARDS = 0;
	/*
	 * Report
	 */
	public static final String P_EXPORT_DELTA_LEFT_MILLISECONDS_REPORT = "exportDeltaLeftMillisecondsReport";
	public static final int DEF_EXPORT_DELTA_LEFT_MILLISECONDS_REPORT = 0;
	public static final String P_EXPORT_DELTA_RIGHT_MILLISECONDS_REPORT = "exportDeltaRightMillisecondsReport";
	public static final int DEF_EXPORT_DELTA_RIGHT_MILLISECONDS_REPORT = 0;
	/*
	 * 
	 */
	public static final String P_STANDARDS_EXTRACTOR_CONCENTRATION_UNIT = "standardsExtractorConcentrationUnit";
	public static final String DEF_STANDARDS_EXTRACTOR_CONCENTRATION_UNIT = "ppm";
	/*
	 * Transfer
	 */
	public static final String P_TRANSFER_USE_IDENTIFIED_PEAKS_ONLY = "transferUseIdentifiedPeaksOnly";
	public static final boolean DEF_TRANSFER_USE_IDENTIFIED_PEAKS_ONLY = true;
	public static final String P_TRANSFER_USE_BEST_TARGET_ONLY = "transferUseBestTargetOnly";
	public static final boolean DEF_TRANSFER_USE_BEST_TARGET_ONLY = true;
	public static final String P_TRANSFER_RETENTION_TIME_MILLISECONDS_LEFT = "transferRetentionTimeMillisecondsLeft";
	public static final int DEF_TRANSFER_RETENTION_TIME_MILLISECONDS_LEFT = 0;
	public static final String P_TRANSFER_RETENTION_TIME_MILLISECONDS_RIGHT = "transferRetentionTimeMillisecondsRight";
	public static final int DEF_TRANSFER_RETENTION_TIME_MILLISECONDS_RIGHT = 0;
	public static final String P_TRANSFER_OFFSET_RETENTION_TIME_MILLISECONDS_PEAK_MAXIMUM = "transferOffsetRetentionTimeMillisecondsPeakMaximum";
	public static final int DEF_TRANSFER_OFFSET_RETENTION_TIME_MILLISECONDS_PEAK_MAXIMUM = 500;
	public static final String P_TRANSFER_ADJUST_PEAK_HEIGHT = "transferAdjustPeakHeight";
	public static final boolean DEF_TRANSFER_ADJUST_PEAK_HEIGHT = true;
	public static final String P_TRANSFER_CREATE_MODEL_PEAK = "transferCreateModelPeak";
	public static final boolean DEF_TRANSFER_CREATE_MODEL_PEAK = true;
	public static final String P_TRANSFER_PEAK_OVERLAP_COVERAGE = "transferPeakOverlapCoverage";
	public static final double DEF_TRANSFER_PEAK_OVERLAP_COVERAGE = 12.5f;
	public static final String P_TRANSFER_OPTIMIZE_RANGE = "transferOptimizeRange";
	public static final boolean DEF_TRANSFER_OPTIMIZE_RANGE = true;
	public static final String P_TRANSFER_CHECK_PURITY = "transferCheckPurity";
	public static final boolean DEF_TRANSFER_CHECK_PURITY = true;
	public static final String P_TRANSFER_NUMBER_TRACES = "transferNumberTraces";
	public static final int DEF_TRANSFER_NUMBER_TRACES = 15;
	/*
	 * Report
	 */
	public static final String P_REPORT_REFERENCED_CHROMATOGRAMS = "reportReferencedChromatograms";
	public static final boolean DEF_REPORT_REFERENCED_CHROMATOGRAMS = false;
	/*
	 * Peak Detector
	 */
	public static final String P_DETECTOR_DELTA_LEFT_MILLISECONDS = "detectorDeltaLeftMilliseconds";
	public static final int DEF_DETECTOR_DELTA_LEFT_MILLISECONDS = 0;
	public static final String P_DETECTOR_DELTA_RIGHT_MILLISECONDS = "detectorDeltaRightMilliseconds";
	public static final int DEF_DETECTOR_DELTA_RIGHT_MILLISECONDS = 0;
	public static final String P_DETECTOR_REPLACE_NEAREST_PEAK = "detectorReplaceNearestPeak";
	public static final boolean DEF_DETECTOR_REPLACE_NEAREST_PEAK = true;
	public static final String P_DETECTOR_SHOW_CHROMATOGRAM_TIC = "detectorShowChromatogramTIC";
	public static final boolean DEF_DETECTOR_SHOW_CHROMATOGRAM_TIC = true;
	public static final String P_DETECTOR_SHOW_CHROMATOGRAM_XIC = "detectorShowChromatogramXIC";
	public static final boolean DEF_DETECTOR_SHOW_CHROMATOGRAM_XIC = true;
	public static final String P_DETECTOR_FOCUS_XIC = "detectorFocusXIC";
	public static final boolean DEF_DETECTOR_FOCUS_XIC = true;
	public static final String P_DETECTOR_SHOW_BASELINE = "detectorShowBaseline";
	public static final boolean DEF_DETECTOR_SHOW_BASELINE = false;
	public static final String P_DETECTOR_SHOW_ONLY_RELEVANT_PEAKS = "detectorShowOnlyRelevantPeaks";
	public static final boolean DEF_DETECTOR_SHOW_ONLY_RELEVANT_PEAKS = false;
	/*
	 * Peak Review
	 */
	public static final String P_REVIEW_DELTA_LEFT_MILLISECONDS = "reviewDeltaLeftMilliseconds";
	public static final int DEF_REVIEW_DELTA_LEFT_MILLISECONDS = 0;
	public static final String P_REVIEW_DELTA_RIGHT_MILLISECONDS = "reviewDeltaRightMilliseconds";
	public static final int DEF_REVIEW_DELTA_RIGHT_MILLISECONDS = 0;
	public static final String P_REVIEW_REPLACE_NEAREST_PEAK = "reviewReplaceNearestPeak";
	public static final boolean DEF_REVIEW_REPLACE_NEAREST_PEAK = true;
	public static final String P_REVIEW_SET_TARGET_DETECTED_PEAK = "reviewSetTargetDetectedPeak";
	public static final boolean DEF_REVIEW_SET_TARGET_DETECTED_PEAK = true;
	public static final String P_REVIEW_AUTO_SELECT_BEST_MATCH = "reviewAutoSelectBestMatch";
	public static final boolean DEF_REVIEW_AUTO_SELECT_BEST_MATCH = false;
	public static final String P_REVIEW_SET_TARGET_VERIFICATION = "reviewSetTargetVerification";
	public static final boolean DEF_REVIEW_SET_TARGET_VERIFICATION = true;
	public static final String P_REVIEW_SHOW_CHROMATOGRAM_TIC = "reviewShowChromatogramTIC";
	public static final boolean DEF_REVIEW_SHOW_CHROMATOGRAM_TIC = true;
	public static final String P_REVIEW_SHOW_CHROMATOGRAM_XIC = "reviewShowChromatogramXIC";
	public static final boolean DEF_REVIEW_SHOW_CHROMATOGRAM_XIC = true;
	public static final String P_REVIEW_FOCUS_XIC = "reviewFocusXIC";
	public static final boolean DEF_REVIEW_FOCUS_XIC = true;
	public static final String P_REVIEW_SHOW_BASELINE = "reviewShowBaseline";
	public static final boolean DEF_REVIEW_SHOW_BASELINE = false;
	public static final String P_REVIEW_SHOW_DETAILS = "reviewShowDetails";
	public static final boolean DEF_REVIEW_SHOW_DETAILS = false;
	public static final String P_REVIEW_UPDATE_SEARCH_TARGET = "reviewUpdateSearchTarget";
	public static final boolean DEF_REVIEW_UPDATE_SEARCH_TARGET = false;
	public static final String P_REVIEW_FETCH_LIBRARY_SPECTRUM = "reviewFetchLibrarySpectrum";
	public static final boolean DEF_REVIEW_FETCH_LIBRARY_SPECTRUM = false;
	public static final String P_REVIEW_SHOW_ONLY_RELEVANT_PEAKS = "reviewShowOnlyRelevantPeaks";
	public static final boolean DEF_REVIEW_SHOW_ONLY_RELEVANT_PEAKS = false;
	public static final String P_REVIEW_IGNORE_NULL_CAS_NUMBER = "reviewIgnoreNullCasNumber";
	public static final boolean DEF_REVIEW_IGNORE_NULL_CAS_NUMBER = true;
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
		defaultValues.put(P_CHROMATOGRAM_REPORT_LIST, DEF_CHROMATOGRAM_REPORT_LIST);
		defaultValues.put(P_CHROMATOGRAM_REVIEW_LIST_MSD, DEF_CHROMATOGRAM_REVIEW_LIST_MSD);
		defaultValues.put(P_CHROMATOGRAM_REVIEW_LIST_CSD, DEF_CHROMATOGRAM_REVIEW_LIST_CSD);
		//
		defaultValues.put(P_LIST_PATH_IMPORT, DEF_LIST_PATH_IMPORT);
		defaultValues.put(P_LIST_PATH_EXPORT, DEF_LIST_PATH_EXPORT);
		//
		defaultValues.put(P_CHART_BUFFERED_SELECTION, Boolean.toString(DEF_CHART_BUFFERED_SELECTION));
		defaultValues.put(P_OFFSET_MIN_Y, Integer.toString(DEF_OFFSET_MIN_Y));
		defaultValues.put(P_OFFSET_MAX_Y, Integer.toString(DEF_OFFSET_MAX_Y));
		//
		defaultValues.put(P_SORT_IMPORT_TEMPLATE, Boolean.toString(DEF_SORT_IMPORT_TEMPLATE));
		defaultValues.put(P_SORT_EXPORT_TEMPLATE, Boolean.toString(DEF_SORT_EXPORT_TEMPLATE));
		defaultValues.put(P_DETECTOR_SETTINGS_SORT, Boolean.toString(DEF_DETECTOR_SETTINGS_SORT));
		defaultValues.put(P_REVIEW_SETTINGS_SORT, Boolean.toString(DEF_REVIEW_SETTINGS_SORT));
		defaultValues.put(P_REPORT_SETTINGS_SORT, Boolean.toString(DEF_REPORT_SETTINGS_SORT));
		//
		defaultValues.put(P_EXPORT_NUMBER_TRACES_DETECTOR, Integer.toString(DEF_EXPORT_NUMBER_TRACES_DETECTOR));
		defaultValues.put(P_EXPORT_OPTIMIZE_RANGE_DETECTOR, Boolean.toString(DEF_EXPORT_OPTIMIZE_RANGE_DETECTOR));
		defaultValues.put(P_EXPORT_DELTA_LEFT_MILLISECONDS_DETECTOR, Integer.toString(DEF_EXPORT_DELTA_LEFT_MILLISECONDS_DETECTOR));
		defaultValues.put(P_EXPORT_DELTA_RIGHT_MILLISECONDS_DETECTOR, Integer.toString(DEF_EXPORT_DELTA_RIGHT_MILLISECONDS_DETECTOR));
		//
		defaultValues.put(P_EXPORT_NUMBER_TRACES_IDENTIFIER, Integer.toString(DEF_EXPORT_NUMBER_TRACES_IDENTIFIER));
		defaultValues.put(P_EXPORT_DELTA_LEFT_MILLISECONDS_IDENTIFIER, Integer.toString(DEF_EXPORT_DELTA_LEFT_MILLISECONDS_IDENTIFIER));
		defaultValues.put(P_EXPORT_DELTA_RIGHT_MILLISECONDS_IDENTIFIER, Integer.toString(DEF_EXPORT_DELTA_RIGHT_MILLISECONDS_IDENTIFIER));
		//
		defaultValues.put(P_EXPORT_NUMBER_TRACES_REVIEW, Integer.toString(DEF_EXPORT_NUMBER_TRACES_REVIEW));
		defaultValues.put(P_EXPORT_DELTA_LEFT_MILLISECONDS_REVIEW, Integer.toString(DEF_EXPORT_DELTA_LEFT_MILLISECONDS_REVIEW));
		defaultValues.put(P_EXPORT_DELTA_RIGHT_MILLISECONDS_REVIEW, Integer.toString(DEF_EXPORT_DELTA_RIGHT_MILLISECONDS_REVIEW));
		//
		defaultValues.put(P_EXPORT_DELTA_LEFT_MILLISECONDS_ASSIGNER, Integer.toString(DEF_EXPORT_DELTA_LEFT_MILLISECONDS_ASSIGNER));
		defaultValues.put(P_EXPORT_DELTA_RIGHT_MILLISECONDS_ASSIGNER, Integer.toString(DEF_EXPORT_DELTA_RIGHT_MILLISECONDS_ASSIGNER));
		//
		defaultValues.put(P_EXPORT_DELTA_LEFT_MILLISECONDS_STANDARDS, Integer.toString(DEF_EXPORT_DELTA_LEFT_MILLISECONDS_STANDARDS));
		defaultValues.put(P_EXPORT_DELTA_RIGHT_MILLISECONDS_STANDARDS, Integer.toString(DEF_EXPORT_DELTA_RIGHT_MILLISECONDS_STANDARDS));
		//
		defaultValues.put(P_EXPORT_DELTA_LEFT_MILLISECONDS_REPORT, Double.toString(DEF_EXPORT_DELTA_LEFT_MILLISECONDS_REPORT));
		defaultValues.put(P_EXPORT_DELTA_RIGHT_MILLISECONDS_REPORT, Double.toString(DEF_EXPORT_DELTA_RIGHT_MILLISECONDS_REPORT));
		//
		defaultValues.put(P_STANDARDS_EXTRACTOR_CONCENTRATION_UNIT, DEF_STANDARDS_EXTRACTOR_CONCENTRATION_UNIT);
		/*
		 * Transfer
		 */
		defaultValues.put(P_TRANSFER_USE_IDENTIFIED_PEAKS_ONLY, Boolean.toString(DEF_TRANSFER_USE_IDENTIFIED_PEAKS_ONLY));
		defaultValues.put(P_TRANSFER_USE_BEST_TARGET_ONLY, Boolean.toString(DEF_TRANSFER_USE_BEST_TARGET_ONLY));
		defaultValues.put(P_TRANSFER_RETENTION_TIME_MILLISECONDS_LEFT, Integer.toString(DEF_TRANSFER_RETENTION_TIME_MILLISECONDS_LEFT));
		defaultValues.put(P_TRANSFER_RETENTION_TIME_MILLISECONDS_RIGHT, Integer.toString(DEF_TRANSFER_RETENTION_TIME_MILLISECONDS_RIGHT));
		defaultValues.put(P_TRANSFER_OFFSET_RETENTION_TIME_MILLISECONDS_PEAK_MAXIMUM, Integer.toString(DEF_TRANSFER_OFFSET_RETENTION_TIME_MILLISECONDS_PEAK_MAXIMUM));
		defaultValues.put(P_TRANSFER_ADJUST_PEAK_HEIGHT, Boolean.toString(DEF_TRANSFER_ADJUST_PEAK_HEIGHT));
		defaultValues.put(P_TRANSFER_CREATE_MODEL_PEAK, Boolean.toString(DEF_TRANSFER_CREATE_MODEL_PEAK));
		defaultValues.put(P_TRANSFER_PEAK_OVERLAP_COVERAGE, Double.toString(DEF_TRANSFER_PEAK_OVERLAP_COVERAGE));
		defaultValues.put(P_TRANSFER_OPTIMIZE_RANGE, Boolean.toString(DEF_TRANSFER_OPTIMIZE_RANGE));
		defaultValues.put(P_TRANSFER_CHECK_PURITY, Boolean.toString(DEF_TRANSFER_CHECK_PURITY));
		defaultValues.put(P_TRANSFER_NUMBER_TRACES, Integer.toString(DEF_TRANSFER_NUMBER_TRACES));
		//
		defaultValues.put(P_REPORT_REFERENCED_CHROMATOGRAMS, Boolean.toString(DEF_REPORT_REFERENCED_CHROMATOGRAMS));
		/*
		 * Detector
		 */
		defaultValues.put(P_DETECTOR_DELTA_LEFT_MILLISECONDS, Integer.toString(DEF_DETECTOR_DELTA_LEFT_MILLISECONDS));
		defaultValues.put(P_DETECTOR_DELTA_RIGHT_MILLISECONDS, Integer.toString(DEF_DETECTOR_DELTA_RIGHT_MILLISECONDS));
		defaultValues.put(P_DETECTOR_REPLACE_NEAREST_PEAK, Boolean.toString(DEF_DETECTOR_REPLACE_NEAREST_PEAK));
		defaultValues.put(P_DETECTOR_SHOW_CHROMATOGRAM_TIC, Boolean.toString(DEF_DETECTOR_SHOW_CHROMATOGRAM_TIC));
		defaultValues.put(P_DETECTOR_SHOW_CHROMATOGRAM_XIC, Boolean.toString(DEF_DETECTOR_SHOW_CHROMATOGRAM_XIC));
		defaultValues.put(P_DETECTOR_FOCUS_XIC, Boolean.toString(DEF_DETECTOR_FOCUS_XIC));
		defaultValues.put(P_DETECTOR_SHOW_BASELINE, Boolean.toString(DEF_DETECTOR_SHOW_BASELINE));
		defaultValues.put(P_DETECTOR_SHOW_ONLY_RELEVANT_PEAKS, Boolean.toString(DEF_DETECTOR_SHOW_ONLY_RELEVANT_PEAKS));
		/*
		 * Review
		 */
		defaultValues.put(P_REVIEW_DELTA_LEFT_MILLISECONDS, Integer.toString(DEF_REVIEW_DELTA_LEFT_MILLISECONDS));
		defaultValues.put(P_REVIEW_DELTA_RIGHT_MILLISECONDS, Integer.toString(DEF_REVIEW_DELTA_RIGHT_MILLISECONDS));
		defaultValues.put(P_REVIEW_REPLACE_NEAREST_PEAK, Boolean.toString(DEF_REVIEW_REPLACE_NEAREST_PEAK));
		defaultValues.put(P_REVIEW_SET_TARGET_VERIFICATION, Boolean.toString(DEF_REVIEW_SET_TARGET_VERIFICATION));
		defaultValues.put(P_REVIEW_AUTO_SELECT_BEST_MATCH, Boolean.toString(DEF_REVIEW_AUTO_SELECT_BEST_MATCH));
		defaultValues.put(P_REVIEW_SET_TARGET_DETECTED_PEAK, Boolean.toString(DEF_REVIEW_SET_TARGET_DETECTED_PEAK));
		defaultValues.put(P_REVIEW_SHOW_CHROMATOGRAM_TIC, Boolean.toString(DEF_REVIEW_SHOW_CHROMATOGRAM_TIC));
		defaultValues.put(P_REVIEW_SHOW_CHROMATOGRAM_XIC, Boolean.toString(DEF_REVIEW_SHOW_CHROMATOGRAM_XIC));
		defaultValues.put(P_REVIEW_FOCUS_XIC, Boolean.toString(DEF_REVIEW_FOCUS_XIC));
		defaultValues.put(P_REVIEW_SHOW_BASELINE, Boolean.toString(DEF_REVIEW_SHOW_BASELINE));
		defaultValues.put(P_REVIEW_SHOW_DETAILS, Boolean.toString(DEF_REVIEW_SHOW_DETAILS));
		defaultValues.put(P_REVIEW_UPDATE_SEARCH_TARGET, Boolean.toString(DEF_REVIEW_UPDATE_SEARCH_TARGET));
		defaultValues.put(P_REVIEW_FETCH_LIBRARY_SPECTRUM, Boolean.toString(DEF_REVIEW_FETCH_LIBRARY_SPECTRUM));
		defaultValues.put(P_REVIEW_SHOW_ONLY_RELEVANT_PEAKS, Boolean.toString(DEF_REVIEW_SHOW_ONLY_RELEVANT_PEAKS));
		defaultValues.put(P_REVIEW_IGNORE_NULL_CAS_NUMBER, Boolean.toString(DEF_REVIEW_IGNORE_NULL_CAS_NUMBER));
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

		putString(P_LIST_PATH_IMPORT, filterPath);
	}

	public static String getListPathExport() {

		return getFilterPath(P_LIST_PATH_EXPORT, DEF_LIST_PATH_EXPORT);
	}

	public static void setListPathExport(String filterPath) {

		putString(P_LIST_PATH_EXPORT, filterPath);
	}

	public static boolean isChartBufferedSelection() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_CHART_BUFFERED_SELECTION, DEF_CHART_BUFFERED_SELECTION);
	}

	public static int getOffsetMinY() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getInt(P_OFFSET_MIN_Y, DEF_OFFSET_MIN_Y);
	}

	public static int getOffsetMaxY() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getInt(P_OFFSET_MAX_Y, DEF_OFFSET_MAX_Y);
	}

	public static boolean isSortImportTemplate() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_SORT_IMPORT_TEMPLATE, DEF_SORT_IMPORT_TEMPLATE);
	}

	public static boolean isSortExportTemplate() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_SORT_EXPORT_TEMPLATE, DEF_SORT_EXPORT_TEMPLATE);
	}

	public static boolean isDetectorSettingsSort() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_DETECTOR_SETTINGS_SORT, DEF_DETECTOR_SETTINGS_SORT);
	}

	public static boolean isReviewSettingsSort() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_REVIEW_SETTINGS_SORT, DEF_REVIEW_SETTINGS_SORT);
	}

	public static boolean isReportSettingsSort() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_REPORT_SETTINGS_SORT, DEF_REPORT_SETTINGS_SORT);
	}

	public static boolean isExportOptimizeRangeDetector() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_EXPORT_OPTIMIZE_RANGE_DETECTOR, DEF_EXPORT_OPTIMIZE_RANGE_DETECTOR);
	}

	public static int getExportDeltaLeftMillisecondsDetector() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getInt(P_EXPORT_DELTA_LEFT_MILLISECONDS_DETECTOR, DEF_EXPORT_DELTA_LEFT_MILLISECONDS_DETECTOR);
	}

	public static int getExportDeltaRightMillisecondsDetector() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getInt(P_EXPORT_DELTA_RIGHT_MILLISECONDS_DETECTOR, DEF_EXPORT_DELTA_RIGHT_MILLISECONDS_DETECTOR);
	}

	public static int getExportDeltaLeftMillisecondsIdentifier() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getInt(P_EXPORT_DELTA_LEFT_MILLISECONDS_IDENTIFIER, DEF_EXPORT_DELTA_LEFT_MILLISECONDS_IDENTIFIER);
	}

	public static int getExportDeltaRightMillisecondsIdentifier() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getInt(P_EXPORT_DELTA_RIGHT_MILLISECONDS_IDENTIFIER, DEF_EXPORT_DELTA_RIGHT_MILLISECONDS_IDENTIFIER);
	}

	public static int getExportDeltaLeftMillisecondsReview() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getInt(P_EXPORT_DELTA_LEFT_MILLISECONDS_REVIEW, DEF_EXPORT_DELTA_LEFT_MILLISECONDS_REVIEW);
	}

	public static int getExportDeltaRightMillisecondsReview() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getInt(P_EXPORT_DELTA_RIGHT_MILLISECONDS_REVIEW, DEF_EXPORT_DELTA_RIGHT_MILLISECONDS_REVIEW);
	}

	public static int getExportDeltaLeftMillisecondsAssigner() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getInt(P_EXPORT_DELTA_LEFT_MILLISECONDS_ASSIGNER, DEF_EXPORT_DELTA_LEFT_MILLISECONDS_ASSIGNER);
	}

	public static int getExportDeltaRightMillisecondsAssigner() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getInt(P_EXPORT_DELTA_RIGHT_MILLISECONDS_ASSIGNER, DEF_EXPORT_DELTA_RIGHT_MILLISECONDS_ASSIGNER);
	}

	public static int getExportDeltaLeftMillisecondsStandards() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getInt(P_EXPORT_DELTA_LEFT_MILLISECONDS_STANDARDS, DEF_EXPORT_DELTA_LEFT_MILLISECONDS_STANDARDS);
	}

	public static int getExportDeltaRightMillisecondsStandards() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getInt(P_EXPORT_DELTA_RIGHT_MILLISECONDS_STANDARDS, DEF_EXPORT_DELTA_RIGHT_MILLISECONDS_STANDARDS);
	}

	public static int getExportDeltaLeftMillisecondsReport() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getInt(P_EXPORT_DELTA_LEFT_MILLISECONDS_REPORT, DEF_EXPORT_DELTA_LEFT_MILLISECONDS_REPORT);
	}

	public static int getExportDeltaRightMillisecondsReport() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getInt(P_EXPORT_DELTA_RIGHT_MILLISECONDS_REPORT, DEF_EXPORT_DELTA_RIGHT_MILLISECONDS_REPORT);
	}

	public static int getExportNumberTracesDetector() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getInt(P_EXPORT_NUMBER_TRACES_DETECTOR, DEF_EXPORT_NUMBER_TRACES_DETECTOR);
	}

	public static int getExportNumberTracesIdentifier() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getInt(P_EXPORT_NUMBER_TRACES_IDENTIFIER, DEF_EXPORT_NUMBER_TRACES_IDENTIFIER);
	}

	public static int getExportNumberTracesReview() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getInt(P_EXPORT_NUMBER_TRACES_REVIEW, DEF_EXPORT_NUMBER_TRACES_REVIEW);
	}

	public static int getDetectorDeltaLeftMilliseconds() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getInt(P_DETECTOR_DELTA_LEFT_MILLISECONDS, DEF_DETECTOR_DELTA_LEFT_MILLISECONDS);
	}

	public static int getDetectorDeltaRightMilliseconds() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getInt(P_DETECTOR_DELTA_RIGHT_MILLISECONDS, DEF_DETECTOR_DELTA_RIGHT_MILLISECONDS);
	}

	public static boolean isDetectorReplaceNearestPeak() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_DETECTOR_REPLACE_NEAREST_PEAK, DEF_DETECTOR_REPLACE_NEAREST_PEAK);
	}

	public static void toggleDetectorReplaceNearestPeak() {

		boolean replacePeak = isDetectorReplaceNearestPeak();
		putBoolean(P_DETECTOR_REPLACE_NEAREST_PEAK, !replacePeak);
	}

	public static boolean isShowChromatogramDetectorTIC() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_DETECTOR_SHOW_CHROMATOGRAM_TIC, DEF_DETECTOR_SHOW_CHROMATOGRAM_TIC);
	}

	public static void toggleShowChromatogramDetectorTIC() {

		boolean show = isShowChromatogramDetectorTIC();
		putBoolean(P_DETECTOR_SHOW_CHROMATOGRAM_TIC, !show);
	}

	public static boolean isShowChromatogramDetectorXIC() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_DETECTOR_SHOW_CHROMATOGRAM_XIC, DEF_DETECTOR_SHOW_CHROMATOGRAM_XIC);
	}

	public static void toggleShowChromatogramDetectorXIC() {

		boolean show = isShowChromatogramDetectorXIC();
		putBoolean(P_DETECTOR_SHOW_CHROMATOGRAM_XIC, !show);
	}

	public static boolean isDetectorFocusXIC() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_DETECTOR_FOCUS_XIC, DEF_DETECTOR_FOCUS_XIC);
	}

	public static boolean isShowBaselineDetector() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_DETECTOR_SHOW_BASELINE, DEF_DETECTOR_SHOW_BASELINE);
	}

	public static void toggleShowBaselineDetector() {

		boolean show = isShowBaselineDetector();
		putBoolean(P_DETECTOR_SHOW_BASELINE, !show);
	}

	public static boolean isDetectorShowOnlyRelevantPeaks() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_DETECTOR_SHOW_ONLY_RELEVANT_PEAKS, DEF_DETECTOR_SHOW_ONLY_RELEVANT_PEAKS);
	}

	public static int getReviewDeltaLeftMilliseconds() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getInt(P_REVIEW_DELTA_LEFT_MILLISECONDS, DEF_REVIEW_DELTA_LEFT_MILLISECONDS);
	}

	public static int getReviewDeltaRightMilliseconds() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getInt(P_REVIEW_DELTA_RIGHT_MILLISECONDS, DEF_REVIEW_DELTA_RIGHT_MILLISECONDS);
	}

	public static boolean isReviewReplaceNearestPeak() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_REVIEW_REPLACE_NEAREST_PEAK, DEF_REVIEW_REPLACE_NEAREST_PEAK);
	}

	public static void toggleReviewReplaceNearestPeak() {

		boolean replacePeak = isReviewReplaceNearestPeak();
		putBoolean(P_REVIEW_REPLACE_NEAREST_PEAK, !replacePeak);
	}

	public static boolean isReviewSetTargetVerification() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_REVIEW_SET_TARGET_VERIFICATION, DEF_REVIEW_SET_TARGET_VERIFICATION);
	}

	public static boolean isReviewAutoSelectBestMatch() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_REVIEW_AUTO_SELECT_BEST_MATCH, DEF_REVIEW_AUTO_SELECT_BEST_MATCH);
	}

	public static boolean isReviewSetTargetDetectedPeak() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_REVIEW_SET_TARGET_DETECTED_PEAK, DEF_REVIEW_SET_TARGET_DETECTED_PEAK);
	}

	public static boolean isShowChromatogramReviewTIC() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_REVIEW_SHOW_CHROMATOGRAM_TIC, DEF_REVIEW_SHOW_CHROMATOGRAM_TIC);
	}

	public static void toggleShowChromatogramReviewTIC() {

		boolean show = isShowChromatogramReviewTIC();
		putBoolean(P_REVIEW_SHOW_CHROMATOGRAM_TIC, !show);
	}

	public static boolean isShowChromatogramReviewXIC() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_REVIEW_SHOW_CHROMATOGRAM_XIC, DEF_REVIEW_SHOW_CHROMATOGRAM_XIC);
	}

	public static void toggleShowChromatogramReviewXIC() {

		boolean show = isShowChromatogramReviewXIC();
		putBoolean(P_REVIEW_SHOW_CHROMATOGRAM_XIC, !show);
	}

	public static boolean isReviewFocusXIC() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_REVIEW_FOCUS_XIC, DEF_REVIEW_FOCUS_XIC);
	}

	public static boolean isShowBaselineReview() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_REVIEW_SHOW_BASELINE, DEF_REVIEW_SHOW_BASELINE);
	}

	public static void toggleShowBaselineReview() {

		boolean show = isShowBaselineReview();
		putBoolean(P_REVIEW_SHOW_BASELINE, !show);
	}

	public static boolean isShowReviewDetails() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_REVIEW_SHOW_DETAILS, DEF_REVIEW_SHOW_DETAILS);
	}

	public static void toggleShowReviewDetails() {

		boolean show = isShowReviewDetails();
		putBoolean(P_REVIEW_SHOW_DETAILS, !show);
	}

	public static boolean isReviewUpdateSearchTarget() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_REVIEW_UPDATE_SEARCH_TARGET, DEF_REVIEW_UPDATE_SEARCH_TARGET);
	}

	public static boolean isReviewFetchLibrarySpectrum() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_REVIEW_FETCH_LIBRARY_SPECTRUM, DEF_REVIEW_FETCH_LIBRARY_SPECTRUM);
	}

	public static boolean isReviewShowOnlyRelevantPeaks() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_REVIEW_SHOW_ONLY_RELEVANT_PEAKS, DEF_REVIEW_SHOW_ONLY_RELEVANT_PEAKS);
	}

	public static boolean isReviewIgnoreNullCasNumber() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_REVIEW_IGNORE_NULL_CAS_NUMBER, DEF_REVIEW_IGNORE_NULL_CAS_NUMBER);
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

	public static PeakDetectorDirectSettings getPeakDetectorSettingsDirectMSD() {

		PeakDetectorDirectSettings settings = new PeakDetectorDirectSettings();
		settings.setTraces("");
		return settings;
	}

	public static ChromatogramReportSettings getChromatogramReportSettings() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		ChromatogramReportSettings settings = new ChromatogramReportSettings();
		settings.setReportReferencedChromatograms(preferences.getBoolean(P_REPORT_REFERENCED_CHROMATOGRAMS, DEF_REPORT_REFERENCED_CHROMATOGRAMS));
		settings.setReportSettings(preferences.get(P_CHROMATOGRAM_REPORT_LIST, DEF_CHROMATOGRAM_REPORT_LIST));
		return settings;
	}

	public static PeakReviewSettings getReviewSettingsMSD() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		PeakReviewSettings settings = new PeakReviewSettings();
		settings.setReviewSettings(preferences.get(P_CHROMATOGRAM_REVIEW_LIST_MSD, DEF_CHROMATOGRAM_REVIEW_LIST_MSD));
		return settings;
	}

	public static PeakReviewSettings getReviewSettingsCSD() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		PeakReviewSettings settings = new PeakReviewSettings();
		settings.setReviewSettings(preferences.get(P_CHROMATOGRAM_REVIEW_LIST_CSD, DEF_CHROMATOGRAM_REVIEW_LIST_CSD));
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

	public static boolean isTransferUseIdentifiedPeaksOnly() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_TRANSFER_USE_IDENTIFIED_PEAKS_ONLY, DEF_TRANSFER_USE_IDENTIFIED_PEAKS_ONLY);
	}

	public static boolean isTransferUseBestTargetOnly() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_TRANSFER_USE_BEST_TARGET_ONLY, DEF_TRANSFER_USE_BEST_TARGET_ONLY);
	}

	public static int getTransferRetentionTimeMillisecondsLeft() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getInt(P_TRANSFER_RETENTION_TIME_MILLISECONDS_LEFT, DEF_TRANSFER_RETENTION_TIME_MILLISECONDS_LEFT);
	}

	public static int getTransferRetentionTimeMillisecondsRight() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getInt(P_TRANSFER_RETENTION_TIME_MILLISECONDS_RIGHT, DEF_TRANSFER_RETENTION_TIME_MILLISECONDS_RIGHT);
	}

	public static int getTransferOffsetRetentionTimePeakMaximum() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getInt(P_TRANSFER_OFFSET_RETENTION_TIME_MILLISECONDS_PEAK_MAXIMUM, DEF_TRANSFER_OFFSET_RETENTION_TIME_MILLISECONDS_PEAK_MAXIMUM);
	}

	public static boolean isTransferAdjustPeakHeight() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_TRANSFER_ADJUST_PEAK_HEIGHT, DEF_TRANSFER_ADJUST_PEAK_HEIGHT);
	}

	public static boolean isTransferCreateModelPeak() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_TRANSFER_CREATE_MODEL_PEAK, DEF_TRANSFER_CREATE_MODEL_PEAK);
	}

	public static double getTransferPeakOverlapCoverage() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getDouble(P_TRANSFER_PEAK_OVERLAP_COVERAGE, DEF_TRANSFER_PEAK_OVERLAP_COVERAGE);
	}

	public static boolean isTransferOptimizeRange() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_TRANSFER_OPTIMIZE_RANGE, DEF_TRANSFER_OPTIMIZE_RANGE);
	}

	public static boolean isTransferCheckPurity() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_TRANSFER_CHECK_PURITY, DEF_TRANSFER_CHECK_PURITY);
	}

	public static int getTransferNumberTraces() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getInt(P_TRANSFER_NUMBER_TRACES, DEF_TRANSFER_NUMBER_TRACES);
	}

	private static void putBoolean(String key, boolean value) {

		try {
			IEclipsePreferences preferences = INSTANCE().getPreferences();
			preferences.putBoolean(key, value);
			preferences.flush();
		} catch(BackingStoreException e) {
			logger.warn(e);
		}
	}

	private static void putString(String key, String value) {

		try {
			IEclipsePreferences preferences = INSTANCE().getPreferences();
			preferences.put(key, value);
			preferences.flush();
		} catch(BackingStoreException e) {
			logger.warn(e);
		}
	}
}

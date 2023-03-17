/*******************************************************************************
 * Copyright (c) 2018, 2023 Lablicate GmbH.
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

import org.eclipse.chemclipse.support.preferences.IPreferenceSupplier;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;

import net.openchrom.xxd.process.supplier.templates.Activator;
import net.openchrom.xxd.process.supplier.templates.model.PositionDirective;
import net.openchrom.xxd.process.supplier.templates.model.Visibility;
import net.openchrom.xxd.process.supplier.templates.settings.ChromatogramReportSettings;
import net.openchrom.xxd.process.supplier.templates.settings.FilterSettingsRetentionIndexMapper;
import net.openchrom.xxd.process.supplier.templates.settings.PeakDetectorDirectSettings;
import net.openchrom.xxd.process.supplier.templates.settings.PeakDetectorSettings;
import net.openchrom.xxd.process.supplier.templates.settings.PeakReviewSettings;

public class PreferenceSupplier implements IPreferenceSupplier {

	public static final String[][] VISIBILITY_OPTIONS = new String[][]{//
			{"TIC", Visibility.TIC.name()}, //
			{"TRACE", Visibility.TRACE.name()}, //
			{"BOTH", Visibility.BOTH.name()} //
	};
	//
	public static final double MIN_DELTA_POSITION = 0;
	public static final double MAX_DELTA_POSITION = 120000;
	public static final int MIN_DELTA_MILLISECONDS = 0; // 0 Minutes
	public static final int MAX_DELTA_MILLISECONDS = 120000; // 2 Minutes
	public static final int MIN_NUMBER_TRACES = 0; // 0 = TIC
	public static final int MAX_NUMBER_TRACES = Integer.MAX_VALUE;
	public static final int MIN_OFFSET_Y = 0; // %
	public static final int MAX_OFFSET_Y = 100; // %
	public static final int MIN_PEAK_OVERLAP_COVERAGE = 1; // %
	public static final int MAX_PEAK_OVERLAP_COVERAGE = 100; // %
	public static final int MIN_TRACES = 1;
	public static final int MAX_TRACES = 100;
	public static final float MIN_FACTOR = 0.0f;
	public static final float MAX_FACTOR = 100.0f;
	public static final int MIN_DELTA_REPLACE_PEAK_MILLISECONDS = 0;
	public static final int MAX_DELTA_REPLACE_PEAK_MILLISECONDS = Integer.MAX_VALUE;
	public static final int DEF_DELTA_REPLACE_PEAK_MILLISECONDS = 5000; // 5 Seconds
	//
	public static final String P_PEAK_DETECTOR_LIST_MSD = "peakDetectorListMSD";
	public static final String DEF_PEAK_DETECTOR_LIST_MSD = "";
	public static final String P_PEAK_DETECTOR_LIST_CSD = "peakDetectorListCSD";
	public static final String DEF_PEAK_DETECTOR_LIST_CSD = "";
	public static final String P_PEAK_DETECTOR_LIST_WSD = "peakDetectorListWSD";
	public static final String DEF_PEAK_DETECTOR_LIST_WSD = "";
	public static final String P_PEAK_IDENTIFIER_LIST_MSD = "peakIdentifierListMSD";
	public static final String DEF_PEAK_IDENTIFIER_LIST_MSD = "";
	public static final String P_PEAK_IDENTIFIER_LIST_CSD = "peakIdentifierListCSD";
	public static final String DEF_PEAK_IDENTIFIER_LIST_CSD = "";
	public static final String P_PEAK_IDENTIFIER_LIST_WSD = "peakIdentifierListWSD";
	public static final String DEF_PEAK_IDENTIFIER_LIST_WSD = "";
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
	public static final String P_EXPORT_DELTA_LEFT_POSITION_DETECTOR = "exportDeltaLeftPositionDetector";
	public static final double DEF_EXPORT_DELTA_LEFT_POSITION_DETECTOR = 0.0d;
	public static final String P_EXPORT_DELTA_RIGHT_POSITION_DETECTOR = "exportDeltaRightPositionDetector";
	public static final double DEF_EXPORT_DELTA_RIGHT_POSITION_DETECTOR = 0.0d;
	public static final String P_EXPORT_POSITION_DIRECTIVE_DETECTOR = "exportPositionDirectiveDetector";
	public static final String DEF_EXPORT_POSITION_DIRECTIVE_DETECTOR = PositionDirective.RETENTION_TIME_MIN.name();
	/*
	 * Identifier
	 */
	public static final String P_EXPORT_NUMBER_TRACES_IDENTIFIER = "exportNumberTracesIdentifier";
	public static final int DEF_EXPORT_NUMBER_TRACES_IDENTIFIER = 2;
	public static final String P_EXPORT_DELTA_LEFT_MILLISECONDS_IDENTIFIER = "exportDeltaLeftMillisecondsIdentifier";
	public static final int DEF_EXPORT_DELTA_LEFT_MILLISECONDS_IDENTIFIER = 0;
	public static final String P_EXPORT_DELTA_RIGHT_MILLISECONDS_IDENTIFIER = "exportDeltaRightMillisecondsIdentifier";
	public static final int DEF_EXPORT_DELTA_RIGHT_MILLISECONDS_IDENTIFIER = 0;
	//
	public static final String P_LIMIT_MATCH_FACTOR_IDENTIFIER = "limitMatchFactorIdentifier";
	public static final float DEF_LIMIT_MATCH_FACTOR_IDENTIFIER = 80.0f;
	public static final String P_MATCH_QUALITY_IDENTIFIER = "matchQualityIdentifier";
	public static final float DEF_MATCH_QUALITY_IDENTIFIER = 80.0f;
	/*
	 * Review
	 */
	public static final String P_EXPORT_NUMBER_TRACES_REVIEW = "exportNumberTracesReview";
	public static final int DEF_EXPORT_NUMBER_TRACES_REVIEW = 5;
	public static final String P_EXPORT_OPTIMIZE_RANGE_REVIEW = "exportOptimizeRangeReview";
	public static final boolean DEF_EXPORT_OPTIMIZE_RANGE_REVIEW = true;
	public static final String P_EXPORT_DELTA_LEFT_MILLISECONDS_REVIEW = "exportDeltaLeftMillisecondsReview";
	public static final int DEF_EXPORT_DELTA_LEFT_MILLISECONDS_REVIEW = 0;
	public static final String P_EXPORT_DELTA_RIGHT_MILLISECONDS_REVIEW = "exportDeltaRightMillisecondsReview";
	public static final int DEF_EXPORT_DELTA_RIGHT_MILLISECONDS_REVIEW = 0;
	/*
	 * Assigner
	 */
	public static final String P_EXPORT_NUMBER_TRACES_ASSIGNER = "exportNumberTracesAssigner";
	public static final int DEF_EXPORT_NUMBER_TRACES_ASSIGNER = 0;
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
	public static final String P_TRANSFER_MATCH_QUALITY = "transferMatchQuality";
	public static final float DEF_TRANSFER_MATCH_QUALITY = 80.0f;
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
	public static final String P_DETECTOR_DYNAMIC_OFFSET_MILLISECONDS = "detectorDynamicOffsetMilliseconds";
	public static final int DEF_DETECTOR_DYNAMIC_OFFSET_MILLISECONDS = 1000;
	public static final String P_DETECTOR_REPLACE_NEAREST_PEAK = "detectorReplaceNearestPeak";
	public static final boolean DEF_DETECTOR_REPLACE_NEAREST_PEAK = true;
	public static final String P_DETECTOR_REPLACE_PEAK_DELTA_MILLISECONDS = "reviewReplacePeakDeltaMilliseconds";
	public static final int DEF_DETECTOR_REPLACE_PEAK_DELTA_MILLISECONDS = DEF_DELTA_REPLACE_PEAK_MILLISECONDS;
	public static final String P_DETECTOR_HIDE_EXISTING_PEAKS = "detectorHideExistingPeaks";
	public static final boolean DEF_DETECTOR_HIDE_EXISTING_PEAKS = false;
	public static final String P_DETECTOR_VISIBILITY = "detectorVisibility";
	public static final String DEF_DETECTOR_VISIBILITY = Visibility.BOTH.name();;
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
	public static final String P_REVIEW_DYNAMIC_OFFSET_MILLISECONDS = "reviewDynamicOffsetMilliseconds";
	public static final int DEF_REVIEW_DYNAMIC_OFFSET_MILLISECONDS = 1000;
	public static final String P_REVIEW_REPLACE_NEAREST_PEAK = "reviewReplaceNearestPeak";
	public static final boolean DEF_REVIEW_REPLACE_NEAREST_PEAK = true;
	public static final String P_REVIEW_REPLACE_PEAK_DELTA_MILLISECONDS = "reviewReplacePeakDeltaMilliseconds";
	public static final int DEF_REVIEW_REPLACE_PEAK_DELTA_MILLISECONDS = DEF_DELTA_REPLACE_PEAK_MILLISECONDS;
	public static final String P_REVIEW_SET_TARGET_DETECTED_PEAK = "reviewSetTargetDetectedPeak";
	public static final boolean DEF_REVIEW_SET_TARGET_DETECTED_PEAK = true;
	public static final String P_REVIEW_AUTO_SELECT_BEST_MATCH = "reviewAutoSelectBestMatch";
	public static final boolean DEF_REVIEW_AUTO_SELECT_BEST_MATCH = false;
	public static final String P_REVIEW_HIDE_EXISTING_PEAKS = "reviewHideExistingPeaks";
	public static final boolean DEF_REVIEW_HIDE_EXISTING_PEAKS = true;
	public static final String P_REVIEW_SET_TARGET_VERIFICATION = "reviewSetTargetVerification";
	public static final boolean DEF_REVIEW_SET_TARGET_VERIFICATION = true;
	public static final String P_REVIEW_VISIBILITY = "reviewVisibility";
	public static final String DEF_REVIEW_VISIBILITY = Visibility.BOTH.name();
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
	/*
	 * Named Traces
	 */
	public static final String P_PEAK_EXPORT_NUMBER_TRACES = "peakExportNumberTraces";
	public static final int DEF_PEAK_EXPORT_NUMBER_TRACES = 5;
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

		Map<String, String> defaultValues = new HashMap<>();
		defaultValues.put(P_PEAK_DETECTOR_LIST_MSD, DEF_PEAK_DETECTOR_LIST_MSD);
		defaultValues.put(P_PEAK_DETECTOR_LIST_CSD, DEF_PEAK_DETECTOR_LIST_CSD);
		defaultValues.put(P_PEAK_DETECTOR_LIST_WSD, DEF_PEAK_DETECTOR_LIST_WSD);
		defaultValues.put(P_PEAK_IDENTIFIER_LIST_MSD, DEF_PEAK_IDENTIFIER_LIST_MSD);
		defaultValues.put(P_PEAK_IDENTIFIER_LIST_CSD, DEF_PEAK_IDENTIFIER_LIST_CSD);
		defaultValues.put(P_PEAK_IDENTIFIER_LIST_WSD, DEF_PEAK_IDENTIFIER_LIST_WSD);
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
		defaultValues.put(P_EXPORT_DELTA_LEFT_POSITION_DETECTOR, Double.toString(DEF_EXPORT_DELTA_LEFT_POSITION_DETECTOR));
		defaultValues.put(P_EXPORT_DELTA_RIGHT_POSITION_DETECTOR, Double.toString(DEF_EXPORT_DELTA_RIGHT_POSITION_DETECTOR));
		defaultValues.put(P_EXPORT_POSITION_DIRECTIVE_DETECTOR, DEF_EXPORT_POSITION_DIRECTIVE_DETECTOR);
		//
		defaultValues.put(P_EXPORT_NUMBER_TRACES_IDENTIFIER, Integer.toString(DEF_EXPORT_NUMBER_TRACES_IDENTIFIER));
		defaultValues.put(P_EXPORT_DELTA_LEFT_MILLISECONDS_IDENTIFIER, Integer.toString(DEF_EXPORT_DELTA_LEFT_MILLISECONDS_IDENTIFIER));
		defaultValues.put(P_EXPORT_DELTA_RIGHT_MILLISECONDS_IDENTIFIER, Integer.toString(DEF_EXPORT_DELTA_RIGHT_MILLISECONDS_IDENTIFIER));
		defaultValues.put(P_LIMIT_MATCH_FACTOR_IDENTIFIER, Float.toString(DEF_LIMIT_MATCH_FACTOR_IDENTIFIER));
		defaultValues.put(P_MATCH_QUALITY_IDENTIFIER, Float.toString(DEF_MATCH_QUALITY_IDENTIFIER));
		//
		defaultValues.put(P_EXPORT_NUMBER_TRACES_REVIEW, Integer.toString(DEF_EXPORT_NUMBER_TRACES_REVIEW));
		defaultValues.put(P_EXPORT_OPTIMIZE_RANGE_REVIEW, Boolean.toString(DEF_EXPORT_OPTIMIZE_RANGE_REVIEW));
		defaultValues.put(P_EXPORT_DELTA_LEFT_MILLISECONDS_REVIEW, Integer.toString(DEF_EXPORT_DELTA_LEFT_MILLISECONDS_REVIEW));
		defaultValues.put(P_EXPORT_DELTA_RIGHT_MILLISECONDS_REVIEW, Integer.toString(DEF_EXPORT_DELTA_RIGHT_MILLISECONDS_REVIEW));
		//
		defaultValues.put(P_EXPORT_NUMBER_TRACES_ASSIGNER, Integer.toString(DEF_EXPORT_NUMBER_TRACES_ASSIGNER));
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
		defaultValues.put(P_TRANSFER_MATCH_QUALITY, Float.toString(DEF_TRANSFER_MATCH_QUALITY));
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
		defaultValues.put(P_DETECTOR_DYNAMIC_OFFSET_MILLISECONDS, Integer.toString(DEF_DETECTOR_DYNAMIC_OFFSET_MILLISECONDS));
		defaultValues.put(P_DETECTOR_REPLACE_NEAREST_PEAK, Boolean.toString(DEF_DETECTOR_REPLACE_NEAREST_PEAK));
		defaultValues.put(P_DETECTOR_REPLACE_PEAK_DELTA_MILLISECONDS, Integer.toString(DEF_DETECTOR_REPLACE_PEAK_DELTA_MILLISECONDS));
		defaultValues.put(P_DETECTOR_HIDE_EXISTING_PEAKS, Boolean.toString(DEF_DETECTOR_HIDE_EXISTING_PEAKS));
		defaultValues.put(P_DETECTOR_VISIBILITY, DEF_DETECTOR_VISIBILITY);
		defaultValues.put(P_DETECTOR_FOCUS_XIC, Boolean.toString(DEF_DETECTOR_FOCUS_XIC));
		defaultValues.put(P_DETECTOR_SHOW_BASELINE, Boolean.toString(DEF_DETECTOR_SHOW_BASELINE));
		defaultValues.put(P_DETECTOR_SHOW_ONLY_RELEVANT_PEAKS, Boolean.toString(DEF_DETECTOR_SHOW_ONLY_RELEVANT_PEAKS));
		/*
		 * Review
		 */
		defaultValues.put(P_REVIEW_DELTA_LEFT_MILLISECONDS, Integer.toString(DEF_REVIEW_DELTA_LEFT_MILLISECONDS));
		defaultValues.put(P_REVIEW_DELTA_RIGHT_MILLISECONDS, Integer.toString(DEF_REVIEW_DELTA_RIGHT_MILLISECONDS));
		defaultValues.put(P_REVIEW_DYNAMIC_OFFSET_MILLISECONDS, Integer.toString(DEF_REVIEW_DYNAMIC_OFFSET_MILLISECONDS));
		defaultValues.put(P_REVIEW_REPLACE_NEAREST_PEAK, Boolean.toString(DEF_REVIEW_REPLACE_NEAREST_PEAK));
		defaultValues.put(P_REVIEW_REPLACE_PEAK_DELTA_MILLISECONDS, Integer.toString(DEF_REVIEW_REPLACE_PEAK_DELTA_MILLISECONDS));
		defaultValues.put(P_REVIEW_SET_TARGET_VERIFICATION, Boolean.toString(DEF_REVIEW_SET_TARGET_VERIFICATION));
		defaultValues.put(P_REVIEW_AUTO_SELECT_BEST_MATCH, Boolean.toString(DEF_REVIEW_AUTO_SELECT_BEST_MATCH));
		defaultValues.put(P_REVIEW_HIDE_EXISTING_PEAKS, Boolean.toString(DEF_REVIEW_HIDE_EXISTING_PEAKS));
		defaultValues.put(P_REVIEW_SET_TARGET_DETECTED_PEAK, Boolean.toString(DEF_REVIEW_SET_TARGET_DETECTED_PEAK));
		defaultValues.put(P_REVIEW_VISIBILITY, DEF_REVIEW_VISIBILITY);
		defaultValues.put(P_REVIEW_FOCUS_XIC, Boolean.toString(DEF_REVIEW_FOCUS_XIC));
		defaultValues.put(P_REVIEW_SHOW_BASELINE, Boolean.toString(DEF_REVIEW_SHOW_BASELINE));
		defaultValues.put(P_REVIEW_SHOW_DETAILS, Boolean.toString(DEF_REVIEW_SHOW_DETAILS));
		defaultValues.put(P_REVIEW_UPDATE_SEARCH_TARGET, Boolean.toString(DEF_REVIEW_UPDATE_SEARCH_TARGET));
		defaultValues.put(P_REVIEW_FETCH_LIBRARY_SPECTRUM, Boolean.toString(DEF_REVIEW_FETCH_LIBRARY_SPECTRUM));
		defaultValues.put(P_REVIEW_SHOW_ONLY_RELEVANT_PEAKS, Boolean.toString(DEF_REVIEW_SHOW_ONLY_RELEVANT_PEAKS));
		defaultValues.put(P_REVIEW_IGNORE_NULL_CAS_NUMBER, Boolean.toString(DEF_REVIEW_IGNORE_NULL_CAS_NUMBER));
		/*
		 * Named Traces
		 */
		defaultValues.put(P_PEAK_EXPORT_NUMBER_TRACES, Integer.toString(DEF_PEAK_EXPORT_NUMBER_TRACES));
		//
		return defaultValues;
	}

	@Override
	public IEclipsePreferences getPreferences() {

		return getScopeContext().getNode(getPreferenceNode());
	}

	public static String getSettings(String key, String def) {

		return INSTANCE().get(key, def);
	}

	public static FilterSettingsRetentionIndexMapper getFilterSettings() {

		return new FilterSettingsRetentionIndexMapper();
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

	public static int getOffsetMinY() {

		return INSTANCE().getInteger(P_OFFSET_MIN_Y, DEF_OFFSET_MIN_Y);
	}

	public static int getOffsetMaxY() {

		return INSTANCE().getInteger(P_OFFSET_MAX_Y, DEF_OFFSET_MAX_Y);
	}

	public static boolean isSortImportTemplate() {

		return INSTANCE().getBoolean(P_SORT_IMPORT_TEMPLATE, DEF_SORT_IMPORT_TEMPLATE);
	}

	public static boolean isSortExportTemplate() {

		return INSTANCE().getBoolean(P_SORT_EXPORT_TEMPLATE, DEF_SORT_EXPORT_TEMPLATE);
	}

	public static boolean isDetectorSettingsSort() {

		return INSTANCE().getBoolean(P_DETECTOR_SETTINGS_SORT, DEF_DETECTOR_SETTINGS_SORT);
	}

	public static boolean isReviewSettingsSort() {

		return INSTANCE().getBoolean(P_REVIEW_SETTINGS_SORT, DEF_REVIEW_SETTINGS_SORT);
	}

	public static boolean isReportSettingsSort() {

		return INSTANCE().getBoolean(P_REPORT_SETTINGS_SORT, DEF_REPORT_SETTINGS_SORT);
	}

	public static boolean isExportOptimizeRangeDetector() {

		return INSTANCE().getBoolean(P_EXPORT_OPTIMIZE_RANGE_DETECTOR, DEF_EXPORT_OPTIMIZE_RANGE_DETECTOR);
	}

	public static void setExportOptimizeRangeDetector(boolean optimizeRange) {

		INSTANCE().putBoolean(P_EXPORT_OPTIMIZE_RANGE_DETECTOR, optimizeRange);
	}

	public static double getExportDeltaLeftPositionDetector() {

		return INSTANCE().getDouble(P_EXPORT_DELTA_LEFT_POSITION_DETECTOR, DEF_EXPORT_DELTA_LEFT_POSITION_DETECTOR);
	}

	public static void setExportDeltaLeftPositionDetector(double delta) {

		INSTANCE().putDouble(P_EXPORT_DELTA_LEFT_POSITION_DETECTOR, delta);
	}

	public static double getExportDeltaRightPositionDetector() {

		return INSTANCE().getDouble(P_EXPORT_DELTA_RIGHT_POSITION_DETECTOR, DEF_EXPORT_DELTA_RIGHT_POSITION_DETECTOR);
	}

	public static void setExportDeltaRightPositionDetector(double delta) {

		INSTANCE().putDouble(P_EXPORT_DELTA_RIGHT_POSITION_DETECTOR, delta);
	}

	public static PositionDirective getExportPositionDirectiveDetector() {

		return getPositionDirective(P_EXPORT_POSITION_DIRECTIVE_DETECTOR, DEF_EXPORT_POSITION_DIRECTIVE_DETECTOR);
	}

	public static void setExportPositionDirectiveDetector(PositionDirective positionDirective) {

		putPositionDirective(P_EXPORT_POSITION_DIRECTIVE_DETECTOR, positionDirective);
	}

	public static int getExportDeltaLeftMillisecondsIdentifier() {

		return INSTANCE().getInteger(P_EXPORT_DELTA_LEFT_MILLISECONDS_IDENTIFIER, DEF_EXPORT_DELTA_LEFT_MILLISECONDS_IDENTIFIER);
	}

	public static void setExportDeltaLeftMillisecondsIdentifier(int deltaMilliseconds) {

		INSTANCE().putInteger(P_EXPORT_DELTA_LEFT_MILLISECONDS_IDENTIFIER, deltaMilliseconds);
	}

	public static int getExportDeltaRightMillisecondsIdentifier() {

		return INSTANCE().getInteger(P_EXPORT_DELTA_RIGHT_MILLISECONDS_IDENTIFIER, DEF_EXPORT_DELTA_RIGHT_MILLISECONDS_IDENTIFIER);
	}

	public static void setExportDeltaRightMillisecondsIdentifier(int deltaMilliseconds) {

		INSTANCE().putInteger(P_EXPORT_DELTA_RIGHT_MILLISECONDS_IDENTIFIER, deltaMilliseconds);
	}

	public static float getLimitMatchFactorIdentifier() {

		return INSTANCE().getFloat(P_LIMIT_MATCH_FACTOR_IDENTIFIER, DEF_LIMIT_MATCH_FACTOR_IDENTIFIER);
	}

	public static float getMatchQualityIdentifier() {

		return INSTANCE().getFloat(P_MATCH_QUALITY_IDENTIFIER, DEF_MATCH_QUALITY_IDENTIFIER);
	}

	public static int getExportDeltaLeftMillisecondsReview() {

		return INSTANCE().getInteger(P_EXPORT_DELTA_LEFT_MILLISECONDS_REVIEW, DEF_EXPORT_DELTA_LEFT_MILLISECONDS_REVIEW);
	}

	public static void setExportDeltaLeftMillisecondsReview(int deltaMilliseconds) {

		INSTANCE().putInteger(P_EXPORT_DELTA_LEFT_MILLISECONDS_REVIEW, deltaMilliseconds);
	}

	public static int getExportDeltaRightMillisecondsReview() {

		return INSTANCE().getInteger(P_EXPORT_DELTA_RIGHT_MILLISECONDS_REVIEW, DEF_EXPORT_DELTA_RIGHT_MILLISECONDS_REVIEW);
	}

	public static void setExportDeltaRightMillisecondsReview(int deltaMilliseconds) {

		INSTANCE().putInteger(P_EXPORT_DELTA_RIGHT_MILLISECONDS_REVIEW, deltaMilliseconds);
	}

	public static int getExportNumberTracesAssigner() {

		return INSTANCE().getInteger(P_EXPORT_NUMBER_TRACES_ASSIGNER, DEF_EXPORT_NUMBER_TRACES_ASSIGNER);
	}

	public static void setExportNumberTracesAssigner(int numberTraces) {

		INSTANCE().putInteger(P_EXPORT_NUMBER_TRACES_ASSIGNER, numberTraces);
	}

	public static int getExportDeltaLeftMillisecondsAssigner() {

		return INSTANCE().getInteger(P_EXPORT_DELTA_LEFT_MILLISECONDS_ASSIGNER, DEF_EXPORT_DELTA_LEFT_MILLISECONDS_ASSIGNER);
	}

	public static int getExportDeltaRightMillisecondsAssigner() {

		return INSTANCE().getInteger(P_EXPORT_DELTA_RIGHT_MILLISECONDS_ASSIGNER, DEF_EXPORT_DELTA_RIGHT_MILLISECONDS_ASSIGNER);
	}

	public static int getExportDeltaLeftMillisecondsStandards() {

		return INSTANCE().getInteger(P_EXPORT_DELTA_LEFT_MILLISECONDS_STANDARDS, DEF_EXPORT_DELTA_LEFT_MILLISECONDS_STANDARDS);
	}

	public static int getExportDeltaRightMillisecondsStandards() {

		return INSTANCE().getInteger(P_EXPORT_DELTA_RIGHT_MILLISECONDS_STANDARDS, DEF_EXPORT_DELTA_RIGHT_MILLISECONDS_STANDARDS);
	}

	public static int getExportDeltaLeftMillisecondsReport() {

		return INSTANCE().getInteger(P_EXPORT_DELTA_LEFT_MILLISECONDS_REPORT, DEF_EXPORT_DELTA_LEFT_MILLISECONDS_REPORT);
	}

	public static int getExportDeltaRightMillisecondsReport() {

		return INSTANCE().getInteger(P_EXPORT_DELTA_RIGHT_MILLISECONDS_REPORT, DEF_EXPORT_DELTA_RIGHT_MILLISECONDS_REPORT);
	}

	public static int getExportNumberTracesDetector() {

		return INSTANCE().getInteger(P_EXPORT_NUMBER_TRACES_DETECTOR, DEF_EXPORT_NUMBER_TRACES_DETECTOR);
	}

	public static void setExportNumberTracesDetector(int numberTraces) {

		INSTANCE().putInteger(P_EXPORT_NUMBER_TRACES_DETECTOR, numberTraces);
	}

	public static int getExportNumberTracesIdentifier() {

		return INSTANCE().getInteger(P_EXPORT_NUMBER_TRACES_IDENTIFIER, DEF_EXPORT_NUMBER_TRACES_IDENTIFIER);
	}

	public static void setExportNumberTracesIdentifier(int numberTraces) {

		INSTANCE().putInteger(P_EXPORT_NUMBER_TRACES_IDENTIFIER, numberTraces);
	}

	public static int getExportNumberTracesReview() {

		return INSTANCE().getInteger(P_EXPORT_NUMBER_TRACES_REVIEW, DEF_EXPORT_NUMBER_TRACES_REVIEW);
	}

	public static void setExportNumberTracesReview(int numberTraces) {

		INSTANCE().putInteger(P_EXPORT_NUMBER_TRACES_REVIEW, numberTraces);
	}

	public static boolean isExportOptimizeRangeReview() {

		return INSTANCE().getBoolean(P_EXPORT_OPTIMIZE_RANGE_REVIEW, DEF_EXPORT_OPTIMIZE_RANGE_REVIEW);
	}

	public static void setExportOptimizeRangeReview(boolean optimizeRange) {

		INSTANCE().putBoolean(P_EXPORT_OPTIMIZE_RANGE_REVIEW, optimizeRange);
	}

	public static int getDetectorDeltaLeftMilliseconds() {

		return INSTANCE().getInteger(P_DETECTOR_DELTA_LEFT_MILLISECONDS, DEF_DETECTOR_DELTA_LEFT_MILLISECONDS);
	}

	public static void setDetectorDeltaLeftMilliseconds(int milliseconds) {

		INSTANCE().putInteger(P_DETECTOR_DELTA_LEFT_MILLISECONDS, milliseconds);
	}

	public static int getDetectorDeltaRightMilliseconds() {

		return INSTANCE().getInteger(P_DETECTOR_DELTA_RIGHT_MILLISECONDS, DEF_DETECTOR_DELTA_RIGHT_MILLISECONDS);
	}

	public static void setDetectorDeltaRightMilliseconds(int milliseconds) {

		INSTANCE().putInteger(P_DETECTOR_DELTA_RIGHT_MILLISECONDS, milliseconds);
	}

	public static int getDetectorDynamicOffsetMilliseconds() {

		return INSTANCE().getInteger(P_DETECTOR_DYNAMIC_OFFSET_MILLISECONDS, DEF_DETECTOR_DYNAMIC_OFFSET_MILLISECONDS);
	}

	public static boolean isDetectorReplaceNearestPeak() {

		return INSTANCE().getBoolean(P_DETECTOR_REPLACE_NEAREST_PEAK, DEF_DETECTOR_REPLACE_NEAREST_PEAK);
	}

	public static void toggleDetectorReplaceNearestPeak() {

		boolean replacePeak = isDetectorReplaceNearestPeak();
		INSTANCE().putBoolean(P_DETECTOR_REPLACE_NEAREST_PEAK, !replacePeak);
	}

	public static int getDetectorReplacePeakDeltaMilliseconds() {

		return INSTANCE().getInteger(P_DETECTOR_REPLACE_PEAK_DELTA_MILLISECONDS, DEF_DETECTOR_REPLACE_PEAK_DELTA_MILLISECONDS);
	}

	public static Visibility getDetectorVisibility() {

		try {
			return Visibility.valueOf(INSTANCE().get(P_DETECTOR_VISIBILITY, DEF_DETECTOR_VISIBILITY));
		} catch(Exception e) {
			return Visibility.BOTH;
		}
	}

	public static void setDetectorVisibility(Visibility visibility) {

		INSTANCE().put(P_DETECTOR_VISIBILITY, visibility.name());
	}

	public static boolean isDetectorFocusXIC() {

		return INSTANCE().getBoolean(P_DETECTOR_FOCUS_XIC, DEF_DETECTOR_FOCUS_XIC);
	}

	public static boolean isDetectorHideExistingPeaks() {

		return INSTANCE().getBoolean(P_DETECTOR_HIDE_EXISTING_PEAKS, DEF_DETECTOR_HIDE_EXISTING_PEAKS);
	}

	public static boolean isShowBaselineDetector() {

		return INSTANCE().getBoolean(P_DETECTOR_SHOW_BASELINE, DEF_DETECTOR_SHOW_BASELINE);
	}

	public static void toggleShowBaselineDetector() {

		boolean show = isShowBaselineDetector();
		INSTANCE().putBoolean(P_DETECTOR_SHOW_BASELINE, !show);
	}

	public static boolean isDetectorShowOnlyRelevantPeaks() {

		return INSTANCE().getBoolean(P_DETECTOR_SHOW_ONLY_RELEVANT_PEAKS, DEF_DETECTOR_SHOW_ONLY_RELEVANT_PEAKS);
	}

	public static int getReviewDeltaLeftMilliseconds() {

		return INSTANCE().getInteger(P_REVIEW_DELTA_LEFT_MILLISECONDS, DEF_REVIEW_DELTA_LEFT_MILLISECONDS);
	}

	public static void setReviewDeltaLeftMilliseconds(int milliseconds) {

		INSTANCE().putInteger(P_REVIEW_DELTA_LEFT_MILLISECONDS, milliseconds);
	}

	public static int getReviewDeltaRightMilliseconds() {

		return INSTANCE().getInteger(P_REVIEW_DELTA_RIGHT_MILLISECONDS, DEF_REVIEW_DELTA_RIGHT_MILLISECONDS);
	}

	public static void setReviewDeltaRightMilliseconds(int milliseconds) {

		INSTANCE().putInteger(P_REVIEW_DELTA_RIGHT_MILLISECONDS, milliseconds);
	}

	public static int getReviewDynamicOffsetMilliseconds() {

		return INSTANCE().getInteger(P_REVIEW_DYNAMIC_OFFSET_MILLISECONDS, DEF_REVIEW_DYNAMIC_OFFSET_MILLISECONDS);
	}

	public static boolean isReviewReplaceNearestPeak() {

		return INSTANCE().getBoolean(P_REVIEW_REPLACE_NEAREST_PEAK, DEF_REVIEW_REPLACE_NEAREST_PEAK);
	}

	public static void toggleReviewReplaceNearestPeak() {

		boolean replacePeak = isReviewReplaceNearestPeak();
		INSTANCE().putBoolean(P_REVIEW_REPLACE_NEAREST_PEAK, !replacePeak);
	}

	public static int getReviewReplacePeakDeltaMilliseconds() {

		return INSTANCE().getInteger(P_REVIEW_REPLACE_PEAK_DELTA_MILLISECONDS, DEF_REVIEW_REPLACE_PEAK_DELTA_MILLISECONDS);
	}

	public static boolean isReviewSetTargetVerification() {

		return INSTANCE().getBoolean(P_REVIEW_SET_TARGET_VERIFICATION, DEF_REVIEW_SET_TARGET_VERIFICATION);
	}

	public static boolean isReviewAutoSelectBestMatch() {

		return INSTANCE().getBoolean(P_REVIEW_AUTO_SELECT_BEST_MATCH, DEF_REVIEW_AUTO_SELECT_BEST_MATCH);
	}

	public static boolean isReviewHideExistingPeaks() {

		return INSTANCE().getBoolean(P_REVIEW_HIDE_EXISTING_PEAKS, DEF_REVIEW_HIDE_EXISTING_PEAKS);
	}

	public static boolean isReviewSetTargetDetectedPeak() {

		return INSTANCE().getBoolean(P_REVIEW_SET_TARGET_DETECTED_PEAK, DEF_REVIEW_SET_TARGET_DETECTED_PEAK);
	}

	public static Visibility getReviewVisibility() {

		try {
			return Visibility.valueOf(INSTANCE().get(P_REVIEW_VISIBILITY, DEF_REVIEW_VISIBILITY));
		} catch(Exception e) {
			return Visibility.BOTH;
		}
	}

	public static void setReviewVisibility(Visibility visibility) {

		INSTANCE().put(P_REVIEW_VISIBILITY, visibility.name());
	}

	public static boolean isReviewFocusXIC() {

		return INSTANCE().getBoolean(P_REVIEW_FOCUS_XIC, DEF_REVIEW_FOCUS_XIC);
	}

	public static boolean isShowBaselineReview() {

		return INSTANCE().getBoolean(P_REVIEW_SHOW_BASELINE, DEF_REVIEW_SHOW_BASELINE);
	}

	public static void toggleShowBaselineReview() {

		boolean show = isShowBaselineReview();
		INSTANCE().putBoolean(P_REVIEW_SHOW_BASELINE, !show);
	}

	public static boolean isShowReviewDetails() {

		return INSTANCE().getBoolean(P_REVIEW_SHOW_DETAILS, DEF_REVIEW_SHOW_DETAILS);
	}

	public static void toggleShowReviewDetails() {

		boolean show = isShowReviewDetails();
		INSTANCE().putBoolean(P_REVIEW_SHOW_DETAILS, !show);
	}

	public static boolean isReviewUpdateSearchTarget() {

		return INSTANCE().getBoolean(P_REVIEW_UPDATE_SEARCH_TARGET, DEF_REVIEW_UPDATE_SEARCH_TARGET);
	}

	public static boolean isReviewFetchLibrarySpectrum() {

		return INSTANCE().getBoolean(P_REVIEW_FETCH_LIBRARY_SPECTRUM, DEF_REVIEW_FETCH_LIBRARY_SPECTRUM);
	}

	public static boolean isReviewShowOnlyRelevantPeaks() {

		return INSTANCE().getBoolean(P_REVIEW_SHOW_ONLY_RELEVANT_PEAKS, DEF_REVIEW_SHOW_ONLY_RELEVANT_PEAKS);
	}

	public static boolean isReviewIgnoreNullCasNumber() {

		return INSTANCE().getBoolean(P_REVIEW_IGNORE_NULL_CAS_NUMBER, DEF_REVIEW_IGNORE_NULL_CAS_NUMBER);
	}

	public static PeakDetectorSettings getPeakDetectorSettingsCSD() {

		PeakDetectorSettings settings = new PeakDetectorSettings();
		settings.setDetectorSettings(INSTANCE().get(P_PEAK_DETECTOR_LIST_CSD, DEF_PEAK_DETECTOR_LIST_CSD));
		return settings;
	}

	public static PeakDetectorSettings getPeakDetectorSettingsMSD() {

		PeakDetectorSettings settings = new PeakDetectorSettings();
		settings.setDetectorSettings(INSTANCE().get(P_PEAK_DETECTOR_LIST_MSD, DEF_PEAK_DETECTOR_LIST_MSD));
		return settings;
	}

	public static PeakDetectorSettings getPeakDetectorSettingsWSD() {

		PeakDetectorSettings settings = new PeakDetectorSettings();
		settings.setDetectorSettings(INSTANCE().get(P_PEAK_DETECTOR_LIST_WSD, DEF_PEAK_DETECTOR_LIST_WSD));
		return settings;
	}

	public static PeakDetectorDirectSettings getPeakDetectorSettingsDirectMSD() {

		PeakDetectorDirectSettings settings = new PeakDetectorDirectSettings();
		settings.setTraces("");
		return settings;
	}

	public static PeakDetectorDirectSettings getPeakDetectorSettingsDirectWSD() {

		PeakDetectorDirectSettings settings = new PeakDetectorDirectSettings();
		settings.setTraces("");
		return settings;
	}

	public static ChromatogramReportSettings getChromatogramReportSettings() {

		ChromatogramReportSettings settings = new ChromatogramReportSettings();
		settings.setReportReferencedChromatograms(INSTANCE().getBoolean(P_REPORT_REFERENCED_CHROMATOGRAMS, DEF_REPORT_REFERENCED_CHROMATOGRAMS));
		settings.getReportSettings().load(INSTANCE().get(P_CHROMATOGRAM_REPORT_LIST, DEF_CHROMATOGRAM_REPORT_LIST));
		return settings;
	}

	public static PeakReviewSettings getReviewSettingsMSD() {

		PeakReviewSettings settings = new PeakReviewSettings();
		settings.setReviewSettings(INSTANCE().get(P_CHROMATOGRAM_REVIEW_LIST_MSD, DEF_CHROMATOGRAM_REVIEW_LIST_MSD));
		return settings;
	}

	public static PeakReviewSettings getReviewSettingsCSD() {

		PeakReviewSettings settings = new PeakReviewSettings();
		settings.setReviewSettings(INSTANCE().get(P_CHROMATOGRAM_REVIEW_LIST_CSD, DEF_CHROMATOGRAM_REVIEW_LIST_CSD));
		return settings;
	}

	public static String getStandardsExtractorConcentrationUnit() {

		return INSTANCE().get(P_STANDARDS_EXTRACTOR_CONCENTRATION_UNIT, DEF_STANDARDS_EXTRACTOR_CONCENTRATION_UNIT);
	}

	public static boolean isTransferUseIdentifiedPeaksOnly() {

		return INSTANCE().getBoolean(P_TRANSFER_USE_IDENTIFIED_PEAKS_ONLY, DEF_TRANSFER_USE_IDENTIFIED_PEAKS_ONLY);
	}

	public static boolean isTransferUseBestTargetOnly() {

		return INSTANCE().getBoolean(P_TRANSFER_USE_BEST_TARGET_ONLY, DEF_TRANSFER_USE_BEST_TARGET_ONLY);
	}

	public static float getMatchQualityTransfer() {

		return INSTANCE().getFloat(P_TRANSFER_MATCH_QUALITY, DEF_TRANSFER_MATCH_QUALITY);
	}

	public static int getTransferRetentionTimeMillisecondsLeft() {

		return INSTANCE().getInteger(P_TRANSFER_RETENTION_TIME_MILLISECONDS_LEFT, DEF_TRANSFER_RETENTION_TIME_MILLISECONDS_LEFT);
	}

	public static int getTransferRetentionTimeMillisecondsRight() {

		return INSTANCE().getInteger(P_TRANSFER_RETENTION_TIME_MILLISECONDS_RIGHT, DEF_TRANSFER_RETENTION_TIME_MILLISECONDS_RIGHT);
	}

	public static int getTransferOffsetRetentionTimePeakMaximum() {

		return INSTANCE().getInteger(P_TRANSFER_OFFSET_RETENTION_TIME_MILLISECONDS_PEAK_MAXIMUM, DEF_TRANSFER_OFFSET_RETENTION_TIME_MILLISECONDS_PEAK_MAXIMUM);
	}

	public static boolean isTransferAdjustPeakHeight() {

		return INSTANCE().getBoolean(P_TRANSFER_ADJUST_PEAK_HEIGHT, DEF_TRANSFER_ADJUST_PEAK_HEIGHT);
	}

	public static boolean isTransferCreateModelPeak() {

		return INSTANCE().getBoolean(P_TRANSFER_CREATE_MODEL_PEAK, DEF_TRANSFER_CREATE_MODEL_PEAK);
	}

	public static double getTransferPeakOverlapCoverage() {

		return INSTANCE().getDouble(P_TRANSFER_PEAK_OVERLAP_COVERAGE, DEF_TRANSFER_PEAK_OVERLAP_COVERAGE);
	}

	public static boolean isTransferOptimizeRange() {

		return INSTANCE().getBoolean(P_TRANSFER_OPTIMIZE_RANGE, DEF_TRANSFER_OPTIMIZE_RANGE);
	}

	public static boolean isTransferCheckPurity() {

		return INSTANCE().getBoolean(P_TRANSFER_CHECK_PURITY, DEF_TRANSFER_CHECK_PURITY);
	}

	public static int getTransferNumberTraces() {

		return INSTANCE().getInteger(P_TRANSFER_NUMBER_TRACES, DEF_TRANSFER_NUMBER_TRACES);
	}

	public static int getPeakExportNumberTraces() {

		return INSTANCE().getInteger(P_PEAK_EXPORT_NUMBER_TRACES, DEF_PEAK_EXPORT_NUMBER_TRACES);
	}

	private static PositionDirective getPositionDirective(String key, String def) {

		try {
			return PositionDirective.valueOf(INSTANCE().get(key, def));
		} catch(Exception e) {
			return PositionDirective.RETENTION_TIME_MIN;
		}
	}

	private static void putPositionDirective(String key, PositionDirective positionDirective) {

		INSTANCE().put(key, positionDirective.name());
	}
}
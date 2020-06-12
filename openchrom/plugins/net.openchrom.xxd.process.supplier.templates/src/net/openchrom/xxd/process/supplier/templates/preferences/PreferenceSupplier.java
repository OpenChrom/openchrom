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
	public static final double MIN_DELTA_MINUTES = 0.0d; // 0 Minutes
	public static final double MAX_DELTA_MINUTES = 1.0d; // 1 Minute
	public static final int MIN_DELTA_MILLISECONDS = 0; // 0 Minutes
	public static final int MAX_DELTA_MILLISECONDS = 60000; // 1 Minute
	public static final int MIN_NUMBER_TRACES = 0; // 0 = TIC
	public static final int MAX_NUMBER_TRACES = Integer.MAX_VALUE;
	public static final int MIN_OFFSET_Y = 0; // %
	public static final int MAX_OFFSET_Y = 100; // %
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
	/*
	 * Detector
	 */
	public static final String P_EXPORT_NUMBER_TRACES_DETECTOR = "exportNumberTracesDetector";
	public static final int DEF_EXPORT_NUMBER_TRACES_DETECTOR = 10;
	public static final String P_EXPORT_OPTIMIZE_RANGE_DETECTOR = "exportOptimizeRangeDetector";
	public static final boolean DEF_EXPORT_OPTIMIZE_RANGE_DETECTOR = true;
	public static final String P_EXPORT_DELTA_LEFT_MINUTES_DETECTOR = "exportDeltaLeftMinutesDetector";
	public static final double DEF_EXPORT_DELTA_LEFT_MINUTES_DETECTOR = 0.1d;
	public static final String P_EXPORT_DELTA_RIGHT_MINUTES_DETECTOR = "exportDeltaRightMinutesDetector";
	public static final double DEF_EXPORT_DELTA_RIGHT_MINUTES_DETECTOR = 0.1d;
	/*
	 * Identifier
	 */
	public static final String P_EXPORT_NUMBER_TRACES_IDENTIFIER = "exportNumberTracesIdentifier";
	public static final int DEF_EXPORT_NUMBER_TRACES_IDENTIFIER = 2;
	public static final String P_EXPORT_DELTA_LEFT_MINUTES_IDENTIFIER = "exportDeltaLeftMinutesIdentifier";
	public static final double DEF_EXPORT_DELTA_LEFT_MINUTES_IDENTIFIER = 0.1d;
	public static final String P_EXPORT_DELTA_RIGHT_MINUTES_IDENTIFIER = "exportDeltaRightMinutesIdentifier";
	public static final double DEF_EXPORT_DELTA_RIGHT_MINUTES_IDENTIFIER = 0.1d;
	/*
	 * Review
	 */
	public static final String P_EXPORT_NUMBER_TRACES_REVIEW = "exportNumberTracesReview";
	public static final int DEF_EXPORT_NUMBER_TRACES_REVIEW = 5;
	public static final String P_EXPORT_DELTA_LEFT_MINUTES_REVIEW = "exportDeltaLeftMinutesReview";
	public static final double DEF_EXPORT_DELTA_LEFT_MINUTES_REVIEW = 0.1d;
	public static final String P_EXPORT_DELTA_RIGHT_MINUTES_REVIEW = "exportDeltaRightMinutesReview";
	public static final double DEF_EXPORT_DELTA_RIGHT_MINUTES_REVIEW = 0.1d;
	/*
	 * Assigner
	 */
	public static final String P_EXPORT_DELTA_LEFT_MINUTES_ASSIGNER = "exportDeltaLeftMinutesAssigner";
	public static final double DEF_EXPORT_DELTA_LEFT_MINUTES_ASSIGNER = 0.1d;
	public static final String P_EXPORT_DELTA_RIGHT_MINUTES_ASSIGNER = "exportDeltaRightMinutesAssigner";
	public static final double DEF_EXPORT_DELTA_RIGHT_MINUTES_ASSIGNER = 0.1d;
	/*
	 * Standards
	 */
	public static final String P_EXPORT_DELTA_LEFT_MINUTES_STANDARDS = "exportDeltaLeftMinutesStandards";
	public static final double DEF_EXPORT_DELTA_LEFT_MINUTES_STANDARDS = 0.1d;
	public static final String P_EXPORT_DELTA_RIGHT_MINUTES_STANDARDS = "exportDeltaRightMinutesStandards";
	public static final double DEF_EXPORT_DELTA_RIGHT_MINUTES_STANDARDS = 0.1d;
	/*
	 * Report
	 */
	public static final String P_EXPORT_DELTA_LEFT_MINUTES_REPORT = "exportDeltaLeftMinutesReport";
	public static final double DEF_EXPORT_DELTA_LEFT_MINUTES_REPORT = 0.1d;
	public static final String P_EXPORT_DELTA_RIGHT_MINUTES_REPORT = "exportDeltaRightMinutesReport";
	public static final double DEF_EXPORT_DELTA_RIGHT_MINUTES_REPORT = 0.1d;
	/*
	 * 
	 */
	public static final String P_STANDARDS_EXTRACTOR_CONCENTRATION_UNIT = "standardsExtractorConcentrationUnit";
	public static final String DEF_STANDARDS_EXTRACTOR_CONCENTRATION_UNIT = "ppm";
	//
	public static final String P_TRANSFER_USE_BEST_TARGET_ONLY = "transferUseBestTargetOnly";
	public static final boolean DEF_TRANSFER_USE_BEST_TARGET_ONLY = true;
	public static final String P_TRANSFER_RETENTION_TIME_MINUTES_LEFT = "transferRetentionTimeMinutesLeft";
	public static final double DEF_TRANSFER_RETENTION_TIME_MINUTES_LEFT = 0.0d;
	public static final String P_TRANSFER_RETENTION_TIME_MINUTES_RIGHT = "transferRetentionTimeMinutesRight";
	public static final double DEF_TRANSFER_RETENTION_TIME_MINUTES_RIGHT = 0.0d;
	public static final String P_TRANSFER_NUMBER_TRACES = "transferNumberTraces";
	public static final int DEF_TRANSFER_NUMBER_TRACES = 15;
	public static final String P_TRANSFER_USE_IDENTIFIED_PEAKS_ONLY = "transferUseIdentifiedPeaksOnly";
	public static final boolean DEF_TRANSFER_USE_IDENTIFIED_PEAKS_ONLY = false;
	public static final String P_TRANSFER_USE_ADJUSTMENT_BY_PURITY = "transferUseAdjustmentByPurity";
	public static final boolean DEF_TRANSFER_USE_ADJUSTMENT_BY_PURITY = true;
	public static final String P_TRANSFER_OPTIMIZE_RANGE = "transferOptimizeRange";
	public static final boolean DEF_TRANSFER_OPTIMIZE_RANGE = true;
	//
	public static final String P_REPORT_REFERENCED_CHROMATOGRAMS = "reportReferencedChromatograms";
	public static final boolean DEF_REPORT_REFERENCED_CHROMATOGRAMS = false;
	/*
	 * Peak Detector
	 */
	public static final String P_DETECTOR_UI_DELTA_LEFT_MILLISECONDS = "detectorUIDeltaLeftMilliseconds";
	public static final int DEF_DETECTOR_UI_DELTA_LEFT_MILLISECONDS = 0;
	public static final String P_DETECTOR_UI_DELTA_RIGHT_MILLISECONDS = "detectorUIDeltaRightMilliseconds";
	public static final int DEF_DETECTOR_UI_DELTA_RIGHT_MILLISECONDS = 0;
	public static final String P_DETECTOR_UI_REPLACE_PEAK = "detectorUIReplacePeak";
	public static final boolean DEF_DETECTOR_UI_REPLACE_PEAK = true;
	public static final String P_DETECTOR_SHOW_CHROMATOGRAM_TIC = "detectorShowChromatogramTIC";
	public static final boolean DEF_DETECTOR_SHOW_CHROMATOGRAM_TIC = true;
	public static final String P_DETECTOR_SHOW_CHROMATOGRAM_XIC = "detectorShowChromatogramXIC";
	public static final boolean DEF_DETECTOR_SHOW_CHROMATOGRAM_XIC = true;
	public static final String P_DETECTOR_SHOW_BASELINE = "detectorShowBaseline";
	public static final boolean DEF_DETECTOR_SHOW_BASELINE = false;
	/*
	 * Peak Review
	 */
	public static final String P_REVIEW_UI_DELTA_LEFT_MILLISECONDS = "reviewUIDeltaLeftMilliseconds";
	public static final int DEF_REVIEW_UI_DELTA_LEFT_MILLISECONDS = 0;
	public static final String P_REVIEW_UI_DELTA_RIGHT_MILLISECONDS = "reviewUIDeltaRightMilliseconds";
	public static final int DEF_REVIEW_UI_DELTA_RIGHT_MILLISECONDS = 0;
	public static final String P_REVIEW_UI_REPLACE_PEAK = "reviewUIReplacePeak";
	public static final boolean DEF_REVIEW_UI_REPLACE_PEAK = true;
	public static final String P_SET_REVIEW_TARGET_NAME = "setReviewTargetName";
	public static final boolean DEF_SET_REVIEW_TARGET_NAME = false;
	public static final String P_AUTO_SELECT_BEST_PEAK_MATCH = "autoSelectBestPeakMatch";
	public static final boolean DEF_AUTO_SELECT_BEST_PEAK_MATCH = false;
	public static final String P_AUTO_LABEL_DETECTED_PEAK = "autoLabelDetectedPeak";
	public static final boolean DEF_AUTO_LABEL_DETECTED_PEAK = true;
	public static final String P_REVIEW_SHOW_CHROMATOGRAM_TIC = "reviewShowChromatogramTIC";
	public static final boolean DEF_REVIEW_SHOW_CHROMATOGRAM_TIC = true;
	public static final String P_REVIEW_SHOW_CHROMATOGRAM_XIC = "reviewShowChromatogramXIC";
	public static final boolean DEF_REVIEW_SHOW_CHROMATOGRAM_XIC = true;
	public static final String P_REVIEW_SHOW_BASELINE = "reviewShowBaseline";
	public static final boolean DEF_REVIEW_SHOW_BASELINE = false;
	public static final String P_REVIEW_SHOW_DETAILS = "reviewShowDetails";
	public static final boolean DEF_REVIEW_SHOW_DETAILS = false;
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
		defaultValues.put(P_EXPORT_NUMBER_TRACES_DETECTOR, Integer.toString(DEF_EXPORT_NUMBER_TRACES_DETECTOR));
		defaultValues.put(P_EXPORT_OPTIMIZE_RANGE_DETECTOR, Boolean.toString(DEF_EXPORT_OPTIMIZE_RANGE_DETECTOR));
		defaultValues.put(P_EXPORT_DELTA_LEFT_MINUTES_DETECTOR, Double.toString(DEF_EXPORT_DELTA_LEFT_MINUTES_DETECTOR));
		defaultValues.put(P_EXPORT_DELTA_RIGHT_MINUTES_DETECTOR, Double.toString(DEF_EXPORT_DELTA_RIGHT_MINUTES_DETECTOR));
		//
		defaultValues.put(P_EXPORT_NUMBER_TRACES_IDENTIFIER, Integer.toString(DEF_EXPORT_NUMBER_TRACES_IDENTIFIER));
		defaultValues.put(P_EXPORT_DELTA_LEFT_MINUTES_IDENTIFIER, Double.toString(DEF_EXPORT_DELTA_LEFT_MINUTES_IDENTIFIER));
		defaultValues.put(P_EXPORT_DELTA_RIGHT_MINUTES_IDENTIFIER, Double.toString(DEF_EXPORT_DELTA_RIGHT_MINUTES_IDENTIFIER));
		//
		defaultValues.put(P_EXPORT_NUMBER_TRACES_REVIEW, Integer.toString(DEF_EXPORT_NUMBER_TRACES_REVIEW));
		defaultValues.put(P_EXPORT_DELTA_LEFT_MINUTES_REVIEW, Double.toString(DEF_EXPORT_DELTA_LEFT_MINUTES_REVIEW));
		defaultValues.put(P_EXPORT_DELTA_RIGHT_MINUTES_REVIEW, Double.toString(DEF_EXPORT_DELTA_RIGHT_MINUTES_REVIEW));
		//
		defaultValues.put(P_EXPORT_DELTA_LEFT_MINUTES_ASSIGNER, Double.toString(DEF_EXPORT_DELTA_LEFT_MINUTES_ASSIGNER));
		defaultValues.put(P_EXPORT_DELTA_RIGHT_MINUTES_ASSIGNER, Double.toString(DEF_EXPORT_DELTA_RIGHT_MINUTES_ASSIGNER));
		//
		defaultValues.put(P_EXPORT_DELTA_LEFT_MINUTES_STANDARDS, Double.toString(DEF_EXPORT_DELTA_LEFT_MINUTES_STANDARDS));
		defaultValues.put(P_EXPORT_DELTA_RIGHT_MINUTES_STANDARDS, Double.toString(DEF_EXPORT_DELTA_RIGHT_MINUTES_STANDARDS));
		//
		defaultValues.put(P_EXPORT_DELTA_LEFT_MINUTES_REPORT, Double.toString(DEF_EXPORT_DELTA_LEFT_MINUTES_REPORT));
		defaultValues.put(P_EXPORT_DELTA_RIGHT_MINUTES_REPORT, Double.toString(DEF_EXPORT_DELTA_RIGHT_MINUTES_REPORT));
		//
		defaultValues.put(P_STANDARDS_EXTRACTOR_CONCENTRATION_UNIT, DEF_STANDARDS_EXTRACTOR_CONCENTRATION_UNIT);
		/*
		 * Transfer
		 */
		defaultValues.put(P_TRANSFER_USE_BEST_TARGET_ONLY, Boolean.toString(DEF_TRANSFER_USE_BEST_TARGET_ONLY));
		defaultValues.put(P_TRANSFER_RETENTION_TIME_MINUTES_LEFT, Double.toString(DEF_TRANSFER_RETENTION_TIME_MINUTES_LEFT));
		defaultValues.put(P_TRANSFER_RETENTION_TIME_MINUTES_RIGHT, Double.toString(DEF_TRANSFER_RETENTION_TIME_MINUTES_RIGHT));
		defaultValues.put(P_TRANSFER_NUMBER_TRACES, Integer.toString(DEF_TRANSFER_NUMBER_TRACES));
		defaultValues.put(P_TRANSFER_USE_IDENTIFIED_PEAKS_ONLY, Boolean.toString(DEF_TRANSFER_USE_IDENTIFIED_PEAKS_ONLY));
		defaultValues.put(P_TRANSFER_USE_ADJUSTMENT_BY_PURITY, Boolean.toString(DEF_TRANSFER_USE_ADJUSTMENT_BY_PURITY));
		defaultValues.put(P_TRANSFER_OPTIMIZE_RANGE, Boolean.toString(DEF_TRANSFER_OPTIMIZE_RANGE));
		//
		defaultValues.put(P_REPORT_REFERENCED_CHROMATOGRAMS, Boolean.toString(DEF_REPORT_REFERENCED_CHROMATOGRAMS));
		/*
		 * Detector
		 */
		defaultValues.put(P_DETECTOR_UI_DELTA_LEFT_MILLISECONDS, Integer.toString(DEF_DETECTOR_UI_DELTA_LEFT_MILLISECONDS));
		defaultValues.put(P_DETECTOR_UI_DELTA_RIGHT_MILLISECONDS, Integer.toString(DEF_DETECTOR_UI_DELTA_RIGHT_MILLISECONDS));
		defaultValues.put(P_DETECTOR_UI_REPLACE_PEAK, Boolean.toString(DEF_DETECTOR_UI_REPLACE_PEAK));
		defaultValues.put(P_DETECTOR_SHOW_CHROMATOGRAM_TIC, Boolean.toString(DEF_DETECTOR_SHOW_CHROMATOGRAM_TIC));
		defaultValues.put(P_DETECTOR_SHOW_CHROMATOGRAM_XIC, Boolean.toString(DEF_DETECTOR_SHOW_CHROMATOGRAM_XIC));
		defaultValues.put(P_DETECTOR_SHOW_BASELINE, Boolean.toString(DEF_DETECTOR_SHOW_BASELINE));
		/*
		 * Review
		 */
		defaultValues.put(P_REVIEW_UI_DELTA_LEFT_MILLISECONDS, Integer.toString(DEF_REVIEW_UI_DELTA_LEFT_MILLISECONDS));
		defaultValues.put(P_REVIEW_UI_DELTA_RIGHT_MILLISECONDS, Integer.toString(DEF_REVIEW_UI_DELTA_RIGHT_MILLISECONDS));
		defaultValues.put(P_REVIEW_UI_REPLACE_PEAK, Boolean.toString(DEF_REVIEW_UI_REPLACE_PEAK));
		defaultValues.put(P_SET_REVIEW_TARGET_NAME, Boolean.toString(DEF_SET_REVIEW_TARGET_NAME));
		defaultValues.put(P_AUTO_SELECT_BEST_PEAK_MATCH, Boolean.toString(DEF_AUTO_SELECT_BEST_PEAK_MATCH));
		defaultValues.put(P_AUTO_LABEL_DETECTED_PEAK, Boolean.toString(DEF_AUTO_LABEL_DETECTED_PEAK));
		defaultValues.put(P_REVIEW_SHOW_CHROMATOGRAM_TIC, Boolean.toString(DEF_REVIEW_SHOW_CHROMATOGRAM_TIC));
		defaultValues.put(P_REVIEW_SHOW_CHROMATOGRAM_XIC, Boolean.toString(DEF_REVIEW_SHOW_CHROMATOGRAM_XIC));
		defaultValues.put(P_REVIEW_SHOW_BASELINE, Boolean.toString(DEF_REVIEW_SHOW_BASELINE));
		defaultValues.put(P_REVIEW_SHOW_DETAILS, Boolean.toString(DEF_REVIEW_SHOW_DETAILS));
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

	public static boolean isExportOptimizeRangeDetector() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_EXPORT_OPTIMIZE_RANGE_DETECTOR, DEF_EXPORT_OPTIMIZE_RANGE_DETECTOR);
	}

	public static double getExportDeltaLeftMinutesDetector() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getDouble(P_EXPORT_DELTA_LEFT_MINUTES_DETECTOR, DEF_EXPORT_DELTA_LEFT_MINUTES_DETECTOR);
	}

	public static double getExportDeltaRightMinutesDetector() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getDouble(P_EXPORT_DELTA_RIGHT_MINUTES_DETECTOR, DEF_EXPORT_DELTA_RIGHT_MINUTES_DETECTOR);
	}

	public static double getExportDeltaLeftMinutesIdentifier() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getDouble(P_EXPORT_DELTA_LEFT_MINUTES_IDENTIFIER, DEF_EXPORT_DELTA_LEFT_MINUTES_IDENTIFIER);
	}

	public static double getExportDeltaRightMinutesIdentifier() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getDouble(P_EXPORT_DELTA_RIGHT_MINUTES_IDENTIFIER, DEF_EXPORT_DELTA_RIGHT_MINUTES_IDENTIFIER);
	}

	public static double getExportDeltaLeftMinutesReview() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getDouble(P_EXPORT_DELTA_LEFT_MINUTES_REVIEW, DEF_EXPORT_DELTA_LEFT_MINUTES_REVIEW);
	}

	public static double getExportDeltaRightMinutesReview() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getDouble(P_EXPORT_DELTA_RIGHT_MINUTES_REVIEW, DEF_EXPORT_DELTA_RIGHT_MINUTES_REVIEW);
	}

	public static double getExportDeltaLeftMinutesAssigner() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getDouble(P_EXPORT_DELTA_LEFT_MINUTES_ASSIGNER, DEF_EXPORT_DELTA_LEFT_MINUTES_ASSIGNER);
	}

	public static double getExportDeltaRightMinutesAssigner() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getDouble(P_EXPORT_DELTA_RIGHT_MINUTES_ASSIGNER, DEF_EXPORT_DELTA_RIGHT_MINUTES_ASSIGNER);
	}

	public static double getExportDeltaLeftMinutesStandards() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getDouble(P_EXPORT_DELTA_LEFT_MINUTES_STANDARDS, DEF_EXPORT_DELTA_LEFT_MINUTES_STANDARDS);
	}

	public static double getExportDeltaRightMinutesStandards() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getDouble(P_EXPORT_DELTA_RIGHT_MINUTES_STANDARDS, DEF_EXPORT_DELTA_RIGHT_MINUTES_STANDARDS);
	}

	public static double getExportDeltaLeftMinutesReport() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getDouble(P_EXPORT_DELTA_LEFT_MINUTES_REPORT, DEF_EXPORT_DELTA_LEFT_MINUTES_REPORT);
	}

	public static double getExportDeltaRightMinutesReport() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getDouble(P_EXPORT_DELTA_RIGHT_MINUTES_REPORT, DEF_EXPORT_DELTA_RIGHT_MINUTES_REPORT);
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
		return preferences.getInt(P_DETECTOR_UI_DELTA_LEFT_MILLISECONDS, DEF_DETECTOR_UI_DELTA_LEFT_MILLISECONDS);
	}

	public static int getDetectorDeltaRightMilliseconds() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getInt(P_DETECTOR_UI_DELTA_RIGHT_MILLISECONDS, DEF_DETECTOR_UI_DELTA_RIGHT_MILLISECONDS);
	}

	public static boolean isDetectorReplacePeak() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_DETECTOR_UI_REPLACE_PEAK, DEF_DETECTOR_UI_REPLACE_PEAK);
	}

	public static void toggleDetectorReplacePeak() {

		boolean replacePeak = isDetectorReplacePeak();
		putBoolean(P_DETECTOR_UI_REPLACE_PEAK, !replacePeak);
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

	public static boolean isShowBaselineDetector() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_DETECTOR_SHOW_BASELINE, DEF_DETECTOR_SHOW_BASELINE);
	}

	public static void toggleShowBaselineDetector() {

		boolean show = isShowBaselineDetector();
		putBoolean(P_DETECTOR_SHOW_BASELINE, !show);
	}

	public static int getReviewDeltaLeftMilliseconds() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getInt(P_REVIEW_UI_DELTA_LEFT_MILLISECONDS, DEF_REVIEW_UI_DELTA_LEFT_MILLISECONDS);
	}

	public static int getReviewDeltaRightMilliseconds() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getInt(P_REVIEW_UI_DELTA_RIGHT_MILLISECONDS, DEF_REVIEW_UI_DELTA_RIGHT_MILLISECONDS);
	}

	public static boolean isReviewReplacePeak() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_REVIEW_UI_REPLACE_PEAK, DEF_REVIEW_UI_REPLACE_PEAK);
	}

	public static void toggleReviewReplacePeak() {

		boolean replacePeak = isReviewReplacePeak();
		putBoolean(P_REVIEW_UI_REPLACE_PEAK, !replacePeak);
	}

	public static boolean isSetReviewTargetName() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_SET_REVIEW_TARGET_NAME, DEF_SET_REVIEW_TARGET_NAME);
	}

	public static boolean isAutoSelectBestPeakMatch() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_AUTO_SELECT_BEST_PEAK_MATCH, DEF_AUTO_SELECT_BEST_PEAK_MATCH);
	}

	public static boolean isAutoLabelDetectedPeak() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_AUTO_LABEL_DETECTED_PEAK, DEF_AUTO_LABEL_DETECTED_PEAK);
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

	public static boolean isTransferUseIdentifiedPeaksOnly() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_TRANSFER_USE_IDENTIFIED_PEAKS_ONLY, DEF_TRANSFER_USE_IDENTIFIED_PEAKS_ONLY);
	}

	public static boolean isTransferUseAdjustmentByPurity() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_TRANSFER_USE_ADJUSTMENT_BY_PURITY, DEF_TRANSFER_USE_ADJUSTMENT_BY_PURITY);
	}

	public static boolean isTransferOptimizeRange() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_TRANSFER_OPTIMIZE_RANGE, DEF_TRANSFER_OPTIMIZE_RANGE);
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

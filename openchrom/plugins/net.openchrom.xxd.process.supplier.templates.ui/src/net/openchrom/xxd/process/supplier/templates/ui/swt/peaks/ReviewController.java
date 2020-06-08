/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.swt.peaks;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.PeakType;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.updates.IPeakUpdateListener;
import org.eclipse.chemclipse.msd.model.core.IPeakMSD;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.process.supplier.templates.model.ReviewSetting;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.ProcessReviewSettings;
import net.openchrom.xxd.process.supplier.templates.util.PeakDetectorListUtil;

public class ReviewController {

	private ProcessReviewUI processReviewUI;
	private PeakDetectorChart peakDetectorChart;
	private ExtendedPeakReviewUI extendedPeakReviewUI;
	private ExtendedTargetsUI extendedTargetsUI;
	private ExtendedComparisonUI extendedComparisonUI;
	//
	private PeakDetectorListUtil peakDetectorListUtil = new PeakDetectorListUtil();
	private ProcessReviewSettings processSettings;
	private ReviewSetting reviewSetting = null;

	public void setInput(ProcessReviewSettings processSettings) {

		/*
		 * Setting the extended review UI input
		 * calls the method "update(ReviewSetting reviewSetting, PeakType peakType, boolean optimizeRange)".
		 */
		this.processSettings = processSettings;
		processReviewUI.setInput(processSettings);
	}

	/**
	 * Updates the selection of a review setting.
	 * 
	 * @param reviewSetting
	 * @param peakType
	 * @param optimizeRange
	 */
	public void update(ReviewSetting reviewSetting) {

		this.reviewSetting = reviewSetting;
		updateDetectorChart();
		updatePeakStatusUI(null);
	}

	/**
	 * Updates the selected peak.
	 * 
	 * @param reviewSetting
	 * @param peak
	 */
	public void update(List<IPeak> peaks) {

		Set<IIdentificationTarget> targets = null;
		IPeak peak = peaks.size() > 0 ? peaks.get(0) : null;
		//
		if(peak != null) {
			peaks.add(peak);
			targets = peak.getTargets();
		}
		//
		updateChartPeaks(peaks);
		updateExtendedTargetsUI(peak, targets);
	}

	/**
	 * Updates the selected peak and target.
	 * 
	 * @param peak
	 * @param identificationTarget
	 */
	public void update(IPeak peak, IIdentificationTarget identificationTarget) {

		if(peak instanceof IPeakMSD && identificationTarget != null) {
			IPeakMSD peakMSD = (IPeakMSD)peak;
			IScanMSD scanMSD = peakMSD.getExtractedMassSpectrum();
			IScanMSD unknownMassSpectrum = scanMSD.getOptimizedMassSpectrum() != null ? scanMSD.getOptimizedMassSpectrum() : scanMSD;
			extendedComparisonUI.update(unknownMassSpectrum, identificationTarget);
		} else {
			extendedComparisonUI.update(null, null);
		}
	}

	/**
	 * This method updates the peak detector chart.
	 */
	public void updateDetectorChart() {

		if(peakDetectorChart != null && processReviewUI != null) {
			if(processSettings != null) {
				if(processSettings != null && reviewSetting != null) {
					IChromatogram<?> chromatogram = processSettings.getChromatogram();
					if(chromatogram != null) {
						/*
						 * Settings
						 */
						int startRetentionTime = getStartRetentionTime();
						int stopRetentionTime = getStopRetentionTime();
						PeakType peakType = reviewSetting.getDetectorType();
						boolean optimizeRange = reviewSetting.isOptimizeRange();
						//
						if(peakType != null) {
							/*
							 * The detector range defines how to detect peaks.
							 */
							DetectorRange detectorRange = new DetectorRange();
							detectorRange.setChromatogram(chromatogram);
							detectorRange.setRetentionTimeStart(startRetentionTime);
							detectorRange.setRetentionTimeStop(stopRetentionTime);
							detectorRange.setTraces(peakDetectorListUtil.extractTraces(reviewSetting.getTraces()));
							detectorRange.setDetectorType(peakType.toString());
							detectorRange.setOptimizeRange(optimizeRange);
							//
							peakDetectorChart.update(detectorRange);
						}
					}
				}
			}
		}
	}

	@SuppressWarnings({"unchecked"})
	public void deletePeaks(List<IPeak> peaks) {

		if(processSettings != null) {
			IChromatogram<IPeak> chromatogram = (IChromatogram<IPeak>)processSettings.getChromatogram();
			if(chromatogram != null) {
				chromatogram.removePeaks(peaks);
				updateDetectorChart();
				updatePeakStatusUI(null);
			}
		}
	}

	protected void createProcessReviewUI(Composite parent) {

		processReviewUI = new ProcessReviewUI(parent, SWT.NONE);
		processReviewUI.setController(this);
	}

	protected void createPeakDetectorChart(Composite parent) {

		peakDetectorChart = new PeakDetectorChart(parent, SWT.BORDER);
		peakDetectorChart.setPeakUpdateListener(new IPeakUpdateListener() {

			@Override
			public void update(IPeak peak) {

				updatePeakStatusUI(peak);
			}
		});
	}

	protected void createExtendedPeaksUI(Composite parent) {

		extendedPeakReviewUI = new ExtendedPeakReviewUI(parent, SWT.NONE);
		extendedPeakReviewUI.setController(this);
	}

	protected void createExtendedTargetsUI(Composite parent) {

		extendedTargetsUI = new ExtendedTargetsUI(parent, SWT.NONE);
		extendedTargetsUI.setController(this);
	}

	protected void createExtendedComparisonUI(Composite parent) {

		extendedComparisonUI = new ExtendedComparisonUI(parent, SWT.NONE);
	}

	private void updateChartPeaks(List<IPeak> peaks) {

		peakDetectorChart.updatePeaks(peaks);
	}

	@SuppressWarnings("unchecked")
	private void updatePeakStatusUI(IPeak peak) {

		List<IPeak> peaks = new ArrayList<>();
		//
		if(processReviewUI != null) {
			if(peak != null) {
				peaks.add(peak);
			} else {
				if(reviewSetting != null) {
					if(processSettings != null) {
						IChromatogram<IPeak> chromatogram = (IChromatogram<IPeak>)processSettings.getChromatogram();
						if(chromatogram != null) {
							/*
							 * Settings
							 */
							int startRetentionTime = getStartRetentionTime();
							int stopRetentionTime = getStopRetentionTime();
							peaks.addAll(chromatogram.getPeaks(startRetentionTime, stopRetentionTime));
						}
					}
				}
			}
		}
		//
		if(extendedPeakReviewUI != null) {
			extendedPeakReviewUI.setInput(reviewSetting, peaks);
		}
	}

	private void updateExtendedTargetsUI(IPeak peak, Set<IIdentificationTarget> targets) {

		extendedTargetsUI.setInput(reviewSetting, peak, targets);
	}

	private int getStartRetentionTime() {

		int time = 0;
		if(reviewSetting != null) {
			int retentionTimeDeltaLeft = (int)(PreferenceSupplier.getUiDetectorDeltaLeftMinutes() * IChromatogram.MINUTE_CORRELATION_FACTOR);
			time = reviewSetting.getStartRetentionTime() - retentionTimeDeltaLeft;
		}
		return time;
	}

	private int getStopRetentionTime() {

		int time = 0;
		if(reviewSetting != null) {
			int retentionTimeDeltaRight = (int)(PreferenceSupplier.getUiDetectorDeltaRightMinutes() * IChromatogram.MINUTE_CORRELATION_FACTOR);
			time = reviewSetting.getStopRetentionTime() + retentionTimeDeltaRight;
		}
		return time;
	}
}

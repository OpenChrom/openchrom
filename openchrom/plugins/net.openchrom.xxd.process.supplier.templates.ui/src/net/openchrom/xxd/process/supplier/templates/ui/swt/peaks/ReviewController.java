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
import org.eclipse.chemclipse.msd.model.core.IPeakMSD;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.process.supplier.templates.model.ReviewSetting;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.ProcessReviewSettings;
import net.openchrom.xxd.process.supplier.templates.util.PeakDetectorListUtil;

public class ReviewController {

	private ExtendedReviewUI extendedReviewUI;
	private PeakDetectorChart peakDetectorChart;
	private ExtendedPeakStatusUI extendedPeakStatusUI;
	private ExtendedTargetsUI extendedTargetsUI;
	private ExtendedComparisonUI extendedComparisonUI;
	//
	private PeakDetectorListUtil peakDetectorListUtil = new PeakDetectorListUtil();
	private ProcessReviewSettings processSettings;

	public void setInput(ProcessReviewSettings processSettings) {

		this.processSettings = processSettings;
		extendedReviewUI.setInput(processSettings);
	}

	/**
	 * This method updates the selection of a review setting.
	 * 
	 * @param reviewSetting
	 * @param peakType
	 * @param optimizeRange
	 * @param forceTIC
	 */
	public void update(ReviewSetting reviewSetting, PeakType peakType, boolean optimizeRange, boolean forceTIC) {

		peakDetectorChart.setForceTIC(forceTIC);
		updateDetectorChart(reviewSetting, peakType, optimizeRange);
		updatePeakStatusUI(reviewSetting);
	}

	public void update(ReviewSetting reviewSetting, IPeak peak) {

		List<IPeak> peaks = new ArrayList<>();
		Set<IIdentificationTarget> targets = null;
		if(peak != null) {
			peaks.add(peak);
			targets = peak.getTargets();
		}
		//
		updateChartPeaks(peaks);
		updateExtendedTargetsUI(peak, targets);
	}

	protected void createExtendedReviewUI(Composite parent) {

		extendedReviewUI = new ExtendedReviewUI(parent, SWT.NONE);
		extendedReviewUI.setReviewController(this);
	}

	protected void createPeakDetectorChart(Composite parent) {

		peakDetectorChart = new PeakDetectorChart(parent, SWT.BORDER);
		peakDetectorChart.setReviewController(this);
	}

	protected void createExtendedPeaksUI(Composite parent) {

		extendedPeakStatusUI = new ExtendedPeakStatusUI(parent, SWT.NONE);
		extendedPeakStatusUI.setReviewController(this);
	}

	protected void createExtendedTargetsUI(Composite parent) {

		extendedTargetsUI = new ExtendedTargetsUI(parent, SWT.NONE);
		extendedTargetsUI.setReviewController(this);
	}

	protected void createExtendedComparisonUI(Composite parent) {

		extendedComparisonUI = new ExtendedComparisonUI(parent, SWT.NONE);
		extendedComparisonUI.setReviewController(this);
	}

	public void updateTarget(IPeak peak, IIdentificationTarget identificationTarget) {

		if(peak instanceof IPeakMSD) {
			IPeakMSD peakMSD = (IPeakMSD)peak;
			IScanMSD scanMSD = peakMSD.getExtractedMassSpectrum();
			IScanMSD unknownMassSpectrum = scanMSD.getOptimizedMassSpectrum() != null ? scanMSD.getOptimizedMassSpectrum() : scanMSD;
			extendedComparisonUI.update(unknownMassSpectrum, identificationTarget);
		}
	}

	private void updateDetectorChart(ReviewSetting reviewSetting, PeakType peakType, boolean optimizeRange) {

		if(peakDetectorChart != null && extendedReviewUI != null) {
			if(processSettings != null) {
				if(processSettings != null && reviewSetting != null) {
					IChromatogram<?> chromatogram = processSettings.getChromatogram();
					if(chromatogram != null) {
						/*
						 * Settings
						 */
						int startRetentionTime = getStartRetentionTime(reviewSetting);
						int stopRetentionTime = getStopRetentionTime(reviewSetting);
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

	private void updateChartPeaks(List<IPeak> peaks) {

		peakDetectorChart.updatePeaks(peaks);
	}

	@SuppressWarnings("unchecked")
	private void updatePeakStatusUI(ReviewSetting reviewSetting) {

		List<IPeak> peaks = null;
		//
		if(extendedReviewUI != null) {
			if(reviewSetting != null) {
				if(processSettings != null) {
					IChromatogram<IPeak> chromatogram = (IChromatogram<IPeak>)processSettings.getChromatogram();
					if(chromatogram != null) {
						/*
						 * Settings
						 */
						int startRetentionTime = getStartRetentionTime(reviewSetting);
						int stopRetentionTime = getStopRetentionTime(reviewSetting);
						peaks = chromatogram.getPeaks(startRetentionTime, stopRetentionTime);
					}
				}
			}
		}
		//
		if(extendedPeakStatusUI != null) {
			extendedPeakStatusUI.setInput(peaks, reviewSetting);
		}
	}

	private void updateExtendedTargetsUI(IPeak peak, Set<IIdentificationTarget> targets) {

		extendedTargetsUI.setInput(peak, targets);
	}

	private int getStartRetentionTime(ReviewSetting reviewSetting) {

		int time = 0;
		if(reviewSetting != null) {
			int retentionTimeDeltaLeft = (int)(PreferenceSupplier.getUiDetectorDeltaLeftMinutes() * IChromatogram.MINUTE_CORRELATION_FACTOR);
			time = reviewSetting.getStartRetentionTime() - retentionTimeDeltaLeft;
		}
		return time;
	}

	private int getStopRetentionTime(ReviewSetting reviewSetting) {

		int time = 0;
		if(reviewSetting != null) {
			int retentionTimeDeltaRight = (int)(PreferenceSupplier.getUiDetectorDeltaRightMinutes() * IChromatogram.MINUTE_CORRELATION_FACTOR);
			time = reviewSetting.getStopRetentionTime() + retentionTimeDeltaRight;
		}
		return time;
	}
}

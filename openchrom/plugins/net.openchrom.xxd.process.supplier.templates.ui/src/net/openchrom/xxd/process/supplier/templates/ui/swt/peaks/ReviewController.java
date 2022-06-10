/*******************************************************************************
 * Copyright (c) 2020, 2022 Lablicate GmbH.
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

import org.eclipse.chemclipse.csd.model.core.IChromatogramCSD;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.PeakType;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.updates.IPeakUpdateListener;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IPeakMSD;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.ux.extension.xxd.ui.custom.PeakChartSettings;
import org.eclipse.chemclipse.ux.extension.xxd.ui.swt.ExtendedPeakTracesUI;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtchart.Range;

import net.openchrom.xxd.process.supplier.templates.model.AbstractSetting;
import net.openchrom.xxd.process.supplier.templates.model.ReviewSetting;
import net.openchrom.xxd.process.supplier.templates.model.Visibility;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.ReviewSupport;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.ProcessReviewSettings;
import net.openchrom.xxd.process.supplier.templates.util.PeakDetectorListUtil;

public class ReviewController {

	private static final String DETECTOR_DESCRIPTION = "Manual (Review UI)";
	//
	private ProcessReviewUI processReviewUI;
	private PeakDetectorChart peakDetectorChart;
	private ExtendedPeakReviewUI extendedPeakReviewUI;
	private ExtendedTargetsUI extendedTargetsUI;
	private ExtendedComparisonUI extendedComparisonUI;
	private ExtendedPeakTracesUI extendedPeakTracesUI;
	//
	private PeakDetectorListUtil peakDetectorListUtil = new PeakDetectorListUtil();
	private ProcessReviewSettings processSettings;
	private ReviewSetting reviewSetting = null;
	//
	private PeakReviewControl peakReviewControl;

	public ReviewController(PeakReviewControl peakReviewControl) {

		this.peakReviewControl = peakReviewControl;
	}

	public void setInput(ProcessReviewSettings processSettings) {

		this.processSettings = processSettings;
		processReviewUI.setInput(processSettings);
		//
		if(processSettings != null) {
			List<ReviewSetting> reviewSettings = processSettings.getReviewSettings();
			if(!reviewSettings.isEmpty()) {
				update(reviewSettings.get(0));
			}
		}
	}

	public void updateSettings() {

		if(peakReviewControl != null) {
			peakReviewControl.updateWidgets();
		}
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
		if(processSettings != null && reviewSetting != null) {
			/*
			 * The retention time validation is performed in the updateDetectorChart() method.
			 */
			updateDetectorChart();
			updatePeakStatusUI(null);
		}
	}

	/**
	 * Updates the selected peak.
	 * 
	 * @param reviewSetting
	 * @param peak
	 */
	public void update(List<IPeak> peaks) {

		IPeak peak = !peaks.isEmpty() ? peaks.get(0) : null;
		updateChartPeaks(peaks);
		updateExtendedTargetsUI(peak);
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
		//
		extendedPeakTracesUI.update(peak);
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
						 * Validate min/max retention time ranges.
						 */
						int startRetentionTime = getStartRetentionTime();
						int stopRetentionTime = getStopRetentionTime();
						//
						if(isRetentionTimeRangeValid(startRetentionTime, stopRetentionTime)) {
							startRetentionTime = (startRetentionTime < chromatogram.getStartRetentionTime()) ? chromatogram.getStartRetentionTime() : startRetentionTime;
							stopRetentionTime = (stopRetentionTime > chromatogram.getStopRetentionTime()) ? chromatogram.getStopRetentionTime() : stopRetentionTime;
							PeakType peakType = reviewSetting.getPeakType();
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
								detectorRange.setDetectorType(peakType);
								detectorRange.setOptimizeRange(optimizeRange);
								/*
								 * Settings display data
								 */
								PeakDetectorChartSettings chartSettings = new PeakDetectorChartSettings();
								setVisibilityOptions(chartSettings, chromatogram);
								chartSettings.setShowBaseline(PreferenceSupplier.isShowBaselineReview());
								chartSettings.setDeltaRetentionTimeLeft(PreferenceSupplier.getReviewDeltaLeftMilliseconds());
								chartSettings.setDeltaRetentionTimeRight(PreferenceSupplier.getReviewDeltaRightMilliseconds());
								chartSettings.setReplacePeak(PreferenceSupplier.isReviewReplaceNearestPeak());
								//
								peakDetectorChart.update(detectorRange, chartSettings);
								/*
								 * Focus XIC
								 */
								if(focusXIC(detectorRange, chartSettings)) {
									peakDetectorChart.focusTraces(PreferenceSupplier.getOffsetMaxY());
								}
							}
						}
					}
				}
			}
		}
	}

	@SuppressWarnings({"unchecked"})
	public void deletePeaks(Shell shell, List<IPeak> peaks) {

		if(processSettings != null) {
			IChromatogram<IPeak> chromatogram = (IChromatogram<IPeak>)processSettings.getChromatogram();
			if(chromatogram != null) {
				/*
				 * If at least one peak has been reviewed already, let
				 * the user confirm the delete action.
				 */
				boolean deletePeaks = true;
				if(containsReviewedPeaks(peaks)) {
					MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
					messageBox.setText("Delete Peaks");
					messageBox.setMessage("At least one peak has been reviewed already. Delete anyway?");
					if(messageBox.open() != SWT.YES) {
						deletePeaks = false;
					}
				}
				/*
				 * Delete the peaks
				 */
				if(deletePeaks) {
					chromatogram.removePeaks(peaks);
					updateDetectorChart();
					updatePeakStatusUI(null);
				}
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

				if(peak != null) {
					/*
					 * Get the current selection.
					 */
					Range selectedRangeX = peakDetectorChart.getCurrentRangeX();
					Range selectedRangeY = peakDetectorChart.getCurrentRangeY();
					//
					if(PreferenceSupplier.isReviewSetTargetDetectedPeak()) {
						if(reviewSetting != null) {
							peak.setDetectorDescription(DETECTOR_DESCRIPTION);
							ReviewSupport.setReview(peak, reviewSetting, true, true);
						}
					}
					/*
					 * Update the chart and list.
					 */
					updateDetectorChart();
					updatePeakStatusUI(peak);
					/*
					 * Keep the selection
					 */
					peakDetectorChart.updateRange(selectedRangeX, selectedRangeY);
				}
			}
		});
		//
		peakDetectorChart.setSectionUpdateListener(new ISectionUpdateListener() {

			@Override
			public void update(boolean previous) {

				int index = processReviewUI.getSelection();
				if(previous) {
					index--;
				} else {
					index++;
				}
				processReviewUI.setSelection(index);
			}
		});
		//
		peakDetectorChart.setRangeUpdateListener(new IRangeUpdateListener() {

			@Override
			public void update(boolean zoomIn) {

				if(reviewSetting != null) {
					/*
					 * Adjust the retention time range.
					 */
					int offset = PreferenceSupplier.getReviewDynamicOffsetMilliseconds();
					int retentionTimeDelta = zoomIn ? -offset : offset;
					int startRetentionTime = reviewSetting.getStartRetentionTime() - retentionTimeDelta;
					int stopRetentionTime = reviewSetting.getStopRetentionTime() + retentionTimeDelta;
					//
					if(startRetentionTime < stopRetentionTime) {
						//
						reviewSetting.setStartRetentionTime(startRetentionTime);
						reviewSetting.setStopRetentionTime(stopRetentionTime);
						/*
						 * The retention time validation is performed in the updateDetectorChart() method.
						 */
						processReviewUI.update();
						updateDetectorChart();
						updatePeakStatusUI(null);
					}
				}
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

	protected void createExtendedPeakTracesUI(Composite parent) {

		extendedPeakTracesUI = new ExtendedPeakTracesUI(parent, SWT.NONE);
	}

	private void updateChartPeaks(List<IPeak> peaks) {

		peakDetectorChart.updatePeaks(peaks);
	}

	@SuppressWarnings("unchecked")
	private void updatePeakStatusUI(IPeak peak) {

		List<IPeak> peaks = new ArrayList<>();
		//
		if(processReviewUI != null) {
			if(reviewSetting != null) {
				if(processSettings != null) {
					IChromatogram<IPeak> chromatogram = (IChromatogram<IPeak>)processSettings.getChromatogram();
					if(chromatogram != null) {
						/*
						 * Settings
						 */
						int startRetentionTime = getStartRetentionTime();
						int stopRetentionTime = getStopRetentionTime();
						List<IPeak> chromatogramPeaks = chromatogram.getPeaks(startRetentionTime, stopRetentionTime);
						//
						if(PreferenceSupplier.isReviewShowOnlyRelevantPeaks() && isChromatogramMSD(chromatogram)) {
							Set<Integer> traces = peakDetectorListUtil.extractTraces(reviewSetting.getTraces());
							for(IPeak chromatogramPeak : chromatogramPeaks) {
								if(PeakSupport.isPeakRelevant(chromatogramPeak, traces)) {
									peaks.add(chromatogramPeak);
								}
							}
						} else {
							peaks.addAll(chromatogramPeaks);
						}
					}
				}
			}
		}
		//
		if(extendedPeakReviewUI != null) {
			extendedPeakReviewUI.setInput(reviewSetting, peaks, peak);
		}
	}

	@SuppressWarnings("rawtypes")
	private boolean isChromatogramMSD(IChromatogram chromatogram) {

		return chromatogram instanceof IChromatogramMSD;
	}

	private void updateExtendedTargetsUI(IPeak peak) {

		extendedTargetsUI.setInput(reviewSetting, peak);
	}

	private boolean isRetentionTimeRangeValid(int startRetentionTime, int stopRetentionTime) {

		return startRetentionTime != AbstractSetting.MISSING_RETENTION_TIME && stopRetentionTime != AbstractSetting.MISSING_RETENTION_TIME;
	}

	private int getStartRetentionTime() {

		int retentionTime = AbstractSetting.FULL_RETENTION_TIME;
		if(reviewSetting != null) {
			int retentionTimeDeltaLeft = PreferenceSupplier.getReviewDeltaLeftMilliseconds();
			retentionTime = reviewSetting.getStartRetentionTime() - retentionTimeDeltaLeft;
		}
		//
		return retentionTime;
	}

	private int getStopRetentionTime() {

		int retentionTime = AbstractSetting.FULL_RETENTION_TIME;
		if(reviewSetting != null) {
			int retentionTimeDeltaRight = PreferenceSupplier.getReviewDeltaRightMilliseconds();
			retentionTime = reviewSetting.getStopRetentionTime() + retentionTimeDeltaRight;
		}
		//
		return retentionTime;
	}

	private boolean focusXIC(DetectorRange detectorRange, PeakDetectorChartSettings chartSettings) {

		return detectorRange.getTraces().size() > 0 && chartSettings.isShowChromatogramTraces() && PreferenceSupplier.isReviewFocusXIC();
	}

	private boolean containsReviewedPeaks(List<IPeak> peaks) {

		for(IPeak peak : peaks) {
			if(ReviewSupport.isPeakReviewed(peak)) {
				return true;
			}
		}
		return false;
	}

	private void setVisibilityOptions(PeakChartSettings chartSettings, IChromatogram<?> chromatogram) {

		if(chromatogram instanceof IChromatogramCSD) {
			chartSettings.setShowChromatogramTIC(true);
			chartSettings.setShowChromatogramTraces(false);
		} else {
			Visibility visibility = PreferenceSupplier.getReviewVisibility();
			chartSettings.setShowChromatogramTIC(Visibility.isTIC(visibility));
			chartSettings.setShowChromatogramTraces(Visibility.isTRACE(visibility));
		}
	}
}

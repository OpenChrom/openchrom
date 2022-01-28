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
import org.eclipse.chemclipse.model.targets.TargetValidator;
import org.eclipse.chemclipse.model.updates.IPeakUpdateListener;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.ux.extension.xxd.ui.custom.PeakChartSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtchart.Range;
import org.eclipse.swtchart.extensions.core.BaseChart;
import org.eclipse.swtchart.extensions.core.ICustomSelectionHandler;

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;
import net.openchrom.xxd.process.supplier.templates.model.Visibility;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.ProcessDetectorSettings;
import net.openchrom.xxd.process.supplier.templates.util.PeakDetectorListUtil;

public class DetectorController {

	private static final String DETECTOR_DESCRIPTION = "Manual (Detector UI)";
	//
	private ProcessDetectorUI processDetectorUI;
	private ExtendedPeaksUI extendedPeaksUI;
	private PeakDetectorChart peakDetectorChart;
	//
	private PeakDetectorListUtil peakDetectorListUtil = new PeakDetectorListUtil();
	private ProcessDetectorSettings processSettings;
	private DetectorSetting detectorSetting;

	public void setInput(ProcessDetectorSettings processSettings) {

		this.processSettings = processSettings;
		processDetectorUI.setInput(processSettings);
		if(processSettings != null) {
			List<DetectorSetting> detectorSettings = processSettings.getDetectorSettings();
			if(!detectorSettings.isEmpty()) {
				update(detectorSettings.get(0));
			}
		}
	}

	/**
	 * Updates the selection of a detector setting.
	 * 
	 * @param detectorSetting
	 * @param peakType
	 * @param optimizeRange
	 */
	public void update(DetectorSetting detectorSetting) {

		this.detectorSetting = detectorSetting;
		if(processSettings != null && detectorSetting != null) {
			/*
			 * The retention time validation is performed in the updateDetectorChart() method.
			 */
			updateDetectorChart();
			updatePeakStatusUI(null);
		}
	}

	public void update(List<IPeak> peaks) {

		updateChartPeaks(peaks);
	}

	public void updateDetectorChart() {

		if(peakDetectorChart != null && processDetectorUI != null) {
			if(processSettings != null) {
				if(processSettings != null && detectorSetting != null) {
					IChromatogram<?> chromatogram = processSettings.getChromatogram();
					if(chromatogram != null) {
						/*
						 * Settings
						 * Validate min/max retention time ranges.
						 */
						int startRetentionTime = getStartRetentionTime();
						int stopRetentionTime = getStopRetentionTime();
						startRetentionTime = (startRetentionTime < chromatogram.getStartRetentionTime()) ? chromatogram.getStartRetentionTime() : startRetentionTime;
						stopRetentionTime = (stopRetentionTime > chromatogram.getStopRetentionTime()) ? chromatogram.getStopRetentionTime() : stopRetentionTime;
						PeakType peakType = detectorSetting.getDetectorType();
						boolean optimizeRange = detectorSetting.isOptimizeRange();
						//
						if(peakType != null) {
							/*
							 * The detector range defines how to detect peaks.
							 */
							DetectorRange detectorRange = new DetectorRange();
							detectorRange.setChromatogram(chromatogram);
							detectorRange.setRetentionTimeStart(startRetentionTime);
							detectorRange.setRetentionTimeStop(stopRetentionTime);
							detectorRange.setTraces(peakDetectorListUtil.extractTraces(detectorSetting.getTraces()));
							detectorRange.setDetectorType(peakType);
							detectorRange.setOptimizeRange(optimizeRange);
							/*
							 * Settings display data
							 */
							PeakDetectorChartSettings chartSettings = new PeakDetectorChartSettings();
							setVisibilityOptions(chartSettings, chromatogram);
							chartSettings.setShowBaseline(PreferenceSupplier.isShowBaselineDetector());
							chartSettings.setDeltaRetentionTimeLeft(PreferenceSupplier.getDetectorDeltaLeftMilliseconds());
							chartSettings.setDeltaRetentionTimeRight(PreferenceSupplier.getDetectorDeltaRightMilliseconds());
							chartSettings.setReplacePeak(PreferenceSupplier.isDetectorReplaceNearestPeak());
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

	@SuppressWarnings({"unchecked"})
	public void deletePeaks(List<IPeak> peaks) {

		if(detectorSetting != null) {
			IChromatogram<IPeak> chromatogram = (IChromatogram<IPeak>)processSettings.getChromatogram();
			if(chromatogram != null) {
				chromatogram.removePeaks(peaks);
				updateDetectorChart();
				updatePeakStatusUI(null);
			}
		}
	}

	protected void createExtendedDetectorUI(Composite parent) {

		processDetectorUI = new ProcessDetectorUI(parent, SWT.NONE);
		processDetectorUI.setController(this);
	}

	protected void createExtendedPeaksUI(Composite parent) {

		extendedPeaksUI = new ExtendedPeaksUI(parent, SWT.NONE);
		extendedPeaksUI.setController(this);
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
					/*
					 * Label the peak.
					 */
					peak.setDetectorDescription(DETECTOR_DESCRIPTION);
					if(detectorSetting != null) {
						String name = detectorSetting.getName();
						if(!name.isEmpty()) {
							String casNumber = "";
							IIdentificationTarget identificationTarget = IIdentificationTarget.createDefaultTarget(name, casNumber, TargetValidator.IDENTIFIER);
							if(identificationTarget != null) {
								peak.getTargets().add(identificationTarget);
							}
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
					updatePeakListSelection(peakDetectorChart.getBaseChart());
				}
			}
		});
		//
		peakDetectorChart.setSectionUpdateListener(new ISectionUpdateListener() {

			@Override
			public void update(boolean previous) {

				int index = processDetectorUI.getSelection();
				if(previous) {
					index--;
				} else {
					index++;
				}
				processDetectorUI.setSelection(index);
			}
		});
		//
		peakDetectorChart.setRangeUpdateListener(new IRangeUpdateListener() {

			@Override
			public void update(boolean zoomIn) {

				if(detectorSetting != null) {
					/*
					 * Adjust the retention time range.
					 */
					int offset = PreferenceSupplier.getDetectorDynamicOffsetMilliseconds();
					int retentionTimeDelta = zoomIn ? -offset : offset;
					int startRetentionTime = detectorSetting.getStartRetentionTime() - retentionTimeDelta;
					int stopRetentionTime = detectorSetting.getStopRetentionTime() + retentionTimeDelta;
					//
					if(startRetentionTime < stopRetentionTime) {
						//
						detectorSetting.setStartRetentionTime(startRetentionTime);
						detectorSetting.setStopRetentionTime(stopRetentionTime);
						/*
						 * The retention time validation is performed in the updateDetectorChart() method.
						 */
						processDetectorUI.update();
						updateDetectorChart();
						updatePeakStatusUI(null);
					}
				}
			}
		});
		//
		BaseChart baseChart = peakDetectorChart.getBaseChart();
		baseChart.addCustomRangeSelectionHandler(new ICustomSelectionHandler() {

			@Override
			public void handleUserSelection(Event event) {

				BaseChart baseChart = peakDetectorChart.getBaseChart();
				updatePeakListSelection(baseChart);
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void updatePeakListSelection(BaseChart baseChart) {

		Range rangeX = baseChart.getAxisSet().getXAxis(BaseChart.ID_PRIMARY_X_AXIS).getRange();
		if(processSettings != null) {
			IChromatogram<IPeak> chromatogram = (IChromatogram<IPeak>)processSettings.getChromatogram();
			if(chromatogram != null) {
				/*
				 * Settings
				 */
				int startRetentionTime = (int)rangeX.lower;
				int stopRetentionTime = (int)rangeX.upper;
				List<IPeak> peaks = chromatogram.getPeaks(startRetentionTime, stopRetentionTime);
				if(extendedPeaksUI != null) {
					extendedPeaksUI.setInput(detectorSetting, peaks, null);
				}
			}
		}
	}

	private void updateChartPeaks(List<IPeak> peaks) {

		peakDetectorChart.updatePeaks(peaks);
	}

	@SuppressWarnings("unchecked")
	private void updatePeakStatusUI(IPeak peak) {

		List<IPeak> peaks = new ArrayList<>();
		//
		if(processDetectorUI != null) {
			if(detectorSetting != null) {
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
						if(PreferenceSupplier.isDetectorShowOnlyRelevantPeaks() && isChromatogramMSD(chromatogram)) {
							Set<Integer> traces = peakDetectorListUtil.extractTraces(detectorSetting.getTraces());
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
		if(extendedPeaksUI != null) {
			extendedPeaksUI.setInput(detectorSetting, peaks, peak);
		}
	}

	@SuppressWarnings("rawtypes")
	private boolean isChromatogramMSD(IChromatogram chromatogram) {

		return chromatogram instanceof IChromatogramMSD;
	}

	private int getStartRetentionTime() {

		int time = 0;
		if(detectorSetting != null) {
			int retentionTimeDeltaLeft = PreferenceSupplier.getDetectorDeltaLeftMilliseconds();
			time = detectorSetting.getStartRetentionTime() - retentionTimeDeltaLeft;
		}
		return time;
	}

	private int getStopRetentionTime() {

		int time = 0;
		if(detectorSetting != null) {
			int retentionTimeDeltaRight = PreferenceSupplier.getDetectorDeltaRightMilliseconds();
			time = detectorSetting.getStopRetentionTime() + retentionTimeDeltaRight;
		}
		return time;
	}

	private boolean focusXIC(DetectorRange detectorRange, PeakDetectorChartSettings chartSettings) {

		return !detectorRange.getTraces().isEmpty() && chartSettings.isShowChromatogramTraces() && PreferenceSupplier.isDetectorFocusXIC();
	}

	private void setVisibilityOptions(PeakChartSettings chartSettings, IChromatogram<?> chromatogram) {

		if(chromatogram instanceof IChromatogramCSD) {
			chartSettings.setShowChromatogramTIC(true);
			chartSettings.setShowChromatogramTraces(false);
		} else {
			Visibility visibility = PreferenceSupplier.getDetectorVisibility();
			chartSettings.setShowChromatogramTIC(Visibility.isTIC(visibility));
			chartSettings.setShowChromatogramTraces(Visibility.isTRACE(visibility));
		}
	}
}

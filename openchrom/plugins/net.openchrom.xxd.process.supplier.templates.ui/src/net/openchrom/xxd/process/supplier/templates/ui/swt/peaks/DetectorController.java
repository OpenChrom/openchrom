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

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.PeakType;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.targets.TargetValidator;
import org.eclipse.chemclipse.model.updates.IPeakUpdateListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtchart.IAxis;
import org.eclipse.swtchart.IAxisSet;
import org.eclipse.swtchart.Range;
import org.eclipse.swtchart.extensions.core.BaseChart;
import org.eclipse.swtchart.extensions.core.ICustomSelectionHandler;

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;
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
			if(detectorSettings.size() > 0) {
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
		updateDetectorChart();
		updatePeakStatusUI(null);
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
						 */
						int startRetentionTime = getStartRetentionTime();
						int stopRetentionTime = getStopRetentionTime();
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
							detectorRange.setDetectorType(peakType.toString());
							detectorRange.setOptimizeRange(optimizeRange);
							/*
							 * Settings display data
							 */
							PeakDetectorChartSettings chartSettings = new PeakDetectorChartSettings();
							chartSettings.setShowChromatogramTIC(PreferenceSupplier.isShowChromatogramDetectorTIC());
							chartSettings.setShowChromatogramXIC(PreferenceSupplier.isShowChromatogramDetectorXIC());
							chartSettings.setShowBaseline(PreferenceSupplier.isShowBaselineDetector());
							chartSettings.setDeltaRetentionTimeLeft(PreferenceSupplier.getDetectorDeltaLeftMilliseconds());
							chartSettings.setDeltaRetentionTimeRight(PreferenceSupplier.getDetectorDeltaRightMilliseconds());
							chartSettings.setReplacePeak(PreferenceSupplier.isDetectorReplacePeak());
							//
							peakDetectorChart.update(detectorRange, chartSettings);
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
					BaseChart baseChart = peakDetectorChart.getBaseChart();
					IAxisSet axisSet = baseChart.getAxisSet();
					IAxis xAxis = axisSet.getXAxis(BaseChart.ID_PRIMARY_X_AXIS);
					IAxis yAxis = axisSet.getYAxis(BaseChart.ID_PRIMARY_Y_AXIS);
					Range selectedRangeX = new Range(xAxis.getRange().lower, xAxis.getRange().upper);
					Range selectedRangeY = new Range(yAxis.getRange().lower, yAxis.getRange().upper);
					/*
					 * Label the peak.
					 */
					peak.setDetectorDescription(DETECTOR_DESCRIPTION);
					if(detectorSetting != null) {
						String name = detectorSetting.getName();
						String casNumber = "";
						IIdentificationTarget identificationTarget = IIdentificationTarget.createDefaultTarget(name, casNumber, TargetValidator.IDENTIFIER);
						if(identificationTarget != null) {
							peak.getTargets().add(identificationTarget);
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
		peakDetectorChart.getBaseChart().addCustomRangeSelectionHandler(new ICustomSelectionHandler() {

			@SuppressWarnings("unchecked")
			@Override
			public void handleUserSelection(Event event) {

				BaseChart baseChart = peakDetectorChart.getBaseChart();
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
		});
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
						peaks.addAll(chromatogram.getPeaks(startRetentionTime, stopRetentionTime));
					}
				}
			}
		}
		//
		if(extendedPeaksUI != null) {
			extendedPeaksUI.setInput(detectorSetting, peaks, peak);
		}
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
}

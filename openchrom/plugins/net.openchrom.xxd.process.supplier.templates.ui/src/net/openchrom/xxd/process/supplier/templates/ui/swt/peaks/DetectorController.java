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
import org.eclipse.chemclipse.model.updates.IUpdateListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.ProcessDetectorSettings;
import net.openchrom.xxd.process.supplier.templates.util.PeakDetectorListUtil;

public class DetectorController {

	private ExtendedDetectorUI extendedDetectorUI;
	private ExtendedPeaksUI extendedPeaksUI;
	private PeakDetectorChart peakDetectorChart;
	//
	private PeakDetectorListUtil peakDetectorListUtil = new PeakDetectorListUtil();
	private ProcessDetectorSettings processSettings;
	private DetectorSetting detectorSetting;

	public void setInput(ProcessDetectorSettings processSettings) {

		this.processSettings = processSettings;
		extendedDetectorUI.setInput(processSettings);
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
		updatePeakUI();
	}

	public void update(IPeak peak) {

		List<IPeak> peaks = new ArrayList<>();
		if(peak != null) {
			peaks.add(peak);
		}
		//
		updateChartPeaks(peaks);
	}

	public void updateDetectorChart() {

		if(peakDetectorChart != null && extendedDetectorUI != null) {
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

		if(detectorSetting != null) {
			IChromatogram<IPeak> chromatogram = (IChromatogram<IPeak>)processSettings.getChromatogram();
			if(chromatogram != null) {
				chromatogram.removePeaks(peaks);
				updateDetectorChart();
				updatePeakUI();
			}
		}
	}

	protected void createExtendedDetectorUI(Composite parent) {

		extendedDetectorUI = new ExtendedDetectorUI(parent, SWT.NONE);
		extendedDetectorUI.setController(this);
	}

	protected void createExtendedPeaksUI(Composite parent) {

		extendedPeaksUI = new ExtendedPeaksUI(parent, SWT.NONE);
		extendedPeaksUI.setController(this);
	}

	protected void createPeakDetectorChart(Composite parent) {

		peakDetectorChart = new PeakDetectorChart(parent, SWT.BORDER);
		peakDetectorChart.setUpdateListener(new IUpdateListener() {

			@Override
			public void update() {

				updatePeakUI();
			}
		});
	}

	private void updateChartPeaks(List<IPeak> peaks) {

		peakDetectorChart.updatePeaks(peaks);
	}

	@SuppressWarnings("unchecked")
	private void updatePeakUI() {

		List<IPeak> peaks = null;
		if(extendedDetectorUI != null) {
			if(detectorSetting != null) {
				if(processSettings != null) {
					IChromatogram<IPeak> chromatogram = (IChromatogram<IPeak>)processSettings.getChromatogram();
					if(chromatogram != null) {
						/*
						 * Settings
						 */
						int startRetentionTime = getStartRetentionTime();
						int stopRetentionTime = getStopRetentionTime();
						peaks = chromatogram.getPeaks(startRetentionTime, stopRetentionTime);
					}
				}
			}
		}
		//
		if(extendedPeaksUI != null) {
			extendedPeaksUI.setInput(detectorSetting, peaks);
		}
	}

	private int getStartRetentionTime() {

		int time = 0;
		if(detectorSetting != null) {
			int retentionTimeDeltaLeft = (int)(PreferenceSupplier.getUiDetectorDeltaLeftMinutes() * IChromatogram.MINUTE_CORRELATION_FACTOR);
			time = detectorSetting.getStartRetentionTime() - retentionTimeDeltaLeft;
		}
		return time;
	}

	private int getStopRetentionTime() {

		int time = 0;
		if(detectorSetting != null) {
			int retentionTimeDeltaRight = (int)(PreferenceSupplier.getUiDetectorDeltaRightMinutes() * IChromatogram.MINUTE_CORRELATION_FACTOR);
			time = detectorSetting.getStopRetentionTime() + retentionTimeDeltaRight;
		}
		return time;
	}
}

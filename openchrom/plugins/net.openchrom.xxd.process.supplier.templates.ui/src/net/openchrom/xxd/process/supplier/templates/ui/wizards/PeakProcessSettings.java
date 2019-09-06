/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.wizards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.model.updates.IUpdateListener;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;

import net.openchrom.xxd.process.supplier.templates.comparator.DetectorComparator;
import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;
import net.openchrom.xxd.process.supplier.templates.settings.PeakDetectorSettings;

@SuppressWarnings("rawtypes")
public class PeakProcessSettings {

	private IProcessingInfo processingInfo;
	private IChromatogramSelection chromatogramSelection;
	private List<DetectorSetting> detectorSettings = new ArrayList<>();
	private int selectedIndex;
	/*
	 * Crap ... try to find a smarter solution.
	 */
	private IUpdateListener chartUpdateListener;
	private IUpdateListener controlUpdateListener;

	public PeakProcessSettings(IProcessingInfo processingInfo, IChromatogramSelection chromatogramSelection, PeakDetectorSettings peakDetectorSettings) {
		this.processingInfo = processingInfo;
		this.chromatogramSelection = chromatogramSelection;
		this.detectorSettings.addAll(peakDetectorSettings.getDetectorSettings());
		Collections.sort(detectorSettings, new DetectorComparator());
		selectedIndex = detectorSettings.size() > 0 ? 0 : -1;
	}

	public void setChartUpdateListener(IUpdateListener chartUpdateListener) {

		this.chartUpdateListener = chartUpdateListener;
	}

	public void setControlUpdateListener(IUpdateListener controlUpdateListener) {

		this.controlUpdateListener = controlUpdateListener;
	}

	public void updateControl() {

		fireUpdateControl();
	}

	public IProcessingInfo getProcessingInfo() {

		return processingInfo;
	}

	public IChromatogramSelection getChromatogramSelection() {

		return chromatogramSelection;
	}

	public void decreaseSelection() {

		if(selectedIndex > 0) {
			selectedIndex--;
			fireUpdateChart();
		}
	}

	public void increaseSelection() {

		if(selectedIndex < detectorSettings.size()) {
			selectedIndex++;
			fireUpdateChart();
		}
	}

	public void applySettings() {

		fireUpdateChart();
	}

	public boolean hasPrevious() {

		return selectedIndex > 0;
	}

	public boolean hasNext() {

		return selectedIndex < detectorSettings.size();
	}

	public DetectorSetting getSelectedDetectorSetting() {

		if(selectedIndex >= 0 && selectedIndex < detectorSettings.size()) {
			return detectorSettings.get(selectedIndex);
		} else {
			return null;
		}
	}

	private void fireUpdateChart() {

		if(chartUpdateListener != null) {
			chartUpdateListener.update();
		}
	}

	private void fireUpdateControl() {

		if(controlUpdateListener != null) {
			controlUpdateListener.update();
		}
	}
}

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
package net.openchrom.xxd.process.supplier.templates.ui.wizards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.msd.model.core.IPeakMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;

import net.openchrom.xxd.process.supplier.templates.comparator.ReviewComparator;
import net.openchrom.xxd.process.supplier.templates.model.ReviewSetting;
import net.openchrom.xxd.process.supplier.templates.settings.PeakReviewSettings;

@SuppressWarnings("rawtypes")
public class ProcessReviewSettings {

	private IProcessingInfo processingInfo;
	private List<? extends IPeakMSD> peaks;
	private List<ReviewSetting> reviewSettings = new ArrayList<>();
	private int selectedIndex;

	public ProcessReviewSettings(IProcessingInfo processingInfo, List<? extends IPeakMSD> peaks, PeakReviewSettings chromatogramReviewSettings) {
		this.processingInfo = processingInfo;
		this.peaks = peaks;
		this.reviewSettings.addAll(chromatogramReviewSettings.getReviewSettingsList());
		Collections.sort(reviewSettings, new ReviewComparator());
		selectedIndex = reviewSettings.size() > 0 ? 0 : -1;
	}

	public IProcessingInfo getProcessingInfo() {

		return processingInfo;
	}

	public List<IPeak> getSelectedPeaks() {

		ReviewSetting reviewSetting = getSelectedReviewSetting();
		List<IPeak> selectedPeaks = new ArrayList<>();
		if(reviewSetting != null) {
			int startRetentionTime = reviewSetting.getStartRetentionTime();
			int stopRetentionTime = reviewSetting.getStopRetentionTime();
			for(IPeak peak : peaks) {
				if(peak.getPeakStart() >= startRetentionTime && peak.getPeakEnd() <= stopRetentionTime) {
					selectedPeaks.add(peak);
				}
			}
		}
		return selectedPeaks;
	}

	public void decreaseSelection() {

		if(selectedIndex > 0) {
			selectedIndex--;
		}
	}

	public void increaseSelection() {

		if(selectedIndex < reviewSettings.size()) {
			selectedIndex++;
		}
	}

	public boolean hasPrevious() {

		if(reviewSettings.size() > 0) {
			return selectedIndex > 0;
		}
		return false;
	}

	public boolean hasNext() {

		if(reviewSettings.size() > 0) {
			return selectedIndex < reviewSettings.size();
		}
		return false;
	}

	public ReviewSetting getSelectedReviewSetting() {

		if(selectedIndex >= 0 && selectedIndex < reviewSettings.size()) {
			return reviewSettings.get(selectedIndex);
		} else {
			return null;
		}
	}
}

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

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;

import net.openchrom.xxd.process.supplier.templates.comparator.ReviewComparator;
import net.openchrom.xxd.process.supplier.templates.model.ReviewSetting;
import net.openchrom.xxd.process.supplier.templates.settings.PeakReviewSettings;

public class ProcessReviewSettings {

	private IProcessingInfo<?> processingInfo;
	private IChromatogram<?> chromatogram;
	private List<ReviewSetting> reviewSettings = new ArrayList<>();

	public ProcessReviewSettings(IProcessingInfo<?> processingInfo, IChromatogram<?> chromatogram, PeakReviewSettings peakReviewSettings) {
		this.processingInfo = processingInfo;
		this.chromatogram = chromatogram;
		this.reviewSettings.addAll(peakReviewSettings.getReviewSettingsList());
		Collections.sort(reviewSettings, new ReviewComparator());
	}

	public IProcessingInfo<?> getProcessingInfo() {

		return processingInfo;
	}

	public IChromatogram<?> getChromatogram() {

		return chromatogram;
	}

	public List<ReviewSetting> getReviewSettings() {

		return reviewSettings;
	}
}

/*******************************************************************************
 * Copyright (c) 2020, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.settings.PeakReviewSettings;
import net.openchrom.xxd.process.supplier.templates.support.RetentionIndexSupport;

public class ProcessReviewSettings {

	private IProcessingInfo<?> processingInfo;
	private IChromatogram chromatogram;
	private List<ReviewSetting> reviewSettings = new ArrayList<>();

	public ProcessReviewSettings(IProcessingInfo<?> processingInfo, IChromatogram chromatogram, PeakReviewSettings peakReviewSettings) {

		this.processingInfo = processingInfo;
		this.chromatogram = chromatogram;
		this.reviewSettings.addAll(RetentionIndexSupport.adjustReviewSettings(chromatogram, peakReviewSettings.getReviewSettingsList()));

		if(PreferenceSupplier.isReviewSettingsSort()) {
			Collections.sort(reviewSettings, new ReviewComparator()); // SORT OK
		}
	}

	public IProcessingInfo<?> getProcessingInfo() {

		return processingInfo;
	}

	public IChromatogram getChromatogram() {

		return chromatogram;
	}

	public List<ReviewSetting> getReviewSettings() {

		return reviewSettings;
	}
}

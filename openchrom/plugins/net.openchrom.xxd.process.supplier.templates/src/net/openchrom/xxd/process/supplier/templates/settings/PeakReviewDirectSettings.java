/*******************************************************************************
 * Copyright (c) 2025, 2026 Lablicate GmbH.
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
package net.openchrom.xxd.process.supplier.templates.settings;

import org.eclipse.chemclipse.chromatogram.csd.identifier.settings.IPeakIdentifierSettingsCSD;
import org.eclipse.chemclipse.chromatogram.msd.identifier.settings.IPeakIdentifierSettingsMSD;
import org.eclipse.chemclipse.chromatogram.wsd.identifier.settings.IPeakIdentifierSettingsWSD;
import org.eclipse.chemclipse.model.settings.AbstractProcessSettings;
import org.eclipse.chemclipse.support.settings.IntSettingsProperty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import net.openchrom.xxd.process.supplier.templates.model.DetectorType;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;

public class PeakReviewDirectSettings extends AbstractProcessSettings implements IPeakIdentifierSettingsMSD, IPeakIdentifierSettingsCSD, IPeakIdentifierSettingsWSD {

	@JsonProperty(value = "Detector Type", defaultValue = "")
	@JsonPropertyDescription(value = "Select the default detector type.")
	private DetectorType detectorType = DetectorType.DEFAULT;

	@JsonProperty(value = "Delta Left [ms]", defaultValue = "")
	@JsonPropertyDescription(value = "Additionally display milliseconds left of the peak.")
	@IntSettingsProperty(minValue = PreferenceSupplier.MIN_DELTA_MILLISECONDS, maxValue = PreferenceSupplier.MAX_DELTA_MILLISECONDS)
	private int reviewDeltaLeft = 0;

	@JsonProperty(value = "Delta Right [ms]", defaultValue = "")
	@JsonPropertyDescription(value = "Additionally display milliseconds right of the peak.")
	@IntSettingsProperty(minValue = PreferenceSupplier.MIN_DELTA_MILLISECONDS, maxValue = PreferenceSupplier.MAX_DELTA_MILLISECONDS)
	private int reviewDeltaRight = 0;

	public DetectorType getDetectorType() {

		return detectorType;
	}

	public void setDetectorType(DetectorType detectorType) {

		this.detectorType = detectorType;
	}

	public int getReviewDeltaLeft() {

		return reviewDeltaLeft;
	}

	public void setReviewDeltaLeft(int reviewDeltaLeft) {

		this.reviewDeltaLeft = reviewDeltaLeft;
	}

	public int getReviewDeltaRight() {

		return reviewDeltaRight;
	}

	public void setReviewDeltaRight(int reviewDeltaRight) {

		this.reviewDeltaRight = reviewDeltaRight;
	}

	@Override
	public float getLimitMatchFactor() {

		return 100; // Not needed in Review UI [Direct]
	}

	@Override
	public void setLimitMatchFactor(float limitMatchFactor) {

		/*
		 * Not needed here.
		 */
	}
}
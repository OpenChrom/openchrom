/*******************************************************************************
 * Copyright (c) 2022, 2025 Lablicate GmbH.
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
package net.openchrom.xxd.classifier.supplier.ratios.settings;

import org.eclipse.chemclipse.processing.system.ISystemProcessSettings;
import org.eclipse.chemclipse.support.settings.FloatSettingsProperty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import net.openchrom.xxd.classifier.supplier.ratios.preferences.PreferenceSupplier;

public abstract class AbstractRatioExportSettings implements ISystemProcessSettings {

	@JsonProperty(value = "Allowed Deviation (OK)", defaultValue = "20.0f")
	@FloatSettingsProperty(minValue = PreferenceSupplier.MIN_DEVIATION, maxValue = PreferenceSupplier.MAX_DEVIATION)
	@JsonPropertyDescription(value = "Define the deviation that is allowed.")
	private float allowedDeviationOk = PreferenceSupplier.DEF_ALLOWED_DEVIATION_OK;
	@JsonProperty(value = "Allowed Deviation (WARN)", defaultValue = "40.0f")
	@FloatSettingsProperty(minValue = PreferenceSupplier.MIN_DEVIATION, maxValue = PreferenceSupplier.MAX_DEVIATION)
	@JsonPropertyDescription(value = "Define the deviation that creates a warning.")
	private float allowedDeviationWarn = PreferenceSupplier.DEF_ALLOWED_DEVIATION_WARN;

	public float getAllowedDeviationOk() {

		return allowedDeviationOk;
	}

	public void setAllowedDeviationOk(float allowedDeviationOk) {

		this.allowedDeviationOk = allowedDeviationOk;
	}

	public float getAllowedDeviationWarn() {

		return allowedDeviationWarn;
	}

	public void setAllowedDeviationWarn(float allowedDeviationWarn) {

		this.allowedDeviationWarn = allowedDeviationWarn;
	}
}
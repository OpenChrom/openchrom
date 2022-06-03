/*******************************************************************************
 * Copyright (c) 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.settings;

import org.eclipse.chemclipse.processing.system.ISystemProcessSettings;
import org.eclipse.chemclipse.support.settings.FloatSettingsProperty;
import org.eclipse.chemclipse.support.settings.IntSettingsProperty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import net.openchrom.xxd.classifier.supplier.ratios.preferences.PreferenceSupplier;

public class TraceRatioExportSettings implements ISystemProcessSettings {

	@JsonProperty(value = "Number Traces (0 = TIC)", defaultValue = "5")
	@IntSettingsProperty(minValue = PreferenceSupplier.MIN_NUMBER_TRACES, maxValue = PreferenceSupplier.MAX_NUMBER_TRACES)
	@JsonPropertyDescription(value = "Select the number of highest traces to be exported.")
	private int numberTraces = PreferenceSupplier.DEF_EXPORT_NUMBER_TRACES;
	@JsonProperty(value = "Allowed Deviation (OK)", defaultValue = "20.0f")
	@FloatSettingsProperty(minValue = PreferenceSupplier.MIN_DEVIATION, maxValue = PreferenceSupplier.MAX_DEVIATION)
	@JsonPropertyDescription(value = "Define the deviation that is allowed.")
	private float allowedDeviationOk = PreferenceSupplier.DEF_ALLOWED_DEVIATION_OK;
	@JsonProperty(value = "Allowed Deviation (WARN)", defaultValue = "40.0f")
	@FloatSettingsProperty(minValue = PreferenceSupplier.MIN_DEVIATION, maxValue = PreferenceSupplier.MAX_DEVIATION)
	@JsonPropertyDescription(value = "Define the deviation that creates a warning.")
	private float allowedDeviationWarn = PreferenceSupplier.DEF_ALLOWED_DEVIATION_WARN;

	public int getNumberTraces() {

		return numberTraces;
	}

	public void setNumberTraces(int numberTraces) {

		this.numberTraces = numberTraces;
	}

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
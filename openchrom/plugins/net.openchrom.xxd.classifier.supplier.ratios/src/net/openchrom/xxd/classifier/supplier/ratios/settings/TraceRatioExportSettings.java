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

import org.eclipse.chemclipse.support.settings.IntSettingsProperty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import net.openchrom.xxd.classifier.supplier.ratios.preferences.PreferenceSupplier;

public class TraceRatioExportSettings extends AbstractRatioExportSettings {

	@JsonProperty(value = "Number Traces (0 = TIC)", defaultValue = "5")
	@IntSettingsProperty(minValue = PreferenceSupplier.MIN_NUMBER_TRACES, maxValue = PreferenceSupplier.MAX_NUMBER_TRACES)
	@JsonPropertyDescription(value = "Select the number of highest traces to be exported.")
	private int numberTraces = PreferenceSupplier.DEF_EXPORT_NUMBER_TRACES;

	public int getNumberTraces() {

		return numberTraces;
	}

	public void setNumberTraces(int numberTraces) {

		this.numberTraces = numberTraces;
	}
}
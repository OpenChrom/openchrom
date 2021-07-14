/*******************************************************************************
 * Copyright (c) 2021 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.system;

import org.eclipse.chemclipse.processing.system.ISystemProcessSettings;
import org.eclipse.chemclipse.support.settings.IntSettingsProperty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;

public class IdentifierExportProcessSettings implements ISystemProcessSettings {

	@JsonProperty(value = "Number Traces (0 = TIC)", defaultValue = "0")
	@IntSettingsProperty(minValue = PreferenceSupplier.MIN_NUMBER_TRACES, maxValue = PreferenceSupplier.MAX_NUMBER_TRACES)
	@JsonPropertyDescription(value = "Select the number of highest traces to be exported.")
	private int numberTraces = 0;
	@JsonProperty(value = "Delta Left [ms]", defaultValue = "0")
	@IntSettingsProperty(minValue = PreferenceSupplier.MIN_DELTA_MILLISECONDS, maxValue = PreferenceSupplier.MAX_DELTA_MILLISECONDS)
	@JsonPropertyDescription(value = "Extend the left review range by the given value in milliseconds.")
	private int retentionTimeDeltaLeft = 0;
	@JsonProperty(value = "Delta Right [ms]", defaultValue = "0")
	@IntSettingsProperty(minValue = PreferenceSupplier.MIN_DELTA_MILLISECONDS, maxValue = PreferenceSupplier.MAX_DELTA_MILLISECONDS)
	@JsonPropertyDescription(value = "Extend the right review range by the given value in milliseconds.")
	private int retentionTimeDeltaRight = 0;

	public int getNumberTraces() {

		return numberTraces;
	}

	public void setNumberTraces(int numberTraces) {

		this.numberTraces = numberTraces;
	}

	public int getRetentionTimeDeltaLeft() {

		return retentionTimeDeltaLeft;
	}

	public void setRetentionTimeDeltaLeft(int retentionTimeDeltaLeft) {

		this.retentionTimeDeltaLeft = retentionTimeDeltaLeft;
	}

	public int getRetentionTimeDeltaRight() {

		return retentionTimeDeltaRight;
	}

	public void setRetentionTimeDeltaRight(int retentionTimeDeltaRight) {

		this.retentionTimeDeltaRight = retentionTimeDeltaRight;
	}
}
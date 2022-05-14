/*******************************************************************************
 * Copyright (c) 2021, 2022 Lablicate GmbH.
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
import org.eclipse.chemclipse.support.settings.DoubleSettingsProperty;
import org.eclipse.chemclipse.support.settings.IntSettingsProperty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import net.openchrom.xxd.process.supplier.templates.model.PositionDirective;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;

public class DetectorExportProcessSettings implements ISystemProcessSettings {

	@JsonProperty(value = "Number Traces (0 = TIC)", defaultValue = "0")
	@IntSettingsProperty(minValue = PreferenceSupplier.MIN_NUMBER_TRACES, maxValue = PreferenceSupplier.MAX_NUMBER_TRACES)
	@JsonPropertyDescription(value = "Select the number of highest traces to be exported.")
	private int numberTraces = 0;
	@JsonProperty(value = "Export Optimize Range", defaultValue = "true")
	@JsonPropertyDescription(value = "Try to optimize the range when running a manual peak detection.")
	private boolean optimizeRange = true;
	@JsonProperty(value = "Delta Left", defaultValue = "0")
	@DoubleSettingsProperty(minValue = PreferenceSupplier.MIN_DELTA_POSITION, maxValue = PreferenceSupplier.MAX_DELTA_POSITION)
	@JsonPropertyDescription(value = "Extend the left review range by the given value.")
	private double positionDeltaLeft = 0;
	@JsonProperty(value = "Delta Right", defaultValue = "0")
	@DoubleSettingsProperty(minValue = PreferenceSupplier.MIN_DELTA_POSITION, maxValue = PreferenceSupplier.MAX_DELTA_POSITION)
	@JsonPropertyDescription(value = "Extend the right review range by the given value.")
	private double positionDeltaRight = 0;
	@JsonProperty(value = "Position Directive", defaultValue = "RETENTION_TIME_MIN")
	@JsonPropertyDescription(value = "Select whether to use Minutes, Milliseconds or Retention Index.")
	private PositionDirective positionDirective = PositionDirective.RETENTION_TIME_MIN;

	public int getNumberTraces() {

		return numberTraces;
	}

	public void setNumberTraces(int numberTraces) {

		this.numberTraces = numberTraces;
	}

	public boolean isOptimizeRange() {

		return optimizeRange;
	}

	public void setOptimizeRange(boolean optimizeRange) {

		this.optimizeRange = optimizeRange;
	}

	public double getPositionDeltaLeft() {

		return positionDeltaLeft;
	}

	public void setPositionDeltaLeft(double positionDeltaLeft) {

		this.positionDeltaLeft = positionDeltaLeft;
	}

	public double getPositionDeltaRight() {

		return positionDeltaRight;
	}

	public void setPositionDeltaRight(double positionDeltaRight) {

		this.positionDeltaRight = positionDeltaRight;
	}

	public PositionDirective getPositionDirective() {

		return positionDirective;
	}

	public void setPositionDirective(PositionDirective positionDirective) {

		this.positionDirective = positionDirective;
	}
}
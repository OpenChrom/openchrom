/*******************************************************************************
 * Copyright (c) 2021, 2026 Lablicate GmbH.
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
package net.openchrom.xxd.process.supplier.templates.system;

import org.eclipse.chemclipse.processing.system.ISystemProcessSettings;
import org.eclipse.chemclipse.support.settings.DoubleSettingsProperty;
import org.eclipse.chemclipse.support.settings.IntSettingsProperty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import net.openchrom.xxd.process.supplier.templates.model.PositionDirective;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;

public class IdentifierExportProcessSettings implements ISystemProcessSettings {

	@JsonProperty(value = "Number Traces (0 = TIC)", defaultValue = "0")
	@IntSettingsProperty(minValue = PreferenceSupplier.MIN_NUMBER_TRACES, maxValue = PreferenceSupplier.MAX_NUMBER_TRACES)
	@JsonPropertyDescription(value = "Select the number of highest traces to be exported.")
	private int numberTraces = 0;
	@JsonProperty(value = "Position Directive (Coordinate)", defaultValue = "RETENTION_TIME_MIN")
	@JsonPropertyDescription(value = "Select whether to use Minutes, Milliseconds or Retention Index.")
	private PositionDirective positionDirective = PositionDirective.RETENTION_TIME_MIN;
	@JsonProperty(value = "Delta Left [Coordinate]", defaultValue = "0")
	@DoubleSettingsProperty(minValue = PreferenceSupplier.MIN_DELTA_COORDINATE, maxValue = PreferenceSupplier.MAX_DELTA_COORDINATE)
	@JsonPropertyDescription(value = "Extend the left review range by the given coordinate value.")
	private double coordinateDeltaLeft = 0;
	@JsonProperty(value = "Delta Right [Coordinate]", defaultValue = "0")
	@DoubleSettingsProperty(minValue = PreferenceSupplier.MIN_DELTA_COORDINATE, maxValue = PreferenceSupplier.MAX_DELTA_COORDINATE)
	@JsonPropertyDescription(value = "Extend the right review range by the given coordinate value.")
	private double coordinateDeltaRight = 0;

	public int getNumberTraces() {

		return numberTraces;
	}

	public void setNumberTraces(int numberTraces) {

		this.numberTraces = numberTraces;
	}

	public PositionDirective getPositionDirective() {

		return positionDirective;
	}

	public void setPositionDirective(PositionDirective positionDirective) {

		this.positionDirective = positionDirective;
	}

	public double getCoordinateDeltaLeft() {

		return coordinateDeltaLeft;
	}

	public void setCoordinateDeltaLeft(double coordinateDeltaLeft) {

		this.coordinateDeltaLeft = coordinateDeltaLeft;
	}

	public double getCoordinateDeltaRight() {

		return coordinateDeltaRight;
	}

	public void setCoordinateDeltaRight(double coordinateDeltaRight) {

		this.coordinateDeltaRight = coordinateDeltaRight;
	}
}
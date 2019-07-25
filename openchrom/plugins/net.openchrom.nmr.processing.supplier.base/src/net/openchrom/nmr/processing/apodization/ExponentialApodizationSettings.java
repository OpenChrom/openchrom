/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexander Stark - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.processing.apodization;

import org.eclipse.chemclipse.support.settings.DoubleSettingsProperty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExponentialApodizationSettings {

	@JsonProperty(value = "Exponential Line Broadening Factor", defaultValue = "0.0")
	@DoubleSettingsProperty()
	private double exponentialLineBroadeningFactor = 0;

	public ExponentialApodizationSettings() {
	}

	public double getExponentialLineBroadeningFactor() {

		return exponentialLineBroadeningFactor;
	}

	public void setExponentialLineBroadeningFactor(double exponentialLineBroadeningFactor) {

		this.exponentialLineBroadeningFactor = exponentialLineBroadeningFactor;
	}
}

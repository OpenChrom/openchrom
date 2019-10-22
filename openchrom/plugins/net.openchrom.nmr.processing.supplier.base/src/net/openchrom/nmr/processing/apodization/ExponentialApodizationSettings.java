/*******************************************************************************
 * Copyright (c) 2018, 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexander Stark - initial API and implementation
 * Alexander Kerner - implementation
 *******************************************************************************/
package net.openchrom.nmr.processing.apodization;

import org.eclipse.chemclipse.support.settings.DoubleSettingsProperty;
import org.eclipse.chemclipse.support.settings.SystemSettings;
import org.eclipse.chemclipse.support.settings.SystemSettingsStrategy;

import com.fasterxml.jackson.annotation.JsonProperty;

@SystemSettings(SystemSettingsStrategy.NEW_INSTANCE)
public class ExponentialApodizationSettings {

	public static ExponentialApodizationSettings build(String exponentialLineBroadeningFactorString) {

		ExponentialApodizationSettings settings = new ExponentialApodizationSettings();
		if(exponentialLineBroadeningFactorString != null) {
			double exponentialLineBroadeningFactor = Double.parseDouble(exponentialLineBroadeningFactorString);
			settings.setExponentialLineBroadeningFactor(exponentialLineBroadeningFactor);
		}
		return settings;
	}

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

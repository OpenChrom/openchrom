/*******************************************************************************
 * Copyright (c) 2019, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.settings;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.chromatogram.xxd.integrator.core.settings.peaks.AbstractPeakIntegrationSettings;
import org.eclipse.chemclipse.support.settings.StringSettingsProperty;
import org.eclipse.core.runtime.IStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import net.openchrom.xxd.process.supplier.templates.model.IntegratorSetting;
import net.openchrom.xxd.process.supplier.templates.model.IntegratorSettings;
import net.openchrom.xxd.process.supplier.templates.util.PeakIntegratorListUtil;
import net.openchrom.xxd.process.supplier.templates.util.PeakIntegratorValidator;

public class PeakIntegrationSettings extends AbstractPeakIntegrationSettings implements ITemplateSettings {

	/*
	 * 10.52 | 10.63 | Styrene | Trapezoid
	 */
	@JsonProperty(value = "Integrator Settings", defaultValue = "")
	@JsonPropertyDescription(value = "Example: '" + PeakIntegratorListUtil.EXAMPLE_SINGLE + "'")
	@StringSettingsProperty(regExp = RE_START + //
			RE_NUMBER + // Start RT
			RE_SEPARATOR + //
			RE_NUMBER + // Stop RT
			RE_SEPARATOR + //
			RE_TEXT + // Name
			RE_SEPARATOR + //
			RE_INTEGRATOR_TYPE, // Type
			isMultiLine = true)
	private String integratorSettings = "";

	public void setIntegrationSettings(String integratorSettings) {

		this.integratorSettings = integratorSettings;
	}

	@JsonIgnore
	public void setIntegrationSettings(List<IntegratorSetting> integratorSettings) {

		IntegratorSettings settings = new IntegratorSettings();
		this.integratorSettings = settings.extractSettings(integratorSettings);
	}

	public String getIntegratorSettings() {

		return integratorSettings;
	}

	@JsonIgnore
	public List<IntegratorSetting> getIntegrationSettingsList() {

		PeakIntegratorListUtil util = new PeakIntegratorListUtil();
		PeakIntegratorValidator validator = new PeakIntegratorValidator();
		List<IntegratorSetting> settings = new ArrayList<>();
		//
		List<String> items = util.getList(integratorSettings);
		for(String item : items) {
			IStatus status = validator.validate(item);
			if(status.isOK()) {
				settings.add(validator.getSetting());
			}
		}
		//
		return settings;
	}
}

/*******************************************************************************
 * Copyright (c) 2019, 2025 Lablicate GmbH.
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

import java.util.List;

import org.eclipse.chemclipse.chromatogram.xxd.classifier.settings.AbstractChromatogramClassifierSettings;
import org.eclipse.chemclipse.support.settings.StringSettingsProperty;
import org.eclipse.core.runtime.IStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import net.openchrom.xxd.classifier.supplier.ratios.model.time.TimeRatio;
import net.openchrom.xxd.classifier.supplier.ratios.model.time.TimeRatios;
import net.openchrom.xxd.classifier.supplier.ratios.util.time.TimeRatioListUtil;
import net.openchrom.xxd.classifier.supplier.ratios.util.time.TimeRatioValidator;

public class TimeRatioSettings extends AbstractChromatogramClassifierSettings implements ITemplateSettings {

	/*
	 * Naphthalin | 3.45 | 5.0 | 15.0
	 */
	@JsonProperty(value = "Time Ratio Settings", defaultValue = "")
	@JsonPropertyDescription(value = "Example: '" + TimeRatioListUtil.EXAMPLE_SINGLE + "'")
	@StringSettingsProperty(regExp = RE_START + //
			RE_TEXT + // Identifier
			RE_SEPARATOR + //
			RE_NUMBER + // Expected Retention Time
			RE_SEPARATOR + //
			RE_NUMBER + // Limit Warn
			RE_SEPARATOR + //
			RE_NUMBER, // Limit Error
			isMultiLine = true)
	private String ratioSettings = "";

	public void setRatioSettings(String ratioSettings) {

		this.ratioSettings = ratioSettings;
	}

	@JsonIgnore
	public void setRatioSettings(List<TimeRatio> ratioSettings) {

		TimeRatios settings = new TimeRatios();
		this.ratioSettings = settings.extractSettings(ratioSettings);
	}

	public String getRatioSettings() {

		return ratioSettings;
	}

	@JsonIgnore
	public TimeRatios getRatioSettingsList() {

		TimeRatioListUtil util = new TimeRatioListUtil();
		TimeRatioValidator validator = new TimeRatioValidator();
		TimeRatios ratios = new TimeRatios();

		List<String> items = util.getList(ratioSettings);
		for(String item : items) {
			IStatus status = validator.validate(item);
			if(status.isOK()) {
				ratios.add(validator.getSetting());
			}
		}

		return ratios;
	}
}
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

import net.openchrom.xxd.classifier.supplier.ratios.model.trace.TraceRatio;
import net.openchrom.xxd.classifier.supplier.ratios.model.trace.TraceRatios;
import net.openchrom.xxd.classifier.supplier.ratios.util.trace.TraceRatioListUtil;
import net.openchrom.xxd.classifier.supplier.ratios.util.trace.TraceRatioValidator;

public class TraceRatioSettings extends AbstractChromatogramClassifierSettings implements ITemplateSettings {

	/*
	 * Naphthalin | 128:127 | 14.6 | 5.0 | 15.0
	 */
	@JsonProperty(value = "Trace Ratio Settings", defaultValue = "")
	@JsonPropertyDescription(value = "Example: '" + TraceRatioListUtil.EXAMPLE_SINGLE + "'")
	@StringSettingsProperty(regExp = RE_START + //
			RE_TEXT + // Identifier
			RE_SEPARATOR + //
			RE_TRACE_PATTERN + // Test Case
			RE_SEPARATOR + //
			RE_NUMBER + // Expected Ratio
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
	public void setRatioSettings(List<TraceRatio> ratioSettings) {

		TraceRatios settings = new TraceRatios();
		this.ratioSettings = settings.extractSettings(ratioSettings);
	}

	public String getRatioSettings() {

		return ratioSettings;
	}

	@JsonIgnore
	public TraceRatios getRatioSettingsList() {

		TraceRatioListUtil util = new TraceRatioListUtil();
		TraceRatioValidator validator = new TraceRatioValidator();
		TraceRatios ratios = new TraceRatios();

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
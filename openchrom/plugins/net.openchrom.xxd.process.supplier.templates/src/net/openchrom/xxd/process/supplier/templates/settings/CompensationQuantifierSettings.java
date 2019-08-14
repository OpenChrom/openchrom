/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
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

import org.eclipse.chemclipse.chromatogram.msd.quantitation.settings.AbstractPeakQuantifierSettings;
import org.eclipse.chemclipse.support.settings.StringSettingsProperty;
import org.eclipse.core.runtime.IStatus;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import net.openchrom.xxd.process.supplier.templates.model.CompensationSetting;
import net.openchrom.xxd.process.supplier.templates.model.CompensationSettings;
import net.openchrom.xxd.process.supplier.templates.util.CompensationQuantListUtil;
import net.openchrom.xxd.process.supplier.templates.util.CompensationQuantValidator;

public class CompensationQuantifierSettings extends AbstractPeakQuantifierSettings implements ITemplateSettings {

	public static final String DESCRIPTION = "Template Compensation Quantifier";
	/*
	 * Substance A | Styrene | 1.0 | mg/L | false
	 */
	@JsonProperty(value = "Compensation Settings", defaultValue = "")
	@JsonPropertyDescription(value = "Example: '" + CompensationQuantListUtil.EXAMPLE_SINGLE + "'")
	@StringSettingsProperty(regExp = RE_START + //
			RE_TEXT + // Name
			RE_SEPARATOR + //
			RE_TEXT + // ISTD
			RE_SEPARATOR + //
			RE_NUMBER + // Concentration
			RE_SEPARATOR + //
			RE_TEXT + // Unit
			RE_SEPARATOR + //
			RE_FLAG, // Adjust Quantitation Entry
			isMultiLine = true)
	private String compensationSettings = "";

	public void setCompensationSettings(String compensationSettings) {

		this.compensationSettings = compensationSettings;
	}

	public void setCompensationSettings(List<CompensationSetting> compensationSetting) {

		CompensationSettings settings = new CompensationSettings();
		this.compensationSettings = settings.extractSettings(compensationSetting);
	}

	public List<CompensationSetting> getCompensationSettings() {

		CompensationQuantListUtil util = new CompensationQuantListUtil();
		CompensationQuantValidator validator = new CompensationQuantValidator();
		List<CompensationSetting> settings = new ArrayList<>();
		//
		List<String> items = util.getList(compensationSettings);
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

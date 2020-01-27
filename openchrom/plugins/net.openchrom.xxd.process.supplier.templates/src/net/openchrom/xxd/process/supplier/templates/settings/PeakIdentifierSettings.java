/*******************************************************************************
 * Copyright (c) 2018, 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Christoph Läubrich - adjust method names
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.settings;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.chromatogram.csd.identifier.settings.IPeakIdentifierSettingsCSD;
import org.eclipse.chemclipse.chromatogram.msd.identifier.settings.IPeakIdentifierSettingsMSD;
import org.eclipse.chemclipse.model.identifier.AbstractIdentifierSettings;
import org.eclipse.chemclipse.msd.model.core.support.IMarkedIons;
import org.eclipse.chemclipse.support.settings.StringSettingsProperty;
import org.eclipse.core.runtime.IStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import net.openchrom.xxd.process.supplier.templates.model.IdentifierSetting;
import net.openchrom.xxd.process.supplier.templates.model.IdentifierSettings;
import net.openchrom.xxd.process.supplier.templates.util.PeakIdentifierListUtil;
import net.openchrom.xxd.process.supplier.templates.util.PeakIdentifierValidator;

public class PeakIdentifierSettings extends AbstractIdentifierSettings implements IPeakIdentifierSettingsMSD, IPeakIdentifierSettingsCSD, ITemplateSettings {

	public static final String DESCRIPTION = "Template Peak Identifier";
	/*
	 * 10.52 | 10.63 | Styrene | 100-42-5 | comment | contributor | referenceId | 103, 104
	 */
	@JsonProperty(value = "Identifier Settings", defaultValue = "")
	@JsonPropertyDescription(value = "Example: '" + PeakIdentifierListUtil.EXAMPLE_SINGLE + "'")
	@StringSettingsProperty(regExp = RE_START + //
			RE_NUMBER + // Start RT
			RE_SEPARATOR + //
			RE_NUMBER + // Stop RT
			RE_SEPARATOR + //
			RE_TEXT + // Substance
			RE_SEPARATOR + //
			RE_TEXT + // CAS
			RE_SEPARATOR + //
			RE_TEXT + // Comment
			RE_SEPARATOR + //
			RE_TEXT + // Contributor
			RE_SEPARATOR + //
			RE_TEXT + // Reference
			RE_SEPARATOR + //
			RE_TRACES + // Traces
			RE_SEPARATOR + //
			RE_TEXT, // Reference Identifier
			isMultiLine = true)
	private String identifierSettings = "";

	public void setIdentifierSettings(String identifierSettings) {

		this.identifierSettings = identifierSettings;
	}

	@JsonIgnore
	public void setIdentifierSettings(List<IdentifierSetting> identifierSettings) {

		IdentifierSettings settings = new IdentifierSettings();
		this.identifierSettings = settings.extractSettings(identifierSettings);
	}

	public String getIdentifierSettings() {

		return identifierSettings;
	}

	@JsonIgnore
	public List<IdentifierSetting> getIdentifierSettingsList() {

		PeakIdentifierListUtil util = new PeakIdentifierListUtil();
		PeakIdentifierValidator validator = new PeakIdentifierValidator();
		List<IdentifierSetting> settings = new ArrayList<>();
		//
		List<String> items = util.getList(identifierSettings);
		for(String item : items) {
			IStatus status = validator.validate(item);
			if(status.isOK()) {
				settings.add(validator.getSetting());
			}
		}
		//
		return settings;
	}

	@JsonIgnore
	@Override
	public String getMassSpectrumComparatorId() {

		return "not needed here";
	}

	@Override
	public void setMassSpectrumComparatorId(String massSpectrumComparatorId) {

	}

	@JsonIgnore
	@Override
	public IMarkedIons getExcludedIons() {

		return null;
	}
}

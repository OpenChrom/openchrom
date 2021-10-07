/*******************************************************************************
 * Copyright (c) 2020, 2021 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.settings;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.chromatogram.csd.identifier.settings.IPeakIdentifierSettingsCSD;
import org.eclipse.chemclipse.chromatogram.msd.identifier.settings.IPeakIdentifierSettingsMSD;
import org.eclipse.chemclipse.model.identifier.IdentifierAdapterSettings;
import org.eclipse.chemclipse.msd.model.core.support.IMarkedIons;
import org.eclipse.chemclipse.support.settings.StringSettingsProperty;
import org.eclipse.core.runtime.IStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import net.openchrom.xxd.process.supplier.templates.model.ReviewSetting;
import net.openchrom.xxd.process.supplier.templates.model.ReviewSettings;
import net.openchrom.xxd.process.supplier.templates.util.ReviewListUtil;
import net.openchrom.xxd.process.supplier.templates.util.ReviewValidator;

public class PeakReviewSettings extends IdentifierAdapterSettings implements IPeakIdentifierSettingsMSD, IPeakIdentifierSettingsCSD, ITemplateSettings {

	public static final String DESCRIPTION = "Template Review";
	/*
	 * 10.52 | 10.63 | Styrene | 100-42-5 | 103, 104
	 */
	@JsonProperty(value = "Review Settings", defaultValue = "")
	@JsonPropertyDescription(value = "Example: '" + ReviewListUtil.EXAMPLE_SINGLE + "'")
	@StringSettingsProperty(regExp = RE_START + //
			RE_NUMBER + // Start RT
			RE_SEPARATOR + //
			RE_NUMBER + // Stop RT
			RE_SEPARATOR + //
			RE_TEXT + // Substance
			RE_SEPARATOR + //
			RE_TEXT + // CAS
			RE_SEPARATOR + //
			RE_TRACES, // Traces
			isMultiLine = true)
	private String reviewSettings = "";

	public void setReviewSettings(String reviewSettings) {

		this.reviewSettings = reviewSettings;
	}

	public String getReviewSettings() {

		return reviewSettings;
	}

	@JsonIgnore
	public void setReviewSettings(List<ReviewSetting> reviewSettings) {

		ReviewSettings settings = new ReviewSettings();
		this.reviewSettings = settings.extractSettings(reviewSettings);
	}

	@JsonIgnore
	public List<ReviewSetting> getReviewSettingsList() {

		ReviewListUtil util = new ReviewListUtil();
		ReviewValidator validator = new ReviewValidator();
		List<ReviewSetting> settings = new ArrayList<>();
		//
		List<String> items = util.getList(reviewSettings);
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

/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
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

import org.eclipse.chemclipse.chromatogram.xxd.report.settings.DefaultChromatogramReportSettings;
import org.eclipse.chemclipse.support.settings.StringSettingsProperty;
import org.eclipse.core.runtime.IStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import net.openchrom.xxd.process.supplier.templates.model.ReportSetting;
import net.openchrom.xxd.process.supplier.templates.model.ReportSettings;
import net.openchrom.xxd.process.supplier.templates.util.ReportListUtil;
import net.openchrom.xxd.process.supplier.templates.util.ReportValidator;

public class ChromatogramReportSettings extends DefaultChromatogramReportSettings implements ITemplateSettings {

	public static final String DESCRIPTION = "Template Report";
	//
	@JsonProperty(value = "Report referenced chromatogram(s)", defaultValue = "false")
	private boolean reportReferencedChromatograms;
	/*
	 * 10.52 | 10.63 | Styrene | 100-42-5
	 */
	@JsonProperty(value = "Report Settings", defaultValue = "")
	@JsonPropertyDescription(value = "Example: '" + ReportListUtil.EXAMPLE_SINGLE + "'")
	@StringSettingsProperty(regExp = RE_START + //
			RE_NUMBER + // Start RT
			RE_SEPARATOR + //
			RE_NUMBER + // Stop RT
			RE_SEPARATOR + //
			RE_TEXT + // Substance
			RE_SEPARATOR + //
			RE_TEXT + // CAS
			RE_SEPARATOR + //
			RE_TEXT, // Report Strategy
			isMultiLine = true)
	private String reportSettings = "";

	public boolean isReportReferencedChromatograms() {

		return reportReferencedChromatograms;
	}

	public void setReportReferencedChromatograms(boolean reportReferencedChromatograms) {

		this.reportReferencedChromatograms = reportReferencedChromatograms;
	}

	public void setReportSettings(String reportSettings) {

		this.reportSettings = reportSettings;
	}

	@JsonIgnore
	public void setReportSettings(List<ReportSetting> reportSettings) {

		ReportSettings settings = new ReportSettings();
		this.reportSettings = settings.extractSettings(reportSettings);
	}

	public String getReportSettings() {

		return reportSettings;
	}

	@JsonIgnore
	public List<ReportSetting> getReportSettingsList() {

		ReportListUtil util = new ReportListUtil();
		ReportValidator validator = new ReportValidator();
		List<ReportSetting> settings = new ArrayList<>();
		//
		List<String> items = util.getList(reportSettings);
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

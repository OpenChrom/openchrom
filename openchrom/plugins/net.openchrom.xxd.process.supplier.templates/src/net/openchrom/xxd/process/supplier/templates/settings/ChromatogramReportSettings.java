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
	//
	@JsonProperty(value = "Print Header", defaultValue = "true")
	private boolean printHeader = true;
	@JsonProperty(value = "Print Summary", defaultValue = "true")
	private boolean printSummary = true;
	@JsonProperty(value = "Print Columns", defaultValue = "")
	@JsonPropertyDescription(value = "List the columns to print, e.g. 'Name','Start Time [min]'. Leave this value empty to print all columns.")
	private String printColumns = "";
	@JsonProperty(value = "Report referenced chromatogram(s)", defaultValue = "false")
	private boolean reportReferencedChromatograms = false;

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

	public boolean isPrintHeader() {

		return printHeader;
	}

	public void setPrintHeader(boolean printHeader) {

		this.printHeader = printHeader;
	}

	public boolean isPrintSummary() {

		return printSummary;
	}

	public void setPrintSummary(boolean printSummary) {

		this.printSummary = printSummary;
	}

	public String getPrintColumns() {

		return printColumns;
	}

	public void setPrintColumns(String printColumns) {

		this.printColumns = printColumns;
	}

	public boolean isReportReferencedChromatograms() {

		return reportReferencedChromatograms;
	}

	public void setReportReferencedChromatograms(boolean reportReferencedChromatograms) {

		this.reportReferencedChromatograms = reportReferencedChromatograms;
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

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

import org.eclipse.chemclipse.chromatogram.xxd.report.settings.DefaultChromatogramReportSettings;
import org.eclipse.chemclipse.support.settings.ValidatorSettingsProperty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import net.openchrom.xxd.process.supplier.templates.model.ReportColumns;
import net.openchrom.xxd.process.supplier.templates.model.ReportSettings;
import net.openchrom.xxd.process.supplier.templates.util.ReportColumnsValidator;
import net.openchrom.xxd.process.supplier.templates.util.ReportSettingsValidator;

public class ChromatogramReportSettings extends DefaultChromatogramReportSettings implements ITemplateSettings {

	public static final String DESCRIPTION = "Template Report";
	//
	@JsonProperty(value = "Print Chromatogram Header", defaultValue = "true")
	@JsonPropertyDescription(value = "The chromatogram header data is printed on top.")
	private boolean printHeader = true;
	@JsonProperty(value = "Print Results Header", defaultValue = "true")
	@JsonPropertyDescription(value = "Print the column description of each peak report column.")
	private boolean printResultsHeader = true;
	@JsonProperty(value = "Results Header (Append)", defaultValue = "true")
	@JsonPropertyDescription(value = "Repeat the report header if the file is appended.")
	private boolean appendResultsHeader = true;
	@JsonProperty(value = "Print Summary", defaultValue = "true")
	private boolean printSummary = true;
	@JsonProperty(value = "Report Settings", defaultValue = "")
	@JsonPropertyDescription(value = "List the peaks and strategy here.")
	@ValidatorSettingsProperty(validator = ReportSettingsValidator.class)
	private ReportSettings reportSettings;
	@JsonProperty(value = "Print Columns", defaultValue = "")
	@JsonPropertyDescription(value = "Select the report columns.")
	@ValidatorSettingsProperty(validator = ReportColumnsValidator.class)
	private ReportColumns reportColumns;
	@JsonProperty(value = "Use Reference(s)", defaultValue = "false")
	@JsonPropertyDescription(value = "Report all referenced chromatograms.")
	private boolean reportReferencedChromatograms = false;

	public boolean isPrintHeader() {

		return printHeader;
	}

	public void setPrintHeader(boolean printHeader) {

		this.printHeader = printHeader;
	}

	public boolean isPrintResultsHeader() {

		return printResultsHeader;
	}

	public void setPrintResultsHeader(boolean printResultsHeader) {

		this.printResultsHeader = printResultsHeader;
	}

	public boolean isAppendResultsHeader() {

		return appendResultsHeader;
	}

	public void setAppendResultsHeader(boolean appendResultsHeader) {

		this.appendResultsHeader = appendResultsHeader;
	}

	public boolean isPrintSummary() {

		return printSummary;
	}

	public void setPrintSummary(boolean printSummary) {

		this.printSummary = printSummary;
	}

	public ReportSettings getReportSettings() {

		return reportSettings;
	}

	public void setReportSettings(ReportSettings reportSettings) {

		this.reportSettings = reportSettings;
	}

	public ReportColumns getReportColumns() {

		return reportColumns;
	}

	public void setReportColumns(ReportColumns reportColumns) {

		this.reportColumns = reportColumns;
	}

	public boolean isReportReferencedChromatograms() {

		return reportReferencedChromatograms;
	}

	public void setReportReferencedChromatograms(boolean reportReferencedChromatograms) {

		this.reportReferencedChromatograms = reportReferencedChromatograms;
	}
}

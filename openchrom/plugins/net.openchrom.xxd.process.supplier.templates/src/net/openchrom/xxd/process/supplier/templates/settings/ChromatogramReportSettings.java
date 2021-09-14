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

import net.openchrom.xxd.process.supplier.templates.model.ReportSettings;
import net.openchrom.xxd.process.supplier.templates.util.ReportSettingsValidator;

public class ChromatogramReportSettings extends DefaultChromatogramReportSettings implements ITemplateSettings {

	public static final String DESCRIPTION = "Template Report";
	//
	@JsonProperty(value = "Report Settings", defaultValue = "")
	@JsonPropertyDescription(value = "List the peaks and strategy here.")
	@ValidatorSettingsProperty(validator = ReportSettingsValidator.class)
	private ReportSettings reportSettings;
	@JsonProperty(value = "Print Header", defaultValue = "true")
	private boolean printHeader = true;
	@JsonProperty(value = "Print Summary", defaultValue = "true")
	private boolean printSummary = true;
	@JsonProperty(value = "Print Columns", defaultValue = "")
	@JsonPropertyDescription(value = "List the columns to print, e.g. 'Name','Start Time [min]'. Leave this value empty to print all columns.")
	private String printColumns = "";
	@JsonProperty(value = "Report referenced chromatogram(s)", defaultValue = "false")
	private boolean reportReferencedChromatograms = false;

	public ReportSettings getReportSettings() {

		return reportSettings;
	}

	public void setReportSettings(ReportSettings reportSettings) {

		this.reportSettings = reportSettings;
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
}

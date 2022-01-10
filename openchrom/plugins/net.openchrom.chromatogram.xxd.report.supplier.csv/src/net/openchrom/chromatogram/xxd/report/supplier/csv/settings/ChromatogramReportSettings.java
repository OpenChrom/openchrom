/*******************************************************************************
 * Copyright (c) 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.xxd.report.supplier.csv.settings;

import org.apache.commons.csv.CSVFormat;
import org.eclipse.chemclipse.chromatogram.xxd.report.settings.DefaultChromatogramReportSettings;
import org.eclipse.chemclipse.support.settings.ValidatorSettingsProperty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import net.openchrom.chromatogram.xxd.report.supplier.csv.model.Delimiter;
import net.openchrom.chromatogram.xxd.report.supplier.csv.model.ReportColumns;
import net.openchrom.chromatogram.xxd.report.supplier.csv.validators.ReportColumnsValidator;

public class ChromatogramReportSettings extends DefaultChromatogramReportSettings {

	public static final String DESCRIPTION = "CSV Report";
	//
	@JsonProperty(value = "Delimiter", defaultValue = "COMMA")
	@JsonPropertyDescription(value = "Select the column delimiter.")
	private Delimiter delimiter = Delimiter.COMMA;
	//
	@JsonProperty(value = "Columns", defaultValue = "")
	@JsonPropertyDescription(value = "Select the report columns.")
	@ValidatorSettingsProperty(validator = ReportColumnsValidator.class)
	private ReportColumns reportColumns;
	//
	@JsonProperty(value = "References", defaultValue = "false")
	@JsonPropertyDescription(value = "Report all referenced chromatograms.")
	private boolean reportReferencedChromatograms = false;

	public CSVFormat getFormat() {

		return CSVFormat.RFC4180.withDelimiter(delimiter.getCharacter());
	}

	public Delimiter getDelimiter() {

		return delimiter;
	}

	public void setDelimiter(Delimiter delimiter) {

		this.delimiter = delimiter;
	}

	public ReportColumns getReportColumns() {

		return reportColumns;
	}

	public void setReportColumns(ReportColumns reportColumns) {

		this.reportColumns = reportColumns;
	}

	public boolean reportReferencedChromatograms() {

		return reportReferencedChromatograms;
	}

	public void setReportReferencedChromatograms(boolean reportReferencedChromatograms) {

		this.reportReferencedChromatograms = reportReferencedChromatograms;
	}
}

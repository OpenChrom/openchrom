/*******************************************************************************
 * Copyright (c) 2024, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.xxd.report.supplier.excel.template.settings;

import java.io.File;

import org.eclipse.chemclipse.chromatogram.xxd.report.settings.DefaultChromatogramReportSettings;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChromatogramReportSettings extends DefaultChromatogramReportSettings {

	@JsonProperty(value = "Excel Template", defaultValue = ".xltx")
	private File template = null;

	public File getTemplate() {

		return template;
	}

	public void setTemplate(File template) {

		this.template = template;
	}
}
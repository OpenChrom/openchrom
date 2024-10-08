/*******************************************************************************
 * Copyright (c) 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
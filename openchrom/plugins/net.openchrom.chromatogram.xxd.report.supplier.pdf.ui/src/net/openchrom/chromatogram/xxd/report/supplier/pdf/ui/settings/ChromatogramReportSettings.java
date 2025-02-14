/*******************************************************************************
 * Copyright (c) 2025 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.xxd.report.supplier.pdf.ui.settings;

import org.eclipse.chemclipse.chromatogram.xxd.report.settings.DefaultChromatogramReportSettings;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import net.openchrom.chromatogram.xxd.report.supplier.pdf.ui.preferences.PreferenceSupplier;

public class ChromatogramReportSettings extends DefaultChromatogramReportSettings {

	@JsonPropertyDescription(value = "Distribute the chromatogram on to this many images each on a separate page. Zero to turn off.")
	@JsonProperty(value = "Images per Chromatogram", defaultValue = "" + PreferenceSupplier.DEF_NUMBER_IMAGE_PAGES)
	private int numberOfImagesPerPage = 5;

	public int getNumberOfImagesPerPage() {

		return numberOfImagesPerPage;
	}
}
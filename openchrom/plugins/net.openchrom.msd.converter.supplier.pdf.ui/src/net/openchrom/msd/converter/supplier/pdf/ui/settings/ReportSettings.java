/*******************************************************************************
 * Copyright (c) 2018, 2019 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Christoph Läubrich - enhance configuration, align with new API
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.pdf.ui.settings;

import java.io.File;

import org.eclipse.chemclipse.chromatogram.xxd.report.settings.AbstractChromatogramReportSettings;
import org.eclipse.chemclipse.support.settings.FileSettingProperty;
import org.eclipse.chemclipse.support.settings.IntSettingsProperty;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.openchrom.msd.converter.supplier.pdf.preferences.PreferenceSupplier;

public class ReportSettings extends AbstractChromatogramReportSettings {

	@IntSettingsProperty(maxValue = PreferenceSupplier.MAX_NUMBER_IMAGE_PAGES, minValue = PreferenceSupplier.MIN_NUMBER_IMAGE_PAGES)
	@JsonProperty(value = "Number Images per Pages", defaultValue = "" + PreferenceSupplier.DEF_NUMBER_IMAGE_PAGES)
	private int numberOfImagesPerPage = -1;
	@JsonProperty(value = "Report Banner (*.jpg) [1200x164 px, 600 dpi]", defaultValue = PreferenceSupplier.DEF_REPORT_BANNER)
	@FileSettingProperty(onlyDirectory = false, validExtensions = {"*.jpg;*.jpeg", "*.*"}, extensionNames = {"JPEG Files", "All Files"})
	private File reportBanner;
	@JsonProperty(value = "Report Slogan", defaultValue = PreferenceSupplier.DEF_REPORT_SLOGAN)
	private String reportSlogan;

	@Override
	protected String getDefaultFolder() {

		return org.eclipse.chemclipse.xxd.process.preferences.PreferenceSupplier.getReportExportFolder();
	}

	public int getNumberOfImagesPerPage() {

		if(numberOfImagesPerPage < 1) {
			return PreferenceSupplier.getNumberImagePages();
		}
		return numberOfImagesPerPage;
	}

	public File getReportBanner() {

		if(reportBanner == null) {
			String banner = PreferenceSupplier.getReportBanner();
			if(banner != null && !banner.isEmpty()) {
				return new File(banner);
			}
		}
		return reportBanner;
	}

	public String getReportSlogan() {

		if(reportSlogan == null) {
			return PreferenceSupplier.getReportSlogan();
		}
		return reportSlogan;
	}
}

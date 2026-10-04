/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
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
package net.openchrom.chromatogram.xxd.report.supplier.masc.escape.settings;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.eclipse.chemclipse.chromatogram.xxd.report.settings.IChromatogramReportSettings;
import org.eclipse.chemclipse.model.settings.AbstractProcessSettings;
import org.eclipse.chemclipse.support.literature.LiteratureReference;
import org.eclipse.chemclipse.support.settings.FileSettingProperty;
import org.eclipse.chemclipse.support.settings.FileSettingProperty.DialogType;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public class ChromatogramReportSettings extends AbstractProcessSettings implements IChromatogramReportSettings {

	@JsonProperty(value = "ESCAPE Master Template", defaultValue = "")
	@FileSettingProperty(dialogType = DialogType.OPEN_DIALOG, validExtensions = ".xltx", allowEmpty = false)
	private File template = null;

	@JsonProperty(value = "Export Folder", defaultValue = "")
	@FileSettingProperty(onlyDirectory = true, dialogType = DialogType.SAVE_DIALOG, allowEmpty = false)
	@JsonPropertyDescription("Save the Excel report in this location.")
	private File exportFolder;

	public File getTemplate() {

		return template;
	}

	public void setTemplate(File template) {

		this.template = template;
	}

	@Override
	public File getExportFolder() {

		return exportFolder;
	}

	public void setExportFolder(File exportFolder) {

		this.exportFolder = exportFolder;
	}

	@Override
	public boolean isAppend() {

		return false;
	}

	@Override
	public String getFileNamePattern() {

		return VARIABLE_CHROMATOGRAM_NAME + VARIABLE_EXTENSION;
	}

	@Override
	public List<LiteratureReference> getLiteratureReferences() {

		return Arrays.asList(createLiteratureReference());
	}

	private static LiteratureReference createLiteratureReference() {

		String content;
		try {
			content = new String(ChromatogramReportSettings.class.getResourceAsStream("tandf_ysic2064_S1.ris").readAllBytes(), StandardCharsets.US_ASCII);
		} catch(Exception e) {
			e.printStackTrace();
			content = "https://doi.org/10.1080/00393630.2019.1594580";
		}

		return new LiteratureReference(content);
	}
}
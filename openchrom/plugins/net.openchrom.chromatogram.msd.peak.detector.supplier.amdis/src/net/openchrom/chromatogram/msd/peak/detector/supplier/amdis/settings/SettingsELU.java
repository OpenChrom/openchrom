/*******************************************************************************
 * Copyright (c) 2020, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.settings;

import java.io.File;

import org.eclipse.chemclipse.support.settings.FileSettingProperty;
import org.eclipse.chemclipse.support.settings.FileSettingProperty.DialogType;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public class SettingsELU extends AbstractProcessSettings {

	@JsonProperty(value = "Result File", defaultValue = "")
	@JsonPropertyDescription("Select the result file.")
	@FileSettingProperty(dialogType = DialogType.OPEN_DIALOG, extensionNames = {"AMDIS (*.elu)"}, validExtensions = {"*.ELU;*.elu"}, onlyDirectory = false)
	private File resultFile;

	public File getResultFile() {

		return resultFile;
	}

	public void setResultFile(File resultFile) {

		this.resultFile = resultFile;
	}
}
